package com.example.tradoid.backend;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HttpUtils {

    private final String root = "http://10.12.11.28:8000/";

    private class SendGetAsyncTask extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... strings) {
            String urlString = strings[0];
            try {
                URL url = new URL(root + urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                conn.disconnect();

                return new Gson().fromJson(response.toString(), Response.class);
            } catch (Exception e) {
                return new Response(false, e);
            }
        }
    }

    private class SendPostAsyncTask extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... strings) {
            String urlString = strings[0];
            if (!urlString.isEmpty()){
                urlString += "/";
            }
            String payload = strings[1];
            try {
                URL url = new URL(root + urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Content-Length", String.valueOf(payload.length()));

                OutputStream os = conn.getOutputStream();
                os.write(payload.getBytes());
                os.flush();
                os.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();
                conn.disconnect();

                return new Gson().fromJson(response.toString(), Response.class);
            } catch (IOException e){
                return new Response(false, e);
            }
        }
    }

    public Response sendGet(String urlString) {
        SendGetAsyncTask task = new SendGetAsyncTask();
        task.execute(urlString);
        try{
            Response response = task.get();
            return response;
        } catch (ExecutionException | InterruptedException e) {
            return new Response(false, e);
        }
    }
    public Response sendPost(String urlString, Map<String, Object> params) {
        SendPostAsyncTask task = new SendPostAsyncTask();
        task.execute(urlString, new Gson().toJson(params));
        try {
            Response response = task.get();
            return response;
        } catch (ExecutionException | InterruptedException e) {
            return new Response(false, e);
        }
    }
}


