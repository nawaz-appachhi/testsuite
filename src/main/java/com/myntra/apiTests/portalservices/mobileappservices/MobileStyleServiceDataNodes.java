package com.myntra.apiTests.portalservices.mobileappservices;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum MobileStyleServiceDataNodes 
{
	MOBILE_STYLE_RESP_META("meta"),
	MOBILE_STYLE_RESP_META_CODE("meta.code"),
	MOBILE_STYLE_RESP_META_REQ_ID("meta.requestId"),
	
	MOBILE_STYLE_RESP_NOTIFICATION("notification"),
	
	MOBILE_STYLE_RESP_DATA("data"),
	MOBILE_STYLE_RESP_DATA_ID("data.id"),
	MOBILE_STYLE_RESP_DATA_COMMENTS("data.comments"),
	MOBILE_STYLE_RESP_DATA_PRICE("data.price"),
	MOBILE_STYLE_RESP_DATA_DISC_PRICE("data.discountedPrice"),
	MOBILE_STYLE_RESP_DATA_STYLE_TYPE("data.styleType"),
	MOBILE_STYLE_RESP_DATA_PROD_TYPE_ID("data.productTypeId"),
	MOBILE_STYLE_RESP_DATA_BRAND_LOGO("data.brandLogo"),
	MOBILE_STYLE_RESP_DATA_ARTICAL_NUMBER("data.articleNumber"),
	MOBILE_STYLE_RESP_DATA_PROD_TAG("data.productTag"),
	MOBILE_STYLE_RESP_DATA_VISUAL_TAG("data.visualTag"),
	MOBILE_STYLE_RESP_DATA_PROD_DISP_NAME("data.productDisplayName"),
	MOBILE_STYLE_RESP_DATA_VARIENT_NAME("data.variantName"),
	MOBILE_STYLE_RESP_DATA_MYNTRA_RATING("data.myntraRating"),
	MOBILE_STYLE_RESP_DATA_ADD_DATE("data.addDate"),
	MOBILE_STYLE_RESP_DATA_CATALOG_ADD_DATE("data.catalogAddDate"),
	MOBILE_STYLE_RESP_DATA_BRAND_NAME("data.brandName"),
	MOBILE_STYLE_RESP_DATA_BRAND_AGE_GRP("data.ageGroup"),
	MOBILE_STYLE_RESP_DATA_GENDER("data.gender"),
	MOBILE_STYLE_RESP_DATA_BASE_COLOR("data.baseColour"),
	MOBILE_STYLE_RESP_DATA_COLOR1("data.colour1"),
	MOBILE_STYLE_RESP_DATA_COLOR2("data.colour2"),
	MOBILE_STYLE_RESP_DATA_FASHION_TYPE("data.fashionType"),
	MOBILE_STYLE_RESP_DATA_SEASON("data.season"),
	MOBILE_STYLE_RESP_DATA_YEAR("data.year"),
	MOBILE_STYLE_RESP_DATA_USAGE("data.usage"),
	MOBILE_STYLE_RESP_DATA_VAT("data.vat"),
	MOBILE_STYLE_RESP_DATA_NAVIGATION_ID("data.navigationId"),
	MOBILE_STYLE_RESP_DATA_LANDING_PAGE_URL("data.landingPageUrl"),
	
	MOBILE_STYLE_RESP_DATA_ARTICLE_ATTRIBUTES("data.articleAttributes"),
	
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES("data.styleImages"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_DEFAULT("data.styleImages.default"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_SIZE_REP("data.styleImages.size_representation"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_SEARCH("data.styleImages.search"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_BACK("data.styleImages.back"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_FRONT("data.styleImages.front"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_LEFT("data.styleImages.left"),
	MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_RIGHT("data.styleImages.right"),
	
	
	MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY("data.masterCategory"),
	MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_ID("data.masterCategory.id"),
	MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_TYPE_NAME("data.masterCategory.typeName"),
	MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_ACTIVE("data.masterCategory.active"),
	MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_SOC_SHARING_ENABLED("data.masterCategory.socialSharingEnabled"),
	
	MOBILE_STYLE_RESP_SUB_CATAGORY("data.subCategory"),
	MOBILE_STYLE_RESP_SUB_CATAGORY_ID("data.subCategory.id"),
	MOBILE_STYLE_RESP_SUB_CATAGORY_TYPE_NAME("data.subCategory.typeName"),
	MOBILE_STYLE_RESP_SUB_CATAGORY_ACTIVE("data.subCategory.active"),
	MOBILE_STYLE_RESP_SUB_CATAGORY_SOC_SHARING_ENABLED("data.subCategory.socialSharingEnabled"),
	
	MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE("data.articleType"),
	MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_ID("data.articleType.id"),
	MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_NAME("data.articleType.typeName"),
	MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_ACTIVE("data.articleType.active"),
	MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_SOC_SHARING_ENABLED("data.articleType.socialSharingEnabled"),
	
	MOBILE_STYLE_RESP_PRODUCT_DESCRIPTORS("data.productDescriptors"),
	MOBILE_STYLE_RESP_PRODUCT_DESCRIPTORS_MATERIALS_CARE_DESC("data.productDescriptors.materials_care_desc"),
	
	MOBILE_STYLE_RESP_STYLE_OPTIONS("data.styleOptions"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_ID("data.styleOptions.id"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_NAME("data.styleOptions.name"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_VALUE("data.styleOptions.value"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_ID("data.styleOptions.skuId"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_INVENTORY_COUNT("data.styleOptions.inventoryCount"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_WARE_HOUSE_ID_TO_ITEM_COUNT_MAP("data.styleOptions.warehouseIdToItemCountMap"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_AVAIL_DETAIL_MAP("data.styleOptions.skuAvailabilityDetailMap"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_ACTIVE("data.styleOptions.active"),
	MOBILE_STYLE_RESP_STYLE_OPTIONS_AVAILABLE("data.styleOptions.available");
	
	private String nodePath;
	
	private MobileStyleServiceDataNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return nodePath;
	}
	
	public static List<String> getMobileStyleServiceResponseMetaTagNodes()
	{
		List<String> mobileStyleMetaTagNodes = new ArrayList<String>();
		mobileStyleMetaTagNodes.add(MOBILE_STYLE_RESP_META.getNodePath());
		mobileStyleMetaTagNodes.add(MOBILE_STYLE_RESP_META_CODE.getNodePath());
		mobileStyleMetaTagNodes.add(MOBILE_STYLE_RESP_META_REQ_ID.getNodePath());
		
		return mobileStyleMetaTagNodes;
	}
	
	public static List<String> getMobileStyleServiceResponseNotificationTagNodes()
	{
		List<String> mobileStyleNotificationNodes = new ArrayList<String>();
		mobileStyleNotificationNodes.add(MOBILE_STYLE_RESP_NOTIFICATION.getNodePath());
		
		return mobileStyleNotificationNodes;
	}
	
	public static List<String> getMobileStyleServiceResponseDataNodes()
	{
		List<String> mobileStyleDataNodes = new ArrayList<String>();
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_ID.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_COMMENTS.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_PRICE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_DISC_PRICE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_TYPE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_PROD_TYPE_ID.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_BRAND_LOGO.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_ARTICAL_NUMBER.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_PROD_TAG.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_VISUAL_TAG.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_PROD_DISP_NAME.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_VARIENT_NAME.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_MYNTRA_RATING.getNodePath());
		//dataTagNodes.add(MOBILE_STYLE_RESP_DATA_ADD_DATE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_CATALOG_ADD_DATE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_BRAND_NAME.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_BRAND_AGE_GRP.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_GENDER.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_BASE_COLOR.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_COLOR1.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_COLOR2.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_FASHION_TYPE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_SEASON.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_YEAR.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_USAGE.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_VAT.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_NAVIGATION_ID.getNodePath());
		mobileStyleDataNodes.add(MOBILE_STYLE_RESP_DATA_LANDING_PAGE_URL.getNodePath());
		
		return mobileStyleDataNodes;
	}
	
	public static List<String> getMobileStyleResponseArticleAttributeDataNodes() 
	{
		List<String> mobileStyleArticleAttributeDataNodes = new ArrayList<String>();
		mobileStyleArticleAttributeDataNodes.add(MOBILE_STYLE_RESP_DATA_ARTICLE_ATTRIBUTES.getNodePath());
		
		return mobileStyleArticleAttributeDataNodes;
	}
	
	public static List<String> getMobileStyleResponseStyleImagesDataNodes() 
	{
		List<String> styleImagesDataNodes = new ArrayList<String>();
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_DEFAULT.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_SIZE_REP.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_SEARCH.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_BACK.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_FRONT.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_LEFT.getNodePath());
		styleImagesDataNodes.add(MOBILE_STYLE_RESP_DATA_STYLE_IMAGES_RIGHT.getNodePath());
		
		return styleImagesDataNodes;
	}
	
	public static List<String> getMobileStyleResponseMasterCatagoryDataNodes() 
	{
		List<String> masterCatagoryDataNodes = new ArrayList<String>();
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY.getNodePath());
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_ID.getNodePath());
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_TYPE_NAME.getNodePath());
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_ACTIVE.getNodePath());
		masterCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA_MASTER_CATAGORY_SOC_SHARING_ENABLED.getNodePath());
		
		return masterCatagoryDataNodes;
	}
	
	public static List<String> getMobileStyleResponseSubCatagoryDataNodes() 
	{
		List<String> subCatagoryDataNodes = new ArrayList<String>();
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_SUB_CATAGORY.getNodePath());
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_SUB_CATAGORY_ID.getNodePath());
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_SUB_CATAGORY_TYPE_NAME.getNodePath());
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_SUB_CATAGORY_ACTIVE.getNodePath());
		subCatagoryDataNodes.add(MOBILE_STYLE_RESP_SUB_CATAGORY_SOC_SHARING_ENABLED.getNodePath());
		
		return subCatagoryDataNodes;
	}

	public static List<String> getMobileStyleResponseSubArticleDataNodes() 
	{
		List<String> subArticleDataNodes = new ArrayList<String>();
		subArticleDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		subArticleDataNodes.add(MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE.getNodePath());
		subArticleDataNodes.add(MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_ID.getNodePath());
		subArticleDataNodes.add(MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_NAME.getNodePath());
		subArticleDataNodes.add(MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_ACTIVE.getNodePath());
		subArticleDataNodes.add(MOBILE_STYLE_RESP_SUB_ARTICLE_TYPE_SOC_SHARING_ENABLED.getNodePath());
		
		return subArticleDataNodes;
	}
	
	public static List<String> getMobileStyleResponseProductDescriptorDataNodes() 
	{
		List<String> productDescriptorsDataNodes = new ArrayList<String>();
		productDescriptorsDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		productDescriptorsDataNodes.add(MOBILE_STYLE_RESP_PRODUCT_DESCRIPTORS.getNodePath());
		productDescriptorsDataNodes.add(MOBILE_STYLE_RESP_PRODUCT_DESCRIPTORS_MATERIALS_CARE_DESC.getNodePath());
		
		return productDescriptorsDataNodes;
	}
	
	public static List<String> getMobileStyleResponseStyleOptionsDataNodes() 
	{
		List<String> styleOptionsDataNodes = new ArrayList<String>();
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_ID.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_NAME.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_VALUE.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_ID.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_INVENTORY_COUNT.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_WARE_HOUSE_ID_TO_ITEM_COUNT_MAP.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_AVAIL_DETAIL_MAP.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_ACTIVE.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_AVAILABLE.getNodePath());
																
		return styleOptionsDataNodes;
	}
	
	public static List<String> getMobileStyleResponseDiscountDataNodes() 
	{
		List<String> styleOptionsDataNodes = new ArrayList<String>();
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_DATA.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_ID.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_NAME.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_VALUE.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_ID.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_INVENTORY_COUNT.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_WARE_HOUSE_ID_TO_ITEM_COUNT_MAP.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_SKU_AVAIL_DETAIL_MAP.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_ACTIVE.getNodePath());
		styleOptionsDataNodes.add(MOBILE_STYLE_RESP_STYLE_OPTIONS_AVAILABLE.getNodePath());
																
		return styleOptionsDataNodes;
	}
	
}
