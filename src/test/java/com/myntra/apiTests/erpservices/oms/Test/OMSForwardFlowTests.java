package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Created by abhijit.pati on 14/07/16.
 */
public class OMSForwardFlowTests extends BaseTest{

    String login = "end2endautomation1@gmail.com";
    String password = "123456";
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    static org.slf4j.Logger log = LoggerFactory.getLogger(OMSForwardFlowTests.class);
    LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    private String supplyTypeOnHand = "ON_HAND";
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
  

    @Test(enabled=true, description = "")
    public void verifyInventoryReducedAfterOrderPacked() throws Exception {
        HashMap<String, HashMap<String, Object>> atpInventoryDetailsBeforeOrderPlace = atpServiceHelper.getAtpInvDetails(new String[]{OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM4});
        String skuId1[] = { OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1+":1", OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2+":1", OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3+":1"};
        String orderID = end2EndHelper.createOrder(login, password, "6118982", skuId1, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);

        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1).get("inventory_count"));
        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1).get("blocked_order_count"));

        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("inventory_count"));
        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("blocked_order_count"));

        List<OrderReleaseEntry> orderReleaseEntries = orderEntry.getOrderReleases();
        Assert.assertEquals(orderReleaseEntries.size(), 3, "3 Shipment should be created");
        HashMap<String, HashMap<String, Object>> atpInventoryDetailsAfterOrderPlace = atpServiceHelper.getAtpInvDetails(new String[]{OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3, OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM4});

        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("inventory_count"));
        log.info(""+atpInventoryDetailsBeforeOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("blocked_order_count"));

        log.info(""+atpInventoryDetailsAfterOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("inventory_count"));
        log.info(""+atpInventoryDetailsAfterOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("blocked_order_count"));

        log.info(""+atpInventoryDetailsAfterOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("inventory_count"));
        log.info(""+atpInventoryDetailsAfterOrderPlace.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("blocked_order_count"));

        for(OrderReleaseEntry orderReleaseEntry:orderReleaseEntries){
            end2EndHelper.markReleaseDelivered(orderReleaseEntry, "PK");
        }

        HashMap<String, HashMap<String, Object>> atpInventoryDetailsAfterPack = atpServiceHelper.getAtpInvDetails(new String[]{OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1,
        		OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2,
        		OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3,
        		OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM4});

        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1).get("inventory_count"));
        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM1).get("blocked_order_count"));


        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("inventory_count"));
        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM2).get("blocked_order_count"));

        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("inventory_count"));
        log.info(""+atpInventoryDetailsAfterPack.get(OMSTCConstants.OMSForwardFlowTests.verifyInventoryReducedAfterOrderPacked_ITEM3).get("blocked_order_count"));
    }
    
  //Added by Sneha - For Courier reassignment hotifx
       @Test
        public void CourierReassignment() throws Exception {
           String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":2"};
            atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+21l+":1"},"ON_HAND");
            imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+21l},"ON_HAND");
            String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
            omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
            Thread.sleep(5000);
            OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
            Assert.assertEquals(lmsServiceHelper.bulkOrderReassignment(""+omsServiceHelper.getReleaseId(orderID), "ML", "EK", EnumSCM.DELIVERY, EnumSCM.NORMAL, "COD").getStatus().getStatusType().toString(),
                    EnumSCM.SUCCESS, "Order reassignment status is not expected (SUCCESS/ERROR)");
       	}
     }

