package com.myntra.apiTests.erpservices.partners.Tests;

/*
 * Author Shubham gupta 
 */

import com.myntra.apiTests.erpservices.partners.dp.PartnerPortalSellerDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

import com.myntra.test.commons.testbase.BaseTest;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;

public class PartnerPortalSeller extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();
	
	/** Search WP order for that particular seller **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "searchOrder",description="1.Search Order in Work in Progrees State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_searchOrderWP (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.SEARCHORDERWP,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	/** Search F order for that particular seller **/
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "searchOrder",description="1.Search Order in Rejected State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_searchOrderF (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.SEARCHORDERF,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	/** Search PK order for that particular seller **/
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "searchOrder",description="1.Search Order in Packed State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_searchOrderPK (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.SEARCHORDERPK,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	/** Search C order for that particular seller **/
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "searchOrder",description="1.Search Order in Completed State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_searchOrderC (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.SEARCHORDERC,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	
	/** Search DL order for that particular seller **/
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "searchOrder",description="1.Search Order in Delivered State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_searchOrderDL (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.SEARCHORDERDL,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	
	/** Dispatch shipment api(Without placing order we are hitting 
	 * api and validating the response) **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "dispatchShipment",description="1.Search Order in Shipped State",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_dispatchShipment (String sellerId, String orderId, String expectedResult) {
		String val = "";
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.DISPATCHSHIPMENT,
				init.Configurations,new String[]{val}, new String[] { sellerId, orderId},
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	
	/** Dispatch shipment api(Here we are changing previous order from PK to WP and again deispathing) 
	 * @throws SQLException **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "dispatchShipmentByDB",description="1.Mark Order Packed",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_dispatchShipmentByDB (String sellerId, String orderId, String expectedResult) throws SQLException {
		String val = "";
		String query = "UPDATE order_release SET status_code = 'WP' WHERE order_id_fk=2147568763";
		DBUtilities.exUpdateQuery(query, "myntra_oms");
		String query1 = "UPDATE order_line SET status_code = 'A' WHERE order_release_id_fk=2147568763";
		DBUtilities.exUpdateQuery(query1, "myntra_oms");
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.DISPATCHSHIPMENT,
				init.Configurations,new String[]{val}, new String[] { sellerId, orderId},
				PayloadType.JSON, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
		
		
	}
	
	
	/** Get the category Hierarchy for a particular seller  **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "categoryHierarchy",description="1.Search by Category Hierarchy",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_categoryHierarchy (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.CATEGORYHIERARCHY,
				init.Configurations,PayloadType.JSON, new String[] { sellerId },  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	/**  Reload the cache for a particular seller **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "reload",description="1.Reload the Seller",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_reload (String sellerId, String expectedResult) {
		String val = "";
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.RELOAD,
				init.Configurations,new String[]{val}, new String[] { sellerId },
				PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
		
	/**  listing By style **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "listingByStyle",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_listingByStyle(String sellerId, String styleId, String expectedResult) {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.LISTINGBYSTYLE,
				init.Configurations, new String[] { sellerId, styleId, expectedResult }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		
	}
	
	
	/**  listing By style **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "InventoryUpdate",dataProviderClass = PartnerPortalSellerDP.class)
	public void PPSeller_InventoryUpdate(String sellerId, String skuCode, String inventoryCount, String expectedResult) {

		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACEPARTNERPORTAL, APINAME.INVENTORYUPDATE,
				init.Configurations, new String[] { sellerId, skuCode, inventoryCount, expectedResult }, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
		
	}
	
	
}
