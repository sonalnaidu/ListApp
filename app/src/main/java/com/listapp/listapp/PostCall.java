package com.listapp.listapp;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Sonal on 2/16/2017.
 */
public class PostCall {

    void sendData(String title, String item){

        HttpURLConnection connection;
        OutputStreamWriter request = null;

        URL url;
        String response = null;
        String parameters = "title="+title+"items"+item;

        try
        {
            url = new URL("http://dev-lil-list.appspot.com/lists?id=id_of_an_list_object");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.write(parameters);
            request.flush();
            request.close();
            String line = "";
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            // Response from server after login process will be stored in response variable.
            response = sb.toString();
            // You can perform UI operations here
           // Toast.makeText(this,"Message from Server: \n"+ response, 0).show();
            Log.d("POST_OCCURED",response);
            isr.close();
            reader.close();

        }
        catch(IOException e)
        {
            // Error
        }
    }
    }


