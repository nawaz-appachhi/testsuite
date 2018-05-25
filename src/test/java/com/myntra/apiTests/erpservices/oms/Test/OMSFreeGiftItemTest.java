package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OMSFreeGiftItemTest extends BaseTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	String login = OMSTCConstants.freeItemTestData.LOGIN;
	String uidx;
	String password = OMSTCConstants.freeItemTestData.PASSWORD;
	private static Long vectorSellerID;
	private static Logger log = Logger.getLogger(OMSServiceHelper.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	ProcessRelease processRelease = new ProcessRelease();
	LMSHelper lmsHelper = new LMSHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	StyleServiceApiHelper styleServiceApiHelper = new StyleServiceApiHelper();
	private String orderID;
	//Date setup
	private String paidStyle = OMSTCConstants.freeItemTestData.PaidStyle;
	private String freeStyle = OMSTCConstants.freeItemTestData.FreeStyle;
	private String paidSku = OMSTCConstants.freeItemTestData.PaidSku;
	private String freeSku = OMSTCConstants.freeItemTestData.FreeSku;
	
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private Integer warehouseId1;
	private Integer warehouseId2;
	private boolean releaseSplit;
	private String orderReleaseId;
	private String lineID;
	private int quantity;
	private Integer newWarehouseId;
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	SoftAssert sft;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23,37);",
				"myntra_oms");
		
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		//End2EndHelper.sleep(15000);
		omsServiceHelper.createFreeItem(paidStyle,freeStyle,2);
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		log.info("style reindexing...");
		styleServiceApiHelper.styleReindexForStyleIDs(paidStyle);
		styleServiceApiHelper.styleReindexForStyleIDs(paidStyle);
		End2EndHelper.sleep(120000);

	}
	
	@BeforeMethod(alwaysRun=true)
	public void testBeforeMethod() throws JAXBException, IOException{
		
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem"}, description = "TC001:Order with freeItem assigned to same warehouse, verify single release should be created,Order should be DL after shipment")
	public void freeItemAssginedToSameWarehouse() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		//Check if order is in WP status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single release for both items");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);


		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
		//Check if order is in DL status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "DL");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem"}, description = "TC002:Free item Assigned to different warehouse and Free item release in OnHold=10, After shipment of paid item release free item release will be unhold and mark as DL after shipment")
	public void freeItemAssginedToDifferentWarehouseAndValidateFreeItemReleaseOnHold() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		//Check if order is in WP status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to different warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		
		warehouseId1 = orderReleaseEntries.get(0).getWarehouseId();
		warehouseId2 = orderReleaseEntries.get(1).getWarehouseId();
		releaseSplit = (warehouseId1==28 && warehouseId2==36) || (warehouseId1==36 && warehouseId2==28);
		sft.assertTrue(releaseSplit,"Releases are not assigned to correct warehouses");
		
		sft.assertEquals(orderReleaseEntries.size(), 2,"There should be two releases");
		String freeItemRelease = getReleaseOfParticularItem(orderID, freeSku+"");
		Boolean freeItemOnHold = omsServiceHelper.getOrderReleaseEntry(freeItemRelease).getOnHold();
		Assert.assertEquals(freeItemOnHold, Boolean.TRUE);

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(freeItemRelease, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);


		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(freeItemRelease, ReleaseStatus.DL).build());
		//Verify Both release are with no onhold
		for(OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
			sft.assertEquals(orderReleaseEntry.getOnHold(), Boolean.FALSE,"Free Item release should be Unhold after paid item is shipped");
		}
		String paidItemRelease = getReleaseOfParticularItem(orderID, paidSku+"");


		List<ReleaseEntry> releaseEntries_Paid = new ArrayList<>();
		releaseEntries_Paid.add(new ReleaseEntry.Builder(paidItemRelease, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList_Paid= new ReleaseEntryList(releaseEntries_Paid);
		processRelease.processReleaseToStatus(releaseEntryList_Paid);


		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(paidItemRelease, ReleaseStatus.DL).build());
		//Check both releases are in DL status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "DL");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC003:Free item assigned to different warehouse, verify after cancelling paid item free item is also cancelled")
	public void freeItemAssginedToDifferentWarehouseAndCancelOriginalItemShouldCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 2,"There should be single release for both items");
		
		cancelLineWithOriginalItem(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC004:Free item assigned to same warehouse, verify after cancelling paid item free item is also cancelled")
	public void freeItemAssginedToSameWarehouseAndCancelOriginalItemShouldCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single release for both items");
		
		cancelLineWithOriginalItem(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC005:Free item assigned to different warehouse, verify after cancelling paid itemRelease free item is also cancelled")
	public void freeItemAssginedToDifferentWarehouseAndCancelOriginalItemReleaseShouldCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 2,"There should be multiple release for both items");
		
		cancelReleaseWithOriginalItem(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
		sft.assertAll();
	}

	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC006:Free item assigned to same warehouse, verify after cancelling paid itemRelease free item is also cancelled")
	public void freeItemAssginedToSameWarehouseAndCancelOriginalItemReleaseShouldCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single release for both items");
		cancelReleaseWithOriginalItem(orderID);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC007:Free item assigned to same warehouse, verify after cancelling order both lines are cancelled")
	public void freeItemAssginedToSameWarehouseAndCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single release for both items");
		OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type ");
        omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
        sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem","cancel"}, description = "TC008:Free item assigned to different warehouse, verify after cancelling order both releases are cancelled")
	public void freeItemAssginedToDifferentWarehouseAndCancelOrder() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 2,"There should be multiple release for both items");
		OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify Response Status Type ");
        omsServiceHelper.checkReleaseStatusForOrder(orderID, "F");
        sft.assertAll();
	}



	
	@Test(enabled = true,groups={"smoke","regression","freeItem"}, description = "TC009:Free item Reassigned to different warehouse, verify free item release is put onHold=10 and After shipment paid item free item is unhold and marked as DL after shipment")
	public void freeItemReAssginedToDifferentWarehouse() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		
		warehouseId1 = orderReleaseEntries.get(0).getWarehouseId();
		
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single releases");
		
		//Reassign free item to warehouse = 19
		imsServiceHelper.updateInventoryForSeller(new String[]{freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId19_BG+":10000:0:"+vectorSellerID},
				"ON_HAND");
		
		reassignToDifferentWarehouse(orderID,19,freeStyle);
		
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 2,"There should be two releases");
		
		String freeItemRelease = getReleaseOfParticularItem(orderID, freeSku+"");
		Boolean freeItemOnHold = omsServiceHelper.getOrderReleaseEntry(freeItemRelease).getOnHold();
		sft.assertEquals(freeItemOnHold, Boolean.TRUE,"Free Item release should be put on Hold");
		
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(freeItemRelease, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(freeItemRelease, ReleaseStatus.DL).build());
		//Verify Both release are with no onhold
		for(OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
			Assert.assertEquals(orderReleaseEntry.getOnHold(), Boolean.FALSE,"Free Item release should be Unhold after paid item is shipped");
		}
		String paidItemRelease = getReleaseOfParticularItem(orderID, paidSku+"");

		List<ReleaseEntry> releaseEntries_Paid = new ArrayList<>();
		releaseEntries_Paid.add(new ReleaseEntry.Builder(paidItemRelease, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList_Paid= new ReleaseEntryList(releaseEntries_Paid);
		processRelease.processReleaseToStatus(releaseEntryList_Paid);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(paidItemRelease, ReleaseStatus.DL).build());
		//Check both releases are in DL status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "DL");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"smoke","regression","freeItem"}, description = "TC001:Order with freeItem assigned to same warehouse, verify single release should be created,Order should be DL after shipment")
	public void freeItemAssginedToSameWarehouseBuy2Get1Free() throws Exception {
		sft = new SoftAssert();
		String paidSku = "";
		String skuId[] = { paidSku+":1" };
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1",freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, "ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{paidSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID,
				freeSku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
		//Check if order is in WP status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
		//Check if Free Sku added to 
		sft.assertTrue(validateFreeItemAddedToOrder(orderID),"Free item is not added for the order "+orderID);
		//Check both items assigned to same warehouse
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseEntries = orderEntry.getOrderReleases();
		sft.assertEquals(orderReleaseEntries.size(), 1,"There should be single release for both items");

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
		//Check if order is in DL status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, "DL");
		sft.assertAll();
	}

	
	/**
	 * This function is to reassign line to new warehouse
	 * @param orderId
	 * @param warehouseId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void reassignToDifferentWarehouse(String orderId, int warehouseId,String style2) throws UnsupportedEncodingException, JAXBException{
		List lines = omsServiceHelper.getOrderLineDBEntry(""+orderId);
		HashMap<String, Object> hmLine1 = (HashMap<String, Object>) lines.get(0);
		HashMap<String, Object> hmLine2 = (HashMap<String, Object>) lines.get(1);
        
		if(hmLine1.get("style_id").toString().equals(style2)){
			orderReleaseId = hmLine1.get("order_release_id_fk").toString();
			lineID = hmLine1.get("id").toString();
			quantity = (int) hmLine1.get("quantity");
		}else if(hmLine2.get("style_id").toString().equals(style2)){
			orderReleaseId =  hmLine2.get("order_release_id_fk").toString();
			lineID =  hmLine2.get("id").toString();
			quantity = (int) hmLine2.get("quantity");
		}
		
		String LineIDAndQuantity[] = { "" + lineID + ":1" };

		OrderReleaseResponse lineMovementResponse = omsServiceHelper.splitOrder
				(orderReleaseId, warehouseId,LineIDAndQuantity, "ML",LineMovementAction.REASSIGN_WAREHOUSE);
		sft.assertEquals(lineMovementResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"There is error in response");
		sft.assertEquals(lineMovementResponse.getStatus().getStatusMessage().toString(),"Items are moved out of release successfully");
		sft.assertEquals(lineMovementResponse.getStatus().getStatusCode(),1033,"status code is not matched");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		newWarehouseId = orderEntry.getOrderReleases().get(1).getWarehouseId();
		sft.assertEquals(newWarehouseId.toString(), ""+warehouseId,"Line is not assigned to new warehouseId:"+warehouseId);
	}

	
	/**
	 * This will cancel line having free item
	 * @param orderId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void cancelLineWithOriginalItem(String orderId) throws UnsupportedEncodingException, JAXBException{
		List lines = omsServiceHelper.getOrderLineDBEntry(""+orderId);
		HashMap<String, Object> hmLine1 = (HashMap<String, Object>) lines.get(0);
		HashMap<String, Object> hmLine2 = (HashMap<String, Object>) lines.get(1);
		
		if(hmLine1.get("style_id").toString().equals(paidStyle)){
			orderReleaseId =  hmLine1.get("order_release_id_fk").toString();
			lineID =  hmLine1.get("id").toString();
			quantity = (int) hmLine1.get("quantity");
		}else if(hmLine2.get("style_id").toString().equals(paidStyle)){
			orderReleaseId = hmLine2.get("order_release_id_fk").toString();
			lineID = hmLine2.get("id").toString();
			quantity = (int) hmLine2.get("quantity");
		}
		
		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine
				(orderReleaseId, login, new String[] {lineID +":"+quantity+""}, 41);
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"Error in response");
		
	}
	
	/**
	 * @param orderId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * This function is to cancel release of paid item
	 */
	public void cancelReleaseWithOriginalItem(String orderId) throws UnsupportedEncodingException, JAXBException{
		List lines = omsServiceHelper.getOrderLineDBEntry(""+orderId);
		HashMap<String, Object> hmLine1 = (HashMap<String, Object>) lines.get(0);
		HashMap<String, Object> hmLine2 = (HashMap<String, Object>) lines.get(1);
		
		if(hmLine1.get("style_id").toString().equals(paidStyle)){
			orderReleaseId =  hmLine1.get("order_release_id_fk").toString();
			lineID =  hmLine1.get("id").toString();
			quantity = (int) hmLine1.get("quantity");
		}else if(hmLine2.get("style_id").toString().equals(paidStyle)){
			orderReleaseId = hmLine2.get("order_release_id_fk").toString();
			lineID = hmLine2.get("id").toString();
			quantity = (int) hmLine2.get("quantity");
		}
		
		OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", "erpadmin", "TestOrder Cancellation");
		sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS,"verify response type after cancel release");
        
		
	}
	
	/**
	 * This function to check if Free item has been added to order
	 * @param orderId
	 * @return
	 */
	public boolean validateFreeItemAddedToOrder(String orderId){
		boolean isFreeItemAdded = false ;
		
		List lines = omsServiceHelper.getOrderLineDBEntry(""+orderId);

		if(lines.size() == 2){
			HashMap<String, Object> hmLine1 = (HashMap<String, Object>) lines.get(0);
			HashMap<String, Object> hmLine2 = (HashMap<String, Object>) lines.get(1);
			
			 if(hmLine1.get("style_id").toString().equals(freeStyle) && 
					 hmLine1.get("unit_price").equals(hmLine1.get("discount"))){//This line contains Free item
				 isFreeItemAdded = true;
			 }else if(hmLine2.get("style_id").toString().equals(freeStyle) && 
					 hmLine2.get("unit_price").equals(hmLine2.get("discount"))){//This line contains free item
				 isFreeItemAdded = true; 
			 }else{
				 isFreeItemAdded = false; 
			 }
		}else{
			isFreeItemAdded = false;
		}
		
		return isFreeItemAdded;
	}
	
	/**
	 * @param OrderId
	 * @param skuId
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * This function to provide release of free item or paid item
	 */
	public String getReleaseOfParticularItem(String OrderId,String skuId) throws UnsupportedEncodingException, JAXBException{
		
		orderEntry = omsServiceHelper.getOrderEntry(OrderId);
		List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
		for(OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
			List<OrderLineEntry> orderLineEntries = orderReleaseEntry.getOrderLines();;
			for(OrderLineEntry orderLineEntry: orderLineEntries){
				System.out.println("temp");
				if(orderLineEntry.getSkuId().toString().equals(skuId)){
					return orderReleaseEntry.getId().toString();
				}
			}
		}
		return  null;
	}


}
