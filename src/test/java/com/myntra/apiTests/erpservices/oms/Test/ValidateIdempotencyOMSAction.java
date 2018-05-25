package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.ValidateIdempotencyOMSActionDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OmsCommonResponse;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ValidateIdempotencyOMSAction extends BaseTest{
	
	static Initialize init = new Initialize("/Data/configuration");
    private Logger log = Logger.getLogger(OMSServiceHelper.class);
    private String login = OMSTCConstants.LoginAndPassword.ValidateIdempotencyOMSActionLogin;
    private String uidx;
    private String password = OMSTCConstants.LoginAndPassword.ValidateIdempotencyOMSActionPassword;
    private static Long vectorSellerID;
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
    PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    SoftAssert sft;
	private OrderEntry orderEntry;
	private String orderReleaseId;
    
    @BeforeClass(alwaysRun = true)
    public void testBeforeClass() throws SQLException, IOException, JAXBException {
        DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35, 23,37);", "myntra_oms");
        omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
    }
    
    /**
     * To Validate Response
     * @param orderReleaseResponse
     * @param statusCode
     * @param statusType
     */
    public void validateResponse(OrderReleaseResponse orderReleaseResponse,int statusCode,Type statusType){
    	sft = new SoftAssert();
    	sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), statusCode,"Verify ResponseCode Actual:"+orderReleaseResponse.getStatus().getStatusCode());
    	if(statusType.toString().equalsIgnoreCase("SUCCESS")){
    		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.SUCCESS,"Verify ResponseType should be success Actual:"+orderReleaseResponse.getStatus().getStatusType());
    	}        
    	else if(statusType.toString().equalsIgnoreCase("ERROR")){
    		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType(), Type.ERROR,"Verify ResponseType should be error Actual:"+orderReleaseResponse.getStatus().getStatusType());
    	}
        sft.assertAll();
    }
    

    
    @Deprecated
    @Test(enabled = false, dataProvider="cancleAlreadyCancleLineDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Cancel Already cancelled Line")
    public void cancleAlreadyCancleLine(String orderID) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
     
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderID);
        String lineID = ""+orderLineEntries.get(0).getId();
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {""+orderLineEntries.get(0).getId() +":1"}, 41);

        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
        sft.assertEquals(cancellationRes.getStatus().getStatusMessage(), "Lines are canceled from the release successfully.");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
        
        //Cancel again cancelled Line
        cancellationRes = omsServiceHelper.cancelLine(orderID, login, new String[] {lineID +":1"}, 41);

        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 8076);
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR);
        //sft.assertEquals(cancellationRes.getStatus().getStatusMessage(), "Cancellation not allowed because release in not in 'Q', 'RFR', or 'WP' status");
    }
    
    @Deprecated
    @Test(enabled = false, dataProvider="cancleAlreadyCancleOrderDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Cancel Already Cancel order")
    public void cancleAlreadyCancleOrder(String orderID, String login) throws UnsupportedEncodingException, JAXBException{

    	OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        sft.assertEquals(cancellationRes.getStatus().getStatusCode(), 8053, "Verify  Cancellation Response Status Code. Actual:"+cancellationRes.getStatus().getStatusCode());
        sft.assertEquals(cancellationRes.getStatus().getStatusType(), Type.ERROR, "Release "+orderID+" already in {1} status");
        
    }
    
    @Test(enabled = true, dataProvider="cancleAlreadyCancelledReleaseDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency","sanity"}, description="Cancel Already cancelled order Release")
    public void cancleAlreadyCancelledRelease(String orderID, String login) throws UnsupportedEncodingException, JAXBException, ManagerException{
    	sft=new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
        String orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        validateResponse(cancellationRes,1006,Type.SUCCESS);
        sft.assertEquals(cancellationRes.getData().get(0).getStatus(), "F", "Verify Status_code after CAncellation");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 10),"Release is not in F status");
        //Cancel Already cancelled release
    	cancellationRes = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");
        validateResponse(cancellationRes,8249,Type.ERROR);
        //sft.assertEquals(cancellationRes.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in F status");
        sft.assertAll();
        
    }
    
    @Test(enabled = true, dataProvider="packAlreadyPackedOrderDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Pack Already pack orders")
    public void packAlreadyPackedOrder(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "PK");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "PK", 10),"Release is not in PK status");
        
        
        OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.rtsScan(""+orderReleaseId);
        
        validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in PK status");
        sft.assertAll();
    }
    
    @Test(enabled = true, dataProvider="shipAlreadyShippedOrderDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Ship Already Shipped order.")
    public void shipAlreadyShippedOrder(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "SH");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "SH", 10),"Release is not in SH status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
        OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markReleaseShipped(orderReleaseId);      
        validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in SH status");
        sft.assertAll();
    }
    
    @Deprecated
    @Test(enabled = false, dataProvider="markOrderRFRAlreadyInRFRStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Mark order RFR which are already in RFR status")
    public void markOrderRFRAlreadyInRFRStatus(String releaseId, String login) throws JAXBException, IOException{
        
        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(releaseId);
        omsServiceHelper.updateReleaseStatusDB(releaseId, "RFR");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId, "RFR", 10),"Release is not in RFR status");

    	OrderReleaseResponse omsCommonResponse = omsServiceHelper.markOrderRFRInOMS(releaseId);
    	sft.assertEquals(omsCommonResponse.getStatus().getStatusCode(), 8249, "Verify  Release Response Status Code");
        sft.assertEquals(omsCommonResponse.getStatus().getStatusType(), Type.ERROR, "Verify  Release Response Status Type should be Error Actual:"+omsCommonResponse.getStatus().getStatusType());
        sft.assertEquals(omsCommonResponse.getStatus().getStatusMessage(), "Release "+releaseId+" already in RFR status");
        
    }
    
    @Deprecated
    @Test(enabled = false, dataProvider="markOrderWPAlreadyInWPStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="Mark order WP which are already in WP status.")
    public void markOrderWPAlreadyInWPStatus(String releaseId, String login) throws JAXBException, IOException{
    	
    	OmsCommonResponse omsCommonResponse = omsServiceHelper.markOrderWPInOMS(releaseId);
   	    sft.assertEquals(omsCommonResponse.getStatus().getStatusCode(), 8249, "Verify  Release Response Status Code");
        sft.assertEquals(omsCommonResponse.getStatus().getStatusType(), Type.ERROR, "Verify  Release Response Status Type should be Error Actual:"+omsCommonResponse.getStatus().getStatusType());
        sft.assertEquals(omsCommonResponse.getStatus().getStatusMessage(), "Release "+releaseId+" already in WP status");
    	
    }
    
    @Test(enabled = true, dataProvider="markOrderCWhichAlreadyInCStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Completed Which are Already In Complete Status")
    public void markOrderCompletedWhichAlreadyInCompleteStatus(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "C");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "C", 10),"Release is not in C status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
    	OrderReleaseResponse orderReleaseResponse = omsServiceHelper.markDeliveredOrderRelease(orderReleaseId,login);
   	    validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in C status");
        sft.assertAll();    
    }
    
    @Test(enabled = true, dataProvider="markOrderLAlreadyInLStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Lost which are Already In Lost Status")
    public void markOrderLostAlreadyInLostStatus(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();        
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "L");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "L", 10),"Release in not in L status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markOrderReleaseLost(orderReleaseId, login, 23);      
        validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in L status");
        sft.assertAll();
    }
    
    @Test(enabled = true, dataProvider="markOrderRTOAlreadyInRTOStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder RTO which are Already In RTO Status")
    public void markOrderRTOAlreadyInRTOStatus(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
    	omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "RTO");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "RTO", 10),"Release is not in RTO status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markOrderReleaseRTO(orderReleaseId, login);      
        validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in RTO status");
        sft.assertAll();    
    }
    
    @Test(enabled = true, dataProvider="markOrderDLAlreadyInDLStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder DL which are Already In DL Status")
    public void markOrderDLAlreadyInDLStatus(String orderID, String login) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        omsServiceHelper.updateReleaseStatusDB(orderReleaseId, "DL");
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 10),"Release is not in DL status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markDeliveredOrderRelease(orderReleaseId, login);      
        validateResponse(orderReleaseResponse,8249,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in DL status");
        sft.assertAll();    
    }
    
    //New Testcases
    @Deprecated 
    @Test(enabled = false, dataProvider="markOrderRFRFromWP_PK_SH_DL_CStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder RFR From Work in Progress, Packed, Shipped, Delivered, Completed Status")
    public void markOrderRFRFromWP_PK_SH_DL_CStatus(String releaseId, String login, String status) throws JAXBException, IOException{
    	
    	OrderReleaseResponse omsCommonResponse = omsServiceHelper.markOrderRFRInOMS(releaseId);
   	    sft.assertEquals(omsCommonResponse.getStatus().getStatusCode(), 8017, "Verify  Release Response Status Code");
        sft.assertEquals(omsCommonResponse.getStatus().getStatusType(), Type.ERROR, "Verify  Release Response Status Type should be Error Actual:"+omsCommonResponse.getStatus().getStatusType());
        sft.assertEquals(omsCommonResponse.getStatus().getStatusMessage(), "Order release with id "+releaseId+" is "+status+". It should be in Queued.");
        
    }
    
    @Deprecated
    @Test(enabled = false, dataProvider="markOrderWPFromPK_DL_C_SHStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Work in progress From Packed,Delivered,Complete,Shipped Status")
    public void markOrderWPFromPK_DL_C_SHStatus(String releaseId, String login,String status) throws JAXBException, IOException{
    	
    	OmsCommonResponse omsCommonResponse = omsServiceHelper.markOrderWPInOMS(releaseId);
   	    sft.assertEquals(omsCommonResponse.getStatus().getStatusCode(), 3, "Verify  Release Response Status Code");
        sft.assertEquals(omsCommonResponse.getStatus().getStatusType(), Type.SUCCESS, "Verify  Release Response Status Type");
        //Check Release Status should be PK
        omsServiceHelper.validateReleaseStatusInOMS(releaseId, status, 10);
        
    }
    
    @Test(enabled = true, dataProvider="markOrderPKFromSH_DL_CStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Packed From Shipped, Delivered, Completed Status")
    public void markOrderPKFromSH_DL_CStatus(String orderID, String login, String status,String statusCode) throws JAXBException, IOException, ManagerException{
    	sft=new SoftAssert();
    	omsServiceHelper.checkReleaseStatusForOrder(orderID, EnumSCM.WP);
    	updateOrderAndReleaseLevelOnHold(orderID);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
    	omsServiceHelper.updateReleaseStatusDB(orderReleaseId, statusCode);
    	sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, statusCode, 10),"Release is not in C status");
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.rtsScan(""+orderReleaseId);
           
        validateResponse(orderReleaseResponse,8017,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Order release with id "+orderReleaseId+" is "+status+". It should be in Work in progress.");
        sft.assertAll();
    }
    
    
    @Test(enabled = true, dataProvider="markOrderSHFromWP_DL_CStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrderShipped From Work in progress,Delivered,Completed Status")
    public void markOrderSHFromWP_DL_CStatus(String orderID, String login,String status) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
    	omsServiceHelper.updateReleaseStatusDB(orderReleaseId, status);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, status, 10),"Release is not in C status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markReleaseShipped(orderReleaseId);      
        if(status.equalsIgnoreCase(EnumSCM.WP)){
        	validateResponse(orderReleaseResponse,8304,Type.ERROR);
        }else{
        	validateResponse(orderReleaseResponse,8016,Type.ERROR);
        }
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Order release with id "+orderReleaseId+" is "+status+". It should be in PK.");
        sft.assertAll();    
    }
        
    
    @Test(enabled = true, dataProvider="markOrderDLFromC_FStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Delivered From Complete, cancelled Status")
    public void markOrderDLFromC_FStatus(String orderID, String login,String status) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
       	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
    	omsServiceHelper.updateReleaseStatusDB(orderReleaseId, status);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, status, 10),"Release is not in "+status+" status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markDeliveredOrderRelease(orderReleaseId, login); 
    	if(status=="F"){
    		validateResponse(orderReleaseResponse,8016,Type.ERROR);
    		//sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Order release with id "+orderReleaseId+" is "+status+". It should be in SH.");
    	}else if(status=="C"){
            validateResponse(orderReleaseResponse,8249,Type.ERROR);
            //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Release "+orderReleaseId+" already in C status");
    	}
        sft.assertAll();
    }
    
    
    @Deprecated
    @Test(enabled = false, dataProvider="markOrderCFromF_L_RTO_SH_PKStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder Complete From cancelled ,Lost, RTO,Shipped,Packed Status.")
    public void markOrderCFromF_L_RTO_SH_PKStatus(String releaseId, String login, String status) throws JAXBException, IOException{
        omsServiceHelper.updateReleaseStatusDB(releaseId, status);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseId, status, 10),"Release is not in "+status+" status");
        
    	OmsCommonResponse omsCommonResponse = omsServiceHelper.markOrderCInOMS(releaseId);
   	    sft.assertEquals(omsCommonResponse.getStatus().getStatusCode(), 8017, "Verify  Release Response Status Code");
        sft.assertEquals(omsCommonResponse.getStatus().getStatusType(), Type.ERROR, "Verify  Release Response Status Type should be Error Actual:"+omsCommonResponse.getStatus().getStatusType());
        sft.assertEquals(omsCommonResponse.getStatus().getStatusMessage(), "Order release with id "+releaseId+" is "+status+". It should be in DL.");
        
    }
    
    @Test(enabled = true, dataProvider="markOrderRTOFromL_F_DL_C_PKStatusDP", dataProviderClass=ValidateIdempotencyOMSActionDP.class, groups={"smoke", "regression","idempotency"}, description="markOrder RTO From Lost, cancelled,Delivered,Complete, Packed Status.")
    public void markOrderRTOFromL_F_DL_C_PKStatus(String orderID, String login, String status) throws JAXBException, IOException{
    	sft=new SoftAssert();
    	updateOrderAndReleaseLevelOnHold(orderID);
    	orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
    	omsServiceHelper.updateReleaseStatusDB(orderReleaseId, status);
        sft.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, status, 10),"Release is not in "+status+" status");
        omsServiceHelper.updateDateInRelease(orderReleaseId);
        
    	OrderReleaseResponse orderReleaseResponse  =  omsServiceHelper.markOrderReleaseRTO(orderReleaseId, login);      
        validateResponse(orderReleaseResponse,8016,Type.ERROR);
        //sft.assertEquals(orderReleaseResponse.getStatus().getStatusMessage(), "Order release with id "+orderReleaseId+" is "+status+". It should be in SH.");
        sft.assertAll();
    }
    
    /**
     * This function is remove hold from order and releaseLevel
     * @param orderId
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    public void updateOrderAndReleaseLevelOnHold(String orderId) throws UnsupportedEncodingException, JAXBException{
    	HashMap<String, Object> row = null;
        List resultSet = null;

        String query = "update orders set is_on_hold="+Boolean.FALSE+" where id = "+orderId+";";
        DBUtilities.exUpdateQuery(query, "myntra_oms");
        
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
        List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
        for(OrderReleaseEntry orderReleaseEntry: orderReleaseEntries){
        	long releaseId = orderReleaseEntry.getId();
        	query = "update order_release set is_on_hold="+Boolean.FALSE+" where id = "+releaseId+";";
            DBUtilities.exUpdateQuery(query, "myntra_oms");            
        }
    }
    
}
