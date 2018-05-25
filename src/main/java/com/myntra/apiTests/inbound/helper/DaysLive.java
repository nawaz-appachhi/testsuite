package com.myntra.apiTests.inbound.helper;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "greater_than_equal_to"
})
public class DaysLive {

    @JsonProperty("greater_than_equal_to")
    private long greater_than_equal_to;

	public long getGreater_than_equal_to() {
		return greater_than_equal_to;
	}

	public void setGreater_than_equal_to(long daysLive) {
		this.greater_than_equal_to = daysLive;
	}

    
}
