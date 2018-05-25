package com.myntra.apiTests.erpservices.marketplace;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.test.commons.testbase.BaseTest;

public class RmsGatePassEndToEnd extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(RmsGatePassEndToEnd.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();
	// random generator
	static String id_order_line = randomGen(9);
	static String order_id_fk = randomGen(9);
	static String id_return_line = randomGen(9);
	static String order_release_id_fk = randomGen(9);
	static String return_id = randomGen(9);
	static String item_barcode = randomGen(9);
	static String gatepassId;
	static String gatepassIdRTV;

	public static String randomGen(int length) {
		Random random = new Random();
		int n = random.nextInt(10);
		for (int i = 0; i < length - 1; i++) {
			n = n * 10;
			n += random.nextInt(10);
		}
		String ar = Integer.toString(n);
		return ar;
	}

	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException {
		// Insert query for inserting dummy data to create gatepass
		System.out.println(" Order line id: " + id_order_line + " order id fk: " + order_id_fk + " id order line: "
				+ id_return_line + " order release id fk: " + order_release_id_fk + " return id: " + return_id
				+ " item barcode: " + item_barcode + "");

		String query = "INSERT INTO `order_line` (`id`, `order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, `discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, `cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, `cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, `supply_type`, `vendor_id`, `store_line_id`, `po_status`) VALUES("
				+ id_order_line + ", " + order_id_fk + "," + order_release_id_fk + ","
				+ "28746, 118997, 125848, 'A', 550.00, 1, 1,137.50, 0.00, 0.00, 0.00, 0.00, 412.50, 0.00, 4.00, 0.00, 0, 0, NULL, 0, 0, NULL, NULL, 'shubham2012@gmail.com', '2015-05-07 12:42:02', '2015-08-18 14:45:29', 2, NULL, 0.00, 5, 'ON_HAND', NULL, NULL, 'UNUSED')";
		DBUtilities.exUpdateQuery(query, "oms");
		String query1 = "INSERT INTO `return_line` (`id`, `return_id`, `order_id`, `order_release_id`, `order_line_id`, `exchange_id`, `sku_id`, `option_id`, `return_reason_id`, `unit_price`, `refund_amount`, `is_refunded`, `refunded_on`, `rejected_on`, `restocked_on`, `qa_pass_on`, `qa_fail_on`, `sv_pass_on`, `sv_fail_on`, `cc_review_pass_on`, `cc_review_fail_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `status_code`, `item_barcode`, `supply_type`, `warehouse_id`, `group_key`, `style_id`, `sku_code`, `quality`,`seller_id`) VALUES("
				+ id_return_line + "," + return_id + "," + order_id_fk + "," + order_release_id_fk + "," + id_order_line
				+ ", NULL, 881915, 884610, 13, 550.00, 0.00, 0, NULL, NULL, NULL, NULL, '2015-06-24 14:45:07', '2015-06-24 14:45:07', NULL, NULL, NULL, 'myntrapptest@gmail.com', '2015-04-07 15:35:20', '2015-07-10 12:34:15', 17, 'RPI', "
				+ item_barcode + ", 'ON_HAND', 45, NULL, 263347, 'test123', NULL,5);";
		DBUtilities.exUpdateQuery(query1, "rms");
		System.out.println("The DB entry is sucessfull");
	}

	@AfterClass(alwaysRun = true)
	public void testAfterClass() throws SQLException{
		String updateOrderLine = "DELETE from order_line where id = "+id_order_line+"";
		DBUtilities.exUpdateQuery(updateOrderLine, "oms");
		
		String updateReturnLine = "DELETE from return_line where id = "+id_return_line+"";
		DBUtilities.exUpdateQuery(updateReturnLine, "rms");
		
	}

	/** API Name - Create GatePass Stock Transfer **/
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "createGatePass_StockTransfer",description="1.Creates Stock Transfer Return GatePass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_CreateGatePass_ST(String fromPartyId, String toPartyId, String type, String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSSTR,
				init.Configurations, new String[] { fromPartyId, toPartyId, type, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		gatepassId = req.respvalidate.NodeValue("data.id", true).replaceAll("\"", "").trim();
		System.out.println("Stock transfer Return Gate pass created and Gatepass ID is: " + gatepassId);
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	/** API Name - Create GatePass Item for stock transfer **/

	@Test(groups = { "Regression" }, priority = 1, dataProvider = "createGatePassItem_ST",description="1.Scanning Item Barcode to ST GatePass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_ScanItem_ST(String expectedResult) {
		System.out.println("Gate Pass Id " + gatepassId + "    " + item_barcode);
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSITEM,
				init.Configurations, new String[] { gatepassId, item_barcode, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		System.out.println(service.Payload);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println("Response" + res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Item barcode Scanned Successfull");
	}

	/** API Name - Status update Gatepass (mark gatePass IN_TRANSIT) **/

	@Test(groups = { "Regression" }, priority = 2, dataProvider = "statusUpdateGatepass",description="1.Updating Gatepass to Intransit State", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_UpdateGatepassIntransit_ST(String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.STATUSUPDATEGATEPASS,
				init.Configurations, new String[] { gatepassId, expectedResult }, PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully (IN_TRANSIT)");
	}

	/**
	 * API Name - Update gatepass item (inbound scan - Item Received)
	 * 
	 * @throws SQLException
	 **/

	@Test(groups = { "Regression" }, priority = 3, dataProvider = "updateGatepassItem",description="1.Updating Gatepass to Receiving State", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_updateGatepassReceived_ST(String expectedResult) throws SQLException {

		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.UPDATEGATEPASSITEM,
				init.Configurations, new String[] { gatepassId, item_barcode, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());

		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully inbound (RECEIVED)");

	}

	// ** API Name - Status update Gatepass_Closed (mark gatePass CLOSED) **/

	@Test(groups = { "Regression" }, priority = 4, dataProvider = "statusUpdateGatepass_Closed",description="1.Updating Gatepass to Closed State", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_UpdateGatepassClosed_ST(String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS,
				APINAME.STATUSUPDATEGATEPASSCLOSE, init.Configurations, new String[] { gatepassId, expectedResult },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully (Closed)");
	}

	// Create Gatepass RTV
	@Test(groups = { "Regression" }, priority = 5, dataProvider = "createGatePassRTV",description="1.Creates Return to Seller Return Gatepass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Retrn_createGatePass_RTV(String fromPartyId, String toPartyId, String type,
			String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSRTV,
				init.Configurations, new String[] { fromPartyId, toPartyId, type, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		gatepassIdRTV = req.respvalidate.NodeValue("data.id", true).replaceAll("\"", "").trim();
		System.out.println("RTV Return Gatepass created and Gatepass ID is: " + gatepassIdRTV);
		APIUtilities.validateResponse("json", res, expectedResult);

	}

	// ** API Name - Create GatePass Item for RTV **//*

	@Test(groups = { "Regression" }, priority = 6, dataProvider = "createGatePassItem_RTV",description="1.Scan Item barcode to RTV Gatepass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_ScanItem_RTV(String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSITEM,
				init.Configurations, new String[] { gatepassIdRTV, item_barcode, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Item barcode Scanned Successfull");
	}

	// ** API Name - Status update Gatepass (mark gatePass status Intransit from
	// RTV) **//*

	@Test(groups = { "Regression" }, priority = 7, dataProvider = "statusUpdateGatepassRTV_IN_TRANSIT",description="1.Updating Gatepass to Intransit State", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_UpdateGatepassIntransit_RTV(String expectedResult) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.STATUSUPDATEGATEPASS,
				init.Configurations, new String[] { gatepassIdRTV, expectedResult }, PayloadType.JSON,
				PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully (IN_TRANSIT)");
	}
	
	// ** API Name - Status update Gatepass_Closed (mark gatePass CLOSED) **/

			@Test(groups = { "Regression" }, priority = 8, dataProvider = "statusUpdateGatepass_Closed",description="1.Updating Gatepass to Closed State", dataProviderClass = RmsGatePassEndToEndDP.class)
			public void Return_UpdateGatepassClosed_RTV(String expectedResult) {
				MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS,
						APINAME.STATUSUPDATEGATEPASSCLOSE, init.Configurations, new String[] { gatepassIdRTV, expectedResult },
						PayloadType.JSON, PayloadType.JSON);

				HashMap getParam = new HashMap();
				getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

				System.out.println(service.URL);
				RequestGenerator req = new RequestGenerator(service, getParam);
				String res = req.respvalidate.returnresponseasstring();
				AssertJUnit.assertEquals(200, req.response.getStatus());
				APIUtilities.validateResponse("json", res, expectedResult);
				System.out.println("Gatepass Updated successfully (Closed)");
			}


	// ---------------------------------------------------------------------

	@Test(groups = { "Regression" }, priority = 8, dataProvider = "createGatePassNegativeSTR",description="1.Select Stock Transfer Return Gatepass \n 2.Giving invalid from and toparty ", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_GatePassNegativeST(String fromPartyId, String toPartyId, String type,
			String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSSTR,
				init.Configurations,
				new String[] { fromPartyId, toPartyId, type, paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("create gatepass statusCode is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("create gatepass statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("create gatepass statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	@Test(groups = { "Regression" }, priority = 9, dataProvider = "createGatePassNegativeRTV",description="1.Select Return to Seller Return Retunr Gatepass \n 2.Giving invalid from and toparty ", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_GatePassNegativeRTV(String fromPartyId, String toPartyId, String type,
			String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSRTV,
				init.Configurations,
				new String[] { fromPartyId, toPartyId, type, paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("create gatepass statusCode is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("create gatepass statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("create gatepass statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - Get To Party **//*

	@Test(groups = { "Regression" }, priority = 10, dataProvider = "getToParty",description="1.Gets the list of Seller associated with warehouse", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_getToParty(String id, String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.GETTOPARTY,
				init.Configurations, PayloadType.JSON, new String[] { id }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get to Party statusCode is invalid, Expected: <" + paramStatusCode + "> but Actual: <"
				+ responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get to Party statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get to Party statusType is invalid, Expected: <" + paramStatusType + "> but Actual: <"
				+ responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - Get Gate Pass Types **//*

	@Test(groups = { "Regression" }, priority = 11, dataProvider = "getGetPassTypes",description="1.Gets the list of all the Gatepass types", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_getGatePassTypes(String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.GETGATEPASSTYPES,
				init.Configurations, new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get Gate Pass Types statusCode is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get Gate Pass Types statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get Gate Pass Types statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - Get Gate Pass item **//*

	@Test(groups = { "Regression" }, priority = 12, dataProvider = "getGetPassItem",description="1.Gets the list of all the Gatepass items in a gatepass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_getGatePassItem(String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.GETGATEPASSITEM,
				init.Configurations, new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get Gate Pass item response code is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get Gate Pass item statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get Gate Pass item statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - Search Gatepass **//*

	@Test(groups = { "Regression" }, priority = 13, dataProvider = "searchGatepass",description="1.Search for all the Gatepass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_searchGatepass(String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.SEARCHGATEPASS,
				init.Configurations, new String[] { paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get Gate Pass item response code is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get Gate Pass item statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get Gate Pass item statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - Get RTV to Party **//*

	@Test(groups = { "Regression" }, priority = 14, dataProvider = "getRTVtoParty",description="1.Gets the list of Seller associated with warehouse", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_getRTVtoParty(String id, String paramStatusCode, String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.GETRTVTOPARTY,
				init.Configurations, PayloadType.JSON, new String[] { id }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get Gate Pass item response code is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get Gate Pass item statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get Gate Pass item statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

	//** API Name - create gatepass negative TCs **//*

	@Test(groups = { "Regression" }, priority = 15, dataProvider = "createGatePassItem_negative",description="1.Scanning invalid Item Barcode to gatepass", dataProviderClass = RmsGatePassEndToEndDP.class)
	public void Return_GatePassItemNegative(String gatePassId, String scannedCode, String paramStatusCode,
			String paramStatusMessage, String paramStatusType) {
		MyntraService service = Myntra.getService(ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSITEM,
				init.Configurations,
				new String[] { gatePassId, scannedCode, paramStatusCode, paramStatusMessage, paramStatusType },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);

		AssertJUnit.assertEquals(200, req.response.getStatus());

		String responseStatusCode = req.respvalidate.NodeValue(StatusNodes.STATUS_CODE.toString(), false).replace("\"",
				"");
		String responseStatusMessage = req.respvalidate.NodeValue(StatusNodes.STATUS_MESSAGE.toString(), false)
				.replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(StatusNodes.STATUS_TYPE.toString(), false).replace("\"",
				"");

		AssertJUnit.assertTrue("Get Gate Pass item response code is invalid, Expected: <" + paramStatusCode
				+ "> but Actual: <" + responseStatusCode + ">", responseStatusCode.equals(paramStatusCode));

		AssertJUnit.assertTrue("Get Gate Pass item statusMessage is invalid, Expected: <" + paramStatusMessage
				+ "> but Actual: <" + responseStatusMessage + ">", responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue("Get Gate Pass item statusType is invalid, Expected: <" + paramStatusType
				+ "> but Actual: <" + responseStatusType + ">", responseStatusType.equals(paramStatusType));
	}

}
