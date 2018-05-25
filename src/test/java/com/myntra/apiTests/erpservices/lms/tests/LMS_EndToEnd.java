package com.myntra.apiTests.erpservices.lms.tests;

import argo.saj.InvalidSyntaxException;
import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessOrder;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.packman.PackmanServiceHelper;
import com.myntra.apiTests.erpservices.partners.SellerServicesHelper;
import com.myntra.apiTests.erpservices.wms.WMSConstants;
import com.myntra.apiTests.erpservices.wms.WMSOrderFlowHelper;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.client.wms.response.VirtualPacketResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.underworld.viktor.Tools;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LMS_EndToEnd extends BaseTest {
	
	static Logger log = Logger.getLogger(WMSServiceHelper.class);
	
	String login = OMSTCConstants.LoginAndPassword.AddidionalChargeLogin;
	String password = OMSTCConstants.LoginAndPassword.AddidionalChargePassword;
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	ProcessOrder processOrder = new ProcessOrder();
	ProcessRelease processRelease = new ProcessRelease();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	SellerServicesHelper sellerServicesHelper = new SellerServicesHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	Tools tools = new Tools();
	String envData = null;
	Resource envResource;
	private ResourceLoader resourceLoader;
	private String environment;
	private String isDockinsEnabled;
	private String dockEnvName;
	private IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private String uidx;
	String emailId1 = "notifyme763@gmail.com";
	String password1 = "myntra@123";
	String emailId2 = "hotstarr123@gmail.com";
	String password2 = "123456";
	String email3 = "newcus123@gmail.com";
	WMSOrderFlowHelper wmsOrderFlowHelper = new WMSOrderFlowHelper();
	PackmanServiceHelper packmanServiceHelper=new PackmanServiceHelper();
	
	private String orderID;
	private String supplyTypeOnHand = "ON_HAND";
	
	private int sellerId_HEAL = 21;
	private int sellerId_CONS = 25;
	private int sellerId_HEAL_JIT = 21;
	private int sellerId_CONS_JIT = 25;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException, ManagerException {
		
		//vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		//omsServiceHelper.refreshOMSApplicationPropertyCache();
		//omsServiceHelper.refreshOMSJVMCache();
		//uidx = ideaApiHelper.getUIDXForLogin("myntra", emailId2);
	}
	
	@Test
	public void createOrderAPI() throws Exception{
		List<String> ordersList = new ArrayList<String>();
		//818511 jit sku   1319621   984027
		SkuEntry[] skuEntries = {new SkuEntry("3837", 1)};
		
		//SkuEntry[] skuEntries = {new SkuEntry(3832, 2)};
		
		//end2EndHelper.updateloyalityAndCashBack(uidx, 100, 1000);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21+":1",
				3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21,
				3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+21},"ON_HAND");
		
		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(emailId1,password1,560068L,skuEntries).build();
		System.out.println(APIUtilities.getObjectToJSON(createOrderEntry).toString());
		String orderID = "";
		for(int i=0;i<1;i++){
			orderID = end2EndHelper.createOrder(createOrderEntry);
			System.out.println(orderID);
			omsServiceHelper.checkReleaseStatusForOrderWithHack(orderID, "WP");
			String storeOrderId = omsServiceHelper.getOrderEntry(orderID).getStoreOrderId();
			ordersList.add(storeOrderId);
			
			
			//markOrderTillPK(orderID);
			
			String orderReleaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
			
			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
			processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
			
			
		}
	
		
		
	}
	
	
	@Test
	public void process() throws JAXBException, IOException, InstantiationException, JSONException,
			NoSuchMethodException, SQLException, InterruptedException, ManagerException, InvocationTargetException,
			IllegalAccessException, XMLStreamException {
		String orderID = "2147491263";
		String orderReleaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
		
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		
		
	}
	@Test
	public void createOrderAPI_RTO() throws Exception{
		List<String> ordersList = new ArrayList<String>();
		//818511 jit sku   1319621   984027
		SkuEntry[] skuEntries = {new SkuEntry("3837", 1)};
		
		//SkuEntry[] skuEntries = {new SkuEntry(3832, 2)};
		
		//end2EndHelper.updateloyalityAndCashBack(uidx, 100, 1000);
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21+":1",
				3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+21,
				3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+21},"ON_HAND");
		
		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(emailId1,password1,560068L,skuEntries).build();
		System.out.println(APIUtilities.getObjectToJSON(createOrderEntry).toString());
		String orderID = "";
		for(int i=0;i<1;i++){
			orderID = end2EndHelper.createOrder(createOrderEntry);
			System.out.println(orderID);
			omsServiceHelper.checkReleaseStatusForOrderWithHack(orderID, "WP");
			String storeOrderId = omsServiceHelper.getOrderEntry(orderID).getStoreOrderId();
			ordersList.add(storeOrderId);
			
			
			//markOrderTillPK(orderID);
			
			String orderReleaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
			
			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.RTO).shipmentSource(ShipmentSource.MYNTRA).build());
			processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
			
			
		}
		
		
		
	}
	public void markOrderTillPK(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException, JSONException, XMLStreamException, ParseException, InterruptedException{
		
		ArrayList<HashMap> items = null;
		HashMap<Integer, String> warehouseIdsAndPickTypes;
		HashMap<String, String> lmcSortingResponse = null;
		VirtualPacketResponse virtualPacketResponse;
		boolean orderCheckout = false;
		boolean isBinFlushRequired = true;
		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		String storeOrderId = orderEntry.getStoreOrderId();
		
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		
		List<HashMap> orderLines = wmsOrderFlowHelper.getOrderDetails(storeOrderId);
		warehouseIdsAndPickTypes = wmsOrderFlowHelper.getWarehouseIdsAndPickTypes(orderEntry.getOrderReleases().get(0).getOrderId().toString());
		
		// Push Order to Order wave for picking
		if(!wmsOrderFlowHelper.isPushOrderToWaveApiHit(orderID)){
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
				if(!wmsOrderFlowHelper.isItemConsolidated(item.get("barcode").toString())){
					lmcSortingResponse = wmsOrderFlowHelper.lmcSorting(item.get("barcode").toString());
					if(lmcSortingResponse.get("statusMessage").contains("Please send item to packing desk.")){
						isBinFlushRequired = false;
						
					}else if(lmcSortingResponse.get("statusMessage").contains("Please keep item into bin/section for consolidation")){
						wmsOrderFlowHelper.prepareAndConsolidate(item.get("barcode").toString(), orderLine.get("source_wh_id").toString());
						
					}else{
						Assert.fail(lmcSortingResponse.get("statusMessage"));
					}
				}
			}
		}
		
		if(isBinFlushRequired){
			virtualPacketResponse = wmsOrderFlowHelper.flushBin(lmcSortingResponse.get("binNumber"));
			wmsOrderFlowHelper.markVirtualPacketPicked(virtualPacketResponse.getData().get(0).getConsolidationPacketId());
		}
		
		packmanServiceHelper.markItemTillPack(orderLines.get(0).get("order_release_id_fk").toString());
	}

	
/*	@Test
	public void markOrderDeliveredTest() throws ManagerException, JAXBException, SQLException, IOException, InterruptedException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, XMLStreamException, JSONException{
		List<String> releaseList = new ArrayList<String>();
		//releaseList.add(e);
		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder("21576304305", ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList = new ReleaseEntryList();
		ReleaseEntry releaseEntry1 = getReleaseEntry("21576304938");
		ReleaseEntry releaseEntry2 = getReleaseEntry("21576304939");
		ReleaseEntry releaseEntry3 = getReleaseEntry("21576304940");
		ReleaseEntry releaseEntry4 = getReleaseEntry("21576304941");
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(releaseEntry1);
		releaseEntries.add(releaseEntry2);
		releaseEntries.add(releaseEntry3);
		releaseEntries.add(releaseEntry4);
		releaseEntryList.setReleaseEntries(releaseEntries);
		processRelease.processReleaseToStatusHelper(releaseEntryList);
	}*/
	
	public ReleaseEntry getReleaseEntry(String release){
		return new ReleaseEntry.Builder(release, ReleaseStatus.DL).build();
	}
	
	
	@Test
	public void multiseller() throws InvalidSyntaxException, SCMExceptions, ParseException, IOException, JAXBException, ManagerException, JSONException, NoSuchMethodException, InterruptedException, IllegalAccessException, XMLStreamException, SQLException, InstantiationException, InvocationTargetException {
		
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_47583+":1",OMSTCConstants.OtherSkus.skuId_749877+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL+":1",OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_47583+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_47584+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749877+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS },supplyTypeOnHand);
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		String orderReleaseId = omsServiceHelper.getOrderEntry(orderID).getOrderReleases().get(0).getId().toString();
		
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).shipmentSource(ShipmentSource.MYNTRA).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		
		
	}
}
