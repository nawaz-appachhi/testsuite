package com.myntra.apiTests.erpservices;

public class InternalOrderTestConstants {
	// prevent instantiation
	private InternalOrderTestConstants(){
		
	}
	public static final int DEFAULT_TEST_WAREHOUSE_ID=36;
	public static final int DEFAULT_TEST_SELLER_ID = 21;
	public static final String DEFAULT_REMARKS_TO_CREATE_ORDER="Creating Test Internal Order";
	public static final String REMARKS_TO_MOVE_IO_TO_SEND_FOR_APPROVAL = "Sending IO for approval";
	public static final String REMARKS_TO_MOVE_IO_TO_SHIPPED_STATE = "Remarks to ship an internal Order";
	public static final String REMARKS_TO_MOVE_IO_TO_PICK_INITIATION = "Sending IO for pick initiation";
	public static final String REMARKS_TO_APPROVE_AN_INTERNAL_ORDER = "Approving an internal Order";
	public static final Integer DEFAULT_TEST_SKU_ID = 3869;
	public static final Integer DEFAULT_TEST_SKU_ID_1 = 3959;
	public static final long BUYER_ID = 2297L;
	public static final int DEFAULT_QUANTITY_OF_ITEMS_TO_TEST = 1;
	public static final int DEFAULT_SKU_ID_WITH_NO_INVENTORY = 3958;
	public static final String DEFAULT_APPROVER="avinashg";
	public static final String ORDER_TYPE_B2B_SALES="B2B_Sales";
	public static final String ORDER_TYPE_PROMOTINAL_GOODS="";
	public static final String ORDER_TYPE_OTHERS="";
	public static final String ORDER_STATUS_TO_PLACE_AN_INTERNAL_ORDER="CREATED";
	public static final String INTERNAL_ORDER_TRANSITIONAL_FAILURE_MESSAGE="Error occurred while inserting/processing data";
	public static final int INTERNAL_ORDER_TRANSITIONAL_FAILURE_CODE=54;
	public static final int CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_CODE=7027;
	public static final String CREATE_INTERNAL_ORDER_SUCCESS_REPONSE_STATUS_MESSAGE="Internal Order Placed Successfully";
	public static final int ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_CODE=7560;
	public static final String ITEM_INTERNAL_ORDER_ASSOCIATION_SUCCESS_REPONSE_STATUS_MESSAGE="IO Items Created successfully";
	public static final int INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_CODE=7029;
	public static final String INTERNAL_ORDER_STATE_TRANSITION_SUCCESS_MESSAGE="Internal Order updated";
	public static final String ITEM_CHECK_OUT_SUCCESS_MESSAGE="Items checkout for the order successfully";
	public static final int ITEM_CHECK_OUT_SUCCESS_CODE=6551;
}
