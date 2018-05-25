package com.myntra.apiTests.portalservices.DealsOfTheDayService;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.apache.log4j.Logger;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.legolas.Commons;

public class DealsOfTheDayHelper extends CommonUtils {
	Commons commonUtil = new Commons();
	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(DealsOfTheDayHelper.class);
	APIUtilities apiUtil=new APIUtilities();
}
