package com.example.purchaseclientandroid.Models;

public class Article {

    private int id;
    private String nom;
    private Float prix;
    private Integer quantite;
    private String img;

    public Article() {}

    public Article(int id, String nom, Float prix, Integer quantite) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Article(int id, String nom, Float prix, Integer quantite, String img) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.img = img;
    }

    public Article(String nom, Float prix, Integer quantite, String img) {
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", quantite=" + quantite +
                ", img='" + img + '\'' +
                '}';
    }
}
