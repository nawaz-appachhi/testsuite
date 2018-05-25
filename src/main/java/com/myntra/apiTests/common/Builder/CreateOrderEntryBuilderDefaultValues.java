package com.myntra.apiTests.common.Builder;

import com.myntra.apiTests.common.createOrder.GenerateRandomData;
import com.myntra.apiTests.common.entries.PaymentInstruments;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.apiTests.erpservices.oms.OMSServiceHelper;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderEntryBuilderDefaultValues {
	
	String isMockOrderTrue = System.getenv("isMockOrder");
	
	public ShippingMethod defaultShipmentMethod(){

			return ShippingMethod.NORMAL;
	}

	public PaymentInstruments defaultPaymentInstruments() {
		// TODO Auto-generated method stub
		return new PaymentInstrumentsBuilder()
				.setCOD(true)
				.setOnline(false)
				.setUseLoyaltyPoints(false)
				.setUseWallet(false).build();
	}

	public Boolean defaultIsGiftWrapEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public ShipmentType defaultShipmentType() {
		// TODO Auto-generated method stub
		return ShipmentType.DL;
	}

	public String[] getRandomUserNameAndPassword() {
		// TODO Auto-generated method stub
		return new GenerateRandomData().getRandomUserNameAndPassword();
	}

	public Long getRandomPincode() {
		// TODO Auto-generated method stub
		return new GenerateRandomData().getRandomPincode();
	}

	public Long configAddressDataIfNotAvailable(String userName, String password, Long pincode) throws IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		OMSServiceHelper omsServiceHelper = new OMSServiceHelper();
		String customerAddressId = omsServiceHelper.getCustomerAddressForUser(userName, pincode);
		Long addressId = null;
		if (customerAddressId == null) {
			customerAddressId = omsServiceHelper.addCustomerAddressForNewUser(userName,pincode);
			addressId = Long.parseLong(customerAddressId);
		} else {
			addressId = Long.parseLong(customerAddressId);
		}
		

		return addressId;
	}

	public String defaultCouponCode() {
		// TODO Auto-generated method stub
		return "";
	}

	public Boolean defaultEGCValue() {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean defaultMockOrderValue() {
		// TODO Auto-generated method stub
		if(isMockOrderTrue!=null && isMockOrderTrue.equalsIgnoreCase("true")){
			return true;
		}
		
		return false;
	}

	public List<SkuEntry> defaultSkuEntries() {
		// TODO Auto-generated method stub
	    List<SkuEntry> skuEntriesNew = new ArrayList<>();
	    SkuEntry skuEntryTemp = new SkuEntry();
	    
	    skuEntryTemp = new SkuEntryBuilder().setSkuId(getRandomSkuId())
				.setQuantity(1)
				.setSkuType("ON_HAND")
				.build();
		skuEntriesNew.add(skuEntryTemp);
		
		return skuEntriesNew;
	}

	private String getRandomSkuId() {
		// TODO Auto-generated method stub
		return ""+new GenerateRandomData().getRandomSkuId();
	}

}
