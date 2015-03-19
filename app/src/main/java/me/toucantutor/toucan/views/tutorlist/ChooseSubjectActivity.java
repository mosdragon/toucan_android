package me.toucantutor.toucan.views.tutorlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.Constants;


public class ChooseSubjectActivity extends Activity implements TaskCallback {

    private String[] newSubjectList;
    private String[] subjectList = {"Calculus 1", "Calculus II", "Physics I", "Physics II", "Chemistry I", "Biology I"};
    private ArrayList<Long> chosenList = new ArrayList<Long>(subjectList.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_choose_subjects);
        getCourses();
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjectList.length; ++i) {
            list.add(subjectList[i]);
        }
        //updates list of selected subjects
        ListView listOfSubjects = (ListView) findViewById(R.id.chooseSubjectList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tutor_choose_subject_item, R.id.subject,list);
        listOfSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplication(),TutorListActivity.class);
                startActivity(i);
            }
        });
        //choose button listener and handles error
        listOfSubjects.setAdapter(adapter);


    }

    /*
     * gets a list of the courses being offered in the area.
     * Actual version--->delete hard-coded array, keep newsubjectList
     */
    public JsonObject getCourses(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("latitude", 33);
        object.addProperty("longitude", 55);
        String jsonString = gson.toJson(object);
        HttpTask task = new HttpTask(this, object, Constants.GET_COURSES_URL);
        //should get JsonString from task. Set this.jsonString = return;
        String returnedJsonString = "[ [\"SPAN 1001\", \"University of Georgia(UGA)\"], [\"PSYC 2001\", \"University of Georgia(UGA)\"], ]";

        JsonElement elem = new JsonParser().parse(returnedJsonString);
        JsonArray array1 = elem.getAsJsonArray();
        int numberOfCourses = array.size();
        newSubjectList = new String[numberOfCourses];
        for(int x=0;x<numberOfCourses;x++){
            JsonObject courseObject = array.get(x).getAsJsonObject();
            String course = courseObject.get("name").getAsString();
            newSubjectList[x]=course;
        }
        return null;
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

    }

    @Override
    public void taskFail(JsonObject json) {

    }
}
