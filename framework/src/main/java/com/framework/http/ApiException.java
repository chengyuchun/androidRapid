package com.framework.http;

public class ApiException extends RuntimeException {

    private static int errorCode;
    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode,""));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(int resultCode,String detailMessage) {
        this(getApiExceptionMessage(resultCode,detailMessage));
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */

    private static String getApiExceptionMessage(int code,String defaultMsg){
        errorCode = code;
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            default:
                message = defaultMsg;

        }
        return message;
    }

    public int getErrorCode(){
        return errorCode;
    }

}

