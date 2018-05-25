package com.myntra.apiTests.inbound.helper.planningandbuying;

public class BaseOrderIndent {
	private String vendorContactPerson;

	private String vendorName;

	private String vendorWarehouseLocation;

	private String orderIndentOrderType;

	private String vendorAddress;

	private Integer vendorId;

	private String vendorEmail;

	private String season;

	private String orderIndentSource;

	private String stockOrigin;

	private String seasonYear;

	private String commercialType;

	private String paymentTerms;

	public String getVendorContactPerson() {
		return vendorContactPerson;
	}

	public void setVendorContactPerson(String vendorContactPerson) {
		this.vendorContactPerson = vendorContactPerson;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorWarehouseLocation() {
		return vendorWarehouseLocation;
	}

	public void setVendorWarehouseLocation(String vendorWarehouseLocation) {
		this.vendorWarehouseLocation = vendorWarehouseLocation;
	}

	public String getOrderIndentOrderType() {
		return orderIndentOrderType;
	}

	public void setOrderIndentOrderType(String orderIndentOrderType) {
		this.orderIndentOrderType = orderIndentOrderType;
	}

	public String getVendorAddress() {
		return vendorAddress;
	}

	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getOrderIndentSource() {
		return orderIndentSource;
	}

	public void setOrderIndentSource(String orderIndentSource) {
		this.orderIndentSource = orderIndentSource;
	}

	public String getStockOrigin() {
		return stockOrigin;
	}

	public void setStockOrigin(String stockOrigin) {
		this.stockOrigin = stockOrigin;
	}

	public String getSeasonYear() {
		return seasonYear;
	}

	public void setSeasonYear(String seasonYear) {
		this.seasonYear = seasonYear;
	}

	public String getCommercialType() {
		return commercialType;
	}

	public void setCommercialType(String commercialType) {
		this.commercialType = commercialType;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	@Override
	public String toString() {
		return "ClassPojo [vendorContactPerson = " + vendorContactPerson + ", vendorName = " + vendorName
				+ ", vendorWarehouseLocation = " + vendorWarehouseLocation + ", orderIndentOrderType = "
				+ orderIndentOrderType + ", vendorAddress = " + vendorAddress + ", vendorId = " + vendorId
				+ ", vendorEmail = " + vendorEmail + ", season = " + season + ", orderIndentSource = "
				+ orderIndentSource + ", stockOrigin = " + stockOrigin + ", seasonYear = " + seasonYear
				+ ", commercialType = " + commercialType + ", paymentTerms = " + paymentTerms + "]";
	}
}
