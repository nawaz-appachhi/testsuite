package com.myntra.apiTests.portalservices.lgpServicesOnDemand;

import com.myntra.apiTests.dataproviders.LgpPrivacyDP;
import org.apache.log4j.Logger;

import com.myntra.apiTests.portalservices.lgpservices.LgpUserTests;
import com.myntra.lordoftherings.Initialize;

public class PrivacyTests extends LgpPrivacyDP {
	
	public static Initialize init = new Initialize("/Data/configuration");
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static Logger log = Logger.getLogger(LgpUserTests.class);

}
