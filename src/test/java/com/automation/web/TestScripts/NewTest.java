package com.automation.web.TestScripts;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

public class NewTest {
	@Test
	public void f() {
		System.out.println("@test");
	}

	@Parameters({ "deviceName_", "UDID_", "platformVersion_", "URL_", "appUrl_", "screenshotPath_", "engine_",
	"platform_" })
@BeforeTest

public void beforeTest(@Optional("TD") String deviceName_, @Optional("TD") String UDID_,
	@Optional("TD") String platformVersion_, @Optional("TD") String URL_, @Optional("TD") String appUrl_,
	@Optional("TD") String screenshotPath_, @Optional("TD") String engine_, @Optional("TD") String platform_)
	throws Exception {

	}

	@Parameters({ "engine_" })
	@AfterTest
	public void afterTest(@Optional("TD") String engine_) {
		System.out.println("engine_" + engine_);
	}
}
