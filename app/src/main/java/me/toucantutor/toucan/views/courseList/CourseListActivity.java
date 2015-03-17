package me.toucantutor.toucan.views.courseList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.AppConstants;
import me.toucantutor.toucan.views.tutorlist.TutorListActivity;

public class CourseListActivity extends Activity implements TaskCallback {

    private String[] newSubjectList;
    private String[] subjectList = {"Calculus 1", "Calculus II", "Physics I", "Physics II", "Chemistry I", "Biology I"};
    private ArrayList<String> courseNameList = new ArrayList<String>();
    private ArrayList<Course> courses = new ArrayList<Course>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        getCourses();
    }

    public void createCourseList(){

        ListView courseList = (ListView) findViewById(R.id.courseList);
        ListAdapter listAdapter = new CourseAdapter(this,R.layout.activity_course_list, courses);
        courseList.setAdapter(listAdapter);

        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CourseListActivity.this, TutorListActivity.class);
                intent.putExtra("chosenCourse", courses.get(position).getCoursename());
                startActivity(intent);
            }
        });


    }
    /*
     * gets a list of the courses being offered in the area.
     * Actual version--->delete hard-coded array, keep newsubjectList
     */
    public void getCourses(){
        Gson gson = new Gson();
        JsonObject object = new JsonObject();
        //DetermineLocation.getLatitude();
        object.addProperty("latitude", 33.948005);
        object.addProperty("longitude", -83.377322);
        Log.v("","JSON SENT TO SERVER "+gson.toJson(object));
        HttpTask task = new HttpTask(this, object, AppConstants.GET_COURSES_URL);
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_choose_subjects, menu);
        return true;
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
    public void taskSuccess(JsonObject json) {
        Log.v("","MAHHH NIGAAAAAAAAAAA"+json.toString());
        String returnedJsonString = "[ [\"SPAN 1001\", \"University of Georgia(UGA)\"], [\"PSYC 2001\", \"University of Georgia(UGA)\"], ]";
        JsonArray courseArray = json.get("courseData").getAsJsonArray();
        for(int x=0;x<courseArray.size();x++){
            Course c = new Course(courseArray.get(x).getAsJsonObject());
            courses.add(c);
        }
        createCourseList();
    }

    @Override
    public void taskFail(JsonObject json) {
        Log.v("","NOOOOO");

    }
}
