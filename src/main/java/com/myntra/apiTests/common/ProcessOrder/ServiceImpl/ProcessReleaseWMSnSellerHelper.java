package com.myntra.apiTests.common.ProcessOrder.ServiceImpl;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntry;
import com.myntra.apiTests.common.entries.ReleaseDetailsEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.client.wms.response.VirtualPacketResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Shubham Gupta on 8/3/17.
 */
public class ProcessReleaseWMSnSellerHelper {

	private static Logger log = Logger.getLogger(ProcessReleaseWMSnSellerHelper.class);
	
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	SellerApiHelper sellerApiHelper = new SellerApiHelper();
	LMSHelper lmsHelper = new LMSHelper();

	public void processSmallSellerTillPK(ReleaseDetailsEntry releaseDetailsEntry)
			throws JAXBException, IOException, ManagerException {
		
		ExceptionHandler.handleEquals(wmsServiceHelper.markReleasePacked(releaseDetailsEntry.getReleaseId()).getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS, "RTS scan failed for PPMP seller");
		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PK.name(), 5), "Release Status is not PK in OMS");
		ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PACKED.name(), 10));
	}

	public void processMaduraTillPK(ReleaseDetailsEntry releaseDetailsEntry)
			throws JAXBException, IOException, ManagerException {
		
		List<OrderLineEntry> orderLineEntries = omsServiceHelper
				.getOrderLineEntries(releaseDetailsEntry.getReleaseId());
		ExceptionHandler
				.handleEquals(
						sellerApiHelper
								.updateTax(releaseDetailsEntry.getReleaseId(),
										new String[] {
												"" + orderLineEntries.get(0).getSkuId().toString() + ":100:5.25:VAT" })
								.getStatus().getStatusType().toString(),
						"SUCCESS", "Update Tax for seller got failed");
		ExceptionHandler.handleEquals(sellerApiHelper
				.readyToDispatch(releaseDetailsEntry.getReleaseId(),
						new String[] { orderLineEntries.get(0).getSkuId().toString() + ":"
								+ orderLineEntries.get(0).getQuantity().toString() + "" })
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTS failed for Madura seller");
		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PK.name(), 5), "Release Status is not PK in OMS");
		ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PACKED.name(), 10));
	}

	public void processMaduraTillPKMock(ReleaseDetailsEntry releaseDetailsEntry)
			throws JAXBException, IOException, ManagerException {
		
		List<OrderLineEntry> orderLineEntries = omsServiceHelper
				.getOrderLineEntries(releaseDetailsEntry.getReleaseId());
		ExceptionHandler
				.handleEquals(
						sellerApiHelper
								.updateTax(releaseDetailsEntry.getReleaseId(),
										new String[] {
												"" + orderLineEntries.get(0).getSkuId().toString() + ":100:5.25:VAT" })
								.getStatus().getStatusType().toString(),
						"SUCCESS", "Update Tax for seller got failed");
		ExceptionHandler.handleEquals(sellerApiHelper
				.readyToDispatch(releaseDetailsEntry.getReleaseId(),
						new String[] { orderLineEntries.get(0).getSkuId().toString() + ":"
								+ orderLineEntries.get(0).getQuantity().toString() + "" })
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTS failed for Madura seller");
		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PK.name(), 5), "Release Status is not PK in OMS");
		ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PACKED.name(), 10));
	}

	public void processForJITItems(ReleaseDetailsEntry releaseDetailsEntry) {
		log.info("For JIT Items Need to Associate Item From Partener Portal");
	}

	public void processForJITItemsMock(ReleaseDetailsEntry releaseDetailsEntry) {
		log.info("For JIT Items Need to Associate Item From Partener Portal");
	}

	public void processForOnHandInWarehouse(ReleaseDetailsEntry releaseDetailsEntry)
			throws JAXBException, SQLException, IOException, InterruptedException, ManagerException, JSONException, XMLStreamException, ParseException {
		
		boolean isLmcOn = Boolean.parseBoolean(omsServiceHelper.getApplicationPropertyValue(EnumSCM.IS_LMC_ON));
		// Push Order to Order wave for picking
		wmsServiceHelper.pushOrderToWave(releaseDetailsEntry.getReleaseId());
		if (!wmsServiceHelper.validateOrderInCoreOrderRelease(releaseDetailsEntry.getReleaseId(), 8)) {
			log.info("Entry was not yet created in Core_order_release so inserting");
			wmsServiceHelper.creteRelaseInCore_order_release(releaseDetailsEntry.getReleaseId());
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> orderLine = DBUtilities
				.exSelectQuery("select sku_id, quantity from order_line where order_release_id_fk = "
						+ releaseDetailsEntry.getReleaseId(), "oms");
		for (Map<String, Object> line : orderLine) {
			wmsServiceHelper.insertItem(line.get("sku_id").toString(), releaseDetailsEntry.getWarehouseId(),
					Integer.parseInt(line.get("quantity").toString()));
		}
		// Item CheckOut in WMS
		String[] itemBarcodes = wmsServiceHelper.orderItemsCheckout(releaseDetailsEntry.getReleaseId(),
				releaseDetailsEntry.getWarehouseId());
		End2EndHelper.sleep(10000);
		
		HashMap<String, String> lmcSortingResponse;
		String binBarcode;
		VirtualPacketResponse response;
		
		if(isLmcOn){
			for(String item : itemBarcodes){
				lmcSortingResponse = wmsServiceHelper.lmcSorting(item);
				
				if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin")){
					binBarcode = wmsServiceHelper.prepareAndConsolidate(item, releaseDetailsEntry.getWarehouseId());
					response = wmsServiceHelper.flushBin(binBarcode);
					wmsServiceHelper.markVirtualPacketPicked(response.getData().get(0).getConsolidationPacketId());
					packmanServiceHelper.markItemTillPack(releaseDetailsEntry.getReleaseId());
				}else if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.")){
					packmanServiceHelper.markItemTillPack(releaseDetailsEntry.getReleaseId());					
				}else {
					log.info("Item Sorting error: "+lmcSortingResponse.get("statusMessage"));
					ExceptionHandler.handleTrue(false, lmcSortingResponse.get("statusMessage"));
				}
			}
		}else{
			// Consolidating
			if (itemBarcodes.length > 1) {
				for (String itemId : itemBarcodes) {
					wmsServiceHelper.prepareAndConsolidate(itemId, releaseDetailsEntry.getWarehouseId());
				}
			} else
				wmsServiceHelper.setVirtualBin(itemBarcodes[0], releaseDetailsEntry.getWarehouseId());
		}
		// Mark Item QA PASS
				wmsServiceHelper.markItemQADONE(releaseDetailsEntry.getReleaseId());
		
		if(isLmcOn){
			
		}else{
			// Hack to markRelease packed due to timeout issue, we can remove this
			// once timeout issue is fixed
			wmsServiceHelper.markReleasePacked(releaseDetailsEntry.getReleaseId());
			End2EndHelper.sleep(5000L);
		}
				

		if (!omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId()).getStatus().toString()
				.equals("PK")) {
			// RTS SCAN in OMS
			ExceptionHandler.handleEquals(wmsServiceHelper.markReleasePacked(releaseDetailsEntry.getReleaseId())
					.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "RTS call failed");
		}
		ExceptionHandler.handleTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PK.name(), 5), "Release Status is not PK in OMS");
		String packetId =  ""+omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntry.getReleaseId()).getOrderLines().get(0).getPacketId();
		ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(packetId,
				ReleaseStatus.PACKED.name(), 10));
	}

	public void processForOnHandInWarehouseMock(ReleaseDetailsEntry releaseDetailsEntry)
			throws JAXBException, SQLException, IOException, InterruptedException, ManagerException {
		
		DBUtilities.exUpdateQuery("update order_line set status_code = 'QD' where order_release_id_fk = "
				+ releaseDetailsEntry.getReleaseId(), "oms");
		DBUtilities.exUpdateQuery(
				"update order_release set status_code = 'PK' where id = " + releaseDetailsEntry.getReleaseId(), "oms");
		lmsHelper.insertWMSItem(releaseDetailsEntry.getReleaseId(), releaseDetailsEntry.getWarehouseId());
		ExceptionHandler.handleEquals(omsServiceHelper.pushReleaseToLms(releaseDetailsEntry.getReleaseId()).getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS, "Unable to push release to LMS. OMS throwing ERROR");
		ExceptionHandler.handleTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseDetailsEntry.getReleaseId(),
				ReleaseStatus.PACKED.name(), 15), "Order is not in LMS");
	}
	
	public void processReleaseInWMS(ReleaseDetailsEntryList releaseDetailsEntryList) throws UnsupportedEncodingException, ManagerException, InterruptedException, JAXBException, JSONException, XMLStreamException, ParseException{
		
		List<ReleaseDetailsEntry> releaseDetailsEntries = releaseDetailsEntryList.getReleaseDetailsEntries();
		String orderId = ""+omsServiceHelper.getOrderReleaseEntry(releaseDetailsEntries.get(0).getReleaseId()).getOrderId();
		
		// Push Order to Order wave for picking
		wmsServiceHelper.pushOrderToWMS(orderId, releaseDetailsEntries);
		// Order checkout
		List<Map<String, Object>> items = wmsServiceHelper.orderChckout(releaseDetailsEntries);
		// Order Consolidation
		wmsServiceHelper.consolidateRelease(items);
		// Flush Bin
		if(wmsServiceHelper.isBinFlushRequired(items.get(0).get("barcode").toString())){
			VirtualPacketResponse virtualPacketResponse = wmsServiceHelper.flushBin(wmsServiceHelper.getBinBarcode(items.get(0).get("barcode").toString()));
			wmsServiceHelper.markVirtualPacketPicked(virtualPacketResponse.getData().get(0).getConsolidationPacketId());
		}
	}

}

