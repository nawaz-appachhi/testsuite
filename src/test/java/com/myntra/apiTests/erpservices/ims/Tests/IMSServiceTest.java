/**
 * 
 */
package com.myntra.apiTests.erpservices.ims.Tests;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.ims.dp.IMSServiceDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.commons.response.EmptyResponse;
import com.myntra.ims.client.entry.SkuWhLvlWhInventoryEntry;
import com.myntra.ims.client.response.*;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author santwana.samantray
 *
 */
public class IMSServiceTest extends BaseTest {
	static Logger log = Logger.getLogger(IMSServiceTest.class);
	static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private static Long vectorSellerID;

	@BeforeClass(alwaysRun = true)
	public void setinvIMSbeforeTests() throws SQLException,
			UnsupportedEncodingException, JAXBException {
		{
			vectorSellerID = SellerConfig
					.getSellerID(SellerConfig.SellerName.HANDH);
			DBUtilities
					.exUpdateQuery(
							"update wh_inventory set inventory_count=100,blocked_order_count=10 where sku_id in(585868,1251856,980522,7789,3817,6952)",
							"IMS");
			DBUtilities
					.exUpdateQuery(
							"update inventory set inventory_count=201,available_in_warehouses = '1,28,36',blocked_order_count=1 where sku_id=90603",
							"ATP");
			DBUtilities
					.exUpdateQuery(
							"update wh_inventory set inventory_count=1,blocked_order_count=0 where warehouse_id=1 and sku_id=90603 and seller_id=73",
							"IMS");
			DBUtilities
					.exUpdateQuery(
							"update wh_inventory set inventory_count=100 where warehouse_id in (36,28) and sku_id=90603 and seller_id=73",
							"IMS");
			String query1 = "update wh_inventory set inventory_count=1000 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860) and store_id =1 and warehouse_id in (36,28) ";
			String query2 = "update inventory set inventory_count=2000 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860)";
			String query3 = "update wh_inventory set blocked_order_count=100 where sku_code in ('RDTPFOSH79902','PUMATSRM01391','PUMALNGP01005')and store_id in(2,3) and warehouse_id in (36,28)";
			String query4 = "update wh_inventory set inventory_count=10 where sku_code='KNKHTSHT02241'and store_id =1 and warehouse_id in (36,28);";
			DBUtilities.exUpdateQuery(query1, "myntra_ims");
			DBUtilities.exUpdateQuery(query3, "myntra_ims");
			DBUtilities.exUpdateQuery(query2, "myntra_atp");
			DBUtilities.exUpdateQuery(query4, "myntra_ims");
			String query5 = "update wh_inventory set inventory_count=10 where sku_id in(1251856,1251857,26482,19427,1251860,1251876,1251877,1251878,19481,38910,3839,3840,3841,3842,3843,3844)";
			String query6 = "update wh_inventory set blocked_order_count=5 where sku_id in(3843,3844,3841,3842,1251877,1251878,1251860,1251876)";
			String query7 = "update inventory set inventory_count=10 where sku_id in(1251856,1251857,26482,19427,1251860,1251876,1251877,1251878,19481,38910,3839,3840,3841,3842,3843,3844)";
			String query8 = "update inventory set blocked_order_count=5 where sku_id in(1251856,1251857,26482,19427,1251860,1251876,1251877,1251878,19481,38910,3839,3840,3841,3842,3843,3844)";
			String query9 = "update wh_inventory set inventory_count=1000, store_id=1,supply_type='ON_HAND', seller_id=1,warehouse_id=36 where sku_id in(3871,3872)";

			DBUtilities.exUpdateQuery(query5, "IMS");
			DBUtilities.exUpdateQuery(query6, "IMS");
			DBUtilities.exUpdateQuery(query7, "ATP");
			DBUtilities.exUpdateQuery(query8, "ATP");
			DBUtilities.exUpdateQuery(query9, "IMS");
		}
	}

	public static List<Integer> imsandatpdetails(String sku_id,
			String store_id, String warehouse_id) {
		List<Integer> imsandatpdetailslist = new ArrayList<Integer>();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int ims_inventory_count = 0;
		int ims_inv_stor_1_count = 0;
		int ims_boc = 0;
		int atp_inv_count = 0;
		String query1 = "Select * from wh_inventory where sku_code =" + "'"
				+ sku_id + "'" + " and warehouse_id=" + warehouse_id
				+ " and store_id=" + store_id + ";";
		System.out.println(query1);
		String query2 = "Select * from wh_inventory where sku_code =" + "'"
				+ sku_id + "'" + " and warehouse_id=" + warehouse_id
				+ " and store_id=1" + ";";
		System.out.println(query2);
		String query3 = "Select * from inventory where sku_code =" + "'"
				+ sku_id + "'" + ";";
		System.out.println(query3);

		Map<String, Object> rset1 = DBUtilities.exSelectQueryForSingleRecord(
				query1, "myntra_ims");
		ims_inventory_count = (int) rset1.get("inventory_count");
		System.out.println("ims inv count is for fk store is : "
				+ ims_inventory_count);
		imsandatpdetailslist.add(ims_inventory_count);// 0th element in list
		ims_boc = (int) rset1.get("blocked_order_count");
		System.out.println("ims boc count is  for fk store is : " + ims_boc);

		imsandatpdetailslist.add(ims_boc);// 1st element boc fk store
		Map<String, Object> rset3 = DBUtilities.exSelectQueryForSingleRecord(
				query3, "myntra_atp");
		atp_inv_count = (int) rset3.get("inventory_count");
		System.out.println("ims inv in store 1" + atp_inv_count);

		imsandatpdetailslist.add(atp_inv_count);// 2nd element atp inv

		Map<String, Object> rset2 = DBUtilities.exSelectQueryForSingleRecord(
				query2, "myntra_ims");

		ims_inv_stor_1_count = (int) rset2.get("inventory_count");
		System.out.println("inv count ims store_id1" + ims_inv_stor_1_count);

		imsandatpdetailslist.add(ims_inv_stor_1_count);
		// System.out.println(imsandatpdetailslist);

		return imsandatpdetailslist;
	}

	public static List<Integer> getims_inv_details(String skuid,
			String warehouse_id, String seller, String store) {

		//End2EndHelper.sleep(10000L);
		List<Integer> invlist = new ArrayList<Integer>();
		int wh_inventory = 0;
		int get_boc = 0;

		End2EndHelper.sleep(1000L);

		Map<String, Object> rset = DBUtilities
				.exSelectQueryForSingleRecord(
						"select * from wh_inventory where sku_id =" + skuid
								+ " and warehouse_id=" + warehouse_id
								+ " and seller_id=" + seller + " and store_id="
								+ store, "myntra_ims");

		wh_inventory = (int) rset.get("inventory_count");
		get_boc = (int) rset.get("blocked_order_count");
		invlist.add(wh_inventory);
		invlist.add(get_boc);
		return invlist;
	}

	public static List<Integer> getatp_inv_details(String skuid,
			String warehouse_id, String seller, String store) {

		//End2EndHelper.sleep(1000L);
		List<Integer> invlist = new ArrayList<Integer>();
		int atp_inventory = 0;
		int boc = 0;

		End2EndHelper.sleep(1000L);

		Map<String, Object> rset = DBUtilities
				.exSelectQueryForSingleRecord(
						"select * from inventory where sku_id =" + skuid
								+ " and seller_id=" + seller + " and store_id="
								+ store, "myntra_atp");
		atp_inventory = (int) rset.get("inventory_count");
		boc = (int) rset.get("blocked_order_count");
		invlist.add(atp_inventory);
		invlist.add(boc);
		return invlist;
	}

	public static void setinvIMS(String inv, String boc, String sku_id,
			String warehouse_id) {
		DBUtilities.exUpdateQuery("update wh_inventory set inventory_count="
				+ inv + ", blocked_order_count=" + boc + " where sku_id="
				+ sku_id + " and warehouse_id=" + warehouse_id, "myntra_ims");
	}

	public static void setinvATP(String inv, String boc, String sku_id) {
		DBUtilities.exUpdateQuery("update inventory set inventory_count=" + inv
				+ ", blocked_order_count=" + boc + " where sku_id=" + sku_id,
				"myntra_atp");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "checksingleswithseller1", dataProviderClass = IMSServiceDP.class)
	public void checksingleWarehouse_SKU_Seller1_Store1_type_inv_entry(
			String[] sellersku, long store, long[] warehouse, long statusCode,
			String statusMessage) throws IOException, JAXBException {
		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response for the serbice is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Available inventory is", "90", testresponse
				.getData().get(0).getAvailableInventory().toString());
		System.out.println("invcout is:"
				+ testresponse.getData().get(0).getAvailableInventory());

	}

	@Test(groups = { "Regression" }, dataProvider = "checkmuliplesku_seller_type_comb", dataProviderClass = IMSServiceDP.class)
	public void checkmuliplesku_seller_type_comb(String[] sellersku,
			long store, long[] warehouse, long statusCode, String statusMessage)
			throws IOException, JAXBException {

		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response for the serbice is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());

		for(SkuWhLvlWhInventoryEntry skuWhLvlWhInventoryEntry: testresponse.getData()) {
			if (skuWhLvlWhInventoryEntry.getSkuId() == 1251856l)
				AssertJUnit.assertEquals("Available inventory is", "0", skuWhLvlWhInventoryEntry.getAvailableInventory().toString());
			else
				AssertJUnit.assertEquals("Available inventory is", "90", skuWhLvlWhInventoryEntry.getAvailableInventory().toString());
		}


	}

	@Test(groups = { "Regression" }, dataProvider = "checksingleswithseller2", dataProviderClass = IMSServiceDP.class)
	public void checksingleWarehouse_SKU_Seller2_Store1_type_inv_entry(
			String[] sellersku, long store, long[] warehouse, long statusCode,
			String statusMessage) throws IOException, JAXBException {
		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response status_code:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response status_message is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Available inventory is", "0", testresponse
				.getData().get(0).getAvailableInventory().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "checkSingleskustore2", dataProviderClass = IMSServiceDP.class)
	public void checksingleWarehouse_SKU_Seller1_Store2_type_inv_entry(
			String[] sellersku, long store, long[] warehouse, long statusCode,
			String statusMessage) throws IOException, JAXBException {

		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Available inventory is", "90", testresponse
				.getData().get(0).getAvailableInventory().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "checkskusellerinvNotPresent", dataProviderClass = IMSServiceDP.class)
	public void checkskusellerinvNotPresent(String[] sellersku, long store,
			long[] warehouse, long statusCode, String statusMessage)
			throws IOException, JAXBException {

		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());

	}

	@Test(groups = { "Regression" }, dataProvider = "checkskustoreinvNotPresent", dataProviderClass = IMSServiceDP.class)
	public void checkskustoreskuinvNotPresent(String[] sellersku, long store,
			long[] warehouse, long statusCode, String statusMessage)
			throws IOException, JAXBException {

		StoreLvlWhInventoryResponse testresponse = imsServiceHelper
				.getStoreLvlWhInventory(sellersku, store, warehouse);
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinvSeller1", dataProviderClass = IMSServiceDP.class)
	public void blockinvSeller1(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvSeller15", dataProviderClass = IMSServiceDP.class)
	public void blockinvSeller15(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvStore2", dataProviderClass = IMSServiceDP.class)
	public void blockinvStore2(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvMultipleskuinSameWH", dataProviderClass = IMSServiceDP.class)
	public void blockinvMultipleskuinSameWH(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvMultipleskuinDiffWH", dataProviderClass = IMSServiceDP.class)
	public void blockinvMultipleskuinDiffWH(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvSkuNotPresent", dataProviderClass = IMSServiceDP.class)
	public void blockinvSkuNotPresent(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvSkuWithAvailableQty0", dataProviderClass = IMSServiceDP.class)
	public void blockinvSkuWithAvailableQty0(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvCheckAvailableinWHinATP", dataProviderClass = IMSServiceDP.class)
	public void blockinvCheckAvailableinWHinATP(String[] blockwhentry,
			long store, long statusCode, String statusMessage)
			throws IOException, JAXBException, SQLException {
		Map<String, Object> atpmap = new HashMap<String, Object>();
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		End2EndHelper.sleep(10000L);
		atpmap = DBUtilities.exSelectQueryForSingleRecord(
				"select * from inventory where sku_id=90603", "ATP");
		String availableinWH = atpmap.get("available_in_warehouses").toString();
		System.out.println("Availbale in warehouse are:" + availableinWH);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Available in warehouses", "28",
				availableinWH);

	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvWithNUllWH", dataProviderClass = IMSServiceDP.class)
	public void blockinvWithNUllWH(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	// allocate inventory more than available single sku

	@Test(groups = { "Regression" }, dataProvider = "blockinvWithNUllSuppply_Type", dataProviderClass = IMSServiceDP.class)
	public void blockinvWithNUllSuppply_Type(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Regression" }, dataProvider = "blockinvWithNUllStore", dataProviderClass = IMSServiceDP.class)
	public void blockinvWithNUllStore(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	// allocate inventory less than blocked order count for multiple sku

	@Test(groups = { "Regression" }, dataProvider = "blockinvWithNUllSeller", dataProviderClass = IMSServiceDP.class)
	public void blockinvWithNUllSeller(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	// allocate inventory less than blocked order count for multiple sku

	@Test(groups = { "Regression" }, dataProvider = "blockinvWithNUllQty", dataProviderClass = IMSServiceDP.class)
	public void blockinvWithNUllQty(String[] blockwhentry, long store,
			long statusCode, String statusMessage) throws IOException,
			JAXBException {
		BlockWhInventoryResponse testresponse = imsServiceHelper
				.blockInventoryIMSHelper(blockwhentry, store);
		System.out.println("Status Code is:"
				+ testresponse.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status Code is", statusCode, testresponse
				.getStatus().getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "invincrement", dataProviderClass = IMSServiceDP.class)
	public void invincrement(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("0", "0", sku_id);
		setinvIMS("0", "0", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = 100;
		atp_boc_b = 0;
		ims_inv_b = 100;
		ims_boc_b = 0;

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "checkabsolute", dataProviderClass = IMSServiceDP.class)
	public void checkabsolute(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("100", "0", sku_id);
		setinvIMS("100", "0", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);
	}

	@Test(groups = { "Regression" }, dataProvider = "invdecrement", dataProviderClass = IMSServiceDP.class)
	public void invdecrement(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("100", "0", sku_id);
		setinvIMS("100", "0", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = 10;
		ims_inv_b = 10;

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "invsetto0", dataProviderClass = IMSServiceDP.class)
	public void invsetto0(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("10", "0", sku_id);
		setinvIMS("10", "0", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		ims_inv_b = 0;
		atp_inv_b = 0;

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "invsetto0WhenbocMorethan0", dataProviderClass = IMSServiceDP.class)
	public void invsetto0WhenbocMorethan0(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("40", "30", sku_id);
		setinvIMS("40", "30", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = atp_boc_b;
		ims_inv_b = ims_boc_b;
		
		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
	//	Assert.assertEquals(ims_boc_b, 30);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "setinvLessthanboc", dataProviderClass = IMSServiceDP.class)
	public void setinvLessthanboc(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("11", "10", sku_id);
		setinvIMS("11", "10", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = Integer.parseInt(quantity) + atp_boc_b;
		ims_inv_b = Integer.parseInt(quantity) + ims_boc_b;
		System.out.println(atp_inv_b);

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "setinvequalsboc", dataProviderClass = IMSServiceDP.class)
	public void setinvequalsboc(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("10", "5", sku_id);
		setinvIMS("10", "5", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = Integer.parseInt(quantity) + atp_boc_b;
		ims_inv_b = Integer.parseInt(quantity) + ims_boc_b;

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "setinvgreterthanboc", dataProviderClass = IMSServiceDP.class)
	public void setinvgreterthanboc(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		setinvATP("10", "5", sku_id);
		setinvIMS("10", "5", sku_id, warehouse);

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		atp_inv_b = Integer.parseInt(quantity) + atp_boc_b;
		ims_inv_b = Integer.parseInt(quantity) + ims_boc_b;

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Regression" }, dataProvider = "uploadinvForNewSku", dataProviderClass = IMSServiceDP.class)
	public void uploadinvForNewSku(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {
		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		List<Integer> inventoryDetailsFromAtp = getatp_inv_details(sku_id,
				warehouse, seller, store);
		int atp_inv_a = inventoryDetailsFromAtp.get(0);
		int atp_boc_a = inventoryDetailsFromAtp.get(1);
		List<Integer> inventoryDetailsFromIms = getims_inv_details(sku_id,
				warehouse, seller, store);
		int ims_inv_a = inventoryDetailsFromIms.get(0);
		int ims_boc_a = inventoryDetailsFromIms.get(1);
		Assert.assertEquals(atp_inv_a, Integer.parseInt(quantity));
		Assert.assertEquals(atp_boc_a, 0);
		Assert.assertEquals(ims_inv_a, Integer.parseInt(quantity));
		Assert.assertEquals(ims_boc_a, 0);

	}

	@Test(groups = { "Regression" }, dataProvider = "random_warehouse", dataProviderClass = IMSServiceDP.class)
	public void random_warehouse(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");

		Map<String, Object> rset1 = DBUtilities
				.exSelectQueryForSingleRecord(
						"select * from wh_inventory where sku_id =12216 and seller_id=1  and store_id=1 and warehouse_id =7117 ",
						"myntra_ims");

		if (rset1 == null) {
			System.out.println("no data");
		}
	}

	@Test(groups = { "Regression" }, dataProvider = "random_storeid", dataProviderClass = IMSServiceDP.class)
	public void random_storeid(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");

		Map<String, Object> rset1 = DBUtilities
				.exSelectQueryForSingleRecord(
						"select * from wh_inventory where sku_id =12216 and seller_id=1  and store_id=1212121 and warehouse_id =36 ",
						"myntra_ims");

		if (rset1 == null) {
			System.out.println("no data");
		}
	}

	@Test(groups = { "Regression" }, dataProvider = "random_supplyType", dataProviderClass = IMSServiceDP.class)
	public void random_supplyType(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

		int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		int atp_inv_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_a = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_a = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		Assert.assertEquals(atp_inv_a, atp_inv_b);
		Assert.assertEquals(atp_boc_a, atp_boc_b);
		Assert.assertEquals(ims_inv_a, ims_inv_b);
		Assert.assertEquals(ims_boc_a, ims_boc_b);

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "random_whInventoryOperation", dataProviderClass = IMSServiceDP.class)
	public void random_whInventoryOperation(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

		List<Integer> atpInventoryDetailsBeforeInventoryUpdateCall = getatp_inv_details(
				sku_id, warehouse, seller, store);

		int initialInventoryInAtp = atpInventoryDetailsBeforeInventoryUpdateCall
				.get(0);
		int initialBlockedInventory = atpInventoryDetailsBeforeInventoryUpdateCall
				.get(1);
		List<Integer> imsInventoryDetailsBeforeInventoryUpdateCall = getims_inv_details(
				sku_id, warehouse, seller, store);
		int initialInventoryInIms = imsInventoryDetailsBeforeInventoryUpdateCall
				.get(0);
		int initialBlockedInventoryInIms = imsInventoryDetailsBeforeInventoryUpdateCall
				.get(1);

		RequestGenerator imsSyncInv = imsServiceHelper
				.invokeIMSSyncinventoryAPI(warehouse, store, seller,
						supplytype, sku_id, sku_code, whInventoryOperation,
						quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		List<Integer> atpInventoryDetailsPostInventoryUpdateCall = getatp_inv_details(
				sku_id, warehouse, seller, store);
		int inventoryCountInAtpPostInventoryUpdate = atpInventoryDetailsPostInventoryUpdateCall
				.get(0);
		int blockedInventoryInAtpPostInventoryUpdate = atpInventoryDetailsPostInventoryUpdateCall
				.get(1);
		List<Integer> imsInventoryDetailsPostInventoryUpdateCall = getatp_inv_details(
				sku_id, warehouse, seller, store);

		int inventoryInImsPostUpdate = imsInventoryDetailsPostInventoryUpdateCall
				.get(0);
		int blockedInventoryInImsPostUpdate = imsInventoryDetailsPostInventoryUpdateCall
				.get(1);
		Assert.assertEquals(inventoryCountInAtpPostInventoryUpdate,
				initialInventoryInAtp);
		Assert.assertEquals(blockedInventoryInAtpPostInventoryUpdate,
				initialBlockedInventory);
		Assert.assertEquals(inventoryInImsPostUpdate, initialInventoryInIms);
		Assert.assertEquals(blockedInventoryInImsPostUpdate,
				initialBlockedInventoryInIms);

	}

	// search APIs

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "search_inventory_withinvalidwarehouseid", dataProviderClass = IMSServiceDP.class)
	public void search_inventory_withinvalidwarehouseid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		WhInventoryCountResponse testresponse = imsServiceHelper.searchInv(
				fetchsize, sortOrder, warehouse_id, sku_code);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_inventory_withinvaliskuid", dataProviderClass = IMSServiceDP.class)
	public void search_inventory_withinvaliskuid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		WhInventoryCountResponse testresponse = imsServiceHelper.searchInv(
				fetchsize, sortOrder, warehouse_id, sku_code);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_inventory_with_valiskuid", dataProviderClass = IMSServiceDP.class)
	public void search_inventory_with_valiskuid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		WhInventoryCountResponse testresponse = imsServiceHelper.searchInv(
				fetchsize, sortOrder, warehouse_id, sku_code);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_inventory_with_valid_warehouse_id", dataProviderClass = IMSServiceDP.class)
	public void search_inventory_with_valid_warehouse_id(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		WhInventoryCountResponse testresponse = imsServiceHelper.searchInv(
				fetchsize, sortOrder, warehouse_id, sku_code);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_core_inventory_withinvalidwarehouseid", dataProviderClass = IMSServiceDP.class)
	public void search_core_inventory_withinvalidwarehouseid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,String quality,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		CoreInventoryCountResponse testresponse = imsServiceHelper
				.searchCoreInv(fetchsize, sortOrder, warehouse_id, "6258", quality);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_core_inventory_withinvaliskuid", dataProviderClass = IMSServiceDP.class)
	public void search_core_inventory_withinvaliskuid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,String quality,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		CoreInventoryCountResponse testresponse = imsServiceHelper
				.searchCoreInvSkuCode(fetchsize, sortOrder, warehouse_id, sku_code, quality);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_core_inventory_with_valiskuid", dataProviderClass = IMSServiceDP.class)
	public void search_core_inventory_with_valiskuid(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,String quality,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		CoreInventoryCountResponse testresponse = imsServiceHelper
				.searchCoreInvSkuCode(fetchsize, sortOrder, warehouse_id, sku_code, quality);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "search_core_inventory_with_valid_warehouse_id", dataProviderClass = IMSServiceDP.class)
	public void search_core_inventory_with_valid_warehouse_id(String fetchsize,
			String sortOrder, String warehouse_id, String sku_code,String quality,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {
		CoreInventoryCountResponse testresponse = imsServiceHelper
				.searchCoreInvSkuCode(fetchsize, sortOrder, warehouse_id, sku_code, quality);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());

	}

	// bulk sync inv

	// 1.bulk_sysncinv_multiple_sku_seller_wh_increment
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_seller_wh_increment", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_seller_wh_increment(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException,
			InterruptedException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		Thread.sleep(10000);
		int imsinvsku1 = getims_inv_details("1251880", "46", "18", "1").get(0);
		int imsboc1 = getims_inv_details("1251880", "46", "18", "1").get(1);
		int imsinvsku2 = getims_inv_details("1251857", "46", "18", "1").get(0);
		int imsboc2 = getims_inv_details("1251857", "46", "18", "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 11,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 11, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 0, imsboc2);

		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_seller_wh_decrement", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_seller_wh_decrement(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("26482", "55", "20", "1").get(0);
		int imsboc1 = getims_inv_details("26482", "55", "20", "1").get(1);
		int imsinvsku2 = getims_inv_details("19427", "55", "20", "1").get(0);
		int imsboc2 = getims_inv_details("19427", "55", "20", "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 2,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 2, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 0, imsboc2);

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_seller_wh_decrement_less_than_boc", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_seller_wh_decrement_less_than_boc(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("1251860", "55", "20", "1").get(0);
		int imsboc1 = getims_inv_details("1251860", "55", "20", "1").get(1);
		int imsinvsku2 = getims_inv_details("1251876", "55", "20", "1").get(0);
		int imsboc2 = getims_inv_details("1251876", "55", "20", "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 2,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 5, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 2, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 5, imsboc2);
		/*
		 * AssertJUnit.assertEquals("Available inventory is", "100000",
		 * testresponse.getData().get(0).getAvailableInventory().toString());
		 * System.out.println("invcout is:" +
		 * testresponse.getData().get(0).getAvailableInventory());
		 */

	}

	@Test(groups = { "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_seller_wh_increment_greater_than_boc", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_seller_wh_increment_greater_than_boc(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException,
			InterruptedException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		Thread.sleep(10000);
		int imsinvsku1 = getims_inv_details("1251877", "55", "20", "1").get(0);
		int imsboc1 = getims_inv_details("1251877", "55", "20", "1").get(1);
		int imsinvsku2 = getims_inv_details("1251878", "55", "20", "1").get(0);
		int imsboc2 = getims_inv_details("1251878", "55", "20", "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 7,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 5, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 7, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 5, imsboc2);

	}

	@Test(groups = { "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_myntra_wh_increment", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_myntra_wh_increment(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("19481", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc1 = getims_inv_details("19481", "36", "" + vectorSellerID, "1")
				.get(1);
		int imsinvsku2 = getims_inv_details("38910", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc2 = getims_inv_details("38910", "36", "" + vectorSellerID, "1")
				.get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 12,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 12, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 0, imsboc2);

	}

	@Test(groups = { "Regression" }, dataProvider = "bulk_sysncinv_multiple_sku_myntra_wh_decrement", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_myntra_wh_decrement(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("3839", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc1 = getims_inv_details("3839", "36", "" + vectorSellerID, "1")
				.get(1);
		int imsinvsku2 = getims_inv_details("3840", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc2 = getims_inv_details("3840", "36", "" + vectorSellerID, "1")
				.get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 2,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 2, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 0, imsboc2);

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression" }, dataProvider = "bulk_sysncinv_multiple_sku_myntra_wh_decrement_less_than_boc", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_myntra_wh_decrement_less_than_boc(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("3841", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc1 = getims_inv_details("3841", "36", "" + vectorSellerID, "1")
				.get(1);
		int imsinvsku2 = getims_inv_details("3842", "36", "" + vectorSellerID,
				"1").get(0);
		int imsboc2 = getims_inv_details("3842", "36", "" + vectorSellerID, "1")
				.get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 2,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 5, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 2, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 5, imsboc2);

	}

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression" }, dataProvider = "bulk_sysncinv_multiple_sku_myntra_wh_increment_greater_than_boc", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_sku_myntra_wh_increment_greater_than_boc(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		int imsinvsku1 = getims_inv_details("3843", "36", "21", "1").get(0);
		int imsboc1 = getims_inv_details("3843", "36", "21", "1").get(1);
		int imsinvsku2 = getims_inv_details("3844", "36", "21", "1").get(0);
		int imsboc2 = getims_inv_details("3844", "36", "21", "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 10,
				imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 5, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 10, imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 5, imsboc2);

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "bulk_sysncinv_multiple_new_sku_seller_wh_increment", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_new_sku_seller_wh_increment(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException,
			InterruptedException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		Thread.sleep(10000);
		int imsinvsku1 = getims_inv_details("11640", "45",
				"" + vectorSellerID, "1").get(0);
		int imsboc1 = getims_inv_details("11640", "45",
				"" + vectorSellerID, "1").get(1);
		int imsinvsku2 = getims_inv_details("23321", "45",
				"" + vectorSellerID, "1").get(0);
		int imsboc2 = getims_inv_details("23321", "45",
				"" + vectorSellerID, "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ",
				2, imsinvsku1);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0, imsboc1);

		AssertJUnit.assertEquals("ims wh_inventory sku2: ", 2,
				imsinvsku2);

		AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ", 0, imsboc2);

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "bulk_sysncinv_multiple_new_sku_seller_wh_decrement", dataProviderClass = IMSServiceDP.class)
	public void bulk_sysncinv_multiple_new_sku_seller_wh_decrement(
			String[] bulkupload, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException,
			InterruptedException {
		WhSyncInventoryResponse testresponse = imsServiceHelper
				.bulkSellerInventoryUpload(bulkupload);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
		Thread.sleep(10000);
		int imsinvsku1Update = getims_inv_details("11439", "45",
				"" + vectorSellerID, "1").get(0);
		int imsboc1Update = getims_inv_details("11439", "45",
				"" + vectorSellerID, "1").get(1);
		int imsinvsku2Update = getims_inv_details("4405265", "45",
				"" + vectorSellerID, "1").get(0);
		int imsboc2Update = getims_inv_details("4405265", "45",
				"" + vectorSellerID, "1").get(1);
		AssertJUnit.assertEquals("ims wh_inventory inventory sku1: ", 2,
				imsinvsku1Update);
		AssertJUnit.assertEquals("ims wh_inventory boc sku1: ", 0,
				imsboc1Update);

		 AssertJUnit.assertEquals("ims wh_inventory sku2: ",2,imsinvsku2Update);
		
		 AssertJUnit.assertEquals(" ims wh_inventory boc sku2: ",0,imsboc2Update);

	}

	/*
	 * @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
	 * "MiniRegression", "ExhaustiveRegression" }, dataProvider =
	 * "bulk_sysncinv_multiple_new_sku_seller_wh_decrement_less_than_boc",
	 * dataProviderClass = IMSServiceDP.class) public void
	 * bulk_sysncinv_multiple_new_sku_seller_wh_decrement_less_than_boc(String[]
	 * bulkupload, String statusCode, String statusMessage,String totalcount)
	 * throws IOException, JAXBException, InterruptedException {
	 * WhSyncInventoryResponse testresponse =
	 * imsServiceHelper.bulkSellerInventoryUpload(bulkupload);
	 * System.out.println("response for the service is:"+testresponse.toString()
	 * ); System.out.println("response is:" +
	 * testresponse.getStatus().getStatusCode());
	 * System.out.println("response is:" +
	 * testresponse.getStatus().getStatusMessage());
	 * AssertJUnit.assertEquals("Status Code is", Integer.parseInt(statusCode),
	 * testresponse .getStatus().getStatusCode());
	 * AssertJUnit.assertEquals("Status message is", statusMessage,
	 * testresponse.getStatus().getStatusMessage());
	 * AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
	 * testresponse.getStatus().getTotalCount()); Thread.sleep(10000);
	 * 
	 * int imsinvsku1=getims_inv_details("11787","45","2","1").get(0); int
	 * imsboc1=getims_inv_details("11787","45","2","1").get(1); int
	 * imsinvsku2=getims_inv_details("11814","45","2","1").get(0); int
	 * imsboc2=getims_inv_details("11814","45","2","1").get(1);
	 * AssertJUnit.assertEquals
	 * ("ims wh_inventory inventory sku1: ",2,imsinvsku1);
	 * AssertJUnit.assertEquals("ims wh_inventory boc sku1: ",0,imsboc1);
	 * 
	 * AssertJUnit.assertEquals("ims wh_inventory sku2: ",2,imsinvsku2);
	 * 
	 * AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ",0,imsboc2);
	 * 
	 * 
	 * }
	 * 
	 * @Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
	 * "MiniRegression", "ExhaustiveRegression" }, dataProvider =
	 * "bulk_sysncinv_multiple_new_sku_seller_wh_increment_more_than_boc",
	 * dataProviderClass = IMSServiceDP.class) public void
	 * bulk_sysncinv_multiple_new_sku_seller_wh_increment_more_than_boc(String[]
	 * bulkupload, String statusCode, String statusMessage,String totalcount)
	 * throws IOException, JAXBException, InterruptedException {
	 * 
	 * WhSyncInventoryResponse testresponse =
	 * imsServiceHelper.bulkSellerInventoryUpload(bulkupload);
	 * 
	 * System.out.println("response for the service is:"+testresponse.toString()
	 * ); System.out.println("response is:" +
	 * testresponse.getStatus().getStatusCode());
	 * System.out.println("response is:" +
	 * testresponse.getStatus().getStatusMessage());
	 * AssertJUnit.assertEquals("Status Code is", Integer.parseInt(statusCode),
	 * testresponse .getStatus().getStatusCode());
	 * AssertJUnit.assertEquals("Status message is", statusMessage,
	 * testresponse.getStatus().getStatusMessage());
	 * AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
	 * testresponse.getStatus().getTotalCount()); Thread.sleep(10000); int
	 * imsinvsku1=getims_inv_details("14261","45","2","1").get(0); int
	 * imsboc1=getims_inv_details("14261","45","2","1").get(1); int
	 * imsinvsku2=getims_inv_details("14262","45","2","1").get(0); int
	 * imsboc2=getims_inv_details("14262","45","2","1").get(1);
	 * AssertJUnit.assertEquals
	 * ("ims wh_inventory inventory sku1: ",2,imsinvsku1);
	 * AssertJUnit.assertEquals("ims wh_inventory boc sku1: ",0,imsboc1);
	 * 
	 * AssertJUnit.assertEquals("ims wh_inventory sku2: ",2,imsinvsku2);
	 * 
	 * AssertJUnit.assertEquals(" ims wh_inventory boc sku2:: ",0,imsboc2);
	 * 
	 * 
	 * }
	 */
	@Test(groups = { "Regression" }, dataProvider = "getSLAvalidcombSupplytypeONHAND", dataProviderClass = IMSServiceDP.class)
	public void getSLAvalidcombSupplytypeONHAND(String[] bulkupload,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {

		EmptyResponse testresponse = imsServiceHelper.getSLAinMin(bulkupload);

		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "getSLAvalidcombSupplytypeJIT", dataProviderClass = IMSServiceDP.class)
	public void getSLAvalidcombSupplytypeJIT(String[] bulkupload,
			String statusCode, String statusMessage, String totalcount)
			throws IOException, JAXBException {

		EmptyResponse testresponse = imsServiceHelper.getSLAinMin(bulkupload);

		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "validSellerWarehouse", dataProviderClass = IMSServiceDP.class)
	public void validSellerWarehouse(String warehouse, String store_id,
			String seller_id, String type, String allocation_type,
			String skucode, String skuid, String fromquality, String toquality,
			String leadtime, String quantity, String statusCode,
			String statusMessage, String totalcount) throws IOException,
			JAXBException {

		EmptyResponse testresponse = imsServiceHelper.isvalidSellerWarehouse(
				warehouse, store_id, seller_id, type, allocation_type, skucode,
				skuid, fromquality, toquality, leadtime, quantity);

		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	// core warehouses TC
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "core_warehouse_validskuandNonMyntraWarehouse", dataProviderClass = IMSServiceDP.class)
	public void core_warehouse_validskuandNonMyntraWarehouse(String quality,
			String warehouseId, long[] sku_id, String statusCode,
			String statusMessage, String totalcount) throws IOException,
			JAXBException {

		CoreInventoryResponse testresponse = imsServiceHelper.core_warehouses(
				quality, warehouseId, sku_id);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "core_warehouse_validskuandMyntraWarehouse", dataProviderClass = IMSServiceDP.class)
	public void core_warehouse_validskuandMyntraWarehouse(String quality,
			String warehouseId, long[] sku_id, String statusCode,
			String statusMessage, String totalcount) throws IOException,
			JAXBException {

		CoreInventoryResponse testresponse = imsServiceHelper.core_warehouses(
				quality, warehouseId, sku_id);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "core_warehouse_invalidWarehouse", dataProviderClass = IMSServiceDP.class)
	public void core_warehouse_invalidWarehouse(String quality,
			String warehouseId, long[] sku_id, String statusCode,
			String statusMessage, String totalcount) throws IOException,
			JAXBException {

		CoreInventoryResponse testresponse = imsServiceHelper.core_warehouses(
				quality, warehouseId, sku_id);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "core_warehouse_validskuandMyntraWarehouseQ2", dataProviderClass = IMSServiceDP.class)
	public void core_warehouse_validskuandMyntraWarehouseQ2(String quality,
			String warehouseId, long[] sku_id, String statusCode,
			String statusMessage, String totalcount) throws IOException,
			JAXBException {

		CoreInventoryResponse testresponse = imsServiceHelper.core_warehouses(
				quality, warehouseId, sku_id);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@Test(groups = { "Regression" }, dataProvider = "core_warehouse_invalidsku", dataProviderClass = IMSServiceDP.class)
	public void core_warehouse_invalidsku(String quality, String warehouseId,
			long[] sku_id, String statusCode, String statusMessage,
			String totalcount) throws IOException, JAXBException {

		CoreInventoryResponse testresponse = imsServiceHelper.core_warehouses(
				quality, warehouseId, sku_id);
		System.out.println("response for the service is:"
				+ testresponse.toString());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusCode());
		System.out.println("response is:"
				+ testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Total count is", Long.parseLong(totalcount),
				testresponse.getStatus().getTotalCount());
	}

	@AfterClass(alwaysRun=true)
	public void setinvIMSAfterTests() throws SQLException {
		DBUtilities
				.exUpdateQuery(
						"update wh_inventory set inventory_count=0,blocked_order_count=0 where sku_id in(585868,1251856,980522,7789,3817,6952,1251880)",
						"IMS");
		DBUtilities
				.exUpdateQuery(
						"delete from wh_inventory where sku_id in (11640,23321,11439,4405265,11787,11814,14261,14262) ",
						"IMS");
		DBUtilities
				.exUpdateQuery(
						"delete from inventory where sku_id in (11640,23321,11439,4405265,11787,11814,14261,14262) ",
						"ATP");
		DBUtilities
				.exUpdateQuery(
						"update wh_inventory set inventory_count=0,blocked_order_count=0 where sku_id in(1251856,1251857,26482,19427,1251860,1251876,1251877,1251878,19481,38910,3839,3840,3841,3842,3843,3844)",
						"IMS");

		String query5 = "update wh_inventory set inventory_count =0,blocked_order_count=0 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860,6258) and store_id in (2,3) and warehouse_id in (36,28)";
		String query6 = "update inventory set inventory_count =0,blocked_order_count=0 where sku_id in (66373,90603,66407,24318,1225025,3864,3869,3870,3871,3857,3858,3859,3860,6258)";
		DBUtilities.exUpdateQuery(query5, "myntra_ims");
		DBUtilities.exUpdateQuery(query6, "myntra_atp");
	}

}
