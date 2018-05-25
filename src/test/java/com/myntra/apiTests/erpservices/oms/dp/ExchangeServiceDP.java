package com.myntra.apiTests.erpservices.oms.dp;

import java.util.List;

import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.ExchangeServiceTest.ExchangeTyeNegative;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.oms.client.entry.OrderEntry;
import com.myntra.oms.client.entry.OrderLineEntry;
import com.myntra.oms.client.entry.OrderReleaseEntry;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

/**
 * Created by abhijit.pati on 23/03/16.
 */
public class ExchangeServiceDP {

    static OMSServiceHelper omsServiceHelper =  new OMSServiceHelper();
    static End2EndHelper end2EndHelper = new End2EndHelper();
    static Initialize init = new Initialize("/Data/configuration");
    static String username = OMSTCConstants.LoginAndPassword.ExchangeServiceLogin;
    static String password = OMSTCConstants.LoginAndPassword.ExchangeServicePassword;
	static ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	private static OrderEntry orderEntry;
	private static List<OrderReleaseEntry> orderReleaseEntries;
	private static String orderReleaseId;
	private static String supplyTypeOnHand = "ON_HAND";
	private static Long vectorSellerID = SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);


	
    @DataProvider(name = "exchangeNegativeCasesDP")
    public static Object[][] exchangeNegativeCasesDP(ITestContext testContext) throws Exception {

        Object[][] dataSet = new Object[][] { {2,OMSTCConstants.OtherSkus.skuId_8005,"DNL", 2023, "EXCHANGE_ORDER_CALL_FAILED",ExchangeTyeNegative.MORE_QTY.name()},
        		{ -1,OMSTCConstants.OtherSkus.skuId_8005,"DNL",2023, "EXCHANGE_ORDER_CALL_FAILED",ExchangeTyeNegative.NEGATIVE_QTY.name()}, 
        		{ 0,OMSTCConstants.OtherSkus.skuId_8005, "DNL",2009, "ECHANGE_ITEM_QUANTITY_IS_REQUIRED",ExchangeTyeNegative.ZERO_QTY.name()},
        		{1,OMSTCConstants.OtherSkus.skuId_8005, "DNL",2023, "EXCHANGE_ORDER_CALL_FAILED",ExchangeTyeNegative.NO_INVENTORY.name()}};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
    }


}
