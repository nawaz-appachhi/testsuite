package com.myntra.apiTests.erpservices.partners;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"SellerDetails",
//"total_pages",
//"current_page"
//})
@JsonIgnoreProperties(ignoreUnknown=true)
public class CitrusPayoutResponse {
	private ArrayList<SellerDetails> sellerDetails;

    private String total_pages;

    private String current_page;

    @JsonProperty("SellerDetails")
    public ArrayList<SellerDetails> getSellerDetails ()
    {
        return sellerDetails;
    }
    @JsonProperty("SellerDetails")
    public void setSellerDetails (ArrayList<SellerDetails> sellerDetails)
    {
        this.sellerDetails = sellerDetails;
    }
    
    private String error_id;

    public String getError_id() {
		return error_id;
	}
	public void setError_id(String error_id) {
		this.error_id = error_id;
	}
	public String getTotal_pages ()
    {
        return total_pages;
    }

    public void setTotal_pages (String total_pages)
    {
        this.total_pages = total_pages;
    }

    public String getCurrent_page ()
    {
        return current_page;
    }

    public void setCurrent_page (String current_page)
    {
        this.current_page = current_page;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SellerDetails = "+sellerDetails+", total_pages = "+total_pages+", current_page = "+current_page+"]";
    }
}
