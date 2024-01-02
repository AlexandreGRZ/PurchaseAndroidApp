package com.example.purchaseclientandroid.networks.ResponseObject;

import com.example.purchaseclientandroid.Models.Article;

public class BuyResponse implements Response{
    Article ArticleBuy;

    public BuyResponse(Article articleBuy) {
        ArticleBuy = articleBuy;
    }

    public Article getArticleBuy() {
        return ArticleBuy;
    }

    public void setArticleBuy(Article articleBuy) {
        ArticleBuy = articleBuy;
    }
}
