package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

import java.util.Random;
import java.util.UUID;

public class KnightServiceDataProvider {
	String one = createEmailIds();
	String two = createEmailIds();
	
	@DataProvider
	public Object[][] setBlockDataProvider(ITestContext testContext) {
		String[] arr1 = { one, "blockedLogin"};
		String[] arr2 = { one, "blockedMobile"};
		String[] arr3 = { one, "blockedIMEI"};
		
		String[] arr4 = { one, "blockedLogin"};
		String[] arr5 = { one, "blockedMobile"};
		String[] arr6 = { one, "blockedIMEI"};
		
		String[] arr7 = { two, "blockedLogin"};
		String[] arr8 = { two, "blockedMobile"};
		String[] arr9 = { two, "blockedIMEI"};
		
		String[] arr10 = { two, "blockedLogin"};
		String[] arr11 = { two, "blockedMobile"};
		String[] arr12 = { two, "blockedIMEI"};
		
		String[] arr13 = { "{\"key\":\"vivek.vasvani@myntra.com\",\"value\":\"true\"}", "blockedLogin" }; 
		String[] arr14 = { "{\"key\":\"vivek.vasvani@myntra.com\",\"value\":\"true\"}", "blockedMobile" }; 
		String[] arr15 = { "{\"key\":\"vivek.vasvani@myntra.com\",\"value\":\"true\"}", "blockedIMEI" }; 
		
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6, arr7, arr8, arr9, arr10, arr11, arr12, arr13, arr14, arr15 };
		return dataSet;
	}
	
	@DataProvider
	public Object[][] setBlockDataProviderNegative(ITestContext testContext) {
		String[] arr1 = { "{\"key\":\"vivek.vasvani@gmail.com\", \"value\": \"unknown\"}", "blockedLogin"};
		String[] arr2 = { "{\"key\":\"vivek.vasvani@gmail.com\", \"value\": \"true\"}", "Unknown"};
	
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return dataSet;
	}

	
	@DataProvider
	public Object[][] getBlockDataProvider(ITestContext testContext) {
		String[] arr1 = { getEmailIds(one), "blockedLogin"};
		String[] arr2 = { getEmailIds(two), "blockedLogin"};
		String[] arr3 = { "\"vivek.vasvani@gmail.com\"", "blockedLogin"};
		
		String[] arr4 = { getEmailIds(one), "blockedMobile"};
		String[] arr5 = { getEmailIds(two), "blockedMobile"};
		String[] arr6 = { "\"vivek.vasvani@gmail.com\"", "blockedMobile"};
		
		String[] arr7 = { getEmailIds(one), "blockedIMEI"};
		String[] arr8 = { getEmailIds(two), "blockedIMEI"};
		String[] arr9 = { "\"vivek.vasvani@gmail.com\"", "blockedIMEI"};
	
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3,
											  arr4, arr5, arr6,
											  arr7, arr8, arr9
											};
		return dataSet;
	}
	
	@DataProvider
	public Object[][] setCODLimit(ITestContext testContext) {
		String[] arr1 = { getEmailIds(one), "blockedLogin"};
		String[] arr2 = { getEmailIds(two), "blockedLogin"};
		String[] arr3 = { "\"vivek.vasvani@gmail.com\"", "blockedLogin"};
		
		String[] arr4 = { getEmailIds(one), "blockedMobile"};
		String[] arr5 = { getEmailIds(two), "blockedMobile"};
		String[] arr6 = { "\"vivek.vasvani@gmail.com\"", "blockedMobile"};
		
		String[] arr7 = { getEmailIds(one), "blockedIMEI"};
		String[] arr8 = { getEmailIds(two), "blockedIMEI"};
		String[] arr9 = { "\"vivek.vasvani@gmail.com\"", "blockedIMEI"};
	
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3,
											  arr4, arr5, arr6,
											  arr7, arr8, arr9
											};
		return dataSet;
	}
	
	private String createEmailIds(){
		String[] trueOrFalse = { "true", "false" };
		int randomNum = new Random().nextInt(10);
		StringBuffer sb = new StringBuffer();
		//sb.append("{\n");
		if(randomNum ==0)
			randomNum = 1;
		for(int i=0;i<randomNum;i++){
			String str = UUID.randomUUID().toString();
			sb.append("{ \n\"key\":\""+str.split("-")[0]+"@myntra.com\",\n \"value\":\""+trueOrFalse[new Random().nextInt(trueOrFalse.length)]+"\"}\n,");
		}
		return sb.toString().substring(0, sb.toString().length()-1);
	}
	
	private String getEmailIds(String outputOfCreateEmails){
		String[] commaSep = outputOfCreateEmails.split(",");
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<commaSep.length;i++){
			if(commaSep[i].contains("key")){
				String value = commaSep[i].split(":")[1];
				sb.append(commaSep[i].split(":")[1]+",");
			}
		}
		String toReturn = sb.toString();
		return toReturn.substring(0, toReturn.length()-1);
	}
}
