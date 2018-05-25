package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.erpservices.marketplace.RmsGatePassEndToEnd;
import com.myntra.apiTests.erpservices.partners.dp.VmsInvoiceUploadDP;
import com.myntra.lordoftherings.Initialize;

import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.HashMap;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


/*
 * Author Shubham gupta
 */

public class VmsInvoiceUpload extends VmsInvoiceUploadDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(RmsGatePassEndToEnd.class);
	static String envName = "fox8";

	APIUtilities apiUtil = new APIUtilities();
	
	
	/** API Name - Get Invoice Details (Without date/pono/invoice ref) **/

	@Test(groups = { "Smoke","Regression" }, priority = 0, dataProvider = "getInvoiceDetails")
	public void VMS_InvoiceDetails(String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETINVOICEDETAILS,
				init.Configurations,PayloadType.JSON, new String[] { expectedResult},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);
	}
	
	
	/** API Name - Get Invoice Details with only date **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "getInvoiceDetailsOnlyDate")
	public void VMS_InvoiceDetailsOnlyDate(String from, String to, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETINVOICEDETAILSONLYDATE,
				init.Configurations,PayloadType.JSON, new String[] { from, to, expectedResult},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	/** API Name - Get Invoice Details with date and Po and invoice ref number number  **/

	@Test(groups = { "Regression" }, priority = 0, dataProvider = "getInvoiceDetailsWithInvRef")
	public void VMS_InvoiceDetailsWithInvRef(String from, String to, String invRef, String poNo, String expectedResult) {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER, APINAME.GETINVOICEDETAILSWITHINVREF,
				init.Configurations,PayloadType.JSON, new String[] { from, to, invRef, poNo, expectedResult},  PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);	
	}
	
	
	// Bulk upload invoice single invoice (Includes tax calculation)
		@Test(groups = { "Regression" }, priority = 0, dataProvider = "bulkUploadInvoiceSingle")
		public void VMS_bulkUploadInvoiceSingle( String poNumber, String invoiceRef, String invoiceDate, String vanNumber, String productDescription,
				String quantity, String unitPrice, String totalBasePrice, String taxPercentage, String taxType, String taxValue, 
				String totalIncludingTax, String vendorCode, String expectedResult) {
					
			
			MyntraService service = Myntra.getService(
					ServiceType.ERP_MARKETPLACESELLER,
					APINAME.BULKUPLOADINVOICESINGLE, init.Configurations,
					new String[] {  poNumber, invoiceRef, invoiceDate, vanNumber, productDescription,
						 quantity, unitPrice, totalBasePrice, taxPercentage, taxType, taxValue, 
						 totalIncludingTax, vendorCode, expectedResult}, PayloadType.JSON, PayloadType.JSON);
			HashMap getParam = new HashMap();
			getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
			System.out.println(service.URL);
			RequestGenerator req = new RequestGenerator(service, getParam);
			String res = req.respvalidate.returnresponseasstring();
			System.out.println(res);
			AssertJUnit.assertEquals(200, req.response.getStatus());
			APIUtilities.validateResponse("json", res, expectedResult);
			
		}
	
	// Bulk upload invoice with multi invoice(Includes tax calculation)
	@Test(groups = { "Regression" }, priority = 0, dataProvider = "bulkUploadInvoice")
	public void VMS_bulkUploadInvoice( String poNumber, String invoiceRef, String invoiceDate, String vanNumber, String productDescription,
			String quantity, String unitPrice, String totalBasePrice, String taxPercentage, String taxType, String taxValue, 
			String totalIncludingTax, String vendorCode, String poNumber1, String invoiceRef1, String invoiceDate1, 
			String vanNumber1, String productDescription1, String quantity1, String unitPrice1, String totalBasePrice1, 
			String taxPercentage1, String taxType1, String taxValue1, 
			String totalIncludingTax1, String vendorCode1, String poNumber2, String invoiceRef2, String invoiceDate2, 
			String vanNumber2, String productDescription2,String quantity2, String unitPrice2, String totalBasePrice2,
			String taxPercentage2, String taxType2, String taxValue2, 
			String totalIncludingTax2, String vendorCode2,String expectedResult) {
						
		MyntraService service = Myntra.getService(
				ServiceType.ERP_MARKETPLACESELLER,
				APINAME.BULKUPLOADINVOICE, init.Configurations,
				new String[] {  poNumber, invoiceRef, invoiceDate, vanNumber, productDescription,
					 quantity, unitPrice, totalBasePrice, taxPercentage, taxType, taxValue, 
					 totalIncludingTax, vendorCode, poNumber1, invoiceRef1, invoiceDate1, 
					 vanNumber1, productDescription1, quantity1, unitPrice1, totalBasePrice1, 
					 taxPercentage1, taxType1, taxValue1, 
					 totalIncludingTax1, vendorCode1, poNumber2, invoiceRef2, invoiceDate2, vanNumber2, productDescription2,
					 quantity2, unitPrice2, totalBasePrice2, taxPercentage2, taxType2, taxValue2, 
					 totalIncludingTax2, vendorCode2, expectedResult}, PayloadType.JSON, PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		System.out.println(res);
		AssertJUnit.assertEquals(200, req.response.getStatus());
		APIUtilities.validateResponse("json", res, expectedResult);		
	}
	
	
	
	
	
	
}
