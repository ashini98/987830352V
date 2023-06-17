package com.aishu.platinum.model;

public class ChequeBalance {

    private String ChequeNumber;
    private double Balance;

    public ChequeBalance() {

    }

    public String getChequeNumber() {
        return ChequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        ChequeNumber = chequeNumber;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }
}
