package com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.net.URL;

public class BaseAndroidTest extends BaseTest {

	@Override
	protected void setAppiumDriver() throws IOException {
	    logger.debug("Setting up AndroidDriver");
		this.wd = new AndroidDriver<MobileElement>(new URL(getAppiumServerAddress() + "/wd/hub"),
				capabilities);
	}

    @Override
    protected String getServerSideApplicationPath() {
        return System.getProperty("user.dir") + "/application.apk";
    }

    @Override
    protected String getDesiredCapabilitiesPropertiesFileName() {
    		String propertyFileString = null ;
        if (isClientSideTestRun()){
            return "desiredCapabilities.android.clientside.properties";
        } else {
            switch (platform) {
			case "ANDROID":
				propertyFileString = "desiredCapabilities.android.serverside.properties";
			case "ANDROID_mWeb":
				propertyFileString = "desiredCapabilities.androidWeb.serverside.properties";
			}
            return propertyFileString;
        }
    }
}
