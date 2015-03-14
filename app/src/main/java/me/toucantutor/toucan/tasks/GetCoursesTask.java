package me.toucantutor.toucan.tasks;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.locationdata.DetermineLocation;

/**
 * Created by osama on 3/5/15.
 */
public class GetCoursesTask extends AsyncTask<Void, Void, Void> {

    private TaskCallback callback;
//    private static String baseUrl = "localhost:3000/api/v1/";
    private static String baseUrl = "http://beta-toucanapp.rhcloud.com/api/v1/";
    private static final String url = baseUrl + "sessions/getCourses";
    private List<String> courses;
    private boolean failure;

    public GetCoursesTask(TaskCallback callback) {
        this.callback = callback;
        failure = true;
    }



    @Override
    protected Void doInBackground(Void... params) {
        HttpPost post = new HttpPost();
        try {
            post.setURI(new URI(url));
            post.setHeader("Content-Type", "application/json; charset=utf-8");
//          post.setEntity(new StringEntity(new Gson().toJson(hashMap)));

            Gson gson = new Gson();
            JsonObject obj = new JsonObject();
            Location location = DetermineLocation.getLocation();

            obj.addProperty("latitude", location.getLatitude());
            obj.addProperty("longitude", location.getLongitude());

            StringEntity stringEntity = new StringEntity(obj.toString());
            post.setEntity(stringEntity);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // If successful.
                InputStream stream = httpResponse.getEntity().getContent();
                handleStream(stream);

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

    private void handleStream(InputStream stream) {
        InputStreamReader reader = new InputStreamReader(stream);
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        parseData(json);
    }

    private void parseData(JsonObject json) {
        boolean coursesFound = json.get("coursesFound").getAsBoolean();
        Log.d("coursesFound", ""+coursesFound);
        Log.d("~~~~~~~~~~~JSON:\n", json.toString());
        courses = new ArrayList<>();
        Log.d("parseData", json.toString());
        if (coursesFound) {
            failure = false;
            JsonElement courseData = json.get("courseData");
//            If there is only one found course, GSON treats courseData as a singular JsonObject
//            So if it can be treated as an array, iterate through
            if (courseData.isJsonArray()) {
                JsonArray outer = courseData.getAsJsonArray();
                for (JsonElement je : outer) {
                    Log.d("JsonElement je", je.toString());
                    JsonObject inner = je.getAsJsonObject();
                    String course = inner.get("coursename").getAsString();
                    String school = inner.get("school").getAsString();

                    String toAdd = course + "\n" + school;
                    courses.add(toAdd);
                }
            } else {
                JsonObject inner = courseData.getAsJsonObject();
                String course = inner.get("coursename").getAsString();
                String school = inner.get("school").getAsString();

                String toAdd = course + "\n" + school;
                courses.add(toAdd);
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (failure) {
            callback.taskFail();
        } else {
            callback.taskSuccess(courses);
        }

    }
}
