package com.myntra.apiTests.portalservices.pricingpromotionservices;

import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.dataproviders.PricingPolicyDP;
import org.apache.log4j.Logger;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;

public class PricingPolicy extends PricingPolicyDP {
	Initialize init = new Initialize("/Data/configuration");
	private static Logger logger = Logger.getLogger(PricingPolicy.class);

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression",
			"Regression" }, dataProvider = "getinternalpricingpolicy")
	public void getinternalpricingpolicy(String policyName) {
		MyntraService service = Myntra.getService(
				ServiceType.PORTAL_PRICINGPOLICY,
				APINAME.GETINTERNALPRICINGPOLICY, init.Configurations,
				PayloadType.JSON, new String[] { policyName }, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		AssertJUnit.assertEquals("Status not equal to 200", 200,
				requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" },
			dataProvider = "createInternalPolicy")
	public void createInternalPolicy(String operator, String minRGMPerItem, String oldSeasonBoost,
									 String brandInFocusBoost, String commercialType) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATEINTERNALPOLICY,
				init.Configurations,
				new String[] {operator, minRGMPerItem, oldSeasonBoost, brandInFocusBoost, commercialType},
				PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		if (requestGenerator.response.getStatus() != 200) {
			logger.error("Exception in createInternalPolicy {}"+ requestGenerator.response);
		}
		AssertJUnit.assertEquals("Status not equal to 200", 200, requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" },
			dataProvider = "createFeatureGate")
	public void createFeatureGate(String noCategoryFundingMFB, String noCategoryFundingMMP, String noCategoryFundingSOR,
								  String noCategoryFundingOR, String turnOffAutoPricingMFB, String turnOffAutoPricingMMP,
								  String turnOffAutoPricingSOR, String turnOffAutoPricingOR, String canTDGreaterThanVF,
								  String minTD, String maxTD) {
		String [] payloadParameters = {noCategoryFundingMFB, noCategoryFundingMMP, noCategoryFundingSOR,
				noCategoryFundingOR, turnOffAutoPricingMFB, turnOffAutoPricingMMP, turnOffAutoPricingSOR,
				turnOffAutoPricingOR, canTDGreaterThanVF, minTD, maxTD};
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATEFEATUREGATE,
				init.Configurations, payloadParameters , PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		if (requestGenerator.response.getStatus() == 200) {
			logger.error("Exception in createInternalPolicy {}"+ requestGenerator.response);
		}
		AssertJUnit.assertEquals("HTTP status is not 200", 200, requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" },
			dataProvider = "createFeatureGateNegativeCases")
	public void createFeatureGateNegativeCases (String noCategoryFundingMFB, String noCategoryFundingMMP,
												String noCategoryFundingSOR, String noCategoryFundingOR,
												String turnOffAutoPricingMFB, String turnOffAutoPricingMMP,
												String turnOffAutoPricingSOR, String turnOffAutoPricingOR,
												String canTDGreaterThanVF, String minTD, String maxTD) {
		String [] payloadParameters = {noCategoryFundingMFB, noCategoryFundingMMP, noCategoryFundingSOR,
				noCategoryFundingOR, turnOffAutoPricingMFB, turnOffAutoPricingMMP, turnOffAutoPricingSOR,
				turnOffAutoPricingOR, canTDGreaterThanVF, minTD, maxTD};
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATEFEATUREGATE,
				init.Configurations, payloadParameters , PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		AssertJUnit.assertEquals("The case should fail", 500, requestGenerator.response.getStatus());
	}

	@Test(groups = { "Sanity", "MiniRegression", "ExhaustiveRegression", "Regression" },
			dataProvider = "createUserRole")
	public void createUserRole (String emailId, String roles) {
		MyntraService service = Myntra.getService(ServiceType.PORTAL_PRICINGPOLICY, APINAME.CREATEUSERROLE,
				init.Configurations, new String[] {roles}, new String[]{emailId}, PayloadType.JSON, PayloadType.JSON);
		RequestGenerator requestGenerator = new RequestGenerator(service);
		AssertJUnit.assertEquals("The status is not 200", 200, requestGenerator.response.getStatus());
	}
}
