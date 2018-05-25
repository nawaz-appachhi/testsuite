package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.test.commons.testbase.BaseTest;

public class PPSServiceDP{
	
	static Initialize init = new Initialize("/Data/configuration");
	
	/**
	 * Pass form parameters for cod order 
	 * @param address
	 * @param clientCode
	 * @param cartContext
	 * @param pm
	 * @param profile
	 * @param userGroup
	 * @param mobile
	 * @param firstName
	 * @param address
	 * @param city
	 * @param state
	 * @param zipcode
	 * @param country
	 * @param walletEnabledFlag
	 * @param captchaCode
	 * @return
	 */
	@DataProvider
	public Object[][] formParametersCod(ITestContext testContext)
	{
		String[] arr1 = { "6140304", "responsive", "default","cod","stage.myntra.com","normal",
				"1111111111","PPS Service","PPS Service Automation Myntra address","Bangalore",
				"Karnataka","560045","India","true","246"};
		
	
		
		Object[][] dataSet = new Object[][] { arr1};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}	
	
	@DataProvider
	public static Object[][] formParametersOnline(ITestContext testContext)
	{
				
	// email, pwd, pm, isAutoGCEnable, isAutoGCOrder, isManualGCEnable, isManualGCOrder, isMyntOrder,isLoyalty,isGiftWrap,isTryNBuy, isCancel, isReturn, isExchange
		
		// Online + Auto GC	- Return
		String[] arr1= {"TC1 : Credit/Debit + Auto Gift Card => Return",
				"automationtest@myntra.com","123456", "creditcard","true", "false","false","false","false","false","false","false","false","true","false"};

		// Online + Auto GC	- Cancel
		String[] arr2= {"TC2 : Credit/Debit + Auto Gift Card => Cancel",
				"automationtest@myntra.com","123456", "creditcard","true","false","false","false","false","false","false","false","true","false","false"};
		
		// Online + Auto GC	- Exchange
		String[] arr3= {"TC3 : Credit/Debit + Auto Gift Card => Exchange",
				"automationtest@myntra.com","123456", "creditcard","true","false","false","false","false","false","false","false","false","false","true"};

		// Complete Auto GC Order - Cancel
		String[] arr4= {"TC4 : Complete Auto Gift Card Order => Cancel",
				"automationtest@myntra.com","123456", "cashback","true","true","false","false","false","false","false","false","true","false","false"};

		// Complete Auto GC Order - Return
		String[] arr5= {"TC5 : Complete Auto Gift Card Order => Return",
				"automationtest@myntra.com","123456", "cashback","true","true","false","false","false","false","false","false","false","true","false"};

		// Complete Auto GC Order - Exchange
		String[] arr6= {"TC6 : Complete Auto Gift Card Order => Exchange",
				"automationtest@myntra.com","123456", "cashback","true","true","false","false","false","false","false","false","false","false","true"};

	
	// Online + Manual GC - Return
	 String[] arr7= {"TC7 : Credit/Debit + Manual Gift Card => Return",
			 "manualgctest@myntra.com","123456", "creditcard","false","false","true","false","false","false","false","false","false","true","false"};
	
	 //Online + Manual GC - Cancel
	 String[] arr8= {"TC8 : Credit/Debit + Manual Gift Card => Cancel",
			 "manualgctest@myntra.com","123456", "creditcard","false","false","true","false","false","false","false","false","true","false","false"};

	//Online + Manual GC - Exchange 
	 String[] arr9= {"TC9 : Credit/Debit + Manual Gift Card => Exchange",
			 "manualgctest@myntra.com","123456", "creditcard","false","false","true","false","false","false","false","false","false","false","true"};

	// Complete Manual GC order - Return
	 String[] arr10= {"TC10 : Complete Manual Gift Card Order => Return",
			 "manualgctest@myntra.com","123456", "giftcard","false","false","true","true","false","false","false","false","false","true","false"};

	// Complete Manual GC order - Cancel
	 String[] arr11= {"TC11 : Complete Manual Gift Card Order => Cancel",
			 "manualgctest@myntra.com","123456", "giftcard","false","false","true","true","false","false","false","false","true","false","false"};

	// Complete Manual GC order - Exchange
	 String[] arr12= {"TC12 : Complete Manual Gift Card Order => Exchange",
			 "manualgctest@myntra.com","123456", "giftcard","false","false","true","true","false","false","false","false","false","false","true"};

	// email, pwd, pm, isAutoGCEnable, isAutoGCOrder, autoGCAmount, isManualGCEnable, isManualGCOrder, isMyntOrder,isLoyalty,isGiftWrap,isTryNBuy, isCancel, isReturn, isExchange

	// Online + Mynt  - Return
	 String[] arr13= {"TC13 : Credit/Debit + Mynt Order => Return",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","false","false","false","false","true","false"};
	 
	// Online + Mynt - Cancel
	 String[] arr14= {"TC14 : Credit/Debit + Mynt Order => Cancel",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","false","false","false","true","false","false"};

	// Online + LP - Cancel
	 String[] arr15= {"TC15 : Credit/Debit + Loyalty Point => Cancel",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "false","true","false","false","true","false","false"};

	// Online + LP - Return
	 String[] arr16= {"TC16 : Credit/Debit + Loyalty Point => Return",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "false","true","false","false","false","true","false"};

	// Online + LP + Mynt  - Cancel
	 String[] arr17= {"TC17 : Credit/Debit + Loyalty Point + Mynt => Cancel",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","false","false","true","false","false"};
	 
	// Online + LP + Mynt  - Return
	 String[] arr18= {"TC18 : Credit/Debit + Loyalty Point + Mynt => Return",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","false","false","false","true","false"};

	// Online + LP + Mynt + GiftWrap - Return
	 String[] arr19= {"TC19 : Credit/Debit + Loyalty Point + Mynt + GiftWrap => Return",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","true","false","false","true","false"};

	// Online + LP + Mynt + GiftWrap  - Cancel
	 String[] arr20= {"TC20 : Credit/Debit + Loyalty Point + Mynt + Giftwrap => Cancel",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","true","false","true","false","false"};
	 
	 //cod + LP + Mynt - Cancel
	 String[] arr21= {"TC21 : Cod + Loyalty Point + Mynt => Cancel",
			 "automationtest@myntra.com","123456", "cod","false","false","false","false", "true","true","false","false","true","false","false"};

	 //cod + LP + Mynt - Return
	 String[] arr22= {"TC22 : Cod + Loyalty Point + Mynt => Return",
			 "automationtest@myntra.com","123456", "cod","false","false","false","false", "true","true","false","false","false","true","false"};

	// Online + LP + Mynt + GiftWrap + TryNBuy - Return
	 String[] arr23= {"TC23 : Credit/Debit + Loyalty Point + Mynt + GiftWrap + TryNBuy => Return",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","true","true","false","true","false"};
	 
	// Online + LP + Mynt + GiftWrap + TryNBuy - Cancel
	 String[] arr24= {"TC24 : Credit/Debit + Loyalty Point + Mynt + GiftWrap + TryNBuy => Cancel",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","true","true","true","false","false"};
	 
	// email, pwd, pm, isAutoGCEnable, autoGCAmount, isManualGCEnable, isManualGCOrder, isMyntOrder,isLoyalty,isGiftWrap,isTryNBuy, isCancel, isReturn, isExchange

	 // Online - Exchange
	 String[] arr25= {"TC25 : Credit/Debit => Exchange",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "false","false","false","false","false","false","true"};

	// Online + LP + Mynt + GiftWrap + TryNBuy - Exchange
	 String[] arr26= {"TC26 : Credit/Debit + Loyalty Point + Mynt + GiftWrap + TryNBuy => Exchange",
			 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "true","true","true","true","false","false","true"};

	 //cod + LP + Mynt - Exchange
	 String[] arr27= {"TC27 : Cod + Loyalty Point + Mynt => Exchange",
			 "automationtest@myntra.com","123456", "cod","false","false","false","false", "true","true","false","false","false","false","true"};
	 
	// Online + LP + Mynt + GiftWrap + TryNBuy - Cancel
	 String[] arr28= {"TC28 : Credit/Debit + Loyalty Point + Mynt + AutoGC + GiftWrap + TryNBuy=> Cancel",
				 "automationtest@myntra.com","123456", "creditcard","true","false","false","false", "true","true","true","true","true","false","false"};
	//Online - Cancel
	String[] arr29= {"TC29 : Online=> Cancel",
					 "automationtest@myntra.com","123456", "creditcard","false","false","false","false", "false","false","false","false","true","false","false"};
			
	//Online - Cancel
	String[] arr30= {"TC30 : COD=> Cancel",
					 "automationtest@myntra.com","123456", "cod","false","false","false","false", "false","false","false","false","true","false","false"};


	// Online + LP + Mynt + GiftWrap + TryNBuy - Cancel
	String[] arr31= {"TC31 : Credit/Debit + Loyalty Point + Mynt + ManualGC + GiftWrap + TryNBuy=> Cancel",
				"manualgctest@myntra.com","123456", "creditcard","false","false","true","false", "true","true","true","true","true","false","false"};

	// COD + Loyalty Point + Mynt + ManualGC + TryNBuy=> Cancel
	String[] arr32= {"TC32 : COD + Loyalty Point + Mynt + ManualGC => Cancel",
				"manualgctest@myntra.com","123456", "cod","false","false","true","false", "true","true","false","false","true","false","false"};

	// COD + Loyalty Point + Mynt + AutoGC + TryNBuy=> Cancel
	String[] arr33= {"TC33 : COD + Loyalty Point + Mynt + AutoGC => Cancel",
				"automationtest@myntra.com","123456", "cod","true","false","false","false", "true","true","false","false","true","false","false"};

	//Online - Return
	String[] arr34= {"TC34 : Online=> Return",
				"automationtest@myntra.com","123456", "creditcard","false","false","false","false", "false","false","false","false","false","true","false"};

	//Online - Return
	String[] arr35= {"TC35 : COD=> Return",
				"automationtest@myntra.com","123456", "cod","false","false","false","false", "false","false","false","false","false","true","false"};



/*	 String[] arr15= {"email:automationtest@myntra.com","pwd:123456", "pm:creditcard","isAutoGCEnable:false","autoGCAmount:498",
			 "isManualGCEnable:false","isManualGCOrder:false", "isMyntOrder:true","isLoyalty:true","isGiftWrap:true",
			 "isTryNBuy:true","isCancel:true","isReturn:false"};
*/		
/*		String[] arr1= {"automationtest@myntra.com","123456","false", "creditcard","responsive","default","stage.myntra.com",
				"normal","true","false","5415679107547290","Test card","07","22","123","false","true","6991201055068040","225248","3000","1"};
*/		
	 
	//    Object[][] dataSet = new Object[][] { arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12,
	// arr13,arr14,arr15,arr16,arr17,arr18,arr19,arr20,arr21,arr22,arr23,arr24,arr25,arr26,arr27};

	//	Object[][] dataSet = new Object[][] { arr7,arr10,arr6,arr1};
	//	Object[][] dataSet = new Object[][] { arr1,arr7,arr6,arr10};
		/*Object[][] dataSet = new Object[][] { arr1,arr7,arr2,arr8,arr3,arr9,arr6,arr10,arr5,arr11,arr4,arr12,
			arr13,arr14,arr15,arr16,arr17,arr18,arr19,arr20,arr21,arr22,arr23,arr24,arr25,arr26,arr27,arr28,
				arr29,arr30, arr31, arr32, arr33, arr34, arr35};*/
	 //Object[][] dataSet = new Object[][] {arr4, arr5,arr6,arr10,arr11,arr12,arr28};
	 Object[][] dataSet = new Object[][] {arr24,arr29,arr30};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(),30,30);
	}	

	
	@DataProvider
	public static Object[][] gcPurchase(ITestContext testContext)
	{
		// Online + Auto GC	- Return
				String[] arr1= {"TC : 500 INR Giftcard Purchase",
						"automationtest@myntra.com","123456", "creditcard"};
				Object[][] dataSet = new Object[][] { arr1};
				return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 1, 1);
	}
	
	
	
	
}
