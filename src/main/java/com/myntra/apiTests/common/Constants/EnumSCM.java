package com.myntra.apiTests.common.Constants;

public interface EnumSCM {

	String XPRESS = "XPRESS";
	public static final String PACKED = "PACKED";
	public static final String DELIVERED = "DELIVERED";
	public static final String INSCANNED = "INSCANNED";
	public static final String RTO_LOST = "RTO_LOST";
	public static final String LOST = "LOST";
	public static final String LOST_IN_HUB = "LOST_IN_HUB";
	public static final String LOST_IN_DC = "LOST_IN_DC";
	public static final String SHIPPED = "SHIPPED";
	public static final String UNASSIGN = "UNASSIGN";
	public static final String PICKUP_CREATED = "PICKUP_CREATED";
	public static final String ONHOLD_PICKUP_WITH_PLATFORM = "ONHOLD_PICKUP_WITH_PLATFORM";
	public static final String RTO_RECEIVED = "RTO_RECEIVED";
	public static final String RECEIVED_IN_RETURNS_HUB = "RECEIVED_IN_RETURNS_HUB";
	public static final String RECEIVED_AT_TRANSPORT_HUB = "RECEIVED_AT_TRANSPORT_HUB";
	public static final String RTO_DISPATCHED = "RTO_DISPATCHED";
	public static final String RETURN_RECEIVED = "RETURN_RECEIVED";
	public static final String RETURN_REJECTED_RESHIP_PENDING = "RETURN_REJECTED_RESHIP_PENDING";
	public static final String CANCELLED_AT_HUB = "CANCELLED_AT_HUB";
	public static final String ON_HOLD = "ON_HOLD";
	public static final String ASSIGN_TO_SDA = "ASSIGN_TO_SDA";
	public static final String ASSIGNED_TO_SDA = "ASSIGNED_TO_SDA";
	public static final String START_TRIP = "START_TRIP";
	public static final String PICKUP_QC_COMPLETE = "PICKUP_QC_COMPLETE";
	public static final String ASSIGN_TO_LAST_MILE_PARTNER = "ASSIGN_TO_LAST_MILE_PARTNER";
	public static final String HAND_OVER_TO_LAST_MILE_PARTNER = "HAND_OVER_TO_LAST_MILE_PARTNER";
	public static final String PICKUP_APPROVED_BY_CC = "PICKUP_APPROVED_BY_CC";
	public static final String PICKUP_REJECTED_BY_CC = "PICKUP_REJECTED_BY_CC";
	public static final String RETURN_IN_TRANSIT = "RETURN_IN_TRANSIT";
	public static final String COMPLETE_TRIP = "COMPLETE_TRIP";
	public static final String RESHIP_TO_CUSTOMER = "RESHIP_TO_CUSTOMER";
	public static final String RESHIPPED_TO_CUSTOMER = "RESHIPPED_TO_CUSTOMER";
	public static final String COMPLETED = "COMPLETED";
	public static final String CREATED = "CREATED";
	public static final String CREATE = "CREATE";
	public static final String DL = "DL";
	public static final String PU = "PU";
	public static final String FD = "FD";
	public static final String OFD = "OFD";
	public static final String TRY_AND_BUY = "TRY_AND_BUY";
	public static final String IS = "IS";
	public static final String PK = "PK";
	public static final String RCVD_IN_DISPATCH_HUB = "RCVD_IN_DISPATCH_HUB";
	public static final String RTO = "RTO";
	public static final String RTO_R = "RTO_R";
	public static final String SH = "SH";
	public static final String CANCELLED_IN_HUB = "CANCELLED_IN_HUB";
	public static final String ADDED_TO_MB = "ADDED_TO_MB";
	public static final String RECEIVED_IN_REGIONAL_HANDOVER_CENTER = "RECEIVED_IN_REGIONAL_HANDOVER_CENTER";
	public static final String RTO_IN_TRANSIT = "RTO_IN_TRANSIT";
	public static final String RECEIVED_IN_DISPATCH_HUB = "RECEIVED_IN_DISPATCH_HUB";
	public static final String PICKED_UP_SUCCESSFULLY = "PICKED_UP_SUCCESSFULLY";
	public static final String PICKED_UP_QCP_APPROVED = "PICKED_UP_QCP_APPROVED";
	public static final String PICKED_UP_QCP_REJECTED = "PICKED_UP_QCP_REJECTED";
	public static final String PICKED_UP_QCP_APPROVED_Before_trip_close = "PICKED_UP_QCP_APPROVED_Before_trip_close";
	public static final String PICKED_UP_QCP_REJECTED_Before_trip_close = "PICKED_UP_QCP_REJECTED_Before_trip_close";
	public static final String PICKED_UP_QCP_APPROVED_After_trip_close = "PICKED_UP_QCP_APPROVED_After_trip_close";
	public static final String PICKED_UP_QCP_REJECTED_After_trip_close = "PICKED_UP_QCP_REJECTED_After_trip_close";
	public static final String ONHOLD_PICKUP_WITH_CUSTOMER_REJECT = "ONHOLD_PICKUP_WITH_CUSTOMER_REJECT";
	public static final String FAILED_PICKUP_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT = "FAILED_PICKUP_ONHOLD_PICKUP_WITH_CUSTOMER_REJECT";
	public static final String ONHOLD_PICKUP_WITH_CUSTOMER_APPROVE = "ONHOLD_PICKUP_WITH_CUSTOMER_APPROVE";
	public static final String ONHOLD_PICKUP_WITH_DC_REJECT = "ONHOLD_PICKUP_WITH_DC_REJECT";
	public static final String ONHOLD_PICKUP_WITH_DC_APPROVE = "ONHOLD_PICKUP_WITH_DC_APPROVE";
	public static final String FAILED_PICKUP_AND_SUCCESS = "FAILED_PICKUP_AND_SUCCESS";
	public static final String PICKUP_DONE_QC_PENDING = "PICKUP_DONE_QC_PENDING";
	public static final String FAILED_PICKUP_FAILED_PICKUP_AND_SUCCESS = "FAILED_PICKUP_FAILED_PICKUP_AND_SUCCESS";
	public static final String FAILED_PICKUP_AND_SUCCESS_ON_SAMETRIP = "FAILED_PICKUP_AND_SUCCESS_ON_SAMETRIP";
	public static final String APPROVED_ONHOLD_PICKUP_WITH_DC = "APPROVED_ONHOLD_PICKUP_WITH_DC";
	public static final String ASSIGNED_TO_LAST_MILE_PARTNER = "ASSIGNED_TO_LAST_MILE_PARTNER";
	public static final String EXPECTED_IN_DC = "EXPECTED_IN_DC";
	public static final String FAILED_DELIVERY = "FAILED_DELIVERY";
	public static final String FAILED_PICKUP = "FAILED_PICKUP";
	public static final String ONHOLD_PICKUP_WITH_CUSTOMER = "ONHOLD_PICKUP_WITH_CUSTOMER";
	public static final String OUT_FOR_DELIVERY = "OUT_FOR_DELIVERY";
	public static final String OUT_FOR_PICKUP = "OUT_FOR_PICKUP";
	public static final String PICKED_UP = "PICKED_UP";
	public static final String PICKUP_SUCCESSFUL = "PICKUP_SUCCESSFUL";
	public static final String PICKUP_SUCCESSFUL_CB_AT_DC = "PICKUP_SUCCESSFUL_CB_AT_DC";
	public static final String PICKUP_SUCCESSFUL_CB_AT_WH = "PICKUP_SUCCESSFUL_CB_AT_WH";
	public static final String PICKUP_SUCCESSFUL_CB_AT_RPU = "PICKUP_SUCCESSFUL_CB_AT_RPU";
	public static final String RESCHEDULED_CUSTOMER_NOT_AVAILABLE = "RESCHEDULED_CUSTOMER_NOT_AVAILABLE";
	public static final String RECEIVED_IN_DC = "RECEIVED_IN_DC";
	public static final String RTO_CONFIRMED = "RTO_CONFIRMED";
	public static final String REJECTED_ONHOLD_PICKUP_WITH_DC = "REJECTED_ONHOLD_PICKUP_WITH_DC";
	public static final String REJECTED_ONHOLD_PICKUP_WITH_COURIER = "REJECTED_ONHOLD_PICKUP_WITH_COURIER";
	public static final String RESHIPPED = "RESHIPPED";
	public static final String UNASSIGNED = "UNASSIGNED";
	public static final String HANDED_OVER_TO_3PL = "HANDED_OVER_TO_3PL";
	public static final String RECEIVED_AT_TRANSPORTER_HUB = "RECEIVED_AT_TRANSPORTER_HUB";
	public static final String IN_TRANSIT_DELAYED = "IN_TRANSIT_DELAYED";
	public static final String CLOSED = "CLOSED";
	public static final String NEW = "NEW";
	public static final String IN_TRANSIT = "IN_TRANSIT";
	public static final String RECEIVED = "RECEIVED";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String WARNING = "WARNING";
	public static final String DELIVERY = "DELIVERY";
	public static final String PICKUP = "PICKUP";
	public static final String TRYNBUY = "TRYNBUY";
	public static final String TRY_BUY = "TRY_BUY";
	public static final String SELF_SHIP = "SELF_SHIP";
	public static final String RECEIVE_IN_DC = "RECEIVE_IN_DC";
	public static final String SELF_SHIP_REJECT = "SELF_SHIP_REJECT";
	public static final String SELF_SHIP_PICKUP_SUCCESSFUL = "SELF_SHIP_PICKUP_SUCCESSFUL";
	public static final String SELF_SHIP_ON_HOLD_APPROVE = "SELF_SHIP_ON_HOLD_APPROVE";
	public static final String ONHOLD_PICKUP_WITH_DC = "ONHOLD_PICKUP_WITH_DC";
	public static final String ONHOLD_PICKUP_WITH_COURIER = "ONHOLD_PICKUP_WITH_COURIER";
	public static final String SELF_SHIP_ON_HOLD_REJECT = "SELF_SHIP_ON_HOLD_REJECT";
	public static final String FAILED = "FAILED";
	public static final String ON_HAND = "ON_HAND";
	public static final String JIT = "JIT";
	public static final String JUST_IN_TIME = "JUST_IN_TIME";
	public static final String OPEN_BOX_PICKUP = "OPEN_BOX_PICKUP";
	public static final String CLOSED_BOX_PICKUP = "CLOSED_BOX_PICKUP";
	public static final String VALUE_SHIPPING = "VALUE_SHIPPING";
	public static final  String PICKUP_SUCCESSFUL_ONHOLD_APPROVE="PICKUP_SUCCESSFUL_ONHOLD_APPROVE";
	public static final  String PICKUP_SUCCESSFUL_ONHOLD_REJECTED="PICKUP_SUCCESSFUL_ONHOLD_REJECTED";
	public static final  String IN_TRANSIT_WRONG_ROUTE="IN_TRANSIT_WRONG_ROUTE";
	
	public static final String Q = "Q";
	public static final String RFR = "RFR";
	public static final String PP = "PP";
	public static final String PV = "PV";
	public static final String WP = "WP";
	public static final String C = "C";
	public static final String L = "L";
	public static final String A = "A";
	public static final String D = "D";
	public static final String IC = "IC";
	public static final String QD = "QD";
	public static final String S = "S";
	public static final String F = "F";
	public static final String NORMAL = "NORMAL";
	public static final String EXPRESS = "EXPRESS";
	public static final String SDD = "SDD";
	public static final String EXCHANGE = "EXCHANGE";
	public static final String RETURN = "RETURN";
	public static final String CANCELLED = "CANCELLED";
	public static final String ORDER = "ORDER";
	public static final String ORDERNUMBER = "ORDER";
	public static final String RELEASE = "RELEASE";
	public static final String LINE = "LINE";
	public static final String ISOC = "ISOC";
	public static final String CCR = "CCR";
	public static final String PQCP = "PQCP";
	public static final String REJECTED = "REJECTED";
	public static final String NOT_INITIATED = "NOT_INITIATED";
	public static final String AWAITING = "AWAITING";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String FIT = "FIT";
	public static final String UNRTO = "UNRTO";
	public static final String SMDL = "SMDL";
	public static final String FDDL = "FDDL";
	public static final String FDTODL = "FDTODL";
	public static final String FDFDDL = "FDFDDL";
	public static final String RECEIVED_AT_HANDOVER_CENTER = "RECEIVED_AT_HANDOVER_CENTER";
	public static final String SHORTAGE = "SHORTAGE";
	public static final String RECEIVED_DAMAGED = "RECEIVED_DAMAGED";
	public static final String UPDATE = "UPDATE";
	public static final String NOT_ABLE_TO_PICKUP = "NOT_ABLE_TO_PICKUP";
	public static final String PICKUP_SUCCESSFUL_QC_PENDING = "PICKUP_SUCCESSFUL_QC_PENDING";
	public static final String RETURNS_CANCELLATION = "RETURNS_CANCELLATION";
	public static final String RETURN_REJECTED = "RETURN_REJECTED";
	public static final String ON_HOLD_DAMAGED_PRODUCT = "ON_HOLD_DAMAGED_PRODUCT";
	public static final String TRIP_COMPLETE = "TRIP_COMPLETE";
	public static final String APPROVED = "APPROVED";
	public static final String COURIER_HANDOVER = "COURIER_HANDOVER";
	public static final String TRANSITION_NOT_CONFIGURED = "TRANSITION_NOT_CONFIGURED";
	
	
	// **********RMS status ****************
	public static final String CFDC = "CFDC";
	public static final String CPDC = "CPDC";
	public static final String RADC = "RADC";
	public static final String RDU = "RDU";
	public static final String RIS = "RIS";
	public static final String RJDC = "RJDC";
	public static final String RJS = "RJS";
	public static final String RJUP = "RJUP";
	public static final String RNC = "RNC";
	public static final String RPF = "RPF";
	public static final String RPF2 = "RPF2";
	public static final String RPF3 = "RPF3";
	public static final String RPI = "RPI";
	public static final String RPI2 = "RPI2";
	public static final String RPI3 = "RPI3";
	public static final String RPU = "RPU";
	public static final String RPUQ2 = "RPUQ2";
	public static final String RPUQ3 = "RPUQ3";
	public static final String RQCF = "RQCF";
	public static final String RQCP = "RQCP";
	public static final String RQF = "RQF";
	public static final String RQP = "RQP";
	public static final String RQSF = "RQSF";
	public static final String RQSP = "RQSP";
	public static final String RRC = "RRC";
	public static final String RRD = "RRD";
	public static final String RRJ = "RRJ";
	public static final String RRQP = "RRQP";
	public static final String RRQS = "RRQS";
	public static final String LPI = "LPI";
	public static final String DEC = "DEC";
	public static final String RRRS = "RRRS";
	public static final String RRS = "RRS";
	public static final String RSD = "RSD";

	// ********** TOD Status ****************
	public static final String NOT_TRIED = "NOT_TRIED";
	public static final String TRIED_AND_BOUGHT = "TRIED_AND_BOUGHT";
	public static final String TRIED_AND_NOT_BOUGHT = "TRIED_AND_NOT_BOUGHT";
	public static final String SNATCHED = "SNATCHED";
	public static final String wareHouseIdJabong = "110";

    //********** Courier Codes ****************
	public static final   String COURIER_CODE_ML = "ML";
	public static final String COURIER_CODE_EK = "EK";
	public static final String COURIER_CODE_BD = "BD";	
	public static final String COURIER_CODE_IP = "IP";
	
	// ********** Payment Methods ****************
	public static final String ONLINE_PAYMENT = "on";
	public static final String COD_PAYMENT = "cod";
	
	// ********** Item Types ****************
	public static final String ITEM_TYPE = "E_GIFT_CARD";

    //*********Store Codes*********************
    
	public static final Long STORE_ID_MYNTRA=1L;
	public static final Long STORE_ID_FKART2=2L;
	public static final Long STORE_ID_FKART3=3L;
	public static final Long STORE_ID_FKART4=4L;
	public static final Long STORE_ID_JABONG=5L;
    
    
	public static final String SELLER_PACKET_ITEM_STATUS_A="A";
	public static final String SELLER_PACKET_ITEM_STATUS_QD="QD";
	public static final String SELLER_PACKET_ITEM_STATUS_PK="PK";
	public static final String SELLER_PACKET_ITEM_STATUS_IC="IC";
	public static final String SELLER_PACKET_ITEM_STATUS_PP="PP";
    
    
	public static final  String SELLER_PACKET_STATUS_Q="Q";
	public static final String SELLER_PACKET_STATUS_PK="PK";
	public static final String SELLER_PACKET_STATUS_SH="SH";
	public static final String SELLER_PACKET_STATUS_F="F";
	public static final String SELLER_PACKET_STATUS_L="L";
    
    //********** ORDER ADDITIONAL INFO ****************
	public static final String ADDITIONAL_INFO_PACKAGING_TYPE = "PACKAGING_TYPE";
	public static final String ADDITIONAL_INFO_PACKAGING_VALUE_PREMIUM="PREMIUM";
	public static final String ADDITIONAL_INFO_PACKAGING_VALUE_NORMAL="NORMAL";
	public static final String EXPRESS_REFUND_PPS_ID="EXPRESS_REFUND_PPS_ID";
	public static final String EXPRESS_REFUND_TX_REF_ID = "EXPRESS_REFUND_TX_REF_ID";
	public static final Long QC_PASS_CODE=61L;
	public static final Long QC_FAIL_CODE = 62L;
	public static final Long QC_DESK_CODE=1L;
	public static final String QC_QUALITY_Q2 = "Q2";
	public static final String QC_REJECT_REASON = "DAMAGED_CTH";
	public static final String QC_REJECT_DESCRIPTION = "Damaged (Cut/Torn/Hole)";
	String wareHouse_BLR="36";
	String MFB_Jabong_StoreID="5";
	
	//********** Application Properties ****************
	public static final String USE_GST_ENGINE="taxmaster.use_customer_gst_engine";
	public static final String IS_LMC_ON="oms.lmc_on";
}
