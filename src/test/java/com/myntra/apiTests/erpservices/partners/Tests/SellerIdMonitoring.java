package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellerIdMonitoring extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(SellerIdMonitoring.class);
	public static SellerPaymentServiceHelper sellerPaymentServiceHelper;
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	SellerPaymentServiceHelper sellerHelper = new SellerPaymentServiceHelper();
	
	// Verifies if any orders are placed against Seller_id=1 and returns the order_id
		@Test(groups = { "Regression", "Smoke" }, priority = 0, enabled = false, 
				description="1. Create online order with seller qty1 sku1. \n2. check the split in citrus than in SPS")
		public void SPS_GetOrderId_With_SellerID_1() throws SQLException, IOException, InterruptedException{
			List list=new ArrayList<>();
			list=sellerHelper.getOrderIdWithSellerId1("5",1);
		}
		
		@Test(groups = { "ProdSanity" }, priority = 0,enabled = true)
		public void get_All_Citrus_Sellers() {
			sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
			String auth_token;
			int totalSellers;
			try {
				auth_token = sellerPaymentServiceHelper.logInCitrusProd();
				Svc service = HttpExecutorService.executeHttpService(Constants.SELLERSERVICES_PATH.UPDATE_SELLER, null,
						SERVICE_TYPE.CITRUS_SVC.toString(), HTTPMethods.GET, null, getCitrusAPIHeader(auth_token));
				Assert.assertEquals(service.getResponseStatus(), 200, "API Statuscode : ");		
				JSONArray jsonArray=new JSONArray(service.getResponseBody());		
				totalSellers=jsonArray.length();
				log.debug("Total Sellers Found in Citrus:"+jsonArray.length());
				Assert.assertTrue(totalSellers>0, "Citrus get Sellers API failed");
			
			} catch (Exception e) {
				//sellerPaymentServiceHelper.webHookNotificationPersonal(init, "```Citrus Running Successfully.\nTotal Sellers available in Citrus: "+totalSellers+"```", slackuser, botname);
				sellerPaymentServiceHelper.webHookNotificationPersonal(init, "```"+"Citrus Run Failed - "+e.getMessage()+"```", slackuser, botname);

			}
			catch (AssertionError e) {
				sellerPaymentServiceHelper.webHookNotificationPersonal(init, "```"+"Citrus Run Failed - "+e.getMessage()+"```", slackuser, botname);

			}

			
			
			
			
		}
		
		private HashMap<String, String> getCitrusAPIHeader(String auth_token) {
			HashMap<String, String> createArtieServiceHeaders = new HashMap<String, String>();
			createArtieServiceHeaders.put("auth_token", auth_token);
			return createArtieServiceHeaders;
		}
		

}
