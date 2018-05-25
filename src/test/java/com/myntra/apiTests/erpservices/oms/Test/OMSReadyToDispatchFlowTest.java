package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.OMSReadyToDispatchFlowTestDP;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class OMSReadyToDispatchFlowTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(OMSReadyToDispatchFlowTest.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	SoftAssert sft;
	private long sellerId_HEAL = 21;
	private String supplyTypeOnHand = "ON_HAND";
	private long defaultWaitTime = 5000;
	private Long pincode_560068 = Long.parseLong(OMSTCConstants.Pincodes.PINCODE_560068);
	String login = OMSTCConstants.MarkReadyToDispatch.Login;
	String password = OMSTCConstants.MarkReadyToDispatch.Password;
	private String skuId1 = OMSTCConstants.OtherSkus.skuId_3831;
	private String skuId2 = OMSTCConstants.OtherSkus.skuId_3832;
	private int warehouse28 = OMSTCConstants.WareHouseIds.warehouseId28_GN;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderID;
	private String firstRelease;
	private String secondRelease;

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, description = "TC001:By passing release Which is having only one Item with one quantity. Supply Type On Hand and Courier Code is ML.")
	public void markReadyToDispatchSingleLineOrder() throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 1) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" }, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		markOrderPK(orderID, "ML", "ML000001");
		sft.assertAll();

	}

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, description = "TC002:By passing release Which is having multiItem. Supply Type On Hand and Courier Code is ML.")
	public void markReadyToDispatchMultiLineOrder() throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 2) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" }, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		markOrderPK(orderID, "ML", "ML000001");
		omsServiceHelper.checkReleaseStatusForOrder(orderID, EnumSCM.PK);
		sft.assertAll();

	}

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, description = "TC003:Mark order packed where partial order is cancelled. Supply Type On Hand and Courier Code is ML.")
	public void markReadyToDispatchSingleLineOrderPartialCancel() throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 2) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" }, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		firstRelease = orderReleaseEntries.get(0).getId().toString();
		String lineID = omsServiceHelper.getOrderLineEntries(firstRelease).get(0).getId().toString();

		cancelLineSuccess(firstRelease, login, lineID, 1, 41);

		markOrderPK(orderID, "ML", "ML000001");
		sft.assertAll();

	}

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, description = "TC004:Create multiSku orderMark order packed where partial order is cancelled. Supply Type On Hand and Courier Code is ML.")
	public void markReadyToDispatchMultiLineOrderPartialCancel() throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 2), new SkuEntry(skuId2, 2) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1",
						skuId2 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" },
				supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL,
				skuId2 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		firstRelease = getReleaseOfParticularSkuId(orderReleaseEntries, "" + skuId1);
		String lineId = omsServiceHelper.getOrderLineEntries(firstRelease).get(0).getId().toString();
		cancelLineSuccess(firstRelease, login, lineId, 1, 41);

		secondRelease = getReleaseOfParticularSkuId(orderReleaseEntries, "" + skuId2);
		lineId = omsServiceHelper.getOrderLineEntries(firstRelease).get(0).getId().toString();
		cancelLineSuccess(secondRelease, login, lineId, 1, 41);

		markOrderPK(orderID, "ML", "ML000001");
		sft.assertAll();

	}

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, description = "TC005:Mark order packed where partial order is cancelled. Supply Type On Hand and Courier Code is ML.")
	public void markReadyToDispatchSingleLineOrderForCancelRelease() throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 1) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" }, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		firstRelease = orderReleaseEntries.get(0).getId().toString();
		String lineID = omsServiceHelper.getOrderLineEntries(firstRelease).get(0).getId().toString();

		cancelLineSuccess(firstRelease, login, lineID, 1, 41);

		markOrderPKFailure(firstRelease, 1234, Type.SUCCESS.name(), ReadyToDispatchType.NEGATIVE.name());
		sft.assertAll();

	}

	@Test(enabled = true, groups = { "regression", "sanity",
			"readyToDispatch" }, dataProvider = "readyToDispatchNegativeCasesDP", dataProviderClass = OMSReadyToDispatchFlowTestDP.class, description = "TC006:Mark order packed and verify Error for negative cases")
	public void markReadyToDispatchNegative(String skuId, String dispatchType) throws Exception {

		sft = new SoftAssert();
		SkuEntry[] skuEntries = { new SkuEntry(skuId1, 1) };
		atpServiceHelper.updateInventoryDetailsForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL + ":1" }, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(
				new String[] { skuId1 + ":" + warehouse28 + ":100:0:" + sellerId_HEAL }, supplyTypeOnHand);

		orderReleaseEntries = createOrderHelper(login, password, pincode_560068, skuEntries, EnumSCM.WP);

		firstRelease = orderReleaseEntries.get(0).getId().toString();
		String lineID = omsServiceHelper.getOrderLineEntries(firstRelease).get(0).getId().toString();

		cancelLineSuccess(firstRelease, login, lineID, 1, 41);

		markOrderPKFailure(firstRelease, 1234, Type.ERROR.name(), dispatchType);
		sft.assertAll();

	}

	/**
	 * @param releaseId
	 * @param statusCode
	 * @param statusType
	 * @param dispatchType
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	private void markOrderPKFailure(String releaseId, int statusCode, String statusType, String dispatchType)
			throws UnsupportedEncodingException, JAXBException {
		// TODO Auto-generated method stub
		if (dispatchType.equalsIgnoreCase(ReadyToDispatchType.MISSING_COURIER.name())) {
			updateCourierAndTrackingForRelease(releaseId, null, null);
		}

		OrderReleaseResponse orderReleaseResponse = omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(releaseId,
				dispatchType);
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusCode(), statusCode,
				"Status code should be same Actual:" + orderReleaseResponse.getStatus().getStatusCode());
		sft.assertEquals(orderReleaseResponse.getStatus().getStatusType().toString(), statusType,
				"Status Type should be same Actual:" + orderReleaseResponse.getStatus().getStatusType());
	}

	/**
	 * @param orderReleaseEntries
	 * @param skuId
	 * @return
	 */
	public String getReleaseOfParticularSkuId(List<OrderReleaseEntry> orderReleaseEntries, String skuId) {
		String releaseIdOfParticluarSku = null;
		for (OrderReleaseEntry orderReleaseEntry : orderReleaseEntries) {
			if (orderReleaseEntry.getOrderLines().get(0).getSkuId().toString().equalsIgnoreCase(skuId)
					&& !orderReleaseEntry.getStatus().equalsIgnoreCase(EnumSCM.F)) {
				releaseIdOfParticluarSku = orderReleaseEntry.getId().toString();
			}

		}

		return releaseIdOfParticluarSku;
	}

	/**
	 * This is to create order and return release entries
	 * 
	 * @param login
	 * @param password
	 * @param pincode
	 * @param skuEntries
	 * @return
	 * @throws Exception
	 */
	public List<OrderReleaseEntry> createOrderHelper(String login, String password, Long pincode, SkuEntry[] skuEntries,
			String status) throws Exception {
		CreateOrderEntry createOrderEntry = new CreateOrderEntryBuilder(login, password, pincode, skuEntries).build();
		orderID = end2EndHelper.createOrder(createOrderEntry);
		// Check Release Status
		omsServiceHelper.checkReleaseStatusForOrder(orderID, status);
		orderReleaseEntries = omsServiceHelper.getOrderEntry(orderID).getOrderReleases();
		return orderReleaseEntries;
	}

	/**
	 * @param orderReleaseId
	 * @param login
	 * @param lineId
	 * @param qty
	 * @param reasonId
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 */
	public void cancelLineSuccess(String orderReleaseId, String login, String lineId, int qty, int reasonId)
			throws UnsupportedEncodingException, JAXBException {
		sft = new SoftAssert();
		OrderReleaseResponse cancellationLineRes = omsServiceHelper.cancelLine(orderReleaseId, login,
				new String[] { lineId + ":" + qty }, reasonId);
		sft.assertEquals(cancellationLineRes.getStatus().getStatusType(), Type.SUCCESS,
				"Verify response after cancel Line Actual:" + cancellationLineRes.getStatus().getStatusType());
		sft.assertAll();
	}

	/**
	 * @param orderId
	 * @param courierCode
	 * @param trackingNo
	 * @throws IOException
	 * @throws JAXBException
	 * @throws InterruptedException
	 * @throws SQLException
	 * @throws XMLStreamException
	 * @throws JSONException
	 * @throws ParseException
	 * @throws ManagerException
	 */
	public void markOrderPK(String orderId, String courierCode, String trackingNo) throws Exception {
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderId);
		for (OrderReleaseEntry orderReleaseEntry : orderEntry.getOrderReleases()) {
			if (!orderReleaseEntry.getStatus().equalsIgnoreCase(EnumSCM.F)) {
				String releaseId = orderReleaseEntry.getId().toString();

				updateCourierAndTrackingForRelease(releaseId, courierCode, trackingNo);

				omsServiceHelper.markReadyToDispatchV3ForMyntraSeller(releaseId, ReadyToDispatchType.POSITIVE.name());
				sft.assertEquals(omsServiceHelper.validateReleaseStatusInOMS(releaseId, EnumSCM.PK, 5),
						"Release is " + releaseId + "not in " + EnumSCM.PK + " status");
			}

		}

	}

	/**
	 * @param releaseId
	 * @param courierCode
	 * @param trackingNo
	 */
	public void updateCourierAndTrackingForRelease(String releaseId, String courierCode, String trackingNo) {
		String query = "update order_release set courier_code='" + courierCode + "',tracking_no='" + trackingNo
				+ "' where id = '" + releaseId + "';";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
	}
}
