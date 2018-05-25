package com.myntra.apiTests.erpservices.oms.dp;

import java.util.ArrayList;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.lordoftherings.Toolbox;

public class GenerateShippingLabelTestDP {

	IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	private static Long pincode_560068 = Long.parseLong(OMSTCConstants.Pincodes.PINCODE_560068);
	private static Long pincode_180001 = Long.parseLong(OMSTCConstants.Pincodes.PINCODE_180001);
	
	@DataProvider
    public static Object[][] shippingLabelDP(ITestContext testContext) throws Exception {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
			
		list.add(new Object[] {pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] {pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.SDD,EnumSCM.NORMAL });
		list.add(new Object[] {pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] {pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });

		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.SDD,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });
		
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.NORMAL,EnumSCM.NORMAL }); //Failed
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.SDD,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.XPRESS,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_560068,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });//Failed
		
		list.add(new Object[] { pincode_180001,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_180001,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.SDD,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_180001,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_180001,EnumSCM.COD_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });
		
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.SDD,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_ML,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });

		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.SDD,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_EK,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });
		
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.NORMAL,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.SDD,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.XPRESS,EnumSCM.NORMAL });//Failed
		list.add(new Object[] { pincode_560068,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_BD,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });//Failed
		
		list.add(new Object[] { pincode_180001,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.NORMAL,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_180001,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.SDD,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_180001,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.XPRESS,EnumSCM.NORMAL });
		list.add(new Object[] { pincode_180001,EnumSCM.ONLINE_PAYMENT,EnumSCM.COURIER_CODE_IP,EnumSCM.VALUE_SHIPPING,EnumSCM.NORMAL });
		
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}

}