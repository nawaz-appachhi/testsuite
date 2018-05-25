package com.myntra.apiTests.common.createOrder;

import java.io.IOException;

import com.myntra.apiTests.common.entries.CreateOrderEntry;

public interface CreateOrderObject {

	public String createOrder(CreateOrderEntry createOrderEntry) throws IOException;
}
