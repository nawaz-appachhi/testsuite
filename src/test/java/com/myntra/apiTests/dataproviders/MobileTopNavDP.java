package com.myntra.apiTests.dataproviders;

import java.util.List;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.mobileappservices.MobileTopNavServiceHelper;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

/**
 * @author shankara.c
 *
 */
public class MobileTopNavDP extends CommonUtils
{
	public static MobileTopNavServiceHelper  mobileTopNavServiceHelper = new MobileTopNavServiceHelper();
	static Logger log = Logger.getLogger(MobileTopNavDP.class);
	
	@DataProvider
	public Object[][] MobileTopNavDP_getAllMyntraUrlsFromTopNav(ITestContext Context)
	{
		Object[][] dataSet;
		RequestGenerator mobileTopNavServiceReqGen = mobileTopNavServiceHelper.invokeMobileTopNavService();
		String mobileTopNavServiceResponse = mobileTopNavServiceReqGen.respvalidate.returnresponseasstring();
		System.out.println("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		log.info("\nPrinting MobileTopNavService getTopNav API response :\n\n"+mobileTopNavServiceResponse);
		
		AssertJUnit.assertEquals("MobileTopNavService getTopNav API is not working", 200, mobileTopNavServiceReqGen.response.getStatus());
		
		List<String> urls = JsonPath.read(mobileTopNavServiceResponse, "$.data..categories[*].linkUrl.resourceValue");
		
		System.out.println("\nAll TopNav url size : "+urls.size()+"\n");
		log.info("\nAll TopNav url size : "+urls.size()+"\n");
		
		List<String> absoluteurls = Toolbox.getAbsoluteUrlSet(urls);
		dataSet = new Object[absoluteurls.size()][];
		int counter = 0;
		for (String url : absoluteurls)
		{
			System.out.println("Printing the absolute top nav url "+counter+" : " + url);
			log.info("Printing the absolute top nav url : "+counter+". " + url);
			String url1 = url;
			dataSet[counter] = new Object[] { url1 };
			counter++;
		}
		return Toolbox.returnReducedDataSet(dataSet, Context.getIncludedGroups(), dataSet.length, 2);
	}
	
}
