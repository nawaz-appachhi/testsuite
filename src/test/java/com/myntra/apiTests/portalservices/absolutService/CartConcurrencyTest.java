package com.myntra.apiTests.portalservices.absolutService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.myntra.apiTests.portalservices.atsService.ATSHelper;
import com.myntra.idea.response.ProfileResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class CartConcurrencyTest extends BaseTest {

	int THREAD_POOL_SIZE = 5;

	static UUID idOne = UUID.randomUUID();
	static String signupPrefix = String.valueOf(idOne);

	static ATSHelper atsHelper = new ATSHelper();
	static AbsolutHelper absHelper = new AbsolutHelper();

	@Test
	public void cartConsumerConcurrency() throws JsonParseException, JsonMappingException, IOException {
		String uidx = null;
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		List<Callable<Boolean>> tasks = new ArrayList<>();
		List<String> uidxList = new ArrayList<>();
		for (int i = 0; i < THREAD_POOL_SIZE; i++) {
			String userEmail = signupPrefix + String.valueOf(i) + "@myntra.com";
			ProfileResponse profileResponse = atsHelper.performSignup(userEmail);
			uidx = profileResponse.getEntry().getUidx();
			uidxList.add(uidx);
			tasks.add(new CreateCartUnit(uidx, "1531", "3831", 20, "", "add"));
		}
		try {
			List<Future<Boolean>> results = executor.invokeAll(tasks);
			for (Iterator<String> iterator = uidxList.iterator(); iterator.hasNext();) {
				String abc = iterator.next();
				Assert.assertEquals("20", JsonPath.read(absHelper.getCartSecuredAPI(abc), "$.cartCount"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
