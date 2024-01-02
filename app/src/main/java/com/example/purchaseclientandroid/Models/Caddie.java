package com.example.purchaseclientandroid.Models;

import java.util.ArrayList;

public class Caddie {

    private ArrayList<Article> panier = new ArrayList<>();

    public Caddie(ArrayList<Article> panier) {
        this.panier = panier;
    }

    public ArrayList<Article> getPanier() {
        return panier;
    }

    public void setPanier(ArrayList<Article> panier) {
        this.panier = panier;
    }

    public Article getArticleFromListById(int id){
        return getPanier().get(id);
    }

    public void addArt(Article A) {
        boolean trouve = false;
        for (int i = 0; (getPanier() != null && getPanier().size() > i) && trouve == false; i++) {
            if (getArticleFromListById(i).getId() == A.getId()) {
                getArticleFromListById(i).setQuantite(getArticleFromListById(i).getQuantite() + A.getQuantite());
                trouve = true;
            }
        }
        if (trouve == false) {
            getPanier().add(A);
        }
        for (int i = 0; getPanier() != null && getPanier().size() > i; i++) {
            System.out.println("Panier client " + i + ":" + getPanier().get(i));
        }
    }
}
