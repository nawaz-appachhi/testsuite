package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import java.io.IOException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.portalservices.lgpServe.feed.FeedObjectHelper;
import com.myntra.apiTests.utility.Runtime;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class BannerSyncTest {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(BannerSyncTest.class);
	static APIUtilities apiUtil = new APIUtilities();
	static APIUtilities utilities = new APIUtilities();
	static FeedObjectHelper feedHelper= new FeedObjectHelper();
	


	
	@Test (groups ="Sanity",priority=1)
	public void generateTestBannersTest() throws IOException
	{
		MyntraService getservice = Myntra.getService(ServiceType.LGP_CONTOUR, APINAME.GENERATETESTBANNERS, init.Configurations);
		System.out.println(getservice.URL);
		RequestGenerator feedreq = new RequestGenerator(getservice);
		String response = feedreq.returnresponseasstring();
		System.out.println(response);
		Assert.assertEquals(200, feedreq.response.getStatus(),"The response code is not as expected");
		
 	}
	
	@Test (groups ="Sanity",priority=2)
	public void importTestBannersTest() throws IOException
	{
		MyntraService importservice = Myntra.getService(ServiceType.LGP_CONTOUR, APINAME.IMPORTTESTBANNERS, init.Configurations);
		System.out.println(importservice.URL);
		RequestGenerator importreq = new RequestGenerator(importservice);
		String response1 = importreq.returnresponseasstring();
		System.out.println(response1);
		Assert.assertEquals(200, importreq.response.getStatus(),"The response code is not as expected");
	}

	@Test (groups ="Sanity",priority=3)
	public void runPythonScriptTest() throws IOException
	{
		boolean checkflag=false;
		String s = Runtime.runShellCommandAndGetResponse("python ./Data/sponsored_sync_check_es_test.py");
		System.out.println("---------   "+ s +"   ------------");
		if(s.contains("All Good? True"))
		{
			checkflag =true;
			System.out.println(checkflag);
		}
		Assert.assertEquals(true,checkflag,s);
	}

}
