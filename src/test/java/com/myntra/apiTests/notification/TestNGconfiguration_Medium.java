package com.myntra.apiTests.notification;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.myntra.lordoftherings.AlertLevel;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListener;

public class TestNGconfiguration_Medium
{
	static Logger log = Logger.getLogger(TestNGconfiguration_Medium.class);
	Initialize init = new Initialize("Data/configuration");

	@BeforeSuite(alwaysRun = true)
	public void testBeforeSuite()
	{
		init.NotificationLevel = AlertLevel.MEDIUM;
		System.out.println("testBeforeSuite()");
	}

	@AfterSuite(alwaysRun = true)
	public void testAfterSuite()
	{
		log.info("From after suite");
		init.FrameworkData.endtime = new Date();
		MyntraListener.mailer.sendmail(init);
	}

	@BeforeTest(alwaysRun = true)
	public void testBeforeTest()
	{
		System.out.println("beginning test run");
	}

	@AfterTest(alwaysRun = true)
	public void testAfterTest()
	{
		log.info("ending test run");
	}
}
