package com.myntra.apiTests.dataproviders;

import java.util.Random;

import com.myntra.apiTests.portalservices.CRMPortalService.customerprofileHelper;
import com.myntra.apiTests.portalservices.CRMPortalService.AllServiceHelper;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.PropertyReader;
import com.myntra.lordoftherings.Toolbox;
import com.myntra.lordoftherings.legolas.Commons;

public class CRMServiceDP {

	public static PropertyReader propertyReader = new PropertyReader(
			new String[] { "./Data/dataproviders/CRMservicedp.properties" });

	@DataProvider
	public Object[][] CRMServiceDP_CustomerProfileDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr1 = new String[] {
				propertyReader
						.getPropertyValue(customerprofileHelper.VALID_USERNAME
								.name()),
				propertyReader
						.getPropertyValue(customerprofileHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(customerprofileHelper.SUCCESS_STATUS_CODE_PROFILE
								.name()),
				propertyReader
						.getPropertyValue(customerprofileHelper.SUCCESS_STATUS_COUNT
								.name()),
				propertyReader
						.getPropertyValue(customerprofileHelper.SUCCESS_STATUS_TYPE
								.name()), };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_ReturnDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr1 = new String[] {
				propertyReader.getPropertyValue(AllServiceHelper.VALID_RETURNID
						.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_COUNT
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_TYPE
								.name()), };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_CouponDataProvider_verifyAPIResponse(
			ITestContext testContext) {

		String[] arr1 = new String[] {
				propertyReader.getPropertyValue(AllServiceHelper.VALID_USERNAME_FOR_COUPON
						.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_CODE_COUPON
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_COUNT
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_TYPE
								.name()), };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_CommentDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String arr1[] = new String[] {
				propertyReader.getPropertyValue(AllServiceHelper.VALID_RETURNID
						.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_CODE_COMMENT
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_TYPE
								.name()),

		};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_ExchnageDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String arr1[] = new String[] {
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_ORDERID_EXCHANGE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_CODE_EXCHANGE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_TYPE_EXCHANGE
								.name()), };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_PaymentDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String arr1[] = new String[] {
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_ORDERID_EXCHANGE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_CODE_EXCHANGE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_TYPE_EXCHANGE
								.name()) };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMServiceDP_onholdDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String arr1[] = new String[] {
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_ONHOLD_ORDERID
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_RESP_CODE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_CODE_ONHOLD
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.SUCCESS_STATUS_TYPE
								.name()),
				propertyReader
						.getPropertyValue(AllServiceHelper.STATUS_TOTAL_COUNT_ONHOLD
								.name()) };
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMERPServiceDP_OrderDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] dtr1 = new String[] { "Start", "sortBy", "sortOrder",
				"fetchSize", "isWare" };

		Object ftr[] = null;
		try {
			ftr = Commons.getSubRandomArray(dtr1, 3, true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str[] = new String[ftr.length];

		for (int i = 0; i < str.length; i++) {
			str[i] = ftr[i].toString();
			System.out.println(str[i]);
		}
		String Start = "start";
		String[] value1 = { "0", "1", "2", "3", "4", "5" };
		String start = getKeyValue(Start, value1);
		// System.out.println("From DP  :"+getKeyValue(start, values));

		String sortOrder = "sortOrder";
		String[] value2 = { "asc", "desc" };
		String sortorder = getKeyValue(sortOrder, value2);

		String sortBy = "sortBy";
		String[] value3 = { "id" };
		String sortby = getKeyValue(sortBy, value3);

		String fs = "fetchSize";
		String[] value5 = { "1", "-1" };
		String fetchsize = getKeyValue(fs, value5);

		/*
		 * String isSKU="isSKUDetailNeeded"; //String[] value5={"true","false"};
		 * String issku=getKeyValue(isSKU,value4); String isWare =
		 * "isWarehouseDetailNeeded"; String[] value4 = { "true", "false" };
		 * String isware = getKeyValue(isWare, value4);
		 * 
		 * String islogistic="isLogisticDetailNeeded"; String
		 * islogis=getKeyValue(islogistic,value4);
		 * 
		 * StringBuffer test1 = new StringBuffer(); if(ArrayUtils.contains(ftr,
		 * "Start"))
		 * test1.append("start="+start[Commons.randomNumberUptoLimit(start
		 * .length)]+"&"); if(ArrayUtils.contains(ftr, "sortOrder"))
		 * test1.append
		 * ("sortOrder="+sortOrder[Commons.randomNumberUptoLimit(sortOrder
		 * .length)]+"&"); if(ArrayUtils.contains(ftr, "IsWare"))
		 * test1.append("isWarehouseDetailNeeded="
		 * +trueOrFalse[Commons.randomNumberUptoLimit(trueOrFalse.length)]+"&");
		 */
		
		
		String login1= "q=login.eq:eshita.singh@myntra.com";
		String finalstring2 = login1 +"&"+fetchsize;
		String finalstring1 = start + "&" + sortby + "&" + sortorder + "&" + finalstring2;
		
		String[] gtr= new String[] {finalstring1};
		Object[][] dataSet = new Object[][] { gtr};
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	public String getKeyValue(String key, String[] value) {
		return key + "=" + value[new Random().nextInt(value.length)];
	}

	/*
	 * @DataProvider public Object[][]
	 * CRMERPServiceDP_OrderDataProvider_negativecases( ITestContext
	 * testContext{
	 * 
	 * return Toolbox.returnReducedDataSet(dataSet,
	 * testContext.getIncludedGroups(), 2, 2);
	 * 
	 * }
	 */

	@DataProvider
	public Object[][] CRMERPServiceDP_ReturnDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] dtr1 = new String[] { "returnId", "orderId" };
		String orderid = "orderId";
		String[] value1 = { "17930692" };
		String OrderId = getKeyValue(orderid, value1);
		// System.out.println("From DP  :"+getKeyValue(start, values));

		String returnid = "returnId";
		String[] value2 = { "1996046" };
		String returnId = getKeyValue(returnid, value2);

		String loginid = propertyReader
				.getPropertyValue(AllServiceHelper.VALID_USERNAME.name());
		Object ftr[] = null;
		try {
			ftr = Commons.getSubRandomArray(dtr1, 2, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String finalstring =loginid+"&"+ OrderId + "&" + returnId;
		String[] gtr = new String[]{finalstring};
		Object[][] dataSet = new Object[][] {gtr};

		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMERPServiceDP_CashbackDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "pravin.mehta@myntra.com" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMERPServiceDP_DeliveryStaffDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "42" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);
	}

	@DataProvider
	public Object[][] CRMERPServiceDP_CommonOrderIdDataProvider_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "17365640" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMRightNowServiceDP_drishtiIVROrderStatus_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "17365640" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMRightNowServiceDP_createcustomerprofile_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "eshita.singh@myntra.com" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	@DataProvider
	public Object[][] CRMRightNowServiceDP_getNEFTBankAccount_verifyAPIResponse(
			ITestContext testContext) {
		String[] arr = { "eshita.singh@myntra.com", "38" };
		Object[][] dataSet = new Object[][] { arr };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 2, 2);

	}

	/*
	 * static void test() { String[] dtr1 = new String[] {
	 * "returnId","orderId","login" }; String str=null; try { str =
	 * Commons.getCommaSeperatedValuesFromArray(dtr1, 3, false);
	 * System.out.println(Commons.getCommaSeperatedValuesFromArray(dtr1, 3,
	 * false)); } catch (InterruptedException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } StringTokenizer st=null; st = new
	 * StringTokenizer(str,",");
	 * 
	 * while(st.hasMoreTokens()){ System.out.println(st.nextToken()); } } public
	 * static void main(String args[]) { test(); }
	 */
}
