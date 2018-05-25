package com.myntra.apiTests.dataproviders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import com.myntra.apiTests.portalservices.tagService.tagServiceHelper;
import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;
import com.myntra.test.commons.testbase.BaseTest;


public class CouponServiceDataProvider extends BaseTest{
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	Commons commonUtil = new Commons();
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	public long currentTime = System.currentTimeMillis()/1000;
	String startTime = "" , endTime = "" , endTime1 = "" ,endTime2 = "";
	tagServiceHelper helper = new tagServiceHelper();


	
	public CouponServiceDataProvider() {
		if(System.getenv("ENVIRONMENT")==null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	List<Integer> styleIdRoadster = styleHelper.performSeachServiceToGetStyleIds("Roadster", "15", "true", "false");
	List<Integer> styleIdKurtas = styleHelper.performSeachServiceToGetStyleIds("kurtas", "15", "true", "false");
	
	@DataProvider
	public Object[][] getAllCouponsDP(ITestContext testContext)
	{
		String[] arr1 = { "vasvani11@gmail.com"};
		String[] arr2 = { "vivek.vasvani@myntra.com"};
		String[] arr3 = { "unregistereduser@hotmail.com" };
		String[] arr4 = { "nonregistereduser@hotmail.com" };
		String[] arr5 = { " " };
		/*String[] arr6 = { };
		String[] arr7 = { };
		String[] arr8 = { };
		String[] arr9 = { };
		String[] arr10 = { }; */
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5 }; //,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] getCoupon(ITestContext testContext)
	{
		String[] arr1 = { "myntraemp20"};
		
		Object[][] dataSet = new Object[][] {arr1}; //,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 2);
	}
	
	
	
	@DataProvider
	public Object[][] getExpiredCouponsDP(ITestContext testContext)
	{
		String[] arr1 = { "vasvani11@gmail.com" };
		String[] arr2 = { "vivek.vasvani@gmail.com" };
		String[] arr3 = { "invaliduser_abcd@rediffmail.com" };
		String[] arr4 = { "10' or '1=1" };

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}
	
	
	
	
	
	
	@DataProvider
	public Object[][] ExpiryBulkUpdateCoupon(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+500000 );
		String[] arr1 = { "groupName","MAYIPL8","expire",startTime,"0","500"};
	

		Object[][] dataSet = new Object[][] { arr1 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] StartdateBulkUpdateCoupon(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+1000 );
		String[] arr1 = { "groupName","MAYIPL8","startdate",startTime,"0","20"};
	

		Object[][] dataSet = new Object[][] { arr1 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] CouponTypeBulkUpdateCoupon(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+1000 );
		String[] arr1 = { "groupName","MAYIPL8","couponType","percentage","mrpAmount","0","mrpPercentage","40","0","20"};
//		String[] arr2 = { "groupName","couponbulk","couponType","dual","mrpAmount","10","mrpPercentage","60","0","5000"};

	

		Object[][] dataSet = new Object[][] {arr1 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] StatusBulkUpdateCoupon(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+1000 );
		String[] arr1 = { "groupName","MAYIPL8","status","D","0","500"};
		String[] arr2 = { "groupName","MAYIPL8","status","U","0","500"};

		String[] arr3 = { "groupName","MAYIPL8","status","A","0","500"};


//		String[] arr2 = { "groupName","couponbulk","couponType","dual","mrpAmount","10","mrpPercentage","60","0","5000"};


		Object[][] dataSet = new Object[][] {arr1,arr2,arr3 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	
	
	@DataProvider
	public Object[][] BulkUpdateLock(ITestContext testContext)
	{
		
		startTime = String.valueOf(currentTime+1000 );
		String[] arr1 = { "groupName","MAYIPL8","expire",startTime};
		


//		String[] arr2 = { "groupName","couponbulk","couponType","dual","mrpAmount","10","mrpPercentage","60","0","5000"};

	

		Object[][] dataSet = new Object[][] {arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	
	@DataProvider
	public Object[][] BulkUpdateMoreThan2Query(ITestContext testContext)
	{
		
		endTime = String.valueOf(currentTime+100000 );
		endTime1 = String.valueOf(currentTime+200000 );

		endTime2 = String.valueOf(currentTime+300000 );

		String[] arr1 = { "groupName","MAYIPL8","expire",endTime,endTime1,endTime2,"upload101","upload102"};
	

		Object[][] dataSet = new Object[][] { arr1 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
	@DataProvider
	public Object[][] IntenCouponScore(ITestContext testContext)
	{  
			
		String[] arr1 = { generateRandomCoupon(),"7" };
		String[] arr2 = { generateRandomCoupon(),"6"};
		String[] arr3 = { generateRandomCoupon(),"4" };
		String[] arr4 = { generateRandomCoupon(),"3" };


		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4 };//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}
	
	
	
	@DataProvider
	public Object[][] IntenCouponScoreNegatice(ITestContext testContext)
	{  
			
		String[] arr1 = { "Negative1","7" };
		String[] arr2 = { "Negative2","6"};
//		String[] arr3 = { " ","4" };
		


		Object[][] dataSet = new Object[][] { arr1,arr2};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 4, 4);
	}
	
	@DataProvider
	public Object[][] getCouponname(ITestContext testContext)
	{
		String[] arr1 = {"vat3290"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] getSpecifciFlashCoupon(ITestContext testContext)
	{
		String[] arr1 = {"intelli4","newintelli2@myntra.com","newintelli2@myntra.com"};
		String[] arr2 = {"intelli2","newintelli4@myntra.com","newintelli4@myntra.com"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	@DataProvider
	public Object[][] getFlashCouponname(ITestContext testContext)
	{
		String[] arr1 = {"intelli2","newintelli2@myntra.com","newintelli2@myntra.com"};
		String[] arr2 = {"intelli2","newintelli2@myntra.com","newintelli2@myntra.com"};
		

		Object[][] dataSet = new Object[][] { arr1,arr2};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	
	@DataProvider
	public Object[][] fLasMPDCoupon(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = {startTime,endTime,generateRandomCoupon(),startTime,"30","40","Test","max_percentage_dual"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	
	}
	
	private String generateRandomCoupon()
	{
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
		int randomLimitedInt = leftLimit + (int) 
		(new Random().nextFloat() * (rightLimit - leftLimit));
		buffer.append((char) randomLimitedInt);
		}
		String generatedString = buffer.toString();
		System.out.println(generatedString);
		return generatedString;
	}
	@DataProvider
	public Object[][] getCouponnameForDifferentUser(ITestContext testContext)
	{
		String[] arr1 = {"DifferentUserCoupon"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	@DataProvider
	public Object[][] GetIntent(ITestContext testContext)
	{
		String[] arr1 = {"7"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] DeleteIntent(ITestContext testContext)
	{
		String[] arr1 = {"1"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] DeleteIntentNegative(ITestContext testContext)
	{
		
		String[] arr1 = {"-1"};
		String[] arr2 = {"0"};
		String[] arr3 ={"11"};
		
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider
	public Object[][] DeleteIntentNegative1(ITestContext testContext)
	{
		
		String[] arr4 = {"cool"};
		String[] arr5 = {"ok"};
		
		
		Object[][] dataSet = new Object[][] { arr4,arr5};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] DeleteIntentNegative2(ITestContext testContext)
	{
		
	
		String[] arr4 = {""};
		
		
		Object[][] dataSet = new Object[][] { arr4};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	@DataProvider
	public Object[][] GetIntentNegative(ITestContext testContext)
	{
		String[] arr1 = {"-1"};
		String[] arr2 = {"0"};
		String[] arr3 ={"11"};
		

		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	
	@DataProvider
	public Object[][] GetIntentNegative1(ITestContext testContext)
	{
		String[] arr1 = {"cool"};
		String[] arr2 = {"ok"};
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] GetIntentNegative2(ITestContext testContext)
	{
		String[] arr1 = {""};
		String[] arr2 = {""};
		
		

		Object[][] dataSet = new Object[][] { arr1,arr2};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] getMinimumAmtCoupon(ITestContext testContext)
	{
		String[] arr1 = {"MinimumAMountCoupon"};
		

		Object[][] dataSet = new Object[][] {arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getMultipleItemCoupon(ITestContext testContext)
	{
		String[] arr1 = {"MultipleItemCoupon"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	@DataProvider
	public Object[][] getExpiredCoupons(ITestContext testContext)
	{
		String[] arr1 = {"expiredcoupon23"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getInvalidCoupon(ITestContext testContext)
	{
		String[] arr1 = {"OK"};
//		String[] arr2 = {"Invalid"};

		
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getLockedCoupon(ITestContext testContext)
	{
		String[] arr1 = {"lockedcoupon"};
		

		Object[][] dataSet = new Object[][] { arr1};//,arr5,arr6,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	@DataProvider
	public Object[][] getFestivalCouponsForOrderDP(ITestContext testContext)
	{
		String[] arr1 = { "10044" };
		String[] arr2 = { "30980116" };
		String[] arr3 = { "30980118" };
		String[] arr4 = { "1" };
		String[] arr5 = { "10101010" };
		String[] arr6 = { "889975" };
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3, arr4,arr5,arr6 };//,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 6, 6);
	}
	
	@DataProvider
	public Object[][] modifyDualCoupon(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		String[] arr1 = { startTime,startTime,"couponTesting","40","400","coupontesting@myntra.com"};
		String[] arr2 = { startTime,startTime,"couponTesting","20","200","coupontesting@myntra.com"};
		String[] arr3 = { startTime,startTime,"couponTesting","30","400","coupontesting@myntra.com" };
	
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};//,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	
	@DataProvider
	public Object[][] modifyPercentageCoupon(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		String[] arr1 = { startTime,startTime,"couponTesting","40","coupontesting@myntra.com"};
		String[] arr2 = { startTime,startTime,"couponTesting","20","coupontesting@myntra.com"};
		String[] arr3 = { startTime,startTime,"couponTesting","30","coupontesting@myntra.com" };
	
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};//,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] modifyDualPercentageCoupon(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		String[] arr1 = { startTime,startTime,"couponTesting","80","90","coupontesting@myntra.com"};
		String[] arr2 = { startTime,startTime,"couponTesting","82","91","coupontesting@myntra.com"};
		String[] arr3 = { startTime,startTime,"couponTesting","84","92","coupontesting@myntra.com" };
	
		
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3};//,arr7,arr8,arr9,arr10  };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 3, 3);
	}
	
	@DataProvider
	public Object[][] couponUsageHistoryDP(ITestContext testContext)
	{
		String[] arr1 = { "vivek.vasvani@myntra.com" };
		String[] arr2 = { "vasvani11@gmail.com" };
		String[] arr3 = { "nonRegisteredUserCouponApis@myntra.com" };
		String[] arr4 = { "newRegisteredUser@myntra.com" };
		String[] arr5 = { "SignUp100_W6Uk0CgEH9CHJX8@hotmail.com" };
		String[] arr6 = { "SignUp100_XH3b1SKWA0vK3MX@rediffmail.com" };
		String[] arr7 = { "SignUp102_KbiUnOVvb2CD9OR@gmail.com" };
		String[] arr8 = { "SignUp105_8bwlE2bET9RnXBj@yahoo.com" };
		String[] arr9 = { "SignUp105_p15wY6totzOKZX3@gmail.com" };
		String[] arr10 = { "SignUp107_9Yg72jSSkGN0bsf@myntra.com" };
		String[] arr11 = { "SignUp123_JK7i9DA02ajW1cD@rediffmail.com" };
		String[] arr12 = { "SignUp133_8u28ANTiGTUEpId@hotmail.com" };
		String[] arr13 = { "SignUp157_IJ5n7U6TE6fi00z@hotmail.com" };
		String[] arr14 = { "SignUp188_Nx1gqlEPuuAjXAq@myntra.com" };
		String[] arr15 = { "checkoutservicetest420@myntra.com" };
		String[] arr16 = { "checkoutservicetest421@myntra.com" };
		String[] arr17 = { "checkoutservicetest200@myntra.com" };
		String[] arr18 = { "checkoutservicetest422@myntra.com" };
		String[] arr19 = { "mohit.jain@myntra.com" };
		String[] arr20 = { "ashish.bajpai@myntra.com" };
		

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10,
											  arr11, arr12, arr13, arr14, arr15, arr16, arr17, arr18, arr19, arr20
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 20, 20);
	}
	
	@DataProvider
	public Object[][] getCouponsAllActiveExpiredDP(ITestContext testContext)
	{
		String[] arr1 = { "vivek.vasvani@myntra.com", "" };
		String[] arr2 = { "vivek.vasvani@myntra.com", "?status=active" };
		String[] arr3 = { "vivek.vasvani@myntra.com", "?status=expired" };
		String[] arr4 = { "vivek.vasvani@myntra.com", "?status=all" };
		String[] arr5 = { "nonRegisteredUserCouponApis@myntra.com", "" };
		String[] arr6 = { "nonRegisteredUserCouponApis@myntra.com", "?status=active" };
		String[] arr7 = { "nonRegisteredUserCouponApis@myntra.com", "?status=expired" };
		String[] arr8 = { "nonRegisteredUserCouponApis@myntra.com", "?status=all" };
		String[] arr9 = { "newRegisteredUser@myntra.com", "" };
		String[] arr10 = { "newRegisteredUser@myntra.com", "?status=active" };
		String[] arr11 = { "newRegisteredUser@myntra.com", "?status=expired" };
		String[] arr12 = { "nonRenewRegisteredUsergisteredUserCouponApis@myntra.com", "?status=all" };
		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12 };
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 12, 12);
	}
	
	@DataProvider
	public Object[][] getCouponsAllActiveExpiredMixDP(ITestContext testContext)
	{
		String[] arr1 = { "vivek.vasvani@myntra.com"};
		String[] arr2 = { "nonRegisteredUserCouponApis@myntra.com"};
		String[] arr3 = { "newRegisteredUser@myntra.com"};
		String[] arr4 = { "maninder.chhabra@myntra.com"};
		String[] arr5 = { "vasvani11@gmail.com"};

		Object[][] dataSet = new Object[][] { arr1,arr2};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),5, 5);
	}
	
	@DataProvider
	public Object[][] getStyleidForCoupon(ITestContext testContext)
	{
		String[] arr1 = {helper.getOneStyleID()};
		String[] arr2 = {helper.getOneStyleID()};
		String[] arr3 = {helper.getOneStyleID()};
		String[] arr4 = {helper.getOneStyleID()};
		String[] arr5 = {helper.getOneStyleID()};
		String[] arr6 = {helper.getOneStyleID()};
		String[] arr7 = {helper.getOneStyleID()};
		String[] arr8 = {helper.getOneStyleID()};
		
		String[] style1={"185358"};//185358 198939 306502 191311 189709 274728 162210 80242
		String[] style2={"198939"};
		String[] style3={"353811"};
		String[] style4={"306502"};
		String[] style5={"315401"};
		String[] style6={"80242"};
		String[] style7={"189709"};
		String[] style8={"274728"};
		String[] style9={"162210"};
		Object[][] dataSet = new Object[][] { style1,style2,style3,style4,style5,style6,style7,style8,style9};

//		Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8};
		for (int i=0; i<=arr1.length-1;i++){
			System.out.println("@@@@");
			System.out.println(arr1[i]);
		}
//		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 8, 8);
	}
	
	@DataProvider
	public Object[][] isCouponvalid(ITestContext testContext)
	{
		String[] arr1 = {"couponTesting","manishkumar.gupta@myntra.com","online"};
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	public String getOneStyleID() {
		String styleIds = null;
		try {
			styleIds = Commons.getCommaSeperatedValuesFromArray(
					sumOfAllStyleIds(), 1, false);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return styleIds;
	}
	private Object[] sumOfAllStyleIds(){
		int size = styleIdRoadster.size();
		Object[] roadster = ArrayUtils.addAll(styleIdRoadster.toArray(), styleIdRoadster.toArray());
		Object[] kurtas = ArrayUtils.addAll(styleIdKurtas.toArray(), styleIdKurtas.toArray());
		

		Object[] styleIdKurtasRoadster = ArrayUtils.addAll(roadster, kurtas);
		
       return styleIdKurtasRoadster;
	}

	@DataProvider()

	public static Object[][] couponGroup() {


		return new Object[][] { { "swatMNT",new String[]{"SWAT12","swatn11"}}};

	}
	@DataProvider()

	public static Object[][] brandExclusinSuccessMsg() {


		return new Object[][] { {"Brand/article fetched from global exclusion successfully"}};

	}
	@DataProvider()

	public static Object[][] DELETEbrandExclusinSuccessMsg() {


		return new Object[][] { {"Brand/article deleted from global exclusion successfully"}};

	}
	@DataProvider()

	public static Object[][] CreatebrandExclusinSuccessMsg() {


		return new Object[][] { {"Brand/article added to global exclusion successfully",new String[]{"Arrow"}}};

	}
	@DataProvider()

	public static Object[][] styleInclusionExclusion(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/styleinclusionexclusion.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][3];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);
//		return new Object[][] { {"Style Inclusion Exclusion Successful",new String[]{"swatMNT","5"}}};

	}
	@DataProvider
	public Object[][] lockUserCoupon()
	{
		String[] arr1 = {"SWAT12","4d4043c7.4ba8.43f2.b953.b1432105eda0D7fOj5MIwo","Locked the user coupon:SWAT12"};

		Object[][] dataSet = new Object[][] { arr1};
		return dataSet;
	}
	@DataProvider
	public Object[][] unlockUserCoupon()
	{
		String[] arr1 = {"SWAT12","4d4043c7.4ba8.43f2.b953.b1432105eda0D7fOj5MIwo","Un locked the user coupon:SWAT12"};

		Object[][] dataSet = new Object[][] { arr1};
		return dataSet;
	}
	
	@DataProvider
	public Object[][] createcoupon(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/createcoupon.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][39];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] updatecoupon(ITestContext testContext) throws IOException {
		
		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/updatecoupon.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][3];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);
//		String[] arr1 = {"SWAT12","4d4043c7.4ba8.43f2.b953.b1432105eda0D7fOj5MIwo","SUCCESS"};
//
//		Object[][] dataSet = new Object[][] { arr1};
//		return dataSet;
	}
	
	@DataProvider()

	public static Object[][] getBinRange() {


		return new Object[][] { {"swatMNT"}};

	}
	
	@DataProvider
	public Object[][] createcoupontemplate(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/createcoupontemplate.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][34];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] updatecoupontemplate(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/updatecoupontemplate.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][34];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] listcoupontemplate(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/listcoupontemplate.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][2];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	@DataProvider
	public Object[][] getcoupontemplatebyattribute(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/getcoupontemplatebyattribute.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][8];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] createcoupongroup(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/createcoupongroup.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][8];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] updatecoupongroup(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/updatecoupongroup.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][2];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] applymyntcoupon(ITestContext testContext) throws IOException {

		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/applymyntcoupon.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][16];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);

	}
	
	@DataProvider
	public Object[][] schemaData(ITestContext testContext) throws IOException {
		String line="";
		int i =0;
		String[] arr;
		String filePath="./Data/payloads/CSV/applymyntcoupon.csv";
		int lineCount =(int) Files.lines(Paths.get(filePath)).count();
		
		BufferedReader br = new BufferedReader(new FileReader(filePath));
        Object[][] dataSet = new Object[lineCount][16];//n: no. of line in csv and m: column
        
        /*
         * this will read the csv and trim each element's white space
        */
        while ((line = br.readLine()) != null) {
        	arr= line.split(",");
        	for (int x = 0; x < arr.length; x++)
        		arr[x] = arr[x].trim();
        	for(int j=0; j<arr.length;j++){
        		if (arr[j] ==null)
        				break;
        		dataSet[i][j]=arr[j];
        		
        	}
        	i++;
        	
        }
        
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), lineCount, lineCount);
	}
}
