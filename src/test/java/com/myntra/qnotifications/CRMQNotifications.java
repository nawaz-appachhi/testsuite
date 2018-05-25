package com.myntra.qnotifications;

import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import com.myntra.test.commons.topology.SystemConfigProvider;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;


public class CRMQNotifications extends BaseTest{
	private static Logger log = Logger.getLogger(CRMQNotifications.class);
	static Initialize init = new Initialize("/Data/configuration");
	private static final String Q_Name = "spsAddTransactionQueue";

	@Test(groups = { "Smoke", "Sanity", "ProdSanity", "Regression", "MiniRegression", "ExhaustiveRegression",
			"RMSService" }, dataProviderClass = CRMNotificationsDp.class, dataProvider = "notificationpayloads")
	public void pushNotificationsToQ(boolean enabled, String notification, String payload) throws IOException {
		if (enabled) {
			log.info("The payload being sent for " + notification + " Payload : " + payload);
			SystemConfigProvider.getTopology().getQueueTemplate(Q_Name).convertAndSend(new String(payload));
			
		}
	}

}
