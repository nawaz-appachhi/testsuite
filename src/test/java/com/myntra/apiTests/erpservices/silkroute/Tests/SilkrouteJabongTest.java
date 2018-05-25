package com.myntra.apiTests.erpservices.silkroute.Tests;

import com.myntra.apiTests.erpservices.silkroute.SilkRouteConstants;
import com.myntra.apiTests.erpservices.silkroute.SilkRouteServiceHelper;
import com.myntra.apiTests.erpservices.silkroute.dp.SilkRouteServiceDP;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.silkroute.client.response.jabong.BaseSilkrouteRespForJabong;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.myntra.apiTests.erpservices.bounty.dp.BountyServiceDP.getRandomOrderId;


/**
 * Created by abhijit on 09/03/17.
 */
public class SilkrouteJabongTest extends BaseTest {
    SilkRouteServiceHelper silkRouteServiceHelper = new SilkRouteServiceHelper();
    Logger log = Logger.getLogger(SilkRouteServiceHelper.class);
    String ldt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String ldt3 = LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    final private static String store_id = "1";
    final private static String warehouse = "20";


    @Test(description = "Create Order Jabong with Various scenarios", dataProvider = "jabong_CreateOrder", dataProviderClass = SilkRouteServiceDP.class)
    public void createOrderJabong(String[] attributeList, double totalAmount, String shippingPincode, String processingStartDate, String processingEndDate, String type, String errorMessage) throws IOException {
        Svc svc = silkRouteServiceHelper.createOrderJabong(attributeList, totalAmount, shippingPincode, processingStartDate, processingEndDate, type);
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
        Assert.assertEquals(baseSilkrouteRespForJabong.getErrMsg(), errorMessage, "Error Message : ");
    }

    @Test(description = "Validate Block Order Count After Order Placement", dataProvider = "jabong_CreateOrder", dataProviderClass = SilkRouteServiceDP.class)
    public void createOrderJabongValidateInventory() throws IOException {
        int imscountBeforeOrderPlacement = SilkRouteServiceHelper.getIMSboc(Integer.parseInt(SilkRouteConstants.SKU_ID_JABONG_CREATEORDER), store_id, warehouse);
        Svc svc = silkRouteServiceHelper.createOrderJabong(new String[] {getRandomOrderId()+":"+"400.01:+500.01:1:"+ SilkRouteConstants.SKU_ID_JABONG_CREATEORDER+":JUST_IN_TIME:"+SilkRouteConstants.SELLER_ID_JABONG}, 500.01, "560068", ldt, ldt3, "normal");
        BaseSilkrouteRespForJabong baseSilkrouteRespForJabong = (BaseSilkrouteRespForJabong) APIUtilities.getJsontoObject(svc.getResponseBody(), new BaseSilkrouteRespForJabong());
        Assert.assertEquals(baseSilkrouteRespForJabong.getErrMsg(), "", "Error Message : ");
        int imscountAfterOrderPlacement = SilkRouteServiceHelper.getIMSboc(Integer.parseInt(SilkRouteConstants.SKU_ID_JABONG_CREATEORDER), store_id, warehouse);
        Assert.assertEquals(imscountAfterOrderPlacement, imscountBeforeOrderPlacement+1, "IMS BOC After Order Placement");
    }

}
