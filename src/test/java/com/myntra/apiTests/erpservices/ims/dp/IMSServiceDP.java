package com.myntra.apiTests.erpservices.ims.dp;

import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class IMSServiceDP {

    private static Long vectorSellerID;

    static {
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
    }
	
	//DP for store_seller_warehouse
	@DataProvider
	public static Object[][] checkmuliplesku_seller_type_comb(ITestContext testContext) {
		String[] sku_seller_type_comb={vectorSellerID+",585868,ON_HAND","2,1251856,ON_HAND"};
		long[] warehouse1={1,2,36,28,45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,1,warehouse1,2004,"Warehouse Inventory Retrieved"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][] checksingleswithseller1(ITestContext testContext) {
		String[] sku_seller_type_comb={vectorSellerID+",585868,ON_HAND"};
		long[] warehouse1={1,2,36,28,45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,1,warehouse1,2004,"Warehouse Inventory Retrieved"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	@DataProvider
	public static Object[][] checksingleswithseller2(ITestContext testContext) {
		String[] sku_seller_type_comb={"2,1251856,ON_HAND"};
		long[] warehouse1={45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,1,warehouse1,2004,"Warehouse Inventory Retrieved"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] checkSingleskustore2(ITestContext testContext) {
		String[] sku_seller_type_comb={vectorSellerID+",980522,ON_HAND"};
		long[] warehouse1={1,2,36,28,45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,1,warehouse1,2004,"Warehouse Inventory Retrieved"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] checkskusellerinvNotPresent(ITestContext testContext) {
		String[] sku_seller_type_comb={"2,7789,ON_HAND"};
		long[] warehouse1={1,2,36,28,45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,1,warehouse1,2005,"No Warehouse Inventory Results Found"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] checkskustoreinvNotPresent(ITestContext testContext) {
		String[] sku_seller_type_comb={"2,7789,ON_HAND"};
		long[] warehouse1={1,2,36,28,45};
		// paramOrderId, paramRespCode
		Object[] arr1 = {sku_seller_type_comb,3,warehouse1,2005,"No Warehouse Inventory Results Found"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	//DP for block inv
	
	@DataProvider
	public static Object[][] blockinvSeller1(ITestContext testContext) {
		String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",3817,1,ON_HAND","1,"+vectorSellerID+",6129,1,ON_HAND","1,"+vectorSellerID+",7790,1,ON_HAND","1,"+vectorSellerID+",5619,1,ON_HAND","1,"+vectorSellerID+",5932,1,ON_HAND","1,"+vectorSellerID+",5933,1,ON_HAND"};
		// paramOrderId, paramRespCode
		Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,2002,"Warehouse Inventory Updated"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvSeller15(ITestContext testContext) {
    String[] qty_sku_warehouse_seller_type_comb={"1,15,3817,20,ON_HAND"};
		
		// paramOrderId, paramRespCode
		Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,2002,"Warehouse Inventory Updated"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvStore2(ITestContext testContext) {
    String[] qty_sku_warehouse_seller_type_comb={"1,1,3817,1,ON_HAND"};
		
		// paramOrderId, paramRespCode
		Object[] arr1 = {qty_sku_warehouse_seller_type_comb,2,2002,"Warehouse Inventory Updated"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvMultipleskuinSameWH(ITestContext testContext) {
      String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",3817,1,ON_HAND","1,"+vectorSellerID+",6952,1,ON_HAND"};
		
		// paramOrderId, paramRespCode
		Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,2002,"Warehouse Inventory Updated"};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvMultipleskuinDiffWH(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",3817,1,ON_HAND","1,"+vectorSellerID+",6952,36,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,2002,"Warehouse Inventory Updated"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvSkuNotPresent(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",121212,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,1007,"Failed to block 1-quantity for skuId:121212, storeId: 1, warehouseId:1 and supplyTypeON_HAND"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvSkuWithAvailableQty0(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"10,"+vectorSellerID+",3832,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,1007,"Failed to block 10-quantity for skuId:3832, storeId: 1, warehouseId:1 and supplyTypeON_HAND"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvCheckAvailableinWHinATP(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",90603,28,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,2002,"Warehouse Inventory Updated"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllSku(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,1,null,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,58,"Error occurred while updating/processing data"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllWH(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",980522,null,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,58,"Error occurred while updating/processing data"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllSuppply_Type(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",980522,1,null"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,58,"Error occurred while updating/processing data"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllStore(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"1,"+vectorSellerID+",980522,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,0,1004,"Invalid IMS Sync inventory operation. Missing warehouse,supply type or store."};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllSeller(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"10,null,980522,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,1007,"Failed to block 10-quantity for skuId:980522, storeId: 1, warehouseId:1 and supplyTypeON_HAND"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] blockinvWithNUllQty(ITestContext testContext) {
		 String[] qty_sku_warehouse_seller_type_comb={"null,"+vectorSellerID+",980522,1,ON_HAND"};
			
			// paramOrderId, paramRespCode
			Object[] arr1 = {qty_sku_warehouse_seller_type_comb,1,58,"Error occurred while updating/processing data"};
			Object[][] dataSet = new Object[][] { arr1 };
			return Toolbox.returnReducedDataSet(dataSet,
					testContext.getIncludedGroups(), 1, 2);
	}
	
	
	//DP for allocate inv
	
	@DataProvider
	public static Object[][] allocateInventory_single_sku(ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"RDTLTRSR00013", "10", "3", "28", "200" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "RDTLTRSR00013", "10", "2",
				"36", "200" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}
	//just for test
	@DataProvider
	public static Object[][] allocateinv(ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] allocatecomb={"10,RDTLTRSR00013,Flipkart_Binola,Gurgaon-Binola W/H"};
		Object[] arr1 = { allocatecomb,"2004","Warehouse Inventory Retrieved","0"};
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "RDTLTRSR00013", "10", "2",
				"36", "200" };
		Object[][] dataSet = new Object[][] { arr1};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventory_multiple_sku(ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount

		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMAWLLT93492", "10", "RDTLMFLR00003", "10", "RDTLSCKS00008",
				"10", "3", "28", "200" };

		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "RDTLTRSR00013", "10",
				"PUMAWLLT93492", "10", "RDTLSCKS00008", "10", "2", "36", "200" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventorySingleSkuMoreThanAvailableQty(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = {
				"Flipkart_Binola",
				"Gurgaon-Binola W/H",
				"KNKHTSHT02241",
				"25",
				"200",
				"Cannot allocate inventory as available inventory is 10 while required qty is 25" };
		String[] arr2 = {
				"Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H",
				"KNKHTSHT02241",
				"25",
				"200",
				"Cannot allocate inventory as available inventory is 10 while required qty is 25" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventorySingleSkuLessThanBOC(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"RDTPFOSH79902", "15", "200",
				"Cannot reduce inventory to 15 when there are 100 orders" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "RDTPFOSH79902", "15", "200",
				"Cannot reduce inventory to 15 when there are 100 orders" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventoryMultipleSkuLessThanBOC(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"RDTPFOSH79902", "0", "PUMALNGP01005", "0", "PUMATSRM01391",
				"0", "200",
				"Cannot reduce inventory to 0 when there are 100 orders" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "RDTPFOSH79902", "0",
				"PUMATSRM01391", "0", "PUMALNGP01005", "0", "200",
				"Cannot reduce inventory to 0 when there are 100 orders" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventorySingleSkuIncrement(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"MTHBSHRT01069", "2", "3", "28", "200" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "MTHBSHRT01069", "2", "2",
				"36", "200" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	/*
	 * @DataProvider public Object[][] allocateInventoryMultipleSkuIncrement(
	 * ITestContext testContext) { //
	 * orderItemId(releaseid),orderEvent,orderitemid
	 * (lineid),Hold,Dispatchbydate,
	 * "quantity",cancelledQuantity,ListingID,"sku",
	 * "price","pincode",response,statusCode,statusMessage,statusType,totalCount
	 * String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
	 * "PUMATSRM01371", "2", "PUMATSRM01370", "2", "PUMATSRM01370",
	 * "2","3","28", "200" };
	 * 
	 * String[] arr2 = { "Flipkart_Bangalore", "Bangalore - New Whitefield W/H",
	 * "PUMATSRM01371", "2", "PUMATSRM01370", "2", "PUMATSRM01370",
	 * "2","3","28", "200" }; Object[][] dataSet = new Object[][] { arr1, arr2
	 * };
	 * 
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 2, 2); }
	 */

	@DataProvider
	public static Object[][] allocateInventory25MultipleSku(ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMATSRM01382", "25", "PUMATSRM01384", "25", "PUMATSRM01383",
				"25", "200","3", "28"};

		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "PUMATSRM01383", "25",
				"PUMATSRM01382", "25", "PUMATSRM01384", "25", "200", "2", "36", };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventory25SingleSku(ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMATSRM01385", "25", "200" , "3", "28"};
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "PUMATSRM01385", "25", "200","2","36" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	/*
	 * @DataProvider public Object[][]
	 * allocateInventoryGreaterThan25MultipleSku(ITestContext testContext) { //
	 * orderItemId
	 * (releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,
	 * "quantity",
	 * cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode
	 * ,statusMessage,statusType,totalCount String[] arr1 = { "Flipkart_Binola",
	 * "Gurgaon-Binola W/H", "PUMATSRM01382", "25", "PUMATSRM01384", "25",
	 * "PUMATSRM01383", "25","3","28", "200",
	 * "Required quantity 27 is greater than maximum allowed quantity for fk (25)"
	 * };
	 * 
	 * String[] arr2 = { "Flipkart_Bangalore", "Bangalore - New Whitefield W/H",
	 * "PUMATSRM01383", "25", "PUMATSRM01382", "25", "PUMATSRM01384",
	 * "25","3","28", "200",
	 * "Required quantity 27 is greater than maximum allowed quantity for fk (25)"
	 * }; Object[][] dataSet = new Object[][] { arr1, arr2 };
	 * 
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 2, 2); }
	 */

	@DataProvider
	public static Object[][] allocateInventoryGreaterThan25SingleSku(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMATSRM01385", "25", "200",
				"Required quantity 27 is greater than maximum allowed quantity for fk (25)" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "PUMATSRM01385", "25", "200",
				"Required quantity 27 is greater than maximum allowed quantity for fk (25)" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventorydecrementInvSingleSku(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMATSPM01139", "5","200","3", "28" };
		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "PUMATSPM01139", "5","200","2",
				"36" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] allocateInventorydecrementInvMultipleSku(
			ITestContext testContext) {
		// orderItemId(releaseid),orderEvent,orderitemid(lineid),Hold,Dispatchbydate,"quantity",cancelledQuantity,ListingID,"sku","price","pincode",response,statusCode,statusMessage,statusType,totalCount
		String[] arr1 = { "Flipkart_Binola", "Gurgaon-Binola W/H",
				"PUMASHSM00704", "5", "PUMASHSM00706", "5", "PUMASHSM00703",
				"25", "200", "3", "28" };

		String[] arr2 = { "Flipkart_Bangalore",
				"Bangalore - New Whitefield W/H", "PUMASHSM00704", "5",
				"PUMASHSM00706", "5", "PUMASHSM00703", "5", "200", "2", "36", };
		Object[][] dataSet = new Object[][] { arr1, arr2 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}
	
	//DP for syncinv
	
	@DataProvider
	public static Object[][] invincrement(
			ITestContext testContext) {
		//upload inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","100"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] checkabsolute(
			ITestContext testContext) {
		//upload inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","100"};
				Object[][] dataSet = new Object[][] {arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] invdecrement(
			ITestContext testContext) {
		//dec inv 
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","10"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] invsetto0(
			ITestContext testContext) {
		//set inv 0
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] invsetto0WhenbocMorethan0(
			ITestContext testContext) {
		//set inv 0 when boc more than 0
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] setinvLessthanboc(
			ITestContext testContext) {
		//set inv less than boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","5"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] setinvequalsboc(
			ITestContext testContext) {
		//set inv equal boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","5"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] setinvgreterthanboc(
			ITestContext testContext) {
		//set inv greater boc
		String[] arr1 = {"36", "1",""+vectorSellerID,"ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] uploadinvForNewSku(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"46", "1","18","ON_HAND","123","AUTOMATIONSKU1","PUREPLAY_SELLER_ALLOCATE","1001"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][] random_warehouse(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"7117", "1","1","ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  random_storeid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"36", "1212121","1","ON_HAND","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  random_supplyType(
			ITestContext testContext) {
		//set new sku
		String[] arr1 ={"36", "1","19","sjahslkhaklhsalk","12216","ADDSFLTM09247","PUREPLAY_SELLER_ALLOCATE","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  random_whInventoryOperation(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"36", "1","19","ON_HAND","12216","ADDSFLTM09247","dgsdgksjkjd","15"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	//get APIs
	//DP for wh_inv search
	
	@DataProvider
	public static Object[][]  search_inventory_withinvalidwarehouseid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","35","BFC","2004","Warehouse Inventory Retrieved","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	
	@DataProvider
	public static Object[][]  search_inventory_withinvaliskuid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BFC123","2004","Warehouse Inventory Retrieved","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  search_inventory_with_valiskuid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BLFLDRSS00045","2004","Warehouse Inventory Retrieved","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  search_inventory_with_valid_warehouse_id(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BLF","2004","Warehouse Inventory Retrieved","8"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
// DP for search core_inv
	
	
	@DataProvider
	public static Object[][]  search_core_inventory_withinvalidwarehouseid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","35","BLFLDRSS00045","Q1","2004","Warehouse Inventory Retrieved","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  search_core_inventory_withinvaliskuid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BFC123","Q1","2004","Warehouse Inventory Retrieved","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  search_core_inventory_with_valiskuid(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BLFLDRSS00045","Q1","2004","Warehouse Inventory Retrieved","1"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  search_core_inventory_with_valid_warehouse_id(
			ITestContext testContext) {
		//set new sku
		String[] arr1 = {"10", "DESC","1","BLFLDRSS00045","Q1","2004","Warehouse Inventory Retrieved","1"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
//bulk sync inv DP	
	
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_seller_wh_increment(
			ITestContext testContext) {
		String[] bulupdate={"46,1,18,ON_HAND,1251880,XNYYSHRT00235,SELLER_ALLOCATE,Q1,Q1,11,3","46,1,18,ON_HAND,1251857,XNYYSHRT00212,SELLER_ALLOCATE,Q1,Q1,11,4"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_seller_wh_decrement(
			ITestContext testContext) {
		String[] bulupdate={"55,1,20,ON_HAND,26482,XNYYSHRT00213,SELLER_ALLOCATE,Q1,Q1,2,3","55,1,20,ON_HAND,19427,XNYYSHRT00214,SELLER_ALLOCATE,Q1,Q1,2,4"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_seller_wh_decrement_less_than_boc(
			ITestContext testContext) {
		String[] bulupdate={"55,1,20,ON_HAND,1251860,XNYYSHRT00215,SELLER_ALLOCATE,Q1,Q1,2,3","55,1,20,ON_HAND,1251876,XNYYSHRT00231,SELLER_ALLOCATE,Q1,Q1,2,4"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_seller_wh_increment_greater_than_boc(
			ITestContext testContext) {
		String[] bulupdate={"55,1,20,ON_HAND,1251877,XNYYSHRT00232,SELLER_ALLOCATE,Q1,Q1,7,3","55,1,20,ON_HAND,1251878,XNYYSHRT00233,SELLER_ALLOCATE,Q1,Q1,7,4"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_myntra_wh_increment(
			ITestContext testContext) {
				String[] bulupdate={"36,1,"+vectorSellerID+",ON_HAND,19481,PUMATSRM01389,SELLER_ALLOCATE,Q1,Q1,12,1","36,1,"+vectorSellerID+",ON_HAND,38910,PUMATSRM01390,SELLER_ALLOCATE,Q1,Q1,12,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_myntra_wh_decrement(
			ITestContext testContext) {
				String[] bulupdate={"36,1,"+vectorSellerID+",ON_HAND,3839,PUMATSRM01391,SELLER_ALLOCATE,Q1,Q1,2,1","36,1,"+vectorSellerID+",ON_HAND,3840,PUMATSRM01392,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_myntra_wh_decrement_less_than_boc(
			ITestContext testContext) {
		//set new sku
		String[] bulupdate={"36,1,"+vectorSellerID+",ON_HAND,3841,PUMATSRM01393,SELLER_ALLOCATE,Q1,Q1,2,1","36,1,"+vectorSellerID+",ON_HAND,3842,PUMATSRM01394,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_sku_myntra_wh_increment_greater_than_boc(
			ITestContext testContext) {
				String[] bulupdate={"36,1,"+vectorSellerID+",ON_HAND,3843,PUMATSRM01395,SELLER_ALLOCATE,Q1,Q1,10,2","36,1,"+vectorSellerID+",ON_HAND,3844,PUMATSRM01395,SELLER_ALLOCATE,Q1,Q1,10,2"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_new_sku_seller_wh_increment(
			ITestContext testContext) {
				String[] bulupdate={"45,1,"+vectorSellerID+",ON_HAND,11640,AUTOMATIONSKU1,SELLER_ALLOCATE,Q1,Q1,2,2","45,1,"+vectorSellerID+",ON_HAND,23321,AUTOMATIONSKU2,SELLER_ALLOCATE,Q1,Q1,2,2"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_new_sku_seller_wh_decrement(
			ITestContext testContext) {
			//	String[] bulupdate={"45,1,"+vectorSellerID+",ON_HAND,1251666,XNYYSHRT00021,SELLER_ALLOCATE,Q1,Q1,2,2"};
						//"45,"+vectorSellerID+",4,ON_HAND,4405265,AUTOMATIONSKU4,SELLER_ALLOCATE,Q1,Q1,2,1"};
				String[] bulupdate={"45,1,"+vectorSellerID+",ON_HAND,11439,AUTOMATIONSKU3,SELLER_ALLOCATE,Q1,Q1,2,2","45,1,"+vectorSellerID+",ON_HAND,4405265,AUTOMATIONSKU4,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_new_sku_seller_wh_decrement_less_than_boc(
			ITestContext testContext) {
				String[] bulupdate={"45,2,"+vectorSellerID+",ON_HAND,11787,AUTOMATIONSKU5,SELLER_ALLOCATE,Q1,Q1,2,2","45,2,"+vectorSellerID+",ON_HAND,14261,AUTOMATIONSKU6,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  bulk_sysncinv_multiple_new_sku_seller_wh_increment_more_than_boc(
			ITestContext testContext) {
				String[] bulupdate={"45,2,"+vectorSellerID+"ON_HAND,11814,AUTOMATIONSKU7,SELLER_ALLOCATE,Q1,Q1,2,2","45,2,"+vectorSellerID+",ON_HAND,14262,AUTOMATIONSKU8,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  bulk_sysncinv_seller_warehouse_mismatch(
			ITestContext testContext) {
				String[] bulupdate={"36,2,"+vectorSellerID+",ON_HAND,14261,AUTOMATIONSKU7,SELLER_ALLOCATE,Q1,Q1,2,1","45,2,"+vectorSellerID+",ON_HAND,14262,AUTOMATIONSKU8,SELLER_ALLOCATE,Q1,Q1,2,1"};
		Object[] arr1 = {bulupdate,"2002","Warehouse Inventory Updated","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
//getSLADP
	@DataProvider
	public static Object[][]  getSLAvalidcombSupplytypeONHAND(
			ITestContext testContext) {
				String[] bulupdate={"1,3871,ON_HAND,36","1,3872,ON_HAND,36"};
		Object[] arr1 = {bulupdate,"3","Success","50"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  getSLAvalidcombSupplytypeJIT(
			ITestContext testContext) {
				String[] bulupdate={"1,3873,JUST_IN_TIME,36","1,3874,JUST_IN_TIME,36"};
		Object[] arr1 = {bulupdate,"3","Success","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
//validsellerwarehouse
	
	@DataProvider
	public static Object[][]  validSellerWarehouse(
			ITestContext testContext) {
		String[] arr1 = {"36","1","1","ON_HAND","SELLER_ALLOCATE","PUMATSRM01389","19481","Q1","Q1","3","100","3","Success","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
//core_warehouses
	@DataProvider
	public static Object[][]  core_warehouse_validskuandNonMyntraWarehouse(
			ITestContext testContext) {
		long sku_id[]={1251856,834230};
		Object[] arr1 = {"Q1","45",sku_id,"2005","No Warehouse Inventory Results Found","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public static Object[][]  core_warehouse_validskuandMyntraWarehouse(
			ITestContext testContext) {
		long sku_id[]={40656,40657};
		Object[] arr1 = {"Q1","36",sku_id,"2004","Warehouse Inventory Retrieved","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  core_warehouse_invalidWarehouse(
			ITestContext testContext) {
		long sku_id[]={1251856,41804};
		Object[] arr1 = {"Q1","11112222444",sku_id,"2005","No Warehouse Inventory Results Found","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  core_warehouse_validskuandMyntraWarehouseQ2(
			ITestContext testContext) {
		long sku_id[]={18907,11862};
		Object[] arr1 = {"Q2","1",sku_id,"2004","Warehouse Inventory Retrieved","2"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	@DataProvider
	public static Object[][]  core_warehouse_invalidsku(
			ITestContext testContext) {
		long sku_id[]={0,0};
		Object[] arr1 = {"Q1","1",sku_id,"2005","No Warehouse Inventory Results Found","0"};
				Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
}

