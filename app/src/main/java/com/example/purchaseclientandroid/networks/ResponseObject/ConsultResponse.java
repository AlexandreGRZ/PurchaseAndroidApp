package com.example.purchaseclientandroid.networks.ResponseObject;

import com.example.purchaseclientandroid.Models.Article;

public class ConsultResponse implements Response{
    private Article ArticleOfTheRequest;
    private String Message_ERROR;

    public ConsultResponse(Article articleOfTheRequest, String message_ERROR) {
        ArticleOfTheRequest = articleOfTheRequest;
        Message_ERROR = message_ERROR;
    }

    public ConsultResponse(Article articleOfTheRequest) {
        ArticleOfTheRequest = articleOfTheRequest;
        Message_ERROR = null;
    }

    public Article getArticleOfTheRequest() {
        return ArticleOfTheRequest;
    }

    public void setArticleOfTheRequest(Article articleOfTheRequest) {
        ArticleOfTheRequest = articleOfTheRequest;
    }

    public String getMessage_ERROR() {
        return Message_ERROR;
    }

    public void setMessage_ERROR(String message_ERROR) {
        Message_ERROR = message_ERROR;
    }
}
