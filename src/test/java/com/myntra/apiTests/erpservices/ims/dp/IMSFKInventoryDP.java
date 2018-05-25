package com.myntra.apiTests.erpservices.ims.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
/**
 * @author santwana.samantray
 *
 */

public class IMSFKInventoryDP {
	
	final String store2sku_barcode_single="DHLNSHSM00649";
	final String store2sku_barcode_multiple_1="DHLNSHSM00651";
	final String store2sku_barcode_multiple_2="DHLNSHSM00652";
	final String store2sku_barcode_multiple_3="DHLNSHSM00657";
	final String store3sku_barcode_single="DHLNSHSM00659";
	final String store3sku_barcode_multiple_1="DHLNSHSM00692";
	final String store3sku_barcode_multiple_2="DHLNSHSM00693";
	final String store3sku_barcode_multiple_3="DHLNSHSM00694";
	final String store4sku_barcode_single="RDSTJENS01941";
	final String store4sku_barcode_multiple_1="NIKEBAPK99759";
	final String store4sku_barcode_multiple_2="LBASKRTA00005965";
	
	@DataProvider
	public static Object[][] allocateinv_increment_inv(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"10,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"10,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"10,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),1","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),1","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),1"};	
		String[] store_warehouse_combo3_multi={"10,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"10,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,1"};		
		String[] store_warehouse_combo4_reallocation_false={"10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
        String[] store_warehouse_combo3_reallocation_false={"10,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"10,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"10,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};		
		String[] store_warehouse_combo3_multi_reallocation_false={"10,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"10,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		//storeid,whid,autooverride,status, String statusCode, String statusMessage,StatusType
		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_decrement_inv(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };		
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_lessthan0(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"-1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"-1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"-1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","-1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"-1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","-1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","-1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"-1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","-1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","-1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"-1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"-1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"-1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","-1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"-1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","-1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","-1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"-1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","-1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","-1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_lessthanboc(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1,NIKEBAPK99759,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	
	@DataProvider
	public static Object[][] allocateinv_MoreThanAvailableInv(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1000,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1000,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1000,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1000,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1000,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1000,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1000,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1000,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","1000,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","1000,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1000,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1000,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1000,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1000,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1000,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1000,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1000,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1000,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","1000,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","1000,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11= {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	
	@DataProvider
	public static Object[][] allocateinv_increment_inv_withAppPropertyswitchedoff(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"10,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"10,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"10,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"10,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"10,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,1"};		
		String[] store_warehouse_combo4_reallocation_false={"10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"10,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"10,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"10,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"10,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"10,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_decrement_inv_withAppPropertyswitchedoff(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,1","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_lessthan0_withAppPropertyswitchedoff(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"-1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"-1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"-1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","-1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"-1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","-1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","-1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"-1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","-1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","-1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"-1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"-1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"-1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","-1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","-1,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"-1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","-1,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","-1,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"-1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","-1,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","-1,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_lessthanboc_withAppPropertyswitchedoff(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,1","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,1"};		
		String[] store_warehouse_combo4_reallocation_false={"1,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","10,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","10,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","10,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_MoreThanAvailableInv_withAppPropertyswitchedoff(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"1000,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"1000,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"1000,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","1000,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"1000,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1000,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","1000,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"1000,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","1000,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","1000,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"1000,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"1000,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"1000,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1000,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","1000,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"1000,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1000,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","1000,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"1000,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","1000,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","1000,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	
	
	@DataProvider
	public static Object[][] allocateinv_fromFKWhenThresholdBreachedInMyntraStore(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"0,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"0,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"0,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","0,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"0,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","0,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","0,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"0,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","0,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","0,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		String[] store_warehouse_combo4_reallocation_false={"0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"0,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"0,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"0,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","0,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"0,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","0,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","0,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"0,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","0,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","0,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr7 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr8 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr9 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr10 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr11 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr12 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };

		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_breachThresholdForFKwithOverridesettotrue(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4={"0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3={"0,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2={"0,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,true"};
		String[] store_warehouse_combo4_multi={"0,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),true","0,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),true"};
		String[] store_warehouse_combo3_multi={"0,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","0,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true","0,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,true"};
		String[] store_warehouse_combo2_multi={"0,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,true","0,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,true","0,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,true"};		
		
		Object[] arr1 = {store_warehouse_combo4,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr2 = {store_warehouse_combo3,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr3 = { store_warehouse_combo2, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		Object[] arr4 = {store_warehouse_combo4_multi,"4", "81","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr5 = {store_warehouse_combo3_multi,"2", "36","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS"};
		Object[] arr6 = { store_warehouse_combo2_multi, "3","28","true", "200","2004","Warehouse Inventory Retrieved","SUCCESS" };
		
		Object[][] dataSet = new Object[][] {  arr1, arr2,arr3,arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
	}
	@DataProvider
	public static Object[][] allocateinv_breachThresholdForFKwithOverridesettofalse(ITestContext testContext) {
		//quality,skucode,storename,whname
		String[] store_warehouse_combo4_reallocation_false={"0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_reallocation_false={"0,DHLNSHSM00649,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_reallocation_false={"0,DHLNSHSM00659,Flipkart_Binola,Gurgaon-Binola W/H,false"};
		String[] store_warehouse_combo4_multi_reallocation_false={"0,NIKEBAPK99759,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","0,RDSTJENS01941,flipkart_mumbai,Myntra warehouse ( Mumbai ),false","0,LBASKRTA00005965,flipkart_mumbai,Myntra warehouse ( Mumbai ),false"};
		String[] store_warehouse_combo3_multi_reallocation_false={"0,DHLNSHSM00651,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","0,DHLNSHSM00652,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false","0,DHLNSHSM00657,Flipkart_Bangalore,Bangalore - New Whitefield W/H,false"};
		String[] store_warehouse_combo2_multi_reallocation_false={"0,DHLNSHSM00692,Flipkart_Binola,Gurgaon-Binola W/H,false","0,DHLNSHSM00693,Flipkart_Binola,Gurgaon-Binola W/H,false","0,DHLNSHSM00694,Flipkart_Binola,Gurgaon-Binola W/H,false"};		

		Object[] arr1 = {store_warehouse_combo4_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr2 = {store_warehouse_combo3_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr3 = { store_warehouse_combo2_reallocation_false, "3","28", "false","200","2004","Warehouse Inventory Retrieved","ERROR" };
		Object[] arr4 = {store_warehouse_combo4_multi_reallocation_false,"4", "81","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr5 = {store_warehouse_combo3_multi_reallocation_false,"2", "36","false", "200","2004","Warehouse Inventory Retrieved","ERROR"};
		Object[] arr6 = { store_warehouse_combo2_multi_reallocation_false, "3","28","false", "200","2004","Warehouse Inventory Retrieved","ERROR" };

		Object[][] dataSet = new Object[][] {arr1,arr2,arr3,arr4,arr5,arr6};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 2);
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
				testContext.getIncludedGroups(), 4, 2);
	}

	

}
