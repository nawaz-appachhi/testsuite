package com.myntra.apiTests.common.Builder;

import com.myntra.apiTests.common.entries.PaymentInstruments;

public class PaymentInstrumentsBuilder {
    private Boolean isCOD;
    private Boolean useLoyaltyPoints;
    private Boolean useWallet;
    private Boolean isOnline;
    
    public PaymentInstrumentsBuilder(){
    	
    }
    
	public PaymentInstrumentsBuilder setCOD(Boolean isCOD) {
		this.isCOD = isCOD;
		return this;
	}
	public PaymentInstrumentsBuilder setUseLoyaltyPoints(Boolean useLoyaltyPoints) {
		this.useLoyaltyPoints = useLoyaltyPoints;
		return this;
	}
	public PaymentInstrumentsBuilder setUseWallet(Boolean useWallet) {
		this.useWallet = useWallet;
		return this;
	}
	public PaymentInstrumentsBuilder setOnline(Boolean isOnline) {
		this.isOnline = isOnline;
		return this;
	}
	
	public PaymentInstruments build(){
		return new PaymentInstruments(isCOD,useLoyaltyPoints,useWallet,isOnline);
	}
    
    
    
    
}
