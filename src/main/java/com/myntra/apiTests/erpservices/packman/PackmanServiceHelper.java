package com.myntra.apiTests.erpservices.packman;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.wms.WMSConstants;
import com.myntra.apiTests.erpservices.wms.WMSOrderFlowHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.packman.entry.SellerPacketEntry;
import com.myntra.packman.entry.SellerPacketItemEntry;
import com.myntra.packman.entry.request.SellerPacketItemUpdateEntry;
import com.myntra.packman.entry.request.SellerPacketUpdateEntry;
import com.myntra.packman.response.SellerPacketItemResponse;
import com.myntra.packman.response.SellerPacketResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

public class PackmanServiceHelper {
	SoftAssert sft=null;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
	PackmanDBHelper packmanDBHelper=new PackmanDBHelper();
	WMSOrderFlowHelper wmsOrderFlowHelper = new WMSOrderFlowHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	private Logger log = Logger.getLogger(PackmanServiceHelper.class);
	SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
	public SellerPacketResponse createSellerPacketPackman(int locationId, Long storeId, OrderEntry orderEntry, String[] itemBarcode,Long sellerId) throws JAXBException, UnsupportedEncodingException {
		int count=0;
		SellerPacketEntry sellerPacketEntry=new SellerPacketEntry();
		sellerPacketEntry.setLocationId(locationId);
		sellerPacketEntry.setStoreId(storeId);
		Date dateobj = new Date();
		List<SellerPacketItemEntry>  listSellerPacketItemEntry=new ArrayList<SellerPacketItemEntry>();
		sellerPacketEntry.setExpectedPackByTime(dateobj);
		List<OrderReleaseEntry> orderReleaseEntry=orderEntry.getOrderReleases();
		for(OrderReleaseEntry singleRelease:orderReleaseEntry){
			for(int i=0;i<itemBarcode.length;i++){
				SellerPacketItemEntry sellerPacketItemEntry=new SellerPacketItemEntry();
				sellerPacketItemEntry.setCreatedBy("Oms-Qa-Automation");
				sellerPacketItemEntry.setCreatedOn(dateobj);
				sellerPacketItemEntry.setLastModifiedOn(dateobj);
				sellerPacketItemEntry.setItemBarcode(itemBarcode[count++]);
				sellerPacketItemEntry.setPortalOrderReleaseId(singleRelease.getId());
				OrderLineEntry orderLineEntry=singleRelease.getOrderLines().get(0);
				sellerPacketItemEntry.setUnitPrice(orderLineEntry.getUnitPrice());
				sellerPacketItemEntry.setSkuId(orderLineEntry.getSkuId());
				listSellerPacketItemEntry.add(sellerPacketItemEntry);
				sellerPacketItemEntry.setSize("L");
				sellerPacketItemEntry.setSellerId(sellerId);
			}
		}
		sellerPacketEntry.setPacketItems(listSellerPacketItemEntry);

		String payload = APIUtilities.convertXMLObjectToString(sellerPacketEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.CREATE_SELLER_PACKET, null,SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.POST, payload, getPackmanServiceHeaders());
		SellerPacketResponse sellerPacketResponse = (SellerPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketResponse());
		return sellerPacketResponse;
	}
	public SellerPacketResponse getSellerPacketByPacketId(Long sellerPacketId) throws JAXBException, UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.CREATE_SELLER_PACKET, new String[] {sellerPacketId.toString()},SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.GET, null, getPackmanServiceHeaders());
		SellerPacketResponse sellerPacketResponse = (SellerPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketResponse());
		return sellerPacketResponse;
	}
	public SellerPacketResponse getSellerPacketByItemBarcode(String item_barcode) throws JAXBException, UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.GET_SELLER_PACKET_BY_ITEMBARCODE, new String[] {item_barcode.toString()},SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.GET, null, getPackmanServiceHeaders());
		SellerPacketResponse sellerPacketResponse = (SellerPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketResponse());
		return sellerPacketResponse;
	}
	public SellerPacketItemResponse markItemQcPass(Long sellerPacketId,String[] item_barcode) throws JAXBException, UnsupportedEncodingException {
		SellerPacketUpdateEntry sellerPacketUpdateEntry=new SellerPacketUpdateEntry();
		sellerPacketUpdateEntry.setSellerPacketId(sellerPacketId);
		List<SellerPacketItemUpdateEntry>  listSellerPacketItemEntry=new ArrayList<SellerPacketItemUpdateEntry>();
		for(int i=0;i<item_barcode.length;i++){
			SellerPacketItemUpdateEntry sellerPacketItemUpdateEntry=new SellerPacketItemUpdateEntry();
			sellerPacketItemUpdateEntry.setItemBarcode(item_barcode[i]);
			sellerPacketItemUpdateEntry.setQcPassCode(EnumSCM.QC_PASS_CODE);
			sellerPacketItemUpdateEntry.setQcDeskCode(String.valueOf(EnumSCM.QC_DESK_CODE));
			sellerPacketItemUpdateEntry.setUserLogin("Oms-Qa-Automation");
			listSellerPacketItemEntry.add(sellerPacketItemUpdateEntry);
		}
		sellerPacketUpdateEntry.setPacketItems(listSellerPacketItemEntry);
		String payload = APIUtilities.convertXMLObjectToString(sellerPacketUpdateEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.MARK_ITEM_QC_PASS, null,SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.PUT, payload, getPackmanServiceHeaders());
		SellerPacketItemResponse sellerPacketResponse = (SellerPacketItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketItemResponse());
		return sellerPacketResponse;
	}
	
	public SellerPacketItemResponse martItemQcFail(Long sellerPacketId, String[] itemBarcode, int warehouseId) throws JAXBException, UnsupportedEncodingException{
		SellerPacketUpdateEntry sellerPacketUpdateEntry=new SellerPacketUpdateEntry();
		sellerPacketUpdateEntry.setSellerPacketId(sellerPacketId);
		List<SellerPacketItemUpdateEntry>  listSellerPacketItemEntry=new ArrayList<SellerPacketItemUpdateEntry>();
		for(int i=0;i<itemBarcode.length;i++){
			SellerPacketItemUpdateEntry sellerPacketItemUpdateEntry=new SellerPacketItemUpdateEntry();
			sellerPacketItemUpdateEntry.setItemBarcode(itemBarcode[i]);
			sellerPacketItemUpdateEntry.setQcPassCode(EnumSCM.QC_FAIL_CODE);
			sellerPacketItemUpdateEntry.setQcDeskCode(String.valueOf(EnumSCM.QC_DESK_CODE));
			sellerPacketItemUpdateEntry.setUserLogin("Oms-Qa-Automation");
			sellerPacketItemUpdateEntry.setWarehouseId(warehouseId);
			sellerPacketItemUpdateEntry.setQcRejectQuality(EnumSCM.QC_QUALITY_Q2);
			sellerPacketItemUpdateEntry.setQcRejectReason(EnumSCM.QC_REJECT_REASON);
			sellerPacketItemUpdateEntry.setQcRejectReasonDescription(EnumSCM.QC_REJECT_DESCRIPTION);
			listSellerPacketItemEntry.add(sellerPacketItemUpdateEntry);
		}
		sellerPacketUpdateEntry.setPacketItems(listSellerPacketItemEntry);
		String payload = APIUtilities.convertXMLObjectToString(sellerPacketUpdateEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.MARK_ITEM_QC_FAIL, null,SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.PUT, payload, getPackmanServiceHeaders());
		SellerPacketItemResponse sellerPacketResponse = (SellerPacketItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketItemResponse());
		return sellerPacketResponse;
	}
	
	public SellerPacketItemResponse markItemPremiumPacked(String item_barcode) throws JAXBException, UnsupportedEncodingException {
		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.MARK_ITEM_PREMIUM_PACKED, new String[] {item_barcode.toString()},SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.PUT, null, getPackmanServiceHeaders());
		SellerPacketItemResponse sellerPacketItemResponse = (SellerPacketItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketItemResponse());
		return sellerPacketItemResponse;
	}
	public SellerPacketResponse markItemReadyToDispatch(Long sellerPacketId) throws JAXBException, UnsupportedEncodingException {
		String payload = APIUtilities.convertXMLObjectToString(getSellerPacketUpdateEntry(sellerPacketId));
		
		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.MARK_READY_TO_DISPATCH, new String[] {sellerPacketId.toString()},SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.PUT, payload, getPackmanServiceHeaders());
		SellerPacketResponse sellerPacketResponse = (SellerPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketResponse());
		return sellerPacketResponse;
	}
	public SellerPacketResponse cancelItemsBySeller(Long sellerPacketId,String[] item_barcode,Long qcDeskCode, Long cancellationReasonId) throws JAXBException, UnsupportedEncodingException {
		SellerPacketUpdateEntry sellerPacketUpdateEntry=new SellerPacketUpdateEntry();
		sellerPacketUpdateEntry.setSellerPacketId(sellerPacketId);
		sellerPacketUpdateEntry.setCancellationReasonId(cancellationReasonId);
		sellerPacketUpdateEntry.setQcDeskCode(String.valueOf(qcDeskCode));
		sellerPacketUpdateEntry.setUserLogin("Oms-Qa-Automation");
		List<SellerPacketItemUpdateEntry>  listSellerPacketItemEntry=new ArrayList<SellerPacketItemUpdateEntry>();
		for(int i=0;i<item_barcode.length;i++){
			SellerPacketItemUpdateEntry sellerPacketItemUpdateEntry=new SellerPacketItemUpdateEntry();
			sellerPacketItemUpdateEntry.setItemBarcode(item_barcode[i]);
			listSellerPacketItemEntry.add(sellerPacketItemUpdateEntry);
		}
		sellerPacketUpdateEntry.setPacketItems(listSellerPacketItemEntry);
		String payload = APIUtilities.convertXMLObjectToString(sellerPacketUpdateEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.PACKMAN_PATH.CANCEL_ITEMS_BY_SELLER, null,SERVICE_TYPE.PACKMAN_SVC.toString(), HTTPMethods.PUT, payload, getPackmanServiceHeaders());
		SellerPacketResponse sellerPacketResponse = (SellerPacketResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new SellerPacketResponse());
		return sellerPacketResponse;
	}
	public static HashMap<String, String> getPackmanServiceHeaders() {
		HashMap<String, String> sellerPacketHeader = new HashMap<String, String>();
		sellerPacketHeader.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		sellerPacketHeader.put("Content-Type", "Application/xml");
		sellerPacketHeader.put("Accept", "Application/xml");
		return sellerPacketHeader;
	}
	public static HashMap<String, String> getPackmanHeaderJson() {
		HashMap<String, String> sellerPacketHeader = new HashMap<String, String>();
		sellerPacketHeader.put("Authorization", "Basic bW9iaWxlfm1vYmlsZTptb2JpbGU");
		sellerPacketHeader.put("Content-Type", "application/json");
		sellerPacketHeader.put("Accept", "application/json");
		return sellerPacketHeader;
	}
	public void validateSellerPacetRespoonse(SellerPacketResponse sellerPacketResponse,OrderEntry orderEntry,Long storeId,boolean giftWrap) {
		sft = new SoftAssert();
		sft.assertEquals(sellerPacketResponse.getStatus().getStatusType(), Type.SUCCESS,"Issue in Seller Packet API");
		SellerPacketEntry sellerPacketItems=sellerPacketResponse.getData().get(0);

		List<SellerPacketItemEntry> sellerPacketList=sellerPacketItems.getPacketItems();
		for(int i=0;i<sellerPacketList.size();i++){
			sft.assertNotNull(sellerPacketList.get(i).getItemBarcode(),"Item Barcode can never be null");
			sft.assertEquals(sellerPacketList.get(i).getStatus(),"A","Packet Item Status does not matched not matched");
		}
		sft.assertEquals(sellerPacketItems.getStoreId(),storeId,"Store Id does not matched");
		sft.assertEquals(sellerPacketItems.getStatus(),"Q","Status does not matched not matched");
		sft.assertAll();
	}
	public OrderEntry getOrderEntry(String login,String password,String[] skuId,String pincode,boolean giftWrap,Long vectorSellerID)throws Exception{
		String orderID =  end2EndHelper.createOrder(login, password, pincode, skuId, "", false, false, giftWrap,"",false);
		omsServiceHelper.checkReleaseStatusForOrderWithHack(orderID,"WP");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		return orderEntry;
	}
	public SellerPacketResponse getDataForCreateSellerPacket(OrderEntry orderEntry, Long storeId, int wareHouseId, Long sellerId) throws ManagerException, InterruptedException, SQLException, IOException, JAXBException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, XMLStreamException, JSONException, ParseException{
		ArrayList<String>itemBarcodesList=null;
		itemBarcodesList=processOrderInWMSandGetBarcodes(String.valueOf(orderEntry.getId()));
		String itemBarcodes[]=itemBarcodesList.toArray(new String[itemBarcodesList.size()]);
		return createSellerPacket(orderEntry,storeId,wareHouseId,itemBarcodes,sellerId);
	}
	public Object createSellerPacketInDesiredState(SellerPacketResponse sellerPacketResponse, String status) throws ManagerException, InterruptedException, SQLException, IOException, JAXBException{
		String[] itemBarcodes=null;
		List<SellerPacketItemEntry> sellerPacketItemEntry=sellerPacketResponse.getData().get(0).getPacketItems();
		itemBarcodes=getItemBarcode(sellerPacketItemEntry);
		SellerPacketItemResponse markItemQcPass=null;
		SellerPacketResponse markItemRtoD=null;
		SellerPacketResponse cancelItemBySeller=null;
		Long sellerPacketId = sellerPacketResponse.getData().get(0).getId();
		if(status.equals(EnumSCM.SELLER_PACKET_ITEM_STATUS_QD)||status.equalsIgnoreCase(EnumSCM.SELLER_PACKET_ITEM_STATUS_PK)
				||status.equalsIgnoreCase(EnumSCM.SELLER_PACKET_ITEM_STATUS_PP)||status.equalsIgnoreCase(EnumSCM.SELLER_PACKET_ITEM_STATUS_IC)){
			if(itemBarcodes.length!=0){
				markItemQcPass=markItemQcPass(sellerPacketId, itemBarcodes);
				if(status.equals(EnumSCM.SELLER_PACKET_ITEM_STATUS_QD)){
					return markItemQcPass;
				}
			}
		}
		if(status.equalsIgnoreCase(EnumSCM.SELLER_PACKET_ITEM_STATUS_PP)){
			String itemBarcode=sellerPacketItemEntry.get(0).getItemBarcode();
			SellerPacketItemResponse sellerPacketItemResponseAfterPremiumPacked = markItemPremiumPacked(itemBarcode);
			return sellerPacketItemResponseAfterPremiumPacked.getData().get(0);
		}
		if(status.equalsIgnoreCase(EnumSCM.SELLER_PACKET_ITEM_STATUS_PK)){
			if(markItemQcPass.getStatus().getStatusType().equals(Type.SUCCESS)){
				markItemRtoD=markItemReadyToDispatch(sellerPacketId);
				if(markItemRtoD.getStatus().getStatusType().equals(Type.SUCCESS)){
					log.debug("Release marked to shipped");
				}else{
					log.debug("Failed process of Ready to Deliver for sellerPacketId="+sellerPacketId);
				}
			}
		}
		if(status.equals(EnumSCM.SELLER_PACKET_ITEM_STATUS_IC)){
			cancelItemBySeller= cancelItemsBySeller(sellerPacketId, itemBarcodes, EnumSCM.QC_PASS_CODE, EnumSCM.QC_DESK_CODE);
			return cancelItemBySeller;
		}
		return markItemRtoD;
	}
	public SellerPacketResponse createSellerPacket(OrderEntry orderEntry, Long storeId, int wareHouseId, String[] itemBarcodes,Long sellerId) throws UnsupportedEncodingException, JAXBException{
		SellerPacketResponse sellerPacketResponse=createSellerPacketPackman(wareHouseId,storeId,orderEntry,itemBarcodes,sellerId);
		return sellerPacketResponse;
	}
	public void validateDbResponse(List<SellerPacketItemEntry> sellerPacketItemEntry,
			List<Map<String, Object>> sellerPacketDbData) {
		sft = new SoftAssert();
		for(int i=0;i<sellerPacketItemEntry.size();i++){
			sft.assertEquals(sellerPacketItemEntry.get(i).getPortalOrderReleaseId(), sellerPacketDbData.get(i).get("portal_order_release_id"),"Portal Order release id does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getItemBarcode(), sellerPacketDbData.get(i).get("item_barcode"),"Item Barcode does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getStatus(), sellerPacketDbData.get(i).get("status_code"),"Status code does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getSkuId(), sellerPacketDbData.get(i).get("sku_id"),"SKU id does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getStyleId(), sellerPacketDbData.get(i).get("style_id"),"Style id does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getShippingMethod(), sellerPacketDbData.get(i).get("shipping_method"),"Shipping method does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getPackagingType(), sellerPacketDbData.get(i).get("packaging_type"),"Packaging type does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getPackagingStatus(), sellerPacketDbData.get(i).get("packaging_status"),"Packaging status does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getHazmat(), sellerPacketDbData.get(i).get("is_hazmat"),"is_hazmat does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getJewellery(), sellerPacketDbData.get(i).get("is_jewellery"),"is_jewellery does not matched with DB");
			sft.assertEquals(sellerPacketItemEntry.get(i).getFragile(), sellerPacketDbData.get(i).get("is_fragile"),"is_fragile does not matched with DB");
			sft.assertAll();
		}
	}
	public String[] getItemBarcode(List<SellerPacketItemEntry> sellerPacketItemEntry) {
		String[] itemBarcode=new String[sellerPacketItemEntry.size()];
		for(int i=0;i<sellerPacketItemEntry.size();i++){
			itemBarcode[i]=sellerPacketItemEntry.get(i).getItemBarcode();
		}
		return itemBarcode;
	}
	public long getSellerPacketId(String releaseId){
		long sellerPacketId=packmanDBHelper.getSellerPacketByReleaseId(releaseId);
		return sellerPacketId;
	}
	public String[] getItemBarcodes(long sellerPacketId){
		List<Map<String,Object>> listSellerPacketItems=packmanDBHelper.getPacketItemDataBySellerPacket(sellerPacketId);
		String[] itemBarcode=new String[listSellerPacketItems.size()];
		for(int i=0;i<listSellerPacketItems.size();i++){
			Map<String,Object> sellerPacketItemMap=listSellerPacketItems.get(i);
			itemBarcode[i]=String.valueOf(sellerPacketItemMap.get("item_barcode"));
		}
		return itemBarcode;
	}
	public void markItemTillPack(String releaseId) throws ManagerException, InterruptedException {
		long sellerPacketId=getSellerPacketId(releaseId);
		String[] itemBarcodes=getItemBarcodes(sellerPacketId);
		try {
			Thread.sleep(5000);
			SellerPacketItemResponse sellerPacketResponseForQcPass=markItemQcPass(sellerPacketId, itemBarcodes);
			List<SellerPacketItemEntry> sellerPacketItemEntry =sellerPacketResponseForQcPass.getData();
			for(int i=0;i<sellerPacketItemEntry.size();i++){
				ExceptionHandler.handleEquals(EnumSCM.QD,sellerPacketItemEntry.get(i).getStatus(),"Seller packet is not in QD state");
			}
			Thread.sleep(5000);
			SellerPacketResponse sellerPacketResponseForMarkReadyToDispatch=markItemReadyToDispatch(sellerPacketId);
			assertReadyToDispatch(sellerPacketResponseForMarkReadyToDispatch);
		} catch (UnsupportedEncodingException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public void assertReadyToDispatch(SellerPacketResponse markReadyToDispatchResponse){
		sft = new SoftAssert();
		StatusResponse statusResponse=markReadyToDispatchResponse.getStatus();
		sft.assertEquals(statusResponse.getStatusType(), Type.SUCCESS,"Status type does not matched");
		List<SellerPacketItemEntry> sellerPacketItemEntry=markReadyToDispatchResponse.getData().get(0).getPacketItems();
		for(int i=0;i<sellerPacketItemEntry.size();i++){
			sft.assertEquals(sellerPacketItemEntry.get(i).getStatus(),EnumSCM.SELLER_PACKET_STATUS_PK,"Status does not matched. It should be PK");
		}
		sft.assertAll();
	}
	public ArrayList<String> processOrderInWMSandGetBarcodes(String orderId) throws UnsupportedEncodingException, JAXBException, InterruptedException, JSONException, XMLStreamException, ParseException{
		ArrayList<HashMap> items = null;
		HashMap<Integer, String> warehouseIdsAndPickTypes;
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
    	String storeOrderId = orderEntry.getStoreOrderId();
    	boolean orderCheckout = false;
    	HashMap<String, String> lmcSortingResponse = null;
		List<HashMap> orderLines = wmsOrderFlowHelper.getOrderDetails(storeOrderId);
    	warehouseIdsAndPickTypes = wmsOrderFlowHelper.getWarehouseIdsAndPickTypes(orderEntry.getOrderReleases().get(0).getOrderId().toString());
    	
    	// Push Order to Order wave for picking
    	if(!wmsOrderFlowHelper.isPushOrderToWaveApiHit(orderId)){
    		wmsOrderFlowHelper.pushOrderToWave(warehouseIdsAndPickTypes);
    	}
    	
    	if(!wmsServiceHelper.validateOrderInCoreOrderRelease(orderLines.get(0).get("order_release_id_fk").toString(), 8)){
    		log.info("Entry was not yet created in Core_order_release so inserting");
    		
    		for(HashMap orderLine : orderLines){
    			if(!wmsOrderFlowHelper.isOrderPushedToWMS(orderLine.get("order_release_id_fk").toString())){
    				wmsServiceHelper.creteRelaseInCore_order_release(orderLine.get("order_release_id_fk").toString());
    			}
    		}
    	}
    	ArrayList<String> al=new ArrayList<>();
    	//Item checkout and Consolidation
    	for(HashMap orderLine : orderLines){
    		if(!wmsOrderFlowHelper.isOrderCheckedOut(orderLine.get("order_release_id_fk").toString())){
    			items = (ArrayList<HashMap>) wmsOrderFlowHelper.insertItems(orderLine.get("sku_id").toString(), orderLine.get("source_wh_id").toString(), (int)orderLine.get("quantity"), WMSConstants.BUYER1, 313, 1);
        		wmsOrderFlowHelper.itemCheckout(items, orderLine.get("order_release_id_fk").toString());
        		orderCheckout = true;
    		}
    		
    		if(!orderCheckout){
    			items = wmsOrderFlowHelper.getItemsAssociatedWithOrder(orderLine.get("order_release_id_fk").toString());
    		}
    		
    		for(HashMap item : items){
    			al.add(item.get("barcode").toString());
    			if(!wmsOrderFlowHelper.isItemConsolidated(item.get("barcode").toString())){
    				lmcSortingResponse = wmsOrderFlowHelper.lmcSorting(item.get("barcode").toString());
        			Assert.assertEquals(lmcSortingResponse.get("statusMessage"), "Please keep item into bin/section for consolidation", lmcSortingResponse.get("statusMessage"));
        			wmsOrderFlowHelper.prepareAndConsolidate(item.get("barcode").toString(), orderLine.get("source_wh_id").toString());
    			}
    		}
    	}
		return al;
	}
	
	
	/**
	 * Get Seller packet update Entry
	 * @param sellerPacketId
	 * @return
	 */
	public SellerPacketUpdateEntry getSellerPacketUpdateEntry(Long sellerPacketId){
		List<Map<String,Object>> sellerPacketEntries = packmanDBHelper.getPacketItemDataBySellerPacket(sellerPacketId);
		SellerPacketUpdateEntry sellerPacketUpdateEntry = new SellerPacketUpdateEntry();
		List<SellerPacketItemUpdateEntry> sellerPacketItemUpdateEntries = new ArrayList<>();

		for(Map<String,Object> sellerPacketEntry:sellerPacketEntries){
			SellerPacketItemUpdateEntry sellerPacketItemUpdateEntry = new SellerPacketItemUpdateEntry();
			String status_code = ""+sellerPacketEntry.get("status_code");
			if(!status_code.equalsIgnoreCase(EnumSCM.IC)){
				String itemBarcode = ""+sellerPacketEntry.get("item_barcode");
				String portalOrderReleaseId = ""+sellerPacketEntry.get("portal_order_release_id");
				sellerPacketItemUpdateEntry.setItemBarcode(itemBarcode);
				sellerPacketItemUpdateEntry.setPortalOrderReleaseId(Long.parseLong(portalOrderReleaseId));
				sellerPacketItemUpdateEntries.add(sellerPacketItemUpdateEntry);
			}
			
		}
		
		sellerPacketUpdateEntry.setSellerPacketId(sellerPacketId);
		sellerPacketUpdateEntry.setPacketItems(sellerPacketItemUpdateEntries);
		return sellerPacketUpdateEntry;
	}
}
