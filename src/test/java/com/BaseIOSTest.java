package com;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;

public class BaseIOSTest extends BaseTest {

	@Override
	protected void setAppiumDriver() throws IOException {
	    logger.debug("Setting up iOSDriver");
		this.wd = new IOSDriver<MobileElement>(new URL(getAppiumServerAddress() + "/wd/hub"), capabilities);
	}

    @Override
    protected String getServerSideApplicationPath() {
        return System.getProperty("user.dir") + "/application.ipa";
    }

    @Override
    protected String getDesiredCapabilitiesPropertiesFileName() {
    		String propertyFileString = null ;
        if (isClientSideTestRun()){
            return "desiredCapabilities.ios.clientside.properties";
        } else {
			switch (platform) {
			case "iOS":
				propertyFileString = "desiredCapabilities.ios.serverside.properties";
			case "iOS_mWeb":
				propertyFileString = "desiredCapabilities.iosWeb.serverside.properties";
			}
            return propertyFileString;
        }
    }

}
