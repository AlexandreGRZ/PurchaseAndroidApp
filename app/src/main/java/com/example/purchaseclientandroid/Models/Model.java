package com.example.purchaseclientandroid.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import com.example.purchaseclientandroid.networks.Protocol;
import com.example.purchaseclientandroid.networks.RequestObject.Request;
import com.example.purchaseclientandroid.networks.ResponseObject.Response;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Model {
    public static volatile Model instance;
    Protocol protocol;
    private final Context context;
    private String _requete;
    private Caddie caddie;
    private DisplayerOfArticle displayerOfArticle;

    private ArrayList<Article> listOfArticleInCaddie;

    public static Model getInstance(Context c) throws SQLException, ClassNotFoundException, IOException {
        if (instance == null) {
            synchronized (Model.class) {
                if (instance == null) {
                    instance = new Model(c);
                }
            }
        }
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public Model(Context c1) throws IOException {
        this.context = c1.getApplicationContext();
        this.protocol = new Protocol(this.context);
        this.caddie = new Caddie(new ArrayList<>());
        this.displayerOfArticle = new DisplayerOfArticle();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    protocol.connectToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public Response sendRequest(Request request) throws IOException {
        return protocol.onRequete(request);
    }

    public String getrequete() {
        return _requete;
    }

    public void setrequete(String _requete) {
        this._requete = _requete;
    }

    public DisplayerOfArticle getDisplayerOfArticle() {
        return displayerOfArticle;
    }

    public void setDisplayerOfArticle(DisplayerOfArticle displayerOfArticle) {
        this.displayerOfArticle = displayerOfArticle;
    }

    public Caddie getCaddie() {
        return caddie;
    }

    public void setCaddie(Caddie caddie) {
        this.caddie = caddie;
    }

    public ArrayList<Article> getListOfArticleInCaddie() {
        return listOfArticleInCaddie;
    }

    public void setListOfArticleInCaddie(ArrayList<Article> listOfArticleInCaddie) {
        this.listOfArticleInCaddie = listOfArticleInCaddie;
    }


}
