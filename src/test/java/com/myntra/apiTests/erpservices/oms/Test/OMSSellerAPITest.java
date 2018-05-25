package com.myntra.apiTests.erpservices.oms.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.partners.Tests.PartnerPortalService;
import com.myntra.apiTests.erpservices.sellerapis.SellerApiHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.commons.codes.StatusResponse;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class OMSSellerAPITest extends BaseTest {
    static Initialize init = new Initialize("/Data/configuration");
    static Logger log = Logger.getLogger(PartnerPortalService.class);
    String login = "omssellerapitest@myntra.com";
    String password = "123456";

    SellerApiHelper sellerServiceHelper = new SellerApiHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private boolean status;
	private OrderEntry orderEntry;
	private String orderReleaseId;
	private String orderId;
	private String maduraSeller = "18";
    
	//Passed
	@Test(groups = {"Regression"})
    public void cancelItemForValidSku() throws Exception {
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560076, skuId, "", false,false,false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = new String[]{OMSTCConstants.OtherSkus.skuId_473783+":1:"+orderLineEntry1.getSellerId()};
        OrderReleaseResponse orderResponse = omsServiceHelper.cancelItemsV2(orderReleaseId,lineEntry,"OOSC", "CCR");
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);

        //Check Status in OMS
        validateOrderAndLineStatus(orderReleaseId,"F","IC");
    }
	
	//Passed
	@Test(groups = {"Regression"})
    public void cancelItemForValidSkuMultipleLine() throws Exception {
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1",OMSTCConstants.OtherSkus.skuId_895235+":1"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_473783+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller+":1",OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_473783+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller,OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller},"ON_HAND");

        createOrderAndFetchReleaseID(skuId);
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        List<OrderReleaseEntry> orderReleases = orderEntry.getOrderReleases();
        String orderReleaseId1 = orderReleases.get(0).getId().toString();
        String orderReleaseId2 = orderReleases.get(1).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId1);
        List<OrderLineEntry> orderLineEntries2 = omsServiceHelper.getOrderLineEntries(orderReleaseId2);
        
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        OrderLineEntry orderLineEntry2 = orderLineEntries2.get(0);
        String lineEntry[] = new String[]{orderLineEntry1.getSkuId()+":1:"+orderLineEntry1.getSellerId()};
        
        OrderReleaseResponse orderResponse = omsServiceHelper.cancelItemsV2(orderReleaseId1,lineEntry,"OOSC", "CCR");
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);

        lineEntry = new String[]{orderLineEntry2.getSkuId()+":1:"+orderLineEntry2.getSellerId()};
        
        orderResponse = omsServiceHelper.cancelItemsV2(orderReleaseId2,lineEntry,"OOSC", "CCR");
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);

        //Check Status in OMS
        validateOrderAndLineStatus(orderReleaseId1,"F","IC");
        validateOrderAndLineStatus(orderReleaseId2,"F","IC");
    }
    
	//Passed
    @Test(groups = {"Regression"})
    public void cancelOrderNotBelongToSeller() throws Exception {
    	String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831 +":1"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
    	createOrderAndFetchReleaseID(skuId);
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":1:"+orderLineEntry1.getSellerId()+"1"};
        
        OrderReleaseResponse orderResponse = omsServiceHelper.cancelItemsV2(orderReleaseId,lineEntry,"OOSC", "CCR");
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
    }
    
    //Passed
    @Test(groups = {"Regression"})
    public void cancelOrderDuplicate() throws Exception {
    	String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
    	createOrderAndFetchReleaseID(skuId);
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = new String[]{OMSTCConstants.OtherSkus.skuId_473783+":1:"+orderLineEntry1.getSellerId()};
        OrderReleaseResponse orderResponse = omsServiceHelper.cancelItemsV2(orderReleaseId,lineEntry,"OOSC", "CCR");
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
   
        OrderReleaseResponse orderResponse1 = omsServiceHelper.cancelItemsV2(orderReleaseId,lineEntry,"OOSC", "CCR");
        validateError(orderResponse1,8076,"Cancellation not allowed because release in not in Q, RFR, or WP status",StatusResponse.Type.ERROR);
    }
    
    
//Multiple Releases are getting created for same set of Order Items.
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiReadyToDispatchMultipleLine() throws Exception {
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":1",OMSTCConstants.OtherSkus.skuId_895235+":1"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_473783+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller+":1",OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller+":1"}, "ON_HAND");
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_473783+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller,OMSTCConstants.OtherSkus.skuId_895235+":"+OMSTCConstants.WareHouseIds.warehouseId46_BW+":10000:0:"+maduraSeller}, "ON_HAND");
        
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560076, skuId, "", false,false,false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String orderReleaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId2);
        OrderLineEntry orderLineEntry2 = orderLineEntries.get(0);
        
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId()};// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        
        //omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
        //omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV2(orderReleaseId, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String lineEntry2[] ={OMSTCConstants.OtherSkus.skuId_895235+":100.00:10.1:VAT:1:"+orderLineEntry2.getSellerId()};
        orderResponse = omsServiceHelper.updateTaxV2(orderReleaseId2, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber+"', courier_code='"+"ML"
                                          + "' where ID=" + orderReleaseId, "oms");
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber+"', courier_code='"+"ML"
                + "' where ID=" + orderReleaseId2, "oms");
      
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(orderReleaseId,lineEntry);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        
        orderResponse1 = omsServiceHelper.markReadyToDispatchV2(orderReleaseId2,lineEntry2);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    //Passed
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiReadyToDispatch() throws Exception {
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false,false,false,"",false);
        //omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId() };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV2(releaseId, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
      
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    //Known Prod issue-Code commented out because of MI issue
    @Test(enabled=true, groups = {"Regression"})
    public void RtdOrderNotBelongToSeller() throws Exception {
/*    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.AddressIds.ADDRESSID_6140464, skuId, "", false,false,false,"");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        Long releaseId = orderEntry.getOrderReleases().get(0).getId();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);*/
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_3831+":100.00:10.1:VAT:1:19" };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        /*DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        */
        
        String releaseId = "2147590880";
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV2(releaseId, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        /*String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
      */
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateError(orderResponse,1008,"This order does not belong to the given seller",StatusResponse.Type.ERROR);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    
    //Passed
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiReadyToDispatchDuplicate() throws Exception {
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560076, skuId, "", false,false,false,"",false);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId() };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+releaseId+"';", "myntra_oms");
        
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV2(releaseId, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
      
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateSuccess(orderResponse1,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
        
        orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateError(orderResponse1,8249,"Release "+releaseId+" already in Packed status",StatusResponse.Type.ERROR);
    }
    
    
    
  //Passed
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiUpdateTaxV1AndReadyToDispatch() throws Exception {
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false,false,false,"",false);
        //omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId() };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV1(releaseId, lineEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
      
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiUpdateTaxV3SingleLine() throws Exception {
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3831+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false,false,false,"",false);
        //omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId() };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        
        String taxEntry[] = { "26.02:5.5:VAT:100.0:22.0:711.0" }; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV3(releaseId, taxEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
      
        OrderReleaseResponse  orderResponse1 = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    
    @Test(enabled=true, groups = {"Regression"})
    public void sellerApiUpdateTaxV3MultiLine() throws Exception {
    	LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_473783+":1",OMSTCConstants.OtherSkus.skuId_895235+":1" };
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false,false,false,"",false);
        //omsServiceHelper.updateReleaseStatusDB(releaseId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        String releaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        String releaseId2 = orderEntry.getOrderReleases().get(1).getId().toString();
        
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId);
        OrderLineEntry orderLineEntry1 = orderLineEntries.get(0);
        
        orderLineEntries = omsServiceHelper.getOrderLineEntries(releaseId2);
        OrderLineEntry orderLineEntry2 = orderLineEntries.get(0);
        
        String lineEntry[] = { OMSTCConstants.OtherSkus.skuId_473783+":100.00:10.1:VAT:1:"+orderLineEntry1.getSellerId() };// SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        String lineEntry2[] = { OMSTCConstants.OtherSkus.skuId_895235+":100.00:10.1:VAT:1:"+orderLineEntry2.getSellerId() };
        
        DBUtilities.exUpdateQuery("update orders set is_on_hold="+Boolean.FALSE+
        " where id = '"+orderId+"';", "myntra_oms");
        
        String taxEntry[] = { "26.02:5.5:VAT:100.0:22.0:711.0"}; // SkuCode - GRSNTSHT00021 , MaduraSkuCode - 8903880133641 Style id - 267511
        String taxEntry2[]={"27.02:5.5:VAT:100.0:22.0:711.0"};
        
        OrderReleaseResponse  orderResponse = omsServiceHelper.updateTaxV3(releaseId, taxEntry);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        orderResponse = omsServiceHelper.updateTaxV3(releaseId2, taxEntry2);
        Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), "SUCCESS");
        
        String trackingNumber = lmsServiceHelper.getTrackingNumber("ML","46","true", "560068","NORMAL").getTrackingNumberEntry().getTrackingNumber().toString();
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                                          + "' where ID=" + releaseId, "oms");
        DBUtilities.exUpdateQuery("update order_release set tracking_no='" + trackingNumber
                + "' where ID=" + releaseId2, "oms");
        
        orderResponse = omsServiceHelper.markReadyToDispatchV2(releaseId,lineEntry);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        
        orderResponse = omsServiceHelper.markReadyToDispatchV2(releaseId2,lineEntry2);
        validateSuccess(orderResponse,1008,"OrderRelease updated successfully",StatusResponse.Type.SUCCESS);
        
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"PK");
    }
    
	public void validateSuccess(OrderReleaseResponse capturedResponse, int statusCode, String successMessage, Type type){
        Assert.assertEquals(capturedResponse.getStatus().getStatusCode(), statusCode);
        Assert.assertEquals(capturedResponse.getStatus().getStatusMessage(), successMessage);
        Assert.assertEquals(capturedResponse.getStatus().getStatusType(), type.SUCCESS);
	}
	
	public void validateError(OrderReleaseResponse capturedResponse, int statusCode, String successMessage, Type type){
        Assert.assertEquals(capturedResponse.getStatus().getStatusCode(), statusCode);
        Assert.assertEquals(capturedResponse.getStatus().getStatusMessage(), successMessage);
        Assert.assertEquals(capturedResponse.getStatus().getStatusType(), type.ERROR);
	}
	
	public void validateOrderAndLineStatus(String orderReleaseId,String releaseStatus, String lineStatus) throws UnsupportedEncodingException, JAXBException{
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        Assert.assertEquals(orderReleaseEntry.getStatus(),releaseStatus);
        OrderLineEntry orderLineEntry = orderReleaseEntry.getOrderLines().get(0);
        Assert.assertEquals(orderLineEntry.getStatus(), lineStatus);
	}
	
	public void createOrderAndFetchReleaseID(String[] skuId) throws Exception{
        orderId = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560076, skuId, "", false,false,false,"",false);
        //omsServiceHelper.updateReleaseStatusDB(orderId, "WP");
        omsServiceHelper.checkReleaseStatusForOrder(orderId,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderId);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
	}
    
}
