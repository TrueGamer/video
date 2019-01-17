package com.zhxu.library.exception;

import android.content.Context;

/**
 * 自定义错误信息，统一处理返回处理
 */
public class HttpTimeException extends RuntimeException {

    //没有登陆
    public static final int NO_LOGIN = 100053;
    private int mResultCode;
    private String mMessage;


    public HttpTimeException(int resultCode,String message) {
        this(getApiExceptionMessage(resultCode,message));
        mResultCode = resultCode;
        mMessage = message;
    }

    public HttpTimeException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 转换错误数据
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code,String msg) {
        String message = "";
        switch (code) {
            case NO_LOGIN:
                message = "无数据";
                sOnValid.onUnValid();
                break;
            default:
                message = msg;
                break;

        }
        return message;
    }

    public static OnLoginUnValid sOnValid;

    /**
     * 登陆失效的处理
     */
    public interface OnLoginUnValid{
        public void onUnValid();
    }

    public static void setOnValid(OnLoginUnValid onValid) {
        sOnValid = onValid;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public void setResultCode(int resultCode) {
        mResultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}

