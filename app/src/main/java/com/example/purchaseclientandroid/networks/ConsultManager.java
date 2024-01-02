package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.ConsultRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.ConsultResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ConsultManager {

    private Model model;

    public ConsultManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void fetchArticleAsync(OnArticleFetchListener listener) {
        new FetchArticleTask(listener).execute();
    }

    private class FetchArticleTask extends AsyncTask<Integer, Void, Article> {
        private final OnArticleFetchListener listener;

        public FetchArticleTask(OnArticleFetchListener listener) {
            this.listener = listener;
        }
        @Override
        protected Article doInBackground(Integer... params) {
            try {
                ConsultRequest request = model.getDisplayerOfArticle().GetRequestForServer(model.getDisplayerOfArticle().getNumArticleToDisplay());
                ConsultResponse response = (ConsultResponse) model.sendRequest(request);
                Log.d("ConsultASYNC", response.getArticleOfTheRequest().toString());
                return response.getArticleOfTheRequest();
            } catch (Exception e) {
                // Gérer l'erreur de manière appropriée (affichage à l'utilisateur, journalisation, etc.)
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Article article) {
            super.onPostExecute(article);
            if (article != null) {
                model.getDisplayerOfArticle().setArticleToDisplay(article);
                listener.onArticleFetched();
            } else {
                listener.onArticleFetchError("Erreur lors de la récupération de l'article");
            }
        }
    }

    public interface OnArticleFetchListener {
        void onArticleFetched();
        void onArticleFetchError(String errorMessage);
    }
}
