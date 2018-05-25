package com.myntra.apiTests.dataproviders;

import java.util.UUID;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;


public class RefundAPIsDP {
	
	@DataProvider
	public Object[][] refundAPIDataProvider(ITestContext testContext) {
		
		
		String[] arr1 = { "10734664", generateRandomId(), "0.01",
				"qa", "1.0" };

		Object[][] dataSet = new Object[][] { arr1 };

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}
	
	public String generateRandomId()
	{
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(uuid.lastIndexOf("-") + 1, uuid.length());
	}

}
