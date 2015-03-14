package me.toucantutor.toucan.views.courseList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.GetCoursesTask;
import me.toucantutor.toucan.tasks.TaskCallback;

public class CourseListActivity extends ActionBarActivity implements TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        GetCoursesTask task = new GetCoursesTask(this);
        task.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_list, menu);
        return true;
    }

    @Override
    public void taskSuccess(Object... input) {
        Log.d("taskSuccess called", "-----true");
        List<String> courses = (List<String>) input[0];
        ListView listView = (ListView) findViewById(R.id.course_list);
        ListAdapter listAdapter = new ArrayAdapter<String>(this,
                R.layout.activity_course_list, R.id.listHolder, courses);
//        new ArrayAdapter<String>()
        listView.setAdapter(listAdapter);
    }

    @Override
    public void taskFail(Object... input) {
        Log.d("taskSuccess called", "-----true");
        TextView textView = (TextView) findViewById(R.id.listHolder);
        textView.setText("Could not find any courses near you. Please get closer to the " +
                "University of Georgia campus or try again later for better results.");
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
