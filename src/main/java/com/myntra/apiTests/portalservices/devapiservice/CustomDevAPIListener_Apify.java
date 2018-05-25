package com.myntra.apiTests.portalservices.devapiservice;

import java.util.HashMap;
import java.util.HashSet;

import com.myntra.apiTests.common.Myntra;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.time.*;

import com.myntra.lordoftherings.gandalf.Method;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;

public class CustomDevAPIListener_Apify implements ITestListener, ISuiteListener {
private String APIFYWEBHOOK = "https://hooks.slack.com/services/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";
	private HashSet<String> failedMethods = new HashSet<>();
	 
	private HashMap<String, HashMap<String, String>> result = new HashMap<>();
	@Override
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult arg0) {
		failedMethods.add(arg0.getName());
		}

	@Override
	public void onTestSkipped(ITestResult arg0) {
		failedMethods.add(arg0.getName());
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		String slackResult = prepareAndReturnSlackPayload();
		NotifyResultsInSlack(slackResult);
	}

	@Override
	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void NotifyResultsInSlack(String payload)
	{
		try{
		MyntraService service = Myntra.getService();
		service.RequestSendMethod = Method.POST;
		service.URL = APIFYWEBHOOK;
		service.Payload = payload;
		RequestGenerator request = new RequestGenerator(service);
		if(request.response.getStatus()==200)
		{
			System.out.println("Test Reports Notified in Slack!");
		}
		else
		{
			System.out.println("Error while sending slack Notification!");
		}
		}
		catch (Exception e)
		{
			System.out.println("Exception while sending Slack Notification!");
			System.out.println("Exception Detail : "+e.getMessage());

		}
	}
	
	private String prepareAndReturnSlackPayload()
	{
		String payload="";
		long now = Instant.now().toEpochMilli();
		if(failedMethods.size()==0 || failedMethods.isEmpty())
		{
			payload = "{\"attachments\":[{\"fallback\":\"If you are seeing this, it means we have screwed up in the notification code! Please inform test engineering team at the earliest!\",\"color\":\"#36a64f\",\"pretext\":\"Looks like there aren't any failures this time!\",\"title\":\"Apify Test Report\",\"title_link\":\"http://192.168.20.63:8080/job\",\"text\":\"Congratulations! - No failures\",\"footer\":\"Report generated @ \",\"ts\":"+Long.toString(now)+"}]}";
		}
		else
		{
			String failedMethodsList ="";
			for (String method : failedMethods)
			{
				failedMethodsList = failedMethodsList + "\n";
				failedMethodsList = failedMethodsList + method;

			}
			payload = "{\"attachments\":[{\"fallback\":\"If you are seeing this, it means we have screwed up in the notification code! Please inform test engineering team at the earliest!\",\"color\":\"#ff0000\",\"pretext\":\""+failedMethods.size()+" testcases failed in this run!\",\"title\":\"Apify Test Report\",\"title_link\":\"http://192.168.20.63:8080/job\",\"text\":\""+failedMethodsList+"\",\"footer\":\"Report generated @ \",\"ts\":"+Long.toString(now)+"}]}";

		}
		return payload;
		
	}

}
