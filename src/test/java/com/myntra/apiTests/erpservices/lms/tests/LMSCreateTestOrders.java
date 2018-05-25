package com.myntra.apiTests.erpservices.lms.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_LOGIN;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Constants.PaymentMode;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 * Created by Shubham gupta on 10/4/16.
 */
public class LMSCreateTestOrders extends BaseTest {

	private String login = LMS_LOGIN.LogIn1;
	private static List<String> orders = new ArrayList<>();
	private ProcessRelease processRelease = new ProcessRelease();
	private OMSServiceHelper omsServiceHelper = new OMSServiceHelper();

    @Test(groups = { "Smoke","Regression"}, enabled=true, priority=0)
    public void deletePreviousTestOrders(){
        DBUtilities.exUpdateQuery("delete From test_order WHERE status_oms in ('F','L','RTO')", "test");
        DBUtilities.exUpdateQuery("delete From test_order WHERE last_modified_on < DATE(CURRENT_DATE() - INTERVAL 1 HOUR) and inUse = 1", "test");
        DBUtilities.exUpdateQuery("delete From test_order WHERE created_on < DATE(CURRENT_DATE() - INTERVAL 4 DAY)", "test");
    }

    @Test(groups = { "Smoke","Regression"}, enabled=true, priority=1, dataProvider = "createBulkOrderLMS")
    public void createBulkOrderLMS(String pincode, String sku, boolean isTOD, String toStatus, PaymentMode paymentMothod) throws Exception {
        End2EndHelper end2EndHelper = new End2EndHelper();
        LMSHelper lmsHepler = new LMSHelper();
        String orderID = null;
       //String packetId = null;
        String releaseId = null;
        try {
            if (paymentMothod == PaymentMode.COD) {
                orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, new String[]{sku}, "", false, false, false, "", isTOD);
                //packetId = omsServiceHelper.getPacketId(orderID);
                releaseId = omsServiceHelper.getReleaseId(orderID);
                System.out.println("Order ID : " + orderID);
                List<ReleaseEntry> releaseEntries = new ArrayList<>();
                releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.WP).force(true).build());
                if (isTOD == true) {
                		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
                    lmsHepler.insertTestOrder(orderID, null, EnumSCM.TRY_AND_BUY);
                    lmsHepler.insertTestOrder(orderID);
                } else {
                	processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
                    lmsHepler.insertTestOrder(orderID, null, EnumSCM.DL);
                }
                orders.add("" + orderID + ":" + toStatus);
            } else {
                orderID = end2EndHelper.createOrder(login, LMS_LOGIN.PASSWORD, pincode, new String[]{sku}, "", false, false, false, "", isTOD, paymentMothod);
                System.out.println("Order ID : " + orderID);
                //packetId = omsServiceHelper.getPacketId(orderID);
                releaseId = omsServiceHelper.getReleaseId(orderID);
                List<ReleaseEntry> releaseEntries = new ArrayList<>();
                releaseEntries.add(new ReleaseEntry.Builder(releaseId, ReleaseStatus.WP).force(true).build());
                if (isTOD == true && paymentMothod == PaymentMode.WALLET) {
                		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
                    lmsHepler.insertTestOrder(orderID, null, EnumSCM.TRY_AND_BUY, "wallet");
                } else if (isTOD == true && paymentMothod != PaymentMode.WALLET) {
                		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
                    lmsHepler.insertTestOrder(orderID, null, EnumSCM.TRY_AND_BUY, "wallet");
                } else {
                		processRelease.processReleaseToStatus(processRelease.getReleaseEntryList(releaseEntries));
                    lmsHepler.insertTestOrder(orderID, null, EnumSCM.DL);
                }
                orders.add("" + orderID + ":" + toStatus);
            }
        } catch (IOException e) {
            try {
                lmsHepler.insertTestOrder(orderID);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    @Test(groups = { "Smoke","Regression"}, enabled=true, priority=2, dataProvider = "ordersList")
//    public void MarkOrdersPK(String orderID) throws Exception{
//        LMSHelper lmsHepler = new LMSHelper();
//        String[] temp;
//        temp = orderID.split(":");
//        processRelease.processReleaseToStatus(new ReleaseEntry.Builder(omsServiceHelper.getPacketId(temp[0]), ReleaseStatus.PK).force(true).build());
//        lmsHepler.updateTestOrder(temp[0]);
//    }

    @Test(groups = { "Smoke","Regression"}, enabled=true, priority=3)
    public void updateOrders() throws Exception{
        LMSHelper lmsHepler = new LMSHelper();
        @SuppressWarnings("unchecked")
		List<Map<String,Object>> orders = DBUtilities.exSelectQuery("select order_id from test_order where status_oms not in('DL','RT','EX') order by last_modified_on DESC limit 150","test");
        for (Map<String, Object> orderMap: orders) {
            long orderID = Long.parseLong(orderMap.get("order_id").toString());
            lmsHepler.updateTestOrderWithOutInUse(""+orderID);
        }
    }

    @DataProvider
    public static Object[][] createBulkOrderLMS(ITestContext testContext)
    {
        Object[] arr1= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr2= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr3= {LMS_PINCODE.ML_BLR,"3875:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr4= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr5= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr6= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr7= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr8= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr9= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr10= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr11= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.CC};
        Object[] arr12= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr13= {LMS_PINCODE.ML_BLR,"3874:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr14= {LMS_PINCODE.ML_BLR,"3875:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr15= {LMS_PINCODE.ML_BLR,"3875:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr16= {LMS_PINCODE.ML_BLR,"3874:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr17= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr18= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.WALLET};
        Object[] arr19= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr20= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr21= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr22= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr23= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr24= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr25= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr26= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr27= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr28= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr29= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr30= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr31= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr32= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr33= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr34= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr35= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr36= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr37= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr38= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr39= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr40= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr41= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr42= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr43= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr44= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr45= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr46= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr47= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr48= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr49= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr50= {LMS_PINCODE.ML_BLR,"3867:1", true, EnumSCM.PK,PaymentMode.COD};
        Object[] arr51= {LMS_PINCODE.ML_BLR,"3867:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr52= {LMS_PINCODE.ML_BLR,"3867:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr53= {LMS_PINCODE.ML_BLR,"3875:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr54= {LMS_PINCODE.ML_BLR,"3875:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr55= {LMS_PINCODE.ML_BLR,"3875:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr56= {LMS_PINCODE.ML_BLR,"3875:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr57= {LMS_PINCODE.ML_BLR,"3875:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr58= {LMS_PINCODE.ML_BLR,"3867:1", true, EnumSCM.WP,PaymentMode.COD};
        Object[] arr59= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr60= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr61= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr62= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr63= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr64= {LMS_PINCODE.PUNE_EK,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr65= {LMS_PINCODE.PUNE_EK,"3867:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr66= {LMS_PINCODE.PUNE_EK,"3875:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr67= {LMS_PINCODE.PUNE_EK,"3875:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr68= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr69= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr70= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr71= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr72= {LMS_PINCODE.MUMBAI_DE_RHD,"3866:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr73= {LMS_PINCODE.PUNE_EK,"3867:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr74= {LMS_PINCODE.PUNE_EK,"3867:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr75= {LMS_PINCODE.PUNE_EK,"3875:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr76= {LMS_PINCODE.PUNE_EK,"3875:1", false, EnumSCM.WP,PaymentMode.COD};
        Object[] arr77= {LMS_PINCODE.ML_BLR,"3880:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr78= {LMS_PINCODE.ML_BLR,"3880:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr79= {LMS_PINCODE.ML_BLR,"3880:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr80= {LMS_PINCODE.ML_BLR,"3880:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr81= {LMS_PINCODE.ML_BLR,"3880:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr82= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.PK,PaymentMode.COD};
        Object[] arr83= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr84= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr85= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr86= {LMS_PINCODE.ML_BLR,"3866:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr87= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr88= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr89= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr90= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr91= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr92= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr93= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr94= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr95= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr96= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.DL,PaymentMode.COD};
        Object[] arr97= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.SH,PaymentMode.COD};
        Object[] arr98= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.SH,PaymentMode.COD};
        Object[] arr99= {LMS_PINCODE.ML_BLR,"3913:1", false, EnumSCM.SH,PaymentMode.COD};
        Object[] arr100= {LMS_PINCODE.ML_BLR,"3867:1", false, EnumSCM.SH,PaymentMode.COD};

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20, arr21, arr22, arr23
                , arr24, arr25, arr26, arr27, arr28, arr29, arr30, arr31, arr32, arr33, arr34, arr35, arr36, arr37, arr38, arr39, arr40, arr41, arr42, arr43, arr44, arr45, arr46, arr47, arr48, arr49, arr50, arr51, arr52
                , arr53, arr54, arr55, arr56, arr57, arr58, arr59, arr60, arr61, arr62, arr63, arr64, arr65, arr66, arr67, arr68, arr69, arr70, arr71, arr72, arr73, arr74, arr75, arr76, arr77, arr78, arr79, arr80, arr81,
                arr82, arr83, arr84, arr85, arr86, arr87, arr88, arr89, arr90, arr91, arr92, arr93, arr94, arr95, arr96, arr97, arr98, arr99, arr100};

        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 100, 100);
    }

    @DataProvider(parallel = true)
    public static Object[][] ordersList(ITestContext testContext)
    {	System.out.println(orders.toArray());
        Object[] order = orders.toArray();
        Object[][] dataSet = new Object[order.length][];
        for(int i=0;i<order.length;i++){
            dataSet[i] = new Object[]{order[i]};
        }
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 150, 150);
    }

}
