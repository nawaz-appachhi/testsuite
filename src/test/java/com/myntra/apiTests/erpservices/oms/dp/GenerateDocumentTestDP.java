package com.myntra.apiTests.erpservices.oms.dp;

import java.util.ArrayList;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.atp.ATPServiceHelper;
import com.myntra.apiTests.erpservices.ims.IMSServiceHelper;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants.OtherSkus;
import com.myntra.apiTests.erpservices.oms.Test.OMSTCConstants.WareHouseIds;
import com.myntra.apiTests.erpservices.sellerapis.SellerConfig;
import com.myntra.lordoftherings.Toolbox;

public class GenerateDocumentTestDP {
	

	private static Long vectorSellerID=SellerConfig.getSellerID(SellerConfig.SellerName.HANDH);;
	private static String supplyTypeOnHand="ON_HAND";
	static IMSServiceHelper imsServiceHelper = new IMSServiceHelper();
	static ATPServiceHelper atpServiceHelper = new ATPServiceHelper();
	static String login_discount = "notifyme763@gmail.com"; 
	static String password_discount = "myntra@123";
	static End2EndHelper end2EndHelper = new End2EndHelper();
	

	
	@DataProvider
    public static Object[][] documentGenerationDP(ITestContext testContext) throws Exception {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		
		String orderID = end2EndHelper.createOrder(login_discount, password_discount, "6132352", skuId, "", false, false, false, "", false);
		
		list.add(new Object[] { orderID });
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());
	}
	
	@DataProvider
    public static Object[][] cancelPKDP(ITestContext testContext) throws Exception {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		
		String skuId[] = {OMSTCConstants.OtherSkus.skuId_3831+":4"};
		atpServiceHelper.updateInventoryDetailsForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID+":1"}, supplyTypeOnHand);
		imsServiceHelper.updateInventoryForSeller(new String[]{OMSTCConstants.OtherSkus.skuId_3831+":"+OMSTCConstants.WareHouseIds.warehouseId28_GN+":10000:0:"+vectorSellerID},
				supplyTypeOnHand);
		
		String orderID = end2EndHelper.createOrder(login_discount, password_discount, "6132352", skuId, "", false, false, false, "", false);
		
		list.add(new Object[] { orderID,login_discount });
		return Toolbox.returnReducedDataSet(list.toArray(new Object[0][]), testContext.getIncludedGroups(), list.size(),
				list.size());

	}

}
