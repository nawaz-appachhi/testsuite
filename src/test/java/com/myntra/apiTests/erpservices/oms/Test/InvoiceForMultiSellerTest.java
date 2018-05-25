package com.myntra.apiTests.erpservices.oms.Test;

import static org.testng.Assert.assertEquals;

import java.sql.SQLException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.InvoicePdfParsingHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.paymentplan.domain.response.PaymentPlanResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class InvoiceForMultiSellerTest extends BaseTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	String login = "end2endautomation1@gmail.com";
	String uidx;
	String password = OMSTCConstants.CancellationTillHubTest.PASSWORD;
	String dis_login=OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
	String dis_password=OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
	String couponCode=OMSTCConstants.LoginAndPassword.DiscountCouponMoreThan20;
    private static Long vectorSellerID;
    private static Logger log = Logger.getLogger(OMSServiceHelper.class);

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
	BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
	private String orderID;
	private OrderEntry orderEntry;
	PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
	private PaymentPlanResponse response;
	
	/*//Fox8 sellerIds
	private int sellerId_HEAL = 21;
	private int sellerId_CONS = 32;
	private int sellerId_HEAL_JIT = 21;
	private int sellerId_CONS_JIT = 4;*/
	
	//Fox7 SellerIds
	private int sellerId_HEAL = 21;
	private int sellerId_CONS = 25;
	private int sellerId_HEAL_JIT = 21;
	private int sellerId_CONS_JIT = 25;
	
	private Long orderReleaseId1;
	
	@BeforeClass(alwaysRun = true)
	public void testBeforeClass() throws SQLException, JAXBException, Exception {
		omsServiceHelper.updateGiftWrapCharges(25);
		omsServiceHelper.refreshOMSApplicationPropertyCache();
		omsServiceHelper.refreshOMSJVMCache();
        vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
        uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", login);
        
	}
	
	
	//Passed
	@Test(enabled = true,groups={"regression"}, description = "TC001:Generate Invoice for MultipleSeller")
	public void orderWithMultipleSellerWithAdditionalCharges() throws Exception {
		/*//Fox8 Skus Mapping
		String skuId[] = {"3835:2",OMSTCConstants.OtherSkus.skuId63+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3835:36:100:0:21:1",OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{"3835:36:100:0:21:1","3835:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1","3835:1:0:0:21:1","3835:19:0:0:21:1",
				OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1",OMSTCConstants.OtherSkus.skuId63+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":2:0:0:32:1"},"ON_HAND");*/
		
		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 +":2",OMSTCConstants.OtherSkus.skuId_749874+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS},"ON_HAND");

		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, true, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        //Verify there should be only one Release
        Assert.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only. Actual :" +orderEntry.getOrderReleases().size());
        
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId();
		
        InvoicePdfParsingHelper invoicePdfParsingHelper =new InvoicePdfParsingHelper();
        List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();
        
        for(OrderReleaseEntry releaseEntry: releases){
        	omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
        	End2EndHelper.sleep(10000);
        	updateCourierAndTrackingNumber(releaseEntry.getId(),"ML","ML000001");
    		invoicePdfParsingHelper.validateInvoiceAndShippingPartOfSellerInInvoicePDF(releaseEntry.getId().toString());
        }
	}
	

	@Test(enabled = true,groups={"regression"}, description = "TC002:Generate Invoice for MultipleSeller with CB and LP")
	public void orderWithMultipleSellerWithLPAndDiscounts() throws Exception {
			/*//Fox8 Skus Mapping
			String skuId[] = {"3835:2",OMSTCConstants.OtherSkus.skuId63+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3835:36:100:0:21:1",OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1"},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{"3835:36:100:0:21:1","3835:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1","3835:1:0:0:21:1","3835:19:0:0:21:1",
					OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1",OMSTCConstants.OtherSkus.skuId63+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":2:0:0:32:1"},"ON_HAND");*/

		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 +":2",OMSTCConstants.OtherSkus.skuId_749874+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS},"ON_HAND");
		end2EndHelper.updateloyalityAndCashBack(uidx,50,0);
		orderID = end2EndHelper.createOrder(dis_login, dis_password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, couponCode, false, true, true, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		//Verify there should be only one Release
		Assert.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only. Actual :" +orderEntry.getOrderReleases().size());

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId();
		InvoicePdfParsingHelper invoicePdfParsingHelper =new InvoicePdfParsingHelper();
		List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();

		for(OrderReleaseEntry releaseEntry: releases){
			omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
			End2EndHelper.sleep(10000);
			updateCourierAndTrackingNumber(releaseEntry.getId(),"ML","ML0000001");
			invoicePdfParsingHelper.validateInvoiceAndShippingPartOfSellerInInvoicePDF(releaseEntry.getId().toString());
		}

	}

	@Test(enabled = true,groups={"regression"}, description = "TC002:Generate Invoice for MultipleSeller with CB and LP")
	public void orderWithMultipleSellerWithCBAndLP() throws Exception {
			/*//Fox8 Skus Mapping
			String skuId[] = {"3835:2",OMSTCConstants.OtherSkus.skuId63+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3835:36:100:0:21:1",OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1"},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{"3835:36:100:0:21:1","3835:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1","3835:1:0:0:21:1","3835:19:0:0:21:1",
					OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1",OMSTCConstants.OtherSkus.skuId63+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":2:0:0:32:1"},"ON_HAND");*/

		//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 +":2",OMSTCConstants.OtherSkus.skuId_749874+":2"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},"ON_HAND");
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
				OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS},"ON_HAND");
		end2EndHelper.updateloyalityAndCashBack(uidx,50,50);
		orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", true, true, true, "", false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		//Verify there should be only one Release
		Assert.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only. Actual :" +orderEntry.getOrderReleases().size());

		orderEntry = omsServiceHelper.getOrderEntry(orderID);
		orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId();
		InvoicePdfParsingHelper invoicePdfParsingHelper =new InvoicePdfParsingHelper();
		List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();

		for(OrderReleaseEntry releaseEntry: releases){
			omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
			End2EndHelper.sleep(10000);
			updateCourierAndTrackingNumber(releaseEntry.getId(),"ML","ML0000001");
			invoicePdfParsingHelper.validateInvoiceAndShippingPartOfSellerInInvoicePDF(releaseEntry.getId().toString());
		}

	}
		
		@Test(enabled = true,groups={"regression"}, description = "TC001:Generate Invoice for MultipleSeller for EK courier")
		public void orderWithMultipleSellerWithAdditionalChargesForEK() throws Exception {
			/*//Fox8 Skus Mapping
			String skuId[] = {"3835:2",OMSTCConstants.OtherSkus.skuId63+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3835:36:100:0:21:1",OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1"},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{"3835:36:100:0:21:1","3835:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1","3835:1:0:0:21:1","3835:19:0:0:21:1",
					OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1",OMSTCConstants.OtherSkus.skuId63+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":2:0:0:32:1"},"ON_HAND");*/
			
			//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 +":2",OMSTCConstants.OtherSkus.skuId_749874+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1",
					OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS+":1"},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
					OMSTCConstants.OtherSkus.skuId_749874+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_CONS},"ON_HAND");

			login = "bountytestuser001@myntra.com";
			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_411001, skuId, "", false, false, true, "", false);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        //Verify there should be only one Release
	        Assert.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only. Actual :" +orderEntry.getOrderReleases().size());
	        
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId();
			InvoicePdfParsingHelper invoicePdfParsingHelper =new InvoicePdfParsingHelper();
	        List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();
	        
	        for(OrderReleaseEntry releaseEntry: releases){
	        	omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
	        	End2EndHelper.sleep(10000);
	        	updateCourierAndTrackingNumber(releaseEntry.getId(),"EK","EK000001");
	    		invoicePdfParsingHelper.validateInvoiceAndShippingPartOfSellerInInvoicePDF(releaseEntry.getId().toString());
	        }
		}
		
		//Passed
		@Test(enabled = true,groups={"regression"}, description = "TC001:Generate Invoice for MultipleSeller")
		public void orderWithSingleeSellerWithAdditionalCharges() throws Exception {
			/*//Fox8 Skus Mapping
			String skuId[] = {"3835:2",OMSTCConstants.OtherSkus.skuId63+":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{"3835:36:100:0:21:1",OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1"},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{"3835:36:100:0:21:1","3835:"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:21:1","3835:1:0:0:21:1","3835:19:0:0:21:1",
					OMSTCConstants.OtherSkus.skuId63+":36:100:0:32:1",OMSTCConstants.OtherSkus.skuId63+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":1:0:0:32:1",OMSTCConstants.OtherSkus.skuId63+":2:0:0:32:1"},"ON_HAND");*/
			
			//Fox7 Skus Mapping 3831:sellerId_HEAL  3834:sellerId_HEAL 749874:sellerId_CONS
			String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831 +":2"};
			atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL+":1"
					},"ON_HAND");
			imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831 +":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":100:0:"+sellerId_HEAL,
					},"ON_HAND");

			orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, true, "", false);
	        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        //Verify there should be only one Release
	        Assert.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only. Actual :" +orderEntry.getOrderReleases().size());
	        
	        orderEntry = omsServiceHelper.getOrderEntry(orderID);
	        orderReleaseId1 = orderEntry.getOrderReleases().get(0).getId();
			
	        InvoicePdfParsingHelper invoicePdfParsingHelper =new InvoicePdfParsingHelper();
	        List<OrderReleaseEntry> releases = orderEntry.getOrderReleases();
	        
	        for(OrderReleaseEntry releaseEntry: releases){
	        	omsServiceHelper.stampGovtTaxForVectorSuccess(releaseEntry.getId().toString());
	        	End2EndHelper.sleep(10000);
	        	updateCourierAndTrackingNumber(releaseEntry.getId(),"ML","ML000001");
	    		invoicePdfParsingHelper.validateInvoiceAndShippingPartOfSellerInInvoicePDF(releaseEntry.getId().toString());
	        }
		}


		
		public void updateCourierAndTrackingNumber(long releaseId,String courier,String trackingNum){
        	String query = "update order_release set courier_code='"+courier+"',tracking_no='"+trackingNum+"'  where id = '"+releaseId+"';";
    		DBUtilities.exUpdateQuery(query, "myntra_oms");
		}
}