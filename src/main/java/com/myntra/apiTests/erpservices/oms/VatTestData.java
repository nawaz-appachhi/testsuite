/**
 * 
 */
package com.myntra.apiTests.erpservices.oms;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The class contains the information about the data which is used to place the order
 * @author puneet.khanna1@myntra.com
 *
 */
public class VatTestData {
	private static Logger log = LoggerFactory.getLogger(VatTestData.class);
	
	// testDetails is a unique string for each VatTestData object
	String testDetails;
	String username;
	String password;
	String destinationAddress;
	String couponId;
	boolean isCashbackOrder;
	boolean ifLoyalityPointsUsed;
	boolean isGiftWrapOpted;
	String giftcard;
	List<SkuTriplet> skuTripletList;
	int cashBackRequiredToPlaceTheCompleteOrder=0;
	
	public VatTestData(String testDetails, String username, String password, String destinationAddress, String couponId,
			boolean isCashbackOrder, boolean ifLoyalityPointsUsed, boolean isGiftWrapOpted, String giftcard,
			List<SkuTriplet> skuTripletList) {
		super();
		this.testDetails = testDetails;
		this.username = username;
		this.password = password;
		this.destinationAddress = destinationAddress;
		this.couponId = couponId;
		this.isCashbackOrder = isCashbackOrder;
		this.ifLoyalityPointsUsed = ifLoyalityPointsUsed;
		this.isGiftWrapOpted = isGiftWrapOpted;
		this.giftcard = giftcard;
		this.skuTripletList = skuTripletList;
		constructCashBackRequiredToPlaceTheCompleteOrder();
	}
	public int getCashBackRequiredToPlaceTheCompleteOrder() {
		return cashBackRequiredToPlaceTheCompleteOrder;
	}
	
	void constructCashBackRequiredToPlaceTheCompleteOrder(){
		if (this.isCashbackOrder()) {
			for (SkuTriplet skuTriplet : this.skuTripletList) {
		
				int amountRequiredForthisSku = (int) (TaxationUtil.getMrpBySku(skuTriplet.getSkuCode()) * skuTriplet.getQuanity());
				
				cashBackRequiredToPlaceTheCompleteOrder = cashBackRequiredToPlaceTheCompleteOrder
						+ ((int) Math.round(14.5 * (amountRequiredForthisSku)));
		}
			log.info("The cashBack required to place this order is "+cashBackRequiredToPlaceTheCompleteOrder);
		}
	}
	public String getTestDetails() {
		return testDetails;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getDestinationAddress() {
		return destinationAddress;
	}
	public String getCouponId() {
		return couponId;
	}
	public boolean isCashbackOrder() {
		return isCashbackOrder;
	}
	public boolean isIfLoyalityPointsUsed() {
		return ifLoyalityPointsUsed;
	}
	public boolean isGiftWrapOpted() {
		return isGiftWrapOpted;
	}
	public String getGiftcard() {
		return giftcard;
	}
	public List<SkuTriplet> getSkuTripletList() {
		return skuTripletList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testDetails == null) ? 0 : testDetails.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VatTestData other = (VatTestData) obj;
		if (testDetails == null) {
			if (other.testDetails != null)
				return false;
		} else if (!testDetails.equals(other.testDetails))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "VatTestData [testDetails=" + testDetails + ", username=" + username + ", password=" + password
				+ ", destinationAddress=" + destinationAddress + ", couponId=" + couponId + ", isCashbackOrder="
				+ isCashbackOrder + ", ifLoyalityPointsUsed=" + ifLoyalityPointsUsed + ", isGiftWrapOpted="
				+ isGiftWrapOpted + ", giftcard=" + giftcard + ", skuTripletList=" + skuTripletList
				+ ", cashBackRequiredToPlaceTheCompleteOrder=" + cashBackRequiredToPlaceTheCompleteOrder + "]";
	}
	
	
}
