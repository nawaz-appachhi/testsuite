package com.myntra.apiTests.erpservices.oms;

public interface OMSHelpersEnums {
	
	public enum ReadyToDispatchType{
		POSITIVE, //This is the positive behaviour, payload data will be taken from DB
		
		//These are negative and payload data will be modified during runtime
		DIFFERENT_SKU,
		MISSING_SKU,
		ZERO_QTY,
		GREATER_QTY,
		
		//These are negative behaviour and data will be modified in DB
		MISSING_COURIER, NEGATIVE,
		
	}
	
}
