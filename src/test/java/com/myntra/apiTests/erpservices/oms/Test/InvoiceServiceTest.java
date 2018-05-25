package com.myntra.apiTests.erpservices.oms.Test;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;


/**
 * Created by Maheswarareddy on 27/06/16.
 */

public class InvoiceServiceTest extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	private String login = "end2endautomation1@gmail.com";
	private String uidx = "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R";
	private String password = "123456";

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	BountyServiceHelper bountyServiceHelper=new BountyServiceHelper();
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, UnsupportedEncodingException, JAXBException {
	
	}
	
	// Completed 1
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Shipment with Multiple release")
	public void CheckInvoiceForShipmentWithMultipleRelease() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3918", "myntra_atp");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3917", "myntra_atp");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3833", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3918:1","3917:1" ,"3833:1"  };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		
		System.out.println("Relese Id 0 :"+orderReleaseEntry.get(0).getId());
		System.out.println("Relese Id 1 :"+orderReleaseEntry.get(1).getId());
		System.out.println("Relese Id 2 :"+orderReleaseEntry.get(2).getId());
		
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(0).getId().toString(), "PUMATSRM01401");
		Assert.assertEquals(orderReleaseEntry.get(0).getId().toString(), omsServiceHelper.getBarcode(dataOfInvoice));
		String[] shipmentNo=omsServiceHelper.getShipmentNo(dataOfInvoice).split("\n");
		Assert.assertEquals(orderReleaseEntry.get(0).getId().toString(), shipmentNo[0].replace(": ", "").trim());
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice),"L" );
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice)," 799.00" );
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice),"1" );
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 799" );
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(1).getId().toString(), "DHLNSGMC00643");
		Assert.assertEquals(orderReleaseEntry.get(1).getId().toString(), omsServiceHelper.getBarcode(dataOfInvoice));
		String[] shipmentOne=omsServiceHelper.getShipmentNo(dataOfInvoice).split("\n");
		Assert.assertEquals(orderReleaseEntry.get(1).getId().toString(), shipmentOne[0].replace(": ", "").trim());
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice),"1LTR" );
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice)," 249.00" );
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice),"1" );
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 249" );
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(2).getId().toString(), "DHLNSGMC00644");
		Assert.assertEquals(orderReleaseEntry.get(2).getId().toString(), omsServiceHelper.getBarcode(dataOfInvoice));
		String[] shipmentTwo=omsServiceHelper.getShipmentNo(dataOfInvoice).split("\n");
		Assert.assertEquals(orderReleaseEntry.get(2).getId().toString(), shipmentTwo[0].replace(": ", "").trim());
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice),"1LTR" );
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice)," 199.00" );
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice),"1" );
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 199" );
		
		
	}
	
	// Completed 2
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for ML Online Order")
	public void generateInvoiceForMLOnlineOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3830", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3830:2" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 100);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		
		System.out.println("Relese Id 0 :"+orderReleaseEntry.get(0).getId());
		End2EndHelper.sleep(200000l);
		
		// Read invoice PDF File.
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMAJKWU00209");
		
		String courierCode=orderReleaseEntry.get(0).getCourierCode();

		System.out.println("getAmountPaid :"+omsServiceHelper.getAmountPaid(dataOfInvoice));
		System.out.println("getShipmentValue :"+omsServiceHelper.getShipmentValue(dataOfInvoice));
		System.out.println("getCourierCode :"+omsServiceHelper.getCourierCode(dataOfInvoice));
		Assert.assertEquals(omsServiceHelper.getCourierCode(dataOfInvoice).substring(0, 2),courierCode);
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 0");
		Assert.assertEquals(omsServiceHelper.getShipmentValue(dataOfInvoice), " 6");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"),"ML - ON");
		
	}
			
	//Completed 3
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for ML COD order")
	public void generateInvoiceForMLCODOrder() throws Exception {
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3830:2" };
		
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000l);
		// Read invoice PDF File.
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMAJKWU00209");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
	
		String courierCode=orderReleaseEntry.get(0).getCourierCode();

		System.out.println("getAmountPaid :"+omsServiceHelper.getAmountPaid(dataOfInvoice));
		System.out.println("getShipmentValue :"+omsServiceHelper.getShipmentValue(dataOfInvoice));
		System.out.println("getCourierCode :"+omsServiceHelper.getCourierCode(dataOfInvoice));
		Assert.assertEquals(omsServiceHelper.getCourierCode(dataOfInvoice).substring(0, 2),courierCode);
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 0");
		Assert.assertEquals(omsServiceHelper.getShipmentValue(dataOfInvoice), " 6");
		}
	
	//Completed 4
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Full COD Payment")
	public void generateInvoiceForFullCODPayment() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3830", "myntra_atp");
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3830:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMAJKWU00209");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 4");
		Assert.assertEquals(omsServiceHelper.getShipmentValue(dataOfInvoice), " 4");
		Assert.assertEquals(omsServiceHelper.getShipmentValue(dataOfInvoice).replace("Rs", ""), omsServiceHelper.getShipmentValue(dataOfInvoice));
		
	}
	
	//Completed 5
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Full Online Payment")
	public void generateInvoiceForFullOnlinePayment() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3830", "myntra_atp");
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3830:1" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 100);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMAJKWU00209");
		System.out.println("omsServiceHelper.getAmountPaid(dataOfInvoice) :"+omsServiceHelper.getAmountPaid(dataOfInvoice));
		System.out.println("omsServiceHelper.getAmountPaid(dataOfInvoice) :"+omsServiceHelper.getShipmentValue(dataOfInvoice));
		System.out.println("ShipmentLabel :"+dataOfInvoice.get("ShipmentLabel"));
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 0");
		Assert.assertEquals(omsServiceHelper.getShipmentValue(dataOfInvoice), " 4");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"),"ML - ON");
		
	}
	
	//Completed 6
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Partial CashBack is used")
	public void generateInvoiceWhenPartialCashBackIsUsed() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3836", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3836:2" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 3);
	    orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	    End2EndHelper.sleep(200000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMATSRM01388");
	    Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 3");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), orderID);
	    Assert.assertEquals(dataOfInvoice.get("MyCashback").trim(), "Rs 3.00");
	}

	
	//Completed 7
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Full CashBack is used")
	public void generateInvoiceWhenFullCashBackIsUsed() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3836", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3836:2" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 6);
	    orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMATSRM01388");
		
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 0");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), orderID);
	    System.out.println("dataOfInvoice MyCashback :"+dataOfInvoice.get("MyCashback"));
	    Assert.assertEquals(dataOfInvoice.get("MyCashback").trim(), "Rs 6.00");
	}
	
	
	// Completed 8
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Shipment with one Line and multiple item")
	public void generateInvoiceForShipmentWithOneLineAndMultipleItem() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3830", "myntra_atp");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3832", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		
		String orderID;
	    String skuId[] = { "3832:1","3830:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(400000l);
		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(0).getId().toString(), "PUMAJKWU00209");
		
		Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice),String.valueOf(orderID));
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice)," 4.00","Code shuld be ML.");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice),"1");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice),"XL");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice)," 2.00");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 4");
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(1).getId().toString(), "PUMATSRM01400");
		Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice),orderReleaseEntry.get(1).getId().toString());
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice)," 799.00","Code shuld be ML.");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice),"1");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice),"M");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice)," 799.00");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 799");
	}
	
	// Completed 9
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate invoice when Full loyalty points used")
	public void generateInvoiceWhenFullLoyaltyPointsUsed() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3836", "myntra_atp");
	
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3836:1" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 50, 0);
	    orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		End2EndHelper.sleep(200000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMATSRM01388");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 3");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), orderID);
	    Assert.assertEquals(dataOfInvoice.get("LoyaltyCredit").trim(), "(-) Rs 0.60");
		
	}
	
	// Completed 10
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Shipment with Single release with one line")
	public void generateInvoiceForShipmentWithSingleReleaseWithOneline() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3836", "myntra_atp");
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3836:1" };
		
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 500);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000L);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMATSRM01388");
    	// get the lines count.
		Assert.assertTrue(omsServiceHelper.getCourierCode(dataOfInvoice).substring(0, 2).contains("ML"),"Code shuld be ML.");
		Assert.assertEquals(dataOfInvoice.get("MyCashback").trim(), "Rs 4.00");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 0","Code shuld be ML.");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "ML - ON");
	}
	
	// Completed 11
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Shipping Label is present")
	public void generateInvoiceWhenShippingLabelIsPresent() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19,36,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3836", "myntra_atp");
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "3836:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(200000L);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMATSRM01388");
		System.out.println("ShipmentLabel :"+dataOfInvoice.get("ShipmentLabel"));
		System.out.println("ShipmentLabel :"+dataOfInvoice.get("ShipmentCode"));
		Assert.assertTrue(omsServiceHelper.getCourierCode(dataOfInvoice).substring(0, 2).contains("ML"),"Code shuld be ML.");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice),"Rs 4","Code shuld be ML.");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "ML - COD");	
	}
	
	// Completed 12
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when One high discounted and one low discounted product")
	public void generateInvoiceWhenOneHighDiscountedAndOneLowDiscountedProduct() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=991768", "myntra_atp");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='1,19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493287", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
	    String skuId[] = { "991768:1","493287:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(400000L);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		//40 Discount
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(0).getId().toString(), "PUMACASH103033");
		Assert.assertEquals(omsServiceHelper.getDiscount(dataOfInvoice), " 1199.60");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 260.91");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2060.31");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK8");
		
		//20 Discount
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(1).getId().toString(), "ATMC MNG-BLK-ANTHRCT");
		Assert.assertEquals(omsServiceHelper.getDiscount(dataOfInvoice), " 519.00");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2595.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 301.02");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2377.02");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "US11(UK10)");
		
	}
	
	// Completed 13
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for Shipment with Single release and Multiple Line")
	public void generateInvoiceForShipmentWithSingleReleaseAndMultipleLine() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36,1,28', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3831", "myntra_atp");
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='36', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=3840", "myntra_atp");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		//493441
	    //493287
	    String skuId[] = { "3831:1","3840:1" };
		String orderID;
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(400000L);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		//Line One
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(0).getId().toString(), "PUMACASH103033");
		Assert.assertEquals(omsServiceHelper.getDiscount(dataOfInvoice), " 1199.60");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 260.91");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2060.31");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK8");
				
		//Line 2
		dataOfInvoice=omsServiceHelper.getInvoicePdf(orderReleaseEntry.get(1).getId().toString(), "ATMC MNG-BLK-ANTHRCT");
		Assert.assertEquals(omsServiceHelper.getDiscount(dataOfInvoice), " 519.00");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2595.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 301.02");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2377.02");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "US11(UK10)");
	}
	
	// Completed 14
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for EK COD order")
	public void generateInvoiceForEKCODOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
	
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		
	    orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		omsServiceHelper.changeAddress(orderID, "Abhijit", "Pati", "BANGALORE", "Ben", "888779",true,login);
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf("70097647", "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "EK - COD");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
		Assert.assertEquals(omsServiceHelper.getAmountPaid(dataOfInvoice), "Rs 2999");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70097647");

	}
	
	// Completed 15
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for EK online order")
	public void generateInvoiceForEKOnlineOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
		 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 10000);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		omsServiceHelper.changeAddress((orderID), "Abhijit", "Pati", "BANGALORE", "Ben", "888777",true,login);
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "EK - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70097647");
	}
	

	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for IP COD order")
	public void generateInvoiceForIPCODOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
		 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		String wareHouseId = String.valueOf(orderReleaseEntry.get(0).getWarehouseId());
		System.out.println("warehouseId :" + wareHouseId);
		Assert.assertEquals(wareHouseId, "19","Wharehouse id is not matched..19.");

		End2EndHelper.sleep(200000l);
		omsServiceHelper.changeAddress((orderID), "Abhijit", "Pati", "BANGALORE", "Ben", "888778",true,login);
		DBUtilities.exUpdateQuery("update order_release set `tracking_no`='IPNO2016065306' where order_id_fk="+orderID+" and courier_code='IP'", "myntra_oms");
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "IP - COD");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), String.valueOf(orderID));
	}
	
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for IP online order")
	public void generateInvoiceForIPOnlineOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
		 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 10000);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		omsServiceHelper.changeAddress(orderID, "Abhijit", "Pati", "BANGALORE", "Ben", "888778",true,login);
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "IP - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70097647");
	}
	
	
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for 3PL COD order")
	public void generateInvoiceFor3PLCODOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
		 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		omsServiceHelper.changeAddress(orderID, "Abhijit", "Pati", "BANGALORE", "Ben", "888779",true,login);
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "3PL - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70097647");
	}
  
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice for 3PL online order")
    public void generateInvoiceFor3PLOnlineOrder() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
		 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		String orderID;
		String skuId[] = { "493441:1" };
		end2EndHelper.updateloyalityAndCashBack(uidx, 0, 10000);
		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, false, "",true);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		omsServiceHelper.changeAddress(orderID, "Abhijit", "Pati", "BANGALORE", "Ben", "888779",true,login);
		End2EndHelper.sleep(200000l);
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "PUMACASH103049");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "IP - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 379.79");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 2999.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "UK7");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70097647");
	}
	
	
	
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Partial payment from gift card")
	public void generateInvoiceWhenFullPaymentFromGiftCard() throws Exception {
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
	/*	boolean status=omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 15);
		Assert.assertTrue(status,"Order status is not in WP.");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		
		End2EndHelper.sleep(400000l);*/
		dataOfInvoice=omsServiceHelper.getInvoicePdf("70127774", "PUMATSRM01388");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "ML - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 0.21");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 4.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "M");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70127774");
		Assert.assertEquals(omsServiceHelper.getGiftCardAmount(dataOfInvoice), "(-) Rs 4.00");
	}
	
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Full payment from gift card")
	public void generateInvoiceWhenPartialPaymentFromGiftCard() throws Exception {
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		/*boolean status=omsServiceHelper.validateReleaseStatusInOMS(orderID, "WP", 15);
		Assert.assertTrue(status,"Order status is not in WP.");
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(70054322);
		List<OrderReleaseEntry> orderReleaseEntry = orderEntry.getOrderReleases();
		
		End2EndHelper.sleep(200000l);
		*/dataOfInvoice=omsServiceHelper.getInvoicePdf("70127774", "PUMATSRM01388");
		Assert.assertEquals(dataOfInvoice.get("ShipmentLabel"), "ML - ON");
		Assert.assertEquals(omsServiceHelper.getUnitPrice(dataOfInvoice), " 2.00");
		Assert.assertEquals(omsServiceHelper.getQuantity(dataOfInvoice), "1");
		Assert.assertEquals(omsServiceHelper.getTaxAmount(dataOfInvoice), " 0.21");
		Assert.assertEquals(omsServiceHelper.getTotalAmount(dataOfInvoice), " 4.00");
		Assert.assertEquals(omsServiceHelper.getSize(dataOfInvoice), "M");
	    Assert.assertEquals(omsServiceHelper.getOrderNo(dataOfInvoice), "70127774");
		Assert.assertEquals(omsServiceHelper.getGiftCardAmount(dataOfInvoice), "(-) Rs 4.00");
		
	}
	
	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Gift Label is present")
	public void generateInvoiceWhenGiftLabelIsPresent() throws Exception {
		
		

        String orderID;
		String skuId[] = { "848606:1" };

		orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
		
		End2EndHelper.sleep(400000l);
		dataOfInvoice=omsServiceHelper.getInvoicePdf(String.valueOf(orderID), "DHLNSGMC00643");
		System.out.println("Address :"+omsServiceHelper.getShippingAdderss(dataOfInvoice));
		System.out.println("Size :"+omsServiceHelper.getSize(dataOfInvoice));
		System.out.println("Unit Price:"+omsServiceHelper.getUnitPrice(dataOfInvoice));
		System.out.println("Quantity :"+omsServiceHelper.getQuantity(dataOfInvoice));
	}

	@Test(enabled = true, groups = { "regression","invoice" },description="Generate Invoice when Free item in the shipment")
	public void generateInvoiceWhenFreeItemInTheShipment() throws Exception {
		DBUtilities.exUpdateQuery("update inventory set `available_in_warehouses`='19', `inventory_count`=100000, `blocked_order_count`=0 where sku_id=493441", "myntra_atp");
	 
		HashMap<String,String> dataOfInvoice = new HashMap<String,String>();
	    String orderID;
		String skuId[] = { "848606:1" };
		
	    orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, true, true, "",true);
	    omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		End2EndHelper.sleep(400000l);
		OrderEntry orderEntry = omsServiceHelper.getOrderEntry(orderID);
		Assert.assertEquals(orderEntry.getOrderReleases().size(), 2,"Two relese is created.");
		
		dataOfInvoice=omsServiceHelper.getInvoicePdf("70127931", "4999DRSS00109");
		System.out.println("Address :"+omsServiceHelper.getShippingAdderss(dataOfInvoice));
		System.out.println("Size :"+omsServiceHelper.getSize(dataOfInvoice));
		System.out.println("Unit Price:"+omsServiceHelper.getUnitPrice(dataOfInvoice));
		System.out.println("Quantity :"+omsServiceHelper.getQuantity(dataOfInvoice));
	   
		
	}
}
