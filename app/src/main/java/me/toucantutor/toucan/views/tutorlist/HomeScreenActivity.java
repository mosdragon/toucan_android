package me.toucantutor.toucan.views.tutorlist;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.locationdata.DetermineLocation;
import me.toucantutor.toucan.util.MapsClientCallback;
import me.toucantutor.toucan.views.RegisterActivity;
import me.toucantutor.toucan.views.courseList.CourseListActivity;
import me.toucantutor.toucan.views.session.StartSessionActivity;


public class HomeScreenActivity extends ActionBarActivity implements MapsClientCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        DetermineLocation.createInstance(this);

        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),CourseListActivity.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(),TutorChooseSubjectsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DetermineLocation.connect();
    }

    public void findCourses(View view) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void clientFinished() {
        Log.d("~~~~~~~~~~HomeScreenActivity", "clientFinished called~~~~~~~~~~~~");
        TextView latitude = (TextView) findViewById(R.id.latitude);
        TextView longitude = (TextView) findViewById(R.id.longitude);
        Location currentLocation = DetermineLocation.getLocation();
        Log.d("HomeScreenActivity - onStart", "Setting textviews");
//        if (currentLocation != null) {
            latitude.setText(latitude.getText() + " " + currentLocation.getLatitude());
            longitude.setText(longitude.getText() + " " + currentLocation.getLongitude());
//        } else {
//            latitude.setText(latitude.getText() + " " + "NOT FOUND");
//            longitude.setText(longitude.getText() + " " + "NOT FOUND");
//        }
    }

    public void toSession(View view) {
        Intent intent = new Intent(this, StartSessionActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
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
