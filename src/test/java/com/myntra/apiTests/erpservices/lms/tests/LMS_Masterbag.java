package com.myntra.apiTests.erpservices.lms.tests;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ExceptionHandler;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.TMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.lms.client.response.OrderResponse;
import com.myntra.lms.client.response.ShipmentResponse;
import com.myntra.lms.client.status.OrderShipmentAssociationStatus;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.PacketEntry;
import com.myntra.returns.common.enums.RefundMode;
import com.myntra.returns.common.enums.ReturnMode;
import com.myntra.returns.response.ReturnResponse;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.tms.masterbag.TMSMasterbagEntry;
import com.myntra.tms.masterbag.TMSMasterbagReponse;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Created by Shubham Gupta on 12/16/16.
 */
public class LMS_Masterbag extends BaseTest {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(LMS_Masterbag.class);
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	private TMSServiceHelper tmsServiceHelper = new TMSServiceHelper();
	private ProcessRelease processRelease = new ProcessRelease();

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardDL", description = "ID: C305 , MasterBagForwardDL", enabled = true)
	public void MasterBagForwardDL(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) {
		long masterBagId = 0;
		try {
			masterBagId = lmsServiceHelper
					.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
					.getId();
		
		Assert.assertTrue(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType).equals( statusType),
				"Unable to add order to MB");
		}catch (SkipException e) {
			
			log.info(" Exception : "+e);
			Assert.assertTrue(e.toString().contains(statusType.toString()));
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardTryAndBuy", description = "ID: C306 , MasterBagForwardTryAndBuy", enabled = true)
	public void MasterBagForwardTryAndBuy(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) {
		long masterBagId = 0;
		try {
			masterBagId = lmsServiceHelper
					.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
					.getId();
			
			Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType), statusType,
					
					"Unable to add order to MB");
		}catch (SkipException e) {
			
			log.info(" Exception : "+e);
			Assert.assertTrue(e.toString().contains(statusType.toString()));		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardExpress", description = "ID: C307 , MasterBagForwardExpress", enabled = true)
	public void MasterBagForwardExpress(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) {
		long masterBagId = 0;
		try {
			masterBagId = lmsServiceHelper
					.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
					.getId();
		
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType), statusType,
				"Unable to add order to MB");
		}catch (SkipException e) {
			
			log.info(" Exception : "+e);
			Assert.assertTrue(e.toString().contains(statusType.toString()));		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardSDD", description = "ID: C308 , MasterBagForwardSDD", enabled = true)
	public void MasterBagForwardSDD(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)  {
		long masterBagId = 0;
		try {
			masterBagId = lmsServiceHelper
					.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
					.getId();
		
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType),
				statusType, "Unable to add order to MB");
		} catch (SkipException e) {
			
			log.info(" Exception : "+e);
			Assert.assertTrue(e.toString().contains(statusType.toString()));		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForward3PL", description = "ID: C309 , MasterBagForward3PL", enabled = true)
	public void MasterBagForward3PL(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)  {
		long masterBagId = 0;
		try {
			masterBagId = lmsServiceHelper
					.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
					.getId();
		
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType), statusType,
				"Unable to add order to MB");
		} catch (SkipException e) {
			
			log.info(" Exception : "+e);
			Assert.assertTrue(e.toString().contains(statusType.toString()));		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardDiffPincode", description = "ID: C310 , MasterBagForwardDiffPincode warning false", enabled = true)
	public void MasterBagForwardDiffPincode(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, false),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardWithWarningTrueError", description = "ID: C324 , MasterBagForwardDL", enabled = true)
	public void MasterBagForwardWithWarningTrueError(String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, true),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardWithWarningTrue", description = "ID: C311 , MasterBagForwardDiffPincodeWithWarningTrue warning false", enabled = true)
	public void MasterBagForwardWithWarningTrue(String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, true),
				statusType, "Unable to add order to MB");
		Map<String, Object> shipmentOrderMap = DBUtilities
				.exSelectQueryForSingleRecord("select * from shipment_order_map where order_id = " + orderId, "lms");
		Assert.assertNotNull(shipmentOrderMap.size(), "order association not created");
		Assert.assertEquals(
				lmsServiceHelper.removeShipmentFromMasterBag(orderId, "" + masterBagId, shipmentType, true),
				EnumSCM.SUCCESS, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardAddItemToMultiBag", description = "ID: C312 , MasterBagForwardDiffPincodeWithWarningTrue warning false", enabled = true)
	public void MasterBagForwardAddItemToMultiBag(String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, true),
				statusType, "Unable to add order to MB");
		Map<String, Object> shipmentOrderMap = DBUtilities.exSelectQueryForSingleRecord(
				"select * from shipment_order_map where order_id = " + orderId + " and shipment_id = " + masterBagId,
				"lms");
		Assert.assertNotNull(shipmentOrderMap.size(), "order association not created");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardAddItemTosameBag", description = "ID: C313 , MasterBagForwardAddItemTosameBag", enabled = true)
	public void MasterBagForwardAddItemTosameBag(String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, true),
				statusType, "Unable to add order to MB");
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, true),
				EnumSCM.ERROR, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardMBinDiffState", description = "ID: C314 , MasterBagForwardDiffPincode", enabled = true)
	public void MasterBagForwardMBinDiffState(String status, String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		DBUtilities.exUpdateQuery("update shipment set status = '" + status + "' where id = " + masterBagId, "lms");
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType, false),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardWithDiffOrderStatus", description = "ID: C315 , MasterBagForwardWithDiffOrderStatus", enabled = true)
	public void MasterBagForwardWithDiffOrderStatus(String toStatus, long masterBagId, String shipmentStatus,
			long source, String sourceType, long dest, String destType, String courierCode, ShipmentType shipmentType,
			String shippingMethod, String statusType) throws Exception {
		String orderId = lmsHelper.createMockOrder(toStatus, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		// Map<String, Object> orderToShip =
		// DBUtilities.exSelectQueryForSingleRecord("select order_id from order_to_ship
		// where shipment_status = '"+shipmentStatus+"' and warehouse_id = "+source+"
		// and delivery_center_id = " +
		// ""+dest+" and courier_code = '"+courierCode+"' and shipment_type =
		// '"+shipmentType+"' and shipping_method = '"+shippingMethod+"' and first_name
		// in('lmsmock','shubham') order by last_modified_on DESC","lms");
		String releaseId = omsServiceHelper.getPacketId(orderId);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId, "" + masterBagId, shipmentType, false),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardRHD", description = "ID: C316 , MasterBagForwardRHD", enabled = true)
	public void MasterBagForwardRHD(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardRHDWithNormalOrder", description = "ID: C318 , MasterBagForwardRHD", enabled = true)
	public void MasterBagForwardRHDWithNormalOrder(long masterBagId, long source, long dest, String pincode,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		// if (!courierCode.equals("ML")){
		// Map<String, Object> orderToShip =
		// DBUtilities.exSelectQueryForSingleRecord("select order_id from order_to_ship
		// where shipment_status = 'INSCANNED' and warehouse_id = "+source+" and " +
		// "courier_code = '"+courierCode+"' and shipment_type = '"+shipmentType+"' and
		// shipping_method = '"+shippingMethod+"'","lms");
		// Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag((long)orderToShip.get("order_id"),
		// ""+masterBagId, shipmentType),statusType,"Status mismatch");
		// }else {
		// Map<String, Object> orderToShip =
		// DBUtilities.exSelectQueryForSingleRecord("select order_id from order_to_ship
		// where shipment_status = 'INSCANNED' and warehouse_id = "+source+" and
		// delivery_center_id = " +
		// ""+dest+" and courier_code = '"+courierCode+"' and shipment_type =
		// '"+shipmentType+"' and shipping_method = '"+shippingMethod+"'","lms");
		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, pincode, courierCode, "" + source, EnumSCM.NORMAL, "cod",
				false, true);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType),
				statusType, "Status mismatch");
		// }

	}

	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardWithCourierMismatch", description = "ID: C353 , MasterBagForwardWithCourierMismatch", enabled = true)
	public void MasterBagForwardWithCourierMismatch(String pincode, String fromCourier, String toCourier,
			String statusType) throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, pincode, fromCourier, "36", EnumSCM.NORMAL, "cod", false,
				true);
		Map<String, Object> orderToShip = DBUtilities.exSelectQueryForSingleRecord(
				"select * from order_to_ship where order_id = " + omsServiceHelper.getPacketId(orderId), "lms");
		long dest = 0L;
		if (toCourier.equalsIgnoreCase("EK")) {
			Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord(
					"select id from delivery_center where code = 'EKART' and name = 'EKART' and courier_code = 'EK'",
					"lms");
			dest = (long) deliveryCenter.get("id");
		} else if (toCourier.equalsIgnoreCase("ML")) {
			dest = 5L;
		} else if (toCourier.equals("IP")) {
			Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord(
					"select id from delivery_center where  code = 'IPOST' and name = 'India Post' and type = 'OTHER_LOGISTICS' and courier_code = 'IP'",
					"lms");
			dest = (long) deliveryCenter.get("id");
		} else if (toCourier.equals("DE") && orderToShip.get("zipcode").equals(LMS_PINCODE.MUMBAI_DE_RHD)) {
			dest = (long) orderToShip.get("delivery_center_id");
		} else if (toCourier.equals("DE")) {
			Map<String, Object> deliveryCenter = DBUtilities.exSelectQueryForSingleRecord(
					"select * from delivery_center where code = 'DE' and name = 'Delhivery (DE)' and type = 'OTHER_LOGISTICS' and courier_code = 'DE'",
					"lms");
			dest = (long) deliveryCenter.get("id");
		}
		long masterBagId = lmsServiceHelper.createMasterBag(36, "WH", dest, "DC", EnumSCM.NORMAL, toCourier)
				.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + omsServiceHelper.getPacketId(orderId),
				"" + masterBagId, ShipmentType.DL), statusType);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardLastMilePartner", description = "ID: C319 , MasterBagForwardLastMilePartner", enabled = true)
	public void MasterBagForwardLastMilePartner(String orderId, long source, String sourceType, long dest,
			String destType, String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType)
			throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagForwardDCtoDC", description = "ID: C320 , MasterBagForwardDCtoDC", enabled = true)
	public void MasterBagForwardDCtoDC(String orderId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(orderId, "" + masterBagId, shipmentType), statusType,
				"Unable to add order to MB");
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertEquals(
					lmsServiceHelper.removeShipmentFromMasterBag(orderId, "" + masterBagId, shipmentType, true),
					EnumSCM.SUCCESS, "Unable to add order to MB");
		}
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagReturnDCtoDC", description = "ID: C321 , MasterBagForwardWithDiffOrderStatus", enabled = true)
	public void MasterBagReturnDCtoDC(String shipmentStatus, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		Map<String, Object> pickup = DBUtilities.exSelectQueryForSingleRecord(
				"select source_return_id from return_shipment where shipment_status = '" + shipmentStatus
						+ "' and delivery_center_id = " + "" + source + " and courier_code = '" + courierCode + "'",
				"lms");
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(pickup.get("source_return_id").toString(),
				"" + masterBagId, shipmentType, false), statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagReverseDCtoWH", description = "ID: C321 , MasterBagReverse", enabled = true)
	public void MasterBagReverseDCtoWH(String shipmentId, long source, String sourceType, long dest, String destType,
			String courierCode, ShipmentType shipmentType, String shippingMethod, String statusType) throws Exception {
		long masterBagId = lmsServiceHelper
				.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getEntries().get(0)
				.getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + shipmentId, "" + masterBagId, shipmentType),
				statusType, "Unable to add order to MB");
	}

	@SuppressWarnings("deprecation")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagWHtoWHPU", description = "ID: C352 , MasterBagWHtoWH", enabled = true)
	public void MasterBagWHtoWHPU(String courierCode, ShipmentType shipmentType, String shippingMethod,
			String statusType) throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "28", shippingMethod, "on",
				false, true);
		PacketEntry orderReleaseEntry = omsServiceHelper.getPacketEntry(omsServiceHelper.getPacketId(orderId));
		ReturnResponse returnResponse = rmsServiceHelper.createReturn(
				orderReleaseEntry.getOrderLines().get(0).getId().toString(), 1, ReturnMode.PICKUP, 27L,
				RefundMode.CASHBACK, "1234567890", "Myntra test lms automation", "Bangalore", "KA", LMS_PINCODE.ML_BLR,
				"ML");
		Assert.assertEquals(returnResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Return creation failed");
		Long returnID = returnResponse.getData().get(0).getId();
		//lmsServiceHelper.validatePickupStatusInLMS("" + returnID, EnumSCM.PICKUP_CREATED, 15);
		lmsServiceHelper.processReturnInLMS("" + returnID, EnumSCM.PICKED_UP_SUCCESSFULLY);
		long masterBagId = lmsServiceHelper.createMasterBag(36, "WH", 28, "WH", shippingMethod, courierCode)
				.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + returnID, "" + masterBagId, shipmentType),
				statusType, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C351 , MasterBagWHtoWH", enabled = true)
	public void MasterBagWHtoWHRTO() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.RTO, LMS_PINCODE.ML_BLR, "ML", "28", EnumSCM.NORMAL, "on",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		lmsServiceHelper.transferShipmentBackToWHRTO("" + omsServiceHelper.getPacketId(orderId));
		long masterBagId = lmsServiceHelper.createMasterBag(28, "WH", 36, "WH", EnumSCM.NORMAL, "ML").getEntries()
				.get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS, "Unable to add order to MB");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "MasterBagReverseDCtoWHWithQuery", description = "ID: C350 , MasterBagReverseDCtoWHWithQuery", enabled = false)
	public void MasterBagReverseDCtoDCWithQuery(long masterBagId, String shipmentStatus, ShipmentType shipmentType,
			String statusType) throws Exception {
		Map<String, Object> pickup = DBUtilities
				.exSelectQueryForSingleRecord("select source_return_id from return_shipment where shipment_status = '"
						+ shipmentStatus + "' and delivery_center_id = " + "5 and courier_code = 'ML'", "lms");
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(pickup.get("source_return_id").toString(),
				"" + masterBagId, shipmentType, false), statusType, "Status mismatch");
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "getMasterBag", description = "ID: C325")
	public void getMasterBag(String sourceId, String destId, String statusType) throws Exception {
		Map<String, Object> shipment = DBUtilities
				.exSelectQueryForSingleRecord("select id from shipment where origin_premises_id = " + sourceId
						+ " and dest_premises_id = " + destId + " " + "order by last_modified_on DESC", "lms");
		if (shipment.size() == 0) {
			Assert.fail("Unable to find shipment/MasterBag of the mentioned Type");
		}
		Assert.assertEquals(((ShipmentResponse) lmsServiceHelper.getMasterBag.apply(shipment.get("id").toString()))
				.getStatus().getStatusType().toString(), statusType);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "P0", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "getMasterBag", description = "ID: C740")
	public void getMasterBagViaSearch(String sourceId, String destId, String statusType) throws Exception {
		Map<String, Object> shipment = DBUtilities
				.exSelectQueryForSingleRecord("select id from shipment where origin_premises_id = " + sourceId
						+ " and dest_premises_id = " + destId + " " + "order by last_modified_on DESC", "lms");
		if (shipment.size() == 0) {
			Assert.fail("Unable to find shipment/MasterBag of the mentioned Type");
		}
		Assert.assertEquals(
				((ShipmentResponse) lmsServiceHelper.getMasterBagViaSearch.apply(shipment.get("id").toString()))
						.getStatus().getStatusType().toString(),
				statusType);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "getMasterBagWithParam", description = "ID: C326 , ")
	public void getMasterBagWithParam(String pathParam, String statusCode, String statusMessage, String statusType)
			throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JAXBException {
		ShipmentResponse shipmentResponse = lmsServiceHelper.getMasterBagWithParam(pathParam);
		Assert.assertEquals(shipmentResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
		Assert.assertEquals(shipmentResponse.getStatus().getStatusMessage().toString(), statusMessage,
				"Unable to get bag something wronge");
		Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), statusType);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "createMasterBag", description = "ID: C327 , ")
	public void createMasterBag(String originPremisesId, String originPremisesType, String originCity,
			String destinationPremisesId, String destinationPremisesType, String destinationCity, String shippingMethod,
			String statusCode, String statusMessage, String statusType)
			throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JAXBException {
		ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(
				(long) lmsHelper.getDHHubIdForWH.apply(originPremisesId), originPremisesType, originCity,
				Long.parseLong(destinationPremisesId), destinationPremisesType, destinationCity, shippingMethod);
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertEquals(shipmentResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(shipmentResponse.getStatus().getStatusMessage().toString(), statusMessage);
			Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(shipmentResponse.getStatus().getTotalCount() > 0);
			log.info("Shipment ID: " + shipmentResponse.getEntries().get(0).getId());
		} else {
			Assert.assertEquals(shipmentResponse.getStatus().getStatusCode(), Integer.parseInt(statusCode));
			Assert.assertEquals(shipmentResponse.getStatus().getStatusMessage().toString(), statusMessage,
					"Unable to create Master Bag");
			Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), statusType);
		}
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "updateMasterBag", description = "ID: C328 , updateMasterBag", enabled = true)
	public void updateMasterBag(String originPremisesId, String originPremisesType, String originCity,
			String destinationPremisesId, String destinationPremisesType, String destinationCity, String shippingMethod,
			String status, String statusCode, String statusMessage, String statusType)
			throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JAXBException {
		long shipment = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL).getEntries().get(0).getId();
		ShipmentResponse shipmentResponse = lmsServiceHelper.updateMasterBag("" + shipment,
				Long.parseLong(originPremisesId), originPremisesType, originCity, Long.parseLong(destinationPremisesId),
				destinationPremisesType, destinationCity, shippingMethod, status);
		if (statusType.equals(EnumSCM.SUCCESS)) {
			Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), statusType);
			Assert.assertTrue(shipmentResponse.getStatus().getTotalCount() > 0);
			Assert.assertEquals(shipmentResponse.getEntries().get(0).getId().toString(), "" + shipment);
		} else {
			Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), statusType);
		}
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C354 , MasterBagAddShipmentOnClose", enabled = true)
	public void MasterBagAddShipmentOnClose() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId1 = omsServiceHelper.getPacketId(orderId1);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId(releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId1, "" + masterBagId, ShipmentType.DL),
				EnumSCM.ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C349 , MasterBagLostOnShip", enabled = true)
	public void MasterBagLostOnShip() throws Exception {

		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String releaseId1 = omsServiceHelper.getPacketId(orderId1);
		// String masterBagId = lmsHelper.getMasterBagId(releaseId);

		ShipmentResponse createMasterBagRes = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId1, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 5));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId1, EnumSCM.S, 5));
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost("" + masterBagId), EnumSCM.SUCCESS);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.L, 5));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId1, EnumSCM.L, 5));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C345 , MasterBagLostOnNew", enabled = true)
	public void MasterBagLostOnNew() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost(masterBagId), EnumSCM.SUCCESS);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.L, 5));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C346 , MasterBagLostOnClosed", enabled = true)
	public void MasterBagLostOnClosed() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost(masterBagId), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInLMS("" + releaseId, EnumSCM.LOST_IN_HUB, 3));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.L, 5));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C347 , MasterBagLostOnClosed3PL", enabled = true)
	public void MasterBagLostOnClosed3PL() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost("" + masterBagId), EnumSCM.SUCCESS);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.L, 5));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C348 , MasterBagLostOnSH3PL", enabled = true)
	public void MasterBagLostOnSH3PL() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.SH, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost(masterBagId), EnumSCM.ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C355 , MasterBagLostOnReceivedAtTransportHub", enabled = true)
	public void MasterBagLostOnReceivedAtTransportHub() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		TMSMasterbagEntry masterBag = ((TMSMasterbagReponse) tmsServiceHelper.getTmsMasterBagById.apply(masterBagId))
				.getMasterbagEntries().get(0);
		String sourceTH = (String) tmsServiceHelper.getTHForLH.apply(masterBag.getSourceHub());
		ExceptionHandler.handleError(
				((TMSMasterbagReponse) tmsServiceHelper.tmsReceiveMasterBag.apply(sourceTH, masterBagId)).getStatus(),
				"Unable to receive MasterBag in source TH");
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost(masterBagId), EnumSCM.SUCCESS);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.L, 5));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C356 , MasterBagLostOnReceived", enabled = true)
	public void MasterBagLostOnReceived() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(lmsServiceHelper.markMasterBagLost(masterBagId), EnumSCM.ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C357 , MasterBagDiffServiceability", enabled = true)
	public void MasterBagDiffServiceabilityWithWarningTrue() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		DBUtilities.exUpdateQuery(
				"update order_to_ship set delivery_center_id = 1 where order_id = '" + releaseId + "'", "lms");
		DBUtilities.exUpdateQuery(
				"update ml_shipment set delivery_center_id = 1 where source_reference_id = '" + releaseId + "'", "lms");
		Assert.assertEquals(lmsServiceHelper.orderInScanNew("" + releaseId), EnumSCM.SUCCESS, "Unable to inscan order");
		ShipmentResponse createMasterBagRes = lmsServiceHelper.createMasterBag(1L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL, true),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 10));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C358 , MasterBagDiffServiceability", enabled = true)
	public void MasterBagDiffServiceabilityWithWarningFalse() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		DBUtilities.exUpdateQuery(
				"update order_to_ship set delivery_center_id = 1 where order_id = '" + releaseId + "'", "lms");
		DBUtilities.exUpdateQuery(
				"update ml_shipment set delivery_center_id = 1 where source_reference_id = '" + releaseId + "'", "lms");
		Assert.assertEquals(lmsServiceHelper.orderInScanNew("" + releaseId), EnumSCM.SUCCESS, "Unable to inscan order");
		ShipmentResponse createMasterBagRes = lmsServiceHelper.createMasterBag(1L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL, false, "dummy"),
				939);
	}

	@Deprecated
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "saveMasterBag", description = "ID: C330 , saveMasterBag", enabled = false)
	public void saveMasterBag(String masterBagId, String orderId, String statusCode, String statusMessage,
			String statusType)
			throws JsonGenerationException, JsonMappingException, IOException, NumberFormatException, JAXBException {
		// ShipmentResponse response =
		// lmsServiceHelper.saveMasterBagDL(Long.parseLong(shipmentId),
		// Long.parseLong(orderId));
		String response = lmsServiceHelper.addAndSaveMasterBag(orderId, masterBagId, ShipmentType.DL);
		Assert.assertEquals(response, statusType);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "closeAndShipMasterBag", description = "ID: C331 , closeAndShipMasterBag", enabled = true)
	public void closeAndShipMasterBag(String courierCode, String pincode, String warehouseId, String shippingMethod,
			boolean isTod) throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, pincode, courierCode, warehouseId, shippingMethod, "cod", isTod, true);
		//String packetId = omsServiceHelper.getPacketId(orderId);
		String releaseId = omsServiceHelper.getReleaseId(orderId);
		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.SH).force(true).build());
		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C332 , closeAndShipMasterBagWithOutTransporterHubScan", enabled = false)
	public void closeAndShipMasterBagWithOutTransporterHubScan() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String masterBagId = lmsHelper.getMasterBagId("" + omsServiceHelper.getPacketId(orderId));
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.shipMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C333 , closeAndShipMasterBag3PLWithOutTransporterHubScan", enabled = true)
	public void closeAndShipMasterBag3PLWithOutTransporterHubScan() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.PUNE_EK, "EK", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String masterBagId = lmsHelper.getMasterBagId(omsServiceHelper.getPacketId(orderId));
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.shipMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "reopenMaterBagAndShip", description = "ID: C334 , reopenMaterBagAndShip", enabled = true)
	public void reopenMaterBagAndShip(String pincode, String courierCode) throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, pincode, courierCode, "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId(releaseId);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.removeShipmentFromMasterBag("" + releaseId, masterBagId, ShipmentType.DL, true),
				EnumSCM.ERROR);
		Assert.assertEquals(
				lmsServiceHelper.reopenMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsHelper.getMasterBagStatus(Long.parseLong(masterBagId)), EnumSCM.NEW,
				"masterbag status is not updated in DB to `CLOSED`");
		Assert.assertEquals(
				lmsServiceHelper.removeShipmentFromMasterBag("" + releaseId, masterBagId, ShipmentType.DL, true),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, masterBagId, ShipmentType.DL, true),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C359 , This operation has been blocked after TMS so for now its not considered", enabled = false)
	public void reopenMaterBagAfterReceiveAtTransportHub() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		Assert.assertEquals(lmsServiceHelper.scanMasterBagInTransportHub(masterBagId), EnumSCM.SUCCESS,
				"Unable to receive in Transport Hub");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.RECEIVED_AT_TRANSPORTER_HUB,
				"MasterBag DB status is not updated to `RECEIVED_AT_TRANSPORTER_HUB`");
		Assert.assertEquals(lmsServiceHelper.reopenMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.NEW,
				"masterbag status is not updated in DB to `CLOSED`");
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		Assert.assertEquals(lmsServiceHelper.scanMasterBagInTransportHub(masterBagId), EnumSCM.SUCCESS,
				"Unable to receive in Transport Hub");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.RECEIVED_AT_TRANSPORTER_HUB,
				"MasterBag DB status is not updated to `RECEIVED_AT_TRANSPORTER_HUB`");
		Assert.assertEquals(lmsServiceHelper.shipMasterBag(masterBagId, lmsHelper.getTransporter(36, 5)).getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS, "Unable to ship masterBag");
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "reopenMaterOnDiffStatus", description = "ID: C360 , reopenMaterOnDiffStatus: On SH and on receieved Error Scenarios", enabled = true)
	public void reopenMaterOnDiffStatus(String status) throws Exception {
		String orderId = lmsHelper.createMockOrder(status, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false,
				true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(
				lmsServiceHelper.reopenMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C335 , AddedToMB_RemoveAndCancelShipment and again try to add", enabled = true)
	public void AddedToMB_RemoveAndCancelShipment() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String masterBagId = lmsHelper.getMasterBagId("" + releaseId);
		Assert.assertEquals(
				lmsServiceHelper.closeMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.reopenMasterBag(Long.parseLong(masterBagId)).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.removeShipmentFromMasterBag("" + releaseId, masterBagId, ShipmentType.DL, true),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.cancelShipmentInLMS("" + releaseId), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, masterBagId, ShipmentType.DL, true),
				EnumSCM.ERROR);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C336 , RHDHandoverToCourierPartnerWithoutReciving", enabled = true)
	public void RHDHandoverToCourierPartnerWithoutReciving() throws Exception {

		String orderId = lmsHelper.createMockOrder(EnumSCM.SH, LMS_PINCODE.MUMBAI_DE_RHD, "DE", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(
				lmsServiceHelper.handoverToRegionalCourier(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
		long dcId = Long.parseLong(lmsHelper.getDCId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScan(masterBagId, EnumSCM.RECEIVED_AT_HANDOVER_CENTER, "bangalore", dcId, "DC").getStatus()
				.getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId, "" + releaseId, "DC-Delhi", dcId, "DC", 36,
						ShipmentStatus.RECEIVED_AT_HANDOVER_CENTER,
						OrderShipmentAssociationStatus.RECEIVED_AT_HANDOVER_CENTER)
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.handoverToRegionalCourier(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C337 , Scan 2 order in a shipment and ship, scan 2nd order in other masterbag and ship, Then in 1st masterbag: recieve 1st shipment and  "
					+ "mark 'shortage' for 2nd shipment. Receive 2nd masterbag", enabled = true)
	public void masterBagInScanAndUpdateWithReceiveShortage() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = "" + omsServiceHelper.getPacketId(orderId);
		String releaseId1 = "" + omsServiceHelper.getPacketId(orderId1);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId(releaseId));
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId1, "36"), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId1, "" + masterBagId, ShipmentType.DL, false),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 3));
		assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 8),
				"Checking the OrderStatus moved to SH in OMS");
		// -----Add releaseId1 to another bag -------------
		ShipmentResponse shipment2 = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(shipment2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId1 = (long) shipment2.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId1, "" + masterBagId1, ShipmentType.DL, true),
				EnumSCM.SUCCESS, "Unable to save masterBag");
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId1), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId1);
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId1), EnumSCM.IN_TRANSIT,
				"MasterBag DB status is not updated to `IN_TRANSIT`");
		lmsServiceHelper.validateOrderStatusInLMS(releaseId1, EnumSCM.SHIPPED, 3);
		// ---------------- Inscan Materbag ------------------
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper
						.masterBagInScanUpdate(masterBagId,
								new String[] { releaseId + ":RECEIVED", releaseId1 + ":SHORTAGE" }, 5, "DC")
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId1, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId1, releaseId1, "Bangalore", 5, "DC", 36)
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C338 , Scan 2 order in a shipment and ship, scan 2nd order in other masterbag and ship, Recieve 1st mastebag and then try to recieve 2nd masterbag. "
					+ "In second masterbag update: It should throw error", enabled = true)
	public void masterBagInScanAndUpdateWithReceiveShortage1() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.PK, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = "" + omsServiceHelper.getPacketId(orderId);
		String releaseId1 = "" + omsServiceHelper.getPacketId(orderId1);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId(releaseId));
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId1, "36"), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId1, "" + masterBagId, ShipmentType.DL, false),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.IN_TRANSIT,
				"MasterBag DB status is not updated to `IN_TRANSIT`");
		assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 3));
		assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 8),
				"Checking the OrderStatus moved to SH in OMS");
		// -----Add releaseId1 to another bag -------------
		ShipmentResponse shipment2 = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(shipment2.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId1 = (long) shipment2.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag(releaseId1, "" + masterBagId1, ShipmentType.DL, true),
				EnumSCM.SUCCESS, "Unable to save masterBag");
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId1), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId1);
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId1), EnumSCM.IN_TRANSIT,
				"MasterBag DB status is not updated to `IN_TRANSIT`");
		lmsServiceHelper.validateOrderStatusInLMS(releaseId1, EnumSCM.SHIPPED, 3);
		// ---------------- Inscan Materbag ------------------
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper
						.masterBagInScanUpdate(masterBagId,
								new String[] { releaseId + ":RECEIVED", releaseId1 + ":RECEIVED" }, 5, "DC")
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId1, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId1, releaseId1, "Bangalore", 5, "DC", 36)
				.getStatus().getStatusType().toString(), EnumSCM.ERROR);
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId1, new String[] { releaseId1 + ":SHORTAGE" }, 5, "DC")
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C361 , masterBagReciveDamaged", enabled = true)
	public void masterBagReceiveDamaged() throws Exception {

		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);

		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId, new String[] { releaseId + ":RECEIVED_DAMAGED" }, 5, "DC")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C340 , masterBagReciveInWrongDC", enabled = true)
	public void masterBagReciveInWrongDC() throws Exception {

		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);

		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 1, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 5, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C341 , masterBagReciveAnd_DCToDCMovement, check source and warehouse mismatch and finally with both correct", enabled = false)
	public void masterBagReciveAnd_DCToDCMovement() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.SH, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 5, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		// Try add order to source mismatch
		long masterBagId1 = lmsServiceHelper.createMasterBag(1, "DC", 5, "DC", EnumSCM.NORMAL, "ML").getEntries().get(0)
				.getId();
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId1, ShipmentType.DL, true),
				EnumSCM.ERROR);

		long masterBagId2 = lmsServiceHelper.createMasterBag(5, "DC", 1, "DC", EnumSCM.NORMAL, "ML").getEntries().get(0)
				.getId();
		Assert.assertEquals(
				lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId2, ShipmentType.DL, true),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId2).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsServiceHelper.shipMasterBag(masterBagId2).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to ship masterBag");
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId2, 10).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId2).getStatus().getStatusType().toString(),
				EnumSCM.ERROR, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId2, "" + releaseId, "Bangalore", 10, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId2, 1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId2, "" + releaseId, "Bangalore", 1, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "masterBagReceiveWithExpressNTOD", description = "ID: C342 , masterBagReceiveWithExpressNTOD and paymentMode = CC", enabled = true)
	public void masterBagReceiveWithExpressNTOD(String shippingMethod, boolean istod) throws Exception {

		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", shippingMethod,
				"CC", istod, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 5, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C343 , masterBagReciveInWrongStatus", enabled = true)
	public void masterBagReciveInWrongStatus() throws Exception {
		Map<String, Object> order = DBUtilities.exSelectQueryForSingleRecord(
				"select order_id from order_to_ship where shipment_status = 'DELIVERED' and warehouse_id = 36 and delivery_center_id = 5 order by last_modified_on DESC",
				"lms");
		String releaseId = order.get("order_id").toString();
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.ERROR, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 5, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
	}

	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "masterBagReciveClosedMB", description = "ID: C344 , masterBagReciveClosedMB its a known bug so disabling it for now", enabled = false)
	public void masterBagReciveClosedMB(String mbStatus) throws Exception {
		Map<String, Object> shipment = DBUtilities.exSelectQueryForSingleRecord(
				"select sh.id, sm.order_id from shipment sh, shipment_order_map sm where sh.id = sm.shipment_id and sh.status = '"
						+ mbStatus + "' and "
						+ "sh.order_count>0 and sh.origin_premises_id = 36 and sh.dest_premises_id = 5 limit 1",
				"lms");
		long masterBagId = (long) shipment.get("id");
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.ERROR);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.ERROR, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId, shipment.get("order_id").toString(), "Bangalore", 5, "DC", 36)
				.getStatus().getStatusType().toString(), EnumSCM.ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, dataProviderClass = LMSTestsDP.class, dataProvider = "scanOrderInMasterBag", description = "ID: C , scanOrderInMasterBag check can scan in master bag", enabled = false)
	public void scanOrderInMasterBag(String createOrder, String shipmentId, String orderId, String statusCode,
			String statusMessage, String statusType) throws Exception {
		LMSHelper lmsHelper = new LMSHelper();
		String masterBagId = "";
		String masterBag1 = "", masterBag2 = "", masterBag3 = "";
		if (createOrder.equals("1")) {
			lmsHelper.createOrderWithSingleSku("70023011", "false");
			lmsServiceHelper.validateOrderStatusInLMS("70023011", EnumSCM.PACKED, 15);
			Assert.assertEquals(lmsServiceHelper.orderInScanNew(orderId, "1"), EnumSCM.SUCCESS);
			ShipmentResponse shipmentResponse1 = lmsServiceHelper.createMasterBag(1L, "WH", "Bangalore", 5L, "DC",
					"Bangalore", EnumSCM.NORMAL);
			masterBag1 = shipmentResponse1.getEntries().get(0).getId().toString();
			log.info("Wait for 2 sec .......");
			Thread.sleep(20000);
		} else if (createOrder.equals("2")) {
			ShipmentResponse shipmentResponse2 = lmsServiceHelper.createMasterBag(
					(long) lmsHelper.getDHHubIdForWH.apply(1), "WH", "Bangalore", 5L, "DC", "Bangalore",
					EnumSCM.EXPRESS);
			masterBag2 = shipmentResponse2.getEntries().get(0).getId().toString();
			log.info("Wait for 2 sec .......");
			Thread.sleep(20000);
		} else if (createOrder.equals("3")) {
			ShipmentResponse shipmentResponse3 = lmsServiceHelper.createMasterBag(
					(long) lmsHelper.getDHHubIdForWH.apply(1), "WH", "Bangalore", 11L, "DC", "Pune", EnumSCM.NORMAL);
			masterBag3 = shipmentResponse3.getEntries().get(0).getId().toString();
			log.info("Wait for 2 sec .......");
			Thread.sleep(20000);
		}
		if (shipmentId.equals(EnumSCM.NORMAL)) {
			masterBagId = masterBag1;
		} else if (shipmentId.equals(EnumSCM.EXPRESS)) {
			masterBagId = masterBag2;
		} else if (shipmentId.equals("DIFF")) {
			masterBagId = masterBag3;
		} else {
			masterBagId = shipmentId;
		}
		OrderResponse orderResponse = lmsServiceHelper.scanOrderInMasterBag(orderId, masterBagId);
		// Assert.assertEquals(orderResponse.getStatus().getStatusCode(),
		// Integer.parseInt(statusCode));
		Assert.assertEquals(orderResponse.getStatus().getStatusMessage().toString(), statusMessage,
				"Unable to scan order In Bag, something went wronge");
		Assert.assertEquals(orderResponse.getStatus().getStatusType().toString(), statusType,
				"unable to scanOrderInMasterBag");
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C362 , MasterBagWithMultiShipments", enabled = true)
	public void MasterBagWithMultiShipments() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId2 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId3 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				true, true);
		String orderId4 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "ON",
				false, true);
		String releaseId = omsServiceHelper.getPacketId(orderId);
		String releaseId1 = omsServiceHelper.getPacketId(orderId1);
		String releaseId2 = omsServiceHelper.getPacketId(orderId2);
		String releaseId3 = omsServiceHelper.getPacketId(orderId3);
		String releaseId4 = omsServiceHelper.getPacketId(orderId4);
		ShipmentResponse createMasterBagRes = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId1, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId2, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId3, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId4, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 5));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId1, EnumSCM.S, 1));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId2, EnumSCM.S, 1));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId3, EnumSCM.S, 1));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId4, EnumSCM.S, 1));
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId,
						new String[] { releaseId + ":RECEIVED", releaseId1 + ":RECEIVED", releaseId2 + ":RECEIVED",
								releaseId3 + ":RECEIVED", releaseId4 + ":RECEIVED" },
						5, "DC")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId, EnumSCM.UNASSIGNED, 3),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId1, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId2, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId3, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId4, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C363 , MasterBagWithMultiShipments", enabled = true)
	public void MasterBagWithMultiWarehouseButOneHub() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "1", EnumSCM.NORMAL, "cod",
				false, true);
		String orderId2 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "19", EnumSCM.NORMAL, "cod",
				false, true);

		String releaseId = omsServiceHelper.getPacketId(orderId);
		String releaseId1 = omsServiceHelper.getPacketId(orderId1);
		String releaseId2 = omsServiceHelper.getPacketId(orderId2);

		ShipmentResponse createMasterBagRes = lmsServiceHelper.createMasterBag(5L, "36", ShippingMethod.NORMAL);
		Assert.assertEquals(createMasterBagRes.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"Unable to create MaterBag");
		long masterBagId = (long) createMasterBagRes.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId1, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId2, "" + masterBagId, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 5));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId1, EnumSCM.S, 1));
		Assert.assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId2, EnumSCM.S, 1));

		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper.masterBagInScanUpdate(masterBagId,
				new String[] { releaseId + ":RECEIVED", releaseId1 + ":RECEIVED", releaseId2 + ":RECEIVED" }, 5, "DC")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId, EnumSCM.UNASSIGNED, 3),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId1, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML("" + releaseId2, EnumSCM.UNASSIGNED, 1),
				"Status is not Unassigned in ML");
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C364 , MasterBagReverseWithMultiWarehouseButOneHub", enabled = true) // This
	public void MasterBagReverseWithMultiWarehouseButOneHub() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, false);
		String releaseId = "" + omsServiceHelper.getPacketId(orderId);
		String returnId = lmsServiceHelper.createReturnAndPickupSuccessFul.apply(releaseId, LMS_PINCODE.ML_BLR, 5)
				.toString();
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.DL, LMS_PINCODE.ML_BLR, "ML", "1", EnumSCM.NORMAL, "cod",
				false, false);
		String releaseId1 = "" + omsServiceHelper.getPacketId(orderId1);
		long returnId1 = Long.parseLong(
				lmsServiceHelper.createReturnAndPickupSuccessFul.apply(releaseId1, LMS_PINCODE.ML_BLR, 5).toString());
		ShipmentResponse shipmentResponse = lmsServiceHelper.createMasterBag(5, "DC",
				lmsServiceHelper.getReturnAddress("560068", "ML", 36L).getId(), "WH", EnumSCM.NORMAL, "ML");
		Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS,
				"MasterBag creation failed");
		long masterBagId = shipmentResponse.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + returnId, "" + masterBagId, ShipmentType.PU),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + returnId1, "" + masterBagId, ShipmentType.PU),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
	
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, EnumSCM.RECEIVED, "Bangalore", 36, "WH")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to inscan masterBag at WH");
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId,
						new String[] { omsServiceHelper.getPacketId(orderId) + ":RECEIVED",
								omsServiceHelper.getPacketId(orderId) + ":RECEIVED" },
						36, "WH")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C365 , After receiving masterbag in a DC then transfer that Masterbag from one DC to another using the separate api, Try -ve scenarios as well.", enabled = true)
	public void DCtoDCTransfer() throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB,
				LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId("" + releaseId));
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close the MasterBag");
		Assert.assertEquals(((TMSMasterbagReponse) tmsServiceHelper.tmsReceiveMasterBag.apply("TH-BLR", masterBagId))
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS, "Unable to receive masterBag in TMS HUB");
		tmsServiceHelper.intracityTransferFw.accept(masterBagId);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(
				lmsServiceHelper.masterBagInScanUpdate(masterBagId, "" + releaseId, "Bangalore", 5, "DC", 36)
						.getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		long masterBagId1 = lmsServiceHelper.createMasterBag(5, "DC", "Bangalore", 1, "DC", "Bangalore", "NORMAL")
				.getEntries().get(0).getId();
		Assert.assertEquals(lmsServiceHelper.addAndSaveMasterBag("" + releaseId, "" + masterBagId1, ShipmentType.DL),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.dcToDcTransferMB.apply(masterBagId1), EnumSCM.ERROR);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId1).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.dcToDcTransferMB.apply(masterBagId1), EnumSCM.SUCCESS,
				"Unable to transfer masterBag DC to DC");
		Assert.assertEquals(lmsServiceHelper.dcToDcTransferMB.apply(masterBagId1), EnumSCM.ERROR);
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C878, Receive Excess shipment in a masterbag", enabled = true)
	public void masterBagReceiveExcessShipment() throws Exception {
		String orderId = lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL,
				"cod", false, true);
		String orderId1 = lmsHelper.createMockOrder(EnumSCM.IS, LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod",
				false, true);
		String releaseId = "" + omsServiceHelper.getPacketId(orderId);
		String releaseId1 = "" + omsServiceHelper.getPacketId(orderId1);
		long masterBagId = Long.parseLong(lmsHelper.getMasterBagId(releaseId));
		Assert.assertEquals(lmsServiceHelper.orderInScanNew(releaseId1, "36"), EnumSCM.SUCCESS);
		Assert.assertEquals(lmsServiceHelper.closeMasterBag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to close masterBag");
		Assert.assertEquals(lmsHelper.getMasterBagStatus(masterBagId), EnumSCM.CLOSED,
				"masterbag status is not updated in DB to `CLOSED`");
		tmsServiceHelper.processInTMSFromClosedShipped.accept(masterBagId);
		assertTrue(lmsServiceHelper.validateOrderStatusInLMS(releaseId, EnumSCM.SHIPPED, 3));
		assertTrue(omsServiceHelper.validatePacketStatusInOMS(releaseId, EnumSCM.S, 8),
				"Checking the OrderStatus moved to SH in OMS");
		// ---------------- Inscan Materbag ------------------
		Assert.assertEquals(lmsServiceHelper.masterBagInScan(masterBagId, 5).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS);
		Assert.assertEquals(
				lmsServiceHelper.receiveShipmentFromMasterbag(masterBagId).getStatus().getStatusType().toString(),
				EnumSCM.SUCCESS, "Unable to receive shipment in DC");
		Assert.assertEquals(lmsServiceHelper
				.masterBagInScanUpdate(masterBagId,
						new String[] { releaseId + ":RECEIVED", releaseId1 + ":IN_TRANSIT_WRONG_ROUTE" }, 5, "DC")
				.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(lmsServiceHelper.validateOrderStatusInML(releaseId1, EnumSCM.UNASSIGNED, 2));
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "MasterBag", "Smoke",
			"Regression" }, priority = 4, description = "ID: C879, get Order shipment Association", enabled = true)
	public void getOrderShipmenetAssociation() throws Exception {
		String releaseId = omsServiceHelper.getPacketId(lmsHelper.createMockOrder(EnumSCM.ADDED_TO_MB,
				LMS_PINCODE.ML_BLR, "ML", "36", EnumSCM.NORMAL, "cod", false, true));
		ShipmentResponse shipmentResponse = (ShipmentResponse) lmsServiceHelper.getOrderShipmentAssociations
				.apply(releaseId);
		Assert.assertEquals(shipmentResponse.getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
		Assert.assertTrue(shipmentResponse.getEntries().get(0).getOrderCount() > 0);
		Assert.assertEquals(shipmentResponse.getEntries().get(0).getStatus(), ShipmentStatus.NEW);
	}

}
