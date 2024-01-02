package com.example.purchaseclientandroid.networks.ResponseObject;

public class ConfirmResponse implements Response{
    boolean works;

    public ConfirmResponse(boolean works) {
        this.works = works;
    }

    public boolean isWorks() {
        return works;
    }

    public void setWorks(boolean works) {
        this.works = works;
    }
}
