package com.myntra.apiTests.end2end.paymentPlanEntries;

public class PaymentPlanInstrumentDetails {
	long id;
	int paymentInstrumentType;
	double totalPrice;
	String ppsId;
	String paymentPlanExecutionStatusId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPaymentInstrumentType() {
		return paymentInstrumentType;
	}
	public void setPaymentInstrumentType(int paymentInstrumentType) {
		this.paymentInstrumentType = paymentInstrumentType;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getPpsId() {
		return ppsId;
	}
	public void setPpsId(String pps_Id) {
		this.ppsId = pps_Id;
	}
	public String getPaymentPlanExecutionStatusId() {
		return paymentPlanExecutionStatusId;
	}
	public void setPaymentPlanExecutionStatusId(String paymentPlanExecutionStatus_id) {
		this.paymentPlanExecutionStatusId = paymentPlanExecutionStatus_id;
	}
}
