package me.toucantutor.toucan.views.courseList;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.views.customTutorList.TutorListActivity;

public class CourseListActivity extends ActionBarActivity implements TaskCallback {

    private static final String url = "sessions/getCourses";
    private static final String failMsg = "It looks like we weren't able to find any courses " +
            "available near you at this time. Please try searching closer to the University " +
            "of Georgia campus for better results during our beta. Thanks!";
    private List<Course> courses;
    private HttpTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        JsonObject json = new JsonObject();
        Location location = DetermineLocation.getLocation();
        json.addProperty("latitude", location.getLatitude());
        json.addProperty("longitude", location.getLongitude());

        task = new HttpTask(this, json, url);
        task.execute();
    }

    @Override
    public void taskSuccess(JsonObject json) {
        Log.d("taskSuccess called", "-----true");
        parseData(json);
    }

    private void parseData(JsonObject json) {
        if (json != null) {
            boolean coursesFound = json.get("coursesFound").getAsBoolean();
            courses = new ArrayList<>();
            if (coursesFound) {
                JsonElement courseData = json.get("courseData");
//                If there is only one found course, GSON treats courseData as a singular JsonObject
//                So if it can be treated as an array, iterate through
                if (courseData.isJsonArray()) {
                    JsonArray outer = courseData.getAsJsonArray();
                    for (JsonElement je : outer) {
                        JsonObject courseInfo = je.getAsJsonObject();
                        Course course = new Course(courseInfo);
                        courses.add(course);
                    }
                } else {
                    JsonObject courseInfo = courseData.getAsJsonObject();
                    Course course = new Course(courseInfo);
                    courses.add(course);
                }

                setList();
            }
        }  else {
//            Do same thing as taskFail
            Toast.makeText(getApplicationContext(), failMsg, Toast.LENGTH_LONG).show();
        }
    }

    private void setList() {
        ListView courseList = (ListView) findViewById(R.id.courseList);
        ListAdapter listAdapter = new CourseAdapter(this,
                R.layout.activity_course_list, courses);
        courseList.setAdapter(listAdapter);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseListActivity.this, TutorListActivity.class);
                intent.putExtra("COURSE", courses.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void taskFail(JsonObject json) {
        Log.d("taskFail called", "-----true");
        Toast.makeText(getApplicationContext(), failMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (task != null) {
            task.cancel(true);
        }
    }
}
