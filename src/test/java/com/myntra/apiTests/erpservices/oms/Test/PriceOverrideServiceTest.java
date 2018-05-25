package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderLineResponse;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class PriceOverrideServiceTest extends BaseTest {
    static Initialize init = new Initialize("/Data/configuration");
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    private static Logger log = Logger.getLogger(PriceOverrideServiceTest.class);
    private static Long vectorSellerID;
	String login = OMSTCConstants.LoginAndPassword.PriceOverrideServiceTestLogin;
	String uidx;
	String password = OMSTCConstants.LoginAndPassword.PriceOverrideServiceTestPassword;
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private String supplyTypeOnHand = "ON_HAND";
	private SoftAssert sft;
	static String orderID, lineID;


    @BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException {
    	vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23, 37);", "myntra_oms");
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
	}

    public void updateInventory(String sku,int warehouseId,String supplyType){
    	atpServiceHelper.updateInventoryDetailsForSeller(new String[] {sku+":"+warehouseId+":100:0:"+vectorSellerID+":1"},supplyType);
        imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+warehouseId+":100:0:"+vectorSellerID},supplyType);

    }
    
	/**
	 * Price override of a line with quantity 1 And Payment instrument is
	 * CashBack
	 * 
	 * @throws Exception
	 */
    @Deprecated
	@Test(priority = 1, enabled = false, description = "Price Override Of a Line with Single Quantity and Payment Method is CashBack",groups={"smoke", "regression","priceoverride"})
	public void priceOverrideOfASingleLineQuantity_CB() throws Exception {
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		createOrderAndDoPriceOverrideSuccess(801, 0,skuId, "750.00", 48.0D);
		assertEquals(end2EndHelper.getCashBackPoints(uidx), 48.0D,"Verify CashBack Amount should be 49.0");
	}

	@Test(priority = 2, enabled = true, groups = { "priceoverride","regression" }, description = "Price OverRide Of a Line That is already refunded")
	public void priceOverRideForALineThatAlreadyRefunded() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		createOrderAndDoPriceOverrideSuccess(801, 0,skuId, "750.00", 98.0D);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		String lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
		OrderLineResponse orderLineResponse = omsServiceHelper.priceOverride(lineID, "725.00", uidx,
				"Price found on the tag was lesser." + orderID + " Line ID : " + lineID);

		sft.assertEquals(orderLineResponse.getStatus().getStatusCode(), 8045,"Verify error code");
		sft.assertEquals(orderLineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR,"Verify error response");
		sft.assertEquals(orderLineResponse.getStatus().getStatusMessage(),
				"Refund already issued for this entity id.");
		sft.assertAll();
	}

    @Deprecated
	@Test(priority = 3, enabled = false, groups = {"priceoverride","regression" }, description = "Cancelation After Price Override should not refund the already refunded PriceOverride Amount")
	public void cancellationAfterPriceOverrideOfAsingleLineQuantity() throws Exception {
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		createOrderAndDoPriceOverrideSuccess(801, 0,skuId, "750.00", 98.0D);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
		System.out.println(lineID+" "+orderID);
		omsServiceHelper.cancelLine(orderReleaseId, uidx, new String[] { "" + lineID + ":2" }, 41);
		end2EndHelper.sleep(15000L);
		assertEquals(end2EndHelper.getCashBackPoints(uidx), 801D, "Verify CashBack Amount should be 801.0");
	}

	@Test(priority = 4, enabled = true, groups = {"priceoverride","regression" }, description = "Price Override Of a Single Line with Single Quantity and Payment methods are CashBack+Loyaltypoint+COD")
	public void priceOverrideOfASingleLineQuantity_COD_CB_LP() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		End2EndHelper end2EndHelper = new End2EndHelper();

		createOrderAndDoPriceOverrideSuccess(501, 100,skuId, "750.00", 49.0D);
		sft.assertEquals(end2EndHelper.getCashBackPoints(uidx), 42.88D, 0.02D, "Verify CashBack Amount should be 0.02");
		sft.assertEquals(end2EndHelper.getLoyaltyPoints(uidx), 13,"Verify loyality points");
		sft.assertAll();
	}


	@Test(priority = 5, enabled = true, groups = {"priceoverride","regression" }, description = "Price Override Of a COD Order. Price Overide refund should go to CashBack Account")
	public void priceOverrideOfASingleLineQuantity_COD() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		createOrderAndDoPriceOverrideSuccess(0, 0, skuId, "750.00", 49.0D);
		sft.assertEquals(end2EndHelper.getCashBackPoints(uidx), 49.0D, "Verify CashBack Amount should be 49.0");
		sft.assertAll();
	}
	
	@Test(priority = 6, enabled = true,groups = { "priceoverride","regression" }, description = "Price Override Of a Line with Multiple Quantity- Price Overide will happen for all the quantities")
	public void priceOverRideForALineWithMultipleQuantity() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":4" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
        createOrderAndDoPriceOverrideSuccess(0, 0, skuId, "750.00", 196.0D);
        sft.assertEquals(end2EndHelper.getCashBackPoints(uidx), 196.0D, "Verify CashBack Amount should be 196.0");
        sft.assertAll();
	}
	
	@Test(priority = 7, enabled = true, groups = {"priceoverride","regression" }, description = "Check Price Override by putting the amount greater than the Price of the Item")
	public void priceOverRideForALineWithHigherValue() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		OrderLineResponse orderLineResponse = createOrderAndDoPriceOverrideFailure(0, 0, 1598, skuId, "800.00", 196.0D);
		sft.assertEquals(orderLineResponse.getStatus().getStatusCode(), 8048,"Verify status code");
		sft.assertEquals(orderLineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR,"verify response error");
		sft.assertEquals(orderLineResponse.getStatus().getStatusMessage(),
				"Refund value is negative or more than the original charged amount.");
		sft.assertAll();
	}

	@Test(priority = 8, enabled = true, groups = {"priceoverride","regression" }, description = "Check Price Override by putting -ve amount")
	public void priceOverRideForALineWithNegetiveValue() throws Exception {
		sft = new SoftAssert();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2" };
		updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
		OrderLineResponse orderLineResponse  = createOrderAndDoPriceOverrideFailure(0, 0, 1598, skuId, "-725.00", 196.0D);

		sft.assertEquals(orderLineResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR,"verify response error");
		sft.assertEquals(orderLineResponse.getStatus().getStatusMessage(),
				"Refund value is negative or more than the original charged amount.");
		sft.assertAll();
	}

	@Deprecated
	@Test(priority = 9, enabled=false, groups = {"priceoverride","regression" }, description = "Return a Price Override Item. Return Refund should extract the price override amount from total amount")
	public void returnOfAPriecOverRideItem() throws Exception {
		RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
		String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
		createOrderAndDoPriceOverrideSuccess(801, 0, skuId, "750.00", 49.0D);
		assertEquals(end2EndHelper.getCashBackPoints(uidx), 49.0D, "Verify CashBack Amount should be 49.0");
		DBUtilities.exUpdateQuery("update item set item_status='SHIPPED', order_id='"+ orderID +"', bin_id=271521, `last_modified_on`=now() where id='91000007';","wms");
		DBUtilities.exUpdateQuery("UPDATE order_release SET status_code = 'DL' WHERE order_id_fk="+ orderID+";","myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE order_release SET delivered_on=NOW() WHERE order_id_fk="+ orderID +";","myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE order_line SET status_code='D' WHERE order_id_fk="+ orderID +";","myntra_oms");
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineID, 1, ReturnMode.PICKUP, 27L);
		String returnID = returnResponse.getData().get(0).getId().toString();
        end2EndHelper.sleep(60000L);
		end2EndHelper.completeOrderInRMS(returnID);
		end2EndHelper.sleep(60000L);
		assertEquals(end2EndHelper.getCashBackPoints(uidx), 799.0D, "Refunded to CashBack Account 799.0");
	}

	@Test(priority = 10, enabled = false, groups = {"priceoverride","regression" }, description = "Return of Price override Exchange Item. Return Refund should extract the price overide amount from total amount ")
	public void returnOfAPriceOverRidedExchangedItem() throws Exception{
		sft = new SoftAssert();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":1" };
        updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
        createOrderAndDoPriceOverrideSuccess(801, 0, skuId, "750.0", 49.0D);
        sft.assertEquals(end2EndHelper.getCashBackPoints(uidx), 49.0D, "Verify CashBack Amount should be 49.0");
        DBUtilities.exUpdateQuery("update item set item_status='SHIPPED', order_id="+ orderID +", bin_id=271521, `last_modified_on`=now() where id='91000007';","wms");
        DBUtilities.exUpdateQuery("UPDATE order_release SET status_code = 'DL' WHERE order_id_fk="+ orderID+";","myntra_oms");
        DBUtilities.exUpdateQuery("UPDATE order_release SET delivered_on=NOW() WHERE order_id_fk="+ orderID +";","myntra_oms");
        DBUtilities.exUpdateQuery("UPDATE order_line SET status_code='D' WHERE order_id_fk="+ orderID +";","myntra_oms");
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(lineID, 1, ReturnMode.PICKUP, 27L);
        String returnID = returnResponse.getData().get(0).getId().toString();
        end2EndHelper.sleep(60000L);
        end2EndHelper.completeOrderInRMS(returnID);
        end2EndHelper.sleep(60000L);
        sft.assertEquals(end2EndHelper.getCashBackPoints(uidx), 799.0D, "Refunded to CashBack Account should be 799.0");
        sft.assertAll();
	}

	
	
	private void createOrderAndDoPriceOverrideSuccess(int cashBack, int loyalty, String[] skuId,
			String overridePrice, double diffPrice) throws Exception {
		end2EndHelper.updateloyalityAndCashBack(uidx, loyalty * 2, cashBack);

		orderID = end2EndHelper.createOrder(login, "123456", "6130557", skuId, "", true, true,false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		lineID = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId().toString();

		OrderLineResponse orderLineResponse = omsServiceHelper.priceOverride(lineID, overridePrice, login,
				"Price found on the tag was lesser." + orderReleaseId + " Line ID : " + lineID);

		assertEquals(orderLineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
		assertEquals(orderLineResponse.getStatus().getStatusCode(), 1012);
		assertEquals(orderLineResponse.getData().get(0).getPriceMismatchRefund(), diffPrice);
		
		OrderLineEntry orderLineEntry = orderLineResponse.getData().get(0);
		Assert.assertNotNull(orderLineEntry.getPriceMismatchRefundPpsId());
		Assert.assertNotEquals(orderLineEntry.getPriceMismatchRefundPpsId(), "",
				"Price Mismatch Refund PPS ID should not be Empty");
	}

	private OrderLineResponse createOrderAndDoPriceOverrideFailure(int cashBack, int loyalty, int cod, String[] skuId,
			String overridePrice, double diffPrice) throws Exception {
		end2EndHelper.updateloyalityAndCashBack(uidx, loyalty * 2, cashBack);
		orderID = end2EndHelper.createOrder(login, "123456", "6130557", skuId, "", true, true,false, "",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
		lineID = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId().toString();

		OrderLineResponse orderLineResponse = omsServiceHelper.priceOverride(lineID, overridePrice, uidx,
				"Price found on the tag was lesser." + orderReleaseId + " Line ID : " + lineID);
		return orderLineResponse;
	}
}
