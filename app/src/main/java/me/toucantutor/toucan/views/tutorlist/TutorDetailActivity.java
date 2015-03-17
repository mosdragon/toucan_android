package me.toucantutor.toucan.views.tutorlist;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.util.AppConstants;
import me.toucantutor.toucan.views.session.StartSessionActivity;


public class TutorDetailActivity extends ActionBarActivity {

    private Tutor chosenTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        selectTutorRequest();
        Bundle b = getIntent().getExtras();
        chosenTutor = (Tutor)b.getSerializable("tutorChosen");
        createLayout();

    }

    public JsonObject selectTutorRequest(){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("tutorId", 3234113);
        object.addProperty("userId", 551234);
        object.addProperty("course", "Physics I");
        object.addProperty("studentPhone", "7709382274");
        String jsonString = gson.toJson(object);
       // HttpTask task = new HttpTask(jsonString, AppConstants.SELECT_TUTOR_URL);
        //should get JsonString from task. Set this.jsonString = return;
        //some Json string is returned
        return null;
    }

    public void createLayout(){
        TextView name = (TextView)findViewById(R.id.tutorName);
        TextView email = (TextView)findViewById(R.id.tutorEmail);
        TextView certification = (TextView)findViewById(R.id.tutorCertification);
        ImageView certified = (ImageView)findViewById(R.id.tutorCertSymbol);
        TextView phone = (TextView)findViewById(R.id.tutorPhoneNumber);
//        TextView rating = (TextView)findViewById(R.id.tutorRating);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.tutorRatingBar);
        TextView distance = (TextView)findViewById(R.id.tutorDistance);
        TextView price = (TextView)findViewById(R.id.tutorPrice);
        Button choose = (Button)findViewById(R.id.choose);

        name.setText(chosenTutor.getName().toUpperCase());
        email.setText(chosenTutor.getEmail());
        if(chosenTutor.getCertification()==true){
            certification.setText("Certified");
            certification.setPadding(82,10,0,10);
            certified.setVisibility(View.VISIBLE);
        }
        else{
            certification.setText("Uncertified");
            certified.setVisibility(View.INVISIBLE);
        }
        phone.setText("" + chosenTutor.getPhoneNumber());
        ratingBar.setRating(chosenTutor.getRating().floatValue());
        distance.setText(chosenTutor.getDistance().intValue() + " mi.");
        price.setText("$" + chosenTutor.getPrice().intValue());
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tutor has been chosen. Send result to db
                Intent intent = new Intent(TutorDetailActivity.this, StartSessionActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_detail, menu);
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