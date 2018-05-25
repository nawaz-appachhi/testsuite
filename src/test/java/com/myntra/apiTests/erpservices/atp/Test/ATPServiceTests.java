/**
 * 
 */
package com.myntra.apiTests.erpservices.atp.Test;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.atp.dp.ATPServiceDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.atp.client.response.BlockInventoryResponse;
import com.myntra.atp.client.response.InventoryResponse;
import com.myntra.atp.client.response.OrderInventoryResponse;
import com.myntra.atp.client.response.PortalInventoryResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author santwana.samantray
 *
 */
public class ATPServiceTests extends BaseTest {
	Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ATPServiceTests.class);
    private static Long vectorSellerID;
	RabbitTemplate rabbitmqadmin = new RabbitTemplate();

    @BeforeClass(groups = { "Smoke", "Sanity", "ProdSanity", "Regression",
			"MiniRegression", "ExhaustiveRegression" },alwaysRun = true)
   
	public static void beforeclass() throws SQLException {
    	log.info("Entered beforeclass : ");
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		String query1 = "update inventory set blocked_order_count=0 ,inventory_count=1000 where sku_id in (522816,579497,586238,522814,445383,1251856,1251880,818521,818522,895234,895235)";
		DBUtilities.exUpdateQuery(query1, "atp");
		String query2 = "update inventory set inventory_count=0 ,blocked_order_count=0 where sku_id in (3858,3857)";
	
		  DBUtilities.exUpdateQuery(query2, "atp"); 
		  String query3="update inventory set inventory_count=0 ,blocked_order_count=0 where sku_id in (791089,445386) and supply_type='JUST_IN_TIME'"
		  ; DBUtilities.exUpdateQuery(query3, "atp"); 
		  String query4="update inventory set inventory_count=0 ,blocked_order_count=0 where sku_id in (275556,275570) and supply_type='ON_HAND'"
		  ; DBUtilities.exUpdateQuery(query4, "atp");
		  String query5="update inventory set enabled=0 where sku_id in (748033,764270)"
				  ; DBUtilities.exUpdateQuery(query5, "atp");
		String query6="update inventory set inventory_count=1000 ,blocked_order_count=100 where sku_id in (1152954,603116,1044944) ";
		   DBUtilities.exUpdateQuery(query6, "atp");
		   log.info("Completed beforeclass : ");
	}


    @Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventoryMultiplesku", dataProviderClass = ATPServiceDP.class)
	public void blockinventoryMultiplesku(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		String boc1 = getATPBocCount("522816", ""+vectorSellerID);
		String boc2 = getATPBocCount("445383", ""+vectorSellerID);
		Assert.assertEquals("5", boc1, "Expected inv for sku_id 522816 is: ");
		Assert.assertEquals("5", boc2, "Expected inv for sku_id 445383 is: ");

	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventoryNegqty", dataProviderClass = ATPServiceDP.class)
	public void blockinventoryNegqty(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		System.out.println(SystemConfigProvider.getTopology().getQueueTemplate("atpToStyleInventoryOOSNotification").receive());
	//	Message message= rabbitmqadmin.receive("atpToStyleInventoryOOSNotificationTest");
		//System.out.println(message.toString());
		

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySinglesku", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySinglesku(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		String boc1 = getATPBocCount("522814", ""+vectorSellerID);
		Assert.assertEquals("5", boc1, "Expected inv for sku_id 522814 is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySeller2", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySeller2(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		String boc1 = getATPBocCount("895234", "18");
		String boc2 = getATPBocCount("895235", "18");
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		int count1 = Integer.parseInt(boc1);
		count1=count1+5;
		boc1=Integer.toString(count1);
		log.info("boc1 is :"+ boc1);
		int count2 = Integer.parseInt(boc2);
		count2=count2+5;
		boc2=Integer.toString(count2);
		log.info("boc2 is :"+ boc2);
		String newboc1 = getATPBocCount("895234", "18");
		String newboc2 = getATPBocCount("895235", "18");
		Assert.assertEquals(boc1,newboc1, "Expected inv for sku_id 895234 is: ");
		Assert.assertEquals(boc2,newboc2, "Expected inv for sku_id 895235 is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySuppplyTypeON_HAND", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySuppplyTypeON_HAND(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException,JsonMappingException, IOException {
		String boc1 = getATPBocCountWithSupplyType("579497", ""+vectorSellerID,"ON_HAND");
		String boc2 = getATPBocCountWithSupplyType("586238", ""+vectorSellerID,"ON_HAND");
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		int count1 = Integer.parseInt(boc1);
		count1=count1+5;
		boc1=Integer.toString(count1);
		log.info("boc1 is :"+ boc1);
		int count2 = Integer.parseInt(boc2);
		count2=count2+5;
		boc2 = Integer.toString(count2);
		log.info("boc2 is :"+ boc2);
		String newboc1 = getATPBocCountWithSupplyType("579497", ""+vectorSellerID,"ON_HAND");
		log.info("newboc1 is :"+ newboc1);
		String newboc2 = getATPBocCountWithSupplyType("586238", ""+vectorSellerID,"ON_HAND");
		log.info("newboc2 is :"+ newboc2);
		Assert.assertEquals(boc1,newboc1, "Expected inv for sku_id 579497 is: ");
		Assert.assertEquals(boc2,newboc2, "Expected inv for sku_id 586238 is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySuppplyTypeJIT", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySuppplyTypeJIT(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		String boc1 = getATPBocCount("818521", ""+vectorSellerID);
		String boc2 = getATPBocCount("818522", ""+vectorSellerID);
		Assert.assertEquals("5", boc1, "Expected inv for sku_id 818521 is: ");
		Assert.assertEquals("5", boc2, "Expected inv for sku_id 818522 is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventoryLessThanAvailableInv", dataProviderClass = ATPServiceDP.class)
	public void blockinventoryLessThanAvailableInv(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventoryQtynull", dataProviderClass = ATPServiceDP.class)
	public void blockinventoryQtynull(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.debug("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.debug("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySellernull", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySellernull(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventorySKu_idnull", dataProviderClass = ATPServiceDP.class)
	public void blockinventorySKu_idnull(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "blockinventoryStore_idnull", dataProviderClass = ATPServiceDP.class)
	public void blockinventoryStore_idnull(String[] blockInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		BlockInventoryResponse blockinvresponse = ATPServiceHelper.blockOrder(
				blockInventries, store_id);
		long statusCode = blockinvresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = blockinvresponse.getStatus().getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	// orderinv

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryStore_idnull", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryStore_idnull(String[] orderInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventorySellerid_idnull", dataProviderClass = ATPServiceDP.class)
	public void orderInventorySellerid_idnull(String[] orderInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventorySkuid_idnull", dataProviderClass = ATPServiceDP.class)
	public void orderInventorySkuid_idnull(String[] orderInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventorySellerid1", dataProviderClass = ATPServiceDP.class)
	public void orderInventorySellerid1(String[] orderInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		String supplytype = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();
		Map hm1 = new HashMap();

		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		long bfc1 = Long.parseLong(hm1.get("blocked_future_count").toString());

		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());

		Assert.assertEquals(availabelinvfromresponse1, availabelinvfromdb1
				-  (boc1 + bfc1), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryMultipleorderInventriesEntries", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryMultipleorderInventriesEntries(
			String[] orderInventries, String store_id, String RespCode,
			long status_code, String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		Map hm1, hm2 = new HashMap();
		String supplytype1 = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();
		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype1);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		long bfc1 = Long.parseLong(hm1.get("blocked_future_count").toString());
		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());
		long sku_id2 = orderInventriesresponse.getData().get(1).getSkuId();
		long seller_id_2 = orderInventriesresponse.getData().get(1)
				.getSellerId();
		String supplytype2 = orderInventriesresponse.getData().get(1)
				.getSupplyType().toString();
		hm2 = getATPInvEntry(sku_id2, seller_id_2, supplytype2);
		long availableinvfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInventory();
		String availabelinwhfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInWarehouses();
		long leadtimeFromresponse2 = orderInventriesresponse.getData().get(1)
				.getLeadTime();
		long availabelinvfromdb2 = Long.parseLong(hm2.get("inventory_count")
				.toString());
		long boc2 = Long.parseLong(hm2.get("blocked_order_count").toString());
		long bfc2 = Long.parseLong(hm2.get("blocked_future_count").toString());

		String availabelinwhfromdb2 = String.valueOf(hm2
				.get("available_in_warehouses"));
		long leadtimeFromdb2 = Long.parseLong(hm2.get("lead_time").toString());
		Assert.assertEquals(availabelinvfromresponse1,
				(availabelinvfromdb1 - ( boc1+bfc1)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(availableinvfromresponse2,
				(availabelinvfromdb2 - ( boc2+bfc2)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse2, availabelinwhfromdb2,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse2, leadtimeFromdb2,
				"leadtime for sku seller in atp");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryMultipleorderInventriesEntriesSupplytypeONHand", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryMultipleorderInventriesEntriesSupplytypeONHand(
			String[] orderInventries, String store_id, String RespCode,
			long status_code, String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException, InterruptedException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		String supplytype1 = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();

		Map hm1, hm2 = new HashMap();
		Thread.sleep(9000);

		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype1);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		long bfc1 = Long.parseLong(hm1.get("blocked_future_count").toString());
		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());
		long sku_id2 = orderInventriesresponse.getData().get(1).getSkuId();
		long seller_id_2 = orderInventriesresponse.getData().get(1)
				.getSellerId();
		String supplytype2 = orderInventriesresponse.getData().get(1)
				.getSupplyType().toString();

		hm2 = getATPInvEntry(sku_id2, seller_id_2, supplytype2);
		long availableinvfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInventory();
		String availabelinwhfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInWarehouses();
		long leadtimeFromresponse2 = orderInventriesresponse.getData().get(1)
				.getLeadTime();
		long availabelinvfromdb2 = Long.parseLong(hm2.get("inventory_count")
				.toString());
		long boc2 = Long.parseLong(hm2.get("blocked_order_count").toString());
		long bfc2 = Long.parseLong(hm2.get("blocked_future_count").toString());

		String availabelinwhfromdb2 = String.valueOf(hm2
				.get("available_in_warehouses"));
		long leadtimeFromdb2 = Long.parseLong(hm2.get("lead_time").toString());
		Assert.assertEquals(availabelinvfromresponse1,
				(availabelinvfromdb1 - (boc1+bfc1)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(availableinvfromresponse2,
				(availabelinvfromdb2 - (boc2+bfc2)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse2, availabelinwhfromdb2,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse2, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryMultipleorderInventriesEntriesSupplytypeJIT", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryMultipleorderInventriesEntriesSupplytypeJIT(
			String[] orderInventries, String store_id, String RespCode,
			long status_code, String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:" + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		String supplytype1 = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();

		Map hm1, hm2 = new HashMap();

		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype1);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());
		long sku_id2 = orderInventriesresponse.getData().get(1).getSkuId();
		long seller_id_2 = orderInventriesresponse.getData().get(1)
				.getSellerId();
		String supplytype2 = orderInventriesresponse.getData().get(1)
				.getSupplyType().toString();

		hm2 = getATPInvEntry(sku_id2, seller_id_2, supplytype2);
		long availableinvfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInventory();
		String availabelinwhfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInWarehouses();
		long leadtimeFromresponse2 = orderInventriesresponse.getData().get(1)
				.getLeadTime();
		long availabelinvfromdb2 = Long.parseLong(hm2.get("inventory_count")
				.toString());
		long boc2 = Long.parseLong(hm2.get("blocked_order_count").toString());

		String availabelinwhfromdb2 = String.valueOf(hm2
				.get("available_in_warehouses"));
		long leadtimeFromdb2 = Long.parseLong(hm2.get("lead_time").toString());
		Assert.assertEquals(availabelinvfromresponse1,
				(availabelinvfromdb1 - boc1), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(availableinvfromresponse2,
				(availabelinvfromdb2 - boc2), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse2, availabelinwhfromdb2,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse2, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryMultipleorderInventriesEntriesSupplytypeONHandAndJIT", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryMultipleorderInventriesEntriesSupplytypeONHandAndJIT(
			String[] orderInventries, String store_id, String RespCode,
			long status_code, String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:  " + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		String supplytype1 = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();

		Map hm1, hm2 = new HashMap();

		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype1);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		long bfc1 = Long.parseLong(hm1.get("blocked_future_count").toString());
		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());
		long sku_id2 = orderInventriesresponse.getData().get(1).getSkuId();
		long seller_id_2 = orderInventriesresponse.getData().get(1)
				.getSellerId();
		String supplytype2 = orderInventriesresponse.getData().get(1)
				.getSupplyType().toString();

		hm2 = getATPInvEntry(sku_id2, seller_id_2, supplytype2);
		long availableinvfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInventory();
		String availabelinwhfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInWarehouses();
		long leadtimeFromresponse2 = orderInventriesresponse.getData().get(1)
				.getLeadTime();
		long availabelinvfromdb2 = Long.parseLong(hm2.get("inventory_count")
				.toString());
		long boc2 = Long.parseLong(hm2.get("blocked_order_count").toString());
		long bfc2 = Long.parseLong(hm2.get("blocked_future_count").toString());

		String availabelinwhfromdb2 = String.valueOf(hm2
				.get("available_in_warehouses"));
		long leadtimeFromdb2 = Long.parseLong(hm2.get("lead_time").toString());
		Assert.assertEquals(availabelinvfromresponse1,
				(availabelinvfromdb1 - (boc1+bfc1)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(availableinvfromresponse2,
				(availabelinvfromdb2 - (boc2+bfc2)), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse2, availabelinwhfromdb2,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(leadtimeFromresponse2, leadtimeFromdb2,
				"leadtime for sku seller in atp");
	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "orderInventoryWithboc", dataProviderClass = ATPServiceDP.class)
	public void orderInventoryWithboc(String[] orderInventries,
			String store_id, String RespCode, long status_code,
			String status_message, String status_type)
			throws NumberFormatException, JAXBException, SQLException, JsonParseException, JsonMappingException, IOException {
		OrderInventoryResponse orderInventriesresponse = ATPServiceHelper
				.OrderInventory(orderInventries, store_id);
		long statusCode = orderInventriesresponse.getStatus().getStatusCode();
		log.info("Status code:  " + status_code);
		String Status_message = orderInventriesresponse.getStatus()
				.getStatusMessage();
		log.info("Status code:" + Status_message);
		Assert.assertEquals(statusCode, status_code, "Status code is: ");
		Assert.assertEquals(Status_message, status_message,
				"Status message is: ");
		long sku_id1 = orderInventriesresponse.getData().get(0).getSkuId();
		long seller_id_1 = orderInventriesresponse.getData().get(0)
				.getSellerId();
		String supplytype1 = orderInventriesresponse.getData().get(0)
				.getSupplyType().toString();

		Map hm1, hm2 = new HashMap();

		hm1 = getATPInvEntry(sku_id1, seller_id_1, supplytype1);

		long availabelinvfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInventory();
		String availabelinwhfromresponse1 = orderInventriesresponse.getData()
				.get(0).getAvailableInWarehouses();
		long leadtimeFromresponse1 = orderInventriesresponse.getData().get(0)
				.getLeadTime();
		long availabelinvfromdb1 = Long.parseLong(hm1.get("inventory_count")
				.toString());
		long boc1 = Long.parseLong(hm1.get("blocked_order_count").toString());
		String availabelinwhfromdb1 = String.valueOf(hm1
				.get("available_in_warehouses"));
		long leadtimeFromdb1 = Long.parseLong(hm1.get("lead_time").toString());
		long sku_id2 = orderInventriesresponse.getData().get(1).getSkuId();
		long seller_id_2 = orderInventriesresponse.getData().get(1)
				.getSellerId();
		String supplytype2 = orderInventriesresponse.getData().get(1)
				.getSupplyType().toString();

		hm2 = getATPInvEntry(sku_id2, seller_id_2, supplytype2);
		long availableinvfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInventory();
		String availabelinwhfromresponse2 = orderInventriesresponse.getData()
				.get(1).getAvailableInWarehouses();
		long leadtimeFromresponse2 = orderInventriesresponse.getData().get(1)
				.getLeadTime();
		long availabelinvfromdb2 = Long.parseLong(hm2.get("inventory_count")
				.toString());
		long boc2 = Long.parseLong(hm2.get("blocked_order_count").toString());

		String availabelinwhfromdb2 = String.valueOf(hm2
				.get("available_in_warehouses"));
		long leadtimeFromdb2 = Long.parseLong(hm2.get("lead_time").toString());
		Assert.assertEquals(availabelinvfromresponse1,
				(availabelinvfromdb1 - boc1), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse1, availabelinwhfromdb1,
				"availabel in wh for sku seller in atp for 1st record");

		Assert.assertEquals(leadtimeFromresponse1, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(availableinvfromresponse2,
				(availabelinvfromdb2 - boc2), "inv for sku seller in atp");
		Assert.assertEquals(availabelinwhfromresponse2, availabelinwhfromdb2,
				"availabel in wh for sku seller in atp for 2nd record");

		Assert.assertEquals(leadtimeFromresponse2, leadtimeFromdb2,
				"leadtime for sku seller in atp");
	}



	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryMultipleskuid", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryMultipleskuid(String[] sku_entries,
			String store_id, String response, long responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, SQLException {
		
	ATPServiceHelper.setUpAtpInventoryToTestAtpInfoFetchAPI(sku_entries);
		
		
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");
		Map h1, h2 = new HashMap();

		String sku1_availablewhresponse = portalInventoryResponse.getData()
				.get(0).getAvailableInWarehouses();
		long sku1_available_inv_response = portalInventoryResponse.getData()
				.get(0).getAvailableInventory();
		long sku1_leadtime_response = portalInventoryResponse.getData().get(0)
				.getLeadTime();
		long sku_id_1 = portalInventoryResponse.getData().get(0).getSkuId();
		long seller_id_1 = portalInventoryResponse.getData().get(0)
				.getSellerId();
		String supplytype_1 = portalInventoryResponse.getData().get(0)
				.getSupplyType().toString();
		h1 = getATPInvEntry( sku_id_1,seller_id_1, supplytype_1);
		String sku1_availablewh_db = h1.get("available_in_warehouses")
				.toString();
		long sku1_available_inv_db = Long.parseLong(h1.get("inventory_count")
				.toString());
		long leadtimeFromdb1 = Long.parseLong(h1.get("lead_time").toString());
		long boc1 = Long.parseLong(h1.get("blocked_order_count").toString());
		String sku2_availablewhresponse = portalInventoryResponse.getData()
				.get(1).getAvailableInWarehouses();
		long sku2_available_inv_response = portalInventoryResponse.getData()
				.get(1).getAvailableInventory();
		long sku2_leadtime_response = portalInventoryResponse.getData().get(1)
				.getLeadTime();
		long sku_id_2 = portalInventoryResponse.getData().get(1).getSkuId();
		long seller_id_2 = portalInventoryResponse.getData().get(1)
				.getSellerId();
		String supplytype_2 = portalInventoryResponse.getData().get(1)
				.getSupplyType().toString();
		h2 = getATPInvEntry(sku_id_2, seller_id_2,supplytype_2);
		String sku2_availablewh_db = h2.get("available_in_warehouses")
				.toString();
		long sku2_available_inv_db = Long.parseLong(h2.get("inventory_count")
				.toString());
		long leadtimeFromdb2 = Long.parseLong(h2.get("lead_time").toString());
		long boc2 = Long.parseLong(h2.get("blocked_order_count").toString());

		Assert.assertEquals(sku1_available_inv_response,
				(sku1_available_inv_db - boc1), "inv for sku seller in atp");
		Assert.assertEquals(sku1_availablewhresponse, sku1_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku1_leadtime_response, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(sku2_available_inv_response,
				(sku2_available_inv_db - boc2), "inv for sku seller in atp");
		Assert.assertEquals(sku2_availablewhresponse, sku2_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku2_leadtime_response, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryStoreidnull", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryStoreidnull(String[] sku_entries,
			String store_id, String response, long responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException {
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryBothJITANDONHANDWithINvForBothPresent", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryBothJITANDONHANDWithINvForBothPresent(
			String[] sku_entries, String store_id, String response,
			long responsecode, String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, SQLException {
		ATPServiceHelper.setUpAtpInventoryToTestAtpInfoFetchAPI(sku_entries);
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");
		Map h1, h2 = new HashMap();

		String sku1_availablewhresponse = portalInventoryResponse.getData()
				.get(0).getAvailableInWarehouses();
		long sku1_available_inv_response = portalInventoryResponse.getData()
				.get(0).getAvailableInventory();
		long sku1_leadtime_response = portalInventoryResponse.getData().get(0)
				.getLeadTime();
		long sku_id_1 = portalInventoryResponse.getData().get(0).getSkuId();
		long seller_id_1 = portalInventoryResponse.getData().get(0)
				.getSellerId();
		String supplytype_1 = portalInventoryResponse.getData().get(0)
				.getSupplyType().toString();
		h1 = getATPInvEntry(sku_id_1,seller_id_1 , supplytype_1);
		String sku1_availablewh_db = h1.get("available_in_warehouses")
				.toString();
		long sku1_available_inv_db = Long.parseLong(h1.get("inventory_count")
				.toString());
		long leadtimeFromdb1 = Long.parseLong(h1.get("lead_time").toString());
		long boc1 = Long.parseLong(h1.get("blocked_order_count").toString());
		String sku2_availablewhresponse = portalInventoryResponse.getData()
				.get(1).getAvailableInWarehouses();
		long sku2_available_inv_response = portalInventoryResponse.getData()
				.get(1).getAvailableInventory();
		long sku2_leadtime_response = portalInventoryResponse.getData().get(1)
				.getLeadTime();
		long sku_id_2 = portalInventoryResponse.getData().get(1).getSkuId();
		long seller_id_2 = portalInventoryResponse.getData().get(1)
				.getSellerId();
		String supplytype_2 = portalInventoryResponse.getData().get(1)
				.getSupplyType().toString();
		h2 = getATPInvEntry(sku_id_2, seller_id_2,supplytype_2);
		String sku2_availablewh_db = h2.get("available_in_warehouses")
				.toString();
		long sku2_available_inv_db = Long.parseLong(h2.get("inventory_count")
				.toString());
		long leadtimeFromdb2 = Long.parseLong(h2.get("lead_time").toString());
		long boc2 = Long.parseLong(h2.get("blocked_order_count").toString());

		Assert.assertEquals(sku1_available_inv_response,
				(sku1_available_inv_db - boc1), "inv for sku seller in atp");
		Assert.assertEquals(sku1_availablewhresponse, sku1_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku1_leadtime_response, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(sku2_available_inv_response,
				(sku2_available_inv_db - boc2), "inv for sku seller in atp");
		Assert.assertEquals(sku2_availablewhresponse, sku2_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku2_leadtime_response, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryBothJITANDOnhandWithInvPresentForJIT", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryBothJITANDOnhandWithInvPresentForJIT(
			String[] sku_entries, String store_id, String response,
			long responsecode, String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, SQLException {
		ATPServiceHelper.setUpAtpInventoryToTestAtpInfoFetchAPI(sku_entries);
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");
		Map h1, h2 = new HashMap();

		String sku1_availablewhresponse = portalInventoryResponse.getData()
				.get(0).getAvailableInWarehouses();
		long sku1_available_inv_response = portalInventoryResponse.getData()
				.get(0).getAvailableInventory();
		long sku1_leadtime_response = portalInventoryResponse.getData().get(0)
				.getLeadTime();
		long sku_id_1 = portalInventoryResponse.getData().get(0).getSkuId();
		long seller_id_1 = portalInventoryResponse.getData().get(0)
				.getSellerId();
		String supplytype_1 = portalInventoryResponse.getData().get(0)
				.getSupplyType().toString();
		h1 = getATPInvEntry(sku_id_1, seller_id_1,supplytype_1);
		String sku1_availablewh_db = h1.get("available_in_warehouses")
				.toString();
		long sku1_available_inv_db = Long.parseLong(h1.get("inventory_count")
				.toString());
		long leadtimeFromdb1 = Long.parseLong(h1.get("lead_time").toString());
		long boc1 = Long.parseLong(h1.get("blocked_order_count").toString());
		String sku2_availablewhresponse = portalInventoryResponse.getData()
				.get(1).getAvailableInWarehouses();
		long sku2_available_inv_response = portalInventoryResponse.getData()
				.get(1).getAvailableInventory();
		long sku2_leadtime_response = portalInventoryResponse.getData().get(1)
				.getLeadTime();
		long sku_id_2 = portalInventoryResponse.getData().get(1).getSkuId();
		long seller_id_2 = portalInventoryResponse.getData().get(1)
				.getSellerId();
		String supplytype_2 = portalInventoryResponse.getData().get(1)
				.getSupplyType().toString();
		h2 = getATPInvEntry(sku_id_2, seller_id_2,supplytype_2);
		String sku2_availablewh_db = h2.get("available_in_warehouses")
				.toString();
		long sku2_available_inv_db = Long.parseLong(h2.get("inventory_count")
				.toString());
		long leadtimeFromdb2 = Long.parseLong(h2.get("lead_time").toString());
		long boc2 = Long.parseLong(h2.get("blocked_order_count").toString());

		Assert.assertEquals(sku1_available_inv_response,
				(sku1_available_inv_db - boc1), "inv for sku seller in atp");
		Assert.assertEquals(sku1_availablewhresponse, sku1_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku1_leadtime_response, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(sku2_available_inv_response,
				(sku2_available_inv_db - boc2), "inv for sku seller in atp");
		Assert.assertEquals(sku2_availablewhresponse, sku2_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku2_leadtime_response, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryBothJITANDOnhandWithInvPresentForOnhand", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryBothJITANDOnhandWithInvPresentForOnhand(
			String[] sku_entries, String store_id, String response,
			long responsecode, String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, SQLException {
		ATPServiceHelper.setUpAtpInventoryToTestAtpInfoFetchAPI(sku_entries);
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");
		Map h1, h2 = new HashMap();

		String sku1_availablewhresponse = portalInventoryResponse.getData()
				.get(0).getAvailableInWarehouses();
		long sku1_available_inv_response = portalInventoryResponse.getData()
				.get(0).getAvailableInventory();
		long sku1_leadtime_response = portalInventoryResponse.getData().get(0)
				.getLeadTime();
		long sku_id_1 = portalInventoryResponse.getData().get(0).getSkuId();
		long seller_id_1 = portalInventoryResponse.getData().get(0)
				.getSellerId();
		String supplytype_1 = portalInventoryResponse.getData().get(0)
				.getSupplyType().toString();
		h1 = getATPInvEntry(sku_id_1, seller_id_1,supplytype_1);
		String sku1_availablewh_db = h1.get("available_in_warehouses")
				.toString();
		long sku1_available_inv_db = Long.parseLong(h1.get("inventory_count")
				.toString());
		long leadtimeFromdb1 = Long.parseLong(h1.get("lead_time").toString());
		long boc1 = Long.parseLong(h1.get("blocked_order_count").toString());
		
		
		String sku2_availablewhresponse = portalInventoryResponse.getData()
				.get(1).getAvailableInWarehouses();
		long sku2_available_inv_response = portalInventoryResponse.getData()
				.get(1).getAvailableInventory();
		
		
		long sku2_leadtime_response = portalInventoryResponse.getData().get(1)
				.getLeadTime();
		long sku_id_2 = portalInventoryResponse.getData().get(1).getSkuId();
		long seller_id_2 = portalInventoryResponse.getData().get(1)
				.getSellerId();
		String supplytype_2 = portalInventoryResponse.getData().get(1)
				.getSupplyType().toString();
		
		h2 = getATPInvEntry(sku_id_2, seller_id_2,supplytype_2);
		
		
		String sku2_availablewh_db = h2.get("available_in_warehouses")
				.toString();
		long sku2_available_inv_db = Long.parseLong(h2.get("inventory_count")
				.toString());
		long leadtimeFromdb2 = Long.parseLong(h2.get("lead_time").toString());
		long boc2 = Long.parseLong(h2.get("blocked_order_count").toString());

		Assert.assertEquals(sku1_available_inv_response,
				(sku1_available_inv_db - boc1), "inv for sku seller in atp");
		Assert.assertEquals(sku1_availablewhresponse, sku1_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku1_leadtime_response, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(sku2_available_inv_response,
				(sku2_available_inv_db - boc2), "inv for sku seller in atp");
		Assert.assertEquals(sku2_availablewhresponse, sku2_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku2_leadtime_response, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "portalInventoryBothJITANDOnhandWithInvNotPresentForBoth", dataProviderClass = ATPServiceDP.class)
	public void portalInventoryBothJITANDOnhandWithInvNotPresentForBoth(
			String[] sku_entries, String store_id, String response,
			long responsecode, String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, SQLException {
		ATPServiceHelper.setUpAtpInventoryToTestAtpInfoFetchAPI(sku_entries);
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");
		Map h1, h2 = new HashMap();

		String sku1_availablewhresponse = portalInventoryResponse.getData()
				.get(0).getAvailableInWarehouses();
		long sku1_available_inv_response = portalInventoryResponse.getData()
				.get(0).getAvailableInventory();
		long sku1_leadtime_response = portalInventoryResponse.getData().get(0)
				.getLeadTime();
		long sku_id_1 = portalInventoryResponse.getData().get(0).getSkuId();
		long seller_id_1 = portalInventoryResponse.getData().get(0)
				.getSellerId();
		String supplytype_1 = portalInventoryResponse.getData().get(0)
				.getSupplyType().toString();
		h1 = getATPInvEntry(sku_id_1, seller_id_1,supplytype_1);
		String sku1_availablewh_db = h1.get("available_in_warehouses")
				.toString();
		long sku1_available_inv_db = Long.parseLong(h1.get("inventory_count")
				.toString());
		long leadtimeFromdb1 = Long.parseLong(h1.get("lead_time").toString());
		long boc1 = Long.parseLong(h1.get("blocked_order_count").toString());
		String sku2_availablewhresponse = portalInventoryResponse.getData()
				.get(1).getAvailableInWarehouses();
		long sku2_available_inv_response = portalInventoryResponse.getData()
				.get(1).getAvailableInventory();
		long sku2_leadtime_response = portalInventoryResponse.getData().get(1)
				.getLeadTime();
		long sku_id_2 = portalInventoryResponse.getData().get(1).getSkuId();
		long seller_id_2 = portalInventoryResponse.getData().get(1)
				.getSellerId();
		String supplytype_2 = portalInventoryResponse.getData().get(1)
				.getSupplyType().toString();
		h2 = getATPInvEntry(sku_id_2, seller_id_2,supplytype_2);
		String sku2_availablewh_db = h2.get("available_in_warehouses")
				.toString();
		long sku2_available_inv_db = Long.parseLong(h2.get("inventory_count")
				.toString());
		long leadtimeFromdb2 = Long.parseLong(h2.get("lead_time").toString());
		long boc2 = Long.parseLong(h2.get("blocked_order_count").toString());

		Assert.assertEquals(sku1_available_inv_response,
				(sku1_available_inv_db - boc1), "inv for sku seller in atp");
		Assert.assertEquals(sku1_availablewhresponse, sku1_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku1_leadtime_response, leadtimeFromdb1,
				"leadtime for sku seller in atp");
		Assert.assertEquals(sku2_available_inv_response,
				(sku2_available_inv_db - boc2), "inv for sku seller in atp");
		Assert.assertEquals(sku2_availablewhresponse, sku2_availablewh_db,
				"availabel in wh for sku seller in atp");

		Assert.assertEquals(sku2_leadtime_response, leadtimeFromdb2,
				"leadtime for sku seller in atp");

	}

	@Test(groups = { "Smoke", "Regression" }, dataProvider = "disabledSku", dataProviderClass = ATPServiceDP.class)
	public void disabledSku(String[] sku_entries, String store_id,
			String response, long responsecode, String responseMessage,
			String responseType) throws UnsupportedEncodingException,
			JAXBException {
		PortalInventoryResponse portalInventoryResponse = ATPServiceHelper
				.PortalInventory(sku_entries, store_id);
		long statuscode = portalInventoryResponse.getStatus().getStatusCode();
		String statusmessage = portalInventoryResponse.getStatus()
				.getStatusMessage();
		String statusType = portalInventoryResponse.getStatus().getStatusType()
				.toString();
		Assert.assertEquals(statuscode, responsecode, "Status code :");
		Assert.assertEquals(statusmessage, responseMessage, "Status code :");
		Assert.assertEquals(statusType, responseType, "Status code :");

	}

	
	public static String getATPBocCount(String sku_id, String seller_id)
			throws SQLException {

		String query1 = "select * from inventory where sku_id=" + sku_id
				+ " and seller_id=" + seller_id;
		Map map = new HashMap();
		map = DBUtilities.exSelectQueryForSingleRecord(query1, "atp");
		String boc = map.get("blocked_order_count").toString();
		return boc;
	}
	
	public static String getATPBocCountWithSupplyType(String sku_id, String seller_id,String supply_type)
			throws SQLException {

		String query1 = "select * from inventory where sku_id=" + sku_id
				+ " and seller_id=" + seller_id + " and supply_type=" +"'"+supply_type+"'";
		Map map = new HashMap();
		map = DBUtilities.exSelectQueryForSingleRecord(query1, "atp");
		String boc = map.get("blocked_order_count").toString();
		return boc;
	}

	public static Map getATPInvEntry(long sku_id, long seller_id,
			String supplytype) throws SQLException {

		String query1 = "select * from inventory where sku_id=" + sku_id
				+ " and seller_id=" + seller_id+" and supply_type="+"'"+supplytype+"'";
		Map map = new HashMap();
		map = DBUtilities.exSelectQueryForSingleRecord(query1, "atp");
		// String boc = map.get("blocked_order_count").toString();
		return map;
	} 
	
	//syncInvDecrementInv,syncInvIncrementInvAndBOC,syncInvDisableInventory,syncInvEnableInventory,syncInvDisableInventory,syncInvIncrementBOC,syncInvJITInvUpdate
	//String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			//String sellerId, String skuCode, String skuId, String storeId, String supplyType
	
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvDecrementBOC", dataProviderClass = ATPServiceDP.class)
	public void syncInvDecrementBOC(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long boc_before=Long.parseLong(invMapbefore.get("blocked_order_count").toString());
	     boc_before = boc_before-1;
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        long bocAfter=Long.parseLong(invMapafter.get("blocked_order_count").toString());
        Assert.assertEquals(boc_before,bocAfter);

	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncDcrIncrementInvAndBOC", dataProviderClass = ATPServiceDP.class)
	public void syncDcrIncrementInvAndBOC(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException, InterruptedException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long boc_before=Long.parseLong(invMapbefore.get("blocked_order_count").toString());
	     long inventory_before=Long.parseLong(invMapbefore.get("inventory_count").toString());
	     inventory_before=inventory_before-1;
	     boc_before=boc_before-1;
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       Thread.sleep(10000);
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        long bocAfter=Long.parseLong(invMapafter.get("blocked_order_count").toString());
	     long inventoryAfter=Long.parseLong(invMapafter.get("inventory_count").toString());

        Assert.assertEquals(boc_before,bocAfter);
        Assert.assertEquals(inventory_before,inventoryAfter);

	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvDisableInventory", dataProviderClass = ATPServiceDP.class)
	public void syncInvDisableInventory(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		String query1="update inventory set enabled=1 where sku_id ="+skuId+" and seller_id="+sellerId+" and supply_type='"+supplyType+"'";
		DBUtilities.exUpdateQuery(query1, "myntra_atp");
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     String enableBefore=invMapbefore.get("enabled").toString();
	    
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        String enableAfter=invMapafter.get("enabled").toString();        
        Assert.assertEquals(enableBefore,"true");
        Assert.assertEquals(enableAfter,"false");
	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvEnableInventory", dataProviderClass = ATPServiceDP.class)
	public void syncInvEnableInventory(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		String query1="update inventory set enabled=0 where sku_id ="+skuId+" and seller_id="+sellerId+" and supply_type='"+supplyType+"'";
		DBUtilities.exUpdateQuery(query1, "myntra_atp");
		  Map invMapbefore,invMapafter=new HashMap();
		     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
		     String enableBefore=invMapbefore.get("enabled").toString();
		    
			InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
			long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
			String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
			String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
			Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
			Assert.assertEquals(responseMessage, status_message_response);
	        Assert.assertEquals(responseType, status_message_type);
	       
	        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	        String enableAfter=invMapafter.get("enabled").toString();        
	        Assert.assertEquals(enableBefore,"false");
	        Assert.assertEquals(enableAfter,"true");

	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvIncrementBOC", dataProviderClass = ATPServiceDP.class)
	public void syncInvIncrementBOC(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException, InterruptedException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long boc_before=Long.parseLong(invMapbefore.get("blocked_order_count").toString());
	     boc_before=boc_before+1;
	     System.out.println("invetory count after adding up:"+boc_before);
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       Thread.sleep(10000);
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        long bocAfter=Long.parseLong(invMapafter.get("blocked_order_count").toString());
        Assert.assertEquals(boc_before,bocAfter);

	}
	/*@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvJITInvUpdate", dataProviderClass = ATPServiceDP.class)
	public void syncInvJITInvUpdate(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long boc_before=Long.parseLong(invMapbefore.get("blocked_order_count").toString());
	     boc_before=boc_before+1;
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        String bocAfter=invMapafter.get("blocked_order_count").toString();
        Assert.assertEquals(boc_before,bocAfter);

	}*/
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvDecrementInv", dataProviderClass = ATPServiceDP.class)
	public void syncInvDecrementInv(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long inv_before=Long.parseLong(invMapbefore.get("inventory_count").toString());
	     inv_before=inv_before-1;
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        long invAfter=Long.parseLong(invMapafter.get("inventory_count").toString());
        Assert.assertEquals(inv_before,invAfter);

	}
	@Test(groups = { "Smoke", "Regression" }, dataProvider = "syncInvIncrementInv", dataProviderClass = ATPServiceDP.class)
	public void syncInvIncrementInv(String availableInWhs, String inventoryOperation, String leadTime, String quantity,
			String sellerId, String skuCode, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType)
			throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException {
		
	     Map invMapbefore,invMapafter=new HashMap();
	     invMapbefore= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
	     long inv_before=Long.parseLong(invMapbefore.get("inventory_count").toString());
	     inv_before=inv_before+1;
		InventoryResponse syncInventoryresponse = ATPServiceHelper.syncInventory(availableInWhs, inventoryOperation, leadTime, quantity, sellerId, skuCode, skuId, storeId, supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
       
        invMapafter= getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
        long invAfter=Long.parseLong(invMapafter.get("inventory_count").toString());
        Assert.assertEquals(inv_before,invAfter);

	}
	@Test(groups = { "Smoke", "Regression" } ,dataProvider = "getBoc", dataProviderClass = ATPServiceDP.class)
	public void getBocTest(String sellerId, String skuId, String storeId, String supplyType,  String responsecode,
			String responseMessage, String responseType) throws UnsupportedEncodingException, JAXBException, NumberFormatException, SQLException
	{
		InventoryResponse syncInventoryresponse = ATPServiceHelper.getBoc(sellerId,skuId,storeId,supplyType);
		long status_code_response=syncInventoryresponse.getStatus().getStatusCode();
		String  status_message_response=syncInventoryresponse.getStatus().getStatusMessage();
		String status_message_type=syncInventoryresponse.getStatus().getStatusType().toString();
		long boc=syncInventoryresponse.getData().get(0).getBlockedOrderCount().longValue();
		  Map invMap=new HashMap();
		  invMap=getATPInvEntry(Long.parseLong(skuId),Long.parseLong(sellerId),supplyType);
		  long bocdb= Long.parseLong(invMap.get("blocked_order_count").toString());
		Assert.assertEquals(Long.parseLong(responsecode), status_code_response);
		Assert.assertEquals(responseMessage, status_message_response);
        Assert.assertEquals(responseType, status_message_type);
        Assert.assertEquals(bocdb, boc);
       
	}
	
	@Test(groups = { "Smoke", "Regression" } ,dataProvider = "getBocSellerNull", dataProviderClass = ATPServiceDP.class)
	public void getBocSellerNull(String sellerId, String skuId, String storeId, String supplyType, int response ) throws UnsupportedEncodingException, JAXBException
	{
		Svc syncInventoryresponse = ATPServiceHelper.getBocNegative(sellerId, skuId, storeId, supplyType);
		int responseStatus= syncInventoryresponse.getResponseStatus();
		Assert.assertEquals(responseStatus,response);
		}
	@Test(groups = { "Smoke", "Regression" } ,dataProvider = "getBocStoreIdNull", dataProviderClass = ATPServiceDP.class)
	public void getBocStoreIdNull(String sellerId, String skuId, String storeId, String supplyType, int response ) throws UnsupportedEncodingException, JAXBException
	{
		Svc syncInventoryresponse = ATPServiceHelper.getBocNegative(sellerId, skuId, storeId, supplyType);
		int responseStatus= syncInventoryresponse.getResponseStatus();
		Assert.assertEquals(responseStatus,response);
		}
	@Test(groups = { "Smoke", "Regression" } ,dataProvider = "getBocSkuIdNull", dataProviderClass = ATPServiceDP.class)
	public void getBocSkuIdNull(String sellerId, String skuId, String storeId, String supplyType, int response ) throws UnsupportedEncodingException, JAXBException
	{
		Svc syncInventoryresponse = ATPServiceHelper.getBocNegative(sellerId, skuId, storeId, supplyType);
		int responseStatus= syncInventoryresponse.getResponseStatus();
		Assert.assertEquals(responseStatus,response);
		}
	@Test(groups = { "Smoke", "Regression" } ,dataProvider = "getBocSupplyTypeNull", dataProviderClass = ATPServiceDP.class)
	public void getBocSupplyTypeNull(String sellerId, String skuId, String storeId, String supplyType, int response ) throws UnsupportedEncodingException, JAXBException
	{
		Svc syncInventoryresponse = ATPServiceHelper.getBocNegative(sellerId, skuId, storeId, supplyType);
		int responseStatus= syncInventoryresponse.getResponseStatus();
		Assert.assertEquals(responseStatus,response);
	}
}