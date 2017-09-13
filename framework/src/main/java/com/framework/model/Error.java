package com.framework.model;

/**
 * Created by chengyuchun on 2017/6/19.
 */

public class Error {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    private int code;
    private String errorMessage;
}
