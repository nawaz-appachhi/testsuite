package com.myntra.apiTests.erpservices.oms.dp;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class ValidateIdempotencyOMSActionDP extends BaseTest {
	
    static Initialize init = new Initialize("/Data/configuration");
    static OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	static BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	static End2EndHelper end2EndHelper = new End2EndHelper();
	static WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	static String login = OMSTCConstants.LoginAndPassword.ValidateIdempotencyOMSActionLogin;
	static String uidx ;
	static String password = OMSTCConstants.LoginAndPassword.ValidateIdempotencyOMSActionPassword;
	private static OrderEntry orderEntry;
	private static String orderReleaseId;
	private static String supplyTypeOnHand="ON_HAND";
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    static ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException {
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);

	}
	
    /**
     * @param sku
     * @param warehouseId
     * @param supplyType
     * common function for class to update inventory
     * 
     */
    public static void updateInventory(String sku,int warehouseId,String supplyType){
    	atpServiceHelper.updateInventoryDetailsForSeller(new String[] {sku+":"+warehouseId+":100:0:"+vectorSellerID+":1"},supplyType);
        imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+warehouseId+":100:0:"+vectorSellerID},supplyType);

    }
    
	@Deprecated
    @DataProvider(name = "cancleAlreadyCancleLineDP")
	public static Object[][] cancleAlreadyCancleLineDP(ITestContext testContext)
            throws Exception {
        String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
        Object[][] dataSet = new Object[][] { {orderID} };
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
	}
    
    @Deprecated
    @DataProvider(name = "cancleAlreadyCancleOrderDP")
  	public static Object[][] cancleAlreadyCancleOrderDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
          
          OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
          Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006, "Verify  Cancellation Response Status Code");
          Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS, "Verify  Cancellation Response Status Type");
          Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F", "Verify Status_code after Cancellation");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "cancleAlreadyCancelledReleaseDP")
  	public static Object[][] cancleAlreadyCancelledReleaseDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    
    @DataProvider(name = "packAlreadyPackedOrderDP")
  	public static Object[][] packAlreadyPackedOrderDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "shipAlreadyShippedOrderDP")
  	public static Object[][] shipAlreadyShippedOrderDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
            
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderRFRAlreadyInRFRStatusDP")
  	public static Object[][] markOrderRFRAlreadyInRFRStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "markOrderDLAlreadyInDLStatusDP")
  	public static Object[][] markOrderDLAlreadyInDLStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "markOrderLAlreadyInLStatusDP")
  	public static Object[][] markOrderLAlreadyInLStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
            
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderWPAlreadyInWPStatusDP")
  	public static Object[][] markOrderWPAlreadyInWPStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);

          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
       //   omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
       //   omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
          
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "markOrderCWhichAlreadyInCStatusDP")
  	public static Object[][] markOrderCWhichAlreadyInCStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);

          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}

    @Deprecated
    @DataProvider(name = "markOrderSHFromWPStatusDP")
  	public static Object[][] markOrderSHFromWPStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);

          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        //  omsServiceHelper.updateReleaseStatusDB(releaseId, "C");
        //  omsServiceHelper.validateReleaseStatusInOMS(orderID, "C", 10);
          
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderWPFromSHStatusDP")
  	public static Object[][] markOrderWPFromSHStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);

          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10),"Release is not in SH status");
          
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderPKFromSHStatusDP")
  	public static Object[][] markOrderPKFromSHStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

          omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10),"Release in not in SH status");
          
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderRFRFromWPStatusDP")
  	public static Object[][] markOrderRFRFromWPStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
         // omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
         // omsServiceHelper.validateReleaseStatusInOMS(orderID, "SH", 10);
          
          
          Object[][] dataSet = new Object[][] { { orderReleaseId, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderCFromF_L_RTO_SH_PKStatusDP")
  	public static Object[][] markOrderCFromF_L_RTO_SH_PKStatusDP(ITestContext testContext)
              throws Exception {
    	 String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
    	 String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
         orderEntry = omsServiceHelper.getOrderEntry(orderID);
         OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
         String releaseId = orderReleaseEntry.getId().toString();
         
         String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
         orderEntry = omsServiceHelper.getOrderEntry(orderID2);
         OrderReleaseEntry orderReleaseEntry2 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
         String releaseId2 = orderReleaseEntry2.getId().toString();
         
         String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
         orderEntry = omsServiceHelper.getOrderEntry(orderID3);
         OrderReleaseEntry orderReleaseEntry3 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
         String releaseId3 = orderReleaseEntry3.getId().toString();
         
         String orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
         orderEntry = omsServiceHelper.getOrderEntry(orderID4);
         OrderReleaseEntry orderReleaseEntry4 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
         String releaseId4 = orderReleaseEntry4.getId().toString();
         
         String orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
         orderEntry = omsServiceHelper.getOrderEntry(orderID5);
         OrderReleaseEntry orderReleaseEntry5 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
         String releaseId5 = orderReleaseEntry5.getId().toString();
         
         Object[] arr1 = {releaseId,login,"F"};
         Object[] arr2 = {releaseId2,login,"L"};
         Object[] arr3 = {releaseId3,login,"RTO"};
         Object[] arr4 = {releaseId4,login,"SH"};
         Object[] arr5 = {releaseId5,login,"PK"};
         
         Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
         
 		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
  	}
    //WIP
    @Deprecated
    @DataProvider(name = "markOrderRFRFromWP_PK_SH_DL_CStatusDP")
  	public static Object[][] markOrderRFRFromWP_PK_SH_DL_CStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
            
          OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
          String releaseId = orderReleaseEntry.getId().toString();
         // omsServiceHelper.updateReleaseStatusDB(releaseId, "SH");
         // omsServiceHelper.validateReleaseStatusInOMS(orderID, "SH", 10);
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID2,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID2);
            
          OrderReleaseEntry orderReleaseEntry2 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
          String releaseId2 = orderReleaseEntry2.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId2, "PK");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId2, "PK", 10),"Release is not in PK status");
          
          String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID3,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID3);
            
          OrderReleaseEntry orderReleaseEntry3 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
          String releaseId3 = orderReleaseEntry3.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId3, "SH");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId3, "SH", 10),"Release is not in SH status");
          
          String orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID4,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID4);
            
          OrderReleaseEntry orderReleaseEntry4 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
          String releaseId4 = orderReleaseEntry4.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId4, "DL");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId4, "DL", 10),"Release is not in DL status");
          
          String orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID5,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID5);
            
          OrderReleaseEntry orderReleaseEntry5 = omsServiceHelper.getOrderReleaseEntry(orderEntry.getOrderReleases().get(0).getId().toString());
          String releaseId5 = orderReleaseEntry5.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId5, "C");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId5, "C", 10),"Release is not in C status");
          
          Object[] arr1 = {releaseId,login,"Work in progress"};
          Object[] arr2 = {releaseId2,login,"Packed"};
          Object[] arr3 = {releaseId3,login,"Shipped"};
          Object[] arr4 = {releaseId4,login,"Delivered"};
          Object[] arr5 = {releaseId5,login,"Complete"};
          
          Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
  	}
    
    @Deprecated
    @DataProvider(name = "markOrderWPFromPK_DL_C_SHStatusDP")
  	public static Object[][] markOrderWPFromPK_DL_C_SHStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
          String releaseId = orderReleaseEntry.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId, "PK");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId, "PK", 10),"Release is not in PK status");
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID2,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID2);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          OrderReleaseEntry orderReleaseEntry2 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
          String releaseId2 = orderReleaseEntry2.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId2, "DL");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId2, "DL", 10),"Release is not in DL status");
          
          String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID3,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID3);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          OrderReleaseEntry orderReleaseEntry3 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
          String releaseId3 = orderReleaseEntry3.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId3, "C");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId3, "C", 10),"Release is not in C status");
          
          String orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          omsServiceHelper.checkReleaseStatusForOrder(orderID4,"WP");
          orderEntry = omsServiceHelper.getOrderEntry(orderID4);
          orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
            
          OrderReleaseEntry orderReleaseEntry4 = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
          String releaseId4 = orderReleaseEntry4.getId().toString();
          omsServiceHelper.updateReleaseStatusDB(releaseId4, "SH");
          Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId4, "SH", 10),"Release is not in SH status");
          
//          Long orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.AddressIds.ADDRESS3, skuId, "", false, false, false, "", false);
//          omsServiceHelper.validateReleaseStatusInOMS(orderID5, "WP", 5);
            
//          OrderReleaseEntry orderReleaseEntry5 = omsServiceHelper.getOrderReleaseEntry(orderID5);
//          long releaseId5 = orderReleaseEntry5.getId();
//          omsServiceHelper.updateReleaseStatusDB(releaseId5, "C");
//          omsServiceHelper.validateReleaseStatusInOMS(orderID5, "C", 10);
          
          Object[] arr1 = {releaseId,login,"PK"};
          Object[] arr2 = {releaseId2,login,"DL"};
          Object[] arr3 = {releaseId3,login,"C"};
          Object[] arr4 = {releaseId4,login,"SH"};
        //  Object[] arr5 = {releaseId2,login,"Complete"};
          
          Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 4, 4);
  	}
    
    
    @DataProvider(name = "markOrderPKFromSH_DL_CStatusDP")
  	public static Object[][] markOrderPKFromSH_DL_CStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[] arr1 = {orderID,login,"Shipped","SH"};
          Object[] arr2 = {orderID2,login,"Delivered","DL"};
          Object[] arr3 = {orderID3,login,"Complete","C"};
          
          Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
  	}
    
    
    @DataProvider(name = "markOrderSHFromWP_DL_CStatusDP")
  	public static Object[][] markOrderSHFromWP_DL_CStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[] arr1 = {orderID,login,"WP"};
          Object[] arr2 = {orderID2,login,"DL"};
          Object[] arr3 = {orderID3,login,"C"};
          
          Object[][] dataSet = new Object[][] { arr1,arr2,arr3 };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
  	}
    
    @DataProvider(name = "markOrderDLFromC_FStatusDP")
  	public static Object[][] markOrderDLFromC_FStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[] arr1 = {orderID,login,"C"};
          Object[] arr2 = {orderID2,login,"F"};

          Object[][] dataSet = new Object[][] { arr1,arr2};
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
  	}
    
    @DataProvider(name = "markOrderRTOAlreadyInRTOStatusDP")
  	public static Object[][] markOrderRTOAlreadyInRTOStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[][] dataSet = new Object[][] { { orderID, login } };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
  	}
    
    @DataProvider(name = "markOrderRTOFromL_F_DL_C_PKStatusDP")
  	public static Object[][] markOrderRTOFromL_F_DL_C_PKStatusDP(ITestContext testContext)
              throws Exception {
          String[] skuId = {OMSTCConstants.OtherSkus.skuId_3832+":1"};
          updateInventory(OMSTCConstants.OtherSkus.skuId_3832,OMSTCConstants.WareHouseIds.warehouseId28_GN,supplyTypeOnHand);
          String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          String orderID5 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560067, skuId, "", false, false, false, "", false);
          
          Object[] arr1 = {orderID,login,"L"};
          Object[] arr2 = {orderID2,login,"F"};
          Object[] arr3 = {orderID3,login,"DL"};
          Object[] arr4 = {orderID4,login,"C"};
          Object[] arr5 = {orderID5,login,"PK"};
          
          Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 };
          
  		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
  	}
}
