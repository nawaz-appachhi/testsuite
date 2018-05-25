package com.myntra.apiTests.portalservices.discountservice;

import java.io.IOException;

import com.myntra.apiTests.common.Myntra;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class DiscountServiceV2Helper {
	
	Initialize init = new Initialize("/Data/configuration");
	APIUtilities apiUtil = new APIUtilities();
	
	
	public RequestGenerator createFlatDiscount(String expiredDate,
			String startDate, String percent, String styleID)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2,
				init.Configurations, new String[] { expiredDate, startDate,
						percent, styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
	
		return new RequestGenerator(service); 
		

	}
	

	public RequestGenerator deleteDiscountFromDiscountId(String discountId)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.DELETEDISCOUNTFORDISCOUNTID,
				init.Configurations,  PayloadType.JSON, new String[] { discountId}, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
	
		return new RequestGenerator(service); 
		

	}
	
	public RequestGenerator getCurrentDiscount(String styleid,
			String mrp)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETCURRENTDISCOUNTV1,
				init.Configurations, new String[] { styleid, mrp}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
	
		return new RequestGenerator(service); 
		

	}
	
	public RequestGenerator  createFlatConditionalPercentageDiscount(
			String expiredDate, String startDate, String percentage,
			String buyamount, String styleID){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	APINAME.CREATEFLATCONDITIONALV2, init.Configurations,
	new String[] { expiredDate, startDate, percentage, buyamount,
			styleID}, PayloadType.JSON, PayloadType.JSON);
	    return new RequestGenerator(service); 

	}
	
	public RequestGenerator  createFlatConditionalAmountDiscount(
			String expiredDate, String startDate, String amount,
			String buyamount, String styleID,String buyCount){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	APINAME.CREATEFLATCONDITIONALAMOUNTV2, init.Configurations,
	new String[] { expiredDate, startDate, amount, buyamount,
			styleID,buyCount}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat condtion paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	
	
	public RequestGenerator  createFreeItemDiscount(String expiredDate,
			String startDate,String FreeItem, String styleID) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEFREEITEMDISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate,FreeItem,
						styleID}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Freeitem discount  paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	
	
	public RequestGenerator  createBuy1Get1Discount(String expiredDate,
			String startDate,String styleID, String styleID1) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEBUY1GET2DISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate,
						styleID,styleID1}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("B1G1  paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	
	public RequestGenerator  createBuy2Get2Discount(String expiredDate,
			String startDate,String styleID, String styleID1,String styleID2,String styleID3) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2,
				APINAME.CREATEBUY2GET4DISCOUNTV2, init.Configurations,
				new String[] { expiredDate, startDate,
						styleID,styleID1,styleID2,styleID3}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("B2G2  paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	
	public RequestGenerator  createFlatConditionalPercentWithBuyCountDiscount(
			String expiredDate, String startDate, String percent, String styleID,String buyCount){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	APINAME.CREATEFLATCONDITIONALPERCENTWITHBUYCOUNTV2, init.Configurations,
	new String[] { expiredDate, startDate, percent,styleID,buyCount}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat condtion paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	public RequestGenerator  createFlatConditionalAmountWithBuyCountDiscount(
			String expiredDate, String startDate, String amount, String styleID,String buyCount){
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2,
	APINAME.CREATEFLATCONDITIONALAMOUNTWITHBUYCOUNTV2, init.Configurations,
	new String[] { expiredDate, startDate, amount,styleID,buyCount}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat condtion paylaod \n" + service.Payload);
	    return new RequestGenerator(service); 

	}
	
	
	public RequestGenerator getDiscountFromDiscountId(String discountId)
			throws IOException {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.GETDISCOUNTFORDISCOUNTID, init.Configurations);
		service.URL = apiUtil.prepareparameterizedURL(service.URL, discountId);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
	    return new RequestGenerator(service); 
		

	}
	
	public RequestGenerator createFlatDiscountMultiStyleid(String expiredDate,
			String startDate, String percent, String styleID, String styleID1, String styleID2,String styleID3)
			throws IOException {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_DISCOUNTSERVICEV2, APINAME.CREATEFLATDISCOUNTV2MULTISTYLEID,
				init.Configurations, new String[] { expiredDate, startDate,
						percent, styleID,styleID1,styleID2,styleID3}, PayloadType.JSON, PayloadType.JSON);
		System.out.println("Flat discount Payload -- \n" + service.Payload);
	
		return new RequestGenerator(service); 
		

	}
	
	//Changes in DiscountServiceV2 - Addition of discountFunding,discountLimit,fundingPerentage attributes actionOnCart and getDiscountFromDiscountRuleID api's.
	//14
	
	
}
