package com.myntra.apiTests.erpservices.marketplace;

import java.util.HashMap;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.OrchUnicomDP;
import com.myntra.apiTests.erpservices.partners.Tests.PartnerPortalService;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.apiTests.portalservices.commons.StatusNodes;
import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class OrchUnicomGetItemDetails extends OrchUnicomDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(PartnerPortalService.class);
	static String envName = "fox7";

	APIUtilities apiUtil = new APIUtilities();



	@Test(groups = { "ProdSanity" }, priority = 0,dataProvider = "getItemDetails")
	public void getItemDetails_Unicom(
			 String paramStatusMessage ,
			String paramStatusType)
	 {
		MyntraService service = Myntra.getService(
				ServiceType.ERP_UNICOM,
				APINAME.GETITEMDETAILS, init.Configurations, PayloadType.JSON,
				new String[] {  paramStatusMessage,
						paramStatusType }, PayloadType.JSON);

		HashMap getParam = new HashMap();
		getParam.put("Authorization", "Basic U3lzdGVtflN5c3RlbTpTeXN0ZW0");

		System.out.println(service.URL);
		RequestGenerator req = new RequestGenerator(service, getParam);
		System.out.println(req.respvalidate.returnresponseasstring());
		AssertJUnit.assertEquals(200, req.response.getStatus());

		
		String responseStatusMessage = req.respvalidate.NodeValue(
				StatusNodes.STATUS_MESSAGE.toString(), false).replace("\"", "");
		String responseStatusType = req.respvalidate.NodeValue(
				StatusNodes.STATUS_TYPE.toString(), false).replace("\"", "");

		

		AssertJUnit.assertTrue(
				"Load Vendor Data statusMessage is invalid, Expected: <"
						+ paramStatusMessage + "> but Actual: <"
						+ responseStatusMessage + ">",
				responseStatusMessage.equals(paramStatusMessage));

		AssertJUnit.assertTrue(
				"Load Vendor Data statusType is invalid, Expected: <"
						+ paramStatusType + "> but Actual: <"
						+ responseStatusType + ">",
				responseStatusType.equals(paramStatusType));

	 }
}