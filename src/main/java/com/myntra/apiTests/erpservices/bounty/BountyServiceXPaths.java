package com.myntra.apiTests.erpservices.bounty;

/**
 * @author shankara.c
 *
 */
public enum BountyServiceXPaths 
{
	
	BOUNTYSERVICE_STATUS_NODE_IS_EXISTS("boolean(//status/node())"),
	
	BOUNTYSERVICE_STATUS_CODE_NODE_IS_EXISTS("boolean(//status/statusCode/node())"),
	BOUNTYSERVICE_STATUS_CODE_NODE_VALUE("//status/statusCode/text()"),
	
	BOUNTYSERVICE_STATUS_MSG_NODE_IS_EXISTS("boolean(//status/statusMessage/node())"),
	BOUNTYSERVICE_STATUS_MSG_NODE_VALUE("//status/statusMessage/text()"),
	
	BOUNTYSERVICE_STATUS_TYPE_NODE_IS_EXISTS("boolean(//status/statusType/node())"),
	BOUNTYSERVICE_STATUS_TYPE_NODE_VALUE("//status/statusType/text()"),
	
	BOUNTYSERVICE_STATUS_TOTAL_COUNT_NODE_IS_EXISTS("boolean(//status/totalCount/node())"),
	BOUNTYSERVICE_STATUS_TOTAL_COUNT_NODE_VALUE("//status/totalCount/text())"),
	
	BOUNTYSERVICE_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	BOUNTYSERVICE_DATA_NODE_VALUE("//data/text()"),
	
	BOUNTYSERVICE_CREATEORDER_ORDER_NODE_IS_EXISTS("boolean(//data/order/node())"),
	BOUNTYSERVICE_CREATEORDER_ORDER_NODE_VALUE("//data/order/text()"),
	
	BOUNTYSERVICE_CREATEORDER_ORDER_ID_NODE_IS_EXISTS("boolean(//data/order/id/node())"),
	BOUNTYSERVICE_CREATEORDER_ORDER_ID_NODE_VALUE("//data/order/id/text()"),
	
	BOUNTYSERVICE_CREATEORDER_ORDER_FINALAMOUNT_NODE_IS_EXISTS("boolean(//data/order/finalAmount/node())"),
	BOUNTYSERVICE_CREATEORDER_ORDER_FINALAMOUNT_NODE_VALUE("//data/order/finalAmount/text()"),
	
	BOUNTYSERVICE_ORDERRESPONSE_DATA_NODESET("/orderResponse"),
	
	//for WMSTest.java 
	//status node and nodes in it
	
	STATUS_NODE_IS_EXISTS("boolean(//status/node())"),
	
	STATUS_CODE_NODE_IS_EXISTS("boolean(//status/statusCode/node())"),
	STATUS_CODE_NODE_VALUE("//status/statusCode/text()"),
	
	STATUS_MSG_NODE_IS_EXISTS("boolean(//status/statusMessage/node())"),
	STATUS_MSG_NODE_VALUE("//status/statusMessage/text()"),
	
	STATUS_TYPE_NODE_IS_EXISTS("boolean(//status/statusType/node())"),
	STATUS_TYPE_NODE_VALUE("//status/statusType/text()"),
	
	STATUS_TOTAL_COUNT_NODE_IS_EXISTS("boolean(//status/totalCount/node())"),
	STATUS_TOTAL_COUNT_NODE_VALUE("//status/totalCount/text()"),
	
	//IOS 
	
	
	IOS_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	IOS_INTERNAL_ORDER_NODE_IS_EXISTS("boolean(//data/internalOrder/node())"),
	
	IOS_CREATED_BY_NODE_IS_EXISTS("boolean(//createdBy/node())"),
	IOS_CREATED_BY_NODE_VALUE("//createdBy/text()"),
	
	IOS_CREATED_ON_NODE_IS_EXISTS("boolean(//createdOn/node())"),
	IOS_CREATED_ON_NODE_VALUE("//createdOn/text()"),
	
	IOS_ID_NODE_IS_EXISTS("boolean(//id/node())"),
	IOS_ID_NODE_VALUE("//id/text()"),
	
	IOS_APPROVER_NODE_IS_EXISTS("boolean(//approver/node())"),
	IOS_APPROVER_NODE_VALUE("//approver/text()"),
	
	IOS_BARCODE_NODE_IS_EXISTS("boolean(//barcode/node())"),
	IOS_BARCODE_NODE_VALUE("//barcode/text()"),
	
	IOS_DESCRIPTION_NODE_IS_EXISTS("boolean(//description/node())"),
	IOS_DESCRIPTION_NODE_VALUE("//description/text()"),
	
	IOS_ORDER_STATUS_NODE_IS_EXISTS("boolean(//orderStatus/node())"),
	IOS_ORDER_STATUS_NODE_VALUE("//orderStatus/text()"),
	
	IOS_ORDER_TYPE_NODE_IS_EXISTS("boolean(//orderType/node())"),
	IOS_ORDER_TYPE_NODE_VALUE("//orderType/text()"),
	
	IOS_INTERNAL_ORDER_TOTAL_COUNT_NODE_IS_EXISTS("boolean(//internalOrder/totalCount/node())"),
	IOS_INTERNAL_ORDER_TOTAL_COUNT_NODE_VALUE("//internalOrder/totalCount/text()"),
	
	//invoices
	INVOICES_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	INVOICES_INVOICE_NODE_IS_EXISTS("boolean(//data/invoice/node())"),
	

	INVOICES_CREATEDBY_NODE_IS_EXISTS("boolean(//data/invoice/createdBy/node())"),
	INVOICES_CREATEDBY_NODE_VALUE("//data/invoice/createdBy/text()"),
	
	INVOICES_ID_NODE_IS_EXISTS("boolean(//data/invoice/id/node())"),
	INVOICES_ID_NODE_VALUE("//data/invoice/id/text()"),
	
	//items
	
	ITEMS_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	ITEMS_ITEM_NODE_IS_EXISTS("boolean(//data/item/node())"),
	

	ITEMS_CREATEDBY_NODE_IS_EXISTS("boolean(//data/item/createdBy/node())"),
	ITEMS_CREATEDBY_NODE_VALUE("//data/item/createdBy/text()"),
	
	ITEMS_ID_NODE_IS_EXISTS("boolean(//data/item/id/node())"),
	ITEMS_ID_NODE_VALUE("//data/item/id/text()"),
	
	//SKUs
	
	SKUS_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	SKUS_SKU_NODE_IS_EXISTS("boolean(//data/sku/node())"),
	

	SKUS_ID_NODE_IS_EXISTS("boolean(//data/sku/id/node())"),
	SKUS_ID_NODE_VALUE("//data/sku/id/text()"),
	
	SKUS_CODE_NODE_IS_EXISTS("boolean(//data/sku/code/node())"),
	SKUS_CODE_NODE_VALUE("//data/sku/code/text()"),
	
	//rejected items
	
	RI_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	RI_REJECTED_ITEM_NODE_IS_EXISTS("boolean(//data/rejectedItem/node())"),
	
	RI_ITEM_NODE_IS_EXISTS("boolean(//data/rejectedItem/item/node())"),

	

	RI_CREATEDBY_NODE_IS_EXISTS("boolean(//data/rejectedItem/createdBy/node())"),
	RI_CREATEDBY_NODE_VALUE("//data/rejectedItem/createdBy/text()"),
	
	RI_ID_NODE_IS_EXISTS("boolean(//data/rejectedItem/item/id/node())"),
	RI_ID_NODE_VALUE("//data/rejectedItem/item/id/text()"),
	
	//Return orders
	

	RO_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	RO_RO_NODE_IS_EXISTS("boolean(//data/ro/node())"),
	
	RO_CREATEDBY_NODE_IS_EXISTS("boolean(//data/ro/createdBy/node())"),
	RO_CREATEDBY_NODE_VALUE("//data/ro/createdBy/text()"),
	
	RO_ID_NODE_IS_EXISTS("boolean(//data/ro/id/node())"),
	RO_ID_NODE_VALUE("//data/ro/id/text()"),
	
	//RGP
	RGP_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	RGP_RGP_NODE_IS_EXISTS("boolean(//data/rgp/node())"),
	
	RGP_CREATEDBY_NODE_IS_EXISTS("boolean(//data/rgp/createdBy/node())"),
	RGP_CREATEDBY_NODE_VALUE("//data/rgp/createdBy/text()"),
	
	RGP_BARCODE_NODE_IS_EXISTS("boolean(//data/rgp/barcode/node())"),
	RGP_BARCODE_NODE_VALUE("//data/rgp/barcode/text()"),
	
	//STN
	
	STN_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	STN_STN_NODE_IS_EXISTS("boolean(//data/stn/node())"),
	
	STN_CREATEDBY_NODE_IS_EXISTS("boolean(//data/stn/createdBy/node())"),
	STN_CREATEDBY_NODE_VALUE("//data/stn/createdBy/text()"),
	
	STN_BARCODE_NODE_IS_EXISTS("boolean(//data/stn/barcode/node())"),
	STN_BARCODE_NODE_VALUE("//data/stn/barcode/text()"),
	
	//STNItems
	
	STNITEMS_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	STNITEMS_STNITEM_NODE_IS_EXISTS("boolean(//data/stnItem/node())"),
	
	STNITEMS_STN_NODE_IS_EXISTS("boolean(//data/stnItem/stn/node())"),
	
	STNITEMS_ITEMID_NODE_IS_EXISTS("boolean(//data/stnItem/itemId/node())"),
	STNITEMS_ITEMID_NODE_VALUE("//data/stnItem/itemId/text()"),
	
	STNITEMS_BARCODE_NODE_IS_EXISTS("boolean(//data/stnItem/stn/barcode/node())"),
	STNITEMS_BARCODE_NODE_VALUE("//data/stnItem/stn/barcode/text()"),
	
	//StationsBin
	
	SB_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	SB_STATIONSBIN_NODE_IS_EXISTS("boolean(//data/stationsBin/node())"),
	
	SB_SECTION_NODE_IS_EXISTS("boolean(//data/stationsBin/section/node())"),
	
	SB_BIN_BARCODE_NODE_IS_EXISTS("boolean(//data/stationsBin/binBarcode/node())"),
	SB_BIN_BARCODE_NODE_VALUE("//data/stationsBin/binBarcode/text()"),
	
	SB_NAME_NODE_IS_EXISTS("boolean(//data/stationsBin/section/name/node())"),
	SB_NAME_NODE_VALUE("//data/stationsBin/section/name/text()"),
	
	//Purchase orders
	
	PO_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	PO_PO_NODE_IS_EXISTS("boolean(//data/po/node())"),
		
	PO_CREATEDBY_NODE_IS_EXISTS("boolean(//data/po/createdBy/node())"),
	PO_CREATEDBY_NODE_VALUE("//data/po/createdBy/text()"),
	
	PO_ID_NODE_IS_EXISTS("boolean(//data/po/id/node())"),
	PO_ID_NODE_VALUE("//data/po/id/text()"),
	
	//Purchase intents
	
	PI_DATA_NODE_IS_EXISTS("boolean(//data/node())"),
	
	PI_PI_NODE_IS_EXISTS("boolean(//data/pi/node())"),
		
	PI_CREATEDBY_NODE_IS_EXISTS("boolean(//data/pi/createdBy/node())"),
	PI_CREATEDBY_NODE_VALUE("//data/pi/createdBy/text()"),
	
	PI_ID_NODE_IS_EXISTS("boolean(//data/pi/id/node())"),
	PI_ID_NODE_VALUE("//data/pi/id/text()"),
	
	//lot
	LOT_ID_NODE_IS_EXISTS("boolean(//data/pi/id/node())"),
	LOT_ID_VALUE("//data/lot/id/text()");
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*<orderResponse><status><statusCode>1003</statusCode><statusMessage>Order(s) retrieved successfully</statusMessage><statusType>SUCCESS</statusType><totalCount>1</totalCount></status><data><order><createdBy>santwana.samantray@myntra.com</createdBy><createdOn>2015-02-09T14:55:45+05:30</createdOn><id>70001305</id><billingAddress><billingAddress></billingAddress><billingCity></billingCity><billingCounty></billingCounty><billingEmail>santwana.samantray@myntra.com</billingEmail><billingFirstName></billingFirstName><billingLastName></billingLastName><billingMobile>9393184532</billingMobile><billingState></billingState><billingZipCode></billingZipCode></billingAddress><cartDiscount>0.0</cartDiscount><cashBackCouponCode></cashBackCouponCode><cashRedeemed>0.0</cashRedeemed><cashbackOffered>0.0</cashbackOffered><channel>web</channel><codCharge>0.0</codCharge><couponCode></couponCode><couponDiscount>0.0</couponDiscount><customerName>Ranjan Choudhury</customerName><discount>0.0</discount><emiCharge>0.0</emiCharge><finalAmount>799.0</finalAmount><giftCardAmount>0.0</giftCardAmount><giftCharge>0.0</giftCharge><giftOrder>false</giftOrder><login>santwana.samantray@myntra.com</login><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsAwarded>0.0</loyaltyPointsAwarded><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><mrpTotal>799.0</mrpTotal><notes></notes><onHold>false</onHold><orderProcessingFlowMode>OMS</orderProcessingFlowMode><orderReleases><orderRelease><createdBy>santwana.samantray@myntra.com</createdBy><id>70001305</id><address>Shri krishna dham Sri ram nagar colony 1-57&amp;#x2f;518 2nd floor block A&amp;#xa;Kondapur&amp;#xa;K.v.rangareddy </address><cartDiscount>0.0</cartDiscount><cashRedeemed>0.0</cashRedeemed><cashbackOffered>0.0</cashbackOffered><city>K.v.rangareddy</city><codCharge>0.0</codCharge><codPaymentStatus>pending</codPaymentStatus><country>India</country><couponDiscount>0.0</couponDiscount><courierCode></courierCode><discount>0.0</discount><email>santwana.samantray&amp;#x40;myntra.com</email><emiCharge>0.0</emiCharge><finalAmount>799.0</finalAmount><giftCardAmount>0.0</giftCardAmount><giftCharge>0.0</giftCharge><locality>Kothaguda  &amp;#x28;K.V.Rangareddy&amp;#x29;</locality><login>santwana.samantray@myntra.com</login><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsAwarded>0.0</loyaltyPointsAwarded><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><mobile>9393184532</mobile><mrpTotal>799.0</mrpTotal><onHold>false</onHold><orderId>70001305</orderId><orderLines><orderLine><createdBy>santwana.samantray@myntra.com</createdBy><id>70001876</id><cartDiscount>0.0</cartDiscount><cashRedeemed>0.0</cashRedeemed><cashbackOffered>0.0</cashbackOffered><couponDiscount>0.0</couponDiscount><customizedMessage></customizedMessage><discount>0.0</discount><discountRuleId>0</discountRuleId><discountRuleRevId>0</discountRuleRevId><discountedProduct>false</discountedProduct><discountedQuantity>0</discountedQuantity><finalAmount>799.0</finalAmount><giftCardAmount>0.0</giftCardAmount><govtTaxAmount>0.0</govtTaxAmount><govtTaxRate>5.5</govtTaxRate><isCustomizable>false</isCustomizable><isDiscountedProduct>false</isDiscountedProduct><isReturnableProduct>true</isReturnableProduct><loyaltyConversionFactor>0.0</loyaltyConversionFactor><loyaltyPointsAwarded>0.0</loyaltyPointsAwarded><loyaltyPointsCredit>0.0</loyaltyPointsCredit><loyaltyPointsUsed>0.0</loyaltyPointsUsed><optionId>1134515</optionId><orderId>70001305</orderId><orderReleaseId>70001305</orderReleaseId><packagingStatus>NOT_PACKAGED</packagingStatus><packagingType>NORMAL</packagingType><pgDiscount>0.0</pgDiscount><priceMismatchRefund>0.0</priceMismatchRefund><quantity>1</quantity><returnableProduct>true</returnableProduct><sellerId>1</sellerId><skuId>1131505</skuId><status>A</status><styleId>339878</styleId><supplyType>JUST_IN_TIME</supplyType><taxAmount>0.0</taxAmount><taxRate>5.5</taxRate><unitPrice>799.0</unitPrice></orderLine></orderLines><paymentMethod>cod</paymentMethod><pgDiscount>0.0</pgDiscount><queuedOn>2015-02-09T14:55:45+05:30</queuedOn><receiverName>Ranjan Choudhury </receiverName><refunded>false</refunded><shippingCharge>0.0</shippingCharge><shippingMethod>NORMAL</shippingMethod><state>AP</state><status>Q</status><storeId>1</storeId><taxAmount>0.0</taxAmount><trackingNo></trackingNo><userContactNo>8147835807</userContactNo><zipcode>500084</zipcode></orderRelease></orderReleases><orderType>on</orderType><paymentMethod>cod</paymentMethod><paymentMethodDisplay>Cash On Delivery</paymentMethodDisplay><pgDiscount>0.0</pgDiscount><queuedOn>2015-02-09T14:55:45+05:30</queuedOn><shippingCharge>0.0</shippingCharge><storeId>1</storeId><taxAmount>0.0</taxAmount><userContactNo>8147835807</userContactNo></order></data></orderResponse>*/
	private String xmlPath;
	
	private BountyServiceXPaths(String xmlPath){
		this.xmlPath = xmlPath;
	}
	
	public String getXmlPath() {
		return xmlPath;
	}
	
}
