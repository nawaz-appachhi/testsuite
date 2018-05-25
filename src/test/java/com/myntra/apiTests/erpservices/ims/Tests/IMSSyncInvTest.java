/**
 * 
 */
package com.myntra.apiTests.erpservices.ims.Tests;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.ims.dp.IMSSyncInvDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author santwana.samantray
 *
 */

public class IMSSyncInvTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(IMSServiceHelper.class);
	static ATPServiceHelper atphelper = new ATPServiceHelper();


    @Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "invincrement", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "checkabsolute", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "invdecrement", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "invsetto0", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "invsetto0WhenbocMorethan0", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "setinvLessthanboc", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "setinvequalsboc", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "setinvgreterthanboc", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "uploadinvForNewSku", dataProviderClass = IMSSyncInvDP.class)
	public void uploadinvForNewSku(String warehouse, String store,
			String seller, String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

	/*	int atp_inv_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int atp_boc_b = getatp_inv_details(sku_id, warehouse, seller, store)
				.get(1);
		int ims_inv_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(0);
		int ims_boc_b = getims_inv_details(sku_id, warehouse, seller, store)
				.get(1);*/

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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
		Assert.assertEquals(atp_inv_a,Integer.parseInt(quantity));
		Assert.assertEquals(atp_boc_a, 0);
		Assert.assertEquals(ims_inv_a,Integer.parseInt(quantity));
		Assert.assertEquals(ims_boc_a, 0);
		try {
			DBUtilities.exUpdateQuery("delete from inventory where sku_id=123", "myntra_atp");
			DBUtilities.exUpdateQuery("delete from wh_inventory where sku_id=123", "myntra_ims");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "random_warehouse", dataProviderClass = IMSSyncInvDP.class)
	public void random_warehouse(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

	

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		try {
			Map<String, Object> rset1 = DBUtilities.exSelectQueryForSingleRecord(
					"select * from wh_inventory where sku_id =12216 and seller_id=1  and store_id=1 and warehouse_id =7117 ", "myntra_ims");
			
			if (rset1.size()==0 ) {
			    System.out.println("no data");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "random_storeid", dataProviderClass = IMSSyncInvDP.class)
	public void random_storeid(String warehouse, String store, String seller,
			String supplytype, String sku_id, String sku_code,
			String whInventoryOperation, String quantity) {

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
		String getOrderResponse = imsSyncInv.respvalidate
				.returnresponseasstring();
		System.out.println("\nPrinting ATP updateboc API response :\n\n"
				+ getOrderResponse + "\n");
		log.info("\nPrinting ATP updateboc  API response :\n\n"
				+ getOrderResponse + "\n");
		try {
			Map<String, Object> rset1 = DBUtilities.exSelectQueryForSingleRecord(
					"select * from wh_inventory where sku_id =12216 and seller_id=1  and store_id=1212121 and warehouse_id =36 ", "myntra_ims");
			
			if (rset1.size() == 0) {
			    System.out.println("no data");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "random_supplyType", dataProviderClass = IMSSyncInvDP.class)
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	@Test(groups = { "Smoke", "ProdSanity", "Regression", "MiniRegression",
			"ExhaustiveRegression" }, dataProvider = "random_whInventoryOperation", dataProviderClass = IMSSyncInvDP.class)
	public void random_whInventoryOperation(String warehouse, String store,
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

		RequestGenerator imsSyncInv = atphelper.invokeIMSSyncinventoryAPI(
				warehouse, store, seller, supplytype, sku_id, sku_code,
				whInventoryOperation, quantity);
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

	public static List<Integer> getims_inv_details(String skuid,
			String warehouse_id, String seller, String store) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		List<Integer> invlist = new ArrayList<Integer>();
		int wh_inventory = 0;
		int get_boc = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Map<String, Object> rset = DBUtilities.exSelectQueryForSingleRecord(
					"select * from wh_inventory where sku_id =" + skuid
							+ " and warehouse_id=" + warehouse_id
							+ " and seller_id=" + seller + " and store_id="
							+ store, "myntra_ims");

			wh_inventory = (int) rset.get("inventory_count");
			get_boc = (int) rset.get("blocked_order_count");
			invlist.add(wh_inventory);
			invlist.add(get_boc);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invlist;
	}

	public static List<Integer> getatp_inv_details(String skuid,
			String warehouse_id, String seller, String store) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		List<Integer> invlist = new ArrayList<Integer>();
		int atp_inventory = 0;
		int boc = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Map<String, Object> rset = DBUtilities.exSelectQueryForSingleRecord(
					"select * from inventory where sku_id =" + skuid
							+ " and seller_id=" + seller + " and store_id="
							+ store, "myntra_atp");

			atp_inventory = (int) rset.get("inventory_count");
			boc = (int) rset.get("blocked_order_count");
			invlist.add(atp_inventory);
			invlist.add(boc);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return invlist;
	}

	public static void setinvIMS(String inv, String boc, String sku_id,
			String warehouse_id) {

		try {
			DBUtilities.exUpdateQuery(
					"update wh_inventory set inventory_count=" + inv
							+ ", blocked_order_count=" + boc + " where sku_id="
							+ sku_id + " and warehouse_id=" + warehouse_id,
					"myntra_ims");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setinvATP(String inv, String boc, String sku_id) {
		try {
            DBUtilities.exUpdateQuery("update inventory set inventory_count="
					+ inv + ", blocked_order_count=" + boc + " where sku_id="
					+ sku_id, "myntra_atp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}