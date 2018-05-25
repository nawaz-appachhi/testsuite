package com.myntra.apiTests.portalservices.all.discountService;

import java.io.IOException;

import com.myntra.apiTests.common.Myntra;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.dataproviders.discountService.DiscountServiceDP;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DiscountServiceTests extends DiscountServiceDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DiscountServiceTests.class);
	APIUtilities apiUtil = new APIUtilities();
	

	@Test(groups = { "" })
	public void DiscountService_getAllDiscounts() {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE, APINAME.GETALLDISCOUNTS,
				init.Configurations);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getAllDiscounts is not working", 200,
				req.response.getStatus());
	}

	@Test(groups = { "" }, dataProvider = "getDiscountByIdPositiveCasesDataProvider")
	public void DiscountService_getDiscountById_positiveCases(String discountid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE, APINAME.GETDISCOUNTBYID,
				init.Configurations, PayloadType.JSON,
				new String[] { discountid }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		System.out.println(service.URL);
		log.info(service.URL);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals("getDiscountById is not working", 200,
				req.response.getStatus());

	}

	@Test(groups = { "" }, dataProvider = "getDiscountByIdNegativeCasesDataProvider")
	public void DiscountService_getDiscountById_negativeCases(String discountid) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE, APINAME.GETDISCOUNTBYID,
				init.Configurations, PayloadType.JSON,
				new String[] { discountid }, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		System.out.println("Status: "+req.response.getStatus());
		AssertJUnit.assertEquals("Status code does not match", 404,
				req.response.getStatus());
	}

	@Test(dataProvider = "createFlatDiscountDataProvider")
	public void DiscountService_createFlatDiscount(String expiredDate ,
			String startDate , String percent, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE, APINAME.CREATEFLATDISCOUNT,
				init.Configurations, new String[] { expiredDate,startDate, 
						percent, styleID }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	// 4 combinations can be created - Buy Count- Get Amount, Buy Count - Get
	// Percent, Buy Amount - Get Amount, Buy Amount - Get Percent
	@Test(dataProvider = "createFlatConditionalDiscountDataProvider")
	public void DiscountService_createFlatConditionalDiscount(
			String expiredDate, String startDate, String buyCount,
			String amount, String styleID) throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE,
				APINAME.CREATEFLATCONDITIONAL, init.Configurations,
				new String[] { expiredDate, startDate, buyCount, amount,
						styleID }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(dataProvider = "createFreeItemDiscountDataProvider")
	public void DiscountService_createFreeItemDiscount(String expiredDate,
			String startDate, String buyCount, String amount, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE,
				APINAME.CREATEFLATCONDITIONAL, init.Configurations,
				new String[] { expiredDate, startDate, buyCount, amount,
						styleID }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(dataProvider = "createBuyXGetYDiscountDataProvider")
	public void DiscountService_createBuyXGetYDiscount(String expiredDate,
			String startDate, String buyCount, String amount, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE,
				APINAME.CREATEBUYXGETYDISCOUNT, init.Configurations,
				new String[] { expiredDate, startDate, buyCount, amount,
						styleID }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(dataProvider = "createCartLevelDiscountDataProvider")
	public void DiscountService_createCartLevelDiscount(String expiredDate,
			String startDate, String buyAmount, String percent, String brand,
			String brand1) throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE,APINAME.CREATECARTLEVELDISCOUNT, init.Configurations,
				new String[] { expiredDate, startDate, buyAmount, percent,
						brand, brand1 }, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

	@Test(dataProvider = "updateDiscountDataProvider")
	public void DiscountService_updateDiscount(String discountID,
			String discountName, String startDate, String expiredDate,
			String styleID) throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICE,
				APINAME.UPDATEDISCOUNT, init.Configurations,
				new String[] { discountID, discountName, startDate, expiredDate,
						styleID}, PayloadType.JSON, PayloadType.JSON);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountID);
		RequestGenerator req = new RequestGenerator(service);
		log.info(service.URL);
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
	}

}
