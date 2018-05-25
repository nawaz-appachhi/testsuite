package com.myntra.apiTests.erpservices.atp;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig.SellerName;
import com.myntra.atp.client.entry.BlockInventoryEntry;
import com.myntra.atp.client.entry.SyncInventoryEntry;
import com.myntra.atp.client.request.BlockInventoryRequest;
import com.myntra.atp.client.request.OrderInventoryRequest;
import com.myntra.atp.client.request.PortalInventoryRequest;
import com.myntra.atp.client.response.BlockInventoryResponse;
import com.myntra.atp.client.response.InventoryResponse;
import com.myntra.atp.client.response.OrderInventoryResponse;
import com.myntra.atp.client.response.PortalInventoryResponse;
import com.myntra.atp.enums.InventoryOperation;
import com.myntra.client.inventory.SellerSupplyTypeSkuEntry;
import com.myntra.client.inventory.enums.SupplyType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/**
 * @author santwana.samantray
 *
 */
public class ATPServiceHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(ATPServiceHelper.class);
	private static String defaultJustInTimewareHouseId = "20";
	private static String defaultOnHandWareHouseId = "28";

	private static HashMap<String, String> getATPHeaderjson() {
		HashMap<String, String> getATPHeaders = new HashMap<String, String>();
		getATPHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		getATPHeaders.put("Content-Type", "Application/json");
		return getATPHeaders;
	}
	private static HashMap<String, String> getATPHeader() {
		HashMap<String, String> getATPHeaders = new HashMap<String, String>();
		getATPHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		getATPHeaders.put("Content-Type", "Application/xml");
		return getATPHeaders;
	}


	/**
	 * The method fetches the wareHouse list from ims per sku and populated atp
	 * with the wareHouse array it forms It updates ATP per sku only if ATP per
	 * sku does not have a wareHouse info The method throws assert error if IMS
	 * does not have any wareHouse info for the SKU when it tried to populate
	 * ATP from IMS
	 * 
	 * @param skus
	 *            , takes the sku array
	 */
	public static void setUpAtpInventoryToTestAtpInfoFetchAPI(String[] skus) {

		Assert.assertTrue(skus.length > 0, "There is no sku passed..");

		for (String sku : skus) {

			if (!isWareHouseInfoPresentInAtpForOnHandInventory(sku)) {
				updateOnHandAtpInventoryWithWareHouseIdForTheSku(sku);
				updateJustInTimeAtpInventoryWithWareHouseIdForTheSku(sku);
			}

		}

	}

	/**
	 * @param
	 * @param sku
	 */
	private static void updateJustInTimeAtpInventoryWithWareHouseIdForTheSku(String sku) {
		DBUtilities.exUpdateQuery(
				"update `inventory` set `available_in_warehouses` = " + "'" + defaultJustInTimewareHouseId + "'"
						+ " where `sku_id` = " + sku + " and `supply_type` = 'JUST_IN_TIME' ",
				"myntra_atp");

	}

	static Map<String, Object> getWareHouseListFromAtpPerSkuForOnHandInventory(String sku) {
		return DBUtilities
				.exSelectQueryForSingleRecord("select `available_in_warehouses` from `inventory` where `sku_id` =" + sku
						+ "  and `supply_type` = 'ON_HAND'", "myntra_atp");

	}

	static boolean isWareHouseInfoPresentInAtpForOnHandInventory(String sku) {

		boolean isWareHosueInfoAlreadyPresent = false;
		Map<String, Object> wareHouseInfoMapPerSku = getWareHouseListFromAtpPerSkuForOnHandInventory(sku);
		if (null != (String) wareHouseInfoMapPerSku.get("available_in_warehouses")) {
			isWareHosueInfoAlreadyPresent = true;
		}
		return isWareHosueInfoAlreadyPresent;
	}

	/**
	 * @param
	 */
	private static void updateOnHandAtpInventoryWithWareHouseIdForTheSku(String sku) {

		DBUtilities.exUpdateQuery("update `inventory` set `available_in_warehouses` = " + "'" + defaultOnHandWareHouseId
				+ "'" + " where `sku_id` = " + sku + " and `supply_type` = 'ON_HAND' ", "myntra_atp");

	}

	public RequestGenerator invokeATPbocinventoryAPI(String skuid, String boc) {
		MyntraService atpBoc = Myntra.getService(ServiceType.ERP_ATP, APINAME.updateatpinventory, init.Configurations,
				new String[] { skuid, boc }, PayloadType.JSON, PayloadType.JSON);

		System.out.println("\nPrinting ATP boc  API URL : " + atpBoc.URL);
		log.info("\nPrinting ATP boc API URL : " + atpBoc.URL);

		System.out.println("\nPrinting ATP boc API Payload : " + atpBoc.Payload);
		log.info("\nPrinting ATP boc API Payload : " + atpBoc.Payload);

		HashMap<String, String> atpBocHeaders = new HashMap<String, String>();
		atpBocHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		atpBocHeaders.put("Content-Type", "Application/xml");

		return new RequestGenerator(atpBoc, atpBocHeaders);
	}

	public RequestGenerator invokeIMSSyncinventoryAPI(String warehouse, String store, String seller, String supplytype,
			String sku_id, String sku_code, String whInventoryOperation, String quantity) {
		MyntraService imsSyncInv = Myntra.getService(ServiceType.ERP_IMS, APINAME.updateimsinventory,
				init.Configurations,
				new String[] { warehouse, store, seller, supplytype, sku_id, sku_code, whInventoryOperation, quantity },
				PayloadType.XML, PayloadType.XML);

		System.out.println("\nPrinting IMS Sync INV API URL : " + imsSyncInv.URL);
		log.info("\nPrinting IMS Sync INV API URL : " + imsSyncInv.URL);

		System.out.println("\nPrinting IMS Sync INV API Payload : " + imsSyncInv.Payload);
		log.info("\nPrinting IMS Sync INV API Payload : " + imsSyncInv.Payload);

		HashMap<String, String> imsSyncInvHeaders = new HashMap<String, String>();
		imsSyncInvHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		imsSyncInvHeaders.put("Content-Type", "Application/xml");

		return new RequestGenerator(imsSyncInv, imsSyncInvHeaders);
	}

	public static BlockInventoryResponse blockOrder(String[] blockInventries, String store_id)
			throws JAXBException, NumberFormatException, JsonParseException, JsonMappingException, IOException {
		List BlockinvreqList = new ArrayList();
		BlockInventoryRequest blockInventoryRequest = new BlockInventoryRequest();
		for (String object : blockInventries) {
			String[] blockInventrieslist = object.split(",");
			System.out.println("1st elemnt is" + blockInventrieslist[0] + "," + blockInventrieslist[2] + "");
			BlockInventoryEntry blockInventoryEntry = new BlockInventoryEntry();
			if (blockInventrieslist[0].equalsIgnoreCase("null")) {
				blockInventoryEntry.setQuantity(null);
			} else {
				blockInventoryEntry.setQuantity(Long.parseLong(blockInventrieslist[0]));
			}
			if (blockInventrieslist[1].equalsIgnoreCase("null")) {
				blockInventoryEntry.setSellerId(null);
			} else {
				blockInventoryEntry.setSellerId(Long.parseLong(blockInventrieslist[1]));
			}
			if (blockInventrieslist[2].equalsIgnoreCase("null")) {
				blockInventoryEntry.setSkuId(null);
			} else {
				blockInventoryEntry.setSkuId(Long.parseLong(blockInventrieslist[2]));
			}
			if (blockInventrieslist[3].equalsIgnoreCase("ON_HAND"))
				blockInventoryEntry.setSupplyType(SupplyType.ON_HAND);
			else
				blockInventoryEntry.setSupplyType(SupplyType.JUST_IN_TIME);
			BlockinvreqList.add(blockInventoryEntry);
		}
		blockInventoryRequest.setData(BlockinvreqList);
		if (store_id == "null") {
			blockInventoryRequest.setStoreId(null);
		} else {
			blockInventoryRequest.setStoreId(Long.parseLong(store_id));
		}
		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(blockInventoryRequest);
		System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.BLOCK_INV, null, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.PUT, payload, getATPHeaderjson());
		System.out.println("Response is:" + service.getResponseBody());
		BlockInventoryResponse blockInventoryResponse = (BlockInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new BlockInventoryResponse());
		System.out.println("response is:" + blockInventoryResponse);
		return blockInventoryResponse;
		/*
		 * <?xml version="1.0" encoding="UTF-8"
		 * standalone="yes"?><blockInventoryRequest><data><blockInventory><
		 * quantity>3</quantity><sellerId>1</sellerId><skuId>8005</skuId><
		 * supplyType>ON_HAND</supplyType></blockInventory>
		 * </data><storeId>1</storeId></blockInventoryRequest> <?xml
		 * version="1.0" encoding="UTF-8"
		 * standalone="yes"?><blockInventoryResponse><status><statusCode>1003</
		 * statusCode><statusMessage>Inventory updated
		 * successfully</statusMessage><statusType>SUCCESS</statusType>
		 * <totalCount>0</totalCount></status></blockInventoryResponse>
		 */

	}

	public static OrderInventoryResponse OrderInventory(String[] orderInventries, String store_id)
			throws JAXBException, JsonParseException, JsonMappingException, IOException {
		List orderinvreqList = new ArrayList();
		OrderInventoryRequest orderInventoryRequest = new OrderInventoryRequest();
		for (String object : orderInventries) {
			String[] orderInventrieslist = object.split(",");
			SellerSupplyTypeSkuEntry sellerSupplyTypeSkuEntry = new SellerSupplyTypeSkuEntry();
			if (orderInventrieslist[0].contains("null"))
				sellerSupplyTypeSkuEntry.setSellerId(null);
			else
				sellerSupplyTypeSkuEntry.setSellerId(Long.parseLong(orderInventrieslist[0]));
			if (orderInventrieslist[1].contains("null"))
				sellerSupplyTypeSkuEntry.setSkuId(null);
			else
				sellerSupplyTypeSkuEntry.setSkuId(Long.parseLong(orderInventrieslist[1]));
			if (orderInventrieslist[2].equalsIgnoreCase("ON_HAND"))
				sellerSupplyTypeSkuEntry.setSupplyType(SupplyType.ON_HAND);
			else
				sellerSupplyTypeSkuEntry.setSupplyType(SupplyType.JUST_IN_TIME);
			orderinvreqList.add(sellerSupplyTypeSkuEntry);

		}
		orderInventoryRequest.setData(orderinvreqList);
		if (store_id == null)
			orderInventoryRequest.setStoreId(null);
		else
			orderInventoryRequest.setStoreId(Long.parseLong(store_id));
		//String payload = APIUtilities.convertXMLObjectToString(orderInventoryRequest);
		String payload = APIUtilities.convertJavaObjectToJsonUsingGson(orderInventoryRequest);

		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.ORDER_INV, null, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.PUT, payload, getATPHeaderjson());
		OrderInventoryResponse orderInventoryResponse = (OrderInventoryResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new OrderInventoryResponse());
		System.out.println("response is:" + orderInventoryResponse);
		return orderInventoryResponse;
	}
	/*
	 * payload<?xml version="1.0" encoding="UTF-8"
	 * standalone="yes"?><orderInventoryRequest><data><sellerSupplyTypeSku><
	 * sellerId>1</sellerId><skuId>1152963</skuId>
	 * <supplyType>JUST_IN_TIME</supplyType></sellerSupplyTypeSku></data><
	 * storeId>1</storeId></orderInventoryRequest> response: <?xml version="1.0"
	 * encoding="UTF-8"
	 * standalone="yes"?><orderInventoryResponse><status><statusCode>1002</
	 * statusCode><statusMessage>Inventory Retreived
	 * successfully</statusMessage><statusType>SUCCESS</statusType><totalCount>1
	 * </totalCount></status><data><orderInventory><availableInWarehouses>20</
	 * availableInWarehouses><availableInventory>9999237</availableInventory><
	 * lastSyncedOn>2014-07-26T09:37:13+05:30</lastSyncedOn><leadTime>8</
	 * leadTime><sellerId>1</sellerId><skuId>1152963</skuId><supplyType>
	 * JUST_IN_TIME</supplyType>
	 * <vendorId>1292</vendorId></orderInventory></data><storeId>1</storeId></
	 * orderInventoryResponse>
	 */

	public static PortalInventoryResponse PortalInventory(String[] sku_id, String store_id)
			throws JAXBException, UnsupportedEncodingException {
		List skuID_List = new ArrayList();
		PortalInventoryRequest portalInventoryRequest = new PortalInventoryRequest();
		for (int i = 0; i < sku_id.length; i++) {
			skuID_List.add(sku_id[i]);
		}

		portalInventoryRequest.setSkus(skuID_List);
		if (store_id.equalsIgnoreCase("null"))

			portalInventoryRequest.setStoreId(null);
		else
			portalInventoryRequest.setStoreId(Long.parseLong(store_id));

		String payload = APIUtilities.convertXMLObjectToString(portalInventoryRequest);

		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.PORTAL_INV, null, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.PUT, payload, getATPHeader());
		PortalInventoryResponse portalInventoryResponse = (PortalInventoryResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new PortalInventoryResponse());
		System.out.println("response is:" + portalInventoryResponse);
		return portalInventoryResponse;
	}

	/**
	 * Get ATP Inventory and Block Order Count
	 *
	 * @param skuIDs
	 * @throws SQLException
	 */
	public HashMap<String, int[]> getAtpInvAndBlockOrderCount(String[] skuIDs) {
		HashMap<String, int[]> skuMapping = new HashMap();
		try {

			for (String sku : skuIDs) {
				List bociocs = DBUtilities.exSelectQuery(
						"select inventory_count, blocked_order_count from myntra_atp.inventory where sku_id=" + sku,
						"myntra_atp");
				HashMap<String, Object> hm = (HashMap<String, Object>) bociocs.get(0);
				int[] skuquantity = { (Integer) hm.get("inventory_count"), (Integer) hm.get("blocked_order_count") };
				skuMapping.put(sku, skuquantity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return skuMapping;
	}

	/**
	 * Get ATP Inventory Details
	 *
	 * @param skuIDs
	 * @throws SQLException
	 */
	public HashMap<String, HashMap<String, Object>> getAtpInvDetails(String[] skuIDs) throws SQLException {
		HashMap<String, HashMap<String, Object>> skuMapping = new HashMap();
		for (String sku : skuIDs) {
			HashMap<java.lang.String, java.lang.Object> bociocs = (HashMap<String, Object>) DBUtilities
					.exSelectQueryForSingleRecord("select * from myntra_atp.inventory where sku_id=" + sku,
							"myntra_atp");
			skuMapping.put(sku, bociocs);
		}
		return skuMapping;
	}

	/**
	 * Update ATP Inventory
	 * 
	 * @param inventorydetails
	 *            //skuid:warehouse_id:inventory_count:block_order_count
	 */
	public void updateInventoryDetails(String[] inventorydetails) {

		List resultSet = null;

		for (String inventory : inventorydetails) {
			String[] singleInventory = inventory.split(":");

			String selectQuery = "select * from inventory where sku_id ='" + singleInventory[0] + "'";
			resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_atp");
			// row = (HashMap<String, Object>) resultSet.get(0);
			System.out.println(resultSet);
			if (resultSet == null) {
				String query = "INSERT INTO `inventory` ( `store_id`, `seller_id`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `supply_type`, `lead_time`, `enabled`, `last_synced_on`, `available_in_warehouses`, `vendor_id`, `created_by`, `created_on`, `last_modified_on`, `version`,`blocked_future_count`)"
						+ "VALUES (1, 19, " + singleInventory[0] + ", NULL, " + singleInventory[2] + ", "
						+ singleInventory[3] + ", 'ON_HAND', 0, 1, '2015-02-09 08:23:10', '" + singleInventory[1]
						+ "', NULL, 'System', '2014-02-04 14:24:43', '2016-10-04 20:13:55', 103,0);";
				DBUtilities.exUpdateQuery(query, "myntra_atp");

			} else {
				DBUtilities
						.exUpdateQuery(
								"update inventory set inventory_count=" + singleInventory[2] + ", blocked_order_count="
										+ singleInventory[3] + ", available_in_warehouses='" + singleInventory[1]
										+ "',blocked_future_count=0 where sku_id=" + singleInventory[0] + ";",
								"myntra_atp");
			}

		}
	}

	/**
	 * Update Supply Type Inventory
	 * 
	 * @param
	 */
	public void updateSupplyType(String[] SkuIds, String supplyType) {
		for (String sku : SkuIds) {
			DBUtilities.exUpdateQuery(
					"update inventory set supply_type='" + supplyType + "' where sku_id='" + sku + "';", "myntra_atp");
		}
	}

	/**
	 * Update ATP Inventory
	 * 
	 * @param inventorydetails
	 *            //skuid:warehouse_id:inventory_count:block_order_count
	 */
	public void updateInventoryDetails(String[] inventorydetails, String supplyType) {

		List resultSet = null;
		Long sellerId = SellerConfig.getSellerID(SellerName.HANDH);

		for (String inventory : inventorydetails) {
			String[] singleInventory = inventory.split(":");

			String selectQuery = "select * from inventory where sku_id ='" + singleInventory[0] + "' and seller_id='"
					+ sellerId + "'";
			resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_atp");
			// row = (HashMap<String, Object>) resultSet.get(0);
			System.out.println(resultSet);
			if (resultSet == null) {
				String query = "INSERT INTO `inventory` ( `store_id`, `seller_id`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `supply_type`, `lead_time`, `enabled`, `last_synced_on`, `available_in_warehouses`, `vendor_id`, `created_by`, `created_on`, `last_modified_on`, `version`,`blocked_future_count`)"
						+ "VALUES (1," + sellerId + ", " + singleInventory[0] + ", NULL, " + singleInventory[2] + ", "
						+ singleInventory[3] + ", '" + supplyType + "', 0, 1, '2015-02-09 08:23:10', '"
						+ singleInventory[1]
						+ "', NULL, 'System', '2014-02-04 14:24:43', '2016-10-04 20:13:55', 103,0);";
				DBUtilities.exUpdateQuery(query, "myntra_atp");

			} else {
				DBUtilities.exUpdateQuery(
						"update inventory set supply_type='" + supplyType + "', inventory_count=" + singleInventory[2]
								+ ", blocked_order_count=" + singleInventory[3] + ", available_in_warehouses='"
								+ singleInventory[1] + "',blocked_future_count=0 where sku_id ='" + singleInventory[0]
								+ "' and seller_id='" + sellerId + "';",
						"myntra_atp");
			}

		}
	}

	/**
	 * Update ATP Inventory for specific seller
	 * 
	 * @param inventorydetails
	 *            //skuid:warehouse_id:inventory_count:block_order_count:
	 *            sellerId:IsEnabled, Supply Type: On_HAND or JUST_IN_TIME
	 */
	public void updateInventoryDetailsForSeller(String[] inventorydetails, String supplyType) {

		List resultSet = null;
		String vendorId = null;
		String selectQuery = null;
		String query=null;
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		
		for (String inventory : inventorydetails) {
			String[] singleInventory = inventory.split(":");
			
			if(supplyType.equalsIgnoreCase("JUST_IN_TIME")){
				vendorId = omsServiceHelper.getVendorIdFromVendorItemDB(singleInventory[0]);
				selectQuery = "select * from inventory where sku_id ='" + singleInventory[0] + "' and seller_id='"
						+ singleInventory[4] +"' and vendor_id="+vendorId+";";
			}else{
				selectQuery = "select * from inventory where sku_id ='" + singleInventory[0] + "' and seller_id='"
						+ singleInventory[4] +"';";
			}
			log.info("VendorId: "+vendorId);

			resultSet = DBUtilities.exSelectQuery(selectQuery, "myntra_atp");
			// row = (HashMap<String, Object>) resultSet.get(0);
			System.out.println(resultSet);
			if (resultSet == null) {
				query = "INSERT INTO `inventory` ( `store_id`, `seller_id`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `supply_type`, `lead_time`, `enabled`, `last_synced_on`, `available_in_warehouses`, `vendor_id`, `created_by`, `created_on`, `last_modified_on`, `version`,`blocked_future_count`)"
						+ "VALUES (1," + singleInventory[4] + ", " + singleInventory[0] + ", NULL, "
						+ singleInventory[2] + ", " + singleInventory[3] + ", '" + supplyType + "', 0, "
						+ singleInventory[5] + ", '2015-02-09 08:23:10', '" + singleInventory[1]
						+ "', "+vendorId+", 'System', '2014-02-04 14:24:43', '2016-10-04 20:13:55', 103,0);";
				DBUtilities.exUpdateQuery(query, "myntra_atp");
				DBUtilities.exUpdateQuery("update inventory set enabled='0' where sku_id ='" + singleInventory[0]
						+ "' and seller_id!='" + singleInventory[4] + "';", "myntra_atp");

			} else {
				if(vendorId!=null){
					query = "update inventory set supply_type='" + supplyType + "', inventory_count="
							+ singleInventory[2] + ", blocked_order_count=" + singleInventory[3] + ",enabled='"
							+ singleInventory[5] + "', available_in_warehouses='" + singleInventory[1] + "' where sku_id ='"
							+ singleInventory[0] + "' and vendor_id="+vendorId+" and seller_id='" + singleInventory[4] + "';";
				}else{
					query = "update inventory set supply_type='" + supplyType + "', inventory_count="
							+ singleInventory[2] + ", blocked_order_count=" + singleInventory[3] + ",enabled='"
							+ singleInventory[5] + "', available_in_warehouses='" + singleInventory[1] + "' where sku_id ='"
							+ singleInventory[0] + "' and seller_id='" + singleInventory[4] + "';";
				}
				
				
				DBUtilities.exUpdateQuery(query, "myntra_atp");
				DBUtilities
						.exUpdateQuery(
								"update inventory set enabled='0',inventory_count=0,blocked_future_count=0 where sku_id ='"
										+ singleInventory[0] + "' and seller_id!='" + singleInventory[4] + "';",
								"myntra_atp");
			}

		}
	}
	
	public static InventoryResponse syncInventory(String availableInWhs, String inventoryOperation, String leadTime,
			String quantity, String sellerId, String skuCode, String skuId, String storeId, String supplyType)
			throws JAXBException, UnsupportedEncodingException {
		SyncInventoryEntry syncInventoryrequest = new SyncInventoryEntry();
		syncInventoryrequest.setAvailableInWhs(availableInWhs);
		if (inventoryOperation.equals("DCR_BOC"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.DCR_BOC);
		if (inventoryOperation.equals("DCR_INV"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.DCR_INV);
		if (inventoryOperation.equals("DCR_INV_BOC"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.DCR_INV_BOC);
		if (inventoryOperation.equals("DISABLE_INV"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.DISABLE_INV);
		if (inventoryOperation.equals("ENABLE_INV"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.ENABLE_INV);
		if (inventoryOperation.equals("INR_BOC"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.INR_BOC);
		if (inventoryOperation.equals("INR_INV"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.INR_INV);
		if (inventoryOperation.equals("JIT_INV_UPDATE"))
			syncInventoryrequest.setInventoryOperation(InventoryOperation.JIT_INV_UPDATE);
		syncInventoryrequest.setLeadTime(Long.parseLong(leadTime));
		syncInventoryrequest.setQuantity(Long.parseLong(quantity));
		syncInventoryrequest.setSellerId(Long.parseLong(sellerId));
		syncInventoryrequest.setSkuCode(skuCode);
		syncInventoryrequest.setSkuId(Long.parseLong(skuId));
		syncInventoryrequest.setStoreId(Long.parseLong(storeId));
		if (supplyType.equals("ON_HAND"))
			syncInventoryrequest.setSupplyType(SupplyType.ON_HAND);
		else
			syncInventoryrequest.setSupplyType(SupplyType.JUST_IN_TIME);
		String payload = APIUtilities.convertXMLObjectToString(syncInventoryrequest);
		System.out.println(payload);
		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.SYNC_INV, null, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.PUT, payload, getATPHeader());
		System.out.println("Response is:" + service.getResponseBody());
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InventoryResponse());
		System.out.println("response is:" + inventoryResponse);
		return inventoryResponse;
	}

	public static InventoryResponse getBoc(String sellerId, String skuId, String storeId, String supplyType)
			throws UnsupportedEncodingException, JAXBException {
		String[] pathparam = { "getBlockedOrderCount?skuId=" + skuId + "&supplyType=" + supplyType + "&sellerId="
				+ sellerId + "&storeId=" + storeId };

		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.GET_BOC, pathparam, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.GET, "", getATPHeader());
		System.out.println("Response is:" + service.getResponseBody());
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new InventoryResponse());
		System.out.println("response is:" + inventoryResponse);
		return inventoryResponse;
	}
	
	public static Svc getBocNegative(String sellerId, String skuId, String storeId, String supplyType)
			throws UnsupportedEncodingException, JAXBException {
		String[] pathparam = { "getBlockedOrderCount?skuId=" + skuId + "&supplyType=" + supplyType + "&sellerId="
				+ sellerId + "&storeId=" + storeId };

		Svc service = HttpExecutorService.executeHttpService(Constants.ATP_PATH.GET_BOC, pathparam, SERVICE_TYPE.ATP_SVC.toString(),
				HTTPMethods.GET, "", getATPHeader());
		return service;
	}

}
