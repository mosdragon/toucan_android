package me.toucantutor.toucan.views.session;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.toucantutor.toucan.R;

public class SessionInProgressActivity extends ActionBarActivity {

    //stuff to finish:
    //implement the popup window
    //implement the button being clicked for tutees showing the popup
    //implement the timer and saving it (look into sharedpref and such
    //implement the changing of the begin button to an end button
    //and the onclick listeners that go with that and the popup
    //reminders for that.


    private boolean isTutor;
    private boolean isInSession = false;

    private long startTime = 0;
    private TextView timerTextView;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int hours = seconds/3600;
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timerTextView.setText(String.format("%d:%02d:%02d", hours, minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_in_progress);   // isTutor = false;
        isTutor = true;
        setDisplay(isTutor, isInSession);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_session_in_progress, menu);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
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


    public void endSession() {
        displayEndMessage(isTutor);
        nextActivity();
    }

    private void displayEndMessage(boolean isTut) {
        if(!isTut) {
            //show a popup dialog that emphasizes that they have to end the session.
        } else if (isTut) {
            //show the dialog asking them to remind the other person to end as well
        }
    }

    private void nextActivity() {
        Intent i = new Intent(this, SessionSummaryActivity.class);
        i.putExtra("isTut", isTutor);
        startActivity(i);
    }


    private void setDisplay(boolean isTutor, boolean isInSession) {
        if(isInSession) {
            displayOldSession();
        }
        else if(isTutor) {
            setContentView(R.layout.activity_tutorsession_in_progress);
            setRequestDetails();
            displayPopup();
        }
        //otherwise the default screen is for tutees.
    }

    private void displayPopup() {

    }

    private void displayTuteePopup() {
        
    }

    public void handleSessionButton(View view) {
        Button sessionButt = (Button) view;
        String begin = getString(R.string.begin_session_button);
        String end = getString(R.string.end_session_button);
        if(sessionButt.getText().equals(begin)) {
            //begin the session and timer, change the message for both
            //probs want something like tutee.setIsInSession(true) and
            //tutor.setIsInSession(true);
            TextView message = (TextView)findViewById(R.id.sessionMessage);
            message.setText(getString(R.string.sessionInProg));
            //set both of their screen to say session in progress when the start button
            //is pressed
            sessionButt.setText(getString(R.string.end_session_button));
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            if(!isTutor) {
                displayTuteePopup();
            }
        } else if (sessionButt.getText().equals(end)) {
            String timerTime = ((TextView)findViewById(R.id.timerTextView)).getText().toString();
            //save the timer time at session end
            timerHandler.removeCallbacks(timerRunnable);
            //handle isInSession being set to false for the clicker
            endSession();
        }

    }
    private void setRequestDetails() {
        String course = ((TextView)findViewById(R.id.session_course)).getText().toString();
        String tuteeName = ((TextView)findViewById(R.id.session_tutee_name)).getText().toString();
        String phoneNum = ((TextView)findViewById(R.id.session_phone_num)).getText().toString();
        course += " CS 3251";
        tuteeName += " Mashal";
        phoneNum += " 678-227-9788";
        //make that actually get the request values, once i figure out how those are passed in.
        ((TextView)findViewById(R.id.session_course)).setText(course);
        ((TextView)findViewById(R.id.session_tutee_name)).setText(tuteeName);
        ((TextView)findViewById(R.id.session_phone_num)).setText(phoneNum);
    }

    private void displayOldSession() {

    }
}
