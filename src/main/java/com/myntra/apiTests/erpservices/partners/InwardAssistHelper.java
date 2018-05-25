package com.myntra.apiTests.erpservices.partners;

import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import com.myntra.apiTests.SERVICE_TYPE;
import com.myntra.apiTests.erpservices.Constants;
import com.myntra.inwardassist.client.response.BusinessLineResponse;
import com.myntra.inwardassist.client.response.CapacityDistributionResponse;
import com.myntra.inwardassist.client.response.CapacityResponse;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.test.commons.service.HTTPMethods;
import com.myntra.test.commons.service.HttpExecutorService;
import com.myntra.test.commons.service.Svc;

public class InwardAssistHelper {
	
	
	public CapacityDistributionResponse getCapacity(String name) throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.INWARDASSISTSERVICE_PATH.GET_CAPACITY_DISTRIBUTION, new String[] {name}, SERVICE_TYPE.INWARDASSIST.toString(), HTTPMethods.GET, null, getInwardAssistHeader());
		CapacityDistributionResponse capacityDistributionResponse = (CapacityDistributionResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new CapacityDistributionResponse());
		return capacityDistributionResponse;
	}
	
	public CapacityResponse getAvailDates() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.INWARDASSISTSERVICE_PATH.GET_AVAILABLE_DATES, null, SERVICE_TYPE.INWARDASSIST.toString(), HTTPMethods.GET, null, getInwardAssistHeader());
		CapacityResponse capacityResponse = (CapacityResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new CapacityResponse());
		return capacityResponse;
	}
	
	public BusinessLineResponse getAllBrandType() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.INWARDASSISTSERVICE_PATH.GET_BRAND_TYPE, null, SERVICE_TYPE.INWARDASSIST.toString(), HTTPMethods.GET, null, getInwardAssistHeader());
		BusinessLineResponse businessLineResponse = (BusinessLineResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new BusinessLineResponse());
		return businessLineResponse;
	}
	
	
	public CapacityResponse downloadCapacity() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.INWARDASSISTSERVICE_PATH.DOWNLOAD_CAPACITY, null, SERVICE_TYPE.INWARDASSIST.toString(), HTTPMethods.GET, null, getInwardAssistHeader());
		CapacityResponse capacityResponse = (CapacityResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new CapacityResponse());
		return capacityResponse;
	}
	
	
	public CapacityResponse syncEs() throws JAXBException, IOException {

		Svc service = HttpExecutorService.executeHttpService(Constants.INWARDASSISTSERVICE_PATH.SYNC_ES, null, SERVICE_TYPE.INWARDASSIST.toString(), HTTPMethods.GET, null, getInwardAssistHeader());
		CapacityResponse capacityResponse = (CapacityResponse) APIUtilities.getJsontoObject(service.getResponseBody(), new CapacityResponse());
		return capacityResponse;
	}
	
	

	
	private HashMap<String,String> getInwardAssistHeader() {
    	HashMap<String, String> createOrderHeaders = new HashMap<String, String>();
		createOrderHeaders.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0=");
		createOrderHeaders.put("Content-Type", "Application/json");
		createOrderHeaders.put("Accept", "Application/json");
		return createOrderHeaders;
    }
}
