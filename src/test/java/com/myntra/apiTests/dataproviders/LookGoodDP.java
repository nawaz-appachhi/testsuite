package com.myntra.apiTests.dataproviders;

import java.util.List;

import com.myntra.apiTests.portalservices.lookgoodservice.LookGoodHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

/**
 * @author shankara.c
 *
 */
public class LookGoodDP extends LookGoodHelper {
	
	String Env;
	Configuration con = new Configuration("./Data/configuration");
	
	public LookGoodDP() {
		if(System.getenv("ENVIRONMENT")==null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}
	
	@DataProvider     
	public Object[][] lookGoodIdsDataNodeProvider(ITestContext Context) throws ClassNotFoundException{
		
		List<String> lookidFox=lookGoodIdsFox7();
		List<String> lookidFunctional=lookGoodIdsFox7();
		String[] arr1  = {"Fox7",lookidFox.get(0)};
		String[] arr2  = {"Fox7",lookidFox.get(1)};
		String[] arr3  = {"Fox7",lookidFox.get(2)};
		String[] arr4  = {"Fox7",lookidFox.get(3)};
		
		String[] arr5  = {"Functional",lookidFunctional.get(0)};
		String[] arr6  = {"Functional",lookidFunctional.get(1)};
		String[] arr7  = {"Functional",lookidFunctional.get(2)};
		String[] arr8  = {"Functional",lookidFunctional.get(3)};
		
		String[] arr9  = {"Production","1"};
		String[] arr10 = {"Production","2"};
		String[] arr11 = {"production","3"};
		String[] arr12 = {"production","4"};
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12};
		return Toolbox.returnReducedDataSet(Env,dataSet,Context.getIncludedGroups(),2, 3);
	}
	
	@DataProvider     
	public Object[][] lookGoodIdsDataNodeNegetiveIdsProvider(ITestContext Context) {
		
		String[] arr1 = {"Fox7",""};
		String[] arr2 = {"Fox7"," "};
		String[] arr3 = {"Fox7","!@#$%"};
	//	String[] arr4 = {"Fox7",null};
		String[] arr5 = {"Fox7","asfd"};
		String[] arr6 = {"Fox7","JIKEL"};
		String[] arr7 = {"Fox7","JlkImiL"};
		
		String[] arr8 = {"Functional",""};
		String[] arr9 = {"Functional"," "};
		String[] arr10 = {"Functional","!@#$%"};
	//	String[] arr11 = {"Functional",null};
		String[] arr12 = {"Functional","asfd"};
		String[] arr13 = {"Functional","JIKEL"};
		String[] arr14 = {"Functional","JlkImiL"};
		
		
		
		Object[][] dataSet = new Object[][]{arr1, arr2, arr3, arr5, arr6, arr7,
											arr8, arr9, arr10, arr12, arr13, arr14,
											};
		return Toolbox.returnReducedDataSet(Env,dataSet,Context.getIncludedGroups(),2, 3);
	}
	
	@DataProvider     
	public Object[][] LookGoodDP_getLookBookById_verifyResponseDataNodesUsingSchemaValidations(ITestContext Context) throws ClassNotFoundException
	{
		List<String> lookidFox = lookGoodIdsFox7();
		List<String> lookidFunctional = lookGoodIdsFox7();
		
		String[] arr1  = { "Fox7", lookidFox.get(0) };
		String[] arr2  = { "Fox7", lookidFox.get(1) };
		String[] arr3  = { "Fox7", lookidFox.get(2) };
		String[] arr4  = { "Fox7", lookidFox.get(3) };
		
		String[] arr5  = { "Functional", lookidFunctional.get(0) };
		String[] arr6  = { "Functional", lookidFunctional.get(1) };
		String[] arr7  = { "Functional", lookidFunctional.get(2) };
		String[] arr8  = { "Functional", lookidFunctional.get(3) };
		
		String[] arr9  = { "Production", "1" };
		String[] arr10 = { "Production", "2" };
		String[] arr11 = { "production", "3" };
		String[] arr12 = { "production", "4" };
		
		Object[][] dataSet = new Object[][]{ arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12 };
		return Toolbox.returnReducedDataSet(Env, dataSet,Context.getIncludedGroups(), 1, 2);
	}
	
}
