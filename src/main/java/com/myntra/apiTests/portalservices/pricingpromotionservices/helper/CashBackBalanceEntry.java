package com.myntra.apiTests.portalservices.pricingpromotionservices.helper;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by abhijit.pati on 01/02/16.
 */

@XmlRootElement(
        name = "cashbackBalanceEntry"
)
public class CashBackBalanceEntry{
    private static final long serialVersionUID = 1L;
    private Double balance=0.0;
    private Double storeCreditBalance=0.0;
    private Double earnedCreditbalance=0.0;
    private String migrationStatus;


    public Double getBalance() {
        return balance;
    }


    public Double getCashbackBalance() {
        return balance;
    }



    public void setBalance(Double balance) {
        this.balance = balance;
    }




    public Double getStoreCreditBalance() {
        return storeCreditBalance;
    }


    public void setStoreCreditBalance(Double storeCreditBalance) {
        this.storeCreditBalance = storeCreditBalance;
    }


    public Double getEarnedCreditbalance() {
        return earnedCreditbalance;
    }


    public void setEarnedCreditbalance(Double earnedCreditbalance) {
        this.earnedCreditbalance = earnedCreditbalance;
    }

    public String getMigrationStatus() { return migrationStatus; }

    public void setMigrationStatus(String migrationStatus) { this.migrationStatus = migrationStatus; }

    @Override
    public String toString() {
        return "CashbackBalanceEntry [cashbackBalance=" + balance + ", storeCreditBalance="
                + storeCreditBalance + ", earnedCreditbalance=" + earnedCreditbalance + ",migrationStatus= "
                + migrationStatus +"]";
    }
}
