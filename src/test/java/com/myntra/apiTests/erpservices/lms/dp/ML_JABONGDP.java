package com.myntra.apiTests.erpservices.lms.dp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.lmsClient.JabongCreateShipmentResponse;
import com.myntra.apiTests.erpservices.lms.lmsClient.LMSOrderEntries;
import com.myntra.apiTests.erpservices.lms.lmsClient.LMSOrderEntry;
import com.myntra.lms.client.status.PremisesType;
import com.myntra.lms.client.status.ShipmentStatus;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;
import com.myntra.lordoftherings.Toolbox;

/**
 * Created by abhijit.pati on 05/05/17.
 */
@SuppressWarnings("deprecation")
public class ML_JABONGDP {

    static LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
    private static final String wareHouseID = EnumSCM.wareHouseIdJabong;

    @DataProvider
    public static Object [][] createJabongOrder(ITestContext testContext) {

        Object[] arr1 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false)};

        Object[] arr2 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 2, true, true, true, true, true)};

        Object[] arr3 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 20, true, true, false, true, true)};

        Object[] arr4 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 2, false, false, false, false, false)};

        Object[] arr5 = { lmsServiceHelper.createJabongShipmentRequestPayloadOnline(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 2, true, true, true, true, true)};

        Object[] arr6 = { lmsServiceHelper.createJabongShipmentRequestPayloadOnline(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, true, true, true)};


        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6};//
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
    }



    @DataProvider
    public static Object [][] createSingleShipmentMarkOrderRTOSingleShipment(ITestContext testContext) {
        Object[] arr1 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false)};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] createSameShipmentMultipleTime(ITestContext testContext) {
        Object[] arr1 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false)};

        Object[][] dataSet = new Object[][] { arr1 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
    }


    @DataProvider
    public static Object [][] createJabongOrderNegativeScenarios(ITestContext testContext) {
        Object[] arr1 = { LMS_PINCODE.ML_BLR, "81", EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false};

        Object[] arr2 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_EK, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr3 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.EXCHANGE,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr4 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.TRY_AND_BUY,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr5 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 0, true, true, true, true, true};

        Object[] arr6 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.SDD, 1, false, false, false, false, false};

        Object[] arr7 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.EXPRESS, 1, false, false, false, false, false};

        Object[] arr8 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_BD, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr9 = { LMS_PINCODE.ML_BLR, null, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr10 = { LMS_PINCODE.ML_BLR, "", EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr17 = {LMS_PINCODE.ML_BLR, "TagNotPresent", EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr11 = { "", wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr12 = { null, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr13 = { LMS_PINCODE.ML_BLR, wareHouseID, "", ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr14 = { LMS_PINCODE.ML_BLR, wareHouseID, null, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr15 = { LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, null,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[] arr16 = { LMS_PINCODE.ML_BLR, wareHouseID, "TagNotPresent", ShipmentType.DL,
                ShippingMethod.NORMAL, 1, true, true, true, true, true};

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15, arr16, arr17};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20, 20);
    }

	@DataProvider
    public static Object [][] createShipmentMarkOrderDeliver(ITestContext testContext) throws IOException {
        LMSOrderEntries lmsOrderEntries;

        List<LMSOrderEntry> orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse dl = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(dl.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);

        Object[] arr1 = { lmsOrderEntries };

        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse rto = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(rto.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.RTO));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr2 = { lmsOrderEntries };

        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse lost = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(lost.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.LOST));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr3 = { lmsOrderEntries };

        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse fd = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(fd.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.FD));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr4 = { lmsOrderEntries };

        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse fddl = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(fddl.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.FDDL));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr5 = { lmsOrderEntries };

        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse multiItemDL = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 3, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(multiItemDL.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr6 = { lmsOrderEntries };


        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse jabongMultiItemRTO = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 4, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(jabongMultiItemRTO.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.RTO));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr7 = { lmsOrderEntries };


        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse jabongMultiItemFD = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 5, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(jabongMultiItemFD.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.FD));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr8 = { lmsOrderEntries };


        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse jabongMultiItemLost = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 10, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(jabongMultiItemLost.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.LOST));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr9 = { lmsOrderEntries };


        orderEntries = new ArrayList<>();
        JabongCreateShipmentResponse jabongMultiItemUNRTO = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 10, false, false, false, false, false));
        orderEntries.add(new LMSOrderEntry(jabongMultiItemUNRTO.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.UNRTO));
        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);
        Object[] arr10 = { lmsOrderEntries };


        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 11, 11);
    }

    @DataProvider
    public static Object [][] createMultipleMyntraAndJabongShipmentsAndMarkOrderDeliver(ITestContext testContext) {
        Object[] arr1 = { lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, "36", EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 1, false, false, false, false, false)};

        Object[][] dataSet = new Object[][] { arr1};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 6);
    }

    @DataProvider
    public static Object [][] createMultipleJabongShipmentsAndMarkOrderDeliver(ITestContext testContext) throws IOException {


        //List<LMSOrderEntry> orderEntries = new ArrayList<>();

        JabongCreateShipmentResponse sh1 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 4, false, false, false, false, false));

        JabongCreateShipmentResponse sh2 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 3, false, false, false, false, false));

        JabongCreateShipmentResponse sh3 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 4, false, false, false, false, false));

        JabongCreateShipmentResponse sh4 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 5, false, false, false, false, false));

        JabongCreateShipmentResponse sh5 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 5, false, false, false, false, false));

        JabongCreateShipmentResponse sh6 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 5, false, false, false, false, false));

        JabongCreateShipmentResponse sh7 = lmsServiceHelper.createJabongShipment(lmsServiceHelper.createJabongShipmentRequestPayload(LMS_PINCODE.ML_BLR, wareHouseID, EnumSCM.COURIER_CODE_ML, ShipmentType.DL,
                ShippingMethod.NORMAL, 5, false, false, false, false, false));

//        orderEntries.add(new LMSOrderEntry(sh1.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.UNRTO));
//        orderEntries.add(new LMSOrderEntry(sh2.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.RTO));
//        orderEntries.add(new LMSOrderEntry(sh3.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.DL));
//        orderEntries.add(new LMSOrderEntry(sh4.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.LOST));
//        orderEntries.add(new LMSOrderEntry(sh5.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.FDFDDL));
//        orderEntries.add(new LMSOrderEntry(sh6.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.SMDL));
//        orderEntries.add(new LMSOrderEntry(sh7.getOrderID(), ShipmentType.DL, ShipmentStatus.RECEIVED, ReleaseStatus.FDDL));
//        lmsOrderEntries = new LMSOrderEntries(orderEntries, wareHouseID, ShippingMethod.NORMAL, PremisesType.DC, 5L);


        Object[] arr1 = { sh1.getOrderID(), ReleaseStatus.UNRTO };
        Object[] arr2 = { sh2.getOrderID(), ReleaseStatus.RTO };
        Object[] arr3 = { sh3.getOrderID(), ReleaseStatus.DL };
        Object[] arr4 = { sh4.getOrderID(), ReleaseStatus.LOST };
        Object[] arr5 = { sh5.getOrderID(), ReleaseStatus.FDFDDL };
        Object[] arr6 = { sh6.getOrderID(), ReleaseStatus.SMDL };
        Object[] arr7 = { sh7.getOrderID(), ReleaseStatus.FDDL };

        Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
    }


}
