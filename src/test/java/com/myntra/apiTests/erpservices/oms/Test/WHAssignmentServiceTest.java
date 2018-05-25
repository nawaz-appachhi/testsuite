package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.LineMovementEntry;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.entry.request.BulkLineMovementEntry;
import com.myntra.oms.client.response.LineMovementResponse;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.testbase.BaseTest;

import argo.saj.InvalidSyntaxException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WHAssignmentServiceTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	private String login = OMSTCConstants.LoginAndPassword.WHAssignmentServiceTestLogin;
	private String uidx;
	private String password = OMSTCConstants.LoginAndPassword.WHAssignmentServiceTestPassword;

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    private static Logger log = Logger.getLogger(WHAssignmentServiceTest.class);
	SoftAssert sft;
	private static Long vectorSellerID;
	private static String supplyTypeOnHand = "ON_HAND";
	List<LineMovementEntry> lineMovementEntries=null;
	private String orderID;
	private int atpInventory = 1000;
	private int imsInventory = 1000;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private OrderReleaseResponse orderReleaseResponse;
	private String firstReleaseId;
	private String secondReleaseId;
	private long delayedTime = 5000;
	BulkLineMovementEntry bulkLineMovementEntry = null;
	private OrderReleaseEntry orderReleaseEntry;
	private OrderLineEntry orderLineEntry;
	private String invalidLineId = "-1";
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException {
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
		uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
	}
	
	
	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment","sanity" },description="1. Check warehouse reassignment  of a single line where sku is available in the given warehouse(WH1) 2.Check warehouse reassignment  of a single line where sku is available in the given warehouse(WH2)")
	public void checkWarehouseAssignmentForSKUPresentInMultipleWarehouses() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);
		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();
		String LineIDAndQuantity[] = { "" + lineID + ":1" };
		
		validationOfReleaseAfterWarehouseassign(firstReleaseId, OMSTCConstants.WareHouseIds.warehouseId36_BW);
		
		//Inventory present in 36 and 28 warehouse, it should take 36 warehouse as its optimal
		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		reassigWarehouseSuccess(lineMovementEntries,1032);
		
		validationOfReleaseAfterWarehouseassign(firstReleaseId, OMSTCConstants.WareHouseIds.warehouseId36_BW);
		sft.assertAll();
	}
	



	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment" })
	public void checkReAssignmentSkuIdWhereSkuIdIsNotAvailableInWarehouse() throws Exception {

		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);		
		
		orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String LineIDAndQuantity[] = { "" + lineID + ":1" };
		
		validationOfReleaseAfterWarehouseassign(firstReleaseId, OMSTCConstants.WareHouseIds.warehouseId36_BW);
		
		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+vectorSellerID},supplyTypeOnHand);
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		reassigWarehouseError(lineMovementEntries,8040);
		sft.assertAll();

	}
	
	
	
	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check warehouse reassignment  of multiple lines where sku for both is available in the given warehouse")
	public void checkWarehouseReassignmentOfMultipleLinesWhereSkuForBothIsAvailableInTheGivenWarehouse() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1", OMSTCConstants.OtherSkus.skuId_3839+":1" };

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);
		
		orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String [] LineIDAndQuantity = { "" + lineID + ":1" };
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		
		orderReleaseEntry = orderReleaseEntries.get(1);
		secondReleaseId = orderReleaseEntry.getId().toString();
		orderLine = omsServiceHelper.getOrderReleaseEntry(secondReleaseId).getOrderLines().get(0);
		lineID = "" + orderLine.getId();

		String[] LineIDAndQuantity2 = { "" + lineID + ":1" };
		
        validationOfReleaseAfterWarehouseassign(firstReleaseId,OMSTCConstants.WareHouseIds.warehouseId36_BW);
        validationOfReleaseAfterWarehouseassign(secondReleaseId,OMSTCConstants.WareHouseIds.warehouseId36_BW);
        
		addLineMovementEntryToList(getLineMovementEntry(secondReleaseId,LineIDAndQuantity2));
		
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);
        
        reassigWarehouseSuccess(lineMovementEntries, 1032);
        validationOfReleaseAfterWarehouseassign(firstReleaseId,OMSTCConstants.WareHouseIds.warehouseId28_GN);
        validationOfReleaseAfterWarehouseassign(secondReleaseId,OMSTCConstants.WareHouseIds.warehouseId28_GN);
		sft.assertAll();
		
	}
	

	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check warehouse reassignment  of multiple lines where sku for both is not available in the given warehouse")
	public void checkWarehouseReassignmentOfMultipleLinesWhereSkuForBothIsNotAvailableInTheGivenWarehouse() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+","+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1", OMSTCConstants.OtherSkus.skuId_3839+":1" };

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);
		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String [] LineIDAndQuantity = { "" + lineID + ":1" };
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		
		orderReleaseEntry = orderReleaseEntries.get(1);
		firstReleaseId = orderReleaseEntry.getId().toString();
		orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		lineID = "" + orderLine.getId();

		String[] LineIDAndQuantity2 = { "" + lineID + ":1" };
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity2));
		
		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:"+vectorSellerID},supplyTypeOnHand);
		
		reassigWarehouseError(lineMovementEntries, 8040);
		sft.assertAll();
	}
		
	
	//Passed
	@Test(enabled = true, groups = { "regression" ,"WHAssignment"},description="Check warehouse reassignment  of multiple lines where sku for any one item is not available in the given warehouse")
	public void checkWarehouseReassignmentOfMultipleLinesWhereSkuForAnyOneItemIsNotAvailableInTheGivenWarehouse() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1",
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+","+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1", OMSTCConstants.OtherSkus.skuId_3839+":1" };

		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);
		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		String lineID = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0).getId().toString();

		String [] LineIDAndQuantity = { "" + lineID + ":1" };
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		
		orderReleaseEntry = orderReleaseEntries.get(1);
		firstReleaseId = orderReleaseEntry.getId().toString();
		lineID = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0).getId().toString();

		String[] LineIDAndQuantity2 = { "" + lineID + ":1" };
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity2));
		
		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID,
        		OMSTCConstants.OtherSkus.skuId_3839 +":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":0:0:"+vectorSellerID},supplyTypeOnHand);

		reassigWarehouseError(lineMovementEntries, 8040);
		sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment"},description="Check warehouse assignment of a line with quantity to be reassigned>ordered qty")
	public void checkWarehouseAssignmentOfLineWithQuantityToBeReassignedGreaterThanOrderedQty() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);		
		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String LineIDAndQuantity[] = { "" + lineID + ":2" };

		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		reassigWarehouseError(lineMovementEntries,8036);
		sft.assertAll();
	}
	
	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check warehouse reassignment for lines with pincode not serviceable")
	public void checkWarehouseAssignmentWherePinCodeIsNotServiceable() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_123456,supplyTypeOnHand,vectorSellerID,EnumSCM.Q);		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		
		sft.assertTrue(omsServiceHelper.verifyOrderIsInOnHoldWithReasonId(firstReleaseId, 32, 5), "Release should be onhold with 32");
		
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String LineIDAndQuantity[] = { "" + lineID + ":1" };

		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);
		
		addLineMovementEntryToList(getLineMovementEntry(firstReleaseId,LineIDAndQuantity));
		reassigWarehouseError(lineMovementEntries,8126);
		sft.assertAll();
	}
	
	
	//Passed
	@Test(enabled = true, groups = { "regression","WHAssignment" },description="Check warehouse assignment of a non existent Release id.")
	public void checkWarehouseAssignmentOfANonExistentReleaseId() throws Exception {
		sft=new SoftAssert();
		lineMovementEntries = new ArrayList<>();
		
        atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":1000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":100:0:"+vectorSellerID},supplyTypeOnHand);

		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1"};
		orderReleaseEntries = createOrderHelper(skuId,OMSTCConstants.Pincodes.PINCODE_560068,supplyTypeOnHand,vectorSellerID,EnumSCM.WP);		
		
		OrderReleaseEntry orderReleaseEntry = orderReleaseEntries.get(0);
		firstReleaseId = orderReleaseEntry.getId().toString();
		OrderLineEntry orderLine = omsServiceHelper.getOrderReleaseEntry(firstReleaseId).getOrderLines().get(0);
		String lineID = "" + orderLine.getId();

		String LineIDAndQuantity[] = { "" + lineID + ":1" };

		imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand);
		
		addLineMovementEntryToList(getLineMovementEntry("-9999",LineIDAndQuantity));
		reassigWarehouseError(lineMovementEntries,64);
		sft.assertAll();
	}
	
	
	/**
	 * This is to add lineMovementEntry into arrayList
	 * @param lineMovementEntry
	 */
	public void addLineMovementEntryToList(LineMovementEntry lineMovementEntry){
		lineMovementEntries.add(lineMovementEntry);
	}
	
	/**
	 * This is to get Linemovement Entry
	 * @param releaseId
	 * @param lineIDAndQuantity
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private LineMovementEntry getLineMovementEntry(String releaseId,
			String[] lineIDAndQuantity) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		LineMovementEntry lineMovementEntry = new LineMovementEntry();
		List<OrderLineEntry> linesToBeMoved = new ArrayList<>();
		
		lineMovementEntry.setReleaseId(Long.parseLong(releaseId));
		for(int i=0;i<lineIDAndQuantity.length;i++){
			String[] entry = lineIDAndQuantity[0].split(":");
			OrderLineEntry orderLineEntry = new OrderLineEntry();
			orderLineEntry.setQuantity(Integer.parseInt(entry[1]));
			if(!entry[0].equals(invalidLineId)){
				orderLineEntry.setSkuId(omsServiceHelper.getOrderLineEntry(entry[0]).getSkuId());
			}else{
				orderLineEntry.setSkuId(Long.parseLong(OMSTCConstants.OtherSkus.skuId_3832));//For non existence Line Id putting default sku
			}
			orderLineEntry.setId(Long.parseLong(entry[0]));
			
			linesToBeMoved.add(orderLineEntry);
		}
		lineMovementEntry.setLinesToBeMoved(linesToBeMoved);
		return lineMovementEntry;
	}
	
	
	/**
	 * This helper is to create Order
	 * @param skuID
	 * @param pincode
	 * @param supplyType
	 * @param sellerId
	 * @param expectedStatus
	 * @return
	 * @throws ManagerException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InvalidSyntaxException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws SCMExceptions
	 */
	private List<OrderReleaseEntry> createOrderHelper(String[] skuID,String pincode,String supplyType, long sellerId,String expectedStatus) throws ManagerException, IOException, JAXBException, InvalidSyntaxException, JSONException, ParseException, SCMExceptions{

    	orderID = end2EndHelper.createOrder(login, password,pincode, skuID, "", false, false, false, "", false);
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,expectedStatus);
    	return omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
	}
	
	/**
	 * This is to validate success from reassign warehouse
	 * @param lineMovementEntries
	 * @param statusCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void reassigWarehouseSuccess(List<LineMovementEntry> lineMovementEntries,int statusCode) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		bulkLineMovementEntry = new BulkLineMovementEntry();
		bulkLineMovementEntry.setLineMovementEntries(lineMovementEntries);
		orderReleaseResponse = omsServiceHelper.reAssignWareHouseV2(bulkLineMovementEntry);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS,"There is success in response after reassignment Actual:"+orderReleaseResponse.getStatus().getStatusType());
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(),statusCode,"verify statuscode after reassignment but found "+orderReleaseResponse.getStatus().getStatusCode());
		sft.assertAll();
	}
	
	/**
	 * This is to validate error from reassign warehouse
	 * @param lineMovementEntries
	 * @param statusCode
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void reassigWarehouseError(List<LineMovementEntry> lineMovementEntries,int statusCode) throws UnsupportedEncodingException, JAXBException{
		sft = new SoftAssert();
		bulkLineMovementEntry = new BulkLineMovementEntry();
		bulkLineMovementEntry.setLineMovementEntries(lineMovementEntries);
		orderReleaseResponse = omsServiceHelper.reAssignWareHouseV2(bulkLineMovementEntry);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR,"There should be error in response reassignment Actual:"+orderReleaseResponse.getStatus().getStatusType());
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(),statusCode,"verify statuscode after reassignment but Actual: "+orderReleaseResponse.getStatus().getStatusCode());
		sft.assertAll();
	}
	
	/**
	 * This is to validate warehouse at release and line level
	 * @param releaseId
	 * @param warehouseId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void validationOfReleaseAfterWarehouseassign(String releaseId, Integer warehouseId) throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		sft = new SoftAssert();
		orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
		sft.assertEquals(orderReleaseEntry.getWarehouseId(), warehouseId,"Reassigned warehouse ID is not correct Actual:"+orderReleaseEntry.getWarehouseId());
		sft.assertNotNull(orderReleaseEntry.getCourierCode(), "Courier code should not be null after reassignment");
		//Line validation
		orderLineEntry = omsServiceHelper.getOrderReleaseEntry(releaseId).getOrderLines().get(0);
		sft.assertEquals(""+orderLineEntry.getSourceWarehouseId(), ""+warehouseId,"Source warehouse id should be same as Release warehouseId");
		sft.assertEquals(""+orderLineEntry.getDispatchWarehouseId(), ""+warehouseId,"Dispatch warehouse id should be same as Release warehouseId");
		sft.assertAll();
	}


	
}
