package com.king.bean;

import java.util.List;

/**
 * User: king
 * Date: 2015/4/10
 */
public class Payment {

    private String payment;
    private List<AccountType> types;


    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<AccountType> getTypes() {
        return types;
    }

    public void setTypes(List<AccountType> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return payment;
    }
}
