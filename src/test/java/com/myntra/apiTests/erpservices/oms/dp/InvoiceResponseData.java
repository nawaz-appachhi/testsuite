package com.myntra.apiTests.erpservices.oms.dp;

import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 16553 on 27/02/17.
 */
public class InvoiceResponseData extends BaseTest {

    static Initialize init = new Initialize("/Data/configuration");

    private String login = OMSTCConstants.OMSOrderCancellationServiceTest.LOGIN_URL;
    private String uidx;
    private String password = OMSTCConstants.OMSOrderCancellationServiceTest.PASSWORD;
    static String login_discount = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestLogin;
    static String password_discount = OMSTCConstants.LoginAndPassword.GovtTaxCalculationTestPassword;
    String coupon_Less_Than_20="govttax1";
    String coupon_More_Than_20="govttax";
    private static Long vectorSellerID= SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);;
    private static Logger log = Logger.getLogger(InvoiceResponseData.class);
    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
    private OrderEntry orderEntry;
    private Long orderReleaseId;
    private OrderReleaseResponse orderReleaseResponse;
    private String supplyTypeOnHand = "ON_HAND";
    private static List<HashMap<String,String>> excelData = new LinkedList<HashMap<String,String>>();





    @DataProvider (name="OrderWithMultipleLines_AndSingleLineCancellation_WithDiscountsData")
    public Object[][] OrderWithMultipleLines_AndSingleLineCancellation_WithDiscountsData(ITestContext testContext) throws Exception{
     //   excelData=InvoiceServiceHelper.getDataFromExcel();
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        String skuId[] = { OMSTCConstants.OtherSkus.skuId_3832+":2", OMSTCConstants.OtherSkus.skuId_3837+":2" };
        atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1",OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID+":1"},supplyTypeOnHand);
        imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3832+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID,OMSTCConstants.OtherSkus.skuId_3837+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+","+OMSTCConstants.WareHouseIds.warehouseId36_BW+":10000:0:"+vectorSellerID},supplyTypeOnHand);
        String orderID = end2EndHelper.createOrder(login_discount, password_discount, OMSTCConstants.Pincodes.PINCODE_560068, skuId, coupon_More_Than_20, false, false, false,"", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId();
        list.add(new Object[]{orderEntry,login_discount,excelData.get(15)});
        return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
                list.size());
    }
}
