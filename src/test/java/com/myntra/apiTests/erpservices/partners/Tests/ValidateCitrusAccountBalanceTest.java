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
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;

public class ValidateCitrusAccountBalanceTest extends BaseTest {
	static Initialize init = new Initialize("Data/configuration");
	static Logger log = Logger.getLogger(ValidateCitrusAccountBalanceTest.class);
	public static SellerPaymentServiceHelper sellerPaymentServiceHelper;
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	@Test(groups = { "ProdSanity" }, priority = 0)
	public void getCitrusAccountBalance() throws Exception {

		sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
		String auth_token = sellerPaymentServiceHelper.logInCitrusProd();

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.GET_CITURS_BAL, null,
				SERVICE_TYPE.CITRUS_SVC.toString(), HTTPMethods.GET, null, getCitrusAPIHeader(auth_token));
		JSONObject jsonObject = new JSONObject(service.getResponseBody());
		String platform_account_balance=jsonObject.get("platform_account_balance").toString();
		log.debug("Citrus Platform Account Balance : "+platform_account_balance);
		BigDecimal citrusBal=new BigDecimal(platform_account_balance);	
		
		String spsBal=sellerPaymentServiceHelper.getSPSBalanceAmount(); //Request sent to sps to get balance
		BigDecimal spsBal1=new BigDecimal(spsBal);	
		log.debug("SPS Platform Account Balance : "+spsBal);
		BigDecimal diff=citrusBal.subtract(spsBal1);
		log.debug("Balance difference between Citrus and SPS : "+diff);
		
		sellerPaymentServiceHelper.webHookNotificationPersonal(init, "Citrus Platform Account Balance: "+citrusBal+"\n "
				+ "SPS Total Balance : "+spsBal+"\n"
				+ "Balance difference between Citrus and SPS : "+diff, slackuser, botname);

	}

	private HashMap<String, String> getCitrusAPIHeader(String auth_token) {
		HashMap<String, String> createArtieServiceHeaders = new HashMap<String, String>();
		createArtieServiceHeaders.put("auth_token", auth_token);
		return createArtieServiceHeaders;
	}
	
	

}
