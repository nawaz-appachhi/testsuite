package com.myntra.apiTests.erpservices;

public class Constants {


	public static class IDP_PATH {
		public static final String SIGN_IN = "signin";
	}

	public static class KNIGHT_PATH {
		public static final String BLOCK_USER = "store/set";
	}

	public class ATS_PATH {
		// ATS APIS	
		public static final String BLOCK_USER = "customer/store/set";
		public static final String SET_COD_LIMIT = "customer/setcodlimit";
		public static final String GET_OPENORDERS_VALUE = "customer/cod/difference";
		public static final String SET_OPENORDERS_VALUE = "customer/cod/difference/set";
		public static final String GET_OUTSTANDINGCCOD = "customer/getCumulativecodlimit";
		public static final String GET_ATSUSERDETAILS = "customer/getUserDetails";
		public static final String POST_ATSUSERDETAILS = "customer/abuser/details/set";
		public static final String PUT_RETURNABUSERDETAILS = "customer/returns/abuser/details/set";
		public static final String GET_RETURNABUSERDETAILS = "customer/high/returns";
		public static final String PUT_FAKEEMAILDETAILS = "customer/fake/email/set";
		public static final String GET_FAKEEMAILDETAILS = "customer/fake/email";
		public static final String PUT_LINKEDPHONE = "customer/linked/mobile/set";
		public static final String GET_LINKEDPHONE = "linked/getLinkedCustomersByPhone";
		public static final String GET_LINKEDDEVICE = "linked/getlinkedaccounts/date";
		public static final String GET_LINKEDACCOUNTS = "linked/getlinkedaccounts";
		public static final String GET_ADDRESSCORE = "address/getscore";
		public static final String PUT_ISCUSTOMERBLOCKED = "blocked/oms";
		public static final String PUT_CUSTOMERBLOCKED = "customer/store/set";
		public static final String GET_BLOCKEDBY = "blocked";
		public static final String CHANGEUSER_STATUS = "profile/changeUserStatus";
		public static final String IDEASIGNUP = "signup/email";
		public static final String GET_ALLADDRESS = "v2/securedaddress/address/user/";
		}
		
	public static class SPOT_PATH{
		public static final String Create_RAPID_DataSet="rapid/queries";
		//public static final String PROCESS_RAPID_DataSet="rapid/process";
		public static final String GET_RAPID_DATA="rapid/queries";
		public static final String FILTER_RAPID_DATA="rapid/filter";
		public static final String GET_STYLE_DATA_WITH_STYLEID="getStyleData";
		public static final String GET_CLUSTER_DATA="getClusterData";
		public static final String GET_PRODUCT_DATA="v2/getproductdata";
		public static final String UPLOAD_BI_DATA="rapid/upload";
		//public static final String UPLOAD_PROCESSED_DATA="rapid/upload/processed";
		//public static final String FETCH_BI_DATA="fetchBIData";
		//	public static final String PROCESS_BI_DATA="processBIData";
		//public static final String GET_DATA="getData";
		public static final String GET_GLOBAL_ATTRIBUTE_OF_ALL_BRANDS_DATA="globalattribute/allBrands";
		public static final String FETCH_ATTRIBUTES_VALUES="rapid/fetch/attribute/values";
		public static final String FILTER_STYLES="filterstyles";

	}

	public static class SPOT_NODE_PATH{
		public static final String FETCH_DATA="fetchdata";
		public static final String REFINE_SEARCH="refine_search";
		public static final String VIEW_IMAGES="start_visualizing";


	}

	public static class ES_PATH{
		public static final String CMS="fifaes/style_aggregates/_search";
		public static final String AMAZON="amazon/amazon/_search";
		public static final String JABONG="jabong/jabong/_search";

	}

	public static class DDP_PATH {
		public static final String GET_DDP_SUBMIT = "submit";
		public static final String GET_DDP_STATUS = "/status";
		public static final String GET_DDP_DOWNLOAD_LINK = "/download";
	}

	public static class LMS_PATH{
		public static final int sleepTime=20000;
		public static final String BULK_ORDER_REASSIGN="bulkOrder";
		public static final String PUSH_PICKUP_TO_COURIER="return/pushPickupToCourier";
		public static final String DELHIVERY_CREATE="cmu/create.json";
		public static final String CTS_DE_UPDATE="delhivery/shipments/v0/update";
		public static final String CTS_EKART_UPDATE="ekart/shipments/v0/update";
		public static final String UPDATE_ORDER_STATUS ="order/updateOrderStatus";
		public static final String MASTER_BAG="shipment";
		public static final String MASTERBAG_NEW ="platform/v1/masterbag";
		public static final String PLATEFORM_SHIPMENT ="platform/v1/shipment";
		public static final String UPDATE_SHIPMENT_STATUS="platform/v1/shipment/updateShipmentStatus";
		public static final String NEW_ADMIN_UPDATE_SHIPMENT="platform/v1/platform-ml/updateShipmentStatus";
		public static final String MASTER_BAG_SEARCH="shipment/search";
		public static final String SAVE_MASTER_BAG="shipment/addOrders";
		public static final String CLOSE_MASTER_BAG="shipment/close";
		public static final String SHIP_MASTER_BAG="shipment/ship";
		public static final String BULK_UPLOAD_HLP="ml/mlShipmentService/bulkUploadLastMileShipmentUpdate";
		public static final String DELIVERY_CENTRE="deliveryCenter";
		public static final String GET_TRACKING_NUMBER="courier/getTrackingNumber";
		public static final String DELIVERY_STAFF="deliveryStaff";
		public static final String DELIVERY_STAFF_SEARCH="deliveryStaff/search";
		public static final String COURIER="courier";
		public static final String COURIER_STATISTICS = "pincode/getCourierStatistics";
		public static final String COURIER_FOR_PINCODE = "pincode/getCouriers";
		public static final String COURIER_SEARCH="courier/search";
		public static final String GENERATE_TRACKING_NUMBER="courier/generateTrackingNumber";
		public static final String REGION="region";
		public static final String REGION_SEARCH="region/search";
		public static final String PINCODE="pincode/data";
		public static final String PINCODE_="pincode";
		public static final String PINCODEV1="platform/v1/pincode";
		public static final String PINCODE_SEARCH="pincode/data/search";
		public static final String PINCODE_UPLOAD="pincode/uploadPincodes";
		public static final String COURIER_PREFERENCE="courier/setCourierPreference";
		public static final String ORDER="order";
		public static final String BULK_UPLOAD_ORDER_TRACKING="order/bulkUpdateOrderTracking";
		public static final String ORDER_BY_ID="order/orderId";
		public static final String ORDER_TRACKING ="orderTracking";
		public static final String PINCODE_SERVICEABLE="pincode/isPincodeServiceable";
		public static final String NETWORK="network";
		public static final String BULK_UPLOAD="order/bulkUpdateOrderTracking";
		public static final String TRIP="trip";
		public static final String TRIP_BY_TRIP_NUMBER="trip/findOrdersByTripNumber";
		public static final String TRIP_DETAILS="trip/getTripsDetail";
		public static final String TRIP_CREATE="trip/createByRouteTripMap";
		public static final String TRIP_ASSIGN_ORDER="trip/assignOrderToTripBulk";
		public static final String TRIP_START="trip/startTrip";
		public static final String TRIP_UPDATE="trip/updateTripOrder";
		public static final String TRIP_INSCAN="trip/addAndOutscanNewOrderToTrip";
		public static final String UPDATE_END_ODOMETER ="trip/updateEndOdometerReading";
		public static final String UPDATE_ODOMETER ="trip/updateOdometerReading";
		public static final String UPLOAD_PINCODES = "pincode/uploadPincodes";
		public static final String UPLOAD_TAT = "tat/upload";
		public static final String SET_COURIER_CAPACITY = "courierCapacityConfig/setCourierCapacityConfig";
		public static final String SET_COURIER_PREFERENCEV2 = "courierPreference/setCourierPreference";
		public static final String CHECKSERVE= "serviceabilityv2/checkServiceability";
		public static final String CONSUME_CAPACITY= "courierCapacity/incrementCount";
		public static final String FORCE_UPDATE= "order/forceUpdate";
		public static final String COURIER_TRACKING_NUMBER="courier/getTrackingNumber";
		public static final String CHANGE_COURIER_CODE="orderTracking";
		public static final String RELOADSERVICEABILITY="pincode/reloadServiceability";
		public static final String RELOADCSSSERVICEABILITY="sync/v2";
		public static final String DOWNLOADPINCODES="pincode/downloadPincodes";
		public static final String PICKUP="pickup";
		public static final String GET_RETURN_ADDRESS="pickup/getReturnAddress";
		public static final String GET_RTO_ADDRESS="order/getRtoAddress";
		public static final String ASSIGN_SHIPMENT_TO_HLP="ml/mlShipmentService/assignOrUnAssignShipmentToHLP";
		public static final String TRIP_OUT_SCAN="trip/addAndOutscanNewOrderToTrip";
		public static final String UNASSIGN_ORDER_FROM_TRIP="trip/unassignOrderFromTrip";
		public static final String UNASSIGN_ORDER_FROM_TRIP_THROUGH_TRIP_ID="trip/unassignOrderFromTripThroughTripId";
		public static final String SELF_MARK_DL="trip/updateTripOrderOnTheFly";
		public static final String REQUEUE_ORDER="trip/autoAssignmentOfOrderToTrip";
		public static final String REQUEUE_PICKUP="order/updateOrderFromPickup";
		public static final String APPROVE_REJECT_RETURN="order/approveOrReject";
		public static final String GET_RETURN="return/returnId";
		public static final String GET_ORDER_TRACKING_DETAIL="orderTracking/getOrderTrackingDetail";
		public static final String GET_ORDER_TRACKING_DETAIL_V2="orderTracking/v2";
		public static final String ML_SHIPMENT_UPDATE="ml/mlShipmentService/updateStatus";
		public static final String UPDATE_ML_SHIPMENT="ml/mlShipmentService/updateMLShipment";
		public static final String SHIPMENT_LABEL="ml/mlShipmentService/printShipmentLabel";
		public static final String SHIPMENT_MANIFEST="shipment/manifestReportData";
		public static final String HANDOVER_TO_RHC="shipment/handedOverToRegionalCourier";
		public static final String HUB ="hub";
		public static final String BULKJOB_FETCH = "bulkJob/fetch?";
		public static final String MLSHIPMENT_SERVICE="ml/mlShipmentService";
		public static final String GET_ALL_COURIER="courier/getAllCourierDetails";
		public static final String TRANSPORTER="transporter";
		public static final String GET_FD_REASONS="trip/getAttemptReasonCodes?attemptReasonType=FAILED";
		public static final String GET_SHIPPING_CUTOFF="tat/getShippingCutoffsForOrder";
		public static final String ASSIGN_PICKUP_TO_DC="ml/mlShipmentService/assignPickupToDC";
		public static final String SERVICEABILITY="serviceability";
		public static final String GET_ALL_AVAILABLE_TRIPS="trip/getAllAvailableTripsForDC";
		public static final String GET_DS_ROUTE="trip/getDsRouteNameForDc";
		public static final String TRIP_SEARCH="trip/search";
		public static final String TRIP_DETAIL="trip/getTripsDetail";
		public static final String TRIP_DETAILS_FOR_TRACKINGNO="trip/getTripDetails";
		public static final String ACTIVE_TRIP_FOR_ORDER="trip/getActiveTripForOrder";
		public static final String UPDATE_PAYMENT_TYPE="trip/updateOrderPaymentType";
		public static final String VALIDATE_ORDER="trip/validateOrder";
		public static final String HUB_WH_CONFIG="hubWarehouseConfig/search";
		//public static final String DISPATCH_HUB_WH_CONFIG="dispatchHubWarehouseConfig";
		//public static final String RETURN_HUB_WH_CONFIG="returnHubWarehouseConfig";
		public static final String HUB_HANDOVER_CONFIG="hubHandoverConfig";
		public static final String SET_HUB_HANDOVER_CONFIG="hubHandoverConfig/setConfig";
		public static final String COURIER_BY_PINCODE="pincode/getCouriers";
		public static final String INSCAN_GOR="order/inscan/gor";
		public static final String CONSOLIDATION_BAG="consolidationBag";
		public static final String CONSOLIDATION_BAG_CLOSE="consolidationBag/close";
		public static final String CREATE_JABONG_SHIPMENT="platform/v2/shipment/create/v1";
		public static final String ORDER_IN_SCAN="order/updateOrderStatus";
		public static final String HUB_SERVICEABILITY_UPDATE="serviceability/update";
		public static final String REDIS_DELETE_KEYS="redis/delete/keys";
		public static final String TMS_TRANSPORTER="transporter";
		public static final String TMS_HUB="hub";
		public static final String CONTAINER="container";
		public static final String CONTAINER_TRACKING_DETAIL="container/getContainerTrackingDetail";
		public static final String TMS_MISROUTE_PENDENCY="hub/getMisrouteBagsHubPendency";
		public static final String LANE="lane";
		public static final String SHIP_CONTAINER="container/ship";
		public static final String TMS_CREATE_UPDATE="masterbag/createOrUpdate";
		public static final String TMS_MASTERBAG ="masterbag";
		public static final String GET_TRANSPORTER_HUB_PENDENCY ="hub/getTransportHubPendency";
		public static final String GET_MISROUTE_PENDENCY ="hub/getMisrouteBagsHubPendency";
		public static final String MASTERBAG_PREALERT ="hub/getMasterbagPreAlert";
		public static final String SHIPMENT_SOURCE ="shipmentSource/search";
		public static final String TMS_RECEIVE_MASTERBAG ="masterbag/receiveMasterbag";
		public static final String TMS_CONTAINER ="container";
		public static final String GET_SUPPORTED_LANES ="lane/getSupportedLanes";
		public static final String GET_LOCATION_HUB_CONFIG ="locationHubToTransportHubConfig/getConfigByLocationHubCode";
		public static final String GET_SUPORTED_TRANSPORTER_FOR_LANE_ ="transporter/findSupportedTransportersForLane";
		public static final String TRANSPORTER_LANE_CONFIG_DOWNLOAD ="transporterLaneConfig/download";
		public static final String LH_TH_CONFIG_DOWNLOAD ="locationHubToTransportHubConfig/download";
		public static final String LANE_HUB_CONFIG_DOWNLOAD ="laneHubConfig/download";
		public static final String SUPPORTED_TRANSPORTHUB_CONFIG_DOWNLOAD ="supportedTransportHubConfig/download";
		public static final String TMS_CONTAINER_MANIFEST = "manifest";
		public static final String TMS_MASTERBAG_MANIFEST ="manifest/masterbag";
		public static final String CTS_SHIPMENT_UPDATE ="shipments/update/v2";
		public static final String GET_ML_SHIPMENT_BY_TRACKING_NO ="ml/mlShipmentService/trackingNumber";
		public static final String ML_SHIPMENT_SERVICE ="ml/mlShipmentService";
		public static final String PLATEFORM_ML ="platform/v1/platform-ml/shipment";
		public static final String TRIP_ORDER_ASSIGNMENT ="tripOrderAssignment";
		public static final String RECEIVE_SHIPMENT_IN_DC ="platform/v1/masterbag/receiveShipment";
		public static final String GET_SHIPPING_LABEL ="platform/v1/doc/shippingLabel";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
		public  static final String PICKUP_BY_ID="platform/v1/pickup";
		public static final String RETURN="return/returnId";
		public static final String OPEN_BOX_MANIFEST ="platform/v1/pickup/manifestWrapperService/OPEN_BOX_PICKUP/ML";
		public static final String CLOSED_BOX_MANIFEST ="platform/v1/pickup/manifestWrapperService/CLOSED_BOX_PICKUP/DE";
		public static final String UPLOAD_CLOSEDBOX_STATUS="platform/v1/pickup/uploadPickupStatus";
		public static final String UPDATE_PICKUP_STATUS_EVENT="platform/v1/pickup/updatePickupStatus";
		public static final String UPDATE_CLOSEDBOX_QC_STATUS="platform/v1/pickup/closedBoxPickupQCUpdate";
		public static final String UPDATE_OPENBOX_QC_STATUS="ml/mlShipmentService/openBoxQcUpdate";
	}

	public static class CSS_PATH{
		public static final String CHECK_SERVICABILITY="serviceability/checkServiceability";
		public static final String LOAD_SERVICABILITY="sync/loadServiceability";
		public static final String CHECK_SERVICABILITY_WITH_ATTRIBUTES="serviceabilityv2/checkServiceabilityWithAttributes";
		public static final String CHECK_SERVICABILITY_SUMMARY="serviceabilityv2/checkServiceabilitySummary";
		public static final String UPLOAD_WH_HUB_CONFIG="dispatchHubWarehouseConfig/uploadWarehouseHubConfig";
		public static final String HUB_COURIER_CAPACITY_CONFIG="hubCourierCapacityConfig";
	}

	public static class ARMOR_PATH{
		public static final String   GET_ORDER= "armor/order";
		public static final String   FIND_ORDER= "armor/order";
	}

	public static class OMS_PATH{

		public static final String CANCEL_ORDER="oms/order/v2/cancelOrder";
		public static final String CANCEL_ORDER_RELEASE = "oms/orderrelease/v2/cancelRelease";
		public static final String CANCEL_LINES = "oms/orderrelease/v2/cancelLines";
		public static final String GET_ORDER="oms/order";
		public static final String GET_ORDER_LINE="oms/orderline";
		public static final String GET_ORDER_RELEASE = "oms/orderrelease";
		public static final String GET_PACKET = "oms/packet";
		public static final String PRICE_OVER_RIDE = "oms/orderline/priceOverride";
		public static final String EXPRESS_REFUND = "oms/orderrelease/refundExpressCharges";
		public static final String EXPRESS_REFUND_PACKET = "oms/packet/refundExpressCharges";
		public static final String CREATE_ORDER = "oms/order";
		public static final String EVALUATE_ORDER_ONHOLD = "oms/order/evaluateOnholdRules";
		public static final String GETALLMINTAXINFO = "oms/orderline/getAllMinTaxInfo";
		public static final String REFRESHMINTAXINFO = "oms/orderline/refreshMinTaxInfo";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
		public static final String RESOLVE_ONHOLD_ORDER = "oms/order/resolveOnhold";
		public static final String CREATE_EXCHANGE = "exchange/order/createExchange/";
		public static final String QUEUE_EXCHANGE = "exchange/order/queueExchange";
		public static final String INVOICE = "oms/orderrelease/invoice";
		public static final String CHANGEADDRESS = "oms/orderrelease/changeAddress";
		public static final String CLEAR_JVM_CACHE = "monitoring?action=clear_caches";
		public static final String RTSSCAN = "oms/orderrelease/markReleasePacked";
		public static final String MARK_RELEASE_SHIP = "oms/orderrelease/markReleaseShipped";
		public static final String SPLITORDER = "oms/orderrelease/splitOrder";
		public static final String REASSIGNWAREHOUSE = "oms/orderrelease/reAssignWarehouse";
		public static final String GETORDER= "oms/order";
		public static final String STAMP_GOVT_TAX = "oms/orderrelease/stampGovtTax";
		public static final String SHIPPING_LABEL = "oms/orderrelease/doc/test/getShippinLabelEntry";
		public static final String INVOICE_SERVICE = "oms/orderrelease/doc/test/getStickerInvoiceEntry";
		public static final String INVOICE_SERVICE_PDF = "oms/orderrelease/doc/shippingLabelAndStickerInvoice";
		public static final String IS_CANCELALLOWED = "oms/orderrelease/isCancellationAllowed";
		public static final String SCHEDULE_RELEASE = "oms/orderrelease/pushProcessedRelease";
		public static final String MARK_RELEASE_DELIVERED = "oms/orderrelease/markReleaseDelivered";
		public static final String MARK_RELEASE_LOST = "oms/orderrelease/markReleaseLost";
		public static final String MARK_RELEASE_WP = "oms/action/execute/MARK_RELEASE_WP";
		public static final String COMPLETE_RELEASE = "oms/action/execute/COMPLETE_RELEASE";
		public static final String MARK_RELEASE_RTO = "oms/orderrelease/markReleaseRto";
		public static final String STICKER_INVOICE = "oms/orderrelease/doc/test/getStickerInvoiceEntry";
		public static final String CREATE_GIFTCARD_ORDER = "cart/egiftcard";
		public static final String SAVE_GIFTCARD_INFO = "cart/egiftcard/saveGiftCardInfo";
		public static final String MARK_READY_TO_DISPATCHV2 = "oms/seller/order/v2/markReadyToDispatch";
		public static final String UPDATE_TAXV2 = "oms/seller/order/v2/updateTax";
		public static final String CANCEL_ITEMSV2 = "oms/seller/order/v2/cancelItems";
		public static final String PUSH_RELEASE_TO_LMS = "oms/orderrelease/pushReleaseToLms";
		public static final String PUSH_PACKET_TO_LMS = "oms/packet/pushPacketToLms";
		public static final String ASSIGN_TRACKING_NUMBER = "oms/orderrelease/assignTrackingNo";
		public static final String GETTAXONITEM = "oms/getTaxOnItem";
		public static final String UPDATE_TAXV3 = "oms/seller/order/v3/updateTax";
		public static final String UPDATE_TAXV1 = "oms/seller/order/updateTax";
		public static final String GET_SPLIT_RELEASE = "oms/orderrelease/getSplitRelease";
		public static final String MARK_READY_TO_DISPATCHV3 = "oms/seller/order/v3/markReadyToDispatch";
		public static final String CONFIRM_ORDER_PB = "oms/seller/order/confirmOrder";
		public static final String CONSOLIDATE = "oms/v2/consolidation/consolidate";
		public static final String MARK_READY_TO_DISPATCHV4 = "oms/seller/order/v4/markReadyToDispatch";
		public static final String GET_SHIPPING_LABEL_DOC ="oms/orderrelease/doc/shippingLabel";
		public static final String REASSIGNWAREHOUSEV2 = "v2/oms/seller/order/bulkAutoReassignWarehouse";
    }
	
	public static class MUNSHI_PATH{
		public static final String B2C_CustomerInvoiceEntry="munshi/test/dataAggregator/customerInvoice";
		public static final String B2C_CustomerInvoicePDF="munshi/document/customerInvoice";
		public static final String GENERATE_INFO ="munshi/generateInfo";
		public static final String GENERATE_DOCUMENT="munshi/document/generateDocument";
		public static final String GENERATE_DOCUMENT_WITH_INFO="/generateDocumentWithInfo";
		public static final String FETCH_USING_PARTNER_IDS="/fetchUsingPartnerIds";
		public static final String FETCH_USING_DOCUMENT_IDS="/fetchUsingDocumentIds";
		public static final String B2C_StickerInvoicePDF="oms/orderrelease/doc/shippingLabelAndStickerInvoice";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";

	}



	public static class TOOLS_PATH {
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
		public static final String  QUERYAPPLICATIONPROPERTY="platform/tools/properties";
	}

	public static class ORCH_PATH {
		public static final String RESOLVE_ONHOLD_ORDER = "oms/order/resolveOnHold";
		public static final String RESOLVE_ONHOLD_ORDER_RELEASE = "oms/orderrelease/resolveOnHold";
	}



	public static class SELLERAPI_PATH{
		public static final String UPDATEINVENTORY = "seller/inventory/v1/update";
		public static final String SEARCHINVENTORY = "seller/inventory/v1/search";
		public static final String CANCEL_ORDER = "seller/order/v1";
		public static final String RTD = "seller/order/v1";
		public static final String CANCEL_ORDER_LINE = "seller/order/v1";
		public static final String GET_ORDER = "seller/order/v1";
		public static final String DOWNLOADSHIPPINGLABEL = "seller/order/v1";
		public static final String UPDATE_TAX = "seller/order/v1";
		public static final String AUTH = "v1/authenticate";
		public static final String GET_CITURS_BAL="merchant/getAllbalance";
		public static final String AUTH_PROD = "auth";
		public static final String GET_CITURS_PAYOUT="merchant/getbankreference/";

		public static final String UPDATEINVENTORY_V2 = "seller/v2/inventory/update";
		public static final String SEARCHINVENTORY_V2 = "seller/v2/inventory/search";
		public static final String CANCEL_ORDER_V2 = "seller/v2/order";
		public static final String RTD_V2 = "seller/v2/order";
		public static final String CANCEL_ORDER_LINE_V2 = "seller/v2/order";
		public static final String GET_ORDER_V2 = "seller/v2/order";
		public static final String DOWNLOADSHIPPINGLABEL_V2 = "seller/v2/order";
		public static final String UPDATE_TAX_V2 = "seller/v2/order";
		public static final String AUTH_V2 = "v1/authenticate";
		public static final String GET_CITURS_BAL_V2 = "merchant/getAllbalance";
		public static final String AUTH_PROD_V2 = "auth";
		public static final String GET_CITURS_PAYOUT_V2 = "merchant/getbankreference/";

	}

	public static class SELLERSERVICES_PATH{
		public static final String CREATE_SELLER = "seller";
		public static final String UPDATE_SELLER = "seller";
		public static final String GET_ALL_SELLER = "seller/search?start=0&fetchSize=50&sortBy=id&sortOrder=desc&q";
		public static final String GET_SELLER_INFO = "seller/info";
		public static final String DISABLE_BRAND = "seller/brand/disable";
		public static final String BRAND_SEARCH = "seller/brand/search?q";
		public static final String ADD_BRAND = "seller/brand/create";
		public static final String ADD_CATEGORY_MANAGER = "seller/categoryManager/create";
		public static final String DISABLE_CATEGORY_MANAGER = "seller/categoryManager/disable";
		public static final String SEARCH_CATEGORY_MANAGER = "seller/categoryManager/search?q";
		public static final String ADD_SELLER_WAREHOUSE = "seller/warehouse/create";
		public static final String DISABLE_SELLER_WAREHOUSE = "seller/warehouse/disable";
		public static final String SEARCH_SELLER_WAREHOUSE = "seller/warehouse/search?q";
		public static final String GET_WAREHOUSE_BY_SELLER = "seller/warehouse";
		public static final String ADD_FINANCE_INFORMATION = "seller/financeInformation/create";
		public static final String GET_FINANCE_INFORMATION = "seller/financeInformation/search?q";
		public static final String GET_FINANCEINFO_SELLER = "seller/financeInformation/search?q";
		public static final String DISABLE_FINANCE_INFORMATION = "seller/financeInformation/disable";
		public static final String ADD_PAYMENT_CONFIGURATION = "seller/paymentConfiguration/create";
		public static final String GET_PAYMENT_CONFIGURATION = "seller/paymentConfiguration/search/?q";
		public static final String GET_PAYMENT_CONFIG_SELLER = "seller/paymentConfiguration/search/?q";
		public static final String DISABLE_PAYMENT_CONFIGURATION = "seller/paymentConfiguration/disable";
		public static final String ADD_KYC_DOCUMENT = "seller/kyc/create";
		public static final String GET_KYC_DOCUMENT = "seller/kyc/search/?q";
		public static final String GET_KYC_DOCUMENT_SELLER = "seller/kyc/search/?q";
		public static final String CREATE_LOGIN = "seller/login/create";
		public static final String ADD_ADDRESS = "seller/address/createAddresses";
		public static final String GET_SELLER_ADDRESS_WITH_TIN = "seller/1/address";
		public static final String GET_SELLER_BY_WAREHOUSE_ID = "seller/searchByWarehouseId?warehouseId=45"; 
		public static final String GET_SELLER_SETTELMENT = "seller/getSellerSettelmentInfoBySellerId";
		public static final String GET_SELLER_CONFIGURATION = "sellerConfiguration/search/bySellerId?sellerId=1&configurationCategory=ORDER_MANAGEMENT";
		public static final String GET_ALL_ADDRESS = "seller/address/search?q";
		public static final String GET_ADDRESS_BY_SELLER = "seller/address/search?q=sellerId.eq:1";
		public static final String ADD_SELLERITEMMASTER = "sellerItemMaster/bulkUpload";
		public static final String GET_SELLERITEM_BY_SKUID = "sellerItemMaster";
		public static final String GET_SELLERITEM_BY_SKUCODE = "sellerItemMaster";
		public static final String GET_ALL_SELLERITEM = "sellerItemMaster/search?q";
		public static final String GET_SELLERITEM_BY_SELLERID = "sellerItemMaster";
		public static final String GET_STYLEIDS_BY_SELLER = "sellerItemMaster";
		public static final String GET_SELLER_COMMISSION = "sellerTerms/getCommissionPercentage";

	}

	public static class BOUNTY_PATH{

		public static final String GET_ORDER_V0="bounty/portalOrder/getOrder";
		public static final String CREATE_ORDER_V2="bounty/portalOrder/v2/createOrder";
		public static final String QUEUE_ORDER_V2="bounty/portalOrder/v2/queueOrder";
		public static final String CONFIRM_ORDER_V2="bounty/portalOrder/v2/confirmOnHoldOrder";
		public static final String PUT_ORDER_ONHOLD="bounty/portalOrder/orderOnHold";
		public static final String DECLINE_ORDER_V2="bounty/portalOrder/v2/declineOrder";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
		public static final String PUSHORDERTOOMS = "bounty/portalOrder/pushOrderToOms";
		
		//Endpoint for MT APIs
		public static final String GET_ORDER_V0_MT="bounty/buyerOrder/buyer/1/getOrder";
		public static final String CREATE_ORDER_V2_MT="bounty/buyerOrder/v2/buyer/1/createOrder";
		public static final String QUEUE_ORDER_V2_MT="bounty/buyerOrder/v2/buyer/1/queueOrder";
		public static final String CONFIRM_ORDER_V2_MT="bounty/buyerOrder/v2/buyer/1/confirmOnHoldOrder";
		public static final String PUT_ORDER_ONHOLD_MT="bounty/portalOrder/orderOnHold";
		public static final String DECLINE_ORDER_V2_MT="bounty/buyerOrder/v2/buyer/1/declineOrder";
		public static final String REFRESHAPPLICATIONPROPERTIES_MT = "commons/properties/refresh";
		public static final String PUSHORDERTOOMS_MT = "bounty/portalOrder/pushOrderToOms";

	}

	public static class SILKROUTE_PATH{
		public static final String CREATE_ORDER_V2="";
		public static final String CREATE_ORDER_JABONG_V1="v1/createOrder/";
		public static final String CREATE_ORDER_JABONG_V2="/updateOrder/";
		public static final String BLOCK_INVENTORY_STAMP_WAREHOUSE="oms/orderrelease/blockInventoryAndStampWarehouse";
		public static final String PROCESS_RETURN = "";
		public static final String RESOLVE_ON_HOLD="silkroute/orderrelease/resolveReleaseOnHold";
		public static final String REPUSH_TO_OMS="silkroute/orderrelease/pushReleaseToOms";
		public static final String CANCEL_RELEASE="silkroute/orderrelease/cancelRelease";
	}

	public static class JROUTE_PATH{

	}

	public static class PPS_PATH{
		public static final String PAYNOW="v1/paynow";
		public static final String EXCHANGE = "v1/createExchangeOrder";
		public static final String PAYNOWFORM = "v1/paynowform";
		public static final String GET_PAYMENT_PLAN="v1/getPaymentPlan";
		public static final String PGRESPONSE ="v1/pg/response/test";
	}

	public static class CART_PATH{
		public static final String VIEW_CART="cart";
		public static final String GETCART="cart/default";
		public static final String OPTTRYNBUY="cart/default/tryandbuy";
		public static final String CLEAR_CART = "cart/default";
		public static final String ADD_ITEMTO_CART = "cart/default";
		public static final String APPLY_COUPON = "cart/default/applymyntcoupon";
		public static final String ADD_GIFTWRAPANDMESSAGE = "cart/default/giftwrap";
		public static final String APPLY_LOYALTYPOINTS = "cart/default/applyloyaltypoints?rt=true";
		public static final String APPLY_CASHBACK = "absolut/cart/default/applymyntcredit?rt=true";
		public static final String CART_SECURED =  "securedcart/v2/user";
	}

	public static class STYLE_PATH{
		public static final String STYLE_REINDEX="pdpservice/style/index";
		public static final String STYLE_FULL_REINDEX="pdpservice/style/fullindexrequest";
		public static final String STYLE_REINDEX_BOLT="v1/index/styles/";
	}

	public static class MYNTS_PATH{
		public static final String APPLY_MYNTS="myntra-absolut-service/absolut/cart/DEFAULT/applymynt";
	}


	public static class PNP_PATH{
		public static final String VIEW_CASHBACK_ACCOUNT="pp/cashback/balance/cb";
		public static final String CREDIT_CASHBACK = "pp/cashback/credit";
		public static final String DEBIT_CASHBACK = "pp/cashback/debit";
		public static final String GET_STYLE_DISCOUNT_INFO = "pp/discount/stylesV1?page=default";
		public static final String DELETE_STYLE_DISCOUNT_INFO = "pp/discount";
	}

	public static class LOYALTY_PATH{
		public static final String GET_LOYALTY_USER_ACCOUNT_INFO="loyaltyPoints/accountInfo";
		public static final String UPDATE_LOYALTY_POINTS="loyaltyPoints/credit";
		public static final String DEBIT_LOYALTY_POINTS="loyaltyPoints/debit";

	}

	public static class IDEA_PATH{
		public static final String GET_UIDX_DETAILS="profile/uidx";
		public static final String GET_UIDX_DETAILS_EMAIL="profile/email";
	}

	public static class SESSION_PATH{
		public static final String CREATESESSION="opt/sessions";
		public static final String GETNEWSESSION = "opt/sessions";
		public static final String UPDATESESSION = "opt/sessions";
	}

	public static class CHECKOUT_PATH{
		public static final String CREATESESSION="opt/sessions";
		public static final String GETPAYMENTPAGE = "payment";
	}

	public static class RMS_PATH{
		public static final String CREATE_RETURN="rms/return/createReturn";
		public static final String CREATE_RETURN_TNB="rms/return/createTryAndBuyReturn";
		public static final String GET_RETURN_DETAILS = "rms/return";
		public static final String RETURN_STATUS_PROCESS = "rms/return/updateReturn";
		public static final String RETURNLINE_STATUS_PROCESS = "rms/returnLine/updateReturnLine";
		public static final String RETURN_PICKUP_PROCESS = "rms/return/updatePickupStatus";
		public static final String RETURN_BULK_STATUS_PROCESS = "rms/return/bulkUpdateReturn";
		public static final String RETURN_BULK_ISSUE_REFUNDS = "rms/return/bulkIssueRefund";
		public static final String CREATE_RETURN_EXCHANGE="rms/return/createReturnForExchange";
		public static final String RECIEVE_SHIPMENT="rms/rto/receiveShipment";
		public static final String RESTOCK_ITEM = "rms/rto/restockItem";
	}

	public static class IMS_PATH{
		public static final String STORE_SELLER_WAREHOUSE="ims/inventory/storeSeller/warehouse";
		public static final String CORE_WAREHOUSE="ims/inventory/core/warehouse";
		public static final String CORE_UPDATE="ims/inventory/core/update";
		public static final String IS_VALIDSELLER_WAREHOUSE="ims/inventory/isValidSellerWarehouse/";
		public static final String ORDER_BLOCKING="ims/inventory/block/";
		public static final String INVENTORY_SEARCH="ims/inventory/search";	
		public static final String CORE_INVENTORY_SEARCH="ims/inventory/search/coreInventory";
		public static final String BULK_SYNC_INV="ims/inventory/bulkSellerInventoryUpload";
		public static final String BULK_PROC_SLA="ims/inventory/getSLAInMins";
		public static final String ALLOCATEINV="ims/inventory/allocateExtInventory";

	}

	public static class WMS_PATH{
		public static final String QADONE="wms/locationtracker/itemcheckin";
		public static final String CREATEPI="wms/pi/";
		public static final String UPDATEPI="wms/pi/";
		public static final String FINDBYIDPI="wms/pi/";
		public static final String GETPI="wms/pi/search/";
		public static final String GETRELEASE = "wms/orderRelease/search";
		public static final String GETITEM = "platform/items/search";
		public static final String ADDRO="wms/ro";
		public static final String UPDATERO="wms/ro";
		public static final String ADDROITEM="wms/ro";
		public static final String PUSHORDERWMS = "wms/orderRelease/pushOrdersToWarehouse";
		public static final String GETRO="wms/ro";
		public static final String OPENCARTON="wms/ro";
		public static final String DELETEROITEM="wms/ro/items/delete";
		public static final String ADDGATEPASS="wms/gatepass/createByOrder";
		public static final String CLOSECARTON="wms/ro";
		public static final String VAlIDATERECONCILE="wms/reconciliation/validateEntries";
		public static final String RECONCILE="wms/reconciliation/reconcile";
		public static final  String SEARCH_LOCATION_PREFIX="?q=locationType%";
		public static final  String SEARCH_LOCATION_SERVICE_URL="wms/properties/searchall";
		public static final String LOCATIONS_ZONE="wms/zones";
		public static final String LOCATIONS_SECTION="wms/sections";
		public static final String LOCATIONS_AISLE="wms/aisles/bulk/?count=1";
		public static final String LOCATIONS_BAY="wms/bays/bulk/?count=1";
		public static final String LOCATIONS_RACK="wms/racks/bulk/?count=1";
		public static final String LOCATIONS_BIN="wms/bins/bulk/?count=1";
		public static final String PREPAREFORCONSOLIDATION = "wms/consolidation";
		public static final String CHECKINANDCONSOLIDATE = "wms/consolidation/checkInAndConsolidate";
		public static final String FREECONSOLIDATIONBIN = "wms/consolidation/freeConsolidationBin";
		public static final String BULKUPDATE = "platform/items/bulkupdate";
		public static final String SORTING_BABYLON ="wms/items/lmcItemInScan";

		public static final String STOCK_TRANSFER_NOTE_PROCESSING_URL = "wms/stns";
		public static final String STOCK_TRANSFER_DOWNLOAD_DOCUMENT = "wms/download";
		public static final String STOCK_TRANSFER_LMC_CREATE="wms/stns/LMC/getOrCreateStn";
		public static final String STOCK_TRANSFER_LMC_CLOSE_CARTON="wms/stns";
		public static final String STOCK_TRANSFER_LMC_VALIDATE_ITEM="wms/stns";
		public static final String ITEM_CHECKOUT = "platform/items";

		public static final String LMC_ITEM_INSCAN = "wms/items/lmcItemInScan";
		public static final String LMC_MARK_PACKET_PICKED = "wms/consolidation/markVirtualPacketPicked";
		public static final String LMC_FLUSH_BIN = "wms/consolidation";

		public static final String CREATE_PO="po/order-indent";
		public static final String APPROVE_PO="po/";

		/** The method constructs the URL to change stn states
		 * @param stnId
		 * @return
		 */
		public static final String getUrlToChangeStateOfStockTransferNote(long stnId) {
			return STOCK_TRANSFER_NOTE_PROCESSING_URL+"/"+stnId;
		}

		/** The method contructs and returns the URL to associate SKU with STN
		 * @param stnId
		 * @return
		 */
		public static final  String getUrlToSkuStnMappingCall(long stnId) {
			return STOCK_TRANSFER_NOTE_PROCESSING_URL+"/"+stnId+"/skus";
		}

		/** The method contructs and returns the URL to dispatch the STN
		 * @param stnId
		 * @return
		 */
		public static final String getUrlToDispatch(long stnId) {
			return STOCK_TRANSFER_NOTE_PROCESSING_URL+"/dispatch";
		}
		/** he method contructs and returns the URL to associate SKUs in bulk to STN
		 * @param stnId
		 * @return
		 */
		public static String getUrlToAssociateSkusToStnInBulk(long stnId) {
			return STOCK_TRANSFER_NOTE_PROCESSING_URL+"/bulk/"+stnId;
		}

		public static final String INTERNAL_ORDER_PROCESSING_URL = "wms/ios";

		/** The method contructs and returns the URL to associate SKU with STN
		 * @param internalOrderId
		 * @return
		 */
		public static final  String getUrlToItemAndInternalOrderAssociationMappingCall(long internalOrderId) {
			return INTERNAL_ORDER_PROCESSING_URL+"/"+internalOrderId+"/items";
		}
		public static final  String getUrlToUpdateInternalOrder(long internalOrderId) {
			return INTERNAL_ORDER_PROCESSING_URL+"/"+internalOrderId;
		}

		public static final  String getUrlToSkuAndInternalOrderAssociationApi(long internalOrderId) {
			return INTERNAL_ORDER_PROCESSING_URL+"/"+internalOrderId+"/skus";
		}


		public static final  String getUrlToItemInternalAssociationApi(long internalOrderId) {
			return INTERNAL_ORDER_PROCESSING_URL+"/"+internalOrderId+"/items";
		}

		public static final  String getUrlToDeleteIoSkuAssociation(long ioSkuId) {
			return INTERNAL_ORDER_PROCESSING_URL+"/"+"skus/"+ioSkuId;
		}
		public static final  String getUrlToCheckOutOrderFromMaterialMovementsPage() {
			return "platform/items/checkout";
		}

		public static final String ORDER_ITEM_ASSOCIATION = "platform/items/checkout/";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";

	}

	public static class WORMS_PATH{
		public static final String GETRELEASE="worms/orderRelease/search";
		public static final String MARKORDERPACKED = "worms/orderRelease/markOrderPK";
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
	}



	public static class VMS_PATH{
		public static final String CREATE_SELLER="sellers";
		public static final String GETPARTYIDBYSOURCESYSTEMID="contract/partner/SELLER";

	}
	public static class ATP_PATH{
		public static final String BLOCK_INV="atpV2/inventory/blockInventory";
		public static final String ORDER_INV="atpV2/inventory/orderInventory";
		public static final String PORTAL_INV ="atp/inventory/portalInventory";
		public static final String SYNC_INV ="atp/inventory/syncInventory";
		public static final String GET_BOC ="atp/inventory";


	}

	public static class VIM_PATH{
		public static final String CREATE_VIM="vendorItemMaster";
		public static final String BULKUPLOAD_VIM="vendorItemMaster/bulkUpload";
		public static final String EDIT_VIM="vendorItemMaster";
		public static final String COMMERCIALTYPE_SKUCODE_VIM="vendorItemMaster/vendor";
		public static final String COMMERCIALTYPE="vendorItemMaster/vendor";
		public static final String SKUCODE="vendorItemMaster/vendor";
		public static final String GET_VIM_BY_VENDOR="vendorItemMaster/vendor";
		public static final String GET_VIM_BY_ID="vendorItemMaster";
		public static final String GET_VIM_BY_STYLEIDS="vendorItemMaster/vendor";
	}

	public static class RDS_PATH{
		public static final String SPLITPI="rds/inventorydemandinfo/split";
	}

	public static class VENDOR_PATH{
		public static final String FILTER_POS="pos/filters";
		public static final String  KYC_CREATE="partner/kyc/create";
	}

	public static class TAXMASTER_PATH{
		public static final String GET_TAX_GST="customerGST/getTax";
		public static final String GET_TAX_VAT ="customerVat/getTax";
		public static final String VAT_TO_RECOVER = "taxmaster/customerVat/getVatToRecover";
		public static final String GET_ALLTAX = "customerVat/getAllTax";
		public static final String GET_ALLTAX_GST = "customerGST/getAllTax";
		public static final String GETTAXONITEM = "taxmaster/oms/getTaxOnItem";
		public static final String GETBULKTAXONITEM = "taxmaster/oms/getBulkGovtTaxInfo";
		public static final String GET_SERVICE_GST_TAX="serviceGST/getTax";
		public static final String GET_BULK_TAX="customerGST/bulk/getBulkTax";
		public static final String GET_TAX_CESS="cess/getTax";
		public static final String GET_ALLTAX_CESS="cess/getAllTax";
		public static final String GET_BULKTAX_CESS="cess/bulk/getBulkTax";
	}


	public static class NOTIFICATIONS_PATH{
		public static final String PUSH_NOTIFICATIONS="platform/notification/renderTemplate/render/pushMessage";
	}


	public static class ARTIE_PATH{
		public static final String GET_CATEGORY_ALL="/category/getAll";
		public static final String CREATE_CATEGORY="/category";
		public static final String GET_DROPBYCATEGORYID="/drop";
		public static final String GET_DROPBYDROPID="/drop";
		public static final String CREATE_DROPFORCATEGORYID="/drop";
		public static final String UPDATE_DROPBYDROPID="/drop";
		public static final String CLOSE_DROPBYDROPID="/drop/close";
		public static final String GET_METADATABYCATEGORYID="/metadata";
		public static final String GET_ALLRECIPESBYDROPID="/recipe";
		public static final String GET_RECIPEDETAILSBYRECIPEID="/recipe";
		public static final String ADD_RECIPEUNDERDROP="/recipe";
		public static final String BULK_UPLOAD_RECIPES="/recipe";
		public static final String UPDATE_RECIPEBYRECIPEID="/recipe";
		public static final String REMOVE_STYLESFROMRECIPE="/recipe/removeProducts";
		public static final String ARCHIVE_RECIPE="/recipe/archive";
		public static final String PUBLISH_RECIPE="/recipe/publish";
		public static final String CLOSE_RECIPE="/recipe/close";
		public static final String ADD_DESIGN_UNDER_RECIPE="/design";
		public static final String GET_ALL_DESIGNSFORRECIPEID="/design";
		public static final String GET_DESIGNDETAILSBYDESIGNID="/design";
		public static final String UPDATE_DESIGNDETAILSBYDESIGNID="/design";
		public static final String ADD_COMMENTSFORDESIGNID="/design/addComment";
		public static final String APPROVE_DESIGN="/design/approve";
		public static final String REJECT_DESIGN="/design/reject";
		public static final String GET_APPROVEDDESIGNSFORDESIGNID="/design";
	}

	public static class VENDOR_TERMS_PATH{
		public static final String GET_TAXONOMY_ADDITIONAL_CLASSIFICATION="taxonomy/AdditionalClassification";
		public static final String GET_TAXONOMY_AGREEMENTTYPE="taxonomy/AgreementType";
		public static final String GET_CATALOG_MASTERCATEGORY="catalog/masterCategory";
		public static final String GET_TAXONOMY_GENDER="taxonomy/Gender";
		public static final String CREATE_TERMS="terms/create";
		public static final String UPDATE_TERMS="terms/update";
		public static final String DELETE_TERMS="terms/delete";
		public static final String SEARCH_TERMS="terms/search";
		public static final String VENDOR="vendor";
		public static final String CONTRACT_ACCOUNT="contract/account";
		public static final String CONTRACT="contract";
	}

	public static class PARTNER_CONNECT_PATH{
		public static final String LIST_BRAND_NAME_WITH_BRAND_CODE="brand";
		public static final String FETCH_REPORT_DATA="report/fetchData";
		public static final String FETCH_ALL_FILTERS_FOR_REPORT_BRAND_COMBO="report/dimensionValues";
		public static final String LIST_COMPAIGNS_OF_BRAND="campaign";
		public static final String LIST_BANNERS_OF_A_COMPAIGN="campaign";
		public static final String APPROVE_REJECT_BANNER="campaign/channel/banner";
		public static final String GET_COMPAIGN_DETAILS="campaign";
	}

	public static class SECURITY_PATH{
		public static final String SECURITY_SERVICE="platform/security/users/authenticate/V2/";
	}

	public static class EVENTS_DISCOUNT_PATH{
		public static final String VENDOR="vendor";
	}

	public static class SLACK_UPLOAD_PATH{
		public static final String UPLOAD="files.upload";
	}

	public static class PARTNERPORTALSERVICE_PATH{
		public static final String GET_SKUS_BY_INVOICENUMBER="invoice/getSKUsByInvoiceNumber";
		public static final String GET_INVOICE_DETAILS_BY_INVOICENUMBER="invoice/getInvoiceDetailsByInvoiceNumber";
		public static final String GET_POS_BY_VENDORID="pos/getPOsByVendorId";
		public static final String GET_REJECTED_ITEMS_INVOICENUMBER="invoice/getRejectedItemsByInvoiceNumber";
		public static final String PO_SEARCH="pos/search";
		public static final String GET_PO_DETAILS_BY_POID="pos/getPODetailsByPOId";
		public static final String GET_SKUS_BY_POID="pos/getSKUsByPOId";
	}

	public static class INWARDASSISTSERVICE_PATH{
		public static final String GET_CAPACITY_DISTRIBUTION="capacity/distribution";
		public static final String GET_AVAILABLE_DATES="booking/availableDates/?location_id=14&warehouse_id=36&buid=1&poSlotId=3";
		public static final String GET_BRAND_TYPE="pos/brandType/getAll";
		public static final String DOWNLOAD_CAPACITY="capacity/getAll?startDate=01-11-2016&endDate=05-11-2016";
		public static final String SYNC_ES="sync/es";

	}

	public static class AMAZON_PATH{
		public static final String GET_TSHIRTS_LIST="/s/ref=lp_1968123031_ex_n_1?rh=n%3A1571271031%2Cn%3A%211571272031%2Cn%3A1968024031%2Cn%3A1968120031&bbn=1968120031&ie=UTF8&qid=1490265340&dataVersion=v0.2&cid=08e6b9c8bdfc91895ce634a035f3d00febd36433&format=json";
	}
	public static class SPS_PATH{
		public static final String SYNC_ADD_TRANSACTION="sync/syncAddTransaction";
		public static final String SYNC_RELEASE_FUND="sync/syncReleaseFund";
		public static final String SYNC_REFUND="sync/syncRefundStore/ppsIds";
	}

	public static class CMS_CATALOG{
		public static final String PRODUCT = "product";
	}

	public static class TMS_PATH{
		public static final String REFRESHAPPLICATIONPROPERTIES = "commons/properties/refresh";
	}

	public static class RabbitMQ{
		public static final String DEFINITION="api/definitions";
	}


	public static class PACKMAN_PATH{
		public static final String CREATE_SELLER_PACKET="packman/sellerpacket";
		public static final String GET_SELLER_PACKET_BY_ITEMBARCODE="packman/sellerpacket/getPacketByItemBarcode";
		public static final String MARK_ITEM_PREMIUM_PACKED="packman/sellerpacketitem/markItemPremiumPacked";
		public static final String MARK_ITEM_QC_PASS="packman/sellerpacketitem/markItemsQCPass";
		public static final String MARK_ITEM_QC_FAIL="packman/sellerpacketitem/markItemsQCFail";
		public static final String MARK_READY_TO_DISPATCH="packman/sellerpacket/markReadyToDispatch";
		public static final String CANCEL_ITEMS_BY_SELLER="packman/sellerpacket/cancelItemsBySeller";
	}

	public static class FLIPKART_PATH{
		public static final String TEST_ORDERS="sandbox/test_orders";
	}

	public static class SLACK_PATH{
		public static final String SCMQA_DOCKINS_ISSUES="T024FPRGW/B8YUPAKPZ/DMiALK5D94JVumTMP5ZB3Hnu";
		public static final String MJINT_DOCKINS_ISSUES="T024FPRGW/B94P262TE/XytTbbrT2dS9JbFFPZZjk5MF";
		public static final String STAGE_DOCKINS_ISSUES="T024FPRGW/B97A6FKUJ/MTxtykSLWAsqamX2hBnCd9me";

	}

}