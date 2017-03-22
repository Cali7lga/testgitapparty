package com.example.home.weddingapp.Others;

import android.os.AsyncTask;
import android.util.Log;

import com.stripe.android.model.Token;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DatabaseTask extends AsyncTask<String, Void, String> {

    String command = "";
    Token token;
    String amount = "";
    String desc = "";

    public DatabaseTask(String mCommand, Token mToken, String mAmount, String mDescription) {
        command = mCommand;
        token = mToken;
        amount = mAmount;
        desc = mDescription;
    }

    @Override
    protected String doInBackground(String... strings) {

        String echoData = "";

        if (command.equals("SENDTOKEN")) {
            try {
                URL url = new URL("https://herasoft.com.br/charge");
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());

                dStream.writeBytes("stripeToken=" + token.getId() + "&amount=" + amount + "&description=" + desc);
                dStream.flush();
                dStream.close();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                echoData = responseOutput.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return echoData;
    }

    protected void onPostExecute(String mData) {
        Log.e("DatabaseTask", "onPostExecute result: " + mData);
    }
}
