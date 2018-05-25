package com.myntra.apiTests.end2end.paymentPlan;

import java.util.ArrayList;

import com.myntra.apiTests.common.entries.PaymentInstruments;
import com.myntra.apiTests.end2end.paymentPlanEntries.PaymentPlanInstrumentDetails;

public class PaymentPlanInstrumentType {
	
	String ppsId;
	double totalAmount;
	double loyaltyPointsAmount;
	double walletAmount;
	
	
	public PaymentPlanInstrumentType(String ppsId, double totalAmount){
		this.ppsId = ppsId;
		this.totalAmount = totalAmount;
		loyaltyPointsAmount = totalAmount * 0.1;
		walletAmount = totalAmount * 0.1;
	}
	
	public ArrayList<PaymentPlanInstrumentDetails> getPaymentPlanInstrumentDetailObjects(PaymentInstruments paymentInstruments){
		
		PaymentPlanBuilder paymentPlanBuilder = new PaymentPlanBuilder(ppsId);
		
		if(paymentInstruments.isCOD() & !paymentInstruments.isUseLoyaltyPoints() & !paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.cod(totalAmount).build();
			
		}else if(paymentInstruments.isOnline() & !paymentInstruments.isUseLoyaltyPoints() & !paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.online(totalAmount).build();
			
		}else if(paymentInstruments.isUseWallet() & !paymentInstruments.isUseLoyaltyPoints() & !paymentInstruments.isOnline() & !paymentInstruments.isCOD()){
			return paymentPlanBuilder.wallet(totalAmount).build();
			
		}else if(paymentInstruments.isCOD() & paymentInstruments.isUseLoyaltyPoints() & !paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.cod(totalAmount - loyaltyPointsAmount).loyalty(loyaltyPointsAmount).build();
			
		}else if(paymentInstruments.isCOD() & !paymentInstruments.isUseLoyaltyPoints() & paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.cod(totalAmount - walletAmount).wallet(walletAmount).build();
			
		}else if(paymentInstruments.isCOD() & paymentInstruments.isUseLoyaltyPoints() & paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.cod(totalAmount- loyaltyPointsAmount - walletAmount).loyalty(loyaltyPointsAmount).wallet(walletAmount).build();
			
		}else if(paymentInstruments.isOnline() & paymentInstruments.isUseLoyaltyPoints() & !paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.online(totalAmount - loyaltyPointsAmount).loyalty(loyaltyPointsAmount).build();
			
		}else if(paymentInstruments.isOnline() & !paymentInstruments.isUseLoyaltyPoints() & paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.online(totalAmount - walletAmount).wallet(walletAmount).build();
			
		}else if(paymentInstruments.isOnline() & paymentInstruments.isUseLoyaltyPoints() & paymentInstruments.isUseWallet()){
			return paymentPlanBuilder.online(totalAmount - loyaltyPointsAmount - walletAmount).loyalty(loyaltyPointsAmount).wallet(walletAmount).build();
		
		}else{
			return null;
		}
	
	}
}
