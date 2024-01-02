package com.example.purchaseclientandroid.networks;

import android.content.Context;
import android.util.Log;
import com.example.purchaseclientandroid.Models.ConfigPropety;
import com.example.purchaseclientandroid.networks.RequestObject.Request;
import com.example.purchaseclientandroid.networks.ResponseObject.LoginResponse;
import com.example.purchaseclientandroid.networks.ResponseObject.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Protocol {

    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket sClient;
    private final Context context;
    private OVESP OVESP = new OVESP(this);

    public Protocol(Context context) {
        this.context = context;
    }

    public void Send(String r) throws IOException {
        try {
            r += "!#";

            dos.write(r.getBytes());
            dos.flush();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'envoi des données: " + e.getMessage());
            throw e;
        }
    }

    public String Receive() throws IOException {
        StringBuilder data = new StringBuilder();
        boolean finished = false;
        byte lastByte = 0;

        while (!finished) {
            int byteRead = dis.read();
            if (byteRead == -1) {
                throw new IOException("Erreur de lecture");
            }

            char readChar = (char) byteRead;

            if (lastByte == '!' && readChar == '#') {
                finished = true;
            } else {
                data.append(readChar);
            }

            lastByte = (byte) readChar;
        }

        return data.toString();
    }

    public String Echange(String requete) throws IOException {
        // Envoie de la requête
        try {
            Send(requete);
        } catch (IOException e) {
            System.err.println("Erreur d'envoie : " + e.getMessage());
            sClient.close();
        }

        // Recevoir la réponse
        String reponse;
        try {
            reponse = Receive();
        } catch (IOException e) {
            System.err.println("Erreur de Receive : " + e.getMessage());
            sClient.close();
            throw e;
        }
        return reponse;
    }

    public void connectToServer() throws IOException {
        try {
            ConfigPropety cg = new ConfigPropety(context);
            sClient = new Socket("10.0.2.2", 50000);
            dos = new DataOutputStream(sClient.getOutputStream());
            dis = new DataInputStream(sClient.getInputStream());
        } catch (IOException e) {
            System.out.println("Erreur : " + e);
            throw e;
        }

    }

    public Response onRequete(Request request) throws IOException {
        Response response = this.OVESP.handleRequest(request);
        return response;
    }
}
