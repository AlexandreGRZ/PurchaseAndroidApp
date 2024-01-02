package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.purchaseclientandroid.Models.Article;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.CancelRequest;
import com.example.purchaseclientandroid.networks.RequestObject.ConsultRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.CancelResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.ConsultResponse;

import java.io.IOException;
import java.sql.SQLException;

public class CancelManager {

    private Model model;

    public CancelManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void CancelAsync(int idArticleToCancel,CancelManager.OnCancelFetchListener listener) {
        new CancelManager.CancelTask(listener).execute(idArticleToCancel);
    }
    private class CancelTask extends AsyncTask<Integer, Void, Boolean> {
        private final CancelManager.OnCancelFetchListener listener;

        public CancelTask(CancelManager.OnCancelFetchListener listener) {
            this.listener = listener;
        }
        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                CancelRequest request = new CancelRequest(params[0]);
                CancelResponse response = (CancelResponse) model.sendRequest(request);
                return response.isWorks();
            } catch (Exception e) {
                // Gérer l'erreur de manière appropriée (affichage à l'utilisateur, journalisation, etc.)
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean successOrNot) {
            super.onPostExecute(successOrNot);
            if (successOrNot) {
                try {
                    listener.OnCancelFetch();
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                listener.onCancelFetchError("Erreur lors de la récupération de l'article");
            }
        }
    }

    public interface OnCancelFetchListener {
        void OnCancelFetch() throws SQLException, IOException, ClassNotFoundException;
        void onCancelFetchError(String errorMessage);
    }
}
