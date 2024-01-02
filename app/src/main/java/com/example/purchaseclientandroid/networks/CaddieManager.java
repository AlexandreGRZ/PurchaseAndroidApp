package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.BuyRequest;
import com.example.purchaseclientandroid.networks.RequestObject.CaddieRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.BuyResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.CaddieResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CaddieManager {

    private Model model;

    public CaddieManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void CaddieAsync(CaddieManager.OnCaddieListener listener) {
        new CaddieTask(listener).execute();
    }

    private class CaddieTask extends AsyncTask<Integer, Void, ArrayList<Article>> {
        private final CaddieManager.OnCaddieListener listener;

        public CaddieTask(CaddieManager.OnCaddieListener listener) {
            this.listener = listener;
        }

        @Override
        protected ArrayList<Article> doInBackground(Integer... params) {
            try {
                CaddieRequest caddieRequest = new CaddieRequest();
                CaddieResponse response = (CaddieResponse) model.sendRequest(caddieRequest);
                return response.getListOfArticleInTheCaddie();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Article> ListArticleInCaddie) {
            super.onPostExecute(ListArticleInCaddie);
            if (ListArticleInCaddie != null) {
                // Appeler la méthode de l'interface lorsque l'opération est terminée avec succès
                listener.onCaddieSuccess(ListArticleInCaddie);
            } else {
                // Appeler la méthode de l'interface en cas d'erreur
                listener.onCaddieError("Erreur lors de l'achat de l'article");
            }
        }
    }

    // Interface pour écouter la fin de l'achat d'article
    public interface OnCaddieListener {
        void onCaddieSuccess(ArrayList<Article> ListArticleInCaddie);
        void onCaddieError(String errorMessage);
    }
}
