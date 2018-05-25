/**
 * 
 */
package com.myntra.apiTests.portalservices.styleservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sneha.deep
 *
 */
public enum StyleServiceNodes 
{
	// data nodes
	STYLE_DATA("data"),
	STYLE_DATA_ID("id"),
	STYLE_DATA_COMMENTS("comments"),
	STYLE_DATA_PRICE("price"),
	STYLE_DATA_DISC_PRICE("discountedPrice"),
	STYLE_DATA_TYPE("styleType"),
	STYLE_DATA_PROD_TYPE_ID("productTypeId"),
	STYLE_DATA_BRAND_LOGO("brandLogo"),
	STYLE_DATA_ARTICAL_NUMBER("articleNumber"),
	STYLE_DATA_PROD_TAG("productTag"),
	STYLE_DATA_VISUAL_TAG("visualTag"),
	STYLE_DATA_DISP_NAME("productDisplayName"),
	STYLE_DATA_VARIENT_NAME("variantName"),
	STYLE_DATA_MYNTRA_RATING("myntraRating"),
	STYLE_DATA_ADD_DATE("addDate"),
	STYLE_DATA_CATALOG_ADD_DATE("catalogAddDate"),
	STYLE_DATA_BRAND_NAME("brandName"),
	STYLE_DATA_BRAND_AGE_GRP("ageGroup"),
	STYLE_DATA_GENDER("gender"),
	STYLE_DATA_BASE_COLOR("baseColour"),
	STYLE_DATA_COLOR1("colour1"),
	STYLE_DATA_COLOR2("colour2"),
	STYLE_DATA_FASHION_TYPE("fashionType"),
	STYLE_DATA_SEASON("season"),
	STYLE_DATA_YEAR("year"),
	STYLE_DATA_USAGE("usage"),
	STYLE_DATA_VAT("vat"),
	STYLE_DATA_NAVIGATION_ID("navigationId"),
	STYLE_DATA_LANDING_PAGE_URL("landingPageUrl"),
	
	// articleType data nodes
	STYLE_DATA_ARTICLE_TYPE("articleType"),
	STYLE_DATA_ARTICLE_TYPE_ID("id"),
	STYLE_DATA_ARTICLE_TYPE_FILTER_ORDER("filterOrder"),
	STYLE_DATA_ARTICLE_TYPE_NAME("typeName"),
	STYLE_DATA_ARTICLE_TYPE_CODE("typeCode"),
	STYLE_DATA_ARTICLE_TYPE_ACTIVE("active"),
	STYLE_DATA_ARTICLE_TYPE_SOCIAL_SHARING_ENABLED("socialSharingEnabled"),
	
	// subCategory data nodes
	STYLE_DATA_SUB_CATEGORY("subCategory"),
	STYLE_DATA_SUB_CATEGORY_ID("id"),
	STYLE_DATA_SUB_CATEGORY_FILTER_ORDER("filterOrder"),
	STYLE_DATA_SUB_CATEGORY_TYPE_NAME("typeName"),
	STYLE_DATA_SUB_CATEGORY_TYPE_CODE("typeCode"),
	STYLE_DATA_SUB_CATEGORY_ACTIVE("active"),
	STYLE_DATA_SUB_CATEGORY_SOCIAL_SHARING_ENABLED("socialSharingEnabled"),

	// masterCategory
	STYLE_DATA_MASTER_CATAGORY("masterCategory"),
	STYLE_DATA_MASTER_CATAGORY_ID("id"),
	STYLE_DATA_MASTER_CATAGORY_FILTER_ORDER("filterOrder"),
	STYLE_DATA_MASTER_CATAGORY_TYPE_NAME("typeName"),
	STYLE_DATA_MASTER_CATAGORY_TYPE_CODE("typeCode"),
	STYLE_DATA_MASTER_CATAGORY_ACTIVE("active"),
	STYLE_DATA_MASTER_CATAGORY_SOCIAL_SHARING_ENABLED("socialSharingEnabled"),
	STYLE_DATA_MASTER_CATAGORY_IS_FRAGILE("isFragile"),
	STYLE_DATA_MASTER_CATAGORY_IS_RETURNABLE("isReturnable"),
	STYLE_DATA_MASTER_CATAGORY_IS_EXCHANGABLE("isExchangeable"),
	STYLE_DATA_MASTER_CATAGORY_PICKUP_ENABLED("pickupEnabled"),
	STYLE_DATA_MASTER_CATAGORY_IS_TRYANDBUY_ENABLED("isTryAndBuyEnabled"),
	STYLE_DATA_MASTER_CATAGORY_IS_LARGE("isLarge"),
	STYLE_DATA_MASTER_CATAGORY_IS_HAZMAT("isHazmat"),
	STYLE_DATA_MASTER_CATAGORY_IS_JEWELLERY("isJewellery"),
	
	// brandDetailsEntry
	STYLE_DATA_BRAND_DETAILS_ENTRY("brandDetailsEntry"),
	STYLE_DATA_BRAND_DETAILS_ENTRY_ID("id"),
	STYLE_DATA_BRAND_DETAILS_ENTRY_NAME("name"),
	STYLE_DATA_BRAND_DETAILS_ENTRY_LOGOUT_URL("logoURL"),
	
	// styleImages
	STYLE_DATA_STYLE_IMAGES("styleImages"),
	STYLE_DATA_STYLE_IMAGES_SEARCH("search"),
	STYLE_DATA_STYLE_IMAGES_PATH("path"),
	STYLE_DATA_STYLE_IMAGES_RESOLUTION_FORMULA("resolutionFormula"),
	STYLE_DATA_STYLE_IMAGES_DOMAIN("domain"),
	STYLE_DATA_STYLE_IMAGES_SECURE_DOMAIN("securedDomain"),
	STYLE_DATA_STYLE_IMAGES_RELATIVE_PATH("relativePath"),
	STYLE_DATA_STYLE_IMAGES_STORED_UPLOADER_TYPE("storedUploaderType"),
	STYLE_DATA_STYLE_IMAGES_SERVING_UPLOADER_TYPE("servingUploaderType"),
	STYLE_DATA_STYLE_IMAGES_SUPPORTED_RESOLUTION("supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_IMAGE_TYPE("imageType"),
	STYLE_DATA_STYLE_IMAGES_IMAGE_URL("imageURL"),
	STYLE_DATA_STYLE_IMAGES_RESOLUTIONS("resolutions"),
	
	// supportedResolutions
	STYLE_DATA_STYLE_IMAGES_DEF_DEP_SUP_RES("data.styleImages.default_deprecated.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_DEF_RES("data.styleImages.default.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_SEARCH_SUP_RES("data.styleImages.search.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_BACK_SUP_RES("data.styleImages.back.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_FRONT_SUP_RES("data.styleImages.front.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_LEFT_SUP_RES("data.styleImages.left.supportedResolutions"),
	STYLE_DATA_STYLE_IMAGES_RIGHT_SUP_RES("data.styleImages.right.supportedResolutions"),
	
	// productDescriptors
	STYLE_DATA_PROD_DESCRIPTORS("productDescriptors"),
	STYLE_DATA_PROD_DESCRIPTORS_MATERIALS_CARE_DESC("materials_care_desc"),
	STYLE_DATA_PROD_DESCRIPTORS_STYLE_NOTE("style_note"),
	STYLE_DATA_PROD_DESCRIPTORS_DESCRIPTION("description"),
	
	// articalAttribute data nodes
	STYLE_DATA_ARTICAL_ATTRIBUTE("data.articleAttributes"),
		
	// styleOptions
	STYLE_DATA_STYLE_OPTIONS("styleOptions"),
	STYLE_DATA_STYLE_OPTIONS_ID("id"),
	STYLE_DATA_STYLE_OPTIONS_NAME("name"),
	STYLE_DATA_STYLE_OPTIONS_VALUE("value"),
	STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE("unifiedSize"),
	STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE_VALUE("unifiedSizeValue"),
	STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE_SCALE("unifiedSizeScale"),
	STYLE_DATA_STYLE_OPTIONS_ALL_SIZE("allSize"),
	STYLE_DATA_STYLE_OPTIONS_SKUID("skuId"),
	STYLE_DATA_STYLE_OPTIONS_INVENTORY_COUNT("inventoryCount"),
	STYLE_DATA_STYLE_OPTIONS_WAREHOUSE_ID_TO_ITEM_COUNT_MAP("warehouseIdToItemCountMap"),
	STYLE_DATA_STYLE_OPTIONS_SKU_AVAILABILITY_DETAIL_MAP("skuAvailabilityDetailMap"),
	STYLE_DATA_STYLE_OPTIONS_TAX_ENTRY("taxEntry"),
     
	// recommendations
	STYLE_DATA_RECOMMENDATIONS("recommendations"),
	STYLE_DATA_RECOMMENDATIONS_ALSO_POPULAR("alsoPopular"),
	STYLE_DATA_RECOMMENDATIONS_MATCH_WITH("matchWith"),

	// taxEntry
	STYLE_DATA_TAX_ENTRY("taxEntry"),
	STYLE_DATA_TAX_PERCENTAGE("taxPercentage"),
	
	// styleVisibilityInfo
	STYLE_DATA_STYLE_VISIBILITY_INFO("styleVisibilityInfo"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_STYLE_ID("styleId"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_PVV1("pvv1"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_PVV2("pvv2"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_PVV3("pvv3"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_NEW_STYLE("newStyle"),
	STYLE_DATA_STYLE_VISIBILITY_INFO_REPLENISHED_STYLE("replenishedStyle"),
	
	// styleDiscountData
	STYLE_DATA_DISCOUNT_DATA("data.discountData"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_ID("data.discountData.discountId"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TYPE("data.discountData.discountType"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_AMT("data.discountData.discountAmount"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_PERCENT("data.discountData.discountPercent"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT("data.discountData.discountText"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_TEXT("data.discountData.discountText.text"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_DISCOUNT_TYPE("data.discountData.discountText.discountType"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_HAS_FREE_ITEM("data.discountData.discountText.hasFreeItem"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT("data.discountData.discountToolTipText"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_TEXT("data.discountData.discountToolTipText.text"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_DISCOUNT_TYPE("data.discountData.discountToolTipText.discountType"),
	STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_HAS_FREE_ITEM("data.discountData.discountToolTipText.hasFreeItem"),
	
	
	
	SERVICE_DATA_DISCOUNT_DATA("data.discountData"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_ID("data.discountData.discountId"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TYPE("data.discountData.discountType"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_AMT("data.discountData.discountAmount"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_PERCENT("data.discountData.discountPercent"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT("data.discountData.discountText"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_TEXT("data.discountData.discountText.text"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_DISCOUNT_TYPE("data.discountData.discountText.discountType"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_HAS_FREE_ITEM("data.discountData.discountText.hasFreeItem"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT("data.discountData.discountToolTipText"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_TEXT("data.discountData.discountToolTipText.text"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_DISCOUNT_TYPE("data.discountData.discountToolTipText.discountType"),
	SERVICE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_HAS_FREE_ITEM("data.discountData.discountToolTipText.hasFreeItem"),
	
	PREORDER_DATA("data..advanceOrderOptions"),
	PREORDER_DATA_PRENODE("data..advanceOrderOptions.preOrder"),
	PREORDER_DATA_ID("data..advanceOrderOptions.preOrder.id"),
	PREORDER_DATA_LAUNCHDATE("data..advanceOrderOptions.preOrder.launchDate"),
	PREORDER_DATA_STARTDATE("data..advanceOrderOptions.preOrder.startDate"),
	PREORDER_DATA_ENDDATE("data..advanceOrderOptions.preOrder.endDate"),
	PREORDER_DATA_PDPTEXTINVENTORYISZERO("data..advanceOrderOptions.preOrder.pdpTextIfInventoryIsZero"),
	PREORDER_DATA_PDPTEXTIFINVENTORYISAVAILABLE("data..advanceOrderOptions.preOrder.pdpTextIfInventoryIsAvailable"),
	PREORDER_DATA_PRODUCTIMAGETAG("data..advanceOrderOptions.preOrder.productImageTag"),
	PREORDER_DATA_DISCLAIMERTEXT("data..advanceOrderOptions.preOrder.disclaimerText"),
	PREORDER_DATA_CODENABLED("data..advanceOrderOptions.preOrder.codEnabled"),
	PREORDER_DATA_ADVANCEAMOUNTPERCENT("data..advanceOrderOptions.preOrder.advanceAmountPercent"),
	PREORDER_DATA_ACCESSCODEENABLED("data..advanceOrderOptions.preOrder.accessCodeEnabled"),
	PREORDER_DATA_scarcityThresholdSCARCITYTHRESHOLD("data..advanceOrderOptions.preOrder.scarcityThreshold"),
	PREORDER_DATA_ISACTIVE("data..advanceOrderOptions.preOrder.isActive");
	
	
	
	private String nodePath;
	
	private StyleServiceNodes(String nodePath) {
		this.nodePath = nodePath;
	}
	
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getDataNodes()
	{
		List<String> styleDataNodes = new ArrayList<String>();
		styleDataNodes.add(STYLE_DATA_ID.getNodePath());
		//styleDataNodes.add(STYLE_DATA_COMMENTS.getNodePath());
		styleDataNodes.add(STYLE_DATA_PRICE.getNodePath());
		styleDataNodes.add(STYLE_DATA_DISC_PRICE.getNodePath());
		styleDataNodes.add(STYLE_DATA_TYPE.getNodePath());
		//styleDataNodes.add(STYLE_DATA_PROD_TYPE_ID.getNodePath());
		//styleDataNodes.add(STYLE_DATA_BRAND_LOGO.getNodePath());
		styleDataNodes.add(STYLE_DATA_ARTICAL_NUMBER.getNodePath());
		styleDataNodes.add(STYLE_DATA_PROD_TAG.getNodePath());
		styleDataNodes.add(STYLE_DATA_VISUAL_TAG.getNodePath());
		styleDataNodes.add(STYLE_DATA_DISP_NAME.getNodePath());
		styleDataNodes.add(STYLE_DATA_VARIENT_NAME.getNodePath());
		//styleDataNodes.add(STYLE_DATA_MYNTRA_RATING.getNodePath());
		styleDataNodes.add(STYLE_DATA_ADD_DATE.getNodePath());
		//styleDataNodes.add(STYLE_DATA_CATALOG_ADD_DATE.getNodePath());
		styleDataNodes.add(STYLE_DATA_BRAND_NAME.getNodePath());
		styleDataNodes.add(STYLE_DATA_BRAND_AGE_GRP.getNodePath());
		styleDataNodes.add(STYLE_DATA_GENDER.getNodePath());
		styleDataNodes.add(STYLE_DATA_BASE_COLOR.getNodePath());
		styleDataNodes.add(STYLE_DATA_COLOR1.getNodePath());
		styleDataNodes.add(STYLE_DATA_COLOR2.getNodePath());
		styleDataNodes.add(STYLE_DATA_FASHION_TYPE.getNodePath());
		styleDataNodes.add(STYLE_DATA_SEASON.getNodePath());
		styleDataNodes.add(STYLE_DATA_YEAR.getNodePath());
		styleDataNodes.add(STYLE_DATA_USAGE.getNodePath());
		//styleDataNodes.add(STYLE_DATA_VAT.getNodePath());
		styleDataNodes.add(STYLE_DATA_NAVIGATION_ID.getNodePath());
		styleDataNodes.add(STYLE_DATA_LANDING_PAGE_URL.getNodePath());
		
		
		return styleDataNodes;		
	}

	public static List<String> getArticalTypeDataNodes()
	{
		List<String> subArticalDataNodes = new ArrayList<String>();
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_ID.getNodePath());
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_FILTER_ORDER.getNodePath());
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_NAME.getNodePath());
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_CODE.getNodePath());
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_ACTIVE.getNodePath());
		subArticalDataNodes.add(STYLE_DATA_ARTICLE_TYPE_SOCIAL_SHARING_ENABLED.getNodePath());
		
		return subArticalDataNodes;		
	}

	public static List<String> getSubCategoryDataNodes()
	{
		List<String> subCategoryDataNodes = new ArrayList<String>();
		subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_ID.getNodePath());
		subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_FILTER_ORDER.getNodePath());
		//subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_TYPE_NAME.getNodePath());
		//subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_TYPE_CODE.getNodePath());
		subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_ACTIVE.getNodePath());
		subCategoryDataNodes.add(STYLE_DATA_SUB_CATEGORY_SOCIAL_SHARING_ENABLED.getNodePath());
		
		return subCategoryDataNodes;		
	}
	
	public static List<String> getMasterCategoryDataNodes()
	{
		List<String> masterCategoryDataNodes = new ArrayList<String>();
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_ID.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_FILTER_ORDER.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_TYPE_NAME.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_TYPE_CODE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_ACTIVE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_SOCIAL_SHARING_ENABLED.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_FRAGILE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_RETURNABLE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_EXCHANGABLE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_PICKUP_ENABLED.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_TRYANDBUY_ENABLED.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_LARGE.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_HAZMAT.getNodePath());
		masterCategoryDataNodes.add(STYLE_DATA_MASTER_CATAGORY_IS_JEWELLERY.getNodePath());
		
		return masterCategoryDataNodes;		
	}

	public static List<String> getBrandDetailsEntryDataNodes()
	{
		List<String> brandDetailsEntryDataNodes = new ArrayList<String>();
		brandDetailsEntryDataNodes.add(STYLE_DATA_BRAND_DETAILS_ENTRY_ID.getNodePath());
		brandDetailsEntryDataNodes.add(STYLE_DATA_BRAND_DETAILS_ENTRY_NAME.getNodePath());
		//brandDetailsEntryDataNodes.add(STYLE_DATA_BRAND_DETAILS_ENTRY_LOGOUT_URL.getNodePath());
		
		return brandDetailsEntryDataNodes;		
	}

	public static List<String> getStyleimagesDataNodes()
	{
		List<String> styleImagesDataNodes = new ArrayList<String>();
		//styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_SEARCH.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_PATH.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_RESOLUTION_FORMULA.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_DOMAIN.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_SECURE_DOMAIN.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_RELATIVE_PATH.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_STORED_UPLOADER_TYPE.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_SERVING_UPLOADER_TYPE.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_SUPPORTED_RESOLUTION.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_IMAGE_TYPE.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_IMAGE_URL.getNodePath());
		styleImagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_RESOLUTIONS.getNodePath());
																
		return styleImagesDataNodes;		
	}

	public static List<String> getProductDescriptorsDataNodes()
	{
		List<String> productDescriptorsDataNodes = new ArrayList<String>();
		productDescriptorsDataNodes.add(STYLE_DATA_PROD_DESCRIPTORS_MATERIALS_CARE_DESC.getNodePath());
		productDescriptorsDataNodes.add(STYLE_DATA_PROD_DESCRIPTORS_STYLE_NOTE.getNodePath());
		productDescriptorsDataNodes.add(STYLE_DATA_PROD_DESCRIPTORS_DESCRIPTION.getNodePath());
		
		return productDescriptorsDataNodes;		
	}

	public static List<String> getStyleOptionsDataNodes()
	{
		List<String> styleOptionsDataNodes = new ArrayList<String>();
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_ID.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_NAME.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_VALUE.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE_VALUE.getNodePath());
		//styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE_SCALE.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_ALL_SIZE.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_SKUID.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_INVENTORY_COUNT.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_WAREHOUSE_ID_TO_ITEM_COUNT_MAP.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_SKU_AVAILABILITY_DETAIL_MAP.getNodePath());
		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_TAX_ENTRY.getNodePath());
		
		//styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE.getNodePath());
		//styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SIZE_VAL.getNodePath());
		//styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_ALL_SIZE.getNodePath());
		//styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_UNIFIED_SKU_ID.getNodePath());
//		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_INV_COUNT.getNodePath());
//		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_WH_ITEM_COUNT_MAP.getNodePath());
//		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_SKU_AVAIL_DET_MAP.getNodePath());
//		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_ACTIVE.getNodePath());
//		styleOptionsDataNodes.add(STYLE_DATA_STYLE_OPTIONS_AVAILABLE.getNodePath());
		
		return styleOptionsDataNodes;		
	}

	public static List<String> getSupportedResolutionDataNodes()
	{
		List<String> styleimagesDataNodes = new ArrayList<String>();
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_DEF_DEP_SUP_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_DEF_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_SEARCH_SUP_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_BACK_SUP_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_FRONT_SUP_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_LEFT_SUP_RES.getNodePath());
		styleimagesDataNodes.add(STYLE_DATA_STYLE_IMAGES_RIGHT_SUP_RES.getNodePath());
		
		return styleimagesDataNodes;		
	}

	public static List<String> getPreOrderDataNodes()
	{
		List<String> preOrderDataNodes = new ArrayList<String>();
		
		preOrderDataNodes.add(PREORDER_DATA.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_PRENODE.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_ID.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_LAUNCHDATE.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_STARTDATE.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_ENDDATE.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_PDPTEXTINVENTORYISZERO.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_PDPTEXTIFINVENTORYISAVAILABLE.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_PRODUCTIMAGETAG.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_DISCLAIMERTEXT.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_CODENABLED.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_ADVANCEAMOUNTPERCENT.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_ACCESSCODEENABLED.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_scarcityThresholdSCARCITYTHRESHOLD.getNodePath());
		preOrderDataNodes.add(PREORDER_DATA_ISACTIVE.getNodePath());
		
		return preOrderDataNodes;

	

		
	}
	public static List<String> getArticleAttributesDataNodes()
	{
		List<String> articleAttributesDataNodes = new ArrayList<String>();
		articleAttributesDataNodes.add(STYLE_DATA_ARTICAL_ATTRIBUTE.getNodePath());
		
		return articleAttributesDataNodes;		
	}
	
	public static List<String> getTaxEntryDataNodes()
	{
		List<String> taxEntryDataNodes = new ArrayList<String>();
		taxEntryDataNodes.add(STYLE_DATA_TAX_PERCENTAGE.getNodePath());
		
		return taxEntryDataNodes;		
	}

	public static List<String> getRecommendationsDataNodes()
	{
		List<String> recommendationsDataNodes = new ArrayList<String>();
		recommendationsDataNodes.add(STYLE_DATA_RECOMMENDATIONS_ALSO_POPULAR.getNodePath());
		recommendationsDataNodes.add(STYLE_DATA_RECOMMENDATIONS_MATCH_WITH.getNodePath());

		return recommendationsDataNodes;		
	}

	public static List<String> getStyleVisibilityInfoDataNodes()
	{
		List<String> styleVisibilityInfoNodes = new ArrayList<String>();
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_STYLE_ID.getNodePath());
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_PVV1.getNodePath());
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_PVV2.getNodePath());
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_PVV3.getNodePath());
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_NEW_STYLE.getNodePath());
		styleVisibilityInfoNodes.add(STYLE_DATA_STYLE_VISIBILITY_INFO_REPLENISHED_STYLE.getNodePath());
		
		return styleVisibilityInfoNodes;		
	}

	public static List<String> getStyleDiscountDataNodes()
	{
		List<String> styleDiscountDataNodes = new ArrayList<String>();
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_ID.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TYPE.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_AMT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_PERCENT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_TEXT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_DISCOUNT_TYPE.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_HAS_FREE_ITEM.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_TEXT.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_DISCOUNT_TYPE.getNodePath());
		styleDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_HAS_FREE_ITEM.getNodePath());
		
		return styleDiscountDataNodes;		
	}
	
	public static List<String> getServiceDiscountDataNodes()
	{
		List<String> serviceDiscountDataNodes = new ArrayList<String>();
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_ID.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TYPE.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_AMT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_PERCENT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_TEXT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_DISCOUNT_TYPE.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TEXT_HAS_FREE_ITEM.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_TEXT.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_DISCOUNT_TYPE.getNodePath());
		serviceDiscountDataNodes.add(STYLE_DATA_DISCOUNT_DATA_DISCOUNT_TOOL_TIP_TEXT_HAS_FREE_ITEM.getNodePath());
		
		return serviceDiscountDataNodes;		
	}
	
	
}
