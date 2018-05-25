package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

public class SPSGetSeller{
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();
	
	/** Get seller by ID **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "getSellerById")
	public void getSellerById (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_SPS, APINAME.GETSELLERBYID,
				init.Configurations,PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	
	
	@DataProvider 
	public Object [][] getSellerById (ITestContext testContext)
	{
		String expected = "status.statusCode==3::"
				+ "status.statusMessage=='Success'::"
				+ "status.statusType=='SUCCESS'";
		String [] arr1 = {"2",expected};
		String [] arr2 = {"3",expected};
		
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
	

}