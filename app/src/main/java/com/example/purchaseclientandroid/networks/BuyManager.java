package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.BuyRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.BuyResponse;

import java.io.IOException;
import java.sql.SQLException;

public class BuyManager {

    private Model model;

    public BuyManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void BuyArticleAsync(int quantite, BuyManager.OnAchatListener listener) {
        new BuyArticleTask(listener).execute(quantite);
    }

    private class BuyArticleTask extends AsyncTask<Integer, Void, Article> {
        private final OnAchatListener listener;

        public BuyArticleTask(OnAchatListener listener) {
            this.listener = listener;
        }

        @Override
        protected Article doInBackground(Integer... params) {
            try {
                int quantite = params[0];
                BuyRequest request = new BuyRequest(model.getDisplayerOfArticle().getArticleToDisplay(), quantite);
                BuyResponse response = (BuyResponse) model.sendRequest(request);
                return response.getArticleBuy();  // L'achat a réussi
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
            if (article != null) {
                // Appeler la méthode de l'interface lorsque l'opération est terminée avec succès
                try {
                    listener.onAchatSuccess();
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Appeler la méthode de l'interface en cas d'erreur
                listener.onAchatError("Erreur lors de l'achat de l'article");
            }
        }
    }

    // Interface pour écouter la fin de l'achat d'article
    public interface OnAchatListener {
        void onAchatSuccess() throws SQLException, IOException, ClassNotFoundException;
        void onAchatError(String errorMessage);
    }
}
