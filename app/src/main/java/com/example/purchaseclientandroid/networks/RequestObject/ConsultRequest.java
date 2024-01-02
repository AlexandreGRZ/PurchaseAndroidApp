package com.example.purchaseclientandroid.networks.RequestObject;

public class ConsultRequest implements Request{
    private int idOfArticleToDisplay;

    public ConsultRequest(int idOfArticleToDisplay) {
        this.idOfArticleToDisplay = idOfArticleToDisplay;
    }

    public int getIdOfArticleToDisplay() {
        return idOfArticleToDisplay;
    }

    public void setIdOfArticleToDisplay(int idOfArticleToDisplay) {
        this.idOfArticleToDisplay = idOfArticleToDisplay;
    }
}
