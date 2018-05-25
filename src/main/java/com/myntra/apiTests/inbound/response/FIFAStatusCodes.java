package com.myntra.apiTests.inbound.response;

import com.myntra.commons.codes.StatusCodes;


public class FIFAStatusCodes extends StatusCodes {
    private static final String BUNDLE_NAME = "FIFAStatusCodes";

    public FIFAStatusCodes(int code, String message) {
        setAll(code, message, BUNDLE_NAME);
    }
    
    public static enum Type {

        ERROR, SUCCESS, WARNING,FAILURE,FALSE;
    };
    public static final StatusCodes DATA_FETCH_SUCCESS = new FIFAStatusCodes(10001, "Fetch data is successful");
    public static final StatusCodes DATA_FETCH_EXCEPTION = new FIFAStatusCodes(19003, "There was some exception in getting data");
    public static final StatusCodes SPOT_DATA_FETCH_SUCCESS = new FIFAStatusCodes(10009, "The call for getting the style data is successful");
    public static final StatusCodes SPOT_NODE_DATA_FETCH_SUCCESS = new FIFAStatusCodes(3, "The call for getting the style data is successful");
    public static final StatusCodes SPOT_DATA_FETCH = new FIFAStatusCodes(10008, "Fetch data is successful");
    
    /*public static final StatusCodes ORDER_DELETED = new OmsSuccessCodes(1002, "ORDER_DELETED");
    public static final StatusCodes ORDER_RETRIEVED = new OmsSuccessCodes(1003, "ORDER_RETRIEVED");
    public static final StatusCodes ORDER_UPDATED = new OmsSuccessCodes(1004, "ORDER_UPDATED");
    public static final StatusCodes ORDER_RELEASE_ADDED = new OmsSuccessCodes(1005, "ORDER_RELEASE_ADDED");
    public static final StatusCodes ORDER_RELEASE_DELETED = new OmsSuccessCodes(1006, "ORDER_RELEASE_DELETED");
    public static final StatusCodes ORDER_RELEASE_RETRIEVED = new OmsSuccessCodes(1007, "ORDER_RELEASE_RETRIEVED");
    public static final StatusCodes ORDER_RELEASE_UPDATED = new OmsSuccessCodes(1008, "ORDER_RELEASE_UPDATED");
    public static final StatusCodes ORDER_LINE_ADDED = new OmsSuccessCodes(1009, "ORDER_LINE_ADDED");
    public static final StatusCodes ORDER_LINE_DELETED = new OmsSuccessCodes(1010, "ORDER_LINE_DELETED");
    public static final StatusCodes ORDER_LINE_RETRIEVED = new OmsSuccessCodes(1011, "ORDER_LINE_RETRIEVED");
    public static final StatusCodes ORDER_LINE_UPDATED = new OmsSuccessCodes(1012, "ORDER_LINE_UPDATED");
    public static final StatusCodes TRACKER_ADDED = new OmsSuccessCodes(1013, "TRACKER_ADDED");
    public static final StatusCodes TRACKER_DELETED = new OmsSuccessCodes(1014, "TRACKER_DELETED");
    public static final StatusCodes TRACKER_RETRIEVED = new OmsSuccessCodes(1015, "TRACKER_RETRIEVED");
    public static final StatusCodes TRACKER_UPDATED = new OmsSuccessCodes(1016, "TRACKER_UPDATED");
    public static final StatusCodes TRACKER_ENTITY_ADDED = new OmsSuccessCodes(1017, "TRACKER_ENTITY_ADDED");
    public static final StatusCodes TRACKER_ENTITY_DELETED = new OmsSuccessCodes(1018, "TRACKER_ENTITY_DELETED");
    public static final StatusCodes TRACKER_ENTITY_RETRIEVED = new OmsSuccessCodes(1019, "TRACKER_ENTITY_RETRIEVED");
    public static final StatusCodes TRACKER_ENTITY_UPDATED = new OmsSuccessCodes(1020, "TRACKER_ENTITY_UPDATED");
    public static final StatusCodes TRACKER_ENTITY_ACTIONS_RETRIEVED = new OmsSuccessCodes(1021, "TRACKER_ENTITY_ACTIONS_RETRIEVED");
    public static final StatusCodes ACTION_HISTORY_CREATED = new OmsSuccessCodes(1022, "ACTION_HISTORY_CREATED");
    public static final StatusCodes ACTION_HISTORY_UPDATED = new OmsSuccessCodes(1023, "ACTION_HISTORY_UPDATED");
    public static final StatusCodes ACTION_HISTORY_DELETED = new OmsSuccessCodes(1024, "ACTION_HISTORY_DELETED");
    public static final StatusCodes ACTION_HISTORY_RETRIEVED = new OmsSuccessCodes(1025, "ACTION_HISTORY_RETRIEVED");
    public static final StatusCodes EVENT_CREATED = new OmsSuccessCodes(1026, "EVENT_CREATED");
    public static final StatusCodes EVENT_UPDATED = new OmsSuccessCodes(1027, "EVENT_UPDATED");
    public static final StatusCodes EVENT_DELETED = new OmsSuccessCodes(1028, "EVENT_DELETED");
    public static final StatusCodes EVENT_RETRIEVED = new OmsSuccessCodes(1029, "EVENT_RETRIEVED");
    public static final StatusCodes TRACKER_LINE_BULK_UPDATED = new OmsSuccessCodes(1030, "TRACKER_LINE_BULK_UPDATED");
    public static final StatusCodes WAREHOUSE_COURIER_ASSIGNED = new OmsSuccessCodes(1031, "WAREHOUSE_COURIER_ASSIGNED");
    public static final StatusCodes WAREHOUSE_REASSIGNED = new OmsSuccessCodes(1032, "WAREHOUSE_REASSIGNED");
    public static final StatusCodes SPLIT_RELEASE_SUCCESSFUL = new OmsSuccessCodes(1033, "SPLIT_RELEASE_SUCCESSFUL");
    public static final StatusCodes WAREHOUSE_REASSIGNMENT_POSSIBLE = new OmsSuccessCodes(1034, "WAREHOUSE_REASSIGNMENT_POSSIBLE");
    public static final StatusCodes LINE_CANCELLATION_SUCCESSFUL = new OmsSuccessCodes(1034, "LINE_CANCELLATION_SUCCESSFUL");
    public static final StatusCodes IS_FRAUD_USER = new OmsSuccessCodes(1035, "IS_FRAUD_USER");
    public static final StatusCodes NOT_FRAUD_USER = new OmsSuccessCodes(1036, "NOT_FRAUD_USER");
    public static final StatusCodes UNIQUE_INVOICE_CREATED = new OmsSuccessCodes(1037, "UNIQUE_INVOICE_CREATED");
    public static final StatusCodes UNIQUE_INVOICE_UPDATED = new OmsSuccessCodes(1038, "UNIQUE_INVOICE_UPDATED");
    public static final StatusCodes UNIQUE_INVOICE_DELETED = new OmsSuccessCodes(1039, "UNIQUE_INVOICE_DELETED");
    public static final StatusCodes UNIQUE_INVOICE_RETRIEVED = new OmsSuccessCodes(1040, "UNIQUE_INVOICE_RETRIEVED");
    public static final StatusCodes GOVT_TAX_MIN_VALUES_RETRIEVED = new OmsSuccessCodes(1041, "GOVT_TAX_MIN_VALUES_RETRIEVED");
    public static final StatusCodes RETURNS_CREATED = new OmsSuccessCodes(1050, "RETURNS_CREATED");
    public static final StatusCodes RETURNS_RETRIEVED = new OmsSuccessCodes(1051, "RETURNS_RETRIEVED");
    public static final StatusCodes USER_COMMENTS_RETRIEVED = new OmsSuccessCodes(1042, "USER_COMMENTS_RETRIEVED");
    public static final StatusCodes WAREHOUSE_ALREADY_ASSIGNED = new OmsSuccessCodes(1043, "WAREHOUSE_ALREADY_ASSIGNED");
    public static final StatusCodes REFUND_SUCCESSFUL = new OmsSuccessCodes(1044, "REFUND_SUCCESSFUL");
    public static final StatusCodes EXCHANGE_ORDER_CREATED = new OmsSuccessCodes(1045, "EXCHANGE_ORDER_CREATED");*/
}
