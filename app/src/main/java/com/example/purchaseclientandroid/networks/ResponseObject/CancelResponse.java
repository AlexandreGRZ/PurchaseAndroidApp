package com.example.purchaseclientandroid.networks.ResponseObject;

public class CancelResponse implements Response{

    private boolean isWorks;

    private String ErrorMessage;

    public CancelResponse(boolean isWorks, String errorMessage) {
        this.isWorks = isWorks;
        ErrorMessage = errorMessage;
    }

    public CancelResponse(boolean isWorks) {
        this.isWorks = isWorks;
        ErrorMessage = "Works Well";
    }

    public boolean isWorks() {
        return isWorks;
    }

    public void setWorks(boolean works) {
        isWorks = works;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
