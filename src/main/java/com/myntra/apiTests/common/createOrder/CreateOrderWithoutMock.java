package com.myntra.apiTests.common.createOrder;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.apiTests.common.Builder.CreateOrderEntryBuilder;
import com.myntra.apiTests.end2end.End2EndHelper;
import com.myntra.apiTests.erpservices.CustomExceptions.SCMExceptions;
import com.myntra.commons.exception.ManagerException;
import com.myntra.lordoftherings.Initialize;

import argo.saj.InvalidSyntaxException;

public class CreateOrderWithoutMock implements CreateOrderObject {

	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(CreateOrderWithoutMock.class);
	
	@Override
	public String createOrder(CreateOrderEntry createOrderEntry) throws IOException {
		// TODO Auto-generated method stub
		log.info("creating order with CreateOrderWithoutMock method");
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
		try {
			orderId = end2EndHelper.createOrder(createOrderEntry);
		} catch (ManagerException | InvalidSyntaxException | SCMExceptions | IOException | JAXBException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderId;

	}
	
}
