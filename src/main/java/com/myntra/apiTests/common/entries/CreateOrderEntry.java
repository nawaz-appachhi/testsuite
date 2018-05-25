package com.myntra.apiTests.common.entries;


import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abhijit.pati on 27/05/17.
 */
public class CreateOrderEntry {

	private ShippingMethod shipmentMethod;
   	private PaymentInstruments paymentInstruments;
    private List<SkuEntry> skuEntries;
    private Boolean isGiftWrapEnabled;
    private ShipmentType shipmentType;
    private String userName;
    private String password;
    private Long addressId;
    private Long pincode;
	private String couponCode;
    private Boolean isEGC;
    private Boolean isMockOrder;

	public CreateOrderEntry() {
		
	}
	
    public CreateOrderEntry(ShippingMethod shipmentMethod, PaymentInstruments paymentInstruments,
                            List<SkuEntry> skuEntries, Boolean isGiftWrapEnabled,
                            ShipmentType shipmentType, String userName, String password,
                            Long addressId, Long pincode, String couponCode, Boolean isEGC, Boolean isTryNBuy, Boolean isMockOrder) {

		this.shipmentMethod = shipmentMethod;
		this.paymentInstruments = paymentInstruments;
		this.skuEntries = skuEntries;
		this.isGiftWrapEnabled = isGiftWrapEnabled;
		this.shipmentType = shipmentType;
		this.userName = userName;
		this.password = password;
		this.pincode = pincode;
		this.addressId = addressId;
		this.couponCode = couponCode;
		this.isEGC = isEGC;
		this.isMockOrder = isMockOrder;
	}

    public CreateOrderEntry(String userName, String password, Long pincode, SkuEntry[] skuEntries2) {
		// TODO Auto-generated constructor stub
    	this.userName = userName;
    	this.password = password;
    	this.pincode = pincode;
    	
    	ArrayList<SkuEntry> skuEntries = new ArrayList<>();
		for(SkuEntry skuEntry:skuEntries2){
			skuEntries.add(skuEntry);
		}
		this.skuEntries = skuEntries;
	}

	public ShippingMethod getShipmentMethod() {
        return shipmentMethod;
    }

    public void setShipmentMethod(ShippingMethod shipmentMethod) {
        this.shipmentMethod = shipmentMethod;
    }

    public PaymentInstruments getPaymentInstruments() {
        return paymentInstruments;
    }

    public void setPaymentInstruments(PaymentInstruments paymentInstruments) {
        this.paymentInstruments = paymentInstruments;
    }

    public List<SkuEntry> getSkuEntries() {
        return skuEntries;
    }

    public void setSkuEntries(List<SkuEntry> skuEntries) {
        this.skuEntries = skuEntries;
    }

    public Boolean isGiftWrapEnabled() {
        return isGiftWrapEnabled;
    }

    public void setGiftWrapEnabled(Boolean giftWrapEnabled) {
        isGiftWrapEnabled = giftWrapEnabled;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
    	this.shipmentType = shipmentType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Boolean isEGC() {
        return isEGC;
    }

    public void setEGC(Boolean EGC) {
        isEGC = EGC;
    }

	public Boolean isMockOrder() {
		return isMockOrder;
	}

	public void setMockOrder(Boolean isMockOrder) {
		this.isMockOrder = isMockOrder;
	}
	
    public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	
	@Override
	public String toString() {
		return "CreateOrderEntry [shipmentMethod=" + shipmentMethod + ", paymentInstruments=" + paymentInstruments
				+ ", skuEntries=" + skuEntries + ", isGiftWrapEnabled=" + isGiftWrapEnabled + ", shipmentType="
				+ shipmentType + ", userName=" + userName + ", password=" + password + ", addressId=" + addressId
				+ ", pincode=" + pincode + ", couponCode=" + couponCode + ", isEGC=" + isEGC + ", isMockOrder="
				+ isMockOrder + "]";
	}



	
	
}
