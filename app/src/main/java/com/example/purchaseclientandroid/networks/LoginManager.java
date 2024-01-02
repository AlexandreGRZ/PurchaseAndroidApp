package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.example.purchaseclientandroid.Models.Model;
import com.example.purchaseclientandroid.networks.RequestObject.LoginRequest;
import com.example.purchaseclientandroid.networks.ResponseObject.LoginResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.Response;

import java.io.IOException;
import java.sql.SQLException;

public class LoginManager {
    private Context context;
    private Model model;

    public LoginManager(Context context) throws SQLException, IOException, ClassNotFoundException {
        this.context = context;
        this.model = Model.getInstance(context);
    }

    public void performLoginAsync(String username, String password, OnLoginCompleteListener listener) {
        new LoginTask(listener).execute(username, password);
    }

    private class LoginTask extends AsyncTask<String, Void, Void> {
        private final OnLoginCompleteListener listener;

        public LoginTask(OnLoginCompleteListener listener) {
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d("AsyncTask", "doInBackground started");
            LoginResponse response = new LoginResponse(false,"ERROR IN MESSAGE");
            if (params[0] == null || params[0].equals("") || params[1] == null || params[1].equals("")){
                listener.onLoginFailed();
                return null;
            }

            try {
                response = (LoginResponse) model.sendRequest(new LoginRequest(params[0], params[1], false));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (listener != null) {
                if ((response.isLoginYesOrNot()))
                    listener.onLoginComplete();
                else
                    listener.onLoginFailed();
            }

            return null;
        }
    }

    public interface OnLoginCompleteListener {
        void onLoginComplete();

        void onLoginFailed();
    }




}
