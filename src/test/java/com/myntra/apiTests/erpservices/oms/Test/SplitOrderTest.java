package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lms.client.response.OrderTrackingResponse;
import com.myntra.lms.client.response.TrackingNumberResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.common.enums.LineMovementAction;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SplitOrderTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
    private String login = "gadem1985@gmail.com";
	private String password = "1234554321";
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(OMSServiceHelper.class);
	
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();	
	String orderID;
	private boolean orderStatus;
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntry;
	private OrderLineEntry orderLine;
	private OrderLineEntry orderLine1;
	private int wareHouseId;
	private String corrierCode;
	private String delCoreOrdersRelese;
	private List<OrderReleaseResponse> orderReleaseResponse;
	private OrderReleaseResponse orderReleaseResponse2;
	private Date CustomerPromiseTimeAfter;
	private Date customerPromiseTimeBefore;
	private String lineID;
	private int lineID2;
	private String delPickupAdditionInfo;
	private Integer newReAssignedWarehouse;
	private Date customerPromiseTimeAfter;
	private Date customerPromiseTimeAfter1;
	private String wareHouseIdNew;
	private int newWarehouseId;
	private String wareHouseIdBefore;
	private String orderReleaseId;
	private SoftAssert sft;
	 
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split with action as reassign WH when courier is not provided.")
	public void checkManualSplitWithActionAsReassignWarehouseWhenCourierIsNotProvided() throws Exception{
		sft = new SoftAssert();
		
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		Integer wareHouseId = orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = "";  //String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);

		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		String LineIDAndQuantity[] = { "" + lineID + ":1"};
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "courier code not found in the input","Status Message should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
		sft.assertAll();
		
	}
   
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line when no warehouse is provided.")
	public void checkManualSplitOfAsingleLineWhenNoWarehouseIsProvided() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_159821+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_159821+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_159821+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		Integer wareHouseId = 0;
		log.info("warehouseId :" + wareHouseId);
		String corrierCode =String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);

		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		String LineIDAndQuantity[] = { "" + lineID + ":1"};
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Cannot move all lines out of the current release");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
		sft.assertAll();
		
	}
	
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line when same warehouse is provided.")
	public void checkManualSplitOfAsingleLineWhenSameWarehouseIsProvided() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_159821+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_159821+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_159821+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		Integer wareHouseId = orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		String LineIDAndQuantity[] = { "" + lineID + ":1"};
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Cannot move all lines out of the current release");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
		sft.assertAll();
		
	}
	
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split when different warehouse id is  provided (action is wh reassignment).")
	public void checkManualSplitWhenDifferentWarehouseIdIsProvided() throws Exception{
		sft = new SoftAssert();
		String orderID;
   		String skuId[] = { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1+":1"};
   		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+","+OMSTCConstants.WareHouseIds.warehouseId1_BA+":1000:0:"+vectorSellerID},"ON_HAND");
        
   		HashMap<String, int[]> inventoryCountInATPBeforePlacingOrder = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 });
   		int[] blockCountATP = inventoryCountInATPBeforePlacingOrder.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int ATPInvtCountBeforePlacingOrder = blockCountATP[0];
   		int blockCountATPBeforePlacingOrder = blockCountATP[1];
   		
   		HashMap<String, int[]> inventoryCountInIMSBeforPlacingOrder = imsServiceHelper.getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 }, "36", "1", ""+vectorSellerID);
   		int[] blockCountIMS = inventoryCountInIMSBeforPlacingOrder.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int IMSInvtCountBeforePlacingOrder = blockCountIMS[0];
   		int blockCountIMSBeforePlacingOrder = blockCountIMS[1];
           
   		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
   		
   		end2EndHelper.sleep(10000l);
   		
   		HashMap<String, int[]> inventoryCountInATPAfterPlacingOrder = atpServiceHelper.getAtpInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 });
   		int[] blockCountATPAfterPlacingOrder = inventoryCountInATPAfterPlacingOrder.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int ATPInvtCountAfterPlacingOrder = blockCountATPAfterPlacingOrder[0];
   		int blockCountATPAfterPlacing = blockCountATPAfterPlacingOrder[1];
           
   		HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrder = imsServiceHelper.getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 }, "36", "1", ""+vectorSellerID);
   		int[] blockCountIMSAfter = inventoryCountInIMSAfterPlacingOrder.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int IMSInvtCountAfterPlacingOrder = blockCountIMSAfter[0];
   		int blockCountIMSAfterPlacingOrder = blockCountIMSAfter[1];
   		
   		HashMap<String, int[]> inventoryCountInIMSAfterPlacingOrderWarehouse1 = imsServiceHelper.getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 }, "1", "1", ""+vectorSellerID);
   		int[] blockCountIMSAfterWarehouse1 = inventoryCountInIMSAfterPlacingOrderWarehouse1.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int blockCountIMSAfterPlacingOrderWarehouse1 = blockCountIMSAfterWarehouse1[1];
           
   		sft.assertEquals(ATPInvtCountBeforePlacingOrder, ATPInvtCountAfterPlacingOrder,"Inventory should be the same values.");
   		log.info("blockCountATPBeforePlacingOrder :"+blockCountATPBeforePlacingOrder);
   		log.info("blockCountATPAfterPlacing :"+blockCountATPAfterPlacing);
   		sft.assertEquals(blockCountATPBeforePlacingOrder+1, blockCountATPAfterPlacing,"Inventory should be the same values.");
          
   		log.info("IMSInvtCountBeforePlacingOrder :"+IMSInvtCountBeforePlacingOrder);
   		log.info("IMSInvtCountAfterPlacingOrder :"+IMSInvtCountAfterPlacingOrder);
           
   		log.info("blockCountIMSBeforePlacingOrder :"+blockCountIMSBeforePlacingOrder);
   		log.info("blockCountIMSAfterPlacingOrder :"+blockCountIMSAfterPlacingOrder);
           
   		sft.assertEquals(IMSInvtCountBeforePlacingOrder, IMSInvtCountAfterPlacingOrder,"Inventory should be the same values.");
   		sft.assertEquals(blockCountIMSBeforePlacingOrder+1, blockCountIMSAfterPlacingOrder,"Block order count should be the same values.");
   		
   		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
   		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
   		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
   		  
   		log.info("Wharehouse Id :"+ orderReleaseEntry.get(0).getWarehouseId());
   		Integer wareHouseId = 1;
   		log.info("warehouseId :" + wareHouseId);
   		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
   		log.info("corrierCode :" + corrierCode);
   		
   		String lineID = "" + orderLine.getId();
   		log.info("Line Id :" + orderLine.getId());
   		String LineIDAndQuantity[] = { "" + lineID + ":1"};
   	
   		DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='1000',blocked_order_count=0 where sku_id ='"+orderLine.getSkuId()+"' "
   				+ "and warehouse_id in ("+wareHouseId+") and seller_id='"+vectorSellerID+"';","myntra_ims");
        
   		
   		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.REASSIGN_WAREHOUSE);
   		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
   		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
   		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
   		
   	    End2EndHelper.sleep(5000L);
   		// Verify Inventory After REASSIGN_WAREHOUSE
          
   		HashMap<String, int[]> inventoryCountInIMSAfterReassignWarehouse = imsServiceHelper.getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 }, "36", "1", ""+vectorSellerID);
   		int[] blockCountAfetreIMSReAssign = inventoryCountInIMSAfterReassignWarehouse.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
		int IMSInvtCountAfterReassigine = blockCountAfetreIMSReAssign[0];
		int blockCountIMSAfterReassigine = blockCountAfetreIMSReAssign[1];
           
		log.info("blockCountIMSAfterPlacingOrder :"+blockCountIMSAfterPlacingOrder);
		log.info("blockCountIMSAfterReassign :"+blockCountIMSAfterReassigine);
           
           
		sft.assertEquals(IMSInvtCountAfterPlacingOrder, IMSInvtCountAfterReassigine,"Both the IMS Block count should be the same.");
		sft.assertEquals(blockCountIMSAfterPlacingOrder-1, blockCountIMSAfterReassigine,"After Reassign Block count should decrease.");
        
   		HashMap<String, int[]> inventoryCountInIMSAfterReassignOrderWarehouse1 = imsServiceHelper.getIMSInvAndBlockOrderCount(new String[] { OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1 }, "1", "1", ""+vectorSellerID);
   		int[] blockCountIMSAfterReassignWarehouse1 = inventoryCountInIMSAfterReassignOrderWarehouse1.get(OMSTCConstants.SplitOrderTest.checkManualSplitWhenDifferentWarehouseIdIsProvided_ITEM1);
   		int blockCountIMSAfterReassignOrderWarehouse1 = blockCountIMSAfterReassignWarehouse1[1];
   		sft.assertEquals(blockCountIMSAfterPlacingOrderWarehouse1+1, blockCountIMSAfterReassignOrderWarehouse1,"After Reassign Block count should decrease.");
           
   		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
   		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
   		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
   		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),1, "Items are moved out of release successfully");
   		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderLines().get(0).getId(), orderLine.getId(),"Verify order line which has been assigned");
   		sft.assertAll();
   		

	}
	@Test(enabled = true,groups={"regression","splitorder","sanity"},description="Check manual split of a single line from an order consisting of multiple lines.")
	public void checkManualSplitOfASingleLineFromAnOrderConsistingOfMultipleLines() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":1",OMSTCConstants.OtherSkus.skuId_3836+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
	    OrderLineEntry orderLine1 = orderReleaseEntry.get(0).getOrderLines().get(1);
		
		Integer wareHouseId = orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		
		String LineIDAndQuantity[] = { "" + orderLine.getId() + ":1"};
	    
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),wareHouseId.intValue(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderLines().get(0).getId(), orderLine.getId());
		sft.assertEquals(orderReleaseResponse.getData().get(1).getWarehouseId().intValue(),wareHouseId.intValue(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getData().get(1).getOrderLines().get(0).getId(), orderLine1.getId(),"Verify orderLine after reassignment");
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of an order having multiple order lines to a different wh (moving 2 lines of a 3 line order to a diff wh).")
	public void checkManualSplitOfAnOrderHavingMultipleOrderLinesToAdifferentWarehouse() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3830+":1",OMSTCConstants.OtherSkus.skuId_3836+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
        
		
		log.info("orderReleaseEntry.get(0).getOrderId() :"+orderReleaseEntry.get(0).getOrderId());
		log.info("orderReleaseEntry.get(0).getOrderDate() :"+orderReleaseEntry.get(0).getOrderDate());
		log.info("orderReleaseEntry.get(0).isGift() :"+orderReleaseEntry.get(0).getOrderId());
		log.info("orderReleaseEntry.get(0).getOnHold() :"+orderReleaseEntry.get(0).getOnHold());
		
		log.info("orderReleaseEntry.get(0).getStoreId() :"+orderReleaseEntry.get(0).getStoreId());
		log.info("orderReleaseEntry.get(0).getWarehouseId() :"+orderReleaseEntry.get(0).getWarehouseId());
		log.info("orderReleaseEntry.get(0).getOrderLines().get(0).getQuantity():"+orderReleaseEntry.get(0).getOrderLines().get(0).getQuantity());
		log.info("orderReleaseEntry.get(0).getOrderLines().get(0).getSkuId() :"+orderReleaseEntry.get(0).getOrderLines().get(0).getSkuId());
		
		
		
		Integer wareHouseId =1;//orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		
		String LineIDAndQuantity[] = { "" + orderLine.getId() + ":1"};
	
		String delCoreOrdersRelese = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
		DBUtilities.exUpdateQuery(delCoreOrdersRelese,"myntra_wms");
		String delPickupAdditionInfo = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
		DBUtilities.exUpdateQuery(delPickupAdditionInfo,"myntra_wms");
		imsServiceHelper.updateInventoryForSeller(new String[]{orderLine.getSkuId()+":"+wareHouseId+":1000:0:"+vectorSellerID},"ON_HAND");
        
		List<OrderReleaseResponse> orderReleaseResponseList=omsServiceHelper.splitOrderV2(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		OrderReleaseResponse orderReleaseResponse = orderReleaseResponseList.get(0);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		//Assert.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),1, "Second Line assigned to Warehouse 1");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderLines().get(0).getId(), orderLine.getId());
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of an order having multiple order lines to the same warehaouse (moving 2 lines of a 3 line order to the same warehouse).")
	public void checkManualSplitOfAnOrderHavingMultipleOrderLinesToSameWarehouse() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = {  OMSTCConstants.OtherSkus.skuId_3830+":4", OMSTCConstants.OtherSkus.skuId_3856+":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
				OMSTCConstants.OtherSkus.skuId_3856+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
				OMSTCConstants.OtherSkus.skuId_3856+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		log.info("Size :"+orderReleaseEntry.get(0).getOrderLines().size());
		
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		OrderLineEntry orderLine1 = orderReleaseEntry.get(0).getOrderLines().get(1);
		
		Integer wareHouseId = orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		Integer lineID2=Integer.parseInt(lineID);
		int secondLineId=lineID2+1;
	
		String LineIDAndQuantity[] = { "" + lineID + ":1","" + secondLineId + ":1"};
	
		String delPickupAdditionInfo = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
		//DBUtilities.exUpdateQuery(delPickupAdditionInfo,"myntra_lms");
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
	   
	   
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),wareHouseId.intValue(), "Items are moved out of release successfully");
		sft.assertAll();
	}

	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to a different wh (moving 2 qty out of 3 to a different wh)")
	public void checkManualSplitOfASingleLineWithQuantityGreaterThanOneToADiffrentWarehouse() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":3"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		
		Integer wareHouseId =28; //orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		
	
		String LineIDAndQuantity[] = { "" + lineID + ":2"};
	
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),orderReleaseEntry.get(0).getWarehouseId().intValue(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderLines().get(0).getOrderId(), orderLine.getOrderId());
		sft.assertEquals(orderReleaseResponse.getData().get(1).getWarehouseId().intValue(),orderReleaseEntry.get(0).getWarehouseId().intValue(), "Items are moved out of release successfully");
		sft.assertAll();
		
	}
	
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to the same wh (moving 2 qty out of 3 to the same wh)")
	public void checkManualSplitOfASingleLineWithQuantityGreaterThanOneToTheSameWarehouse() throws Exception{
		sft = new SoftAssert();
		WMSServiceHelper wmsServiceHelper=new WMSServiceHelper();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":3"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		Integer wareHouseId =orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		
	
		String LineIDAndQuantity[] = { "" + lineID + ":2"};
		wmsServiceHelper.pushOrderToWave(orderReleaseId);
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getWarehouseId().intValue(),wareHouseId.intValue(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderLines().get(0).getOrderId(), orderLine.getOrderId());
		sft.assertAll();
	
	}

	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to the same wh(moving all quantity to the same warehouse).")
	public void checkManualSplitOfASingleLineWithQuantityGreaterThanOneToTheSameWarehouseMovingAllQuantityToTheSameWarehouse() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":3"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		
		Integer wareHouseId = orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		String LineIDAndQuantity[] = { "" + lineID + ":3",};
	
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Cannot move all lines out of the current release");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
		sft.assertAll();
		
	}
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 when no wh id is provided(moving some quantity to the same wh).")
	public void checkManualSplitOfASingleLineWithQuantityGreaterThanOneNoWarehouseIdIsProvided() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":3"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		
		Integer wareHouseId = 0;//orderReleaseEntry.get(0).getWarehouseId();
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		String LineIDAndQuantity[] = { "" + lineID + ":2"};
	
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 1033,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getData().get(0).getOrderId().longValue(), orderID,"Both the orderID id's should be the same.");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to the same wh(moving all quantity to the diffrent warehouse).")
	public void checkManualSplitOfASingleLineWithQuantityGreaterThanOneToTheDiffrentWarehouseMovingAllQuantityToThediffrentWarehouse() throws Exception{
		sft = new SoftAssert();
		String orderID;
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":3"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
        
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
		
		Integer wareHouseId = 1;
		log.info("warehouseId :" + wareHouseId);
		String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
		log.info("corrierCode :" + corrierCode);
		
		String lineID = "" + orderLine.getId();
		log.info("Line Id :" + orderLine.getId());
		
	
		String LineIDAndQuantity[] = { "" + lineID + ":3"};
	
		
		OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
		log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
		log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
		log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
		
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Cannot move all lines out of the current release");
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
		sft.assertAll();
		
	}
	
	
		@Deprecated
		@Test(enabled = false,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to the same wh(moving all quantity to the diffrent warehouse).")
		public void courierCodeChangeToBeAllowedInAllStates() throws Exception{
			
			DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3831", "myntra_atp");
		 	DBUtilities.exUpdateQuery("UPDATE wh_inventory SET inventory_count=100000,blocked_order_count=0 where sku_id in (3831) and warehouse_id in (36);", "ims");
			
		 	String orderID;
			String skuId[] = { "3831:1"};
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	        
			List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
			OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			
			Integer wareHouseId = 1;
			log.info("warehouseId :" + wareHouseId);
			String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
			log.info("corrierCode :" + corrierCode);
			
			TrackingNumberResponse trackingNumberResponse=omsServiceHelper.getTrackingNumber("/ML/19/true/560009/NORMAL");
            log.info("getStatusCode :"+trackingNumberResponse.getStatus().getStatusCode());
            log.info("getStatusMessage :"+trackingNumberResponse.getStatus().getStatusMessage());
            log.info("getTotalCount"+trackingNumberResponse.getStatus().getTotalCount());
            LMSHelper lmsHepler=new LMSHelper();
            
            
            log.info("lmsHepler.getOrderTrackingId"+lmsHepler.getOrderTrackingId("70068565"));
           
            int id= Integer.parseInt(lmsHepler.getOrderTrackingId("70068565"));
            
           OrderTrackingResponse orderTrackingResponse=omsServiceHelper.changeCourierCode(""+70068565, id, "EK");
           log.info("orderTrackingResponse :"+orderTrackingResponse);
            
			
		}
		
		//Passed
		@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check customer promise time is updated after reassignment of warehouse")
		public void checkCustomerPromiseTimeForWarehouseAssignmentForSKUPresentInMultipleWarehouses() throws Exception {
			sft = new SoftAssert();
			String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":1"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
	        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
	        		OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
	        

			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
			omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

			wareHouseIdBefore = String.valueOf(orderReleaseEntry.get(0).getWarehouseId());
			log.info("warehouseIdBefore :" + wareHouseIdBefore);

			lineID = "" + orderLine.getId();

			log.info("Line Id :" + orderLine.getId());
			String LineIDAndQuantity[] = { "" + lineID + ":1" };

			log.info("CustomerPromiseTime Before: "+orderReleaseEntry.get(0).getCustomerPromiseTime());
			customerPromiseTimeBefore = orderReleaseEntry.get(0).getCustomerPromiseTime();
			
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			log.info("warehouseIdAfter :" + wareHouseId);
	     //   Assert.assertEquals(wareHouseId, 36); 
			newWarehouseId = 1 ;
			DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='1000',blocked_order_count=0 where sku_id ='"+orderLine.getSkuId()+"' "
	   				+ "and warehouse_id in ("+newWarehouseId+") and seller_id='"+vectorSellerID+"';","myntra_ims");

			orderReleaseResponse=omsServiceHelper.splitOrderV2(orderReleaseId, newWarehouseId, LineIDAndQuantity, "ML", LineMovementAction.SPLIT_ORDER);
			orderReleaseResponse2 = orderReleaseResponse.get(0);
			
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"verify status response");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusMessage(),"Items are moved out of release successfully");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusCode(),1033,"verify status code");
			
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			wareHouseIdNew = String.valueOf(orderReleaseEntry.get(0).getWarehouseId());
			sft.assertEquals(""+newWarehouseId, wareHouseIdNew,"ReleaseOrder should reassign to new warehouse");

			String orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId().toString();
			String createdOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("created_on").toString();
			String lastUpdatedOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("last_modified_on").toString();
			sft.assertNotEquals(createdOn, lastUpdatedOn,"Verify promiseDate is updated after reassignment");
	        
			sft.assertAll();
		}
		
		//Passed
		@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check customer promise time is updated after reassignment of warehouse")
		public void checkCustomerPromiseTimeForWarehouseAssignmentForSKUPresentInMultipleWarehousesMultipleQty() throws Exception {
			sft = new SoftAssert();
			String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
	        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
	        		OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
	        

			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "",false);
			omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");

			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

			wareHouseIdBefore = String.valueOf(orderReleaseEntry.get(0).getWarehouseId());
			log.info("warehouseIdBefore :" + wareHouseIdBefore);

			lineID = "" + orderLine.getId();

			log.info("Line Id :" + orderLine.getId());
			String LineIDAndQuantity[] = { "" + lineID + ":2" };

			log.info("CustomerPromiseTime Before: "+orderReleaseEntry.get(0).getCustomerPromiseTime());
			customerPromiseTimeBefore = orderReleaseEntry.get(0).getCustomerPromiseTime();
			
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			log.info("warehouseIdAfter :" + wareHouseId);
	     //   Assert.assertEquals(wareHouseId, 36); 
			newWarehouseId = 1 ;
			DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='1000',blocked_order_count=0 where sku_id ='"+orderLine.getSkuId()+"' "
	   				+ "and warehouse_id in ("+newWarehouseId+") and seller_id='"+vectorSellerID+"';","myntra_ims");

			orderReleaseResponse=omsServiceHelper.splitOrderV2(orderReleaseId, newWarehouseId, LineIDAndQuantity, "ML", LineMovementAction.SPLIT_ORDER);
			OrderReleaseResponse orderReleaseResponse2 = orderReleaseResponse.get(0);
			log.info("Status code :"+orderReleaseResponse2.getStatus().getStatusCode());
			log.info("Status Message :"+orderReleaseResponse2.getStatus().getStatusMessage());
			log.info("Status Type :"+orderReleaseResponse2.getStatus().getStatusType());
			
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusMessage(),"Items are moved out of release successfully");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusCode(),1033);
			
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			wareHouseIdNew = String.valueOf(orderReleaseEntry.get(0).getWarehouseId());
			sft.assertEquals(""+newWarehouseId, wareHouseIdNew,"ReleaseOrder should reassign to new warehouse");
			
			String orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId().toString();
			String createdOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("created_on").toString();
			String lastUpdatedOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("last_modified_on").toString();
			sft.assertNotEquals(createdOn, lastUpdatedOn,"Verify promiseDate is updated after reassignment");
	        
	        sft.assertAll();
		}
		
		//Passed
		@Test(enabled = true,groups={"regression","splitorder"},description="Check customer promise time is updated after reassignment of warehouse")
		public void checkCustomerPromiseTimeForManualSplitOfAnOrderHavingMultipleOrderLinesToSameWarehouse() throws Exception{
			sft = new SoftAssert();
			String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":4",OMSTCConstants.OtherSkus.skuId_3856+":4"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
					OMSTCConstants.OtherSkus.skuId_3856+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
	        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
	        		OMSTCConstants.OtherSkus.skuId_3856+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
	        
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	 
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			log.info("Size :"+orderReleaseEntry.get(0).getOrderLines().size());
			
			
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			orderLine1 = orderReleaseEntry.get(0).getOrderLines().get(1);
			
			wareHouseId = 28 ;
			DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='1000',blocked_order_count=0 where sku_id in("+orderLine.getSkuId()+","+orderLine1.getSkuId()+") "
	   				+ "and warehouse_id in ("+wareHouseId+") and seller_id='"+vectorSellerID+"';","myntra_ims");
			
			lineID = "" + orderLine.getId();
			log.info("Line Id :" + orderLine.getId());
			lineID2=Integer.parseInt(lineID);
			int secondLineId=lineID2+1;
		
			String LineIDAndQuantity[] = { "" + lineID + ":1","" + secondLineId + ":1"};
		
			delPickupAdditionInfo = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
			//DBUtilities.exUpdateQuery(delPickupAdditionInfo,"myntra_lms");
			orderReleaseResponse=omsServiceHelper.splitOrderV2(orderReleaseId, wareHouseId, LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
			orderReleaseResponse2 = orderReleaseResponse.get(0);
			
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusCode(), 1033,"Status code should be the same.");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusMessage(), "Items are moved out of release successfully");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
			sft.assertEquals(orderReleaseResponse2.getData().get(0).getWarehouseId().intValue(),wareHouseId, "Items are moved out of release successfully");	
			
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			String orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId().toString();
			String orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
			String customerPromiseForFirstRelease = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("value").toString();
			String customerPromiseForSecondRelease = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId2, "CUSTOMER_PROMISE_TIME").
					get("value").toString();
			sft.assertNotEquals(customerPromiseForFirstRelease, customerPromiseForSecondRelease,"Verify promiseDate is updated after reassignment");
	        
			sft.assertAll();
		}
		
		//Passed
		@Test(enabled = true,groups={"regression","splitorder"},description="Check customer promise time is updated after reassignment of warehouse")
		public void checkCustomerPromiseTimeForManualSplitOfAnOrderHavingMultipleOrderLinesToAdifferentWarehouse() throws Exception{
			sft = new SoftAssert();
			String skuId[] = { OMSTCConstants.OtherSkus.skuId_3836+":1",OMSTCConstants.OtherSkus.skuId_3830+":1"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
					OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
	        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3836+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID,
	        		OMSTCConstants.OtherSkus.skuId_3830+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":1000:0:"+vectorSellerID},"ON_HAND");
	        
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	        
	       
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
		    orderReleaseEntry = orderEntry.getOrderReleases();
			
			orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			
			wareHouseId =1;
			//orderReleaseEntry.get(0).getWarehouseId();
			log.info("warehouseIdReassign :" + wareHouseId);
			corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
			log.info("corrierCode :" + corrierCode);
			
			
			String LineIDAndQuantity[] = { "" + orderLine.getId() + ":1"};
		
			delCoreOrdersRelese = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
			DBUtilities.exUpdateQuery(delCoreOrdersRelese,"myntra_wms");
			String delPickupAdditionInfo = "delete from core_order_release_line where order_release_id in (select id from core_order_release where order_id="+orderID+")";
			DBUtilities.exUpdateQuery(delPickupAdditionInfo,"myntra_wms");
			
			//Update Inventory in Warehouse 36
			String skuId1 = ""+orderLine.getSkuId();
			DBUtilities.exUpdateQuery("update wh_inventory set inventory_count='1000',blocked_order_count=0 where sku_id ='"+orderLine.getSkuId()+"' "
	   				+ "and warehouse_id in ("+wareHouseId+") and seller_id='"+vectorSellerID+"';","myntra_ims");

		    orderReleaseResponse=omsServiceHelper.splitOrderV2(orderReleaseId, wareHouseId, LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
			orderReleaseResponse2 = orderReleaseResponse.get(0);
			
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusCode(), 1033,"Status code should be the same.");
			//Assert.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Items are moved out of release successfully");
			sft.assertEquals(orderReleaseResponse2.getStatus().getStatusType().toString(), "SUCCESS","Status code should be the same.");
			
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			
			sft.assertEquals(orderReleaseEntry.get(0).getWarehouseId().intValue(),wareHouseId, "First Line should move to WarehouseId:36");
			//Assert.assertEquals(orderReleaseEntry.get(1).getWarehouseId().intValue(),1, "Second Line should move to WarehouseId:1");
			
			//Check customer promisetime got updated after reassignment of warehouse
			orderEntry = omsServiceHelper.getOrderEntry(orderID);
			orderReleaseEntry = orderEntry.getOrderReleases();
			String orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId().toString();
			String orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
			String createdOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("created_on").toString();
			String lastUpdatedOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId1, "CUSTOMER_PROMISE_TIME").
					get("last_modified_on").toString();
			log.info(createdOn+" "+lastUpdatedOn);
			sft.assertNotEquals(createdOn, lastUpdatedOn,"Verify promiseDate is updated after reassignment");
	        
			createdOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId2, "CUSTOMER_PROMISE_TIME").
					get("created_on").toString();
			lastUpdatedOn = omsServiceHelper.getOrderReleaseAdditionalInfoDBEntry(orderReleaseId2, "CUSTOMER_PROMISE_TIME").
					get("last_modified_on").toString();
			log.info(createdOn+" "+lastUpdatedOn);
			sft.assertEquals(createdOn, lastUpdatedOn,"Verify promiseDate should not update for release not reassigned");
	        
			sft.assertAll();
			
		}
		
		
		@Test(enabled = true,groups={"regression","splitorder"},description="Check manual split of a single line with quantity greater than 1 to the same wh(moving all quantity to the diffrent warehouse).")
		public void checkManualSplitOfASingleLineWithZeroQuantity() throws Exception{
			sft = new SoftAssert();
			String orderID;
			String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":3"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},"ON_HAND");
	        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID},"ON_HAND");
	        
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false,"",true);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	        
	       
	        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
			List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
			OrderLineEntry orderLine = orderReleaseEntry.get(0).getOrderLines().get(0);
			
			Integer wareHouseId = 1;
			log.info("warehouseId :" + wareHouseId);
			String corrierCode = String.valueOf(orderReleaseEntry.get(0).getCourierCode());
			log.info("corrierCode :" + corrierCode);
			
			String lineID = "" + orderLine.getId();
			log.info("Line Id :" + orderLine.getId());
			
		
			String LineIDAndQuantity[] = { "" + lineID + ":0"};
		
			
			OrderReleaseResponse orderReleaseResponse=omsServiceHelper.splitOrder(orderReleaseId, wareHouseId.intValue(), LineIDAndQuantity, corrierCode, LineMovementAction.SPLIT_ORDER);
			log.info("Status code :"+orderReleaseResponse.getStatus().getStatusCode());
			log.info("Status Message :"+orderReleaseResponse.getStatus().getStatusMessage());
			log.info("Status Type :"+orderReleaseResponse.getStatus().getStatusType());
			
			sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), 8028,"Status code should be the same.");
			sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Error occured while splitting order releases for Order "+orderReleaseId+".");
			sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), "ERROR","Status code should be the same.");
			sft.assertAll();
			
		}
		


}
