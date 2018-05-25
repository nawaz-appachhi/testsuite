package com.myntra.apiTests.end2end.paymentPlan;

import java.util.ArrayList;

import org.drools.core.util.StringUtils;

import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanInstrumentDetails;

public class PaymentPlanBuilder {
	
	ArrayList<PaymentPlanInstrumentDetails> paymentPlanInstrumentDetails;
	PaymentPlanInstrumentDetails paymentPlanInstrumentDetail;
	String ppsId;

	public PaymentPlanBuilder(String ppsId) {
		this.ppsId=ppsId;
		paymentPlanInstrumentDetails = new ArrayList<>();
	}


	public PaymentPlanBuilder cod(double totalAmount) {
		addInstument(PaymentPlanConstants.COD, totalAmount);
		return this;
	}

	public PaymentPlanBuilder wallet(double totalAmount) {
		addInstument(PaymentPlanConstants.WALLET, totalAmount);
		return this;
	}

	public PaymentPlanBuilder loyalty(double totalAmount) {
		addInstument(PaymentPlanConstants.LOYALTY, totalAmount);
		return this;
	}

	public PaymentPlanBuilder online(double totalAmount) {
		addInstument(PaymentPlanConstants.ONLINE, totalAmount);
		return this;
	}

	public void addInstument(int paymentPlanConstants, double totalAmount) {
		paymentPlanInstrumentDetail = new PaymentPlanInstrumentDetails();
		paymentPlanInstrumentDetail.setPaymentInstrumentType(paymentPlanConstants);
		paymentPlanInstrumentDetail.setTotalPrice(totalAmount);
		paymentPlanInstrumentDetail.setPpsId(ppsId);
		paymentPlanInstrumentDetails.add(paymentPlanInstrumentDetail);
	}

	public ArrayList<PaymentPlanInstrumentDetails> build() {
		if (StringUtils.isEmpty(ppsId))
			return null;

		return paymentPlanInstrumentDetails;
	}

}
