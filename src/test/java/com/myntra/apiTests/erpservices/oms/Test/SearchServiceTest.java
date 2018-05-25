package com.myntra.apiTests.erpservices.oms.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.portalservices.checkoutservice.CheckoutTestsHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class SearchServiceTest extends BaseTest {

	static Initialize init = new Initialize("/Data/configuration");
    private String login = OMSTCConstants.LoginAndPassword.SearchServiceTestLogin;
	private String uidx ;
	private String password = OMSTCConstants.LoginAndPassword.SearchServiceTestPassword;
	private static Logger log = Logger.getLogger(SearchServiceTest.class);
	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	SplitOrderServiceTest splitOrderServiceTest=new SplitOrderServiceTest();
	CheckoutTestsHelper checkoutTestsHelper = new CheckoutTestsHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	private String uidx1;
	private static Long vectorSellerID;
	private SoftAssert sft;
	private String supplyTypeOnHand = "ON_HAND";
	 
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, IOException {
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);

	}

	@Test(enabled = true,groups={"regression","getOrder"},description="Search the orders.")
	public void  searchOrdersTest() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		log.info("Total Orders count should be 5." +orderRespince.getData().size());
		sft.assertEquals(orderRespince.getData().size(), 5,"Total Orders count should be 5.");
		sft.assertAll();
		
	}

	@Test(enabled = true,groups={"regression","getOrder","sanity"},description="Search the pending orders.")
	public void  searchPendingOrdersTest() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?status=pending&fetchSize=5&start=0&q=login.eq:"+uidx+"";
	
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero");
		log.info("Total Orders count should be 5." +orderRespince.getData().size());
		sft.assertEquals(orderRespince.getData().size(), 5,"Total Orders count should be 5.");
		sft.assertAll();

	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the completed orders.")
	public void  searchCompleateOrdersTest() throws Exception{
		sft = new SoftAssert();
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3834+":1"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3834+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3834+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand );
        
        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
        String orderID1 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
        String orderID2 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
        String orderID3 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
        String orderID4 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
		
		DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID+"';", "myntra_oms");
		DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID1+"';", "myntra_oms");
		DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID2+"';", "myntra_oms");
		DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID3+"';", "myntra_oms");
		DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID4+"';", "myntra_oms");

		String quaryParam="getOrders?distinct=true&status=completed&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		log.info("Total Orders count should be 5." +orderRespince.getData().size());
		sft.assertTrue(orderRespince.getData().size()==5,"Total Orders count should be 5.");
		sft.assertAll();
	
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchcancelledOrdersTest() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=cancelled&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		log.info("Total Orders count should be 5." +orderRespince.getData().size());
		sft.assertTrue(orderRespince.getData().size()<=5,"Total Orders count should be 5.");
		sft.assertAll();

	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchTheOrdersWithFetchSizeIsNagitveValue() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=ALL&FetchSize=-1&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the pending orders with fetch size -1.")
	public void  searchPendingOrdersFetchSizeLessthanZero() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?status=pending&fetchSize=-1&start=0&q=login.eq:"+uidx+"";
	
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero");
		log.info("Total Orders count should be 10." +orderRespince.getData().size());
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchThecancelledOrdersWithFetchSizeIsNagitveValue() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=cancelled&FetchSize=-1&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertAll();
		 
	}
	

	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchTheOrdersWithSortOrderAssendingSort() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=ALL&FetchSize=5&start=0&sortBy=id&sortOrder=ASC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchThePendingOrdersWithSortOrderAssendingSort() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=pending&FetchSize=5&start=0&sortBy=id&sortOrder=ASC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchThecancelledOrdersWithSortOrderAssendingSort() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=true&status=cancelled&FetchSize=5&start=0&sortBy=id&sortOrder=ASC&q=login.eq:"+uidx+"";
		
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the cancelled orders.")
	public void  searchThecompletedOrdersWithSortOrderAssendingSort() throws Exception{
	   sft = new SoftAssert();
	   String skuId[] = {OMSTCConstants.OtherSkus.skuId_3834+":1"};
	   atpServiceHelper.updateInventoryDetailsForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3834+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID+":1"},supplyTypeOnHand);
       imsServiceHelper.updateInventoryForSeller(new String[] {OMSTCConstants.OtherSkus.skuId_3834+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+vectorSellerID},supplyTypeOnHand );
       
       String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
       String orderID1 = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", true);
       DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID+"';", "myntra_oms");
       DBUtilities.exUpdateQuery("update order_release set status_code='DL' where order_id_fk='"+orderID1+"';", "myntra_oms");
   		
       String quaryParam="getOrders?distinct=true&status=completed&FetchSize=5&start=0&sortBy=id&sortOrder=ASC&q=login.eq:"+uidx+"";
       OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
       orderRespince.getStatus();
       sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
       sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
       sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
       sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
       sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the orders with fetch size 0.")
	public void  SearchTheOrdersWithFetchSizeIsZero() throws Exception{
		sft = new SoftAssert();

		uidx1 = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
		String quaryParam="getOrders?distinct=true&status=ALL&fetchSize=0&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx1+"";
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount() > 0,"Total order count should not be more than zero ");
		log.info("Total Orders count should be 0. Actual:" +orderRespince.getData().size());
		sft.assertEquals(orderRespince.getData().size(), 0,"Total Orders count should be 0.");
		sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search Order with Distinct set to False.")
	public void  SearchTheOrdersWithDistinctIsFalse() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"";
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		log.info("Total Orders count should be 5." +orderRespince.getData().size());
		sft.assertEquals(orderRespince.getData().size(), 5,"Total Orders count should not be 0.");
		sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search the orders for a user with order id.")
	public void  SearchTheOrdersWithOrderId() throws Exception{
		sft = new SoftAssert();
		List<HashMap> Login_Db=DBUtilities.exSelectQuery("select order_id_fk from order_release order by created_on DESC LIMIT 1", "oms");
		HashMap<String, Long> txResult = (HashMap<String, Long>) Login_Db.get(0);
		
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=id.eq:"+txResult.get("order_id_fk")+"";
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertNotNull(orderRespince.getData().size(),"Total Orders count should not be 0.");
		sft.assertAll();
		 
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search orders for a user with order id and order status.")
	public void  SearchTheOrdersForUserWithOrderIdAndOrderStatus() throws Exception{
		sft  = new SoftAssert();
		List<HashMap> Login_Db=DBUtilities.exSelectQuery("select order_id_fk from order_release order by created_on DESC LIMIT 1", "oms");
		HashMap<String, Long> txResult = (HashMap<String, Long>) Login_Db.get(0);
		
		String quaryParam="getOrders?distinct=true&status=ALL&fetchSize=0&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"&orderReleases.releaseStatus.eq:Q&id.eq:"+txResult.get("order_id_fk")+"";
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertNotNull(orderRespince.getData().size(),"Total Orders count should be more than 0.");
		sft.assertAll();
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="SSearch order with invalid order id.")
	public void  searchTheOrdersForAuserWithOverallStatusPendingAndReleaseStatusWP() throws Exception{
		sft = new SoftAssert();
		List<HashMap> Login_Db=DBUtilities.exSelectQuery("select order_id_fk from order_release order by created_on DESC LIMIT 1", "oms");
		HashMap<String, Long> txResult = (HashMap<String, Long>) Login_Db.get(0);
		String quaryParam="getOrders?distinct=true&status=pending&fetchSize=0&start=0&sortBy=id&sortOrder=DESC&q=login.eq:"+uidx+"&orderReleases.releaseStatus.eq:WP&id.eq:"+txResult.get("order_id_fk")+"";
		
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()>0,"Total order count should be more than zero ");
		sft.assertNotNull(orderRespince.getData().size(),"Total Orders count should be more than 0.");
		sft.assertAll();
	    
	}
	
	@Test(enabled = true,groups={"regression","getOrder"},description="Search order with invalid order id.")
	public void  searchOrderWithInvalidOrderId() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=id.eq:99999999";
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()==0,"Total order count should be zero ");	
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","getOrder"},description="Search order with invalid emailid and valid order id.")
	public void  searchOrderWithInvalidEmailIdAndValidOrderId() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:0000000@myntra.com&&q=id.eq:17412246";
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()==0,"Total order count should be zero ");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","getOrder"},description="Search order with email containing hyphen -.")
	public void  searchOrderWithEmailContainingHyphen() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:akshay-ece15@snu.edu.in";
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()==0,"Total order count should be zero ");
		sft.assertAll();
	}
	@Test(enabled = true,groups={"regression","getOrder"},description="Search order with email containing Plus +.")
	public void  searchOrderWithEmailContainingPlus() throws Exception{
		sft = new SoftAssert();
		String quaryParam="getOrders?distinct=false&status=ALL&fetchSize=5&start=0&sortBy=id&sortOrder=DESC&q=login.eq:akshay+ece15@snu.edu.in";
		OrderResponse orderRespince=omsServiceHelper.getOrderSearch(quaryParam);
		orderRespince.getStatus();
		sft.assertEquals(orderRespince.getStatus().getStatusCode(), 1003,"Status code should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusMessage(), "Order(s) retrieved successfully","Status message should be the same.");
		sft.assertEquals(orderRespince.getStatus().getStatusType().toString(), "SUCCESS","Status Type should be the same.");
		sft.assertTrue(orderRespince.getStatus().getTotalCount()==0,"Total order count should be zero ");
		sft.assertAll();
	}
}

	
