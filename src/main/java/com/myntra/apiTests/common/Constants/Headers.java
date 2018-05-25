package com.myntra.apiTests.common.Constants;

import java.util.HashMap;

public class Headers {

	/**
	 * getBasicHeaderJSON
	 * 
	 * @return
	 */
	public static HashMap<String, String> getBasicHeaderJSON() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bXludHJhOmR1bW15S2V5");
		createOrderHeaders.put("Content-Type", "application/json");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

	/**
	 * getBasicHeaderXML
	 * 
	 * @return
	 */
	public static HashMap<String, String> getBasicHeaderXML() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bXludHJhOmR1bW15S2V5");
		createOrderHeaders.put("Content-Type", "application/xml");
		createOrderHeaders.put("Accept", "application/xml");
		return createOrderHeaders;
	}

	/**
	 * getRMSHeader
	 * 
	 * @return {@link HashMap}
	 */
	public static HashMap<String, String> getRMSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		createOrderHeaders.put("user", "testing");
		return createOrderHeaders;
	}

	/**
	 * getpushPickupToCourierHeader
	 * 
	 * @return
	 */
	public static HashMap<String, String> getpushPickupToCourierHeader() {
		HashMap<String, String> pushPickupToCourierHeader = new HashMap<String, String>();
		pushPickupToCourierHeader.put("Authorization", "Basic YTpi");
		pushPickupToCourierHeader.put("Accept", "Application/json");
		return pushPickupToCourierHeader;
	}

	public static HashMap<String, String> JabongHeader() {
		HashMap<String, String> JabongHeader = new HashMap<String, String>();
		JabongHeader.put("Authorization", "Basic bmVvcGh5LmJpc2hub2k");
		JabongHeader.put("cache-control", "no-cache");
		JabongHeader.put("content-type", "Application/json");
		JabongHeader.put("postman-token", "1eef3f19-e357-c9cd-5eaf-47088ca81617");
		JabongHeader.put("x-jabong-reqid", "AAA");
		JabongHeader.put("x-jabong-tid", "BBBB");
		return JabongHeader;
	}



	/**
	 * getLmsHeaderJSON
	 * 
	 * @return
	 */
	public static HashMap<String, String> getLmsHeaderJSON() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

	/**
	 * getLmsHeaderXML
	 * 
	 * @return
	 */
	public static HashMap<String, String> getLmsHeaderXML() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "Application/xml");
		createOrderHeaders.put("Accept", "application/xml");
		return createOrderHeaders;
	}

	/**
	 * getCTSHeaderJSON
	 * 
	 * @return
	 */
	public static HashMap<String, String> getCTSHeaderJSON() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic ZWthcnQ6YWNjZXNz");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

	/**
	 * getOMSHeaderJSON
	 * 
	 * @return
	 */
	public static HashMap<String, String> getOMSHeaderJSON() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

	/**
	 * @return
	 */
	public static HashMap<String, String> getOMSHeaderJSONOnlyAccept() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Accept", "application/json");
		return createOrderHeaders;
	}

	/**
	 * getOmsHeaderXML
	 * 
	 * @return
	 */
	public static HashMap<String, String> getOmsHeaderXML() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic YTpi");
		createOrderHeaders.put("Accept", "application/xml");
		createOrderHeaders.put("Content-Type", "application/xml");
		return createOrderHeaders;
	}

	/**
	 * getLmsLOSTHeaderXML
	 * 
	 * @return
	 */
	public static HashMap<String, String> getLmsAdminHeaderXML() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic bG1zYWRtaW5+bG1zYWRtaW46dGVzdA==");
		createOrderHeaders.put("Content-Type", "application/xml");
		createOrderHeaders.put("Accept",
				"text/xml,application/json,application/xhtml+xml,text/html,text/plain,image/png,image/jpeg,image/gif,*/*");
		createOrderHeaders.put("Accept-Language", "en-us");
		return createOrderHeaders;
	}

	/**
	 * getStyleHeaderXML
	 * 
	 * @return
	 */
	public static HashMap<String, String> getStyleHeaderXML() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("user", "gopal");
		createOrderHeaders.put("Content-Type", "Application/xml");
		createOrderHeaders.put("Accept", "application/xml");
		return createOrderHeaders;
	}

	/**
	 * getCTSHeader
	 * 
	 * @return
	 */
	public static HashMap<String, String> getCTSHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic ZGVsaGl2ZXJ5OmFjY2Vzcw==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	public static HashMap<String, String> getBoltHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		// createOrderHeaders.put("Authorization", "Basic ZGVsaGl2ZXJ5OmFjY2Vzcw==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	public static HashMap<String, String> getSessionHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	public static HashMap<String, String> getPGResponseHeader(String xid, String csrf_token) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		HashMap<String, String> hmx = new HashMap<String, String>();
		String valueCard = "fox-xid=" + xid;
		hmx.put("cookie", valueCard);
		hmx.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		hmx.put("Accept-Language", "en-US,en;q=0.5");
		hmx.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
		hmx.put("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
		hmx.put("csrf_token", csrf_token);
		return hmx;
	}

	public static HashMap<String, String> getPaymentPageHeader(String xid, String sxid) {
		String value_pay = "fox-xid=" + xid + "; fox-sxid=" + sxid;
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		HashMap<String, String> hmp = new HashMap<>();
		hmp.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:51.0) Gecko/20100101 Firefox/51.0");
		hmp.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		hmp.put("Accept-Language", "en-US,en;q=0.5");
		hmp.put("Cookie", value_pay);
		hmp.put("Content-Type", "text/html");
		hmp.put("Accept", "application/xml");
		return hmp;
	}

	public static HashMap<String, String> getPayNowHeader(String xid, String csrf_token) {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		HashMap<String, String> hm = new HashMap<>();
		hm.put("cookie", "fox-xid=" + xid);
		hm.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		hm.put("Accept-Language", "en-US,en;q=0.5");
		hm.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:50.0) Gecko/20100101 Firefox/50.0");
		hm.put("Content-Type", "application/x-www-form-urlencoded ; charset=UTF-8");
		hm.put("csrf_token", csrf_token);
		return hm;
	}

}
