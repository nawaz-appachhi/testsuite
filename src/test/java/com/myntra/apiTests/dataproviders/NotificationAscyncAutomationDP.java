package com.myntra.apiTests.dataproviders;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class NotificationAscyncAutomationDP {
	
	@DataProvider
	public Object[][] publishNotificationOne(ITestContext testContext) {
		String[] arr1 = { "1", "Myntra", "0", "Marketing", "21600000", "Heading", "Title", "Deesc", "http://jabong.com", "http://freekaamaalhai.com/wp-content/uploads/2012/11/myn.jpg",
				"myntra", "0", "android", "0", "rita.agarwala84@gmail.com", "publishOneExpected.json"};
		String[] arr2 = { "1", "Myntra", "0", "Marketing", "21600000", "Heading", "Title", "Deesc", "http://jabong.com", "http://freekaamaalhai.com/wp-content/uploads/2012/11/myn.jpg",
				"myntra", "0", "android", "0", "rita.agarwala84@gmail.com", "publishOneExpected.json"};
		String[] arr3 = { "1", "Myntra", "0", "Marketing", "21600000", "Heading", "Title", "Deesc", "http://jabong.com", "http://freekaamaalhai.com/wp-content/uploads/2012/11/myn.jpg",
				"myntra", "0", "android", "0", "rita.agarwala84@gmail.com", "publishOneExpected.json"};
		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3 };
		return dataSet;
	}

}
