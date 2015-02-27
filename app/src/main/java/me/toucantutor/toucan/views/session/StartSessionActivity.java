package me.toucantutor.toucan.views.session;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.toucantutor.toucan.R;

/*
 * Right now this screen is pretty useless lol. But later if we integrate canceling
 * then it will be useful for allowing the tutee to cancel the request and the tutor
 * to reject the request.
 */

public class StartSessionActivity extends ActionBarActivity {


    private boolean isTutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_session);

        isTutor = false;
        isTutor = true;
        //for now I'm only dealing with them starting a new session
        //for old sessions, I will need the session time and the users in the session
        setDisplay(isTutor);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_session, menu);
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

        nextActivity();
    }



    private void nextActivity() {
        Intent i = new Intent(this, SessionInProgressActivity.class);
        i.putExtra("isTut", isTutor);
        startActivity(i);
    }
}
