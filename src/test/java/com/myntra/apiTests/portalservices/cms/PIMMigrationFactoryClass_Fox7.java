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

public class PIMMigrationFactoryClass_Fox7 {
	@Factory
	public Object[] factoryMethod() {
		return new Object[] {
				new PIMMigrationTest("1198379", "Jeans", "70",
						APINAME.CMSPUTSTYLEV2_JEANS, APINAME.CMSPUTSTYLE_JEANS,
						APINAME.CMSV2CHANGEARTICLETYPE_JEANS,
						APINAME.CMSCHANGEARTICLETYPE_JEANS, "90", "Tshirts",
						"44", "Bag", "Topwear", "Apparel", "Bags",
						"Accessories"),
				new PIMMigrationTest("1198380", "Tshirts", "90",
						APINAME.CMSPUTSTYLEV2_TSHIRTS,
						APINAME.CMSPUTSTYLE_TSHIRTS,
						APINAME.CMSV2CHANGEARTICLETYPE_TSHIRTS,
						APINAME.CMSCHANGEARTICLETYPE_TSHIRTS, "70", "Jeans",
						"44", "Bag", "Bottomwear", "Apparel", "Bags",
						"Accessories") };
	}
}