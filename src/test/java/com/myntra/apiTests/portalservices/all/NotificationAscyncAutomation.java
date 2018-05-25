package com.myntra.apiTests.portalservices.all;
import com.myntra.apiTests.common.Myntra;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import com.myntra.apiTests.dataproviders.NotificationAscyncAutomationDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

import org.apache.commons.io.FileUtils;

public class NotificationAscyncAutomation extends NotificationAscyncAutomationDP {
	
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(knightService.class);
	static APIUtilities apiUtil = new APIUtilities();
	String pathOfOutputFolder = System.getProperty("user.dir");
	String pathOfExpected = System.getProperty("user.dir");
	String testExecutionFile = System.getProperty("user.dir")+File.separator+"testExecution.txt";
	
	@BeforeSuite
	public void setUP(){
		System.out.println(System.getProperty("user.dir"));
		File f = new File(testExecutionFile);
		if(f.exists())
			f.delete();
		else
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test(groups = {  }, dataProvider="publishNotificationOne", priority=0)
	public void publishOne(String appId, String appName, String nId, String nType, String ttl, String heading, String title,
			String desc, String pageUrl, String imgUrl, String targetPortalName, String cVersion, String cChannelName, 
			String tVersion, String tUserList, String expected)
	{
		String json = getJson("./Data/payloads/JSON/publishone", appId, appName, nId, nType, ttl, heading, title, desc, pageUrl,
				imgUrl, targetPortalName, cVersion, cChannelName, tVersion, tUserList);
		System.out.println("Payload  :\n"+json);		
		MyntraService service = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONASYNC, APINAME.PUBLISH, init.Configurations, json);
		HashMap headers = new HashMap<>();
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		RequestGenerator requestGen = new RequestGenerator(service, headers);
		String response = requestGen.returnresponseasstring();
		System.out.println("----------------------------------------"+response);
		String notificationId = requestGen.respvalidate.GetNodeValue("data.notificationId", response).replaceAll("\"", "");
		writeStringAsFile("publishOne,"+notificationId+","+expected+"\n", testExecutionFile);
		System.out.println("notificationId   :\n"+notificationId);
	}
	
	@Test(priority=1)
	public void publishOneWait(){
		System.out.println("Waiting....publishOneWait");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeStringAsFile(":\n", testExecutionFile);
	}
	
	@Test(priority=2)
	public void validationOne() throws IOException{
		String results = getFileAsString(testExecutionFile);
		String[] splittedResult = results.split(":");
		for(String str : splittedResult){
			if(str.contains("publishOne")){
				String[] testResults = str.split("\n");
				for(String testcase : testResults){
					System.out.println(testcase);
					File file1 = new File(pathOfOutputFolder+File.separator+testcase.split(",")[0]);
	                File file2 = new File(pathOfExpected+File.separator+testcase.split(",")[1]);	        
	                boolean compareResult = FileUtils.contentEquals(file1, file2);
	                AssertJUnit.assertTrue(compareResult);
				}
			}
		}		
	}
	
	@Test(groups = {  }, dataProvider="publishNotificationOne", priority=3)
	public void publishTwo(String appId, String appName, String nId, String nType, String ttl, String heading, String title,
			String desc, String pageUrl, String imgUrl, String targetPortalName, String cVersion, String cChannelName, 
			String tVersion, String tUserList, String expected)
	{
		String json = getJson("./Data/payloads/JSON/publishone", appId, appName, nId, nType, ttl, heading, title, desc, pageUrl,
				imgUrl, targetPortalName, cVersion, cChannelName, tVersion, tUserList);
		MyntraService service = Myntra.getService(ServiceType.PORTAL_NOTIFICATIONASYNC, APINAME.PUBLISH, init.Configurations, json);
		HashMap headers = new HashMap<>();
		headers.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		RequestGenerator requestGen = new RequestGenerator(service, headers);
		String response = requestGen.returnresponseasstring();
		String notificationId = requestGen.respvalidate.GetNodeValue("data.notificationId", response).replaceAll("\"", "");
		writeStringAsFile("publishTwo,"+notificationId+","+expected+"\n", testExecutionFile);
	}
	
	@Test(priority=4)
	public void publishTwoWait(){
		System.out.println("Waiting....publishTwoWait");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writeStringAsFile(":\n", testExecutionFile);
	}
	
	@Test(priority=5)
	public void validationTwo() throws IOException{
		String results = getFileAsString(testExecutionFile);
		String[] splittedResult = results.split(":");
		for(String str : splittedResult){
			if(str.contains("publishTwo")){
				String[] testResults = str.split("\n");
				for(String testcase : testResults){
					File file1 = new File(pathOfOutputFolder+File.separator+testcase.split(",")[0]);
	                File file2 = new File(pathOfExpected+File.separator+testcase.split(",")[1]);	        
	                boolean compareResult = FileUtils.contentEquals(file1, file2);
	                AssertJUnit.assertTrue(compareResult);
				}
			}
		}		
		
	}
		
	private String getJson(String Path, String... args){
		File f = new File(Path);
		String toReturn = "";
		if(f.exists()){
			String line;
			StringBuffer sb = new StringBuffer();
			BufferedReader bf;
			try {
				bf = new BufferedReader(new FileReader(f));
				while((line = bf.readLine())!=null){
					sb.append(line+"\n");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			toReturn = sb.toString();
			for(int i=1;i<=args.length;i++){
				toReturn = toReturn.replace("${"+i+"}", args[i-1]);
			}
			return toReturn;
		}else{
			System.out.println("ERROR  : "+Path+" file not found");
			return "";
		}
	}
	
	private String getFileAsString(String Path){
		File f = new File(Path);
		//String toReturn = "";
		if(f.exists()){
			String line;
			StringBuffer sb = new StringBuffer();
			BufferedReader bf;
			try {
				bf = new BufferedReader(new FileReader(f));
				while((line = bf.readLine())!=null){
					sb.append(line+"\n");
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return sb.toString();
		}else{
			System.out.println("ERROR  : "+Path+" file not found");
			return "";
		}
	}
	
	private void writeStringAsFile(String sb, String path){
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(path, true)));
			out.write(sb);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
