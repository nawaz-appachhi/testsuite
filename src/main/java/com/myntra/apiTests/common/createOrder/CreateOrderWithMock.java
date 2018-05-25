package com.myntra.apiTests.common.createOrder;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.lordoftherings.Initialize;

public class CreateOrderWithMock implements CreateOrderObject {

	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(CreateOrderWithMock.class);
	

	@Override
	public String createOrder(CreateOrderEntry createOrderEntry) throws IOException {
		// TODO Auto-generated method stub
		log.info("creating order with createOrderWithMock method");
		createOrderEntry = new CreateOrderEntryBuilder()
				.userName(createOrderEntry.getUserName())
				.password(createOrderEntry.getPassword())
				.skuEntries(createOrderEntry.getSkuEntries())
				.couponCode(createOrderEntry.getCouponCode())
				.eGC(createOrderEntry.isEGC())
				.giftWrapEnabled(createOrderEntry.isGiftWrapEnabled())
				.pincode(createOrderEntry.getPincode())
				.shipmentMethod(createOrderEntry.getShipmentMethod())
				.shipmentType(createOrderEntry.getShipmentType())
				.paymentInstruments(createOrderEntry.getPaymentInstruments())
				.mockOrder(createOrderEntry.isMockOrder())
				.build();
		
		End2EndHelper end2EndHelper = new End2EndHelper();
		String orderId = null;
//		orderId = end2EndHelper.createOrderWithMock(createOrderEntry);
		return null;
	}
	
}
