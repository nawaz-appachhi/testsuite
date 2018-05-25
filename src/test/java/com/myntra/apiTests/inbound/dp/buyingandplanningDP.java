package com.myntra.apiTests.inbound.dp;


import com.myntra.apiTests.inbound.helper.planningandbuying.BaseOrderIndentJson;
import com.myntra.apiTests.inbound.helper.planningandbuying.Constants.*;
import com.myntra.apiTests.inbound.helper.planningandbuying.JeevesHelper;
import com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses.BuyPlanHeaderUpdateRequest;
import com.myntra.orderindent.enums.BuyPlanType;
import org.testng.annotations.DataProvider;

import java.util.HashMap;

public class buyingandplanningDP {

    @DataProvider(name = "roles")
    public static Object[][] getPartnerPortalRoles() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[2][1];

        // 1st row
        data[0][0] = ROLES.CM_ROLE;
        data[1][0] = ROLES.UCB_VENDOR_ROLE;

        return data;
    }

    @DataProvider(name = "CreateOI")
    public static Object[][] CreateOI() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][3];
        BaseOrderIndentJson baseOI = new BaseOrderIndentJson();

        OIHeaderFF FractalFashion=new OIHeaderFF();
        String payload = baseOI.createOIPayload(FractalFashion,BuyPlanType.MFB.getType());

        // 1st row
        data[0][0] = ROLES.CM_ROLE;
        data[0][1] = payload;
        data[0][2] = DATA_FILES.file7;

        OIHeaderUCB UCB = new OIHeaderUCB();
        payload = baseOI.createOIPayload(UCB, BuyPlanType.MMB.getType());

        // 2nd row
        data[1][0] = ROLES.CM_ROLE;
        data[1][1] = payload;
        data[1][2] = DATA_FILES.file1;

        // 3rd row
        data[2][0] = ROLES.UCB_VENDOR_ROLE;
        data[2][1] = payload;
        data[2][2] = DATA_FILES.file1;

        return data;
    }

    @DataProvider(name = "SkuLookupOrderIndents")
    public static Object[][] getUserRoles() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[4][2];

        // 1st row
        data[0][0] = ROLES.CM_ROLE;
        data[0][1] = DATA_FILES.skuLookupFile250;
        data[1][0] = ROLES.BATA_VENDOR_ROLE;
        data[1][1] = DATA_FILES.skuLookupFile250;

        data[2][0] = ROLES.CM_ROLE;
        data[2][1] = DATA_FILES.skuLookupFile4333;
        data[3][0] = ROLES.BATA_VENDOR_ROLE;
        data[3][1] = DATA_FILES.skuLookupFile4333;
        return data;
    }


    @DataProvider(name = "oiRolesAndBuyPlanType")
    public static Object[][] getCMRole() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[4][2];

        data[0][0] = ROLES.CM_ROLE;
        data[0][1] = BuyPlanType.MMB.getType();

        data[1][0] = ROLES.UCB_VENDOR_ROLE;
        data[1][1] = BuyPlanType.MMB.getType();

        data[2][0] = ROLES.CM_ROLE;
        data[2][1] = BuyPlanType.MFB.getType();

        data[3][0] = ROLES.UCB_VENDOR_ROLE;
        data[3][1] = BuyPlanType.MFB.getType();

        return data;
    }

    @DataProvider(name = "CreateOIDataAndBuyPlanType")
    public static Object[][] getRoleForMMBType() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        JeevesHelper jeevesHelper = new JeevesHelper();
        Object[][] testData = new Object[10][14];

        for (int i = 1; i < testData.length; i++) {
            for (int j = 0; j < 14; j++) {
                testData[i - 1][j] = jeevesHelper.readDataFile(DATA_FILES.file1, i, j);
            }
        }

        Object[][] testDataMFB = new Object[10][19];

        for (int i = 1; i < testDataMFB.length; i++) {
            for (int j = 0; j < 19; j++) {
                testDataMFB[i - 1][j] = jeevesHelper.readDataFile(DATA_FILES.file7, i, j);
            }
        }

        Object[][] data = new Object[3][3];

        data[0][0] = ROLES.CM_ROLE;
        data[0][1] = BuyPlanType.MMB.getType();
        data[0][2] = testData;

        data[1][0] = ROLES.UCB_VENDOR_ROLE;
        data[1][1] = BuyPlanType.MMB.getType();
        data[1][2] = testData;

        data[2][0] = ROLES.CM_ROLE;
        data[2][1] = BuyPlanType.MFB.getType();
        data[2][2] = testDataMFB;

        return data;
    }

    @DataProvider(name = "CreateTsrOi")
    public static Object[][] CreateTsrOi() throws Exception {
        JeevesHelper jeeveshelper = new JeevesHelper();
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][3];

        // 1st row
        data[0][0] = TENANTS.MMB;
        data[0][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.MMB, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, JOB_TRACKER_STATUS.INPROGRESS);
        data[0][2] = jeeveshelper.generateOIQuery(TENANTS.MMB, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR);

        data[1][0] = TENANTS.SJIT;
        data[1][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.SJIT, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, JOB_TRACKER_STATUS.INPROGRESS);
        data[1][2] = jeeveshelper.generateOIQuery(TENANTS.SJIT, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR);

        data[2][0] = TENANTS.RAPID;
        data[2][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.RAPID, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR, JOB_TRACKER_STATUS.INPROGRESS);
        data[2][2] = jeeveshelper.generateOIQuery(TENANTS.RAPID, BUSINESS_UNITS.MENS_CASUAL_FOOTWEAR);

        return data;
    }

    @DataProvider(name = "BulkUploadTsrOi")
    public static Object[][] BulkUploadTsrOi() throws Exception {
        JeevesHelper jeeveshelper = new JeevesHelper();
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][3];

        // 1st row
        data[0][0] = TENANTS.MMB;
        data[0][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.MMB, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);
        data[0][2] = jeeveshelper.generateOIQuery(TENANTS.MMB, BUSINESS_UNITS.SPORTS);

        data[1][0] = TENANTS.SJIT;
        data[1][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.SJIT, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);
        data[1][2] = jeeveshelper.generateOIQuery(TENANTS.SJIT, BUSINESS_UNITS.SPORTS);

        data[2][0] = TENANTS.RAPID;
        data[2][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.RAPID, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);
        data[2][2] = jeeveshelper.generateOIQuery(TENANTS.RAPID, BUSINESS_UNITS.SPORTS);

        return data;
    }

    @DataProvider(name = "ReplenishmentSummaryAndStyle")
    public static Object[][] ReplenishmentSummaryAndStyle() throws Exception {
        JeevesHelper jeeveshelper = new JeevesHelper();
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][2];

        // 1st row
        data[0][0] = TENANTS.MMB;
        data[0][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.MMB, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);

        data[1][0] = TENANTS.SJIT;
        data[1][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.SJIT, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);

        data[2][0] = TENANTS.RAPID;
        data[2][1] = jeeveshelper.generateRconfigStatusQuery(TENANTS.RAPID, BUSINESS_UNITS.SPORTS, JOB_TRACKER_STATUS.INPROGRESS);

        return data;
    }

    @DataProvider(name = "MultiTsrTenants")
    public static Object[][] MultiTsrTenants() throws Exception {
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][1];

        // 1st row
        data[0][0] = TENANTS.MMB;
        data[1][0] = TENANTS.SJIT;
        data[2][0] = TENANTS.RAPID;

        return data;
    }

    @DataProvider(name = "update")
    public static Object[][] updateHeader() throws Exception {

        OIHeaderUCB oiheader1 = new OIHeaderUCB();
        Object[][] data = new Object[2][3];

        //For Vendor
        Object[] obj = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) obj[1];

        data[0][0] = buyPlanHeaderUpdateRequest;
        data[0][1] = ROLES.BATA_VENDOR_ROLE;
        data[0][2] = requestData;

        //For CM
        obj = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        requestData = (HashMap<String, Object>) obj[1];

        data[1][0] = buyPlanHeaderUpdateRequest;
        data[1][1] = ROLES.CM_ROLE;
        data[1][2] = requestData;

        return data;
    }

    @DataProvider(name = "updateMFB")
    public static Object[][] updateHeaderMFB() throws Exception {

        OIHeaderFF ff = new OIHeaderFF();
        Object[][] data = new Object[1][3];

        //For CM
        Object[] obj = BaseOrderIndentJson.updateHeaderPayloadForCM(ff);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        buyPlanHeaderUpdateRequest.setBrandType("INTERNAL");
        buyPlanHeaderUpdateRequest.setCategoryManagerName("Garima Juyal");
        buyPlanHeaderUpdateRequest.setCategoryManagerId("Garima Juyal");
        HashMap<String, Object> requestData = (HashMap<String, Object>) obj[1];
        requestData.put("brandType","INTERNAL");
        requestData.put("categoryManagerName","Garima Juyal");
        requestData.put("categoryManagerId","Garima Juyal");

        data[0][0] = buyPlanHeaderUpdateRequest;
        data[0][1] = ROLES.CM_ROLE;
        data[0][2] = requestData;

        return data;
    }


    @DataProvider(name = "updateHeaderVendor")
    public static Object[][] updateHeaderVendor() throws Exception {

        OIHeaderUCB oiheader1 = new OIHeaderUCB();
        Object[][] data = new Object[2][3];

        //For Vendor
        Object[] obj = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) obj[1];

        data[0][0] = buyPlanHeaderUpdateRequest;
        data[0][1] = ROLES.UCB_VENDOR_ROLE;
        data[0][2] = requestData;

        obj = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        requestData = (HashMap<String, Object>) obj[1];

        data[0][3] = buyPlanHeaderUpdateRequest;
        data[0][4] = requestData;

        return data;
    }

    @DataProvider(name = "errorDataForBuyPlanHeaderUpdate")
    public static Object[][] errorDataForCreateOI() throws Exception {

        Object[][] data = BaseOrderIndentJson.createUpdateHeaderErrorPayload();
        return data;

    }

    @DataProvider(name = "bulkUpload")
    public static Object[][] bulkUpload() throws Exception {

        OIHeaderUCB oiheader1 = new OIHeaderUCB();
        JeevesHelper jeevesHelper = new JeevesHelper();
        Object[][] testData = new Object[10][14];

        for (int i = 1; i < testData.length; i++) {
            for (int j = 0; j < 14; j++) {
                testData[i - 1][j] = jeevesHelper.readDataFile(DATA_FILES.file1, i, j);
            }
        }

        Object[][] data = new Object[2][5];

        //For Vendor
        Object[] obj = BaseOrderIndentJson.updateHeaderPayloadForVendor(oiheader1);
        BuyPlanHeaderUpdateRequest buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        HashMap<String, Object> requestData = (HashMap<String, Object>) obj[1];

        data[0][0] = buyPlanHeaderUpdateRequest;
        data[0][1] = ROLES.UCB_VENDOR_ROLE;
        data[0][2] = requestData;
        data[0][3] = testData;
        data[0][4] = BuyPlanType.MMB.getType();

        //For CM
        obj = BaseOrderIndentJson.updateHeaderPayloadForCM(oiheader1);
        buyPlanHeaderUpdateRequest = (BuyPlanHeaderUpdateRequest) obj[0];
        requestData = (HashMap<String, Object>) obj[1];

        data[1][0] = buyPlanHeaderUpdateRequest;
        data[1][1] = ROLES.CM_ROLE;
        data[1][2] = requestData;
        data[1][3] = testData;
        data[1][4] = BuyPlanType.MMB.getType();


        return data;
    }


    @DataProvider(name = "ReplenishmentSet")
    public static Object[][] getReplenishmentSet() throws Exception {
        JeevesHelper jeeveshelper = new JeevesHelper();
        // Rows - Number of times your test has to be repeated.
        // Columns - Number of parameters in test data.
        Object[][] data = new Object[3][3];
        // 1st row
        data[0][0] = TENANTS.SJIT;
        data[0][1] = DATA_FILES.sjit_tenant_mens_casual_rconfig_file;
        data[0][2] = jeeveshelper.generateRconfigStatusQuery(TENANTS.SJIT, BUSINESS_UNITS.MENS_CASUAL, JOB_TRACKER_STATUS.INPROGRESS);
        // 2nd row
        data[1][0] = TENANTS.RAPID;
        data[1][1] = DATA_FILES.rapid_tenant_mens_casual_rconfig_file;
        data[1][2] = jeeveshelper.generateRconfigStatusQuery(TENANTS.RAPID, BUSINESS_UNITS.MENS_CASUAL, JOB_TRACKER_STATUS.INPROGRESS);

        // 3rd row
        data[2][0] = TENANTS.MMB;
        data[2][1] = DATA_FILES.mmb_tenant_mens_casual_rconfig_file;
        data[2][2] = jeeveshelper.generateRconfigStatusQuery(TENANTS.MMB, BUSINESS_UNITS.MENS_CASUAL, JOB_TRACKER_STATUS.INPROGRESS);
        return data;
    }


}


