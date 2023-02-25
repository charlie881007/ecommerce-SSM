package com.yhlin.response;

public class EmailCheckResponse {
    private boolean canUse;

    public EmailCheckResponse() {
    }

    public EmailCheckResponse(boolean canUse) {
        this.canUse = canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }

    public boolean isCanUse() {
        return canUse;
    }
}
