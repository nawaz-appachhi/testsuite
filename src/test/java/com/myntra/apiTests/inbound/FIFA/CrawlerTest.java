package com.myntra.apiTests.inbound.FIFA;

import java.util.HashMap;

import com.myntra.apiTests.inbound.dp.CrawlerDP;
import com.myntra.apiTests.inbound.helper.SpotConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.partners.SellerPaymentServiceHelper;
import com.myntra.apiTests.inbound.helper.CrawlerHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.test.commons.testbase.BaseTest;

public class CrawlerTest extends BaseTest {

	public CrawlerHelper crawlerHelper;
	public SellerPaymentServiceHelper sellerPaymentServiceHelper;
	public static String failedElements;
	HashMap<String, Integer> map = new HashMap<>();
	static Logger log = LoggerFactory.getLogger(CrawlerTest.class);
	static Initialize init = new Initialize("Data/configuration");
	String slackuser = System.getenv("slackuser");
	String botname = System.getenv("botname");

	// "Data/configuration"
	@BeforeClass
	public void openHome() {
		crawlerHelper = new CrawlerHelper();
		sellerPaymentServiceHelper = new SellerPaymentServiceHelper();
	}

	@Test(groups = {
			"Regression" }, enabled = true, dataProviderClass = CrawlerDP.class, dataProvider = "amazonTshirtListKeys")
	public void amazonListPage(Object[] keysList) throws Exception {
		log.info("*****Start TEST*******");
		String response = crawlerHelper.getAmazonList();
		JSONObject jsonResponse = new JSONObject(response);
		try {
			JSONObject results = jsonResponse.getJSONObject(SpotConstants.AMAZON_LIST.RESULTS);
			JSONArray sections = results.getJSONArray(SpotConstants.AMAZON_LIST.SECTIONS);
			JSONObject items = sections.getJSONObject(0);
			JSONArray itemsArray = items.getJSONArray(SpotConstants.AMAZON_LIST.ITEMS);

			for (int i = 0; i < itemsArray.length(); i++) {
				JSONObject lstItems = itemsArray.getJSONObject(i);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_ASIN, lstItems.has(SpotConstants.AMAZON_LIST.ASIN), map);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_TITLE, lstItems.has(SpotConstants.AMAZON_LIST.TITLE), map);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_BRANDNAME, lstItems.has(SpotConstants.AMAZON_LIST.BRANDNAME), map);
				JSONObject link = (JSONObject) lstItems.get(SpotConstants.AMAZON_LIST.LINK);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_LINK_URL, link.has(SpotConstants.AMAZON_LIST.URL), map);
				JSONObject image = (JSONObject) lstItems.get(SpotConstants.AMAZON_LIST.IMAGE);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_URL, image.has(SpotConstants.AMAZON_LIST.URL), map);
				JSONObject prices = (JSONObject) lstItems.get(SpotConstants.AMAZON_LIST.PRICES);
				JSONObject buy = (JSONObject) prices.get(SpotConstants.AMAZON_LIST.BUY);
				crawlerHelper.checkJsonKeyIsAvailable(SpotConstants.AMAZON_LIST.AMAZON_LIST_PRICE, buy.has(SpotConstants.AMAZON_LIST.PRICE), map);
			}
			failedElements = crawlerHelper.calculateElementAvailabilityPercentage(map, itemsArray.length(),
					failedElements);
			log.debug(failedElements);
		}

		catch (JSONException e) {
			log.debug(e.getMessage());
			failedElements=e.getMessage();
		}

	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("tearDown");
		if(failedElements!=null && !failedElements.isEmpty())
		sellerPaymentServiceHelper.webHookNotificationPersonal(init, failedElements, slackuser, botname);

	}

}
