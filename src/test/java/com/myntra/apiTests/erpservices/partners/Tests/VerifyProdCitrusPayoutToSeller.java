package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.partners.Bankreferencedetails;
import com.myntra.apiTests.erpservices.partners.CitrusPayloadGenerator;
import com.myntra.apiTests.erpservices.partners.CitrusPayoutResponse;
import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.erpservices.partners.dp.SellerPaymentDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class VerifyProdCitrusPayoutToSeller extends BaseTest {
	static Initialize init = new Initialize("Data/configuration");
	static Logger log = Logger.getLogger(VerifyProdCitrusPayoutToSeller.class);
	public static SellerPaymentServiceHelper sellerPaymentServiceHelper;
	
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	@Test(groups = {
			"ProdSanity" }, priority = 0, dataProviderClass = SellerPaymentDP.class, dataProvider = "getCitrusPayoutDetails")
	public void getCitrusPayoutDetails(String from_date, String to_date, long seller_id, long limit, long page_number,String sellerName)
			throws Exception {

		sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
		CitrusPayloadGenerator citrusPayloadGenerator = new CitrusPayloadGenerator();
		String payload = citrusPayloadGenerator.payloadGenerator(from_date, to_date, seller_id, limit, page_number);

		String auth_token = sellerPaymentServiceHelper.logInCitrusProd();

		Svc service = HttpExecutorService.executeHttpService(Constants.SELLERAPI_PATH.GET_CITURS_PAYOUT, null,
				SERVICE_TYPE.CITRUS_SVC.toString(), HTTPMethods.POST, payload, getCitrusAPIHeader(auth_token));
		
		String sellerId=String.valueOf(seller_id);
		
		CitrusPayoutResponse citrusResponse= (CitrusPayoutResponse) APIUtilities
				.getJsontoObject(service.getResponseBody().replace("SellerDetails", "sellerDetails"), new CitrusPayoutResponse());
		
		if(citrusResponse.getError_id()!=null && citrusResponse.getError_id().contains("473")){
			sellerPaymentServiceHelper.webHookNotificationPersonal(init,
					"*Citrus SellerId: " + seller_id + ", "+sellerName+"*"+"\n " + "`Bank Reference Details :` \n" + ">`No data available between :"+from_date+" - " +to_date+ "`"+"\n"
							,
					slackuser, botname);
		}
		else{
		
		ArrayList<Bankreferencedetails> bankreferencedetails=citrusResponse.getSellerDetails().get(0).getBankreferencedetails();
		String bankref="";
		for(int i=0;i<bankreferencedetails.size();i++){
			bankref=bankref +">Payment Date : "+bankreferencedetails.get(i).getPayment_ref_date()+" , "+"Amount : "+bankreferencedetails.get(i).getAmount()+"\n";
			
		}

		
		
		
		sellerPaymentServiceHelper.webHookNotificationPersonal(init,
				"*Citrus SellerId: " + seller_id + ", "+sellerName+"*"+"\n " + "`Bank Reference Details :` \n" + bankref + "\n"
						,
				slackuser, botname);
		}

	}

	private HashMap<String, String> getCitrusAPIHeader(String auth_token) {
		HashMap<String, String> createCitrusServiceHeaders = new HashMap<String, String>();
		createCitrusServiceHeaders.put("auth_token", auth_token);
		createCitrusServiceHeaders.put("Content-Type", "application/json");
		return createCitrusServiceHeaders;
	}

}
