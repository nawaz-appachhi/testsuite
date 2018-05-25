package com.myntra.apiTests.erpservices.partners.Tests;
/*
 * Author Shubham gupta 
 */
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;

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

public class GetPartnerConfiguration {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();
	
	/** Get partner configuration **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "getPartnerConfiguration")
	public void getPartnerConfiguration (String sellerId, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETPARTNERCONFIGURATION,
				init.Configurations, PayloadType.JSON, new String[] { sellerId},  PayloadType.JSON);
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
	public Object [][] getPartnerConfiguration (ITestContext testContext)
	{
		String expected = "partnerConfigurationResponse.status.statusCode==1653::"
				+ "partnerConfigurationResponse.status.statusMessage=='Partner configuration retrieved successfully'::"
				+ "partnerConfigurationResponse.status.statusType=='SUCCESS'";
		String [] arr1 = {"1",expected};
		String [] arr2 = {"3",expected};
		String [] arr3 = {"5",expected};
		
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return Toolbox.returnReducedDataSet(dataSet,
			testContext.getIncludedGroups(), 5, 6);
	}
}
