package com.myntra.apiTests.erpservices.lms.tests;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.lms.lmsClient.MLShipmentResponse;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lms.client.response.AttemptReasonCodeResponse;
import com.myntra.lms.client.response.CourierResponse;
import com.myntra.lms.client.response.CourierServiceabilityInfoResponse;
import com.myntra.lms.client.response.DeliveryCenterResponse;
import com.myntra.lms.client.response.DeliveryStaffResponse;
import com.myntra.lms.client.response.HubCourierHandoverConfigResponse;
import com.myntra.lms.client.response.HubResponse;
import com.myntra.lms.client.response.NetworkResponse;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.OrderTrackingResponse;
import com.myntra.lms.client.response.OrderTrackingResponseV2;
import com.myntra.lms.client.response.PincodeResponse;
import com.myntra.lms.client.response.PincodeResponseV2;
import com.myntra.lms.client.response.RegionResponse;
import com.myntra.lms.client.response.ReturnAddress;
import com.myntra.lms.client.response.ShipmentResponse;
import com.myntra.lms.client.response.ShippingCutoffResponse;
import com.myntra.lms.client.response.TrackingNumberResponse;
import com.myntra.lms.client.response.TripOrderAssignmentResponse;
import com.myntra.lms.client.response.ValidTransitionResponse;
import com.myntra.lms.client.status.DeliveryCenterType;
import com.myntra.lms.client.status.DeliveryStaffCommute;
import com.myntra.lms.client.status.HubType;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lms.client.status.TrackingNoSource;
import com.myntra.lms.util.DateUtil;
import com.myntra.logistics.platform.domain.ShipmentUpdateEvent;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 *
 * @author Shubham gupta
 *
 */
public class LMS_AllApi extends BaseTest {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_AllApi.class);
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	private ProcessRelease processRelease = new ProcessRelease();
	//private String myntraSourceId;

	@BeforeClass
	public void beforeCall() throws ManagerException, IOException, JAXBException, JSONException, XMLStreamException,
			InterruptedException {

		//myntraSourceId = (String) lmsServiceHelper.getShipmentSource.apply("MYNTRA");
	}

	public Date addDate(int days) {
		Date d = DateUtil.addDate(new Date(), days);
		return d;
	}

	public Date subtractDate(int days) {
		Date d = DateUtil.subtractDate(new Date(), 0);
		return d;
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addDC", description = "ID: CC225, Add delivery centres with diff combinations")
	public void addDC(String code, String name, String manager, String storeId, String address, String city,
			String cityCode, String state, String pincode, String selfShipSupported, String isStrictServiceable,
			String active, String isCardEnabled, String courierCode, String contactNumber, String DCtype,
			String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		DeliveryCenterType type = DeliveryCenterType.ML;
		if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("OTHER_LOGISTICS")) {
			type = DeliveryCenterType.OTHER_LOGISTICS;
		} else if (DCtype.equals("WHPL")) {
			type = DeliveryCenterType.WHPL;
		}
		LMSHelper lmsHepler = new LMSHelper();
		lmsHepler.delDelivery_center(code);

		DeliveryCenterResponse DCResponse = lmsServiceHelper.addDC(code, name, manager, Long.parseLong(storeId),
				address, city, cityCode, state, pincode, Boolean.parseBoolean(selfShipSupported),
				Boolean.parseBoolean(isStrictServiceable), Boolean.parseBoolean(active),
				Boolean.parseBoolean(isCardEnabled), courierCode, contactNumber, type);
		Assert.assertEquals(DCResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(DCResponse.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(DCResponse.getStatus().getStatusType().toString(), statusType);
		Assert.assertEquals(DCResponse.getDeliveryCenters().get(0).getCode().toString(), code);
		Assert.assertEquals(DCResponse.getDeliveryCenters().get(0).getName().toString(), name);
		Assert.assertEquals(DCResponse.getDeliveryCenters().get(0).getType().toString(), DCtype);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addDCNegative", description = "ID: C226,  Add delivery centres with diff combinations")
	public void addDCNegative(String code, String name, String manager, String storeId, String address, String city,
			String cityCode, String state, String pincode, String selfShipSupported, String isStrictServiceable,
			String active, String isCardEnabled, String courierCode, String contactNumber, String DCtype,
			String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		DeliveryCenterType type = DeliveryCenterType.ML;
		if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("OTHER_LOGISTICS")) {
			type = DeliveryCenterType.OTHER_LOGISTICS;
		} else if (DCtype.equals("WHPL")) {
			type = DeliveryCenterType.WHPL;
		}
		DeliveryCenterResponse DCResponse = lmsServiceHelper.addDC(code, name, manager, Long.parseLong(storeId),
				address, city, cityCode, state, pincode, Boolean.parseBoolean(selfShipSupported),
				Boolean.parseBoolean(isStrictServiceable), Boolean.parseBoolean(active),
				Boolean.parseBoolean(isCardEnabled), courierCode, contactNumber, type);
//		Assert.assertEquals(DCResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
//		Assert.assertEquals(DCResponse.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(DCResponse.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "updateDC", description = "ID: C281, Update delivery centres with diff combinations")
	public void updateDC(String id, String code, String name, String manager, String storeId, String address,
			String city, String cityCode, String state, String pincode, String selfShipSupported,
			String isStrictServiceable, String active, String isCardEnabled, String courierCode, String contactNumber,
			String DCtype) throws IOException, NumberFormatException, JAXBException {
		DeliveryCenterType type = DeliveryCenterType.ML;
		if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("FRANCHISE")) {
			type = DeliveryCenterType.FRANCHISE;
		} else if (DCtype.equals("OTHER_LOGISTICS")) {
			type = DeliveryCenterType.OTHER_LOGISTICS;
		} else if (DCtype.equals("WHPL")) {
			type = DeliveryCenterType.WHPL;
		}
		DeliveryCenterResponse DCResponse = lmsServiceHelper.updateDC(id, code, name, manager, Long.parseLong(storeId),
				address, city, cityCode, state, pincode, Boolean.parseBoolean(selfShipSupported),
				Boolean.parseBoolean(isStrictServiceable), Boolean.parseBoolean(active),
				Boolean.parseBoolean(isCardEnabled), courierCode, contactNumber, type);
		Assert.assertEquals(DCResponse.getStatus().getStatusCode(), 804);
		Assert.assertEquals(DCResponse.getStatus().getStatusMessage().toString(),
				"DELIVERY_CENTER updated successfully");
		Assert.assertEquals(DCResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDC", description = "ID: C251, get delivery centres with diff combinations")
	public void getDC(String pathParam, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException, JSONException, XMLStreamException {
		DeliveryCenterResponse DCResponse = lmsServiceHelper.getDC(pathParam);
		Assert.assertEquals(DCResponse.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addDeliveryStaff", description = "ID: C227")
	public void addDeliveryStaff(String code, String firstName, String lastName, String deliveryCenterId, String mobile,
			String createdBy, String available, String deleted, String modeOfCommute, String employeeCode,
			String isCardEnabled, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		lmsHelper.delDelivery_staff(code);
		DeliveryStaffCommute Commute;
		if (modeOfCommute.equals("CYCLIST")) {
			Commute = DeliveryStaffCommute.CYCLIST;
		} else if (modeOfCommute.equals("DELIVERY_VAN")) {
			Commute = DeliveryStaffCommute.DELIVERY_VAN;
		} else {
			Commute = DeliveryStaffCommute.BIKER;
		}
		DeliveryStaffResponse deliveryStaffResponse = lmsServiceHelper.addDeliveryStaff(code, firstName, lastName,
				Long.parseLong(deliveryCenterId), mobile, createdBy, Boolean.parseBoolean(available),
				Boolean.parseBoolean(deleted), Commute, employeeCode, Boolean.parseBoolean(isCardEnabled));
		Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusType().toString(), statusType);
		Assert.assertEquals(deliveryStaffResponse.getDeliveryStaffs().get(0).getCode().toString(), code);
		Assert.assertEquals(deliveryStaffResponse.getDeliveryStaffs().get(0).getModeOfCommute().toString(),
				Commute.toString());
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "updateDeliveryStaff", description = "ID: C282")
	public void updateDeliveryStaff(String id, String code, String firstName, String lastName, String deliveryCenterId,
			String mobile, String createdBy, String available, String deleted, String modeOfCommute,
			String employeeCode, String isCardEnabled, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		DeliveryStaffCommute Commute;
		if (modeOfCommute.equals("CYCLIST")) {
			Commute = DeliveryStaffCommute.CYCLIST;
		} else if (modeOfCommute.equals("DELIVERY_VAN")) {
			Commute = DeliveryStaffCommute.DELIVERY_VAN;
		} else {
			Commute = DeliveryStaffCommute.BIKER;
		}
		DeliveryStaffResponse deliveryStaffResponse = lmsServiceHelper.updateDeliveryStaff(id, code, firstName,
				lastName, Long.parseLong(deliveryCenterId), mobile, createdBy, Boolean.parseBoolean(available),
				Boolean.parseBoolean(deleted), Commute, employeeCode, Boolean.parseBoolean(isCardEnabled));
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertEquals(deliveryStaffResponse.getDeliveryStaffs().get(0).getCode().toString(), code);
			Assert.assertEquals(deliveryStaffResponse.getDeliveryStaffs().get(0).getModeOfCommute().toString(),
					Commute.toString());
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDeliveryStaff", description = "ID: C252")
	public void getDeliveryStaff(String pathParam, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		DeliveryStaffResponse deliveryStaffResponse = lmsServiceHelper.getDeliveryStaff(pathParam);
		Assert.assertEquals(deliveryStaffResponse.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "generateTrackingNumber", description = "ID: C242")
	public void generateTrackingNumber(String delTrackigNumber, String trackingNumbers, String courierCode,
			String start, String end, String increment, String prefix, String statusCode, String statusMessage,
			String statusType) throws IOException, NumberFormatException, JAXBException {
		LMSHelper lmsHepler = new LMSHelper();
		lmsHepler.deltracking_numbers(delTrackigNumber);
		TrackingNumberResponse trackingNumberResponse = lmsServiceHelper.generateTrackingNumber(courierCode,
				Long.parseLong(start), Long.parseLong(end), Integer.parseInt(increment), prefix);
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusType().toString(), statusType);
		Assert.assertEquals(trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber().toString(),
				trackingNumbers);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getTrackingNumber", description = "ID: C276")
	public void getTrackingNumber(String courier, String wh, String isCod, String pinCode, String shipmentType,
			String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		TrackingNumberResponse trackingNumberResponse = lmsServiceHelper.getTrackingNumber(courier, wh, isCod, pinCode,
				shipmentType);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(trackingNumberResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(trackingNumberResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertNotNull(trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber().toString());
			log.info(trackingNumberResponse.getTrackingNumberEntry().getTrackingNumber());
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addCourier", description = "ID: C224")
	public void addCourier(String code, String colorCode, String dailyCapacity, String enabled, String isRegional,
			String manifestTemplate, String name, String pickupSupported, String pickupTrackingNumberSplitEnabled,
			String regional, String returnSupported, String splitTrackingNumberEnabled, String trackingNumberSource,
			String trackingNumberGenerationSupported, String warehouseSplitEnabled, String website, String statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		if (statusType.equals(EnumSCM.SUCCESS)) {
			LMSHelper lmsHepler = new LMSHelper();
			lmsHepler.delCourier(code);
		}
		TrackingNoSource trackingNoSource;
		if (trackingNumberSource.equals("GENERATED")) {
			trackingNoSource = TrackingNoSource.GENERATED;
		} else if (trackingNumberSource.equals("PULLED")) {
			trackingNoSource = TrackingNoSource.PULLED;
		} else {
			trackingNoSource = TrackingNoSource.STORED;
		}
		CourierResponse courierResponse = lmsServiceHelper.addCourier(code, colorCode, Long.parseLong(dailyCapacity),
				Boolean.parseBoolean(enabled), Boolean.parseBoolean(isRegional), manifestTemplate, name,
				Boolean.parseBoolean(pickupSupported), Boolean.parseBoolean(pickupTrackingNumberSplitEnabled),
				Boolean.parseBoolean(regional), Boolean.parseBoolean(returnSupported),
				Boolean.parseBoolean(splitTrackingNumberEnabled), trackingNoSource,
				Boolean.parseBoolean(trackingNumberGenerationSupported), Boolean.parseBoolean(warehouseSplitEnabled),
				website);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertEquals(courierResponse.getCourierEntries().get(0).getCode().toString(), code);
			Assert.assertEquals(courierResponse.getCourierEntries().get(0).getColorCode().toString(), colorCode);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "updateCourier", description = "ID: C280")
	public void updateCourier(String id, String code, String colorCode, String dailyCapacity, String enabled,
			String isRegional, String manifestTemplate, String name, String pickupSupported,
			String pickupTrackingNumberSplitEnabled, String regional, String returnSupported,
			String splitTrackingNumberEnabled, String trackingNumberSource, String trackingNumberGenerationSupported,
			String warehouseSplitEnabled, String website, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		TrackingNoSource trackingNoSource;
		if (trackingNumberSource.equals("GENERATED")) {
			trackingNoSource = TrackingNoSource.GENERATED;
		} else if (trackingNumberSource.equals("PULLED")) {
			trackingNoSource = TrackingNoSource.PULLED;
		} else {
			trackingNoSource = TrackingNoSource.STORED;
		}
		CourierResponse courierResponse = lmsServiceHelper.updateCourier(id, code, colorCode,
				Long.parseLong(dailyCapacity), Boolean.parseBoolean(enabled), Boolean.parseBoolean(isRegional),
				manifestTemplate, name, Boolean.parseBoolean(pickupSupported),
				Boolean.parseBoolean(pickupTrackingNumberSplitEnabled), Boolean.parseBoolean(regional),
				Boolean.parseBoolean(returnSupported), Boolean.parseBoolean(splitTrackingNumberEnabled),
				trackingNoSource, Boolean.parseBoolean(trackingNumberGenerationSupported),
				Boolean.parseBoolean(warehouseSplitEnabled), website);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertEquals(courierResponse.getCourierEntries().get(0).getCode().toString(), code);
			Assert.assertEquals(courierResponse.getCourierEntries().get(0).getColorCode().toString(), colorCode);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getCourier", description = "ID: C247")
	public void getCourier(String pathParam, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		CourierResponse courierResponse = lmsServiceHelper.getCourier(pathParam);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(courierResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(courierResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(courierResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(courierResponse.getStatus().getTotalCount() > 0);
		}
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDropDownValuesCourier", description = "ID: C741, getDropDownValuesCourier")
	public void getDropDownValuesCourier(String className) throws Exception {
		Assert.assertEquals(
				APIUtilities.getElement(lmsServiceHelper.getDropDownValuesCourier.apply(className).toString(),
						"dropDownResponseResponse.status.statusType", "json"),
				EnumSCM.SUCCESS);
	}

	
	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getTripOrderAssignment", description = "ID: C742, getTripOrderAssignment")
	public void getTripOrderAssignment(String tripOrderStatus) throws Exception {
		String trackingNumber = DBUtilities.exSelectQueryForSingleRecord(
				"select tracking_no from trip_order_assignment where trip_order_status = '" + tripOrderStatus
						+ "' order by last_modified_on DESC",
				"lms").get("tracking_no").toString();
		Assert.assertEquals(
				((TripOrderAssignmentResponse) lmsServiceHelper.getTripOrderAssignment.apply(trackingNumber))
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "printMasterBagInvoice", description = "ID: C743, printMasterBagInvoice")
	public void printMasterBagInvoice(String status) throws Exception {
		String trackingNumber = DBUtilities.exSelectQueryForSingleRecord(
				"select som.shipment_id from shipment_order_map som join shipment sm on som.`shipment_id` = sm.id and "
						+ "sm.`courier_code` = 'ML' and som.status = '" + status
						+ "' order by sm.last_modified_on DESC",
				"lms").get("shipment_id").toString();
		Assert.assertEquals((int) lmsServiceHelper.printMasterBagInvoice.apply(trackingNumber), 200);
	}

	@Test(groups = { "Smoke", "Regression" }, priority = 0, description = "ID: C250")
	public void getCourierStatistics() throws IOException, NumberFormatException, JAXBException {
		Assert.assertEquals(lmsServiceHelper.getCourierStatistics().getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getCourierForPincode", description = "ID: C249")
	public void getCourierForPincode(String pincode, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		Assert.assertEquals(lmsServiceHelper.getCourierForPincode(pincode).getStatus().getStatusType().toString(),
				statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addRegion", description = "ID: C230")
	public void addRegion(String code, String name, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		if (statusType.equals(EnumSCM.SUCCESS)) {
			LMSHelper lmsHepler = new LMSHelper();
			lmsHepler.delRegion(code);
		}
		RegionResponse regionResponse;
		if (name.isEmpty()) {
			regionResponse = lmsServiceHelper.addRegion(code);
		} else {
			regionResponse = lmsServiceHelper.addRegion(code, name);
		}
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(regionResponse.getStatus().getTotalCount() > 0);
			Assert.assertEquals(regionResponse.getRegions().get(0).getCode().toString(), code);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "updateRegion", description = "ID: C284")
	public void updateRegion(String id, String code, String name, String statusCode, String statusMessage,
			String statusType) throws IOException, NumberFormatException, JAXBException {
		RegionResponse regionResponse = lmsServiceHelper.updateRegion(id, code, name);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(regionResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(regionResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			//Assert.assertEquals(regionResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			//Assert.assertEquals(regionResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(regionResponse.getStatus().getTotalCount() > 0);
			Assert.assertEquals(regionResponse.getRegions().get(0).getCode().toString(), code);
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getRegion", description = "ID: C269")
	public void getRegion(String pathParams, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		RegionResponse regionResponse = lmsServiceHelper.getRegion(pathParams);
		if (statusType.equals(EnumSCM.ERROR) || pathParams.equals("54000")) {
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(regionResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(regionResponse.getStatus().getTotalCount() > 0);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addPincode", description = "ID: C229")
	public void addPincode(String id, String areaCode, String areaName, String cityCode, String cityName,
			String regionCode, String state, String stateCode, String createdBy, String statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		if (statusType.equals(EnumSCM.SUCCESS)) {
			LMSHelper lmsHepler = new LMSHelper();
			lmsHepler.delPincode(id);
		}
		PincodeResponse pincodeResponse = lmsServiceHelper.addPincode(Long.parseLong(id), areaCode, areaName, cityCode,
				cityName, regionCode, state, stateCode, createdBy);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(pincodeResponse.getStatus().getTotalCount() > 0);
			Assert.assertEquals(pincodeResponse.getPincodes().get(0).getAreaCode(), areaCode);
			Assert.assertEquals(pincodeResponse.getPincodes().get(0).getCityCode(), cityCode);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "updatePincode", description = "ID: C283")
	public void updatePincode(String id, String areaCode, String areaName, String cityCode, String cityName,
			String regionCode, String state, String stateCode, String createdBy, String statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		PincodeResponse pincodeResponse = lmsServiceHelper.updatePincode(id, areaCode, areaName, cityCode, cityName,
				regionCode, state, stateCode, createdBy);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(pincodeResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(pincodeResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(pincodeResponse.getStatus().getTotalCount() > 0);
			Assert.assertEquals(pincodeResponse.getPincodes().get(0).getAreaCode(), areaCode);
			Assert.assertEquals(pincodeResponse.getPincodes().get(0).getCityCode(), cityCode);
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getPincode", description = "ID: C265")
	public void getPincode(String pathParam, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		PincodeResponse pincodeResponse = lmsServiceHelper.getPincode(pathParam);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(pincodeResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(pincodeResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(pincodeResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(pincodeResponse.getStatus().getTotalCount() > 0);
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getPincodeV1", description = "ID: C266")
	public void getPincodeV1(String pincode, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		PincodeResponseV2 respose = lmsServiceHelper.getPincodeV1(pincode);
		Assert.assertEquals(respose.getStatus().getStatusType().toString(), statusType);
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertEquals(respose.getPincodes().get(0).getId().toString(), pincode);
			Assert.assertNotNull(respose.getPincodes().get(0).getAreaCode());
			Assert.assertNotNull(respose.getPincodes().get(0).getCityCode());
			Assert.assertNotNull(respose.getPincodes().get(0).getRegionCode());
			Assert.assertNotNull(respose.getPincodes().get(0).getStateCode());
		}
	}

	@Test(groups = { "Smoke", "Regression" }, priority = 0, description = "ID: C267")
	public void getPincodeWithCourierCode() throws IOException, NumberFormatException, JAXBException {
		PincodeResponse pincodeResponse = lmsServiceHelper.getPincode(LMS_PINCODE.ML_BLR, "ML");
		Assert.assertEquals(pincodeResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getLmsOrders", description = "ID: C258")
	public void getLmsOrders(String pathParam, String statusCode, String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException {
		OrderResponse orderResponse = lmsServiceHelper.getLmsOrdersByParama(pathParam);
		if (statusType.equals(EnumSCM.ERROR)) {
			Assert.assertEquals(orderResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(orderResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), statusType);
		} else {
			Assert.assertEquals(orderResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(orderResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), statusType);
			// Assert.assertTrue(orderResponse.getStatus().getTotalCount()>0);
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "bulkUpload", description = "ID: C233, Update order Tracking in bulk")
	public void bulkUpload(String orderId, String trackingNumber, String activityType, String extTrackingCode,
			String remark, String location, String courierOperator, String statusCode, String statusType)
			throws IOException, NumberFormatException, JAXBException, ParseException {
		OrderTrackingResponse orderTrackingResponse = lmsServiceHelper.bulkUpdateOrderTrackiing("" + orderId,
				trackingNumber, activityType, extTrackingCode, remark, location, courierOperator);
		Assert.assertEquals(orderTrackingResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		// Assert.assertEquals(orderTrackingResponse.getStatus().getStatusMessage().toString(),
		// statusMessage);
		Assert.assertEquals(orderTrackingResponse.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "generateTrackingNumberManual", description = "ID: C243")
	public void generateTrackingNumberManual(String courierCode, String type, String prefix, String numbers,
			String statusCode, String statusMessage, String statusType) throws JAXBException, IOException {
		TrackingNumberResponse trackingNumberResponse = lmsServiceHelper.generateTrackingNumberManual(courierCode, type,
				prefix, numbers);
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(trackingNumberResponse.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrder", description = "ID: C259, getOrderByOrderId", enabled = true)
	public void getOrderByOrderId(String status, String shippingMethod, String statusCode, String statusMessage,
			String statusType) throws IOException, NumberFormatException, JAXBException, SQLException {
		Map<String, Object> order = DBUtilities
				.exSelectQueryForSingleRecord("select order_id from order_to_ship where status = '" + status
						+ "' and shipping_method = '" + shippingMethod + "' order by packed_on desc limit 1", "lms");
		String orderId = "" + order.get("order_id");
		OrderResponse response = lmsServiceHelper.getOrderByOrderId(orderId);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrder", description = "ID: C260, getOrderByOrderId", enabled = true)
	public void getOrderByOrderIdWithParam(String status, String shippingMethod, String statusCode,
			String statusMessage, String statusType)
			throws IOException, NumberFormatException, JAXBException, SQLException {
		Map<String, Object> order = DBUtilities
				.exSelectQueryForSingleRecord("select order_id from order_to_ship where status = '" + status
						+ "' and shipping_method = '" + shippingMethod + "' order by packed_on desc limit 1", "lms");
		String orderId = "" + order.get("order_id");
		OrderResponse response = lmsServiceHelper.getOrderByOrderIdByParam(orderId);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getMLOrder", description = "ID: C744, getMLOrder", enabled = true)
	public void getMLOrder(String status, String shippingMethod, String statusType) throws Exception {
		String trackingNumber = DBUtilities
				.exSelectQueryForSingleRecord("select tracking_number from ml_shipment where shipment_status = '"
						+ status + "' and shipping_method = '" + shippingMethod
						+ "' and source_id = 3762 order by last_modified_on desc limit 1", "lms")
				.get("tracking_number").toString();
		Assert.assertEquals(((MLShipmentResponse) lmsServiceHelper.getOrderML.apply(trackingNumber)).getStatus()
				.getStatusType().toString(), statusType);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getMLTrackingDetails", description = "ID: C745, getMLTrackingDetaild", enabled = true)
	public void getMLTrackingDetails(String status, String shippingMethod, String statusType) throws Exception {
		String trackingNumber = DBUtilities
				.exSelectQueryForSingleRecord("select tracking_number from ml_shipment where shipment_status = '"
						+ status + "' and shipping_method = '" + shippingMethod
						+ "' and source_id = 3762 order by last_modified_on desc limit 1", "lms")
				.get("tracking_number").toString();
		Assert.assertEquals(((OrderTrackingResponseV2) lmsServiceHelper.getMLTrackingDetails.apply(trackingNumber))
				.getStatus().getStatusType().toString(), statusType);
	}

	@Deprecated
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "pinCodeServiceable", description = "ID: C746, pinCodeServiceable", enabled = false)
	public void pinCodeServiceable(String courierCode, String pincode, String pymentMode, String statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		PincodeResponse response = lmsServiceHelper.pinCodeServiceable(courierCode, pincode, pymentMode);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "networkSearch", description = "ID: C278, networkSearch", enabled = false)
	public void networkSearch(String sourceId, String destId, String sourceType, String destType, String statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		NetworkResponse response = lmsServiceHelper.networkSearch(sourceId, destId, sourceType, destType);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllIncompleteOrdersForDC", description = "ID: C245, getAllIncompleteOrdersForDC", enabled = true)
	public void getAllIncompleteOrdersForDC(String queryParam, String statusCode, String statusMessage,
			String statusType) throws IOException, NumberFormatException, JAXBException {
		OrderResponse response = lmsServiceHelper.getAllIncompleteOrdersForDC(queryParam);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		// Assert.assertEquals(response.getStatus().getStatusMessage().toString(),
		// statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getReturnAddress", description = "ID: C270, getReturnAddress", enabled = true)
	public void getReturnAddress(String zipcode, String courierCode, long sourceWarehouseId, int statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		ReturnAddress response = lmsServiceHelper.getReturnAddress(zipcode, courierCode, sourceWarehouseId);
		// Assert.assertEquals(response.getStatus().getStatusCode(), statusCode);
		// Assert.assertEquals(response.getStatus().getStatusMessage().toString(),
		// statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getRtoAddress", description = "ID: C274, getRtoAddress", enabled = true)
	public void getRtoAddress(String zipcode, String courierCode, long sourceWarehouseId, int statusCode,
			String statusMessage, String statusType) throws IOException, NumberFormatException, JAXBException {
		ReturnAddress response = lmsServiceHelper.getReturnAddress(zipcode, courierCode, sourceWarehouseId);
		// Assert.assertEquals(response.getStatus().getStatusCode(), statusCode);
		// Assert.assertEquals(response.getStatus().getStatusMessage().toString(),
		// statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getReturnInLMS", description = "ID: C273", enabled = true)
	public void getReturnInLMS(String shipmentStatus, String statusType) throws Exception {
		Map<String, Object> returnShipment = DBUtilities
				.exSelectQueryForSingleRecord("select * from return_shipment where shipment_status = '" + shipmentStatus
						+ "' order by last_modified_on DESC limit 1", "lms");
		OrderResponse response = lmsServiceHelper.getReturnsInLMS(returnShipment.get("source_return_id").toString());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
		Assert.assertEquals(response.getOrders().get(0).getSourceReturnId().toString(),
				returnShipment.get("source_return_id").toString());
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrderTrackingDetail", description = "ID: C262", enabled = true)
	public void getOrderTrackingDetail(String courierCode, String warehouseId, String StatusType) throws Exception {
		Map<String, Object> tracking = DBUtilities
				.exSelectQueryForSingleRecord("select tracking_number from order_to_ship where courier_code = '"
						+ courierCode + "' and warehouse_id = " + warehouseId, "lms");
		OrderTrackingResponse response = lmsServiceHelper.getOrderTrackingDetail(courierCode,
				tracking.get("tracking_number").toString());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrderTrackingDetail", description = "ID: C263", enabled = true)
	public void getOrderTrackingDetailV2(String courierCode, String warehouseId, String StatusType) throws Exception {
		Map<String, Object> tracking = DBUtilities
				.exSelectQueryForSingleRecord("select tracking_number from order_to_ship where courier_code = '"
						+ courierCode + "' and warehouse_id = " + warehouseId, "lms");
		OrderTrackingResponse response = lmsServiceHelper.getOrderTrackingDetailV2(courierCode,
				tracking.get("tracking_number").toString());
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrderTrackingDetailWithData", description = "ID: C264", enabled = true)
	public void getOrderTrackingDetailWithData(String courierCode, String shipmentStatus, String deliveryStatus,
			String StatusType) throws Exception {
		Map<String, Object> orderToShip = DBUtilities.exSelectQueryForSingleRecord(
				"select tracking_number from order_to_ship where shipment_status = '" + shipmentStatus + "' and "
						+ "courier_code = '" + courierCode + "' and tracking_number != 'null' order by RAND() limit 1",
				"lms");
		String trackingNumber = orderToShip.get("tracking_number").toString();
		OrderTrackingResponse response = lmsServiceHelper.getOrderTrackingDetail(courierCode, trackingNumber);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
		Assert.assertEquals(response.getOrderTrackings().get(0).getDeliveryStatus().toString(), deliveryStatus);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "printShipmentLabel", description = "ID: C279", enabled = true)
	public void printShipmentLabel(String courierCode, int destId, String StatusType) throws Exception {
		Map<String, Object> shipment = DBUtilities
				.exSelectQueryForSingleRecord("select id from shipment where order_count > 0 and dest_premises_id = "
						+ destId + " " + "order by last_modified_on DESC", "lms");
		if (shipment != null && shipment.size() == 0) {
			Assert.fail("Unable to find shipment/MasterBag of the mentioned Type");
		}
		String response = lmsServiceHelper.printShipmentLabel(shipment.get("id").toString());
		APIUtilities.validateResponse("xml", response, "mlShipmentResponse.status.statusType=='SUCCESS'");
	}

	@Deprecated
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "shipmentManifestReport", description = "manifestReport", enabled = false)
	public void shipmentManifestReport(String destId, String courierCode, String StatusType) throws Exception {
		Map<String, Object> shipment = DBUtilities
				.exSelectQueryForSingleRecord("select * from shipment where order_count > 0 and dest_premises_id = "
						+ destId + " " + "order by last_modified_on DESC", "lms");
		if (shipment.size() == 0) {
			Assert.fail("Unable to find shipment/MasterBag of the mentioned Type");
		}
		ShipmentResponse response = lmsServiceHelper.shipmentManifestReport(shipment.get("id").toString(), courierCode);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "handoverToRegionalCourier", description = "ID: C277, handoverToRegionalCourier", enabled = true)
	public void handoverToRegionalCourier(long shipmentId, String StatusType) throws Exception {
		ShipmentResponse response = lmsServiceHelper.handoverToRegionalCourier(shipmentId);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getHub", description = "ID: C256, getHub", enabled = true)
	public void getHub(String param, String StatusType) throws Exception {
		HubResponse response = lmsServiceHelper.getHub(param);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "addHub", description = "ID: C228, Add delivery centres with diff combinations")
	public void addHub(String code, String name, String manager, String address, String city, String state,
			String pincode, HubType type, String statusType) throws IOException, JAXBException {
		lmsHelper.delhub(code);
		HubResponse hubResponse = lmsServiceHelper.addHub(code, name, manager, address, city, state, pincode, type);
		Assert.assertEquals(hubResponse.getStatus().getStatusType().toString(), statusType);
		Assert.assertEquals(lmsServiceHelper
				.editHub(hubResponse.getHub().get(0).getId(), code, name, "Random", address, city, state, pincode, type)
				.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getQCPendencyPage", description = "ID: C268, getQCPendencyPage", enabled = true)
	public void getQCPendencyPage(String param, String StatusType) throws Exception {
		String response = lmsServiceHelper.getQCPendencyPage(param);
		APIUtilities.validateResponse("xml", response, "mlShipmentResponse.status.statusType=='" + StatusType + "'");
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllCourierDeatils", description = "ID: C244, getAllCourierDeatils", enabled = true)
	public void getAllCourierDeatils(String param, String StatusType) throws Exception {
		CourierResponse response = lmsServiceHelper.getAllCourierDetail(param);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
	}

	// Here we have disabled WH 19 for Cancellation till Hub and enabled all the
	// other warehouses
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "canCancelShipment", description = "ID: C234, canCancelShipment", enabled = true)
	public void canCancelShipment(String lmsOrderStatus, int warehouseId, boolean isTransitionAllowed,
			String shippingMethod, String StatusType) throws Exception {
		List<Object> orderIds = new ArrayList<>();
		if (lmsOrderStatus.equals("WP")) {
			Map<String, Object> orderId = DBUtilities.exSelectQueryForSingleRecord(
					"select id from order_release where status_code = 'WP' and shipping_method = '" + shippingMethod
							+ "' order by  last_modified_on DESC limit 1",
					"oms");
			orderIds.add((Long) orderId.get("id"));
			ValidTransitionResponse response = lmsServiceHelper.canCancelShipment(orderIds);
			Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType);
			Assert.assertEquals(response.getValidTransitionEntryList().get(0).isTransitionAllowed(),
					isTransitionAllowed);
		} else {
			Map<String, Object> orderId = DBUtilities
					.exSelectQueryForSingleRecord("select order_id from order_to_ship where warehouse_id = "
							+ warehouseId + " and shipment_status = '" + lmsOrderStatus + "' and shipping_method = '"
							+ shippingMethod + "' order by last_modified_on DESC limit 1", "lms");
			orderIds.add(orderId.get("order_id").toString());
			ValidTransitionResponse response = lmsServiceHelper.canCancelShipment(orderIds);
			Assert.assertEquals(response.getStatus().getStatusType().toString(), StatusType,
					"Status is not same in the response");
			Assert.assertEquals(response.getValidTransitionEntryList().get(0).isTransitionAllowed(),
					isTransitionAllowed, "Is transition is not same as expected");
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 1, dataProviderClass = LMSTestsDP.class, dataProvider = "checkServiceability", description = "ID: C239")
	public void checkServiceability(String pincode, long warehouseId, boolean isExpress, boolean shipping,
			String payment, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF,
			boolean isL, boolean isC, int cod, int mrp) throws Exception {
		int codLimit = 1000, mrpLimit = 2000;
		CourierServiceabilityInfoResponse response = lmsServiceHelper.checkServiceability(warehouseId, false, isExpress,
				shipping, payment, pincode, service, shippingMode, isN, isP, isH, isF, isL, isC, (mrpLimit + mrp),
				(codLimit + cod), false);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 1, dataProviderClass = LMSTestsDP.class, dataProvider = "checkServiceability", description = "ID: C240")
	public void checkServiceabilitySummery(String pincode, long warehouseId, boolean isExpress, boolean shipping,
			String payment, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF,
			boolean isL, boolean isC, int cod, int mrp) throws Exception {
		int codLimit = 1000, mrpLimit = 2000;
		CourierServiceabilityInfoResponse response = lmsServiceHelper.checkServiceabilitySummary(warehouseId, false,
				isExpress, shipping, payment, pincode, service, shippingMode, isN, isP, isH, isF, isL, isC,
				(mrpLimit + mrp), (codLimit + cod), false);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 1, dataProviderClass = LMSTestsDP.class, dataProvider = "checkServiceability", description = "ID: C241")
	public void checkServiceabilityWithAttributes(String pincode, long warehouseId, boolean isExpress, boolean shipping,
			String payment, String service, String shippingMode, boolean isN, boolean isP, boolean isH, boolean isF,
			boolean isL, boolean isC, int cod, int mrp) throws Exception {
		int codLimit = 1000, mrpLimit = 2000;
		CourierServiceabilityInfoResponse response = lmsServiceHelper.checkServiceabilityWithAttributes(warehouseId,
				false, isExpress, shipping, payment, pincode, service, shippingMode, isN, isP, isH, isF, isL, isC,
				(mrpLimit + mrp), (codLimit + cod), false);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, dataProviderClass = LMSTestsDP.class, dataProvider = "addTransporter", description = "ID: C231, This test will add a transporter and then delete as well", priority = 1, enabled = false)
	public void addTransporter(String transporterNumber, String transporterName, long whId, long dcId)
			throws Exception {
		DBUtilities.exUpdateQuery("delete from transporter where number = '" + transporterNumber + "' and name = '"
				+ transporterName + "';", "lms");
		DBUtilities.exUpdateQuery("delete from network where source_id = " + whId + " and destination_id = " + dcId,
				"lms");
		Assert.assertEquals(lmsServiceHelper.addTransporter(transporterNumber, transporterName, whId, dcId).getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, dataProviderClass = LMSTestsDP.class, dataProvider = "addTransporterNetwork", description = "This test will add a transporter and then delete as well", priority = 1, enabled = false)
	public void addTransporterNetwork(String transporterNumber, String transporterName, long whId, long dcId)
			throws Exception {
		DBUtilities.exUpdateQuery("delete from transporter where number = '" + transporterNumber + "' and name = '"
				+ transporterName + "';", "lms");
		DBUtilities.exUpdateQuery("delete from network where source_id = " + whId + " and destination_id = " + dcId,
				"lms");
		Assert.assertEquals(lmsServiceHelper.addTransporter(transporterNumber, transporterName, whId, dcId).getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@Test(groups = { "Smoke",
			"Regression" }, dataProviderClass = LMSTestsDP.class, dataProvider = "getAllTransporter", description = "ID: C246, This test will get all the transporter", priority = 1, enabled = false)
	public void getAllTransporter(String param) throws Exception {
		Assert.assertEquals(lmsServiceHelper.getAllTransporter(param).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "cancelShipmentInLMS", description = "ID: C235, cancelShipmentInLMS", enabled = true)
	public void cancelShipmentInLMS(String toStatus, String lmsStatus, int warehouseId, String shippingMethod,
			boolean isTod, String statusType) throws Exception {
		String orderId = lmsHelper.createMockOrder(toStatus, LMS_PINCODE.ML_BLR, "ML", "" + warehouseId, shippingMethod, "CC", isTod, true);
		String packetId = omsServiceHelper.getPacketId(orderId);
		String releaseId = omsServiceHelper.getReleaseId(orderId);
		if (lmsStatus.equals(EnumSCM.INSCANNED))
			Assert.assertEquals(lmsServiceHelper.orderInScanNew(packetId), EnumSCM.SUCCESS);
		else if (lmsStatus.equals(EnumSCM.ADDED_TO_MB)) {
			
			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.ADDED_TO_MB).force(true).build());
			processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		}
		String response = lmsServiceHelper.cancelShipmentInLMS(packetId);
		Assert.assertEquals(response, statusType, "Expected status is not equal");
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertEquals(lmsHelper.getOrderToShipStatus(packetId), "CANCELLED_IN_HUB",
					"DB is not updated to CANCELLED_IN_HUB");
			long masterBagId = lmsServiceHelper.createMasterBag(warehouseId, "WH", 5, "DC", shippingMethod, "ML")
					.getEntries().get(0).getId();
			Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + orderId, "" + masterBagId, ShipmentType.DL),
					EnumSCM.ERROR, "Status mismatch");
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, description = "cID: 236, ancelShipmentInLMSAndRestock", enabled = true)
	public void cancelShipmentInLMSAndRestock() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderReleaseId = omsServiceHelper.getPacketId(orderId);
		Assert.assertEquals(omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", LMS_LOGIN.LogIn1, "test")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(orderReleaseId, EnumSCM.F, 5));
		Assert.assertEquals(
				rmsServiceHelper.recieveShipmentInRejoy(orderReleaseId, 36).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertTrue(
				lmsServiceHelper.validateOrderStatusInLMS("" + orderReleaseId, EnumSCM.RECEIVED_IN_RETURNS_HUB, 3));
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "cancelShipmentInLMSNegative", description = "ID: C238, cancelShipmentInLMSNegative", enabled = true)
	public void cancelShipmentInLMSNegative(String lmsStatus, int warehouseId, String shippingMethod, String statusType)
			throws Exception {
		if (lmsStatus.equals("WP")) {
			Map<String, Object> orderRelease = DBUtilities
					.exSelectQueryForSingleRecord("select * from order_release where warehouse_id = " + warehouseId
							+ " and status_code = '" + lmsStatus + "' and shipping_method = '" + shippingMethod
							+ "' and tracking_no IS NOT NULL order by last_modified_on DESC limit 1", "oms");
			String response = lmsServiceHelper.updateShipmentStatus("" + orderRelease.get("id"),
					orderRelease.get("tracking_no").toString(), ShipmentUpdateEvent.CANCEL, ShipmentType.DL);
			Assert.assertEquals(
					APIUtilities.getElement(response, "ShipmentUpdateResponse.shipmentUpdateResponseCode", "json"),
					statusType, "Expected status is not equal");
		} else {
			Map<String, Object> orderToShip = DBUtilities
					.exSelectQueryForSingleRecord("select * from order_to_ship where warehouse_id = " + warehouseId
							+ " and shipment_status = '" + lmsStatus + "' and shipping_method = '" + shippingMethod
							+ "' order by last_modified_on DESC limit 1", "lms");
			if (orderToShip == null || orderToShip.isEmpty())
				Assert.assertEquals(orderToShip, null,
						"select * from order_to_ship where warehouse_id = " + warehouseId + " and shipment_status = '"
								+ lmsStatus + "' and shipping_method = '" + shippingMethod
								+ "' order by last_modified_on DESC limit 1 is not resulting any result");
			String response = lmsServiceHelper.cancelShipmentInLMS("" + orderToShip.get("order_id"));
			Assert.assertEquals(response, statusType, "Expected status is not equal");
		}
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, description = "ID: C237, cancelShipmentInLMSEdgeCase", enabled = true)
	public void cancelShipmentInLMSEdgeCase() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "on",
				false, true);
		String orderReleaseId = omsServiceHelper.getPacketId(orderId);
		Assert.assertEquals(lmsServiceHelper.orderInScanNew("" + orderReleaseId), EnumSCM.SUCCESS);
		ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(LMS_PINCODE.ML_BLR, "36",
				ShippingMethod.NORMAL, ((OrderResponse) lmsServiceHelper.getOrderLMS.apply(orderReleaseId)).getOrders()
						.get(0).getDeliveryCenterId().toString());
		long masterBagId = shipmentResponse.getEntries().get(0).getId();
		List<Object> orders = new ArrayList<>();
		orders.add(orderReleaseId);
		ValidTransitionResponse response = lmsServiceHelper.canCancelShipment(orders);
		Assert.assertEquals(response.getValidTransitionEntryList().get(0).isTransitionAllowed(), true);
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag("" + orderReleaseId, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS, "Unable to add order into the masterBag");
		ValidTransitionResponse response1 = lmsServiceHelper.canCancelShipment(orders);
		Assert.assertEquals(response1.getValidTransitionEntryList().get(0).isTransitionAllowed(), false);
		String response2 = lmsServiceHelper.cancelShipmentInLMS(orderReleaseId);
		Assert.assertEquals(response2, EnumSCM.TRANSITION_NOT_CONFIGURED, "Expected status is not equal");
		lmsHelper.updateTestOrder("" + orderId);
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "bulkOrderReassignmentForOnHand", description = "ID: C232, Bulk ordeReassinment", enabled = true)
	public void bulkOrderReassignmentForOnHand(String status, String pincode, String from, String to,
			String serviceType, String shippingMethod, boolean isTod, String paymentMode, boolean isMultiseller,
			String statusType) throws Exception {
		
		String orderId = lmsHelper.createMockOrder(status, pincode, from, "36", shippingMethod, paymentMode, isTod, isMultiseller);
		String packetId = omsServiceHelper.getPacketId(orderId);
		String shippingMethod1 = shippingMethod;
		if (shippingMethod.equals(EnumSCM.XPRESS))
			shippingMethod1 = EnumSCM.EXPRESS;
		Assert.assertEquals(
				lmsServiceHelper.bulkOrderReassignment(packetId, from, to, serviceType, shippingMethod1, paymentMode).getStatus().getStatusType().toString(),
				statusType, "Order reassignment status is not expected (SUCCESS/ERROR)");
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Thread.sleep(3000);
			Map<String, Object> release = DBUtilities.exSelectQueryForSingleRecord("select * from packet where id = " + packetId, "oms");
			Assert.assertEquals(release.get("courier_code").toString(), to);
			Assert.assertNotNull(release.get("tracking_no").toString());
		}
	}

	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "bulkOrderReassignmentForJIT", description = "ID: 546 , Bulk ordeReassinment for JUST_IN_TIME order from LMS when order is in WP state", enabled = true)
	public void bulkOrderReassignmentForJIT(String pincode, String from, String to, String serviceType,
			String shippingMethod, boolean isTod, String paymentMode, String statusType) throws Exception {
		String orderID = lmsHelper.createMockOrderForJITinWP(pincode, from, "36", shippingMethod, paymentMode, isTod, 1029L);
		String packetId = omsServiceHelper.getPacketId(orderID);
		String shippingMethod1 = shippingMethod;
		if (shippingMethod.equals(EnumSCM.XPRESS))
			shippingMethod1 = EnumSCM.EXPRESS;
		Assert.assertEquals(
				lmsServiceHelper.bulkOrderReassignment(packetId, from, to, serviceType, shippingMethod1, paymentMode).getStatus().getStatusType().toString(),
				statusType, "Order reassignment status is not expected (SUCCESS/ERROR)");
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Thread.sleep(3000);
			Map<String, Object> release = DBUtilities
					.exSelectQueryForSingleRecord("select * from packet where id = " + packetId, "oms");
			Assert.assertEquals(release.get("courier_code").toString(), to);
			Assert.assertNotNull(release.get("tracking_no").toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "reassignCourierInLMS", description = "ID: C568, Reassign courier from LMS orders tab.", enabled = true)
	public void reassignCourierInLMS(String status, String pincode, String from, String to, String shippingMethod,
			boolean isTod, String paymentMode, boolean isMultiseller, String statusType) throws Exception {
		
		String orderId = lmsHelper.createMockOrder(status, pincode, from, "36",shippingMethod, paymentMode, isTod, isMultiseller);
		String packetId = omsServiceHelper.getPacketId(orderId);
		String releaseId = omsServiceHelper.getReleaseId(orderId);
		OrderResponse orderInitially = (OrderResponse) lmsServiceHelper.getOrderLMS.apply(packetId);
		Assert.assertEquals(((OrderTrackingResponse) lmsServiceHelper.reassignCourierOnLMS.apply(packetId, to))
				.getStatus().getStatusType().toString(), statusType, "Unable to reassign courier");
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Thread.sleep(2000);
			OrderResponse orderResponse = (OrderResponse) lmsServiceHelper.getOrderLMS.apply(packetId);
			Assert.assertEquals(orderResponse.getOrders().get(0).getStatus().toString(), EnumSCM.PK,
					"Status not moved to PK");
			Assert.assertEquals(orderResponse.getOrders().get(0).getCourierOperator(), to,
					"Courier code not matching to the assigned courier");
			Assert.assertNotEquals(orderResponse.getOrders().get(0).getTrackingNumber(),
					orderInitially.getOrders().get(0).getTrackingNumber(),
					"TrackingNumber should got changed but still the same");
			if (from.equals("IP") && to.equals("BD"))
				log.info("No need to validate Delivery center in this case cozz both will be NULL");
			else
				Assert.assertNotEquals(orderResponse.getOrders().get(0).getDeliveryCenterId(),orderInitially.getOrders().get(0).getDeliveryCenterId(),
						"Delivery Center not getting update, Its still the same");
			
			List<ReleaseEntry> releaseEntries = new ArrayList<>();
			releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.DL).force(true).build());
			processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getOrderTracking", description = "ID: C261, getOrderTracking", enabled = true)
	public void getOrderTracking(String toStatus, String statusCode, String statusMessage, String statusType)
			throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(toStatus, LMS_PINCODE.ML_BLR, "ML",
				"36", EnumSCM.NORMAL, "cod", false, false));
		OrderTrackingResponse response = lmsServiceHelper.getOrderTracking("" + releaseId, EnumSCM.DL);
		Assert.assertEquals(response.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(response.getStatus().getStatusMessage().toString(), statusMessage);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), statusType);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, description = "ID: C255, getFailedDeliveryReasonCode or attemp reason code", enabled = true)
	public void getFailedDeliveryReasonCode() throws Exception {
		AttemptReasonCodeResponse response = (AttemptReasonCodeResponse) lmsServiceHelper.getAttemptReasonCode.get();
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(response.getAttemptReasonCodeEntries().get(0).getShipmentType().toString(), EnumSCM.DL);
		Assert.assertEquals(response.getAttemptReasonCodeEntries().get(1).getShipmentType().toString(),
				EnumSCM.EXCHANGE);
		Assert.assertEquals(response.getAttemptReasonCodeEntries().get(2).getShipmentType().toString(), EnumSCM.PU);
		Assert.assertEquals(response.getAttemptReasonCodeEntries().get(3).getShipmentType().toString(),
				EnumSCM.TRY_AND_BUY);
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getShippingCutoff", description = "ID: C275, getShippingCutoff")
	public void getShippingCutOff(String hr, String pincode, long warehouse, ShippingMethod shippingMethod,
			String courier, String statusType) throws IOException, NumberFormatException, JAXBException {
		ShippingCutoffResponse respose = lmsServiceHelper.getShippingCutOff(hr, pincode, "" + warehouse, shippingMethod,
				courier);
		Assert.assertEquals(respose.getStatus().getStatusType().toString(), statusType);
		Assert.assertNotNull(respose.getData().get(0));
	}

//	@Test(groups = { "P0", "Smoke",
//			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDispatchHubFromWarehouse", description = "ID: C253, getDispatchHubFromWarehouse")
//	public void getDispatchHubFromWarehouse(int whId, String statusType) throws Exception {
//		HubWareHouseConfigResponse repsonse = (HubWareHouseConfigResponse) lmsServiceHelper.getDispatchHubFromWarehouse
//				.apply(whId);
//		Assert.assertEquals(repsonse.getStatus().getStatusType().toString(), statusType);
//		Assert.assertNotNull(repsonse.getHubWarehouseConfigEntries().get(0).getHubCode());
//	}

//	@Test(groups = { "P0", "Smoke",
//			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDispatchHubFromWarehouse", description = "ID: C271, getReturnHubFromWarehouse")
//	public void getReturnHubFromWarehouse(int whId, String statusType) throws Exception {
//		ReturnHubWarehouseResponse repsonse = (ReturnHubWarehouseResponse) lmsServiceHelper.getReturnHubFromWarehouse
//				.apply(whId);
//		Assert.assertNotNull(repsonse.getReturnProcessingConfigEntries().get(0).getHubCode());
//	}
//
//	@Test(groups = { "P0", "Smoke",
//			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getDispatchHubWareHouseConfigList", description = "ID: C254, getDispatchHubWareHouseConfigList")
//	public void getDispatchHubWareHouseConfigList(String hubCode, String statusType) throws Exception {
//		HubWareHouseConfigResponse repsonse = (HubWareHouseConfigResponse) lmsServiceHelper.getDispatchHubWareHouseConfigList
//				.apply(hubCode);
//		Assert.assertEquals(repsonse.getStatus().getStatusType().toString(), statusType);
//		Assert.assertNotNull(repsonse.getHubWarehouseConfigEntries().get(0).getHubCode());
//	}
//
//	@Test(groups = { "P0", "Smoke",
//			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getReturnHubWareHouseConfig", description = "ID: C272, getReturnHubWareHouseConfig")
//	public void getReturnHubWareHouseConfig(String hubCode, String statusType) throws Exception {
//		ReturnHubWarehouseResponse repsonse = (ReturnHubWarehouseResponse) lmsServiceHelper.getReturnHubWareHouseConfig
//				.apply(hubCode);
//		Assert.assertEquals(repsonse.getStatus().getStatusType().toString(), statusType);
//		Assert.assertNotNull(repsonse.getReturnProcessingConfigEntries().get(0).getWarehouseId());
//	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getHubCourierConfig", description = "ID: C257")
	public void getHubCourierConfig(String hubCode, String courierCode, String statusType)
			throws IOException, NumberFormatException, JAXBException, InterruptedException, JSONException,
			XMLStreamException, ManagerException {
		HubCourierHandoverConfigResponse repsonse = (HubCourierHandoverConfigResponse) lmsServiceHelper.getHubCourierConfig
				.apply(hubCode, courierCode);
		Assert.assertEquals(repsonse.getStatus().getStatusType().toString(), statusType);
		Assert.assertNotNull(repsonse.getCourierHandoverConfigEntries().get(0).getPincode());
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getCourierByPincode", description = "ID: C248, getCourierByPincode")
	public void getCourierByPincode(long pincode, String statusType) throws Exception {
		PincodeResponse response = (PincodeResponse) lmsServiceHelper.getCourierByPincode.apply(pincode);
		Assert.assertEquals(response.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertNotNull(response.getPincodes().get(0).getCourierCode());
		}
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "uploadBulkOrderTracking", description = "ID: C876, Upload Bulk order tracking: Status update to DL, For ML it should fail and for 3pl should get updated")
	public void uploadBulkOrderTrackingDL(String pincode, String courierCode, String warehouse, String shippingMethod,
			String statusType) throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode,
				warehouse, shippingMethod, "cod", false, false));
		Assert.assertEquals(
				lmsServiceHelper.uploadBulkOrderTracking(releaseId, EnumSCM.DL).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		if (statusType.equals(EnumSCM.SUCCESS))
			Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.DELIVERED, 5));
	}

	@Test(groups = { "P0", "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "uploadBulkOrderTracking", description = "ID: C877, Upload Bulk order tracking: Status update to RTO, For ML it should fail and for 3pl should get updated")
	public void uploadBulkOrderTrackingRTO(String pincode, String courierCode, String warehouse, String shippingMethod,
			String statusType) throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.SH, pincode, courierCode,
				warehouse, shippingMethod, "cod", false, false));
		Assert.assertEquals(
				lmsServiceHelper.uploadBulkOrderTracking(releaseId, EnumSCM.RTO).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		if (statusType.equals(EnumSCM.SUCCESS))
			Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.RTO_CONFIRMED, 5));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "Smoke",
			"Regression" }, priority = 0, dataProviderClass = LMSTestsDP.class, dataProvider = "getCorrectionEvents", description = "ID: C880, For admin forward get the possible events")
	public void getCorrectionEvents(String status, String pincode, String courierCode) throws Exception {
		String releaseId = DBUtilities
				.exSelectQueryForSingleRecord(
						"select order_id from order_to_ship where courier_code = '" + courierCode + "' and zipcode = "
								+ pincode + " and shipment_status = '" + status + "' order by last_modified_on DESC",
						"lms")
				.get("order_id").toString();
		String response = lmsServiceHelper.getCorrectionEvents.apply(releaseId, "DL").toString();
		Assert.assertEquals(APIUtilities.getElement(response, "ShipmentUpdateEventsResponse.status.statusType", "json"),
				EnumSCM.SUCCESS);
		Assert.assertEquals(APIUtilities.getElement(response, "ShipmentUpdateEventsResponse.status.statusType", "json"),
				EnumSCM.SUCCESS);
	}
	
// @Test(dataProvider = "courierDP")
//	@Test(groups = { "Smoke",
//			"Regression" }, priority = 3, dataProviderClass = LMSTestsDP.class, dataProvider = "courierDP", description = "Check shipping barcode for release ID")
//	public void verifyShippingBarcodeApi(String courierCode) {
//
//		Assert.assertEquals(lmsServiceHelper.checkShippingBarcodeApi(courierCode, myntraSourceId).getResponseStatus(), 200, "Failed to get valid response code");
//	}

	@Test(groups = { "Smoke", "Regression" }, priority = 3, description = "Check bulk job fetch api")
	public void checkBulkJobFetchApi() throws UnsupportedEncodingException {

		Assert.assertEquals(
				lmsServiceHelper.getBulkJobFetchApiResponse("jobType=COD_RECONCILIATION").getResponseStatus(), 200,
				"Failed to get valid response code");
	}

}