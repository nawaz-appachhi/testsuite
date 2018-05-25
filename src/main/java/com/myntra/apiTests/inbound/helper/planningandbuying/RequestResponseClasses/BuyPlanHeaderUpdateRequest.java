package com.myntra.apiTests.inbound.helper.planningandbuying.RequestResponseClasses;

import com.myntra.orderindent.enums.BuyPlanStatus;

import java.util.Date;

/**
 * Created by 300000929 on 27/01/17.
 */
public class BuyPlanHeaderUpdateRequest {

    private Long vendorId;
    private String vendorName;
    private String vendorContactPerson;
    private String vendorStateCode;
    private String vendorAddress;
    private String vendorWarehousePincode;
    private String vendorCityCode;
    private String categoryManagerId;
    private String categoryManagerName;
    private String categoryManagerEmail;
    private String toMail;
    private String ccMail;
    private String mailText;
    private String brandId;
    private String brandName;
    private String brandType;
    private String commercialType;
    private Long seasonId;
    private Integer seasonYear;
    private String creditBasisAsOn;
    private String stockOrigin;
    private String paymentTerms;
    private String prioritization;
    private String requestedDate;
    private String estimatedDeliveryDate;
    private String approver;
    private String comments;
    private String letterHeading;
    private String buyPlanOrderType;
    private String source;
    private String businessUnit;
    private String repexName;
    private String repexEmail;
    private Long buyerId;
    private Long termsAdditionalClassification;

    public BuyPlanHeaderUpdateRequest() {

    }

    public Long getTermsAdditionalClassification() {
        return termsAdditionalClassification;
    }

    public void setTermsAdditionalClassification(Long termsAdditionalClassification) {
        this.termsAdditionalClassification = termsAdditionalClassification;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorContactPerson() {
        return vendorContactPerson;
    }

    public void setVendorContactPerson(String vendorContactPerson) {
        this.vendorContactPerson = vendorContactPerson;
    }

    public String getVendorStateCode() {
        return vendorStateCode;
    }

    public void setVendorStateCode(String vendorStateCode) {
        this.vendorStateCode = vendorStateCode;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public String getVendorWarehousePincode() {
        return vendorWarehousePincode;
    }

    public void setVendorWarehousePincode(String vendorWarehousePincode) {
        this.vendorWarehousePincode = vendorWarehousePincode;
    }

    public String getVendorCityCode() {
        return vendorCityCode;
    }

    public void setVendorCityCode(String vendorCityCode) {
        this.vendorCityCode = vendorCityCode;
    }

    public String getCategoryManagerId() {
        return categoryManagerId;
    }

    public void setCategoryManagerId(String categoryManagerId) {
        this.categoryManagerId = categoryManagerId;
    }

    public String getCategoryManagerName() {
        return categoryManagerName;
    }

    public void setCategoryManagerName(String categoryManagerName) {
        this.categoryManagerName = categoryManagerName;
    }

    public String getCategoryManagerEmail() {
        return categoryManagerEmail;
    }

    public void setCategoryManagerEmail(String categoryManagerEmail) {
        this.categoryManagerEmail = categoryManagerEmail;
    }

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getCcMail() {
        return ccMail;
    }

    public void setCcMail(String ccMail) {
        this.ccMail = ccMail;
    }

    public String getMailText() {
        return mailText;
    }

    public void setMailText(String mailText) {
        this.mailText = mailText;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getCommercialType() {
        return commercialType;
    }

    public void setCommercialType(String commercialType) {
        this.commercialType = commercialType;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(Integer seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getCreditBasisAsOn() {
        return creditBasisAsOn;
    }

    public void setCreditBasisAsOn(String creditBasisAsOn) {
        this.creditBasisAsOn = creditBasisAsOn;
    }

    public String getStockOrigin() {
        return stockOrigin;
    }

    public void setStockOrigin(String stockOrigin) {
        this.stockOrigin = stockOrigin;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getPrioritization() {
        return prioritization;
    }

    public void setPrioritization(String prioritization) {
        this.prioritization = prioritization;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLetterHeading() {
        return letterHeading;
    }

    public void setLetterHeading(String letterHeading) {
        this.letterHeading = letterHeading;
    }

    public String getBuyPlanOrderType() {
        return buyPlanOrderType;
    }

    public void setBuyPlanOrderType(String buyPlanOrderType) {
        this.buyPlanOrderType = buyPlanOrderType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getRepexName() {
        return repexName;
    }

    public void setRepexName(String repexName) {
        this.repexName = repexName;
    }

    public String getRepexEmail() {
        return repexEmail;
    }

    public void setRepexEmail(String repexEmail) {
        this.repexEmail = repexEmail;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public String toString() {
        return "BuyPlanHeaderUpdateRequest{" +
                "vendorId=" + vendorId +
                ", vendorName='" + vendorName + '\'' +
                ", vendorContactPerson='" + vendorContactPerson + '\'' +
                ", vendorStateCode='" + vendorStateCode + '\'' +
                ", vendorAddress='" + vendorAddress + '\'' +
                ", vendorWarehousePincode='" + vendorWarehousePincode + '\'' +
                ", vendorCityCode='" + vendorCityCode + '\'' +
                ", categoryManagerId='" + categoryManagerId + '\'' +
                ", categoryManagerName='" + categoryManagerName + '\'' +
                ", categoryManagerEmail='" + categoryManagerEmail + '\'' +
                ", toMail='" + toMail + '\'' +
                ", ccMail='" + ccMail + '\'' +
                ", mailText='" + mailText + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brandType='" + brandType + '\'' +
                ", commercialType='" + commercialType + '\'' +
                ", seasonId=" + seasonId +
                ", seasonYear=" + seasonYear +
                ", creditBasisAsOn='" + creditBasisAsOn + '\'' +
                ", stockOrigin='" + stockOrigin + '\'' +
                ", paymentTerms='" + paymentTerms + '\'' +
                ", prioritization='" + prioritization + '\'' +
                ", requestedDate=" + requestedDate +
                ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                ", approver='" + approver + '\'' +
                ", comments='" + comments + '\'' +
                ", letterHeading='" + letterHeading + '\'' +
                ", buyPlanOrderType='" + buyPlanOrderType + '\'' +
                ", source='" + source + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                ", repexName='" + repexName + '\'' +
                ", repexEmail='" + repexEmail + '\'' +
                ", buyerId=" + buyerId +
                "}";
    }
}
