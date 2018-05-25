/**
 * 
 */
package com.myntra.apiTests.portalservices.devapiservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum DevApiServiceDataNodes 
{

	META("meta"),
	META_CODE("meta.code"),
	META_TOKEN("meta.token"),
	META_XSRF_TOKEN("meta.xsrfToken"),
	META_ERROR_DETAIL("meta.errorDetail"),
	META_ERROR_TYPE("meta.errorType"),
	META_REQ_ID("meta.requestId"),
	
	NOTIFICATION("notification"),
	
	RESP_DATA("data"),
	RESP_DATA_LOGIN("data.login"),
	RESP_DATA_USER_TYPE("data.usertype"),
	RESP_DATA_TITLE("data.title"),
	RESP_DATA_FIRST_NAME("data.profile.firstname"),
	RESP_DATA_LAST_NAME("data.profile.lastname"),
	RESP_DATA_EMAIL("data.email"),
	RESP_DATA_PHONE("data.mobile"),
	RESP_DATA_MOBILE("data.mobile"),
	RESP_DATA_STATUS("data.status"),
	RESP_DATA_LAST_LOGIN("data.lastlogin"),
	RESP_DATA_FIRST_LOGIN("data.firstlogin"),
	
	RESP_DATA_CATAGORIES("data.categories"),
	RESP_DATA_CATAGORIES_NAME("data.categories.name"),
	RESP_DATA_CATAGORIES_LINK_URL("data.categories.linkUrl"),
	RESP_DATA_CATAGORIES_LINK_URL_RES_TYPE("data.categories.linkUrl.resourceType"),
	RESP_DATA_CATAGORIES_LINK_URL_RES_VAL("data.categories.linkUrl.resourceValue"),
	
	STYLE_RESP_DATA_ID("data.id"),
	STYLE_RESP_DATA_PRICE("data.price"),
	STYLE_RESP_DATA_DISC_PRICE("data.discountedPrice"),
	STYLE_RESP_DATA_STYLE_TYPE("data.styleType"),
	STYLE_RESP_DATA_PROD_TYPE_ID("data.productTypeId"),
	STYLE_RESP_DATA_ARTICLE_NUMBER("data.articleNumber"),
	STYLE_RESP_DATA_VISUAL_TAG("data.visualTag"),
	STYLE_RESP_DATA_PROD_DISP_NAME("data.productDisplayName"),
	STYLE_RESP_DATA_VARIENT_NAME("data.variantName"),
	STYLE_RESP_DATA_MYNTRA_RATING("data.myntraRating"),
	STYLE_RESP_DATA_CATALOG_ADD_DATE("data.catalogAddDate"),
	STYLE_RESP_DATA_BRAND_NAME("data.brandName"),
	STYLE_RESP_DATA_AGE_GROUP("data.ageGroup"),
	STYLE_RESP_DATA_GENDER("data.gender"),
	STYLE_RESP_DATA_BASE_COLOUR("data.baseColour"),
	STYLE_RESP_DATA_COLOUR1("data.colour1"),
	STYLE_RESP_DATA_COLOUR2("data.colour2"),
	STYLE_RESP_DATA_FASHION_TYPE("data.fashionType"),
	STYLE_RESP_DATA_SEASON("data.season"),
	STYLE_RESP_DATA_YEAR("data.year"),
	STYLE_RESP_DATA_USAGE("data.usage"),
	STYLE_RESP_DATA_VAT("data.vat"),
	STYLE_RESP_DATA_NAVIGATION_ID("data.navigationId"),
	STYLE_RESP_DATA_LAND_PAGE_URL("data.landingPageUrl"),
	
	STYLE_RESP_DATA_ARTICLE_ATTRIBUTES("data.articleAttributes"),
	
	STYLE_RESP_DATA_SERVICEABLE("data.SERVICEABLE"),
	STYLE_RESP_DATA_DELIVERY_PROMISE_TIME("data.DELIVERY_PROMISE_TIME"),
	STYLE_RESP_DATA_PINCODE("data.pincode"),
	STYLE_RESP_DATA_COD_LIMITS("data.codLimits"),
	
	STYLE_RESP_DATA_STYLE_IMAGES("data.styleImages"),
	STYLE_RESP_DATA_STYLE_IMAGES_DEFAULT("data.styleImages.default"),
	STYLE_RESP_DATA_STYLE_IMAGES_SIZE_REP("data.styleImages.size_representation"),
	STYLE_RESP_DATA_STYLE_IMAGES_SEARCH("data.styleImages.search"),
	STYLE_RESP_DATA_STYLE_IMAGES_BACK("data.styleImages.back"),
	STYLE_RESP_DATA_STYLE_IMAGES_FRONT("data.styleImages.front"),
	STYLE_RESP_DATA_STYLE_IMAGES_LEFT("data.styleImages.left"),
	STYLE_RESP_DATA_STYLE_IMAGES_RIGHT("data.styleImages.right"),
	
	
	STYLE_RESP_DATA_MASTER_CATAGORY("data.masterCategory"),
	STYLE_RESP_DATA_MASTER_CATAGORY_ID("data.masterCategory.id"),
	STYLE_RESP_DATA_MASTER_CATAGORY_TYPE_NAME("data.masterCategory.typeName"),
	STYLE_RESP_DATA_MASTER_CATAGORY_ACTIVE("data.masterCategory.active"),
	STYLE_RESP_DATA_MASTER_CATAGORY_SOC_SHARING_ENABLED("data.masterCategory.socialSharingEnabled"),
	
	STYLE_RESP_SUB_CATAGORY("data.subCategory"),
	STYLE_RESP_SUB_CATAGORY_ID("data.subCategory.id"),
	STYLE_RESP_SUB_CATAGORY_TYPE_NAME("data.subCategory.typeName"),
	STYLE_RESP_SUB_CATAGORY_ACTIVE("data.subCategory.active"),
	STYLE_RESP_SUB_CATAGORY_SOC_SHARING_ENABLED("data.subCategory.socialSharingEnabled"),
	
	STYLE_RESP_SUB_ARTICLE_TYPE("data.articleType"),
	STYLE_RESP_SUB_ARTICLE_TYPE_ID("data.articleType.id"),
	STYLE_RESP_SUB_ARTICLE_TYPE_NAME("data.articleType.typeName"),
	STYLE_RESP_SUB_ARTICLE_TYPE_ACTIVE("data.articleType.active"),
	STYLE_RESP_SUB_ARTICLE_TYPE_SOC_SHARING_ENABLED("data.articleType.socialSharingEnabled"),
	
	STYLE_RESP_PRODUCT_DESCRIPTORS("data.productDescriptors"),
	STYLE_RESP_PRODUCT_DESCRIPTORS_MATERIALS_CARE_DESC("data.productDescriptors.materials_care_desc"),
	
	STYLE_RESP_STYLE_OPTIONS("data.styleOptions"),
	STYLE_RESP_STYLE_OPTIONS_ID("data.styleOptions.id"),
	STYLE_RESP_STYLE_OPTIONS_NAME("data.styleOptions.name"),
	STYLE_RESP_STYLE_OPTIONS_VALUE("data.styleOptions.value"),
	STYLE_RESP_STYLE_OPTIONS_SKU_ID("data.styleOptions.skuId"),
	STYLE_RESP_STYLE_OPTIONS_INVENTORY_COUNT("data.styleOptions.inventoryCount"),
	STYLE_RESP_STYLE_OPTIONS_WARE_HOUSE_ID_TO_ITEM_COUNT_MAP("data.styleOptions.warehouseIdToItemCountMap"),
	STYLE_RESP_STYLE_OPTIONS_SKU_AVAIL_DETAIL_MAP("data.styleOptions.skuAvailabilityDetailMap"),
	STYLE_RESP_STYLE_OPTIONS_ACTIVE("data.styleOptions.active"),
	STYLE_RESP_STYLE_OPTIONS_AVAILABLE("data.styleOptions.available"),
	
	STYLE_OFFERS_RESP_STATUS("data.status"),
	STYLE_OFFERS_RESP_DATA("data.data"),
	STYLE_OFFERS_RESP_DATA_COUPONS("data.data.coupons"),
	STYLE_OFFERS_RESP_DATA_COUPONS_COUPON("data.data.coupons.coupon"),
	STYLE_OFFERS_RESP_DATA_COUPONS_PRICE("data.data.coupons.price"),
	STYLE_OFFERS_RESP_DATA_COUPONS_TOOLTIP("data.data.coupons.tooltip"),
	STYLE_OFFERS_RESP_DATA_COUPONS_TYPE("data.data.coupons.type"),
	STYLE_OFFERS_RESP_DATA_COUPONS_MINIMUM("data.data.coupons.minimum"),
	STYLE_OFFERS_RESP_DATA_COUPONS_MINIMUM_COUNT("data.data.coupons.minimumCount"),
	STYLE_OFFERS_RESP_DATA_COUPONS_COUPON_DISCOUNT("data.data.coupons.coupondiscount"),
	STYLE_OFFERS_RESP_DATA_COUPONS_END_DATE("data.data.coupons.enddate"),
	STYLE_OFFERS_RESP_DATA_COUPONS_ORIGINAL_PRICE("data.data.coupons.originalPrice"),
	STYLE_OFFERS_RESP_DATA_COUPONS_COUPON_EXTRA_TNC("data.data.coupons.couponExtraTNC"),
	STYLE_OFFERS_RESP_DATA_COUPONS_GRP_NAME("data.data.coupons.groupName"),
	STYLE_OFFERS_RESP_DATA_COUPONS_MESSAGE("data.message"),
	STYLE_OFFERS_RESP_DATA_COUPONS_NO_OF_COUPONS("data.numberOfCoupons"),
	
	SEARCH_RESP_DATA_SEARCH("data.search"),
	SEARCH_RESP_DATA_SEARCH_QUERY("data.search.query"),
	SEARCH_RESP_DATA_SEARCH_START("data.search.start"),
	SEARCH_RESP_DATA_SEARCH_ROWS("data.search.rows"),
	SEARCH_RESP_DATA_SEARCH_FACET_FIELD("data.search.facetField"),
	SEARCH_RESP_DATA_SEARCH_FQ("data.search.fq"),
	SEARCH_RESP_DATA_SEARCH_SORT("data.search.sort"),
	SEARCH_RESP_DATA_SEARCH_RET_DOCS("data.search.return_docs"),
	SEARCH_RESP_DATA_SEARCH_COLOUR_GROUPING("data.search.colour_grouping"),
	SEARCH_RESP_DATA_SEARCH_FACET("data.search.facet"),
	SEARCH_RESP_DATA_SYNONYM_MAP("data.synonymMap"),
	SEARCH_RESP_DATA_QUERY_TYPE("data.queryType"),
	SEARCH_RESP_DATA_TOTAL_PRODUCTS_COUNT("data.totalProductsCount"),
	SEARCH_RESP_DATA_LIST_GROUP("data.listGroup"),
	SEARCH_RESP_DATA_RESULTS("data.results"),
	SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCTS_COUNT("data.results.totalProductsCount"),
	SEARCH_RESP_DATA_RESULTS_FILTERS("data.results.filters"),
	SEARCH_RESP_DATA_RESULTS_PRODUCTS("data.results.products"),
	SEARCH_RESP_DATA_SEO("data.seo"),
	SEARCH_RESP_DATA_SEO_IS_NO_INDEXED("data.seo.isNoIndexed"),
	SEARCH_RESP_DATA_SEO_IS_SYNONYM("data.seo.isSynonym"),
	SEARCH_RESP_DATA_SEO_IS_OR_SEARCH("data.seo.isOrSearch"),
	SEARCH_RESP_DATA_SEO_IS_AND_SEARCH("data.seo.isAndSearch"),
	SEARCH_RESP_DATA_SEO_CANONICAL_URI("data.seo.canonicalUri"),
	SEARCH_RESP_DATA_SEO_URI("data.seo.uri"),
	SEARCH_RESP_DATA_SEO_IS_PARAMETRIZED("data.seo.isParametrized"),
	SEARCH_RESP_DATA_SEO_PATTERN("data.seo.pattern"),
	SEARCH_RESP_DATA_SEO_FOOTER_DATA("data.seo.footerData"),
	SEARCH_RESP_DATA_SEO_META_DATA("data.seo.metaData"),
	SEARCH_RESP_DATA_SEO_DNS_PREFETCH("data.seo.dnsPrefetch"),
	SEARCH_RESP_DATA_SEO_SHORT_CUT_ICON("data.seo.shortcutIcon"),
	SEARCH_RESP_DATA_SEO_GPUBLISHER("data.seo.gPublisher"),
	SEARCH_RESP_DATA_SEO_TWITTER_CARD("data.seo.twitterCard"),
	SEARCH_RESP_DATA_SEO_TWITTER_SITE("data.seo.twitterSite"),
	SEARCH_RESP_DATA_SEO_SOCIAL_IMAGE("data.seo.socialImage"),
	SEARCH_RESP_DATA_SEO_FB_ADMINS("data.seo.fbAdmins"),
	SEARCH_RESP_DATA_SEO_FB_APP_ID("data.seo.fbAppId"),
	SEARCH_RESP_DATA_SEO_OG_TYPE("data.seo.ogType"),
	SEARCH_RESP_DATA_SEO_OG_SITE_NAME("data.seo.ogSiteName"),
	SEARCH_RESP_DATA_SEO_TITLE("data.seo.title"),
	SEARCH_RESP_DATA_PICKUPENABLED("$.data.masterCategory.pickupEnabled"),
	SEARCH_RESP_DATA_TRYNBUYENABLED("$.data.masterCategory.isTryAndBuyEnabled"),
	//LOOKS API
	LOOKS_CREATE_RESP_DATA_LOOKID("data.id"),
	LOOKS_CREATE_RESP_DATA_TITLE("data.title"),
	LOOKS_CREATE_RESP_DATA_DESCRIPTION("data.description"),
	LOOKS_CREATE_RESP_DATA_ISACTIVE("data.isActive"),
	LOOKS_CREATE_RESP_DATA_ISDRAFT("data.isDraft"),
	LOOKS_CREATE_RESP_DATA_ISREMYX("data.isRemyx");
	
	
	

	private String nodePath;
	
	private DevApiServiceDataNodes(String nodePath)
	{
		this.nodePath = nodePath;
	}
	
	
	
	@Override
	public String toString() {
		return nodePath;
	}
	
	
	public static List<String> getSignInResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_TOKEN.toString());
		metaTagNodes.add(META_XSRF_TOKEN.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getSignInResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		return response;
	}

	public static List<String> getSignInResponseDataTagNodes() 
	{
		List<String> signInResponseDataTagNodes = new ArrayList<String>();
		signInResponseDataTagNodes.add(RESP_DATA.toString());
		signInResponseDataTagNodes.add(RESP_DATA_LOGIN.toString());
		signInResponseDataTagNodes.add(RESP_DATA_USER_TYPE.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_TITLE.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_FIRST_NAME.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_LAST_NAME.toString());
		signInResponseDataTagNodes.add(RESP_DATA_EMAIL.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_PHONE.toString());
		signInResponseDataTagNodes.add(RESP_DATA_MOBILE.toString());
		signInResponseDataTagNodes.add(RESP_DATA_STATUS.toString());
		signInResponseDataTagNodes.add(RESP_DATA_LAST_LOGIN.toString());
		signInResponseDataTagNodes.add(RESP_DATA_FIRST_LOGIN.toString());
		
		return signInResponseDataTagNodes;
	}
	
	public static List<String> getSignInResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(META.toString());
		errorMetaTagNodes.add(META_CODE.toString());
		errorMetaTagNodes.add(META_ERROR_DETAIL.toString());
		errorMetaTagNodes.add(META_ERROR_TYPE.toString());
		errorMetaTagNodes.add(META_TOKEN.toString());
		errorMetaTagNodes.add(META_XSRF_TOKEN.toString());
		errorMetaTagNodes.add(NOTIFICATION.toString());
		errorMetaTagNodes.add(RESP_DATA.toString());
		
		return errorMetaTagNodes;
	}
	
	public static List<String> getSignUpResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_TOKEN.toString());
		metaTagNodes.add(META_XSRF_TOKEN.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getSignUpResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		return response;
	}

	public static List<String> getSignUpResponseDataTagNodes() 
	{
		List<String> signInResponseDataTagNodes = new ArrayList<String>();
		signInResponseDataTagNodes.add(RESP_DATA.toString());
		signInResponseDataTagNodes.add(RESP_DATA_LOGIN.toString());
		signInResponseDataTagNodes.add(RESP_DATA_USER_TYPE.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_TITLE.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_FIRST_NAME.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_LAST_NAME.toString());
		signInResponseDataTagNodes.add(RESP_DATA_EMAIL.toString());
		//signInResponseDataTagNodes.add(RESP_DATA_PHONE.toString());
		signInResponseDataTagNodes.add(RESP_DATA_MOBILE.toString());
		signInResponseDataTagNodes.add(RESP_DATA_STATUS.toString());
		signInResponseDataTagNodes.add(RESP_DATA_LAST_LOGIN.toString());
		signInResponseDataTagNodes.add(RESP_DATA_FIRST_LOGIN.toString());
		
		return signInResponseDataTagNodes;
	}
	
	public static List<String> getSignUpResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(META.toString());
		errorMetaTagNodes.add(META_CODE.toString());
		errorMetaTagNodes.add(META_ERROR_DETAIL.toString());
		errorMetaTagNodes.add(META_ERROR_TYPE.toString());
		errorMetaTagNodes.add(META_TOKEN.toString());
		errorMetaTagNodes.add(META_XSRF_TOKEN.toString());
		errorMetaTagNodes.add(NOTIFICATION.toString());
		errorMetaTagNodes.add(RESP_DATA.toString());
		
		return errorMetaTagNodes;
	}
	
	public static List<String> getRefreshResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_TOKEN.toString());
		metaTagNodes.add(META_XSRF_TOKEN.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getRefreshResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		return response;
	}

	public static List<String> getRefreshResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(META.toString());
		errorMetaTagNodes.add(META_CODE.toString());
		errorMetaTagNodes.add(META_ERROR_DETAIL.toString());
		errorMetaTagNodes.add(META_ERROR_TYPE.toString());
		errorMetaTagNodes.add(META_TOKEN.toString());
		errorMetaTagNodes.add(META_XSRF_TOKEN.toString());
		errorMetaTagNodes.add(NOTIFICATION.toString());
		errorMetaTagNodes.add(RESP_DATA.toString());
		
		return errorMetaTagNodes;
	}
	
	public static List<String> getRefreshResponseDataTagNodes() 
	{
		List<String> refreshResponseDataTagNodes = new ArrayList<String>();
		refreshResponseDataTagNodes.add(RESP_DATA.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_LOGIN.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_USER_TYPE.toString());
		//refreshResponseDataTagNodes.add(RESP_DATA_TITLE.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_FIRST_NAME.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_LAST_NAME.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_EMAIL.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_PHONE.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_MOBILE.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_STATUS.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_LAST_LOGIN.toString());
		refreshResponseDataTagNodes.add(RESP_DATA_FIRST_LOGIN.toString());
		
		return refreshResponseDataTagNodes;
	}
	
	public static List<String> getSignOutResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_TOKEN.toString());
		metaTagNodes.add(META_XSRF_TOKEN.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getSignOutResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		
		return response;
	}
	
	public static List<String> getSignOutResponseDataTagNodes()
	{
		List<String> signOutDataTagNodes = new ArrayList<String>();
		signOutDataTagNodes.add(RESP_DATA.toString());
		
		return signOutDataTagNodes;
	}
	
	public static List<String> getForgotPasswordResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_TOKEN.toString());
		metaTagNodes.add(META_XSRF_TOKEN.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getForgotPasswordResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		return response;
	}
	
	public static List<String> getForgotPasswordResponseDataTagNodes()
	{
		List<String> signOutDataTagNodes = new ArrayList<String>();
		signOutDataTagNodes.add(RESP_DATA.toString());
		
		return signOutDataTagNodes;
	}
	
	public static List<String> getForgotPasswordResponseErrorMetaTagNodes() 
	{
		ArrayList<String> errorMetaTagNodes = new ArrayList<String>();
		errorMetaTagNodes.add(META.toString());
		errorMetaTagNodes.add(META_CODE.toString());
		errorMetaTagNodes.add(META_ERROR_DETAIL.toString());
		errorMetaTagNodes.add(META_ERROR_TYPE.toString());
		errorMetaTagNodes.add(META_TOKEN.toString());
		errorMetaTagNodes.add(META_XSRF_TOKEN.toString());
		errorMetaTagNodes.add(NOTIFICATION.toString());
		errorMetaTagNodes.add(RESP_DATA.toString());
		
		return errorMetaTagNodes;
	}
	
	public static List<String> getNavResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_REQ_ID.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getNavResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		
		return response;
	}
	
	public static List<String> getNavResponseDataTagNodes()
	{
		List<String> navResponseDataTagNodes = new ArrayList<String>();
		navResponseDataTagNodes.add(RESP_DATA.toString());
		navResponseDataTagNodes.add(RESP_DATA_CATAGORIES.toString());
		navResponseDataTagNodes.add(RESP_DATA_CATAGORIES_LINK_URL.toString());
		navResponseDataTagNodes.add(RESP_DATA_CATAGORIES_LINK_URL_RES_TYPE.toString());
		navResponseDataTagNodes.add(RESP_DATA_CATAGORIES_LINK_URL_RES_VAL.toString());
		
		return navResponseDataTagNodes;
	}
	
	public static List<String> getStyleResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		metaTagNodes.add(META_REQ_ID.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getStyleResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		
		return response;
	}
	
	public static List<String> getStyleResponseDataNodes() 
	{
		List<String> styleDataNodes = new ArrayList<String>();
		styleDataNodes.add(STYLE_RESP_DATA_ID.toString());
		styleDataNodes.add(STYLE_RESP_DATA_PRICE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_DISC_PRICE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_STYLE_TYPE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_PROD_TYPE_ID.toString());
		styleDataNodes.add(STYLE_RESP_DATA_VISUAL_TAG.toString());
		styleDataNodes.add(STYLE_RESP_DATA_PROD_DISP_NAME.toString());
		styleDataNodes.add(STYLE_RESP_DATA_VARIENT_NAME.toString());
		styleDataNodes.add(STYLE_RESP_DATA_MYNTRA_RATING.toString());
		styleDataNodes.add(STYLE_RESP_DATA_CATALOG_ADD_DATE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_BRAND_NAME.toString());
		styleDataNodes.add(STYLE_RESP_DATA_AGE_GROUP.toString());
		styleDataNodes.add(STYLE_RESP_DATA_GENDER.toString());
		styleDataNodes.add(STYLE_RESP_DATA_BASE_COLOUR.toString());
		styleDataNodes.add(STYLE_RESP_DATA_COLOUR1.toString());
		styleDataNodes.add(STYLE_RESP_DATA_COLOUR2.toString());
		styleDataNodes.add(STYLE_RESP_DATA_FASHION_TYPE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_SEASON.toString());
		styleDataNodes.add(STYLE_RESP_DATA_YEAR.toString());
		styleDataNodes.add(STYLE_RESP_DATA_USAGE.toString());
		styleDataNodes.add(STYLE_RESP_DATA_VAT.toString());
		styleDataNodes.add(STYLE_RESP_DATA_NAVIGATION_ID.toString());
		styleDataNodes.add(STYLE_RESP_DATA_LAND_PAGE_URL.toString());
	
		return styleDataNodes;
	}
	
	public static List<String> getStyleResponseArticleAttributeDataNodes() 
	{
		List<String> articleAttributeDataNodes = new ArrayList<String>();
		articleAttributeDataNodes.add(STYLE_RESP_DATA_ARTICLE_ATTRIBUTES.toString());
		
		return articleAttributeDataNodes;
	}
	
	public static List<String> getStyleResponseStyleImagesDataNodes() 
	{
		List<String> styleImagesDataNodes = new ArrayList<String>();
		styleImagesDataNodes.add(RESP_DATA.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_DEFAULT.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_SIZE_REP.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_SEARCH.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_BACK.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_FRONT.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_LEFT.toString());
		styleImagesDataNodes.add(STYLE_RESP_DATA_STYLE_IMAGES_RIGHT.toString());
		
		return styleImagesDataNodes;
	}
	
	public static List<String> getStyleResponseMasterCatagoryDataNodes() 
	{
		List<String> masterCatagoryDataNodes = new ArrayList<String>();
		masterCatagoryDataNodes.add(RESP_DATA.toString());
		masterCatagoryDataNodes.add(STYLE_RESP_DATA_MASTER_CATAGORY.toString());
		masterCatagoryDataNodes.add(STYLE_RESP_DATA_MASTER_CATAGORY_ID.toString());
		masterCatagoryDataNodes.add(STYLE_RESP_DATA_MASTER_CATAGORY_TYPE_NAME.toString());
		masterCatagoryDataNodes.add(STYLE_RESP_DATA_MASTER_CATAGORY_ACTIVE.toString());
		masterCatagoryDataNodes.add(STYLE_RESP_DATA_MASTER_CATAGORY_SOC_SHARING_ENABLED.toString());
		
		return masterCatagoryDataNodes;
	}
	
	public static List<String> getStyleResponseSubCatagoryDataNodes() 
	{
		List<String> subCatagoryDataNodes = new ArrayList<String>();
		subCatagoryDataNodes.add(RESP_DATA.toString());
		subCatagoryDataNodes.add(STYLE_RESP_SUB_CATAGORY.toString());
		subCatagoryDataNodes.add(STYLE_RESP_SUB_CATAGORY_ID.toString());
		subCatagoryDataNodes.add(STYLE_RESP_SUB_CATAGORY_TYPE_NAME.toString());
		subCatagoryDataNodes.add(STYLE_RESP_SUB_CATAGORY_ACTIVE.toString());
		subCatagoryDataNodes.add(STYLE_RESP_SUB_CATAGORY_SOC_SHARING_ENABLED.toString());
		
		return subCatagoryDataNodes;
	}

	public static List<String> getStyleResponseSubArticleDataNodes() 
	{
		List<String> subArticleDataNodes = new ArrayList<String>();
		subArticleDataNodes.add(RESP_DATA.toString());
		subArticleDataNodes.add(STYLE_RESP_SUB_ARTICLE_TYPE.toString());
		subArticleDataNodes.add(STYLE_RESP_SUB_ARTICLE_TYPE_ID.toString());
		subArticleDataNodes.add(STYLE_RESP_SUB_ARTICLE_TYPE_NAME.toString());
		subArticleDataNodes.add(STYLE_RESP_SUB_ARTICLE_TYPE_ACTIVE.toString());
		subArticleDataNodes.add(STYLE_RESP_SUB_ARTICLE_TYPE_SOC_SHARING_ENABLED.toString());
		
		return subArticleDataNodes;
	}
	
	public static List<String> getStyleResponseProductDescriptorDataNodes() 
	{
		List<String> productDescriptorsDataNodes = new ArrayList<String>();
		productDescriptorsDataNodes.add(RESP_DATA.toString());
		productDescriptorsDataNodes.add(STYLE_RESP_PRODUCT_DESCRIPTORS.toString());
		//productDescriptorsDataNodes.add(STYLE_RESP_PRODUCT_DESCRIPTORS_MATERIALS_CARE_DESC.toString());
		
		return productDescriptorsDataNodes;
	}
	
	public static List<String> getStyleResponseStyleOptionsDataNodes() 
	{
		List<String> styleOptionsDataNodes = new ArrayList<String>();
		styleOptionsDataNodes.add(RESP_DATA.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_ID.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_NAME.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_VALUE.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_SKU_ID.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_INVENTORY_COUNT.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_WARE_HOUSE_ID_TO_ITEM_COUNT_MAP.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_SKU_AVAIL_DETAIL_MAP.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_ACTIVE.toString());
		styleOptionsDataNodes.add(STYLE_RESP_STYLE_OPTIONS_AVAILABLE.toString());
																
		return styleOptionsDataNodes;
	}
	
	public static List<String> getStyleOffersResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getStyleOffersResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		
		return response;
	}
	
	public static List<String> getStyleOffersResponseDataTagNodes()
	{
		List<String> styleOffersResponseDataNodes = new ArrayList<String>();
		styleOffersResponseDataNodes.add(RESP_DATA.toString());
		//styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_STATUS.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_COUPON.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_PRICE.toString());
		//styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_TOOLTIP.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_TYPE.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_MINIMUM.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_MINIMUM_COUNT.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_COUPON_DISCOUNT.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_END_DATE.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_ORIGINAL_PRICE.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_COUPON_EXTRA_TNC.toString());
		//styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_GRP_NAME.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_MESSAGE.toString());
		styleOffersResponseDataNodes.add(STYLE_OFFERS_RESP_DATA_COUPONS_NO_OF_COUPONS.toString());
		
		return styleOffersResponseDataNodes;
	}
	
	
	public static List<String> getSearchResponseDataTagNodes()
	{
		List<String> searchResponseDataNodes = new ArrayList<String>();
		searchResponseDataNodes.add(RESP_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_QUERY.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_START.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_ROWS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FACET_FIELD.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FQ.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_SORT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_RET_DOCS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_COLOUR_GROUPING.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FACET.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SYNONYM_MAP.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_QUERY_TYPE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_TOTAL_PRODUCTS_COUNT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_LIST_GROUP.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCTS_COUNT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_FILTERS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_PRODUCTS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO.toString());
		/*
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_NO_INDEXED.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_SYNONYM.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_OR_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_AND_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_CANONICAL_URI.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_URI.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_PARAMETRIZED.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_PATTERN.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FOOTER_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_META_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_DNS_PREFETCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_SHORT_CUT_ICON.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_GPUBLISHER.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TWITTER_CARD.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TWITTER_SITE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_SOCIAL_IMAGE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FB_ADMINS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FB_APP_ID.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_OG_TYPE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_OG_SITE_NAME.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TITLE.toString());*/
		
		return searchResponseDataNodes;
	}
	
	public static List<String> getStyleWithServicabilityResponseMetaTagNodes()
	{
		List<String> metaTagNodes = new ArrayList<String>();
		metaTagNodes.add(META.toString());
		metaTagNodes.add(META_CODE.toString());
		
		return metaTagNodes;
	}
	
	public static List<String> getStyleWithServicabilityResponseNotificationTagNodes()
	{
		List<String> response = new ArrayList<String>();
		response.add(NOTIFICATION.toString());
		
		return response;
	}
	
	public static List<String> getStyleWithServicabilityResponseDataTagNodes() 
	{
		List<String> styleWithServicabilityDataNodes = new ArrayList<String>();
		styleWithServicabilityDataNodes.add(RESP_DATA.toString());
		styleWithServicabilityDataNodes.add(STYLE_RESP_DATA_SERVICEABLE.toString());
		styleWithServicabilityDataNodes.add(STYLE_RESP_DATA_DELIVERY_PROMISE_TIME.toString());
		styleWithServicabilityDataNodes.add(STYLE_RESP_DATA_PINCODE.toString());
		styleWithServicabilityDataNodes.add(STYLE_RESP_DATA_COD_LIMITS.toString());
		
		return styleWithServicabilityDataNodes;
	}
	
	public static List<String> getStyleWithServicabilityResponseErrorDataTagNodes() 
	{
		List<String> styleWithServicabilityErrorDataNodes = new ArrayList<String>();
		styleWithServicabilityErrorDataNodes.add(RESP_DATA.toString());
		styleWithServicabilityErrorDataNodes.add(STYLE_RESP_DATA_SERVICEABLE.toString());
		styleWithServicabilityErrorDataNodes.add(STYLE_RESP_DATA_DELIVERY_PROMISE_TIME.toString());
		styleWithServicabilityErrorDataNodes.add(STYLE_RESP_DATA_PINCODE.toString());
		styleWithServicabilityErrorDataNodes.add(STYLE_RESP_DATA_COD_LIMITS.toString());
		//styleWithServicabilityErrorDataNodes.add(NOTIFICATION.toString());
		styleWithServicabilityErrorDataNodes.add(META.toString());
		styleWithServicabilityErrorDataNodes.add(META_CODE.toString());
		
		return styleWithServicabilityErrorDataNodes;
	}
	
	public static List<String> getSearchWithFacetsResponseDataTagNodes()
	{
		List<String> searchResponseDataNodes = new ArrayList<String>();
		searchResponseDataNodes.add(RESP_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_QUERY.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_START.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_ROWS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FACET_FIELD.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FQ.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_SORT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_RET_DOCS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_COLOUR_GROUPING.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEARCH_FACET.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SYNONYM_MAP.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_QUERY_TYPE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_TOTAL_PRODUCTS_COUNT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_LIST_GROUP.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCTS_COUNT.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_FILTERS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_RESULTS_PRODUCTS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO.toString());
		/*searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_NO_INDEXED.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_SYNONYM.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_OR_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_AND_SEARCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_CANONICAL_URI.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_URI.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_IS_PARAMETRIZED.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_PATTERN.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FOOTER_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_META_DATA.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_DNS_PREFETCH.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_SHORT_CUT_ICON.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_GPUBLISHER.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TWITTER_CARD.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TWITTER_SITE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_SOCIAL_IMAGE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FB_ADMINS.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_FB_APP_ID.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_OG_TYPE.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_OG_SITE_NAME.toString());
		searchResponseDataNodes.add(SEARCH_RESP_DATA_SEO_TITLE.toString());*/
		
		return searchResponseDataNodes;
	}

	public static List<String> getCreateLookDataTagNodes()
	{
		List<String> createLookDataTagNodes = new ArrayList<String>();
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_LOOKID.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_TITLE.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_DESCRIPTION.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISACTIVE.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISDRAFT.toString());
		createLookDataTagNodes.add(LOOKS_CREATE_RESP_DATA_ISREMYX.toString());
		return createLookDataTagNodes;
		
	}
	
	
}
