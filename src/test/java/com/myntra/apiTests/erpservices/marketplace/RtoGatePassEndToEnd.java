package com.myntra.apiTests.erpservices.marketplace;
import com.myntra.apiTests.common.Myntra;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.test.commons.testbase.BaseTest;

public class RtoGatePassEndToEnd extends BaseTest{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(RmsGatePassEndToEnd.class);
	static String envName = "fox8";

	 
	APIUtilities apiUtil = new APIUtilities();
	//random generator 
	static String order_release_id= randomGen(10);
	static String order_id_fk= randomGen(10);
	static String order_line_id = randomGen(9);
	static String tracking_no= randomGen(9);
	static String mk_old_returns_tracking_id = randomGen(5);	
	static String gatepassIdST;
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
	public void testBeforeClass() throws SQLException{
				// Insert query for inserting dummy data to create gatepass
		System.out.println("order_release_id: "+order_id_fk+" "+ "order_id_fk: " +order_id_fk +" order_line_id: "+order_line_id+" tracking_no: "+tracking_no+" mk_old_returns_tracking_id: "+mk_old_returns_tracking_id+" " );
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		String trackingNo = "ML"+randomGen(10);
			String query = "INSERT INTO `order_release` (`id`, `order_id_fk`, `login`, `status_code`, `payment_method`, `mrp_total`, `discount`, `cart_discount`, `coupon_discount`, `cash_redeemed`, `pg_discount`, `final_amount`, "
					+ "`shipping_charge`, `cod_charge`, `emi_charge`, `gift_charge`, `tax_amount`, `cashback_offered`, `receiver_name`, `address`, `city`, `locality`, `state`, `country`, `zipcode`, `mobile`, `email`, "
					+ "`courier_code`, `tracking_no`, `warehouse_id`, `is_refunded`, `cod_pay_status`, `cancellation_reason_id_fk`, `packed_on`, `shipped_on`, `delivered_on`, `completed_on`, "
					+ "`cancelled_on`, `created_by`, `created_on`, `last_modified_on`, `version`, `is_on_hold`, `queued_on`, `invoice_id`, `cheque_no`, `exchange_release_id`, "
					+ "`user_contact_no`, `shipping_method`, `on_hold_reason_id_fk`, `loyalty_points_used`, `store_id`, `store_release_id`) VALUES("+order_id_fk+", "+order_id_fk+", "
					+ "'ddac4081.a533.40b4.a49e.f53504b6a914lk1omgW9fU', 'L', 'on', 550.00, 0.00, 0.00, 0.00, 0.00, 0.00, 412.50, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, "
					+ "'Shubham gupta ', 'Myntra design, AKR tech park, Near kudlu gate', 'Bangalore', 'Bangalore', 'KA', 'India', '560068', '7875265650', 'shubhamguptaasd&#x40;gmail.com', "
					+ "'ML', 'ML0008843070', 45, 0, 'pending', 49, '2015-11-23 15:01:10', '2015-11-23 15:01:33', NULL, NULL, '2015-11-23 15:01:57', 'ddac4081.a533.40b4.a49e.f53504b6a914lk1omgW9fU', "
					+ "'2015-11-19 15:03:01', '2016-01-18 13:21:08', 13, 0, '2015-11-19 15:04:47', NULL, NULL, NULL, '7875265650', 'NORMAL', NULL, 0.00, 1, NULL)";
			
			System.out.println(query);
			DBUtilities.exUpdateQuery(query, "oms");
			
			String query1 = "INSERT INTO `order_line` (id,`order_id_fk`, `order_release_id_fk`, `style_id`, `option_id`, `sku_id`, `status_code`, `unit_price`, `quantity`, "
					+ "`discounted_quantity`, `discount`, `cart_discount`, `cash_redeemed`, `coupon_discount`, `pg_discount`, `final_amount`, `tax_amount`, `tax_rate`, "
					+ "`cashback_offered`, `disocunt_rule_id`, `discount_rule_rev_id`, `promotion_id`, `is_discounted`, `is_returnable`, `cancellation_reason_id_fk`, "
					+ "`cancelled_on`, `created_by`,`version`, `exchange_orderline_id`, `loyalty_points_used`, `seller_id`, "
					+ "`supply_type`, `vendor_id`, `store_line_id`, `po_status`) VALUES( "+order_line_id+","+order_id_fk+", "+order_id_fk+", 373905, 1251454, 1251866, 'S', "
					+ "550.00, 1, 0, 137.50, 0.00, 0.00, 0.00, 0.00, 412.50, 0.00, 5.50, 0.00, 0, 0, NULL, 0, 1, NULL, NULL, 'ddac4081.a533.40b4.a49e.f53504b6a914lk1omgW9fU', "
					+ "5, NULL, 0.00, 5, 'ON_HAND', NULL, NULL, 'UNUSED');";
			System.out.println(query1);
			DBUtilities.exUpdateQuery(query1, "oms");
			
			String query2 = "INSERT INTO `order_to_ship` (`created_by`,`version`, `address`, `city`, `is_cod`, `cod_amount`, `country`, "
					+ "`email`, `first_name`, `last_name`, `mobile`, `order_id`, `state`, `title`, `zipcode`, `cust_mobile`, `status`, `warehouse_id`, `delivery_center_id`,"
					+ " `packed_on`, `inscanned_on`, `shipped_on`, `order_additional_info_id`, `sales_order_id`, `promise_date`, `shipping_method`, `locality`, `store_id`, "
					+ "`seller_id`, `user_id`, `rto_warehouse_id`, `courier_code`, `tracking_number`, `shipment_status`, `rto_received_date`) VALUES('System',"
					+ "2, 'Kudlu gate,', "
					+ "'Bangalore', 1, 2.00, 'IN', 'shubhamguptaasd@gmail.com', 'shubham', 'gupta', '9898988989', "+order_id_fk+", 'KA', '', '560068', '9740088524', 'RTO_R', 45, 1, '"+date+"', "
					+ "NULL, NULL, NULL, NULL, NULL, 'NORMAL', NULL, NULL, 5, NULL, 1, NULL, '"+trackingNo+"', NULL, '"+date+"')";
			System.out.println(query2);
			DBUtilities.exUpdateQuery(query2, "lms");    
			
			String query3 ="INSERT INTO `order_tracking` (`version`, `courier_code`, `delivery_date`, `delivery_status`, `failed_attempts`, `last_scan_time`, `pickup_date`, "
					+ "`order_id`, `return_id`, `shipment_type`, `tracking_no`, `tracking_task_status`, `is_synced_with_portal`, `rto_date`, `first_attempt_date`, `last_attempt_date`, "
					+ "`lost_on_date`, `courier_creation_status`, `order_tracking_status`, `shipment_status`, `rto_received_date`) VALUES (2, 'ML', NULL, 'RTO_R', 0, NULL, NULL, "+order_id_fk+", "
					+ "NULL, 'DL', '"+trackingNo+"', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
			System.out.println(query3);
			DBUtilities.exUpdateQuery(query3, "lms");
			System.out.println("The DB entry is sucessfull");
	}
			
			@AfterClass(alwaysRun = true)
			public void testAfterClass() throws SQLException{
				String updateOrderRelease = "DELETE from order_release where id = "+order_id_fk+"";
				DBUtilities.exUpdateQuery(updateOrderRelease, "oms");
				
				String updateOrderLine = "DELETE from order_line where order_id_fk = "+order_id_fk+"";
				DBUtilities.exUpdateQuery(updateOrderLine, "oms");
				
				/*String updateOrderToShip = "DELETE from order_to_ship where order_id = "+order_id_fk+"";
				DBUtilities.exUpdateQuery(updateOrderToShip, "lms");*/
			
			}
			
			            
	
	
	//** API Name - Create GatePass Stock Transfer**//*
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "createGatePass_StockTransfer", dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_createGatePass_ST(String fromPartyId, String toPartyId, String type, 
			String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSSTR,
				init.Configurations, new String[] { fromPartyId, toPartyId, type,
						expectedResult },
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		gatepassIdST = req.respvalidate
				.NodeValue("data.id", true)
				.replaceAll("\"", "").trim();
		System.out.println("Gate pass created and Gatepass ID is: "+gatepassIdST);
		APIUtilities.validateResponse("json", res, expectedResult);
			
	}
	
	
	//** API Name - Create GatePass order for stock transfer**//*	
	@Test(groups = { "Regression" }, priority = 1, dataProvider = "createGatePassOrder_ST", dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_scanOrder_ST(String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSORDER,
				init.Configurations,  new String[] {gatepassIdST, order_id_fk, expectedResult},
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println(res);
		System.out.println("Order Scanned Successfull");
	}
	
	


/** API Name - Status update Gatepass (mark gatePass IN_TRANSIT) **/
	
	@Test(groups = { "Regression" }, priority = 2, dataProvider = "statusUpdateGatepass", enabled= true, dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_updateGatepassIntransit_ST(String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.STATUSUPDATEGATEPASS,
				init.Configurations,  new String[] {gatepassIdST, expectedResult},
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully (IN_TRANSIT)");		
	}

	
/** API Name - Update gatepass Order (inbound scan - Order Received) 
* @throws SQLException **/
	
	@Test(groups = { "Regression" }, priority = 3, dataProvider = "updateGatepassOrder", enabled= true, dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_updateGatepassReceived_ST(String expectedResult) throws SQLException {
		
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.UPDATEGATEPASSORDER,
				init.Configurations,  new String[] {gatepassIdST, order_id_fk, expectedResult},
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());

		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Gatepass Updated successfully inbound (RECEIVED)");	
	}


	//** API Name - Status update Gatepass_Closed (mark gatePass CLOSED) **/

			@Test(groups = { "Regression" }, priority = 4, dataProvider = "statusUpdateGatepass_Closed", enabled= true, dataProviderClass = RtoGatePassEndToEndDP.class)
			public void Rto_updateGatepassClosed_ST(String expectedResult) {
				MyntraService service = Myntra.getService(
						ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.STATUSUPDATEGATEPASSCLOSE,
						init.Configurations,  new String[] {gatepassIdST, expectedResult},
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
			@Test(groups = { "Regression" }, priority = 5, dataProvider = "createGatePassRTV", enabled= true,description="1.Create Return to Seller RTO Gatepass", dataProviderClass = RtoGatePassEndToEndDP.class)
			public void Rto_createGatePass_RTV(String fromPartyId, String toPartyId, String type,
					String expectedResult) {
				MyntraService service = Myntra.getService(
						ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSRTV,
						init.Configurations, new String[] { fromPartyId, toPartyId, type,
								expectedResult },
						PayloadType.JSON, PayloadType.JSON);			
				HashMap getParam = new HashMap();
				getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

				System.out.println(service.URL);
				RequestGenerator req = new RequestGenerator(service, getParam);
				String res = req.respvalidate.returnresponseasstring();
				AssertJUnit.assertEquals(200, req.response.getStatus());
				gatepassIdRTV = req.respvalidate
						.NodeValue("data.id", true)
						.replaceAll("\"", "").trim();
				System.out.println("RTV RTO Gatepass created and Gatepass ID is: "+gatepassIdRTV);
				APIUtilities.validateResponse("json", res, expectedResult);	
			}				
	
//** API Name - Create GatePass Order for RTV **//*
	
	@Test(groups = { "Regression" }, priority = 6, dataProvider = "createGatePassOrder_RTV", enabled= true,description="1.Scanning Order release ids to RTV gatepass", dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_ScanOrder_RTV(String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.CREATEGATEPASSORDER,
				init.Configurations,  new String[] { gatepassIdRTV, order_id_fk, expectedResult},
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println("Order Scanned successfull");
	}
	
	
	//** API Name - Status update Gatepass (mark gatePass status Intransit from RTV) **//*
	
	@Test(groups = { "Regression" }, priority = 7, dataProvider = "statusUpdateGatepassRTV_IN_TRANSIT", enabled= true,description="1.Updating Gatepass to Intransit State", dataProviderClass = RtoGatePassEndToEndDP.class)
	public void Rto_UpdateGatepassIntransit_RTV(String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACERMSGATEPASS, APINAME.STATUSUPDATEGATEPASS,
				init.Configurations,  new String[] {gatepassIdRTV, expectedResult},
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		System.out.println(" RTV Gatepass Updated successfully (In Transit)");		
	}
	
	// ** API Name - Status update Gatepass_Closed (mark gatePass CLOSED) **/

		@Test(groups = { "Regression" }, priority = 8, dataProvider = "statusUpdateGatepass_Closed",description="1.Updating Gatepass to Closed State", dataProviderClass = RmsGatePassEndToEndDP.class)
		public void Rto_UpdateGatepassClosed_RTV(String expectedResult) {
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


}
