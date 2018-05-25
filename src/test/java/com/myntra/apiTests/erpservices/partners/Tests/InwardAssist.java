package com.myntra.apiTests.erpservices.partners.Tests;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.myntra.apiTests.erpservices.partners.InwardAssistHelper;
import com.myntra.apiTests.erpservices.partners.dp.InwardDP;
import com.myntra.inwardassist.client.response.BusinessLineResponse;
import com.myntra.inwardassist.client.response.CapacityDistributionResponse;
import com.myntra.inwardassist.client.response.CapacityResponse;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.sellers.response.SellerResponse;
import com.myntra.test.commons.testbase.BaseTest;

public class InwardAssist extends BaseTest {
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(InwardAssist.class);
	APIUtilities apiUtil = new APIUtilities();

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = InwardDP.class, dataProvider = "getCapacity", description = "")
	public void Inward_getCapacity(String name)
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		InwardAssistHelper inwardAssistHelper = new InwardAssistHelper();
		CapacityDistributionResponse capacityDistributionResponse = inwardAssistHelper.getCapacity(name);
		Assert.assertEquals(capacityDistributionResponse.getStatus().getStatusCode(), 3);
		Assert.assertEquals(capacityDistributionResponse.getStatus().getStatusMessage().toString(), "Success");
		Assert.assertEquals(capacityDistributionResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = InwardDP.class, dataProvider = "", description = "")
	public void Inward_getAvailDates()
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		InwardAssistHelper inwardAssistHelper = new InwardAssistHelper();
		CapacityResponse capacityResponse = inwardAssistHelper.getAvailDates();
		Assert.assertEquals(capacityResponse.getStatus().getStatusCode(), 3);
		Assert.assertEquals(capacityResponse.getStatus().getStatusMessage().toString(), "Success");
		Assert.assertEquals(capacityResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = InwardDP.class, dataProvider = "", description = "")
	public void Inward_getAllBrandType()
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		InwardAssistHelper inwardAssistHelper = new InwardAssistHelper();
		BusinessLineResponse businessLineResponse = inwardAssistHelper.getAllBrandType();
		Assert.assertEquals(businessLineResponse.getStatus().getStatusCode(), 3);
		Assert.assertEquals(businessLineResponse.getStatus().getStatusMessage().toString(), "Success");
		Assert.assertEquals(businessLineResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = InwardDP.class, dataProvider = "", description = "")
	public void Inward_DownloadCapacity()
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		InwardAssistHelper inwardAssistHelper = new InwardAssistHelper();
		CapacityResponse capacityResponse = inwardAssistHelper.downloadCapacity();
		Assert.assertEquals(capacityResponse.getStatus().getStatusCode(), 3);
		Assert.assertEquals(capacityResponse.getStatus().getStatusMessage().toString(), "Success");
		Assert.assertEquals(capacityResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

	@Test(groups = {
			"Regression" }, priority = 0, dataProviderClass = InwardDP.class, dataProvider = "", description = "")
	public void Inward_syncES()
			throws SQLException, JAXBException, JsonParseException, JsonMappingException, IOException {

		InwardAssistHelper inwardAssistHelper = new InwardAssistHelper();
		CapacityResponse capacityResponse = inwardAssistHelper.syncEs();
		Assert.assertEquals(capacityResponse.getStatus().getStatusCode(), 3);
		Assert.assertEquals(capacityResponse.getStatus().getStatusMessage().toString(), "Success");
		Assert.assertEquals(capacityResponse.getStatus().getStatusType().toString(), "SUCCESS");

	}

}
