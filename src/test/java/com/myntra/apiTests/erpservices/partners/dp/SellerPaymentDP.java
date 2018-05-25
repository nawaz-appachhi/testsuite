package com.myntra.apiTests.erpservices.partners.dp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.common.enums.PaymentMethod;
import com.myntra.oms.common.enums.RefundType;

public class SellerPaymentDP {

	static SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();

	public static String randomGen(int length) {
		Random random = new Random();
		int n = random.nextInt(10);
		for (int i = 0; i < length - 1; i++) {
			n = n * 10;
			n += random.nextInt(10);
		}
		String ar = Integer.toString(Math.abs(n));
		return ar;
	}

	@DataProvider
	public static Object[][] createSellerSPS(ITestContext testContext)

	{
		String rand = randomGen(8);
		String email = "suhbham" + rand + "@gmail.com";

		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "Shubham kumar gupta", "test", "9880880888", "bangalore", "Country", "India", "789760",
				"asdsa", "" + email + "", "hajsh123", "1234567", "1", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_updateSeller(ITestContext testContext)

	{
		String rand = randomGen(8);
		String email = "suhbham" + rand + "@gmail.com";

		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "314", "vallabha", "test", "test1", "9880880888", "bangalore", "Country", "India", "789760",
				"asdsa", "sri@gmail.com", "hajsh123", "1234567", "WALLET", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] reloadSellerCache(ITestContext testContext) {
		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "5", expected };
		String[] arr2 = { "3", expected };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_getSellerById(ITestContext testContext) {
		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "2", expected };
		String[] arr2 = { "3", expected };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_MarginCacheUpdate(ITestContext testContext)

	{
		String rand = randomGen(8);
		String email = "suhbham" + rand + "@gmail.com";

		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "15", "Golden Peacock", "Ring", "Women", "30.0", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_StyleMarginCacheUpdate(ITestContext testContext)

	{
		String rand = randomGen(8);
		String email = "suhbham" + rand + "@gmail.com";

		String expected = "status.statusCode==3::" + "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String[] arr1 = { "6", "SELLER", "290007", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(1) + randomGen(10) + randomGen(1);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251884", "1251884",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251884");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1251884, Long.parseLong(OrderReleaseId), 1, 5, 1251884, "A", "Added", 373908, "ON_HAND",
				2.0, 0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty2WithSkuPriceZero(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "4.00", "5.00", "1.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "0.00", "1.00", "1.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251882", "1251882",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"0.00", "1", "1.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "500", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "0", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "200", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "100", "1", "5",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanItemInstrument("100", paymentPlanItemIdship);

		// sellerHelper.InsertPaymentPlanItemInstrument("0",
		// paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "500", "DEBIT");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 4.0, 5.0, 1.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2.0, 0.0, Long.parseLong(lineId1), 0.0, 1.0, 0.0, false, 0.0, 1251842, Long.parseLong(OrderReleaseId1),
				1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 0.0, 0.0, Long.parseLong(lineId2), 0.0, 2.0, 0.0, false,
				0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5, 1251882, "A", "Added", 373908, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 1251881,
				Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "QD", "QA Done", 373908, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 0.0, 1.0, 1.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 1.0, 0.0, false, 0.0,
				1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 0.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, releaseMessage, releaseMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerMarginCheckForSellerId5(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1899.00", "1899.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "1899.00", "1899.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"1899.00", "1", "1899.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "189900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "189900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("189900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "189900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 1899.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 1899.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_JITSku1Qty1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "1", "999.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "99900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "1", "19", "1155915");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "99900", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 999.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 999.0,
				0.0, false, 0.0, 346107, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 999.0, 0.0, ppsId, PaymentMethod.on.name());
		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_JITMuliSkuMultiQtyRel4Return4(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation1 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation2 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation3 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIdref2 = randomGen(8);
		String paymentPlanItemIdref3 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "399600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "2", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "99900", "2", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "399600", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "A", "Added", 346108, "JUST_IN_TIME", 999.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "QD", "QA Done", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "QD", "QA Done", 346108, "JUST_IN_TIME", 999.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();

		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdCancellation1, OrderReleaseId, refundType);
		String returnMessage2 = sellerHelper.RefundMessageCreation(ppsIdCancellation2, OrderReleaseId, refundType);
		String returnMessage3 = sellerHelper.RefundMessageCreation(ppsIdCancellation3, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, paymentPlanItemId1,
				orderMessage, releaseFundMessage, lineId, ppsIdCancellation, ppsIdCancellation1, ppsIdCancellation2,
				ppsIdCancellation3, paymentPlanItemIdref, paymentPlanItemIdref1, paymentPlanItemIdref2,
				paymentPlanItemIdref3, returnMessage, returnMessage1, returnMessage2, returnMessage3 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_JITMuliSkuMultiQtyHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdref, String paymentPlanItemIdref1, String paymentPlanItemIdref2,
			String paymentPlanItemIdref3, String ppsId, String ppsIdCancellation, String ppsIdCancellation1,
			String ppsIdCancellation2, String ppsIdCancellation3) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "99900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation1, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation1, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation1, paymentPlanItemIdref1, "SKU", "99900", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "99900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation2, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation2, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation2, paymentPlanItemIdref2, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation2, "99900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation3, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation3, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation3, paymentPlanItemIdref3, "SKU", "99900", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref3, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation3, "99900", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_VectorQty1CB(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		;
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "0", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "0");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1WalletAmountReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_Wallet_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1WalletAmountLessThan1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdReturnId = "r76f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79855", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("45", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79855", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "45", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());
		// String m = new
		// String(Files.readAllBytes(Paths.get("../apiTests/Data/payloads/XML/createOrderWithSeller")));
		// String orderMessage = m.replaceAll("Order_ID",
		// OrderId).replaceAll("Date",
		// sellerHelper.getDate().replaceAll("lineId", lineId));
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -------------------------Refund fund
		// ----------------------------------------

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturnId, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				returnMessage, ppsIdReturnId, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty1WalletAmountLessThan1Helper(String OrderId, String store_order_id,
			String paymentPlanItemIdref1, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79855", paymentPlanItemIdref1, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("45", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79855", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "45", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1CodWalletAmount(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = OrderId;
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("69900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "69900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "10000", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1CodWalletAmountLessThan1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdRefund = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79825", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("75", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79825", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "75", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				packedMessage, ppsIdRefund, paymentPlanItemIdref1, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty1CodWalletAmountLessThan1ReturnHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref1, String ppsId, String ppsIdRefund) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79825", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("75", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "79825", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "75", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBoth(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String OrderReleaseId1 = randomGen(10);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdRefund = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund1 = "retf6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1530", "1530", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "69936", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "9963", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "175", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "24", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId1),
				1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530,
				Long.parseLong(OrderReleaseId1), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdRefund1, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, packedMessage, packedMessage1, ppsIdRefund, paymentPlanItemIdref, returnMessage,
				ppsIdRefund1, paymentPlanItemIdref1, returnMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothHelper(String OrderId,
			String store_order_id, String paymentPlanItemIdref1, String ppsId, String ppsIdRefund) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "69936", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "9963", "REFUND", "10");

	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothHelper1(String OrderId,
			String store_order_id, String paymentPlanItemIdref1, String ppsId, String ppsIdRefund) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "175", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "24", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOrigin(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String OrderReleaseId1 = randomGen(10);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdRefund = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund1 = "retf6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1530", "1530", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "70111", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "9987", "DEBIT", "10");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1,
				19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827,
				"QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdRefund1, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				packedMessage, ppsIdRefund, paymentPlanItemIdref, returnMessage, ppsIdRefund1, paymentPlanItemIdref1,
				returnMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOriginHelper(
			String OrderId, String store_order_id, String paymentPlanItemIdref1, String ppsId, String ppsIdRefund)
			throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "69936", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "9963", "REFUND", "10");

	}

	public static void SPS_SellerQty2_Release_1_CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToOriginHelper1(
			String OrderId, String orderReleaseId, String store_order_id, String paymentPlanItemIdref1, String ppsId,
			String ppsIdRefund) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertOrderReleaseAdditionalInfo(orderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "175", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "24", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWallet(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String OrderReleaseId1 = randomGen(10);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdRefund = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund1 = "retf6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1530", "1530", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "69936", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "9963", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "175", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "24", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId1),
				1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2.0, 0.0, false, 0.0, 1530,
				Long.parseLong(OrderReleaseId1), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdRefund1, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, packedMessage, packedMessage1, ppsIdRefund, paymentPlanItemIdref, returnMessage,
				ppsIdRefund1, paymentPlanItemIdref1, returnMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWalletHelper(
			String OrderId, String store_order_id, String paymentPlanItemIdref1, String ppsId, String ppsIdRefund)
			throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "79900", "REFUND", "10");

	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1ReleaseReturnBothToWalletHelper1(
			String OrderId, String orderReleaseId, String store_order_id, String paymentPlanItemIdref1, String ppsId,
			String ppsIdRefund) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertOrderReleaseAdditionalInfo(orderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "200", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String OrderReleaseId1 = randomGen(10);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String paymentPlanExecutionStatus_id1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdCancel = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancel1 = "ret6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "1530", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "69936", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "9963", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "175", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "24", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId1), 1, 19,
				3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 1530,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId1), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RELEASE_CANCELLATION_REFUND.toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancel, OrderReleaseId, refundType);
		String cancellationMessage1 = sellerHelper.RefundMessageCreation(ppsIdCancel1, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, packedMessage, packedMessage1, ppsIdCancel, paymentPlanItemIdref,
				cancellationMessage, paymentPlanExecutionStatus_id, ppsIdCancel1, paymentPlanItemIdref1,
				cancellationMessage1, paymentPlanExecutionStatus_id1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both_Helper(String OrderId,
			String OrderReleaseId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanExecutionStatus_id) throws Exception {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "RELEASE_CANCELLATION_PPS_ID");
		// sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemIdref, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "175", "CREDIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "24", "CREDIT", "10");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
	}

	public static void SPS_SellerQty2CodWalletAmountLessThan1AndGreaterThan1_PK_Cancel_Both_Helper1(String OrderId,
			String OrderReleaseId1, String store_order_id, String ppsId, String ppsIdCancellation1,
			String paymentPlanItemIdref1, String paymentPlanExecutionStatus_id1) throws Exception {
		sellerHelper.updateOrdersRelease(OrderReleaseId1, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation1, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation1, paymentPlanItemIdref1, "SKU", "79900", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "69936", "CREDIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "9963", "CREDIT", "10");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id1, "REFUND", "CANCELLATION");
	}

	@DataProvider
	public static Object[][] SPS_SellerQty2_Release_1_CodWalletAmountLessThan1WithVATAdjustmentAndGreaterThan1ReleaseReturn(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";
		String ppsIdRefund = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdVatRefund = "vat12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdVatref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1530", "1530", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("69936", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("9963", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("175", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("24", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "70111", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "9987", "DEBIT", "10");

		// *********************VAT Adjustment Refund************
		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdVatRefund, "2.34", "1",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3827);
		sellerHelper.InsertPaymentPlan(ppsIdVatRefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdVatRefund, paymentPlanItemIdVatref, "VAT_ADJ_REFUND", "100", "1", "19",
				"3827");
		sellerHelper.InsertPaymentPlanItemInstrument("90", paymentPlanItemIdVatref, "8", "CREDIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10", paymentPlanItemIdVatref, "10", "CREDIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdVatRefund, "100", "CREDIT");
		//
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19,
				3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 1530,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19,
				3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,

				ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false,
				0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "D", "Delivered", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19,
				3831, "D", "Delivered", 1531, "ON_HAND", 799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				packedMessage, releaseMessage, ppsIdRefund, paymentPlanItemIdref, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty2_Release_1_CodWalletAmountLessThan1WithVATAdjustmentAndGreaterThan1ReleaseReturnHelper(
			String OrderId, String orderReleaseId, String store_order_id, String paymentPlanItemIdref1, String ppsId,
			String ppsIdRefund) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund, "return_" + OrderId);
		sellerHelper.InsertOrderReleaseAdditionalInfo(orderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref1, "SKU", "85", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("85", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "85", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1WalletSkuAmountGreater1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, ".75", ".75", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831", ".75",
				"1", ".75", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "75", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "75", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("75", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("40", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "75", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "40", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", .75, .75, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, .75, 0.0,
				false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", .75,
				0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1WalletSkuAmountTotalLessThan1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdReturn = "r12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, ".95", ".95", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831", ".95",
				"1", ".95", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "95", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "95", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("75", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "75", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "20", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", .95, .95, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, .95, 0.0,
				false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", .95,
				0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				.95, .95, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, .95, 0.0, false, 0.0, 1000909,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", .95, 0.0, ppsId,
				PaymentMethod.on.name());

		String refundType = RefundType.RETURN_REFUND.toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				releaseMessage, ppsIdReturn, paymentPlanItemIdref1, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty1WalletSkuAmountTotalLessThan1ReturnHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref1, String ppsId, String ppsIdReturn) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdref1, "SKU", "95", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("75", paymentPlanItemIdref1, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "75", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "20", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_CodQty1WithSKUZeroShippingChrgsPKOrderCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdshipCancel = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "0.70", "50.70", "50.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "0.70", "50.70", "50.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831", "0.70",
				"1", "50.70", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "5070", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "70", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "5000", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("70", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdship, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "5070", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 0.7, 50.7, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 50.7, 0.0,
				false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 0.7,
				0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				0.7, 50.7, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 50.7, 0.0, false, 0.0, 1000909,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 0.7, 0.0, ppsId,
				PaymentMethod.cod.name());

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, packedMessage, ppsId, ppsIdCancellation,
				paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdshipCancel, paymentPlanExecutionStatus_id,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodQty1WithSKUZeroShippingChrgsPKOrderCancel_Helper(String OrderId, String ppsId,
			String OrderReleaseId, String store_order_id, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdshipCancel, String paymentPlanExecutionStatus_id) throws Exception {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrderCancelledOn(OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "70", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdshipCancel, "SHIPPING_CHARGES", "5000",
				"1", "19", "PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("70", paymentPlanItemIdref, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdshipCancel, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "5070", "REFUND", "10",
				paymentPlanExecutionStatus_id);

	}
	//

	@DataProvider
	public static Object[][] SPS_WalletQty1Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");

		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_WalletQty1WithShippingChrgsPKOrderCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdshipCancel = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "849.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "84900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "5000", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdship, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "84900", "DEBIT", "10");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "ORDER_CANCELLATION_PPS_ID");
		// sellerHelper.updateOrders(OrderId, "10");
		//
		// sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		// sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id,
		// "CANCELLATION",
		// "ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null",
		// "Null");
		// sellerHelper.InsertPaymentPlanItem(ppsIdCancellation,
		// paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItem(ppsIdCancellation,
		// paymentPlanItemIdshipCancel, "SHIPPING_CHARGES", "5000", "1", "19",
		// "PPS_9999");
		// sellerHelper.InsertPaymentPlanItemInstrument("79900",paymentPlanItemIdref,"10","REFUND","SYSTEM");
		// sellerHelper.InsertPaymentPlanItemInstrument("5000",
		// paymentPlanItemIdshipCancel, "10", "REFUND","SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation,
		// "84900", "REFUND", "10");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id,
		// "REFUND", "CANCELLATION");

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, packedMessage, ppsId, ppsIdCancellation,
				paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdshipCancel, paymentPlanExecutionStatus_id,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_WalletQty1WithShippingChrgsPKOrderCancel_Helper(String OrderId, String ppsId,
			String OrderReleaseId, String store_order_id, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdshipCancel, String paymentPlanExecutionStatus_id) throws Exception {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrderCancelledOn(OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdshipCancel, "SHIPPING_CHARGES", "5000",
				"1", "19", "PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdshipCancel, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "84900", "REFUND", "10");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
	}

	@DataProvider
	public static Object[][] SPS_WalletQty1WithShippingChrgsPKOrderCancelWithoutOrderId(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdshipCancel = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "849.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "84900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "5000", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdship, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "84900", "DEBIT", "10");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreationWithOutOrderId(store_order_id,
				ppsIdCancellation, RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, packedMessage, ppsId, ppsIdCancellation,
				paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdshipCancel, paymentPlanExecutionStatus_id,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_WalletQty1WithShippingChrgsPKReleaseCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdshipCancel = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "849.00", "50.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "849.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "84900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "5000", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdship, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "84900", "DEBIT", "10");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 849.0, 50.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------

		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				RefundType.RELEASE_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, packedMessage, ppsId, ppsIdCancellation,
				paymentPlanItemId, paymentPlanItemIdref, orderMessage, cancellationMessage, lineId,
				paymentPlanItemIdshipCancel, paymentPlanExecutionStatus_id };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_WalletQty1WithShippingChrgsPKReleaseCancel_Helper(String ppsId, String OrderReleaseId,
			String store_order_id, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdshipCancel, String paymentPlanExecutionStatus_id) throws Exception {
		sellerHelper.InsertOrderReleaseAdditionalInfo(OrderReleaseId, ppsIdCancellation, "RELEASE_CANCELLATION_PPS_ID");

		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdshipCancel, "SHIPPING_CHARGES", "5000",
				"1", "19", "PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdshipCancel, "10", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "84900", "REFUND", "10");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
	}

	@DataProvider
	public static Object[][] SPS_VectorMultiQtyCanRelUsingWalletRelease1Return1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturnId = "r7e502tk-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2199.00", "2199.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2199.00",
				"1", "2199.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "1532", "3836", "3836",
				"2399.00", "1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "219900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "239900", "1", "19", "3836");
		sellerHelper.InsertPaymentPlanItemInstrument("209900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("219900", paymentPlanItemId1, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId2, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "659700", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "30000", "DEBIT", "10");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2199.0,
				0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND",
				2199.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId1), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2399.0, 0.0, false, 0.0, 1532, Long.parseLong(OrderReleaseId2), 1, 19,
				3836, "A", "Added", 1532, "ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId1), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage2 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId2), 0.0, 2399.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId2), 1, 19, 3836, "QD", "QA Done", 1532, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Refund fund
		// ----------------------------------------

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturnId, OrderReleaseId2, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, OrderReleaseId1, OrderReleaseId2, ppsId,
				ppsIdCancellation, ppsIdReturnId, paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdref1,
				orderMessage, cancellationMessage, lineId, lineId1, lineId2, paymentPlanExecutionStatus_id,
				releaseMessage1, releaseMessage2, paymentPlanItemId1, paymentPlanItemId2, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_Wallet_OrderWithSellerMultipleQty2ReleaseHelper(String OrderId,
			String store_order_id, String paymentPlanItemIdref1, String ppsId, String ppsIdCancellation)
			throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "239900", "1", "19",
				"3836");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref1, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "10000", "REFUND", "10");

	}

	public static void SPS_VectorMultiQtyCanRelHelperUsingWallet(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {

		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "219900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("219900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "219900", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQtyCBTxAmountValidation(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(9);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "7197.00", "7197.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4798.00", "4798.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"2399.00", "2", "4798.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2399.00", "1", "2399.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "719700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "479800", "2", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "239900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("279800", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId, "0", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId1, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "519700", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "0");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 7197.0, 7197.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4798.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0, 1000909,
				Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// String m = new
		// String(Files.readAllBytes(Paths.get("../apiTests/Data/payloads/XML/createOrderWithSeller")));
		// String orderMessage = m.replaceAll("Order_ID",
		// OrderId).replaceAll("Date",
		// sellerHelper.getDate().replaceAll("lineId", lineId));
		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, paymentPlanItemId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1LoyaltyPointReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "7");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_LP_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "7");

	}

	@DataProvider
	public static Object[][] SPS_VectorQty1LP(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "1899.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "1000.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "1899.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1000.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"2399.00", "1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "1000.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "239900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("189900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "189900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "7");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 1899.0, 0.0, false, 0.0, 0.0, 1000.0, 0.0, Long.parseLong(lineId), 0.0,
				1899.0, 0.0, false, 1000.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// String m = new
		// String(Files.readAllBytes(Paths.get("../apiTests/Data/payloads/XML/createOrderWithSeller")));
		// String orderMessage = m.replaceAll("Order_ID",
		// OrderId).replaceAll("Date",
		// sellerHelper.getDate().replaceAll("lineId", lineId));
		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);

	}

	@DataProvider
	public static Object[][] SPS_VectorQty1GC(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"2399.00", "1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "239900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.on.name());
		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1Coupon(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2000.00", "0.00", "0.00", "0.00", "0.00",
				"399.00", "0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "399.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"2399.00", "1", "2000.00", "0.00", "0", "19", "0.00", "0.00", "399.00", "Null", "0.00", "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "1");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 399.0,
				2000.0, 0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQtyPriceOverRide(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "806968", "5430905", "5430905",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373905", "1251866", "1251866",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "539700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "5", "5430905");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251866");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 806968,
				Long.parseLong(OrderReleaseId1), 1, 5, 5430905, "A", "Added", 806968, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373905, Long.parseLong(OrderReleaseId2), 1, 5,
				1251866, "A", "Added", 373905, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// --------------------price Mismatch refund
		// ----------------------------------
		sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund, "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"PRICE_OVERRIDE_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "PARTIAL_REFUND", "10000", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.PRICE_OVERRIDE_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1,
				paymentPlanItemId2, orderMessage, lineId, refundMessage, paymentPlanItemIdref, ppsIdrefund,
				releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1PriceOverRide(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// --------------------price Mismatch refund
		// ----------------------------------
		sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund, "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"PRICE_OVERRIDE_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "PARTIAL_REFUND", "10000", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.PRICE_OVERRIDE_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, refundMessage,
				paymentPlanItemIdref, ppsIdrefund, releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1VatRefundAndPriceOveride(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String ppsIdrefundVatRefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4)
				+ "dh30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIVatRefund = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "1", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		// sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund,
		// "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QD Done", 1530, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// --------------------price Mismatch refund
		// ----------------------------------

		sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund, "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"PRICE_OVERRIDE_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "PARTIAL_REFUND", "10000", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.PRICE_OVERRIDE_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		// --------------------Vat Mismatch refund
		// ----------------------------------
		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdrefundVatRefund, "2.34", "100",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		sellerHelper.InsertPaymentPlan(ppsIdrefundVatRefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefundVatRefund, paymentPlanItemIVatRefund, "VAT_ADJ_REFUND", "10000",
				"1", "1", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIVatRefund, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefundVatRefund, "10000", "CREDIT");

		String refundTypeVatAdjustment = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessageVatAdjustment = sellerHelper.RefundMessageCreation(ppsIdrefundVatRefund, OrderReleaseId,
				refundTypeVatAdjustment);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				refundMessage, paymentPlanItemIdref, ppsIdrefund, releaseMessage, refundMessageVatAdjustment,
				ppsIdrefundVatRefund };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	//

	@DataProvider
	public static Object[][] SPS_VectorQty1VatRefund(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String ppsIdreturn = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "1", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		// sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund,
		// "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// --------------------price Mismatch refund
		// ----------------------------------
		// sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund,
		// "799.00", "100.00");

		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdrefund, "2.34", "100",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "VAT_ADJ_REFUND", "10000", "1", "1",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String refundType1 = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdreturn, OrderReleaseId, refundType1);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, refundMessage,
				paymentPlanItemIdref, ppsIdrefund, releaseMessage, ppsIdreturn, paymentPlanItemIdret, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1VatRefundReturnHelper(String OrderId, String store_order_id, String ppsId,
			String ppsReturnId, String paymentPlanItemIdref) throws Exception {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsReturnId, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId,
		// ppsReturnId, "2299");
		sellerHelper.InsertPaymentPlan(ppsReturnId, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsReturnId, paymentPlanItemIdref, "SKU", "69900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("69900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsReturnId, "69900", "REFUND", "1");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1VatAdjustmentCancelRefund(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String ppsIdLineCancellation = "g3m" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4)
				+ "dh30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdLineCancellation = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);

		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251882", "1251882",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "539700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5,
				1251882, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// -------------------Release 1 qty
		// -----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId2), 0.0, 2299.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5, 1251882, "QD", "QA Done", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage2 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false,
				0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// --------------------price Mismatch refund
		// ----------------------------------
		// sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund,
		// "799.00", "100.00");
		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdrefund, "2.34", "100",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "VAT_ADJ_REFUND", "10000", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String lineCancellationRefundType = RefundType.LINE_CANCELLATION_REFUND.name().toString();
		String lineCancellationRefundMessage = sellerHelper.RefundMessageCreation(ppsIdLineCancellation, OrderReleaseId,
				lineCancellationRefundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, refundMessage,
				paymentPlanItemIdref, ppsIdrefund, releaseMessage, ppsIdLineCancellation, lineCancellationRefundMessage,
				paymentPlanExecutionStatus_id, paymentPlanItemIdLineCancellation, releaseMessage1, releaseMessage2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public void SPS_VectorQty1VatAdjustmentCancelRefundHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "799.00");
		// sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79000", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79000", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	public void SPS_VectorQty1VatAdjustmentCancelRefundHelper2(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "799.00");
		// sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79000", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79000", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty2VatAdjustmentCancel1Release1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String lineId3 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String ppsIdLineCancellation = "g3m" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4)
				+ "dh30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemId3 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdGW = randomGen(8);
		String paymentPlanItemIdLineCancellation = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);

		sellerHelper.InsertOrder(OrderId, store_order_id, "7696.0", "7721.0", "0.00", "0.00", "0.00", "0.00", "0.00",
				"25.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "6897.0", "6922.0", "0.00", "0.00",
				"0.00", "0.00", "0.00", "25.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.0", "799.0", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "2299.00",
				"1", "2324.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1531", "3832", "3832",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "1531", "3833", "3833",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderLine(lineId3, OrderId, OrderReleaseId1, store_order_id, "346107", "1152953", "1152953",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "769600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdGW, "GIFT_WRAP_CHARGES", "2500", "1", "19",
				"PPS_9994");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "19", "3833");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId3, "SKU", "79900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdGW);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId3);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "772100", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 7696.0, 7721.0, 0.0, false, 0.0, 25.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2299.0, 0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3832, "A", "Added", 1531, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19,
				3833, "A", "Added", 1531, "ON_HAND", 2299.0, 0.0, Long.parseLong(lineId3), 0.0, 799.0, 0.0, false, 0.0,
				346107, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "A", "Added", 346107, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -------------------Release 1 qty-------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2324.0, 0.0, false, 0.0, 25.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2299.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId3), 0.0, 799.0, 0.0, false,
				0.0, 346107, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		// --------------------Vat Adjustment refund
		// sellerHelper.insertOrderLineAdditionalInfo(lineId1, ppsIdrefund,
		// "2299.00", "100.00");
		sellerHelper.insertOrderLineAdditionalInfo1(lineId1, ppsIdrefund, "2.34", "100",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3832);
		sellerHelper.InsertPaymentPlan(ppsIdrefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId1 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "VAT_ADJ_REFUND", "10000", "1", "19",
				"3832");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "CREDIT");

		String refundType = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String lineCancellationRefundType = RefundType.LINE_CANCELLATION_REFUND.name().toString();
		String lineCancellationRefundMessage = sellerHelper.RefundMessageCreation(ppsIdLineCancellation, OrderReleaseId,
				lineCancellationRefundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, lineId1, lineId2, lineId3, refundMessage, releaseMessage, ppsIdLineCancellation,
				lineCancellationRefundMessage, paymentPlanExecutionStatus_id, paymentPlanItemIdLineCancellation,
				releaseMessage1, ppsIdrefund };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public void SPS_VectorQty2VatAdjustmentCancel1Release1Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2299.00");
		// sellerHelper.updateOrders(OrderId, "10");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanExecutionStatus_idex1 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "5", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
//		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "RELEASE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex1, "REFUND", "CANCELLATION");

		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				RefundType.RELEASE_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerVectorMulti1SkuLessThan1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "4598.95", "4598.95", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "0.95", "0.95", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "0.95",
				"1", "0.95", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "459895", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "2", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("95", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("459800", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "459895", "DEBIT");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 4598.95, 4598.95, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 0.95,
				0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 0.95,
				0.0, Long.parseLong(lineId1), 0.0, 4598.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 2,
				5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.95, 4598.95, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 4598.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, paymentPlanExecutionStatus_id, releaseMessage,
				paymentPlanItemId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerVectorMulti1SkuLessThan1Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.20");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "95", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("95", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "95", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyCan1RelReturn1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "lpe362ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanreturnItemId = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		// sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId1,
		// store_order_id, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// --------------------------Return
		// Fund-------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, lineId1, paymentPlanExecutionStatus_id, releaseMessage,
				returnMessage, ppsIdReturn, paymentPlanreturnItemId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_SPS_SellerMultiQtyReturnHelper(String lineId, String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsReturnId) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsReturnId, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId,
		// ppsReturnId, "2299");
		sellerHelper.InsertPaymentPlan(ppsReturnId, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsReturnId, paymentPlanItemIdref, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsReturnId, "229900", "REFUND", "1");

	}

	public static void SPS_SellerMultiQtyCanRelHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id,
		// "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyCanRelRef(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		// sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId1,
		// store_order_id, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refundType);
		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, paymentPlanExecutionStatus_id, releaseMessage,
				OrderReleaseId1, returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerMultiQtyCanRelRefHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId,
		// ppsIdReturn, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "229900", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementMultipleQtyReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId, "1", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT", "");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);
		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, paymentPlanExecutionStatus_id, releaseMessage,
				OrderReleaseId1, returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementMultipleQtyReturnHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, OrderId, "RETURN", "return_" + OrderId + "", ppsId, "Null", "Null",
				"Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "229900", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyRelRefCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		// String OrderReleaseId = randomGen(10);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "150925", "512230", "512230",
				"2299.00", "1", "2299.00", "0.00", "0", "18", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "150925", "512230", "512230",
				"2299.00", "1", "2299.00", "0.00", "0", "18", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "150925", "512230", "512230",
				"2299.00", "1", "2299.00", "0.00", "0", "18", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "18", "512230");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		// sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId1,
		// store_order_id, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 150925, Long.parseLong(OrderReleaseId), 3, 18, 512230, "A", "Added", 150925, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				150925, Long.parseLong(OrderReleaseId1), 1, 18, 512230, "QD", "QA Done", 150925, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, lineId1, paymentPlanExecutionStatus_id, releaseMessage,
				OrderReleaseId, OrderReleaseId1, returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerMultiQtyRelRefCancelHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId,
		// ppsIdReturn, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "1", "5", "512230");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "229900", "REFUND");

	}

	public static void SPS_SellerMultiQtyRelRefLineCancellationHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id,
		// "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "5",
				"512230");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
	}

	@DataProvider
	public static Object[][] SPS_VectorMultiQtyCanRelease1Return1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsreturnId = "r7e792sb-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		// String
		// ppsIdReturn="r9e252mt-20de-"+randomGen(4)+"-a94d-"+randomGen(4)+"dx03a"+randomGen(3)+"";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2299.00",
				"1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "1532", "3836", "3836",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "219900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "239900", "1", "19", "3836");
		sellerHelper.InsertPaymentPlanItemInstrument("219900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2199.0,
				0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND",
				2199.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2399.0, 0.0, false, 0.0, 1532, Long.parseLong(OrderReleaseId), 1, 19,
				3836, "A", "Added", 1532, "ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.0, 4598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2399.0, 0.0, false, 0.0, 1532, Long.parseLong(OrderReleaseId), 1, 19,
				3836, "QD", "QA Done", 1532, "ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsreturnId, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, ppsreturnId, paymentPlanItemId,
				paymentPlanItemIdref, paymentPlanItemIdref1, orderMessage, cancellationMessage, lineId,
				paymentPlanExecutionStatus_id, releaseMessage, paymentPlanItemId1, paymentPlanItemId2, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_OrderWithSellerQty1ReturnHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3836");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239900", "REFUND");

	}

	public static void SPS_VectorMultiQtyCanRelRefHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {

		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_VectorSellerJITSingleQtyCanRelRef(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3836", "3836", "2299.00",
				"1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"2299.00", "1", "2299.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "19", "3836");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3836, "A", "Added", 1531, "ON_HAND",
				2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId1), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5,
				1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId1), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, lineId1, paymentPlanExecutionStatus_id, releaseMessage,
				OrderReleaseId1, returnMessage, ppsIdReturn, paymentPlanItemIdret, paymentPlanItemId1,
				paymentPlanItemId2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorSellerJITSingleQtyCanRelRefHelper(String lineId1, String OrderId,
			String store_order_id, String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId1,
		// ppsIdReturn, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "229900", "REFUND");

	}

	public static void SPS_VectorSellerJITSingleQtyCanRelRefCancelHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		// sellerHelper.updateOrders(OrderId, "10");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId,"F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "19", "3836");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1",
				paymentPlanExecutionStatus_id);
	}

	@DataProvider
	public static Object[][] SPS_MultiSellerMultiQty(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String OrderReleaseId3 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String lineId3 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund1 = "c12i6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund2 = "d12i6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund3 = "e12i6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund4 = "f12i6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund5 = "r12i6cb4-4e68-" + randomGen(4) + "-r05d-" + randomGen(4) + "rf30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdRefund1 = randomGen(8);
		String paymentPlanItemIdRefund2 = randomGen(8);
		String paymentPlanItemIdRefund3 = randomGen(8);
		String paymentPlanItemIdRefund4 = randomGen(8);
		String paymentPlanItemIdRefund5 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6196.00", "6196.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId3, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373911", "1251896", "1251896",
				"2299.00", "1", "2299.00", "0.00", "0", "4", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId3, OrderId, OrderReleaseId3, store_order_id, "373911", "1251896", "1251896",
				"2299.00", "1", "2299.00", "0.00", "0", "4", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "619600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "2", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "2", "4", "1251896");
		sellerHelper.InsertPaymentPlanItemInstrument("159800", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("459800", paymentPlanItemId1);

		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "619600", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6196.0, 6196.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1598.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId2), 0.0, 4598.0, 0.0, false, 0.0, 373911,
				Long.parseLong(OrderReleaseId2), 2, 4, 1251896, "A", "Added", 373911, "ON_HAND", 2299.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id,
				"PK", "Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseFundMessage1 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id,
				"PK", "Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseFundMessage2 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id,
				"PK", "Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId2), 0.0, 2299.0,
				0.0, false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 4, 1251896, "QD", "QA Done", 373911,
				"ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseFundMessage3 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id,
				"PK", "Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId3), 0.0, 2299.0,
				0.0, false, 0.0, 373911, Long.parseLong(OrderReleaseId3), 1, 4, 1251896, "QD", "QA Done", 373911,
				"ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdRefund1, OrderReleaseId, refundType);
		String returnMessage2 = sellerHelper.RefundMessageCreation(ppsIdRefund2, OrderReleaseId1, refundType);
		String returnMessage3 = sellerHelper.RefundMessageCreation(ppsIdRefund3, OrderReleaseId2, refundType);
		String returnMessage4 = sellerHelper.RefundMessageCreation(ppsIdRefund4, OrderReleaseId3,
				RefundType.LINE_CANCELLATION_REFUND.toString());
		String returnMessage5 = sellerHelper.RefundMessageCreation(ppsIdRefund5, OrderReleaseId2, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, OrderReleaseId2, OrderReleaseId3, store_order_id,
				ppsId, paymentPlanItemId, orderMessage, lineId, lineId3, paymentPlanItemId1, releaseFundMessage,
				releaseFundMessage1, releaseFundMessage2, releaseFundMessage3, paymentPlanItemIdRefund1, ppsIdRefund1,
				paymentPlanItemIdRefund2, ppsIdRefund2, paymentPlanItemIdRefund3, ppsIdRefund3,
				paymentPlanItemIdRefund4, ppsIdRefund4, returnMessage1, returnMessage2, returnMessage3, returnMessage4,
				returnMessage5, paymentPlanExecutionStatus_id, ppsIdRefund5, paymentPlanItemIdRefund5 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_MultiSellerMultiQtyCancelHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation,
		// "LINE_CANCELLATION_PPS_ID");
		// sellerHelper.updateOrders(OrderId, "10");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId,"F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "4",
				"1251896");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id,
		// "REFUND", "CANCELLATION");
	}

	public static void SPS_MultiSellerMultiQtyReturnHelper(String OrderId, String store_order_id, String ppsId,
			String paymentPlanItemIdref1, String ppsIdRefund1, String paymentPlanItemIdref2, String ppsIdRefund2,
			String paymentPlanItemIdref3, String ppsIdRefund3, String paymentPlanItemIdref4, String ppsIdRefund4)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund1, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund1, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund1, paymentPlanItemIdref1, "SKU", "79900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund1, "79900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund2, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund2, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund2, paymentPlanItemIdref2, "SKU", "79900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund2, "79900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund3, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund3, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund3, paymentPlanItemIdref3, "SKU", "229900", "1", "4", "1251896");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref3, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund3, "229900", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund4, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdRefund4, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund4, paymentPlanItemIdref4, "SKU", "229900", "1", "4", "1251896");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref4, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund4, "229900", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1ReleaseAndReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QC Done", 373908, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_tryAndBuy(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1532", "3835", "3835", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId), 1, 19, 3835, "A", "Added", 1532, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				1532, Long.parseLong(OrderReleaseId), 1, 19, 3835, "QD", "QA Done", 1532, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.TRY_AND_BUY_REFUND.name().toString();
		// String refundType = "TRY_AND_BUY_REFUND";
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithSellerQty1ReleaseHelper1(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_tryAndBuy2(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		// String ppsIdCancellation1 =
		// "k3e412yh-92te-"+randomGen(4)+"-o65d-"+randomGen(4)+"lh30a"+randomGen(3)+"";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1532", "3835", "3835", "2.00",
				"2", "4.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "400", "2", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("400", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "400", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "A", "Added", 1532, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0, false, 0.0,
				1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "QD", "QA Done", 1532, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.TRY_AND_BUY_REFUND.name().toString();
		// String refundType = "TRY_AND_BUY_REFUND";
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		// String returnMessage1 =
		// sellerHelper.RefundMessageCreation(ppsIdCancellation1, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithVectorQty2ReleaseHelper1(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_sameSkuQty2AndReturnBoth(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		// String ppsIdCancellation1 =
		// "k3e412yh-92te-"+randomGen(4)+"-o65d-"+randomGen(4)+"lh30a"+randomGen(3)+"";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1532", "3835", "3835", "2.00",
				"2", "4.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "400", "2", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("400", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "400", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "A", "Added", 1532, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0, false, 0.0,
				1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "QD", "QA Done", 1532, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.TRY_AND_BUY_REFUND.name().toString();
		// String refundType = "TRY_AND_BUY_REFUND";
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		// String returnMessage1 =
		// sellerHelper.RefundMessageCreation(ppsIdCancellation1, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithSameSkuQty2AndReturnBoth(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1ReleaseRetryOnOrderDeliverAndReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373911", "1251897", "1251897",
				"2.00", "1", "2.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId), 1, 3, 1251897, "A", "Added", 373911, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				373911, Long.parseLong(OrderReleaseId), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ------------------Release Fund on PK------------------------------
		String releaseFundMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				373911, Long.parseLong(OrderReleaseId), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref, releaseFundMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty1ReleaseRetryOnOrderDeliverAndReturnHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1ReleaseAndReturnOnDL(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373911", "1251897", "1251897",
				"2.00", "1", "2.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId), 1, 3, 1251897, "A", "Added", 373911, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				373911, Long.parseLong(OrderReleaseId), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerQty1ReleaseAndReturnOnDLHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_VectorQty2Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "1531", "4852", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "80100", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "80100", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.00, 801.00, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0,
				0.0, false, 0.0, 1530, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0,
				0.0, Long.parseLong(lineId2), 0.0, 799.0, 0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19,
				3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		String[] arr1 = { orderMessage, cancellationMessage, OrderId, store_order_id, OrderReleaseId, lineId, ppsId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemId, paymentPlanItemId2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void OrderWithVectorQty2CancelHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "799.00", "WP");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty2CancelBoth(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		// String ppsIdCancellation1 =
		// "k3e412yh-92te-"+randomGen(4)+"-o65d-"+randomGen(4)+"lh30a"+randomGen(3)+"";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "801.00", "801.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1532", "3835", "3835", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "80100", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "80100", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 801.0, 801.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "A", "Added", 1532, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId1), 2, 19,
				3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 1532,
				Long.parseLong(OrderReleaseId), 2, 19, 3835, "QD", "QA Done", 1532, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				"ORDER_CANCELLATION_REFUND");

		String[] arr1 = { orderMessage, releaseMessage, cancellationMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemId, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithVectorQty2CancelBoth(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "4.00", "F");
		sellerHelper.updateOrderLineStatus(lineId, "IC");

		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "2", "19", "3835");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1ShippingCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "4.00", "2.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "4.00", "4.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "200", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "400", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 4.0, 2.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.updateOrderLineStatus(OrderId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdrefship, "SHIPPING_CHARGES", "200", "1",
				"19", "PPS_9999");

		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdrefship, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "400", "REFUND");
		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { orderMessage, cancellationMessage, OrderId, store_order_id, lineId, ppsId, paymentPlanItemId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship, paymentPlanItemIdrefship };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_DifferentSellersMultiReleaseSkuWithShippingChrgs(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "5.00", "3.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "273.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346107", "1152953", "1152953",
				"273.00", "1", "273.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27800", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 275.0, 278.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 19,
				1152953, "A", "Added", 346107, "ON_HAND", 273.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 5.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0, false,
				0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, orderMessage, lineId, ppsId, paymentPlanItemId, ppsIdCancellation,
				paymentPlanItemIdref, paymentPlanItemIdship, paymentPlanItemIdrefship, releaseMessage, returnMessage,
				releaseMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_DifferentSellersMultiReleaseSkuWithShippingChrgsReturnHelper(String OrderId,
			String store_order_id, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_DifferentSellersMultiRelease1CancelSkuWithShippingChrgs(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdLineCancellation = "m76f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "5.00", "3.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "273.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346107", "1152953", "1152953",
				"273.00", "1", "273.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27800", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 275.0, 278.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 21,
				1152953, "A", "Added", 346107, "ON_HAND", 273.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 5.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0, false,
				0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdLineCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, orderMessage, lineId, ppsId, paymentPlanItemId, ppsIdCancellation,
				paymentPlanItemIdref, paymentPlanItemIdship, paymentPlanItemIdrefship, releaseMessage, returnMessage,
				releaseMessage1, cancellationMessage, ppsIdLineCancellation, paymentPlanItemIdCancel, OrderReleaseId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_DifferentSellersMultiRelease1CancelSkuWithShippingChrgsLineCancellation(String lineId1,
			String OrderId, String store_order_id, String OrderReleaseId, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdship) throws Exception {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId1, ppsIdCancellation, "2.00");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId1, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		// sellerHelper.InsertPaymentPlanItem(ppsIdCancellation,
		// paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1",
		// "19", "PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		// sellerHelper.InsertPaymentPlanItemInstrument("300",
		// paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_VectorQty1SDDReleaseRefund(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "242400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "242400", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2399.0, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------Cancellation
		// --------------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseMessage, refundMessage, OrderId, store_order_id, lineId, ppsId,
				paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1SDDReleaseRefundHelper(String lineId, String orderId, String store_order_id,
			String ppsId, String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.updateOrderReleaseStatus(orderId, "F");
		sellerHelper.updateOrderLineStatus(orderId, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "RETURN_REFUND" + orderId + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1SDDBreach(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "242400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "242400", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2399.0, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------Cancellation
		// --------------------------------------------------------
		String refundType = RefundType.EXPRESS_CHARGES_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseMessage, refundMessage, OrderId, OrderReleaseId, store_order_id, lineId,
				ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1SDDBreachHelper(String lineId, String orderId, String OrderReleaseId,
			String store_order_id, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertOrderReleaseAdditionalInfo(OrderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"EXPRESS_CHARGES_REFUND" + orderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SHIPPING_CHARGES", "2500", "1",
				"19", "PPS_999");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "2500", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty3ShipRelease(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";

		System.out.println("-------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "7197.00", "7222.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "7197.00", "7222.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2399.00",
				"3", "7197.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "722200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "3", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("719700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "722200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 7197.0, 7222.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				7197.0, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 3, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String[] arr1 = { orderMessage, releaseMessage, OrderId, OrderReleaseId, store_order_id, lineId, ppsId,
				paymentPlanItemId, paymentPlanItemIdship };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1ShippingLessThan1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.45", "0.45", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.45", "0.45", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "245", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "45", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("45", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "245", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.45, 0.45, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				paymentPlanItemIdship };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorAndJITCancelboth(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation2 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8); // payment plan id for
													// refund
		String paymentPlanItemIdref2 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1798.00", "1798.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "4852", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346107", "1155915", "1152953",
				"999.00", "1", "999.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "179800", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "99900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "179800", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1798.00, 1798.00, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				799.00, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 799.0, 0.0, Long.parseLong(lineId1), 0.0, 999.00, 0.0, false, 0.0, 1155915,
				Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "A", "Added", 346107, "ON_HAND", 999.00, 0.0, ppsId,
				PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.00, 799.00, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.00, 0.0, false, 0.0,
				4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 999.00, 999.00, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 999.00, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "QD", "QA Done", 346107,
				"ON_HAND", 999.00, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { orderMessage, cancellationMessage, OrderId, store_order_id, OrderReleaseId, OrderReleaseId1,
				lineId, lineId1, releaseMessage, releaseMessage1, ppsId, ppsIdCancellation, ppsIdCancellation2,
				paymentPlanItemIdref, paymentPlanItemIdref2, paymentPlanItemId, paymentPlanItemId2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void OrderWithVectorAndJITCancelbothHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String OrderReleaseId2, String lineId, String lineId2, String ppsId,
			String ppsIdCancellation, String ppsIdCancellation2, String paymentPlanItemIdref,
			String paymentPlanItemIdref2) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "799.00", "F");
		sellerHelper.updateOrdersRelease(OrderReleaseId2, "999.00", "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId2, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref2, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "179800", "REFUND");

	}

	@DataProvider
	public static Object[][] OrderWithVectorAndJITReleaseBothAndReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation2 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8); // payment plan id for
													// refund
		String paymentPlanItemIdref2 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1798.00", "1798.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "4852", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "346107", "1155915", "1152953",
				"999.00", "1", "999.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "179800", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "99900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "179800", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1798.00, 1798.00, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				799.00, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 799.0, 0.0, Long.parseLong(lineId2), 0.0, 999.00, 0.0, false, 0.0, 1155915,
				Long.parseLong(OrderReleaseId2), 1, 19, 1152953, "A", "Added", 346107, "ON_HAND", 999.00, 0.0, ppsId,
				PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------

		String CancellationMes = new String(
				Files.readAllBytes(Paths.get("../apiTests/Data/payloads/XML/cancelOnlineOrder")));
		String cancellationMessage = CancellationMes.replaceAll("pps_ID", ppsIdCancellation)
				.replaceAll("ORDER_CANCELLATION_REFUND", "LINE_CANCELLATION_REFUND");
		String cancellationMessage2 = CancellationMes.replaceAll("pps_ID", ppsIdCancellation2)
				.replaceAll("ORDER_CANCELLATION_REFUND", "LINE_CANCELLATION_REFUND");

		String[] arr1 = { orderMessage, cancellationMessage, cancellationMessage2, OrderId, store_order_id,
				OrderReleaseId, OrderReleaseId2, lineId, lineId2, ppsId, ppsIdCancellation, ppsIdCancellation2,
				paymentPlanItemIdref, paymentPlanItemIdref2, paymentPlanItemId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public void OrderWithVectorAndJITReleaseBothAndReturnHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String OrderReleaseId2, String lineId, String lineId2, String ppsId,
			String ppsIdCancellation, String ppsIdCancellation2, String paymentPlanItemIdref,
			String paymentPlanItemIdref2) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "799.00", "F");
		sellerHelper.updateOrdersRelease(OrderReleaseId2, "999.00", "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId2, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation2, OrderId, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId2 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation2, paymentPlanItemIdref2, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation2, "99900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1GiftWrapShippingCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdGW = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		String paymentPlanItemIdrefGR = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.65", "826.65", "2.00", "0.00", "0.00", "0.00", "0.00",
				"25.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.65", "826.65", "2.00", "0.00",
				"0.00", "0.00", "0.00", "25.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "4852", "3831", "799.65",
				"1", "799.65", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "82665", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79965", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "200", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdGW, "GIFT_WRAP_CHARGES", "2500", "1", "19",
				"PPS_9994");
		sellerHelper.InsertPaymentPlanItemInstrument("79965", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdGW);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "82665", "DEBIT");
		String updateOrders = "update orders SET `is_gift` = 1 where id = " + OrderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.65, 826.65, 2.0, false, 0.0, 25.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				799.65, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 799.65, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.updateOrderLineStatus(OrderId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79965", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdrefship, "SHIPPING_CHARGES", "200", "1",
				"19", "PPS_9999");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdrefGR, "GIFT_WRAP_CHARGES", "2500", "1",
				"19", "PPS_9994");

		sellerHelper.InsertPaymentPlanItemInstrument("79965", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdrefship, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdrefGR, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "82665", "REFUND");
		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				"ORDER_CANCELLATION_REFUND");

		String[] arr1 = { orderMessage, cancellationMessage, OrderId, store_order_id, lineId, ppsId, paymentPlanItemId,
				paymentPlanItemIdship, paymentPlanItemIdGW, ppsIdCancellation, paymentPlanItemIdref,
				paymentPlanItemIdrefship, paymentPlanItemIdrefGR };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1GiftWrapShippingReleaseRefund(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String paymentPlanItemIdGW = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.65", "826.65", "2.00", "0.00", "0.00", "0.00", "0.00",
				"25.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.65", "826.65", "2.00", "0.00",
				"0.00", "0.00", "0.00", "25.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "4852", "3831", "799.65",
				"1", "799.65", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "82665", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79965", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "200", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdGW, "GIFT_WRAP_CHARGES", "2500", "1", "19",
				"PPS_9994");
		sellerHelper.InsertPaymentPlanItemInstrument("79965", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdship);
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdGW);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "82665", "DEBIT");
		String updateOrders = "update orders SET `is_gift` = 1 where id = " + OrderId + "";
		DBUtilities.exUpdateQuery(updateOrders, "oms");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.65, 826.65, 2.0, false, 0.0, 25.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				799.65, 0.0, false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531,
				"ON_HAND", 799.65, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Release fund --------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.65, 826.65, 2.0, false, 0.0, 25.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.65, 0.0, false, 0.0,
				4852, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.65, 0.0, ppsId,
				PaymentMethod.on.name());
		// --------------------------refund --------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { orderMessage, releaseMessage, OrderId, OrderReleaseId, store_order_id, lineId, ppsId,
				paymentPlanItemId, paymentPlanItemIdship, paymentPlanItemIdGW, ppsIdCancellation, paymentPlanItemIdref,
				refundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1GiftWrapShippingReleaseRefundHelper(String lineId, String orderId,
			String store_order_id, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.updateOrderReleaseStatus(orderId, "F");
		sellerHelper.updateOrderLineStatus(orderId, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "RETURN_REFUND" + orderId + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79965", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79965", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79965", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_SellerQty1ValidateFeeAmountCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND");

		String cancellationMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId, ppsIdCancellation,
				RefundType.ORDER_CANCELLATION_REFUND.toString());

		String[] arr1 = { orderMessage, cancellationMessage, OrderId, store_order_id, lineId, ppsId, paymentPlanItemId,
				ppsIdCancellation, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQty(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		// String OrderReleaseId = randomGen(10);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "r7e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdReturnRef = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5000.00", "5000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "1000.00",
				"2", "2000.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"1500.00", "1", "1500.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"1500.00", "1", "1500.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "500000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "2", "21", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "150000", "2", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("300000", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "500000", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5000.0, 5000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 21, 3831, "A", "Added", 1531, "ON_HAND",
				1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 1500.0, 0.0,
				Long.parseLong(lineId2), 0.0, 1500.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5,
				1251881, "A", "Added", 373908, "ON_HAND", 1500.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1000.0, 1000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 21, 3831, "QD", "QA Done", 1531, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String deliverMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 1000.0, 1000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 21, 3831, "D", "Delivered", 1531, "ON_HAND",
				1000.0, 0.0, ppsId, PaymentMethod.on.name());

		String refund_type = RefundType.LINE_CANCELLATION_REFUND.toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refund_type);

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				1500.0, 0.0, ppsId, PaymentMethod.on.name());
		String refund_type1 = RefundType.RETURN_REFUND.toString();
		String RefundMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refund_type1);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, paymentPlanItemId1, lineId,
				lineId1, orderMessage, releaseMessage, deliverMessage, cancellationMessage, ppsIdCancellation,
				paymentPlanItemIdref, releaseMessage1, RefundMessage, ppsIdReturn, paymentPlanItemIdReturnRef,
				OrderReleaseId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorSellerMultiQtyLineCancellationHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "1000");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "100000", "1", "21", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND");

	}

	public static void SPS_VectorSellerMultiQty1RefundHelper(String OrderId, String store_order_id,
			String OrderReleaseId1, String ppsId, String ppsIdReturn, String paymentPlanItemIdReturnRef)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderReleaseId1);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdReturnRef, "SKU", "150000", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("150000", paymentPlanItemIdReturnRef, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "150000", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyRelRTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		// sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId,
		// store_order_id, "2299.00", "2299.00", "0.00", "0.00",
		// "0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		// sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId,
		// store_order_id, "2299.00", "2299.00", "0.00", "0.00",
		// "0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		// sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5,
				1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5,
				1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0, Long.parseLong(lineId2), 0.0, 2299.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RELEASE_RTO_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, lineId, paymentPlanExecutionStatus_id, releaseMessage, OrderReleaseId, returnMessage,
				ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerMultiQtyRelRTOHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN",
				"RELEASE_RTO_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "689700", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyRelLost(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5,
				1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "L", "Lost",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "L", "Lost", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------------Return
		// ------------------------------------------------

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				OrderReleaseId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyCanRetryCan(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		// sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId,
		// store_order_id, "2299.00", "2299.00", "0.00", "0.00",
		// "0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		// sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId,
		// store_order_id, "2299.00", "2299.00", "0.00", "0.00",
		// "0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerMultiQtyCanRetryCanHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
	}

	@DataProvider
	public static Object[][] SPS_VectorMultiQtyCanRetryCan(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "2299.00",
				"3", "6897.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 3, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorMultiQtyCanRetryCanHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");

		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
	}

	@DataProvider
	public static Object[][] SPS_SellerMultiQtyReleaseRetry(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdReturn = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId2), 1, 5,
				1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		// --
		// -------------------------Release------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		String releaseMessage1 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());

		// --
		// -------------------------Refund------------------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, releaseMessage, releaseMessage1, paymentPlanItemIdref, ppsIdReturn,
				returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerMultiQtyReleaseRetryHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "19",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");

	}

	@DataProvider
	public static Object[][] SPS_VectorMultiQtyRelRefundRetry(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "9296.00", "9296.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "2299.00",
				"3", "6897.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1530", "3827", "3827",
				"2399.00", "1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "929600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "929600", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 9296.0, 9296.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 3, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2299.0, 0.0, Long.parseLong(lineId1), 0.0, 2399.0, 0.0, false, 0.0, 1530,
				Long.parseLong(OrderReleaseId1), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// ---------------------Release ------------------
		String releaseMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 3, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2299.0,
				0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2399.0, 0.0,
				false, 0.0, 1530, Long.parseLong(OrderReleaseId1), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, ppsIdRefund,
				returnMessage, paymentPlanItemIdref, releaseMessage, releaseMessage1, paymentPlanItemId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorMultiQtyRelRefundRetryHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderReleaseId);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "229900", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQtyRelTryOnPKforDLAndRel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5000.00", "5000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "1000.00",
				"2", "2000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373911", "373911", "1251897",
				"1500.00", "1", "1500.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373911", "373911", "1251897",
				"1500.00", "1", "1500.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "500000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "2", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "150000", "2", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("300000", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "500000", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5000.0, 5000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "A", "Added", 3831, "ON_HAND",
				1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0, false, 0.0, 373911,
				Long.parseLong(OrderReleaseId1), 1, 3, 1251897, "A", "Added", 373911, "ON_HAND", 1500.0, 0.0,
				Long.parseLong(lineId2), 0.0, 1500.0, 0.0, false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 3,
				1251897, "A", "Added", 373911, "ON_HAND", 1500.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------Release------------------
		String releaseMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "QD", "QA Done", 3831, "ON_HAND", 1000.0,
				0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId1), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND",
				1500.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage2 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId2), 0.0, 1500.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND",
				1500.0, 0.0, ppsId, PaymentMethod.on.name());

		// String releaseMessage3 =
		// sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId),
		// store_order_id, "PK",
		// "Packed", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0,
		// Long.parseLong(lineId), 0.0, 2000.0, 0.0,
		// false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "QD",
		// "QA Done", 3831, "ON_HAND",
		// 1000.0, 0.0, ppsId, PaymentMethod.on.name());

		// String releaseMessage4 =
		// sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId),
		// store_order_id, "PK",
		// "Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0,
		// Long.parseLong(lineId1), 0.0, 1500.0, 0.0,
		// false, 0.0, 373911, Long.parseLong(OrderReleaseId1), 1, 3, 1251897,
		// "QD", "QA Done", 373911, "ON_HAND",
		// 1500.0, 0.0, ppsId, PaymentMethod.on.name());

		// String releaseMessage4 =
		// sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId),
		// store_order_id, "PK",
		// "Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0,
		// Long.parseLong(lineId2), 0.0, 1500.0, 0.0,
		// false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 3, 1251897,
		// "QD", "QA Done", 373911, "ON_HAND",
		// 1500.0, 0.0, ppsId, PaymentMethod.on.name());

		// --------------------------Cancellation
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId1, ppsId, paymentPlanItemId, paymentPlanItemId1,
				lineId, orderMessage, releaseMessage, releaseMessage1, releaseMessage2, ppsIdRefund,
				paymentPlanItemIdref, returnMessage, OrderReleaseId, OrderReleaseId2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorSellerMultiQtyRelTryOnPKforDLAndRelHelper(String OrderId, String store_order_id,
			String OrderReleaseId1, String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderReleaseId1);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "150000", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("150000", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "150000", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQtyAllRelMultiRet(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdReturn = "t5e432xa-20de-" + randomGen(4) + "-q94d-" + randomGen(4) + "dp81a" + randomGen(3) + "";
		String ppsIdReturn1 = "y5e59cea-20de-" + randomGen(4) + "-p94d-" + randomGen(4) + "dq81a" + randomGen(3) + "";
		String ppsIdReturn2 = "w5e092pa-20de-" + randomGen(4) + "-i94d-" + randomGen(4) + "dr81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanItemIdret1 = randomGen(8);
		String paymentPlanItemIdret2 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5000.00", "5000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "1500.00", "1500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "1000.00",
				"2", "2000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373911", "1251897", "1251897",
				"1500.00", "1", "1500.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373911", "1251897", "1251897",
				"1500.00", "1", "1500.00", "0.00", "0", "3", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "500000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "2", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "150000", "2", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("300000", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "500000", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5000.0, 5000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "A", "Added", 3831, "ON_HAND",
				1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0, false, 0.0, 373911,
				Long.parseLong(OrderReleaseId1), 1, 3, 1251897, "A", "Added", 373911, "ON_HAND", 1500.0, 0.0,
				Long.parseLong(lineId2), 0.0, 1500.0, 0.0, false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 3,
				1251897, "A", "Added", 373911, "ON_HAND", 1500.0, 0.0, ppsId, PaymentMethod.on.name());
		// ------------------Release------------------
		String releaseMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "QD", "QA Done", 3831, "ON_HAND", 1000.0,
				0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage4 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 1500.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId1), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND",
				1500.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage5 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1500.0, 1500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId2), 0.0, 1500.0, 0.0,
				false, 0.0, 373911, Long.parseLong(OrderReleaseId2), 1, 3, 1251897, "QD", "QA Done", 373911, "ON_HAND",
				1500.0, 0.0, ppsId, PaymentMethod.on.name());
		// -----------------Return_refund ------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdReturn1, OrderReleaseId, refundType);
		String returnMessage2 = sellerHelper.RefundMessageCreation(ppsIdReturn2, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, lineId, orderMessage,
				releaseMessage, releaseMessage4, releaseMessage5, returnMessage, returnMessage1, returnMessage2,
				ppsIdReturn, ppsIdReturn1, ppsIdReturn2, paymentPlanItemIdret, paymentPlanItemIdret1,
				paymentPlanItemIdret2, OrderReleaseId1, OrderReleaseId2, lineId1, lineId2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorSellerMultiQtyAllRelMultiRetHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String OrderReleaseId1, String OrderReleaseId2, String ppsId, String ppsIdReturn,
			String ppsIdReturn1, String ppsIdReturn2, String paymentPlanItemIdret, String paymentPlanItemIdret1,
			String paymentPlanItemIdret2, String lineId, String lineId1, String lineId2) throws SQLException {
		// sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn,
		// "return_"+OrderReleaseId);
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdReturn, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"100000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "100000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "100000", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn1, "return_" + OrderReleaseId);
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId1, ppsIdReturn1, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn1, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"100000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn1, paymentPlanItemIdret1, "SKU", "100000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdret1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn1, "100000", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn2, "return_" + OrderReleaseId2);
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId2, ppsIdReturn2, "2.30");
		sellerHelper.InsertPaymentPlan(ppsIdReturn2, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn2, paymentPlanItemIdret2, "SKU", "150000", "1", "3", "1251897");
		sellerHelper.InsertPaymentPlanItemInstrument("150000", paymentPlanItemIdret2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn2, "150000", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorQty1Exchange(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(13);
		String store_order_id_ex = randomGen(13);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseIdex = randomGen(10);
		String lineId = randomGen(9);
		String lineIdex = randomGen(9);
		String ppsId = "b12v6cb4-4e61-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdex = "x17r6cb4-4e95-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexChild = "v45u6cb4-4e71-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdex = randomGen(8);
		String paymentPlanItemIdex1 = paymentPlanItemIdex + 1;
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 3831, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 3831,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String[] arr1 = { OrderId, ppsId, paymentPlanItemId, orderMessage, releaseMessage, OrderIdex, lineIdex, ppsIdex,
				paymentPlanItemIdex, paymentPlanItemIdex1, ppsIdexChild, store_order_id, store_order_id_ex,
				OrderReleaseIdex, OrderReleaseId, lineId, parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1ExchangeHelper(String OrderId, String store_order_id_ex, String OrderReleaseId,
			String OrderReleaseIdex, String lineId, String ppsId, String OrderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex, String paymentPlanItemIdex1, String ppsIdexChild)
			throws SQLException, NumberFormatException, JAXBException {

		sellerHelper.InsertOrder(OrderIdex, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "799.00", "799.00", "0.0", "36",
				OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "1530", "3832", "3832",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", lineId, "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);

		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex1, "EXCHANGED_SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1, "EXCHANGE");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "79900", "EXCHANGE");

	}

	@DataProvider
	public static Object[][] SPS_VectorQty1ExchangeCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseIdex = OrderIdex;
		String lineId = randomGen(9);
		String lineIdex = randomGen(9);
		String ppsId = "b12v6cb4-4e61-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdex = "x17r6cb4-4e95-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexChild = "v45u6cb4-4e71-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "j95u6cb9-1e51-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdex = randomGen(8);
		String paymentPlanItemIdex1 = paymentPlanItemIdex + 1;
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 3831, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 3831,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderId,
				"LINE_CANCELLATION_REFUND");

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, orderMessage,
				releaseMessage, OrderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex,
				paymentPlanItemIdex1, ppsIdexChild, ppsIdCancellation, paymentPlanItemIdref, cancellationMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorQty1ExchangeCancelHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String store_order_id_ex, String lineId, String ppsId, String OrderIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex, String paymentPlanItemIdex1, String ppsIdexChild) throws SQLException {

		sellerHelper.InsertOrder(OrderIdex, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderIdex, OrderIdex, store_order_id_ex, "799.00", "799.00", "0.0", "36",
				OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderIdex, store_order_id_ex, "1530", "3832", "3832",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", lineId, "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);

		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex1, "EXCHANGED_SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1, "EXCHANGE");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "79900", "EXCHANGE");

	}

	public static void SPS_VectorQty1ExchangeCancelHelper1(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "799.00", "WP");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_VectorSellerMultiQtyExchangeCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseIdex = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineIdex = randomGen(9);
		String ppsId = "b12v6cb4-4e61-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdex = "x17r6cb4-4e95-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexChild = "v45u6cb4-4e71-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "j95u6cb9-1e51-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdex = randomGen(8);
		String paymentPlanItemIdex1 = paymentPlanItemIdex + 1;
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3897.00", "3897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "1598.00", "1598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"2", "1598.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "389700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "159800", "2", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("159800", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "389700", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3897.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1598.0,
				0.0, false, 0.0, 3831, Long.parseLong(OrderReleaseId), 2, 19, 3831, "A", "Added", 1530, "ON_HAND",
				799.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0, 373908,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------Release -------------------------
		String releaseMessage = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 1598.0, 1598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1598.0, 0.0,
				false, 0.0, 3831, Long.parseLong(OrderReleaseId), 2, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());
		String releaseMessage1 = sellerHelper.OMSMessageCreationReleaseID(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId1), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String[] arr1 = { OrderId, ppsId, paymentPlanItemId, orderMessage, releaseMessage, OrderIdex, lineId, lineId1,
				lineIdex, ppsIdex, paymentPlanItemIdex, paymentPlanItemIdex1, ppsIdexChild, ppsIdCancellation,
				paymentPlanItemIdref, cancellationMessage, releaseMessage1, paymentPlanItemId1, store_order_id,
				store_order_id_ex, OrderReleaseId, OrderReleaseId1, OrderReleaseIdex, parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_VectorSellerMultiQtyExchangeCancelHelper(String OrderId, String store_order_id,
			String orderReleaseId, String OrderReleaseIdex, String store_order_id_ex, String lineId, String ppsId,
			String OrderIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex, String paymentPlanItemIdex1,
			String ppsIdexChild) throws SQLException {

		sellerHelper.InsertOrder(OrderIdex, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "799.00", "799.00", "0.0", "36",
				orderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "1530", "3832", "3832",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", lineId, "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);

		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex1, "EXCHANGED_SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1, "EXCHANGE");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "79900", "EXCHANGE");

	}

	public static void SPS_VectorSellerMultiQtyExchangeCancelHelper1(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "LINE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrdersRelease(OrderReleaseId, "799.00", "WP");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptQty1CODOrderPKandCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "can12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "239900", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String cancallationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				RefundType.RELEASE_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId,
				releaseMessage, ppsIdCancellation, paymentPlanItemIdCancel, cancallationMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptQty1CODOrderPKandCancel_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239900", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// //-----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_GF_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "6", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "139900", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// //-----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, cancellationMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptCOD_GiftCard_Qty_1_PK_Cancel_Helper(String OrderId, String store_order_id,
			String lineId, String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND_" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "6", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "139900", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementCOD_LP_Qty1PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "7");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_LP_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "7", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "139900", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "7");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementCOD_LP_Qty1_Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "7");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RELEASE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, OrderReleaseId,
				paymentPlanItemIdref, ppsIdCancellation, cancellationMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementCOD_LP_Qty1_CancelHelper(String OrderId, String store_order_id,
			String lineId, String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND_" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "7", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "139900", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "7");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementCOD_Wallet_Qty1PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "D", "Delivered", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				releaseMessage, OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Wallet_OrderWithSellerQty1ReturnHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("139900",paymentPlanItemIdref,
		// "5","System");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref, "10", "System");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation,
		// "139900", "REFUND","5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239900", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_COD_Wallet_GCQty1_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("30000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "30000", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "D", "Delivered", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				releaseMessage, OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Wallet_GCReturnHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "10", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("30000", paymentPlanItemIdref, "6", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "30000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_COD_Wallet_GCQty1_Return1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("30000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "30000", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "D", "Delivered", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				releaseMessage, OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Wallet_GCReturnHelper1(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");

		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemIdref, "10", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("30000", paymentPlanItemIdref, "6", "System");

		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200000", "REFUND", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "30000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_COD_Wallet_GCQty1_Packed_Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("39900", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "39900", "DEBIT", "6");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// String releaseMessage =
		// sellerHelper.OMSMessageCreation(Long.parseLong(OrderId),
		// store_order_id, "DL",
		// "Delivered", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0,
		// Long.parseLong(lineId), 0.0, 2399.0, 0.0,
		// false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "D",
		// "Delivered", 1531, "ON_HAND",
		// 2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RELEASE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, cancellationMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_COD_Wallet_GCQty1_Packed_Cancel_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("39900", paymentPlanItemIdref, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "100000", "REFUND", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "39900", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_COD_WalletAmountLessThan1_Qty1PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "239900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("239850", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "239850", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0,
				false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "D", "Delivered", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage,
				releaseMessage, OrderReleaseId, paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Wallet_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("239850", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("50", paymentPlanItemIdref, "10", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239850", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCODMultiSkuMultiQtyPKandReturn1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "399600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "2", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "99900", "2", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "399600", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "A", "Added", 346108, "JUST_IN_TIME", 999.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "QD", "QA Done", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "QD", "QA Done", 346108, "JUST_IN_TIME", 999.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1,
				orderMessage, releaseFundMessage, lineId, ppsIdCancellation, paymentPlanItemIdref, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptCODMultiSkuMultiQtyPKandReturn1Helper(String OrderId,
			String store_order_id, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "99900", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "99900", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCODMultiQty_Diff_Releases_PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1499.00", "1499.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "500.00", "500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "1", "999.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346108", "1155916", "1152954",
				"500.00", "1", "500.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "149900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "50000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "149900", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1499.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1499.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 500.0, 0.0, Long.parseLong(lineId1), 0.0, 500.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId1), 1, 19, 1152954, "A", "Added", 346108, "JUST_IN_TIME", 500.0, 0.0,
				ppsId, PaymentMethod.cod.name());
		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 999.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 999.0, 0.0, false,
				0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "JUST_IN_TIME",
				999.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseFundMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 500.0, 500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 500.0, 0.0, false,
				0.0, 1155916, Long.parseLong(OrderReleaseId1), 1, 19, 1152954, "QD", "QA Done", 346108, "JUST_IN_TIME",
				500.0, 0.0, ppsId, PaymentMethod.cod.name());

		//
		// // -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId1, ppsId, paymentPlanItemId, paymentPlanItemId1,
				orderMessage, releaseFundMessage, ppsIdCancellation, paymentPlanItemIdref, OrderReleaseId,
				paymentPlanItemIdref1, returnMessage, releaseFundMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCODOrderWithMultyQtyDiffReleasesAndReturnBoth(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref1)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "50000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref1, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptOnlineMultiQty_Diff_Releases_PK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1499.00", "1499.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "999.00", "999.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "500.00", "500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "1", "999.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346108", "1155916", "1152954",
				"500.00", "1", "500.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "149900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "50000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "149900", "DEBIT");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1499.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1499.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 500.0, 0.0, Long.parseLong(lineId1), 0.0, 500.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId1), 1, 19, 1152954, "A", "Added", 346108, "JUST_IN_TIME", 500.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 999.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 999.0, 0.0, false,
				0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "JUST_IN_TIME",
				999.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseFundMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 500.0, 500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 500.0, 0.0, false,
				0.0, 1155916, Long.parseLong(OrderReleaseId1), 1, 19, 1152954, "QD", "QA Done", 346108, "JUST_IN_TIME",
				500.0, 0.0, ppsId, PaymentMethod.on.name());

		//
		// // -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, OrderReleaseId1, lineId, lineId1, ppsId,
				paymentPlanItemId, paymentPlanItemId1, orderMessage, releaseFundMessage, ppsIdCancellation,
				paymentPlanItemIdref, paymentPlanItemIdref1, returnMessage, releaseFundMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createOnlineOrderWithMultyQtyDiffReleasesAndReturnBoth(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref1)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "50000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCOD_GiftCard_MultiSkuMultiQtyPK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"ON_HAND", 1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "A", "Added", 346108, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "D", "Delivered", 346107, "ON_HAND", 1000.0,
				0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916, Long.parseLong(OrderReleaseId), 1,
				19, 1152954, "QD", "QA Done", 346108, "ON_HAND", 1000.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, orderMessage,
				packedMessage, lineId, ppsIdCancellation, paymentPlanItemIdref, OrderReleaseId, returnMessage,
				paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_GF_OrderWithSellerMultiQtyReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "100000", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "100000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemIdref, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemIdref1, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref1, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200000", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Return_1(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdReturn = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanItemIdret1 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"ON_HAND", 1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "A", "Added", 346108, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1000.0, 1000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND", 1000.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.LINE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		// -----------------Refund only 2 ------------------------------
		String refundType1 = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType1);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, orderMessage,
				packedMessage, lineId, cancellationMessage, ppsIdCancellation, paymentPlanItemIdref, OrderReleaseId,
				returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref)
			throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "100000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "90000", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "10000", "REFUND", "6");

	}

	public static void SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Return_1_Helper(String OrderId,
			String store_order_id, String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn)
			throws SQLException {
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"100000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdret, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemIdret, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "80000", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "20000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Cancel1(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation1 = "fc3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "1000.00", "1000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "1000.00", "1000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346108", "1155916", "1152954",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemId, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"ON_HAND", 1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId1), 1, 19, 1152954, "A", "Added", 346108, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1000.0, 1000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND", 1000.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.LINE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId1, refundType);

		// -----------------Refund only 2 ------------------------------
		String refundType1 = RefundType.RELEASE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage1 = sellerHelper.RefundMessageCreation(ppsIdCancellation1, OrderReleaseId,
				refundType1);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, orderMessage,
				packedMessage, lineId, lineId1, cancellationMessage, ppsIdCancellation, paymentPlanItemIdref,
				OrderReleaseId, OrderReleaseId1, cancellationMessage1, ppsIdCancellation1, paymentPlanItemIdCancel };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptCOD_GiftCard_MultiSku_Cancel_1_PK_1_Cancel_1_Helper(String OrderId,
			String store_order_id, String OrderReleaseId, String lineId, String ppsId, String ppsIdCancellation1,
			String paymentPlanItemIdCancel) throws SQLException {
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation1, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation1, paymentPlanItemIdCancel, "SKU", "100000", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdCancel, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemIdCancel, "6", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "80000", "REFUND", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "20000", "REFUND", "6");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementCOD_LP_MultiSkuMultiQtyPK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemId, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"ON_HAND", 1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "A", "Added", 346108, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND", 1000.0,
				0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916, Long.parseLong(OrderReleaseId), 1,
				19, 1152954, "QD", "QA Done", 346108, "ON_HAND", 1000.0, 0.0, ppsId, PaymentMethod.cod.name());

		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "D", "Delivered", 346107,
				"ON_HAND", 1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "D", "Delivered", 346108, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, orderMessage,
				packedMessage, releaseFundMessage, lineId, ppsIdCancellation, paymentPlanItemIdref, OrderReleaseId,
				paymentPlanItemIdref1, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_LP_OrderWithSellerMultiQtyReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "100000", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "100000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdref, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemIdref, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemIdref1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref1, "7", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200000", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementCOD_Wallet_MultiSkuMultiQtyPK_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemId1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "A", "Added", 346107,
				"ON_HAND", 2000.0, 0.0, Long.parseLong(lineId1), 0.0, 2000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "A", "Added", 346108, "ON_HAND", 2000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0, 0.0, false, 0.0,
				1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND", 2000.0,
				0.0, Long.parseLong(lineId1), 0.0, 2000.0, 0.0, false, 0.0, 1155916, Long.parseLong(OrderReleaseId), 1,
				19, 1152954, "QD", "QA Done", 346108, "ON_HAND", 2000.0, 0.0, ppsId, PaymentMethod.cod.name());

		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "D", "Delivered", 346107,
				"ON_HAND", 2000.0, 0.0, Long.parseLong(lineId1), 0.0, 2000.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 1, 19, 1152954, "D", "Delivered", 346108, "ON_HAND", 2000.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1, orderMessage,
				packedMessage, releaseFundMessage, lineId, ppsIdCancellation, paymentPlanItemIdref, OrderReleaseId,
				paymentPlanItemIdref1, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Wallet_OrderWithSellerMultiQtyReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "100000", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "100000", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdref, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("20000", paymentPlanItemIdref, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("90000", paymentPlanItemIdref1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref1, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "170000", "REFUND", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "300000", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_CodWithShippingReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "4.00", "2.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "4.00", "4.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "200", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "400", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 4.0, 2.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 4.0, 2.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, orderMessage, lineId, ppsId, paymentPlanItemId,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship, paymentPlanItemIdrefship,
				releaseMessage, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCod_Shipping_OrderQty1ReturnHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND", "8");

	}

	//

	@DataProvider
	public static Object[][] SPS_CodMultiReleaseWithShippingReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "276.00", "3.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");

		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "346107", "1152953", "1152953",
				"273.00", "1", "276.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",

				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27800", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 275.0, 278.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 19,
				1152953, "A", "Added", 346107, "ON_HAND", 2.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 3827,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 276.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0, false,
				0.0, 1152953, Long.parseLong(OrderReleaseId1), 1, 19, 1152953, "QD", "QA Done", 346107, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, lineId, ppsId,
				paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				paymentPlanItemIdrefship, releaseMessage, returnMessage, releaseMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_Cod_WalletMultiReleaseWithShippingReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "273.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "895356", "3831",
				"273.00", "1", "276.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "21", "3831");
		// sellerHelper.InsertPaymentPlanItem(ppsId,paymentPlanItemIdship,"SHIPPING_CHARGES","300","1","73","PPS_9999");
		// sellerHelper.InsertPaymentPlanItemInstrument("200",paymentPlanItemId,"5","System");
		// sellerHelper.InsertPaymentPlanItemInstrument("27300",paymentPlanItemId1,"10","System");
		// sellerHelper.InsertPaymentPlanItemInstrument("300",paymentPlanItemIdship,"10","System");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200",
		// "DEBIT","5");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27600",
		// "DEBIT","10");

		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "10", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1, "5", "System");
		// sellerHelper.InsertPaymentPlanItemInstrument("300",paymentPlanItemIdship,"5","System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT", "10");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27300", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 278.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 895356, Long.parseLong(OrderReleaseId), 1, 21,
				3831, "A", "Added", 1531, "ON_HAND", 273.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0, false,
				0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "QD", "QA Done", 1531, "ON_HAND", 273.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String deliverMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0,
				false, 0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "D", "Delivered", 1531, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, lineId, ppsId,
				paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				paymentPlanItemIdrefship, releaseMessage, returnMessage, releaseMessage1, deliverMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_CodWithDifferentSellersMultiReleaseWithShippingReleaseReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdLineCancellation = "m76f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "5.00", "3.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "273.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "895356", "3831",
				"273.00", "1", "273.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "21", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27800", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 275.0, 278.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 895356, Long.parseLong(OrderReleaseId), 1, 21,
				3831, "A", "Added", 1531, "ON_HAND", 273.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 5.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0, false,
				0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "QD", "QA Done", 1531, "ON_HAND", 273.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String deliverMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 273.0, 0.0,
				false, 0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "D", "Delivered", 1531, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, lineId, ppsId,
				paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				paymentPlanItemIdrefship, releaseMessage, returnMessage, releaseMessage1, deliverMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	//

	@DataProvider
	public static Object[][] SPS_CodWithDifferentSellersMultiRelease1CancelSkuWithShippingChrgs(
			ITestContext testContext) throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);

		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdLineCancellation = "m76f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdship = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdrefundVatRefund = "vae432ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIdrefship = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanItemIVatRefund = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "275.00", "278.00", "3.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "5.00", "3.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "273.00", "273.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "4852", "3827", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "895356", "3831",
				"273.00", "1", "273.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "27800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "27300", "1", "21", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "300", "1", "19",
				"PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("27300", paymentPlanItemId1, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "27800", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 275.0, 278.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 4852, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 273.0, 0.0, false, 0.0, 895356, Long.parseLong(OrderReleaseId), 1, 21,
				3831, "A", "Added", 1531, "ON_HAND", 273.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 5.0, 3.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 4852,
				Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 273.0, 0.0, false,
				0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "QD", "QA Done", 1531, "ON_HAND", 273.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String deliverMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 273.0, 273.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 273.0, 0.0,
				false, 0.0, 895356, Long.parseLong(OrderReleaseId1), 1, 21, 3831, "D", "Delivered", 1531, "ON_HAND",
				273.0, 0.0, ppsId, PaymentMethod.cod.name());

		// ------------------------------------ Cancellation -------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdLineCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, orderMessage, lineId, ppsId, paymentPlanItemId, ppsIdCancellation,
				paymentPlanItemIdref, paymentPlanItemIdship, paymentPlanItemIdrefship, releaseMessage, returnMessage,
				releaseMessage1, deliverMessage1, cancellationMessage, ppsIdLineCancellation, paymentPlanItemIdCancel,
				OrderReleaseId, OrderReleaseId1, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void vatrefund() {
		// sellerHelper.insertOrderLineAdditionalInfo1(lineId,
		// ppsIdrefundVatRefund, "2.34", "150",
		// "VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		// sellerHelper.InsertPaymentPlan(ppsIdrefundVatRefund, store_order_id,
		// "CANCELLATION",
		// "VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null",
		// "Null");
		// sellerHelper.InsertPaymentPlanItem(ppsIdrefundVatRefund,
		// paymentPlanItemIVatRefund, "VAT_ADJ_REFUND", "15000",
		// "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("10000",
		// paymentPlanItemIVatRefund, "5", "CREDIT", "System");
		// sellerHelper.InsertPaymentPlanItemInstrument("5000",
		// paymentPlanItemIVatRefund, "10", "CREDIT", "System");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefundVatRefund,
		// "15000", "CREDIT");
		//
		// String refundTypeVatAdjustment =
		// RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		// String refundMessageVatAdjustment =
		// sellerHelper.RefundMessageCreation(ppsIdrefundVatRefund,
		// OrderReleaseId,
		// refundTypeVatAdjustment);

	}

	public static void SPS_CodWithDifferentSellersMultiRelease1CancelSkuWithShippingChrgsLineCancellation(
			String lineId1, String OrderId, String store_order_id, String OrderReleaseId, String ppsId,
			String ppsIdCancellation, String paymentPlanItemIdref, String paymentPlanItemIdref1) throws Exception {

		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId1, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId1, ppsIdCancellation, "5.00");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SHIPPING_CHARGES", "300", "1",
				"5", "PPS_9999");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "8", "REFUND", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("300", paymentPlanItemIdref1, "8	", "REFUND", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "500", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_CodQty1SDDBreach(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(9);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdSkuCancellation = "x7e422ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "242400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_999");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "242400", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2399.0, 0.0, false, 0.0, 3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------------Cancellation
		// --------------------------------------------------------
		String refundType = RefundType.EXPRESS_CHARGES_REFUND.name().toString();
		String refundType1 = RefundType.RETURN_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String refundMessage1 = sellerHelper.RefundMessageCreation(ppsIdSkuCancellation, OrderReleaseId, refundType1);
		String[] arr1 = { orderMessage, releaseMessage, refundMessage, OrderId, OrderReleaseId, store_order_id, lineId,
				ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				refundMessage1, ppsIdSkuCancellation, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_CodQty1SDDBreachAfterReleasePK_Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(9);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdSkuCancellation = "x7e422ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "242400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_999");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "242400", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2399.0, 0.0, false, 0.0, 3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------------Cancellation
		// --------------------------------------------------------
		String refundType = RefundType.EXPRESS_CHARGES_REFUND.name().toString();
		String refundType1 = RefundType.RELEASE_CANCELLATION_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String skuCancellationMessage = sellerHelper.RefundMessageCreation(ppsIdSkuCancellation, OrderReleaseId,
				refundType1);
		String[] arr1 = { orderMessage, releaseMessage, refundMessage, OrderId, OrderReleaseId, store_order_id, lineId,
				ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				skuCancellationMessage, ppsIdSkuCancellation, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodQty1SDDBreachAfterReleasePK_CancelHelper(String OrderId, String store_order_id,
			String ppsId, String ppsIdSkuCancellation, String paymentPlanItemIdref1) throws SQLException {

		sellerHelper.InsertPaymentPlan(ppsIdSkuCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdSkuCancellation, paymentPlanItemIdref1, "SKU", "239900", "1", "19",
				"3827");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref1, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdSkuCancellation, "239900", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_CodQty1SDDBreachBeforeRelease(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12x6cb4-9e83-" + randomGen(4) + "-a65d-" + randomGen(4) + "df47a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdship = randomGen(9);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdrefundVatRefund = "vam412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdSkuCancellation = "x7e422ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIVatRefund = randomGen(8);

		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2424.00", "25.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3827", "3827", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "242400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemIdship, "SHIPPING_CHARGES", "2500", "1", "19",
				"PPS_999");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdship, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "242400", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0,
				2399.0, 0.0, false, 0.0, 3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "A", "Added", 1530,
				"ON_HAND", 2399.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------------Release------------------------------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2424.0, 25.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				3827, Long.parseLong(OrderReleaseId), 1, 19, 3827, "QD", "QA Done", 1530, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------------Cancellation
		// --------------------------------------------------------
		String refundType = RefundType.EXPRESS_CHARGES_REFUND.name().toString();
		String refundType1 = RefundType.RETURN_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String refundMessage1 = sellerHelper.RefundMessageCreation(ppsIdSkuCancellation, OrderReleaseId, refundType1);

		// ------------------------------------ vat refund -------------------

		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdrefundVatRefund, "2.34", "150",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3827);
		sellerHelper.InsertPaymentPlan(ppsIdrefundVatRefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefundVatRefund, paymentPlanItemIVatRefund, "VAT_ADJ_REFUND", "15000",
				"1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIVatRefund, "5", "CREDIT", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIVatRefund, "10", "CREDIT", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefundVatRefund, "15000", "CREDIT");

		String refundTypeVatAdjustment = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessageVatAdjustment = sellerHelper.RefundMessageCreation(ppsIdrefundVatRefund, OrderReleaseId,
				refundTypeVatAdjustment);

		String[] arr1 = { orderMessage, releaseMessage, refundMessage, OrderId, OrderReleaseId, store_order_id, lineId,
				ppsId, paymentPlanItemId, ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdship,
				refundMessage1, ppsIdSkuCancellation, paymentPlanItemIdref1, refundMessageVatAdjustment,
				ppsIdrefundVatRefund };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodQty1SDDBreachHelper(String OrderId, String store_order_id, String ppsId,
			String ppsIdCancellation, String paymentPlanItemIdref) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "EXPRESS_REFUND_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"EXPRESS_CHARGES_REFUND" + store_order_id + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SHIPPING_CHARGES", "2500", "1",
				"19", "PPS_999");
		sellerHelper.InsertPaymentPlanItemInstrument("2500", paymentPlanItemIdref, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "2500", "REFUND", "5");

	}

	public static void SPS_CodQty1SDDBreachSkuReturnHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "239900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "239900", "1", "19", "3827");
		sellerHelper.InsertPaymentPlanItemInstrument("239900", paymentPlanItemIdref, "8", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "239900", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_CodQty1PriceOverRide(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdrefund = "c3w" + randomGen(3) + "ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a"
				+ randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund, "799.00", "100.00");
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// --------------------price Mismatch refund
		// ----------------------------------
		sellerHelper.insertOrderLineAdditionalInfo(lineId, ppsIdrefund, "1", "100");

		sellerHelper.InsertPaymentPlan(ppsIdrefund, OrderId, "CANCELLATION", "PRICE_OVERRIDE_REFUND" + lineId + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefund, paymentPlanItemIdref, "PARTIAL_REFUND", "10000", "1", "19",
				"3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdref, "5", "system");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefund, "10000", "REFUND", "5");

		String refundType = RefundType.PRICE_OVERRIDE_REFUND.name().toString();
		String refundMessage = sellerHelper.RefundMessageCreation(ppsIdrefund, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, ppsId, paymentPlanItemId, orderMessage, OrderReleaseId, lineId, refundMessage,
				paymentPlanItemIdref, ppsIdrefund, releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	// order placed with cod , refund amt to myntcredit
	@DataProvider
	public static Object[][] SPS_CodQty1ReleaseAndReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		// String store_order_id = randomGen(10)+randomGen(10)+randomGen(3);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND", 2.0,
				0.0, ppsId, PaymentMethod.cod.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.cod.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCodOrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertOrderReleaseAdditionalInfo(OrderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "16", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND", "16");

	}

	@DataProvider
	public static Object[][] SPS_CodQty1ReleaseAndReturnInCBandLP(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "500.00", "500.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "500.00", "500.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"500.00", "1", "500.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "50000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "50000", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 500.0, 500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 500.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				500.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 500.0, 500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 500.0, 0.0, false,
				0.0, 373908, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 500.0,
				0.0, ppsId, PaymentMethod.cod.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodQty1ReleaseAndReturnInCBandLPHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.updateOrderReleaseDeliveredOn(OrderReleaseId);
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertOrderReleaseAdditionalInfo(OrderReleaseId, ppsId, "PAYMENT_PPS_ID");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "50000", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "10", "System");
		// sellerHelper.InsertPaymentPlanItemInstrument("200",paymentPlanItemIdref,
		// "7","System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "10");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation,
		// "200", "REFUND","7");

	}

	@DataProvider
	public static Object[][] SPS_walletToWalletReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1530", "3831", "3831", "2.00",
				"1", "2.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "10", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 3831, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1530, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0,
				3831, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1530, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.on.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void walletToWalletReturnHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "10", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND", "10");

	}

	@DataProvider
	public static Object[][] SPS_CodQty2ReleaseAndReturn(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "800.00", "800.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "800.00", "800.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"800.00", "2", "800.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "80000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "40000", "2", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "80000", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 800.0, 800.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 800.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				400.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 800.0, 800.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 800.0, 0.0, false,
				0.0, 373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 400.0,
				0.0, ppsId, PaymentMethod.cod.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCodOrderWithSellerQty2ReleaseHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "80000", "2", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemIdref, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "80000", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_CodQty2ReleaseAndReturnSingle(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "800.00", "800.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "800.00", "800.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"800.00", "2", "800.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "80000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "40000", "2", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("80000", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "80000", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 800.0, 800.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 800.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				400.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 800.0, 800.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 800.0, 0.0, false,
				0.0, 373908, Long.parseLong(OrderReleaseId), 2, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 400.0,
				0.0, ppsId, PaymentMethod.cod.name());
		// -----------------------------------refund----------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref, "5" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCodOrderWithSellerQty2Return1Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "40000", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("40000", paymentPlanItemIdref, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "40000", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_CodMultiQtyRelRTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "x9z402wq-27de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT", "5");
		sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId1, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.cod.name());
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0, 0.0,
				false, 0.0, 373908, Long.parseLong(OrderReleaseId), 3, 5, 1251881, "D", "Delivered", 373908, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.cod.name());
		// ------------------------Return
		// ------------------------------------------------
		String refundType = RefundType.RELEASE_RTO_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);
		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, lineId, paymentPlanExecutionStatus_id, releaseMessage, OrderReleaseId, OrderReleaseId1,
				returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodMultiQtyRelRTOHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String paymentPlanItemIdret, String ppsId, String ppsIdReturn) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, OrderId, "RETURN", "RELEASE_RTO_REFUND" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "229900", "3", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemIdret, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "689700", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_CodTryAndBuy2(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		// String ppsIdCancellation1 =
		// "k3e412yh-92te-"+randomGen(4)+"-o65d-"+randomGen(4)+"lh30a"+randomGen(3)+"";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4.00", "4.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "1", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1532", "3835", "3835", "2.00",
				"2", "4.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "400", "2", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("400", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "400", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0,
				false, 0.0, 1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "A", "Added", 1532, "ON_HAND", 2.0, 0.0,
				ppsId, PaymentMethod.cod.name());
		// ------------------Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 4.0, 4.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 4.0, 0.0, false, 0.0,
				1532, Long.parseLong(OrderReleaseId), 2, 19, 3835, "QD", "QA Done", 1532, "ON_HAND", 2.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// -----------------------------------refund----------
		String refundType = RefundType.TRY_AND_BUY_REFUND.name().toString();
		// String refundType = "TRY_AND_BUY_REFUND";
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		// String returnMessage1 =
		// sellerHelper.RefundMessageCreation(ppsIdCancellation1, refundType);
		String[] arr1 = { orderMessage, releaseFundMessage, returnMessage, OrderId, store_order_id, OrderReleaseId,
				lineId, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void createCodOrderWithVectorQty2ReleaseHelper1(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref1) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "200", "1", "19", "3835");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemIdref1, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "200", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_SellerSettlementRptCODMultiQty_LineCancel_2_Pack_1_Return_1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdReturn = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3);
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdret = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2997.00", "2997.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2997.00", "2997.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "3", "2997.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "299700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "3", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("299700", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "299700", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2997.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2997.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 3, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 999.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				999.0, 999.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 999.0, 0.0, false, 0.0,
				1152953, Long.parseLong(OrderReleaseId), 1, 19, 1152953, "QD", "QA Done", 346107, "JUST_IN_TIME", 999.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// ------------------------------------ Cancellation -------------------

		sellerHelper.InsertOrderLineCancellation(lineId1, OrderId, OrderReleaseId, store_order_id, "346107", "1155915",
				"1152953", "999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "199800", "2", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "199800", "REFUND");
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");

		// -----------------Refund only 2 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1,
				orderMessage, releaseMessage, lineId, ppsIdCancellation, paymentPlanItemIdref, cancellationMessage,
				returnMessage, ppsIdReturn, paymentPlanItemIdret };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_SellerSettlementRptCODMultiQty_LineCancel_2_Pack_1_Return_1_Helper(String OrderId,
			String store_order_id, String OrderReleaseId, String paymentPlanItemIdret, String ppsId, String ppsIdReturn)
			throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "99900", "1", "19", "1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdret, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "99900", "REFUND", "8");

	}

	@DataProvider

	public static Object[][] SPS_CodVectorQty1Exchange(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException

	{
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(13);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseIdex = randomGen(10);
		String lineId = randomGen(9);
		String lineIdex = randomGen(9);
		String ppsId = "b12v6cb4-4e61-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdex = "x17r6cb4-4e95-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexChild = "v45u6cb4-4e71-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdex = randomGen(8);
		String paymentPlanItemIdex1 = paymentPlanItemIdex + 1;
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "5", "System");
		// sellerHelper.InsertPaymentPlanItemInstrument("10000",paymentPlanItemId,"0","System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 3831, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0, 3831,
				Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, orderMessage,
				releaseMessage, OrderIdex, store_order_id_ex, OrderReleaseIdex, lineIdex, ppsIdex, paymentPlanItemIdex,
				paymentPlanItemIdex1, ppsIdexChild, parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodVectorQty1ExchangeHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String OrderIdex, String store_order_id_ex, String OrderReleaseIdex,
			String lineIdex, String ppsIdex, String paymentPlanItemIdex, String paymentPlanItemIdex1,
			String ppsIdexChild) throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "799.00", "799.00", "0.0", "36",
				OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "1531", "3832", "3832",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", lineId, "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);
		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "SKU", "79900", "1", "19", "3832");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex1, "EXCHANGED_SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1, "5", "EXCHANGE", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "79900", "EXCHANGE", "5");

	}

	@DataProvider
	public static Object[][] SPS_Online_MultiSku_MultiQty_SingleReleaseAndRefundAll(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String ppsIdRefund1 = "zde012ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String ppsIdRefund2 = "r0k492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIdref2 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3000.00", "3000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3000.00", "3000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "1000.00",
				"2", "2000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1531", "3833", "3833",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "300000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "2", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "3833");
		sellerHelper.InsertPaymentPlanItemInstrument("200000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "300000", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3000.0, 3000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "A", "Added", 3831, "ON_HAND",
				1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3833, "A", "Added", 3833, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// ------------------Release------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				3000.0, 3000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2000.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 2, 19, 3831, "QD", "QA Done", 3831, "ON_HAND", 1000.0, 0.0,
				Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19,
				3833, "QD", "QA Done", 3833, "ON_HAND", 1000.0, 0.0, ppsId, PaymentMethod.on.name());

		// --------------------------Cancellation
		// ---------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdRefund1, OrderReleaseId, refundType);
		String returnMessage2 = sellerHelper.RefundMessageCreation(ppsIdRefund2, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, paymentPlanItemId1, lineId,
				orderMessage, releaseMessage, ppsIdRefund, paymentPlanItemIdref, returnMessage, returnMessage1,
				returnMessage2, ppsIdRefund1, ppsIdRefund2, paymentPlanItemIdref1, paymentPlanItemIdref2 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Online_MultiSku_MultiQty_SingleReleaseAndRefundAllHelper(String OrderId,
			String store_order_id, String OrderReleaseId1, String paymentPlanItemIdret, String ppsId,
			String ppsIdReturn, String ppsIdRefund1, String ppsIdRefund2, String paymentPlanItemIdref1,
			String paymentPlanItemIdref2) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturn, "return_" + OrderReleaseId1);
		sellerHelper.InsertPaymentPlan(ppsIdReturn, OrderId, "RETURN", "return_" + OrderId + "", ppsId, "Null", "Null",
				"Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturn, paymentPlanItemIdret, "SKU", "100000", "1", "1", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdret, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturn, "100000", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund1, "return_" + OrderReleaseId1);
		sellerHelper.InsertPaymentPlan(ppsIdRefund1, OrderId, "RETURN", "return_" + OrderId + "", ppsId, "Null", "Null",
				"Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund1, paymentPlanItemIdref1, "SKU", "100000", "1", "1", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund1, "100000", "REFUND");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRefund2, "return_" + OrderReleaseId1);
		sellerHelper.InsertPaymentPlan(ppsIdRefund2, OrderId, "RETURN", "return_" + OrderId + "", ppsId, "Null", "Null",
				"Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund2, paymentPlanItemIdref2, "SKU", "100000", "1", "1", "3833");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref2, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund2, "100000", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_Cod_MultiSku_MultiQty_SingleReleaseAndRefundAll(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12r6qw4-4e68-" + randomGen(4) + "-o05t-" + randomGen(4) + "le30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation1 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation2 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String ppsIdCancellation3 = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);
		String paymentPlanItemIdref2 = randomGen(8);
		String paymentPlanItemIdref3 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3996.00", "3996.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "20", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "346107", "1155915", "1152953",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "346108", "1155916", "1152954",
				"999.00", "2", "1998.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00",
				"JUST_IN_TIME", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "399600", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "99900", "2", "19", "1152953");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "99900", "2", "19", "1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId, "5", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("199800", paymentPlanItemId1, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "399600", "DEBIT", "5");

		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0,
				0.0, false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "A", "Added", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "A", "Added", 346108, "JUST_IN_TIME", 999.0, 0.0, ppsId,
				PaymentMethod.cod.name());
		// ------------------Return Release Fund ------------------------------
		String releaseFundMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 3996.0, 3996.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1998.0, 0.0,
				false, 0.0, 1155915, Long.parseLong(OrderReleaseId), 2, 19, 1152953, "QD", "QA Done", 346107,
				"JUST_IN_TIME", 999.0, 0.0, Long.parseLong(lineId1), 0.0, 1998.0, 0.0, false, 0.0, 1155916,
				Long.parseLong(OrderReleaseId), 2, 19, 1152954, "QD", "QA Done", 346108, "JUST_IN_TIME", 999.0, 0.0,
				ppsId, PaymentMethod.cod.name());
		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();

		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);
		String returnMessage1 = sellerHelper.RefundMessageCreation(ppsIdCancellation1, OrderReleaseId, refundType);
		String returnMessage2 = sellerHelper.RefundMessageCreation(ppsIdCancellation2, OrderReleaseId, refundType);
		String returnMessage3 = sellerHelper.RefundMessageCreation(ppsIdCancellation3, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, ppsId, paymentPlanItemId, paymentPlanItemId1,
				orderMessage, releaseFundMessage, lineId, ppsIdCancellation, ppsIdCancellation1, ppsIdCancellation2,
				ppsIdCancellation3, paymentPlanItemIdref, paymentPlanItemIdref1, paymentPlanItemIdref2,
				paymentPlanItemIdref3, returnMessage, returnMessage1, returnMessage2, returnMessage3 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_MultiSku_MultiQty_SingleReleaseAndRefundAllHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdref, String paymentPlanItemIdref1,
			String paymentPlanItemIdref2, String paymentPlanItemIdref3, String ppsId, String ppsIdCancellation,
			String ppsIdCancellation1, String ppsIdCancellation2, String ppsIdCancellation3) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "99900", "REFUND", "5");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation1, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation1, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation1, paymentPlanItemIdref1, "SKU", "99900", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref1, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation1, "99900", "REFUND", "5");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation2, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation2, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation2, paymentPlanItemIdref2, "SKU", "99900", "1", "19",
				"1152953");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref2, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation2, "99900", "REFUND", "5");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation3, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation3, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation3, paymentPlanItemIdref3, "SKU", "99900", "1", "19",
				"1152954");
		sellerHelper.InsertPaymentPlanItemInstrument("99900", paymentPlanItemIdref3, "5", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation3, "99900", "REFUND", "5");

	}

	@DataProvider
	public static Object[][] SPS_COD_SellerQty2WithSkuPriceZero(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2.00", "1", "2.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "373900", "1251842", "1251842",
				"0.00", "1", "0.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "0", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("200", paymentPlanItemId, "5", "System");
		// sellerHelper.InsertPaymentPlanItemInstrument("0",
		// paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200", "DEBIT", "5");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0,
				false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908, "ON_HAND",
				2.0, 0.0, Long.parseLong(lineId1), 0.0, 0.0, 0.0, false, 0.0, 1251842, Long.parseLong(OrderReleaseId),
				1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 0.0, 0.0, ppsId, PaymentMethod.cod.name());

		// ------------------Return Release Fund ------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2.0, 2.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2.0, 0.0, false, 0.0, 1251881,
				Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2.0, 0.0,
				Long.parseLong(lineId1), 0.0, 0.0, 0.0, false, 0.0, 1251842, Long.parseLong(OrderReleaseId), 1, 5,
				1251842, "QD", "QA Done", 373900, "ON_HAND", 0.0, 0.0, ppsId, PaymentMethod.cod.name());

		String[] arr1 = { OrderId, OrderReleaseId, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_COD_SellerMarginCheckForSellerId5(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "1899.00", "1899.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "1899.00", "1899.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"1899.00", "1", "1899.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "189900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("189900", paymentPlanItemId, "5", "system");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "189900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 1899.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 1899.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String[] arr1 = { OrderId, OrderReleaseId, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	//

	@DataProvider
	public static Object[][] SPS_COD_SKUInventoryCountZeroForMadhura(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3999.00", "3999.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3999.00", "1899.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"1899.00", "1", "1899.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "189900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "200", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("189900", paymentPlanItemId, "5", "system");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "189900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 1899.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 1899.0, 1899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1899.0, 0.0,
				false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "D", "Delivered", 373908, "ON_HAND",
				1899.0, 0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_COD_Wallet_Qty1VatAdjustment(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";

		String ppsIdrefundVatRefund = "vte412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemIVatRefund = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1531", "3831", "2399.00",
				"1", "2399.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "239900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "239900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("139900", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "10", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "139900", "DEBIT", "5");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "100000", "DEBIT", "10");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				2399.0, 0.0, ppsId, PaymentMethod.cod.name());

		String packedMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2399.0, 2399.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2399.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		//
		// --------------------Vat Mismatch refund
		// ----------------------------------
		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdrefundVatRefund, "2.34", "150",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		sellerHelper.InsertPaymentPlan(ppsIdrefundVatRefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdrefundVatRefund, paymentPlanItemIVatRefund, "VAT_ADJ_REFUND", "15000",
				"1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIVatRefund, "5", "CREDIT", "System");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIVatRefund, "10", "CREDIT", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdrefundVatRefund, "15000", "CREDIT");

		String refundTypeVatAdjustment = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String refundMessageVatAdjustment = sellerHelper.RefundMessageCreation(ppsIdrefundVatRefund, OrderReleaseId,
				refundTypeVatAdjustment);

		String[] arr1 = { OrderId, ppsId, paymentPlanItemId, orderMessage, lineId, packedMessage, OrderReleaseId,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage, refundMessageVatAdjustment,
				ppsIdrefundVatRefund };
		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static Calendar getDate() {
		Calendar cal = Calendar.getInstance();
		return cal;

	}

	@DataProvider
	public static Object[][] getCitrusPayoutDetails(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String startDate = dateFormat.format(cal.getTime());
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = dateFormat.format(cal1.getTime());
		Object[] arr1 = { startDate, endDate, 139L, 100L, 1L, "Tech Connect Retail Pvt Ltd-(19)" };
		Object[] arr2 = { startDate, endDate, 329L, 100L, 1L, "Health & Happiness Pvt Ltd-(21)" };
		Object[] arr3 = { startDate, endDate, 394L, 100L, 1L, "Consulting Rooms Private Limited-(25)" };
		Object[] arr4 = { startDate, endDate, 498L, 100L, 1L, "Shreyash Retail Private Limited-(29)" };
		Object[] arr5 = { startDate, endDate, 57L, 100L, 1L, "TITAN COMPANY LIMITED-(16)" };
		Object[] arr6 = { startDate, endDate, 92L, 100L, 1L,
				"Aditya Birla Fashion and Retail Limited (Madura Fashion & Lifestyle)-(18)" };
		Object[] arr7 = { startDate, endDate, 274L, 100L, 1L, "Gift Card -(20)" };
		Object[] arr8 = { startDate, endDate, 1269L, 100L, 1L, "Sane Retails Private Limited -(30)" };
		Object[] arr9 = { startDate, endDate, 1398L, 100L, 1L, "Savadika Retail Private Limited -(32)" };

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7 , arr8, arr9};

		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);

	}

	@DataProvider
	public static Object[][] SPS_Funds_ReleaseOnPackedAndCancellation(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanItemIdCancel1 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251882", "1251882",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "539700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 3831,
				Long.parseLong(OrderReleaseId1), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.0, 4598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 3831, Long.parseLong(OrderReleaseId1), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_CANCELLATION_REFUND.toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				refundMessage);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, releaseMessage, releaseMessage1, cancellationMessage, ppsIdCancellation,
				paymentPlanItemIdCancel, paymentPlanItemIdCancel1, paymentPlanExecutionStatus_id, lineId1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_ReleaseOnPackedAndCancellation_Helper1(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId1, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdCancel, String paymentPlanItemIdCancel1, String paymentPlanExecutionStatus_id)
			throws SQLException {

		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		// sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId,
		// "IC");
		// sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId1,
		// "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdCancel, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdCancel1, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdCancel, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdCancel1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "459800", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_Funds_ReleaseOnPackedAndCancellation_RTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdrto = randomGen(8);
		String paymentPlanItemIdrto1 = randomGen(8);
		String ppsIdRTO = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251882", "1251882",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.0, 4598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_CANCELLATION_REFUND.toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId1,
				refundMessage);

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage1 = RefundType.RELEASE_RTO_REFUND.toString();
		String RTOMessage = sellerHelper.RefundMessageCreation(ppsIdRTO, OrderReleaseId, refundMessage1);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, releaseMessage, releaseMessage1, cancellationMessage, ppsIdCancellation,
				paymentPlanItemIdCancel, paymentPlanExecutionStatus_id, lineId1, paymentPlanItemIdrto,
				paymentPlanItemIdrto1, ppsIdRTO, RTOMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_ReleaseOnPackedAndCancellation_Helper(String OrderId, String store_order_id,
			String OrderReleaseId1, String lineId1, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {

		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId1, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId1, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseId1 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND");
	}

	public static void SPS_Funds_ReleaseOnPackedAndRTO_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdrto, String paymentPlanItemIdrto1, String ppsId,
			String ppsIdRTO) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdRTO, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdRTO, store_order_id, "RETURN", "RELEASE_RTO_REFUND" + store_order_id + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRTO, paymentPlanItemIdrto, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsIdRTO, paymentPlanItemIdrto1, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrto, "REFUND");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrto1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRTO, "459800", "REFUND");
	}

	//
	@DataProvider
	public static Object[][] SPS_Funds_Release_PackedLost_And_LostBeforePacked(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdrto = randomGen(8);
		String paymentPlanItemIdrto1 = randomGen(8);
		String ppsIdRTO = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251882", "1251882",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.0, 4598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_LOST_REFUND.toString();
		String packedLostMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId1,
				refundMessage);

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage1 = RefundType.RELEASE_LOST_REFUND.toString();
		String LostBeforePackedMessage = sellerHelper.RefundMessageCreation(ppsIdRTO, OrderReleaseId, refundMessage1);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, releaseMessage, releaseMessage1, packedLostMessage, ppsIdCancellation,
				paymentPlanItemIdCancel, paymentPlanExecutionStatus_id, lineId1, paymentPlanItemIdrto,
				paymentPlanItemIdrto1, ppsIdRTO, LostBeforePackedMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_Release_PackedLost_Helper(String OrderId, String store_order_id,
			String OrderReleaseId1, String lineId1, String ppsId, String ppsIdPackedLost, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdPackedLost, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId1, "F");
		sellerHelper.InsertPaymentPlan(ppsIdPackedLost, store_order_id, "CANCELLATION",
				"RELEASE_LOST_REFUND" + OrderReleaseId1 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdPackedLost, paymentPlanItemIdref, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdPackedLost, "79900", "REFUND");
	}

	public static void SPS_Funds_Release_LostBeforePacked_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdLostBeforePacked, String paymentPlanItemIdLostBeforePacked1,
			String ppsId, String ppsIdLostBeforePacked) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdLostBeforePacked, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdLostBeforePacked, OrderId, "RETURN", "RELEASE_LOST_REFUND" + OrderId + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdLostBeforePacked, paymentPlanItemIdLostBeforePacked, "SKU", "229900",
				"1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsIdLostBeforePacked, paymentPlanItemIdLostBeforePacked1, "SKU", "229900",
				"1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdLostBeforePacked);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdLostBeforePacked1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdLostBeforePacked, "459800", "REFUND");

	}

	@DataProvider
	public static Object[][] SPS_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);

		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation1 = "uid12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemIdrefex1 = randomGen(10);
		String paymentPlanExecutionStatus_idex1 = randomGen(10);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.LINE_CANCELLATION_REFUND.toString();
		String exchangeOrderCancellationMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation,
				OrderReleaseIdex, refundMessage);

		String refundMessage1 = RefundType.RELEASE_CANCELLATION_REFUND.toString();
		String exchangeOrderCancellationMessage1 = sellerHelper.RefundMessageCreation(ppsIdexCancellation1,
				OrderReleaseIdex1, refundMessage1);

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id,
				ppsIdex1, OrderReleaseId1, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				exchangeOrderCancellationMessage, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1, ppsIdexCancellation1,
				paymentPlanItemIdrefex1, paymentPlanExecutionStatus_idex1, exchangeOrderCancellationMessage1,
				parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_Release_ExchangeOrderCreationHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String OrderIdex, String store_order_id_ex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex0,
			String paymentPlanItemIdex, String ppsIdexChild) throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex, "2299.00", "2299.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "2299.00", "2299.00", "0.0",
				"36", OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "373908", "1251882",
				"1251882", "2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);
		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex0, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "EXCHANGED_SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex0);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "229900", "EXCHANGE");

	}

	public static void SPS_Funds_Release_ExchangeOrderCancellation_Helper(String OrderIdex, String store_order_idex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String ppsIdexCancellation,
			String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex) throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND");
	}

	public static void SPS_Funds_Release_ExchangeOrderCreationHelper1(String OrderId, String store_order_id,
			String OrderReleaseId1, String lineId1, String ppsId, String OrderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1)
			throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex1, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex1, OrderIdex1, store_order_id_ex1, "799.00", "799.00", "0.0",
				"36", OrderReleaseId1);
		sellerHelper.InsertOrderLine(lineIdex1, OrderIdex1, OrderReleaseIdex1, store_order_id_ex1, "373900", "1251843",
				"1251843", "799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId1, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex1, ppsIdex1);
		sellerHelper.InsertPaymentPlan(ppsIdexChild1, store_order_id_ex1, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex1, store_order_id_ex1, "EXCHANGE", "Null", ppsIdexChild1,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex1, paymentPlanItemIdex1, "SKU", "79900", "1", "5", "1251843");
		sellerHelper.InsertPaymentPlanItem(ppsIdex1, paymentPlanItemIdex2, "EXCHANGED_SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex2);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex1, "79900", "EXCHANGE");

	}

	public static void SPS_Funds_Release_ExchangeOrderCancellation_Helper1(String OrderIdex1, String store_order_idex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String ppsIdexCancellation1,
			String paymentPlanItemIdrefex1, String paymentPlanExecutionStatus_idex1) throws SQLException {
		sellerHelper.updateOrders(OrderIdex1, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex1, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex1, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation1, store_order_idex1, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseIdex1 + "", ppsIdex1, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex1, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation1, paymentPlanItemIdrefex1, "SKU", "79900", "1", "5",
				"1251843");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdrefex1, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation1, "79900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_Funds_Release_ExchangeOrder_Packed_And_RTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_RTO_REFUND.toString();
		String exchangeOrderRTORefundMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation, OrderReleaseIdex,
				refundMessage);

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				exchangeOrderRTORefundMessage, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex, parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_Release_ExchangeOrder_Packed_And_RTO_Helper(String OrderIdex, String store_order_idex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String ppsIdexCancellation,
			String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex) throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_RTO_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND");
	}

	public static void SPS_Funds_Release_ExchangeOrder_Packed_And_Lost_Helper(String OrderIdex, String store_order_idex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String ppsIdexCancellation,
			String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex) throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_LOST_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_Funds_Release_ExchangeOfExchangePacked(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);
		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "t12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.on.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");
		String parentOrderRefundMessage1 = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id_ex,
				ppsIdex1, OrderReleaseIdex, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				paymentPlanItemIdrefex, paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1,
				parentOrderRefundMessage, parentOrderRefundMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Funds_Release_ExchangeofExchangeOrderCreationHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String OrderIdex, String store_order_id_ex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex0,
			String paymentPlanItemIdex, String ppsIdexChild) throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex, "2299.00", "2299.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "2299.00", "2299.00", "0.0",
				"36", OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "373908", "1251883",
				"1251883", "2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);
		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex0, "SKU", "229900", "1", "5", "1251883");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "EXCHANGED_SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex0);
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "229900", "EXCHANGE");

	}

	@DataProvider
	public static Object[][] SPS_FundsRelease_OrderDeliveredAndRTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdRTO = "k12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "1531", "3831", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 3831,
				Long.parseLong(OrderReleaseId1), 1, 19, 3831, "A", "Added", 1531, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1531, Long.parseLong(OrderReleaseId1), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 2399.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_RTO_REFUND.toString();
		String RTORefundMessage = sellerHelper.RefundMessageCreation(ppsIdRTO, OrderReleaseId1, refundMessage);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, RTORefundMessage, lineId, ppsIdRTO, paymentPlanItemIdref,
				paymentPlanExecutionStatus_id, "5", "19" };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_FundsRelease_OrderDeliveredAndRTO_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String ppsIdRTO, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdRTO, store_order_id, "CANCELLATION",
				"RELEASE_RTO_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdRTO, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRTO, "79900", "REFUND");
	}

	@DataProvider
	public static Object[][] SPS_FundsRelease_SellersWithDifferentPayouts(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdRTO = "k12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "356809", "1190298", "1190298",
				"2299.00", "1", "2299.00", "0.00", "0", "25", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		/*sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "799.00",
				"1", "799.00", "0.00", "0", "21", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");*/

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "25", "1190298");
	//	sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId);
	//	sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "229900", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1190298, Long.parseLong(OrderReleaseId), 1, 25, 1190298, "A", "Added", 356809,
				"ON_HAND", 2299.0, 0.0,  ppsId,
				PaymentMethod.on.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1190298, Long.parseLong(OrderReleaseId), 1, 25, 1190298, "QD", "QA Done", 356809, "ON_HAND", 2299.0,
				0.0,  ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, OrderReleaseId, store_order_id, orderMessage, releaseMessage, ppsId,
				paymentPlanItemId, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_CodQty2_VatAdjustment_Cancel_1_Release_1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String ppsIdVatrefund = "vate492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdVatrefund1 = "vat1e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdVatref = randomGen(8);
		String paymentPlanItemIdVatref1 = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2000.00", "2000.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "3831", "3831", "1000.00",
				"1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND", "0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId, store_order_id, "1531", "3833", "3833",
				"1000.00", "1", "1000.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "200000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "100000", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "100000", "1", "19", "3833");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemId1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "200000", "DEBIT", "5");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2000.0, 2000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0,
				0.0, false, 0.0, 1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				1000.0, 0.0, Long.parseLong(lineId1), 0.0, 1000.0, 0.0, false, 0.0, 1531,
				Long.parseLong(OrderReleaseId), 1, 19, 3833, "A", "Added", 1531, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// ------------------Release------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				1000.0, 1000.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 1000.0, 0.0, false, 0.0,
				1531, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 1000.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		sellerHelper.insertOrderLineAdditionalInfo1(lineId, ppsIdVatrefund, "2.34", "100.0",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3831);
		sellerHelper.insertOrderLineAdditionalInfo1(lineId1, ppsIdVatrefund1, "2.34", "50.0",
				"VAT_ADJUSTMENT_REFUND_" + OrderId + 3833);
		sellerHelper.InsertPaymentPlan(ppsIdVatrefund, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdVatrefund1, store_order_id, "CANCELLATION",
				"VAT_ADJUSTMENT_REFUND" + lineId1 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdVatrefund, paymentPlanItemIdVatref, "VAT_ADJ_REFUND", "10000", "1", "1",
				"3831");
		sellerHelper.InsertPaymentPlanItem(ppsIdVatrefund1, paymentPlanItemIdVatref1, "VAT_ADJ_REFUND", "5000", "1",
				"1", "3833");
		sellerHelper.InsertPaymentPlanItemInstrument("10000", paymentPlanItemIdVatref, "8", "CREDIT");
		sellerHelper.InsertPaymentPlanItemInstrument("5000", paymentPlanItemIdVatref1, "8", "CREDIT");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdVatrefund, "10000", "CREDIT", "8");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdVatrefund1, "5000", "CREDIT", "8");

		String refundType = RefundType.VAT_ADJUSTMENT_REFUND.name().toString();
		String vatRefundMessage = sellerHelper.RefundMessageCreation(ppsIdVatrefund, OrderReleaseId, refundType);
		String vatRefundMessage1 = sellerHelper.RefundMessageCreation(ppsIdVatrefund1, OrderReleaseId, refundType);

		// --------------------------Cancellation
		// ---------------------------------------
		String refundType1 = RefundType.LINE_CANCELLATION_REFUND.name().toString();
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdRefund, OrderReleaseId, refundType1);

		String[] arr1 = { OrderId, store_order_id, OrderReleaseId, ppsId, paymentPlanItemId, paymentPlanItemId1, lineId,
				lineId1, orderMessage, releaseMessage, ppsIdRefund, paymentPlanItemIdref, paymentPlanExecutionStatus_id,
				cancellationMessage, vatRefundMessage, vatRefundMessage1, ppsIdVatrefund, ppsIdVatrefund1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_CodQty2_VatAdjustment_Cancel_1_Release_1_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId1, String ppsId, String ppsIdRefund, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		// sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId1, "IC");
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId1, ppsIdRefund, "1000.0");
		sellerHelper.InsertPaymentPlan(ppsIdRefund, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdRefund, paymentPlanItemIdref, "SKU", "100000", "1", "19", "3833");
		sellerHelper.InsertPaymentPlanItemInstrument("100000", paymentPlanItemIdref, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdRefund, "100000", "REFUND", "8");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOrder_Cancel1_And_Packed1_Cancel1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemIdCancel = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);

		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation1 = "uid12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemIdrefex1 = randomGen(10);
		String paymentPlanExecutionStatus_idex1 = randomGen(10);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.LINE_CANCELLATION_REFUND.toString();
		String exchangeOrderCancellationMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation,
				OrderReleaseIdex, refundMessage);

		String refundMessage1 = RefundType.RELEASE_CANCELLATION_REFUND.toString();
		String exchangeOrderCancellationMessage1 = sellerHelper.RefundMessageCreation(ppsIdexCancellation1,
				OrderReleaseIdex1, refundMessage1);

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id,
				ppsIdex1, OrderReleaseId1, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				exchangeOrderCancellationMessage, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1,
				ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1, ppsIdexCancellation1,
				paymentPlanItemIdrefex1, paymentPlanExecutionStatus_idex1, exchangeOrderCancellationMessage1,
				parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeOrderCreationHelper(String OrderId, String store_order_id,
			String OrderReleaseId, String lineId, String ppsId, String OrderIdex, String store_order_id_ex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String paymentPlanItemIdex0,
			String paymentPlanItemIdex, String ppsIdexChild) throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex, "2299.00", "2299.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "2299.00", "2299.00", "0.0",
				"36", OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "373908", "1251882",
				"1251882", "2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);
		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex0, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "EXCHANGED_SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex0, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex, "5", "EXCHANGE", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "229900", "EXCHANGE", "5");

	}

	public static void SPS_Cod_Funds_Release_ExchangeOrderCancellation_Helper(String OrderIdex, String store_order_idex,
			String OrderReleaseIdex, String lineIdex, String ppsIdex, String ppsIdexCancellation,
			String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex) throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND", "8");
	}

	public static void SPS_Cod_Funds_Release_ExchangeOrderCreationHelper1(String OrderId, String store_order_id,
			String OrderReleaseId1, String lineId1, String ppsId, String OrderIdex1, String store_order_id_ex1,
			String OrderReleaseIdex1, String lineIdex1, String ppsIdex1, String paymentPlanItemIdex1,
			String paymentPlanItemIdex2, String ppsIdexChild1)
			throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex1, "799.00", "799.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex1, OrderIdex1, store_order_id_ex1, "799.00", "799.00", "0.0",
				"36", OrderReleaseId1);
		sellerHelper.InsertOrderLine(lineIdex1, OrderIdex1, OrderReleaseIdex1, store_order_id_ex1, "373900", "1251843",
				"1251843", "799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId1, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex1, ppsIdex1);
		sellerHelper.InsertPaymentPlan(ppsIdexChild1, store_order_id_ex1, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex1, store_order_id_ex1, "EXCHANGE", "Null", ppsIdexChild1,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex1, paymentPlanItemIdex1, "SKU", "79900", "1", "5", "1251843");
		sellerHelper.InsertPaymentPlanItem(ppsIdex1, paymentPlanItemIdex2, "EXCHANGED_SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex1, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdex2, "5", "EXCHANGE", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex1, "79900", "EXCHANGE", "5");

	}

	public static void SPS_Cod_Funds_Release_ExchangeOrderCancellation_Helper1(String OrderIdex1,
			String store_order_idex1, String OrderReleaseIdex1, String lineIdex1, String ppsIdex1,
			String ppsIdexCancellation1, String paymentPlanItemIdrefex1, String paymentPlanExecutionStatus_idex1)
			throws SQLException {
		sellerHelper.updateOrders(OrderIdex1, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex1, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex1, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation1, store_order_idex1, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderReleaseIdex1 + "", ppsIdex1, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex1, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation1, paymentPlanItemIdrefex1, "SKU", "79900", "1", "5",
				"1251843");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdrefex1, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation1, "79900", "REFUND", "8");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdexCancellation = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_RTO_REFUND.toString();
		String exchangeOrderRTORefundMessage = sellerHelper.RefundMessageCreation(ppsIdexCancellation, OrderReleaseIdex,
				refundMessage);

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				exchangeOrderRTORefundMessage, ppsIdexCancellation, paymentPlanItemIdrefex,
				paymentPlanExecutionStatus_Idrefex, parentOrderRefundMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_RTO_Helper(String OrderIdex,
			String store_order_idex, String OrderReleaseIdex, String lineIdex, String ppsIdex,
			String ppsIdexCancellation, String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex)
			throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_RTO_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND", "8");
	}

	public static void SPS_Cod_Funds_Release_ExchangeOrder_Packed_And_Lost_Helper(String OrderIdex,
			String store_order_idex, String OrderReleaseIdex, String lineIdex, String ppsIdex,
			String ppsIdexCancellation, String paymentPlanItemIdrefex, String paymentPlanExecutionStatus_idex)
			throws SQLException {
		sellerHelper.updateOrders(OrderIdex, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex, "CANCELLATION",
				"RELEASE_LOST_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation, paymentPlanItemIdrefex, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdrefex, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation, "229900", "REFUND", "8");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOfExchangePacked(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);
		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "t12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");
		String parentOrderRefundMessage1 = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id_ex,
				ppsIdex1, OrderReleaseIdex, "EXCHANGE");

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				paymentPlanItemIdrefex, paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1,
				parentOrderRefundMessage, parentOrderRefundMessage1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeofExchangeOrderCreationHelper(String OrderId,
			String store_order_id, String OrderReleaseId, String lineId, String ppsId, String OrderIdex,
			String store_order_id_ex, String OrderReleaseIdex, String lineIdex, String ppsIdex,
			String paymentPlanItemIdex0, String paymentPlanItemIdex, String ppsIdexChild)
			throws SQLException, NumberFormatException, JAXBException {
		sellerHelper.InsertOrder(OrderIdex, "2299.00", "2299.00", "0.0", "ex");
		sellerHelper.InsertOrderRelease(OrderReleaseIdex, OrderIdex, store_order_id_ex, "2299.00", "2299.00", "0.0",
				"36", OrderReleaseId);
		sellerHelper.InsertOrderLine(lineIdex, OrderIdex, OrderReleaseIdex, store_order_id_ex, "373908", "1251883",
				"1251883", "2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", lineId, "0.00",
				"ON_HAND", "0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex, ppsIdex);
		sellerHelper.InsertPaymentPlan(ppsIdexChild, store_order_id_ex, "CHILD_EXCHANGE", "Null", ppsId,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlan(ppsIdex, store_order_id_ex, "EXCHANGE", "Null", ppsIdexChild,
				"JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M", "229900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex0, "SKU", "229900", "1", "5", "1251883");
		sellerHelper.InsertPaymentPlanItem(ppsIdex, paymentPlanItemIdex, "EXCHANGED_SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex0, "5", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdex, "EXCHANGE");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex, "229900", "EXCHANGE");

	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Cancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);
		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "t12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		String ppsIdexCancel = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdexCancel = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");

		String exchangeOrderCancellationMessage = sellerHelper.RefundMessageCreation(ppsIdexCancel, OrderReleaseIdex1,
				RefundType.RELEASE_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				paymentPlanItemIdrefex, paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1,
				parentOrderRefundMessage, exchangeOrderCancellationMessage, ppsIdexCancel, paymentPlanItemIdexCancel };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Cancel_Helper(String OrderIdex1,
			String store_order_idex1, String OrderReleaseIdex1, String paymentPlanItemIdexCancel, String ppsIdex1,
			String ppsIdexCancel) throws SQLException {
		sellerHelper.updateOrders(OrderIdex1, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex1, "F");
		sellerHelper.InsertOrderAdditionalInfo(OrderIdex1, ppsIdexCancel, "RELEASE_CANCELLATION_REFUND_" + OrderIdex1);
		sellerHelper.InsertPaymentPlan(ppsIdexCancel, store_order_idex1, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND_" + OrderIdex1 + "", ppsIdex1, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdexCancel, paymentPlanItemIdexCancel, "SKU", "229900", "1", "19",
				"1251883");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdexCancel, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancel, "229900", "REFUND", "8");

		// sellerHelper.InsertPaymentPlan(ppsIdexCancellation, store_order_idex,
		// "CANCELLATION",
		// "RELEASE_LOST_REFUND" + OrderReleaseIdex + "", ppsIdex, "Null",
		// "Null", "Null");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex,
		// "REFUND", "CANCELLATION");
		// sellerHelper.InsertPaymentPlanItem(ppsIdexCancellation,
		// paymentPlanItemIdrefex, "SKU", "229900", "1", "5", "1251882");
		// sellerHelper.InsertPaymentPlanItemInstrument("229900",
		// paymentPlanItemIdrefex, "8","REFUND","SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdexCancellation,
		// "229900", "REFUND","8");

	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Return(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdReturnExchange = "retd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemIdreturn = randomGen(8);
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_Idrefex = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);
		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "t12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");
		String parentOrderRefundMessage1 = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id_ex,
				ppsIdex1, OrderReleaseIdex, "EXCHANGE");

		String exchangeOfExchangeReturnMessage = sellerHelper.RefundMessageCreation(ppsIdReturnExchange,
				OrderReleaseIdex1, RefundType.RETURN_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				paymentPlanItemIdrefex, paymentPlanExecutionStatus_Idrefex, OrderIdex1, store_order_id_ex1,
				OrderReleaseIdex1, lineIdex1, ppsIdex1, paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1,
				parentOrderRefundMessage, parentOrderRefundMessage1, ppsIdReturnExchange, paymentPlanItemIdreturn,
				exchangeOfExchangeReturnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_Return_Helper(String OrderId,
			String store_order_id, String OrderReleaseId, String paymentPlanItemIdreturn, String ppsId,
			String ppsIdReturnExchange) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdReturnExchange, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdReturnExchange, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdReturnExchange, paymentPlanItemIdreturn, "SKU", "79900", "1", "19",
				"1251883");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdreturn, "8", "System");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdReturnExchange, "79900", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_ExchangeOfExchangePacked_RTO(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String OrderReleaseIdex = randomGen(10);
		String store_order_id_ex = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderIdex = randomGen(10);
		String lineIdex = randomGen(9);
		String ppsIdex = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex = randomGen(8);
		String ppsIdexChild = "xd12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdex1Cancellation = "rtod12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a"
				+ randomGen(3) + "";
		String paymentPlanItemIdrefex1 = randomGen(8);
		String paymentPlanItemIdrefex = randomGen(8);
		String paymentPlanExecutionStatus_idex1 = randomGen(8);
		String paymentPlanItemIdex0 = randomGen(8);
		String OrderIdex1 = randomGen(10);
		String store_order_id_ex1 = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseIdex1 = randomGen(10);
		String lineIdex1 = randomGen(9);
		String ppsIdex1 = "t12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemIdex1 = randomGen(8);
		String paymentPlanItemIdex2 = randomGen(8);
		String ppsIdexChild1 = "op12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "309800", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		String parentOrderRefundMessage = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id, ppsIdex,
				OrderReleaseId, "EXCHANGE");
		String parentOrderRefundMessage1 = sellerHelper.RefundParentOrderExchangeMessageCreation(store_order_id_ex,
				ppsIdex1, OrderReleaseIdex, "EXCHANGE");

		String exchangeOfExchangeRTOMessage = sellerHelper.RefundMessageCreation(ppsIdex1Cancellation,
				OrderReleaseIdex1, RefundType.RELEASE_RTO_REFUND.toString());

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, OrderReleaseIdex, store_order_id_ex, lineId, lineId1,
				OrderIdex, lineIdex, ppsIdex, paymentPlanItemIdex0, paymentPlanItemIdex, ppsIdexChild,
				paymentPlanItemIdrefex, OrderIdex1, store_order_id_ex1, OrderReleaseIdex1, lineIdex1, ppsIdex1,
				paymentPlanItemIdex1, paymentPlanItemIdex2, ppsIdexChild1, parentOrderRefundMessage,
				parentOrderRefundMessage1, ppsIdex1Cancellation, paymentPlanItemIdrefex1,
				paymentPlanExecutionStatus_idex1, exchangeOfExchangeRTOMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_ExchangeOfExchangePacked_RTO_Helper(String OrderIdex1,
			String store_order_idex1, String OrderReleaseIdex1, String lineIdex1, String ppsIdex1,
			String ppsIdex1Cancellation, String paymentPlanItemIdrefex1, String paymentPlanExecutionStatus_idex1)
			throws SQLException {
		sellerHelper.updateOrders(OrderIdex1, "10");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseIdex1, "F");
		sellerHelper.updateOrderLineStatusByLineId(lineIdex1, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdex1Cancellation, store_order_idex1, "CANCELLATION",
				"RELEASE_RTO_REFUND" + OrderReleaseIdex1 + "", ppsIdex1, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex1, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdex1Cancellation, paymentPlanItemIdrefex1, "SKU", "79900", "1", "5",
				"1251883");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdrefex1, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdex1Cancellation, "79900", "REFUND", "8");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Release_2_PK_2_OrderCancellation(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdCancellation = "or12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		String deliveryMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "DL",
				"Delivered", 2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0,
				false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "D", "Delivered", 373908, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.cod.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.ORDER_CANCELLATION_REFUND.toString();
		String orderCancellationRefundMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId,
				ppsIdCancellation, refundMessage);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, lineId, lineId1, orderCancellationRefundMessage,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdref1, deliveryMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Release_2_PK_2_OrderCancellation_Helper(String OrderId, String OrderReleaseId,
			String store_order_id, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanItemIdref1) throws Exception {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderCancelledOn(OrderId);
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref1, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "309800", "REFUND", "8");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Release_2_PK_1_OrderCancellationWithoutOrderId(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdCancellation = "or12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.ORDER_CANCELLATION_REFUND.toString();
		String orderCancellationRefundMessage = sellerHelper
				.RefundOrderCancellationMessageCreationWithOutOrderId(store_order_id, ppsIdCancellation, refundMessage);
		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage,
				releaseMessage1, ppsId, paymentPlanItemId, lineId, lineId1, orderCancellationRefundMessage,
				ppsIdCancellation, paymentPlanItemIdref, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Release_2_PK_1_OrderCancellationWithoutOrderId_Helper(String OrderId,
			String OrderReleaseId, String store_order_id, String ppsId, String ppsIdCancellation,
			String paymentPlanItemIdref, String paymentPlanItemIdref1) throws Exception {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "ORDER_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderCancelledOnWithOldDate(OrderId);
		sellerHelper.updateOrdersRelease(OrderReleaseId, "10", "1", "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"ORDER_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref1, "SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref1, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "309800", "REFUND", "8");
	}

	@DataProvider
	public static Object[][] SPS_Cod_Release_2_PK_1_OrderCancellation(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String ppsIdCancellation = "or12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanItemIdref1 = randomGen(8);

		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "3098.00", "3098.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");

		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "309800", "DEBIT", "5");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 3098.0, 3098.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0, ppsId,
				PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage = RefundType.ORDER_CANCELLATION_REFUND.toString();
		String orderCancellationRefundMessage = sellerHelper.RefundOrderCancellationMessageCreation(OrderId,
				ppsIdCancellation, refundMessage);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, orderMessage, releaseMessage, ppsId,
				paymentPlanItemId, lineId, lineId1, orderCancellationRefundMessage, ppsIdCancellation,
				paymentPlanItemIdref, paymentPlanItemIdref1 };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public static Object[][] SPS_Cod_Funds_Release_PackedLost_And_LostBeforePacked(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdLostAfterPack = "c32j6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemIdLBP = randomGen(8);
		String paymentPlanItemIdLBP1 = randomGen(8);
		String ppsIdLostBeforePack = "M12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemId1 = randomGen(8);
		String paymentPlanItemId2 = randomGen(8);
		String paymentPlanItemIdLAP = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "5397.00", "5397.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "4598.00", "4598.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "45", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "373908", "1251881", "1251881",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId, store_order_id, "373908", "1251882", "1251882",
				"2299.00", "1", "2299.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "373900", "1251842", "1251842",
				"799.00", "1", "799.00", "0.00", "0", "5", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "400", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "1", "5", "1251881");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId1, "SKU", "79900", "1", "5", "1251842");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId2, "SKU", "229900", "1", "5", "1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemId2, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId1, "5", "DEBIT", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "539700", "DEBIT", "5");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "0", "DEBIT");
		System.out.println("DB query update successfull");

		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 5397.0, 5397.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0,
				0.0, false, 0.0, 1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "A", "Added", 373908,
				"ON_HAND", 2299.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false, 0.0, 1251842,
				Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "A", "Added", 373900, "ON_HAND", 799.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "A", "Added", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.cod.name());

		// -------------------------Release fund
		// ----------------------------------------

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				4598.0, 4598.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2299.0, 0.0, false, 0.0,
				1251881, Long.parseLong(OrderReleaseId), 1, 5, 1251881, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0,
				Long.parseLong(lineId2), 0.0, 2299.0, 0.0, false, 0.0, 1251882, Long.parseLong(OrderReleaseId), 1, 5,
				1251882, "QD", "QA Done", 373908, "ON_HAND", 2299.0, 0.0, ppsId, PaymentMethod.cod.name());

		String releaseMessage1 = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK",
				"Packed", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 799.0, 0.0, false,
				0.0, 1251842, Long.parseLong(OrderReleaseId1), 1, 5, 1251842, "QD", "QA Done", 373900, "ON_HAND", 799.0,
				0.0, ppsId, PaymentMethod.cod.name());

		// ----------------Cancellation
		// Message-----------------------------------------------
		String refundMessage = RefundType.RELEASE_LOST_REFUND.toString();
		String packedLostMessage = sellerHelper.RefundMessageCreation(ppsIdLostAfterPack, OrderReleaseId1,
				refundMessage);

		// ----------------RTO
		// Message-----------------------------------------------
		String refundMessage1 = RefundType.RELEASE_LOST_REFUND.toString();
		String LostBeforePackedMessage = sellerHelper.RefundMessageCreation(ppsIdLostBeforePack, OrderReleaseId,
				refundMessage1);

		String[] arr1 = { OrderId, OrderReleaseId, OrderReleaseId1, store_order_id, ppsId, paymentPlanItemId,
				orderMessage, lineId, lineId1, lineId2, releaseMessage, releaseMessage1, packedLostMessage,
				ppsIdLostAfterPack, paymentPlanItemIdLAP, paymentPlanExecutionStatus_id, paymentPlanItemIdLBP,
				paymentPlanItemIdLBP1, ppsIdLostBeforePack, LostBeforePackedMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void SPS_Cod_Funds_Release_PackedLost_Helper(String OrderId, String store_order_id,
			String OrderReleaseId1, String lineId1, String ppsId, String ppsIdLostAfterPack,
			String paymentPlanItemIdLAP, String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdLostAfterPack, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId1, "F");
		sellerHelper.InsertPaymentPlan(ppsIdLostAfterPack, store_order_id, "CANCELLATION",
				"RELEASE_LOST_REFUND" + OrderReleaseId1 + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id, "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdLostAfterPack, paymentPlanItemIdLAP, "SKU", "79900", "1", "5",
				"1251842");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdLAP, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdLostAfterPack, "79900", "REFUND", "8");
	}

	public static void SPS_Cod_Funds_Release_LostBeforePacked_Helper(String OrderId, String store_order_id,
			String OrderReleaseId, String paymentPlanItemIdLBP, String paymentPlanItemIdLBP1, String ppsId,
			String ppsIdLostBeforePack) throws SQLException {
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdLostBeforePack, "return_" + OrderId);
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdLostBeforePack, OrderId, "RETURN", "RELEASE_LOST_REFUND" + OrderId + "",
				ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdLostBeforePack, paymentPlanItemIdLBP, "SKU", "229900", "1", "5",
				"1251881");
		sellerHelper.InsertPaymentPlanItem(ppsIdLostBeforePack, paymentPlanItemIdLBP1, "SKU", "229900", "1", "5",
				"1251882");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdLBP, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdLBP1, "8", "REFUND", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdLostBeforePack, "459800", "REFUND", "8");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1PhonePe(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("29900",
		// paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "11", "SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900",
		// "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT", "11");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_PhonePe_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("29900",
		// paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "11", "SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation,
		// "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND", "11");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1Mynts(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "13", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "13");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_Mynts_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "13", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "13");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1EMI(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("29900",
		// paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemId, "3", "SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900",
		// "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "79900", "DEBIT", "3");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_EMI_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		// sellerHelper.InsertPaymentPlanItemInstrument("29900",
		// paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("79900", paymentPlanItemIdref, "3", "SYSTEM");
		// sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation,
		// "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "79900", "REFUND", "3");

	}

	@DataProvider
	public static Object[][] SPS_SellerQty1BankCB(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "12", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "29900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "12");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				799.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				799.0, 799.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 799.0, 0.0,
				ppsId, PaymentMethod.on.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_BankCB_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "79900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("29900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "12", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "29900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "12");

	}
	
	
	
	@DataProvider
	public static Object[][] SPS_SellerQty1MyntraCredit(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String ppsIdRefund = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2399.00", "2399.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "799.00", "799.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "1531", "1000909", "3831",
				"799.00", "1", "799.00", "0.00", "0", "19", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "79900", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "89900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("39900", paymentPlanItemId, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemId, "16", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "39900", "DEBIT", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "50000", "DEBIT", "16");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 899.0, 899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 799.0,
				0.0, false, 0.0, 1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "A", "Added", 1531, "ON_HAND",
				899.0, 0.0, ppsId, PaymentMethod.on.name());

		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				899.0, 899.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 899.0, 0.0, false, 0.0,
				1000909, Long.parseLong(OrderReleaseId), 1, 19, 3831, "QD", "QA Done", 1531, "ON_HAND", 899.0, 0.0,
				ppsId, PaymentMethod.cod.name());

		// -----------------Refund only 1 ------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId, releaseMessage,
				paymentPlanItemIdref, ppsIdCancellation, returnMessage };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_Online_MyntraCredit_OrderWithSellerQty1ReleaseHelper(String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsIdCancellation) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "return_" + OrderId);
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "RETURN", "return_" + OrderId + "", ppsId,
				"Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "89900", "1", "19", "3831");
		sellerHelper.InsertPaymentPlanItemInstrument("39900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanItemInstrument("50000", paymentPlanItemIdref, "16", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "39900", "REFUND", "1");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "50000", "REFUND", "16");

	}
	
	//------------------------------PITNEY BOWES DP-------------------------------------------
	
	
	@DataProvider
	public static Object[][] SPS_PBOrderSingleSku(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(1) + randomGen(10) + randomGen(1);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2500.00", "2500.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2500.00", "2500.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "315096", "1046378", "1046378",
				"2500.00", "1", "2500.00", "0.00", "0", "131", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "250000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "250000", "1", "131", "1046378");
		sellerHelper.InsertPaymentPlanItemInstrument("250000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "250000", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2500.0, 2500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2500.0, 0.0,
				false, 0.0, 1046378, Long.parseLong(OrderReleaseId), 1, 131, 1046378, "A", "Added", 315096, "ON_HAND",
				2500.0, 0.0, ppsId, PaymentMethod.on.name());

		String[] arr1 = { OrderId, store_order_id, ppsId, paymentPlanItemId, orderMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider
	public static Object[][] SPS_PBOrderSingleSkuAndCancel(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String lineId = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanExecutionStatus_idex1 = randomGen(8);
		String ppsIdCancellation = "c3e412ea-32de-" + randomGen(4) + "-a05d-" + randomGen(4) + "dh30a" + randomGen(3)
				+ "";
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		String paymentPlanItemIdref = randomGen(8);
		System.out.println("orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: " + paymentPlanItemId
				+ " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "2500.00", "2500.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2500.00", "2500.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "315096", "1046379", "1046379",
				"2500.00", "1", "2500.00", "0.00", "0", "131", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "250000", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "250000", "1", "131", "1046379");
		sellerHelper.InsertPaymentPlanItemInstrument("250000", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "250000", "DEBIT");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 2500.0, 2500.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 2500.0, 0.0,
				false, 0.0, 1046378, Long.parseLong(OrderReleaseId), 1, 131, 1046378, "A", "Added", 315096, "ON_HAND",
				2500.0, 0.0, ppsId, PaymentMethod.on.name());

		// ------------------------------------ Cancellation -------------------
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsIdCancellation, "RELEASE_CANCELLATION_PPS_ID");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderLineStatusByLineId(lineId, "IC");
		sellerHelper.updateOrderReleaseStatusAndCancelledOn(OrderReleaseId, "F");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"RELEASE_CANCELLATION_REFUND" + OrderId + "", ppsId, "Null", "Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "250000", "1", "131", "1046379");
		sellerHelper.InsertPaymentPlanItemInstrument("250000", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "250000", "REFUND");
		sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_idex1, "REFUND", "CANCELLATION");

		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				RefundType.RELEASE_CANCELLATION_REFUND.toString());

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider
	public static Object[][] SPS_PBMultiQtyCan1ReleaseReturn1(ITestContext testContext)
			throws SQLException, IOException, NumberFormatException, JAXBException {
		String OrderId = randomGen(10);
		String store_order_id = randomGen(10) + randomGen(10) + randomGen(3);
		String OrderReleaseId = randomGen(10);
		String OrderReleaseId1 = randomGen(10);
		String OrderReleaseId2 = randomGen(10);
		String lineId = randomGen(9);
		String lineId1 = randomGen(9);
		String lineId2 = randomGen(9);
		String ppsId = "b12f6cb4-4e68-" + randomGen(4) + "-a05d-" + randomGen(4) + "df30a" + randomGen(3) + "";
		String ppsIdCancellation = "t5e492ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3)
				+ "";
		String ppsIdReturn = "lpe362ea-20de-" + randomGen(4) + "-a94d-" + randomGen(4) + "dx81a" + randomGen(3) + "";
		String paymentPlanItemId = randomGen(8);
		String paymentPlanItemIdref = randomGen(8);
		String paymentPlanreturnItemId = randomGen(8);
		String paymentPlanExecutionStatus_id = randomGen(7);
		String sourceId = "bee790b6-4ee7-40a3-a26f-a01179feef29--s3";// "087e3e3d-"+randomGen(4)+"-4d9c-"+randomGen(4)+"-f"+randomGen(3)+"ef"+randomGen(4)+"ca--s2";
		String sessionId = "JJN002e84af0aa30b64e68a09cdff222fbdf181453108195M";// "JJN0054e2bbd9ae"+randomGen(6)+"a89f35aad1b"+randomGen(6)+"b"+randomGen(10)+"M";
		System.out.println("---------orderId: " + OrderId + " ppsId: " + ppsId + " PymentPlanItemId: "
				+ paymentPlanItemId + " OrderReleaseId: " + OrderReleaseId);
		sellerHelper.InsertOrder(OrderId, store_order_id, "6897.00", "6897.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId1, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderRelease(OrderReleaseId2, OrderId, store_order_id, "2299.00", "2299.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "0.00", "0.00", "36", "0.0");
		sellerHelper.InsertOrderLine(lineId, OrderId, OrderReleaseId, store_order_id, "315096", "1046378", "1046378",
				"2299.00", "1", "2299.00", "0.00", "0", "131", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId1, OrderId, OrderReleaseId1, store_order_id, "315096", "1046378", "1046378",
				"2299.00", "1", "2299.00", "0.00", "0", "131", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderLine(lineId2, OrderId, OrderReleaseId2, store_order_id, "315096", "1046378", "1046378",
				"2299.00", "1", "2299.00", "0.00", "0", "131", "0.00", "0.00", "0.00", "Null", "0.00", "0.00", "ON_HAND",
				"0.0");
		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsId);
		sellerHelper.InsertPaymentPlan(ppsId, store_order_id, "SALE", "Null", sourceId, sessionId, "689700", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsId, paymentPlanItemId, "SKU", "229900", "3", "131", "1046378");
		sellerHelper.InsertPaymentPlanItemInstrument("689700", paymentPlanItemId);
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsId, "689700", "DEBIT");
		// sellerHelper.updateOrderLineStatusByLineId(lineId1, "QD");
		// sellerHelper.updateOrderReleaseStatus(OrderReleaseId1,
		// store_order_id, "PK");
		System.out.println("DB query update successfull");
		String orderMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "WP",
				"WORK_IN_PROGRESS", 6897.0, 6897.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId), 0.0, 6897.0,
				0.0, false, 0.0, 1046378, Long.parseLong(OrderReleaseId), 3, 131, 1046378, "A", "Added", 315096, "ON_HAND",
				2299.0, 0.0, ppsId, PaymentMethod.on.name());
		// --------------------------Cancellation
		// ---------------------------------------
		String cancellationMessage = sellerHelper.RefundMessageCreation(ppsIdCancellation, OrderReleaseId,
				"LINE_CANCELLATION_REFUND");
		// -------------------------Release fund
		// ----------------------------------------
		String releaseMessage = sellerHelper.OMSMessageCreation(Long.parseLong(OrderId), store_order_id, "PK", "Packed",
				2299.0, 2299.0, 0.0, false, 0.0, 0.0, 0.0, 0.0, Long.parseLong(lineId1), 0.0, 2299.0, 0.0, false, 0.0,
				1046378, Long.parseLong(OrderReleaseId1), 1, 131, 1046378, "QD", "QA Done", 315096, "ON_HAND", 2299.0, 0.0,
				ppsId, PaymentMethod.on.name());
		// --------------------------Return
		// Fund-------------------------------------
		String refundType = RefundType.RETURN_REFUND.name().toString();
		String returnMessage = sellerHelper.RefundMessageCreation(ppsIdReturn, OrderReleaseId1, refundType);

		String[] arr1 = { OrderId, store_order_id, ppsId, ppsIdCancellation, paymentPlanItemId, paymentPlanItemIdref,
				orderMessage, cancellationMessage, lineId, lineId1, paymentPlanExecutionStatus_id, releaseMessage,
				returnMessage, ppsIdReturn, paymentPlanreturnItemId };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
	}

	public static void create_SPS_PBMultiQtyReturnHelper(String lineId, String OrderId, String store_order_id,
			String paymentPlanItemIdref, String ppsId, String ppsReturnId) throws SQLException {

		sellerHelper.InsertOrderAdditionalInfo(OrderId, ppsReturnId, "return_" + OrderId);
		// sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId,
		// ppsReturnId, "2299");
		sellerHelper.InsertPaymentPlan(ppsReturnId, store_order_id, "RETURN", "return_" + OrderId + "", ppsId, "Null",
				"Null", "Null");
		sellerHelper.InsertPaymentPlanItem(ppsReturnId, paymentPlanItemIdref, "SKU", "229900", "1", "131", "1046378");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "1", "SYSTEM");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsReturnId, "229900", "REFUND", "1");

	}
	
	public static void SPS_PBMultiQtyCanRelHelper(String OrderId, String store_order_id, String OrderReleaseId,
			String lineId, String ppsId, String ppsIdCancellation, String paymentPlanItemIdref,
			String paymentPlanExecutionStatus_id) throws SQLException {
		sellerHelper.insertOrderLineAdditionalInfoLineCancellation(lineId, ppsIdCancellation, "2.30");
		sellerHelper.updateOrders(OrderId, "10");
		sellerHelper.updateOrderReleaseStatus(OrderReleaseId, "F");
		sellerHelper.updateOrderLineStatusByLineIdAndCancelledOn(lineId, "IC");
		sellerHelper.InsertPaymentPlan(ppsIdCancellation, store_order_id, "CANCELLATION",
				"LINE_CANCELLATION_REFUND" + OrderReleaseId + "", ppsId, "Null", "Null", "Null");
		// sellerHelper.InsertPaymentPlanExecutionStatus(paymentPlanExecutionStatus_id,
		// "REFUND", "CANCELLATION");
		sellerHelper.InsertPaymentPlanItem(ppsIdCancellation, paymentPlanItemIdref, "SKU", "229900", "1", "131",
				"1046378");
		sellerHelper.InsertPaymentPlanItemInstrument("229900", paymentPlanItemIdref, "REFUND");
		sellerHelper.InsertPaymentPlanInstrumentDetail(ppsIdCancellation, "229900", "REFUND", "1");
	}


}