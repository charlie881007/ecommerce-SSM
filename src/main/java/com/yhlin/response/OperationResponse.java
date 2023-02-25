package com.yhlin.response;

public class OperationResponse {
    private boolean success;
    private String msg;

    public OperationResponse() {
    }

    public OperationResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }


    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
