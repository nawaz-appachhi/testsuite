package com.myntra.apiTests.erpservices.jobTracker;

import com.myntra.apiTests.common.ServiceHelper;
import com.myntra.test.commons.service.ServiceExecutor;
import com.myntra.test.commons.service.Svc;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.log4testng.Logger;

import java.net.URISyntaxException;

/**
 * 
 * @author sandal.iqbal
 *
 */
public class JobTrackerHelper extends ServiceHelper {

	String url = getJobTrackerServiceURL() + "/jobtracker/job";
	Logger log = Logger.getLogger(JobTrackerHelper.class);

	public JobTrackerHelper(ITestContext testContext) {
		super(testContext);
	}

	public Svc getJobDetails(String jobId) throws URISyntaxException {
		Svc svc = ServiceExecutor.executeGET(url, jobId);
		return svc;
	}

	public String getErrorFile(String jobId) throws URISyntaxException, JSONException {
		Svc svc = ServiceExecutor.executeGET(url, jobId + "/error-file");
		JSONObject obj = new JSONObject(svc.getResponseBody());
		JSONArray array = obj.getJSONArray("data");
		String errorFileUrl = array.getJSONObject(0).getString("url");
		return errorFileUrl;
	}

	public String getSuccessFile(String jobId) throws URISyntaxException, JSONException {
		Svc svc = ServiceExecutor.executeGET(url, jobId + "/success-file");
		JSONObject obj = new JSONObject(svc.getResponseBody());
		JSONArray array = obj.getJSONArray("data");
		String successFileUrl = array.getJSONObject(0).getString("url");
		return successFileUrl;
	}

	public String checkJobStatusInJobTracker(String jobId) throws Exception {
		String status;
		long startTime = System.currentTimeMillis();
		do {
			Svc svc = getJobDetails(jobId);
			String response = svc.getResponseBody();
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.getJSONArray("data");
			status = array.getJSONObject(0).getString("status");
			Thread.sleep(1000);
			if (status.equalsIgnoreCase("INTERRUPTED")) {
				break;
			}
		} while ((!status.equalsIgnoreCase("COMPLETED")) && (System.currentTimeMillis() - startTime) < 70000);
		Assert.assertTrue(status.equalsIgnoreCase("COMPLETED"),
				"The job tracker status is " + status + " expected status is COMPLETED");
		log.debug("\nThe status:\n" + status + "\n");
		return status;
	}

}
