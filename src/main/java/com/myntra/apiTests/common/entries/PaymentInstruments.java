package com.myntra.apiTests.common.entries;

/**
 * Created by abhijit.pati on 27/05/17.
 */
public class PaymentInstruments {

    private Boolean isCOD;
    private Boolean useLoyaltyPoints;
    private Boolean useWallet;
    private Boolean isOnline;


    public PaymentInstruments(Boolean isCOD, Boolean useLoyaltyPoints, Boolean useWallet, Boolean isOnline) {
		// TODO Auto-generated constructor stub
    	this.isCOD = isCOD;
    	this.useLoyaltyPoints = useLoyaltyPoints;
    	this.useWallet = useWallet;
    	this.isOnline = isOnline;
	}

	public PaymentInstruments() {
		// TODO Auto-generated constructor stub
	}

	public Boolean isCOD() {
        return isCOD;
    }

    public void setCOD(boolean COD) {
        this.isCOD = COD;
    }

    public Boolean isUseLoyaltyPoints() {
        return useLoyaltyPoints;
    }

    public void setUseLoyaltyPoints(boolean useLoyaltyPoints) {
        this.useLoyaltyPoints = useLoyaltyPoints;
    }

    public Boolean isUseWallet() {
        return useWallet;
    }

    public void setUseWallet(Boolean useWallet) {
        this.useWallet = useWallet;
    }

    public Boolean isOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        this.isOnline = online;
    }

	@Override
	public String toString() {
		return "PaymentInstruments [isCOD=" + isCOD + ", useLoyaltyPoints=" + useLoyaltyPoints + ", useWallet="
				+ useWallet + ", isOnline=" + isOnline + "]";
	}
    
    

}
