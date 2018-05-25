package com.myntra.apiTests.erpservices.utility;

import com.myntra.apiTests.common.Constants.ReleaseStatus;
import com.myntra.apiTests.common.ProcessOrder.Service.ProcessRelease;
import com.myntra.apiTests.common.entries.ReleaseEntry;
import com.myntra.apiTests.common.entries.ReleaseEntryList;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.common.Constants.EnumSCM;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.test.commons.testbase.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhijit.Pati on 29/10/15.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CreateAndMarkOrderDeliver extends BaseTest{

	public static List orderlist;

	@Test
	public void testBeforeClass() throws Exception {
		ProcessRelease processRelease = new ProcessRelease();
		 End2EndHelper end2EndHelper = new End2EndHelper();
		 OMSServiceHelper omsServiceHelper =  new OMSServiceHelper();
	        String login = System.getenv("Login");
	        String password = System.getenv("Password");
	        String addressId = System.getenv("AddressId");
	        String[] sku = System.getenv("SkuWithQty").split(",");
			String status = System.getenv("ToStatus");

	        String coupon = System.getenv("Coupon");
			boolean isTryNBuy = Boolean.parseBoolean(System.getenv("isTryNBuy"));
//	        boolean cashBack = Boolean.parseBoolean(System.getenv("CashBack"));
//	        boolean loyaltyPoint = Boolean.parseBoolean(System.getenv("Loyalty_point"));
//	        boolean giftWrap = Boolean.parseBoolean(System.getenv("IsGiftWrap"));
			String orderID = end2EndHelper.createOrder(login, password, addressId, sku, coupon, false, false, false,"", isTryNBuy);
			String releaseID = omsServiceHelper.getReleaseId(orderID);
	        Assert.assertTrue(omsServiceHelper.validateReleaseStatusInOMS(releaseID, "WP", 15));
			Assert.assertTrue(omsServiceHelper.validateOrderOFFHoldStatusInOMS(orderID, 15));

		List<ReleaseEntry> releaseEntries = new ArrayList<>();
		releaseEntries.add(new ReleaseEntry.Builder(releaseID, mapStatus(status)).build());
		ReleaseEntryList releaseEntryList= new ReleaseEntryList(releaseEntries);
		processRelease.processReleaseToStatus(releaseEntryList);

		//processRelease.processReleaseToStatus(new ReleaseEntry.Builder(releaseID, mapStatus(status)).build());
	}

	public ReleaseStatus mapStatus(String status){
		ReleaseStatus toStatus = ReleaseStatus.DL;
		switch (status){
			case EnumSCM.PK : toStatus = ReleaseStatus.PK;
				break;
			case EnumSCM.ADDED_TO_MB : toStatus = ReleaseStatus.ADDED_TO_MB;
				break;
			case EnumSCM.IS : toStatus = ReleaseStatus.IS;
				break;
			case EnumSCM.SH : toStatus = ReleaseStatus.SH;
				break;
			case EnumSCM.UNRTO : toStatus = ReleaseStatus.UNRTO;
				break;
			case EnumSCM.RTO : toStatus = ReleaseStatus.RTO;
				break;
			case EnumSCM.LOST : toStatus = ReleaseStatus.LOST;
				break;
			case EnumSCM.FDDL : toStatus = ReleaseStatus.FDDL;
				break;
			case EnumSCM.FDTODL : toStatus = ReleaseStatus.FDTODL;
				break;
			case EnumSCM.FDFDDL : toStatus = ReleaseStatus.FDFDDL;
				break;
			case EnumSCM.DL : toStatus = ReleaseStatus.DL;
				break;
		}
		return toStatus;
	}

}
