
package com.myntra.apiTests.inbound.helper;


import javax.annotation.Generated;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;




@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "Neck or Collar", "asp", "p_td", "ros", "days_live" })

public class Filter {

	@JsonProperty("asp")
	private Asp asp;
	@JsonProperty("p_td")
	private PTd p_td;
	@JsonProperty("ros")
	private Ros ros;
	@JsonProperty("days_live")
	private DaysLive days_live;
	
	@JsonProperty(required=true,value="Neck or Collar")
	String Neck_or_Collar;

	

	@JsonValue
	public String toJson(String key, String value) {
		return key + ": " + value;
	}

	@JsonProperty("asp")
	public void setAsp(Asp asp) {
		this.asp = asp;
	}

	@JsonProperty("asp")
	public Asp getAsp() {
		return asp;
	}

	@JsonProperty("p_td")
	public void setP_td(PTd p_td) {
		this.p_td = p_td;
	}

	@JsonProperty("p_td")
	public PTd getP_td() {
		return p_td;
	}

	@JsonProperty("ros")
	public void setRos(Ros ros) {
		this.ros = ros;
	}

	@JsonProperty("ros")
	public Ros getRos() {
		return ros;
	}

	public DaysLive getDays_live() {
		return days_live;
	}

	public void setDays_live(DaysLive days_live) {
		this.days_live = days_live;
	}

	@JsonProperty(required=true,value="Neck or Collar")
	public String getNeck_or_Collar() {
		return Neck_or_Collar;
	}

	public void setNeck_or_Collar(String neck_or_Collar) {
		this.Neck_or_Collar = neck_or_Collar;
	}

	

}
