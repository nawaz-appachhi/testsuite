package com.myntra.qnotifications;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.test.commons.topology.SystemConfigProvider;

public class CRMNotifyCodesNotifications extends BaseTest {
	private static Logger log = Logger.getLogger(CRMNotifyCodesNotifications.class);
	static Initialize init = new Initialize("/Data/configuration");
	private static final String QName = "orderEventsNotify";

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"RMSService" }, dataProviderClass = CRMNotifyCodesNotificationsDP.class, dataProvider = "notificationpayloads")
	public void pushNotificationsToQ(boolean enabled, String notification, String payload) {
		if (enabled) {
			log.info("The payload being sent for " + notification + " Payload : " + payload);
			SystemConfigProvider.getTopology().getQueueTemplate(QName).convertAndSend(new String(payload));
		}
	}

}
