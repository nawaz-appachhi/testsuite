package com.myntra.apiTests.notification;

import java.util.Date;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.myntra.lordoftherings.AlertLevel;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.MyntraListener;
//import com.myntra.lordoftherings.aragorn.pages.HomePage;
import com.myntra.lordoftherings.aragorn.pages.WebPage;

public class TestNGconfiguration_Low
{
	static Logger log = Logger.getLogger(TestNGconfiguration_Low.class);
	private Initialize initialize;
	String environment = System.getenv("environment");
	String slackuser = System.getenv("slackuser");
	String JENKINSJOBNAME = System.getenv("JENKINSJOBNAME");
	String botname = "Automation Test Started";
	String botname1 = "Automation Test Inprogress";
	String botname2 = "Automation Test Completed";
	String errorReason;
	//HomePage homePage;
	static Initialize init = new Initialize("/Data/configuration");
	
	@BeforeSuite(alwaysRun = true)
	public void testBeforeSuite()
	{
		init.NotificationLevel = AlertLevel.LOW;
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
	public void testAfterTest(ITestContext context)
	{
		WebPage.ReportAfterClass(context, slackuser, errorReason, botname2,
				JENKINSJOBNAME);
		log.info("ending test run");
	}
}
