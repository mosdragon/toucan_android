package me.gettoucan.toucan;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
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


public class TutorList extends Activity {



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
        System.out.println();
        fillContents();
        buildData(readRawTextFile(this));
    }

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
    private void buildData(String jsonString) {
        System.out.println("bruild");

        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray objectArray = object.getJSONArray("contacts");
            for(int x=0;x<objectArray.length();x++){
                System.out.println("----------------------");
                JSONObject current = objectArray.getJSONObject(x);
                Tutor t = new Tutor(current);

            }

        }catch(Exception e){};



//        Gson gson = new GsonBuilder()
//                .serializeNulls()
//                .setDateFormat(DateFormat.MEDIUM)
//                .setPrettyPrinting()
//                .setVersion(1.0)
//                .create();
//        JsonReader jsReader = new JsonReader(new StringReader(jsonString));
//        jsReader.setLenient(true);
//        JsonObject json = gson.fromJson(jsReader, JsonObject.class);
//        //System.out.println(json+" this is the first objkect");
//        String root = "list";
//        String arrayName = "item";

//        JsonArray jsonObjects = json.getAsJsonObject(root).getAsJsonArray(arrayName);
//
//        ArrayList<Tutor> list = new ArrayList<Tutor>();
//        JsonObject item =jsonObjects.get(0).getAsJsonObject();
//        System.out.println(item+" this is the jsonobject");
//        Tutor tutor1 = new Tutor(item);


//        if (restaurantItems != null) {
//            restaurants = new ArrayList<Restaurant>(restaurantItems.size());
//
//            for (int i = 0; i < restaurantItems.size(); i++) {
//                JsonObject item = restaurantItems.get(i).getAsJsonObject();
//                Restaurant restaurant = new Restaurant(item);
//                //restaurant.createFullAddress(activity);
//                restaurants.add(restaurant);
//            }
//        }
    }

    private void fillContents(){
        TextView firstName = (TextView)findViewById(R.id.firstName);
        TextView lastName = (TextView)findViewById(R.id.lastName);
        TextView rating = (TextView)findViewById(R.id.rating);
        TextView price = (TextView)findViewById(R.id.price);


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
