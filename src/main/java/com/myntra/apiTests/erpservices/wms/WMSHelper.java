/**
 * 
 */
package com.myntra.apiTests.erpservices.wms;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.client.inventory.enums.Quality;
import com.myntra.client.wms.codes.utils.ItemStatus;
import com.myntra.client.wms.codes.utils.RoMode;
import com.myntra.client.wms.codes.utils.RoStatus;
import com.myntra.client.wms.codes.utils.RoType;
import com.myntra.client.wms.response.*;
import com.myntra.client.wms.response.location.ReconciliationEntry;
import com.myntra.client.wms.response.location.WarehouseEntry;
import com.myntra.commons.response.portal.PortalBrandDetailsEntry;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.vms.client.entry.VendorEntry;
import org.apache.log4j.Logger;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author santwana.samantray
 *
 */
/**
 * @author santwana.samantray
 *
 */
public class WMSHelper {

	OMSServiceHelper omshelper= new OMSServiceHelper();
	
	
	public static HashMap<String, String> getWMSHeader()
	{
	HashMap<String, String> map  = new HashMap<String, String>();
	
	map.put("Authorization",
			"Basic dW5pY29tfnVuaWNvbX51bmljb206dW5pY29t");
	map.put("Content-Type", "Application/xml");
	map.put("Accept", "Application/xml");
	return map;
	
	}

	static Initialize init = new Initialize("/Data/configuration");

	static Logger log = Logger.getLogger(WMSHelper.class);

	public RequestGenerator invokeOrderItemAssciationAPI(String OrderReleaseId,
			String itemid) {

		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ORDERITEMASSOCIATION,

				init.Configurations,

				new String[] { OrderReleaseId, itemid }, PayloadType.JSON,
				PayloadType.JSON);

		System.out.println("\nPrinting  order item assssociation API URL : "
				+ orderitemAsociation.URL);

		log.info("\nPrinting  order item assssociation   API URL : "
				+ orderitemAsociation.URL);

		System.out
				.println("\nPrinting order item assssociation  API Payload : "
						+ orderitemAsociation.Payload);

		log.info("\nPrinting order item assssociation  API Payload : "
				+ orderitemAsociation.Payload);



		return new RequestGenerator(orderitemAsociation, getWMSHeader());

	}

	public ItemResponse invokeBulkItemTransitionAPI(String itemid,
			ItemStatus item_status, String quality,String orderId ) throws JAXBException, UnsupportedEncodingException {

	
		List <ItemEntry> itementries =new ArrayList<ItemEntry>();
		ItemResponse bulkItemrequest = new ItemResponse();
		ItemEntry itementry = new ItemEntry();
		itementry.setId(Long.parseLong(itemid));
		itementry.setItemStatus(item_status);
		itementry.setOrderId(orderId);
		if(quality.equals("Q1"))
		itementry.setQuality(Quality.Q1);
		if(quality.equals("Q2"))
			itementry.setQuality(Quality.Q2);
		itementries.add(itementry);
	
		bulkItemrequest.setData(itementries);
		  String payload = APIUtilities.convertXMLObjectToString(bulkItemrequest);

	      Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.BULKUPDATE,null,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.PUT, payload, getWMSHeader());
	       ItemResponse itemresponse = (ItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ItemResponse());
	     //  log.info(“response is:“+itemresponse);
	       return itemresponse;

	}

	public static RequestGenerator validate_GRN_API_Helper(String q, String f,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.GRN, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

		HashMap<String, String> getWMSHeader = new HashMap<String, String>();
		getWMSHeader=getWMSHeader();

		return new RequestGenerator(getOrderService, getWMSHeader);
	}

	public static RequestGenerator validate_IOS_API_Helper(String q, String f,
			String start, String fetchSize) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.IOS, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize }, PayloadType.XML);
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_Invoices_API_Helper(String q,
			String f, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.INVOICES, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_Items_API_Helper(String q,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.ITEMS, init.Configurations, PayloadType.XML,
				new String[] { q, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_Orders_API_Helper(
			String q_portal_order_release_id, String q_order_id, String start,
			String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.ORDERS, init.Configurations, PayloadType.XML,
				new String[] { q_portal_order_release_id, q_order_id, start,
						fetchSize, sortBy, sortOrder }, PayloadType.XML);
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_SKUs_API_Helper(String q, String f,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.SKUS, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

		HashMap<String, String> getWMSHeader = new HashMap<String, String>();
		getWMSHeader=getWMSHeader();

		return new RequestGenerator(getOrderService, getWMSHeader);
	}

	public static RequestGenerator validate_Rejected_Items_API_Helper(String q,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.REJECTEDITEMS, init.Configurations, PayloadType.XML,
				new String[] { q, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

		

		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_RO_API_Helper(String q_barcode,
			String q_roStatus, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.RO, init.Configurations, PayloadType.XML, new String[] {
						q_barcode, q_roStatus, start, fetchSize, sortBy,
						sortOrder }, PayloadType.XML);

		HashMap<String, String> getWMSHeader = new HashMap<String, String>();
		getWMSHeader=getWMSHeader();
		

		return new RequestGenerator(getOrderService, getWMSHeader);
	}

	public static RequestGenerator validate_RGP_API_Helper(String q, String f,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.RGP, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

	

		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_STN_API_Helper(String q, String f,
			String start, String fetchSize, String sortBy, String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.STN, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

		

		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_STNItems_API_Helper(String q,
			String f, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.STNITEMS, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);
		
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_Station_Bins_API_Helper(String q,
			String f, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.STATIONBINS, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

		HashMap<String, String> getWMSHeader = new HashMap<String, String>();
		getWMSHeader=getWMSHeader();
		return new RequestGenerator(getOrderService, getWMSHeader);
	}

	public static RequestGenerator validate_Purchase_Orders_API_Helper(
			String q, String f, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.PURCHASEORDERS, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

	
		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	public static RequestGenerator validate_Purchase_Intent_API_Helper(
			String q, String f, String start, String fetchSize, String sortBy,
			String sortOrder) {
		MyntraService getOrderService = Myntra.getService(ServiceType.ERP_WMS,
				APINAME.PURCHASEINTENT, init.Configurations, PayloadType.XML,
				new String[] { q, f, start, fetchSize, sortBy, sortOrder },
				PayloadType.XML);

	

		return new RequestGenerator(getOrderService, getWMSHeader());
	}

	
public	static Long getBuyerIdForMyntraDesigns(){
		String queryToGetBuyerIdForMyntraDesigns="select id from `vendor` where `is_buyer` =1 and `purchasing_entity` = 'MYNTRA DESIGNS PVT LTD'";
		Map<String,Object> buyerIdMap= DBUtilities.exSelectQueryForSingleRecord(queryToGetBuyerIdForMyntraDesigns, "myntra_vms");
		Assert.assertTrue(buyerIdMap.size()>0,"There are no entried for myntra designs as Buyer in VMS database..");
		Long BuyerId=(Long) buyerIdMap.get("id");
		return BuyerId;
	}
	// add PI
	public static RequestGenerator addPI(String vendorid,
			String categoryManagerid, String esitimatedshipmentdate) {
		String buyerId=(""+getBuyerIdForMyntraDesigns()).trim();
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ADDPI,

				init.Configurations,

				new String[] { vendorid, categoryManagerid,
						esitimatedshipmentdate,buyerId}, PayloadType.XML,
				PayloadType.XML);

		System.out.println("\nPrinting add PI API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting add PI  API Payload : "
				+ orderitemAsociation.Payload);


		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}


	// add a lot

	public static RequestGenerator addALot(String po_id, String cartonCount,
			String pieceCount) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ADDLOT,

				init.Configurations, new String[] { po_id, cartonCount,
						pieceCount },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\n addALot API URL : " + orderitemAsociation.URL);

		System.out.println("\nPrinting addALot  API Payload : "
				+ orderitemAsociation.Payload);

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// update a lot
	public static RequestGenerator updateALot(String lot_id, String lotstatus) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.UPDATELOT,

				init.Configurations, new String[] { lotstatus, lot_id },
				new String[] { lot_id },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\nupdateALot API URL : " + orderitemAsociation.URL);

		System.out.println("\nPrinting updateALot  API Payload : "
				+ orderitemAsociation.Payload);
		

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// add a invoice
	public static RequestGenerator addInvoice(String lotid) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ADDINVOICE,

				init.Configurations, new String[] { lotid },

				PayloadType.XML, PayloadType.XML);

		System.out
				.println("\n addInvoice API URL : " + orderitemAsociation.URL);

		System.out.println("\nPrinting addInvoice  API Payload : "
				+ orderitemAsociation.Payload);

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// update invoice
	public static RequestGenerator updateInvoice(String lotid,
			String lotbarcode, String invoiceid, String invoicestatus) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.UPDATEINVOICE,

				init.Configurations, new String[] { invoiceid, invoicestatus,
						lotid, lotbarcode }, new String[] { invoiceid },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\nupdateInvoice API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting updateInvoice  API Payload : "
				+ orderitemAsociation.Payload);
		

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// add skus to a invoice
	public static RequestGenerator addSkuToInvoice(String qty,
			String invoiceid, String skuid) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ADDINVOICESKUS,

				init.Configurations, new String[] { qty, skuid, invoiceid },
				new String[] { invoiceid },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\naddSkuToInvoice API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting addSkuToInvoice  API Payload : "
				+ orderitemAsociation.Payload);



		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// scan QA pass and fail carton for
	public static RequestGenerator scanQAPassFailCarton(String po_id,
			String vendorid, String warehouse_id,
			String esitimatedshipmentdate, String po_status,
			String categoryManagerid) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.APPROVEPO,

				init.Configurations, new String[] { vendorid, warehouse_id,
						esitimatedshipmentdate, po_status, po_id,
						categoryManagerid }, new String[] { po_id },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\nscanQAPassFailCarton API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting scanQAPassFailCarton  API Payload : "
				+ orderitemAsociation.Payload);

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// validateInward
	public static RequestGenerator validateInward(String po_id,
			String vendorid, String warehouse_id,
			String esitimatedshipmentdate, String po_status,
			String categoryManagerid) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.APPROVEPO,

				init.Configurations, new String[] { vendorid, warehouse_id,
						esitimatedshipmentdate, po_status, po_id,
						categoryManagerid }, new String[] { po_id },

				PayloadType.XML, PayloadType.XML);

		System.out.println("\nvalidateInward API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting validateInward  API Payload : "
				+ orderitemAsociation.Payload);

		

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// MarkItemQCPass
	public static RequestGenerator markItemQAPass(String lotbarcode,
			String cartoncode, String itemcode) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.MARKITEMQCPASS,

				init.Configurations, new String[] { lotbarcode, cartoncode,
						itemcode },

				PayloadType.JSON, PayloadType.XML);

		System.out.println("\n markItemQAPass API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting markItemQAPass  API Payload : "
				+ orderitemAsociation.Payload);


		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// MarkItemQCfail
	public static RequestGenerator markItemQAfail(String lotbarcode,
			String cartoncode, String itemcode) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.MARKITEMQCFAIL,

				init.Configurations, new String[] { lotbarcode, cartoncode,
						itemcode }, new String[] { null },

				PayloadType.JSON, PayloadType.XML);

		System.out.println("\n markItemQAfail API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting markItemQAfail  API Payload : "
				+ orderitemAsociation.Payload);

	

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}

	// ItemCheckin
	public static RequestGenerator ItemCheckin(String createdby,String binid,String bincorfirmationcode, String itemid) {
		MyntraService orderitemAsociation = Myntra.getService(
				ServiceType.ERP_WMS, APINAME.ITEMCHECKIN,

				init.Configurations, new String[] { createdby, binid,
						bincorfirmationcode, itemid},

				PayloadType.XML, PayloadType.XML);

		System.out.println("\n  ItemCheckin API URL : "
				+ orderitemAsociation.URL);

		System.out.println("\nPrinting ItemCheckin   API Payload : "
				+ orderitemAsociation.Payload);
		

		return new RequestGenerator(orderitemAsociation, getWMSHeader());
	}
//add ro
	/**
	 * @param vendor_id
	 * @param wh_id
	 * @param
	 * @param roMode
	 * @param roType
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	public static RoResponse addRO(long vendor_id, long wh_id,
			String roMode, String roType,long buyerId) throws JAXBException, UnsupportedEncodingException {
	//	long buyerId=getBuyerIdForMyntraDesigns();
	//	long buyerId=3974;
		RoEntry roentry = new RoEntry();
		VendorEntry vendorentry = new VendorEntry();
		vendorentry.setId(vendor_id);
		roentry.setVendor(vendorentry);
		WarehouseEntry wh_entry = new WarehouseEntry();
		wh_entry.setId(wh_id);
		roentry.setWarehouse(wh_entry);
		PortalBrandDetailsEntry brandentry = new PortalBrandDetailsEntry();
	//	brandentry.setBrandId(brandid);
		roentry.setBrand(brandentry);
		

		if (roMode.equalsIgnoreCase("PICK_UP"))
			roentry.setRoMode(RoMode.PICK_UP);
		else
			roentry.setRoMode(RoMode.TO_PAY);
		if (roType.equalsIgnoreCase("CUSTOMER_RETURNS"))
			roentry.setRoType(RoType.CUSTOMER_RETURNS);
		if (roType.equalsIgnoreCase("INWARD_REJECTS"))
			roentry.setRoType(RoType.INWARD_REJECTS);
		if (roType.equalsIgnoreCase("OUTWARD_REJECTS"))

			roentry.setRoType(RoType.OUTWARD_REJECTS);
		if (roType.equalsIgnoreCase("STOCK_CORRECTION"))

			roentry.setRoType(RoType.STOCK_CORRECTION);
		if (roType.equalsIgnoreCase("SOR_ITEM"))

			roentry.setRoType(RoType.SOR_ITEM);
		VendorEntry vendorEntryForMyntraDesigns= new VendorEntry();
		vendorEntryForMyntraDesigns.setId(buyerId);
		roentry.setBuyer(vendorEntryForMyntraDesigns);
		  String payload = APIUtilities.convertXMLObjectToString(roentry);

	        Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.UPDATERO, null,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
	        RoResponse roResponse = (RoResponse)APIUtilities.convertXMLStringToObject(service.getResponseBody(),new RoResponse());
	        System.out.println("response is:"+roResponse);
	        return roResponse;
	}
//	<ro><id>3453</id><barcode>RO0000003453</barcode><vendor><id>2197</id></vendor><warehouse><id>36</id></warehouse><brand><brandId>6888</brandId></brand><roStatus>DECLINED</roStatus></ro>

	/**
	 * @param roid
	 * @param ro_barcode
	 * @param vendor_id
	 * @param wh_id
	 * @param brandid
	 * @param rostatus
	 * @throws JAXBException 
	 * @throws UnsupportedEncodingException 
	 */
	public static RoResponse updateRO(long roid,String ro_barcode,long vendor_id, long wh_id, long brandid,
			String rostatus) throws JAXBException, UnsupportedEncodingException {
		RoEntry roentry = new RoEntry();
		roentry.setId(roid);
		roentry.setBarcode(ro_barcode);
		VendorEntry vendorentry = new VendorEntry();
		vendorentry.setId(vendor_id);
		roentry.setVendor(vendorentry);
		WarehouseEntry wh_entry = new WarehouseEntry();
		wh_entry.setId(wh_id);
		roentry.setWarehouse(wh_entry);
		PortalBrandDetailsEntry brandentry = new PortalBrandDetailsEntry();
		brandentry.setBrandId(brandid);
		roentry.setBrand(brandentry);
		if(rostatus.equalsIgnoreCase("APPROVED"))
		roentry.setRoStatus(RoStatus.APPROVED);
		if(rostatus.equalsIgnoreCase("ASSIGNED"))

		roentry.setRoStatus(RoStatus.ASSIGNED);
		if(rostatus.equalsIgnoreCase("COMPLETED"))

		roentry.setRoStatus(RoStatus.COMPLETED);
		if(rostatus.equalsIgnoreCase("CREATED"))

		roentry.setRoStatus(RoStatus.CREATED);
		if(rostatus.equalsIgnoreCase("DECLINED"))

		roentry.setRoStatus(RoStatus.DECLINED);
		if(rostatus.equalsIgnoreCase("DISPATCHED"))
		roentry.setRoStatus(RoStatus.DISPATCHED);
		if(rostatus.equalsIgnoreCase("DN_AWAITED"))

		roentry.setRoStatus(RoStatus.DN_AWAITED);
		if(rostatus.equalsIgnoreCase("INVOICED"))

		roentry.setRoStatus(RoStatus.INVOICED);
		if(rostatus.equalsIgnoreCase("PACKED"))

		roentry.setRoStatus(RoStatus.PACKED);
		if(rostatus.equalsIgnoreCase("PICK_INITIATED"))

		roentry.setRoStatus(RoStatus.PICK_INITIATED);
		if(rostatus.equalsIgnoreCase("READY"))

		roentry.setRoStatus(RoStatus.READY);

        String payload = APIUtilities.convertXMLObjectToString(roentry);

        Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.UPDATERO, null,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
        RoResponse roResponse = (RoResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new RoResponse());
        System.out.println("response is:"+roResponse);
        return roResponse;
/*<?xml version="1.0" encoding="UTF-8" standalone="yes"?><transitionResponse><status><statusCode>8008</statusCode><statusMessage>Transition(s) retrieved successfully</statusMessage><statusType>SUCCESS</statusType><totalCount>1</totalCount></status><data><transition>
 * <createdBy>erpadmin</createdBy><createdOn>2015-11-27T09:20:42Z</createdOn><id>295</id>
 * <lastModifiedOn>2015-11-27T09:20:42Z</lastModifiedOn><description>Return Order Status from CREATED to DECLINED</description>
 * <fromState><createdBy>Mohit</createdBy><createdOn>2013-10-10T12:36:43Z</createdOn><id>127</id><lastModifiedOn>2013-10-10T12:36:43Z</lastModifiedOn>
 * <description>New Return Order Created</description><entity>com.myntra.client.wms.codes.utils.RoStatus</entity>
 * <isStart>true</isStart><name>CREATED</name></fromState><toState><createdBy>Mohit</createdBy>
 * <createdOn>2013-10-10T12:36:43Z</createdOn><id>131</id><lastModifiedOn>2013-10-10T12:36:43Z</lastModifiedOn>
 * <description>Return Order Declined</description><entity>com.myntra.client.wms.codes.utils.RoStatus</entity>
 * <isStart>false</isStart><name>DECLINED</name></toState></transition></data></transitionResponse>*/

}
	public static RoItemResponse addItemRo(long roid,String item_barcode) throws JAXBException, UnsupportedEncodingException 
    {
        RoItemEntry roItem= new RoItemEntry();
        RejectedItemEntry rejectedItementry= new RejectedItemEntry();
        ItemEntry itementry= new ItemEntry();
        itementry.setBarcode(item_barcode);
        rejectedItementry.setItem(itementry);
        roItem.setRejectedItem(rejectedItementry);
        roItem.setApproved(false);
       String payload = APIUtilities.convertXMLObjectToString(roItem);
       String[] query_list ={roid+"/items/"};

      Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.ADDROITEM, query_list,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
       RoItemResponse roResponse = (RoItemResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new RoItemResponse());
     //  System.out.println(“response is:“+roResponse);
       return roResponse;
       
        
    }
	//Reconcile 
	public static ReconciliationResponse ReconcileItem(String binBarcode,String[] item) throws JAXBException, UnsupportedEncodingException 
	{
		ReconciliationEntry reconciliationEntry= new ReconciliationEntry();
		reconciliationEntry.setBinBarcode(binBarcode);
		List<String> itemList = new LinkedList<String>();
		for (int i=0;i<item.length;i++)
		{
			itemList.add(item[i]);
		}
		reconciliationEntry.setItemBarcodes(itemList);
       String payload = APIUtilities.convertXMLObjectToString(reconciliationEntry);

       Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.RECONCILE,null,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
       ReconciliationResponse reconciliationResponse = (ReconciliationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ReconciliationResponse());
       log.info("response is:"+reconciliationResponse);
       return reconciliationResponse;
       // {"reconciliationResponse":{"status":{"statusCode":7021,"statusMessage":"\"Reconciliation done successfully\"","statusType":"SUCCESS","totalCount":0},"data":""}}
		
	}
	//validate reconcile
	  public static ReconciliationResponse ValidateReconcileItem(String binBarcode,String[] itemBarcodes) throws JAXBException, UnsupportedEncodingException 
	    {
	        ReconciliationEntry reconciliationEntry= new ReconciliationEntry();
	        reconciliationEntry.setBinBarcode(binBarcode);
	        ItemEntry itementry = new ItemEntry();
	        List<ItemEntry> itemlist= new LinkedList<ItemEntry>();
	        
	        for(int i=itemBarcodes.length; i>0;i--)
	        {
	            itementry.setBarcode(itemBarcodes[i]);
	            itemlist.add(itementry);
	        }
	        reconciliationEntry.setAvailableItems(itemlist);
	       String payload = APIUtilities.convertXMLObjectToString(reconciliationEntry);

	      Svc service = HttpExecutorService.executeHttpService(Constants.WMS_PATH.RECONCILE,null,SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.POST, payload, getWMSHeader());
	       ReconciliationResponse reconciliationResponse = (ReconciliationResponse) APIUtilities.convertXMLStringToObject(service.getResponseBody(), new ReconciliationResponse());
	     //  log.info(“response is:“+reconciliationResponse);
	       return reconciliationResponse;
	    //{“reconciliationResponse”:{“status”:{“statusCode”:742,“statusMessage”:“Location added successfully”,“statusType”:“SUCCESS”,“totalCount”:1}    
	    }
	public static void createItem(String sku_id,String warehouse_id,String itemStatus, int item_bin,String quality,String rejectReason,String rejectReasonDesc,String orderId,int quantity)
	{
		while(quantity!=0){
		String	itembarcode=getRandomItemId();
		String insertItem="INSERT INTO `item` (`id`, `barcode`, `sku_id`, `quality`, `item_status`, `warehouse_id`, `enabled`, `po_id`, `po_barcode`, `po_sku_id`, `lot_id`, `lot_barcode`, `comments`, `order_id`, `bin_id`, `grn_sku_id`, `grn_barcode`, `inwarded_on`, `reject_reason_code`, `reject_reason_description`, `carton_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`)VALUES(null,"+itembarcode+"',"+sku_id+",'"+quality+"','"+itemStatus+"', "+warehouse_id+", 0, 41376, 'UCBI1497001526499', 4113452, NULL, NULL, 'Created for PO: UCBI1497001526499',"+orderId+", "+item_bin+", NULL, NULL, NULL,"+rejectReason+"," +rejectReasonDesc+", NULL, NULL, '2017-06-09 15:24:51','erpMessageQueue', '2017-06-09 15:24:51', 1)";
		String getitemId="select * from  item where barcode="+itembarcode;
		String insertItemInfo="INSERT INTO `item_info` (`id`, `item_id`, `item_action_status`, `task_id`, `order_barcode`, `created_on`, `created_by`, `last_modified_on`, `version`, `order_id`, `invoice_sku_id`, `agreement_type`, `buyer_id`)VALUES(null, 9100000086118, 'NEW', NULL, NULL, '2017-06-09 15:24:51', 'erpMessageQueue', '2017-06-09 15:24:51', 0, NULL, NULL, 'OUTRIGHT', 2297);";
	quantity--;	
	}
		
	} public static String getRandomItemId() {
        Random random = new Random();
        long epoch = System.currentTimeMillis() / 1000 + random.nextInt(100);
        String itemid = String.valueOf(epoch);
        return itemid;
    }
	
	public  String createOrderOMSforWorms(String sku_id, String qty,String supply_type) throws UnsupportedEncodingException, JAXBException
	
	 { String store_order_id=getRandomItemId();
		String createOrder="INSERT INTO `orders` (`id`, `store_order_id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, `cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, `billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`, `queued_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `order_type`, `loyalty_points_used`, `store_id`)VALUES(null, '"+store_order_id+"', NULL, '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '1234567890', 'santwana', 'cod', NULL, NULL, NULL, 3297.00, 0.00, 0.00, 0.00, 0.00, 0.00, 3297.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '2017-08-03 01:36:04', '2017-08-03 01:32:36', 1, 'on', 0.00, 1);";
		String getOrderid= omshelper.getOrderEntryByStoreOrderID(store_order_id).getId().toString();
		String createRelease="INSERT INTO `order_release` (`id`, `order_id_fk`, `store_order_id`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `is_on_hold`, `queued_on`, `invoice_id`, `cheque_no`, `exchange_release_id`, `user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`, `seller_id` ) VALUES(null,"+getOrderid+" , '"+store_order_id+"', '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', 'Q', 'cod', 3297.00, 0.00, 0.00, 0.00, 0.00, 0.00, 3297.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 'santwana', 'Myntra test worms automation', 'Bangalore', 'Bommanahalli  &#x28;Bangalore&#x29;', 'KA', 'India', '560068', '1234567890', 'santwana@myntra.com', 'ML', 'ML0000875348', 36, 0, 'pending', NULL, '2017-08-03 01:36:04', NULL, NULL, NULL, NULL, '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '2017-08-03 01:36:04', '2017-08-03 18:45:36', 1, 0, '2017-08-03 01:36:04', NULL, NULL, NULL, '1234567890', 'NORMAL', NULL, 0.00, 1, NULL, NULL);";
		String getOrderReleaseId=omshelper.getReleaseId(getOrderid);
		String createLine="INSERT INTO `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `store_order_id`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `store_id`, `po_status`)VALUES(null, "+getOrderid+", "+getOrderReleaseId+",'"+store_order_id+"', 1543, 5298, "+sku_id+", 'A', 1099.00, "+qty+", 0, 0.00, 0.00, 0.00, 0.00, 0.00, 2198.00, 0.00, 0.00, 0.00, 0, 0, NULL, 0, 1, NULL, NULL, '2aa1d18c.86c9.4eff.88c0.a7ef8c677eb7TK8AVXQJJz', '2017-08-03 01:36:04', '2017-08-03 01:36:04', 0, NULL, 0.00, 19, '"+supply_type+"', NULL, NULL, NULL, 'UNUSED');";
		
		DBUtilities.exUpdateQuery(createOrder, "myntra_oms");
		DBUtilities.exUpdateQuery(createRelease, "myntra_oms");
		DBUtilities.exUpdateQuery(createLine, "myntra_oms");
		return getOrderReleaseId;
		
	 }
	
	
	
	
}

