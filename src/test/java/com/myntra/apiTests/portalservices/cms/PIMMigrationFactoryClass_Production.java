package com.myntra.apiTests.portalservices.cms;

import org.testng.annotations.Factory;

/**
 * @author Vaishali Behere
 * 
 */

import com.myntra.apiTests.common.APINAME;

/**
 * If you are changing style id i.e. 1198379 here, make sure to update the same
 * style id following payload files: cmsputstylev2_<articletype>,
 * cmsputstyle_<articletype>, cmsv2changearticletype_<articletype>,
 * cmschangearticletype_<articletype>
 **/

public class PIMMigrationFactoryClass_Production {
	@Factory
	public Object[] factoryMethod() {
		return new Object[] {
				new PIMMigrationTest("12348", "Jeans", "70",
						APINAME.CMSPUTSTYLEV2_JEANS_PROD, APINAME.CMSPUTSTYLE_JEANS_PROD,
						APINAME.CMSV2CHANGEARTICLETYPE_JEANS_PROD,
						APINAME.CMSCHANGEARTICLETYPE_JEANS_PROD, "90", "Tshirts",
						"44", "Bag", "Topwear", "Apparel", "Bags",
						"Accessories"),
				new PIMMigrationTest("12349", "Tshirts", "90",
						APINAME.CMSPUTSTYLEV2_TSHIRTS_PROD,
						APINAME.CMSPUTSTYLE_TSHIRTS_PROD,
						APINAME.CMSV2CHANGEARTICLETYPE_TSHIRTS_PROD,
						APINAME.CMSCHANGEARTICLETYPE_TSHIRTS_PROD, "70", "Jeans",
						"44", "Bag", "Bottomwear", "Apparel", "Bags",
						"Accessories") 
				};
	}
}