package com.myntra.apiTests.erpservices.partners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.erpservices.Constants;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;
import com.myntra.lordoftherings.gandalf.APIUtilities;

import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.test.commons.service.Svc;
import com.myntra.vms.client.code.utils.CommercialType;
import com.myntra.vms.client.entry.VendorItemMasterEntry;

import com.myntra.vms.client.response.VendorItemMasterResponse;

public class VIMHelper {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = LoggerFactory.getLogger(VIMHelper.class);
	VMSHelper vmsHelper = new VMSHelper();

	public VendorItemMasterResponse createVIM(String vendorId, String skuId, String vendorSkuCode, String skuCode,
			String styleId, String brand, CommercialType type1, CommercialType type2, CommercialType type3, String mrp,
			String unitPrice) throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		VendorItemMasterEntry vendorItemMasterEntry = new VendorItemMasterEntry();
		vendorItemMasterEntry.setVendorId(Long.parseLong(vendorId));
		vendorItemMasterEntry.setSkuId(Long.parseLong(skuId));
		vendorItemMasterEntry.setVendorSkuCode(vendorSkuCode);
		vendorItemMasterEntry.setSkuCode(skuCode);
		vendorItemMasterEntry.setStyleId(Long.parseLong(styleId));
		vendorItemMasterEntry.setBrand(brand);
		vendorItemMasterEntry.setMrp(Double.parseDouble(mrp));
		vendorItemMasterEntry.setUnitPrice(Double.parseDouble(unitPrice));
		List<CommercialType> ct = new ArrayList<>();
		ct.add(type1);
		ct.add(type2);
		ct.add(type3);

		vendorItemMasterEntry.setCommercialType(ct);

		String payload = APIUtilities.getObjectToJSON(vendorItemMasterEntry);
		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.CREATE_VIM, null,
				SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.POST, payload, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse editVIM(String vendorItemMasterId, String vendorId, String skuId,
			String vendorSkuCode, String skuCode, String styleId, String brand, CommercialType type, String mrp,
			String unitPrice) throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		VendorItemMasterEntry vendorItemMasterEntry = new VendorItemMasterEntry();
		vendorItemMasterEntry.setVendorId(Long.parseLong(vendorId));
		vendorItemMasterEntry.setSkuId(Long.parseLong(skuId));
		vendorItemMasterEntry.setVendorSkuCode(vendorSkuCode);
		vendorItemMasterEntry.setSkuCode(skuCode);
		vendorItemMasterEntry.setStyleId(Long.parseLong(styleId));
		vendorItemMasterEntry.setBrand(brand);
		vendorItemMasterEntry.setMrp(Double.parseDouble(mrp));
		vendorItemMasterEntry.setUnitPrice(Double.parseDouble(unitPrice));
		List<CommercialType> ct = new ArrayList<>();
		ct.add(type);
		vendorItemMasterEntry.setCommercialType(ct);

		String payload = APIUtilities.getObjectToJSON(vendorItemMasterEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.EDIT_VIM,
				new String[] { vendorItemMasterId }, SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.PUT, payload,
				getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse commercialType(String vendorId)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		// VendorItemMasterEntry vendorItemMasterEntry = new VendorItemMasterEntry();
		// List<CommercialType> ct = new ArrayList<>();
		// ct.add(type1);
		// ct.add(type2);
		// ct.add(type3);,\"SOR\",\"OUTRIGHT\"
		String payload = "{\"commercialTypes\":[\"JIT\"]}";
		// vendorItemMasterEntry.setCommercialType(ct);

		// String payload = APIUtilities.getObjectToJSON(vendorItemMasterEntry);

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.COMMERCIALTYPE,
				new String[] { vendorId, "getVIMByCommercialTypes" }, SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.POST,
				payload, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse skuCode(String vendorId)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		String skuCode = vmsHelper.getVendorSKUCodeFromVIM(vendorId);
		String payload = "{\"skuCodes\":[\"" + skuCode + "\"]}";

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.SKUCODE,
				new String[] { vendorId, "getVIMBySkuCodes" }, SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.POST,
				payload, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse commercialTypeAndskuCode(String vendorId, String skuCode, String commType)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		String payload = "{\"commercialType\":\"" + commType + "\",\"skuCodes\":[\"" + skuCode + "\"]}";

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.COMMERCIALTYPE_SKUCODE_VIM,
				new String[] { vendorId, "getVIMByCommercialTypeAndSkuCodes" }, SERVICE_TYPE.VIM_SVC.toString(),
				HTTPMethods.POST, payload, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse getByStyleIds(String vendorId)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		String styleId = vmsHelper.getStyleIdFromVIM(vendorId);
		String payload = "{\"styleIds\":[\"" + styleId + "\"]}";

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.GET_VIM_BY_STYLEIDS,
				new String[] { vendorId, "getVIMByStyleIds" }, SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.POST,
				payload, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse getVimByVendor(String vendorId)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.GET_VIM_BY_VENDOR,
				new String[] { vendorId + "?start=0&fetchSize=50" }, SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.GET,
				null, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	public VendorItemMasterResponse getVimById(String vendorId)
			throws JAXBException, JsonGenerationException, JsonMappingException, IOException {
		String vimId = vmsHelper.getVIMIdFromVIM(vendorId);
		Svc service = HttpExecutorService.executeHttpService(Constants.VIM_PATH.GET_VIM_BY_ID, new String[] { vimId },
				SERVICE_TYPE.VIM_SVC.toString(), HTTPMethods.GET, null, getVIMHeader());
		VendorItemMasterResponse vendorItemMasterResponse = (VendorItemMasterResponse) APIUtilities
				.getJsontoObject(service.getResponseBody(), new VendorItemMasterResponse());
		return vendorItemMasterResponse;
	}

	private HashMap<String, String> getVIMHeader() {
		HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
	}

	public String getVIMTotalJobs(String startDate, String endDate) {
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select count(`job_id`) total_jobs from vim_jit_upload_request where `created_on`  >= '" + startDate
						+ "' and  `created_on` <= '" + endDate + " 23:59:59.999';",
				"vms");
		String total_jobs = transaction_detail.get("total_jobs").toString();
		log.debug("Total Jobs :" + total_jobs);
		return total_jobs;
	}

	public String getVIMAverageJobExecutionTime(String startDate, String endDate) {
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select Round(sum(`last_modified_on`-`created_on`)/100/ count(`job_id`),2) avg_job_execution_time from vim_jit_upload_request where `created_on`  >= '"
						+ startDate + "' and  `created_on` <= '" + endDate
						+ " 23:59:59.999' and  upload_status='UPLOAD_COMPLETED';",
				"vms");
		String avg_job_execution_time = transaction_detail.get("avg_job_execution_time").toString();
		log.debug("Average Job Execution Time :" + avg_job_execution_time);

		return avg_job_execution_time;
	}

	public String getVIMAvgExecutionTimeForEachItem(String startDate, String endDate) {
		Map<String, Object> transaction_detail = DBUtilities.exSelectQueryForSingleRecord(
				"select Round((sum(`last_modified_on`-`created_on`)/(10000/entry_to_step_ratio))/count(`job_id`),2) avg_job_execution_time from vim_jit_upload_request where `created_on`  >= '"
						+ startDate + "' and  `created_on` <= '" + endDate
						+ " 23:59:59.999' and  upload_status='UPLOAD_COMPLETED';",
				"vms");
		String avg_job_execution_time = transaction_detail.get("avg_job_execution_time").toString();
		log.debug("Average Job Execution Time :" + avg_job_execution_time);

		return avg_job_execution_time;
	}

}
