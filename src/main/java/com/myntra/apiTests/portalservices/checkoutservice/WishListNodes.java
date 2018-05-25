package com.myntra.apiTests.portalservices.checkoutservice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shankara.c
 *
 */
public enum WishListNodes {
	WISHLIST_TOTAL_COUNT("data.totalWishlistCount"),
	
	WISHLIST_CREATE_DATE("data.wishListCreateDate"),
	WISHLIST_ID("data.wishListID"),
	WISHLIST_LOGIN("data.login"),
	WISHLIST_LAST_MODIFIED("data.lastmodified"),
	WISHLIST_ITEM_ENTRIES("data.wishListItemEntries"),
	WISHLIST_ITEM_ENTRIES_TITLE("data.wishListItemEntries.title"),
	
	// WISHLIST SELECTED SIZE INFO
	WISHLIST_ITEM_ENTRIES_SELSIZE("data.wishListItemEntries.selectedSize"),
	WISHLIST_ITEM_ENTRIES_SELSIZE_SIZE("data.wishListItemEntries.selectedSize.size"),
	WISHLIST_ITEM_ENTRIES_SELSIZE_UNIFIED_SIZE("data.wishListItemEntries.selectedSize.unifiedSize"),
	WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID("data.wishListItemEntries.selectedSize.skuId"),
	WISHLIST_ITEM_ENTRIES_SELSIZE_AVAIL("data.wishListItemEntries.selectedSize.available"),
	WISHLIST_ITEM_ENTRIES_SELSIZE_AVAIL_QTY("data.wishListItemEntries.selectedSize.availableQuantity"),
	
	WISHLIST_ITEM_ENTRIES_ITEM_IMG("data.wishListItemEntries.itemImage"),
	
	// WISHLIST 
	WISHLIST_ITEM_ENTRIES_AVAILSIZES("data.wishListItemEntries.availableSizes"),
	WISHLIST_ITEM_ENTRIES_AVAILSIZES_SIZE("data.wishListItemEntries.availableSizes.size"),
	WISHLIST_ITEM_ENTRIES_AVAILSIZES_UNIFIED_SIZE("data.wishListItemEntries.availableSizes.unifiedSize"),
	WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID("data.wishListItemEntries.availableSizes.skuId"),
	WISHLIST_ITEM_ENTRIES_AVAILSIZES_AVAIL("data.wishListItemEntries.availableSizes.available"),
	WISHLIST_ITEM_ENTRIES_AVAILSIZES_AVAIL_QTY("data.wishListItemEntries.availableSizes.availableQuantity"),
	      
	WISHLIST_ITEM_ENTRIES_ITEM_ID("data.wishListItemEntries.itemId"),
	WISHLIST_ITEM_ENTRIES_UNIT_MRP("data.wishListItemEntries.unitMrp"),
	WISHLIST_ITEM_ENTRIES_VAT_RATE("data.wishListItemEntries.vatRate"),
	WISHLIST_ITEM_ENTRIES_QTY("data.wishListItemEntries.quantity"),
	WISHLIST_ITEM_ENTRIES_ITEM_ADD_TIME("data.wishListItemEntries.itemAddTime"),
	WISHLIST_ITEM_ENTRIES_UNIT_ADDL_CHARGE("data.wishListItemEntries.unitAdditionalCharge"),
	WISHLIST_ITEM_ENTRIES_UNIT_DISC("data.wishListItemEntries.unitDiscount"),
	WISHLIST_ITEM_ENTRIES_UNIT_DISC_QTY("data.wishListItemEntries.discountQuantity"),
	WISHLIST_ITEM_ENTRIES_IS_DISC_RULE_APPLIED("data.wishListItemEntries.isDiscountRuleApplied"),
	WISHLIST_ITEM_ENTRIES_IS_DISC_PROD("data.wishListItemEntries.isDiscountedProduct"),
	WISHLIST_ITEM_ENTRIES_IS_RETURNABLE("data.wishListItemEntries.isReturnable"),
	WISHLIST_ITEM_ENTRIES_UNIT_CASH_BACK("data.wishListItemEntries.unitCashback"),
	WISHLIST_ITEM_ENTRIES_UNIT_COUPON_DISC("data.wishListItemEntries.unitCouponDiscount"),
	WISHLIST_ITEM_ENTRIES_SKUID("data.wishListItemEntries.skuId"),
	WISHLIST_ITEM_ENTRIES_STYLEID("data.wishListItemEntries.styleId"),
	WISHLIST_ITEM_ENTRIES_LAND_PAGE_URL("data.wishListItemEntries.landingPageUrl"),
	WISHLIST_ITEM_ENTRIES_TOT_PRICE("data.wishListItemEntries.totalPrice"),
	WISHLIST_ITEM_ENTRIES_SIZE_AUTO_SELECTED("data.wishListItemEntries.sizeAutoSelected"),
	WISHLIST_ITEM_ENTRIES_TOT_MRP("data.wishListItemEntries.totalMrp"),
	WISHLIST_ITEM_ENTRIES_TOT_DISC("data.wishListItemEntries.totalDiscount"),
	WISHLIST_ITEM_ENTRIES_TOT_COUPON_DISC("data.wishListItemEntries.totalCouponDiscount"),
	WISHLIST_ITEM_ENTRIES_TOT_CASH_BACK("data.wishListItemEntries.totalCashback");

	private String nodePath;
	
	private WishListNodes(String nodePath){
		this.nodePath = nodePath;
	}
	
	public String getNodePath() {
		return nodePath;
	}
	
	@Override
	public String toString() {
		return nodePath;
	}
	
	public static List<String> getWishListNodes(){
		
		List<String> wishListNodes = new ArrayList<String>();
		wishListNodes.add(WISHLIST_CREATE_DATE.toString());
		wishListNodes.add(WISHLIST_ID.toString());
		wishListNodes.add(WISHLIST_LOGIN.toString());
		wishListNodes.add(WISHLIST_LAST_MODIFIED.toString());
		wishListNodes.add(WISHLIST_TOTAL_COUNT.toString());
		
		return wishListNodes;
	}
	
	public static List<String> getWishListItemEntriesNodes(){
	
		List<String> wishListItemEntriesNodes = new ArrayList<String>();
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TITLE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE_SIZE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE_UNIFIED_SIZE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE_SKUID.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE_AVAIL.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SELSIZE_AVAIL_QTY.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_ITEM_IMG.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES_SIZE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES_UNIFIED_SIZE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES_SKUID.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES_AVAIL.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_AVAILSIZES_AVAIL_QTY.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_ITEM_ID.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_MRP.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_VAT_RATE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_QTY.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_ITEM_ADD_TIME.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_ADDL_CHARGE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_DISC.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_DISC_QTY.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_IS_DISC_RULE_APPLIED.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_IS_DISC_PROD.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_IS_RETURNABLE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_CASH_BACK.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_UNIT_COUPON_DISC.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SKUID.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_STYLEID.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_LAND_PAGE_URL.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TOT_PRICE.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_SIZE_AUTO_SELECTED.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TOT_MRP.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TOT_DISC.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TOT_COUPON_DISC.toString());
		wishListItemEntriesNodes.add(WISHLIST_ITEM_ENTRIES_TOT_CASH_BACK.toString());
		
		return wishListItemEntriesNodes;
	}
	
}
