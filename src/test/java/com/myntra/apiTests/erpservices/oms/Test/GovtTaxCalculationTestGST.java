package com.myntra.apiTests.erpservices.oms.Test;


import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.dp.GovtTaxDP;
import com.myntra.apiTests.erpservices.oms.OrderDetails;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class GovtTaxCalculationTestGST extends BaseTest{

	String login = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
	String password = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
	String addressId = OMSTCConstants.Pincodes.PINCODE_560068;
	OrderDetails orderDetails = new OrderDetails();
	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();

	End2EndHelper end2EndHelper = new End2EndHelper();
	OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private OrderEntry orderEntry;
	private List<OrderReleaseEntry> orderReleaseEntries;
	private String orderReleaseId;
	private String supplyTypeOnHand= "ON_HAND";
	private static Long vectorSellerID;

	@BeforeClass(alwaysRun=true)
	public void testAfterClass() throws SQLException, UnsupportedEncodingException, JAXBException {
		DBUtilities.exUpdateQuery("UPDATE on_hold_reasons_master SET is_enabled=0 where id in (35,37)", "myntra_oms");
		DBUtilities.exUpdateQuery("UPDATE myntra_tools.core_application_properties SET value='20000000' where name='condCODOH.unpaidamount.limit'", "myntra_oms");
		vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);
		omsServiceHelper.refreshOMSApplicationPropertyCache();
        omsServiceHelper.refreshOMSJVMCache();
        end2EndHelper.refreshToolsApplicationPropertyCache();
    }

	
/*	@Test(enabled=false, groups={"smoke","regression","taxCalculation"})
	public void refreshMinTaxInfo() throws UnsupportedEncodingException, JAXBException{
		GovtTaxMinInfoResponse govtTaxMinInfoResponse = omsServiceHelper.refreshMinTaxInfo();
		Assert.assertEquals(govtTaxMinInfoResponse.getStatus().getStatusType(), Type.SUCCESS);
		Assert.assertTrue(govtTaxMinInfoResponse.getStatus().getTotalCount() > 0);
	}
*/
	@Test(enabled=true, groups={"smoke","regression","taxCalculation","sanity"}, dataProvider = "GovtTaxData", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithAndWihoutShippingCharge(String TestScenario,String sku,Double TaxRate) throws Exception{
		SoftAssert sft = new SoftAssert();
		String skuId[]={sku};
		sku = sku.split(":")[0];
		//to update the warehouse inventory
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseId = orderReleaseEntries.get(0).getId().toString();
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Double TaxableAmount=orderDetails.getTaxableAmountOnReleaseDiscountLessThan20(orderReleaseEntry);
		Double TaxAmount=Math.round((TaxableAmount*TaxRate/100)*100)/100.00;
		TaxableAmount=Math.round(TaxableAmount*100)/100.00;
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount(),TaxableAmount, "Govt Taxable amount mismatch.Expected : " +TaxableAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate(),TaxRate , "Govt Tax rate mismatch.Expected : " +TaxRate +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount(),TaxAmount, "Govt Tax amount mismatch . Expected : " +TaxAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount()+ " for orderId: " + orderID);
		sft.assertAll();
	}

	@Test(enabled=true, groups={"smoke","regression","taxCalculation"}, dataProvider = "GovtTaxData", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithGiftCharges_And_GIFTPLUSShippingCharges(String TestScenario,String sku,Double TaxRate) throws Exception{
		SoftAssert sft = new SoftAssert();
		String skuId[]={sku};
		sku = sku.split(":")[0];
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, "", false, false, true, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseId = orderReleaseEntries.get(0).getId().toString();
        
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Double TaxableAmount=orderDetails.getTaxableAmountOnReleaseDiscountLessThan20(orderReleaseEntry);
		Double TaxAmount=Math.round((TaxableAmount*TaxRate/100)*100)/100.00;
		TaxableAmount=Math.round(TaxableAmount*100)/100.00;
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate(),TaxRate , "Govt Tax rate mismatch.Expected : " +TaxRate +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount(),TaxableAmount, "Govt Taxable amount mismatch.Expected : " +TaxableAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount(),TaxAmount, "Govt Tax amount mismatch . Expected : " +TaxAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount()+ " for orderId: " + orderID);
		sft.assertAll();
	}

	@Test(enabled=true, groups={"smoke","regression","taxCalculation"}, dataProvider = "GovtTaxDataForCouponsLessThan20", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithDiscountCouponsLessThan20(String TestScenario,String sku,Double TaxRate,String couponCode) throws Exception{
		SoftAssert sft = new SoftAssert();
		String skuId[]={sku};
		sku = sku.split(":")[0];
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, couponCode, false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseId = orderReleaseEntries.get(0).getId().toString();
        
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Double TaxableAmount=orderDetails.getTaxableAmountOnReleaseDiscountLessThan20(orderReleaseEntry);
		Double TaxAmount=Math.round((TaxableAmount*TaxRate/100)*100)/100.00;
		TaxableAmount=Math.round(TaxableAmount*100)/100.00;
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate(),TaxRate , "Govt Tax rate mismatch.Expected : " +TaxRate +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate() + " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount(),TaxableAmount, "Govt Taxable amount mismatch.Expected : " +TaxableAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount() + " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount(),TaxAmount, "Govt Tax amount mismatch . Expected : " +TaxAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount()+ " for orderId: " + orderID);
		sft.assertAll();
	}

	@Test(enabled=true, groups={"smoke","regression","taxCalculation"}, dataProvider = "GovtTaxDataForCouponsMoreThan20", dataProviderClass = GovtTaxDP.class)
	public void validateGovtTaxableAmountAndGovtTaxRateWithDiscountCouponsMoreThan20(String TestScenario,String sku,Double TaxRate,String couponCode) throws Exception{
		SoftAssert sft = new SoftAssert();
		String skuId[]={sku};
		sku = sku.split(":")[0];
		imsServiceHelper.updateInventoryForSeller(new String[] {sku+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},supplyTypeOnHand);
		String orderID = end2EndHelper.createOrder(login, password, addressId, skuId, couponCode, false, false, false, "",false);
		omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseEntries = orderEntry.getOrderReleases();
        orderReleaseId = orderReleaseEntries.get(0).getId().toString();
        
		omsServiceHelper.stampGovtTaxForVectorSuccess(orderReleaseId);
		OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
		Double TaxableAmount=orderDetails.getTaxableAmountOnReleaseDiscountmoreThan20(orderReleaseEntry);
		Double TaxAmount=Math.round((TaxableAmount*TaxRate/100)*100)/100.00;
		TaxableAmount=Math.round(TaxableAmount*100)/100.00;
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate(),TaxRate , "Govt Tax rate mismatch.Expected : " +TaxRate +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxRate()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount(),TaxableAmount, "Govt Taxable amount mismatch.Expected : " +TaxableAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxableAmount()+ " for orderId: " + orderID);
		sft.assertEquals(orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount(),TaxAmount, "Govt Tax amount mismatch . Expected : " +TaxAmount +" Actual: "+orderReleaseEntry.getOrderLines().get(0).getGovtTaxAmount()+ " for orderId: " + orderID);
		sft.assertAll();
	}

	private BigDecimal truncateDecimal(final double x, final int numberofDecimals) {
		return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_DOWN);
	}


}
