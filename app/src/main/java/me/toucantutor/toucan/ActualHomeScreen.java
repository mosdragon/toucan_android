package me.toucantutor.toucan;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.login_register.LoginActivity;
import me.toucantutor.toucan.util.Globals;
import me.toucantutor.toucan.views.courseList.CourseListActivity;

public class ActualHomeScreen extends ActionBarActivity implements View.OnClickListener {

    boolean isTutor = false;
    Button chooseSubject;
    Button becomeActive;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_home_screen);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isTutor = extras.getBoolean("isTutor");
            Globals.setCurrentId(extras.getString("userId"));
        }
        Log.v("status",isTutor+" ...Is tutor");
        chooseSubject = (Button)findViewById(R.id.chooseSubject);
        becomeActive = (Button)findViewById(R.id.becomeActive);
        logout = (Button)findViewById(R.id.logout);
        chooseSubject.setOnClickListener(this);
        becomeActive.setOnClickListener(this);
        logout.setOnClickListener(this);
        if(isTutor){
            chooseSubject.setVisibility(View.INVISIBLE);
        }
        else{
            becomeActive.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if(view==chooseSubject){
            Intent intent = new Intent(this, CourseListActivity.class);
            startActivity(intent);
        }
        else if (view== becomeActive){
            Intent intent = new Intent(this, ChooseAvailability.class);
            startActivity(intent);
        }
        else if (view==logout){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            //should remove prefs
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actual_home_screen, menu);
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
