
package com.myntra.apiTests.inbound.helper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "filters", "coll_id", "sort_by", "source" })
public class TopSellerFilter {

	@JsonProperty("filters")
	private List<Filter> filters = new ArrayList<Filter>();
	@JsonProperty("coll_id")
	private String coll_id;
	@JsonProperty("sort_by")
	private String sort_by;
	@JsonProperty("source")
	private String source;

	@JsonProperty("filters")
	public List<Filter> getFilters() {
		return filters;
	}

	@JsonProperty("filters")
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	@JsonProperty("coll_id")
	public String getColl_id() {
		return coll_id;
	}

	@JsonProperty("coll_id")
	public void setColl_id(String coll_id) {
		this.coll_id = coll_id;
	}

	@JsonProperty("sort_by")
	public String getSort_by() {
		return sort_by;
	}

	@JsonProperty("sort_by")
	public void setSort_by(String sort_by) {
		this.sort_by = sort_by;
	}

	@JsonProperty("source")
	public String getSource() {
		return source;
	}

	@JsonProperty("source")
	public void setSource(String source) {
		this.source = source;
	}

}