package me.gettoucan.toucan;

import android.app.Activity;
import android.app.ListActivity;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TutorListActivity extends Activity {


    private int numberOfTutors=0;
    List<Tutor> listOfTutors = new ArrayList<Tutor>();
    ArrayList<String> data = new ArrayList<String>();
    private final String testWebsite = "http://jsonplaceholder.typicode.com/users";
    private InputStreamReader inReader;
    private InputStream stream;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.tutor_list);
        createObjects();
        buildData(readRawTextFile(this));
        createListView();
    }

    /*
     * creates the actual list view using the TutorListAdapter
     */
    public void createListView(){
        Log.v("","CREATING LSITVIEW");
        TutorListAdapter adapter = new TutorListAdapter(this, R.layout.tutor_list_item,listOfTutors);
        ListView listView = (ListView)findViewById(R.id.tutorListView);
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
     *Reads JSON text file. *Tester until API is set up*
     */
    public String readRawTextFile(Context ctx)
    {
        Log.v("","READING RAW TEXT");
        Resources res = getResources();
        InputStream inputStream = res.openRawResource(R.raw.jsonfile);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        return text.toString();
    }

    /*
     *gets json file from server
     */
    private void createObjects(){
        Log.v("","Creating Tutor objects");
//        try {
//            HttpClient httpclient = new DefaultHttpClient();
//            HttpResponse httpResponse = httpclient.execute(new
//                    HttpGet(testWebsite));
//            stream = httpResponse.getEntity().getContent();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "iso-8859-1"),8);
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while((line=reader.readLine())!=null){
//                sb.append(line+"\n");
//            }
//            stream.close();
//            String result = sb.toString();
//            System.out.println(result);
//            TextView firstName = (TextView)findViewById(R.id.firstName);
//            firstName.setText(result);
//            buildData(result);
//        } catch (ClientProtocolException e) {
//            Log.e("[AsyncTask]", "Network Error", e);
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e("[AsyncTask]", "Network Error", e);
//            e.printStackTrace();
//        }
    }


    /*
     *parses json file. Separates tutors and creates Tutor objects
     *for each tutor listed in the JSON file. Adds them to listOfTutors
     */
    private void buildData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray objectArray = object.getJSONArray("contacts");
            numberOfTutors = objectArray.length();
            for (int x = 0; x < numberOfTutors; x++) {
                Log.v("","ANOTHER TUTOR");
                JSONObject current = objectArray.getJSONObject(x);
                Tutor t = new Tutor(current);
                listOfTutors.add(t);
            }

        } catch (Exception e) {};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_list, menu);
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




}
