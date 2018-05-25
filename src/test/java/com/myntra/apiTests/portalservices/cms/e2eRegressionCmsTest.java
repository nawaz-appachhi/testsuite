package com.myntra.apiTests.portalservices.cms;

import java.util.HashMap;

import com.myntra.apiTests.dataproviders.CMSAPIDP;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.myntra.lordoftherings.Initialize;
import com.myntra.lordoftherings.aragorn.pages.WebPage;
import com.myntra.apiTests.common.APINAME;
import com.myntra.lordoftherings.gandalf.MyntraService;
import com.myntra.lordoftherings.gandalf.PayloadType;
import com.myntra.lordoftherings.gandalf.RequestGenerator;
import com.myntra.apiTests.common.ServiceType;
import com.myntra.apiTests.common.Myntra;

public class e2eRegressionCmsTest extends CMSAPIDP {

	static Initialize init = new Initialize("/Data/configuration");
	static Logger log = Logger.getLogger(e2eRegressionCmsTest.class);
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
	String styleIdFromFactoryClass;
	APINAME apiName;

	public e2eRegressionCmsTest(String styleIdFromFactoryClass, APINAME apiName) {
		this.styleIdFromFactoryClass = styleIdFromFactoryClass;
		this.apiName = apiName;
	}
	
	@BeforeClass(groups = { "e2eCMSRegression" })
	public void styleClearCache(){
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.CMSCLEARCACHE, new String[] { styleIdFromFactoryClass });
		log.info("clearcache response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
					req.response.getStatus());
	}

	@BeforeMethod(alwaysRun = true)
	public void openHome() {
		log.info("Entry");
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 1, description = "Verify Get Find by Id")
	public void verifyGetFindbyId() throws JSONException {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, apiName, new String[] { styleIdFromFactoryClass });
		log.info("response = " + req.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req.response.getStatus());
		productId = e2eRegressionCmsHelper.getNodeValue(req, "data.productId", true);
		title = e2eRegressionCmsHelper.getNodeValue(req, "data.title", true);
		price = e2eRegressionCmsHelper.getNodeValue(req, "data.price", true);
		styleType = e2eRegressionCmsHelper.getNodeValue(req, "data.styleType", true);
		codEnabled = e2eRegressionCmsHelper.getNodeValue(req, "data.codEnabled", true);
		productOptionsId = e2eRegressionCmsHelper.getNodeValue(req, "data.productOptions.id", true);
		productOptionsname = e2eRegressionCmsHelper.getNodeValue(req, "data.productOptions.name", true);
		productOptionsvalue = e2eRegressionCmsHelper.getNodeValue(req, "data.productOptions.value", true);
		productOptionsisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.isActive", true);
		productOptionsprice = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.price", true);
		productOptionsskuId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.skuId", true);
		productOptionsactive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.active", true);
		articleNumber = e2eRegressionCmsHelper.getNodeValue(req, "data.articleNumber", true);
		productTags = e2eRegressionCmsHelper.getNodeValue(req, "data.productTags", true);
		productDisplayName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productDisplayName", true);
		listViewName = e2eRegressionCmsHelper.getNodeValue(req, "data.listViewName", true);
		variantName = e2eRegressionCmsHelper.getNodeValue(req, "data.variantName", true);
		addDate = e2eRegressionCmsHelper.getNodeValue(req, "data.addDate", true);
		modifiedDate = e2eRegressionCmsHelper.getNodeValue(req, "data.modifiedDate", true);
		articleTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.articleType.id", true);
		articleTypefilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.filterOrder", true);
		articleTypetypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeName", true);
		articleTypetypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeCode", true);
		articleTypeisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isActive", true);
		articleTypeisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isSocialSharingEnabled", true);
		articleTypeisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isJewellery", true);
		articleTypeisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isReturnable", true);
		articleTypeisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isExchangeable", true);
		articleTypeisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isLarge", true);
		articleTypeisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isHazmat", true);
		articleTypeisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isTryAndBuyEnabled", true);
		articleTypepickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.pickupEnabled", true);
		articleTypefragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.fragile", true);
		subCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, "data.subCatagory.id", true);
		subCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.filterOrder", true);
		subCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.typeName", true);
		subCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isActive", true);
		subCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isSocialSharingEnabled", true);
		subCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isJewellery", true);
		subCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isReturnable", true);
		subCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isExchangeable", true);
		subCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isLarge", true);
		subCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isHazmat", true);
		subCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isTryAndBuyEnabled", true);
		subCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.pickupEnabled", true);
		subCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.fragile", true);
		masterCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, "data.masterCatagory.id",
				true);
		masterCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.filterOrder", true);
		masterCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeName", true);
		masterCatagorytypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeCode", true);
		masterCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isActive", true);
		masterCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isSocialSharingEnabled", true);
		masterCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isJewellery", true);
		masterCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isReturnable", true);
		masterCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isExchangeable", true);
		masterCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isLarge", true);
		masterCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isHazmat", true);
		masterCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isTryAndBuyEnabled", true);
		masterCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.pickupEnabled", true);
		masterCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.fragile", true);
		productTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.productType.id", true);
		productTypeName = e2eRegressionCmsHelper.getNodeValue(req, "data.productType.name",
				true);
		productTypecodEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.codEnabled", true);
		productTypeVat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.taxation.taxPercentage", true);
		productTypeproductGroupId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.id", true);
		productTypeproductGroupname = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.name", true);
		log.info(productId);
		log.info(title);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 2, description = "Verify Get Search Id API")
	public void verifyGetSearchId() {
		RequestGenerator req = e2eRegressionCmsHelper.getRequestObjForCMSForQueryParam(ServiceType.CMS_CATALOG, APINAME.SEARCHFORSTYLE, new String[] { styleIdFromFactoryClass });
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
		searchProductOptionsvalue = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.value", true);
		searchProductOptionsisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.isActive", true);
		searchProductOptionsprice = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.price", true);
		searchProductOptionsskuId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.skuId", true);
		searchProductOptionsactive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.active", true);
		searchArticleNumber = e2eRegressionCmsHelper.getNodeValue(req, "data.articleNumber",
				true);
		searchProductTags = req.respvalidate
				.NodeValue("data.productTags", true);
		searchProductDisplayName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productDisplayName", true);
		searchlistViewName = e2eRegressionCmsHelper.getNodeValue(req, "data.listViewName",
				true);
		searchVariantName = req.respvalidate
				.NodeValue("data.variantName", true);
		searchAddDate = e2eRegressionCmsHelper.getNodeValue(req, "data.addDate", true);
		searchModifiedDate = e2eRegressionCmsHelper.getNodeValue(req, "data.modifiedDate",
				true);
		searchArticleTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.articleType.id",
				true);
		searchArticleTypefilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.filterOrder", true);
		searchArticleTypetypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeName", true);
		searchArticleTypetypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeCode", true);
		searchArticleTypeisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isActive", true);
		searchArticleTypeisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isSocialSharingEnabled", true);
		searchArticleTypeisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isJewellery", true);
		searchArticleTypeisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isReturnable", true);
		searchArticleTypeisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isExchangeable", true);
		searchArticleTypeisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isLarge", true);
		searchArticleTypeisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isHazmat", true);
		searchArticleTypeisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isTryAndBuyEnabled", true);
		searchArticleTypepickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.pickupEnabled", true);
		searchArticleTypefragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.fragile", true);
		searchsubCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, "data.subCatagory.id",
				true);
		searchsubCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.filterOrder", true);
		searchsubCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.typeName", true);
		searchsubCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isActive", true);
		searchsubCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isSocialSharingEnabled", true);
		searchsubCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isJewellery", true);
		searchsubCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isReturnable", true);
		searchsubCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isExchangeable", true);
		searchsubCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isLarge", true);
		searchsubCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isHazmat", true);
		searchsubCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isTryAndBuyEnabled", true);
		searchsubCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.pickupEnabled", true);
		searchsubCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.fragile", true);
		searchmasterCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.id", true);
		searchmasterCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.filterOrder", true);
		searchmasterCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeName", true);
		searchmasterCatagorytypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeCode", true);
		searchmasterCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isActive", true);
		searchmasterCatagoryisSocialSharingEnabled = req.respvalidate
				.NodeValue("data.masterCatagory.isSocialSharingEnabled", true);
		searchmasterCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isJewellery", true);
		searchmasterCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isReturnable", true);
		searchmasterCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isExchangeable", true);
		searchmasterCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isLarge", true);
		searchmasterCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isHazmat", true);
		searchmasterCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isTryAndBuyEnabled", true);
		searchmasterCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.pickupEnabled", true);
		searchmasterCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.fragile", true);
		searchproductTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.productType.id",
				true);
		searchproductTypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.name", true);
		searchproductTypecodEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.codEnabled", true);
		searchproductTypeproductGroupId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.id", true);
		searchproductTypeproductGroupname = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.name", true);
		searchproductTypeVat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.taxation.taxPercentage", true);
		log.info(searchProductId);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 3, description = "verify productId Is Same In Search And FindById API")
	public void verifyproductIdIsSameInSearchAndFindByIdAPI() {
		if(searchProductId == null && productId ==null){
			Assert.fail("This node is null in both apis");
		}
		else {
			Assert.assertEquals(searchProductId, productId,
				"Product Id is not same in Search API for Style ID "+styleIdFromFactoryClass);
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 4, description = "verify title Is Same In Search And FindById API")
	public void verifytitleIsSameInSearchAndFindByIdAPI() {
		if(searchTitle == null && title ==null){
			Assert.fail("This node is null in both apis");
		}
		else {
			Assert.assertEquals(searchTitle, title,
				"title is not same in Search API for Style ID "+styleIdFromFactoryClass);
		}
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 5, description = "verify price Is Same In Search And FindById API")
	public void verifypriceIsSameInSearchAndFindByIdAPI() {
		if(searchPrice == null && price ==null){
			Assert.fail("This node is null in both apis");
		}
		else 
			Assert.assertEquals(searchPrice, price,
				"price is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 6, description = "verify styleType Is Same In Search And FindById API")
	public void verifystyleTypeIsSameInSearchAndFindByIdAPI() {
		if(searchStyleType == null && styleType ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchStyleType, styleType,
				"styleType is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 7, description = "verify codEnabled Is Same In Search And FindById API")
	public void verifycodEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchCodEnabled == null && codEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else	
			Assert.assertEquals(searchCodEnabled, codEnabled,
				"codEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 8, description = "verify productOptionsId Is Same In Search And FindById API")
	public void verifyproductOptionsIdIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsId == null && productOptionsId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsId, productOptionsId,
				"productOptionsId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 9, description = "verify productOptionsname Is Same In Search And FindById API")
	public void verifyproductOptionsnameIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsname == null && productOptionsname ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsname, productOptionsname,
				"productOptionsname is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 10, description = "verify productOptionsvalue Is Same In Search And FindById API")
	public void verifyproductOptionsvalueIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsvalue == null && productOptionsvalue ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsvalue, productOptionsvalue,
				"productOptionsvalue is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 11, description = "verify searchProductOptionsprice Is Same In Search And FindById API")
	public void verifysearchProductOptionspriceIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsprice == null && productOptionsprice ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsprice, productOptionsprice,
				"searchProductOptionsprice is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 12, description = "verify productOptionsskuId Is Same In Search And FindById API")
	public void verifyproductOptionsskuIdIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsskuId == null && productOptionsskuId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsskuId, productOptionsskuId,
				"productOptionsskuId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 13, description = "verify productOptionsactive Is Same In Search And FindById API")
	public void verifyproductOptionsactiveIsSameInSearchAndFindByIdAPI() {
		if(searchProductOptionsactive == null && productOptionsactive ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductOptionsactive, productOptionsactive,
				"productOptionsactive is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 14, description = "verify articleNumber Is Same In Search And FindById API")
	public void verifyarticleNumberIsSameInSearchAndFindByIdAPI() {
		if(searchArticleNumber == null && articleNumber ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleNumber, articleNumber,
				"articleNumber is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 15, description = "verify productTags Is Same In Search And FindById API")
	public void verifyproductTagsIsSameInSearchAndFindByIdAPI() {
		if(searchProductTags == null && productTags ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductTags, productTags,
				"productTags is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 16, description = "verify productDisplayName Is Same In Search And FindById API")
	public void verifyproductDisplayNameIsSameInSearchAndFindByIdAPI() {
		if(searchProductDisplayName == null && productDisplayName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchProductDisplayName, productDisplayName,
				"productDisplayName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 17, description = "verify listViewName Is Same In Search And FindById API")
	public void verifylistViewNameIsSameInSearchAndFindByIdAPI() {
		if(searchlistViewName == null && listViewName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchlistViewName, listViewName,
				"listViewName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 18, description = "verify variantName Is Same In Search And FindById API")
	public void verifyvariantNameIsSameInSearchAndFindByIdAPI() {
		if(searchVariantName == null && variantName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchVariantName, variantName,
				"variantName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 19, description = "verify addDate Is Same In Search And FindById API")
	public void verifyaddDateIsSameInSearchAndFindByIdAPI() {
		if(searchAddDate == null && addDate ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchAddDate, addDate,
				"addDate is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 20, description = "verify modifiedDate Is Same In Search And FindById API")
	public void verifymodifiedDateIsSameInSearchAndFindByIdAPI() {
		if(searchModifiedDate == null && modifiedDate ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchModifiedDate, modifiedDate,
				"modifiedDate is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 21, description = "verify articleTypeId Is Same In Search And FindById API")
	public void verifyarticleTypeIdIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeId == null && articleTypeId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeId, articleTypeId,
				"articleTypeId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 22, description = "verify articleTypefilterOrder Is Same In Search And FindById API")
	public void verifyarticleTypefilterOrderIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypefilterOrder == null && articleTypefilterOrder ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypefilterOrder,
				articleTypefilterOrder,
				"articleTypefilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 23, description = "verify articleTypetypeName Is Same In Search And FindById API")
	public void verifyarticleTypetypeNameIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypetypeName == null && articleTypetypeName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypetypeName, articleTypetypeName,
				"articleTypetypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 24, description = "verify articleTypetypeCode Is Same In Search And FindById API")
	public void verifyarticleTypetypeCodeIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypetypeCode == null && articleTypetypeCode ==null){
			Assert.fail("This node is null in both apis");
		}
		else	
		Assert.assertEquals(searchArticleTypetypeCode, articleTypetypeCode,
				"articleTypetypeCode is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 25, description = "verify articleTypeisActive Is Same In Search And FindById API")
	public void verifyarticleTypeisActiveIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisActive == null && articleTypeisActive ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisActive, articleTypeisActive,
				"articleTypeisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 26, description = "verify articleTypeisSocialSharingEnabled Is Same In Search And FindById API")
	public void verifyarticleTypeisSocialSharingEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisSocialSharingEnabled == null && articleTypeisSocialSharingEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisSocialSharingEnabled,
				articleTypeisSocialSharingEnabled,
				"articleTypeisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 27, description = "verify articleTypeisJewellery Is Same In Search And FindById API")
	public void verifyarticleTypeisJewelleryIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisJewellery == null && articleTypeisJewellery ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisJewellery,
				articleTypeisJewellery,
				"articleTypeisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 28, description = "verify articleTypeisReturnable Is Same In Search And FindById API")
	public void verifyarticleTypeisReturnableIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisReturnable == null && articleTypeisReturnable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisReturnable,
				articleTypeisReturnable,
				"articleTypeisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 29, description = "verify articleTypeisExchangeable Is Same In Search And FindById API")
	public void verifyarticleTypeisExchangeableIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisExchangeable == null && articleTypeisExchangeable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisExchangeable,
				articleTypeisExchangeable,
				"articleTypeisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 30, description = "verify searchArticleTypeisLarge Is Same In Search And FindById API")
	public void verifysearchArticleTypeisLargeIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisLarge == null && articleTypeisLarge ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisLarge, articleTypeisLarge,
				"searchArticleTypeisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 31, description = "verify articleTypeisHazmat Is Same In Search And FindById API")
	public void verifyarticleTypeisHazmatIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisHazmat == null && articleTypeisHazmat ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisHazmat, articleTypeisHazmat,
				"articleTypeisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 32, description = "verify articleTypeisTryAndBuyEnabled Is Same In Search And FindById API")
	public void verifyarticleTypeisTryAndBuyEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypeisTryAndBuyEnabled == null && articleTypeisTryAndBuyEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypeisTryAndBuyEnabled,
				articleTypeisTryAndBuyEnabled,
				"articleTypeisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 33, description = "verify articleTypepickupEnabled Is Same In Search And FindById API")
	public void verifyarticleTypepickupEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypepickupEnabled == null && articleTypepickupEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypepickupEnabled,
				articleTypepickupEnabled,
				"articleTypepickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 34, description = "verify articleTypefragile Is Same In Search And FindById API")
	public void verifyarticleTypefragileIsSameInSearchAndFindByIdAPI() {
		if(searchArticleTypefragile == null && articleTypefragile ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchArticleTypefragile, articleTypefragile,
				"articleTypefragile is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 35, description = "verify subCatagoryId Is Same In Search And FindById API")
	public void verifysubCatagoryIdIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryId == null && subCatagoryId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryId, subCatagoryId,
				"subCatagoryId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 36, description = "verify subCatagoryfilterOrder Is Same In Search And FindById API")
	public void verifysubCatagoryfilterOrderIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryfilterOrder == null && subCatagoryfilterOrder ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryfilterOrder,
				subCatagoryfilterOrder,
				"subCatagoryfilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 37, description = "verify subCatagorytypeName Is Same In Search And FindById API")
	public void verifysubCatagorytypeNameIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagorytypeName == null && subCatagorytypeName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagorytypeName, subCatagorytypeName,
				"subCatagorytypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 38, description = "verify subCatagoryisActive Is Same In Search And FindById API")
	public void verifysubCatagoryisActiveIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisActive == null && subCatagoryisActive ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisActive, subCatagoryisActive,
				"subCatagoryisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 39, description = "verify subCatagoryisSocialSharingEnabled Is Same In Search And FindById API")
	public void verifysubCatagoryisSocialSharingEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisSocialSharingEnabled == null && subCatagoryisSocialSharingEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisSocialSharingEnabled,
				subCatagoryisSocialSharingEnabled,
				"subCatagoryisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 40, description = "verify subCatagoryisJewellery Is Same In Search And FindById API")
	public void verifysubCatagoryisJewelleryIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisJewellery == null && subCatagoryisJewellery ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisJewellery,
				subCatagoryisJewellery,
				"subCatagoryisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 41, description = "verify subCatagoryisReturnable Is Same In Search And FindById API")
	public void verifysubCatagoryisReturnableIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisReturnable == null && subCatagoryisReturnable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisReturnable,
				subCatagoryisReturnable,
				"subCatagoryisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 42, description = "verify subCatagoryisExchangeable Is Same In Search And FindById API")
	public void verifysubCatagoryisExchangeableIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisExchangeable == null && subCatagoryisExchangeable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisExchangeable,
				subCatagoryisExchangeable,
				"subCatagoryisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 43, description = "verify subCatagoryisLarge Is Same In Search And FindById API")
	public void verifysubCatagoryisLargeIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisLarge == null && subCatagoryisLarge ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisLarge, subCatagoryisLarge,
				"subCatagoryisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 44, description = "verify subCatagoryisHazmat Is Same In Search And FindById API")
	public void verifysubCatagoryisHazmatIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisHazmat == null && subCatagoryisHazmat ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisHazmat, subCatagoryisHazmat,
				"subCatagoryisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 45, description = "verify subCatagoryisTryAndBuyEnabled Is Same In Search And FindById API")
	public void verifysubCatagoryisTryAndBuyEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryisTryAndBuyEnabled == null && subCatagoryisTryAndBuyEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryisTryAndBuyEnabled,
				subCatagoryisTryAndBuyEnabled,
				"subCatagoryisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 46, description = "verify subCatagorypickupEnabled Is Same In Search And FindById API")
	public void verifysubCatagorypickupEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagorypickupEnabled == null && subCatagorypickupEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagorypickupEnabled,
				subCatagorypickupEnabled,
				"subCatagorypickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 47, description = "verify subCatagoryfragile Is Same In Search And FindById API")
	public void verifysubCatagoryfragileIsSameInSearchAndFindByIdAPI() {
		if(searchsubCatagoryfragile == null && subCatagoryfragile ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchsubCatagoryfragile, subCatagoryfragile,
				"subCatagoryfragile is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 48, description = "verify masterCatagoryId Is Same In Search And FindById API")
	public void verifymasterCatagoryIdIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryId == null && masterCatagoryId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryId, masterCatagoryId,
				"masterCatagoryId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 49, description = "verify masterCatagoryfilterOrder Is Same In Search And FindById API")
	public void verifymasterCatagoryfilterOrderIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryfilterOrder == null && masterCatagoryfilterOrder ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryfilterOrder,
				masterCatagoryfilterOrder,
				"masterCatagoryfilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 50, description = "verify masterCatagorytypeName Is Same In Search And FindById API")
	public void verifymasterCatagorytypeNameIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagorytypeName == null && masterCatagorytypeName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagorytypeName,
				masterCatagorytypeName,
				"masterCatagorytypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 51, description = "verify masterCatagorytypeCode Is Same In Search And FindById API")
	public void verifyasterCatagorytypeCodeIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagorytypeCode == null && masterCatagorytypeCode ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagorytypeCode,
				masterCatagorytypeCode,
				"masterCatagorytypeCode is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 52, description = "verify masterCatagoryisActive Is Same In Search And FindById API")
	public void verifymasterCatagoryisActiveIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisActive == null && masterCatagoryisActive ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisActive,
				masterCatagoryisActive,
				"masterCatagoryisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 53, description = "verify masterCatagoryisSocialSharingEnabled Is Same In Search And FindById API")
	public void verifymasterCatagoryisSocialSharingEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisSocialSharingEnabled == null && masterCatagoryisSocialSharingEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisSocialSharingEnabled,
				masterCatagoryisSocialSharingEnabled,
				"masterCatagoryisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 54, description = "verify masterCatagoryisJewellery Is Same In Search And FindById API")
	public void verifymasterCatagoryisJewelleryIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisJewellery == null && masterCatagoryisJewellery ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisJewellery,
				masterCatagoryisJewellery,
				"masterCatagoryisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 55, description = "verify masterCatagoryisReturnable Is Same In Search And FindById API")
	public void verifymasterCatagoryisReturnableIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisReturnable == null && masterCatagoryisReturnable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisReturnable,
				masterCatagoryisReturnable,
				"masterCatagoryisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 56, description = "verify masterCatagoryisExchangeable Is Same In Search And FindById API")
	public void verifymasterCatagoryisExchangeableIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisExchangeable == null && masterCatagoryisExchangeable ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisExchangeable,
				masterCatagoryisExchangeable,
				"masterCatagoryisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 57, description = "verify masterCatagoryisLarge Is Same In Search And FindById API")
	public void verifymasterCatagoryisLargeIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisLarge == null && masterCatagoryisLarge ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisLarge, masterCatagoryisLarge,
				"masterCatagoryisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 58, description = "verify masterCatagoryisHazmat Is Same In Search And FindById API")
	public void verifymasterCatagoryisHazmatIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisHazmat == null && masterCatagoryisHazmat ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisHazmat,
				masterCatagoryisHazmat,
				"masterCatagoryisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 59, description = "verify masterCatagoryisTryAndBuyEnabled Is Same In Search And FindById API")
	public void verifymasterCatagoryisTryAndBuyEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryisTryAndBuyEnabled == null && masterCatagoryisTryAndBuyEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryisTryAndBuyEnabled,
				masterCatagoryisTryAndBuyEnabled,
				"masterCatagoryisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 60, description = "verify masterCatagorypickupEnabled Is Same In Search And FindById API")
	public void verifymasterCatagorypickupEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagorypickupEnabled == null && masterCatagorypickupEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagorypickupEnabled,
				masterCatagorypickupEnabled,
				"masterCatagorypickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 61, description = "verify masterCatagoryfragile Is Same In Search And FindById API")
	public void verifymasterCatagoryfragileIsSameInSearchAndFindByIdAPI() {
		if(searchmasterCatagoryfragile == null && masterCatagoryfragile ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchmasterCatagoryfragile, masterCatagoryfragile,
				"masterCatagoryfragile is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 62, description = "verify productTypeId Is Same In Search And FindById API")
	public void verifyproductTypeIdIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypeId == null && productTypeId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypeId, productTypeId,
				"productTypeId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 63, description = "verify productTypeName Is Same In Search And FindById API")
	public void verifyproductTypeNameIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypeName == null && productTypeName ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypeName, productTypeName,
				"productTypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 64, description = "verify productTypecodEnabled Is Same In Search And FindById API")
	public void verifyproductTypecodEnabledIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypecodEnabled == null && productTypecodEnabled ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypecodEnabled, productTypecodEnabled,
				"productTypecodEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 65, description = "verify productTypeVat Is Same In Search And FindById API")
	public void verifyproductTypeVatIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypeVat == null && productTypeVat ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypeVat, productTypeVat,
				"productTypeVat is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 66, description = "verify productTypeproductGroupId Is Same In Search And FindById API")
	public void verifyproductTypeproductGroupIdIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypeproductGroupId == null && productTypeproductGroupId ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypeproductGroupId,
				productTypeproductGroupId,
				"productTypeproductGroupId is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@Test(groups = { "e2eCMSRegression" }, priority = 67, description = "verify productTypeproductGroupname Is Same In Search And FindById API")
	public void verifyproductTypeproductGroupnameIsSameInSearchAndFindByIdAPI() {
		if(searchproductTypeproductGroupname == null && productTypeproductGroupname ==null){
			Assert.fail("This node is null in both apis");
		}
		else
			Assert.assertEquals(searchproductTypeproductGroupname,
				productTypeproductGroupname,
				"productTypeproductGroupname is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

//	@Test(groups = { "e2eCMSRegression" }, dataProvider = "singlestyle1", priority = 70, description = "Verify Get Find by Id  for multiple styles from Dataprovider")
	public void compareFindbyIdwithSearchAPIforMultipleStylefromDP(
			String singlestyle1) throws JSONException {
		MyntraService service = Myntra.getService(ServiceType.CMS_CATALOG,
				APINAME.FINDBYID, init.Configurations, PayloadType.JSON,
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
		productId = e2eRegressionCmsHelper.getNodeValue(req, "data.productId", true);
		title = e2eRegressionCmsHelper.getNodeValue(req, "data.title", true);
		price = e2eRegressionCmsHelper.getNodeValue(req, "data.price", true);
		styleType = e2eRegressionCmsHelper.getNodeValue(req, "data.styleType", true);
		codEnabled = e2eRegressionCmsHelper.getNodeValue(req, "data.codEnabled", true);
		productOptionsId = e2eRegressionCmsHelper.getNodeValue(req, "data.productOptions.id",
				true);
		productOptionsname = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.name", true);
		productOptionsvalue = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.value", true);
		productOptionsisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.isActive", true);
		productOptionsprice = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.price", true);
		productOptionsskuId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.skuId", true);
		productOptionsactive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productOptions.active", true);
		articleNumber = e2eRegressionCmsHelper.getNodeValue(req, "data.articleNumber", true);
		productTags = e2eRegressionCmsHelper.getNodeValue(req, "data.productTags", true);
		productDisplayName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productDisplayName", true);
		listViewName = e2eRegressionCmsHelper.getNodeValue(req, "data.listViewName", true);
		variantName = e2eRegressionCmsHelper.getNodeValue(req, "data.variantName", true);
		addDate = e2eRegressionCmsHelper.getNodeValue(req, "data.addDate", true);
		modifiedDate = e2eRegressionCmsHelper.getNodeValue(req, "data.modifiedDate", true);
		articleTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.articleType.id", true);
		articleTypefilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.filterOrder", true);
		articleTypetypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeName", true);
		articleTypetypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.typeCode", true);
		articleTypeisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isActive", true);
		articleTypeisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isSocialSharingEnabled", true);
		articleTypeisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isJewellery", true);
		articleTypeisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isReturnable", true);
		articleTypeisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isExchangeable", true);
		articleTypeisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isLarge", true);
		articleTypeisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isHazmat", true);
		articleTypeisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.isTryAndBuyEnabled", true);
		articleTypepickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.pickupEnabled", true);
		articleTypefragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.articleType.fragile", true);
		subCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, "data.subCatagory.id", true);
		subCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.filterOrder", true);
		subCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.typeName", true);
		subCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isActive", true);
		subCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isSocialSharingEnabled", true);
		subCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isJewellery", true);
		subCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isReturnable", true);
		subCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isExchangeable", true);
		subCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isLarge", true);
		subCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isHazmat", true);
		subCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.isTryAndBuyEnabled", true);
		subCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.pickupEnabled", true);
		subCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.subCatagory.fragile", true);
		masterCatagoryId = e2eRegressionCmsHelper.getNodeValue(req, "data.masterCatagory.id",
				true);
		masterCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.filterOrder", true);
		masterCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeName", true);
		masterCatagorytypeCode = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.typeCode", true);
		masterCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isActive", true);
		masterCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isSocialSharingEnabled", true);
		masterCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isJewellery", true);
		masterCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isReturnable", true);
		masterCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isExchangeable", true);
		masterCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isLarge", true);
		masterCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isHazmat", true);
		masterCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.isTryAndBuyEnabled", true);
		masterCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.pickupEnabled", true);
		masterCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.masterCatagory.fragile", true);
		productTypeId = e2eRegressionCmsHelper.getNodeValue(req, "data.productType.id", true);
		productTypeName = e2eRegressionCmsHelper.getNodeValue(req, "data.productType.name",
				true);
		productTypecodEnabled = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.codEnabled", true);
		productTypeVat = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.taxation.taxPercentage", true);
		productTypeproductGroupId = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.id", true);
		productTypeproductGroupname = e2eRegressionCmsHelper.getNodeValue(req, 
				"data.productType.productGroup.name", true);
		log.info(productId);
		log.info(title);

		MyntraService service1 = Myntra.getService(ServiceType.CMS_CATALOG,
				APINAME.SEARCHFORSTYLE, init.Configurations, PayloadType.JSON,
				new String[] { singlestyle1 }, PayloadType.JSON);
		log.info(service1.Payload);
		log.info(service1.URL);
		HashMap getParam1 = new HashMap();
		getParam1.put("Authorization",
				"Basic YXBpYWRtaW46bTFudHJhUjBja2V0MTMhIw==");
		RequestGenerator req1 = new RequestGenerator(service1, getParam1);
		log.info(service1.URL);
		log.info("response = " + req1.returnresponseasstring());
		AssertJUnit.assertEquals("Status code does not match", 200,
				req1.response.getStatus());
		searchProductId = e2eRegressionCmsHelper.getNodeValue(req1, "data.productId", true);
		searchTitle = e2eRegressionCmsHelper.getNodeValue(req1, "data.title", true);
		searchPrice = e2eRegressionCmsHelper.getNodeValue(req1, "data.price", true);
		searchStyleType = e2eRegressionCmsHelper.getNodeValue(req1, "data.styleType", true);
		searchCodEnabled = e2eRegressionCmsHelper.getNodeValue(req1, "data.codEnabled", true);
		searchProductOptionsId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.id", true);
		searchProductOptionsname = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.name", true);
		searchProductOptionsvalue = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.value", true);
		searchProductOptionsisActive = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.isActive", true);
		searchProductOptionsprice = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.price", true);
		searchProductOptionsskuId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.skuId", true);
		searchProductOptionsactive = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productOptions.active", true);
		searchArticleNumber = e2eRegressionCmsHelper.getNodeValue(req1, "data.articleNumber",
				true);
		searchProductTags = e2eRegressionCmsHelper.getNodeValue(req1, "data.productTags",
				true);
		searchProductDisplayName = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productDisplayName", true);
		searchlistViewName = e2eRegressionCmsHelper.getNodeValue(req1, "data.listViewName",
				true);
		searchVariantName = e2eRegressionCmsHelper.getNodeValue(req1, "data.variantName",
				true);
		searchAddDate = e2eRegressionCmsHelper.getNodeValue(req1, "data.addDate", true);
		searchModifiedDate = e2eRegressionCmsHelper.getNodeValue(req1, "data.modifiedDate",
				true);
		searchArticleTypeId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.id", true);
		searchArticleTypefilterOrder = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.filterOrder", true);
		searchArticleTypetypeName = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.typeName", true);
		searchArticleTypetypeCode = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.typeCode", true);
		searchArticleTypeisActive = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isActive", true);
		searchArticleTypeisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isSocialSharingEnabled", true);
		searchArticleTypeisJewellery = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isJewellery", true);
		searchArticleTypeisReturnable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isReturnable", true);
		searchArticleTypeisExchangeable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isExchangeable", true);
		searchArticleTypeisLarge = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isLarge", true);
		searchArticleTypeisHazmat = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isHazmat", true);
		searchArticleTypeisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.isTryAndBuyEnabled", true);
		searchArticleTypepickupEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.pickupEnabled", true);
		searchArticleTypefragile = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.articleType.fragile", true);
		searchsubCatagoryId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.id", true);
		searchsubCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.filterOrder", true);
		searchsubCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.typeName", true);
		searchsubCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isActive", true);
		searchsubCatagoryisSocialSharingEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isSocialSharingEnabled", true);
		searchsubCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isJewellery", true);
		searchsubCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isReturnable", true);
		searchsubCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isExchangeable", true);
		searchsubCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isLarge", true);
		searchsubCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isHazmat", true);
		searchsubCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.isTryAndBuyEnabled", true);
		searchsubCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.pickupEnabled", true);
		searchsubCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.subCatagory.fragile", true);
		searchmasterCatagoryId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.id", true);
		searchmasterCatagoryfilterOrder = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.filterOrder", true);
		searchmasterCatagorytypeName = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.typeName", true);
		searchmasterCatagorytypeCode = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.typeCode", true);
		searchmasterCatagoryisActive = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isActive", true);
		searchmasterCatagoryisSocialSharingEnabled = req1.respvalidate
				.NodeValue("data.masterCatagory.isSocialSharingEnabled", true);
		searchmasterCatagoryisJewellery = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isJewellery", true);
		searchmasterCatagoryisReturnable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isReturnable", true);
		searchmasterCatagoryisExchangeable = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isExchangeable", true);
		searchmasterCatagoryisLarge = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isLarge", true);
		searchmasterCatagoryisHazmat = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isHazmat", true);
		searchmasterCatagoryisTryAndBuyEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.isTryAndBuyEnabled", true);
		searchmasterCatagorypickupEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.pickupEnabled", true);
		searchmasterCatagoryfragile = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.masterCatagory.fragile", true);
		searchproductTypeId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productType.id", true);
		searchproductTypeName = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productType.name", true);
		searchproductTypecodEnabled = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productType.codEnabled", true);
		searchproductTypeproductGroupId = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productType.productGroup.id", true);
		searchproductTypeproductGroupname = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.productType.productGroup.name", true);
		searchproductTypeVat = e2eRegressionCmsHelper.getNodeValue(req1, 
				"data.taxation.taxPercentage", true);
		log.info(searchProductId);
		Assert.assertEquals(searchProductId, productId,
				"Product Id is not same in Search API for Style ID "+styleIdFromFactoryClass);
		Assert.assertEquals(searchTitle, title,
				"title is not same in Search API for Style ID "+styleIdFromFactoryClass);
		Assert.assertEquals(searchPrice, price,
				"price is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchStyleType, styleType,
				"styleType is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchCodEnabled, codEnabled,
				"codEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsId, productOptionsId,
				"productOptionsId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsname, productOptionsname,
				"productOptionsname is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsvalue, productOptionsvalue,
				"productOptionsvalue is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsprice, productOptionsprice,
				"searchProductOptionsprice is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsskuId, productOptionsskuId,
				"productOptionsskuId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductOptionsactive, productOptionsactive,
				"productOptionsactive is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleNumber, articleNumber,
				"articleNumber is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductTags, productTags,
				"productTags is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchProductDisplayName, productDisplayName,
				"productDisplayName is not same in Search API for Style ID "+styleIdFromFactoryClass);
		Assert.assertEquals(searchlistViewName, listViewName,
				"listViewName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchVariantName, variantName,
				"variantName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchAddDate, addDate,
				"addDate is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchModifiedDate, modifiedDate,
				"modifiedDate is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeId, articleTypeId,
				"articleTypeId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypefilterOrder,
				articleTypefilterOrder,
				"articleTypefilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypetypeName, articleTypetypeName,
				"articleTypetypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypetypeCode, articleTypetypeCode,
				"articleTypetypeCode is not same in Search API for Style ID "+styleIdFromFactoryClass);
		Assert.assertEquals(searchArticleTypeisActive, articleTypeisActive,
				"articleTypeisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisSocialSharingEnabled,
				articleTypeisSocialSharingEnabled,
				"articleTypeisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisJewellery,
				articleTypeisJewellery,
				"articleTypeisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisReturnable,
				articleTypeisReturnable,
				"articleTypeisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisExchangeable,
				articleTypeisExchangeable,
				"articleTypeisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisLarge, articleTypeisLarge,
				"searchArticleTypeisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisHazmat, articleTypeisHazmat,
				"articleTypeisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypeisTryAndBuyEnabled,
				articleTypeisTryAndBuyEnabled,
				"articleTypeisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypepickupEnabled,
				articleTypepickupEnabled,
				"articleTypepickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchArticleTypefragile, articleTypefragile,
				"articleTypefragile is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryId, subCatagoryId,
				"subCatagoryId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryfilterOrder,
				subCatagoryfilterOrder,
				"subCatagoryfilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagorytypeName, subCatagorytypeName,
				"subCatagorytypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisActive, subCatagoryisActive,
				"subCatagoryisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisSocialSharingEnabled,
				subCatagoryisSocialSharingEnabled,
				"subCatagoryisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisJewellery,
				subCatagoryisJewellery,
				"subCatagoryisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisReturnable,
				subCatagoryisReturnable,
				"subCatagoryisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisExchangeable,
				subCatagoryisExchangeable,
				"subCatagoryisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisLarge, subCatagoryisLarge,
				"subCatagoryisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisHazmat, subCatagoryisHazmat,
				"subCatagoryisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryisTryAndBuyEnabled,
				subCatagoryisTryAndBuyEnabled,
				"subCatagoryisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagorypickupEnabled,
				subCatagorypickupEnabled,
				"subCatagorypickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchsubCatagoryfragile, subCatagoryfragile,
				"subCatagoryfragile is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryId, masterCatagoryId,
				"masterCatagoryId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryfilterOrder,
				masterCatagoryfilterOrder,
				"masterCatagoryfilterOrder is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagorytypeName,
				masterCatagorytypeName,
				"masterCatagorytypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagorytypeCode,
				masterCatagorytypeCode,
				"masterCatagorytypeCode is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisActive,
				masterCatagoryisActive,
				"masterCatagoryisActive is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisSocialSharingEnabled,
				masterCatagoryisSocialSharingEnabled,
				"masterCatagoryisSocialSharingEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisJewellery,
				masterCatagoryisJewellery,
				"masterCatagoryisJewellery is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisReturnable,
				masterCatagoryisReturnable,
				"masterCatagoryisReturnable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisExchangeable,
				masterCatagoryisExchangeable,
				"masterCatagoryisExchangeable is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisLarge, masterCatagoryisLarge,
				"masterCatagoryisLarge is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisHazmat,
				masterCatagoryisHazmat,
				"masterCatagoryisHazmat is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryisTryAndBuyEnabled,
				masterCatagoryisTryAndBuyEnabled,
				"masterCatagoryisTryAndBuyEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagorypickupEnabled,
				masterCatagorypickupEnabled,
				"masterCatagorypickupEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchmasterCatagoryfragile, masterCatagoryfragile,
				"masterCatagoryfragile is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypeId, productTypeId,
				"productTypeId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypeName, productTypeName,
				"productTypeName is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypecodEnabled, productTypecodEnabled,
				"productTypecodEnabled is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypeVat, productTypeVat,
				"productTypeVat is not same in Search API for Style ID "+styleIdFromFactoryClass);
		// SoftAssert sa = new SoftAssert();
		// sa.assertEquals(searchproductTypeVat, productTypeVat,
		// "productTypeVat is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypeproductGroupId,
				productTypeproductGroupId,
				"productTypeproductGroupId is not same in Search API for Style ID "+styleIdFromFactoryClass);

		Assert.assertEquals(searchproductTypeproductGroupname,
				productTypeproductGroupname,
				"productTypeproductGroupname is not same in Search API for Style ID "+styleIdFromFactoryClass);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		log.info("Entry");
		WebPage.endTest();
		log.info("Exit");

	}
}
