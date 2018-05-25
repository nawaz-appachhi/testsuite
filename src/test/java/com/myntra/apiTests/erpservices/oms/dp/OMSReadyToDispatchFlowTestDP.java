package com.myntra.apiTests.erpservices.oms.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.oms.OMSHelpersEnums.ReadyToDispatchType;
import com.myntra.apiTests.erpservices.oms.Test.ExchangeServiceTest.ExchangeTyeNegative;
import com.myntra.lordoftherings.Toolbox;

public class OMSReadyToDispatchFlowTestDP {
	
	private static String skuId1= OMSTCConstants.OtherSkus.skuId_3831;
	
    @DataProvider(name = "readyToDispatchNegativeCasesDP")
    public static Object[][] readyToDispatchNegativeCasesDP(ITestContext testContext) throws Exception {

        Object[][] dataSet = new Object[][] { { skuId1, ReadyToDispatchType.DIFFERENT_SKU.name()},
        		{ skuId1, ReadyToDispatchType.GREATER_QTY.name()}, 
        		{skuId1, ReadyToDispatchType.MISSING_SKU.name() },
        		{ skuId1, ReadyToDispatchType.MISSING_COURIER.name()},
        		{ skuId1, ReadyToDispatchType.ZERO_QTY.name()}};
        return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
    }
}
