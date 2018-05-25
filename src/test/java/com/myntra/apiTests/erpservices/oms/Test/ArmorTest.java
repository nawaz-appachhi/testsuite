package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.response.OrderResponse;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit.Pati
 * on 01/12/16.
 */
public class ArmorTest  extends BaseTest{

    String login = OMSTCConstants.ARMOR.LOGIN_URL;
    String password = OMSTCConstants.ARMOR.PASSWORD;

    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    End2EndHelper end2EndHelper = new End2EndHelper();
    ProcessRelease processRelease = new ProcessRelease();

    @Test(description = "Verify End to End Armor status")
    public void checkStatusesInArmorInEndToEndFlow() throws Exception {
        String orderID;
        String orderReleaseId;

        End2EndHelper end2EndHelper = new End2EndHelper();
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.ARMOR.ADDRESSID,
                new String[]{OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorInEndToEndFlow+":1"}, "", false, false, false, "", false);
        validateOrderInArmor("getOrders?q=id:"+orderID);
        orderReleaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
        Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));
        List<ReleaseEntry> releaseEntries = new ArrayList<>();
        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.PK).build());
        ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);

        validateOrderInArmor("getOrders?q=id:"+orderID);

        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.SH).build());
        releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);

        validateOrderInArmor("getOrders?q=id:"+orderID);
        releaseEntries.add(new ReleaseEntry.Builder(orderReleaseId, ReleaseStatus.DL).build());
        releaseEntryList= new ReleaseEntryList(releaseEntries);
        processRelease.processReleaseToStatus(releaseEntryList);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "DL", 15), "Order Should be DL in OMS");
        validateOrderInArmor("getOrders?q=id:"+orderID);
    }

    @Test(description = "Verify End to End Armor status")
    public void checkStatusesInArmorForFullOrderCancellationFlow() throws Exception {
    	String orderID;
        String orderReleaseId;

        End2EndHelper end2EndHelper = new End2EndHelper();
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.ARMOR.ADDRESSID,
                new String[]{OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorInEndToEndFlow+":1"}, "", false, false, false, "", false);
        validateOrderInArmor("getOrders?q=id:"+orderID);
        orderReleaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
        omsServiceHelper.cancelOrder(orderID, "1", login, "TestOrder Cancellation");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
    }

    @Test(description = "Verify End to End Armor status")
    public void checkStatusesInArmorForSingleReleaseCancellation() throws Exception {
        String orderID;
        String orderReleaseId;

        End2EndHelper end2EndHelper = new End2EndHelper();
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.ARMOR.ADDRESSID,
                new String[]{OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorForSingleReleaseCancellation[0]+":1",
                OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorForSingleReleaseCancellation[1]+":1"}, "", false, false, false, "", false);
        validateOrderInArmor("getOrders?q=id:"+orderID);
        orderReleaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
        omsServiceHelper.cancelOrderRelease(orderReleaseId, "1", login, "Test Release Cancellation");
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "F", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
    }

    @Test(description = "Verify End to End Armor status")
    public void checkStatusesInArmorForSingleLineCancellation() throws Exception {
        String orderID;
        String orderReleaseId;

        End2EndHelper end2EndHelper = new End2EndHelper();
        orderID = end2EndHelper.createOrder(login, password, OMSTCConstants.ARMOR.ADDRESSID,
                new String[]{OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorForSingleReleaseCancellation[0]+":2",
                        OMSTCConstants.ARMOR.SKUID_checkStatusesInArmorForSingleReleaseCancellation[1]+":2"}, "",
                false, false, false, "", false);
        orderReleaseId = omsServiceHelper.getReleaseId(orderID);
        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(orderReleaseId, "WP", 15));
        validateOrderInArmor("getOrders?q=id:"+orderID);
        List<OrderLineEntry> orderLineEntries = omsServiceHelper.getOrderLineEntries(orderReleaseId);
        omsServiceHelper.cancelLine(orderReleaseId, login, new String[] {""+orderLineEntries.get(0).getId() +":1"}, 41);
        validateOrderInArmor("getOrders?q=id:"+orderID);
    }

    @Test(description = "Verify Fetch Size of 5 For Particular Login ID")
    public void validateArmorOrderStatusForLast5OrderForALoginID() throws Exception {
        IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
        validateOrderInArmor("getOrders?q=login.eq:"+
                ideaApiHelper.getUIDXForLoginViaEmail("myntra", login)+"&distinct=true&start=0&sortBy=createdOn&sortOrder=desc&fetchSize=5");
    }


    /**
     * Validate Order In Armor - Here Comparing the response return by OMS Get Order API and GET Order Armor API.
     * @param queryParam
     * @throws UnsupportedEncodingException
     * @throws JAXBException
     */
    private void validateOrderInArmor(String queryParam) throws UnsupportedEncodingException, JAXBException {
        End2EndHelper.sleep(20000);
        String replacableString = "<lastModifiedOn>.*</lastModifiedOn>|<cancellationPpsId>.*" +
                "</cancellationPpsId>|<version>.*</version>|<addressId>.*</addressId>" +
                "|<loyaltyPointsAwarded>.*</loyaltyPointsAwarded>|<refunded>.*</refunded>";

        OrderResponse armorOrderResponse = omsServiceHelper.getArmorOrder(queryParam);
        OrderResponse omsOrderResponse = omsServiceHelper.getOrderSearch(queryParam);
        String omsResponse = APIUtilities.convertXMLObjectToString(omsOrderResponse.getData().iterator().next());
        String armorResponse = APIUtilities.convertXMLObjectToString(armorOrderResponse.getData().iterator().next());
        armorResponse = armorResponse.replaceAll(replacableString, "");
        omsResponse = omsResponse.replaceAll(replacableString, "");
        Assert.assertEquals(armorResponse, omsResponse, "ARMOR Response and OMS Response is not matching ");
    }

}
