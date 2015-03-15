package me.toucantutor.toucan.views.customTutorList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.AppConstants;
import me.toucantutor.toucan.views.courseList.Course;
import me.toucantutor.toucan.views.tutorlist.CompareTutors;
import me.toucantutor.toucan.views.tutorlist.Tutor;
import me.toucantutor.toucan.views.tutorlist.TutorDetailActivity;
import me.toucantutor.toucan.views.tutorlist.TutorListAdapter;

/**
 * Created by osama on 3/14/15.
 */

public class TutorListActivity extends ActionBarActivity implements TaskCallback {

    private int numberOfTutors=0;
    private List<Tutor> listOfTutors = new ArrayList<Tutor>();
    private ArrayList<String> data = new ArrayList<String>();
    private final String testWebsite = "http://jsonplaceholder.typicode.com/users";
    private InputStreamReader inReader;
    private InputStream stream;
    private TutorListAdapter adapter;
    private ListView listView;

    private static final String url = "sessions/findActiveTutors";
    private static final int miles = 65;
    private static final String failMsg = "It looks like we weren't able to find any tutors " +
            "available near you at this time. Please try searching later or" +
            " closer to the University of Georgia campus for better results during" +
            " our beta. Thanks!";

    private Course chosenCourse;
    private List<Tutor> tutors;
    private HttpTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_tutor_list);
//        findActiveTutors();
//        buildData(readRawTextFile(this));
        Intent usedIntent = getIntent();
        if (usedIntent != null) {
            chosenCourse = (Course) usedIntent.getSerializableExtra("COURSE");

            if (chosenCourse != null) {
                JsonObject json = new JsonObject();
                Location location = DetermineLocation.getLocation();
                json.addProperty("latitude", location.getLatitude());
                json.addProperty("longitude", location.getLongitude());
                json.addProperty("course", chosenCourse.getCoursename());
                json.addProperty("miles", miles);

                Log.d("chosenCourse", chosenCourse.getCoursename());
                Log.d("json", json.toString());

                task = new HttpTask(this, json, url);
                task.execute();

            }
        }
    }

    /*
     *Sorts the list of tutors based on the string param passed in
     * Updates the Listview immediately
     */
    public void sortList(String sortBy){
        CompareTutors t = new CompareTutors();
        if(sortBy.equals("name")){
            Collections.sort(listOfTutors,t.NAME);
        }
        else if(sortBy.equals("price")){
            Collections.sort(listOfTutors,t.PRICE);
        }
        else if(sortBy.equals("rating")){
            Collections.sort(listOfTutors,t.RATING);
        }
        else if(sortBy.equals("distance")){
            Collections.sort(listOfTutors,t.DISTANCE);
        }
        this.listView.setAdapter(this.adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_list, menu);
        return true;
    }

    /*
     *Handles ActionBar item clicks. (sorting)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sortName) {
            sortList("name");
            return true;
        }
        else if (id == R.id.sortRating) {
            sortList("rating");
            return true;
        }
        else if (id == R.id.sortPrice) {
            sortList("price");
            return true;
        }
        else if (id == R.id.sortDistance) {
            sortList("distance");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void taskSuccess(JsonObject json) {
        parseData(json);
    }

    private void parseData(JsonObject json) {
        if (json != null) {
            Log.d("parseData", json.toString());
            boolean foundTutors = json.get("foundTutors").getAsBoolean();
            tutors = new ArrayList<>();
            if (foundTutors) {
                Log.d("TutorListActivity", "foundTutors");
                JsonElement tutorsData = json.get("tutors");
//                If there is only one found course, GSON treats tutorsData as a singular JsonObject
//                So if it can be treated as an array, iterate through

                if (tutorsData.isJsonArray()) {
                    JsonArray outer = tutorsData.getAsJsonArray();
                    for (JsonElement je : outer) {
                        JsonObject tutorInfo = je.getAsJsonObject();
                        Tutor tutor = new Tutor(tutorInfo);
                        tutors.add(tutor);
                    }
                } else {
                    JsonObject tutorInfo = tutorsData.getAsJsonObject();
                    Tutor tutor = new Tutor(tutorInfo);
                    tutors.add(tutor);
                }

                setList();
            } else {
                Log.d("TutorListActivity", "foundTutors FALSE");
                Toast.makeText(getApplicationContext(), failMsg, Toast.LENGTH_LONG).show();
                this.finish();
            }
        }  else {
//            Do same thing as taskFail
            Toast.makeText(getApplicationContext(), failMsg, Toast.LENGTH_LONG).show();
            this.finish();
        }
    }

    /*
    * creates the actual list view using the TutorListAdapter
    */
    public void setList(){
        Log.d("TutorListActivity","settingList");
        adapter = new TutorListAdapter(this, R.layout.tutor_item, tutors);
        listView = (ListView)findViewById(R.id.tutorListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("","CLICKED TUTOR");
                Intent intent = new Intent(getApplication(), TutorDetailActivity.class);
                intent.putExtra("TUTOR", listOfTutors.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void taskFail(JsonObject json) {
        Toast.makeText(getApplicationContext(), failMsg, Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (task != null) {
            task.cancel(true);
        }
    }
}
