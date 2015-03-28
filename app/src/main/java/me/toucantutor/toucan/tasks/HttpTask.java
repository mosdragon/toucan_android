package me.toucantutor.toucan.tasks;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.gcm.Task;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.util.Constants;
import me.toucantutor.toucan.util.Requests;

/**
 * Created by aadil on 3/12/15.
 */
public class HttpTask extends AsyncTask<Void,Void,Void> {

    public static final String BASEURL = Constants.BASEURL;

    private JsonObject json;
    private String url;
    private JsonObject response;
    private TaskCallback callback;
    private boolean failed;

    public HttpTask(TaskCallback callback, JsonObject json, String url) {
        this.callback = callback;
        this.json = json;
        this.url = BASEURL + url;
        failed = true;
    }

    @Override
    protected Void doInBackground(Void... params) {
        HttpPost post = new HttpPost();
        try {
            post.setURI(new URI(url));
            post.setHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity stringEntity = new StringEntity(json.toString());
            post.setEntity(stringEntity);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // If successful.
                InputStream stream = httpResponse.getEntity().getContent();
                InputStreamReader reader = new InputStreamReader(stream);
                Gson gson = new Gson();
                response = gson.fromJson(reader, JsonObject.class);
                failed = false;

            } else {
                Log.d("doInBackground", "StatusCode not 200");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (callback != null) {
            if (failed) {
                callback.taskFail(response);
            } else {
                callback.taskSuccess(response);
            }
        }
    }

}
