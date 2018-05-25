package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.annotations.DataProvider;

/**
 * @author Achal
 * 
 */
public class CMSAPIDP extends CommonUtils {
	public static String[] dp_styles = new String [] {"351285","310704","373908","322478","325962","373900","267511"};
	
	
	@DataProvider
	public static Object[][] singlestyle1() {
		return new Object[][] { new Object[] { dp_styles[0] },
				new Object[] { dp_styles[1] }, new Object[] { dp_styles[2] },
				new Object[] { dp_styles[3] }, new Object[] { dp_styles[4] },
				new Object[] { dp_styles[5] }, new Object[] { dp_styles[6] } };
	}
	
	@DataProvider
	public static Object[][] singlestyle2() {
		return new Object[][] {  new Object[] { dp_styles[0] } };
	}
	
	@DataProvider
	public static Object[][] multiplestyles() {
		return new Object[][] {  new Object[] { "351285,310704,373908" } };
	}
	
}
