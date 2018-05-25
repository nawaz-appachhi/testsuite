package com.myntra.apiTests.portalservices.absolutService;

import java.util.ArrayList;
import java.util.List;

public enum AbsolutNodes {
	
	//Status Nodes
	STATUS_CODE("status.statusCode"),
	STATUS_MESSAGE("status.statusMessage"),
	STATUS_TYPE("status.statusType"),
	STATUS_TOTAL_COUNT("status.totalCount"),
	
	//Config Status Nodes
	META_CODE("meta.code"),
	META_ACTIVEVARIANT("meta.activeVariant"),
	SHIPPING_CHARGES_AMOUNT("$.data[\"shipping.charges.amount\"]"),
	SHIPPING_CHARGES_CARTLIMIT("$.data[\"shipping.charges.cartlimit\"]"),
	SHIPPING_CHARGES_RETURNABUSER("data.shipping.charges.returnabuser"),
	SHIPPING_CHARGES_TNB("data.shipping.charges.tryNbuy"),
	
	//Seller Config Nodes
	CONFIG_ATTRIBUTES("data.configurationAttributes"),
	//StyleData Nodes
	STYLE_IS_FRAGILE("cmsData.articleType.isFragile"),
	STYLE_IS_TNBENABLED("cmsData.articleType.isTryAndBuyEnabled"),
	STYLE_IS_HAZMAT("cmsData.articleType.isHazmat"),
	STYLE_IS_JWELLERY("cmsData.articleType.isJewellery"),
	STYLE_IS_RETURNABLE("cmsData.articleType.isReturnable"),
	STYLE_IS_EXCHANGEABLE("cmsData.articleType.isExchangeable"),
	STYLE_PICKUPENABLED("cmsData.articleType.pickupEnabled"),
	STYLE_IS_LARGE("cmsData.articleType.isLarge"),
	STYLE_CATEGORY("cmsData.styleCategory"),
	
	
	//Cart Nodes
	CART_CREATION_DATE("data.cartCreateDate"),
	CART_IS_NEW("data.cartIsNew"),
	CART_LOGIN("data.login"),
    CART_CONTEXT("data.context"),
    
    //--coupon information
    CART_APPLIED_COUPONS("data.appliedCoupons"),
    CART_APPLIED_COUPONS_COUPONID("data.appliedCoupons.coupon"),
    CART_APPLIED_COUPONS_APP_MSG("data.appliedCoupons.couponApplicabilityMessage"),
    CART_APPLIED_COUPONS_APPSTATE("data.appliedCoupons.applicationState"),
    CART_APPLIED_COUPONS_APPSTATE_STATE("data.appliedCoupons.applicationState.state"),
    CART_APPLIED_COUPONS_APPSTATE_MSG("data.appliedCoupons.applicationState.message"),
    CART_APPLIED_COUPONS_APPLICABLE("data.appliedCoupons.applicable"),
    
    
    CART_LAST_MODIFIED("data.lastmodified"),
    CART_GIFT_CHARGE("data.giftCharge"),
    CART_SHIPPING_CHARGE("data.shippingCharge"),
    CART_VAT_CHARGE("data.vatCharge"),
    
    //--gift related data
    CART_GIFT_MSG("data.giftMessage"),
    CART_GIFT_MSG_SENDER("data.giftMessage.sender"),
    CART_GIFT_MSG_INFO("data.giftMessage.message"),
    CART_GIFT_MSG_RECIPIENT("data.giftMessage.recipient"),
    
    
    CART_LAST_SAVED_TOTAL_CART_PRICE("data.lastSavedTotalCartPrice"),
    CART_USE_MYNT_CASH("data.useMyntCash"),
    CART_IS_GIFT_ORDER("data.isGiftOrder"),
	CART_TOTAL_COUNT("data.totalCartCount"),
	CART_TOTAL_DISPLAY_COUNT("data.totalDisplayCartCount"),
	
	//--cart items
	CART_ITEM_ENTRIES("data.cartItemEntries"),
	
	CART_ITEM_ENTRIES_TITLE("data.cartItemEntries.title"),
	
	CART_ITEM_ENTRIES_SELSIZE("data.cartItemEntries.selectedSize"),
	CART_ITEM_ENTRIES_SELSIZE_SIZE("data.cartItemEntries.selectedSize.size"),
	CART_ITEM_ENTRIES_SELSIZE_UNIFIED("data.cartItemEntries.selectedSize.unifiedSize"),
	CART_ITEM_ENTRIES_SELSIZE_SKUID("data.cartItemEntries.selectedSize.skuId"),
	CART_ITEM_ENTRIES_SELSIZE_AVAIL("data.cartItemEntries.selectedSize.available"),
	CART_ITEM_ENTRIES_SELSIZE_AVAIL_QTY("data.cartItemEntries.selectedSize.availableQuantity"),
	CART_ITEM_ENTRIES_SELSIZE_ITEM_AVAIL_DET_MAP("data.cartItemEntries.selectedSize.itemAvailabilityDetailMap"),
    
	// Virtual Bundle related
	CART_ITEM_ENTRIES_SELSIZE_VIRTUAL_SKUID("data.cartItemEntries.virtualSkuId"),

	CART_ITEM_LEVEL_MYNTS_ENABLED("data.cartItemEntries.myntsEnabled"),
	CART_ITEM_ENTRIES_COUPON_APPLICABLE("data.cartItemEntries.couponApplicable"),
	CART_ITEM_CASHBACK_AVAILABLE("data.cashbackApplicable"),
	CART_ITEM_WALLET_AVAILABLE("data.walletApplicable"),
	CART_ITEM_LOYALTY_POINTS_APPLICABLE("data.loyaltyPointsApplicable"),
	CART_ITEM_GIFTWRAP_APPLICABLE("data.giftWrapApplicable"),
	CART_ITEM_GIFTCARD_APPLICABLE("data.giftCardApplicable"),
	CART_VALUE_SHIPPING_APPLICABLE("data.valueShippingApplicable"),
	CART_ITEM_ENTRIES_SELSIZE_AVAIL_COUNT("data.cartItemEntries.0.itemAvailabilityDetailMap.availableCount"),
	CART_ITEM_ENTRIES_IS_EXCHANGEABLE("data.cartItemEntries.isExchangeable"),
	CART_ITEM_ENTRIES_AVAIL_SIZES("data.cartItemEntries.availableSizes"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_SIZE("data.cartItemEntries.availableSizes.size"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_UNIFIED_SIZE("data.cartItemEntries.availableSizes.unifiedSize"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_SKUID("data.cartItemEntries.availableSizes.skuId"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_AVAIL("data.cartItemEntries.availableSizes.available"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_AVAIL_QTY("data.cartItemEntries.availableSizes.availableQuantity"),
	CART_ITEM_ENTRIES_AVAIL_SIZES_ITEM_AVAIL_DET_MAP("data.cartItemEntries.availableSizes.itemAvailabilityDetailMap"),
	
	CART_ITEM_ENTRIES_ITEM_IMG("data.cartItemEntries.itemImage"),
           
	CART_ITEM_ENTRIES_ITEM_ID("data.cartItemEntries.itemId"),
	CART_ITEM_ENTRIES_UNIT_MRP("data.cartItemEntries.unitMrp"),
	CART_ITEM_ENTRIES_VAT_RATE("data.cartItemEntries.vatRate"),
	CART_ITEM_ENTRIES_QTY("data.cartItemEntries.quantity"),
	CART_ITEM_ENTRIES_UNIT_ADDL_CHARGES("data.cartItemEntries.unitAdditionalCharge"),
	CART_ITEM_ENTRIES_UNIT_DISC("data.cartItemEntries.unitDiscount"),
	CART_ITEM_ENTRIES_UNIT_BAG_DISC("data.cartItemEntries.unitBagDiscount"),
	CART_ITEM_ENTRIES_LOYALTY_POINTS_AWARDED("data.cartItemEntries.loyaltyPointsAwarded"),
	CART_ITEM_ENTRIES_LOYALTY_POINTS_AWARD_FACTOR("data.cartItemEntries.loyaltyPointsAwardFactor"),
	CART_ITEM_ENTRIES_LOYALTY_POINTS_CONV_FACTOR("data.cartItemEntries.loyaltyPointsConversionFactor"),
	CART_ITEM_ENTRIES_LOYALTY_POINTS_USED("data.cartItemEntries.loyaltyPointsUsed"),
	CART_ITEM_ENTRIES_PROD_TYPE_ID("data.cartItemEntries.productTypeId"),
	CART_ITEM_ENTRIES_BRAND("data.cartItemEntries.brand"),
	CART_ITEM_ENTRIES_STYLE_TYPE("data.cartItemEntries.styleType"),
	CART_ITEM_ENTRIES_PROD_STYLE_TYPE("data.cartItemEntries.productStyleType"),
	CART_ITEM_ENTRIES_GENDER("data.cartItemEntries.gender"),
	CART_ITEM_ENTRIES_ARTICLE_TYPE_ID("data.cartItemEntries.articleTypeId"),
	CART_ITEM_ENTRIES_PACK_TYPE("data.cartItemEntries.packagingType"),
	CART_ITEM_ENTRIES_COMBO_ID("data.cartItemEntries.comboId"),
	CART_ITEM_ENTRIES_DISC_RULE_ID("data.cartItemEntries.discountRuleId"),
	CART_ITEM_ENTRIES_DISC_RULE_HISTORY_ID("data.cartItemEntries.discountRuleHistoryId"),
	CART_ITEM_ENTRIES_DISC_QTY("data.cartItemEntries.discountQuantity"),
	CART_ITEM_ENTRIES_IS_DISC_RULE_APPLIED("data.cartItemEntries.isDiscountRuleApplied"),
	CART_ITEM_ENTRIES_IS_DISC_PRODUCT("data.cartItemEntries.isDiscountedProduct"),
	CART_ITEM_ENTRIES_IS_RETURNABLE("data.cartItemEntries.isReturnable"),
	CART_ITEM_ENTRIES_UNIT_MYNT_CASH_USG("data.cartItemEntries.unitMyntCashUsage"),
	CART_ITEM_ENTRIES_UNIT_COUPON_DISC("data.cartItemEntries.unitCouponDiscount"),
	CART_ITEM_ENTRIES_SKUID("data.cartItemEntries.skuId"),
	CART_ITEM_ENTRIES_STYLEID("data.cartItemEntries.styleId"),
	CART_ITEM_ENTRIES_LAND_PAGE_URL("data.cartItemEntries.landingPageUrl"),
	CART_ITEM_ENTRIES_COUP_APPLICABLE("data.cartItemEntries.couponApplicable"),
	CART_ITEM_ENTRIES_ITEM_ADD_TIME("data.cartItemEntries.itemAddTime"),
	CART_ITEM_ENTRIES_DO_CUSTOMIZE("data.cartItemEntries.doCustomize"),
	CART_ITEM_ENTRIES_SELLER_ID("data.cartItemEntries.sellerId"),
	CART_ITEM_ENTRIES_IS_PUB_ITEM("data.cartItemEntries.isPublicItem"),
	CART_ITEM_ENTRIES_POS_VOTES("data.cartItemEntries.positiveVotes"),
	CART_ITEM_ENTRIES_NEG_VOTES("data.cartItemEntries.negativeVotes"),
	CART_ITEM_ENTRIES_TOT_PRICE("data.cartItemEntries.totalPrice"),
	CART_ITEM_ENTRIES_TOT_MYNT_CASH_USAGE("data.cartItemEntries.totalMyntCashUsage"),
	CART_ITEM_ENTRIES_FREE_ITEM("data.cartItemEntries.freeItem"),
	CART_ITEM_ENTRIES_TOT_MRP("data.cartItemEntries.totalMrp"),
	CART_ITEM_ENTRIES_TOT_DISC("data.cartItemEntries.totalDiscount"),
	CART_ITEM_ENTRIES_TOT_MRP_DECI("data.cartItemEntries.totalMrpDecimal"),
	CART_ITEM_ENTRIES_TOT_DISC_DECI("data.cartItemEntries.totalDiscountDecimal"),
	CART_ITEM_ENTRIES_TOT_MYNT_CASH_USG_DECI("data.cartItemEntries.totalMyntCashUsageDecimal"),
	CART_ITEM_ENTRIES_TOT_COUPON_DISC("data.cartItemEntries.totalCouponDiscount"),
	CART_ITEM_ENTRIES_TOT_COUPON_DISC_DECI("data.cartItemEntries.totalCouponDiscountDecimal"),
	CART_ITEM_ENTRIES_TOT_PRICE_DECI("data.cartItemEntries.totalPriceDecimal"),
	CART_ITEM_ENTRIES_TOT_EFFE_PRICE("data.cartItemEntries.totalEffectivePrice"),
	CART_ITEM_ENTRIES_TOT_EFFE_PRICE_DECI("data.cartItemEntries.totalEffectivePriceDecimal"),
	CART_ITEM_ENTRIES_TOT_EFFE_PRICE_WITHOUT_MYNT_CASH("data.cartItemEntries.totalEffectivePriceWithoutMyntCash"),
	CART_ITEM_ENTRIES_TOT_EFFE_PRICE_WITHOUT_MYNT_CASH_DECI("data.cartItemEntries.totalEffectivePriceWithoutMyntCashDecimal"),
	CART_ITEM_ENTRIES_TOT_BAG_DISC("data.cartItemEntries.totalBagDiscount"),
	CART_ITEM_ENTRIES_TOT_BAG_DISC_DECI("data.cartItemEntries.totalBagDiscountDecimal"),
	CART_ITEM_ENTRIES_SUB_TOTAL("data.cartItemEntries.subTotal"),
	CART_ITEM_ENTRIES_SUB_TOTAL_DECI("data.cartItemEntries.subTotalDecimal"),
	CART_ITEM_ENTRIES_IS_CUSTOMIZABLE("data.cartItemEntries.isCustomizable"),
	CART_ITEM_ENTRIES_IS_CUSTOMIZED("data.cartItemEntries.isCustomized"),
	
	CART_SET_DISP_DATA("data.setDisplayData"),
	CART_TOTAL_MYNT_CASH_USAGE("data.totalMyntCashUsage"),
	CART_LOYALTY_POINTS_USED("data.loyaltyPointsUsed"),
	CART_LOYALTY_POINTS_AWARDED("data.loyaltyPointsAwarded"),
	CART_LOYALTY_POINTS_CONVERSION_FACTOR("data.loyaltyPointsConversionFactor"),
	CART_USER_ENTERED_LOYALTY_POINTS("data.userEnteredLoyaltyPoints"),
	CART_USE_LOYALTY_POINTS("data.useLoyaltyPoints"),
	CART_TOTAL_PRICE("data.totalPrice"),
	CART_READY_FOR_CHECKOUT("data.readyForCheckout"),
	CART_TOTAL_MRP("data.totalMrp"),
	CART_SUB_TOTAL_PRICE("data.subTotalPrice"),
	CART_TOTAL_PRICE_WITHOUT_MYNT_CASH("data.totalPriceWithoutMyntCash"),
	CART_TOTAL_DISCOUNT("data.totalDiscount"),
	CART_MRP_DECIMAL("data.totalMrpDecimal"),
	CART_TOTAL_DISCOUNT_DECIMAL("data.totalDiscountDecimal"),
	CART_TOTAL_MYNT_CASH_USAGE_DECIMAL("data.totalMyntCashUsageDecimal"),
	CART_TOTAL_COUPON_DISCOUNT("data.totalCouponDiscount"),
	CART_TOTAL_COUPON_DISCOUNT_DECIMAL("data.totalCouponDiscountDecimal"),
	CART_TOTAL_PRICE_DECIMAL("data.totalPriceDecimal"),
	CART_BAG_DISCOUNT("data.bagDiscount"),
	CART_BAG_DISCOUNT_DECIMAL("data.bagDiscountDecimal"),
	CART_TOTAL_PRICE_WITHOUT_MYNT_CASH_DECIMAL("data.totalPriceWithoutMyntCashDecimal"),
	CART_SUB_TOTAL_PRICE_DECIMAL("data.subTotalPriceDecimal"),
	CART_LOYALTY_POINTS_USED_CASH_EQUIPMENT("data.loyaltyPointsUsedCashEqualent"),
	
	//--tryAndBuy related data
	CART_ITEM_ENTRIES_TNB_ENABLED("data.cartItemEntries.tryAndBuyEnabled"),
	CART_ITEM_ENTRIES_TNB_OPTED("data.cartItemEntries.tryAndBuyOpted"),
	
	//MyntCredit Nodes
	STORE_CREDIT_BAL("data.storeCreditBalance"),
    EARNED_CREDIT_BAL("data.earnedCreditBalance"),
    TOT_BAL("data.balance"),
    USE_AMT("data.useAmount"),
	
	//Loyalty Related Nodes
    LOYALTY_MAXUSAGE_PER("data.loyaltyMaxUsage"),
    
    
	//Address Nodes
	ADDRESS_ID("data.id"),
	ADDRESS_USER_ID("data.userId"),
	ADDRESS_DEFAULT_ADDR("data.defaultAddress"),
	ADDRESS_USER_NAME("data.name"),
	ADDRESS_ADDR("data.address"),
	ADDRESS_CITY("data.city"),
	ADDRESS_STATECODE("data.stateCode"),
	ADDRESS_STATENAME("data.stateName"),
	ADDRESS_COUNTRY_CODE("data.countryCode"),
	ADDRESS_COUNTRY_NAME("data.countryName"),
	ADDRESS_PIN_CODE("data.pincode"),
	ADDRESS_EMAIL("data.email"),
	ADDRESS_MOBILE("data.mobile"),
	ADDRESS_LOCALITY("data.locality"),
	
	ADDRESS_COD_AVAILABILITY_ENTRY("data.codAvailabilityEntry"),
	ADDRESS_COD_AVAILABILITY_SERVICEABILITY("data.codAvailabilityEntry.serviceability"),
	ADDRESS_COD_AVAILABILITY_ERROR_CODE("data.codAvailabilityEntry.errorCode"),
	ADDRESS_COD_AVAILABILITY_ERROR_MSG("data.codAvailabilityEntry.errorMessage"),
	
	ADDRESS_ADDR_SERVICEABILITY_ENTRY("data.addressServiceabilityEntry"),
	ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY("data.addressServiceabilityEntry.codServiceability"),
	ADDRESS_ADDR_SERVICEABILITY_COD_NON_SERVICEABILITY("data.addressServiceabilityEntry.nonCODServiceability"),
	ADDRESS_ADDR_SERVICEABILITY_EXPRESS_SERVICEABILITIES("data.addressServiceabilityEntry.expressServiceabilities"),
	
	
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES("data.addressServiceabilityEntry.addressItemEntries"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_DELPROMISETIME("data.addressServiceabilityEntry.addressItemEntries.deliveryPromiseTime"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBENABLED("data.addressServiceabilityEntry.addressItemEntries.tryAndBuyEnabled"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBOPTED("data.addressServiceabilityEntry.addressItemEntries.tryandBuyOpted"),
	ADDRESS_ADDR_SERVICEABILITY_ADDITEMENTRIES_TNBSERVICEABLE("data.addressServiceabilityEntry.addressItemEntries.tryAndBuyServiceable"),
	
	//Serviceability Nodes
	SERVICEABILITY_PINCODE("data.pincode"),
	
	SERVICEABILITY_COD_SERVICEABILITY("data.codServiceability"),
	SERVICEABILITY_COD_AVAILABILITY_ENTRY("data.codAvailabilityEntry"),
	SERVICEABILITY_COD_AVAILABILITY_SERVICEABILITY("data.codAvailabilityEntry.serviceability"),
	SERVICEABILITY_COD_AVAILABILITY_ERROR_CODE("data.codAvailabilityEntry.errorCode"),
	SERVICEABILITY_COD_AVAILABILITY_ERROR_MSG("data.codAvailabilityEntry.errorMessage"),	
	SERVICEABILITY_COD_AVAILABILITY_CASH_ONLY("data.codAvailabilityEntry.cashOnly"),	
	
	SERVICEABILITY_COD_NON_COD_SERVICEABILITY("data.nonCODServiceability"),
	SERVICEABILITY_CARD_ON_DELIVERY_SERVICEABLE("data.cardOnDeliveryServiceable"),
	
	SERVICEABILITY_EXPRESS_SERVICEABILITIES("data.expressServiceabilities"),
	SERVICEABILITY_EXPRESS_DELIVERY_SERVICEABILITY_TYPE("data.expressServiceabilities.serviceabilityType"),
	SERVICEABILITY_EXPRESS_DELIVERY_SERVICEABILITY("data.expressServiceabilities.serviceability"),
	
	SERVICEABILITY_SDD_SERVICEABILITIES("data.sddServiceabilities"),
	SERVICEABILITY_SDD_SERVICEABILITY_TYPE("data.sddServiceabilities.serviceabilityType"),
	SERVICEABILITY_SDD_SERVICEABILITY("data.sddServiceabilities.serviceability"),
	
	SERVICEABILITY_VALUE_SHIPPING_SERVICEABILITIES("data.valueShippingServicabilities"),
	SERVICEABILITY_VALUE_SHIPPING_SERVICEABILITY_TYPE("data.valueShippingServicabilities.serviceabilityType"),
	SERVICEABILITY_VALUE_SHIPPING_SERVICEABILITY("data.valueShippingServicabilities.serviceability"),
	
	SERVICEABILITY_TRY_AND_BUY_SERVICEABLE("data.tryAndBuyServiceable"),
	
	SERVICEABILITY_CART_ITEM_SKU_ID("data.addressItemEntries.skuId"),
	SERVICEABILITY_CART_ITEM_TNB_ENABLED("data.addressItemEntries.tryAndBuyEnabled"),
	SERVICEABILITY_CART_ITEM_TNB_OPTED("data.addressItemEntries.tryandBuyOpted"),
	SERVICEABILITY_CART_ITEM_TNB_SERVICEABLE("data.addressItemEntries.tryAndBuyServiceable");
	
	
	private String nodePath;
	
	private AbsolutNodes(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return nodePath;
	}

	@Override
	public String toString() {
		return getNodePath();
	}
	
	public static List<String> getStatusNodes()
	{
		List<String> statusNodes = new ArrayList<String>();
		statusNodes.add(STATUS_CODE.getNodePath());
		statusNodes.add(STATUS_MESSAGE.getNodePath());
		statusNodes.add(STATUS_TYPE.getNodePath());
		statusNodes.add(STATUS_TOTAL_COUNT.getNodePath());
		
		return statusNodes;		
	
	}
	
	
	public static List<String> getCouponApplicabilityStatusNodes()
	{
		List<String> couponApplicabilitySatusNodes = new ArrayList<String>();
		couponApplicabilitySatusNodes.add(CART_APPLIED_COUPONS_COUPONID.toString());
		couponApplicabilitySatusNodes.add(CART_APPLIED_COUPONS_APPSTATE.toString());
		couponApplicabilitySatusNodes.add(CART_APPLIED_COUPONS_APPSTATE_STATE.toString());
		couponApplicabilitySatusNodes.add(CART_APPLIED_COUPONS_APPSTATE_MSG.toString());
		
		return couponApplicabilitySatusNodes;		
	
	}
	public static List<String> getGiftWrapMessageNodes(){
		
		List<String> giftNodes = new ArrayList<String>();
		giftNodes.add(CART_GIFT_MSG.toString());
		giftNodes.add(CART_GIFT_MSG_SENDER.toString());
		giftNodes.add(CART_GIFT_MSG_INFO.toString());
		giftNodes.add(CART_GIFT_MSG_RECIPIENT.toString());
		
		return giftNodes;
	}
	

	public static List<String> getAddressNodes(){
		List<String> addressNodes = new ArrayList<>();
		addressNodes.add(AbsolutNodes.ADDRESS_ID.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_USER_ID.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_DEFAULT_ADDR.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_USER_NAME.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_ADDR.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_CITY.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_STATECODE.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_STATENAME.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_COUNTRY_CODE.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_COUNTRY_NAME.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_PIN_CODE.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_EMAIL.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_MOBILE.toString());
		addressNodes.add(AbsolutNodes.ADDRESS_LOCALITY.toString());
		return addressNodes;		
	}
	
	public static List<String> getCODAvailabilityEntryNodes(){
		List<String> codAvailabilityEntryNodes = new ArrayList<>();
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ENTRY.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_SERVICEABILITY.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ERROR_CODE.toString());
		codAvailabilityEntryNodes.add(ADDRESS_COD_AVAILABILITY_ERROR_MSG.toString());
		
		return codAvailabilityEntryNodes;
		
	}
	
	public static List<String> getAddresServiceabilityEntryNodes(){
		List<String> addrServiceability = new ArrayList<>();
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_ENTRY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_COD_SERVICEABILITY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_COD_NON_SERVICEABILITY.toString());
		addrServiceability.add(ADDRESS_ADDR_SERVICEABILITY_EXPRESS_SERVICEABILITIES.toString());
		
		return addrServiceability;
	}
	
	public static List<String> getCompleteAddressNodes(){
		List<String> completeAddressList = new ArrayList<>();
		completeAddressList.addAll(getAddressNodes());
		completeAddressList.addAll(getCODAvailabilityEntryNodes());
		completeAddressList.addAll(getAddresServiceabilityEntryNodes());
		
		return completeAddressList;
	}
	

	public static List<String> getMyntCreditNodes(){
		List<String> myntCreditNodes = new ArrayList<String>();
		myntCreditNodes.add(STORE_CREDIT_BAL.getNodePath());
		myntCreditNodes.add(EARNED_CREDIT_BAL.getNodePath());
		myntCreditNodes.add(TOT_BAL.getNodePath());
		myntCreditNodes.add(USE_AMT.getNodePath());
		
		return myntCreditNodes;
	}
	
	
}
