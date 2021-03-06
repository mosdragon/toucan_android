package me.toucantutor.toucan.views.session;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.util.AppActivity;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.Constants;
import me.toucantutor.toucan.util.Globals;
import me.toucantutor.toucan.views.tutorlist.Tutor;

/*
 * Right now this screen is pretty useless lol. But later if we integrate canceling
 * then it will be useful for allowing the tutee to cancel the request and the tutor
 * to reject the request.
 */

public class StartSessionActivity extends AppActivity implements TaskCallback{


    private boolean isTutor;
    private Tutor tutor;
    private Long sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);
        Bundle b = getIntent().getExtras();
        tutor = (Tutor) b.getSerializable("tutor");
        getSessionData();
        Location l = DetermineLocation.getLocation();
        //for now I'm only dealing with them starting a new session
        //for old sessions, I will need the session time and the users in the session
        setDisplay(Globals.getState() == Globals.State.TUTOR);
    }

    public void getSessionData() {
        Gson gson = new Gson();
        JsonObject object = new JsonObject();

        String dummyId = "903778";
        String dummyNumber = "6786025306";

        object.addProperty("userId", dummyId);
        object.addProperty("studentPhone", dummyNumber);
        object.addProperty("tutorId", tutor.getTutorId());
        object.addProperty("course", tutor.getCourse());
        Log.v("", "JSON SENT TO SERVER " + gson.toJson(object));
        HttpTask task = new HttpTask(this, object, Constants.SELECT_TUTOR_URL);
        task.execute();
    }

    private void setDisplay(boolean isTutor) {
        if(isTutor) {
            setContentView(R.layout.activity_start_tutorsession);
            setRequestDetails();
        }
        //otherwise the default screen is for tutees.
    }

    private void setRequestDetails() {
        String course = ((TextView)findViewById(R.id.course_name)).getText().toString();
        String tuteeName = ((TextView)findViewById(R.id.tutee_name)).getText().toString();
        course += " CS 3251";
        tuteeName += " Mashal";
        //make that actually get the request values, once i figure out how those are passed in.
        ((TextView)findViewById(R.id.course_name)).setText(course);
        ((TextView)findViewById(R.id.tutee_name)).setText(tuteeName);
    }

    public void continueSession(View view) {
        Intent i = new Intent(this, SessionInProgressActivity.class);
        i.putExtra("isTut", isTutor);
        startActivity(i);
    }

    @Override
    public void taskSuccess(JsonObject json) {
        Log.v("","JSON PASSED BACK"+json.toString());
        String phone = json.get("tutorPhone").getAsString();
        String name = json.get("tutorName").getAsString();
        String course = json.get("course").getAsString();
        this.sessionId = json.get("sessionId").getAsLong();
    }

    @Override
    public void taskFail(JsonObject json) {
        Log.v("", "NOOOOO");

    }


}
