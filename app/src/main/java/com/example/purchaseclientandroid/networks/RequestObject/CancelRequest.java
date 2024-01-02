package com.example.purchaseclientandroid.networks.RequestObject;

public class CancelRequest implements Request{
    private int idArticle;

    public CancelRequest(int idArticle) {
        this.idArticle = idArticle;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }
}
