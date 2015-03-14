package me.toucantutor.toucan.views.courseList;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.tasks.GetCoursesTask;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;

public class CourseListActivity extends ActionBarActivity implements TaskCallback {

    private List<String> courses;
    private HttpTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
//        GetCoursesTask task = new GetCoursesTask(this);
//        task.execute();

        JsonObject json = new JsonObject();
        Location location = DetermineLocation.getLocation();
        json.addProperty("latitude", location.getLatitude());
        json.addProperty("longitude", location.getLongitude());

        task = new HttpTask(this, json, "sessions/getCourses");
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    public void taskSuccess(JsonObject json) {
        Log.d("taskSuccess called", "-----true");
//        List<String> courses = (List<String>) input[0];
        parseData(json);
        ListView listView = (ListView) findViewById(R.id.course_list);
        ListAdapter listAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_course_list, R.id.listHolder, courses);
//        new ArrayAdapter<String>()
        listView.setAdapter(listAdapter);
    }

    private void parseData(JsonObject json) {
        boolean coursesFound = json.get("coursesFound").getAsBoolean();
        courses = new ArrayList<>();
        if (coursesFound) {
            JsonElement courseData = json.get("courseData");
//            If there is only one found course, GSON treats courseData as a singular JsonObject
//            So if it can be treated as an array, iterate through
            if (courseData.isJsonArray()) {
                JsonArray outer = courseData.getAsJsonArray();
                for (JsonElement je : outer) {
                    Log.d("Course and School", je.toString());
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
    public void taskFail(JsonObject json) {
        Log.d("taskFail called", "-----true");
        TextView textView = (TextView) findViewById(R.id.listHolder);
        textView.setText("Could not find any courses near you. Please get closer to the " +
                "University of Georgia campus or try again later for better results.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        task.cancel(true);
    }
}
