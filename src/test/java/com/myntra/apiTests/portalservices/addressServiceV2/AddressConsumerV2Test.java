package com.myntra.apiTests.portalservices.addressServiceV2;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.absolutService.AbsolutHelper;
import com.myntra.apiTests.portalservices.atsService.ATSHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.testbase.BaseTest;

public class AddressConsumerV2Test extends BaseTest {

	static String payload = null;
	static String QUEUE_NAME = "Address_Mt_Sync";
	static String uidx = "";

	static UUID idOne = UUID.randomUUID();
	static String signupPrefix = String.valueOf(idOne);

	static APIUtilities utilities = new APIUtilities();
	static ATSHelper atsHelper = new ATSHelper();
	static AbsolutHelper absHelper = new AbsolutHelper();
	static AddressServiceHelperV2 addressHelper = new AddressServiceHelperV2();

	static String email = signupPrefix + "@myntra.com";

	@DataProvider(name = "myntra")
	public Object[][] addNewAddressDP() {
		String[] address = new String[] { "This is AddressLine", "locality1", "Bangalore", "KA", "Karanataka", "560034",
				email, "7000000000", "HOME" };
		return new Object[][] { address };
	}

	@BeforeClass
	public static void beforeClassMethod() throws JsonParseException, JsonMappingException, IOException {
		ProfileResponse profileResponse = atsHelper.performSignup(email);
		uidx = profileResponse.getEntry().getUidx();
	}

	@Test(priority = 0, dataProvider = "myntra")
	public void testAddAddressConsumer(String address, String locality, String city, String state, String statename,
			String pincode, String email, String mobile, String addressType) throws UnsupportedEncodingException, InterruptedException {
		addAddressMessageToQueue("ADD", "6144311", "1", "kranthi-1", address + "1", locality + "1", city, state,
				pincode, email, mobile, addressType);
		addAddressMessageToQueue("ADD", "6144312", "1", "kranthi-2", address + "2", locality + "2", city, state,
				pincode, email, "7000000001", addressType);
		addAddressMessageToQueue("ADD", "6144313", "1", "kranthi-3", address + "3", locality + "3", city, state,
				pincode, email, "7000000002", "OFFICE");

		String jsonResp = addressHelper.getAddressSecuredAPI(uidx);
		assertTrue(Integer.parseInt(JsonPath.read(jsonResp, "$.totalCount")) == 3);
	}

	@Test(priority = 1, dataProvider = "myntra")
	public void testUpdateAddressConsumer(String address, String locality, String city, String state, String statename,
			String pincode, String email, String mobile, String addressType) throws UnsupportedEncodingException, InterruptedException {
		addAddressMessageToQueue("UPDATE", "6144312", "0", "kranthi-21", address + "21", locality + "21", "Guntur",
				"AP", "560035", email, "7000000021", "OFFICE");
		String jsonResp = addressHelper.getAddressSecuredAPI(uidx);
		assertTrue(Integer.parseInt(JsonPath.read(jsonResp, "$.totalCount")) == 3);
		verifyAddressFields(jsonResp, 6144312, "true", "kranthi-21", address + "21", locality + "21", "Guntur", "AP",
				"Andhra Pradesh", "560035", "7000000021", "OFFICE");

		addAddressMessageToQueue("UPDATE", "6144313", "0", "kranthi-31", address + "31", locality + "31",
				"Bommanahalli", "KA", "560068", email, "7000000031", "HOME");
		String jsonResp1 = addressHelper.getAddressSecuredAPI(uidx);
		assertTrue(Integer.parseInt(JsonPath.read(jsonResp1, "$.totalCount")) == 3);
		verifyAddressFields(jsonResp, 6144313, "true", "kranthi-31", address + "31", locality + "31", "Bommanahalli",
				"KA", "Karanataka", "560068", "7000000031", "HOME");
	}

	@Test(priority = 2, dataProvider = "myntra")
	public void testDeleteAddressConsumer(String address, String locality, String city, String state, String pincode,
			String email, String mobile, String addressType) throws UnsupportedEncodingException, InterruptedException {
		deleteAddressMessageToQueue("6144312");
		String jsonResp = addressHelper.getAddressSecuredAPI(uidx);
		assertTrue(Integer.parseInt(JsonPath.read(jsonResp, "$.totalCount")) == 2);

		deleteAddressMessageToQueue("6144313");
		String jsonResp1 = addressHelper.getAddressSecuredAPI(uidx);
		assertTrue(Integer.parseInt(JsonPath.read(jsonResp1, "$.totalCount")) == 1);
	}

	private void verifyAddressFields(String jsonResp, Integer addressId, String defaultAddress, String name,
			String address, String locality, String city, String state, String statename, String pincode, String mobile,
			String type) throws UnsupportedEncodingException {
		List<Integer> addressIds = JsonPath.read(jsonResp, "$.data..id");
		int index = 0;
		for (int i = 0; i < addressIds.size(); i++) {
			if (addressIds.get(i) == addressId)
				index = i;
		}
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].version").toString(), "0");
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].userId"), uidx);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].defaultAddress").toString(), defaultAddress);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].addressType"), type);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].name"), name);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].address"), address);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].city"), city);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].stateCode"), state);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].stateName"), statename);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].countryCode"), "IN");
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].countryName"), "");
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].pincode"), pincode);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].email"), email);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].mobile"), mobile);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].locality"), locality);
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].address_scoring"), "VALID");
		assertEquals(JsonPath.read(jsonResp, "$.data[" + index + "].addressScore").toString(), "0");
	}

	private void deleteAddressMessageToQueue(String addressId) throws UnsupportedEncodingException {
		Object[] strPayload = { addressId, uidx };
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/deleteaddressmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}

	private void addAddressMessageToQueue(String action, String id, String isDefault, String name, String address,
			String locality, String city, String state, String pincode, String email, String mobile, String addressType)
			throws UnsupportedEncodingException {
		Object[] strPayload = { action, id, uidx, isDefault, name, address, locality, city, state, pincode, email,
				mobile, addressType };
		try {
			payload = utilities.readFileAsString("./Data/payloads/JSON/ats/addaddressmessage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		payLoadCreateAndPublish(payload, strPayload);
	}

	private void payLoadCreateAndPublish(String payload, Object[] strPayload) throws UnsupportedEncodingException {
		absHelper.publishMessageToQueue(utilities.preparepayload(payload, strPayload), QUEUE_NAME);
	}
}
