package com.myntra.apiTests.dataproviders;

import java.util.ArrayList;
import java.util.HashMap;

import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import com.myntra.apiTests.portalservices.lgpservices.LgpActionsTest;
import org.testng.annotations.DataProvider;

public class LgpServDP extends FeedObjectHelper {

	
	String envName = LgpActionsTest.init.Configurations.GetTestEnvironemnt().toString().toLowerCase();
	
	/* ***********  LGP Actions DP ************** */
	
	@DataProvider
	public Object[][] lgpActionsDP(){
		 
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage") || envName.equalsIgnoreCase("sfqa")){
			
			/* Positive Cases */
			
			String[] action1 = {"2:custom-object-868","Object","like","case1"};
			
			/* Negative Cases */
			
			String[] action2 = {"","Object","like", "case2"};
			String[] action3 = {"2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c","","like", "case3"};
			String[] action4 = {"2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c","Object","", "case4"};
			String[] action5 = {"","","", "case5"};
			
			/*Negative Cases with status 200*/
			
			String[] action6 = {" 2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c"," Object"," like", "case6Spaces"};
			String[] action7 = {"2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c ","Object ","like ", "case6Spaces"};
			String[] action8 = {"2:0b545b4f-f308-  48ba-abc6-54c2c15f6d0c","Ob  ject","li ke", "case6Spaces"};
			
			
			String[] action9 = {"~!@#$%^&*()_+","Object","like", "case7Special"};
			String[] action10 = {"2:custom-object-868","~!@#$%^&*()_+","like", "case7Special"};
			String[] action11 = {"2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c","2:custom-object-868","~!@#$%^&*()_+", "case7Special"};
			
			
			/*Negative Cases with status 400*/
			String[] action12 = {"		2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c","		Object","	like", "case8Tabs"};
			String[] action13 = {"2:0b545b4f-f308-48ba-abc6-54c2c15f6d0c		","Object		","like	   ", "case8Tabs"};
			
			dataSet = new Object[][]{action1,action2,action3,action4,action5,action6,action7,action8,action9,action10,
									action11,action12,action13};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			/* Positive Cases */
			
			String[] action1 = {"2:8f7f2fb4-1ba2-44e6-9d25-871797512a62","Object","like","case1"};
			
			/* Negative Cases */
			
			String[] action2 = {"","Object","like", "case2"};
			String[] action3 = {"2:26322012-d79c-4c5f-ba3d-11d4943d22d4","","like", "case3"};
			String[] action4 = {"2:26322012-d79c-4c5f-ba3d-11d4943d22d4","Object","", "case4"};
			String[] action5 = {"","","", "case5"};
			
			/*Negative Cases with status 200*/
			
			String[] action6 = {" 2:26322012-d79c-4c5f-ba3d-11d4943d22d4"," Object"," like", "case6Spaces"};
			String[] action7 = {"2:26322012-d79c-4c5f-ba3d-11d4943d22d4 ","Object ","like ", "case6Spaces"};
			String[] action8 = {"2:26322012-d79c-4c5f-ba3d-11d4943d22d4","Ob  ject","li ke", "case6Spaces"};
			
			String[] action9 = {"~!@#$%^&*()_+","Object","like", "case7Special"};
			String[] action10 = {"2:8f7f2fb4-1ba2-44e6-9d25-871797512a62","~!@#$%^&*()_+","like", "case7Special"};
			String[] action11 = {"2:8f7f2fb4-1ba2-44e6-9d25-871797512a62","2:custom-object-868","~!@#$%^&*()_+", "case7Special"};
			
			
			/*Negative Cases with status 400*/
			String[] action12 = {"	2:26322012-d79c-4c5f-ba3d-11d4943d22d4","	Object","	like", "case8Tabs"};
			String[] action13 = {"2:26322012-d79c-4c5f-ba3d-11d4943d22d4	","Object	","like	   ", "case8Tabs"};
			
			dataSet = new Object[][]{action1,action2,action3,action4,action5,action6,action7,action8,action9,action10,
				action11,action12,action13};

		}
		
		return dataSet;
	}

	@DataProvider
	public Object[][] lgpFollowAndUnFollowDp(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage") || envName.equalsIgnoreCase("sfqa")){
			
			
			/* Positive cases */
			String[] uidxList1 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc","case1"};

			/*Negative cases*/
			String[] uidxList2 = {"a5b3c136.0f59.431c.bb3d.4f704","05ae4bbb.5f53.4eb3.827d.7cf2cc39d5","case2"};
			
			String[] uidxList3 = {"","","case3"};
			
			String[] uidxList4 = {" a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl"," 05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc","case4Spaces"};
			String[] uidxList5 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl ","05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc ","case4Spaces"};
			String[] uidxList6 = {"a5b3c136.0f59.431 c.bb3d . 4f70472356ef6HJtdiMgcl","05ae4bbb.5f53.4eb3.827 d .7cf2cc39d549LhWNwuOfKc","case4Spaces"};
			
			
			String[] uidxList7 = {"		9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P","	252175d5.d64e.4d4a.a696.3c62874b01cd7URPbvacNg","case5Tabs"};
			String[] uidxList8 = {"9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P	","252175d5.d64e.4d4a.a696.3c62874b01cd7URPbvacNg	","case5Tabs"};
			
			String[] uidxList9 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","~!@#$%^&*()_+","case6Special"};
			String[] uidxList10 = {"~!@#$%^&*()_+","a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","case6Special"};
			
			
			dataSet = new Object[][]{uidxList1,uidxList2,uidxList3,uidxList4,uidxList5,uidxList6,uidxList7,uidxList8,uidxList9,uidxList10};
			
			
		}else if(envName.equalsIgnoreCase("production")){
			
			/* Positive cases */
			//String[] uidxList1 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case1"};

			/*Negative cases*/
			String[] uidxList2 = {"a5b3c136.0f59.431c.bb3d.4f704","05ae4bbb.5f53.4eb3.827d.7cf2cc39d5","case2"};
			
			String[] uidxList3 = {"","","case3"};
			
			String[] uidxList4 = {" 79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R"," 4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case4Spaces"};
			String[] uidxList5 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R ","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT ","case4Spaces"};
			String[] uidxList6 = {"79e24ff0.4b d7.41de.a75b. 285d4537aed8l8PcqRIi0R","4621949d.dbe 6.4897. 90ec.0b1e3106d6889vwkMFk9sT","case4Spaces"};
			
			
			String[] uidxList7 = {"		79e24ff0.4b d7.41de.a75b. 285d4537aed8l8PcqRIi0R","		4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case5Tabs"};
			String[] uidxList8 = {"9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P		","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT		","case5Tabs"};
			
			String[] uidxList9 = {"~!@#$%^&*()_+","79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","case6Special"};
			String[] uidxList10 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","~!@#$%^&*()_+","case6Special"};
		
			
			dataSet = new Object[][]{/*uidxList1,*/uidxList2,uidxList3,uidxList4,uidxList5,uidxList6,uidxList7,uidxList8,uidxList9,uidxList10};
			
		}
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] lgpBulkActionsDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
			
			String[] action1 = {"1547","Story","like","4:1c240e35-af1c-4037-a655-bdb5a85174b6","Story","like","case1"};
			
			String[] action3 = {"","Story","like","","Story","like","case3"};
			
			String[] action5 = {"1547","","like","4:1c240e35-af1c-4037-a655-bdb5a85174b6","","like","case5"};
			
			String[] action7 = {"1547","Story","","4:1c240e35-af1c-4037-a655-bdb5a85174b6","Story","","case7"};
			
			String[] action9 = {"","","","","","","case9"};
			
			
			dataSet = new Object[][]{action1,action3,action5,action7,action9};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			String[] action1 = {"1547","Object","like","4:1c240e35-af1c-4037-a655-bdb5a85174b6","Object","like","case1"};
			
			String[] action3 = {"","Story","like","","Story","like","case3"};
			
			String[] action5 = {"1547","","like","4:1c240e35-af1c-4037-a655-bdb5a85174b6","","like","case5"};
			
			String[] action7 = {"1547","Story","","4:1c240e35-af1c-4037-a655-bdb5a85174b6","Story","","case7"};
			
			String[] action9 = {"","","","","","","case9"};
			
			dataSet = new Object[][]{action1,action3,action5,action7,action9};
			
		}
		
		return dataSet;
	}
	
		
		
	
	/* ***********  LGP Actions DP ************** */
	////////////////////////////////////////////////////////////////
	
	/* ***********  LGP User DP ************** */
	
	@DataProvider
	public Object[][] lgpUserPPIDProvider(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){

			/* positive case */
			String[] ppid1 = {"jamesbond007&","case1"};
			
			/* negative case - status 404 */
			String[] ppid2 = {"jamesb","case2"};
			
			dataSet = new Object[][]{ppid1,ppid2};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			
			/* positive case */
			String[] ppid1 = {"ranju.krishna&","case1"};
			
			/* negative case - status 404 */
			String[] ppid2 = {"rnju.kris","case2"};
			
			dataSet = new Object[][]{ppid1,ppid2};
			
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] lgpGetUserProfilesDP(){
		
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){

			/* Positive case*/
			String[] wruidx1 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","case1"};
			
			/* Negative case */
			String[] wruidx2 = {"","case2"};
			String[] wruidx3 = {"79e24ff0.4bd7.4RIi0R","case3"};
			
			
			dataSet = new Object[][]{wruidx1,wruidx2,wruidx3};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			/* Positive case*/
			String[] wruidx1 = {"32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB","case1"};
			
			/* Negative case */
			String[] wruidx2 = {"","case2"};
			String[] wruidx3 = {"32b5f490.cc50.495e.96a3.cae","case3"};
			
			dataSet = new Object[][]{wruidx1,wruidx2,wruidx3};
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] lgpGetUserProfilesByPPIDDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){

			/* Positive case*/
			String[] ppid1 = {"jamesbond007","case1"};
			
			/* Negative case */
			String[] ppid2 = {"jamesb","case2"};
			
			dataSet = new Object[][]{ppid1,ppid2};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			/* Positive case*/
			String[] ppid1 = {"ranju.krishna","case1"};
			
			/* Negative case */
			String[] ppid2 = {"rnju.kris","case2"};
			dataSet = new Object[][]{ppid1,ppid2};
		}
		
		return dataSet;
	}
	
	
	@DataProvider
	public Object[][] lgpSummaryAndDetailsDP(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
			
			/* Positive cases */
			String[] uidxList1 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc","case1"};

			/*Negative cases*/
			String[] uidxList2 = {"a5b3c136.0f59.431c.bb3d.4f704","05ae4bbb.5f53.4eb3.827d.7cf2cc39d5","case2"};
			
			String[] uidxList3 = {"","","case3"};
			
			String[] uidxList4 = {" a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl"," 05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc","case4Spaces"};
			String[] uidxList5 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl ","05ae4bbb.5f53.4eb3.827d.7cf2cc39d549LhWNwuOfKc ","case4Spaces"};
			String[] uidxList6 = {"a5b3c136.0f59.431 c.bb3d . 4f70472356ef6HJtdiMgcl","05ae4bbb.5f53.4eb3.827 d .7cf2cc39d549LhWNwuOfKc","case4Spaces"};
			
			
			String[] uidxList7 = {"		9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P","	252175d5.d64e.4d4a.a696.3c62874b01cd7URPbvacNg","case5Tabs"};
			String[] uidxList8 = {"9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P	","252175d5.d64e.4d4a.a696.3c62874b01cd7URPbvacNg	","case5Tabs"};
			
			String[] uidxList9 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","~!@#$%^&*()_+","case6Special"};
			String[] uidxList10 = {"~!@#$%^&*()_+","a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","case6Special"};
			
			dataSet = new Object[][]{uidxList1,uidxList2,uidxList3,uidxList4,uidxList5,uidxList6,uidxList7,uidxList8,uidxList9,uidxList10};
			
		}else if(envName.equalsIgnoreCase("production")){
			

			/* Positive cases */
			String[] uidxList1 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case1"};

			/*Negative cases*/
			String[] uidxList2 = {"a5b3c136.0f59.431c.bb3d.4f704","05ae4bbb.5f53.4eb3.827d.7cf2cc39d5","case2"};
			
			String[] uidxList3 = {"","","case3"};
			
			String[] uidxList4 = {" 79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R"," 4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case4Spaces"};
			String[] uidxList5 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R ","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT ","case4Spaces"};
			String[] uidxList6 = {"79e24ff0.4b d7.41de.a75b. 285d4537aed8l8PcqRIi0R","4621949d.dbe 6.4897. 90ec.0b1e3106d6889vwkMFk9sT","case4Spaces"};
			
			
			String[] uidxList7 = {"		79e24ff0.4b d7.41de.a75b. 285d4537aed8l8PcqRIi0R","		4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT","case5Tabs"};
			String[] uidxList8 = {"9dca79cf.2e5a.4674.8dc1.42d1bfc208a6RHOJdhAV2P		","4621949d.dbe6.4897.90ec.0b1e3106d6889vwkMFk9sT		","case5Tabs"};
			
			String[] uidxList9 = {"~!@#$%^&*()_+","79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","case6Special"};
			String[] uidxList10 = {"79e24ff0.4bd7.41de.a75b.285d4537aed8l8PcqRIi0R","~!@#$%^&*()_+","case6Special"};
			
			dataSet = new Object[][]{uidxList1,uidxList2,uidxList3,uidxList4,uidxList5,uidxList6,uidxList7,uidxList8,uidxList9,uidxList10};

		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] lgpUserFollowingAndFollowersProvider(){
		
		Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
			
			/*positive case*/
			String[] uidx1 = {"a5b3c136.0f59.431c.bb3d.4f70472356ef6HJtdiMgcl","case1"};
			
			/*negative case*/
			String[] uidx2 = {"a5b72356ef6HJtdiMgcl","case2"};
			
			dataSet = new Object[][]{uidx1,uidx2};
			
		}else if(envName.equalsIgnoreCase("production")){
			
			/*positive case*/
			String[] uidx1 = {"32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB","case1"};
			
			/*negative case*/
			String[] uidx2 = {"495e.96a3.cae4c06cc298ciOvG9gGh","case2"};
			
			dataSet = new Object[][]{uidx1,uidx2};
		}
		
		
		return dataSet;
	}

	
	
	/* ***********  LGP User DP ************** */
	
	@DataProvider
	public Object[][] articleCardFeedObjectEntityDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
			
			String[] objectRefId1 = {"2:test-object-3988"};
			dataSet = new Object[][]{objectRefId1};
			
		}
		
		else if(envName.equalsIgnoreCase("production")){
			
			String[] objectRefId1 = {"2:ce499032-49c9-4e96-9579-d3b575083179"};
			dataSet = new Object[][]{objectRefId1};
			
			
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] questionCardFeedObjectEntityDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
		
			String[] objectRefId1 = {"7:QUESTION-1526"};
			dataSet = new Object[][]{objectRefId1};
		
		}
		else if(envName.equalsIgnoreCase("production")){
			
			String[] objectRefId1 = {""};
			dataSet = new Object[][]{objectRefId1};
			
			
		}
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] bannerCardFeedObjectEntityDP(){
		
		Object[][] dataSet = null;
	
	
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	
			String[] objectRefId1 = {"2:90ca9d14-1557-41ff-9298-7d413d65bbdb"};
			dataSet = new Object[][]{objectRefId1};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			String[] objectRefId1 = {"2:5048f692-170a-4e66-be3d-973e314910e8"};
			dataSet = new Object[][]{objectRefId1};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] splitBannerCardFeedObjectEntityDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	
			String[] objectRefId1 = {"2:a7769e10-7912-41e7-901d-53369dfc3cef"};
			dataSet = new Object[][]{objectRefId1};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			String[] objectRefId1 = {"2:b2cdf6ac-68b8-41c9-b0c1-de9bb33e40a2"};
			dataSet = new Object[][]{objectRefId1};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] videoCardFeedObjectEntityDP(){//2:e28cfbc5-5eea-4b6e-a948-70833052b038
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	
			String[] objectRefId1 = {"2:e28cfbc5-5eea-4b6e-a948-70833052b038"};
			dataSet = new Object[][]{objectRefId1};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			String[] objectRefId1 = {"2:3382b1ad-50e4-44e2-8ee2-4ffdd253db05"};
			dataSet = new Object[][]{objectRefId1};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] pollCardFeedObjectEntityDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	
			String[] objectRefId1 = {"7:POLL-10013"};
			dataSet = new Object[][]{objectRefId1};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			String[] objectRefId1 = {""};
			dataSet = new Object[][]{objectRefId1};
			
		}
	
		return dataSet;
	}

	
	/* SNS */
	
	@DataProvider
	public Object[][] influencersDefaultDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7") || envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("200");
//            list3.add("2009");
//            list3.add("Source vertex not found");
//            list3.add("Trailing Spaces in UIDX Error message not retrived as expected");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x    ",list3);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("200");
//            list4.add("2009");
//            list4.add("Source vertex not found");
//            list4.add("Leading Spaces in UIDX Error message not retrived as expected");
//            map4.put("     970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
//            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
//            ArrayList<String> list6 = new ArrayList<>();
//            list6.add("200");
//            list6.add("56");
//            list6.add("Error occurred while retrieving/processing data");
//            list6.add("Special Chars as UIDX Error message not retrived as expected");
//            map6.put("#$^@$&^#&",list6);
            
			//dataSet = new Object[][]{{map1},{map2},{map5}};
			dataSet = new Object[][]{{map1}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gG",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("200");
//            list3.add("2009");
//            list3.add("Source vertex not found");
//            list3.add("Trailing Spaces in UIDX Error message not retrived as expected");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x    ",list3);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("200");
//            list4.add("2009");
//            list4.add("Source vertex not found");
//            list4.add("Leading Spaces in UIDX Error message not retrived as expected");
//            map4.put("     970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
//            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
//            ArrayList<String> list6 = new ArrayList<>();
//            list6.add("200");
//            list6.add("56");
//            list6.add("Error occurred while retrieving/processing data");
//            list6.add("Special Chars as UIDX Error message not retrived as expected");
//            map6.put("#$^@$&^#&",list6);
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersDefaultDPBadErrorCode(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("404");
            list1.add("Bad Error Code 404 not retrieved as expected");
            map1.put("",list1);
            
               
			dataSet = new Object[][]{{map1}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("404");
	            list1.add("Bad Error Code 404 not retrieved as expected");
	            map1.put("",list1);
	            
	               
				dataSet = new Object[][]{{map1}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersFetchSizeDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("3");
            list1.add("3");
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("0");
            list2.add("0");
            list2.add("200");
            list2.add("2057");
            list2.add("Limit/fetchSize should be greater than zero");
            list2.add("Sending 0 as FetchSize Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("-1");
            list5.add("0");
            list5.add("200");
            list5.add("2057");
            list5.add("Limit/fetchSize should be greater than zero");
            list5.add("Sending -1 as Fetch Size Error message not retrived as expected");
            map5.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list5);
            
			//dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("3");
	            list1.add("3");
	            list1.add("200");
	            list1.add("1051");
	            list1.add("Influencer(s) retrieved successfully");
	            list1.add("Not able to successfully retrieve influencers");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("0");
	            list2.add("0");
	            list2.add("200");
	            list2.add("2057");
	            list2.add("Limit/fetchSize should be greater than zero");
	            list2.add("Sending 0 as FetchSize Error message not retrived as expected");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9g",list2);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("-1");
	            list5.add("0");
	            list5.add("200");
	            list5.add("2057");
	            list5.add("Limit/fetchSize should be greater than zero");
	            list5.add("Sending -1 as Fetch Size Error message not retrived as expected");
	            map5.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list5);
	            
				dataSet = new Object[][]{{map1},{map2},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersFetchSizeDPBadErrorCode(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("null");
            list1.add("404");
            list1.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("");
            list2.add("404");
            list2.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("  ");
//            list3.add("404");
//            list3.add("Bad Error Code 404 not retrieved as expected for spaces in fetchsize");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list3);
//            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("  3");
//            list4.add("404");
//            list4.add("Bad Error Code 404 not retrieved as expected for leading spaces in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
           
           
            
			dataSet = new Object[][]{{map1},{map2}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			  HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("null");
	            list1.add("404");
	            list1.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("");
	            list2.add("404");
	            list2.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list2);
	            
//	            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//	            ArrayList<String> list3 = new ArrayList<>();
//	            list3.add("  ");
//	            list3.add("404");
//	            list3.add("Bad Error Code 404 not retrieved as expected for spaces in fetchsize");
//	            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list3);
//	            
//	            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//	            ArrayList<String> list4 = new ArrayList<>();
//	            list4.add("  3");
//	            list4.add("404");
//	            list4.add("Bad Error Code 404 not retrieved as expected for leading spaces in fetchsize");
//	            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
	           
	           
	            
				dataSet = new Object[][]{{map1},{map2}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersOffsetDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("3");
            list1.add("3");
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("0");
            list2.add("0");
            list2.add("200");
            list2.add("1051");
            list2.add("Influencer(s) retrieved successfully");
            list2.add("Not able to successfully retrieve influencers");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("-1");
            list5.add("0");
            list5.add("200");
            list5.add("1051");
            list5.add("Influencer(s) retrieved successfully");
            list5.add("Not able to successfully retrieve influencers");
            map5.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list5);
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("3");
	            list1.add("3");
	            list1.add("200");
	            list1.add("1051");
	            list1.add("Influencer(s) retrieved successfully");
	            list1.add("Not able to successfully retrieve influencers");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("0");
	            list2.add("0");
	            list2.add("200");
	            list2.add("1051");
	            list2.add("Influencer(s) retrieved successfully");
	            list2.add("Not able to successfully retrieve influencers");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list2);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("-1");
	            list5.add("0");
	            list5.add("200");
	            list5.add("1051");
	            list5.add("Influencer(s) retrieved successfully");
	            list5.add("Not able to successfully retrieve influencers");
	            map5.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list5);
	            
				dataSet = new Object[][]{{map1},{map2},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersOffsetDPBadErrorCode(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("null");
            list1.add("404");
            list1.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("");
            list2.add("404");
            list2.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("  ");
//            list3.add("404");
//            list3.add("Bad Error Code 404 not retrieved as expected for spaces in fetchsize");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list3);
//            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("  3");
//            list4.add("404");
//            list4.add("Bad Error Code 404 not retrieved as expected for leading spaces in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
//           
           
            
			dataSet = new Object[][]{{map1},{map2}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("null");
	            list1.add("404");
	            list1.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("");
	            list2.add("404");
	            list2.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list2);
	            dataSet = new Object[][]{{map1},{map2}};
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("3");
            list1.add("0");
            //list1.add("3");
			list1.add("0");
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("3");
            list2.add("-1");
            //list2.add("3");
			list2.add("0");
            list2.add("200");
            list2.add("1051");
            list2.add("Influencer(s) retrieved successfully");
            list2.add("Not able to successfully retrieve influencers");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("-1");
            list3.add("1");
            list3.add("0");
            list3.add("200");
            list3.add("2057");
            list3.add("Limit/fetchSize should be greater than zero");
            list3.add("Sending 0 as fetchSize Error message not as expected");
            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list3);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("-1");
            list4.add("-1");
            list4.add("0");
            list4.add("200");
            list4.add("2057");
            list4.add("Limit/fetchSize should be greater than zero");
            list4.add("Sending 0 as fetchSize Error message not as expected");
            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("0");
            list5.add("1");
            list5.add("0");
            list5.add("200");
            list5.add("2057");
            list5.add("Limit/fetchSize should be greater than zero");
            list5.add("Sending 0 as fetchSize Error message not as expected");
            map5.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list5);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("3");
            list1.add("0");
            list1.add("3");
            list1.add("200");
            list1.add("1051");
            list1.add("Influencer(s) retrieved successfully");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("3");
            list2.add("-1");
            list2.add("3");
            list2.add("200");
            list2.add("1051");
            list2.add("Influencer(s) retrieved successfully");
            list2.add("Not able to successfully retrieve influencers");
            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("-1");
            list3.add("1");
            list3.add("0");
            list3.add("200");
            list3.add("2057");
            list3.add("Limit/fetchSize should be greater than zero");
            list3.add("Sending 0 as fetchSize Error message not as expected");
            map3.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list3);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("-1");
            list4.add("-1");
            list4.add("0");
            list4.add("200");
            list4.add("2057");
            list4.add("Limit/fetchSize should be greater than zero");
            list4.add("Sending 0 as fetchSize Error message not as expected");
            map4.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("0");
            list5.add("1");
            list5.add("0");
            list5.add("200");
            list5.add("2057");
            list5.add("Limit/fetchSize should be greater than zero");
            list5.add("Sending 0 as fetchSize Error message not as expected");
            map5.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list5);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] influencersDPBadErrorCode(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("-1");
            list1.add("");
            list1.add("404");
            list1.add("Bad Error Code 404 not retrieved as expected for either one of as -1 in fetchsize");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("");
            list2.add("-1");
            list2.add("404");
            list2.add("Bad Error Code 404 not retrieved as expected for either one of as -1 in fetchsize");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list2);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("");
//            list4.add("");
//            list4.add("404");
//            list4.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
//            
//            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
//            ArrayList<String> list5 = new ArrayList<>();
//            list5.add("  ");
//            list5.add("  ");
//            list5.add("404");
//            list5.add("Bad Error Code 404 not retrieved as expected for spaces in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list5);
            
            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> list6 = new ArrayList<>();
            list6.add("null");
            list6.add("null");
            list6.add("404");
            list6.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
            map6.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list6);
            
            HashMap<String,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> list7 = new ArrayList<>();
            list7.add("null");
            list7.add("1");
            list7.add("404");
            list7.add("Bad Error Code 404 not retrieved as expected for either one as null  in fetchsize");
            map7.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list7);
            
            HashMap<String,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> list8 = new ArrayList<>();
            list8.add("1");
            list8.add("null");
            list8.add("404");
            list8.add("Bad Error Code 404 not retrieved as expected for either one as null in fetchsize");
            map8.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list8);
           
           
            
			dataSet = new Object[][]{{map1},{map2},{map6},{map7},{map8}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("-1");
            list1.add("");
            list1.add("404");
            list1.add("Bad Error Code 404 not retrieved as expected for either one of as -1 in fetchsize");
            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("");
            list2.add("-1");
            list2.add("404");
            list2.add("Bad Error Code 404 not retrieved as expected for either one of as -1 in fetchsize");
            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list2);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("");
//            list4.add("");
//            list4.add("404");
//            list4.add("Bad Error Code 404 not retrieved as expected for blanks in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
//            
//            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
//            ArrayList<String> list5 = new ArrayList<>();
//            list5.add("  ");
//            list5.add("  ");
//            list5.add("404");
//            list5.add("Bad Error Code 404 not retrieved as expected for spaces in fetchsize");
//            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list5);
            
            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
            ArrayList<String> list6 = new ArrayList<>();
            list6.add("null");
            list6.add("null");
            list6.add("404");
            list6.add("Bad Error Code 404 not retrieved as expected for null in fetchsize");
            map6.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list6);
            
            HashMap<String,ArrayList<String>> map7 = new HashMap<>();
            ArrayList<String> list7 = new ArrayList<>();
            list7.add("null");
            list7.add("1");
            list7.add("404");
            list7.add("Bad Error Code 404 not retrieved as expected for either one as null  in fetchsize");
            map7.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list7);
            
            HashMap<String,ArrayList<String>> map8 = new HashMap<>();
            ArrayList<String> list8 = new ArrayList<>();
            list8.add("1");
            list8.add("null");
            list8.add("404");
            list8.add("Bad Error Code 404 not retrieved as expected for either one as null in fetchsize");
            map8.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list8);
           
           
            
			dataSet = new Object[][]{{map1},{map2},{map6},{map7},{map8}};
	
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] followingDefaultDP(){
		
		Object[][] dataSet = null;
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1003");
            list1.add("User(s) retrieved successfully");
            list1.add("Not able to successfully retrieve following users");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("200");
//            list3.add("2009");
//            list3.add("Source vertex not found");
//            list3.add("Trailing Spaces in UIDX Error message not retrived as expected");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x    ",list3);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("200");
//            list4.add("2009");
//            list4.add("Source vertex not found");
//            list4.add("Leading Spaces in UIDX Error message not retrived as expected");
//            map4.put("     970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
//            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
//            ArrayList<String> list6 = new ArrayList<>();
//            list6.add("200");
//            list6.add("56");
//            list6.add("Error occurred while retrieving/processing data");
//            list6.add("Special Chars as UIDX Error message not retrived as expected");
//            map6.put("#$^@$&^#&",list6);
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1003");
            list1.add("User(s) retrieved successfully");
            list1.add("Not able to successfully retrieve following users");
            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
//            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
//            ArrayList<String> list3 = new ArrayList<>();
//            list3.add("200");
//            list3.add("2009");
//            list3.add("Source vertex not found");
//            list3.add("Trailing Spaces in UIDX Error message not retrived as expected");
//            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x    ",list3);
            
//            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
//            ArrayList<String> list4 = new ArrayList<>();
//            list4.add("200");
//            list4.add("2009");
//            list4.add("Source vertex not found");
//            list4.add("Leading Spaces in UIDX Error message not retrived as expected");
//            map4.put("     970794db.228e.4133.933e.1574198ef08frA7cPl428x",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
//            HashMap<String,ArrayList<String>> map6 = new HashMap<>();
//            ArrayList<String> list6 = new ArrayList<>();
//            list6.add("200");
//            list6.add("56");
//            list6.add("Error occurred while retrieving/processing data");
//            list6.add("Special Chars as UIDX Error message not retrived as expected");
//            map6.put("#$^@$&^#&",list6);
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] followersDefaultDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1003");
            list1.add("User(s) retrieved successfully");
            list1.add("Not able to successfully retrieve followers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("1003");
	            list1.add("User(s) retrieved successfully");
	            list1.add("Not able to successfully retrieve followers");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("2059");
	            list2.add("user id is invalid");
	            list2.add("Invalid UIDX Error message not retrived as expected");
	            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("200");
	            list5.add("2059");
	            list5.add("user id is invalid");
	            list5.add("Null as UIDX Error message not retrived as expected");
	            map5.put("null",list5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] topicsToFollowDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1023");
            list1.add("Tag(s) retrieved successfully");
            list1.add("Not able to successfully retrieve Tags");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("1023");
	            list1.add("Tag(s) retrieved successfully");
	            list1.add("Not able to successfully retrieve Tags");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("2059");
	            list2.add("user id is invalid");
	            list2.add("Invalid UIDX Error message not retrived as expected");
	            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("200");
	            list5.add("2059");
	            list5.add("user id is invalid");
	            list5.add("Null as UIDX Error message not retrived as expected");
	            map5.put("null",list5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] topicsAffinitiesDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("970794db.228e.4133.933e.1574198ef08frA7cPl428x");
            list1.add("male");
            list1.add("Not able to successfully retrieve influencers");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x",list1);
            
//            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
//            ArrayList<String> list2 = new ArrayList<>();
//            list2.add("200");
//            list2.add("2009");
//            list2.add("Source vertex not found");
//            list2.add("Invalid UIDX Error message not retrived as expected");
//            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
//            
//            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
//            ArrayList<String> list5 = new ArrayList<>();
//            list5.add("200");
//            list5.add("2009");
//            list5.add("Source vertex not found");
//            list5.add("Null as UIDX Error message not retrived as expected");
//            map5.put("null",list5);
            
            
			dataSet = new Object[][]{{map1}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB");
	            list1.add("male");
	            list1.add("Not able to successfully retrieve influencers");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
//	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
//	            ArrayList<String> list2 = new ArrayList<>();
//	            list2.add("200");
//	            list2.add("2009");
//	            list2.add("Source vertex not found");
//	            list2.add("Invalid UIDX Error message not retrived as expected");
//	            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
//	            
//	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
//	            ArrayList<String> list5 = new ArrayList<>();
//	            list5.add("200");
//	            list5.add("2009");
//	            list5.add("Source vertex not found");
//	            list5.add("Null as UIDX Error message not retrived as expected");
//	            map5.put("null",list5);
	            
	            
				dataSet = new Object[][]{{map1}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] contactsToFollowDP(){
		
		Object[][] dataSet = null;
		
		
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1003");
            list1.add("User(s) retrieved successfully");
            list1.add("Not able to successfully retrieve contacts");
            map1.put("87d465aa.0223.46af.963c.1d8506079477XM22CQuVSM",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null",list5);
            
			dataSet = new Object[][]{{map1},{map2},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("1003");
	            list1.add("User(s) retrieved successfully");
	            list1.add("Not able to successfully retrieve contacts");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("2059");
	            list2.add("user id is invalid");
	            list2.add("Invalid UIDX Error message not retrived as expected");
	            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPlkajd",list2);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("200");
	            list5.add("2059");
	            list5.add("user id is invalid");
	            list5.add("Null as UIDX Error message not retrived as expected");
	            map5.put("null",list5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map5}};
			
		}
	
		return dataSet;
	}
	
	@DataProvider
	public Object[][] performActionUserBulkDP()
	{
         Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,String[]> map1 = new HashMap<>();
            String[] payload1 ={"d6eeeaa4.f24f.4f3e.98f8.97a6985e90fcG487Y29GDT","956904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m","03934b0b.8b73.4200.a1a7.c76b01b60eb0EGXT1QnyS3"};
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:A",payload1);
            
            HashMap<String,String[]> map2 = new HashMap<>();
            String[] payload2 ={"d6eeeaa4.f24f.4f3e.98f8.97a6985e90fcG487Y29","56904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m","934b0b.8b73.4200.a1a7.c76b01b60eb0EGXT1QnyS3"};
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:B",payload2);
            
            HashMap<String,String[]> map3 = new HashMap<>();
            String[] payload3 ={"","",""};
            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:C",payload3);
            
            HashMap<String,String[]> map4 = new HashMap<>();
            String[] payload4 ={"  ","  ","  "};
            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:D",payload4);
            
            HashMap<String,String[]> map5 = new HashMap<>();
            String[] payload5 ={null,null,null};
            map5.put("null:E",payload5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,String[]> map1 = new HashMap<>();
	            String[] payload1 ={"a414535f.7867.44dc.bc94.41be0f5378315uEqSBEAWa","ca341289.d5ca.4a04.b9b8.a93bee4527daUy48cD50OJ","a019ab15.c699.4dde.b30f.1db35f8d7cf7lKkg67Ph5K"};
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:A",payload1);
	            
	            HashMap<String,String[]> map2 = new HashMap<>();
	            String[] payload2 ={"d6eeeaa4.f24f.4f3e.98f8.97a6985e90fcG487Y29","56904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m","934b0b.8b73.4200.a1a7.c76b01b60eb0EGXT1QnyS3"};
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:B",payload2);
	            
	            HashMap<String,String[]> map3 = new HashMap<>();
	            String[] payload3 ={"","",""};
	            map3.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:C",payload3);
	            
	            HashMap<String,String[]> map4 = new HashMap<>();
	            String[] payload4 ={"  ","  ","  "};
	            map4.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:D",payload4);
	            
	            HashMap<String,String[]> map5 = new HashMap<>();
	            String[] payload5 ={null,null,null};
	            map5.put("null:E",payload5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] performActionUserSingleDP()
	{
        Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            //list1.add("1010");
			list1.add("1101");
            list1.add("Relation follow doesnt exist for user ");
            list1.add("Perform Action on Single User API Failed");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:956904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
			//list1.add("1101");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2059");
            list3.add("user id is invalid");
            list3.add("Invalid UIDX Error message not retrived as expected");
            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:null",list3);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("200");
            list4.add("2059");
            list4.add("user id is invalid");
            list4.add("Invalid UIDX Error message not retrived as expected");
            map4.put("null:956904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null:null",list5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("1010");
	            list1.add("Relation follow exists for user");
	            list1.add("Perform Action on Single User API Failed");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:a414535f.7867.44dc.bc94.41be0f5378315uEqSBEAWa",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("2059");
	            list2.add("user id is invalid");
	            list2.add("Invalid UIDX Error message not retrived as expected");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list2);
	            
	            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
	            ArrayList<String> list3 = new ArrayList<>();
	            list3.add("200");
	            list3.add("2059");
	            list3.add("user id is invalid");
	            list3.add("Invalid UIDX Error message not retrived as expected");
	            map3.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:null",list3);
	            
	            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
	            ArrayList<String> list4 = new ArrayList<>();
	            list4.add("200");
	            list4.add("2059");
	            list4.add("user id is invalid");
	            list4.add("Invalid UIDX Error message not retrived as expected");
	            map4.put("null:a414535f.7867.44dc.bc94.41be0f5378315uEqSBEAWa",list4);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("200");
	            list5.add("2059");
	            list5.add("user id is invalid");
	            list5.add("Null as UIDX Error message not retrived as expected");
	            map5.put("null:null",list5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] performActionObjectBulkDP()
	{
         Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,String[]> map1 = new HashMap<>();
            String[] payload1 ={"4:1c240e35-af1c-4037-a655-bdb5a85174b6","2:45ad2f2d-aae7-457e-a1bf-f3c602e76fd0","2:test-object-1960"};
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:A",payload1);
            
            HashMap<String,String[]> map2 = new HashMap<>();
            String[] payload2 ={"d6eeeaa4.f24f.4f3e.98f8.97a6985e90fcG487Y29","56904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m","934b0b.8b73.4200.a1a7.c76b01b60eb0EGXT1QnyS3"};
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:B",payload2);
            
            HashMap<String,String[]> map3 = new HashMap<>();
            String[] payload3 ={"","",""};
            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:C",payload3);
            
            HashMap<String,String[]> map4 = new HashMap<>();
            String[] payload4 ={"  ","  ","  "};
            map4.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x:D",payload4);
            
            HashMap<String,String[]> map5 = new HashMap<>();
            String[] payload5 ={null,null,null};
            map5.put("null:E",payload5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,String[]> map1 = new HashMap<>();
	            String[] payload1 ={"2:b43eb799-d4f0-4156-8a02-ddd20a16a76d","2:5db6cd6c-e205-47b2-bd9b-c306ace62880","2:6798d364-7d67-495a-9885-56caa420168b"};
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:A",payload1);
	            
	            HashMap<String,String[]> map2 = new HashMap<>();
	            String[] payload2 ={"d6eeeaa4.f24f.4f3e.98f8.97a6985e90fcG487Y29","56904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m","934b0b.8b73.4200.a1a7.c76b01b60eb0EGXT1QnyS3"};
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:B",payload2);
	            
	            HashMap<String,String[]> map3 = new HashMap<>();
	            String[] payload3 ={"","",""};
	            map3.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:C",payload3);
	            
	            HashMap<String,String[]> map4 = new HashMap<>();
	            String[] payload4 ={"  ","  ","  "};
	            map4.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB:D",payload4);
	            
	            HashMap<String,String[]> map5 = new HashMap<>();
	            String[] payload5 ={null,null,null};
	            map5.put("null:E",payload5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] performActionObjectSingleDP()
	{
        Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            //list1.add("1010");
			list1.add("1101");
            //list1.add("Relation like exists for user");
			list1.add("Relation like doesnt exist for user ");
            list1.add("Perform Action on Single Object API Failed");
            map1.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x,4:1c240e35-af1c-4037-a655-bdb5a85174b6",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2059");
            list2.add("user id is invalid");
            list2.add("Invalid UIDX Error message not retrived as expected");
            map2.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x,c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list1);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2059");
            list3.add("user id is invalid");
            list3.add("Invalid UIDX Error message not retrived as expected");
            map3.put("970794db.228e.4133.933e.1574198ef08frA7cPl428x,null",list1);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("200");
            list4.add("2059");
            list4.add("user id is invalid");
            list4.add("Invalid UIDX Error message not retrived as expected");
            map4.put("null,956904c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("2059");
            list5.add("user id is invalid");
            list5.add("Null as UIDX Error message not retrived as expected");
            map5.put("null,null",list5);
            
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			 HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("1010");
	            list1.add("Relation like exists for user");
	            list1.add("Perform Action on Single Object API Failed");
	            map1.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB,2:6798d364-7d67-495a-9885-56caa420168b",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("2059");
	            list2.add("user id is invalid");
	            list2.add("Invalid UIDX Error message not retrived as expected");
	            map2.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB,c7.f264.452a.bcbb.8c27bce8d228xn3YWDb38m",list2);
	            
	            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
	            ArrayList<String> list3 = new ArrayList<>();
	            list3.add("200");
	            list3.add("2059");
	            list3.add("user id is invalid");
	            list3.add("Invalid UIDX Error message not retrived as expected");
	            map3.put("32b5f490.cc50.495e.96a3.cae4c06cc298ciOvG9gGhB,null",list3);
	            
	            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
	            ArrayList<String> list4 = new ArrayList<>();
	            list4.add("200");
	            list4.add("2059");
	            list4.add("user id is invalid");
	            list4.add("Invalid UIDX Error message not retrived as expected");
	            map4.put("null,2:6798d364-7d67-495a-9885-56caa420168b",list4);
	            
	            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
	            ArrayList<String> list5 = new ArrayList<>();
	            list5.add("200");
	            list5.add("2059");
	            list5.add("user id is invalid");
	            list5.add("Null as UIDX Error message not retrived as expected");
	            map5.put("null,null",list5);
	            
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] whoForObjectDP()
	{
        Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){

			HashMap<String,ArrayList<String>> map2 = new HashMap<>();
			ArrayList<String> list2 = new ArrayList<>();
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1016");
            list1.add("Activity source retrieved successfully");
            list1.add("who For an Object API Failed");
            //map1.put("4:1c240e35-af1c-4037-a655-bdb5a85174b6",list1);
			map1.put("4:1c240e35-af1c-4037-a655-bdb5a85174b6",list2);
            

            list2.add("200");
            list2.add("2009");
            list2.add("Source vertex not found");
            list2.add("Invalid Object Error message not retrived as expected");
            map2.put("4:1c240e35-af1c-4037-a655-bdb5a851",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2009");
            list3.add("Source vertex not found");
            list3.add("Null Object Error message not retrived as expected");
            map3.put("4:null",list3);
            
			dataSet = new Object[][]{{map1},{map2},{map3}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1016");
            list1.add("Activity source retrieved successfully");
            list1.add("who For an Object API Failed");
            map1.put("2:b43eb799-d4f0-4156-8a02-ddd20a16a76d",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2009");
            list2.add("Source vertex not found");
            list2.add("Invalid Object Error message not retrived as expected");
            map2.put("4:1c240e35-af1c-4037-a655-bdb5a851",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2009");
            list3.add("Source vertex not found");
            list3.add("Null Object Error message not retrived as expected");
            map3.put("2:null",list3);
            
            dataSet = new Object[][]{{map1},{map2},{map3}};
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] getObjectByTagDB()
	{
        Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){

			HashMap<String,ArrayList<String>> map2 = new HashMap<>();
			ArrayList<String> list2 = new ArrayList<>();
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1043");
            list1.add("Object(s) retrieved successfully");
            list1.add("Object By  article Type Tag for API Failed");
            //map1.put("articletype:jeans",list2);
			map1.put("articletype:jeans",list2);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("200");
            list4.add("1043");
            list4.add("Object(s) retrieved successfully");
            list4.add("Object By brand Tag API Failed");
            //map4.put("brand:nike",list4);
			map4.put("brand:nike",list2);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("1043");
            list5.add("Object(s) retrieved successfully");
            list5.add("Object By theme Tag API Failed");
            //map5.put("theme:women",list5);
			map5.put("theme:women",list2);
            

            list2.add("200");
            list2.add("2009");
            list2.add("Source vertex not found");
            list2.add("Invalid Object Error message not retrived as expected");
            map2.put("null",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2009");
            list3.add("Source vertex not found");
            list3.add("Null Object Error message not retrived as expected");
            map3.put("4:null",list3);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("1043");
            list1.add("Object(s) retrieved successfully");
            list1.add("Object By  article Type Tag for API Failed");
            map1.put("articletype:jeans",list1);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("200");
            list4.add("1043");
            list4.add("Object(s) retrieved successfully");
            list4.add("Object By brand Tag API Failed");
            map4.put("brand:adidas",list4);
            
            HashMap<String,ArrayList<String>> map5 = new HashMap<>();
            ArrayList<String> list5 = new ArrayList<>();
            list5.add("200");
            list5.add("1043");
            list5.add("Object(s) retrieved successfully");
            list5.add("Object By theme Tag API Failed");
            map5.put("theme:women",list5);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("2009");
            list2.add("Source vertex not found");
            list2.add("Invalid Object Error message not retrived as expected");
            map2.put("null",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("200");
            list3.add("2009");
            list3.add("Source vertex not found");
            list3.add("Null Object Error message not retrived as expected");
            map3.put("4:null",list3);
            
			dataSet = new Object[][]{{map1},{map2},{map3},{map4},{map5}};
	
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] getTagsDB()
	{
        Object[][] dataSet = null;
		if(envName.equalsIgnoreCase("fox7")|| envName.equalsIgnoreCase("stage")|| envName.equalsIgnoreCase("sfqa")){
	      
            HashMap<String,ArrayList<String>> map1 = new HashMap<>();
            ArrayList<String> list1 = new ArrayList<>();
            list1.add("200");
            list1.add("3");
            list1.add("GET Tags API Failed");
            map1.put("fashion:0:3",list1);
            
            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
            ArrayList<String> list2 = new ArrayList<>();
            list2.add("200");
            list2.add("0");
            list2.add("Null Tag Error message not retrived as expected");
            map2.put("null:0:3",list2);
            
            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
            ArrayList<String> list3 = new ArrayList<>();
            list3.add("404");
            list3.add("0");
            list3.add("Null Start Index Error message not retrived as expected");
            map3.put("fashion:null:3",list3);
            
            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
            ArrayList<String> list4 = new ArrayList<>();
            list4.add("404");
            list4.add("0");
            list4.add("Null End Index Error message not retrived as expected");
            map4.put("fashion:0:null",list4);
            
			//dataSet = new Object[][]{{map1},{map2},{map3},{map4}};
			dataSet = new Object[][]{{map2},{map3},{map4}};
	
		}
		else if(envName.equalsIgnoreCase("production")){
		
			   HashMap<String,ArrayList<String>> map1 = new HashMap<>();
	            ArrayList<String> list1 = new ArrayList<>();
	            list1.add("200");
	            list1.add("3");
	            list1.add("GET Tags API Failed");
	            map1.put("fashion:0:3",list1);
	            
	            HashMap<String,ArrayList<String>> map2 = new HashMap<>();
	            ArrayList<String> list2 = new ArrayList<>();
	            list2.add("200");
	            list2.add("0");
	            list2.add("Null Tag Error message not retrived as expected");
	            map2.put("null:0:3",list2);
	            
	            HashMap<String,ArrayList<String>> map3 = new HashMap<>();
	            ArrayList<String> list3 = new ArrayList<>();
	            list3.add("404");
	            list3.add("0");
	            list3.add("Null Start Index Error message not retrived as expected");
	            map3.put("fashion:null:3",list3);
	            
	            HashMap<String,ArrayList<String>> map4 = new HashMap<>();
	            ArrayList<String> list4 = new ArrayList<>();
	            list4.add("404");
	            list4.add("0");
	            list4.add("Null End Index Error message not retrived as expected");
	            map4.put("fashion:0:null",list4);
	            
				dataSet = new Object[][]{{map1},{map2},{map3},{map4}};
			
			
		}
	
		return dataSet;
		
	}
	
	@DataProvider
	public Object[][] lgpGetCardTypeFilters(){
		
		Object[][] dataSet = new Object[][]{{"article"},{"product"},{"collections"},{"post.shot"},{"video"},{"banner"},{"carousel-banner"},{"slideshow"},{"split-banner"}};
		
		return dataSet;
	}
	
	@DataProvider
	public Object[][] lgpGetCardTypNegativeeFilters(){
		
		Object[][] dataSet = new Object[][]{{"null"},{"1=1"}};
		
		return dataSet;
	}
	
}
