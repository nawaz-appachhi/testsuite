package com.myntra.apiTests.common.createOrder;

import com.myntra.apiTests.common.entries.CreateOrderEntry;
import com.myntra.lordoftherings.Initialize;

import java.io.IOException;

import org.apache.log4j.Logger;

public class CreateOrderFactory {

	static Initialize init = new Initialize("/Data/configuration");
	private static Logger log = Logger.getLogger(CreateOrderFactory.class);
	
	public CreateOrderObject getCreateOrderObject(CreateOrderEntry createOrderEntry) throws IOException{
		
		boolean isMockOrder = createOrderEntry.isMockOrder();
		
		if(isMockOrder==true){
			return new CreateOrderWithMock();
		}else if(isMockOrder == false){
			return new CreateOrderWithoutMock();
		}
		
		log.info("There is issue in createOrder Entry request");
		return null;
	}

}
