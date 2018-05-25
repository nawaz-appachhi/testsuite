package com.myntra.apiTests.erpservices.oms.dp;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.oms.TaxMasterServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.taxmaster.client.entry.request.OmsAdditionalChargesTaxRequestEntry;
import com.myntra.taxmaster.client.entry.request.OmsDiscountEntry;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sneha Agarwal on 24/08/16.
 */
public class GovtTaxDP {
    private static Double taxRate=5.5;
	static String login = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
	static String password = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
	static IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    private static String uidx ;
    private static BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    private static End2EndHelper end2EndHelper = new End2EndHelper();

    @DataProvider
    public static Object[][] GovtTaxData(ITestContext testContext){
         ArrayList<Object[]> list = new ArrayList<Object[]>();
        String skuId[]={ OMSTCConstants.OtherSkus.skuId_3832+":1" ,OMSTCConstants.OtherSkus.skuId_3832+":2"};
        list.add(new Object[]{"Order_With_Shipping_Charge",skuId[0],taxRate});
        list.add(new Object[]{"Order_WithOut_Shipping_Charge",skuId[1],taxRate});
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }

    @DataProvider
    public static Object[][] GovtTaxDataForCouponsLessThan20(ITestContext testContext) throws IOException{
        String coupon_Less_Than_20="govttax1";
        ArrayList<Object[]> list = new ArrayList<Object[]>();
       // uidx = ideaApiHelper.getUIDXForLogin("myntra", login);
       // String coupon_Less_Than_20=bountyServiceHelper.createNewCoupon(uidx,"govttax1",15);
        String skuId[]={ OMSTCConstants.OtherSkus.skuId_3832+":1" ,OMSTCConstants.OtherSkus.skuId_3832+":2"};
        list.add(new Object[]{"Order_With_Shipping_Charge_And_DiscountLessThan20",skuId[0],taxRate,coupon_Less_Than_20});
        list.add(new Object[]{"Order_Without_Shipping_Charge_And_DiscountLessThan20",skuId[1],taxRate,coupon_Less_Than_20});
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }

    @DataProvider
    public static Object[][] GovtTaxDataForCouponsMoreThan20(ITestContext testContext) throws Exception {
        String coupon_More_Than_20="govttax";
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        //uidx = ideaApiHelper.getUIDXForLogin("myntra", login);
       // String coupon_More_Than_20=bountyServiceHelper.createNewCoupon(uidx,"govttax",21);
       // String skuId[]={ "3868:1"};
        String skuId[]={ OMSTCConstants.OtherSkus.skuId_3832+":1" ,OMSTCConstants.OtherSkus.skuId_3832+":2"};
        list.add(new Object[]{"Order_With_Shipping_Charge_And_DiscountMoreThan20",skuId[0],taxRate,coupon_More_Than_20});
        list.add(new Object[]{"Order_Without_Shipping_Charge_And_DiscountMoreThan20",skuId[1],taxRate,coupon_More_Than_20});
       // long orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, coupon_More_Than_20, false, false, false, "",false);
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }

    @DataProvider
    public static Object[][] itemLevelTaxCouponLessThan20DP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        long sellerId=21;
        Date orderDate = new Date();

        double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discount10Perct = (unitMrp * 10)/100.0;
        Double discount17Perct = (unitMrp * 17)/100.0;
        Double discount18Perct = (unitMrp * 18)/100.0;
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount17Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount18Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
       
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
    
    @DataProvider
    public static Object[][] itemLevelTaxOtherSellerDP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        long sellerId=1;
        Date orderDate = new Date();

        double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discount10Perct = (unitMrp * 10)/100.0;
        Double discount17Perct = (unitMrp * 17)/100.0;
        Double discount23Perct = (unitMrp * 23)/100.0;
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount17Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        sellerId=19;
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount23Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        sellerId=25;
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
       
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }

    
    @DataProvider
    public static Object[][] itemLevelTaxCouponGreaterThan20DP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        long sellerId=21;
        Date orderDate = new Date();
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discount19Perct = (unitMrp * 19)/100.0;
        Double discount20Perct = (unitMrp * 20)/100.0;
        Double discount30Perct = (unitMrp * 30)/100.0;
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount19Perct);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount20Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount30Perct);
        unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
    
    
    @DataProvider
    public static Object[][] itemLevelTaxNegativeDP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discountLessThan20 = (unitMrp * 10)/100.0;
        Double discountGreaterThan20 = (unitMrp * 30)/100.0;
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discountLessThan20);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        long sellerId=21;
        Date orderDate = new Date();
        
        list.add(new Object[]{null,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        list.add(new Object[]{styleId,(Long)null,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});        
        list.add(new Object[]{styleId,destinationPincode,(Long)null,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,null,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,null,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		(Double)null,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,null,orderDate});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,null});
        
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
    
    @DataProvider
    public static Object[][] bulkItemLevelTaxDP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        long sellerId=21;
        Date orderDate = new Date();
        long entityId = 123456789L;
        String entityType = "OrderLine";
        
        double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discount10Perct = (unitMrp * 10)/100.0;
        Double discount17Perct = (unitMrp * 17)/100.0;
        Double discount18Perct = (unitMrp * 18)/100.0;
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discount10Perct);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        
         return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
    
    @DataProvider
    public static Object[][] bulkItemLevelTaxNegativeDP(ITestContext testContext) throws IOException{
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        
        TaxMasterServiceHelper taxMasterServiceHelper = new TaxMasterServiceHelper();
        List<String> additionalCharges = new ArrayList<String>() ;
        additionalCharges.add(0, "additionalCharge:20");
        
        
        long styleId=1541;
        long destinationPincode=560068;
        long sourceWarehouseId=36;
        String courierCode="ML";
        int quantity=1;
        Double unitMrp=getUnitPriceForStyle(styleId);
        List<OmsAdditionalChargesTaxRequestEntry> unitLevelProratedAdditionalCharges=taxMasterServiceHelper.
        		setOMSAdditionalChargesTaxRequestEntry(additionalCharges);
        Double discountLessThan20 = (unitMrp * 10)/100.0;
        Double discountGreaterThan20 = (unitMrp * 30)/100.0;
        Long entityId = 123456789L;
        String entityType = "OrderLine";
        
        OmsDiscountEntry discountEntry =taxMasterServiceHelper.setOmsDiscountEntry("Coupon", discountLessThan20);
        List<OmsDiscountEntry> unitLevelProratedDiscounts= new ArrayList<OmsDiscountEntry>();
        unitLevelProratedDiscounts.add(0, discountEntry);
        
        long sellerId=21;
        Date orderDate = new Date();
        
        list.add(new Object[]{null,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,(Long)null,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});        
        list.add(new Object[]{styleId,destinationPincode,(Long)null,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,null,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,null,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		(Double)null,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,null,orderDate,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,null,entityId,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,null,null,entityType});
        list.add(new Object[]{styleId,destinationPincode,sourceWarehouseId,courierCode,quantity,
        		unitMrp,unitLevelProratedAdditionalCharges,unitLevelProratedDiscounts,sellerId,null,entityId,null});
        
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }



    
    
    /**
     * @param styleId
     * @return
     */
    public static Double getUnitPriceForStyle(long styleId){
    	String query = "";
    	HashMap<String, Object> productStyle = (HashMap<String, Object>) 
				DBUtilities.exSelectQueryForSingleRecord("select * from mk_product_style where id ="+styleId, "myntra");
		Double unitPrice =  (Double) productStyle.get("price");
		return unitPrice;
		
    }

}
