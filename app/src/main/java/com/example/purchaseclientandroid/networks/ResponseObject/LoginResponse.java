package com.example.purchaseclientandroid.networks.ResponseObject;

public class LoginResponse implements Response{
    private boolean LoginYesOrNot;

    private int idClientLogIn;

    private String ErrorMessage;

    public LoginResponse(boolean loginYesOrNot, String errorMessage) {
        LoginYesOrNot = loginYesOrNot;
        ErrorMessage = errorMessage;
    }

    public LoginResponse(boolean loginYesOrNot,int idClientLogIn ,String errorMessage) {
        LoginYesOrNot = loginYesOrNot;
        this.idClientLogIn = idClientLogIn;
        ErrorMessage = errorMessage;
    }

    public boolean isLoginYesOrNot() {
        return LoginYesOrNot;
    }

    public void setLoginYesOrNot(boolean loginYesOrNot) {
        LoginYesOrNot = loginYesOrNot;
    }

    public int getIdClientLogIn() {
        return idClientLogIn;
    }

    public void setIdClientLogIn(int idClientLogIn) {
        this.idClientLogIn = idClientLogIn;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "LoginYesOrNot=" + LoginYesOrNot +
                ", idClientLogIn=" + idClientLogIn +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }
}
