package com.myntra.apiTests.erpservices.rms.dp;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import org.apache.commons.lang.math.RandomUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBException;

/**
 * @author santwana.samantray
 *
 */
public class RmsDP {

	static SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();

	@DataProvider
	public static Object[][] trackingNumberUpdateShouldThrowErrorIReturnIDNotPresent(ITestContext testContext) {
		String[] arr1 = { "flipkart3", getRandomOrderId(), getRandomOrderId() };
		String[] arr2 = { "flipkart4", getRandomOrderId(), getRandomOrderId() };
		String[] arr3 = { "flipkart5", getRandomOrderId(), getRandomOrderId() };
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}

	@DataProvider
	public static Object[][] CreateReturn(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return","APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "2" };

		String[] arr2 = { "flipkart4", getRandomOrderId() + "1", "return_created", "customer_return",
				"APPROVED", getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1","track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnSourceCourier_Return(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "courier_return", "APPROVED",
				getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "2" };

		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "courier_return", "APPROVED",
				getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "3" };

		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	// 2.Create return multiple times same id
	@DataProvider
	public static Object[][] CreateSameReturnMultipleTimes(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		String returnId1 = getRandomOrderId();
		String returnId2 = getRandomOrderId();
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", returnId1, "return_created", "customer_return", "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1", "track123", "200", "2", "StoreReturnId : " + returnId1 + ", StoreId: 2 Already created" };
		String[] arr2 = { "flipkart4", returnId2, "return_created", "customer_return", "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "3", "StoreReturnId : " + returnId2 + ", StoreId: 3 Already created" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateandDeclineReturn(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return", "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return", "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	// have to do
	@DataProvider
	public static Object[][] ReturnCreationDeclineAndMarkComplete(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return", "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return", "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",
				"track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnForOrderNotpresentinSilkroute(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",   
				"track123", "500", "2", "StoreOrderId: 111, Not found" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				getRandomOrderId(), "customer_return", "RETURN_CREATED", "return", "1",   
				"track123", "500", "3", "StoreOrderId: 111, Not found" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithQtyNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", " ",  
				 "track123", "500", "2", "Request Validation Failed: Quantity cannot be null" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", " ",  
				 "track123", "500", "3", "Request Validation Failed: Quantity cannot be null" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithReturnIdNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", " ", "return_created", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "500", "2", "Request Validation Failed: ReturnId cannot be null" };

		String[] arr2 = { "flipkart4", " ", "return_created", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "500", "3", "Request Validation Failed: ReturnId cannot be null" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithOrderItemIdNull(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				" ", "customer_return", "RETURN_CREATED", "return", "1",    "track123",
				"500", "2", "StoreOrderId:  , Not found" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				" ", "customer_return", "RETURN_CREATED", "return", "1",    "track123",
				"500", "3", "StoreOrderId:  , Not found" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithEventTypeNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), " ", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "2", "Event type {0} is not supported" };

		String[] arr2 = { "flipkart4", getRandomOrderId(), " ", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "3", "Event type {0} is not supported" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithTrackingNumberNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "", "200", "2", "Successfully Received" };

		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "", "200", "3", "Successfully Received" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithSourceNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "",  "APPROVED",
				createFlipkart3Order(), " ", "RETURN_CREATED", "return", "1",    "tr123",
				"500", "2", "Request Validation Failed: Source cannot be null" };

		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "",  "APPROVED",
				createFlipkart4Order(), " ", "RETURN_CREATED", "return", "1",    "tr123",
				"500", "3", "Request Validation Failed: Source cannot be null" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateReturnWithSourceLineNull(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response","store_id", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "",  "APPROVED",
				createFlipkart3Order(), " ", "RETURN_CREATED", "return", "1",    "tr123",
				"500", "2", "Request Validation Failed: Source cannot be null" };

		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "",  "APPROVED",
				createFlipkart4Order(), " ", "RETURN_CREATED", "return", "1",    "tr123",
				"500", "3", "Request Validation Failed: Source cannot be null" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] MakrReturnCompleteBeforeCreation(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId","response", "quantity","status_message"};
		String[] arr1 = { "flipkart3", "111111111111", "return_completed", "customer_return", 
				"RETURN_COMPLETED", createFlipkart3Order(), "200", "2", "Successfully Received" };

		String[] arr2 = { "flipkart4", "111111111111", "return_completed", "customer_return", 
				"RETURN_COMPLETED", createFlipkart4Order(), "200", "3", "Successfully Received" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] MakrReturnDeclineBeforeCreation(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId","response", "status_message"};
		String[] arr1 = { "flipkart3", "111111111111", "return_cancelled", "customer_return", 
				"RETURN_CANCELLED", createFlipkart3Order(), "200", "2", "Successfully Received" };

		String[] arr2 = { "flipkart4", "111111111111", "return_cancelled", "customer_return", 
				"RETURN_CANCELLED", createFlipkart4Order(), "200", "3", "Successfully Received" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] ManualMarkReturnQCPassAfterCreation(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response","store_id", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] ManualMarkReturnQCPassAfterCompletion(ITestContext testContext)
			throws IOException, JAXBException, ParseException {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response","store_id", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart3Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId(), "return_created", "customer_return",  "APPROVED",
				createFlipkart4Order(), "customer_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateRTOMarkComplete(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response","store_id", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "courier_return",  "APPROVED",
				getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",   
				"track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId() + "1", "return_created", "courier_return", 
				"APPROVED", getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public static Object[][] CreateRTOMarkDecline(ITestContext testContext) {
		//String[] arr0={"environment", "returnId", "eventType", "source", " status", "orderItemId", "sourceline", "statusline", "action", "quantity", "tracking", "response","store_id", "status_message"};
		String[] arr1 = { "flipkart3", getRandomOrderId(), "return_created", "courier_return",  "APPROVED",
				getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",   
				"track123", "200", "2" };
		String[] arr2 = { "flipkart4", getRandomOrderId() + "1", "return_created", "courier_return", 
				"APPROVED", getRandomOrderId(), "courier_return", "RETURN_CREATED", "return", "1",  
				 "track123", "200", "3" };
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	public static String getRandomOrderId() {
		long epoch = System.currentTimeMillis();
		epoch = (epoch * RandomUtils.nextInt(500)) / 1000;
		String orderId = String.valueOf(epoch);
		// System.out.println(orderId);
		return orderId;
	}

	public void CreateReturnRRQP_data() throws SQLException {

		RMSServiceHelper addData = new RMSServiceHelper();
		addData.InsertOrder("70041385", "cod", "799.00", "801.00", "2.00", "0.00", "0.00", "0.00", "0.00", "0.00",
				"0.00", "1", "0.00");
		addData.InsertOrderRelease("700413856", "70041385", "cod", "799.00", "801.00", "2.00", "0.00", "0.00", "0.00",
				"0.00", "0.00", "0.00", "ML", "ML0008917531", "1", "28", "0.00");
		addData.InsertOrderLine("52697199", "70041385", "700413856", "1531", "4853", "3832", "799.00", "1", "799.00",
				"0.00", "5.50", "1", "0.00", "0.00", "0.00", "NULL", "NULL", "0.00", "ON_HAND", "0.00");
		addData.InsertOrderAdditionalInfo("70041385", "837f4934-8f3d-437f-838b-30ea662632eb");
		addData.InsertPaymentPlan("837f4934-8f3d-437f-838b-30ea662632eb", "70041385", "SALE", "",
				"2a25036d-dc0c-45fa-a8df-fa4a8f00aa11--s3", "JJN006fc3f7cb25a8641f1ada393c6fbc83a0c1454917739M",
				"80100", "NULL");
		addData.InsertPaymentPlanInstrumentDetail("837f4934-8f3d-437f-838b-30ea662632eb", "80100", "DEBIT", "5",
				"19679");
		addData.InsertPaymentPlanItem("837f4934-8f3d-437f-838b-30ea662632eb", "99940966", "SHIPPING_CHARGES", "200",
				"1", "1", "PPS_9999");
		addData.InsertPaymentPlanItem("837f4934-8f3d-437f-838b-30ea662632eb", "99940967", "SKU", "79900", "1", "1",
				"3832");
		addData.InsertPaymentPlanItemInstrument("200", "99940966", "5");
		addData.InsertPaymentPlanItemInstrument("79900", "99940967", "5");

	}

	public void CreateReturnRRQP_delete() throws SQLException {

		OMSServiceHelper remove = new OMSServiceHelper();
		remove.deleteOMSDBEntriesForOrderID("70041385");
		remove.deletePPSRecords("'837f4934-8f3d-437f-838b-30ea662632eb'");

		RMSServiceHelper delete = new RMSServiceHelper();
		delete.DeleteReturn(70041385);

	}

	public void CODOrder_data() throws SQLException {

		RMSServiceHelper addData = new RMSServiceHelper();
		addData.InsertOrder("70044366", "cod", "799.00", "664.77", "2.00", "32.00", "0.00", "167.79", "0.00", "0.00",
				"0.00", "1", "0.00");
		addData.InsertOrderRelease("700443667", "70044366", "cod", "799.00", "664.77", "2.00", "32.00", "0.00", "0.00",
				"167.79", "0.00", "0.00", "ML", "ML0008947444", "1", "28", "0.00");
		addData.InsertOrderLine("52705190", "70044366", "700443667", "1531", "4853", "3832", "799.00", "1", "662.77",
				"31.56", "5.00", "1", "0.00", "0.00", "167.79", "NULL", "NULL", "0.00", "ON_HAND", "0.00");
		addData.InsertOrderAdditionalInfo("70044366", "8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397");
		addData.InsertPaymentPlan("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "70044366", "SALE", "",
				"c277c932-9a05-4b31-b395-11198854f3be--s1", "JJN0021cb40f463bba40d8af9b3eeca49b12ca1457420691M",
				"66477", "NULL");
		addData.InsertPaymentPlanInstrumentDetail("837f4934-8f3d-437f-838b-30ea662632eb", "80100", "DEBIT", "5",
				"19679");

		String paymentPlanexecution = "Insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('9875419','Payment Plan Execution Status created','SYSTEM','1457421552896','DEBIT','COD:358b69e4-7974-431c-9ca7-620d78707864','pps','af855d9d-28ef-46fc-b64d-a426d1368853','0','SALE','PIFSM Payment Successful','0','24368')";
		DBUtilities.exUpdateQuery(paymentPlanexecution, "pps");

		addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "99943708", "SHIPPING_CHARGES", "200",
				"1", "1", "PPS_9999");
		addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "99943709", "SKU", "66277", "1", "1",
				"3832");
		addData.InsertPaymentPlanItemInstrument("200", "99943708", "5");
		addData.InsertPaymentPlanItemInstrument("66277", "99943709", "5");

	}

	public void CODOrder_delete() throws SQLException {

		OMSServiceHelper remove = new OMSServiceHelper();
		remove.deleteOMSDBEntriesForOrderID("70044366");

		RmsDP deletePPS = new RmsDP();
		deletePPS.deletePPSRecords("'8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397'");

		RMSServiceHelper delete = new RMSServiceHelper();
		delete.DeleteReturn(70044366);
	}

	public static Long vectorSellerID2;

	public void TnBOrder_data() throws SQLException {

		String InsertOrder = "insert into `orders` (`id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, `cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, `billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`, `queued_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `order_type`, `loyalty_points_used`, `store_id`, `store_order_id`) values('70061105',NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','9823888800','Pawell Soni','cod',NULL,'','','4196.00','0.00','0.00','0.00','0.00','0.00','4195.97','0.00','0.00','0.00','0.00','0.00','0.00',NULL,NULL,'0','0','','29335772',NULL,NULL,'2016-05-03 15:52:03',NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','2016-05-03 15:48:53','2016-05-03 15:52:03','2','on','0.00','1',NULL)";
		DBUtilities.exUpdateQuery(InsertOrder, "oms");

		String InsertOrderRelease = "insert into `order_release` (`id`, `order_id_fk`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `is_on_hold`, `queued_on`, `invoice_id`, `cheque_no`, `exchange_release_id`, `user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`, `seller_id`) values('700611056','70061105','8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','DL','cod','4196.00','0.00','0.00','0.00','0.00','0.00','4195.97','0.00','0.00','0.00','0.00','0.00','0.00','Pawell Soni ','Myntra Design Pvt. Ltd.','Bangalore','Begur','KA','India','560068','9823888800','pawell.soni@myntra.com','ML','ML1929147942','36','0','pending',NULL,NULL,NULL,NULL,NULL,NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','2016-05-03 15:48:53','2016-05-03 15:54:04','12','0','2016-05-03 15:52:03',NULL,NULL,NULL,'9823888800','NORMAL',NULL,'0.00','1',NULL, '"
				+ vectorSellerID2 + "')";
		DBUtilities.exUpdateQuery(InsertOrderRelease, "oms");

		String InsertOrderLine1 = "insert into `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `po_status`) values('70061891','70061105','700611056','1531','4852','3831','D','799.00','2','0','0.00','0.00','0.00','0.00','0.00','1598.00','0.00','5.00','0.00','0','0',NULL,'0','1',NULL,NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','2016-05-03 15:48:53','2016-05-03 15:52:54','3',NULL,'0.00','"
				+ vectorSellerID2 + "','ON_HAND',NULL,NULL,'UNUSED')";
		DBUtilities.exUpdateQuery(InsertOrderLine1, "oms");

		String InsertOrderLine2 = "insert into `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `po_status`) values('70061892','70061105','700611056','1532','4886','3836','D','1299.00','2','0','0.00','0.00','0.00','0.00','0.00','2598.00','0.00','5.00','0.00','0','0',NULL,'0','1',NULL,NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','2016-05-03 15:48:53','2016-05-03 15:52:54','3',NULL,'0.00','"
				+ vectorSellerID2 + "','ON_HAND',NULL,NULL,'UNUSED')";
		DBUtilities.exUpdateQuery(InsertOrderLine2, "oms");

		String InsertOrderAdditionalInfo = "insert into `order_additional_info` (`id`, `order_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('286534163','70061105','ORDER_PROCESSING_FLOW','OMS','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0'),('286534164','70061105','CHANNEL','web','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0'),('286534165','70061105','LOYALTY_CONVERSION_FACTOR','0.5','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0'),('286534166','70061105','GIFT_CARD_AMOUNT','0.0','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0'),('286534167','70061105','PAYMENT_PPS_ID','2ab80681-c76d-43bd-a870-10094a5ccf1e','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0'),('286534168','70061105','STORED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0'),('286534169','70061105','EARNED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0')";
		// String InsertOrderAdditionalInfo2 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534164','70061105','CHANNEL','web','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo3 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534165','70061105','LOYALTY_CONVERSION_FACTOR','0.5','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo4 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534166','70061105','GIFT_CARD_AMOUNT','0.0','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo5 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534167','70061105','PAYMENT_PPS_ID','2ab80681-c76d-43bd-a870-10094a5ccf1e','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo6 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534168','70061105','STORED_CREDIT_USAGE','0.0','system','2016-05-03
		// 15:52:03','2016-05-03 15:52:03','0')";
		// String InsertOrderAdditionalInfo7 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534169','70061105','EARNED_CREDIT_USAGE','0.0','system','2016-05-03
		// 15:52:03','2016-05-03 15:52:03','0')";
		DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo2, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo3, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo4, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo5, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo6, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo7, "oms");

		String insertorderlineadditional = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404445','70061891','FRAGILE','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional2 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404446','70061891','HAZMAT','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional3 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404447','70061891','JEWELLERY','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional4 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404448','70061891','LOYALTY_CONVERSION_FACTOR','0.5','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional5 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404449','70061891','CUSTOMIZABLE','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional6 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404450','70061891','CUSTOMIZED_MESSAGE','','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional7 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404451','70061891','PACKAGING_TYPE','NORMAL','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional8 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404452','70061891','PACKAGING_STATUS','NOT_PACKAGED','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional9 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404453','70061891','GIFT_CARD_AMOUNT','0.0','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional10 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404454','70061891','GOVT_TAX_RATE','5.500','mobile','2016-05-03 15:51:52','2016-05-03 15:52:54','1')";
		String insertorderlineadditional11 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404455','70061891','GOVT_TAX_AMOUNT','83.31','mobile','2016-05-03 15:51:52','2016-05-03 15:52:54','1')";
		String insertorderlineadditional12 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404456','70061891','IS_EXCHANGEABLE','true','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional13 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404457','70061891','PAYMENT_PPS_ID','2ab80681-c76d-43bd-a870-10094a5ccf1e','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional14 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404458','70061891','TRY_AND_BUY','true','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional15 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404473','70061891','STORED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0')";
		String insertorderlineadditional16 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404474','70061891','EARNED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0')";
		String insertorderlineadditional17 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404477','70061891','GOVT_TAX_TYPE','VAT','System','2016-05-03 15:52:54','2016-05-03 15:52:54','0')";
		String insertorderlineadditional18 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404478','70061891','GOVT_TAXABLE_AMOUNT','1514.69','System','2016-05-03 15:52:54','2016-05-03 15:52:54','0')";
		DBUtilities.exUpdateQuery(insertorderlineadditional, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional2, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional3, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional4, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional5, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional6, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional7, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional8, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional9, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional10, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional11, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional12, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional13, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional14, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional15, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional16, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional17, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional18, "oms");

		String insertorderlineadditional19 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404459','70061892','FRAGILE','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional20 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404460','70061892','HAZMAT','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional21 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404461','70061892','JEWELLERY','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional22 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404462','70061892','LOYALTY_CONVERSION_FACTOR','0.5','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional23 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404463','70061892','CUSTOMIZABLE','false','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional24 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404464','70061892','CUSTOMIZED_MESSAGE','','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional25 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404465','70061892','PACKAGING_TYPE','NORMAL','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional26 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404466','70061892','PACKAGING_STATUS','NOT_PACKAGED','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional27 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404467','70061892','GIFT_CARD_AMOUNT','0.0','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional28 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404468','70061892','GOVT_TAX_RATE','5.500','mobile','2016-05-03 15:51:52','2016-05-03 15:52:54','1')";
		String insertorderlineadditional29 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404469','70061892','GOVT_TAX_AMOUNT','135.44','mobile','2016-05-03 15:51:52','2016-05-03 15:52:54','1')";
		String insertorderlineadditional30 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404470','70061892','IS_EXCHANGEABLE','true','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional31 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404471','70061892','PAYMENT_PPS_ID','2ab80681-c76d-43bd-a870-10094a5ccf1e','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional32 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404472','70061892','TRY_AND_BUY','true','mobile','2016-05-03 15:51:52','2016-05-03 15:51:52','0')";
		String insertorderlineadditional33 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404475','70061892','STORED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0')";
		String insertorderlineadditional34 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404476','70061892','EARNED_CREDIT_USAGE','0.0','system','2016-05-03 15:52:03','2016-05-03 15:52:03','0')";
		String insertorderlineadditional35 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404479','70061892','GOVT_TAX_TYPE','VAT','System','2016-05-03 15:52:54','2016-05-03 15:52:54','0')";
		String insertorderlineadditional36 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('1089404480','70061892','GOVT_TAXABLE_AMOUNT','2462.56','System','2016-05-03 15:52:54','2016-05-03 15:52:54','0')";
		DBUtilities.exUpdateQuery(insertorderlineadditional19, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional20, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional21, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional22, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional23, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional24, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional25, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional26, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional27, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional28, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional29, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional30, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional31, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional32, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional33, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional34, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional35, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional36, "oms");

		String insertpaymentplan = "insert into `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`, `bankAccountId`) values('2ab80681-c76d-43bd-a870-10094a5ccf1e','PPS Plan created','SYSTEM','1462270733271','SALE',NULL,NULL,'8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R','70061105','ORDER','aa06c65a-3840-41dc-bd9f-4b6a3cdfd22f--s2','PPFSM Order Taking done','JJN001ce485728bfed41efa3c7dbd359e3a2b61462270657M','DEFAULT','419600',NULL,NULL,NULL,'1.1.1.1',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplan, "pps");

		String insertpaymentplanexecution1 = "insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('9992671','Payment Plan Execution Status created','SYSTEM','1462270734338','DEBIT','13752894','pps','1e87991a-5676-497c-ac98-25cc618d4441','0','SALE','PIFSM Payment Successful','0','49548')";
		String insertpaymentplanexecution2 = "insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('9992669','Payment Plan Execution Status created','SYSTEM','1462270733927','DEBIT','COD:f7b1d042-a79f-4d88-b0df-d033b12af314','pps','14b88182-e7ee-4cd1-a3af-53ba6d4e5cd2','0','SALE','PIFSM Payment Successful','0','49549')";
		String insertpaymentplanexecution3 = "insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('9992670','Payment Plan Execution Status created','SYSTEM','1462270734244','DEBIT','19895646','pps','fc44e984-6e87-4775-826e-182d0f949910','0','SALE','PIFSM Payment Successful','0','49550')";
		DBUtilities.exUpdateQuery(insertpaymentplanexecution1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplanexecution2, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplanexecution3, "pps");

		String insertpaymentplaninstrumentdetail1 = "insert into `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) values('49548','PPS Plan Instrument Details created','SYSTEM','1462270733550','0','29999','2ab80681-c76d-43bd-a870-10094a5ccf1e','9992671','DEBIT',NULL)";
		String insertpaymentplaninstrumentdetail2 = "insert into `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) values('49549','PPS Plan Instrument Details created','SYSTEM','1462270733550','5','379599','2ab80681-c76d-43bd-a870-10094a5ccf1e','9992669','DEBIT',NULL)";
		String insertpaymentplaninstrumentdetail3 = "insert into `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) values('49550','PPS Plan Instrument Details created','SYSTEM','1462270733550','7','9999','2ab80681-c76d-43bd-a870-10094a5ccf1e','9992670','DEBIT',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplaninstrumentdetail1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplaninstrumentdetail2, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplaninstrumentdetail3, "pps");

		String insertpaymentplanitem1 = "insert into `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) values('997949032','Payment Plan Item created','SYSTEM','1462270733551','SKU','79900','2','1','3831','2ab80681-c76d-43bd-a870-10094a5ccf1e')";
		String insertpaymentplanitem2 = "insert into `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) values('997949033','Payment Plan Item created','SYSTEM','1462270733551','SKU','129900','2','1','3836','2ab80681-c76d-43bd-a870-10094a5ccf1e')";
		DBUtilities.exUpdateQuery(insertpaymentplanitem1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplanitem2, "pps");

		String insertpaymentplaniteminstrument1 = "insert into `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) values('80020','Payment Plan Item Instrument Detail created','SYSTEM','1462270733597','144566','5','997949032',NULL)";
		String insertpaymentplaniteminstrument2 = "insert into `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) values('80021','Payment Plan Item Instrument Detail created','SYSTEM','1462270733594','3808','7','997949032',NULL)";
		String insertpaymentplaniteminstrument3 = "insert into `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) values('80022','Payment Plan Item Instrument Detail created','SYSTEM','1462270733592','11425','0','997949032',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplaniteminstrument1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplaniteminstrument2, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplaniteminstrument3, "pps");

		// +addData.InsertOrder("70044366", "cod", "799.00", "664.77", "2.00",
		// "32.00", "0.00", "167.79", "0.00", "0.00", "0.00", "1", "0.00");
		// +addData.InsertOrderRelease("70044366", "70044366", "cod", "799.00",
		// "664.77", "2.00", "32.00", "0.00", "0.00", "167.79", "0.00", "0.00",
		// "ML", "ML0008947444", "1", "28", "0.00");
		// +addData.InsertOrderLine("52705190", "70044366", "70044366", "1531",
		// "4853", "3832", "799.00", "1", "662.77", "31.56", "5.00", "1",
		// "0.00", "0.00", "167.79", "NULL", "NULL", "0.00", "ON_HAND", "0.00");
		// +addData.InsertOrderAdditionalInfo("70044366",
		// "8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397");
		// +insertorderlineadditional info
		// +addData.InsertPaymentPlan("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397",
		// "70044366", "SALE", "", "c277c932-9a05-4b31-b395-11198854f3be--s1",
		// "JJN0021cb40f463bba40d8af9b3eeca49b12ca1457420691M", "66477",
		// "NULL");
		// +addData.InsertPaymentPlanInstrumentDetail("837f4934-8f3d-437f-838b-30ea662632eb",
		// "80100", "DEBIT", "5", "19679");

		// +String paymentPlanexecution = "Insert into
		// `payment_plan_execution_status` (`id`, `comments`, `updatedBy`,
		// `updatedTimestamp`, `actionType`, `instrumentTransactionId`,
		// `invoker`, `invokerTransactionId`, `numOfRetriesDone`,
		// `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`)
		// values('9875419','Payment Plan Execution Status
		// created','SYSTEM','1457421552896','DEBIT','COD:358b69e4-7974-431c-9ca7-620d78707864','pps','af855d9d-28ef-46fc-b64d-a426d1368853','0','SALE','PIFSM
		// Payment Successful','0','24368')";
		// +DBUtilities.exUpdateQuery(paymentPlanexecution, "pps");

		// +addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397",
		// "99943708", "SHIPPING_CHARGES", "200", "1", "1", "PPS_9999");
		// +addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397",
		// "99943709", "SKU", "66277", "1", "1", "3832");
		// +addData.InsertPaymentPlanItemInstrument("200", "99943708", "5");
		// +addData.InsertPaymentPlanItemInstrument("66277", "99943709", "5");

	}

	public void TnBOrder_delete() throws SQLException {

		OMSServiceHelper remove = new OMSServiceHelper();
		remove.deleteOMSDBEntriesForOrderID("70061105");

		RmsDP deletePPS = new RmsDP();
		deletePPS.deletePPSRecords("'2ab80681-c76d-43bd-a870-10094a5ccf1e'");

		String deletePaymentPlanExecutionStatus = "DELETE FROM `payment_plan_execution_status` where `id`=9992671;";
		String deletePaymentPlanExecutionStatus2 = "DELETE FROM `payment_plan_execution_status` where `id`=9992669;";
		String deletePaymentPlanExecutionStatus3 = "DELETE FROM `payment_plan_execution_status` where `id`=9992670;";
		DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus2, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus3, "pps");

		RMSServiceHelper delete = new RMSServiceHelper();
		delete.DeleteReturn(70061105);
	}

	public void NewOrder_data() throws SQLException {

		String InsertOrder = "insert into `orders` (`id`, `store_order_id`, `invoice_id`, `login`, `user_contact_no`, `customer_name`, `payment_method`, `status_code`, `coupon_code`, `cash_coupon_code`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `request_server`, `response_server`, `is_on_hold`, `is_gift`, `notes`, `billing_address_id_fk`, `cancellation_reason_id_fk`, `on_hold_reason_id_fk`, `queued_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `order_type`, `loyalty_points_used`, `store_id`) values('2147263062','103670947835364167301',NULL,'6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','9823888800','Pawell','cod',NULL,'','','799.00','0.00','0.00','0.00','0.00','0.00','801.00','2.00','0.00','0.00','0.00','0.00','0.00',NULL,NULL,'0','0','',NULL,NULL,'39','2017-01-19 18:35:11',NULL,'6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','2017-01-19 18:35:06','2017-01-19 18:35:22','3','on','0.00','1')";
		DBUtilities.exUpdateQuery(InsertOrder, "oms");

		String InsertOrderRelease = "insert into `order_release` (`id`, `order_id_fk`, `store_order_id`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, `shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, `courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `is_on_hold`, `queued_on`, `invoice_id`, `cheque_no`, `exchange_release_id`, `user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`, `seller_id`) values('2147439245','2147263062','103670947835364167301','6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','WP','cod','799.00','0.00','0.00','0.00','0.00','0.00','801.00','2.00','0.00','0.00','0.00','0.00','0.00','Test','Myntra','Bangalore','Bommanahalli  &#x28;Bangalore&#x29;','KA','India','560068','9823888800','pawell.soni@myntra.com','ML','ML0000453067','36','0','pending',NULL,NULL,NULL,NULL,NULL,NULL,'6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','2017-01-19 18:35:06','2017-01-19 18:40:22','10','0','2017-01-19 18:35:11',NULL,NULL,NULL,'9823888800','NORMAL',NULL,'0.00','1',NULL,NULL)";
		DBUtilities.exUpdateQuery(InsertOrderRelease, "oms");

		String InsertOrderLine1 = "insert into `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `store_order_id`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `po_status`) values('999962984','2147263062','2147439245','103670947835364167301','1531','4854','3833','A','799.00','1','0','0.00','0.00','0.00','0.00','0.00','799.00','0.00','0.00','0.00','0','0',NULL,'0','1',NULL,NULL,'6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','2017-01-19 18:35:06','2017-01-19 18:37:22','2',NULL,'0.00','21','ON_HAND',NULL,NULL,'UNUSED')";
		DBUtilities.exUpdateQuery(InsertOrderLine1, "oms");

		String InsertOrderAdditionalInfo = "insert into `order_additional_info` (`id`, `order_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('750745716','2147263062','ORDER_PROCESSING_FLOW','OMS','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745717','2147263062','CHANNEL','web','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745718','2147263062','LOYALTY_CONVERSION_FACTOR','0.0','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745719','2147263062','GIFT_CARD_AMOUNT','0.0','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745720','2147263062','PAYMENT_PPS_ID','72747077-212b-4063-b518-fc8392b3614b','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745721','2147263062','ADDITIONAL_CHARGES_SELLER_ID','21','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0'),('750745722','2147263062','STORED_CREDIT_USAGE','0.0','pps-admin','2017-01-19 18:35:11','2017-01-19 18:35:11','0'),('750745723','2147263062','EARNED_CREDIT_USAGE','0.0','pps-admin','2017-01-19 18:35:11','2017-01-19 18:35:11','0')";
		// String InsertOrderAdditionalInfo2 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534164','70061105','CHANNEL','web','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo3 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534165','70061105','LOYALTY_CONVERSION_FACTOR','0.5','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo4 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534166','70061105','GIFT_CARD_AMOUNT','0.0','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo5 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534167','70061105','PAYMENT_PPS_ID','2ab80681-c76d-43bd-a870-10094a5ccf1e','mobile','2016-05-03
		// 15:51:52','2016-05-03 15:51:52','0')";
		// String InsertOrderAdditionalInfo6 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534168','70061105','STORED_CREDIT_USAGE','0.0','system','2016-05-03
		// 15:52:03','2016-05-03 15:52:03','0')";
		// String InsertOrderAdditionalInfo7 ="insert into
		// `order_additional_info` (`id`, `order_id_fk`, `key`, `value`,
		// `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('286534169','70061105','EARNED_CREDIT_USAGE','0.0','system','2016-05-03
		// 15:52:03','2016-05-03 15:52:03','0')";
		DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo2, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo3, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo4, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo5, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo6, "oms");
		// DBUtilities.exUpdateQuery(InsertOrderAdditionalInfo7, "oms");

		String insertorderlineadditional = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600910','999962984','FRAGILE','false','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional2 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600911','999962984','HAZMAT','false','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional3 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600912','999962984','JEWELLERY','false','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional4 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600913','999962984','CUSTOMIZABLE','false','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional5 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600914','999962984','PACKAGING_TYPE','NORMAL','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional6 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600915','999962984','PACKAGING_STATUS','NOT_PACKAGED','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional7 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600916','999962984','GIFT_CARD_AMOUNT','0.0','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional8 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600917','999962984','IS_EXCHANGEABLE','true','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional9 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600918','999962984','PAYMENT_PPS_ID','72747077-212b-4063-b518-fc8392b3614b','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional10 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600919','999962984','TRY_AND_BUY','false','pps-admin','2017-01-19 18:35:06','2017-01-19 18:35:06','0')";
		String insertorderlineadditional11 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600920','999962984','STORED_CREDIT_USAGE','0.0','pps-admin','2017-01-19 18:35:11','2017-01-19 18:35:11','0')";
		String insertorderlineadditional12 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555600921','999962984','EARNED_CREDIT_USAGE','0.0','pps-admin','2017-01-19 18:35:11','2017-01-19 18:35:11','0')";
		String insertorderlineadditional13 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555601020','999962984','GOVT_TAX_RATE','5.500','erpMessageQueue','2017-01-19 18:37:22','2017-01-19 18:37:22','0')";
		String insertorderlineadditional14 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555601021','999962984','GOVT_TAX_TYPE','VAT','erpMessageQueue','2017-01-19 18:37:22','2017-01-19 18:37:22','0')";
		String insertorderlineadditional15 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555601022','999962984','GOVT_TAX_AMOUNT','41.76','erpMessageQueue','2017-01-19 18:37:22','2017-01-19 18:37:22','0')";
		// String insertorderlineadditional16 ="insert into
		// `order_line_additional_info` (`id`, `order_line_id_fk`, `key`,
		// `value`, `created_by`, `created_on`, `last_modified_on`, `version`)
		// values('2555601022','999962984','GOVT_TAX_AMOUNT','41.76','erpMessageQueue','2017-01-19
		// 18:37:22','2017-01-19 18:37:22','0')";
		String insertorderlineadditional17 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555601023','999962984','GOVT_TAXABLE_AMOUNT','759.24','erpMessageQueue','2017-01-19 18:37:22','2017-01-19 18:37:22','0')";
		String insertorderlineadditional18 = "insert into `order_line_additional_info` (`id`, `order_line_id_fk`, `key`, `value`, `created_by`, `created_on`, `last_modified_on`, `version`) values('2555601024','999962984','GOVT_UNIT_TAX_AMOUNT','41.76','erpMessageQueue','2017-01-19 18:37:22','2017-01-19 18:37:22','0')";
		DBUtilities.exUpdateQuery(insertorderlineadditional, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional2, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional3, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional4, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional5, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional6, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional7, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional8, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional9, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional10, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional11, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional12, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional13, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional14, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional15, "oms");
		// DBUtilities.exUpdateQuery(insertorderlineadditional16, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional17, "oms");
		DBUtilities.exUpdateQuery(insertorderlineadditional18, "oms");

		String insertpaymentplan = "insert into `payment_plan` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `clientTransactionId`, `crmRefId`, `login`, `orderId`, `ppsType`, `sourceId`, `state`, `sessionId`, `cartContext`, `totalAmount`, `mobile`, `returnId`, `userAgent`, `clientIP`, `bankAccountId`) values('72747077-212b-4063-b518-fc8392b3614b','PPS Plan created','SYSTEM','1484831106573','SALE',NULL,NULL,'6b273eb3.5de1.4789.98c6.0b0710a9ee44estlAuwtuu','103670947835364167301','ORDER','af03d5ba-7b13-4a48-979a-3c836a24ef2d--s2','PPFSM Order Taking done','JJNc49ddddade4711e6b03422000a90a0271484831050G','DEFAULT','80100','9823888800',NULL,'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36 ','182.156.75.166',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplan, "pps");

		String insertpaymentplanexecution1 = "insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('385512699','Payment Plan Execution Status created','SYSTEM','1484831106531','DEBIT','COD:80872be9-5371-4521-b508-472829ae5174','pps','e228c515-2849-4597-9b70-0d5ea349a47d','0','SALE','PIFSM Payment Successful','0','79998410597')";
		DBUtilities.exUpdateQuery(insertpaymentplanexecution1, "pps");

		String insertpaymentplaninstrumentdetail1 = "insert into `payment_plan_instrument_details` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `paymentInstrumentType`, `totalPrice`, `pps_Id`, `paymentPlanExecutionStatus_id`, `actionType`, `parentInstrumentDetailId`) values('79998410597','PPS Plan Instrument Details created','SYSTEM','1484831106531','5','80100','72747077-212b-4063-b518-fc8392b3614b','385512699','DEBIT',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplaninstrumentdetail1, "pps");

		String insertpaymentplanitem1 = "insert into `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) values('79998519202','Payment Plan Item created','SYSTEM','1484831106452','SKU','79900','1','21','3833','72747077-212b-4063-b518-fc8392b3614b')";
		String insertpaymentplanitem2 = "insert into `payment_plan_item` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `itemType`, `pricePerUnit`, `quantity`, `sellerId`, `skuId`, `pps_Id`) values('79998519203','Payment Plan Item created','SYSTEM','1484831106456','SHIPPING_CHARGES','200','1','21','PPS_9999','72747077-212b-4063-b518-fc8392b3614b')";
		DBUtilities.exUpdateQuery(insertpaymentplanitem1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplanitem2, "pps");

		String insertpaymentplaniteminstrument1 = "insert into `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) values('79998612204','Payment Plan Item Instrument Detail created','SYSTEM','1484831106454','79900','5','79998519202',NULL)";
		String insertpaymentplaniteminstrument2 = "insert into `payment_plan_item_instrument` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `amount`, `paymentInstrumentType`, `ppsItemId`, `actionType`) values('79998612205','Payment Plan Item Instrument Detail created','SYSTEM','1484831106457','200','5','79998519203',NULL)";
		DBUtilities.exUpdateQuery(insertpaymentplaniteminstrument1, "pps");
		DBUtilities.exUpdateQuery(insertpaymentplaniteminstrument2, "pps");

	}

	public void NewOrder_delete() throws SQLException {

		OMSServiceHelper remove = new OMSServiceHelper();
		remove.deleteOMSDBEntriesForOrderID("2147263062");

		String orderLineAdditionalInfo = "DELETE from `order_line_additional_info` where `order_line_id_fk`=999962984;";
		DBUtilities.exUpdateQuery(orderLineAdditionalInfo, "oms");

		RmsDP deletePPS = new RmsDP();
		deletePPS.deletePPSRecords("'72747077-212b-4063-b518-fc8392b3614b'");

		String deletePaymentPlanExecutionStatus = "DELETE FROM `payment_plan_execution_status` where `id`=385512699;";
		DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus, "pps");

		RMSServiceHelper delete = new RMSServiceHelper();
		delete.DeleteReturn(2147263062);
	}

	public void deletePPSRecords(String ppsID) throws SQLException {
		String deletePaymentPlan = "DELETE FROM `payment_plan` where id in (" + ppsID + ");";
		String deletePaymentPlanItem = "DELETE FROM `payment_plan_item` where `pps_Id` in (" + ppsID + ");";
		String deletePaymentPlanItemInstrument = "DELETE FROM `payment_plan_item_instrument` where ppsItemId in (select id from `payment_plan_item` where `pps_Id` in ("
				+ ppsID + "));";
		String deletePaymentInstrumentDetails = "DELETE FROM `payment_plan_instrument_details` where `pps_Id` in ("
				+ ppsID + ");";
		String deletePaymentPlanExecutionStatus = "DELETE FROM `payment_plan_execution_status` where `id`=9875419;";
		DBUtilities.exUpdateQuery(deletePaymentInstrumentDetails, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlanExecutionStatus, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlanItemInstrument, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlanItem, "pps");
		DBUtilities.exUpdateQuery(deletePaymentPlan, "pps");
	}

	public void PurePlay_CODOrder_data() throws SQLException {

		RMSServiceHelper addData = new RMSServiceHelper();
		addData.InsertOrder("70044366", "cod", "799.00", "664.77", "2.00", "32.00", "0.00", "167.79", "0.00", "0.00",
				"0.00", "1", "0.00");
		addData.InsertOrderRelease("700443667", "70044366", "cod", "799.00", "664.77", "2.00", "32.00", "0.00", "0.00",
				"167.79", "0.00", "0.00", "", "", "1", "28", "0.00");
		addData.InsertOrderLine("52705190", "70044366", "700443667", "1531", "4853", "3832", "799.00", "1", "662.77",
				"31.56", "5.00", "100", "0.00", "0.00", "167.79", "NULL", "NULL", "0.00", "ON_HAND", "0.00");
		addData.InsertOrderAdditionalInfo("70044366", "8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397");
		addData.InsertPaymentPlan("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "70044366", "SALE", "",
				"c277c932-9a05-4b31-b395-11198854f3be--s1", "JJN0021cb40f463bba40d8af9b3eeca49b12ca1457420691M",
				"66477", "NULL");
		addData.InsertPaymentPlanInstrumentDetail("837f4934-8f3d-437f-838b-30ea662632eb", "80100", "DEBIT", "5",
				"19679");

		String paymentPlanexecution = "Insert into `payment_plan_execution_status` (`id`, `comments`, `updatedBy`, `updatedTimestamp`, `actionType`, `instrumentTransactionId`, `invoker`, `invokerTransactionId`, `numOfRetriesDone`, `ppsActionType`, `state`, `status`, `paymentPlanInstrumentDetailId`) values('9875419','Payment Plan Execution Status created','SYSTEM','1457421552896','DEBIT','COD:358b69e4-7974-431c-9ca7-620d78707864','pps','af855d9d-28ef-46fc-b64d-a426d1368853','0','SALE','PIFSM Payment Successful','0','24368')";
		DBUtilities.exUpdateQuery(paymentPlanexecution, "pps");

		addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "99943708", "SHIPPING_CHARGES", "200",
				"1", "1", "PPS_9999");
		addData.InsertPaymentPlanItem("8fac01cf-71e4-40f7-8b9f-ce7a0f8bb397", "99943709", "SKU", "66277", "1", "1",
				"3832");
		addData.InsertPaymentPlanItemInstrument("200", "99943708", "5");
		addData.InsertPaymentPlanItemInstrument("66277", "99943709", "5");

	}

	/**
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParseException
	 */
	private static String createFlipkart4Order() throws IOException, JAXBException, ParseException {
		// TODO Auto-generated method stub
		String fkOrderId = silkRouteServiceHelper
				.store_release_id(SilkRouteConstants.FK4, getRandomOrderId(), "order_item_created", "APPROVED", "false",
						SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
						SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001")
				.get(0);

		return fkOrderId;
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParseException
	 */
	private static String createFlipkart3Order() throws IOException, JAXBException, ParseException {
		// TODO Auto-generated method stub
		String fkOrderId = silkRouteServiceHelper
				.store_release_id(SilkRouteConstants.FK3, getRandomOrderId(), "order_item_created", "APPROVED", "false",
						SilkRouteConstants.DISPATCH_DATE, SilkRouteConstants.DISPATCH_DATE, "5", "0",
						SilkRouteConstants.FK_LISTING_ID, SilkRouteConstants.SKU_CODE_NIKE, "200", "560001")
				.get(0);
		return fkOrderId;
	}

}