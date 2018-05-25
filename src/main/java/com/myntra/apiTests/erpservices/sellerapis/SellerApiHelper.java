package com.myntra.apiTests.erpservices.sellerapis;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.partners.SellerInventoryEntries;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.SlackMessenger;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.sellerapi.service.client.entry.InventoryEntry;
import com.myntra.sellerapi.service.client.entry.OrderLineEntry;
import com.myntra.sellerapi.service.client.response.InventoryResponse;
import com.myntra.sellerapi.service.client.response.OrderResponse;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.vms.client.entry.SellerEntry;
import com.myntra.sellers.response.SellerResponse;
import com.myntra.worms.client.response.OrderCaptureReleaseResponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by abhijit.pati on 13/03/16. Updated By @author Subhadeep.Saha
 */
public class SellerApiHelper {

	public void authenticate() {

	}

	/**
	 * Seller will Use this api to update inventory to Myntra System
	 * 
	 * @param list<InventoryEntry>
	 * @return InventoryResponse
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public InventoryResponse updateInventory(String[] list)
			throws JAXBException, JsonParseException, JsonMappingException, IOException {
		// String inventoryCount, String skuCode, String processingSla, String
		// warehouse
		SellerInventoryEntries sellerInventoryEntries = new SellerInventoryEntries();
		List<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		// [skucode:inventoryCount:warehouse:sla]
		for (String string : list) {
			String[] invetoryEntry = string.split(":");
			InventoryEntry inventoryEntry = new InventoryEntry();
			inventoryEntry.setSku(invetoryEntry[0]);
			inventoryEntry.setInventoryCount(Integer.parseInt(invetoryEntry[1]));
			inventoryEntry.setWarehouse(invetoryEntry[2]);
			inventoryEntry.setProcessingSla(Integer.parseInt(invetoryEntry[3]));
			inventoryEntries.add(inventoryEntry);
		}

		sellerInventoryEntries.setInventoryEntries(inventoryEntries);

		String payload = APIUtilities.getObjectToJSON(sellerInventoryEntries.getInventoryEntries());
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.UPDATEINVENTORY, null,
				SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.POST, payload, getSellerAPIHeader());;
		
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new InventoryResponse());
		return inventoryResponse;
	}
	
	public InventoryResponse updateInventoryV2(String[] list)
			throws JAXBException, JsonParseException, JsonMappingException, IOException {
		// String inventoryCount, String skuCode, String processingSla, String
		// warehouse
		SellerInventoryEntries sellerInventoryEntries = new SellerInventoryEntries();
		List<InventoryEntry> inventoryEntries = new ArrayList<InventoryEntry>();
		// [skucode:inventoryCount:warehouse:sla]
		for (String string : list) {
			String[] invetoryEntry = string.split(":");
			InventoryEntry inventoryEntry = new InventoryEntry();
			inventoryEntry.setSku(invetoryEntry[0]);
			inventoryEntry.setInventoryCount(Integer.parseInt(invetoryEntry[1]));
			inventoryEntry.setWarehouse(invetoryEntry[2]);
			inventoryEntry.setProcessingSla(Integer.parseInt(invetoryEntry[3]));
			inventoryEntries.add(inventoryEntry);
		}

		sellerInventoryEntries.setInventoryEntries(inventoryEntries);

		String payload = APIUtilities.getObjectToJSON(sellerInventoryEntries.getInventoryEntries());
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.UPDATEINVENTORY_V2, null,
				SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.POST, payload, getSellerAPIHeader());
		
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new InventoryResponse());
		return inventoryResponse;
	}

	private HashMap<String, String> getSellerAPIHeader() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", getBearerToken("bWFkdXJhQXBpVXNlcn5tYWR1cmFBcGlVc2VyOndlbGNvbWVAMjU4"));
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	private HashMap<String, String> getSellerAPIHeaderXml() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("AUTH_TOKEN",
				"ew0KImFjY2Vzc2tleSIgOiAibWFkaHVyYSIsDQoic2VjcmV0a2V5IjoibWFkaHVyYV9wYXNzd29yZCINCn0=");
		createOrderHeaders.put("Content-Type", "Application/xml");
		createOrderHeaders.put("Accept", "Application/xml");
		return createOrderHeaders;
	}

	private HashMap<String, String> getSellerAPIHeaderShip() throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization",getBearerToken("bWFkdXJhQXBpVXNlcn5tYWR1cmFBcGlVc2VyOndlbGNvbWVAMjU4"));
		createOrderHeaders.put("Accept", "multipart/form-data");
		return createOrderHeaders;
	}

	/**
	 * Search Seller inventory from myntra DB
	 * 
	 * @param skus
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public InventoryResponse searchInventory(List<String> skus)
			throws JAXBException, JsonParseException, JsonMappingException, IOException {

		// com.myntra.sapi.client.entry.InventoryEntry invEnt = new
		// com.myntra.sapi.client.entry.InventoryEntry();
		// invEnt.setCreatedBy(null);

		String payload = skus.toString();
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.SEARCHINVENTORY, null,
				SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.POST, payload, getSellerAPIHeader());
		
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new InventoryResponse());
		return inventoryResponse;
	}
	
	public InventoryResponse searchInventoryV2(List<String> skus)
			throws JAXBException, JsonParseException, JsonMappingException, IOException {

		// com.myntra.sapi.client.entry.InventoryEntry invEnt = new
		// com.myntra.sapi.client.entry.InventoryEntry();
		// invEnt.setCreatedBy(null);

		String payload = skus.toString();
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.SEARCHINVENTORY_V2, null,
				SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.POST, payload, getSellerAPIHeader());
		
		InventoryResponse inventoryResponse = (InventoryResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new InventoryResponse());
		return inventoryResponse;
	}

	/**
	 * RTP call seller will make to update that the Order is Packed
	 * 
	 * @param orderId
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public OrderResponse readyToDispatch(String orderId, String[] SkuId) throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		String[] skuId = SkuId[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setQuantity(Integer.parseInt(skuId[1]));
		orderLineEntries.add(orderLineEntry);

		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());
		
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.RTD,
				new String[] { ""+orderId, "readyToDispatch" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;

	}
	
	public OrderResponse readyToDispatchV2(String orderId, String[] SkuId) throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		String[] skuId = SkuId[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setQuantity(Integer.parseInt(skuId[1]));
		orderLineEntries.add(orderLineEntry);

		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());
		
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.RTD_V2,
				new String[] { ""+orderId, "readyToDispatch" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());;
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;

	}

	/**
	 * cancelOrderLine
	 * @param orderReleaseId
	 * @param skuIds
	 * @return
	 * @throws JAXBException
	 * @throws IOException
     */
	public OrderResponse cancelOrderLine(String orderReleaseId, String[] skuIds) throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();

		String[] skuId = skuIds[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setQuantity(Integer.parseInt(skuId[1]));
		orderLineEntries.add(orderLineEntry);

		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.CANCEL_ORDER_LINE,
				new String[] { ""+orderReleaseId, "cancelItems" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());;
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;
	}
	
	public OrderResponse cancelOrderLineV2(String orderReleaseId, String[] skuIds) throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();

		String[] skuId = skuIds[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setQuantity(Integer.parseInt(skuId[1]));
		orderLineEntries.add(orderLineEntry);

		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.CANCEL_ORDER_LINE_V2,
				new String[] { ""+orderReleaseId, "cancelItems" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());
		
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;
	}

	/**
	 * Update Tax
	 * 
	 * @param orderID
	 * @param skuIds
	 * @throws JAXBException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public OrderResponse updateTax(String orderID, String[] skuIds)
			throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		String[] skuId = skuIds[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setUnitTaxAmount(Double.parseDouble(skuId[1]));
		orderLineEntry.setTaxRate(Double.parseDouble(skuId[2]));
		orderLineEntry.setTaxType(skuId[3]);
		orderLineEntries.add(orderLineEntry);
		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());
		
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.UPDATE_TAX,
				new String[] { ""+orderID, "updateTax" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;

	}
	
	public OrderResponse updateTaxV2(String orderID, String[] skuIds)
			throws JAXBException, IOException {
		OrderLineEntry orderLineEntry = new OrderLineEntry();
		SellerApiOms sellerApiOms = new SellerApiOms();
		List<OrderLineEntry> orderLineEntries = new ArrayList<OrderLineEntry>();
		String[] skuId = skuIds[0].split(":");
		orderLineEntry.setOrderLineId(Long.parseLong(skuId[0]));
		orderLineEntry.setUnitTaxAmount(Double.parseDouble(skuId[1]));
		orderLineEntry.setTaxRate(Double.parseDouble(skuId[2]));
		orderLineEntry.setTaxType(skuId[3]);
		orderLineEntries.add(orderLineEntry);
		sellerApiOms.setOrderEntry(orderLineEntries);
		String payload = APIUtilities.getObjectToJSON(sellerApiOms.getOrderLineEntry());
		
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.UPDATE_TAX_V2,
				new String[] { ""+orderID, "updateTax" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.PUT, payload,
				getSellerAPIHeader());;
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		return orderReleaseResponse;

	}

	/**
	 * To search for an order by its id
	 * 
	 * @param orderId
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public OrderResponse getOrder(String orderId) throws JAXBException, IOException {
		// String payload = APIUtilities.convertXMLObjectToString(null);
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.GET_ORDER,
				new String[] { ""+orderId }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.GET, null, getSellerAPIHeader());
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		System.out.println(orderReleaseResponse);
		return orderReleaseResponse;

	}
	
	public OrderResponse getOrderV2(String orderId) throws JAXBException, IOException {
		// String payload = APIUtilities.convertXMLObjectToString(null);
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.GET_ORDER_V2,
				new String[] { ""+orderId }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.GET, null, getSellerAPIHeader());
		
		OrderResponse orderReleaseResponse = (OrderResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
				new OrderResponse());
		System.out.println(orderReleaseResponse);
		return orderReleaseResponse;

	}

	/**
	 * DownLoad Shipping label for an Order
	 * 
	 * @param orderId
	 * @throws JAXBException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public int downloadShippingLabel(String orderId) throws JAXBException, JsonParseException, JsonMappingException, IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.DOWNLOADSHIPPINGLABEL,
				new String[] { ""+orderId, "shippingLabel" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.GET, null,
				getSellerAPIHeaderShip());;
		
		return service.getResponseStatus();
	}
	
	public int downloadShippingLabelV2(String orderId) throws JAXBException, JsonParseException, JsonMappingException, IOException {
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.DOWNLOADSHIPPINGLABEL_V2,
				new String[] { ""+orderId, "shippingLabel" }, SERVICE_TYPE.SELLER_SVC.toString(), HTTPMethods.GET, null,
				getSellerAPIHeaderShip());
		
		return service.getResponseStatus();
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @throws JAXBException
	 * @throws UnsupportedEncodingException
	 */
	public SellerResponse createSeller(String name) throws JAXBException, UnsupportedEncodingException {
		SellerEntry sellerEntry = new SellerEntry();
		sellerEntry.setName(name);
		String payload = APIUtilities.convertXMLObjectToString(sellerEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.VMS_PATH.CREATE_SELLER, null, SERVICE_TYPE.VMS_SVC.toString(),
				HTTPMethods.POST, payload, getVMSHeader());
		SellerResponse sellerResponse = (SellerResponse) APIUtilities
				.convertXMLStringToObject(service.getResponseBody(), new SellerResponse());
		return sellerResponse;
	}

	/**
	 *
	 * @return
	 */
	private static HashMap<String, String> getVMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		createOrderHeaders.put("Content-Type", "Application/xml");
		return createOrderHeaders;
	}

	
	/**
	 *
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @madura auth
	 */
	public String getBearerToken(String sellerToken) throws JsonParseException, JsonMappingException, IOException {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic " + sellerToken);
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.AUTH, null, SERVICE_TYPE.SELLER_SVC.toString(),
				HTTPMethods.POST, null, createOrderHeaders);
		//AuthResponse authResponse = (AuthResponse) APIUtilities.getJsontoObject(service.getResponseBody(),
		//		new AuthResponse());
		String authToken = APIUtilities.getElement(service.getResponseBody(),"data.authToken", "json");
		System.out.println("Auth Token: "+ authToken);
		return authToken;
	}

    public SellerResponse getSellerAddress(String sellerID, String wareHouseID) throws UnsupportedEncodingException, JAXBException {
        Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.CREATE_SELLER, new String[] {sellerID, "warehouse", wareHouseID, "address"}, SERVICE_TYPE.SELLERSERVICES.toString(),
                                                             HTTPMethods.GET, null, getVMSHeader());
        SellerResponse sellerResponse = (SellerResponse) APIUtilities
                .convertXMLStringToObject(service.getResponseBody(), new SellerResponse());
        return sellerResponse;
    }

	public void processMaduraInSellerServiceTillPK(String orderId) throws JAXBException, IOException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String skuId = omsServiceHelper.getOrderLineEntries(orderId).get(0).getSkuId().toString();
		String qty = omsServiceHelper.getOrderLineEntries(orderId).get(0).getQuantity().toString();
		OrderResponse orderResponse = updateTax(orderId, new String[] {""+skuId+":100:5.25:VAT"});
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS","Update Tax for seller got failed");
		OrderResponse orderResponse1 = readyToDispatch(orderId, new String[]{skuId+":"+qty+""});
		if (orderResponse1.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)){
			SlackMessenger.send("scm_e2e_order_sanity", "Order RTS scan complete");
		}else{
			Assert.fail("Unable to mark release Packed(RTS failed)");
		}
		omsServiceHelper.validateReleaseStatusInOMS(orderId, "PK", 10);
	}

	public void processSmallSellerTillPK(String orderId ) throws JAXBException, IOException {
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
		String skuId = omsServiceHelper.getOrderLineEntries(orderId).get(0).getSkuId().toString();
//		OrderResponse orderResponse = updateTax(orderId,new String[] {""+skuId+":100:5.25:VAT"});
//		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS","Update Tax for seller got failed");
		OrderCaptureReleaseResponse res = wmsServiceHelper.markReleasePacked(orderId);
		if (res.getStatus().getStatusType().equals(StatusResponse.Type.SUCCESS)){
			SlackMessenger.send("scm_e2e_order_sanity", "Order RTS scan complete");
		}else{
			Assert.fail("Unable to mark release Packed(RTS failed)");
		}
		omsServiceHelper.validateReleaseStatusInOMS(orderId, "PK", 10);
	}
	
	public void verifyInventoryCount(String sellerSkuCode, int invCount){
		Assert.assertTrue(invCount == getWarehouseInventoryCount(sellerSkuCode), "Warehouse inventroy count did not match with the updated inventory count");
		Assert.assertTrue(invCount == getATPInventoryCount(sellerSkuCode), "ATP inventroy count did not match with the updated inventory count");
	}
	
	public int getWarehouseInventoryCount(String sellerSkuCode){
		String skuId = String.valueOf(DBUtilities.exSelectQueryForSingleRecord("select sku_id from seller_item_master where seller_sku_code = '" + sellerSkuCode +"'",
				"myntra_seller1").get("sku_id"));
		return (int) DBUtilities.exSelectQueryForSingleRecord("select inventory_count from wh_inventory where sku_id = " + skuId, "myntra_ims")
		.get("inventory_count");
	}
	
	public int getATPInventoryCount(String sellerSkuCode){
		String skuId = String.valueOf(DBUtilities.exSelectQueryForSingleRecord("select sku_id from seller_item_master where seller_sku_code = '" + sellerSkuCode +"'",
				"myntra_seller1").get("sku_id"));
		return (int) DBUtilities.exSelectQueryForSingleRecord("select inventory_count from inventory where sku_id = " + skuId + " and enabled = 1", "myntra_atp")
				.get("inventory_count");
	}
	
	public long getOrderReleaseID(String orderId){
		return (Long) DBUtilities.exSelectQueryForSingleRecord("select id from order_release where order_id_fk = " + orderId, "myntra_oms")
		.get("id");
	}
	
	public HashMap<String, Object> getOrderLineDetails(String orderId){
		HashMap<String, Object> orderLineDetails = (HashMap<String, Object>) DBUtilities.exSelectQueryForSingleRecord("select id, seller_id, sku_id, style_id from order_line where order_id_fk = " + orderId, "myntra_oms");
		return orderLineDetails;
	}
}
