package com.myntra.apiTests.erpservices.marketplace;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.commons.codes.StatusResponse.Type;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.ManualTest;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


/*
 *  
 *  prerequisite: Application Properties
 *  1. marketplace.singleitemsplit.warehouseids - 26
 *  2. marketplace.vendorsplit.warehouseids - 20
 *  3. Warehouse does not exist in both properties - 17,27
 *  
 *  
 */

public class JITOrderPlacementTest extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	String password = "123456";
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	End2EndHelper end2EndHelper = new End2EndHelper();
	
	
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=1 where id in (23,35,37);", "myntra_oms");
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		end2EndHelper.sleep(20000L);
	}

	@AfterClass(alwaysRun = true)
	public void testAfterClass() throws SQLException{
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (23,35,37);", "myntra_oms");
		
	}

	
	@Test(groups = { "Smoke","Regression"},description="1.Create order WH-20 Vendor-1292 Sku1 qty1. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order \n 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void SingleItemAndCancelOrder() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153416:1" }; // sku code:MSMRFHSR00467 , Style id:346570
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID=null;
		
			
			/*HashMap<String, String[]> inventoryCountInATPBeforePlacingOrder = end2EndHelper
					.getAtpInvAndBlockOrderCount(new String[] { "1153416" });*/

			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false,false,false,"",false);
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);

			/*HashMap<String, String[]> inventoryCountInATPAfterPlacingOrder = end2EndHelper
					.getAtpInvAndBlockOrderCount(new String[] { "3832" });*/

		//	Assert.assertEquals(inventoryCountInATPBeforePlacingOrder, inventoryCountInATPAfterPlacingOrder);
			
			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

			/*End2EndHelper.sleep(6000L);
			OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
			System.out.println(APIUtilities.convertXMLObjectToString(orderEntry));
			System.out.println(orderEntry.getCancellationPpsId());
			Assert.assertNotNull(orderEntry.getCancellationPpsId(), "Order Cancelltion PPS ID is not Null");

			HashMap<String, String[]> inventoryCountInATPAfterCancellation = end2EndHelper
					.getAtpInvAndBlockOrderCount(new String[] { "3832" });
			Assert.assertEquals(inventoryCountInATPAfterCancellation, inventoryCountInATPBeforePlacingOrder);*/

		/*} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Exception SingleItemAndCancelOrder" + e.getMessage());*/
		
	
	} 
	
	// This query gets the number of releases from order_release table
	public int getNoOfReleaseCount(String orderID) throws SQLException {
		String releaseCount = "select count(*) from order_release where order_id_fk = "
				+ orderID + "";
		List Rel = DBUtilities.exSelectQuery(releaseCount, "myntra_oms");
		HashMap<String, Long> releaseResult = (HashMap<String, Long>) Rel.get(0);
		Long lp = releaseResult.get("count(*)");
		return lp.intValue();
	}
	
	// This query gets the status code of the order release 
	public String getOrderStatus(String orderID) throws SQLException {
		String orderStatus = "Select status_code from order_release where order_id_fk ="
				+ orderID + "";
		List ST = DBUtilities.exSelectQuery(orderStatus, "myntra_oms");
		HashMap<String, String> status = (HashMap<String, String>) ST.get(0);
		String lp = status.get("status_code");
		return lp;
	}
	

	@Test(groups = {"Regression"},description="1.Create order with WH-20 Vendor-1292 qty1 sku1. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order Release 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void SingleItemAndCancelRelease() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153416:1" }; // sku code:MSMRFHSR00467 , Style id:346570
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID=null;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false,false,false,"",false);
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);

			OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

	}
	
	
	@Test(groups = {"Regression"},description="1.Place Order when email id is in Fraud User list \n 2.Order status in RFR state \n 3.Order flow to Unicom ")
	public void singleItemCheckOnHold() throws Exception {
		String login = "fraud.customer@myntra.com";
		String skuId[] = { "1153411:1" }; //  Style id:346565
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false,false,false,"",false);
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);

			OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

	}
	
	@Test(groups = {"Regression"},description="1.Create order with WH-20 Vendor-1292 qty1 sku1. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order Release 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void CheckOnHoldMultiRelease() throws Exception {
		String login = "fraud.customer@myntra.com";
		String skuId[] = { "1153411:1","1153271:1" }; //  Style id:346565/346425
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982",skuId, "", false,false,false,"",false);
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(2, releaseResult);

			OrderReleaseResponse cancellationRes = omsServiceHelper.cancelOrderRelease(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

	}
	
	@Test(groups = {"Regression"},description="1.Create order with WH-20 Vendor-1292 qty2 sku1. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void multipleQtySkuAndCancelOrder() throws Exception   {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153416:2" }; // sku code:MSMRFHSR00467 , Style id:346570 
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			
			orderID = end2EndHelper.createOrder(login, password, "6118982",skuId, "", false,false,false,"",false);

			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);
			

			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

			
		
	}
	
	
	
	@Test(groups = {"Regression"},description="1.Create order with WH-20 Vendor-1292 qty2 sku1/qty2 sku2. \n 2.Check Order is in WP State \n 3.Check there is 1 Release \n 4. Cancel the Order 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void multipleQtyMultiSkuAndCancelOrder() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153017:2","1153416:2" }; // Sku:MSMRFHSR00467/MSMRFHSR00059 Style id:346570/346162 
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			
			orderID = end2EndHelper.createOrder(login, password, "6118982",skuId, "", false,false,false,"",false);

			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);
			

			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

			
		
	}
	
	@Test(groups = {"Regression"},description="1.Create order with qty1 sku1(Vendor 1292)/qty1 sku1( Vendor 1010) for WH 20. \n 2.Check Order is in WP State \n 3.Check there is 2 Release \n 4. Cancel the Order 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void MultiSkuVendorLevelSplitAndCancelOrder() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153432:1","1153456:1" }; // Sku: MSMRFHSR00059/BLFLTOPS00101 Style id:346162/294294 , 
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			
			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false,false,false,"",false);

			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is WP: "+status);
			AssertJUnit.assertEquals("WP", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(2, releaseResult);
			

			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

			
		
	}

	@Test(groups = {"Regression"},description="1.Select WH-20:Vendor 1292(qty1) Vendor 1010(qty1) \n 2.Select WH-26:Vendor 1292(qty1) Vendor 1010(qty1)\n 3.Place Order \n 4.Check there is 6 Release 2 for WH-20 4 for WH-26 \n 5. Cancel the Order 6.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void WHVendorLevelSplit() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153432:1","1153456:1","1153328:2","1153304:1" }; //Sku:MSMRFHSR00059/BLFLTOPS00101/MSMRFHSR00290/MSMRFHSR00157 Style id:346162/294294 346393/346260 , "1153432:1","1153456:1",1153328
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982",skuId, "", false,false,false,"",false);

			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 5);
			String status = getOrderStatus(orderID);
			//System.out.println("DB check status code is RFR: "+status);
			//AssertJUnit.assertEquals("RFR", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(5, releaseResult);
			
			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

			
		
	}
	
	
	@Test(groups = {"Regression"},description="1.Select WH-26:Vendor 1292(qty3) \n 2.Place Order \n 3.Check there is 3 Release  \n 4. Cancel the Order 5.NOTE:A. marketplace.singleitemsplit.warehouseids - 26  \n B. marketplace.vendorsplit.warehouseids - 20 \n C. Warehouse does not exist in both properties - 17,27")
	public void SingleItemSplit() throws Exception {
		String login = "mktplaceitem@myntra.com";
		String skuId[] = { "1153328:3" }; //Sku:MSMRFHSR00290  Style id:346393 , 
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		String orderID;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false,false,false,"",false);

			omsServiceHelper.validateReleaseStatusInOMS(orderID, "RFR", 5);
			String status = getOrderStatus(orderID);
			System.out.println("DB check status code is RFR: "+status);
			AssertJUnit.assertEquals("RFR", status);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(3, releaseResult);
			
			OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

	}
	
		

	/*@Test(groups = { "Regression"},description="1.Select WH-20:Vendor 1010(qty2) \n 2.Place Order \n 3.Check there is 1 Release  \n 4. Cancel 1 item from the Order")
	public void CancelLine() throws Exception {
		String login = "jitorder@myntra.com";
		String skuId[] = { "984285:2"};
		End2EndHelper end2EndHelper = new End2EndHelper();
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

		long orderID;
		
			orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", "80000", "40000", "39800","","");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 10);
			int releaseResult = getNoOfReleaseCount(orderID);
			System.out.println("DB check Order Release table Release count: "+releaseResult);
			AssertJUnit.assertEquals(1, releaseResult);
			OrderReleaseEntry orderReleaseEntry =  omsServiceHelper.getOrderReleaseEntry(orderID);
			Long lineID = orderReleaseEntry.getOrderLines().get(0).getId();
			System.out.println("Line ID : "+ lineID);
			End2EndHelper.sleep(6000L);
			OrderReleaseResponse cancellationRes = omsServiceHelper.cancelLine(orderID, login, "ML", new String[] {lineID+":1"}, "ISOC", "CCR");
			Assert.assertEquals(cancellationRes.getStatus().getStatusCode(), 1034);
			Assert.assertEquals(cancellationRes.getStatus().getStatusType(), Type.SUCCESS);
			System.out.println("Lince Cancellation Status : "+cancellationRes.getData().get(0).getStatus());
			OrderLineEntry orderLineEntry = cancellationRes.getData().get(1).getOrderLines().get(0);
			Assert.assertEquals(orderLineEntry.getStatus(), "IC");
			Assert.assertEquals(""+orderLineEntry.getQuantity(), "1");
			
			OrderResponse cancellationRes1 = omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
			Assert.assertEquals(cancellationRes1.getStatus().getStatusCode(), 1006);
			Assert.assertEquals(cancellationRes1.getStatus().getStatusType(), Type.SUCCESS);
			Assert.assertEquals(cancellationRes1.getData().get(0).getOrderReleases().get(0).getStatus(), "F");
			omsServiceHelper.validateReleaseStatusInOMS(orderID, "F", 10);
			String status1 = getOrderStatus(orderID);
			System.out.println("DB check status code is cancel: "+status1);
			AssertJUnit.assertEquals("F", status1);

	}
	*/
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple items. \n"
			+ "2. 3 Items for vendor-1 \n 3.Assign items for only 1 item \n 4.Cancel the Line item \n 5.Item should get cancel for which item is not assigned")
	public void JITOrder_ManualTest1(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple items. \n"
			+ "2. 3 Items for vendor-1 \n 3.Mark Order Delivered  \n 4.Create return for 1 item \n 5.Should get return call to Unicom for that particular item")
	public void JITOrder_ManualTest2(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple items. \n"
			+ "2. 3 Items for vendor-1 \n 3.Mark Order Shipped   \n 4.RTO the Order \n 5.Should get RTO call to Unicom for that Order")
	public void JITOrder_ManualTest3(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple items. \n"
			+ "2. 3 Items for vendor-1 \n 3.Mark Order Shipped   \n 4.RTO the Order \n 5.Should get RTO call to Unicom for that Order")
	public void JITOrder_ManualTest4(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple qty for single items. \n"
			+ "2. Return 1 item \n 3. Should get return call to Unicom for that particular item")
	public void JITOrder_ManualTest5(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with multiple qty for single items. \n"
			+ "2. RTO the Order \n 3.Should get RTO call to Unicom for that Order")
	public void JITOrder_ManualTest6(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place Order with qty-2 skuid - 1 \n"
			+ "2. Place another order with the same 2qty for same skuid-1 \n 3.Assign 1 item to first order \n 4.Split the other item from Order 1 \n 5.Now assign item again. \n 6.It should get assigned to first order split item not to the order 2")
	public void JITOrder_ManualTest7(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place Order with qty-2 skuid -1 , order stuck in OH/RFR \n"
			+ "2. Place another order with the same 2qty for same skuid-1 Order pushed to Unicom \n 3.Now push the first Order to Unicom \n 4.Assign item \n 5.It should get assigned to first order not to the second order")
	public void JITOrder_ManualTest8(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with qty-2 \n"
			+ "2. Manual split from Mojo \n 3.Order should get splitted properly.Unicom date should be the new date.Myntra date should be the original order date")
	public void JITOrder_ManualTest9(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order for 2 qty same sku  \n"
			+ "2.Mark them Packed")
	public void JITOrder_ManualTest10(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order for 2 qty different sku  \n"
			+ "2.Mark them Packed")
	public void JITOrder_ManualTest11(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with 2 qty same sku\n"
			+ "2. Assign items to both the skus \n 3.Export the file from Unicom \n 4.Upload the Order is Mojo - Unicom Auto split \n 5.Order should not get split")
	public void AutoSplit_ManualTest1(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with 2 qty same sku\n"
			+ "2. Don't Assign items to both the skus \n 3.Export the file from Unicom \n 4.Upload the Order is Mojo - Unicom Auto split \n 5.Order should not get split")
	public void AutoSplit_ManualTest2(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with 2 qty same sku\n"
			+ "2.Assign item 1 sku \n 3.Export the file from Unicom \n 4.Upload the Order is Mojo - Unicom Auto split \n 5.Order should get split.")
	public void AutoSplit_ManualTest3(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with 4 qty Different sku\n"
			+ "2.Assign items to both the skus 1 each \n 3.Cancel 1 item from each skus \n 4.Export the file from Unicom \n 5.Upload the Order is Mojo - Unicom Auto split \n 5.Order should get split.")
	public void AutoSplit_ManualTest4(){
		Assert.fail("Manual Test");
	}
	
	@ManualTest
	@Test(groups = { "Regression" },description="1.Place order with 1 qty \n 2. Mark Order Packed \n 3. Check logs shelf got release successfully")
	public void ShelfRelease_ManualTest(){
		Assert.fail("Manual Test");
	}

}
