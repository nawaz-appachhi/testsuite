
package com.myntra.apiTests.inbound.helper;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "greater_than_equal_to,less_than_equal_to"
})
public class Ros {

    @JsonProperty("greater_than_equal_to")
    private String greater_than_equal_to;
    @JsonProperty("less_than_equal_to")
    private String less_than_equal_to;

	public String getLess_than_equal_to() {
		return less_than_equal_to;
	}

	public void setLess_than_equal_to(String less_than_equal_to) {
		this.less_than_equal_to = less_than_equal_to;
	}

	public String getGreater_than_equal_to() {
		return greater_than_equal_to;
	}

	public void setGreater_than_equal_to(String greater_than_equal_to) {
		this.greater_than_equal_to = greater_than_equal_to;
	}

    
}