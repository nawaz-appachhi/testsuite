package com.myntra.apiTests.portalservices.cms;

import org.testng.annotations.Factory;

/**
 * @author Vaishali Behere
 * 
 */

import com.myntra.apiTests.common.APINAME;

public class e2eRegressionCmsFactoryClass {
	 @Factory
	    public Object[] factoryMethod() {
	        return new Object[] { 
	                                new e2eRegressionCmsTest("351285", APINAME.FINDBYID), 
	                                new e2eRegressionCmsTest("310704", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("373908", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("322478", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("325962", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("373900", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("267511", APINAME.FINDBYID),
	                                new e2eRegressionCmsTest("351285", APINAME.FINDBYIDS)
	                            };
	    }
}
