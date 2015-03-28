package me.toucantutor.toucan.views.tutorlist;

/**
 * Created by osama on 3/27/15.
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.AppActivity;
import me.toucantutor.toucan.util.Constants;
import me.toucantutor.toucan.util.Globals;
import me.toucantutor.toucan.views.session.StartSessionActivity;


public class TutorDetailActivity extends AppActivity implements TaskCallback {

    private Tutor tutor;
    private HttpTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);

        Intent received = getIntent();
        tutor = (Tutor) received.getSerializableExtra(Constants.TUTOR);
        createTask();
        createLayout();

    }

    private void createTask() {
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        JsonObject object = new JsonObject();
        object.addProperty("tutorId", tutor.getTutorId());
        object.addProperty("userId", Globals.getUserId());
        object.addProperty("course", Globals.getCourse().getCoursename());
        object.addProperty("studentPhone", "7709382274");
//        String jsonString = gson.toJson(object);
        task = new HttpTask(this, object, Constants.SELECT_TUTOR_URL);
        //should get JsonString from task. Set this.jsonString = return;
        //some Json string is returned
    }

    public void createLayout(){
        TextView name = (TextView) findViewById(R.id.tutorName);
        TextView email = (TextView) findViewById(R.id.tutorEmail);
        TextView certification = (TextView) findViewById(R.id.tutorCertification);
        ImageView certified = (ImageView) findViewById(R.id.tutorCertSymbol);
        TextView phone = (TextView) findViewById(R.id.tutorPhoneNumber);
//        TextView rating = (TextView) findViewById(R.id.tutorRating);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.tutorRatingBar);
        TextView distance = (TextView) findViewById(R.id.tutorDistance);
        TextView price = (TextView) findViewById(R.id.tutorPrice);
        Button choose = (Button) findViewById(R.id.choose);

        name.setText(tutor.getName().toUpperCase());
        email.setText(tutor.getEmail());

        if (tutor.isCertified()) {
            certification.setText("Certified");
            certification.setPadding(82,10,0,10);
            certified.setVisibility(View.VISIBLE);
        } else {
//            certification.setText("Uncertified");
            certification.setVisibility(View.INVISIBLE);
            certified.setVisibility(View.INVISIBLE);
        }
        phone.setText("" + tutor.getPhoneNumber());
        ratingBar.setRating(tutor.getRating().floatValue());
        distance.setText(tutor.getDistance() + " mi.");

        String priceText = String.format("$%3.2f", tutor.getRate());
        price.setText(priceText);
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

    @Override
    public void taskSuccess(JsonObject json) {

    }

    @Override
    public void taskFail(JsonObject json) {

    }
}
