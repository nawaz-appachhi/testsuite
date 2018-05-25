package com.myntra.apiTests.erpservices.partners;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.testng.log4testng.Logger;

import com.myntra.apiTests.common.ServiceHelper;
import com.myntra.apiTests.erpservices.jobTracker.JobTrackerHelper;
import com.myntra.test.commons.service.ServiceExecutor;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.utils.CSVHelper;

import junit.framework.Assert;

public class BulkUploadHelper extends ServiceHelper{
	
	JobTrackerHelper jobHelper;
	VMSHelper vmsHelper;
	CSVHelper csv;
	static ITestContext context;
	private String cookies;
	private HttpURLConnection conn;
	private CookieManager manager = new CookieManager();
	private final String USER_AGENT = "Mozilla/5.0";
	private String jobsUrl = getVendorTermsUIURL()+"/api/jobs";
	String unityloginurl = getUnityURL();
	String vendortermsui = getVendorTermsUIURL()+"/upload/request";
	String xsrfToken;

	Logger log = Logger.getLogger(BulkUploadHelper.class);
	
	
	public BulkUploadHelper(ITestContext testContext) {
		super(testContext);
		context=testContext;
		jobHelper = new JobTrackerHelper(testContext);
		vmsHelper=new VMSHelper();
		csv=new CSVHelper();
		CookieHandler.setDefault(manager);
	}
	
	public void checkSuccess(String successFile) throws Exception {
		BufferedInputStream buffin = new BufferedInputStream(new URL(successFile).openStream());
		List<String>[] rows = csv.readCsv(buffin);
		Reporter.log("Success File:");
		if (rows.length > 0) {
			int colSize = rows[0].size();
			for (List<String> line : rows) {
				String status = line.get(colSize - 2);
				Assert.assertTrue(status.equals("SUCCESS"));
				Reporter.log(line.toString());
			}
		}
	}
	
	public void checkError(String errorFile) throws Exception {
		BufferedInputStream buffin = new BufferedInputStream(new URL(errorFile).openStream());
		List<String>[] rows = csv.readCsv(buffin);
		Reporter.log("Error File:");
		SoftAssert softAssert=new SoftAssert();
		softAssert.assertEquals(rows.length, 0,"Error in bulk upload");
		if (rows.length > 0) {
			int colSize = rows[0].size();
			for (List<String> line : rows) {
				String status = line.get(colSize - 2);
				String message = line.get(colSize - 1);
				Reporter.log(line.toString());
			}
		}
		softAssert.assertAll();
	}

	public void sendPost(String url, String postParams) throws Exception {

		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();

		// Acts like a browser
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		conn.setRequestProperty("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");

		if (this.cookies != null) {
			conn.setRequestProperty("Cookie", getCookies());
		}
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

		conn.setDoOutput(true);
		conn.setDoInput(true);

		log.debug("\nSending 'POST' request to URL : " + url);
		log.debug("Post parameters : " + postParams);
		
		Reporter.log("\nSending 'POST' request to URL : " + url);
		Reporter.log("Post parameters : " + postParams);

		// Send post request
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(postParams);
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		log.debug("Response Code : " + responseCode);
		Reporter.log("Response Code : " + responseCode);

		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();
		setCookies(cookies);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	}

	public String GetPageContent(String url) throws Exception {

		URL obj = new URL(url);
		conn = (HttpURLConnection) obj.openConnection();

		// default is GET
		conn.setRequestMethod("GET");

		conn.setUseCaches(false);

		// act like a browser
		conn.setRequestProperty("User-Agent", USER_AGENT);
		conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		if (cookies != null) {
			conn.setRequestProperty("Cookie", getCookies());
		}

		log.debug("\nSending 'GET' request to URL : " + url);
		int responseCode = conn.getResponseCode();
		log.debug("Response Code : " + responseCode);
		
		Reporter.log("\nSending 'GET' request to URL : " + url);
		Reporter.log("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Get the response cookies
		CookieStore cookieJar = manager.getCookieStore();
		List<HttpCookie> cookies = cookieJar.getCookies();
		setCookies(cookies);

		return response.toString();

	}
	
	public void doBulkUpload(String username,String password,String filename,int vendorid) throws Exception {
		String vendorCode=vmsHelper.getVendorCodeFromVendor(vendorid);
		doBulkUpload(username, password, filename, vendorCode);
	}
	
	public void doBulkUpload(String username,String password,String filename) throws Exception {
		String vendorCode=vmsHelper.getApprovedVendorFromVendor();
		doBulkUpload(username, password, filename, vendorCode);
	}

	public void doBulkUpload(String username, String password, String filename, String vendorCode)
			throws Exception {
		InputStream is=this.getClass().getResourceAsStream(filename);
		File file=csv.createCsvWithVendorCode(is, vendorCode);
		
		Reporter.log("Using vendor code: "+vendorCode);
		log.debug("Using vendor code: "+vendorCode);

		// 2. Construct post content and then send a POST request for
		// authentication
		String postParams = createLoginParams(username,password);
		sendPost(unityloginurl, postParams);

		String vterms = GetPageContent(getVendorTermsUIURL());
		setxsrfToken(vterms);

		// 3. send a GET request to upload request page
		String result = GetPageContent(vendortermsui);
		log.debug(result);

		// 4. send multipart request to upload the file		
		sendMultipartRequest(result, file);
	}

	public String createLoginParams(String username, String password) throws UnsupportedEncodingException {

		List<String> paramList = new ArrayList<String>();
		paramList.add("user" + "=" + URLEncoder.encode(username, "UTF-8"));
		paramList.add("pass" + "=" + URLEncoder.encode(password, "UTF-8"));

		// build parameters list
		StringBuilder result = new StringBuilder();
		for (String param : paramList) {
			if (result.length() == 0) {
				result.append(param);
			} else {
				result.append("&" + param);
			}
		}
		return result.toString();
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(List<HttpCookie> cookies) {
		this.cookies = cookies.toString().replaceAll(",", ";").replace("[", "").replace("]", "");
	}
	
	public String getxsrfToken() {
		return xsrfToken;
	}
	
	public void setxsrfToken(String pageContent) {
		xsrfToken=pageContent.split("\"xsrfToken\":")[1].split(",")[0].replaceAll("\"", "");
	}
	
	private void sendMultipartRequest(String pageContent, File file) throws Exception {
		JSONObject obj = new JSONObject(pageContent);
		String url = obj.getString("url");
		String acl = obj.getJSONObject("params").getString("acl");
		String key = obj.getJSONObject("params").getString("key");
		String policy = obj.getJSONObject("params").getString("policy");
		String algo = obj.getJSONObject("params").getString("x-amz-algorithm");
		String credentials = obj.getJSONObject("params").getString("x-amz-credential");
		String date = obj.getJSONObject("params").getString("x-amz-date");
		String user = obj.getJSONObject("params").getString("x-amz-meta-user");
		String signature = obj.getJSONObject("params").getString("x-amz-signature");

		//create and send multipart request to s3
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("acl", acl);
		builder.addTextBody("key", key);
		builder.addTextBody("policy", policy);
		builder.addTextBody("x-amz-algorithm", algo);
		builder.addTextBody("x-amz-credential", credentials);
		builder.addTextBody("x-amz-date", date);
		builder.addTextBody("x-amz-meta-user", user);
		builder.addTextBody("x-amz-signature", signature);
		builder.addBinaryBody("file", file, ContentType.TEXT_XML, file.getName());
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		HttpEntity multipart = builder.build();
		BufferedReader in = new BufferedReader(new InputStreamReader(multipart.getContent()));
		String inputLine;
		StringBuffer resp = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			resp.append(inputLine);
		}
		in.close();
		log.debug("\nSending 'POST' request to URL : " + url);
		log.debug("\nMultipart builder response:\n" + resp.toString() + "\n");
		Svc svc = ServiceExecutor.executePOSTForUpload(url, builder);
		log.debug("Response Code : " + svc.getResponseStatus());
		
		Reporter.log("\nSending 'POST' request to URL : " + url);
		Reporter.log("\nMultipart builder response:\n" + resp.toString() + "\n");
		Reporter.log("Response Code : " + svc.getResponseStatus());
		
		//create and send request to jobs api
		String fileUrl = url + key.split("/")[0] + "/" +  file.getName();
		String payload = "{\"uploadFileUrl\":\"" + fileUrl + "\",\"termType\":\"PO\"}";
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Cookie", getCookies());
		headers.put("xsrf", getxsrfToken());
		log.debug("\nSending 'POST' request to URL : " + jobsUrl);
		log.debug("\nPayload : " + payload);
		svc = ServiceExecutor.executePOST(jobsUrl, "/", payload, headers);
		log.debug("Response Code : " + svc.getResponseStatus());
		log.debug("Response Body : " + svc.getResponseBody());
		
		Reporter.log("\nSending 'POST' request to URL : " + jobsUrl);
		Reporter.log("\nPayload : " + payload);
		Reporter.log("Response Code : " + svc.getResponseStatus());
		Reporter.log("Response Body : " + svc.getResponseBody());
		
		//Get job id and check status in job tracker
		obj = new JSONObject(svc.getResponseBody());
		JSONArray array = obj.getJSONArray("data");
		String id = String.valueOf(array.getJSONObject(0).getInt("id"));
		log.debug("job id: " + id);
		Reporter.log("job id: " + id);
		String status = jobHelper.checkJobStatusInJobTracker(id);
		if (status.equalsIgnoreCase("completed")) {
			String error = jobHelper.getErrorFile(id);
			String success = jobHelper.getSuccessFile(id);
			checkSuccess(success);
			checkError(error);
		}
	}
}
