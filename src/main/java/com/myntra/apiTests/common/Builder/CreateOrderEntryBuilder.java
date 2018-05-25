package com.myntra.apiTests.common.Builder;

import com.myntra.apiTests.common.createOrder.GenerateRandomData;
import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.entries.PaymentInstruments;
import com.myntra.apiTests.common.entries.SkuEntry;
import com.myntra.lms.client.status.ShipmentType;
import com.myntra.lms.client.status.ShippingMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateOrderEntryBuilder {
	
    private CreateOrderEntry createOrderEntry; 
    
    private CreateOrderEntryBuilderDefaultValues createOrderEntryBuilderDefaultValues = new CreateOrderEntryBuilderDefaultValues();
    
    public CreateOrderEntryBuilder(){
    	createOrderEntry = new CreateOrderEntry();
    }
    
    public CreateOrderEntryBuilder(String userName,String password, Long pincode,SkuEntry[] skuEntries){
    	createOrderEntry = new CreateOrderEntry(userName,password,pincode,skuEntries);
    }
    
	public CreateOrderEntryBuilder shipmentMethod(ShippingMethod shipmentMethod) {
		createOrderEntry.setShipmentMethod(shipmentMethod);
		return this;
	}
	
	public CreateOrderEntryBuilder paymentInstruments(PaymentInstruments paymentInstruments) {
		createOrderEntry.setPaymentInstruments(paymentInstruments);
		return this;
	}
	
	public CreateOrderEntryBuilder skuEntries(List<SkuEntry> skuEntries) {
		createOrderEntry.setSkuEntries(skuEntries);
		return this;
	}
	
	
	public CreateOrderEntryBuilder skuEntries(SkuEntry[] skuEntries2) {
		// TODO Auto-generated method stub
		ArrayList<SkuEntry> skuEntries = new ArrayList<>();
		for(SkuEntry skuEntry:skuEntries2){
			skuEntries.add(skuEntry);
		}
		createOrderEntry.setSkuEntries(skuEntries);
		
		return this;
	}
	
	public CreateOrderEntryBuilder skuEntries(String skuId) {
		ArrayList<SkuEntry> skuEntries = new ArrayList<>();
		skuEntries.add(new SkuEntry(skuId, 1));
		createOrderEntry.setSkuEntries(skuEntries);
		return this;
	}
	
	public CreateOrderEntryBuilder skuEntries(String[] skuIds) {
		ArrayList<SkuEntry> skuEntries = new ArrayList<>();
		for(String skuId:skuIds){
			skuEntries.add(new SkuEntry(skuId, 1));
		}	
		createOrderEntry.setSkuEntries(skuEntries);
		return this;
	}
	
	public CreateOrderEntryBuilder skuEntries(Integer quantity) {
		ArrayList<SkuEntry> skuEntries = new ArrayList<>();
		Long skuId = new GenerateRandomData().getRandomSkuId();
		skuEntries.add(new SkuEntry(""+skuId, quantity));
		createOrderEntry.setSkuEntries(skuEntries);
		return this;
	}

	
	public CreateOrderEntryBuilder giftWrapEnabled(Boolean isGiftWrapEnabled) {
		createOrderEntry.setGiftWrapEnabled(isGiftWrapEnabled);
		return this;
	}
	public CreateOrderEntryBuilder shipmentType(ShipmentType shipmentType) {
		createOrderEntry.setShipmentType(shipmentType);
		return this;
	}
	public CreateOrderEntryBuilder userName(String userName) {
		createOrderEntry.setUserName(userName);
		return this;
	}
	public CreateOrderEntryBuilder password(String password) {
		createOrderEntry.setPassword(password);
		return this;
	}
	
	private CreateOrderEntryBuilder addressId(Long addressId) {
		createOrderEntry.setAddressId(addressId);
		return this;
	}
	
	public CreateOrderEntryBuilder pincode(Long pincode) {
		createOrderEntry.setPincode(pincode);
		addressId(12345678L);
		return this;
	}
	public CreateOrderEntryBuilder couponCode(String couponCode) {
		createOrderEntry.setCouponCode(couponCode);
		return this;
	}
	public CreateOrderEntryBuilder eGC(Boolean isEGC) {
		createOrderEntry.setEGC(isEGC);
		return this;
	}
	public CreateOrderEntryBuilder mockOrder(Boolean isMockOrder) {
		createOrderEntry.setMockOrder(isMockOrder);
		return this;
	}

	public CreateOrderEntry build() throws IOException{
			defaultData();
			return createOrderEntry;
	}

	private void defaultData() throws IOException {
		// TODO Auto-generated method stub
		if(createOrderEntry.getShipmentMethod()==null){
			createOrderEntry.setShipmentMethod(createOrderEntryBuilderDefaultValues.defaultShipmentMethod());
		}
		 
		
		if(createOrderEntry.getPaymentInstruments()==null){
			createOrderEntry.setPaymentInstruments(createOrderEntryBuilderDefaultValues.defaultPaymentInstruments());
		}
		
		if(createOrderEntry.getSkuEntries()==null){
			 createOrderEntry.setSkuEntries(createOrderEntryBuilderDefaultValues.defaultSkuEntries());
		}
		
		if(createOrderEntry.isGiftWrapEnabled()==null){
			 createOrderEntry.setGiftWrapEnabled(createOrderEntryBuilderDefaultValues.defaultIsGiftWrapEnabled());
		}
		
		if(createOrderEntry.getShipmentType()==null){
			 createOrderEntry.setShipmentType(createOrderEntryBuilderDefaultValues.defaultShipmentType());
		}
		
		if(createOrderEntry.getUserName()==null || createOrderEntry.getPassword()==null){
			String [] randomUserNameAndPassword = createOrderEntryBuilderDefaultValues.getRandomUserNameAndPassword();			
			createOrderEntry.setUserName(randomUserNameAndPassword[0]);
			createOrderEntry.setPassword(randomUserNameAndPassword[1]);
		}
		
		if(createOrderEntry.getPincode()==null){
			createOrderEntry.setPincode(createOrderEntryBuilderDefaultValues.getRandomPincode());
			createOrderEntry.setAddressId(createOrderEntryBuilderDefaultValues.configAddressDataIfNotAvailable
					(createOrderEntry.getUserName(),createOrderEntry.getPassword(),createOrderEntry.getPincode()));
		}else{
			System.out.println("userNme: " + createOrderEntry.getUserName());
			System.out.println("pincode: " + createOrderEntry.getPincode());
			createOrderEntry.setAddressId(createOrderEntryBuilderDefaultValues.configAddressDataIfNotAvailable
					(createOrderEntry.getUserName(),createOrderEntry.getPassword(),createOrderEntry.getPincode()));
		}
		
		if(createOrderEntry.getCouponCode()==null){
			createOrderEntry.setCouponCode(createOrderEntryBuilderDefaultValues.defaultCouponCode());
		}
		
		if(createOrderEntry.isEGC()==null){
			createOrderEntry.setEGC(createOrderEntryBuilderDefaultValues.defaultEGCValue());
		}
		
		
		if(createOrderEntry.isMockOrder()==null){
			createOrderEntry.setMockOrder(createOrderEntryBuilderDefaultValues.defaultMockOrderValue());
		}
		
	}   

}
