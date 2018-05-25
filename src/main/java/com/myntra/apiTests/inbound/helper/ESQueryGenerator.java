package com.myntra.apiTests.inbound.helper;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.CommonTermsQueryBuilder;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.HashMap;

public class ESQueryGenerator {
	BoolQueryBuilder boolQuery;
	TermQueryBuilder termQuery;
	MultiMatchQueryBuilder matchQuery;
	CommonTermsQueryBuilder commonQuery;
	FiltersAggregationBuilder aggregation;
	TermsBuilder termaggs;
	BoolQueryBuilder filterQuery;
	SearchRequestBuilder query;
	static Logger log = Logger.getLogger(ESQueryGenerator.class);

	public void createESQuery(Client client) {
		try {
			
			TermQueryBuilder termQuery = QueryBuilders.termQuery("source", "Amazon");
			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
					.should(QueryBuilders.termQuery("source", "Amazon"))
					.should(QueryBuilders.termQuery("source", "Jabong"))
					.should(QueryBuilders.termQuery("styleAttributes.style_status", "p"));

			FiltersAggregationBuilder aggregation = AggregationBuilders.filters("agg").filter("style_id",
					boolQuery);

			SearchRequestBuilder req = client.prepareSearch("amazon").setQuery(boolQuery).addAggregation(aggregation)
					.setSize(0);

			SearchResponse sr = req.execute().actionGet();

			Filters agg = sr.getAggregations().get("agg");
					
			
			for(Filters.Bucket entry:agg.getBuckets()){
				String key=entry.getKeyAsString();
				long docCount = entry.getDocCount();  
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public SearchRequestBuilder getDocsMissingStyleIdQuery(Client client, String searchIndex) {
		// BoolQueryBuilder query = QueryBuilders.boolQuery()
		// .must(QueryBuilders.termQuery("styleAttributes.style_status", "p"))
		// .mustNot(QueryBuilders.existsQuery("styleAttributes.style_id"));
		boolQuery = QueryBuilders.boolQuery().should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.AMAZON))
				.should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.JABONG))
				.should(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));

		if (searchIndex.equalsIgnoreCase(ESConstants.INDEX.CMS)) {
			filterQuery = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.STYLE_ID));
		} else {
			filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.EXTERNAL_STYLE_ID));
		}

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.STYLEATTRIBUTES.EXTERNAL_STYLE_ID, filterQuery);
		SearchRequestBuilder query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getdocsmissingStyleAttributes(Client client, String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		boolQuery = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.ATTRS));

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.STYLEATTRIBUTES.ATTRS, boolQuery);
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation).setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getdocsmissingVendorColorAttributeQuery(Client client, String searchIndex) {

		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		boolQuery = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.VENDOR_COLOUR));

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.STYLEATTRIBUTES.VENDOR_COLOUR, boolQuery);
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation).setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getdocsmissingStyleAttributeshavingaggMetrics(Client client,String searchIndex) {
		boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("styleAttributes.style_status", "p"))
				.must(QueryBuilders.existsQuery("aggMetrics"));
		
		filterQuery = QueryBuilders.boolQuery()
				.mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.STYLEATTRIBUTES));
		
		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.STYLEATTRIBUTES.STYLEATTRIBUTES,filterQuery);
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
					.setSize(0);

		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
					+ query + "\n");
		
		return query;

	}

	public SearchRequestBuilder getdocshavingROSlessthanzeroQuery(Client client, String searchIndex, int totalOrders,
			int daysLive) {
		boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL))
				.must(QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.TOTALORDERS).gt(totalOrders))
				.must(QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.DAYSLIVE).gt(daysLive));

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.AGGMETRICS.ROS,
				QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.ROS).lte(0));
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation).setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}

	public SearchRequestBuilder getdocscounthavingRDFForecastsQuery(Client client, String searchIndex) {
		
		ExistsQueryBuilder query1	=QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.RDF);
		
		query = client.prepareSearch(searchIndex).setQuery(query1).setSize(0);
		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getdocshavingSkuCountgreaterthan50Query(Client client, String searchIndex,
			int sku_count) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.AGGMETRICS.SKU_COUNT,
				QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.SKU_COUNT).gt(sku_count));

		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation).setSize(0);
		
		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getdocsmissingSkusQuery(Client client, String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		boolQuery = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.SKU.SKUS));

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.SKU.SKUS, boolQuery);
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation).setSize(0);
		
		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getactiveproductsquery(Client client, String searchIndex) {
		QueryStringQueryBuilder queryString = QueryBuilders.queryStringQuery("*").analyzeWildcard(true);

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.SKU.SKUS,
				QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));

		query = client.prepareSearch(searchIndex).setQuery(queryString).addAggregation(aggregation).setSize(0);
		
		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");

		return query;

	}

	public SearchRequestBuilder getproductsmissingminattributesquery(Client client, String searchIndex) {
		// termQuery = QueryBuilders.termQuery(STYLEATTRIBUTES.STYLE_STATUS,
		// ATTR_VALUES.STYLE_STATUS_VAL);

		boolQuery = QueryBuilders.boolQuery().should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.AMAZON))
				.should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.JABONG))
				.should(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));

		aggregation = AggregationBuilders.filters("agg")
				.filter(ESConstants.STYLEATTRIBUTES.ARTICLE_TYPE,
						QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.ARTICLE_TYPE)))
				.filter(ESConstants.STYLEATTRIBUTES.BRAND,
						QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.BRAND)))
				.filter(ESConstants.STYLEATTRIBUTES.GENDER,
						QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.GENDER)))
				.filter(ESConstants.STYLEATTRIBUTES.MRP,
						QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.MRP)));

		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation).setSize(0);
		
		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}

	public SearchRequestBuilder getcountofNonWellFormedDocsQuery(Client client,String searchIndex) {
		 boolQuery = QueryBuilders.boolQuery()
					.should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.AMAZON ))
					.should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.JABONG))
					.should(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));
		
		 filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.STYLEATTRIBUTES ))
					.mustNot(QueryBuilders.existsQuery(ESConstants.STYLEATTRIBUTES.SOURCE))
					.mustNot(QueryBuilders.existsQuery(ESConstants.SKU.SKUS));
	 
		 aggregation = AggregationBuilders.filters("agg")
					.filter(ESConstants.STYLEATTRIBUTES.STYLEATTRIBUTES, filterQuery);

		
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		return query;
		
	}

	public SearchRequestBuilder getCountOfDocsHavingRDFAndROSForecastsQuery(Client client,String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);
		aggregation = AggregationBuilders.filters("agg")
				.filter(ESConstants.DEMANDFORECAST.RDF, QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.RDF))
				.filter(ESConstants.DEMANDFORECAST.ROS, QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.ROS));
		
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation)
				.setSize(0);
	
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		return query;
	}

	public SearchRequestBuilder getCountOfDocsHavingRDFAndMissingROSForecastsQuery(Client client,String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);
		aggregation = AggregationBuilders.filters("agg")
				.filter(ESConstants.DEMANDFORECAST.RDF, QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.RDF))
				.filter(ESConstants.DEMANDFORECAST.ROS,  filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.ROS)));
			
		
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);
		
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
		
		
	}

	public SearchRequestBuilder getCountOfSOR_OR_DocsMissingVendorInfo(Client client,String searchIndex) {
		boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery("styleAttributes.style_status", "p"))
				.must(QueryBuilders.termQuery("styleAttributes.supply_type", "ON_HAND"))
				.must(QueryBuilders.termsQuery("styleAttributes.commercial_type", new String[] { "outright", "sor" }));

		aggregation = AggregationBuilders.filters("agg")
				.filter(ESConstants.VENDOR_INFO.VENDOR_NAME,filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.VENDOR_INFO.VENDOR_NAME)))
				.filter(ESConstants.VENDOR_INFO.BASIS_OF_MARGIN,filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.VENDOR_INFO.BASIS_OF_MARGIN)))
				.filter(ESConstants.VENDOR_INFO.MARGIN_TYPE,filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.VENDOR_INFO.MARGIN_TYPE)))
				.filter(ESConstants.VENDOR_INFO.PO_TYPE,filterQuery = QueryBuilders.boolQuery()
					.mustNot(QueryBuilders.existsQuery(ESConstants.VENDOR_INFO.PO_TYPE)));


		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);
	
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
		

		
	}

	public SearchRequestBuilder getCountOfDocsHavingVendorInfoMarginGreaterThan100Query(Client client,String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);
		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.VENDOR_INFO.MARGIN,
				QueryBuilders.rangeQuery(ESConstants.VENDOR_INFO.MARGIN).gte(100));
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);
	
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}

	public SearchRequestBuilder getCountOfDocsMissingProductsStreamAttributesQuery(Client client,String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		 boolQuery = QueryBuilders.boolQuery()
				.mustNot(QueryBuilders.existsQuery("productStreamTimeseries"))
				.must(QueryBuilders.existsQuery("productStreamMetrics"));

		aggregation = AggregationBuilders.filters("agg").filter("productStreamMetrics", boolQuery);
		
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation)
				.setSize(0);
	
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}

	public SearchRequestBuilder getCountOfDocsHavingClusterInfoQuery(Client client,String searchIndex) {
		termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);

		boolQuery = QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(ESConstants.CLUSTER_INFO.CLUSTER_INFO));
		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.CLUSTER_INFO.CLUSTER_INFO, boolQuery);
		query = client.prepareSearch(searchIndex).setQuery(termQuery).addAggregation(aggregation)
				.setSize(0);
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}

	public SearchRequestBuilder getCountOfDocsMissingTimeSeriesQuery(Client client,String searchIndex) {
		boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL))
				.must(QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.TOTALORDERS).gt(0))
				.must(QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.TOTQTY).gt(0));

		BoolQueryBuilder missingTerm = QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(ESConstants.TIMESERIES.TIMESERIES));

		aggregation = AggregationBuilders.filters("agg").filter(ESConstants.TIMESERIES.TIMESERIES, missingTerm);
		
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);
	
		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}
	
	public SearchRequestBuilder getCountOfDocsHavingAllMandatoryAttributesQuery(Client client,String searchIndex) {
		//termQuery = QueryBuilders.termQuery(STYLEATTRIBUTES.STYLE_STATUS, ATTR_VALUES.STYLE_STATUS_VAL);

		 boolQuery = QueryBuilders.boolQuery()
					.must(QueryBuilders.existsQuery(ESConstants.SKU.SKU_ID))
						.must(QueryBuilders.existsQuery(ESConstants.VENDOR_INFO.ITEM_PURCHASE_PRICE))
								.must(QueryBuilders.existsQuery(ESConstants.FDB.ID))
										.must(QueryBuilders.existsQuery(ESConstants.DEMANDFORECAST.DEMANDFORECAST))
												.must(QueryBuilders.existsQuery(ESConstants.AGGMETRICS.AGGMETRICS))
																		.must(QueryBuilders.existsQuery(ESConstants.TIMESERIES.TIMESERIES))
																				.must(QueryBuilders.existsQuery(ESConstants.PRODUCTS.PRODUCTSTREAMMETRICS))
																								.must(QueryBuilders.existsQuery(ESConstants.AGGMETRICS.TSR))
																										.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL))
																												.must(QueryBuilders.termsQuery(ESConstants.STYLEATTRIBUTES.COMMERCIAL_TYPE, ESConstants.ATTR_VALUES.COMMERCIAL_TYPE_VAL));
																														

		aggregation = AggregationBuilders.filters("agg").filter("productStreamMetrics", boolQuery);
		
		query = client.prepareSearch(searchIndex).addAggregation(aggregation)
				.setSize(0);

		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}
	
	public SearchRequestBuilder getCountOfProductsHavingSameMRPAndItemPurchasePriceQuery(Client client,String searchIndex,int inventory,int item_purchase_price) {
		Script sc=new Script("doc[\"vendor_info.item_purchase_price\"].value == doc[\"vendor_info.last_mrp\"].value");
		boolQuery = QueryBuilders.boolQuery()
				.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL))
				.must(QueryBuilders.scriptQuery(sc));

		 filterQuery = QueryBuilders.boolQuery()
					.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.SUPPLY_TYPE, ESConstants.ATTR_VALUES.SUPPLY_TYPE))
					.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.COMMERCIAL_TYPE, ESConstants.ATTR_VALUES.COMMERCIAL_TYPE_VAL[1]))
					.must(QueryBuilders.rangeQuery(ESConstants.AGGMETRICS.INVENTORY).gt(inventory))
					.must(QueryBuilders.rangeQuery(ESConstants.VENDOR_INFO.ITEM_PURCHASE_PRICE).gt(item_purchase_price));
		

		aggregation = AggregationBuilders.filters("agg").filter("", filterQuery);
		
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);

		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}
	
	public SearchRequestBuilder getCountOfProductsHavingSameMRPAndASPQuery(Client client,String searchIndex) {
		 boolQuery = QueryBuilders.boolQuery()
					.must(QueryBuilders.matchQuery(ESConstants.STYLEATTRIBUTES.BRAND, ESConstants.ATTR_VALUES.BRAND))
					.must(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));
					
		Script sc=new Script("doc[\"styleAttributes.asp\"].value == doc[\"styleAttributes.MRP\"].value");
		filterQuery = QueryBuilders.boolQuery()
					.must(QueryBuilders.scriptQuery(sc));

		aggregation = AggregationBuilders.filters("agg").filter("ASP_MRP_SAME", filterQuery);
		
		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation)
				.setSize(0);

		log.debug("\nPrinting the Elastic Search Request for "+searchIndex+"Index :\n"
				+ query + "\n");
		
		return query;
	}
	

	public SearchRequestBuilder getproductwithStyleAttrbutmissingStyleAttrSource(Client client, String searchIndex) {

		boolQuery = QueryBuilders.boolQuery().should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.AMAZON))
				.should(QueryBuilders.termQuery(ESConstants.INDEX.SOURCE, ESConstants.STYLEATTRIBUTES.JABONG))
				.should(QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS, ESConstants.ATTR_VALUES.STYLE_STATUS_VAL));

		aggregation = AggregationBuilders.filters("agg")
				.filter("docs", QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(ESConstants.SOURCE.STYLE_ATTRIBUTES_SOURCE)).
						mustNot(QueryBuilders.existsQuery(ESConstants.SOURCE.STYLE_ATTRIBUTES)));

		query = client.prepareSearch(searchIndex).setQuery(boolQuery).addAggregation(aggregation).setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}
	
	public SearchRequestBuilder getproductshavingDaysLiveAttribute(Client client, String searchIndex, String styleID, String sourceattr) {

		matchQuery = QueryBuilders.multiMatchQuery(styleID, "query", ESConstants.STYLEATTRIBUTES.STYLE_ID);
		HashMap<String, String> map = new HashMap<String,String>();
		map.put(ESConstants.SOURCE._SOURCE, sourceattr);
		query = client.prepareSearch(searchIndex).setQuery(matchQuery).setExtraSource(map);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}
	
	public SearchRequestBuilder getproductwithduplicatestyleids(Client client, String searchIndex) {
		 termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS,
		 ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);
		                
		 termaggs = AggregationBuilders.terms("duplicateCount")
		        .field("styleAttributes.style_id").minDocCount(2);
		
		query = client.prepareSearch(searchIndex).addAggregation(termaggs).setSize(0);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}
	
	public SearchRequestBuilder gettotalProductCount(Client client, String searchIndex) {
		 termQuery = QueryBuilders.termQuery(ESConstants.STYLEATTRIBUTES.STYLE_STATUS,
		 ESConstants.ATTR_VALUES.STYLE_STATUS_VAL);
		query = client.prepareSearch(searchIndex).setQuery(termQuery);

		log.debug("\nPrinting the Elastic Search Request for " + searchIndex + "Index :\n" + query + "\n");
		return query;

	}	

}