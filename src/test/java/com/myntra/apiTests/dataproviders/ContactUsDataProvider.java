package com.myntra.apiTests.dataproviders;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

public class ContactUsDataProvider {

	static Initialize init = new Initialize("/Data/configuration");
	Commons comUtil = new Commons();

	@DataProvider
	public Object[][] ContactUs_SendQueryDataProvider(ITestContext testContext) {

		String level1 = getRandomlevel1();

		String level2 = null;
		switch (level1) {
		case "orderdelivery":
			level2 = getRandomorderdelivery();
			break;
		case "changeorder":
			level2 = getRandomchangeorder();
			break;
		case "returnexchange":
			level2 = getRandomreturnexchange();
			break;
		case "paymentrefund":
			level2 = getRandompaymentrefund();
			break;
		case "offersdiscountscoupons":
			level2 = getRandomoffersdiscountscoupons();
			break;
		case "manageaccount":
			level2 = getRandommanageaccount();
			break;
		case "other":
			level2 = getRandomOther();
			break;
		}

		String emailid = "eshita.singh@myntra.com";
		String orderid = "66667777";
		String notes = "lordoftherings";
		
		
		Object[][] dataSet = new Object[][] { new Object[] {level1, level2, emailid, orderid , notes}} ;

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 1, 2);
	}

	
	private String getRandomlevel1() {
		String[] all = { "orderdelivery", "changeorder", "returnexchange",
				"paymentrefund", "offersdiscountscoupons", "manageaccount",
				"other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandomorderdelivery() {
		String[] all = { "orderstatus", "notreceiveditems", "orderdelayed",
				"prioritydeliveryoption", "other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandomchangeorder() {
		String[] all = { "changedeliveryaddress", "unabletocancelorder",
				"other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandomreturnexchange() {
		String[] all = { "returnexchangeitem", "unabletocreatereturnexchange",
				"returnpickupdelayed", "refundnotreceivedselfshipped",
				"refundnotreceivedreturnpicked", "other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandompaymentrefund() {
		String[] all = { "orderfailedamountdebited",
				"paymentdebitedmultipletimes", "refundrequest",
				"haventreceivedrefund", "other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandomoffersdiscountscoupons() {
		String[] all = { "offerdetails", "couponlocked", "unabletoapplycoupon",
				"other" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandommanageaccount() {
		String[] all = { "unsubscribefrompromotions" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

	private String getRandomOther() {
		String[] all = { "productavailable", "issuewithwebsitemobileapp",
				"other", "sharefeedback" };
		return all[comUtil.randomNumberUptoLimit(all.length)];
	}

}
