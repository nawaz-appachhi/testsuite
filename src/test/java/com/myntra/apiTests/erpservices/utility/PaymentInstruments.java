package com.myntra.apiTests.erpservices.utility;

public class PaymentInstruments {
	
	private String coupon;
	private String giftcard;
	private boolean cashback;
	private boolean wallet;
	private boolean loyalty;
	private boolean isGiftWrap;
	private boolean isTryandBuy;
	
	public PaymentInstruments(){
		this.coupon="";
		this.giftcard="";
		this.cashback=false;
		this.wallet=false;
		this.loyalty=false;
		this.isGiftWrap=false;
		this.isTryandBuy=false;		
	}
	
	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getGiftcard() {
		return giftcard;
	}

	public void setGiftcard(String giftcard) {
		this.giftcard = giftcard;
	}

	public boolean isCashback() {
		return cashback;
	}

	public void setCashback(boolean cashback) {
		this.cashback = cashback;
	}

	public boolean isWallet() {
		return wallet;
	}

	public void setWallet(boolean wallet) {
		this.wallet = wallet;
	}

	public boolean isTryandBuy() {
		return isTryandBuy;
	}

	public void setTryandBuy(boolean isTryandBuy) {
		this.isTryandBuy = isTryandBuy;
	}

	public boolean isGiftWrap() {
		return isGiftWrap;
	}

	public void setGiftWrap(boolean isGiftWrap) {
		this.isGiftWrap = isGiftWrap;
	}

	public boolean isLoyalty() {
		return loyalty;
	}

	public void setLoyalty(boolean loyalty) {
		this.loyalty = loyalty;
	}
	

}
