package com.myntra.apiTests.erpservices.partners.dp;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Toolbox;

public class VmsInvoiceUploadDP {

	
	@DataProvider
	public Object[][] getInvoiceDetails(ITestContext testContext) {
		String expected = "invoiceDetailResponse.status.statusCode==3001::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor invoice details retrieved successfully'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[0].createdBy=='system'";
		String[] arr1 = { expected};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider
	public Object[][] getInvoiceDetailsOnlyDate(ITestContext testContext) {
		String expected = "invoiceDetailResponse.status.statusCode==3001::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor invoice details retrieved successfully'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[0].createdBy=='system'";
		String[] arr1 = { "2015-11-01", "2015-12-16", expected};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider
	public Object[][] getInvoiceDetailsWithInvRef(ITestContext testContext) {
		String expected = "invoiceDetailResponse.status.statusCode==3001::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor invoice details retrieved successfully'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[0].createdBy=='system'";
		String expected1 = "invoiceDetailResponse.status.statusCode==3001::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor invoice details retrieved successfully'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='0'";
				
		String[] arr1 = { "2015-11-01", "2015-12-16", "87779", "5765757",expected};
		String[] arr2 = { "2015-11-01", "2015-12-16", "80779", "5765757",expected1};
		String[] arr3 = { "2015-11-01", "2015-12-16", "87779", "5705757",expected1};
		String[] arr4 = { "", "2015-12-16", "87779", "5705757",expected1};
		//String[] arr5 = { "2015-11-01", "2015-12-16", "", "5765757",expected1};
		

		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	
	@DataProvider
	public Object[][] bulkUploadInvoiceSingle(ITestContext testContext) {
		String expected = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='1'::"
				+ "invoiceDetailResponse.invoiceDetails.status=='SUCCESS'";
		String[] arr1 = {"5765757","87779","2015-11-16","3333","Blue Tie","2","20.5","41.0","10","CST","4.1","45.1","EEE",
				 expected};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
	@DataProvider
	public Object[][] bulkUploadInvoice(ITestContext testContext) {
		String expected = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='3'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='SUCCESS'";
		String[] arr1 = {"5765758","87779","2015-11-16","3331","Blue Tie","2","20.5","41.0","10","CST","4.1","45.1","EEE",
				"5765759","87780","2015-11-16","3332","Blue Tie","3","20.5","61.5","10","CST","6.15","67.65","EEE", 
				"5765760","87781","2015-11-16","3333","Black Tie","5","25","125","10","CST","12.5","137.5","EEE",expected};
		
		String expected1 = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='2'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='SUCCESS'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[2].message=='Invalid total price inclusive tax'";
		String[] arr2 = {"5765758","87779","2015-11-16","3331","Blue Tie","2","20.5","41.0","10","CST","4.1","45.1","EEE",
				"5765759","87780","2015-11-16","3332","Blue Tie","3","20.5","61.5","10","CST","6.15","67.65","EEE", 
				"5765760","87781","2015-11-16","3333","Black Tie","5","25","125","10","CST","12.5","135.5","EEE", expected1};

		String expected2 = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='1'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[0].message=='Invalid tax value'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[1].message=='Invalid total base price'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='SUCCESS'";
		String[] arr3 = {"5765758","87779","2015-11-16","3331","Blue Tie","2","20.5","41.0","10","CST","3","45","EEE",
				"5765759","87780","2015-11-16","3332","Blue Tie","4","20.5","61.5","10","CST","6.15","67.65","EEE", 
				"5765760","87781","2015-11-16","3333","Black Tie","5","25","125","10","CST","12.5","136.5","EEE", expected2};

		String expected3 = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='0'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[0].message=='Invalid tax value'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[1].message=='Invalid total base price'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[2].message=='Invalid total price inclusive tax'";
		String[] arr4 = {"5765758","87779","2015-11-16","3331","Blue Tie","5","20","100","10","CST","8","108","EEE",
				"5765759","87780","2015-11-16","3332","Blue Tie","4","20.5","61.5","8","CST","6.15","67.65","EEE", 
				"5765760","87781","2015-11-16","3333","Black Tie","10","25","250","10","CST","25","270","EEE", expected3};
		
		String expected4 = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='0'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[0].message=='Invalid Invoice date'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[1].message=='Invalid Invoice Ref'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[2].message=='Invalid PO Number'";
		String[] arr5 = {"5765758","87779","2015-1116","3331","Blue Tie","2","20.5","41.0","10","CST","4.1","45.1","EEE",
				"5765759","","2015-11-16","3332","Blue Tie","3","20.5","61.5","10","CST","6.15","67.65","EEE", 
				"","87781","2015-11-16","3333","Black Tie","5","25","125","10","CST","12.5","137.5","EEE",expected4};
		
		String expected5 = "invoiceDetailResponse.status.statusCode==3004::"
				+ "invoiceDetailResponse.status.statusMessage=='Vendor Invoice Upload Success'::"
				+ "invoiceDetailResponse.status.statusType=='SUCCESS'::"
				+ "invoiceDetailResponse.status.totalCount=='0'::"
				+ "invoiceDetailResponse.invoiceDetails[0].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[0].message=='Invalid VAN Number'::"
				+ "invoiceDetailResponse.invoiceDetails[1].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[1].message=='Invalid tax type'::"
				+ "invoiceDetailResponse.invoiceDetails[2].status=='ERROR'::"
				+ "invoiceDetailResponse.invoiceDetails[2].message=='Invalid Vendor Code'";
		String[] arr6 = {"5765758","87779","2015-11-16","","Blue Tie","2","20.5","41.0","10","CST","4.1","45.1","EEE",
				"5765759","87780","2015-11-16","3332","Blue Tie","3","20.5","61.5","10","","6.15","67.65","EEE", 
				"5765760","87781","2015-11-16","3333","Black Tie","5","25","125","10","CST","12.5","137.5","",expected5};

		
		Object[][] dataSet = new Object[][] { arr1, arr2, arr3, arr4, arr5, arr6 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 10, 11);
	}
	
	
}