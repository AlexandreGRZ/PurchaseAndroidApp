package com.example.purchaseclientandroid.Models;

import com.example.purchaseclientandroid.networks.RequestObject.ConsultRequest;

import java.io.IOException;

public class DisplayerOfArticle {
    private int numArticleToDisplay;

    private Article ArticleToDisplay;

    public DisplayerOfArticle() {
        numArticleToDisplay = 1;
        ArticleToDisplay = null;
    }

    public Article getArticleToDisplay() {
        return ArticleToDisplay;
    }

    public void setArticleToDisplay(Article articleToDisplay) {
        ArticleToDisplay = articleToDisplay;
    }
    public int getNumArticleToDisplay() {
        return numArticleToDisplay;
    }

    public void setNumArticleToDisplay(int numArticleToDisplay) {
        this.numArticleToDisplay = numArticleToDisplay;
    }

    public ConsultRequest GetRequestForServer(int num) throws IOException {
        return new ConsultRequest(getNumArticleToDisplay());
    }

    public void NextArticle(){
        if(getNumArticleToDisplay() == 21)
            setNumArticleToDisplay(1);
        else
            setNumArticleToDisplay(getNumArticleToDisplay() + 1);
    }

    public void PreviousArticle(){
        if(getNumArticleToDisplay() == 1)
            setNumArticleToDisplay(21);
        else
            setNumArticleToDisplay(getNumArticleToDisplay() - 1);
    }


}
