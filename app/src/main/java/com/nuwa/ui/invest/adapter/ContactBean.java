package com.nuwa.ui.invest.adapter;

/**
 * Created by chengyuchun on 2016/9/23.
 */
public class ContactBean {
    private String sortKey;
    private String displayName;
    private String phoneNum = "333333";

    public ContactBean(String displayName, String sortKey) {
        this.sortKey = sortKey;
        this.displayName = displayName;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
