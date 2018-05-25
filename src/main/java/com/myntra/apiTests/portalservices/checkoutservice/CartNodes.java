package com.myntra.apiTests.portalservices.checkoutservice;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shankara.c
 *
 */
public enum CartNodes {
	
	CART_CREATION_DATE("data.cartCreateDate"),
	CART_IS_NEW("data.cartIsNew"),
	CART_LOGIN("data.login"),
    CART_CONTEXT("data.context"),
    
    // coupon information
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
    
    //gift related data
    CART_GIFT_MSG("data.giftMessage"),
    CART_GIFT_MSG_SENDER("data.giftMessage.sender"),
    CART_GIFT_MSG_INFO("data.giftMessage.message"),
    CART_GIFT_MSG_RECIPIENT("data.giftMessage.recipient"),
    
    
    
    CART_LAST_SAVED_TOTAL_CART_PRICE("data.lastSavedTotalCartPrice"),
    CART_USE_MYNT_CASH("data.useMyntCash"),
    CART_IS_GIFT_ORDER("data.isGiftOrder"),
	CART_TOTAL_COUNT("data.totalCartCount"),
	
	
	// cart items
	CART_ITEM_ENTRIES("data.cartItemEntries"),
	
	CART_ITEM_ENTRIES_TITLE("data.cartItemEntries.title"),
	
	CART_ITEM_ENTRIES_SELSIZE("data.cartItemEntries.selectedSize"),
	CART_ITEM_ENTRIES_SELSIZE_SIZE("data.cartItemEntries.selectedSize.size"),
	CART_ITEM_ENTRIES_SELSIZE_UNIFIED("data.cartItemEntries.selectedSize.unifiedSize"),
	CART_ITEM_ENTRIES_SELSIZE_SKUID("data.cartItemEntries.selectedSize.skuId"),
	CART_ITEM_ENTRIES_SELSIZE_AVAIL("data.cartItemEntries.selectedSize.available"),
	CART_ITEM_ENTRIES_SELSIZE_AVAIL_QTY("data.cartItemEntries.selectedSize.availableQuantity"),
	CART_ITEM_ENTRIES_SELSIZE_ITEM_AVAIL_DET_MAP("data.cartItemEntries.selectedSize.itemAvailabilityDetailMap"),
    
	//shrinkhala
	CART_ITEM_ENTRIES_COUPON_APPLICABLE("data.cartItemEntries.couponApplicable"),
	CART_ITEM_CASHBACK_AVAILABLE("data.cashbackApplicable"),
	CART_ITEM_WALLET_AVAILABLE("data.walletApplicable"),
	CART_ITEM_LOYALTY_POINTS_APPLICABLE("data.loyaltyPointsApplicable"),
	CART_ITEM_GIFTWRAP_APPLICABLE("data.giftWrapApplicable"),
	CART_ITEM_GIFTCARD_APPLICABLE("data.giftCardApplicable"),
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
	
	//tryAndBuy related data
	CART_ITEM_ENTRIES_TNB_ENABLED("data.cartItemEntries.tryAndBuyEnabled"),
	CART_ITEM_ENTRIES_TNB_OPTED("data.cartItemEntries.tryAndBuyOpted");
	
	
	private String nodePath;
	
	private CartNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	public String toString() {
		return nodePath;
	};
	
	public static List<String> getGiftWrapMessageNodes(){
		
		List<String> giftNodes = new ArrayList<String>();
		giftNodes.add(CART_GIFT_MSG.toString());
		giftNodes.add(CART_GIFT_MSG_SENDER.toString());
		giftNodes.add(CART_GIFT_MSG_INFO.toString());
		giftNodes.add(CART_GIFT_MSG_RECIPIENT.toString());
		
		return giftNodes;
	}
	
	
		
}
