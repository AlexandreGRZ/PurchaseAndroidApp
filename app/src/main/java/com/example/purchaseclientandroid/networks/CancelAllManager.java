package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.CancelAllRequest;
import com.example.purchaseclientandroid.networks.RequestObject.CancelRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.CancelAllResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.CancelResponse;

import java.io.IOException;
import java.sql.SQLException;

public class CancelAllManager {

    private Model model;

    public CancelAllManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.model = Model.getInstance(context);
    }

    public void CancelAllAsync(CancelAllManager.OnCancelAllFetchListener listener) {
        new CancelAllManager.CancelAllTask(listener).execute();
    }

    private class CancelAllTask extends AsyncTask<Integer, Void, Boolean> {
        private final CancelAllManager.OnCancelAllFetchListener listener;

        public CancelAllTask(CancelAllManager.OnCancelAllFetchListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            try {
                CancelAllRequest request = new CancelAllRequest();
                CancelAllResponse response = (CancelAllResponse) model.sendRequest(request);
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
                    listener.OnCancelAllFetch();
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                listener.onCancelAllFetchError("Erreur lors de la récupération de l'article");
            }
        }
    }

    public interface OnCancelAllFetchListener {
        void OnCancelAllFetch() throws SQLException, IOException, ClassNotFoundException;
        void onCancelAllFetchError(String errorMessage);
    }
}
