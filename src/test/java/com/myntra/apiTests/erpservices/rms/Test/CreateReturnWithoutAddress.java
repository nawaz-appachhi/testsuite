package com.myntra.apiTests.erpservices.rms.Test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.dp.CreateReturnWithoutAddressDP;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.erpservices.rms.dp.RmsDP;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class CreateReturnWithoutAddress extends BaseTest {
	
	Initialize init = new Initialize("/Data/configuration");
	Logger log = Logger.getLogger(RMSRegressionTest.class);
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    private static Long vectorSellerID;
    String login = "end2endautomation1@gmail.com";
	String uidx = "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R";
    String password = "123456";
    NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper =  new IMSServiceHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    LMSHelper lmsHepler = new LMSHelper();
	private String orderID;
	private boolean status;
	private Object releaseStatus1;
	private ReturnResponse returnResponse;
	private com.myntra.oms.client.entry.OrderReleaseEntry orderReleaseEntry;
	private ExchangeOrderResponse exchangeOrderResponse;
	private String lineID;
	private String exchangeOrderID;
	private String trakingNum = "ML0008891114";
	private String orderReleaseID;
    
    @BeforeClass
    public void testBeforeClass(){
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        RmsDP.vectorSellerID2=vectorSellerID;
    }
   //Pass
    @Test(description = "Create Return of an order without giving Address", enabled = true)
    public void createReturnWithoutAddressFail() throws Exception {
 
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
            End2EndHelper.sleep(3000L);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            log.info("Order ID : " + orderID);
            updateOrderAndLineStatus(orderReleaseID);
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            rmsServiceHelper = new RMSServiceHelper();
            returnResponse = rmsServiceHelper.createReturnWithoutAddress(lineID, 1, ReturnMode.PICKUP, 27L,"ALL");
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9101);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Customer address is required to process the return");

    }
    
    @Test(description = "Create Return of an order without any Field Miss", enabled = true, dataProvider = "createReturnWithoutAnyCity_ADDRESS_COUNTRY_STATE_ZIPCODE_DP", dataProviderClass = CreateReturnWithoutAddressDP.class)
    public void createReturnWithoutAnyCity_ADDRESS_COUNTRY_STATE_ZIPCODE_Fail(String orderID,String missingField,String errorMsg) throws Exception {
 
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
            End2EndHelper.sleep(3000);
    	    orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            rmsServiceHelper = new RMSServiceHelper();
            returnResponse = rmsServiceHelper.createReturnWithoutAddress(lineID, 1, ReturnMode.PICKUP, 27L,missingField);
          
            	Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
            	Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9101);
            	Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Customer "+errorMsg+" is required to process the return");
    }

   
    @Test(description = "Create Return of an order without giving Address Self Ship", enabled = true)
    public void createReturnWithoutAddressFailSelfShip() throws Exception {
 
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
            End2EndHelper.sleep(3000);
            log.info("Order ID : " + orderID);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            rmsServiceHelper = new RMSServiceHelper();
            returnResponse = rmsServiceHelper.createReturnWithoutAddress(lineID, 1, ReturnMode.SELF_SHIP, 27L,"ALL");
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9101);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Customer address is required to process the return");

    }
    
    @Test(description = "Create Return of an MultiLine order without giving Address", enabled = true)
    public void createReturnWithoutAddressFailMultipleLine() throws Exception {
    	
    		String skuId[] = { "1243744:2", "3832:2" };
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0","3832:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0","3832:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", false);
            End2EndHelper.sleep(3000);
            log.info("Order ID : " + orderID);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            returnResponse = rmsServiceHelper.createReturnWithoutAddress(lineID, 1, ReturnMode.PICKUP, 27L,"ALL");
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9101);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Customer address is required to process the return");

    }
 
    @Test(description = "Create Return of an MultiLine order without giving Address Self Ship", enabled = true)
    public void createReturnWithoutAddressFailMultipleLineSelfShip() throws Exception {
    	
    		String skuId[] = { "1243744:2", "3832:2" };
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0","3832:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0","3832:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", false);
            End2EndHelper.sleep(3000);
            log.info("Order ID : " + orderID);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            returnResponse = rmsServiceHelper.createReturnWithoutAddress(lineID, 1, ReturnMode.SELF_SHIP, 27L,"ALL");
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.ERROR);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9101);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Customer address is required to process the return");

    }

  
    @Test(description = "Create Return of an Try and Buy order without giving Address", enabled = true)
    public void createReturnWithoutAddressPassTryAndBuy() throws Exception {
    	
    		String skuId[] = { "3832:1" };
    	    atpServiceHelper.updateInventoryDetails(new String[] {"3832:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"3832:28:100000:0"}); 
    	    HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
            hm.put((long) 3832, Boolean.TRUE);
            orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", true);
            End2EndHelper.sleep(3000L);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            for (String skuAndQuantity : skuId) {
                String[] skuAndQuantitySplit = skuAndQuantity.split(":");
                String sku = skuAndQuantitySplit[0].trim();
                List SelectedRecodes = DBUtilities.exSelectQuery("Select is_try_and_buy from xcart_order_details where orderid='" + orderID + "' and sku_id='" + sku + "';", "myntra_order");
                HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
                for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
                    if (m.getKey().equals(Long.parseLong(sku))) {
                        if (Boolean.TRUE == m.getValue()) {
                            Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1, m.getKey() + ":TryAndBuy Flag value should be same");
                            log.info(txResult.get("is_try_and_buy").intValue() + " flag Value is " + m.getValue());
                        } else if (Boolean.FALSE == m.getValue()) {
                            Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0, m.getKey() + ":TryAndBuy Flag value should be same");
                            log.info(txResult.get("is_try_and_buy").intValue() + " flag Value is " + m.getValue());
                        }
                    }
                }
            }

            
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            log.info("Order ID : " + orderID);
            HashMap<String, String> skuAndStatus = new HashMap<>();
            skuAndStatus.put("3832", EnumSCM.TRIED_AND_BOUGHT);
            
            End2EndHelper.sleep(3000);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            returnResponse = rmsServiceHelper.createReturnTnBV2(lineID, 1, ReturnMode.TRY_AND_BUY,28,27L,RefundMode.CASHBACK,orderReleaseEntry.getCourierCode(),orderReleaseEntry.getTrackingNo());
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9551);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Return Release created successfully");

    }
    
 
    @Test(description = "Create ReturnforExchange of an order without giving Address", enabled = true)
    public void createReturnExchangeWithoutAddressPass() throws Exception {
 
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
            End2EndHelper.sleep(3000);
            log.info("Order ID : " + orderID);
            
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, "1243744");
            exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();
           
            End2EndHelper.sleep(3000);
            updateOrderAndLineStatus(exchangeOrderID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(exchangeOrderID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            returnResponse = rmsServiceHelper.createReturnExchangeWithoutAddress(orderReleaseID,lineID, 1, ReturnMode.PICKUP, 27L,exchangeOrderID);
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9551);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Return Release created successfully");

    }



    @Test(description = "Create ReturnForExchange of an order without giving Address Self Ship", enabled = true)
    public void createReturnExchangeWithoutAddressPassSelfShip() throws Exception {
 
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
            log.info("Order ID : " + orderID);
            
            End2EndHelper.sleep(3000);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, "1243744");
            exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();
            
            End2EndHelper.sleep(3000);
            updateOrderAndLineStatus(exchangeOrderID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(exchangeOrderID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            returnResponse = rmsServiceHelper.createReturnExchangeWithoutAddress(orderReleaseID,lineID, 1, ReturnMode.SELF_SHIP, 27L,exchangeOrderID);
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9551);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Return Release created successfully");

    }
    
    @Test(description = "Create ReturnforExchange of an order without giving Address", enabled = true)
    public void createReturnExchangeWithoutAddressPassMultipleLine() throws Exception {
    		
    		String skuId[] = { "1243744:2", "3832:2" };
    	    atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0","3832:28:100000:0"});
    	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0","3832:28:10000:0"}); 
            orderID = end2EndHelper.createOrder(login, password, "6132352",skuId, "", false, false, false, "", false);
            log.info("Order ID : " + orderID);
            
            End2EndHelper.sleep(3000);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            exchangeOrderResponse =(ExchangeOrderResponse) ppsServiceHelper.createExchange(""+orderID, ""+lineID, "DNL", 1, "1243744");
            exchangeOrderID = exchangeOrderResponse.getExchangeOrderId();
            End2EndHelper.sleep(3000);
            updateOrderAndLineStatus(exchangeOrderID);
            orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(exchangeOrderID);
            lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
            returnResponse = rmsServiceHelper.createReturnExchangeWithoutAddress(orderReleaseID,lineID, 1, ReturnMode.PICKUP, 27L,exchangeOrderID);
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9551);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Return Release created successfully");

    }
    
    @Test(description = "Create Return of an Try and Buy order without giving Address", enabled = true)
    public void createReturnWithoutAddressPassTryAndBuyMultiLine() throws Exception {
    	
    		String skuId[] = { "3832:1", "1243744:1" };
    		atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0","3832:28:100000:0"});
    		imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0","3832:28:10000:0"}); 
    	    HashMap<Long, Boolean> hm = new HashMap<Long, Boolean>();
            hm.put((long) 3832, Boolean.TRUE);
            hm.put((long) 1243744, Boolean.TRUE);
            orderID = end2EndHelper.createOrder(login, password, "6132352", skuId, "", false, false, false, "", true);

            for (String skuAndQuantity : skuId) {
                String[] skuAndQuantitySplit = skuAndQuantity.split(":");
                String sku = skuAndQuantitySplit[0].trim();
                List SelectedRecodes = DBUtilities.exSelectQuery("Select is_try_and_buy from xcart_order_details where orderid='" + orderID + "' and sku_id='" + sku + "';", "myntra_order");
                HashMap<String, Integer> txResult = (HashMap<String, Integer>) SelectedRecodes.get(0);
                for (Map.Entry<Long, Boolean> m : hm.entrySet()) {
                    if (m.getKey().equals(Long.parseLong(sku))) {
                        if (Boolean.TRUE == m.getValue()) {
                            Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 1, m.getKey() + ":TryAndBuy Flag value should be same");
                            log.info(txResult.get("is_try_and_buy").intValue() + " flag Value is " + m.getValue());
                        } else if (Boolean.FALSE == m.getValue()) {
                            Assert.assertEquals(txResult.get("is_try_and_buy").intValue(), 0, m.getKey() + ":TryAndBuy Flag value should be same");
                            log.info(txResult.get("is_try_and_buy").intValue() + " flag Value is " + m.getValue());
                        }
                    }
                }
            }

            
            Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
            log.info("Order ID : " + orderID);
            HashMap<String, String> skuAndStatus = new HashMap<>();
            skuAndStatus.put("3832", EnumSCM.TRIED_AND_BOUGHT);
            
            End2EndHelper.sleep(3000);
            orderReleaseID = omsServiceHelper.getReleaseId(orderID);
            updateOrderAndLineStatus(orderReleaseID);
            
            returnResponse = rmsServiceHelper.createReturnTnBV2(lineID, 1, ReturnMode.TRY_AND_BUY,28,27L,RefundMode.CASHBACK,orderReleaseEntry.getCourierCode(),orderReleaseEntry.getTrackingNo());
            Assert.assertEquals(returnResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
    		Assert.assertEquals(returnResponse.getStatus().getStatusCode(), 9551);
    		Assert.assertEquals(returnResponse.getStatus().getStatusMessage(), "Return Release created successfully");

    }
    
    public void updateOrderAndLineStatus(String orderID) throws UnsupportedEncodingException, JAXBException{

    	//Mark Order DL
    	releaseStatus1 = omsServiceHelper.updateReleaseStatusDB(orderID, "DL");
    	Assert.assertEquals(releaseStatus1, "DL",orderID+" Should be in DL status");
        //Update Tracking Number DB ML0008891114
        DBUtilities.exUpdateQuery("update order_release set tracking_no='"+trakingNum+"',courier_code='"+"ML"+"' where id='"+orderID+"';", "myntra_oms");
        orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderID);
        lineID = orderReleaseEntry.getOrderLines().get(0).getId().toString();
        //Update Line status to D
        releaseStatus1 = omsServiceHelper.updateOrderLineStatusDB(lineID, "D");
        End2EndHelper.sleep(3000);
        Assert.assertEquals(releaseStatus1, "D",lineID+" Should be in D status");
        
    }


}
