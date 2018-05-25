package com.myntra.apiTests.erpservices.lms.tests;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.lms.Constants.LMS_PINCODE;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LmsServiceHelper;
import com.myntra.apiTests.erpservices.lms.dp.LMSTestsDP;
import com.myntra.apiTests.erpservices.lms.lmsClient.RedisResponse;
import com.myntra.apiTests.erpservices.oms.Test.AdjustInventory;
import com.myntra.apiTests.erpservices.wms.WMSServiceHelper;
import com.myntra.apiTests.portalservices.ideaapi.IdeaApiHelper;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.test.commons.testbase.BaseTest;

/**
 *
 * @author Shubham gupta
 *
 */
public class LMSDataPrep extends BaseTest {
    
	private WMSServiceHelper wmsServiceHelper = new WMSServiceHelper();
	private LmsServiceHelper lmsServiceHelper = new LmsServiceHelper();
	private LMSHelper lmsHelper = new LMSHelper();
	private static Logger log = Logger.getLogger(LmsServiceHelper.class);

    @Test(groups = { "P0","Smoke","Regression"}, enabled=false, priority=0, dataProviderClass = LMSTestsDP.class, dataProvider = "hubServiceabilityUpdate", description = "ID: C540, In data preparation this test will update " +
            "the serviceability using the Jugaad serviceability api for all the used pincode hub combinations in out regression suite")
    public void updateServiceabilityHub(String warehouse, String pincode, String[] courier) throws Exception {
        lmsServiceHelper.hubServiceabilityUpdate(warehouse,pincode,courier, false);
    }

    @SuppressWarnings("unchecked")
	@Test(groups = { "P0","Smoke","Regression"}, enabled=true, priority=1, description = "ID: 541: After updating serviceability for all the " +
            "pincodes and Hub combinations we need to refresh cache for the specific keys. We use redis del List<key> api of jugaad service for the same.")
    public void refreshRedis() throws Exception{
        Assert.assertEquals(((RedisResponse)lmsServiceHelper.lmsFlushKeysFromRedis.apply(6378)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
        Assert.assertEquals(((RedisResponse)lmsServiceHelper.lmsFlushKeysFromRedis.apply(6379)).getStatus().getStatusType().toString(),EnumSCM.SUCCESS);
    }

    @Test(groups = { "Smoke","Regression"}, enabled = true, priority=0, description = "ID: C542, In this We use to update inventory and available in WH for all the sku used in our automation in ATP and IMS")
    public void prepData_updateStylesInIMSATPAndReindex() throws Exception{
        //skuid:warehouse_id1,warehouse_id2,warehouse_id3:inventory_count:block_order_count:sellerId
        String[] inventorydetails = {"3913:36:1000000:0:21","3914:36:10000:0:21","3915:36:10000:0:21","3916:36:10000:0:21","3917:36:10000:0:1","3918:36:10000:0:21",
                "3919:36:10000:0:21","3920:36:10000:0:1","3870:28:10000:0:21","3869:36:10000:0:21","3868:36:10000:0:21","3867:36:10000:0:21","3866:36:10000:0:21",
                "3876:28:10000:0:25","3875:36:10000:0:25","3874:28:10000:0:25","3873:36:10000:0:25","3872:36:10000:0:25","3871:28:10000:0:25","3881:36:10000:0:19","3880:19:10000:0:19",
                "3879:36:10000:0:19","3878:28:10000:0:19","3877:36:10000:0:19","895234:46:10000:0:18","1251868:45:10000:0:5"};
        AdjustInventory adjustInventory = new AdjustInventory();
        adjustInventory.updateInventoryInIMSAndATP(inventorydetails, EnumSCM.ON_HAND);
    }

    @Test(groups = { "Smoke","Regression"}, enabled=true, priority=2, description = "ID: C543, This method deletes all the test orders created before 4 days")
    public void deletePreviousTestOrders(){
        DBUtilities.exUpdateQuery("delete from item where id <> barcode", "wms");
        DBUtilities.exUpdateQuery("delete From test_order WHERE status_oms in ('F','L','RTO')", "test");
        DBUtilities.exUpdateQuery("delete From test_order WHERE last_modified_on < DATE(CURRENT_DATE() - INTERVAL 1 HOUR) and inUse = 1", "test");
        DBUtilities.exUpdateQuery("delete From test_order WHERE created_on < DATE(CURRENT_DATE() - INTERVAL 4 DAY)", "test");
    }

    @Test(groups = { "Smoke","Regression"}, enabled=true,dataProviderClass = LMSTestsDP.class,dataProvider="skus", priority = 2, description = "ID: C544, In this method we use to check wheather all the skus which we are using in automation " +
            "available in corresponding warehouse(as items). If item qty is less then 100 then insert 1000 items for the sku else leave.")
    public void wmsInstockItemForLMS(String sku) throws SQLException {
        wmsServiceHelper.insertItem(sku);
    }

    @Test(groups = { "Smoke","Regression"}, enabled=false, dataProviderClass = LMSTestsDP.class, dataProvider="createOrdersInLMSForInitialDataSetup", priority = 2, description = "ID: C545 , This is " +
            "supposed to be disabled. This is needs to executed when you need to setup a new environment and you want to create multiple orders with different combinations to support your regression suite. ")
    public void createOrdersInLMSForInitialDataSetup(String toStatus, String courierCode, String warehouseId, String paymentMode, String shippingMethod, boolean isTryNBuy) throws Exception {
        String newCourierCode  = "";
        String pincode = "";
        switch (courierCode){
            case "MLCGH" : newCourierCode = "ML"; pincode = LMS_PINCODE.NORTH_CGH;
                break;
            case "MLBLR" : newCourierCode = "ML"; pincode = LMS_PINCODE.ML_BLR;
                break;
            case "EK" : newCourierCode = "EK"; pincode = LMS_PINCODE.PUNE_EK;
                break;
            case "DE" : newCourierCode = "DE"; pincode = LMS_PINCODE.NORTH_DE;
                break;
            case "DERHD" : newCourierCode = "DE"; pincode = LMS_PINCODE.MUMBAI_DE_RHD;
                break;
            case "BD" : newCourierCode = "BD"; pincode = LMS_PINCODE.ODISHA_BD;
                break;
            case "IP" : newCourierCode = "IP"; pincode = LMS_PINCODE.JAMMU_IP;
                break;
        }

        if (isTryNBuy && !newCourierCode.equals("ML")){
            log.info("Try and buy case is not suppose to be executed by this courier");
            return;
        }
        if (toStatus.equals(EnumSCM.CANCELLED_IN_HUB) && warehouseId.equals("19")){
            log.info("This case is not suppose to be executed due to not matching some combinations");
            return;
        }
        if (newCourierCode.equals("BD") || newCourierCode.equals("IP")){
            if (toStatus.equals(EnumSCM.DL)|| toStatus.equals(EnumSCM.UNRTO) || toStatus.equals(EnumSCM.LOST) ||
                    toStatus.equals(EnumSCM.FD)|| toStatus.equals(EnumSCM.OFD) || toStatus.equals(EnumSCM.RECEIVE_IN_DC) ||
                    shippingMethod.equals(EnumSCM.XPRESS)||shippingMethod.equals(EnumSCM.SDD)){
                log.info("This case is not suppose to be executed due to not matching some combinations");
                return;
            }
        }
        if (newCourierCode.equals("EK")||newCourierCode.equals("DE")){
            if (toStatus.equals(EnumSCM.UNRTO) || toStatus.equals(EnumSCM.LOST) ||
                    toStatus.equals(EnumSCM.FD)|| toStatus.equals(EnumSCM.OFD) || toStatus.equals(EnumSCM.RECEIVE_IN_DC)){
                log.info("This case is not suppose to be executed due to not matching some combinations");
                return;
            }
        }
        lmsHelper.createMockOrder(toStatus, pincode, newCourierCode, warehouseId, shippingMethod,paymentMode,false, false);
    }

    @Test(groups = { "Smoke","Regression"}, enabled=false, dataProviderClass = LMSTestsDP.class, dataProvider="createMBInLMSForInitialDataSetup", description = "ID: C645, This is " +
            "supposed to be disabled. This is needs to executed when you need to setup a new environment and you want to create multiple masterbags with different combinations to support your regression suite.")
    public void createMBInLMSForInitialDataSetup(long source, String sourceType, long dest, String destType, String shippingMethod, String courierCode) throws Exception{
        Assert.assertEquals(lmsServiceHelper.createMasterBag(source, sourceType, dest, destType, shippingMethod, courierCode).getStatus().getStatusType().toString(), EnumSCM.SUCCESS);
    }

    @Test(enabled = false, description = "ID: C644, Temp method to add multiple address to the corresponding logins")
    public void tempAddAddresstoLogin() throws IOException {
        IdeaApiHelper ideaApiHelper = new IdeaApiHelper();
        for (int i=1;i<=200;i++) {
            String uidx = ideaApiHelper.getUIDXForLoginViaEmail("myntra", "newlmsautomation"+i+"@myntra.com");
            String insert = "INSERT INTO `mk_customer_address` (`login`, `default_address`, `name`, `address`, `city`, `state`, `country`, `pincode`, " +
                    "`email`, `mobile`, `phone`, `datecreated`, `errmask`, `locality`, `updatedBy`, `updatedOn`, `address_scoring`, `address_type`, `available_days`) " +
                    "VALUES ( '" + uidx + "', 1, 'lmsautomation', 'test address myntra design pvt ltd', 'Bangalore', 'KA', 'IN', '560068', 'lmsautomation1&amp;&#x23;x40&#x3b;myntra.com', '1234567890', '', NULL, 00000000, 'Bommanahalli  &#x28;Bangalore&#x29;', NULL, NULL, 0.9209, NULL, 127), " +
                    "( '" + uidx + "', 0, 'lmsautomation', 'test address myntra design pvt ltd', 'Mumbai', 'MH', 'IN', '400053', 'lmsautomation1&#x40;myntra.com', '1234567890', '', NULL, 00000000, 'Andheri H.O', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', '2016-09-28 12:22:15', 0.792, NULL, 127)," +
                    "( '" + uidx + "', 0, 'lmsautomation', 'test address myntra design pvt ltd ', 'Pune', 'MH', 'IN', '411001', 'lmsautomation1&#x40;myntra.com', '1234567890', '', NULL, 00000000, 'Dr.B.A. Chowk', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', '2016-09-28 12:22:01', 0.792, NULL, 127)," +
                    "( '" + uidx + "', 0, 'lmsautomation', 'test address myntra design pvt ltd', 'Lalitpur', 'UP', 'IN', '284403', 'lmsautomation1&amp;&#x23;x40&#x3b;myntra.com', '1234567890', '', NULL, 00000000, 'civil line', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', '2016-12-29 12:45:06', 0.8484, NULL, 127)," +
                    "( '" + uidx + "', 0, 'LMS automation', 'test address myntra design pvt ltd', 'Chandigarh', 'CH', 'IN', '160019', 'lmsautomation101&amp;&#x23;x40&#x3b;myntra.com', '1234567890', '', NULL, 00000000, 'chandigarh', 'd06c75fe.eaf9.47bf.8e30.68a84bdfd9a7RqgoR0JygF', '2017-01-11 19:11:07', 0.8772, NULL, 127)," +
                    "( '" + uidx + "', 0, 'lms automation', 'test address myntra design pvt ltd', 'Central delhi', 'DL', 'IN', '110011', 'lmsautomation101&amp;&#x23;x40&#x3b;myntra.com', '1234567890', '', NULL, 00000000, 'Delhi ', 'd06c75fe.eaf9.47bf.8e30.68a84bdfd9a7RqgoR0JygF', '2017-01-11 19:11:49', 0.8772, NULL, 127)," +
                    "( '" + uidx + "', 0, 'lms automation', 'test address myntra design pvt ltd', 'Jammu', 'JK', 'IN', '180001', 'lmsautomation1&amp;&#x23;x40&#x3b;myntra.com', '1234567890', '', NULL, 00000000, 'kudlu gate', '30f43acf.f0f2.4e44.842f.1aeae1b79652VbqNFDEZLY', '2017-02-02 11:16:23', 0, NULL, 127)," +
                    "( '" + uidx + "', 1, 'lmsautomation', 'test address myntra design pvt ltd', 'Bangalore', 'KA', 'IN', '560068', 'wow&#x40;mfg.com', '9999999999', '', NULL, 00000000, 'l1', 'e79660fc.7723.4636.aa9d.1d057b33f78chrYI4lufhv', '2017-05-22 17:56:20', 0, NULL, NULL)";
            DBUtilities.exUpdateQuery(insert, "myntra_address");
        }
    }

}
