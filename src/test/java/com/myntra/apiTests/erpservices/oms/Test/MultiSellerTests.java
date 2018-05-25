package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.client.notification.response.StatusResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OmsCommonResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 16553 on 11/11/16.
 */

public class MultiSellerTests extends BaseTest{
    static Initialize init = new Initialize("/Data/configuration");
   /* String login= OMSTCConstants.OMSMultiSeller.LOGIN_URL;
    String password= OMSTCConstants.OMSMultiSeller.PASSWORD;*/
    String login= OMSTCConstants.OMSMultiSeller.LOGIN_URL;
    String password= OMSTCConstants.OMSMultiSeller.PASSWORD;
    HashMap<String,String> address_courier = new HashMap<String,String>();
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    static org.slf4j.Logger log = LoggerFactory.getLogger(MultiSellerTests.class);
    private OrderEntry orderEntry;
    private String orderReleaseId;
    SoftAssert sft;
	private String supplyTypeOnHand = "ON_HAND";


    @BeforeTest
    public void setaddressValuesFox7(){
        address_courier.put("IndiaPost","6137216");
        address_courier.put("GOR","6137217");
        address_courier.put("DE","6137218");
        address_courier.put("EKL","6137224");
    }

    /*public void setaddressValuesFox8(){
        address_courier.put("IndiaPost","6137223");
        address_courier.put("GOR","6137225");
        address_courier.put("DE","6137226");
        address_courier.put("EKL","6137224");
    }*/


    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = NONE")
    public void placeOrderForMultipleSellerTypeNone() throws Exception{
        sft=new SoftAssert();
       // String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":2", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":2", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":3"};
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":1",OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                            OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                            OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                                                   /* OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                    OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                    OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
       .                                             OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                    OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                    OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",*/},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                               OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                               OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                                                       /* OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                        OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                        OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":19:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                                                        OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                        OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                        OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":1:0:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",*/},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        Thread.sleep(5000);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only one as the sellersplit type is none: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
       // end2EndHelper.markOrderDelivered(orderID, "DL", "ORDER");
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = NONE but applicable for warehouse split")
    public void placeOrderForMultipleSellerTypeNoneWithWarehouseSpilt() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":1", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":1", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":1"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":28:100:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:10:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:10:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":28:100:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:10:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:10:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(2,orderEntry.getOrderReleases().size(),"The order release should be two though the sellersplit type is none but due to different warehouse: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = SELLER_SPLIT")
    public void placeOrderForMultipleSellerTypeSellerLevel() throws Exception{
        sft=new SoftAssert();
        ProcessRelease processRelease = new ProcessRelease();
        String skuId[] = {OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":1", OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);

        omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only one as the sellersplit type is none: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();


        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
        ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);

       // processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getReleaseId(orderID), ReleaseStatus.DL).build());
    }


    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = SKU_LEVEL")
    public void placeOrderForMultipleSellerTypeSKU_LEVEL() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":1", OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":1", OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU3+":1"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU3+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU3+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(3,orderEntry.getOrderReleases().size(),"The order release should be three as the sellersplit type is SKU_LEVEL: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = QUANTITY_LEVEL")
    public void placeOrderForMultipleSellerTypeQTY_LEVEL() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":2", OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU2+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":36:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":36:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID, "WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(4,orderEntry.getOrderReleases().size(),"The order release should be 4 as the sellersplit type is QUANTITY_LEVEL : Actual :" +orderEntry.getOrderReleases().size() );
        sft.assertAll();
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers with multiple SPLITTYPEs = NONE and QUANTITY_LEVEL")
    public void placeOrderForMultipleSellerTypeNoneAndQTY() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":2", OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:1000:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":36:1000:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":36:2:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1"},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(3,orderEntry.getOrderReleases().size(),"The order release should be 3 as the sellersplit type is NONE and QUANTITY_LEVEL: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers with multiple SPLITTYPEs = SELLER_SPLIT and SKU_LEVEL")
    public void placeOrderForMultipleSellerTypeSellerLevelAndSKULevel() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":1", OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":2",OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":1", OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":1"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":36:2:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU2+":36:2:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:1:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, true, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        Thread.sleep(5000);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(3,orderEntry.getOrderReleases().size(),"The order release should be 3 as the sellersplit type is SellerLevelAndSKULevel: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }

    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers with all SPLITTYPEs = NONE,SELLER_SPLIT,SKU_LEVEL and QUANTITY_LEVEL")
    public void placeOrderForAllMultipleSellerTypes() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku2+":2", OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":2",OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":2", OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":1",OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku2+":36:10:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:10:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:10:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:10:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1SKU1+":36:10:0:"+OMSTCConstants.SellerTypeSellerLevelAndSKUs.sellerLevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        Thread.sleep(5000);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(6,orderEntry.getOrderReleases().size(),"The order release should be 3 as the sellersplit type is NONE and QUANTITY_LEVEL: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
        //end2EndHelper.markOrderDelivered(orderID, "DL", "ORDER");
    }


    //Address not changing - SNeha
    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Change address for a shipment having mutiple sellers to a pincode where all sellers are serviceable")
    public void placeOrderForMultipleSellerAddressChange() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":2", OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":2",OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":2", OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":1"};
        List<Long> rIds=new ArrayList<Long>() ;
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:20:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU1+":36:20:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",
                OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1SKU2+":36:20:0:"+OMSTCConstants.SellerTypeSKULevelAndSKUs.sellerSKULevel1+":1",},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
       Thread.sleep(5000L);
       omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(5,orderEntry.getOrderReleases().size(),"The order release should be 5 as the sellersplit type is NONE and QUANTITY_LEVEL: Actual :" +orderEntry.getOrderReleases().size());
        for(OrderReleaseEntry orderRelease:orderEntry.getOrderReleases()) {
            OmsCommonResponse omsResponse=omsServiceHelper.changeAddress(orderRelease.getId().toString(), "Sneha", "Agarwal", "Bangalore", "Hosur", "560100",true,login);
            sft.assertEquals(StatusResponse.Type.SUCCESS.toString(),omsResponse.getStatus().getStatusType().toString());
        }
        sft.assertAll();
    }


    //passed in delta 7 - Sneha
    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Change address for a shipment having mutiple sellers to a pincode where all sellers are serviceable and For Courier Doesnot Support Shipment Consolidation")
    public void placeOrderForMultipleSellerAddressChangeForCourierDoesnotSupportShipmentConsolidation() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":1",OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                            OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                            OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                                                               OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2+":1",
                                                               OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1"
                                                        		},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.Pincodes.PINCODE_560068, skuId, "", false, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(1,orderEntry.getOrderReleases().size(),
        		"The order release should be 1 as the sellersplit type is NONE:" +orderEntry.getOrderReleases().size());
       // Change Address to IP which doesnot support multiseller consilidation
        OmsCommonResponse omsResponse=omsServiceHelper.changeAddress(orderEntry.getOrderReleases().get(0).getId().toString(), "Sneha", "Agarwal", "Pune", "Hosur", "411001",true,login);
        sft.assertEquals(StatusResponse.Type.SUCCESS.toString(),omsResponse.getStatus().getStatusType().toString(),"Address change failed");

        //Check Release should be split
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(2,orderEntry.getOrderReleases().size(),
        		"The order release should be 2 after address change" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }




    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers with multiple SPLITTYPEs = NONE and QUANTITY_LEVEL")
    public void placeOrderForMultipleSellerTypeQTYForDiffCouriers() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":2",OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":2"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku2+":36:2:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1+":1",
                OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1SKU1+":45:20:0:"+OMSTCConstants.SellerTypeQtyLevelAndSKUs.sellerQTYLevel1+":1"},supplyTypeOnHand);

        String orderID = end2EndHelper.createOrder(login, password, address_courier.get("IndiaPost"), skuId, "", false, false, false, "", false);
 
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(3,orderEntry.getOrderReleases().size(),"The order release should be 3 as the sellersplit type is NONE and QUANTITY_LEVEL: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }


    @Test(enabled = true,groups={"regression","multisellertest","splitorder"}, description = "Place a order for multiple sellers where SPLITTYPE = NONE")
    public void placeOrderForMultipleSellerTypeNoneForDiffCourier() throws Exception{
        sft=new SoftAssert();
        String skuId[] = {OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone1Sku1+":2", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone2Sku1+":2", OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":3"};

        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1",
                OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3Sku1+":36:50:0:"+OMSTCConstants.SellerTypeNoneAndSKUs.sellerNone3+":1"
               		},supplyTypeOnHand );

        String orderID = end2EndHelper.createOrder(login, password, address_courier.get("EKL"), skuId, "", false, false, false, "", false);
        Thread.sleep(5000);
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        sft.assertEquals(1,orderEntry.getOrderReleases().size(),"The order release should be only one as the sellersplit type is none: Actual :" +orderEntry.getOrderReleases().size());
        sft.assertAll();
    }



}
