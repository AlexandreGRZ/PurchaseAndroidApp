package com.example.purchaseclientandroid.networks;

import android.util.Log;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.networks.RequestObject.*;
import com.example.purchaseclientandroid.networks.ResponseObject.*;
import kotlin.contracts.Returns;

import java.io.IOException;
import java.util.ArrayList;

public class OVESP {

    private Protocol protocol;

    private String requeteToSend;

    public OVESP(Protocol protocol) {
        this.protocol = protocol;
    }
    public synchronized Response handleRequest(Request request) throws IOException {
        if (request instanceof LoginRequest){
            return handleLoginRequest((LoginRequest) request);
        }
        if(request instanceof ConsultRequest){
            return handleConsultRequest((ConsultRequest) request);
        }
        if(request instanceof BuyRequest){
            return handleBuyResult((BuyRequest) request);
        }
        if(request instanceof CaddieRequest){
            return handleCaddieResult((CaddieRequest) request);
        }
        if(request instanceof CancelRequest){
            return handleCancelResult((CancelRequest) request);
        }
        if(request instanceof CancelAllRequest){
            return handleCancelAllResult((CancelAllRequest) request);
        }
        if(request instanceof ConfirmRequest){
            return handleConfirmResult((ConfirmRequest) request);
        }

        return null;
    }

    private LoginResponse handleLoginRequest(LoginRequest request) throws IOException {

        if (request.getisNewClientOrNot())
            setRequeteToSend("LOGIN#" + request.getLoginClient() + "#" + request.getPassWordClient() + "#1");
        else setRequeteToSend("LOGIN#" + request.getLoginClient() + "#" + request.getPassWordClient() + "#0");
        String reponse = protocol.Echange(getRequeteToSend());
        if (reponse.contains("ERR"))
            return new LoginResponse(false, "ERREUR lors de la connexion");

        String[] mots = reponse.split("#");
        return new LoginResponse(true,"Vous etes connectez");
        //setArticle(numArticle);
    }

    private ConsultResponse handleConsultRequest(ConsultRequest request) throws IOException {

        setRequeteToSend("CONSULT#" + request.getIdOfArticleToDisplay());
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new ConsultResponse(new Article(), "ERREUR lors de la connexion");

        String[] mots = reponse.split("#");
        Log.d("receive", mots[0] + " " + mots[1] + " " + mots[2] + " "  + mots[3] + " " + mots[4] + " " );
        Article articleOfTheRequest = new Article(Integer.parseInt(mots[2]),mots[3],Float.parseFloat(mots[4]), Integer.parseInt(mots[5]),mots[6].substring(0, mots[6].length() - 1));
        return new ConsultResponse(articleOfTheRequest);
    }

    private Response handleBuyResult(BuyRequest request) throws IOException {

        setRequeteToSend("ACHAT#" + request.getArtcileToBuy().getId() + "#" + request.getQuantity());
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new BuyResponse(new Article());

        String[] mots = reponse.split("#");
        Log.d("receive", mots[0] + " " + mots[1] + " " + mots[2] + " "  + mots[3] + " " + mots[4] + " " );
        Article articleOfTheRequest = new Article(Integer.parseInt(mots[1]),mots[2],Float.parseFloat(mots[3]), Integer.parseInt(mots[4].substring(0, mots[4].length() - 1)));
        return new BuyResponse(articleOfTheRequest);
    }

    private Response handleCaddieResult(CaddieRequest request) throws IOException {


        setRequeteToSend("CADDIE");
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new CaddieResponse(new ArrayList<Article>());

        String[] parts = reponse.split("#");
        CaddieResponse response = new CaddieResponse(new ArrayList<Article>());
        for (int i = 1; i < parts.length; i += 4) {
            Log.d("Caddie", "test");
            Article ArticleToAddInCaddie = new Article();

            ArticleToAddInCaddie.setId(Integer.parseInt(parts[i]));
            ArticleToAddInCaddie.setNom(parts[i + 1]);
            ArticleToAddInCaddie.setPrix(Float.parseFloat(parts[i + 2]));
            if(parts[i+3].contains("!")){
                Log.d("Caddie", "test1");
                ArticleToAddInCaddie.setQuantite(Integer.parseInt(parts[i + 3].substring(0, parts[i + 3].length() - 1)));
            }else{
                Log.d("Caddie", "test2");
                ArticleToAddInCaddie.setQuantite(Integer.parseInt(parts[i + 3]));
            }

            response.getListOfArticleInTheCaddie().add(ArticleToAddInCaddie);
            Log.d("Caddie", response.getListOfArticleInTheCaddie().toString());
        }
        return response;
    }

    private Response handleCancelResult(CancelRequest request) throws IOException {

        setRequeteToSend("CANCEL#" + request.getIdArticle());
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new CancelResponse(false, "Error lors du CANCEL");
        else
            return  new CancelResponse(true);

    }

    private Response handleCancelAllResult(CancelAllRequest request) throws IOException {

        setRequeteToSend("CANCELALL");
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new CancelAllResponse(false);
        else
            return  new CancelAllResponse(true);

    }

    private Response handleConfirmResult(ConfirmRequest request) throws IOException {

        setRequeteToSend("CONFIRM");
        String reponse = protocol.Echange(getRequeteToSend());

        if (reponse.contains("ERR"))
            return new ConfirmResponse(false);
        else
            return  new ConfirmResponse(true);

    }

    public String getRequeteToSend() {
        return requeteToSend;
    }

    public void setRequeteToSend(String requete) {
        this.requeteToSend = requete;
    }
}
