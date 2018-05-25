package com.myntra.apiTests.portalservices.cms;

import com.myntra.apiTests.common.APINAME;
import com.myntra.apiTests.common.Myntra;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.dataproviders.CMSAPIDP;
import com.myntra.apiTests.portalservices.lgpservices.LgpServicesHelper;
import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.lordoftherings.gandalf.APIUtilities;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class e2eRegressionCmsMultiParamTest extends CMSAPIDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsMultiParamTest.class);
	static APIUtilities apiUtil = new APIUtilities();
	static LgpServicesHelper lgpServiceHelper = new LgpServicesHelper();
	static APIUtilities utilities = new APIUtilities();
	String env = init.Configurations.GetTestEnvironemnt().toString();
	static String xid;
	public static String versionSpecification;
	public static HashMap<String, String> headers = new HashMap<String, String>();
	String JENKINSJOBNAME = System.getenv("JENKINSJOBNAME");
	String botname = JENKINSJOBNAME + "Started";
	String botname1 = JENKINSJOBNAME + " Inprogress";
	String botname2 = JENKINSJOBNAME + " Completed";
	String style = System.getenv("style");
	String slackuser = System.getenv("slackuser");
	String errorReason;
	String styles = System.getenv("styles");
	String singlestyle = System.getenv("singlestyle");
	String multiplestyle = System.getenv("multiplestyle");
	String productId;
	String searchProductId;
	String title;
	String searchTitle;
	String styleType;
	String price;
	String searchPrice;
	String searchStyleType;
	String codEnabled;
	String searchCodEnabled;
	String productOptionsId;
	String productOptionsname;
	String productOptionsvalue;
	String productOptionsisActive;
	String productOptionsprice;
	String productOptionsskuId;
	String productOptionsactive;
	String searchProductOptionsId;
	String searchProductOptionsname;
	String searchProductOptionsvalue;
	String searchProductOptionsisActive;
	String searchProductOptionsprice;
	String searchProductOptionsskuId;
	String searchProductOptionsactive;
	String articleNumber;
	String productTags;
	String productDisplayName;
	String listViewName;
	String variantName;
	String myntraRating;
	String addDate;
	String modifiedDate;
	String articleTypeId;
	String articleTypefilterOrder;
	String articleTypetypeName;
	String articleTypetypeCode;
	String articleTypeisActive;
	String articleTypeisSocialSharingEnabled;
	String articleTypeisJewellery;
	String articleTypeisReturnable;
	String articleTypeisExchangeable;
	String articleTypeisLarge;
	String articleTypeisHazmat;
	String articleTypeisTryAndBuyEnabled;
	String articleTypepickupEnabled;
	String articleTypefragile;
	String searchArticleNumber;
	String searchProductTags;
	String searchProductDisplayName;
	String searchlistViewName;
	String searchVariantName;
	String searchMyntraRating;
	String searchAddDate;
	String searchModifiedDate;
	String searchArticleTypeId;
	String searchArticleTypefilterOrder;
	String searchArticleTypetypeName;
	String searchArticleTypetypeCode;
	String searchArticleTypeisActive;
	String searchArticleTypeisSocialSharingEnabled;
	String searchArticleTypeisJewellery;
	String searchArticleTypeisReturnable;
	String searchArticleTypeisExchangeable;
	String searchArticleTypeisLarge;
	String searchArticleTypeisHazmat;
	String searchArticleTypeisTryAndBuyEnabled;
	String searchArticleTypepickupEnabled;
	String searchArticleTypefragile;
	String subCatagoryId;
	String subCatagoryfilterOrder;
	String subCatagorytypeName;
	String subCatagoryisActive;
	String subCatagoryisSocialSharingEnabled;
	String subCatagoryisJewellery;
	String subCatagoryisReturnable;
	String subCatagoryisExchangeable;
	String subCatagoryisLarge;
	String subCatagoryisHazmat;
	String subCatagoryisTryAndBuyEnabled;
	String subCatagorypickupEnabled;
	String subCatagoryfragile;
	String searchsubCatagoryId;
	String searchsubCatagoryfilterOrder;
	String searchsubCatagorytypeName;
	String searchsubCatagoryisActive;
	String searchsubCatagoryisSocialSharingEnabled;
	String searchsubCatagoryisJewellery;
	String searchsubCatagoryisReturnable;
	String searchsubCatagoryisExchangeable;
	String searchsubCatagoryisLarge;
	String searchsubCatagoryisHazmat;
	String searchsubCatagoryisTryAndBuyEnabled;
	String searchsubCatagorypickupEnabled;
	String searchsubCatagoryfragile;
	String masterCatagoryId;
	String masterCatagoryfilterOrder;
	String masterCatagorytypeName;
	String masterCatagorytypeCode;
	String masterCatagoryisActive;
	String masterCatagoryisSocialSharingEnabled;
	String masterCatagoryisJewellery;
	String masterCatagoryisReturnable;
	String masterCatagoryisExchangeable;
	String masterCatagoryisLarge;
	String masterCatagoryisHazmat;
	String masterCatagoryisTryAndBuyEnabled;
	String masterCatagorypickupEnabled;
	String masterCatagoryfragile;
	String searchmasterCatagoryId;
	String searchmasterCatagoryfilterOrder;
	String searchmasterCatagorytypeName;
	String searchmasterCatagorytypeCode;
	String searchmasterCatagoryisActive;
	String searchmasterCatagoryisSocialSharingEnabled;
	String searchmasterCatagoryisJewellery;
	String searchmasterCatagoryisReturnable;
	String searchmasterCatagoryisExchangeable;
	String searchmasterCatagoryisLarge;
	String searchmasterCatagoryisHazmat;
	String searchmasterCatagoryisTryAndBuyEnabled;
	String searchmasterCatagorypickupEnabled;
	String searchmasterCatagoryfragile;
	String productTypeId;
	String productTypeName;
	String productTypecodEnabled;
	String productTypeVat;
	String productTypeproductGroupId;
	String productTypeproductGroupname;
	String searchproductTypeId;
	String searchproductTypeName;
	String searchproductTypecodEnabled;
	String searchproductTypeVat;
	String searchproductTypeproductGroupId;
	String searchproductTypeproductGroupname;

	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		for(int i=0; i<CMSAPIDP.dp_styles.length; i++){
			RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { CMSAPIDP.dp_styles[i] });
			log.info("response = " + req.returnresponseasstring());
			AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
		}
	}
	
	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "multiplestyles", priority = 68, description = "Verify Get Findby Ids Test")
	public void verifyGetFindbyIds(String styles) throws JSONException {
		e2eRegressionCmsHelper.getFindByIdsCMS(styles);

	}

	@Test(groups = { "e2eCMSRegression" }, dataProvider = "multiplestyles", priority = 69, description = "Verify Get Search Ids Test")
	public void verifyGetSearchIds(String styles) throws JSONException {
		e2eRegressionCmsHelper.getSearchForMultipleStylesCMS(styles);

	}

	//@Test(groups = { "e2eCMSRegression" }, dataProvider = "singlestyle1", priority = 71, description = "Verify Get Search Id API for multiple styles from Dataprovider")
	public void verifyGetSearchIdforMultipleStylefromDP(String singlestyle1) {
		MyntraService service = Myntra.getService(ServiceType.CMS_CATALOG,
				APINAME.SEARCHFORSTYLE, init.Configurations, PayloadType.JSON,
				new String[] { singlestyle1 }, PayloadType.JSON);
		log.info(service.Payload);
		log.info(service.URL);
		HashMap getParam = new HashMap();
		getParam.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req = new RequestGenerator(service, getParam);
		log.info(service.URL);
		log.info("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		searchProductId = e2eRegressionCmsHelper.getNodeValue(req, "data.productId", true);
		searchTitle = e2eRegressionCmsHelper.getNodeValue(req, "data.title", true);
		searchPrice = e2eRegressionCmsHelper.getNodeValue(req, "data.price", true);
		searchStyleType = e2eRegressionCmsHelper.getNodeValue(req, "data.styleType", true);
		searchCodEnabled = e2eRegressionCmsHelper.getNodeValue(req, "data.codEnabled", true);
		searchProductOptionsId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.id", true);
		searchProductOptionsname = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.name", true);
	}
	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
