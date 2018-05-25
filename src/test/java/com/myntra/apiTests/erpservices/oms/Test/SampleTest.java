package com.myntra.apiTests.erpservices.oms.Test;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;

import com.myntra.test.commons.testbase.BaseTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;

public class SampleTest extends BaseTest{


	@Test(description="1.Use . 2."
			+ "2 fdsffd"
			+ "3 eferferf"
			+ "4.erferfer"
			+ "5. erferfer"
			+ "6. ewrferfer", groups={"group1","group2"})
	public void testOmsService2() throws UnsupportedEncodingException{
		

		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Assert.fail("Failed sorry");
		Svc lmsSvc = topology.getService(SERVICE_TYPE.LMS_SVC.toString());
		//lmsSvc.appendPath("a").appendPath("b").appendPath("c").post("dfsdfsd");
		
		lmsSvc.detect();
		
		try {
			lmsSvc.addHeader("Auth", "sdfsdfdfdsfsd").addHeader("dfsdfsd", "sdfsdsd").post("dfdfsd");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		System.out.println("Passed");
	}	
	
	
	@Test(description="1.Use . 2."
			+ "2 fdsffd"
			+ "3 eferferf"
			+ "4.erferfer"
			+ "5. erferfer"
			+ "6. ewrferfer", groups={"group3","group4"})
	public void testOmsService3() throws UnsupportedEncodingException{
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("Passed");
	}	


}
