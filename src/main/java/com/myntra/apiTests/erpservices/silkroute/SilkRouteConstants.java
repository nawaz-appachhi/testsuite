package com.myntra.apiTests.erpservices.silkroute;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Created by 8403 on 30/03/17.
 */
public interface SilkRouteConstants {

    public static final String FK3 = "flipkart3";
    public static final String FK4 = "flipkart4";
    public static final String FK5 = "flipkart5";
    public static final String JB = "JB";
    public static final String  SKU_CODE_NIKE =  "NIKEBAPK99760";

    // public static final String  SKU_CODE_NIKE_CREATEORDER =  "NIKEBAPK99761";
    public static final int  SKU_ID_NIKE_CREATEORDER =  1183986;

    public static final String  SKU_CODE_NIKE_ORDER_EVENT_HOLD =  "NIKEBAPK99762";
    public static final int  SKU_ID_NIKE_ORDER_EVENT_HOLD =  1183987;

    public static final String  SKU_CODE_NIKE_PARTIAL_CANCEL =  "NIKEBAPK99763";
    public static final int  SKU_ID_NIKE_PARTIAL_CANCEL =  1183988;

    public static final String  SKU_CODE_NIKE_PARTIAL_CANCEL1 =  "NIKEBAPK99764";
    public static final int  SKU_ID_NIKE_PARTIAL_CANCEL1 =  1183989;

    public static final String  SKU_CODE_NIKE_CANCEL_ORDER =  "NIKEBAPK99765";
    public static final int  SKU_ID_NIKE_CANCEL_ORDER =  1183990;

    public static final String  SKU_CODE_OOS_ONHOLD =  "GGLMFlats00548";
    public static final int  SKU_ID_OOS_ONHOLD =  727598;


    public static final String  SKU_CODE_HOLD_OOS =  "NIKEBAPK99766";
    public static final int  SKU_ID_HOLD_OOS =  1183991;

    public static final String  SKU_CODE_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU =  "NIKEBAPK99767";
    public static final int  SKU_ID_UPDATE_ORDER_TO_HOLD_FOR_OOSSKU =  1183992;

    public static final String  SKU_CODE_MOJO_INITIATED_CANCELATION =  "NIKEBAPK99768";
    public static final int  SKU_ID_MOJO_INITIATED_CANCELATION =  1183993;

    public static final String  SKU_CODE_CREATE_ORDER_OOS =  "NIKEBAPK99769";
    public static final int  SKU_ID_CREATE_ORDER_OOS =  1183994;


    public static final String  SKU_CODE_7709 =  "MNTRTSRM05771";
    public static final String  SKU_CODE_66373 =  "RDTLTRSR00013";
    public static final String  SKU_ID_UpdateOrderToHoldForOOSSku = "66373";
    // public static final String  FK_LISTING_ID = "LSTMOBDACGHGSMVG9VSIQLWV5";
    public static final String  DISPATCH_DATE = new Date().toInstant().plus(2, ChronoUnit.DAYS).toString();
    public static final String  DISPATCH_DATE_SLA = new Date().toInstant().plus(6, ChronoUnit.DAYS).toString();
    public static final String  DISPATCH_DATE_DB = "Tue Jul 18 10:00:00 IST 2017";
    public static final String  SKU_ID_JABONG_CREATEORDER = "12265439";
    String SKU_ID_JABONG_CREATEORDER_ONHAND = "102572";

    public static final int  SELLER_ID_JABONG = 21;

    public static final String  FK_LISTING_ID = "LSTBDDEXR7T4HDZGRF5AMLWTX";
    public static final String  SKU_CODE_NIKE_CREATEORDER =  "NIKEBAPK99761";
    public static final String  SKU_CODE_NIKE_CREATEORDER1 =  "GRSNTSHT00022";

}
