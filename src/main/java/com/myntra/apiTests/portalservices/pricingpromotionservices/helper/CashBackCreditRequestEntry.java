package com.myntra.apiTests.portalservices.pricingpromotionservices.helper;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by 8403 on 01/02/16.
 */

@XmlRootElement(
        name = "cashbackCreditRequestEntry"
)
public class CashBackCreditRequestEntry {
    private static final long serialVersionUID = 1L;
    private String login;
    private Double earnedCreditAmount;
    private Double storeCreditAmount;
    private Double debitAmount;
    private String description;
    private String businessProcess;
    private String itemType;
    private String itemId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getEarnedCreditAmount() {
        return earnedCreditAmount;
    }

    public void setEarnedCreditAmount(Double earnedCreditAmount) {
        this.earnedCreditAmount = earnedCreditAmount;
    }

    public Double getStoreCreditAmount() {
        return storeCreditAmount;
    }

    public void setStoreCreditAmount(Double storeCreditAmount) {
        this.storeCreditAmount = storeCreditAmount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessProcess() {
        return businessProcess;
    }

    public void setBusinessProcess(String businessProcess) {
        this.businessProcess = businessProcess;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public String toString() {
        return "CashBackCreditRequestEntry{" +
                "login='" + login + '\'' +
                ", earnedCreditAmount=" + earnedCreditAmount +
                ", storeCreditAmount=" + storeCreditAmount +
                ", debitAmount=" + debitAmount +
                ", description='" + description + '\'' +
                ", businessProcess='" + businessProcess + '\'' +
                ", itemType='" + itemType + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}
