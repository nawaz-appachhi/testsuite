package com.myntra.apiTests.erpservices.silkroute.dp;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.SellerAndSkus;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sneha.Agarwal on 07/11/17.
 */
public class MFBJabongDP extends BaseTest{

    @DataProvider
    public static Object[][] jabong_CreateOrder(
            ITestContext testContext) {
        String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ldt3 = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String randomOrderID1 = SilkRouteServiceDP.getRandomOrderId();
        List<Object> dataset= new ArrayList<>();

        //"attributeList" - "String itemID, double itemMRP, double itemAmountBeforeTax, int quantity, String skuID, String supplyType"
        /*String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type*/
        dataset.add(new Object[] {getMapData(randomOrderID1,"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Normal order
        dataset.add(new Object[] {getMapData(randomOrderID1,"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",8049});//Same Item Id error
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "", "cod",0}); //Type is missing})
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "", ldt, ldt3, "normal", "cod",8017}); //Shipping Pin code is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR), 500.01, "560068", "", ldt3, "normal","cod",8026}); //Processing StartDate is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR), 500.01, "560068", ldt3, "", "normal","cod", 8026}); //Processing EndDate is missing
        dataset.add(new Object[] {getMapData("","400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR), 500.01, "560068", ldt, ldt3, "normal","cod", 8017}); //ItemId is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1","",EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR), 500.01, "560068", ldt, ldt3, "normal", "cod", 8026}); //Sku Id is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Qty is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,"",SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",8057});//SupplyType is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Item MRP is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Item MRP Before Tax  is missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"500.01","400.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Item MRP Before Tax is More than Item MRP
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,"",EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",8058});//Seller ID  missing
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","3",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Quantity is more than one
        return Toolbox.returnReducedDataSet(dataset.toArray(new Object[0][]),
                testContext.getIncludedGroups(), dataset.size(), dataset.size());
    }

    @DataProvider
    public static Object[][] jabongCreateOderOther(ITestContext testContext){
        String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String ldt3 = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String randomOrderID1 = SilkRouteServiceDP.getRandomOrderId();
        List<Object> dataset= new ArrayList<>();

        //"attributeList" - "String itemID, double itemMRP, double itemAmountBeforeTax, int quantity, String skuID, String supplyType"
        /*String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type*/
        dataset.add(new Object[] {getMapData(SilkRouteServiceDP.getRandomOrderId(),"400.01","500.01","1",SilkRouteConstants.SKU_ID_JABONG_CREATEORDER_ONHAND,EnumSCM.ON_HAND,SellerAndSkus.seller19,EnumSCM.wareHouse_BLR),500.01, "560068", ldt, ldt3, "normal", "cod",0});//Normal order
        return Toolbox.returnReducedDataSet(dataset.toArray(new Object[0][]),
                testContext.getIncludedGroups(), dataset.size(), dataset.size());
    }

    public static Map<String,String> getMapData(String itemId,String itemMrp,String itemAmountBeforeTax, String quantity,String skuId,String supplyType,String sellerId,String warehouseId){
        HashMap<String,String> hmp = new HashMap<String,String>();
        hmp.put("itemId",itemId);
        hmp.put("itemMrp",itemMrp);
        hmp.put("itemAmountBeforeTax",itemAmountBeforeTax);
        hmp.put("quantity",quantity);
        hmp.put("skuId",skuId);
        hmp.put("supplyType",supplyType);
        hmp.put("sellerId",sellerId);
        hmp.put("warehouseId",warehouseId);
        return hmp;
    }
}
