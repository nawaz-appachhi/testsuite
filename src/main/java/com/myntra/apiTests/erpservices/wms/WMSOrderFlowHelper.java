package com.myntra.apiTests.erpservices.wms;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.client.wms.codes.utils.PacketItemStatus;
import com.myntra.client.wms.codes.utils.PickType;
import com.myntra.client.wms.response.CheckoutEntry;
import com.myntra.client.wms.response.ConsolidationEntry;
import com.myntra.client.wms.response.ConsolidationResponse;
import com.myntra.client.wms.response.ItemEntry;
import com.myntra.client.wms.response.ItemResponse;
import com.myntra.client.wms.response.PacketItemEntry;
import com.myntra.client.wms.response.VirtualPacketEntry;
import com.myntra.client.wms.response.VirtualPacketResponse;
import com.myntra.commons.response.EmptyResponse;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;

public class WMSOrderFlowHelper extends BaseTest {
	
	public static void updateInventory(int skuId, int warehouseId, int sellerId){
		updateAtp(skuId, sellerId, warehouseId);
		updateIms(skuId, warehouseId, sellerId);
	}
		
	public static void updateAtp(int skuId, int sellerId, int warehouseId) {

		Map<String, Object> data =  DBUtilities
			.exSelectQueryForSingleRecord("select id from inventory where sku_id=" + skuId + " and seller_id like " + sellerId + " and enabled = 1",
					"myntra_atp");
			
		if(data == null){
			String supplyType = (warehouseId == 28 || warehouseId == 36) ? "ON_HAND" : "JUST_IN_TIME";
			String vendorId = supplyType == "ON_HAND" ? "NULL" : "1010";
				
			String insertQuery = "INSERT INTO `inventory` (`id`, `store_id`, `seller_id`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `supply_type`, `lead_time`, `enabled`, `last_synced_on`, `available_in_warehouses`, `vendor_id`, `created_by`, `created_on`, `last_modified_on`, `version`, `blocked_future_count`)VALUES" +
					"(NULL, 1, "+ sellerId +", "+ skuId +", NULL, 10000, 1, '"+ supplyType +"', 0, 1, '2015-02-09 08:23:10', '"+ warehouseId +"', "+ vendorId +", 'System', '2014-02-04 14:24:43', '2017-11-27 12:07:57', 133, 0)";
			DBUtilities.exSelectQuery(insertQuery, "myntra_atp");
				
			}else{
				long id = (long) data.get("id");
				
				DBUtilities.exUpdateQuery(
						"UPDATE `inventory` SET `inventory_count` = 10000 and `blocked_order_count` = 1 where id = " + id,
						"myntra_atp");

		}
	}
		
	public static void updateIms(int skuId, int warehouseId, int sellerId){
	    Map<String, Object> data =  DBUtilities
				.exSelectQueryForSingleRecord("select id from wh_inventory where sku_id=" + skuId + " and warehouse_id = "+ warehouseId + " and seller_id = "+ sellerId,
						"myntra_ims");
			
	    if(data == null){
	    		String supplyType = (warehouseId == 28 || warehouseId == 36) ? "ON_HAND" : "JUST_IN_TIME";
	    		String vendorId = supplyType == "ON_HAND" ? "NULL" : "1010";
	    		
	    		String insertQuery = "INSERT INTO `wh_inventory` (`id`, `warehouse_id`, `warehouse_name`, `store_id`, `supply_type`, `sku_id`, `sku_code`, `inventory_count`, `blocked_order_count`, `created_by`, `created_on`, `last_modified_on`, `last_synced_on`, `pushed_order_count`, `version`, `vendor_id`, `seller_id`, `proc_sla`, `override_auto_realloc`)VALUES"+
	    			"(NULL, "+ warehouseId +", 'Bangalore New W/H', 1, '"+ supplyType +"', "+ skuId +", 'PUMATSRM01400', 10000, 1, 'erpadmin', '2015-05-05 14:54:36', '2017-11-27 12:23:04', '2015-05-05 14:54:36', 0, 16459, "+ vendorId +", "+ sellerId +", 0, 1)";
	    		DBUtilities.exSelectQuery(insertQuery, "myntra_ims");
	    		
	    	}else{
	    		long id = (long) data.get("id");
	    		DBUtilities.exUpdateQuery(
	    				"UPDATE `wh_inventory` SET `inventory_count` = 10000 and `blocked_order_count` = 1 where id = " + id,
	    				"myntra_ims");
	    }	
	}
	
	public List<HashMap> getOrderDetails(String storeOrderId){
		String selectQuery = "select order_release_id_fk, order_id_fk, sku_id, source_wh_id, dispatch_wh_id, quantity from order_line where store_order_id = '"+ storeOrderId +"'";
		List<HashMap> list = DBUtilities.exSelectQuery(selectQuery, "myntra_oms");
		return list;
	}
	
	public HashMap<Integer, String> getWarehouseIdsAndPickTypes(String OrderIdFk){
		HashMap<Integer, String> warehouseIds = new HashMap<>();
		String selectQuery = "select warehouse_id, pick_type from capture_order_release where order_id = "+ OrderIdFk;
		List<HashMap> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");
		for (HashMap list1 : list) {
			warehouseIds.put(Integer.parseInt(list1.get("warehouse_id").toString()), list1.get("pick_type").toString());
		}
		return warehouseIds;
	}
	
	public void pushOrderToWave(HashMap<Integer, String> warehouseIdsAndPickTypes) throws UnsupportedEncodingException, InterruptedException {
		Svc service;
		
		for(Integer warehouseId : warehouseIdsAndPickTypes.keySet()){
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PUSHORDERWMS,
					new String[] { warehouseId + "?pickType=" + warehouseIdsAndPickTypes.get(warehouseId) }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT,
					null, getWMSHeader());
			String pushOrderResponse = service.getResponseBody();
			Thread.sleep(2000);
			if (!APIUtilities.getElement(pushOrderResponse, "orderReleaseResponse.status.statusType", "XML")
					.equalsIgnoreCase("SUCCESS")) {
				//creteRelaseInCore_order_release(releaseId.toString());
				System.out.println("Failed to Push the Order to Order wave");
				//SlackMessenger.send("scm_e2e_order_sanity", "Failed to Push the Order to Order wave", 2);
				Assert.fail("Failed to Push the Order to Order wave");
			}
		}
	}
	
	public static HashMap<String, String> getWMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "Application/xml");
		return createOrderHeaders;
	}
	
	public List<HashMap> insertItems(String skuId, String warehosueId, int qty, int buyerId, int poId, int poSkuId){
		
		HashMap<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "516128");
		binEntry.put("36", "1328935");
		
		for (int i =0; i<qty;i++) {
			
			long id = getMaxItemId()+1;
			
			DBUtilities.exUpdateQuery("INSERT INTO `item` (id, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, " +
					"`comments`, `order_id`, `bin_id`) VALUES ("+id+", " + id + ", " + skuId + ", 'Q1', 'STORED', " + warehosueId + ", 1, "+ poId +", 'OPST050911-09', "+ poSkuId +", 1, 'LOTVHGA-01', 'Automatio item', NULL, " +
					"" + binEntry.get("" + warehosueId) + ")", "wms");
			
			String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
					+ "VALUES(" + id +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, 'OUTRIGHT', " + buyerId + ")";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
			
			System.out.println(id);
		}
		
		String selectQuery = "select barcode from item where sku_id = "+ skuId +" and po_id = "+ poId +" and po_sku_id = "+ poSkuId +" and warehouse_id = "+ warehosueId +" order by id desc limit "+qty;
		List<HashMap> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");
		return list;
	}
	
	
	public void itemCheckout(List<HashMap> list, String orderId) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		List<ItemEntry> items = new ArrayList<ItemEntry>();
		ItemEntry item;
		for (HashMap list1 : list) {
			System.out.println(list1.get("barcode"));
			item = new ItemEntry();
			item.setId(Long.parseLong(list1.get("barcode").toString()));
			items.add(item);
		}
		
		CheckoutEntry entry = new CheckoutEntry();
		entry.setOrderId(orderId);
		entry.setCartonBarcode("");
		entry.setItems(items);
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ITEM_CHECKOUT,
				new String[] { "checkout" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				APIUtilities.convertXMLObjectToString(entry), getWMSHeader());
		System.out.println(service.getResponseBody());
		ItemResponse response = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());
		System.out.println(response.getStatus().getStatusMessage());
		if(response.getStatus().getStatusMessage().equals("Items checkout for the order successfully")){
			return;
			
		}else if(response.getStatus().getStatusMessage().contains("Some error occurred while processing your request. Please retry after sometime")){
			Thread.sleep(60000);
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ITEM_CHECKOUT,
					new String[] { "checkout" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
					APIUtilities.convertXMLObjectToString(entry), getWMSHeader());
			System.out.println(service.getResponseBody());
			response = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());
			
		}else{
			Assert.assertEquals(response.getStatus().getStatusMessage(), "Items checkout for the order successfully", response.getStatus().getStatusMessage());
		}
		Assert.assertEquals(response.getStatus().getStatusMessage(), "Items checkout for the order successfully", response.getStatus().getStatusMessage());
	}
	
	public long getMaxItemId(){
		Map<String, Object> getId = DBUtilities.exSelectQueryForSingleRecord("select max(id) from item","wms");
		Long maxId = (Long)getId.get("max(id)");
		if(maxId==null){
			maxId = 0L;
		}
		return maxId;
	}
	
	public HashMap<String, String> lmcSorting(String itemBarcode) throws UnsupportedEncodingException, JAXBException, JSONException, XMLStreamException, ParseException{
		HashMap<String, String> lmcSortingResponse = new HashMap<>();
		JSONParser parser = new JSONParser();
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_ITEM_INSCAN,
				new String[] { ""+ itemBarcode}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,
				null, WMSServiceHelper.getWMSHeader());
		System.out.println(service.getResponseBody());
		
		org.json.simple.JSONObject object = (org.json.simple.JSONObject) parser.parse(service.getResponseBody());
		org.json.simple.JSONObject lmcItemScanResponse = (org.json.simple.JSONObject) object.get("lmcItemScanResponse");
		org.json.simple.JSONObject status = (org.json.simple.JSONObject) lmcItemScanResponse.get("status");
		String binNumber = (String) lmcItemScanResponse.get("binNumber");
		String statusType = status.get("statusType").toString();
		String statusMessage = status.get("statusMessage").toString();
		 
		lmcSortingResponse.put("statusType", statusType);
		lmcSortingResponse.put("statusMessage", statusMessage);
		if(binNumber != null){
			lmcSortingResponse.put("binNumber", binNumber.toString());
		}
		return lmcSortingResponse;
	}
	
	public VirtualPacketResponse flushBin(String binBarcode) throws UnsupportedEncodingException, JAXBException{
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_FLUSH_BIN,
				new String[] { "flushBin?binBarcode="+binBarcode}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				null, WMSServiceHelper.getWMSHeader());
		System.out.println(service.getResponseBody());
		VirtualPacketResponse response = (VirtualPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VirtualPacketResponse());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), "SUCCESS", response.getStatus().getStatusMessage());
		return response;
	}
	
	public void markVirtualPacketPicked(long consolidationPacketId) throws UnsupportedEncodingException, JAXBException{
		VirtualPacketEntry virtualPacketEntry;
		PacketItemEntry packetItemEntry;
		ArrayList<PacketItemEntry> packetItemEntries;
		
		Svc service;
		VirtualPacketResponse response;
		
		String query = "select id from virtual_packets where consolidation_packet_id = "+consolidationPacketId;
		List<HashMap> list = DBUtilities.exSelectQuery(query, "myntra_wms");
		
		for(HashMap list1 : list){
			virtualPacketEntry = new VirtualPacketEntry();
			virtualPacketEntry.setId((Long)list1.get("id"));
			
			packetItemEntries = new ArrayList<>();
			
			query = "select item_barcode from packet_items where virtual_packet_id = "+list1.get("id") +" and consolidation_packet_id = "+ consolidationPacketId;
			List<HashMap> items = DBUtilities.exSelectQuery(query, "myntra_wms");
			for(HashMap item : items){
				packetItemEntry = new PacketItemEntry();
				packetItemEntry.setItemBarcode(item.get("item_barcode").toString());
				packetItemEntry.setPacketItemStatus(PacketItemStatus.PICKED);
				packetItemEntries.add(packetItemEntry);
			}
			
			virtualPacketEntry.setPacketItemEntries(packetItemEntries);
			
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_MARK_PACKET_PICKED,
					new String[] {}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
					APIUtilities.convertXMLObjectToString(virtualPacketEntry), WMSServiceHelper.getWMSHeader());
			System.out.println(service.getResponseBody());
			response = (VirtualPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VirtualPacketResponse());
			Assert.assertEquals(response.getStatus().getStatusMessage(), "Success", response.getStatus().getStatusMessage());
			System.out.println(response.getStatus().getStatusMessage());
		}
		
	}
	
	public String prepareAndConsolidate(String itemId, String warehouseId) throws UnsupportedEncodingException, JAXBException{
		String orderBarcode;
		long orderReleaseId;
		long orderReleaseLineId;
		String binBarcode = null;
		PickType pickType;
		long consolidationPacketId;
		
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PREPAREFORCONSOLIDATION,
				new String[] { "prepareForConsolidation?itemId="+itemId }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				null, getWMSHeader());
		System.out.println(service.getResponseBody());
		ConsolidationResponse response = (ConsolidationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ConsolidationResponse());
		
		if(response.getStatus().getStatusMessage().contains("All the stations are occupied. Please place the item in overflow bin")){
			if(emptyConsolidationBin(warehouseId)){
				
				service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PREPAREFORCONSOLIDATION,
						new String[] { "prepareForConsolidation?itemId="+itemId }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
						null, getWMSHeader());
				
				response = (ConsolidationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ConsolidationResponse());
				
				if(!response.getStatus().getStatusMessage().contains("Item is ready for checkout")){
					Assert.fail("Prepare For Consolidation Failed");
				}
				
				orderBarcode = response.getData().get(0).getOrderBarcode();
				orderReleaseId = response.getData().get(0).getOrderReleaseId();
				orderReleaseLineId = response.getData().get(0).getOrderReleaseLineId();
				pickType = response.getData().get(0).getPickType();
				binBarcode = response.getData().get(0).getBinBarcode();
				consolidationPacketId = response.getData().get(0).getConsolidationPacketId();
				
				ConsolidationEntry entry = new ConsolidationEntry();
				entry.setBinBarcode(binBarcode);
				entry.setItemBarcode(itemId+"");
				entry.setOrderBarcode(orderBarcode);
				entry.setOrderReleaseId(orderReleaseId);
				entry.setOrderReleaseLineId(orderReleaseLineId);
				entry.setPickType(pickType);
				entry.setConsolidationPacketId(consolidationPacketId);
				
				checkInAndConsolidate(entry);
				
			}else{
				Assert.fail("Could not empty Consolidation Bin");
			}
		}else if(!response.getStatus().getStatusMessage().contains("Item is ready for checkout")){
			Assert.fail("Prepare For Consolidation Failed");
			
		}else{
			orderBarcode = response.getData().get(0).getOrderBarcode();
			orderReleaseId = response.getData().get(0).getOrderReleaseId();
			orderReleaseLineId = response.getData().get(0).getOrderReleaseLineId();
			pickType = response.getData().get(0).getPickType();
			binBarcode = response.getData().get(0).getBinBarcode();
			consolidationPacketId = response.getData().get(0).getConsolidationPacketId();
			
			ConsolidationEntry entry = new ConsolidationEntry();
			entry.setBinBarcode(binBarcode);
			entry.setItemBarcode(itemId+"");
			entry.setOrderBarcode(orderBarcode);
			entry.setOrderReleaseId(orderReleaseId);
			entry.setOrderReleaseLineId(orderReleaseLineId);
			entry.setPickType(pickType);
			entry.setConsolidationPacketId(consolidationPacketId);
			
			checkInAndConsolidate(entry);
		}
		return binBarcode;
	}
	
	public void checkInAndConsolidate(ConsolidationEntry entry) throws UnsupportedEncodingException, JAXBException{
		long binId = getVirtualBin(entry.getBinBarcode());
		setBinId(binId, entry.getItemBarcode());
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.CHECKINANDCONSOLIDATE,
				new String[] { "" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				APIUtilities.convertXMLObjectToString(entry), getWMSHeader());
		
		ConsolidationResponse response = (ConsolidationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ConsolidationResponse());
		
		if(response.getStatus().getStatusMessage().contains("is not allowed because of section movement restriction")){
			binId = getVirtualBin(entry.getBinBarcode());
			setBinId(binId, entry.getItemBarcode());
			
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.CHECKINANDCONSOLIDATE,
					new String[] { "" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
					APIUtilities.convertJavaObjectToJsonUsingGson(entry), getWMSHeader());
		}else if(response.getStatus().getStatusMessage().equalsIgnoreCase("Done. Consolidation NOT complete")){
			System.out.println("Consolidation NOT complete");
		}else if(response.getStatus().getStatusMessage().equalsIgnoreCase("Done. Consolidation complete")){
			System.out.println("Consolidation complete");
		}else if(response.getStatus().getStatusMessage().equalsIgnoreCase("Item successfully consolidated")){
			System.out.println("Item successfully consolidated");
		}else{
			Assert.fail("Error while Consolidating");
		}
	}
	
	public long getVirtualBin(String binBarcode){
		System.out.println("Getting Virtual Bin");
		String section = binBarcode.substring(0, binBarcode.indexOf("-"));
		long binId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from core_bins where barcode like '" + section + "%' and is_virtual = 1;", "myntra_wms")
				.get("id");
		return binId;
	}
	
	public void setBinId(long id, String itemId){
		System.out.println("Setting Virtual Bin: " + id + ", to itemId: "+ itemId);
		DBUtilities.exUpdateQuery("update item SET bin_id = "
				+ id +" where id = " + itemId + ";", "wms");
	}
	
	public boolean emptyConsolidationBin(String warehouseId) throws UnsupportedEncodingException, JAXBException{
		String binBarcode = (String) DBUtilities.exSelectQueryForSingleRecord("select sob.bin_barcode as binBarcode from core_sections cs join station_order_bin sob on cs.id = sob.section_id where cs.warehouse_id = " + warehouseId + " and sob.soft_assigned_order_barcode != 0;", "myntra_wms")
				.get("binBarcode");
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.FREECONSOLIDATIONBIN,
				new String[] { binBarcode + "" }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT,
				null, getWMSHeader());
		EmptyResponse response = (EmptyResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new EmptyResponse());
		if(response.getStatus().getStatusType().toString().equalsIgnoreCase("SUCCESS")){
			System.out.println("Free Consolidaitn Bin Success");
			return true;
		}else{
			System.out.println("Free Consolidation Bin Failed");
			return false;
		}
	}
	
	public boolean isOrderPushedToWMS(String orderReleaseId){
		String orderReleaseStatus = (String) DBUtilities.exSelectQueryForSingleRecord("select order_release_status from capture_order_release where portal_order_release_id = "+orderReleaseId, "myntra_wms")
				.get("order_release_status");
		if(orderReleaseStatus.equals("PUSHED_TO_PICK")){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isPushOrderToWaveApiHit(String orderIdFk){
		List<HashMap<String, Object>> coreOrderDetails =  DBUtilities.exSelectQuery("select * from core_order_release where order_id = "+orderIdFk, "myntra_wms");
		if(coreOrderDetails != null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isOrderCheckedOut(String orderReleaseId){
		HashMap<String, Object> itemDetails =  (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from item where order_id = '"+ orderReleaseId +"'", "myntra_wms");
		if(itemDetails == null){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isItemConsolidated(String itemBarcode){
		HashMap<String, Object> packetItemDetails =  (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select * from packet_items where item_barcode = '"+ itemBarcode +"'", "myntra_wms");
		if(packetItemDetails == null){
			return false;
		}else{
			return true;
		}
	}
	
	public ArrayList<HashMap> getItemsAssociatedWithOrder(String orderReleaseId){
		ArrayList<HashMap> items = new ArrayList<>();
		List<HashMap<String, Object>> itemsDetails =  DBUtilities.exSelectQuery("select barcode from item where order_id = '"+orderReleaseId+"'", "myntra_wms");
		for(HashMap item : itemsDetails){
			items.add(item);
		}
		return items;
	}
	
	public boolean isBinFlushRequired(String item) throws UnsupportedEncodingException, JAXBException, JSONException, XMLStreamException, ParseException{
		Map<String, String> lmcSortingResponse = lmcSorting(item);
		if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.")){
			System.out.println("Please send item to packing desk.");
			return false;
			
		}else if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin/section for consolidation")){
			System.out.println("Please keep item into bin/section for consolidation");
			return true;
			
		}else{
			return false;
		}
	}
	
	public String getBinBarcode(String item){
		return (String) DBUtilities.exSelectQueryForSingleRecord("select barcode from core_bins where id = (select bin_id from item where id = "+ item +")", "myntra_wms").get("barcode");
	}
	
	public long getWarehouseId(String item){
		return (long) DBUtilities.exSelectQueryForSingleRecord("select warehouse_id from item where id = "+item, "myntra_wms").get("warehouse_id");
	}
	
	public boolean verifyCaptureOrderReleaseTable(String orderReleaseId){
		String orderStatus = (String) DBUtilities.exSelectQueryForSingleRecord("select order_release_status from capture_order_release where portal_order_release_id = "+orderReleaseId, "myntra_wms").get("order_release_status");
		if(orderStatus.equals("COMPLETED")){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean verifyCoreOrderReleaseTable(String orderReleaseId){
		String orderStatus = (String) DBUtilities.exSelectQueryForSingleRecord("select order_release_status from core_order_release where portal_order_release_id = "+orderReleaseId, "myntra_wms").get("order_release_status");
		if(orderStatus.equals("COMPLETED")){
			return true;
		}else{
			return false;
		}
	}
}
