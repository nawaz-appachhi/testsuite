package com.myntra.apiTests.erpservices.rms.Test;

import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.test.commons.testbase.BaseTest;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompleteOrderInRMS extends BaseTest{

	public static List returnOrderlist;

	@BeforeClass
	public void testBeforeClass() {
		returnOrderlist = new ArrayList();

		String orders = System.getenv("Ids");
		
		if(orders == null || orders.isEmpty()){
			orders = "12253421";
		}

		if (null == orders || orders.equalsIgnoreCase("")) {
			System.out.println("Please pass the Order IDs to Proceed");
			Assert.fail();
		}

		String[] orderIds = orders.split(",");
		for (String string : orderIds) {
			returnOrderlist.add(string);
		}
	}

	@DataProvider(name = "orders")
	public synchronized Object[][] testSuiteData() {
		Object[][] dataProviderObject = new Object[returnOrderlist.size()][2];
		;
		for (int index = 0; index < returnOrderlist.size(); index++) {
			dataProviderObject[index][0] = "orderId";
			dataProviderObject[index][1] = returnOrderlist.get(index);
		}
		return dataProviderObject;
	}

	
	@Test(dataProvider = "orders")
	public void completeOrderDelivered(String orderName, String returnID) throws IOException {
		End2EndHelper end2EndHelper = new End2EndHelper();
		end2EndHelper.completeOrderInRMS(returnID);
	}

}
