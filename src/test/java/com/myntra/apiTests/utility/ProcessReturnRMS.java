package com.myntra.apiTests.utility;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.rms.RMSServiceHelper;
import com.myntra.test.commons.testbase.BaseTest;

public class ProcessReturnRMS extends BaseTest{

	 @Test
	    public void ProcessReturnRMS() throws Exception {
		 RMSServiceHelper rmsServiceHelper = new RMSServiceHelper();
	        String id = System.getenv("Id").replace("-","");
	        //String status = System.getenv("ToStatus");
	        //String type = System.getenv("Type");
	        if (null == id || id.equalsIgnoreCase("")) {
	            System.out.println("Please pass the Return ID to Proceed");
	            Assert.fail();
	        }

	        System.out.println("Start Processing For Return ID " + id);

	        long returnId = 0L;
	        returnId = Long.parseLong(id);
	        rmsServiceHelper.ProcessReturnRevamped(""+returnId);
	    }
}
