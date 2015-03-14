package me.toucantutor.toucan.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.util.Requests;

/**
 * Created by aadil on 3/12/15.
 */
public class HttpTask extends AsyncTask<String,Void,Void> {

    private String url = "";
    private JsonObject object;

    public HttpTask(String json, String url) {
        this.url = url;
        this.object = object;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(final String... params) {
        //make request
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            // Request parameters and other properties.
            List<NameValuePair> parameters = new ArrayList<NameValuePair>(2);
            parameters.add(new BasicNameValuePair("param-1", "12345"));
            parameters.add(new BasicNameValuePair("param-2", "Hello!"));
            httppost.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    // do something useful
                } finally {
                    instream.close();
                }
            }
        } catch(Exception e) { }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }

}
