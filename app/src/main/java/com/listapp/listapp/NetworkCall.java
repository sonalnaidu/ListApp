package com.listapp.listapp;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.internal.Excluder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tg on 27-08-2016.
 */
public class NetworkCall extends AsyncTask<String,Void,JSONArray> {
    @Override
    protected JSONArray doInBackground(String... params) {

        URL url;
        HttpURLConnection urlConnection;
        try {
            url = new URL(params[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            JSONArray res =  new JSONArray(readStream(in));

            urlConnection.disconnect();

            return res;

        } catch (Exception e)
        {e.printStackTrace();
        }

        return null;
    }


    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }


}
