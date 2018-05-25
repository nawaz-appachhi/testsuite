package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.commons.codes.StatusResponse;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;
import com.myntra.oms.client.response.OrderLineResponse;
import com.myntra.oms.client.response.OrderReleaseResponse;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author abhijit.pati
 * @since 21/07/2016
 */
public class OMSWalletTest extends BaseTest{

    private String login = "end2endautomation3@gmail.com";
    private String uidx = "22f46884.e7f1.4e53.b44b.ec7957ca2e92YSsxz47oRC";
    private String password = "123456";

    End2EndHelper end2EndHelper = new End2EndHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
	private OrderEntry orderEntry;
	private String orderReleaseId;



    @Test
    public void lineCancellationRefundWhenWalletPaymentInstrumentUsed() throws Exception {
        String[] skuId = {"3830:10"};
        Double walletBalance = end2EndHelper.getCashBackPoints(uidx);
        String orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();
        
        assertEquals(end2EndHelper.getCashBackPoints(uidx), (walletBalance-22), "CashBack Balance");
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        String lineID = ""+orderLineEntries.get(0).getId();
        omsServiceHelper.cancelLine(orderReleaseId, login,  new String[] {lineID +":1"}, 41);
        assertEquals(end2EndHelper.getCashBackPoints(uidx), walletBalance, "CashBack Balance");
    }

    @Test
    public void releaseCancellationRefundWhenWalletPaymentInstrumentUsed() throws Exception {
        String[] skuId = {"3828:5","3830:5"};
        Double walletBalance = end2EndHelper.getCashBackPoints(uidx);
        String orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        assertEquals(end2EndHelper.getCashBackPoints(uidx), (walletBalance-22), "CashBack Balance");
        OrderReleaseResponse orderReleaseEntry = omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "TestOrder Cancellation");

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 5),"Release is not in F status");
        end2EndHelper.sleep(20000);
        assertEquals(end2EndHelper.getCashBackPoints(uidx), (walletBalance-10), "CashBack Balance");

    }

    @Test
    public void orderCancellationRefundWhenWalletPaymentInstrumentUsed() throws Exception {
        String[] skuId = {"3830:10"};

        Double walletBalance = end2EndHelper.getCashBackPoints(uidx);

        String orderID = end2EndHelper.createOrder(login, password, "6118982", skuId, "", true, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        assertEquals(end2EndHelper.getCashBackPoints(uidx), (walletBalance-22), "CashBack Balance");
        OrderResponse cancellationRes = omsServiceHelper.cancelOrder(orderReleaseId, "1", login, "TestOrder Cancellation");

        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 5),"Release is not in F status");
        end2EndHelper.sleep(20000);
        assertEquals(end2EndHelper.getCashBackPoints(uidx), walletBalance, "CashBack Balance");
    }


    @Test
    public void returnRefundWhenWalletPaymentInstrumentUsed() throws Exception {
    }

    @Test
    public void priceOverrideRefundWhenWalletIsUsed() throws Exception {

        String skuId[] = { "3830:10" };

        double walletBalance = end2EndHelper.getCashBackPoints(uidx);
        String orderID = end2EndHelper.createOrder(login, "123456", "6118982", skuId, "", true, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        double walletBalanceAfterOrderPlace = walletBalance-12;

        String lineID = omsServiceHelper.getOrderReleaseEntry(orderReleaseId).getOrderLines().get(0).getId().toString();
        OrderLineResponse orderLineResponse = omsServiceHelper.priceOverride(lineID, "1.5", login,
                                                                             "Price found on the tag was lesser." + orderReleaseId + " Line ID : " + lineID);

        assertEquals(orderLineResponse.getStatus().getStatusType(), StatusResponse.Type.SUCCESS);
        assertEquals(orderLineResponse.getStatus().getStatusCode(), 1012);
        assertEquals(orderLineResponse.getData().get(0).getPriceMismatchRefund(), 5);

        OrderLineEntry orderLineEntry = orderLineResponse.getData().get(0);
        Assert.assertNotNull(orderLineEntry.getPriceMismatchRefundPpsId());
        Assert.assertNotEquals(orderLineEntry.getPriceMismatchRefundPpsId(), "",
                               "Price Mismatch Refund PPS ID should not be Empty");
        end2EndHelper.sleep(20000);
        assertEquals(end2EndHelper.getCashBackPoints(uidx), (walletBalanceAfterOrderPlace - 5), "CashBack Balance after Price Override");

        OrderReleaseEntry orderReleaseEntry = omsServiceHelper.getOrderReleaseEntry(orderReleaseId);
        omsServiceHelper.cancelLine(orderID, uidx, new String[] { "" + lineID + ":1" }, 41);
        end2EndHelper.sleep(60000);
        assertEquals(end2EndHelper.getCashBackPoints(uidx), walletBalance, "Verify CashBack Amount should be "+walletBalance);
    }

    @Test
    public void checkAmountFlowToLMSAfterPKWhenWalletInstrumentIsUsed() throws Exception {
        ProcessRelease processRelease = new ProcessRelease();
        String skuId[] = { "3830:10" };
        double walletBalance = end2EndHelper.getCashBackPoints(uidx);
        String orderID = end2EndHelper.createOrder(login, "123456", "6118982", skuId, "", true, false, false, "", false);
        omsServiceHelper.checkReleaseStatusForOrder(orderID,"WP");
        orderEntry = omsServiceHelper.getOrderEntry(orderID);
        orderReleaseId = orderEntry.getOrderReleases().get(0).getId().toString();

        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
        ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);
    }

    @Test
    public void validateCODOnHoldWhenWalletBalanceIsUsed(){

    }



}
