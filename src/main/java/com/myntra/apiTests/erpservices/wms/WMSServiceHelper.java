package com.myntra.apiTests.erpservices.wms;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntry;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.client.wms.codes.utils.PacketItemStatus;
import com.myntra.client.wms.codes.utils.PickType;
import com.myntra.client.wms.response.*;
import com.myntra.client.wms.response.location.LocationTrackerResponse;
import com.myntra.client.wms.response.location.MovementEntry;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.commons.response.EmptyResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.worms.client.entry.OrderCaptureReleaseEntry;
import com.myntra.worms.client.response.OrderCaptureReleaseResponse;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


/**
 * @author Abhijit.Pati
 * @created 15/10/15.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WMSServiceHelper extends BaseTest {
	 private SoftAssert softAssert = new SoftAssert();
	int waitTime = 15;
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(WMSServiceHelper.class);

	/**
	 * Get the SkuIDs and Quantities for an OrderReleaseID
	 * 
	 * @param orderReleaseID
	 * @return
	 * @throws SQLException
	 */
	private String[] getSKUIdsForAnOrderReleaseID(String orderReleaseID) {
		ArrayList<String> skuids = new ArrayList<>();
		try {

			List<HashMap> list = DBUtilities.exSelectQuery(
					"select sku_id,quantity from order_line where status_code in ('"+EnumSCM.A+"','"+EnumSCM.WP+"') and order_release_id_fk="
							+ orderReleaseID,
					"myntra_oms");
			for (HashMap list1 : list) {
				skuids.add("" + list1.get("sku_id") + ":" + list1.get("quantity"));
			}
		} catch (Exception e) {
			assertFalse(true, "Failed to get SKU information from DB");
		}
		return skuids.toArray(new String[skuids.size()]);
	}

	public OrderCaptureReleaseResponse markReleasePacked(String releaseID) throws IOException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.WORMS_PATH.MARKORDERPACKED,
				new String[] { releaseID }, SERVICE_TYPE.WORMS_SVC.toString(), HTTPMethods.PUT, "{}", getWMSHeader());
		OrderCaptureReleaseResponse orderCaptureReleaseResponse = (OrderCaptureReleaseResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new OrderCaptureReleaseResponse());
		return orderCaptureReleaseResponse;
	}
	
	public VirtualPacketResponse flushBin(String binBarcode) throws UnsupportedEncodingException, JAXBException{
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.LMC_FLUSH_BIN,
				new String[] { "flushBin?binBarcode="+binBarcode}, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST,
				null, WMSServiceHelper.getWMSHeader());
		log.info(service.getResponseBody());
		VirtualPacketResponse response = (VirtualPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new VirtualPacketResponse());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), "SUCCESS", response.getStatus().getStatusMessage());
		return response;
	}
	
	/**
	 * Push Order to Order Wave for Picking
	 * 
	 * @param releaseId
	 * @throws InterruptedException 
	 */
	public void pushOrderToWave(String releaseId) throws UnsupportedEncodingException, InterruptedException {

		HashMap hm = (HashMap) DBUtilities.exSelectQueryForSingleRecord(
				"select * from capture_order_release where portal_order_release_id= " + releaseId, "myntra_wms");
		String pick_type = hm.get("pick_type").toString();
		String warehouseID = "" + hm.get("warehouse_id");
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PUSHORDERWMS,
				new String[] { warehouseID + "?pickType=" + pick_type }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT,
				null, getWMSHeader());
		String pushOrderResponse = service.getResponseBody();
		if (!APIUtilities.getElement(pushOrderResponse, "orderReleaseResponse.status.statusType", "XML")
				.equalsIgnoreCase("SUCCESS")) {
			creteRelaseInCore_order_release(releaseId.toString());
			System.out.println("Failed to Push the Order to Order wave");
			SlackMessenger.send("scm_e2e_order_sanity", "Failed to Push the Order to Order wave", 2);
			//Assert.fail("Failed to Push the Order to Order wave");
		}
	}
	
	public Map<Integer, String> getWarehouseIdsAndPickTypes(List<ReleaseDetailsEntry> releaseDetailsEntries){
		Map<Integer, String> warehouseIds = new HashMap<>();
		for(ReleaseDetailsEntry releaseDetailEntry : releaseDetailsEntries){
			String selectQuery = "select warehouse_id, pick_type from capture_order_release where portal_order_release_id = "+ releaseDetailEntry.getReleaseId();
			Map<String, Object> pickTypeEntry = DBUtilities.exSelectQueryForSingleRecord(selectQuery, "myntra_wms");
			long warehouseId = (long) pickTypeEntry.get("warehouse_id");
			String pickType = pickTypeEntry.get("pick_type").toString();
			if(warehouseId == 0 || pickType.isEmpty()){
				log.info("WarehouseId: "+ warehouseId +" pickType: "+pickType);
				return null;
			}
			warehouseIds.put((int)warehouseId, pickType);
		}
		return warehouseIds;
	}
	
	public void pushOrderToWave(Map<Integer, String> warehouseIdsAndPickTypes) throws UnsupportedEncodingException, InterruptedException, ManagerException {
		Svc service;
		
		for(Integer warehouseId : warehouseIdsAndPickTypes.keySet()){
			service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.PUSHORDERWMS,
					new String[] { warehouseId + "?pickType=" + warehouseIdsAndPickTypes.get(warehouseId) }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT,
					null, getWMSHeader());
			String pushOrderResponse = service.getResponseBody();
			Thread.sleep(2000);
			if (!APIUtilities.getElement(pushOrderResponse, "orderReleaseResponse.status.statusType", "XML")
					.equalsIgnoreCase("SUCCESS")) {
				ExceptionHandler.fail("Push Order Wave API failed");
				return;
			}
			log.info("Push Order to Wave success");
		}
	}
	
	
	public void pushOrderToWMS(String orderId, List<ReleaseDetailsEntry> releaseDetailsEntries) throws UnsupportedEncodingException, ManagerException, InterruptedException{
		Map<Integer, String> warehouseIdsAndPickTypes = getWarehouseIdsAndPickTypes(releaseDetailsEntries);
		
		if(!isPushOrderToWaveApiHit(orderId)){
			pushOrderToWave(warehouseIdsAndPickTypes);
		}
		
		for(ReleaseDetailsEntry releaseDetailsEntry : releaseDetailsEntries){
			if(!isOrderPushedToWMS(releaseDetailsEntry.getReleaseId())){
				creteRelaseInCore_order_release(releaseDetailsEntry.getReleaseId());
			}
		}
	}
	
	public List<Map<String, Object>> orderChckout(List<ReleaseDetailsEntry> releaseDetailsEntries) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		List<Map<String, Object>> allItems = new ArrayList<>();
		
		for(ReleaseDetailsEntry releaseDetailsEntry : releaseDetailsEntries){
			if(!isOrderCheckedOut(releaseDetailsEntry.getReleaseId())){
				
				OrderLineEntry orderLineEntry = getOrderLineEntry(releaseDetailsEntry);
				List<Map<String, Object>> items = insertItems(""+orderLineEntry.getSkuId(), ""+orderLineEntry.getSourceWarehouseId(), orderLineEntry.getQuantity(), 3974, 313, 1);
				itemCheckout(items, orderLineEntry.getOrderReleaseId()+"");
				allItems.addAll(items);
        		Thread.sleep(1000);
        		
			}else{
				allItems.addAll(getItem(releaseDetailsEntry));
			}
		}
		return allItems;
	}
	
	public List<Map<String, Object>> getItem(ReleaseDetailsEntry releaseDetailsEntry){
		return DBUtilities.exSelectQuery("select barcode from item where order_id = "+releaseDetailsEntry.getReleaseId(), "myntra_wms");
	}
	
	public void consolidateRelease(List<Map<String, Object>> items) throws UnsupportedEncodingException, JAXBException, JSONException, XMLStreamException, ParseException, ManagerException{
		
		for(Map item : items){
			if(!isItemConsolidated(item.get("barcode").toString())){
				Map<String, String> lmcSortingResponse = lmcSorting(item.get("barcode").toString());
				
				if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.") || lmcSortingResponse.get("statusMessage").contains("PACK DESK")){
					log.info("Please send item to packing desk.");
					
				}else if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin/section for consolidation") || lmcSortingResponse.get("statusMessage").contains("MULTI - Consolidation Bin")){
					log.info("Please keep item into bin/section for consolidation");
					prepareAndConsolidate(item.get("barcode").toString(), getWarehouseId(item.get("barcode").toString())+"");
					
				}else{
					ExceptionHandler.fail(lmcSortingResponse.get("statusMessage"));
				}
			}
		}
	}
	
	public boolean isBinFlushRequired(String item) throws UnsupportedEncodingException, JAXBException, JSONException, XMLStreamException, ParseException{
		Map<String, String> lmcSortingResponse = lmcSorting(item);
		if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.") || lmcSortingResponse.get("statusMessage").contains("PACK DESK")){
			log.info("Please send item to packing desk.");
			return false;
			
		}else if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin/section for consolidation") || lmcSortingResponse.get("statusMessage").contains("MULTI - Consolidation Bin")){
			log.info("Please keep item into bin/section for consolidation");
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
	
	public List<Map<String, Object>> insertItems(String skuId, String warehosueId, int qty, int buyerId, int poId, int poSkuId){
		
		Map<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
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
		List<Map<String, Object>> list = DBUtilities.exSelectQuery(selectQuery, "myntra_wms");
		return list;
	}
	
	public void itemCheckout(List<Map<String, Object>> list, String orderId) throws UnsupportedEncodingException, JAXBException, InterruptedException{
		List<ItemEntry> items = new ArrayList<ItemEntry>();
		ItemEntry item;
		for (Map list1 : list) {
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
	
	public OrderLineEntry getOrderLineEntry(ReleaseEntry releaseEntry) throws UnsupportedEncodingException, JAXBException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		return omsServiceHelper.getOrderReleaseEntry(releaseEntry.getReleaseId()).getOrderLines().get(0);
	}
	public OrderLineEntry getOrderLineEntry(ReleaseDetailsEntry releaseDetailEntry) throws UnsupportedEncodingException, JAXBException{
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		return omsServiceHelper.getOrderReleaseEntry(releaseDetailEntry.getReleaseId()).getOrderLines().get(0);
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
	

	
	/**
	 * WSM Order Item Association for a Release ID
	 * 
	 * @param orderReleaseID
	 * @param wareHouseID
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public String[] orderItemsCheckout(String orderReleaseID, String wareHouseID)
			throws SQLException, IOException, JAXBException, ManagerException {
		log.info("Item Checkout for Order release ID - " + orderReleaseID);
		
		//Get buyer_id
		long buyerId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from vendor where is_buyer = 1", "myntra_vms")
				.get("id");
		
		String[] skuIdsAndQuantity = getSKUIdsForAnOrderReleaseID(orderReleaseID);

		resetItemStatusForOrderID(orderReleaseID, wareHouseID);
		
		for (String skuidquantity : skuIdsAndQuantity) {
			String[] skuidquantityEach = skuidquantity.split(":");
			System.out.println("SKU ID " + skuidquantityEach[0] + "    Quantity : " + skuidquantityEach[1]);
			int quantityOrdered = Integer.parseInt(skuidquantityEach[1]);
			List<HashMap> itemBarcodes = DBUtilities.exSelectQuery("select id, barcode from item where sku_id="
					+ skuidquantityEach[0] + " and quality='Q1' and item_status='STORED' and `warehouse_id`="
					+ wareHouseID + " ORDER BY RAND() LIMIT " + quantityOrdered + ";", "wms");
			if (itemBarcodes == null) {
				ExceptionHandler.fail("Not able to Find Item Barcodes for skuID" + skuidquantityEach[0]);
			}
			
			//check for item_info data for the items
			for(int i=0; i<itemBarcodes.size(); i++){
				setBinIdForItems(Long.parseLong(itemBarcodes.get(i).get("id").toString()), wareHouseID);
				checkItemInfoForItemData(Long.parseLong(itemBarcodes.get(i).get("id").toString()), buyerId);
			}
			CheckoutEntry checkoutEntry = getCheckOutEntry(orderReleaseID, "", itemBarcodes);
            String payload = APIUtilities.convertXMLObjectToString(checkoutEntry);
            Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ORDER_ITEM_ASSOCIATION,null, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
            String itemAssociationResponse = service.getResponseBody();
            ItemResponse itemResponse = (ItemResponse) APIUtilities.convertXMLStringToObject(itemAssociationResponse, new ItemResponse());
            ExceptionHandler.handleEquals(itemResponse.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"Error in item association");
		}

		String[] itemBarcodes = getItemsAssociatedWithOrder(orderReleaseID);
		List<HashMap> binbarcodes = getBinBarcodeAndRandom(wareHouseID);
		for (HashMap hashMap : binbarcodes) {

			String destBinCode = "" + hashMap.get("barcode");
			String randomCode = "" + hashMap.get("random_code");

			LocationTrackerResponse locationTrackerResponse = markOrderQADone(itemBarcodes, destBinCode, randomCode);
			if (locationTrackerResponse.getStatus().getStatusType() == Type.SUCCESS) {
				break;
			}
		}
		return itemBarcodes;
	}
	
	public void checkItemInfoForItemData(long itemId, long buyerId){
		long itemPoId = (long) DBUtilities.exSelectQueryForSingleRecord("select po_id from item where id = " + itemId, "myntra_wms")
				.get("po_id");
		
		String agreementType = getAgreementType(itemPoId);
		
		HashMap<String, Object> itemInfoData = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select count(*) as count from item_info where item_id = " + itemId, "myntra_wms");
		
		if(Integer.valueOf(itemInfoData.get("count").toString()) == 0){
			log.info("Inserting item_info data for item_id: "+ itemId);
			
			String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
					+ "VALUES(" + itemId +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, '"+ agreementType +"', " + buyerId + ")";
			DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
		}else{
			long itemBuyerId = (long) DBUtilities.exSelectQueryForSingleRecord("select buyer_id from `item_info` where item_id = " + itemId, "myntra_wms")
					.get("buyer_id");
			
			if(checkIsBuyer(itemBuyerId)){
				String updateQuery = "update item_info set agreement_type = '" + agreementType + "' where item_id = " + itemId;
				DBUtilities.exUpdateQuery(updateQuery, "myntra_wms");
				
			}else{
				long newBuyerId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from vendor where is_buyer = 1", "myntra_vms")
				.get("id");
				String updateQuery = "update item_info set buyer_id = " + newBuyerId + ", agreement_type = '" + agreementType + "' where item_id = " + itemId;
				DBUtilities.exUpdateQuery(updateQuery, "myntra_wms");
			}
		}
	}
	
	public boolean checkIsBuyer(long buyerId){
		return (boolean) DBUtilities.exSelectQueryForSingleRecord("select is_buyer from vendor where id = " + buyerId, "myntra_vms")
						.get("is_buyer");
	}
	
	public void setBinIdForItems(long itemId, String wareHouseID){
		HashMap<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
		binEntry.put("36", "582762");
		DBUtilities.exUpdateQuery("update item SET bin_id = "
				+ binEntry.get(wareHouseID) + " where id = " + itemId + ";", "wms");
	}
	
	public void setVirtualBin(String itemId, String warehouse_id){
		HashMap<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
		binEntry.put("36", "582762");
		
		long sectionId = (long) DBUtilities.exSelectQueryForSingleRecord("select section_id from core_bins where id =" + binEntry.get(warehouse_id), "myntra_wms")
		.get("section_id");
		
		long virtualBin = (long) DBUtilities.exSelectQueryForSingleRecord("select id from core_bins where section_id = "+ sectionId +" and warehouse_id = " + warehouse_id + " and is_virtual = 1", "myntra_wms")
				.get("id");
		
		DBUtilities.exUpdateQuery("update item SET bin_id = "
				+ virtualBin + " where id = " + itemId + ";", "wms");
		System.out.println("Virtual Bin has been set for item_id: " + itemId);
	}
	
	/**
	 * Mark Item QA DOne For a Particular ReleaseID
	 * 
	 * @param orderReleaseID
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public void markItemQADONE(String orderReleaseID) throws JAXBException, UnsupportedEncodingException {

		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseOMSEntry = omsServiceHelper
				.getOrderReleaseEntry(orderReleaseID);
		String[] itemBarcodes = getItemsAssociatedWithOrder(orderReleaseID);

		String wareHouseID = "" + orderReleaseOMSEntry.getWarehouseId();

		List<HashMap> binbarcodes = getBinBarcodeAndRandom(wareHouseID);
		for (HashMap hashMap : binbarcodes) {

			String destBinCode = "" + hashMap.get("barcode");
			String randomCode = "" + hashMap.get("random_code");

			LocationTrackerResponse locationTrackerResponse = markOrderQADone(itemBarcodes, destBinCode, randomCode);
			if (locationTrackerResponse.getStatus().getStatusType() == Type.SUCCESS) {
				break;
			}
		}
		markItemQADoneThroughQuery(orderReleaseID);
	}

	/**
	 * Create Order Item Association WMS
	 * 
	 * @param orderReleaseID
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ManagerException 
	 */
	public void createOrderItemAssociation(String orderReleaseID) throws JAXBException, IOException, SQLException, ManagerException {
		log.info("Create Order Item Association - " + orderReleaseID);
		
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String warehouseID = omsServiceHelper.getOrderReleaseEntry(orderReleaseID).getWarehouseId().toString();
		List<Map<String, Object>> orderLine = DBUtilities.exSelectQuery("select sku_id, quantity from order_line where order_release_id_fk = "+orderReleaseID,"oms");
		for (Map<String, Object> line: orderLine){
			insertItem(line.get("sku_id").toString(),warehouseID,Integer.parseInt(line.get("quantity").toString()));
		}
		
		APIUtilities apiUtilities = new APIUtilities();

		com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseOMSEntry = omsServiceHelper
				.getOrderReleaseEntry(orderReleaseID);
		String wareHouseID = "" + orderReleaseOMSEntry.getWarehouseId();
		String[] skuIdsAndQuantity = getSKUIdsForAnOrderReleaseID(orderReleaseID);

		resetItemStatusForOrderID(orderReleaseID, wareHouseID);

		for (String skuIdQuantity : skuIdsAndQuantity) {
			String[] skuIdQuantityEach = skuIdQuantity.split(":");
			System.out.println("SKU ID " + skuIdQuantityEach[0] + "    Quantity : " + skuIdQuantityEach[1]);

			int quantityOrdered = Integer.parseInt(skuIdQuantityEach[1]);

			List<HashMap> itemBarcodes = DBUtilities.exSelectQuery("select id from item where sku_id="
					+ skuIdQuantityEach[0] + " and quality='Q1' and item_status='STORED' and `warehouse_id`="
					+ wareHouseID + " LIMIT " + quantityOrdered + ";", "wms");

			if (itemBarcodes == null) {
				Assert.fail("Not able to Find Item Barcodes for skuID" + skuIdQuantityEach[0]);
			}
			CheckoutEntry checkoutEntry = getCheckOutEntry(orderReleaseID, "", itemBarcodes);

			String payload = APIUtilities.convertXMLObjectToString(checkoutEntry);
			Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ORDER_ITEM_ASSOCIATION,null, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
			String itemAssociationResponse = service.getResponseBody();
	        ItemResponse itemResponse = (ItemResponse) APIUtilities.convertXMLStringToObject(itemAssociationResponse, new ItemResponse());
			ExceptionHandler.handleEquals(itemResponse.getStatus().getStatusType().toString(), Type.SUCCESS.toString(),"Error in item association");
		}
	}

	public void markItemQADoneThroughQuery(String orderReleaseID) {
		try {
			DBUtilities.exUpdateQuery(
					"update order_line set status_code='QD' where status_code='A' and order_release_id_fk="
							+ orderReleaseID,
					"myntra_oms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void resetItemStatusForOrderID(String orderReleaseID, String wareHouseID) throws SQLException {
		HashMap<String, String> binEntry = new HashMap<>();
		binEntry.put("1", "403");
		binEntry.put("19", "151924");
		binEntry.put("28", "271760");
		binEntry.put("36", "582762");
		DBUtilities.exUpdateQuery("update item SET order_id=null, carton_barcode=null, item_status='STORED', bin_id="
				+ binEntry.get(wareHouseID) + " where order_id='" + orderReleaseID + "';", "wms");
	}

	/**
	 * Get Items associated with Orders
	 * 
	 * @param orderID
	 * @return
	 */
	public String[] getItemsAssociatedWithOrder(String orderID) {
		ArrayList<String> al = new ArrayList();
		try {
			List<HashMap> list = DBUtilities.exSelectQuery("select id from item where order_id= " + orderID, "wms");
			for (HashMap hashMap : list) {
				String barcode = "" + hashMap.get("id");
				al.add(barcode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			Assert.assertFalse(true, "Get Item Assocition For Order Failed");
		}
		return al.toArray(new String[al.size()]);
	}

	/**
	 * Get Bin Barcode and Random for a Warehouse ID
	 * 
	 * @param wareHouseID
	 * @return
	 */
	private List<HashMap> getBinBarcodeAndRandom(String wareHouseID) {
		List list = new ArrayList();
		try {
			list = DBUtilities.exSelectQuery("select barcode,random_code from core_bins where warehouse_id="
					+ wareHouseID + "  and `virtual`=0 and barcode like '%ST%' LIMIT 5", "myntra_wms");
		} catch (Exception e) {
			Assert.fail("Unable to find barcode and random code from myntra_wms.core_bins");
		}
		return list;
	}

	/**
	 * Create Checkout Entry Object
	 * 
	 * @param orderReleaseID
	 * @param cartonBarcode
	 * @param itembarcodes
	 * @return {@link CheckoutEntry}
	 */
	private CheckoutEntry getCheckOutEntry(String orderReleaseID, String cartonBarcode, List<HashMap> itembarcodes) {
		System.out.println("Create Checkout Entry Object " + orderReleaseID);
		CheckoutEntry checkoutEntry = new CheckoutEntry();
		checkoutEntry.setOrderId("" + orderReleaseID);
		checkoutEntry.setCartonBarcode(cartonBarcode);

		List<ItemEntry> itemEntries = new ArrayList();

		for (int quantity = 0; quantity < itembarcodes.size(); quantity++) {
			ItemEntry itemEntry = new ItemEntry();
			itemEntry.setId((long)itembarcodes.get(quantity).get("id"));
			itemEntry.setBarcode("" + itembarcodes.get(quantity).get("barcode"));
			itemEntries.add(itemEntry);
		}
		checkoutEntry.setItems((List<ItemEntry>) itemEntries);
		System.out.println("CheckOut Entry :- " + checkoutEntry.toString());
		return checkoutEntry;
	}

	/**
	 * Headers for the WMS Service
	 * 
	 * @return
	 */
	public static HashMap<String, String> getWMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		createOrderHeaders.put("Content-Type", "Application/xml");
		return createOrderHeaders;
	}

	/**
	 * Get SkuIds for a StyleIDs. If Multiple StyleIDs then pass as comma
	 * separated values
	 * 
	 * @author abhijit.pati
	 * @param styleId
	 * @return
	 * 
	 */
	public String[] getSkuIdsForStyles(String styleId) {
		List<String> skuIds = new ArrayList<String>();

		List<HashMap<String, Object>> list = DBUtilities.exSelectQuery(
				"select sku_id from mk_styles_options_skus_mapping where" + "  style_id in (" + styleId + ")",
				"MYNTRA");

		for (HashMap hashMap : list) {
			skuIds.add("" + hashMap.get("sku_id"));
		}
		return skuIds.toArray(new String[skuIds.size()]);
	}

	/**
	 * Mark Item InStock in WMS
	 * 
	 * @author abhijit.pati
	 * @param skuID
	 */
	public void markStyleInStock(String skuID) {
		String availableWareHouses = "'1','2','19','20','26','28','36','33'";
		HashMap<String, String> binWh = new HashMap<String, String>();
		binWh.put("1", "187");
		binWh.put("28", "273223");
		binWh.put("36", "583197");
		binWh.put("33", "578889");
		binWh.put("19", "152004");

		List<HashMap> lsitWareHouses = DBUtilities.exSelectQuery("select distinct warehouse_id from item where"
				+ "  sku_id=" + skuID + " and item_status not in ('STORED','NOT_RECEIVED') and warehouse_id in("
				+ availableWareHouses + ")", "WMS");

		for (HashMap singleWareHouse : lsitWareHouses) {
			String wareHouseID = "" + singleWareHouse.get("warehouse_id");
			String binID = "" + binWh.get(wareHouseID);
			HashMap listCount = (HashMap) DBUtilities.exSelectQueryForSingleRecord(
					"select count(1) from item where sku_id=" + skuID + " and warehouse_id=" + wareHouseID,
					"myntra_wms");
			Long count = (Long) listCount.get("count(1)");

			if (count < 1) {
				System.out.println("No Items are available in the Warehouse For WareHouse ID : " + wareHouseID
						+ " And SKUID : " + skuID);
				log.error("No Items are available in the Warehouse For WareHouse ID : " + wareHouseID + " And SKUID : "
						+ skuID);
			}

			System.out.println("Started Restocking inventory for SKUID : " + skuID);
			log.info("Started Restocking inventory for SKUID : " + skuID);
			DBUtilities.exUpdateQuery(
					"update item set item_status='STORED' ,order_id=null,reject_reason_code=NULL,reject_reason_description=NULL,carton_barcode=NULL,bin_id="
							+ binID + " where sku_id=" + skuID + " and warehouse_id=" + wareHouseID,
					"WMS");

			System.out.println("Updating the ATP Inventory for SKUID : " + skuID);
			log.info("Updating the ATP Inventory for SKUID : " + skuID);
			DBUtilities.exUpdateQuery(
					"update inventory set inventory_count=inventory_count+" + count + " where sku_id=" + skuID, "ATP");

			System.out.println("Updating the IMS Inventory for SKUID : " + skuID + " and WareHouse=" + wareHouseID);
			log.info("Updating the IMS Inventory for SKUID : " + skuID + " and WareHouse=" + wareHouseID);
			DBUtilities.exUpdateQuery("update wh_inventory set inventory_count=inventory_count+" + count
					+ " where sku_id=" + skuID + " and warehouse_id=" + wareHouseID, "IMS");

			System.out.println("Restocked inventory for WareHouse=" + wareHouseID + "\t Count=" + count);
			log.info("Restocked inventory for WareHouse=" + wareHouseID + "\t Count=" + count);
		}

	}

	/**
	 * Get Order Capture Release Data
	 * 
	 * @param releaseId
	 * @return {@link OrderCaptureReleaseEntry}
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public OrderCaptureReleaseEntry getCaptureOrderReleaseData(String releaseId)
			throws UnsupportedEncodingException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.WORMS_PATH.GETRELEASE,
				new String[] { "?q=portalOrderReleaseId.eq:" + releaseId }, SERVICE_TYPE.WORMS_SVC.toString(), HTTPMethods.GET,
				null, getWMSHeader());
		OrderCaptureReleaseResponse orderCaptureResponse = (OrderCaptureReleaseResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new OrderCaptureReleaseResponse());
		return orderCaptureResponse.getData().get(0);
	}

	/**
	 * Get Order Capture Release Data
	 * 
	 * @param releaseId
	 * @return {@link OrderCaptureReleaseEntry}
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public OrderReleaseResponse getCoreOrderRelease(String releaseId) throws UnsupportedEncodingException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.GETRELEASE,
				new String[] { "?q=portalOrderReleaseId.eq:" + releaseId }, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,
				null, getWMSHeader());
		OrderReleaseResponse orderReleaseResponse = (OrderReleaseResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new OrderReleaseResponse());
		return orderReleaseResponse;
	}

	public LocationTrackerResponse markOrderQADone(String[] itemBarcodes, String destinationBinBarcode,
			String destinationBinConfirmationCode) throws UnsupportedEncodingException, JAXBException {
		MovementEntry movementEntry = new MovementEntry();
		movementEntry.setCreatedBy("erpadmin");
		movementEntry.setDestinationBinBarcode(destinationBinBarcode);
		movementEntry.setDestinationBinConfirmationCode(destinationBinConfirmationCode);
		movementEntry.setItemBarcodes(Arrays.asList(itemBarcodes));
		String payload = APIUtilities.convertXMLObjectToString(movementEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.QADONE, null, SERVICE_TYPE.WMS_SVC.toString(),
				HTTPMethods.POST, payload, getWMSHeader());
		LocationTrackerResponse locationTrackingResponse = (LocationTrackerResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new LocationTrackerResponse());
		return locationTrackingResponse;
	}

	/**
	 * Search Items
	 * 
	 * @param queryParam
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public ItemResponse searchItemsByID(String queryParam) throws UnsupportedEncodingException, JAXBException {
		Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.GETITEM, new String[] { queryParam },
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null, getWMSHeader());
		ItemResponse itemSearchResponse = (ItemResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());
		return itemSearchResponse;
	}

	/**
	 * Validate Orders in Core Order Releases
	 * 
	 * @param orderReleaseID
	 * @return
	 */
	public boolean validateOrderInCoreOrderRelease(String orderReleaseID, int delaytoCheck) {
		log.info("Validate Order Status in core_order_release table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				String orderId = getOrderInCoreOrderRelease(orderReleaseID);
				if (!orderId.equalsIgnoreCase("false")) {
					validateStatus = true;
					break;
				} else {
					Thread.sleep(5000);
					validateStatus = false;
				}

				log.info("waiting for core order release " + i);
				System.out.println("waiting for core order release " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}

	/**
	 * Validate Orders in Core Order Releases
	 * 
	 * @param orderReleaseID
	 * @return
	 */
	public boolean validateOrderInCaptureOrderRelease(long orderReleaseID, int delaytoCheck) {
		log.info("Validate Order Status in capture_order_release table");
		boolean validateStatus = false;
		try {
			for (int i = 0; i < delaytoCheck; i++) {
				boolean orderId = getOrderInCaptureOrderRelease(orderReleaseID);
				if (orderId) {
					validateStatus = true;
					break;
				} else {
					End2EndHelper.sleep(2000);
					validateStatus = false;
				}

				log.info("waiting for core order release " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			validateStatus = false;
		}
		return validateStatus;
	}

	public boolean getOrderInCaptureOrderRelease(long orderReleaseID) {
		try {
			List list = DBUtilities.exSelectQuery(
					"select * from capture_order_release where portal_order_release_id=" + orderReleaseID, "wms");
			if (list == null) {
				return false;
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return true;
		} catch (Exception e) {
			log.error("Error in getOrderInCaptureOrderRelease :- " + e.getMessage());
			return false;
		}
	}

	public String getOrderInCoreOrderRelease(String orderReleaseID) {
		try {
			List list = DBUtilities.exSelectQuery(
					"select * from core_order_release where portal_order_release_id=" + orderReleaseID, "wms");
			if (list == null) {
				return "false";
			}
			HashMap<String, Object> hm = (HashMap<String, Object>) list.get(0);
			return "" + hm.get("order_id");
		} catch (Exception e) {
			log.error("Error in getOrderInCoreOrderRelease :- " + e.getMessage());
			System.out.println("Error in getOrderInCoreOrderRelease :- " + e.getMessage());
			return "false";
		}
	}

	/**
	 * Process Order in WMS till PK Status
	 *
	 * @param orderReleaseID
	 * @param warehouseID
	 * @throws JAXBException
	 * @throws SQLException
	 * @throws InterruptedException 
	 * @throws UnsupportedEncodingException
	 */
	public void processOrderInWMS(String orderReleaseID, String warehouseID)
			throws JAXBException, SQLException, IOException, InterruptedException, ManagerException {
		WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		if (omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "PK", 1)
				|| omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "SH", 1)
				|| omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL", 1)) {
			return;
		}
		// Push Order to Order wave for picking
		wmsServiceHelper.pushOrderToWave(orderReleaseID);
		// End2EndHelper.sleep(20000L);
		if (!wmsServiceHelper.validateOrderInCoreOrderRelease(orderReleaseID, 8)) {
			SlackMessenger.send("scm_e2e_order_sanity", "Order not pushed to wave, aborting", 2);
			creteRelaseInCore_order_release(orderReleaseID.toString());
		}
		List<Map<String, Object>> orderLine = DBUtilities.exSelectQuery("select sku_id, quantity from order_line where order_release_id_fk = "+orderReleaseID,"oms");
		for (Map<String, Object> line: orderLine){
			insertItem(line.get("sku_id").toString(),warehouseID,Integer.parseInt(line.get("quantity").toString()));
		}

		// Item CheckOut in WMS
		String[] itemBarcodes = wmsServiceHelper.orderItemsCheckout(orderReleaseID, warehouseID);
		SlackMessenger.send("scm_e2e_order_sanity", "Order checkout completed");
		End2EndHelper.sleep(10000);
		
		//Consolidatin
		if(itemBarcodes.length > 1){
			for(String itemId : itemBarcodes){
				prepareAndConsolidate(itemId, warehouseID);
			}
		}else{
			setVirtualBin(itemBarcodes[0], warehouseID);
		}
		
		// Mark Item QA PASS
		wmsServiceHelper.markItemQADONE(orderReleaseID);
		SlackMessenger.send("scm_e2e_order_sanity", "Order QA complete");
		System.out.println("Mark Item QA PASS Done");
		
		// RTS SCAN in OMS
		OrderCaptureReleaseResponse res = wmsServiceHelper.markReleasePacked(orderReleaseID);
		if (res.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)) {
			SlackMessenger.send("scm_e2e_order_sanity", "Order RTS scan complete");
		} else {
			SlackMessenger.send("scm_e2e_order_sanity", "Unable to mark release Packed(RTS failed)", 1);
			Assert.fail("Unable to mark release - "+orderReleaseID+" Packed(RTS failed)");
		}

		// Check the RTS SCAN ERROR AND Check for the Order Status in OMS
		assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "PK", waitTime),
				" Checking the OrderStatus for release id " +orderReleaseID +" moved to PK");
		// Check the Orders are flowing to LMS system After PK Status
		assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+orderReleaseID, EnumSCM.PACKED, 15),
				"Order Status is not PK in LMS for releaseid-"+orderReleaseID);
		SlackMessenger.send("scm_e2e_order_sanity", "Order marked packed and Order Flowed to WMS");	
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
	
	public void creteRelaseInCore_order_release(String Order_release_id) throws InterruptedException {
		//Order_release_id="2146076484";

		String querycapture = "select * from capture_order_release where portal_order_release_id = " + Order_release_id + " ;";
		String updatecapture_order_release = "update capture_order_release set order_release_status='PUSHED_TO_PICK' where portal_order_release_id=" + Order_release_id;
		DBUtilities.exUpdateQuery(updatecapture_order_release, "myntra_wms");
		Map map = new HashMap();
		List<HashMap> list = new ArrayList<HashMap>();
		map = DBUtilities.exSelectQueryForSingleRecord(querycapture, "myntra_wms");
		String portal_order_release_id = Order_release_id;
		String capture_order_release_id = ""+map.get("id");
		String order_id = ""+map.get("order_id");
		String warehouse_id = ""+map.get("warehouse_id");
		String is_gift = ""+map.get("is_gift");
		String is_express = ""+map.get("is_express");
		String dispatch_warehouse_id= ""+map.get("dispatch_warehouse_id");
		String is_consolidatable=""+map.get("is_consolidatable");
		String inventory_warehouse_start_time=""+map.get("inventory_warehouse_start_time");
		String inventory_warehouse_end_time=""+map.get("inventory_warehouse_end_time");
		String dispatch_warehouse_start_time=""+map.get("dispatch_warehouse_start_time");
		String dispatch_warehouse_end_time=""+map.get("dispatch_warehouse_end_time");
		String customer_id=""+map.get("customer_id");
		String address_id=""+map.get("address_id");

		
		/*String description=null;//=map.get("description").toString().isEmpty(); 
		if (map.get("description").toString().equals("NULL"))
		{
			 description="NORMAL";
		}*/
		String is_single = ""+map.get("is_single");
		String is_special = ""+map.get("is_special");
		String pick_type = ""+map.get("pick_type");
		String description = ""+map.get("description");
		String dispatch_cutoff = ""+map.get("dispatch_warehouse_end_time");

		Map<String,Object> db = DBUtilities.exSelectQueryForSingleRecord("select id from core_order_release where portal_order_release_id = "+portal_order_release_id, "wms");
		if (db == null) {

			String query = "INSERT INTO `core_order_release` ( `portal_order_release_id`, `order_id`, `priority`, `static_priority`, `calculated_priority`, `order_date`, `order_release_status`, `warehouse_id`, `dispatch_warehouse_id`, `consolidation_start_time`, `consolidation_complete_time`, `cutoff_time`, `created_by`, `created_on`, `last_modified_on`, `is_on_hold`, `version`, `is_gift`, `is_express`, `is_threshold_breached`, `description`, `is_single`, `pushed_to_pick_time`, `is_special`, `store_id`, `pick_type`, `wave_cycle_count`, `order_allocation_type`, `seller_id`, `customer_id`, `address_id`, `is_consolidatable`, `dispatch_cutoff`) VALUES ("
					+ portal_order_release_id + "," + order_id + ", 0, 395163520, 1, '2016-10-18 14:26:13', 'NEW',"
					+ warehouse_id +"," +dispatch_warehouse_id
					+ ", NULL, NULL, now(), 'erpMessageQueue', now(), now(), 0, 0,"
					+ is_gift + "," + is_express + ", 1,'" + description + "'," + is_single + ", now(), "
					+ is_special + ", 1, '" + pick_type + "', 1, 'MYNTRA',NULL,'"+ customer_id +"'," + address_id +", " + is_consolidatable + ", '" + dispatch_cutoff + "')";

			DBUtilities.exUpdateQuery(query, "myntra_wms");
			String query2 = "select * from core_order_release where portal_order_release_id= " + portal_order_release_id;
			Map hm = new HashMap();
			hm = DBUtilities.exSelectQueryForSingleRecord(query2, "myntra_wms");
			String order_release_id = hm.get("id").toString();

			String querycaptureline = "select * from capture_order_release_line  where order_release_id = " + capture_order_release_id;
			list = DBUtilities.exSelectQuery(querycaptureline, "myntra_wms");
			//map2=DBUtilities.exSelectQueryForSingleRecord(querycaptureline, "myntra_wms");
			int numberOfLine = list.size();
			for (int i = 0; i < numberOfLine; i++) {
				String sku_id = list.get(i).get("sku_id").toString();
				String quantity = list.get(i).get("quantity").toString();
				String seller_id = list.get(i).get("seller_id").toString();
				String supply_type = list.get(i).get("supply_type").toString();
				String query3 = "INSERT INTO `core_order_release_line` (`id`, `order_release_id`, `sku_id`, `quantity`, `picked_qty`, `created_by`, `created_on`, `last_modified_on`, `is_stale`, `version`, `is_fragile`, `consolidated_qty`, `line_status`, `seller_id`, `supply_type`)VALUES"
						+ "(null, " + order_release_id + "," + sku_id + "," + quantity + " , 0, 'erpMessageQueue', now(), now(), 0, 0, 0, 0, NULL," + seller_id + ", '" + supply_type + "');";
				DBUtilities.exUpdateQuery(query3, "myntra_wms");

			}

		}
	}
	/*@Test(enabled=true)
    public void runpushOrdertoWaveInsert() throws InterruptedException
    { 
        WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
        wmsServiceHelper.creteRelaseInCore_order_release("2146077643");
    }
	*/

	public synchronized void insertItem(String skuId, String warehosueId, int qty){
		List<Map<String, Object>> getItemCount = DBUtilities.exSelectQuery("select * from item where item_status = 'STORED' and quality ='Q1' and warehouse_id = "+warehosueId+" and sku_id = "+skuId,"wms");
		if (getItemCount !=null && getItemCount.size()>=qty) return;
		else {
			HashMap<String, String> binEntry = new HashMap<>();
			binEntry.put("1", "403");
			binEntry.put("19", "151924");
			binEntry.put("28", "271760");
			binEntry.put("36", "582762");
			
			//Get buyer_id
			long buyerId = (long) DBUtilities.exSelectQueryForSingleRecord("select id from vendor where is_buyer = 1", "myntra_vms")
					.get("id");
			
			long poId = 313L;
			String agreementType = getAgreementType(poId);
			
			for (int i =0; i<=qty;i++) {
				
				long id = getMaxItemId()+1;
				
				DBUtilities.exUpdateQuery("INSERT INTO `item` (id, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, " +
						"`comments`, `order_id`, `bin_id`) VALUES ("+id+", " + id + ", " + skuId + ", 'Q1', 'STORED', " + warehosueId + ", 1, "+ poId +", 'OPST050911-09', 1, 1, 'LOTVHGA-01', 'Automatio item', NULL, " +
						"" + binEntry.get("" + warehosueId) + ")", "wms");
				
				String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
						+ "VALUES(" + id +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, '"+ agreementType +"', " + buyerId + ")";
				DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");

			}
		}
	}
	
	public String getAgreementType(long poId){
		long piId = (long) DBUtilities.exSelectQueryForSingleRecord("select pi_id from core_pos where id = " + poId, "myntra_wms")
				.get("pi_id");
		return (String) DBUtilities.exSelectQueryForSingleRecord("select pi_type from core_pis where id = " + piId, "myntra_wms")
				.get("pi_type");
	}
	
	
	/**
	 * Inset Items
	 * @param skus
	 */
	public synchronized void insertItem(String skus){
		String skuID = skus.split(":")[0];
		String[] temp;
		String delimiter = ":";
		temp = skus.split(delimiter);
		long skuId = Long.parseLong(temp[0]);
		long id = Integer.parseInt(temp[1]);
		String[] avilableWHs = temp[2].split(",");
		int noofsku = 1000;
		// delete previous entries
		long idLimit = id + noofsku + 1000;
		boolean canInsert = false;
		for(String warehouse:avilableWHs){
			Map<String, Object> item = DBUtilities.exSelectQueryForSingleRecord("select count(*) from item where item_status = 'STORED' and  sku_id = " + skuID+" and warehouse_id = "+warehouse, "wms");
			if ((Long) item.get("count(*)") < 100) {
				canInsert = true;
			}
		}
		if (canInsert) {
			HashMap<String, String> binEntry = new HashMap<>();
			binEntry.put("1", "403");
			binEntry.put("19", "151924");
			binEntry.put("28", "271760");
			binEntry.put("36", "582762");
			String queryDel = "Delete from item where sku_id=" + skuId + " or id between " + id + " and " + idLimit + ";";
			DBUtilities.exUpdateQuery(queryDel, "wms");

			long id2 = id;
			long poId = 313L;
			String agreementType = getAgreementType(poId);
			
			for (int i = 0; i < noofsku - 1; i++) {
				for (String string : avilableWHs) {
					String query = "insert into item (id,barcode,sku_id,quality,item_status,warehouse_id,enabled,po_id,po_barcode,po_sku_id,lot_id,lot_barcode,comments,bin_id) values("
							+ id2 + ", '" + id2 + "'," + skuId + ",'Q1','STORED'," + string
							+ ",1,313,'OPST050911-09',1,1,'LOTVHGA-01','Automatio item', " + binEntry.get(string) + ");";
					DBUtilities.exUpdateQuery(query, "wms");
					
					String insertItemQuery = "INSERT INTO `item_info` (`item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)"
							+ "VALUES(" + id +", 'NEW', NULL, NULL, now(), 'erpMessageQueue', now(), 0, NULL, NULL, '"+ agreementType +"', " + 2297 + ")";
					DBUtilities.exUpdateQuery(insertItemQuery, "myntra_wms");
					
					id2++;
				}
			}

		}

	}
	
	/**
	 * @param orderReleaseId
	 * @param status
	 * @return
	 */
	public Boolean verifyDataInWorms(String orderReleaseId,String status){
    	Boolean isWormsStatusCorrect = false;
    	
    	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    	LinkedHashMap<String,Object> captureOrderReleaseData =   (LinkedHashMap<String, Object>) getCaptureOrderReleaseEntry(orderReleaseId);
    	String orderStatusInWorms = captureOrderReleaseData.get("order_release_status").toString();
    	if(status.equalsIgnoreCase(orderStatusInWorms)){
    		isWormsStatusCorrect = true;
    	}
    	
    	return isWormsStatusCorrect;
    }
	
	// capture_order_release entry
	 	public  Map getCaptureOrderReleaseEntry(String orderReleaseId) {
	         Map captureOrderReleaseEntry = new HashMap();
	         String query = "select * from capture_order_release where `portal_order_release_id`=" + orderReleaseId;
	         captureOrderReleaseEntry = DBUtilities.exSelectQueryForSingleRecord(query, "myntra_wms");
	         return captureOrderReleaseEntry;
	     }
	 	


	public long getMaxItemId(){
		Map<String, Object> getId = DBUtilities.exSelectQueryForSingleRecord("select max(id) from item","wms");
		Long maxId = (Long)getId.get("max(id)");
		if(maxId==null){
			maxId = 0L;
		}
		return maxId;
	}
	public String[] processOrderInWmsTillBinCreation(String orderReleaseID, String warehouseID) throws InterruptedException, ManagerException, SQLException, IOException, JAXBException{
		WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
		if (omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "PK", 1)
				|| omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "SH", 1)
				|| omsServiceHelper.validateReleaseStatusInOMS(orderReleaseID, "DL", 1)) {
			return null;
		}
		// Push Order to Order wave for picking
		wmsServiceHelper.pushOrderToWave(orderReleaseID);
		// End2EndHelper.sleep(20000L);
		if (!wmsServiceHelper.validateOrderInCoreOrderRelease(orderReleaseID, 8)) {
			SlackMessenger.send("scm_e2e_order_sanity", "Order not pushed to wave, aborting", 2);
			creteRelaseInCore_order_release(orderReleaseID.toString());
		}
		List<Map<String, Object>> orderLine = DBUtilities.exSelectQuery("select sku_id, quantity from order_line where order_release_id_fk = "+orderReleaseID,"oms");
		for (Map<String, Object> line: orderLine){
			insertItem(line.get("sku_id").toString(),warehouseID,Integer.parseInt(line.get("quantity").toString()));
		}

		// Item CheckOut in WMS
		String[] itemBarcodes = wmsServiceHelper.orderItemsCheckout(orderReleaseID, warehouseID);
		SlackMessenger.send("scm_e2e_order_sanity", "Order checkout completed");
		End2EndHelper.sleep(10000);
		
		//Consolidatin
		if(itemBarcodes.length > 1){
			for(String itemId : itemBarcodes){
				prepareAndConsolidate(itemId, warehouseID);
			}
		}else{
			setVirtualBin(itemBarcodes[0], warehouseID);
		}
		return itemBarcodes;
	}
	
	
	   /**
     * Refresh Worms Application Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
	 * @throws ManagerException 
     */
    public Svc refreshWormsApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.WORMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.WORMS_SVC.toString(), HTTPMethods.GET, null, getWMSHeader());
        return service;
    }

    /**
     * Refresh WMS Application Properties
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     * @throws ManagerException 
     */
    public Svc refreshWMSApplicationPropertyCache() throws UnsupportedEncodingException, JAXBException, ManagerException {
        Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.REFRESHAPPLICATIONPROPERTIES, null, SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET, null,getWMSHeader());
        return service;
    }
    
    
    /**
     * This function is to get item entry from WMS DB
     * @param key
     * @param value
     * @return
     * @throws ManagerException
     */
    public List getItemEntryInWMS(String key,String value) throws ManagerException{
        List resultSet = null;
        try {
            resultSet = DBUtilities.exSelectQuery("select * from item where "+key+" =" + value + ";", "myntra_wms");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Release Entry : "+ e.getLocalizedMessage());
            ExceptionHandler.fail("Unable to get item entry");
        }
        return resultSet;
    }


	
}