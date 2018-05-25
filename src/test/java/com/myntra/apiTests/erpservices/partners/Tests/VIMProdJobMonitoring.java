package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.VIMHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VIMProdJobMonitoring extends BaseTest {
	
	static Initialize init = new Initialize("Data/configuration");
	static Logger log = LoggerFactory.getLogger(VIMProdJobMonitoring.class);
	public static VIMHelper vimHelper;
	public static SellerPaymentServiceHelper sellerPaymentServiceHelper;
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	@Test(groups = { "ProdSanity" }, priority = 0)
	public void getVIMJOBDetails() throws Exception {

		vimHelper = new VIMHelper();
		sellerPaymentServiceHelper=new SellerPaymentServiceHelper();
		
	//	Date date = DateUtils.addDays(new Date(), -1);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(date);
		
		String total_jobs=vimHelper.getVIMTotalJobs(startDate, startDate); //Request sent to sps to get balance
		log.debug("Total VIM Uploads for : "+startDate+" is "+total_jobs);
		
		
		String averageJobExecutionTime=vimHelper.getVIMAverageJobExecutionTime(startDate, startDate); //Request sent to sps to get balance
		log.debug("Total Jobs average execution Time : "+averageJobExecutionTime +" mins");
		
		String avgExecutionTimeForEachItem=vimHelper.getVIMAvgExecutionTimeForEachItem(startDate, startDate);
		
		sellerPaymentServiceHelper.webHookNotificationPersonal(init, "Total VIM Uploads for : *"+startDate+" - "+total_jobs+" Jobs*\n "
				+ "Average Job Execution Time : *"+averageJobExecutionTime+" mins*\n"
						+ "Average Execution Time For each Item upload : *"+avgExecutionTimeForEachItem+" Secs*\n"
				, slackuser, botname);

	}

	
	
	

}
