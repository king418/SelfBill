package com.king.bean;

import java.util.List;

/**
 * User: king
 * Date: 2015/4/10
 */
public class AccountType {

    private String typeName;

    private List<AccountSubType> subTypes;



    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<AccountSubType> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<AccountSubType> subTypes) {
        this.subTypes = subTypes;
    }

    @Override
    public String toString() {
        return typeName;
    }
}
