package com.myntra.apiTests.erpservices.lms.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.ShipmentSource;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.ML_JABONGDP;
import com.myntra.apiTests.erpservices.lms.lmsClient.JabongCreateShipmentResponse;
import com.myntra.apiTests.erpservices.lms.lmsClient.LMSOrderEntries;
import com.myntra.apiTests.erpservices.lms.lmsClient.LMSOrderEntry;
import com.myntra.apiTests.erpservices.lms.lmsClient.Shipment;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lms.client.status.PremisesType;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 5/2/17.
 */
@SuppressWarnings("deprecation")
public class LMS_MLJabongTest extends BaseTest {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_MLJabongTest.class);
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	private ProcessRelease processRelease = new ProcessRelease();

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 204, Create Jabong Shipments in LMS",
            dataProvider = "createJabongOrder", dataProviderClass = ML_JABONGDP.class, enabled = true, priority = 2)
    public void createShipment(Shipment shipment) throws IOException {
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        //Assert.assertEquals(createOrderResponse.getStatusCode(), "4001");
        Assert.assertEquals(createOrderResponse.getStatusType(), "SUCCESS");
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 205, Create Jabong Shipments validate Data in DB", enabled = false, priority = 2)
    public void createJabongShipmentAndValidateDB() throws IOException {
        Shipment shipment = lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, EnumSCM.wareHouseIdJabong, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false);
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        Assert.assertEquals(createOrderResponse.getStatusCode(), "4001");
        Assert.assertEquals(createOrderResponse.getStatusType(), "SUCCESS");
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 206, Create Same Shipment multiple Time",
            dataProvider = "createSameShipmentMultipleTime", dataProviderClass = ML_JABONGDP.class, enabled = true, priority = 2)
    public void createShipmentMultipleTime(Shipment shipment) throws IOException {
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        Assert.assertEquals(createOrderResponse.getStatusCode(), "4001");
        Assert.assertEquals(createOrderResponse.getStatusType(), "SUCCESS");
        createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        Assert.assertEquals(createOrderResponse.getStatusType(), "ERROR");
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 207, Create Jabong Shipments in LMS Negative Scenarios",
            dataProvider = "createJabongOrderNegativeScenarios", dataProviderClass = ML_JABONGDP.class, enabled = true, priority = 2)
    public void createShipmentNegativeScenarios(String pincode, String warehouseId, String courierCode, ShipmentType shipmentType,
                                                ShippingMethod shippingMethod, int noOfItem, boolean isJewellery, boolean isFootware,
                                                boolean isFragile, boolean isHazmat, boolean isLarge) throws IOException {
        Shipment shipment = lmsServiceHelper.createJabongShipmentRequestPayload( pincode,  warehouseId,  courierCode,  shipmentType,
                 shippingMethod,  noOfItem,  isJewellery,  isFootware, isFragile,  isHazmat,  isLarge);
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createOrderResponse.getStatusType(), "ERROR");
        softAssert.assertEquals(createOrderResponse.getStatusMessage(), "Shipment Creation Failed");
        softAssert.assertAll();
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 208,  Create Single Shipment and Process them to Different Terminal State",
            dataProvider = "createShipmentMarkOrderDeliver", dataProviderClass = ML_JABONGDP.class, enabled = true, priority = 2)
    public void createShipmentMarkOrderToDifferentStatusForSingleShipment(LMSOrderEntries orderEntries) throws Exception {
        for (LMSOrderEntry order : orderEntries.getOrderEntries()) {
        	
            lmsServiceHelper.validateOrderStatusInLMS(order.getOrderID(), EnumSCM.PACKED, 5);
            List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(order.getOrderID(), ReleaseStatus.DL).shipmentSource(ShipmentSource.JABONG).build());
            processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
            SoftAssert softAssert = new SoftAssert();
            for (LMSOrderEntry orderEntry : orderEntries.getOrderEntries()) {
                log.debug("Validating LMS Order To Ship Table For Order ID : " + orderEntry.getOrderID());
                softAssert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(orderEntry.getOrderID(), lmsServiceHelper.getOrderToShipStatus(orderEntry.getFinalStatus().toString()), 5), "Order Status is not Matching ");
            }
            softAssert.assertAll();
        }
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 209, Create Multiple Jabong Shipments and Mark Those Deliver with Different Order Status",
            dataProvider = "createMultipleJabongShipmentsAndMarkOrderDeliver", dataProviderClass = ML_JABONGDP.class, enabled = true, priority = 2)
    public void createMultipleJabongShipmentsAndMarkOrderDeliver(String orderId, ReleaseStatus toStatus) throws Exception {
            
    		    lmsServiceHelper.validateOrderStatusInLMS(orderId, EnumSCM.PACKED, 5);
    		    List<ReleaseEntry> releaseEntries = new ArrayList<>();
            releaseEntries.add(new ReleaseEntry.Builder(orderId, ReleaseStatus.DL).shipmentSource(ShipmentSource.JABONG).build());
            processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

	@Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 210, Create Jabong Shipments in LMS",
            dataProvider = "createMultipleMyntraAndJabongShipmentsAndMarkOrderDeliver", dataProviderClass = ML_JABONGDP.class, enabled = false, priority = 2)
    public void createMultipleMyntraAndJabongShipmentsAndMarkOrderDeliver(Shipment shipment) throws Exception {
        JabongCreateShipmentResponse createOrderResponse = lmsServiceHelper.createJabongShipment(shipment);
        Assert.assertTrue(createOrderResponse.getStatusType().equals(EnumSCM.SUCCESS), "JABONG Order Creation Failed");

        if(!lmsServiceHelper.orderInScanNew(createOrderResponse.getOrderID(), EnumSCM.wareHouseIdJabong, true).equals(EnumSCM.SUCCESS)){
            Assert.fail("Shipment InScan Failed");
        }

        String orderId = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR,"ML","36",EnumSCM.NORMAL,"cod",false, true);
        String orderId1 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR,"ML","1",EnumSCM.NORMAL,"cod",false, true);
        String orderId2 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR,"ML","19",EnumSCM.NORMAL,"cod",false, true);

        String releaseId = omsServiceHelper.getPacketId(orderId);
        String releaseId1 = omsServiceHelper.getPacketId(orderId1);
        String releaseId2 = omsServiceHelper.getPacketId(orderId2);


        List<LMSOrderEntry> list = new ArrayList<>();
        list.add(new LMSOrderEntry(""+releaseId, ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
        list.add(new LMSOrderEntry(""+releaseId1, ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
        list.add(new LMSOrderEntry(""+releaseId2, ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
        list.add(new LMSOrderEntry(createOrderResponse.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));

        LMSOrderEntries lmsOrderEntries = new LMSOrderEntries(list, "110", ShippingMethod.NORMAL, PremisesType.WH, 5L);
        lmsServiceHelper.processOrderInLMSFromPKToSH(lmsOrderEntries);
        for (LMSOrderEntry lmsOrderEntry : lmsOrderEntries.getOrderEntries()) {
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(lmsOrderEntry.getOrderID(),EnumSCM.UNASSIGNED,3),"Status is not Unassigned in ML");
        }

        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(createOrderResponse.getOrderID(),EnumSCM.UNASSIGNED,3),"Status is not Unassigned in ML");
        lmsServiceHelper.processOrderInLMSFromSHToTerminalState(lmsOrderEntries);
        for (LMSOrderEntry lmsOrderEntry : lmsOrderEntries.getOrderEntries()) {
            Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(lmsOrderEntry.getOrderID(),EnumSCM.DELIVERED,3),"Status is not Unassigned in ML");
        }
    }

    @Test(groups = {"ML-Jabong", "Smoke", "Regression"}, description = "ID: 211, Create Jabong Shipments in LMS and Validate the Data in DB", dataProviderClass = ML_JABONGDP.class, enabled = false, priority = 2)
    public void dbValidationOfJabongOrderInVariousStages(){
   
    }
}
