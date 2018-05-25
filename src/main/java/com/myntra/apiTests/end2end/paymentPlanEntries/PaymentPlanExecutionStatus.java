package com.myntra.apiTests.end2end.paymentPlanEntries;

public class PaymentPlanExecutionStatus {
	long id;
	long paymentPlanInstrumentDetailId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPaymentPlanInstrumentDetailId() {
		return paymentPlanInstrumentDetailId;
	}
	public void setPaymentPlanInstrumentDetailId(long paymentPlanInstrumentDetailId) {
		this.paymentPlanInstrumentDetailId = paymentPlanInstrumentDetailId;
	}
}
