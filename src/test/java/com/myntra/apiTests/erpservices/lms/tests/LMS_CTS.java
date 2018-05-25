package com.myntra.apiTests.erpservices.lms.tests;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.CtsEklDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.cts.entries.CourierTrackingResponse;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.util.DateUtil;
import com.myntra.logistics.platform.domain.ShipmentUpdateEvent;
import com.myntra.test.commons.testbase.BaseTest;

public class LMS_CTS extends BaseTest{
	
	private LmsServiceHelper lmsServiceHelper= new LmsServiceHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	
	public Date addDate(int days){
		Date d = DateUtil.addDate(new Date(), days);
		return d;
	}
	public Date subtractDate(int days){
		Date d = DateUtil.subtractDate(new Date(), 0);
		return d;
	}

	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsTCs",description = "ID: C122, Hitting CTS api with wrong data combinations (Negative cases)")
	public void ctsEkartNegative(String orderId, String vendor_tracking_id, String shipment_type, String event, String status, String location, String merchant_name, String merchant_code,
			String seller_id, String Courier_name, String date, String statusCode, String statusMessage, String statusType) throws Exception{
		Date d = new Date();
		if(date.equals("now")){
			d = new Date();
		}else {
			String[] s = date.split(":");
			if(s[0].equals("A")){
				d = addDate(Integer.parseInt(s[1]));
			} else if(s[0].equals("S")){
			d = subtractDate(Integer.parseInt(s[1]));
			}
		}
		CourierTrackingResponse response = lmsServiceHelper.ekartCts(orderId, vendor_tracking_id, shipment_type, event, statusType, location, merchant_name, merchant_code, seller_id, Courier_name, d);
			Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsNegativeCasesUsingGenericApi", description = "ID: C553, Hitting CTS generic api with multiple combination")
	public void ctsNegativeCasesUsingGenericApi(String orderId,String vendor_tracking_id, String courierCode, ShipmentUpdateEvent event, ShipmentType shipmentType,String statusType) throws Exception{
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(orderId, courierCode, vendor_tracking_id, event, shipmentType).getStatus().getStatusType().toString(), statusType);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsMarkDL", description = "ID: C554, Delivering Shipment using CTS generic API update. Scenarios SHIPPED - OUT_FOR_DELIVERY - FAILED_DELIVERY - OUT_FOR_DELIVERY - DELIVERED")
	public void ctsMarkOrderDeliveredFromShipped(String pincode, String courierCode, String warehouse, String shippingMethod) throws Exception{
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode, warehouse, shippingMethod,"cod",false, false));
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 5));
		OrderResponse releaseLMS = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.IN_TRANSIT, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.FAILED_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.DELIVERED, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.DELIVERED, 5));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsMarkDL", description = "ID: C555, Delivering Ekart Shipment using CTS generic API update. Scenarios SHIPPED - OUT_FOR_DELIVERY - FAILED_DELIVERY - OUT_FOR_DELIVERY - FAILED_DELIVERY - RTO_CONFIRMED")
	public void ctsRTO(String pincode, String courierCode, String warehouse, String shippingMethod) throws Exception{
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode, warehouse, shippingMethod,"cod",false, false));
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 5));
		OrderResponse releaseLMS = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.IN_TRANSIT, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.FAILED_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.FAILED_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.RTO_CONFIRMED, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.RTO_CONFIRMED, 5));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsMarkDL", description = "ID: C556, Delivering Ekart Shipment using CTS generic API update. Scenarios SHIPPED - OUT_FOR_DELIVERY - FAILED_DELIVERY - LOST")
	public void ctsLOST(String pincode, String courierCode, String warehouse, String shippingMethod) throws Exception{
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode, warehouse, shippingMethod,"cod",false, false));
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 5));
		OrderResponse releaseLMS = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.IN_TRANSIT, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.FAILED_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.LOST, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.LOST, 5));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsMarkDL", description = "ID: C561, Delivering Ekart Shipment using CTS generic API update and then trying to update and correct status." +
			" Scenarios SHIPPED - OUT_FOR_DELIVERY - DELIVERED - OUT_FOR_DELIVERY - FAILED_DELIVERY,  Should NOT be updated")
	public void ctsEkartDL_OFD_FD_STATUS_CORRECTION(String pincode, String courierCode, String warehouse, String shippingMethod) throws Exception{
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode, warehouse, shippingMethod,"cod",false, false));
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 5));
		OrderResponse releaseLMS = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.IN_TRANSIT, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.DELIVERED, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.DELIVERED, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertFalse(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 4));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.FAILED_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertFalse(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.FAILED_DELIVERY, 4));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = {"CTS",  "Smoke","Regression"}, priority = 0, dataProviderClass = CtsEklDP.class, dataProvider = "ctsMarkDL", description = "ID: C562, Delivering Ekart Shipment using CTS generic API update and then trying to update and correct status." +
			" Scenarios SHIPPED - OUT_FOR_DELIVERY - DELIVERED - LOST, Should NOT be updated")
	public void ctsEkartDL_LOST_STATUS_CORRECTION(String pincode, String courierCode, String warehouse, String shippingMethod) throws Exception{
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode, warehouse, shippingMethod,"cod",false, false));
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 5));
		OrderResponse releaseLMS = (OrderResponse)lmsServiceHelper.getOrderLMS.apply(releaseId);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.IN_TRANSIT, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.OUT_FOR_DELIVERY, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.OUT_FOR_DELIVERY, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.DELIVERED, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.DELIVERED, 5));

		Assert.assertEquals(lmsServiceHelper.ctsShipmentUpdate(releaseId, releaseLMS.getOrders().get(0).getCourierOperator(),
				releaseLMS.getOrders().get(0).getTrackingNumber(), ShipmentUpdateEvent.LOST, releaseLMS.getOrders().get(0).getShipmentType()).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertFalse(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.LOST, 4));
	}

}