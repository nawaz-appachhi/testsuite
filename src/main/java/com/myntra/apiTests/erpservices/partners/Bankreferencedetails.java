package com.myntra.apiTests.erpservices.partners;

//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"payment_ref_no",
//"payment_ref_date",
//"amount"
//})
public class Bankreferencedetails {
	private String amount;

    private String payment_ref_no;

    private String payment_ref_date;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getPayment_ref_no ()
    {
        return payment_ref_no;
    }

    public void setPayment_ref_no (String payment_ref_no)
    {
        this.payment_ref_no = payment_ref_no;
    }

    public String getPayment_ref_date ()
    {
        return payment_ref_date;
    }

    public void setPayment_ref_date (String payment_ref_date)
    {
        this.payment_ref_date = payment_ref_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", payment_ref_no = "+payment_ref_no+", payment_ref_date = "+payment_ref_date+"]";
    }
}