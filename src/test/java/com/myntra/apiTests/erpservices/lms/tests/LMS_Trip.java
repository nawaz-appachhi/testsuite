package com.myntra.apiTests.erpservices.lms.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import com.myntra.lms.client.response.FinanceReportResponse;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.TripOrderAssignmentResponse;
import com.myntra.lms.client.response.TripResponse;
import com.myntra.lms.client.status.AttemptReasonCode;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.paymentplan.domain.response.ExchangeOrderResponse;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham Gupta on 2/22/17.
 */
public class LMS_Trip extends BaseTest {

    private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    private LMSHelper lmsHelper = new LMSHelper();
    private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    private PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    private ProcessRelease processRelease = new ProcessRelease();

    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 6, description = "ID: C515 , deleteAllTripForDC 42, For all the trip scenarios we are using DC 42 (CAR). So before running any test cases from this class we are just closing all the open trips for this Delivery center", enabled = true)
    public void completeAllTripForDC42(){
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> tripIds = DBUtilities.exSelectQuery("select tp.id from delivery_staff ds, trip tp where ds.id = tp.`delivery_staff_id` and " +
                "tp.`delivery_center_id` in (5,42) and tp.`trip_status` <> 'COMPLETED'","lms");
        if (tripIds==null){
            return;
        }else {
            String ids = "";
            for (Map<String,Object> tripId: tripIds)
                ids = ids+","+tripId.get("id");
            DBUtilities.exUpdateQuery("update trip set trip_status = 'COMPLETED' where id in("+ids.replaceFirst(",","")+")","lms");
        }
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripWithParam",description = "ID: C384 , getTrip", enabled = true)
    public void getTripWithParam( String queryParam, String statusCode, String statusMessage, String statusType) throws Exception{
        TripResponse response = (TripResponse)lmsServiceHelper.getTrip.apply(queryParam);
//        Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
//        Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripByTripNumber",description = "ID: C385 , getTrip", enabled = true)
    public void getTripByTripNumber( long dcId, String statusType) throws Exception {
        TripResponse response = (TripResponse)lmsServiceHelper.getTrip.apply("search?q=tripNumber.like:"+((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(dcId)).
                get("trip_number")+"&start=0&fetchSize=1&sortBy=id&sortOrder=DESC");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
        Assert.assertEquals((long)response.getTrips().get(0).getDeliveryCenterId(),dcId);
        Assert.assertNotNull(response.getTrips().get(0).getDeliveryStaffId());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripByTripNumber",description = "getTripOrderAssignment by tripnumber", enabled = true)
    public void getTripOrderByTripNumber( long dcId, String statusType) throws Exception {
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse)lmsServiceHelper.getTripByTripNumber.apply(((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(dcId)).
                get("trip_number"),"DL");
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripByTripNumber",description = "ID: C386 , getTrip", enabled = true)
    public void searchTripPlanningByTripId( long dcId, String statusType) throws Exception {
        Assert.assertEquals(((TripResponse)lmsServiceHelper.getTrip.apply(((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(dcId)).get("id"))).getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 7,description = "ID: C389 , downloadStoreTrip", enabled = true)
    public void downloadStoreTrip() throws  IOException, NumberFormatException, JAXBException{
        Assert.assertEquals(lmsServiceHelper.downloadStoreTrip("downloadStoreTrip/"+((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(5)).
                get("id")).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    @Test(groups = {"Trip",  "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripReport",description = "ID: C390 , getTripReport", enabled = true)
    public void getTripReport( String queryParam, String statusType) throws IOException, NumberFormatException, JAXBException{
        TripOrderAssignmentResponse response = lmsServiceHelper.getTripOrder(queryParam+"/"+lmsServiceHelper.getDateOnly.get());
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @Test(groups = {"Trip",  "P0", "Smoke","Regression"}, priority = 7,description = "ID: C391 , getTripReportFinance", enabled = true)
    public void getTripReportFinance( ) throws IOException, NumberFormatException, JAXBException{
        FinanceReportResponse response = lmsServiceHelper.getTripOrderFinance("getFinanceReport/"+lmsServiceHelper.getDateOnly.get()+"/"+lmsServiceHelper.getDateOnly.get());
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"Trip",  "P0","Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripOrderByTripId",description = "ID: C387 , getTrip", enabled = true)
    public void getTripOrderByTripId( String queryParam, String statusType) throws IOException, NumberFormatException, JAXBException{
        TripOrderAssignmentResponse response = lmsServiceHelper.getTripOrder(queryParam.replace("TRIP_ID",((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(5)).get("id").toString()));
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"Trip",  "P0","Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripOrderByTripNumber",description = "ID: C388 , getTrip", enabled = true)
    public void getTripOrderByTripNumber( String queryParam, String statusType) throws IOException, NumberFormatException, JAXBException{
        Assert.assertEquals(lmsServiceHelper.getTripOrder(queryParam.replace("TRIP_NUMBER",
                ((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(42)).get("trip_number").toString())).getStatus().getStatusType().toString(), statusType);
    }

    @Test(groups = {"Trip",  "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripResult",description = "ID: C400 , getTripResult", enabled = true)
    public void getTripResult( String param, String statusCode, String statusMessage, String statusType) throws  IOException, NumberFormatException, JAXBException{
        OrderResponse response = lmsServiceHelper.getTripResult(param);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripResultPost",description = "ID: C401 , getTripResultPost", enabled = true)
    public void getTripResultPost(String param, long deliveryCenterId, ShipmentType ShipmentType, String statusCode, String statusMessage, String statusType) throws  IOException, NumberFormatException, JAXBException{
        OrderResponse response = lmsServiceHelper.getTripResultPost(param, deliveryCenterId, ShipmentType);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "createTrip",description = "ID: C393 , createTrip", enabled = true)
    public void createTrip( long dcId, long staffId, String statusCode, String statusMessage, String statusType) throws  IOException, NumberFormatException, JAXBException{
        TripResponse response = lmsServiceHelper.createTrip(dcId, staffId);
        Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
        Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getOpenTripForStaff",description = "ID: C394 , getOpenTripForStaff", enabled = true)
    public void getOpenTripForStaff( String queryParam, String statusType) throws  Exception{
        Assert.assertEquals(((TripResponse)lmsServiceHelper.getTrip.apply(queryParam+((Map<String,Object>)lmsHelper.getTripIdnNoForDC.apply(5)).get("id"))).getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip", "P0", "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "isAutoCardEnabledForDCnDF",description = "ID: C431 , getOpenTripForStaff", enabled = true)
    public void isAutoCardEnabledForDCnDF( int dcId, int dfId, String statusType) throws Exception {
        Assert.assertEquals(((TripResponse)lmsServiceHelper.isAutoCardEnabled.apply(dcId, dfId)).getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllAvailableTripsForDC",description = "ID: C406 , getAllAvailableTripsForDC", enabled = true)
    public void getAllAvailableTripsForDC(long dcId, boolean dataCheck) throws Exception {
        TripResponse response = (TripResponse) lmsServiceHelper.getAllAvailableTripsForDC.apply(dcId);
        Assert.assertEquals(response.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        if (dataCheck && response.getTrips() != null && !response.getTrips().isEmpty()) {
            Assert.assertNotNull(response.getTrips().get(0).getId());
            Assert.assertTrue(response.getStatus().getTotalCount()>0);
        }else Assert.assertTrue(response.getStatus().getTotalCount()==0);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllAvailableTripsForDC",description = "ID: C407 , getDSRouteNameForDC", enabled = false)
    public void getDSRouteNameForDC(long dcId, boolean dataCheck) throws Exception{
        TripResponse response = (TripResponse) lmsServiceHelper.getDSRoute.apply(dcId);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        if (dataCheck) {
            Assert.assertNotNull(response.getTrips().get(0).getDsRouteMapId());
            Assert.assertTrue(response.getStatus().getTotalCount() > 0);
        } else Assert.assertTrue(response.getStatus().getTotalCount() == 0);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 7, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllAvailableTripsForDC",description = "ID: C399 , getDSRouteNameForDC", enabled = true)
    public void getTripDetailForDate(long dcId, boolean dataCheck) throws Exception {
        TripOrderAssignmentResponse response = (TripOrderAssignmentResponse) lmsServiceHelper.getTripsDetailForDate.apply(dcId);
        Assert.assertEquals(response.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        if (dataCheck) {
            Assert.assertNotNull(response.getTripOrders().get(0).getId());
            Assert.assertTrue(response.getStatus().getTotalCount()>0);
        }else Assert.assertTrue(response.getStatus().getTotalCount()==0);
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 7, description = "ID: C409 , getDeliveryStaffByMobileNumber", enabled = true)
    public void getDeliveryStaffByMobileNumber() throws IOException, JAXBException {
        Assert.assertEquals(lmsServiceHelper.getDeliveryStaff("search?q=mobile.eq%3A"+DBUtilities.exSelectQueryForSingleRecord(
                "select ds.`mobile` from trip tp, trip_order_assignment toa, delivery_staff ds where toa.`trip_id`= tp.id and ds.id = tp.`delivery_staff_id` and " +
                        "tp.`delivery_center_id` = 5 order by toa.last_modified_on desc limit 1", "lms").get("mobile")).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C437 , getTripDetailsByTrackingNumber", enabled = true)
    public void getTripDetailsByTrackingNumber() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        TripOrderAssignmentResponse tripAssignmentResponse1 =  (TripOrderAssignmentResponse)lmsServiceHelper.getTripsDetailForTrackingNumber.apply(lmsHelper.getTrackingNumber(packetId));
        Assert.assertEquals(tripAssignmentResponse1.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertNotNull(tripAssignmentResponse1.getTripOrders().get(0).getId());
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        TripOrderAssignmentResponse tripAssignmentResponse2 =  (TripOrderAssignmentResponse)lmsServiceHelper.getTripsDetailForTrackingNumber.apply(lmsHelper.getTrackingNumber(packetId));
        Assert.assertEquals(tripAssignmentResponse2.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertNotNull(tripAssignmentResponse2.getTripOrders().get(0).getId());
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C434 , getActiveTripsForOrder", enabled = true)
    public void getActiveTripsForOrder() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        TripResponse tripAssignmentResponse1 =  (TripResponse)lmsServiceHelper.getActiveTripForOrder.apply(packetId);
        Assert.assertEquals(tripAssignmentResponse1.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertNotNull(tripAssignmentResponse1.getTrips().get(0).getId());
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        TripResponse tripAssignmentResponse2 =  (TripResponse)lmsServiceHelper.getActiveTripForOrder.apply(packetId);
        Assert.assertEquals(tripAssignmentResponse2.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertNotNull(tripAssignmentResponse2.getTrips().get(0).getId());
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C433 , update order to DL via mobile SMS which is long code", enabled = true)
    public void longCodeUpdateOrder() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Map<String,Object> dsfData = DBUtilities.exSelectQueryForSingleRecord("select * from delivery_staff where id ="+deliveryStaffID,"lms");
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.longCodeUpdateOrder.apply(dsfData.get("mobile"), packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.DELIVERED,5));
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, dataProviderClass = LMSTestsDP.class, dataProvider = "startTrip",description = "ID: C516 ,Start Trip Just check that api is returning 200 or not with wrong data set", enabled = true)
    public void startTrip200Check( String tripId, String odometerReading,String statusType) throws  IOException, NumberFormatException, JAXBException{
        Assert.assertEquals(lmsServiceHelper.startTrip(tripId, odometerReading).getStatus().getStatusType().toString(), statusType);
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, dataProviderClass = LMSTestsDP.class, dataProvider = "deleteTrip",description = "ID: C396 , deleteTrip", enabled = true)
    public void deleteTrip( String status, String statusType) throws  IOException, NumberFormatException, JAXBException{
        String tripId ="";
        if (status.equals(EnumSCM.CREATED)){
            TripResponse tripResponse = lmsServiceHelper.createTrip(14,4136);
            Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
            tripId = tripResponse.getTrips().get(0).getId().toString();
        }else {
            Map<String,Object> trip = DBUtilities.exSelectQueryForSingleRecord("select id from trip where trip_status = '"+status+"' limit 1","lms");
            tripId = trip.get("id").toString();
        }
        TripResponse response = lmsServiceHelper.deleteTrip(tripId);
        Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C517 , validateTripOrder", enabled = true)
    public void validateTripOrder() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.validateTripOrder.apply(omsServiceHelper.getPacketId(orderId))).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C518 , update order payment type at the time of Trip update", enabled = true)
    public void updateOrderPaymentType() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));

        Assert.assertEquals(((TripResponse)lmsServiceHelper.updatePaymentType.apply(packetId,"MSwipe")).getStatus().getStatusType().toString(),EnumSCM.SUCCESS); // can be EzeTap/MSwipe
        Assert.assertEquals(DBUtilities.exSelectQueryForSingleRecord("select payment_pos from order_additional_info where id = " +
                "(select order_additional_info_id from order_to_ship where order_id = "+packetId+")","lms").get("payment_pos").toString(),"MSwipe");
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C428 , updateOdometerReading", enabled = true)
    public void updateOdometerReading() throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
        Assert.assertEquals(((TripResponse)lmsServiceHelper.updateOdodmeterReading.apply(tripId,10,200)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Map<String,Object> tripUpdate = DBUtilities.exSelectQueryForSingleRecord("select * from trip where id ="+tripId,"lms");
        Assert.assertNotNull(tripUpdate.get("starting_odometer_reading").toString());
        Assert.assertNotNull(tripUpdate.get("end_odometer_reading").toString());
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C402 , assignReturnTODC", enabled = true)
    public void assignPickupToDC() throws Exception {
   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        String returnID = returnResponse.getData().get(0).getId().toString();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(returnID);
        Assert.assertEquals(lmsServiceHelper.assignOrderToDC.apply(5,1,returnResponse1.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo()),EnumSCM.SUCCESS);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C403 , assignPickupToHLP", enabled = true)
    public void assignPickupToHLP() throws Exception {
    	
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        String returnID = returnResponse.getData().get(0).getId().toString();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        ReturnResponse returnResponse1 = rmsServiceHelper.getReturnDetailsNew(returnID);
        Assert.assertNotNull(lmsServiceHelper.assignPickupToHLP.apply(returnResponse1.getData().get(0).getReturnTrackingDetailsEntry().getTrackingNo()));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C519 , unAssignOrderFromTrip", enabled = true)
    public void unAssignOrderFromTrip() throws Exception {
    
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
    		String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId))).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId))).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(3000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C520 , unAssignOrderFromTripThroughTripId", enabled = true)
    public void unAssignOrderFromTripThroughTripId() throws Exception {
    	
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
    		String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFromTripThroughTripId.apply(packetId,lmsHelper.getTrackingNumber(packetId),tripId,EnumSCM.DL)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFromTripThroughTripId.apply(packetId,lmsHelper.getTrackingNumber(packetId),tripId,EnumSCM.DL)).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C521 , unAssignReturnFromTrip", enabled = true)
    public void unAssignReturnFromTrip() throws Exception {
   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Chandigarh", "CG", LMS_PINCODE.NORTH_CGH, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnID = returnResponse.getData().get(0).getId();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID))).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.OUT_FOR_PICKUP,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID))).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C522 , unAssignExchangeFromTrip", enabled = true)
    public void unAssignExchangeFromTrip() throws Exception {
    
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
    		String packetId = omsServiceHelper.getPacketId(orderId);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(packetId);
        Long lineID = packetEntry.getOrderLines().get(0).getId();
        ExchangeOrderResponse exchangeOrderResponse = (ExchangeOrderResponse) ppsServiceHelper.createExchange("" + orderId, "" + lineID, "DNL", 1, "3876");
        Assert.assertNotNull(exchangeOrderResponse.getSuccess(), "Unable to create exchange");

        String exchangeOrderID = omsServiceHelper.getOrderEntry(exchangeOrderResponse.getExchangeOrderId()).getId().toString();
        String exPacketId = omsServiceHelper.getPacketId(exchangeOrderID);
        String exReleaseId = omsServiceHelper.getReleaseId(exchangeOrderID);
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(exReleaseId, ReleaseStatus.DL).force(true).build());
        processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(exPacketId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+exPacketId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForOrder.apply(exPacketId,tripId))).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+exPacketId,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(exPacketId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+exPacketId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+exPacketId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+exPacketId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForOrder.apply(exPacketId,tripId))).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(exPacketId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(exPacketId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C523 , assignAndOutScanDLOrderToTrip", enabled = true)
    public void assignAndOutScanDLOrderToTrip() throws Exception {
    
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
    		String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFromTripThroughTripId.apply(packetId,lmsHelper.getTrackingNumber(packetId),tripId,EnumSCM.DL)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C524 , assignAndOutScanDLOrderToTripTOD", enabled = true)
    public void assignAndOutScanTODOrderToTrip() throws Exception {
   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", true, true);
    		String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFromTripThroughTripId.apply(packetId,lmsHelper.getTrackingNumber(packetId),tripId,EnumSCM.DL)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,2));
        //------------------ complete trip -----------------------
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C525 , assignAndOutScanReturnFromTrip", enabled = true)
    public void assignAndOutScanReturnFromTrip () throws Exception {
    		
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Chandigarh", "CG", LMS_PINCODE.NORTH_CGH, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnID = returnResponse.getData().get(0).getId();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID))).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.UNASSIGNED,2));
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.OUT_FOR_PICKUP,2));
        Assert.assertEquals(((TripOrderAssignmentResponse)lmsServiceHelper.unassignOrderFronTrip.apply(lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnID))).getStatus().getStatusType().toString(),EnumSCM.ERROR);
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C526 , assignAndOutScanDLOrderToTwoTrips (multiTripAssignment)", enabled = true)
    public void assignAndOutScanDLOrderToTwoTrips() throws Exception {
	  
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        String deliveryStaffID2 = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        TripResponse trip2Response = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID2));
        Assert.assertEquals(trip2Response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId2 = trip2Response.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId2, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId2, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId2),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId2),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C527 , assignAndOutScanReturnToTwoTrips (multiTripAssignment)", enabled = true)
    public void assignAndOutScanReturnToTwoTrips() throws Exception {
    		
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
        ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Chandigarh", "CG", LMS_PINCODE.NORTH_CGH, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        String returnID = returnResponse.getData().get(0).getId().toString();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        String deliveryStaffID2 = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID2, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID, EnumSCM.ASSIGNED_TO_SDA, 2));
        TripResponse trip2Response = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID2));
        Assert.assertEquals(trip2Response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId2 = trip2Response.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId2, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.ERROR, "Unable to assignOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId2, "10").getStatus().getStatusType().toString(), EnumSCM.ERROR, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+returnID,EnumSCM.OUT_FOR_PICKUP,2));
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.deleteTrip(""+tripId2).getStatus().getStatusType().toString(),EnumSCM.SUCCESS, "Unable to delete trip: Post processing");
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C528 , assignAndOutScanDLOrderOFDAndAddToSecondTrip", enabled = true)
    public void assignAndOutScanDLOrderOFDAndAddToSecondTrip() throws Exception {
	   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        String deliveryStaffID2 = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to addAndOutscanNewOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to startTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.OUT_FOR_DELIVERY, 2));
        TripResponse trip2Response = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID2));
        Assert.assertEquals(trip2Response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId2 = trip2Response.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId2, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.ERROR, "Unable to addAndOutscanNewOrderToTrip");
        //------------------ complete trip -----------------------
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),
                EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS,"Unable to complete trip.");
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C529 , assignAndOutScanDeliveredOrderToTrip", enabled = true)
    public void assignAndOutScanDeliveredOrderToTrip() throws Exception {
	   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, false);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,dataProviderClass = LMSTestsDP.class,dataProvider = "assignReturntoTripNegative",description = "ID: C538 , assignReturntoTripPickupSuccessFul", enabled = true)
    public void assignReturntoTripNegative(String status) throws Exception {
    	String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL,"cod",false, false);
        
    		PacketEntry packetEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
        @SuppressWarnings("deprecation")
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(packetEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L, RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Chandigarh", "CG", LMS_PINCODE.NORTH_CGH, "ML");
        Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Return creation failed");
        Long returnID = returnResponse.getData().get(0).getId();
        lmsServiceHelper.validateRmsLmsReturnCreation(""+returnID);
        lmsServiceHelper.processReturnInLMS(""+returnID, status);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnID).toString()).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.deleteTrip(""+tripId).getStatus().getStatusType().toString(),EnumSCM.SUCCESS, "Unable to delete trip: Post processing");
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C530 , startTripWithNoOrders", enabled = true)
    public void startTripWithNoOrders() throws IOException, JAXBException {
        
    		String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.deleteTrip(""+tripId).getStatus().getStatusType().toString(),EnumSCM.SUCCESS, "Unable to delete trip: Post processing");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8,description = "ID: C531 , startTripWithSelfMarkDLOrder", enabled = true)
    public void startTripWithSelfMarkDLOrder() throws Exception {
	   
    		String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID("" + 42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.selfMarkDL(""+packetId).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId,EnumSCM.DELIVERED,5));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId,EnumSCM.D,5));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.OUT_FOR_DELIVERY,5));
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.DELIVERED,5));
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C532 , startTripWithMultiDayOFDTrips", enabled = false) // The case need to be discussed further
    public void startTripWithMultiDayOFDTrips() throws Exception {
	    
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
	    	String orderId1 = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
	    	String packetId1 = omsServiceHelper.getPacketId(orderId1);

        long deliveryStaffID = Long.parseLong(lmsServiceHelper.getDeliveryStaffID("" + 42));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse0 = lmsServiceHelper.createTrip(42L, deliveryStaffID);
        Assert.assertEquals(tripResponse0.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse0.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);

        DBUtilities.exUpdateQuery("update trip set trip_date = DATE(CURRENT_DATE() - INTERVAL 1 DAY), trip_start_time = DATE(CURRENT_DATE() - INTERVAL 1 DAY), created_on = DATE(CURRENT_DATE() - INTERVAL 1 DAY) where id = "+tripId,"lms");
        DBUtilities.exUpdateQuery("update trip_order_assignment set created_on = DATE(CURRENT_DATE() - INTERVAL 1 DAY), assignment_date = DATE(CURRENT_DATE() - INTERVAL 1 DAY) where trip_id = "+tripId,"lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, deliveryStaffID);
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId1 = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId1, lmsHelper.getTrackingNumber(packetId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId1, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId1, "10").getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C533 , startTripWithSameDayOFDTrips", enabled = true)
    public void startTripWithSameDayOFDTrips() throws Exception {
	    
    	String orderId = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
	    	String orderId1 = lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
	    	String packetId = omsServiceHelper.getPacketId(orderId);
	    	String packetId1 = omsServiceHelper.getPacketId(orderId1);

        long deliveryStaffID = Long.parseLong(lmsServiceHelper.getDeliveryStaffID("" + 42));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse0 = lmsServiceHelper.createTrip(42L, deliveryStaffID);
        Assert.assertEquals(tripResponse0.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse0.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);

        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, deliveryStaffID);
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId1 = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId1, lmsHelper.getTrackingNumber(packetId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId1, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId1, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId,"lms");
        DBUtilities.exUpdateQuery("update trip set trip_status ='COMPLETED' where id ="+tripId1,"lms");
    }

    @SuppressWarnings("unchecked")
	@Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C534 , updateTripWithMultiOrderNmultiStatus", enabled = true)
    public void updateTripWithMultiOrderAndMultiStatus() throws Exception {
    
    		String packetId1 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
    		String packetId2 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
    		String packetId3 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));

        long deliveryStaffID = Long.parseLong(lmsServiceHelper.getDeliveryStaffID("" + 42));
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse0 = lmsServiceHelper.createTrip(42L, deliveryStaffID);
        Assert.assertEquals(tripResponse0.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse0.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId1)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId2)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId3)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId1, EnumSCM.ASSIGNED_TO_SDA, 2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId1, EnumSCM.OUT_FOR_DELIVERY, 2));
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId1,tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId2,tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId3,tripId), EnumSCM.FAILED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        List<Map<String,Object>> tripData = new ArrayList<>();
        Map<String,Object> dataMap1 = new HashMap<>();
        Map<String,Object> dataMap2 = new HashMap<>();
        Map<String,Object> dataMap3 = new HashMap<>();
        dataMap1.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId1,tripId));
        dataMap1.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
        dataMap2.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId2,tripId));
        dataMap2.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
        dataMap3.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId3,tripId));
        dataMap3.put("AttemptReasonCode", AttemptReasonCode.NOT_REACHABLE_UNAVAILABLE);
        tripData.add(dataMap1);
        tripData.add(dataMap2);
        tripData.add(dataMap3);
        Assert.assertEquals(lmsServiceHelper.completeTrip(tripData).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId1,EnumSCM.DELIVERED,4));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId1,EnumSCM.D, 4));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C535 , updateTripWithMultiOrderNmultiStatus", enabled = true)
    public void updateTripWithMultiShipmentTypeToMultiStatus() throws Exception {
   
    		String packetId1 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
    		String packetId2 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
    		String packetId3 = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
        long returnId1 = Long.parseLong(lmsServiceHelper.createReturn.apply(packetId1,LMS_PINCODE.NORTH_CGH).toString());
        long returnId2 = Long.parseLong(lmsServiceHelper.createReturn.apply(packetId2,LMS_PINCODE.NORTH_CGH).toString());
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnId1).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.assignOrderToTrip(tripId, lmsHelper.getReturnsTrackingNumber.apply(returnId2).toString()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId3)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId3,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);

        Assert.assertEquals(lmsServiceHelper.updatePickupInTrip((long)lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnId1), EnumSCM.PICKED_UP_SUCCESSFULLY, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Assert.assertEquals(lmsServiceHelper.updatePickupInTrip((long)lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnId1), EnumSCM.FAILED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId3,tripId), EnumSCM.DELIVERED, EnumSCM.UPDATE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to update orders in Trip.");
        Thread.sleep(2000);
        List<Map<String,Object>> tripData = new ArrayList<>();
        Map<String,Object> dataMap1 = new HashMap<>();
        Map<String,Object> dataMap2 = new HashMap<>();
        Map<String,Object> dataMap3 = new HashMap<>();
        dataMap1.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnId1));
        dataMap1.put("AttemptReasonCode", AttemptReasonCode.PICKED_UP_SUCCESSFULLY);
        dataMap2.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForReturn.apply(returnId2));
        dataMap2.put("AttemptReasonCode", AttemptReasonCode.REQUESTED_RE_SCHEDULE);
        dataMap3.put("trip_order_assignment_id",lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId3,tripId));
        dataMap3.put("AttemptReasonCode", AttemptReasonCode.DELIVERED);
        tripData.add(dataMap1);
        tripData.add(dataMap2);
        tripData.add(dataMap3);
        Assert.assertEquals(lmsServiceHelper.completeTrip(tripData).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(packetId3,EnumSCM.DELIVERED,4));
        Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId3,EnumSCM.D,4));
    }

    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C404 , autoAssignmentOfOrderToTrip which is used in requeue orders to trips and being used in Failed delivery cases", enabled = true)
    public void autoAssignmentOfOrderToTrip() throws Exception {
    
    		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.NORMAL, "cod", false, true);
    		//String packetId = omsServiceHelper.getPacketId(orderId);
    		String releaseId = omsServiceHelper.getReleaseId(orderId);
    	    List<ReleaseEntry> releaseEntries = new ArrayList<>();
    		releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.FDDL).force(true).build());
    		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C536 , closeTrip", enabled = true)
    public void completeTripWithoutTripUpdate() throws Exception {
    	
    	    String packetId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId), EnumSCM.DELIVERED, EnumSCM.TRIP_COMPLETE).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        assertTrue(lmsServiceHelper.validateOrderStatusInLMS(""+packetId, EnumSCM.DELIVERED, 4), "Update trip order api failed for marking Delivered in LMS");
        assertTrue(omsServiceHelper.validatePacketStatusInOMS(packetId, "D", 2), "Update OMS api failed for marking Delivered in OMS");
    }

    @SuppressWarnings("unchecked")
    @Test(groups = {"Trip",  "Smoke","Regression"}, priority = 8, description = "ID: C537 , closeTrip", enabled = true)
    public void completeTripWithWrongStatusAndNoUpdate() throws Exception {
    
    	    String packetId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.RECEIVE_IN_DC, LMS_PINCODE.NORTH_CGH, "ML", "36", EnumSCM.XPRESS, "cod", false, true));
        String deliveryStaffID = lmsServiceHelper.getDeliveryStaffID(""+42);
        DBUtilities.exUpdateQuery("update trip set trip_status='COMPLETED' where delivery_staff_id=" + deliveryStaffID, "lms");
        TripResponse tripResponse = lmsServiceHelper.createTrip(42L, Long.parseLong(deliveryStaffID));
        Assert.assertEquals(tripResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to create Trip");
        long tripId = tripResponse.getTrips().get(0).getId();
        Assert.assertEquals(lmsServiceHelper.addAndOutscanNewOrderToTrip(tripId, lmsHelper.getTrackingNumber(packetId)).getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to assignOrderToTrip");
        Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(""+packetId,EnumSCM.ASSIGNED_TO_SDA,2));
        Assert.assertEquals(lmsServiceHelper.startTrip("" + tripId, "10").getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),null).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),AttemptReasonCode.CANNOT_PICKUP).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),AttemptReasonCode.PICKUP_ON_HOLD_INCORRECT_PRODUCT).getStatus().getStatusType().toString(), EnumSCM.ERROR);
        Assert.assertEquals(lmsServiceHelper.updateOrderInTrip((long)lmsHelper.getTripOrderAssignemntIdForOrder.apply(packetId,tripId),AttemptReasonCode.PICKED_UP_SUCCESSFULLY).getStatus().getStatusType().toString(), EnumSCM.ERROR);
    }

}
