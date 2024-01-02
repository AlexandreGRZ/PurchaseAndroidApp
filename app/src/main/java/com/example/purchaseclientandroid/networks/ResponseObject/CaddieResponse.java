package com.example.purchaseclientandroid.networks.ResponseObject;

import com.example.purchaseclientandroid.Models.Article;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CaddieResponse implements Response {

    ArrayList<Article> ListOfArticleInTheCaddie;

    public CaddieResponse(ArrayList<Article> listOfArticleInTheCaddie) {
        ListOfArticleInTheCaddie = listOfArticleInTheCaddie;
    }

    public ArrayList<Article> getListOfArticleInTheCaddie() {
        return ListOfArticleInTheCaddie;
    }

    public void setListOfArticleInTheCaddie(ArrayList<Article> listOfArticleInTheCaddie) {
        ListOfArticleInTheCaddie = listOfArticleInTheCaddie;
    }
}
