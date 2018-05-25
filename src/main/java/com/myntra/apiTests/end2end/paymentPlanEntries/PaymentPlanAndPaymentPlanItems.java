package com.myntra.apiTests.end2end.paymentPlanEntries;

import java.util.ArrayList;

public class PaymentPlanAndPaymentPlanItems {
	private PaymentPlan paymentPlan;
	private ArrayList<PaymentPlanItem> paymentPlanItems;
	
	public PaymentPlan getPaymentPlan() {
		return paymentPlan;
	}
	public void setPaymentPlan(PaymentPlan paymentPlan) {
		this.paymentPlan = paymentPlan;
	}
	public ArrayList<PaymentPlanItem> getPaymentPlanItems() {
		return paymentPlanItems;
	}
	public void setPaymentPlanItems(ArrayList<PaymentPlanItem> paymentPlanItems) {
		this.paymentPlanItems = paymentPlanItems;
	}
}
