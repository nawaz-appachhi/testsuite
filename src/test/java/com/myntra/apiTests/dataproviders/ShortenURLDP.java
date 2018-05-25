package com.myntra.apiTests.dataproviders;

import com.myntra.apiTests.portalservices.commons.CommonUtils;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.myntra.lordoftherings.Configuration;
import com.myntra.lordoftherings.Toolbox;

public class ShortenURLDP extends CommonUtils {

	String Env;
	Configuration con = new Configuration("./Data/configuration");
	public ShortenURLDP() {
		if (System.getenv("ENVIRONMENT") == null)
			Env = con.GetTestEnvironemnt().toString();
		else
			Env = System.getenv("ENVIRONMENT");
	}

	// Poll DP
	@DataProvider
	public Object[][] CreateShortCodeWithAliasUsingPayload(ITestContext testContext) {
		String[] url1 ={"production","http://www.flipkart.com/jaspo-saphire-dual-shoe-skates-combo-size-6-uk-shoe-skates-helmet-bag-foot-length-25-1-cms-age-group-12-13-years-skating-kit/p/itmedyhsdbjkjdfa?pid=KITEDYHSDRMAHCAV&PSdTajXaS71vn9ad0FkP=aZosyclPA5Hgktjtf39jrsldugMWZuE7O96I17%2B9oWkBQQ%2F8r9NCh8ZeHZUnAyqnoPFVpXaDZSc%3D&ref=L%3A8438340957206036667&srno=b_2","PSdTajXaS71vn9ad0FkP"};
		String[] url2 ={"production","http://www.shopclues.com/14fashions-designer-red-and-green-adjustable-alloy-kada-1400233.html","shopclues"};
		String[] url3 ={"production","B004G605Q8%26merchantID%3DA3C112F1UF0XAW%26ref_%3Dgfix-product-details-catalog%26storeID%3Dcomputers","amazon"};
		String[] url4 ={"production","udy-table/p/itme8zhghjvyygeg?pid=OSTE8ZHG6GFHHDYB&al=aZosyclPA5G90ZdYAmuLRcldugMWZuE7JBF1e6xrbIZWeMrV0uMGHOtJEXgCtle3ZAJEhGzOxjE%3D&ref=L%3A9121330895206436690&srno=b_2","flipkart2"};
		String[] url5 ={"production","httpsU3katOLNSS6jPkjy1w8aOOA-G-FOOTWEARAG-C422411A6CAEC5","U3katOLNSS6jPkjy1w8a"};
			
		Object[][] dataSet = new Object[][] { url1,url2,url3,url4,url5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CreateShortCodeWithoutAliasUsingPayload(ITestContext testContext) {
		String[] url1 ={"production","PSdTajXaS71vn9ad0FkPhttp://www.flipkart.com/jaspo-saphire-dual-shoe-skates-combo-size-6-uk-shoe-skates-helmet-bag-foot-length-25-1-cms-age-group-12-13-years-skating-kit/p/itmedyhsdbjkjdfa?pid=KITEDYHSDRMAHCAV&al=aZosyclPA5Hgktjtf39jrsldugMWZuE7O96I17%2B9oWkBQQ%2F8r9NCh8ZeHZUnAyqnoPFVpXaDZSc%3D&ref=L%3A8438340957206036667&srno=b_2",""};
		String[] url2 ={"production","http://www.shopclues.com/14fashions-designer-red-and-green-adjustable-alloy-kada-1PSdTajXaS71vn9ad0FkP400233.html",""};
		String[] url3 ={"production","onMTV0kOvQ79metL8KPChttps://www.amazon.in/ap/signin?_encoding=UTF8&openid.assoc_handle=inflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=900&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fgfix%2Fwelcome.html%3Fie%3DUTF8%26ASIN%3DB004G605Q8%26merchantID%3DA3C112F1UF0XAW%26ref_%3Dgfix-product-details-catalog%26storeID%3Dcomputers",""};
		String[] url4 ={"production","htonMTV0kOvQ79metL8KPCtp://www.fvonMTV0kOvQ79metL8KPClipkart.com/hometown-simply-engineered-wood-study-table/p/itme8zhghjvyygeg?pid=OSTE8ZHG6GFHHDYB&al=aZosyclPA5G90ZdYAmuLRcldugMWZuE7JBF1e6xrbIZWeMrV0uMGHOtJEXgCtle3ZAJEhGzOxjE%3D&ref=L%3A9121330895206436690&srno=b_2",""};
		String[] url5 ={"production","https://paytm.com/shop/p/a-g-footwear-red-blue-canvas-shoes-FOOA-G-FOOTWEARAG-C422411A6CAEC5",""};
			
		Object[][] dataSet = new Object[][] { url1,url2,url3,url4,url5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CreateShortCodeWithAliasUsingQueryParams(ITestContext testContext) {
		String[] url1 ={"production","onMTV0kOvQ79metL8KPC.com/jaspo-saphire-dual-shoe-skates-combo-size-6-uk-shoe-skates-helmet-bag-foot-length-25-1-cms-age-group-12-13-years-skating-kit/p/itmedyhsdbjkjdfa?pid=KITEDYHSDRMAHCAV&al=aZosyclPA5Hgktjtf39jrsldugMWZuE7O96I17%2B9oWkBQQ%2F8r9NCh8ZeHZUnAyqnoPFVpXaDZSc%3D&ref=L%3A8438340957206036667&srno=b_2","onMTV0kOvQ79metL8KPConMTV0kOvQ79metL8KPC"};
		String[] url2 ={"production","htonMTV0kOvQ79metL8KPCcom/14fashions-designer-red-and-green-adjustable-alloy-kada-1400233.html","onMTV0kOvQ79metL8KPC"};
		String[] url3 ={"production","https://wonMTV0kOvQ79metL8KPCd.assoc_handle=inflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=900&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fgfix%2Fwelcome.html%3Fie%3DUTF8%26ASIN%3DB004G605Q8%26merchantID%3DA3C112F1UF0XAW%26ref_%3Dgfix-product-details-catalog%26storeID%3Dcomputers","amazon"};
		String[] url4 ={"production","http://www.flipkart.com/hometoonMTV0kOvQ79metL8KPCwn-simply-engineered-wood-study-table/p/itme8zhghjvyygeg?pid=OSTE8ZHG6GFHHDYB&al=aZosyclPA5G90ZdYAmuLRcldugMWZuE7JBF1e6xrbIZWeMrV0uMGHOtJEXgCtle3ZAJEhGzOxjE%3D&ref=L%3A9121330895206436690&srno=b_2","flipkart2"};
		String[] url5 ={"production","onMTV0kOvQ79metL8KPCytm.com/shop/p/a-g-footwear-red-blue-canvas-shoes-FOOA-G-FOOTWEARAG-C422411A6CAEC5","paytm"};
			
		Object[][] dataSet = new Object[][] { url1,url2,url3,url4,url5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] CreateShortCodeWithoutAliasUsingQueryParams(ITestContext testContext) {
		String[] url1 ={"production","htonMTV0kOvQ79metL8KPCtp://www.flipkart.com/jaspo-saphire-dual-shoe-skates-combo-size-6-uk-shoe-skates-helmet-bag-foot-length-25-1-cms-age-group-12-13-years-skating-kit/p/itmedyhsdbjkjdfa?pid=KITEDYHSDRMAHCAV&al=aZosyclPA5Hgktjtf39jrsldugMWZuE7O96I17%2B9oWkBQQ%2F8r9NCh8ZeHZUnAyqnoPFVpXaDZSc%3D&ref=L%3A8438340957206036667&srno=b_2",""};
		String[] url2 ={"production","http://www.shopclues.com/14fashions-desigAUMZF342WZVfklGY6POiner-red-and-green-adjustable-alloy-kada-1400233.html",""};
		String[] url3 ={"production","https://www.amazon.in/ap/signin?_encoding=UTF8&openid.assoc_handle=inflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=900&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fgfix%2Fwelcome.html%3Fie%3DUTF8%26ASIN%3DB004G605Q8%26merchantID%3DA3C112F1UF0XAW%26ref_%3Dgfix-product-details-catalog%26storeID%3Dcomputers",""};
		String[] url4 ={"production","pHALnXSgVf3anrwLqkkspHALnXSgVf3anrwLqkks",""};
		String[] url5 ={"production","!@#$%^&*()_+}{?><~",""};
			
		Object[][] dataSet = new Object[][] { url1,url2,url3,url4,url5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
	
	@DataProvider
	public Object[][] GetURLFromCode(ITestContext testContext) {
		String[] url1 ={"production","http://www.flipkart.com/hometown-regus-low-mesh-pr-fabric-office-chair/p/itme92msevhnzb6z?pid=OSCE92MS8VAMNGQB&icmpid=reco_pp_cross_furniture_officestudytable_4&ppid=OSTE8ZHG6GFHHDYB","flipkart"};
		String[] url2 ={"production","http://www.amazon.in/Sandisk-MicroSDHC-Class-Memory-Card/dp/B001D0ROGO/ref=pd_cp_147_1?ie=UTF8&refRID=14JQ1VQR4SWMY11Q0QFR","amazon"};
		String[] url3 ={"production","https://www.amazon.in/ap/signin?_encoding=UTF8&openid.assoc_handle=inflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=900&openid.return_to=https%3A%2F%2Fwww.amazon.in%2Fgp%2Fgfix%2Fwelcome.html%3Fie%3DUTF8%26ASIN%3DB004G605Q8%26merchantID%3DA3C112F1UF0XAW%26ref_%3Dgfix-product-details-catalog%26storeID%3Dcomputers","amazon"};
		String[] url4 ={"production","http://www.flipkart.com/hometown-simply-engineered-wood-study-table/p/itme8zhghjvyygeg?pid=OSTE8ZHG6GFHHDYB&al=aZosyclPA5G90ZdYAmuLRcldugMWZuE7JBF1e6xrbIZWeMrV0uMGHOtJEXgCtle3ZAJEhGzOxjE%3D&ref=L%3A9121330895206436690&srno=b_2","flipkart"};
		String[] url5 ={"production","https://paytm.com/shop/p/a-g-footwear-red-blue-canvas-shoes-FOOA-G-FOOTWEARAG-C422411A6CAEC5","paytm"};
			
		Object[][] dataSet = new Object[][] { url1,url2,url3,url4,url5};
		return Toolbox.returnReducedDataSet(Env, dataSet, testContext.getIncludedGroups(), 1, 2);
	}
}