package me.gettoucan.toucan;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TutorList extends FragmentActivity {

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private int numberOfTutors=0;
    ArrayList<Tutor> listOfTutors = new ArrayList<Tutor>();
    ArrayList<String> data = new ArrayList<String>();
    private final String testWebsite = "http://jsonplaceholder.typicode.com/users";
    private InputStreamReader inReader;
    private InputStream stream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //fetch tutors that are tutoring subject x from database
        //create that many objects and put in array/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_list);

        createObjects();
        buildData(readRawTextFile(this));

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));




    }

    /*
     *Reads JSON text file. *Tester until API is set up*
     */
    public String readRawTextFile(Context ctx)
    {
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
    System.out.println("in create object method");
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
                System.out.println("----------------------");
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



    /*
     *Adapter for viewPager.
     */
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /*
         *creates instance of TutorFragment, passing in the current tutor
         */
        @Override
        public Fragment getItem(int pos) {
            Tutor currentTutorShowing = listOfTutors.get(pos);
            return TutorFragment.newInstance(currentTutorShowing);
        }

        @Override
        public int getCount() {
            return numberOfTutors;
        }
    }

}
