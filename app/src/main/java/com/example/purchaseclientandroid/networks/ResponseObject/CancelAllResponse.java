package com.example.purchaseclientandroid.networks.ResponseObject;

public class CancelAllResponse implements Response {
    boolean Works;

    public CancelAllResponse(boolean works) {
        Works = works;
    }

    public boolean isWorks() {
        return Works;
    }

    public void setWorks(boolean works) {
        Works = works;
    }
}
