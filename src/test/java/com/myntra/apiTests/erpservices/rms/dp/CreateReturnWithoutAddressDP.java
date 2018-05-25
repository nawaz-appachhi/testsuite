package com.myntra.apiTests.erpservices.rms.dp;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.rms.Test.RMSRegressionTest;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.bounty.BountyServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.lms.Helper.LMSHelper;
import com.myntra.apiTests.erpservices.notificaton.NotificationServiceHelper;
import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.apiTests.portalservices.pps.PPSServiceHelper;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

public class CreateReturnWithoutAddressDP {
	
	Initialize init = new Initialize("/Data/configuration");
	Logger log = Logger.getLogger(RMSRegressionTest.class);
	RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
    private static Long vectorSellerID;

    NotificationServiceHelper notificationServiceHelper = new NotificationServiceHelper();
   
    BountyServiceHelper bountyServiceHelper = new BountyServiceHelper();
    ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
    IMSServiceHelper imsServiceHelper =  new IMSServiceHelper();
    OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
    PPSServiceHelper ppsServiceHelper = new PPSServiceHelper();
    LMSHelper lmsHepler = new LMSHelper();

	
	@DataProvider
    public static Object[][] createReturnWithoutAnyCity_ADDRESS_COUNTRY_STATE_ZIPCODE_DP(ITestContext testContext) throws Exception {
		ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
		IMSServiceHelper imsServiceHelper =  new IMSServiceHelper();
		End2EndHelper end2EndHelper = new End2EndHelper();
	    String login = "end2endautomation1@gmail.com";
		String uidx = "8861d0a4.f190.4a91.b392.965c152b3914CLcZYByF9R";
	    String password = "123456";
		String orderID1,orderID2,orderID3,orderID4,orderID5,orderID6,orderID7;
		
    	atpServiceHelper.updateInventoryDetails(new String[] {"1243744:28:100000:0"});
	    imsServiceHelper.updateInventory(new String[] {"1243744:28:10000:0"}); 
        orderID1 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID2 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID3 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID4 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID5 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID6 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);
        orderID7 = end2EndHelper.createOrder(login, password, "6132352", new String[]{"1243744:1"}, "", false, false, false, "", false);

     //   Object[] arr1 = {orderID1,"RETURN_ADDRESS","return address"};
      //  Object[] arr2 = {orderID2,"ID","id"};
        Object[] arr3 = {orderID3,"CITY","city"};
        Object[] arr4 = {orderID4,"COUNTRY","country"};
        Object[] arr5 = {orderID5,"STATE","state"};
        Object[] arr6 = {orderID6,"ZIPCODE","pincode"};
        Object[] arr7 = {orderID7,"ADDRESS","address"};

        Object[][] dataSet = new Object[][] { arr3, arr4,arr5, arr6,arr7 };
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
    }

}
