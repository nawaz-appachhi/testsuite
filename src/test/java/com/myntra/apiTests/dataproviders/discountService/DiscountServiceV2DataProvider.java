package com.myntra.apiTests.dataproviders.discountService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import com.myntra.apiTests.portalservices.styleservice.StyleServiceApiHelper;
import org.apache.commons.lang.ArrayUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.apiTests.portalservices.all.discountService.DiscountServiceV2;
import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;


public class DiscountServiceV2DataProvider extends CommonUtils
{
	public long currentTime = System.currentTimeMillis()/1000;
	String startTime = "" , endTime = "";
	Configuration con = new Configuration("./Data/configuration");
	StyleServiceApiHelper styleHelper = new StyleServiceApiHelper();
	Commons commonUtil = new Commons();
	String Env;
	public DiscountServiceV2DataProvider(){
		if(System.getenv("ENVIRONMENT")==null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	List<Integer> styleIdNike = styleHelper.performSeachServiceToGetStyleIds("nike", "15", "true", "false");
	List<Integer> styleIdAdidas = styleHelper.performSeachServiceToGetStyleIds("Adidas", "15", "true", "false");
	List<Integer> styleIdPuma = styleHelper.performSeachServiceToGetStyleIds("Puma", "15", "true", "false");
	List<Integer> styleIdJackets = styleHelper.performSeachServiceToGetStyleIds("Jackets", "15", "true", "false");
	List<Integer> styleIdShoes = styleHelper.performSeachServiceToGetStyleIds("Shoes", "15", "true", "false");
	List<Integer> styleIdJeans = styleHelper.performSeachServiceToGetStyleIds("Jeans", "15", "true", "false");
	List<Integer> styleIdShirts = styleHelper.performSeachServiceToGetStyleIds("Shirts", "15", "true", "false");
	List<Integer> styleIdTshirts = styleHelper.performSeachServiceToGetStyleIds("Tshirts", "15", "true", "false");
	List<Integer> styleIdSunglasses = styleHelper.performSeachServiceToGetStyleIds("Sunglasses", "15", "true", "false");
	List<Integer> styleIdWallets = styleHelper.performSeachServiceToGetStyleIds("Wallets", "15", "true", "false");
	List<Integer> styleIdSportsShoes = styleHelper.performSeachServiceToGetStyleIds("flip-flops", "15", "true", "false");



	@DataProvider
	public Object[][] getDiscountId(ITestContext testContext)
	{
		DiscountServiceV2 ds = new DiscountServiceV2();
		ArrayList discountIds = ds.getDiscountId();
		Object[][] dataSet = new Object[][] { new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
				new String[]{discountIds.get(getRandomNumber(discountIds.size())).toString(), "200"},
//				new String[]{""+0000, "404"},
//				new String[]{""+0, "404"}
				};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 8, 8);
	}

	@DataProvider
	public Object[][] getDSMForStylesDP(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
//				new String[]{getRandomCommaSeperatedStyleIds()}
				};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 9, 9);
	}

	@DataProvider
	public Object[][] getDSMForStylesDPNegative(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{"0, 00, 0001"}, 
				new String[]{"01"},
				new String[]{"00110001000000100"},
				new String[]{"null"},
				new String[]{"0345678"},
		};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}
	
	@DataProvider
	public Object[][] getDSMForStylesDPNegative1(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { 
				
				new String[]{"-1"},
				new String[]{"987234"},				
		};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 10);
	}

	@DataProvider
	public Object[][] getTradeAndDealDiscountsDP(ITestContext testContext)
	{
		String[] pages = {"pdp", "search"};
		String stylesSet1 = getTradeAndDealDiscountHelper(getRandomCommaSeperatedStyleIds());
		String stylesSet2 = getTradeAndDealDiscountHelper(getRandomCommaSeperatedStyleIds());
		String stylesSet3 = getTradeAndDealDiscountHelper(getRandomCommaSeperatedStyleIds());
		String stylesSet4 = getTradeAndDealDiscountHelper(getRandomCommaSeperatedStyleIds());
		String stylesSet5 = getTradeAndDealDiscountHelper(getRandomCommaSeperatedStyleIds());
		String[] arr1 = new String[] { stylesSet1, pages[0] };
		String[] arr2 = new String[] { stylesSet1, pages[1] };
		
		String[] arr3 = new String[] { stylesSet2, pages[0] };
		String[] arr4 = new String[] { stylesSet2, pages[1] };
		
		String[] arr5 = new String[] { stylesSet3, pages[0] };
		String[] arr6 = new String[] { stylesSet3, pages[1] };
		
		String[] arr7 = new String[] { stylesSet4, pages[0] };
		String[] arr8 = new String[] { stylesSet4, pages[1] };
		
		String[] arr9 = new String[] { stylesSet5, pages[0] };
		String[] arr10 = new String[] { stylesSet5, pages[1] };
		
		Object[][] dataSet = new Object[][]{ arr1, 
											 arr2, 
											 arr3, 
											 arr4,
											 arr5,
											 arr6,
											 arr7,
											 arr8,
											 arr9,
											 arr10
		};
		return Toolbox.returnReducedDataSet(dataSet,testContext.getIncludedGroups(), 10, 10);
	}

	@DataProvider
	public Object[][] createFlatDiscountDataProviderV2(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime+500);
		endTime = String.valueOf(currentTime+3600000);
		String set = getOneStyleID();
		System.out.println("single style id = " +set);
		String[] arr1 = {endTime,startTime,generatenumber(), getOneStyleID(),};
		String[] arr2 = {endTime,startTime,generatenumber(), getOneStyleID()};
		String[] arr3 = {endTime,startTime,generatenumber(), getOneStyleID()};
		
       Object [][] dataSet = new Object [][] {arr1 , arr2 , arr3};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 3);
	}
	
	
	
	@DataProvider
	public Object[][] createFlatDiscountDataProviderV2Negative(ITestContext testContext)
	{
		//Invalid StyleId format
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		String set = getOneStyleID();
		System.out.println("single style id = " +set);
		String[] arr1 = {endTime,startTime,generatenumber(),"mkghdjkd"};
		String[] arr3 = {endTime,startTime,generatenumber(), "NULL"};


	

		
       Object [][] dataSet = new Object [][] {arr1,arr3};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] createFlatDiscountDataProviderV2Negative1(ITestContext testContext)
	{
		//Invalid StyleId format
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		String set = getOneStyleID();
		System.out.println("single style id = " +set);
		//Negative Percentage
		String[] arr1 = {endTime,startTime,"-40", getOneStyleID()};
		//Percentage More than 100%
		String[] arr2 = {endTime,startTime,"120", getOneStyleID()};
//		
//		//Same Start and endtime 
		//		String[] arr3 = {String.valueOf(currentTime),String.valueOf(currentTime),"40", getOneStyleID()};
		//Empty STyle id
				
//				String[] arr4 = {endTime,startTime,"100", ""};
//	   //Start time is more than end time
//				String[] arr6 = {String.valueOf(currentTime+360000),String.valueOf(currentTime),"40", getOneStyleID()};

		
		
		
       Object [][] dataSet = new Object [][] {arr1,arr2};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
	}
	
	@DataProvider
	public Object[][] getCurrentDiscountDataProviderv2(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		String set = getOneStyleID();
		System.out.println("single style id = " +set);
		String[] arr1 = {startTime,endTime,getOneStyleID(),"3299",generatenumber()};

		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] createFlatConditionalDiscountV2DataProvider(ITestContext testContext)
	{
		String set = getOneStyleID();
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
	    String[] arr1 = {endTime,startTime,generatenumber(), "500" ,getOneStyleID()};
	    String[] arr2 = {endTime,startTime,generatenumber(), "400" ,getOneStyleID()};
	    String[] arr3 = {endTime,startTime,generatenumber(), "200" ,getOneStyleID()};
	    String[] arr4 = {endTime,startTime,generatenumber(), "100" ,getOneStyleID()};
	    String[] arr5 = {endTime,startTime,generatenumber(), "100" ,getOneStyleID()};
	    String[] arr6 = {endTime,startTime,generatenumber(), "200" ,getOneStyleID()};
	    String[] arr7 = {endTime,startTime,generatenumber(), "100" ,getOneStyleID()};
	    String[] arr8 = {endTime,startTime,generatenumber(), "500" ,getOneStyleID()};
	    String[] arr9 = {endTime,startTime,generatenumber(), "600" ,getOneStyleID()};
	    String[] arr10 = {endTime,startTime,generatenumber(), "300" ,getOneStyleID()};
        Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10};
       
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
				
	}
	
	
	@DataProvider
	public Object[][] createFlatConditionalDiscountAmountV2DataProvider(ITestContext testContext)
	{
		String set = getOneStyleID();
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
	    String[] arr1 = {endTime,startTime,"200", "3000" ,getOneStyleID(),"3"};
	    String[] arr2 = {endTime,startTime,"230", "4000" ,getOneStyleID(),"3"};
	    String[] arr3 = {endTime,startTime,"300", "2000" ,getOneStyleID(),"1"};
	    String[] arr4 = {endTime,startTime,"230", "1000" ,getOneStyleID(),"2"};
	    String[] arr5 = {endTime,startTime,"300", "1000" ,getOneStyleID(),"1"};
	    String[] arr6 = {endTime,startTime,"230", "2000" ,getOneStyleID(),"0"};
	    String[] arr7 = {endTime,startTime,"300", "1000" ,getOneStyleID(),"0"};
	    String[] arr8 = {endTime,startTime,"230", "5000" ,getOneStyleID(),"0"};
	    String[] arr9 = {endTime,startTime,"300", "6000" ,getOneStyleID(),"0"};
	    String[] arr10 = {endTime,startTime,"230", "0" ,getOneStyleID(),"1"};
	    String[] arr11 = {endTime,startTime,"200", "0" ,getOneStyleID(),"2"};
	    String[] arr12 = {endTime,startTime,"230", "0" ,getOneStyleID(),"3"};
	    String[] arr13 = {endTime,startTime,"300", "0" ,getOneStyleID(),"1"};
	    String[] arr14 = {endTime,startTime,"230", "0" ,getOneStyleID(),"2"};
	    String[] arr15 = {endTime,startTime,"300", "0" ,getOneStyleID(),"1"};
	   
        Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10,arr11,arr12,arr13,arr14,arr15};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 15, 15);
				
	}
	
	
	
	
	@DataProvider
	public Object[][] ConditionalDiscountAmtWithCountV2DataProvider(ITestContext testContext)
	{
		String set = getOneStyleID();
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
	    String[] arr1 = {endTime,startTime,"200",getOneStyleID(),"3"};
	    String[] arr2 = {endTime,startTime,"230",getOneStyleID(),"3"};
	    String[] arr3 = {endTime,startTime,"300",getOneStyleID(),"1"};
	    String[] arr4 = {endTime,startTime,"230",getOneStyleID(),"2"};
	    String[] arr5 = {endTime,startTime,"300",getOneStyleID(),"1"};
	    String[] arr6 = {endTime,startTime,"230",getOneStyleID(),"0"};
	    String[] arr7 = {endTime,startTime,"300",getOneStyleID(),"0"};
	    String[] arr8 = {endTime,startTime,"230",getOneStyleID(),"0"};
	    String[] arr9 = {endTime,startTime,"300",getOneStyleID(),"0"};
	    String[] arr10 = {endTime,startTime,"230",getOneStyleID(),"1"};
	    
	   
        Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5,arr6,arr7,arr8,arr9,arr10};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 10, 10);
				
	}
	
	
	
	
	@DataProvider
	public Object[][] ConditionalDiscountPercWithCountV2DataProvider(ITestContext testContext)
	{
		String set = getOneStyleID();
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
	    String[] arr1 = {endTime,startTime,generatenumber(),getOneStyleID(),"3"};
	    String[] arr2 = {endTime,startTime,generatenumber(),getOneStyleID(),"3"};
	    String[] arr3 = {endTime,startTime,generatenumber(),getOneStyleID(),"1"};
	    String[] arr4 = {endTime,startTime,generatenumber(),getOneStyleID(),"2"};
	    String[] arr5 = {endTime,startTime,generatenumber(),getOneStyleID(),"1"};
	    
	   
        Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
				
	}
	
	@DataProvider
	public Object[][] createFlatConditionalDiscountAmountV2DataProvider_Negative(ITestContext testContext)
	{
		String set = getOneStyleID();
		System.out.println("Style id --->>> " + set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		//Maximum Amount Discount
	    String[] arr1 = {endTime,startTime,"0",getOneStyleID(),"0"};
	    //Negative discount
	    String[] arr2 = {endTime,startTime,"200",getOneStyleID(),"-1"};
	    // BuyCount Null
	    String[] arr3 = {endTime,startTime,"0",getOneStyleID(),"1"};
	    //Negative BuyCount 
	    String[] arr4 = {endTime,startTime,"-100 ",getOneStyleID(),"1"};
	    //Null Amount
	    String[] arr5 = {endTime,startTime,"1 ",getOneStyleID(),"0"};




        Object [][] dataSet = new Object [][] {arr1,arr2 , arr3 ,arr4 ,arr5};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 5, 5);
				
	}
	
	@DataProvider
	public Object[][] createFlatConditionalDiscountV2DataProvider_Negative(ITestContext testContext)
	{
		String set = getOneStyleID();
		System.out.println("Style id --->>> " + set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		//Maximum Amount Discount
	    String[] arr1 = {endTime,startTime,"20", "50000" ,getOneStyleID()};
	    //Negative discount
	    String[] arr2 = {endTime,startTime,"21", "-200" ,getOneStyleID()};
	    // BuyCount Null
	    String[] arr3 = {endTime,startTime," ", "200" ,getOneStyleID()};
	    //Negative BuyCount 
	    String[] arr4 = {endTime,startTime,"-1 ", "200" ,getOneStyleID()};
	    //Null Amount
	    String[] arr5 = {endTime,startTime,"1 ", " " ,getOneStyleID()};




        Object [][] dataSet = new Object [][] {arr1,arr2 , arr3 ,arr4 ,arr5};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 3, 2);
				
	}
	
	@DataProvider
	public Object[][] createFreeItemDiscountV2DataProvider(ITestContext testContext)
	{
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		
		String[] arr1 = {endTime,startTime, getOneStyleID() , getOneStyleID()};
		String[] arr2 = {endTime,startTime, getOneStyleID() , getOneStyleID()};


		
		Object [][] dataSet = new Object [][] {arr1,arr2};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 2);
				
	}
	
	@DataProvider
	public Object[][] updateFreeitemsDiscountV2DataProvider(ITestContext testContext)
	{
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		
		String[] arr1 = {endTime,startTime, getOneStyleID() , getOneStyleID(),getOneDiscountID()};
		String[] arr2 = {endTime,startTime,getOneStyleID() , getOneStyleID(),getOneDiscountID()};
		String[] arr3 = {endTime,startTime, getOneStyleID() , getOneStyleID(),getOneDiscountID()};
		String[] arr4 = {endTime,startTime,getOneStyleID() , getOneStyleID(),getOneDiscountID()};


		
		Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 4, 4);
				
	}

	@DataProvider
	public Object[][] createFreeItemDiscountV2DataProvider_Negative1(ITestContext testContext)
	{
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		//Zero BuyAMount
		
		
		//Both style id  and freetime passing Null
		String[] arr2 = {endTime,startTime, "  " , getOneStyleID() };
		String[] arr3 = {endTime,startTime, "  " , " " };




		
		Object [][] dataSet = new Object [][] {arr2,arr3};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
				
	}
	
	@DataProvider
	public Object[][] createFreeItemDiscountV2DataProvider_Negative2(ITestContext testContext)
	{
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		
		//invalid  Freeitem format
		String[] arr1 = {endTime,startTime," Manish" , getOneStyleID()};
		//Both style id  and freetime passing Null
		//FreeItem null
				String[] arr2 = {endTime,startTime,"NULL" , getOneStyleID()};



		
		Object [][] dataSet = new Object [][] {arr1,arr2};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
				
	}
	
	
	@DataProvider
	public Object[][] createBuy1Get2DiscountV2DataProvider(ITestContext testContext)
	{
		
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime+2000);
		System.out.println("Start time is : "+startTime);
		endTime = String.valueOf(currentTime+400000);
		System.out.println("End time is : "+endTime);
		String[] arr1 = {endTime,startTime,getOneStyleID(),getOneStyleID()};
		String[] arr2 = {endTime,startTime,getOneStyleID(),getOneStyleID()};


		
		Object [][] dataSet = new Object [][] {arr1};
		//,arr2
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
				
	}
	
	
	@DataProvider
	public Object[][] createBuy2Get4DiscountV2DataProvider(ITestContext testContext)
	{
		
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime+7000);
		endTime = String.valueOf(currentTime+4000000);
		String[] arr1 = {endTime,startTime,getOneStyleID(),getOneStyleID(),getOneStyleID(),getOneStyleID()};
		String[] arr2 = {endTime,startTime,getOneStyleID(),getOneStyleID(),getOneStyleID(),getOneStyleID()};


		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
				
	}
	@DataProvider
	public Object[][] updateBuy1Get2DiscountV2DataProvider(ITestContext testContext)
	{
		
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
		String[] arr1 = {endTime,startTime,getOneStyleID(),getOneStyleID(),getOneDiscountID()};
		String[] arr2 = {endTime,startTime,getOneStyleID(),getOneStyleID(),getOneDiscountID()};


		
		Object [][] dataSet = new Object [][] {arr1,arr2};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
				
	}
	
	
	@DataProvider
	public Object[][] createBuy1Get2DiscountV2DataProvider_Negative(ITestContext testContext)
	{
		
		
		String set = getOneStyleID();
		System.out.println("Style iD- >>>>> " +set);
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+4000000);
//		String[] arr1 = {endTime,startTime,"1", "0" ,getOneStyleID()};
//		String[] arr2 = {endTime,startTime,"0", "1" ,getOneStyleID()};
//		String[] arr3 = {endTime,startTime,"1", "1" ,getOneStyleID()};
		String[] arr4 = {endTime,startTime,"NULL", getOneStyleID()};
		String[] arr5 = {endTime,startTime,getOneStyleID(),"NULL"};




		
//		Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4};
		Object [][] dataSet = new Object [][] {arr4,arr5};

		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2, 2);
				
	}



	private int getRandomNumber(int size){
		Random ran = new Random();
		return ran.nextInt(size);
	}

	private Object[] sumOfAllStyleIds(){
		int size = styleIdAdidas.size();
		Object[] adidasJackets = ArrayUtils.addAll(styleIdAdidas.toArray(), styleIdJackets.toArray());
		Object[] jeansNike = ArrayUtils.addAll(styleIdJeans.toArray(), styleIdNike.toArray());
		Object[] pumaShirts = ArrayUtils.addAll(styleIdPuma.toArray(), styleIdShirts.toArray());
		Object[] shoesSportsShoes = ArrayUtils.addAll(styleIdShoes.toArray(), styleIdSportsShoes.toArray());
		Object[] tshirtsWallets = ArrayUtils.addAll(styleIdTshirts.toArray(), styleIdWallets.toArray());

		Object[] adidasJacketsJeansNike = ArrayUtils.addAll(adidasJackets, jeansNike);
		Object[] pumaShirtsShoesSportsShoes = ArrayUtils.addAll(pumaShirts, shoesSportsShoes);
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoes = ArrayUtils.addAll(adidasJacketsJeansNike, pumaShirtsShoesSportsShoes);
		Object[] adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets = ArrayUtils.addAll(adidasJacketsJeansNikePumaShirtsShoesSportsShoes, tshirtsWallets);

		return adidasJacketsJeansNikePumaShirtsShoesSportsShoesTshirtsWallets;
	}

	@DataProvider
	public Object[][] removeStyleDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}
	
	@DataProvider
	public Object[][] removeStyleDataProvider_Negative(ITestContext testContext)
	{
		String[] arr1 = {"NULL"};
		String[] arr2 = {"Manish"};
		Object [][] dataSet = new Object [][] {arr1,arr2};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 1);
	}
	
	@DataProvider
	public Object[][] getCurrentDiscountDataProvider(ITestContext testContext)
	{
		Object[][] dataSet = new Object[][] { new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()},
				new String[]{getRandomCommaSeperatedStyleIds()}};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 10);
	}
	
	private String getRandomCommaSeperatedStyleIds(){
		String styleIds = null;
		try {
			styleIds = commonUtil.getCommaSeperatedValuesFromArray(sumOfAllStyleIds(), 30, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return styleIds;
	}
	
	private String getOneStyleID()
	{
		String styleIds = null;
		try {
			styleIds = commonUtil.getCommaSeperatedValuesFromArray(sumOfAllStyleIds(), 1, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return styleIds;
	}
	
	private String getOneDiscountID(){
		DiscountServiceV2 ds = new DiscountServiceV2();
		ArrayList discountIds = ds.getDiscountId();
	//	new String[]{discountIds.get(getRandomNumber}
		
		int index = getRandomNumber (discountIds.size());
		Object value = discountIds.get(index);
		System.out.println("discountId--- >" + value);
		return value.toString();
		
	}
	
	@DataProvider
	public Object[][] GetAllDiscountDataProvider(ITestContext testContext)
	{
		
		String set = getOneStyleID();		
		System.out.println("style id in dp = " + set);
		String[] arr1 = {set};
		String[] arr2 = {getOneStyleID()};
		String[] arr3 = {getOneStyleID()};
		String[] arr4 = {getOneStyleID()};
		String[] arr5 = {getOneStyleID()};
		String[] arr6 = {getOneStyleID()};


		
		
		Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4,arr5,arr6};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 6, 6);
				
	}
	
	@DataProvider
	public Object[][] GetAllDiscountDataProvider_Negative(ITestContext testContext)
	{
		
		//Invalid style id
		String[] arr1 = {"MAnish"};
		
		
		Object [][] dataSet = new Object [][] {arr1};
		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
				
	}

	@DataProvider
	public Object[][] updateDiscountV2DataProvider(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = {endTime ,startTime,getOneStyleID() ,"42" ,getOneDiscountID()};
		String[] arr2 = {endTime ,startTime,getOneStyleID() ,"30" ,getOneDiscountID()};
		String[] arr3 = {endTime ,startTime,getOneStyleID() ,"32" ,getOneDiscountID()};
		String[] arr4 = {endTime ,startTime,getOneStyleID() ,"34" ,getOneDiscountID()};



		Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
		
	}
	
	@DataProvider
	public Object[][] updateFlatConditionalDiscountV2DataProvider(ITestContext testContext)
	{
		startTime = String.valueOf(currentTime);
		endTime = String.valueOf(currentTime+40000);
		String[] arr1 = {endTime ,startTime,"250","5000",getOneStyleID() ,getOneDiscountID()};
		String[] arr2 = {endTime ,startTime,"100","7000",getOneStyleID() ,getOneDiscountID()};
		String[] arr3 = {endTime ,startTime,"350","8000",getOneStyleID() ,getOneDiscountID()};
		String[] arr4 = {endTime ,startTime,"150","9000",getOneStyleID() ,getOneDiscountID()};

		Object [][] dataSet = new Object [][] {arr1,arr2,arr3,arr4};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 4, 4);
		
	}
	
	private String getTradeAndDealDiscountHelper(String CommaSepStyleIds){
		StringBuffer addPrice = new StringBuffer();
		String[] styleIds = CommaSepStyleIds.split(",");
		for(String s : styleIds){
			addPrice.append("\""+s+"\":"+getRandomNumber(10000)+",\n");
		}
		String toReturn = addPrice.toString();
		return "{\n"+toReturn.substring(0, toReturn.length()-2)+"\n}";
	}
	
	 public String generatenumber(){
	     Double number = (Math.floor(Math.random() * 90 + 10));
	     Integer num = number.intValue();
	     
	     String Randomnum = String.valueOf(num);
	      return Randomnum;

		 
	 }
	 
	 // --- 5
	 @DataProvider
		public Object[][] createFlatDiscountDPV2(ITestContext testContext)
		{
			startTime = String.valueOf(currentTime);
			endTime = String.valueOf(currentTime+500);
			String set = getOneStyleID();
			System.out.println("single style id = " +set);
			String[] arr1 = {endTime,startTime,generatenumber(), getOneStyleID(),getOneStyleID(),getOneStyleID(),getOneStyleID()};
//			String[] arr2 = {endTime,startTime,generatenumber(), getOneStyleID()};
//			String[] arr3 = {endTime,startTime,generatenumber(), getOneStyleID()};
			
	       Object [][] dataSet = new Object [][] {arr1 }; //, arr2 , arr3
			
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
		}
	
	 @DataProvider
		public Object[][] getStyleDSM_DPV2(ITestContext testContext)
		{
			startTime = String.valueOf(currentTime);
			endTime = String.valueOf(currentTime+500);
			String set = getOneStyleID();
			System.out.println("single style id = " +set);
			String[] arr1 = {endTime,startTime,generatenumber(), getOneStyleID()};
			//String[] arr1 = {endTime,startTime,generatenumber(), "105337"};
			
//			String[] arr2 = {endTime,startTime,generatenumber(), getOneStyleID()};
//			String[] arr3 = {endTime,startTime,generatenumber(), getOneStyleID()};
			
	       Object [][] dataSet = new Object [][] {arr1 }; //, arr2 , arr3
			
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
		}
	 @DataProvider
		public Object[][] createFlatDiscountDPForMultiStyleidV2(ITestContext testContext)
		{
			startTime = String.valueOf(currentTime);
			endTime = String.valueOf(currentTime+450);
			String set = getOneStyleID();
			System.out.println("single style id = " +set);
			String[] arr1 = {endTime,startTime,generatenumber(), getOneStyleID()};
			
	       Object [][] dataSet = new Object [][] {arr1 };
			
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
		}
	 
	 @DataProvider
		public Object[][] getBulkDiscountDP(ITestContext testContext)
		{
			startTime = String.valueOf(currentTime);
			endTime = String.valueOf(currentTime+450);
			String set = getOneStyleID();
			System.out.println("single style id = " +set);
			String[] arr1 = {endTime,startTime,generatenumber(), "329252"};
			//String[] arr1 = {endTime,startTime,generatenumber(),  getOneStyleID()};
	       Object [][] dataSet = new Object [][] {arr1 };
			
			return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 1, 1);
		}
	 
}


