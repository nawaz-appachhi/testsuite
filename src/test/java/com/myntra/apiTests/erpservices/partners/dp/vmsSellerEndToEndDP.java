package com.myntra.apiTests.erpservices.partners.dp;

import com.myntra.lordoftherings.Toolbox;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;


/**
 * Author Shubham gupta
 */

public class vmsSellerEndToEndDP {

	@DataProvider
	public Object[][] createSeller(ITestContext testContext) {
		String expected = "sellerResponse.status.statusCode==1625::"
				+ "sellerResponse.status.statusMessage=='Seller has been added successfully'::"
				+ "sellerResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "VMS Seller Automation", expected};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addSellarAddress(ITestContext testContext) {
		
		String expected = "partnerContactAddressResponse.status.statusCode==1640::"
				+ "partnerContactAddressResponse.status.statusMessage=='Seller Contact Address added successfully'::"
				+ "partnerContactAddressResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "Bangalore", "Bangalore", "BILLING", "Bangalore", "India",
				"abc@gmail.com", "abc@gmail.com", "true", "notes", "SELLER",
				"9911993388", "9911993388", "560094", "Karnataka", expected};
		String[] arr2 = { "Bangalore", "Bangalore", "SHIPPING", "Bangalore", "India",
				"abc@gmail.com", "abc@gmail.com", "true", "notes", "SELLER",
				"9911993388", "9911993388", "560094", "Karnataka", expected};
		Object[][] dataSet = new Object[][] { arr1, arr2 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addCategoryManager(ITestContext testContext) {
		String expected = "sellerCategoryManagerResponse.status.statusCode==1629::"
				+ "sellerCategoryManagerResponse.status.statusMessage=='Seller Category Manager has been added successfully'::"
				+ "sellerCategoryManagerResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "true", "14", expected};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addMultiBrandToSeller(ITestContext testContext) {
		String expected = "sellerBrandResponse.status.statusCode==1636::"
				+ "sellerBrandResponse.status.statusMessage=='Seller Brand has been added successfully'::"
				+ "sellerBrandResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "7210", "Aaboli", "true", "4272", "Bahubali", "true",
				"8001", "Le Bison", "true", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addSellerWarehouse(ITestContext testContext) {
		String expected = "sellerWarehouseResponse.status.statusCode==1629::"
				+ "sellerWarehouseResponse.status.statusMessage=='Seller warehouse added successfully'::"
				+ "sellerWarehouseResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "3", "16", "ON_HAND", "45", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addSellerFinancialInformation(ITestContext testContext) {
		String expected = "SellerFinanceInformationResponse.status.statusCode==1631::"
				+ "SellerFinanceInformationResponse.status.statusMessage=='Seller finance info has been added successfully'::"
				+ "SellerFinanceInformationResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "220", "true", expected};

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addKYC(ITestContext testContext) {
		String expected = "partnerKYCDocumentResponse.status.statusCode==1639::"
				+ "partnerKYCDocumentResponse.status.statusMessage=='Seller KYC Document added successfully'::"
				+ "partnerKYCDocumentResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "TIN", "TINN123", "abcdAttach-TIN", "true", "SELLER", expected};
		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}

	@DataProvider
	public Object[][] addSellerPaymentConfiguration(ITestContext testContext) {
		String expected = "sellerPaymentConfigurationResponse.status.statusCode==1633::"
				+ "sellerPaymentConfigurationResponse.status.statusMessage=='Seller payment configuration has been added successfully'::"
				+ "sellerPaymentConfigurationResponse.status.statusType=='SUCCESS'";
		String[] arr1 = { "true", "200.0", "33", "2", "ON_ORDER_DELIVERED", expected };

		Object[][] dataSet = new Object[][] { arr1 };
		return Toolbox.returnReducedDataSet(dataSet,
				testContext.getIncludedGroups(), 5, 6);
	}
	
}
