package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.CancelAllRequest;
import com.example.purchaseclientandroid.networks.RequestObject.ConfirmRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.CancelAllResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.ConfirmResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ConfirmManager {
    private Model model;

    public ConfirmManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void ConfirmAsync(ConfirmManager.OnConfirmFetchListener listener) {
        new ConfirmManager.ConfirmTask(listener).execute();
    }

    private class ConfirmTask extends AsyncTask<Integer, Void, Boolean> {
        private final ConfirmManager.OnConfirmFetchListener listener;

        public ConfirmTask(ConfirmManager.OnConfirmFetchListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                ConfirmRequest request = new ConfirmRequest();
                ConfirmResponse response = (ConfirmResponse) model.sendRequest(request);
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
                    listener.OnConfirmFetch();
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                listener.onConfirmFetchError("Erreur lors de la récupération de l'article");
            }
        }
    }

    public interface OnConfirmFetchListener {
        void OnConfirmFetch() throws SQLException, IOException, ClassNotFoundException;
        void onConfirmFetchError(String errorMessage);
    }
}
