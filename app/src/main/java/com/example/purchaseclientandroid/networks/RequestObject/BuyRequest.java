package com.example.purchaseclientandroid.networks.RequestObject;

import com.example.purchaseclientandroid.Models.Article;

public class BuyRequest implements Request {

    Article ArtcileToBuy;

    int Quantity;

    public BuyRequest(Article artcileToBuy, int quantity) {
        ArtcileToBuy = artcileToBuy;
        Quantity = quantity;
    }

    public Article getArtcileToBuy() {
        return ArtcileToBuy;
    }

    public void setArtcileToBuy(Article artcileToBuy) {
        ArtcileToBuy = artcileToBuy;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
