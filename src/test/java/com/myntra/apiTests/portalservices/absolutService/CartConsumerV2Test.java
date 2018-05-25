package com.myntra.apiTests.portalservices.absolutService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.atsService.ATSHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;

public class CartConsumerV2Test extends BaseTest{
	
	static String payload = null;
	static String QUEUE_NAME = System.getenv("queueName"); //"Cart_Mt_Sync";
	static String uidx = "";
	
	//Style Changes
	static String styleId = System.getenv("styleId"); //"1531";
	static String skuId1 = System.getenv("skuId1"); //"3831";
	static String skuId2 = System.getenv("skuId2"); //"3832";
	static String skuId3 = System.getenv("skuId3"); //"3833";

	static UUID idOne = UUID.randomUUID();
	static String signupPrefix = String.valueOf(idOne);
	
	static APIUtilities utilities = new APIUtilities();
	static ATSHelper atsHelper = new ATSHelper();
	static AbsolutHelper absHelper = new AbsolutHelper();
	
	@BeforeClass
	public static void beforeClassMethod() throws JsonParseException, JsonMappingException, IOException {
		ProfileResponse profileResponse = atsHelper.performSignup(signupPrefix + "@myntra.com");
		uidx = profileResponse.getEntry().getUidx();
	}
	
	@Test(priority = 0)
	public void testAddCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		addcartMessageToQueue(styleId,skuId1,10);
		addcartMessageToQueue(styleId,skuId2,10);
		Thread.sleep(3000);
		String jsonResp = absHelper.getCartSecuredAPI(uidx);
		System.out.println("jsonRespOne "+ jsonResp);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 20);
	}
	
	@Test(priority = 1)
	public void testRemoveCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		removecartMessageToQueue(styleId,skuId1);
		Thread.sleep(3000);
		String jsonResp = absHelper.getCartSecuredAPI(uidx);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 10);
	}
	
	@Test(priority = 2)
	public void testUpdateByQtyCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		updatecartMessageToQueue(styleId,"0",20, skuId2);
		Thread.sleep(3000);
		String jsonResp = absHelper.getCartSecuredAPI(uidx);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 20);
	}
	
	@Test(priority = 3)
	public void testUpdateBySkuIdCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		updatecartMessageToQueue(styleId,skuId2,30, skuId3);
		Thread.sleep(3000);
		String jsonResp = absHelper.getCartSecuredAPI(uidx);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 30);
	}
	
	@Test(priority = 4)
	public void testMergeCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		mergecartMessageToQueue(styleId,skuId2,20);
		Thread.sleep(3000);
		String jsonResp = absHelper.getCartSecuredAPI(uidx);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 20);
	}
	
	@Test(priority = 5)
	public void testDeleteCartConsumer() throws UnsupportedEncodingException, InterruptedException {
		deletecartMessageToQueue();
		Thread.sleep(3000);
		String jsonResp = absHelper.deleteCartSecuredAPI(uidx);
		assertTrue((Integer)JsonPath.read(jsonResp, "$.cartCount") == 0);
	}
	
	private void mergecartMessageToQueue(String styleId, String skuId, int qty) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, styleId, skuId, qty};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/mergecartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}
	
	private void deletecartMessageToQueue() throws UnsupportedEncodingException {
		Object[] strPayload = {uidx};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/deletecartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);		
	}

	private void addcartMessageToQueue(String styleId, String skuId, int qty) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, styleId, skuId, qty};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/addcartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}
	
	private void removecartMessageToQueue(String styleId, String skuId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx, styleId, skuId};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/removecartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}
	
	private void updatecartMessageToQueue(String styleId, String skuId, int qty, String newSkuId) throws UnsupportedEncodingException {
		Object[] strPayload = {uidx,styleId, skuId,qty, newSkuId};
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/updatecartmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}
	
	private void payLoadCreateAndPublish(String payload, Object[] strPayload) throws UnsupportedEncodingException {
		absHelper.publishMessageToQueue(utilities.preparepayload(payload, strPayload), QUEUE_NAME);
	}
}
