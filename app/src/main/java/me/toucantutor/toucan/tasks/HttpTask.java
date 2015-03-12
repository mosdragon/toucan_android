package me.toucantutor.toucan.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.util.Requests;

/**
 * Created by aadil on 3/12/15.
 */
public class HttpTask extends AsyncTask<String,Void,Void> {

    String url = "";
    JsonObject object;

    public HttpTask(JsonObject json, String url){
        this.url = url;
        this.object = object;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(final String... params) {
        //make request
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

    }

}
