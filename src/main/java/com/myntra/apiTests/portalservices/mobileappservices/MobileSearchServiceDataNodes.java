package com.myntra.apiTests.portalservices.mobileappservices;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum MobileSearchServiceDataNodes 
{
	MOBILE_SEARCH_RESP_META("meta"),
	MOBILE_SEARCH_RESP_META_CODE("meta.code"),
	MOBILE_SEARCH_RESP_META_ERROR_DETAIL("meta.errorDetail"),
	
	MOBILE_SEARCH_RESP_NOTIFICATION("notification"),
	
	// mobileSearchDataNodes data requestObj nodes
	MOBILE_SEARCH_RESP_DATA("data"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT("data.requestObj"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_QUERY("data.requestObj.query"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_START("data.requestObj.start"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS("data.requestObj.rows"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_FIELD("data.requestObj.facetField"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_FQ("data.requestObj.fq"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_SORT("data.requestObj.sort"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS("data.requestObj.return_docs"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_COLOUR_GROUPING("data.requestObj.colour_grouping"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_USE_CACHE("data.requestObj.useCache"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET("data.requestObj.facet"),
	
	// requestObj curated_query
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY("data.requestObj.curated_query"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_STYLE("data.requestObj.curated_query.style"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_SORT("data.requestObj.curated_query.sort"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ENABLED("data.requestObj.curated_query.enable"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_START("data.requestObj.curated_query.start"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS("data.requestObj.curated_query.rows"),
	MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_NO_SLOTS("data.requestObj.curated_query.no_slots"),
	
	// mobileSearchDataNodes data body nodes
	MOBIEL_SEARCH_RESP_DATA_BODY("data.body"),
	MOBIEL_SEARCH_RESP_DATA_BODY_TOT_PROD_COUNT("data.body.totalProductsCount"),
	
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS("data.body.filters"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_BRAND("data.body.filters.Brand"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_DISC("data.body.filters.Discount"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_PRICE("data.body.filters.Price"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_DEPT("data.body.filters.Department"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_COLOUR("data.body.filters.Colour"),      
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_CATAGORIES("data.body.filters.Categories"),        
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_SUB_CATAGORIES("data.body.filters.Subcategories"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_SECTIONS("data.body.filters.Sections"),
	MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_PROMOTIONS("data.body.filters.Promotions"),
	
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS("data.body.products"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_ALL_SKU_FOR_SIZES("data.body.products.allSkuForSizes"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_BRANDS_FILTER_FACET("data.body.products.brands_filter_facet"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC("data.body.products.discount"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC_LABEL("data.body.products.discount_label"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC_PRICE("data.body.products.discounted_price"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DRE_LANDING_PAGE_URL("data.body.products.dre_landing_page_url"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR("data.body.products.global_attr_base_colour"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR1("data.body.products.global_attr_colour1"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_PRICE("data.body.products.price"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_PRODUCT("data.body.products.product"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_SEARCH_IMAGE("data.body.products.search_image"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_SIZES("data.body.products.sizes"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_GROUP("data.body.products.style_group"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_ID("data.body.products.styleid"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_NAME("data.body.products.stylename"),
	MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_VISUAL_TAG("data.body.products.visual_tag"),
	
	// PARAMETERIZED SEARCH RESPONSE
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA("data"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH("data.search"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_QUERY("data.search.query"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_START("data.search.start"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_ROWS("data.search.rows"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FACET_FIELD("data.search.facetField"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FQ("data.search.fq"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_SORT("data.search.sort"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_RETURN_DOCS("data.search.return_docs"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_COLOUR_GROUPING("data.search.colour_grouping"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_USE_CACHE("data.search.useCache"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FACET("data.search.facet"),
	
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SYNONYM_MAP("data.synonymMap"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_QUERY_TYPE("data.queryType"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_TOTAL_PRODUCT_COUNT("data.totalProductsCount"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_LIST_GROUP("data.listGroup"),
	
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO("data.seo"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA("data.seo.metaData"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE("data.seo.metaData.page_title"),
	
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS("data.results"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCT_COUNT("data.results.totalProductsCount"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTER_QUERY_PARAM("data.results.filterQueryParam"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS("data.results.filters"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_TITLE("data.results.filters.title"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_KEY("data.results.filters.key"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES("data.results.filters.values"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_NUM("data.results.filters.values.num"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_OPTION("data.results.filters.values.option"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_DISABLED("data.results.filters.values.disabled"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_CHECKED("data.results.filters.values.checked"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_COMMON("data.results.filters.common"),
	
	
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS("data.results.products"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_SIZES("data.results.products.sizes"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_NAME("data.results.products.stylename"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_SEARCH_IMAGE("data.results.products.search_image"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNTED_PRICE("data.results.products.discounted_price"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNT_LABEL("data.results.products.discount_label"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DRE_LANDING_PAGE_URL("data.results.products.dre_landing_page_url"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_VISUAL_TAG("data.results.products.visual_tag"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR("data.results.products.global_attr_base_colour"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNT("data.results.products.discount"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_ID("data.results.products.id"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_PRODUCT("data.results.products.product"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_IMAGE_ENTRY_DEFAULT("data.results.products.imageEntry_default"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_PRICE("data.results.products.price"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_BRANDS_FILTER_FACET("data.results.products.brands_filter_facet"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_ID("data.results.products.styleid"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_GLOBAL_ATTR_COLOUR1("data.results.products.global_attr_colour1"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_GROUP("data.results.products.style_group"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_ALL_SKU_FOR_SIZES("data.results.products.allSkuForSizes"),
	
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS("data.results.curated_products"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_SIZES("data.results.curated_products.sizes"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_NAME("data.results.curated_products.stylename"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_SEARCH_IMAGE("data.results.curated_products.search_image"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNTED_PRICE("data.results.curated_products.discounted_price"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNT_LABEL("data.results.curated_products.discount_label"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DRE_LANDING_PAGE_URL("data.results.curated_products.dre_landing_page_url"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_VISUAL_TAG("data.results.curated_products.visual_tag"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR("data.results.curated_products.global_attr_base_colour"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNT("data.results.curated_products.discount"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_ID("data.results.curated_products.id"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_PRODUCT("data.results.curated_products.product"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_IMAGE_ENTRY_DEFAULT("data.results.curated_products.imageEntry_default"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_PRICE("data.results.curated_products.price"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_BRANDS_FILTER_FACET("data.results.curated_products.brands_filter_facet"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_ID("data.results.curated_products.styleid"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_GLOBAL_ATTR_COLOUR1("data.results.curated_products.global_attr_colour1"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_GROUP("data.results.curated_products.style_group"),
	MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_ALL_SKU_FOR_SIZES("data.results.curated_products.allSkuForSizes");
	 
	private String nodePath;
	
	private MobileSearchServiceDataNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	public static List<String> getMobileSearchServiceResponseMetaTagNodes()
	{
		List<String> mobileSearchMetaTagNodes = new ArrayList<String>();
		mobileSearchMetaTagNodes.add(MOBILE_SEARCH_RESP_META.getNodePath());
		mobileSearchMetaTagNodes.add(MOBILE_SEARCH_RESP_META_CODE.getNodePath());
		mobileSearchMetaTagNodes.add(MOBILE_SEARCH_RESP_META_ERROR_DETAIL.getNodePath());
		
		return mobileSearchMetaTagNodes;
	}
	
	public static List<String> getMobileSearchServiceResponseNotificationTagNodes()
	{
		List<String> mobileSearchNotificationNodes = new ArrayList<String>();
		mobileSearchNotificationNodes.add(MOBILE_SEARCH_RESP_NOTIFICATION.getNodePath());
		
		return mobileSearchNotificationNodes;
	}
	
	public static List<String> getRequestObjectDataTagNodes()
	{
		List<String> requestObjectDataTagNodes = new ArrayList<String>();
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_QUERY.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_START.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_ROWS.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_FIELD.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_FQ.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET_SORT.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_RETURN_DOCS.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_COLOUR_GROUPING.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_USE_CACHE.getNodePath());
		requestObjectDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_FACET.getNodePath());
		
		return requestObjectDataTagNodes;
	}
	
	public static List<String> getRequestObjectWithCuratedQueryDataTagNodes()
	{
		List<String> requestObjectWithCuratedQueryDataTagNodes = new ArrayList<String>();
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_STYLE.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_SORT.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ENABLED.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_START.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_ROWS.getNodePath());
		requestObjectWithCuratedQueryDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_REQ_OBJECT_CURATED_QUERY_NO_SLOTS.getNodePath());
		
		return requestObjectWithCuratedQueryDataTagNodes;
	}
	
	public static List<String> getBodyWithFiltersDataTagNodes()
	{
		List<String> bodyWithFiltersDataTagNodes = new ArrayList<String>();
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_BRAND.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_DISC.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_PRICE.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_DEPT.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_COLOUR.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_CATAGORIES.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_SUB_CATAGORIES.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_SECTIONS.getNodePath());
		bodyWithFiltersDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_FILTERS_PROMOTIONS.getNodePath());
		
		return bodyWithFiltersDataTagNodes;
	}
	
	public static List<String> getBodyWithProductsDataTagNodes()
	{
		List<String> bodyWithProductsDataTagNodes = new ArrayList<String>();
		bodyWithProductsDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBIEL_SEARCH_RESP_DATA_BODY_TOT_PROD_COUNT.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_ALL_SKU_FOR_SIZES.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_BRANDS_FILTER_FACET.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC_LABEL.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DISC_PRICE.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_DRE_LANDING_PAGE_URL.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR1.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_PRICE.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_PRODUCT.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_SEARCH_IMAGE.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_SIZES.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_GROUP.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_ID.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_STYLE_NAME.getNodePath());
		bodyWithProductsDataTagNodes.add(MOBILE_SEARCH_RESP_DATA_BODY_PRODUCTS_VISUAL_TAG.getNodePath());
		
		return bodyWithProductsDataTagNodes;
	}
	
	public static List<String> getParameterizedSearchDataNodes()
	{
		List<String> parameterizedSearchDataNodes = new ArrayList<String>();
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SYNONYM_MAP.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_QUERY_TYPE.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_TOTAL_PRODUCT_COUNT.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_LIST_GROUP.getNodePath());
		
		return parameterizedSearchDataNodes;
	}
	
	public static List<String> getParameterizedSearchSeoDataNodes()
	{
		List<String> parameterizedSearchDataNodes = new ArrayList<String>();
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEO_META_DATA_PAGE_TITLE.getNodePath());
		
		return parameterizedSearchDataNodes;
	}
	
	public static List<String> getParameterizedSearchDataSearchNodes()
	{
		List<String> parameterizedSearchDataNodes = new ArrayList<String>();
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_QUERY.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_START.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_ROWS.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FACET_FIELD.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FQ.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_SORT.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_RETURN_DOCS.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_COLOUR_GROUPING.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_USE_CACHE.getNodePath());
		parameterizedSearchDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_SEARCH_FACET.getNodePath());
		
		return parameterizedSearchDataNodes;
	}
	
	public static List<String> getParameterizedSearchResultsFiltersDataNodes()
	{
		List<String> parameterizedSearchResultsFiltersDataNodes = new ArrayList<String>();
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_TITLE.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_KEY.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_NUM.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_OPTION.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_DISABLED.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_VALUES_CHECKED.getNodePath());
		parameterizedSearchResultsFiltersDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_FILTERS_COMMON.getNodePath());
		
		return parameterizedSearchResultsFiltersDataNodes;
	}
	
	public static List<String> getParameterizedSearchResultsProductsDataNodes()
	{
		List<String> parameterizedSearchResultsProductsDataNodes = new ArrayList<String>();
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCT_COUNT.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_SIZES.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_NAME.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_SEARCH_IMAGE.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNTED_PRICE.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNT_LABEL.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DRE_LANDING_PAGE_URL.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_VISUAL_TAG.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_DISCOUNT.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_ID.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_PRODUCT.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_IMAGE_ENTRY_DEFAULT.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_PRICE.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_BRANDS_FILTER_FACET.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_ID.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_GLOBAL_ATTR_COLOUR1.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_STYLE_GROUP.getNodePath());
		parameterizedSearchResultsProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_PRODUCTS_ALL_SKU_FOR_SIZES.getNodePath());
		
		return parameterizedSearchResultsProductsDataNodes;
	}
	
	public static List<String> getParameterizedSearchResultsCuratedProductsDataNodes()
	{
		List<String> parameterizedSearchResultsCuratedProductsDataNodes = new ArrayList<String>();
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_TOTAL_PRODUCT_COUNT.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_SIZES.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_NAME.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_SEARCH_IMAGE.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNTED_PRICE.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNT_LABEL.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DRE_LANDING_PAGE_URL.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_VISUAL_TAG.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_GLOBAL_ATTR_BASE_COLOUR.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_DISCOUNT.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_ID.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_PRODUCT.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_IMAGE_ENTRY_DEFAULT.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_PRICE.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_BRANDS_FILTER_FACET.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_ID.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_GLOBAL_ATTR_COLOUR1.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_STYLE_GROUP.getNodePath());
		parameterizedSearchResultsCuratedProductsDataNodes.add(MOBILE_PARAMETERIZED_SEARCH_RESP_DATA_RESULTS_CURATED_PRODUCTS_ALL_SKU_FOR_SIZES.getNodePath());
		
		return parameterizedSearchResultsCuratedProductsDataNodes;
	}
	
	
}
