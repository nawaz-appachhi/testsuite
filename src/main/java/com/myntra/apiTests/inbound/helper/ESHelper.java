package com.myntra.apiTests.inbound.helper;

import java.util.Collection;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

public class ESHelper {
	
	public long getTotalHits(SearchResponse response){
		return response.getHits().getTotalHits();	
		
	}
	
	public long getAggregationDocCount(SearchResponse response,int bucketId){
		Filters agg = response.getAggregations().get("agg");
		return agg.getBuckets().get(bucketId).getDocCount();
	}

	public long getAggregationBucketSize(SearchResponse response, String key){
		Terms  terms = response.getAggregations().get(key);
		Collection<Terms.Bucket> buckets = terms.getBuckets();
		return buckets.size();
	}
}
