package com.example.purchaseclientandroid.networks.RequestObject;

public class LoginRequest implements Request {
    String LoginClient;
    String PassWordClient;
    boolean newClientOrNot;

    public LoginRequest(String loginClient, String passWordClient, boolean newClientOrNot) {
        LoginClient = loginClient;
        PassWordClient = passWordClient;
        this.newClientOrNot = newClientOrNot;
    }

    public String getLoginClient() {
        return LoginClient;
    }

    public void setLoginClient(String loginClient) {
        LoginClient = loginClient;
    }

    public String getPassWordClient() {
        return PassWordClient;
    }

    public void setPassWordClient(String passWordClient) {
        PassWordClient = passWordClient;
    }

    public boolean getisNewClientOrNot() {
        return newClientOrNot;
    }

    public void getsetNewClientOrNot(boolean newClientOrNot) {
        this.newClientOrNot = newClientOrNot;
    }
}
