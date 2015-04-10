package com.king.bean;

/**
 * User: king
 * Date: 2015/4/10
 */
public class AccountSubType {

    private String subTypeName;


    public String getSubTypeName() {
        return subTypeName;
    }

    public void setSubTypeName(String subTypeName) {
        this.subTypeName = subTypeName;
    }

    @Override
    public String toString() {
        return subTypeName;
    }
}
