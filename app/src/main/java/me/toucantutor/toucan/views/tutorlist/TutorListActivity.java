package me.toucantutor.toucan.views.tutorlist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
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
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.AppConstants;
import me.toucantutor.toucan.util.CompareTutors;
import me.toucantutor.toucan.views.courseList.Course;


public class TutorListActivity extends ActionBarActivity implements TaskCallback{

    private String courseChosen;
    private int numberOfTutors=0;
    private List<Tutor> listOfTutors = new ArrayList<Tutor>();
    private ArrayList<String> data = new ArrayList<String>();
    private InputStreamReader inReader;
    private InputStream stream;
    private TutorListAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bundle extras = getIntent().getExtras();
        courseChosen = extras.getString("chosenCourse");
        setContentView(R.layout.activity_tutor_list);
        findActiveTutors();
       // buildData(readRawTextFile(this));
       // createListView();
    }

    //need to get the parameters needed for the request
    public void findActiveTutors(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("latitude", AppConstants.DUMMY_LAT);
        object.addProperty("longitude", AppConstants.DUMMY_LONG);
        object.addProperty("userId", AppConstants.DUMMY_ID);
        object.addProperty("course", courseChosen);
        object.addProperty("expectedDist", "10 miles away"); //not exactly sure what expectedDist does
        Log.v("","QQQQQQQQQQQQQQQq "+object.toString());
        HttpTask task = new HttpTask(this, object, AppConstants.FIND_ACTIVE_TUTOR_URL);
        task.execute();
    }

    /*
     *parses json file. Separates tutors and creates Tutor objects
     *for each tutor listed in the JSON file. Adds them to listOfTutors
     */
    private void buildData(String jsonString) {
        try {
            JsonElement elem = new JsonParser().parse(jsonString);
            JsonObject entireObject = elem.getAsJsonObject();
            JsonElement arrayElement = entireObject.get("contacts");
            JsonArray array = arrayElement.getAsJsonArray();
            numberOfTutors = array.size();
            for(int x=0;x<numberOfTutors;x++){
                JsonObject tutorObject = array.get(x).getAsJsonObject();
                Tutor t = new Tutor(tutorObject);
                listOfTutors.add(t);
            }
        } catch (Exception e) {};
    }

    /*
     * creates the actual list view using the TutorListAdapter
     */
    public void createListView(){
        Log.v("","CREATING LSITVIEW");
        adapter = new TutorListAdapter(this, R.layout.tutor_item,listOfTutors);
        listView = (ListView)findViewById(R.id.tutorListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("","CLICKED TUTOR");
                Intent i = new Intent(getApplication(), TutorDetailActivity.class);
                i.putExtra("tutorChosen", listOfTutors.get(position));
                startActivity(i);
            }
        });
    }

    /*
     *Sorts the list of tutors based on the string param passed in
     * Updates the listview immediately
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
     *handles ActionBar item clicks. (sorting)
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

    /*
     *Reads JSON text file. *Tester until API is set up*
     */


    @Override
    public void taskSuccess(JsonObject json) {
        Log.v("","JSON PASSED BACK"+json.toString());
        boolean foundTutors = json.get("foundTutors").getAsBoolean();
        if(!foundTutors){
            //dialog box- not tutors available
        }
        JsonArray tutorArray = json.get("tutors").getAsJsonArray();
        numberOfTutors = tutorArray.size();
        for(int x=0;x<numberOfTutors;x++){
            JsonObject tutorObject = tutorArray.get(x).getAsJsonObject();
            Tutor t = new Tutor(tutorObject);
            listOfTutors.add(t);
        }
        createListView();
    }

    @Override
    public void taskFail(JsonObject json) {
        Log.v("","NOOOOO");

    }

}




//    public String readRawTextFile(Context ctx)
//    {
//        Log.v("","READING RAW TEXT");
//        Resources res = getResources();
//        InputStream inputStream = res.openRawResource(R.raw.jsonfile);
//
//        InputStreamReader inputreader = new InputStreamReader(inputStream);
//        BufferedReader buffreader = new BufferedReader(inputreader);
//        String line;
//        StringBuilder text = new StringBuilder();
//
//        try {
//            while (( line = buffreader.readLine()) != null) {
//                text.append(line);
//                text.append('\n');
//            }
//        } catch (IOException e) {
//            return null;
//        }
//        return text.toString();
//    }