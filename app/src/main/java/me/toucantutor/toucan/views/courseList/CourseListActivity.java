package me.toucantutor.toucan.views.courseList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import me.toucantutor.toucan.views.tutorlist.HomeScreenActivity;
import me.toucantutor.toucan.views.tutorlist.TutorChooseSubjectsActivity;
import me.toucantutor.toucan.views.tutorlist.TutorListActivity;

public class CourseListActivity extends ActionBarActivity implements TaskCallback {

    private static final String url = "sessions/getCourses";
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    public void taskSuccess(JsonObject json) {
        Log.d("taskSuccess called", "-----true");
        parseData(json);
        ListView listView = (ListView) findViewById(R.id.courseList);
        ListAdapter listAdapter = new CourseAdapter(this,
                R.layout.activity_course_list, courses);
//        new ArrayAdapter<String>()
        listView.setAdapter(listAdapter);

//        ListView listOfSubjects = (ListView) findViewById(R.id.chooseSubjectList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tutor_choose_subject_item, R.id.subject,list);
    }

    private void parseData(JsonObject json) {
        if (json != null) {
            boolean coursesFound = json.get("coursesFound").getAsBoolean();
            courses = new ArrayList<>();
            if (coursesFound) {
                JsonElement courseData = json.get("courseData");
//            If there is only one found course, GSON treats courseData as a singular JsonObject
//            So if it can be treated as an array, iterate through
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
//            TextView textView = (TextView) findViewById(R.id.listHolder);
//            textView.setText("Could not find any courses near you. Please get closer to the " +
//                    "University of Georgia campus or try again later for better results.");
        }
    }

    private void setList() {
        //updates list of selected subjects
        ListView courseList = (ListView) findViewById(R.id.courseList);
        ArrayAdapter<Course> adapter = new ArrayAdapter<>(this,
                R.layout.course_item, R.id.subject, courses);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseListActivity.this, TutorListActivity.class);
                intent.putExtra("COURSE", courses.get(position));
                startActivity(intent);
            }
        });
        //choose button listener and handles error
        courseList.setAdapter(adapter);
    }

    @Override
    public void taskFail(JsonObject json) {
        Log.d("taskFail called", "-----true");
//        TextView textView = (TextView) findViewById(R.id.listHolder);
//        textView.setText("Could not find any courses near you. Please get closer to the " +
//                "University of Georgia campus or try again later for better results.");
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
