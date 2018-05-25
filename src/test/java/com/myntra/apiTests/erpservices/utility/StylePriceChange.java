package com.myntra.apiTests.erpservices.utility;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.boromir.DBUtilities;

import com.myntra.test.commons.testbase.BaseTest;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;


import java.sql.SQLException;
import java.util.HashMap;

public class StylePriceChange extends BaseTest{
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(StylePriceChange.class);
	APIUtilities apiUtil = new APIUtilities();
	
	public void UpdatePriceInDB(String styleId, String price) throws SQLException{
		String updateCMS = "update mk_product_style set price ="+price+" where id = "+styleId+";";
		DBUtilities.exUpdateQuery(updateCMS, "myntra");
	}
	
	//Hitting api's multiple time cozz sometime we need to do so for make the changes instantly otherwise it takes natural cache refresh time which is 24 hr.
	@Test(groups = { "Regression" }, priority = 0)
	public void changeMRP() throws SQLException {
		
		
		String StyleId = System.getenv("StyleId");
		String Price = System.getenv("Price");
		
		if(StyleId == null || StyleId.isEmpty()){
			StyleId = "1197336";
		}
		
		if(Price == null || Price.isEmpty()){
			Price = "3";
		}
		UpdatePriceInDB(StyleId, Price);
		clearCMSCache(StyleId);
		makeStyleLive(StyleId);
		reindexStyle(StyleId);
		clearCMSCache(StyleId);
		clearCMSCache(StyleId);
	}

	public void makeStyleLive(String styleId) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE, APINAME.GETPRODUCTBYID,
				init.Configurations,PayloadType.JSON, new String[] { styleId },
				 PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic WVhCcFlXUnRhVzQ2YlRGOnVkSEpoVWpCamEyVjBNVE1oSXc9PQ==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String expected = "status.statusType=='SUCCESS'";
		APIUtilities.validateResponse("json", res, expected);
	}
	
	public void clearCMSCache(String styleId) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_CATALOGSERVICE, APINAME.CLEARCACHEFORPRODUCT,
				init.Configurations,PayloadType.JSON, new String[] { styleId },
				 PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic WVhCcFlXUnRhVzQ2YlRGOnVkSEpoVWpCamEyVjBNVE1oSXc9PQ==");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String expected = "status.statusMessage=='Successfully cleared cache for style id "+styleId+"'";
		APIUtilities.validateResponse("json", res, expected);
	}
	
	public void reindexStyle(String styleId) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_STYLEAPI, APINAME.DOSTYLEINDEXBYSTYLEID,
				init.Configurations,PayloadType.JSON, new String[] { styleId },
				 PayloadType.JSON);
		HashMap getParam = new HashMap();
		getParam.put("Authorization", "");
		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		String res = req.respvalidate.returnresponseasstring();
		AssertJUnit.assertEquals(200, req.response.getStatus());
		String expected = "status.statusType=='SUCCESS'";
		APIUtilities.validateResponse("json", res, expected);
	}
	
	
	
}
