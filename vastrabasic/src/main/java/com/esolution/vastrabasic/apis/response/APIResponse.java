package com.esolution.vastrabasic.apis.response;

public class APIResponse<T> {
    boolean success;
    int code;
    String message;
    T data;

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
