/**
 * 
 */
package com.myntra.apiTests.erpservices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author puneet.khanna1@myntra.com
 * @since July 2016
 *
 */
public class StnTestConstants {

	public static final Logger log = LoggerFactory.getLogger(StnTestConstants.class);
	public static final int DEFAUL_TEST_SOURCE_WAREHOUSEID = 36;
	public static final int DEFAULT_TEST_DESTINATION_WAREHOUSE_ID = 28;
	public static final Integer TEST_SKU_ID = 3833;
	public static final Integer TEST_PO_SKU_ID = 3;
	public static final Integer TEST_SKU_ID_1 = 3831;
	public static final Integer TEST_PO_SKU_ID_1 = 2;
	public static  final String STN_CREATE_REMARK = "Creating Stock transfer request";
	public static final  String STN_DEFAULT_TEST_APPROVER = "avinashg";
	public static final long STN_BUYER_ID = 2297L;
	public static final String STN_Status_Message_SameWHInput="Source and Destination Warehouse can not be same";
	public static final int STN_CREATE_ERROR_CODE_FOR_SAME_SOURCE_AND_DESTINATION_WAREHOUSES = 982;
	public static final  String STATUS_TYPE_ERROR = "ERROR";
	public static final int TEST_NEGATIVE_AND_INVALID_WAREHOUSE_ID = -1;
	public static final String STN_Status_Message_InvalidWH="Invalid warehouse Id";
	public static final int EXPECTED_INVALID_WAREHOUSE_INPUT_FOR_STN_CREATION_STATUS_CODE = 775;
	public static final String DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_MESSAGE = "Items are present in a different warehouse than the Source Warehouse";
	public static final int DISPATCH_ITEM_FROM_THE_WAREHOUSE_DIFFERENT_FROM_SOUCE_WAREHOUSE_FAILURE_STATUS_CODE = 979;
	public static final String STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_MESSAGE_TEMP = "Error occurred while updating/processing data";
	public static final int STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_CODE_TEMP = 58;
	public static final String ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS ="Item not is ISSUED_FOR_OPS status";
	public static final int ITEM_NOT_IN_ISSUED_FOR_OPS_STATUS_CODE = 9503;

	public static final boolean IS_SUCCESS_EXPECTED = false;
	public static final String expectedErrorStatusType = "ERROR";
	public static final String EXPECTED_INVALID_WAREHOUSE_INPUT_FOR_STNC_REATION_STATUS_MESSAGE = "Invalid warehouse Id";
	public static final String REMARK_TOS_END_STN_FOR_APPROVAL = "Sending Stn for Approval";
	public static final String REMARK_TO_SEND_STN_FOR_CANELLATION = "Sending Stn for Approval";
	public static final String REMARKS_TO_APPROVE_STN = "Approving the Test Stn";
	public static final String REMARKS_TO_MOVE_STN_TO_PICKUP_STATE = "Moving STN to pickup State";
	public static final String REMARK_TO_DISPATCH_STN = "Disptaching STN";
	public static final String REMARK_TO_RECEIVE_STN = "Receiving STN";
	public static final String TEST_SKU_CODE = "DHLNTPMC05055";
	public static final Integer TEST_SKU_ID_2 = 9744464;
	public static final  int TEST_SKU_ID_FROM_DISTINCT_WAREHOUSE_THAN_SOURCEWAREHOUSE_IN_STN = 475704;
	public static final String ITEM_NOT_ASSOCIATED_WITH_ANY_TEST_SKU="100006";
	public static final  String ITEM_NOT_ASSOCIATED_WITH_ANY_TEST_WAREHOUSE="5000020";
	public static  final String ITEM_WITH_DISTINCT_QUALITY="5000020";
	public static final String TEST_BIN_ID = "578004";
	public static  final String TEST_BIN_BARCODE = "BWTRDI-00-A-01";
	public static final String STN_DEFAULT_NON_EXISTING_TEST_APPROVER = "Non-Existent User"; //Editing Stn in Pick Initiate State
	public static final String REMARK_EDIT_STN_IN_PICKUP_STATE = "Editing Stn in Pick up initiated State";
	public static  final String REMARK_EDIT_STNIN_CREATED_STATE = "Editing Stn in created State";
	public static  final String REMARK_EDIT_STN_IN_DISPATCHED_STATE = "Editing Stn in dispatched  State";
	public static final String STN_EDIT_ATTEMPT_FAILURE_STATUS_MESSAGE = "Error occurred while updating/processing data";
	public static final int STN_EDIT_ATTEMPT_FAILURE_STATUS_CODE = 58;
	public static final String DISPATCH_ITEM_UN_RELATED_TO_THE_STN_SKU_ERROR_STATUS_MESSAGE = " does not belong to any of the STN SKUs";
	public static final int DISPATCH_ITEM_UNRELATED_TO_THE_STN_SKU_ERROR_STATUS_CODE = 987;
	public static final String DISPATCH_ITEM_WITH_STATUS_DIFFERENT_MENTIONED_IN_STN_STATUS_MESSAGE = "Items belong to a different Status";
	public static final int DISPATCH_ITEM_WITH_STATUS_DIFFERENT_MENTIONED_IN_STN_STATUS_CODE = 980;
	public static  final String DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_MESSAGE = "Items belong to a different Quality";
	public static final int DISPATCH_ITEM_FROM_DIFFERENT_QUALITY_MENTIONED_IN_STN_STATUS_CODE = 981;
	public static final String DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUSMESSAGE = "Error while validating quantities";
	public static  final int DISPATCH_ITEM_ATTEMPT_MORE_THE_STN_INTENDS_TO_MOVE_STATUS_CODE = 7007;
	public static final String STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_MESSAGE = "Cannot change status from CREATED to APPROVED";
	public static final int STN_APPROVE_ATTEMPT_WITHOUT_ITEM_ASSOCIATION_FAILURE_STATUS_CODE = 6078;
	public static final String SKU_STN_ASSOCIATION_SUCCESS_STATUS_MESSAGE = "STN SKU added successfully";
	public static final int SKU_STN_ASSOCIATION_SUCCESS_STATUS_CODE = 7001;
	public static final String DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_MESSAGE = "All Items are either in transit status or already added to STN."; 
	public static final int DUPLICATE_ITEM_DISPTACH_FAILURE_STATUS_CODE = 6176;
	public static final String INVALID_QUANITY_PER_SKU_FAILURE_STATUS_MESSAGE = "Quantities are Invalid. Either they are negative or Items are not present in said Status"; 
	public static final int INVALID_QUANITY_PER_SKU_FAILURE_STATUS_CODE = 978;
	public static final String STN_UPDATE_SUCCESS_STATUS_MESSAGE = "STN updated successfully";
	public static final int DISPATCH_ITEM_ATTEMPT_MORE_THAN_STN_INTENDS_TO_STATUSCODE = 977;
	public static final String STN_CREATE_SUCCESS_STATUS_MESSAGE = "STN added successfully";
	public static final int STN_CREATE_SUCCESS_STATUS_CODE = 7005;

	public static final String STATE_PICKUP_INITIATED = "PICK_INITIATED";
	public static final String STATE_DISPATCHED = "DISPATCHED";
	public static final String STN_UPDATED_SUCCESSFULLY_STATUS_MESSAGE = "STN updated successfully";
	public static final int STN_UPDATED_SUCCESSFULLY_STATUS_CODE = 7007;
	public static final String STATE_READY = "READY";
	public static final String STATE_CREATED = "CREATED";
	public static final String STATE_APPROVED = "APPROVED";
	public static final  String STATE_CANCELLED = "CANCELLED";
	
}
