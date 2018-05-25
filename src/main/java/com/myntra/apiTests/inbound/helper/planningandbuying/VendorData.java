package com.myntra.apiTests.inbound.helper.planningandbuying;

/**
 * Created by 300000929 on 05/01/17.
 */
public class VendorData {


    //public Integer LP_VENDOR_ID = 167;
    public Integer VENDOR_ID = 8;
    //public String LP_VENDOR_NAME = "Aditya Birla Nuvo Ltd";
    public String VENDOR_NAME = "Benetton India Pvt. Ltd";
    public String VENDOR_CONTACT_PERSON = "Priya";
    public String VENDOR_WAREHOUSE_LOCATION = "KAR";
    public String VENDOR_ADDRESS = "Mysore";
    public String VENDOR_CONTACT_NUMBERS = "9999999999";
    public String VENDOR_EMAIL = "astha.sahay@madura.adityabirla.com,aditya.birla@myntra.com";
    public String SEASON_YEAR = "2017";
    public String SEASON = "18";
    public String ORDER_INDENT_SOURCE = "ISB";
    public String PAYMENT = "BANK_TRANSFER";
    public String COMMERCIAL_TYPE = "OUTRIGHT";
    public String STOCK_ORIGIN = "DOMESTIC";
    public String ORDER_INDENT_ORDER_TYPE = "IN_SEASON";
    public String CREDIT_BASIS_AS_ON = "RECEIVED_DATE";
    public String PRIORITIZATION = "NORMAL";
    public String LETTER_HEADING = "MYNTRA DESIGNS PVT LTD";
    public String BRAND_TYPE = "EXTERNAL";
    public String MAIL_TEXT = "INVALID";
    public String COMMENTS = "INVALID";
    public String BUYER_ID = "2709";
    public String CM_EMAIL_ID = "shanmukappa.nayak@myntra.com";
    public String CM_EMAIL_ID2 = "sachin.khurana@myntra.com";
    public Long TERMS_ADDITIONAL_CLASSIFICATION= 97l;

    public Long getTERMS_ADDITIONAL_CLASSIFICATION() {
        return TERMS_ADDITIONAL_CLASSIFICATION;
    }

    public Integer getVENDOR_ID() {
        return this.VENDOR_ID;
    }

    public String getCOMMENTS() {
        return this.COMMENTS;
    }

    public String getCOMMERCIAL_TYPE() {
        return this.COMMERCIAL_TYPE;
    }

    public String getCREDIT_BASIS_AS_ON() {
        return this.CREDIT_BASIS_AS_ON;
    }

    public String getLETTER_HEADING() {
        return this.LETTER_HEADING;
    }

    public String getMAIL_TEXT() {
        return this.MAIL_TEXT;
    }

    public String getORDER_INDENT_ORDER_TYPE() {
        return this.ORDER_INDENT_ORDER_TYPE;
    }

    public String getORDER_INDENT_SOURCE() {return this.ORDER_INDENT_SOURCE; }

    public String getPAYMENT() {
        return this.PAYMENT;
    }

    public String getPRIORITIZATION() {
        return this.PRIORITIZATION;
    }

    public String getSEASON() {
        return this.SEASON;
    }

    public String getSEASON_YEAR() {
        return this.SEASON_YEAR;
    }

    public String getSTOCK_ORIGIN() {
        return this.STOCK_ORIGIN;
    }

    public String getVENDOR_ADDRESS() {
        return this.VENDOR_ADDRESS;
    }

    public String getVENDOR_CONTACT_NUMBERS() {
        return this.VENDOR_CONTACT_NUMBERS;
    }

    public String getVENDOR_CONTACT_PERSON() {
        return this.VENDOR_CONTACT_PERSON;
    }

    public String getVENDOR_EMAIL() {
        return this.VENDOR_EMAIL;
    }

    public String getVENDOR_WAREHOUSE_LOCATION() {
        return this.VENDOR_WAREHOUSE_LOCATION;
    }

    public String getBRAND_TYPE() {
        return this.BRAND_TYPE;
    }

    public String getVENDOR_NAME()
    {
        return this.VENDOR_NAME;
    }

    public String getBUYER_ID()
    {
        return this.BUYER_ID;
    }

    public String getCM_EMAIL_ID()
    {
        return this.CM_EMAIL_ID;
    }

    public String getCM_EMAIL_ID2()
    {
        return this.CM_EMAIL_ID2;
    }

}
