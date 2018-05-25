package com.myntra.apiTests.end2end.paymentPlanEntries;

public class PaymentPlanItemInstrument {
	long id;
	long amount;
	int paymentInstrumentType;
	long ppsItemId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getPaymentInstrumentType() {
		return paymentInstrumentType;
	}
	public void setPaymentInstrumentType(int paymentInstrumentType) {
		this.paymentInstrumentType = paymentInstrumentType;
	}
	public long getPpsItemId() {
		return ppsItemId;
	}
	public void setPpsItemId(long ppsItemId) {
		this.ppsItemId = ppsItemId;
	}
}
