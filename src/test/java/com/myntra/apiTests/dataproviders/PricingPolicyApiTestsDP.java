package com.myntra.apiTests.dataproviders;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import com.myntra.lordoftherings.Toolbox;


public class PricingPolicyApiTestsDP {
	
	@DataProvider
	public Object[][] getStyleData(ITestContext testContext)
	{
		String [] arr1 = {"1","10","OUTRIGHT"};
		Object [][] dataSet = new Object [][] {arr1};		
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);		
	}
	
	@DataProvider
	public Object[][] createInternalPolicy(ITestContext testContext)
	{
		/*
		 * Before every discount specify whether the respective should be applied for testing with "Y/N".
		 * Data Provider Format [Follow the order mentioned below]
		 * -------------------
		 * Page Number, 
		 * StyleCount,
		 * Operator,
		 * GM_Adj,GM-Matrix Discount,
		 * FC_Adj,FC-Matrix Discount,
		 * STR Adj, STR Discount,
		 * MRP Adj, MRP Discount,
		 * BrokenSKU Adj, Broken SKU Discount,
		 * OldSeasonBoost Adj, OldSeasonBoost, 
		 * BrandInFocus Adj, BrandInFocusBoost,
		 * Merchandise Type
		 * ----------------------
		 * */
			
		  //Give Different Page Numbers and Style Count so that, we don't end up creating same constraints for all the scenarios
		
		
		String[] Scn001 = {"SCN001","1","2","max","Y","-2","Y","-3","Y","-5","Y","-4","Y","-5","Y","-20","Y","-33","OUTRIGHT"};

		
		
		Object[][] dataSet = new Object[][] {Scn001};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}
	
	
	@DataProvider
	public Object[][] createInternalPolicy_IntegrationTestFlow(ITestContext testContext)
	{
		/*
		 * Before every discount specify whether the respective should be applied for testing with "Y/N".
		 * Data Provider Format [Follow the order mentioned below]
		 * -------------------
		 * Page Number, 
		 * StyleCount,
		 * Operator,
		 * GM_Adj,GM-Matrix Discount,
		 * FC_Adj,FC-Matrix Discount,
		 * STR Adj, STR Discount,
		 * MRP Adj, MRP Discount,
		 * BrokenSKU Adj, Broken SKU Discount,
		 * OldSeasonBoost Adj, OldSeasonBoost, 
		 * BrandInFocus Adj, BrandInFocusBoost,
		 * Merchandise Type,
		 * TD Exclusion Style Count [< StyleCount],
		 * TD Range Exclusion Style Count [< StyleCount],
		 * Collection In Focus Count,
		 * Collection In Focus Adjustment
		 * TD Range Adjustment [Should Include TD Range For Current Scenario? Y/N]
		 * Minimum RGM Per Item
		 * ----------------------
		 * */
			
		  //Give Different Page Numbers and Style Count so that, we don't end up creating same constraints for all the scenarios
		
		
		
		String[] Scn001 = {"SCN001","25","30","max","Y","3","Y","3","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","79"};
		String[] Scn002 = {"SCN002","25","20","min","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","39"};
		String[] Scn003 = {"SCN003","25","30","max","Y","4","Y","4","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","49"};
		String[] Scn004 = {"SCN004","25","10","min","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","19"};
		String[] Scn005 = {"SCN005","25","30","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","5","Y","79"};
		String[] Scn006 = {"SCN006","25","20","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","3","3","5","N","50"};
		String[] Scn007 = {"SCN007","25","30","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","0","3","5","N","30"};
		String[] Scn008 = {"SCN008","25","10","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","3","3","5","N","10"};
		String[] Scn009 = {"SCN009","25","30","max","Y","-3","Y","-3","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","30"};
		String[] Scn010 = {"SCN010","25","30","max","Y","-5","Y","-5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","35"};
		String[] Scn011 = {"SCN001","25","30","max","Y","3","Y","3","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","90"};
		String[] Scn012 = {"SCN002","25","20","min","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","85"};
		String[] Scn013 = {"SCN003","25","30","max","Y","4","Y","4","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","75"};
		String[] Scn014 = {"SCN004","25","10","min","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","10"};
		String[] Scn015 = {"SCN005","25","30","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","5","Y","20"};
		String[] Scn016 = {"SCN006","25","20","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","3","3","5","N","60"};
		String[] Scn017 = {"SCN007","25","30","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","0","3","5","N","35"};
		String[] Scn018 = {"SCN008","25","10","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","0","3","3","5","N","25"};
		String[] Scn019 = {"SCN009","25","30","max","Y","-3","Y","-3","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","40"};
		String[] Scn020 = {"SCN010","25","30","max","Y","-5","Y","-5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","75"};
		
		Object[][] dataSet = new Object[][] {Scn001,Scn002,Scn003,Scn004,Scn005,Scn006,Scn007,Scn008,Scn008,Scn010,Scn011,Scn012,Scn013,Scn014,Scn015,Scn016,Scn017,Scn018,Scn019,Scn020};
		//,Scn002,Scn003,Scn004,Scn005,Scn006,Scn007,Scn008,Scn009,Scn010,Scn011,Scn012,Scn013,Scn014,Scn015,Scn016,Scn017,Scn018,Scn019,Scn020
//		,Scn002,Scn003,Scn004,Scn005,Scn006,Scn007,Scn008
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 20,20);
	}

	@DataProvider
	public Object[][] createInternalPolicy_IntegrationTestFlow_Rework(ITestContext testContext)
	{
		/*
		 * Before every discount specify whether the respective should be applied for testing with "Y/N".
		 * Data Provider Format [Follow the order mentioned below]
		 * -------------------
		 * Page Number, 
		 * StyleCount,
		 * Operator,
		 * GM_Adj,GM-Matrix Discount,
		 * FC_Adj,FC-Matrix Discount,
		 * STR Adj, STR Discount,
		 * MRP Adj, MRP Discount,
		 * BrokenSKU Adj, Broken SKU Discount,
		 * OldSeasonBoost Adj, OldSeasonBoost, 
		 * BrandInFocus Adj, BrandInFocusBoost,
		 * Merchandise Type,
		 * TD Exclusion Style Count [< StyleCount],
		 * TD Range Exclusion Style Count [< StyleCount],
		 * Collection In Focus Count,
		 * Collection In Focus Adjustment
		 * TD Range Adjustment [Should Include TD Range For Current Scenario? Y/N]
		 * Minimum RGM Per Item
		 * ----------------------
		 * */
			
		  //Give Different Page Numbers and Style Count so that, we don't end up creating same constraints for all the scenarios
		
		
		String[] Scn001 = {"SCN001","25","10","max","Y","5","Y","5","Y","15","Y","14","Y","25","Y","20","Y","33","OUTRIGHT","3","3","3","10","Y","40"};

		
		
		Object[][] dataSet = new Object[][] {Scn001};
		return Toolbox.returnReducedDataSet(dataSet, testContext.getIncludedGroups(), 2,3);
	}


}





