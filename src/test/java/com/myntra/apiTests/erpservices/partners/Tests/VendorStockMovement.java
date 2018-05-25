package com.myntra.apiTests.erpservices.partners.Tests;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.apiTests.erpservices.partners.VendorStockMovementhelper;
import com.myntra.apiTests.erpservices.partners.dp.VendorsStockMovementDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;
import com.myntra.test.commons.testbase.BaseTest;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class VendorStockMovement extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(VendorStockMovement.class);
	APIUtilities apiUtil = new APIUtilities();
	static String envName = "fox8";
	VendorStockMovementhelper vsmhelper=new VendorStockMovementhelper();
	
	@Test(groups = { "Smoke","Regression" }, priority = 0,dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_po_base_search",
			description="Searches for PO details with Vendor Id")
	public void VSM_po_base_search(String vendor_id) throws Exception
	{	
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.PO_SEARCH,
				new String[]{"?q=vendor.id.eq:"+vendor_id},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusCode"), "1526", "PO does not exist.");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	
	@Test(groups = { "Smoke","Regression" }, priority = 0,dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_po_details_by_poid",
			description="Searches for PO details with PO Id")
	public void VSM_get_po_details_by_poid(String po_id) throws Exception
	{	
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_PO_DETAILS_BY_POID,
				new String[]{po_id},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusCode"), "1523", "PO details does not exist.");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");
	}
	
	@Test(groups = { "Smoke","Regression" }, priority = 0,dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_skus_by_poid",
			description="Searches for skus details with PO Id")
	public void VSM_get_skus_by_poid(String po_id) throws Exception
	{
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_SKUS_BY_POID,
				new String[]{po_id+"?start=0&amp;fetchSize=1"},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertNotEquals(svc.getResponseStatusType("statusCode"), "1509", "Error in fetching sku details");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");
	}
	
	
	@Test(groups = { "Smoke","Regression" }, priority = 0,dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_po_filter_attributes_by_vendorid",
			description="Filters the PO details and fetches the attributes mapped for the VendorId")
	public void VSM_get_po_filter_attributes_by_vendorid(int VendorId,String searchFilter) throws Exception
	{
		Svc svc = HttpExecutorService.executeHttpService(Constants.VENDOR_PATH.FILTER_POS, new String[]{VendorId+"/"+searchFilter},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,"",  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_RO_list_by_vendorName",
			description="Searches for RO list with VendorId")
	public void VSM_get_RO_list_by_vendorName(String VendorName,int vendor_id) throws Exception
	{
		List<Integer> db_Return_codes=vsmhelper.getReturncodesByVendorIdfromWMSdb(vendor_id);	
		
		Svc svc = HttpExecutorService.executeHttpService(Constants.WMS_PATH.GETRO, new String[]{"search?q=vendor.name.in:"+VendorName},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,"",  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(vsmhelper.getROResponseStatusCode(svc.getResponseBody()), 7129, "Return Order Retrieval Failed :");
		
		List<Integer> api_Return_codes= vsmhelper.getAPIReturnCodes(svc.getResponseBody());
		
		Collections.sort(api_Return_codes,Collections.reverseOrder());
		Collections.sort(db_Return_codes,Collections.reverseOrder());
		
		Assert.assertEquals(api_Return_codes,db_Return_codes, "Return Codes is:");

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_RO_details",
			description="Searches for RO list with RO Code")
	public void VSM_get_RO_ItemDetails_by_RO_Id(String RO_Code) throws Exception
	{		
		List<Integer> db_RO_itemIds=vsmhelper.getRejectedItemIdfromWMSdb(Long.valueOf(RO_Code));
		
		Svc svc = HttpExecutorService.executeHttpService(Constants.WMS_PATH.GETRO, new String[]{RO_Code+"/items/search"},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,"",  vsmhelper.getHeaders());
		
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");	
		Assert.assertEquals(vsmhelper.getROItemResponseStatusCode(svc.getResponseBody()), 7129, "Return Order Retrieval Failed :");
		
		List<Integer> api_RO_Item_ids= vsmhelper.getAPIReturnedItemIds(svc.getResponseBody());
		Collections.sort(api_RO_Item_ids,Collections.reverseOrder());
		Collections.sort(db_RO_itemIds,Collections.reverseOrder());
		
		Assert.assertEquals(api_RO_Item_ids,db_RO_itemIds, "RO_Item_id is:");
		
		
		

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_print_po",
			description="Prints PO data with PO code")
	public void VSM_print_po(String PO_Code) throws Exception
	{		
		Svc svc = HttpExecutorService.executeHttpService(Constants.WMS_PATH.GETRO, new String[]{"platform/pos/download/"+PO_Code},
				SERVICE_TYPE.WMS_SVC.toString(), HTTPMethods.GET,"",  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	// new PO/RO API's
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_skus_by_invoice_number",
			description="Gets all the Skus by Invoice number")
	public void VSM_get_skus_by_invoice_number(String invoiceNumber) throws Exception
	{	
		
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_SKUS_BY_INVOICENUMBER, new String[]{"?invoiceNumber="+invoiceNumber},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_skus_by_invoice_number",
			description="Gets all the Invoice details by Invoice number")
	public void VSM_get_invoice_details_by_invoice_number(String invoiceNumber) throws Exception
	{	
		
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_INVOICE_DETAILS_BY_INVOICENUMBER, new String[]{"?invoiceNumber="+invoiceNumber},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_pos_by_vendorid",enabled=false,
			description="Gets all the PO's by vendorId")
	public void VSM_get_pos_by_vendorid(String vendorId) throws Exception
	{		
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_POS_BY_VENDORID, new String[]{vendorId},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "PO does not exist.");

	}
	
	@Test(groups = { "Smoke","Regression" },dataProviderClass=VendorsStockMovementDP.class, dataProvider="VSM_get_rejectedItems_invoice_number",
			description="Gets all the PO's by vendorId")
	public void VSM_get_rejectedItems_invoice_number(String invoiceNumber) throws Exception
	{		
		Svc svc = HttpExecutorService.executeHttpService(Constants.PARTNERPORTALSERVICE_PATH.GET_REJECTED_ITEMS_INVOICENUMBER, new String[]{"?invoiceNumber="+invoiceNumber},
				SERVICE_TYPE.VENDOR_SVC.toString(), HTTPMethods.GET,null,  vsmhelper.getHeaders());
		Assert.assertEquals(svc.getResponseStatus(), 200, "API response status code is");
		Assert.assertEquals(svc.getResponseStatusType("statusCode"), "3", "API response status code is");

	}
	
	

}
