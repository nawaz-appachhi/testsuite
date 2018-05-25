package com.myntra.apiTests.erpservices.ims.Tests;

import com.myntra.apiTests.common.Constants.SellerAndSkus;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.ims.dp.IMSFKInventoryDP;
import com.myntra.ims.client.request.AllocateExtInventoryRequest;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author santwana.samantray
 *
 */
public class IMSFKInvAutomationTest extends BaseTest {
	static Logger log = Logger.getLogger(IMSFKInvAutomationTest.class);
	static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	String sku_id_list_store_2 = "(4027,4028,4029,4031)";
	String sku_id_list_store_3 = "(4034,4035,4036,4038)";
	String sku_id_list_store_4 = "(1018744,1183984,6795026)";

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_increment_inv", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_increment_inv(String[] allocatecomb,
										  String store_id, String warehouse_id, String autooverride,
										  String status, String statusCode, String statusMessage,
										  String StatusType) throws IOException, JAXBException,
			InterruptedException {
	

		switch (store_id) {
			case "2":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateSim(sku_id_list_store_2);
				updateinv(0, 0, "5", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_2,SellerAndSkus.seller19);
				break;
			}
			case "3": {
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(0, 0, "5", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateSim(sku_id_list_store_3);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(0, 0, "5", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateSim(sku_id_list_store_4);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}

		Thread.sleep(5000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "10",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		//Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invliststore_1.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "100",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			//AssertJUnit.assertEquals("The expected autooverride", autooverride,
				//	override_auto_realloc);
		}

		// ,"The expected wh_inventory count"
		// ,"The expected wh_inventory count"
		//Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected inventory count", "100",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}



	@Test(groups = { "Regression" }, dataProvider = "allocateinv_decrement_inv", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_decrement_inv(String[] allocatecomb,
										  String store_id, String warehouse_id, String autooverride,
										  String status, String statusCode, String statusMessage,
										  String StatusType) throws IOException, JAXBException,
			InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "1",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count store_1",
					"109", wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
//			AssertJUnit.assertEquals("The expected autooverride", autooverride,
				//	override_auto_realloc);
		}

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "109",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_lessthan0", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_lessthan0(String[] allocatecomb, String store_id,
									  String warehouse_id, String autooverride, String status,
									  String statusCode, String statusMessage, String StatusType)
			throws IOException, JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}

		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_lessthanboc", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_lessthanboc(String[] allocatecomb, String store_id,
										String warehouse_id, String autooverride, String status,
										String statusCode, String statusMessage, String StatusType)
			throws IOException, JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());
	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_MoreThanAvailableInv", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_MoreThanAvailableInv(String[] allocatecomb,
												 String store_id, String warehouse_id, String autooverride,
												 String status, String statusCode, String statusMessage,
												 String StatusType) throws IOException, JAXBException,
			InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());
	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_MoreThanAvailableInv_withAppPropertyswitchedoff", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_MoreThanAvailableInv_withAppPropertyswitchedoff(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

	}

	

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_breachThresholdForFKwithOverridesettofalse", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_breachThresholdForFKwithOverridesettofalse(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response code is:" + testresponse.getStatus().getStatusCode());
		log.info("response Message is:"
				+ testresponse.getStatus().getStatusMessage());
		log.info("response type is:" + testresponse.getStatus().getStatusType());
		log.info("response remarks is"
				+ testresponse.getData().get(0).getRemarks());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "10",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			// AssertJUnit.assertEquals("The expected autooverride",autooverride,override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "110",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			// AssertJUnit.assertEquals("The expected autooverride",autooverride,override_auto_realloc);
		}

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "110",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());
		AssertJUnit
				.assertEquals(
						"Data Remark is",
						"Cannot allocate inventory as doing so would breach minimum inventory threshold for Ext store which is 0. Current available Inventory in this external store: 10. Current FK BOC : 0",
						testresponse.getData().get(0).getRemarks().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_breachThresholdForFKwithOverridesettotrue", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_breachThresholdForFKwithOverridesettotrue(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "0",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			//AssertJUnit.assertEquals("The expected autooverride", autooverride,
				//	override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "120",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
//			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					//override_auto_realloc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "120",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_fromFKWhenThresholdBreachedInMyntraStore", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_fromFKWhenThresholdBreachedInMyntraStore(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(1, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(1, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(1, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(1, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(1, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(1, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(1, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(1, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "0",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "2",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
//			AssertJUnit.assertEquals("The expected autooverride", autooverride,
				//	override_auto_realloc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "2",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_fromFKWhenThresholdBreachedInMyntraStore", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_fromFKWhenThresholdBreachedInMyntraStoreAndFK(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(2, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(2, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(2, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(2, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(2, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(2, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(1, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(2, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(2, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "0",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "3",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
//			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					//override_auto_realloc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "3",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}
	
	
	
	//Need to check the feasability of modifiying the application property and only then this can be uncommented
	/*@Test(groups = { "Regression" }, dataProvider = "allocateinv_increment_inv_withAppPropertyswitchedoff", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_increment_inv_withAppPropertyswitchedoff(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		
			updateApplicationProperty(false);
			
			
		

		switch (store_id) {
			case "2":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "10",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "100",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			//AssertJUnit.assertEquals("The expected autooverride", autooverride,
					//override_auto_realloc);
		}

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "100",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_decrement_inv_withAppPropertyswitchedoff", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_decrement_inv_withAppPropertyswitchedoff(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {

		switch (store_id) {
			case "2":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(10, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(100, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(100, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());

		Thread.sleep(10000);
		List wh_invlist, wh_invliststore_1, atp_invliststore = new ArrayList();
		wh_invlist = getwh_inventory(allocatecomb, store_id,warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invlist.get(i).toString();
			String wh_boc = wh_invlist.get(i + 1).toString();
			String override_auto_realloc = wh_invlist.get(i + 2).toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count", "1",
					wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			AssertJUnit.assertEquals("The expected autooverride", autooverride,
					override_auto_realloc);
		}
		Thread.sleep(1000);
		wh_invliststore_1 = getwh_inventory(allocatecomb, "1",warehouse_id);
		for (int i = 0; i < wh_invlist.size(); i += 3) {
			String wh_inventory = wh_invliststore_1.get(i).toString();
			String wh_boc = wh_invliststore_1.get(i + 1).toString();
			String override_auto_realloc = wh_invliststore_1.get(i + 2)
					.toString();
			log.info("warehouse inventory is:" + wh_inventory);
			log.info("warehouse boc is:" + wh_boc);
			AssertJUnit.assertEquals("The expected wh_inventory count store_1",
					"109", wh_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", wh_boc);
			//AssertJUnit.assertEquals("The expected autooverride", autooverride,
				//	override_auto_realloc);
		}

		Thread.sleep(1000);
		atp_invliststore = getatp_inventory(allocatecomb);
		for (int i = 0; i < atp_invliststore.size(); i += 2) {
			String atp_inventory = atp_invliststore.get(i).toString();
			String atp_boc = atp_invliststore.get(i + 1).toString();
			log.info("warehouse inventory is:" + atp_inventory);
			log.info("warehouse boc is:" + atp_boc);
			AssertJUnit.assertEquals("The expected atp_inventory count", "109",
					atp_inventory);
			AssertJUnit.assertEquals("The expected boc count", "0", atp_boc);
		}
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_lessthan0_withAppPropertyswitchedoff", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_lessthan0_withAppPropertyswitchedoff(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(0, 0, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
				updateatpinv(110, 0, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());
		AssertJUnit.assertEquals("Status Code is",
				Integer.parseInt(statusCode), testresponse.getStatus()
						.getStatusCode());
		AssertJUnit.assertEquals("Status message is", statusMessage,
				testresponse.getStatus().getStatusMessage());
		AssertJUnit.assertEquals("Status message is", StatusType, testresponse
				.getStatus().getStatusType().toString());

	}

	@Test(groups = { "Regression" }, dataProvider = "allocateinv_lessthanboc_withAppPropertyswitchedoff", dataProviderClass = IMSFKInventoryDP.class)
	public void allocateinv_lessthanboc_withAppPropertyswitchedoff(
			String[] allocatecomb, String store_id, String warehouse_id,
			String autooverride, String status, String statusCode,
			String statusMessage, String StatusType) throws IOException,
			JAXBException, InterruptedException {
		switch (store_id) {
			case "2":{
				updateinv(0, 10, store_id, warehouse_id, sku_id_list_store_2,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_2,SellerAndSkus.seller21);
				break;
			}
			case "3": {
				updateinv(0, 10, store_id, warehouse_id, sku_id_list_store_3,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_3,SellerAndSkus.seller21);
				break;
			}
			case "4":{
				updateinv(0, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
			case "5":{
				updateinv(0, 10, store_id, warehouse_id, sku_id_list_store_4,SellerAndSkus.seller19);
				updateinv(110, 0, "1", warehouse_id, sku_id_list_store_4,SellerAndSkus.seller21);
			}
		}
		Thread.sleep(1000);
		AllocateExtInventoryRequest testresponse = imsServiceHelper
				.allocateExtInventoryRequest(allocatecomb);
		log.info("reponse is:" + testresponse.getStatus().toString());
		log.info("response for the service is:" + testresponse.toString());
		log.info("response is:" + testresponse.getStatus().getStatusCode());
		log.info("response is:" + testresponse.getStatus().getStatusMessage());
		log.info("response is:" + testresponse.getStatus().getStatusType());
	}*/

	public void updateinv(int invcount, int boc, String store_id,
						  String warehouse_id, String sku_ids,String sellerId) throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update wh_inventory set inventory_count="
				+ invcount + " , blocked_order_count=" + boc
				+ " where sku_id in " + sku_ids + " and store_id=" + store_id
				+ " and warehouse_id=" + warehouse_id + " and seller_id="+sellerId;

		DBUtilities.exUpdateQuery(invupdate, "ims");
	}

	public void updateatpinv(int invcount, int boc, String sku_ids,String sellerId)
			throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update inventory set inventory_count=" + invcount
				+ " , blocked_order_count=" + boc + " where sku_id in "
				+ sku_ids + " and seller_id="+sellerId;

		DBUtilities.exUpdateQuery(invupdate, "atp");
	}

	@Deprecated
	public void updateinv(int invcount, int boc, String store_id,
						  String warehouse_id, String sku_ids) throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update wh_inventory set inventory_count="
				+ invcount + " , blocked_order_count=" + boc
				+ " where sku_id in " + sku_ids + " and store_id=" + store_id
				+ " and warehouse_id=" + warehouse_id + " and seller_id=19";

		DBUtilities.exUpdateQuery(invupdate, "ims");
	}
	@Deprecated
	public void updateinvSel21(int invcount, int boc, String store_id,
							   String warehouse_id, String sku_ids) throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update wh_inventory set inventory_count="
				+ invcount + " , blocked_order_count=" + boc
				+ " where sku_id in " + sku_ids + " and store_id=" + store_id
				+ " and warehouse_id=" + warehouse_id + " and seller_id=21";

		DBUtilities.exUpdateQuery(invupdate, "ims");
	}
	@Deprecated
	public void updateatpinv(int invcount, int boc, String sku_ids)
			throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update inventory set inventory_count=" + invcount
				+ " , blocked_order_count=" + boc + " where sku_id in "
				+ sku_ids + " and seller_id=21";

		DBUtilities.exUpdateQuery(invupdate, "atp");
	}

	public void updateApplicationProperty(boolean value) {
		String updateApplicationProperty = "update core_application_property set value= "
				+ value + " where name='ims.ext.inv.ReAllocation'";
		DBUtilities.exUpdateQuery(updateApplicationProperty, "myntra_tools");
	}

	public static List getwh_inventory(String[] allocatecomb, String store_id,String warehouse_id) {
		List<String> wh_inv_details = new ArrayList();
		Map wh_inv_details_map = new HashMap();
		for (String object : allocatecomb) {
			String[] allocateExtInventoryEntryArray = object.split(",");

			String query = "select * from wh_inventory where sku_code='"
					+ allocateExtInventoryEntryArray[1] + "' and store_id="
					+ store_id +" and warehouse_id="+warehouse_id;
			wh_inv_details_map = DBUtilities.exSelectQueryForSingleRecord(
					query, "ims");
			String inventorycount = wh_inv_details_map.get("inventory_count")
					.toString();
			String boc = wh_inv_details_map.get("blocked_order_count")
					.toString();
			String override_auto_realloc = wh_inv_details_map.get(
					"override_auto_realloc").toString();
			wh_inv_details.add(inventorycount);
			wh_inv_details.add(boc);
			wh_inv_details.add(override_auto_realloc);

		}
		return wh_inv_details;
	}

	public static List getwh_inventory(String[] allocatecomb, String store_id,String warehouse_id, String seller_Id) {
		List<String> wh_inv_details = new ArrayList();
		Map wh_inv_details_map = new HashMap();
		for (String object : allocatecomb) {
			String[] allocateExtInventoryEntryArray = object.split(",");

			String query = "select * from wh_inventory where sku_code='"
					+ allocateExtInventoryEntryArray[1] + "' and store_id="
					+ store_id +" and warehouse_id="+warehouse_id+ " and seller_id="+seller_Id;
			wh_inv_details_map = DBUtilities.exSelectQueryForSingleRecord(
					query, "ims");
			String inventorycount = wh_inv_details_map.get("inventory_count")
					.toString();
			String boc = wh_inv_details_map.get("blocked_order_count")
					.toString();
			String override_auto_realloc = wh_inv_details_map.get(
					"override_auto_realloc").toString();
			wh_inv_details.add(inventorycount);
			wh_inv_details.add(boc);
			wh_inv_details.add(override_auto_realloc);

		}
		return wh_inv_details;
	}

	public static List getatp_inventory(String[] allocatecomb) {
		List<String> atp_inv_details = new ArrayList();
		Map atp_inv_details_map = new HashMap();
		for (String object : allocatecomb) {
			String[] allocateExtInventoryEntryArray = object.split(",");

			String query = "select * from inventory where sku_code='"
					+ allocateExtInventoryEntryArray[1]
					+ "' and supply_type='ON_HAND'";
			atp_inv_details_map = DBUtilities.exSelectQueryForSingleRecord(
					query, "atp");
			String inventorycount = atp_inv_details_map.get("inventory_count")
					.toString();
			String boc = atp_inv_details_map.get("blocked_order_count")
					.toString();
			atp_inv_details.add(inventorycount);
			atp_inv_details.add(boc);
		}
		return atp_inv_details;
	}

	private String getSellerIdfromSIM(String[] allocatecomb) {
			String[] allocateExtInventoryEntryArray = allocatecomb[0].split(",");
		String query = "select seller_id from seller_item_master where sku_code='"
				+ allocateExtInventoryEntryArray[1];
		 return DBUtilities.exSelectQueryForSingleRecord(
				query, "seller1").get("seller_id").toString();


	}

	public void updateSim(String sku_ids) throws InterruptedException {
		Thread.sleep(2000);
		String invupdate = "update seller_item_master1 set seller_id=21"
				+ " where sku_id in " + sku_ids ;
		DBUtilities.exUpdateQuery(invupdate, "seller");
	}
}
