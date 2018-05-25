package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class DevApiShotsDP {
	
	String Env;
	Configuration con = new Configuration("./Data/configuration");

	public DevApiShotsDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}


	@DataProvider
	public Object[][] DevApiShots_GetTopics(ITestContext testContext)
	{
		String[] createShots1 = { "Fox7", "aravind.shots@myntra.com", "randompass"};
		String[] createShots2 = { "Production", "aravind.shots@myntra.com", "randompass"};
		String[] createShots3 = { "DevInt", "aravind.shots@myntra.com", "randompass"};

		Object[][] dataSet = new Object[][] {createShots1,createShots2,createShots3};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);

	}
	
	@DataProvider
	public Object[][] DevApiShots_createShots(ITestContext testContext)
	{
		String[] createShots1 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390"};
		String[] createShots2 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription2",""};
		String[] createShots3 = { "Fox7", "aravind.shots@myntra.com", "randompass", "",""};
		String[] createShots4 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777"};
		String[] createShots5 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390"};
		String[] createShots6 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1",""};
		String[] createShots7 = { "Production", "aravind.shots@myntra.com", "randompass", "",""};
		String[] createShots8 = { "Production", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777"};
		String[] createShots9 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390"};
		String[] createShots10 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1",""};
		String[] createShots11 = { "DevInt", "aravind.shots@myntra.com", "randompass", "",""};
		String[] createShots12 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777"};



		Object[][] dataSet = new Object[][] {createShots1,createShots2,createShots3,createShots4,createShots5,createShots6,createShots7,createShots8,createShots9,createShots10,createShots11,createShots12};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiShots_updateShots(ITestContext testContext)
	{
		String[] createShots1 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777"};
		String[] createShots2 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390"};
		String[] createShots3 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","","ShotDescription1","678048,491206,965390"};
		String[] createShots4 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription",""};
		String[] createShots5 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777"};
		String[] createShots6 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390"};
		String[] createShots7 = { "Production", "aravind.shots@myntra.com", "randompass", "","","UpdateDescription",""};
		String[] createShots8 = { "Production", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription","1001950,937657,957777"};
		String[] createShots9 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777"};
		String[] createShots10 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390"};
		String[] createShots11 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","","UpdateDescription",""};
		String[] createShots12 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription","1001950,937657,957777"};


		Object[][] dataSet = new Object[][] {createShots1,createShots2,createShots3,createShots4,createShots5,createShots6,createShots7,createShots8,createShots9,createShots10,createShots11,createShots12};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiShots_getShots_AnotherUser(ITestContext testContext)
	{
		String[] createShots1 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","aravind.shots@myntra.com"};
		String[] createShots2 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription2","","aravind.shots@myntra.com"};
		String[] createShots3 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","","aravind.shots@myntra.com"};
		String[] createShots4 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","aravind.shots@myntra.com"};
		String[] createShots5 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","aravind.shots@myntra.com"};
		String[] createShots6 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","aravind.shots@myntra.com"};
		String[] createShots7 = { "Production", "aravind.shots@myntra.com", "randompass", "","","aravind.shots@myntra.com"};
		String[] createShots8 = { "Production", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","aravind.shots@myntra.com"};
		String[] createShots9 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","aravind.shots@myntra.com"};
		String[] createShots10 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","aravind.shots@myntra.com"};
		String[] createShots11 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","","aravind.shots@myntra.com"};
		String[] createShots12 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","aravind.shots@myntra.com"};



		Object[][] dataSet = new Object[][] {createShots1,createShots2,createShots3,createShots4,createShots5,createShots6,createShots7,createShots8,createShots9,createShots10,createShots11,createShots12};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] DevApiShots_updateShots_AnotherUser(ITestContext testContext)
	{
		String[] createShots1 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777","aravind.shots1@myntra.com"};
		String[] createShots2 = { "Fox7", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390","aravind.shots1@myntra.com"};
		String[] createShots3 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","","ShotDescription1","678048,491206,965390","aravind.shots1@myntra.com"};
		String[] createShots4 = { "Fox7", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription","","aravind.shots1@myntra.com"};
		String[] createShots5 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777","aravind.shots1@myntra.com"};
		String[] createShots6 = { "Production", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390","aravind.shots1@myntra.com"};
		String[] createShots7 = { "Production", "aravind.shots@myntra.com", "randompass", "","","UpdateDescription","","aravind.shots1@myntra.com","aravind.shots1@myntra.com"};
		String[] createShots8 = { "Production", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription","1001950,937657,957777","aravind.shots1@myntra.com"};
		String[] createShots9 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","678048,491206,965390","UpdateDescription","1001950,937657,957777","aravind.shots1@myntra.com"};
		String[] createShots10 = { "DevInt", "aravind.shots@myntra.com", "randompass", "ShotDescription1","","ShotDescription1","678048,491206,965390","aravind.shots1@myntra.com"};
		String[] createShots11 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","","UpdateDescription","","aravind.shots1@myntra.com"};
		String[] createShots12 = { "DevInt", "aravind.shots@myntra.com", "randompass", "","1001950,937657,957777","UpdateDescription","1001950,937657,957777","aravind.shots1@myntra.com"};


		Object[][] dataSet = new Object[][] {createShots1,createShots2,createShots3,createShots4,createShots5,createShots6,createShots7,createShots8,createShots9,createShots10,createShots11,createShots12};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}
