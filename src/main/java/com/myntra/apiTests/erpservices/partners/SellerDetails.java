package com.myntra.apiTests.erpservices.partners;

import java.util.ArrayList;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"seller_id",
//"bankreferencedetails"
//})
public class SellerDetails {
	
private ArrayList<Bankreferencedetails> bankreferencedetails;

private String seller_id;

public ArrayList<Bankreferencedetails> getBankreferencedetails ()
{
    return bankreferencedetails;
}

public void setBankreferencedetails (ArrayList<Bankreferencedetails> bankreferencedetails)
{
    this.bankreferencedetails = bankreferencedetails;
}

public String getSeller_id ()
{
    return seller_id;
}

public void setSeller_id (String seller_id)
{
    this.seller_id = seller_id;
}

@Override
public String toString()
{
    return "ClassPojo [bankreferencedetails = "+bankreferencedetails+", seller_id = "+seller_id+"]";
}}