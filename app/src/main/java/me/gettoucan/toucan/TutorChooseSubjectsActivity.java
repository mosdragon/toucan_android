package me.gettoucan.toucan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class TutorChooseSubjectsActivity extends ActionBarActivity {

    String[] subjectList = {"Calculus 1", "Calculus II", "Physics I", "Physics II", "Chemistry I", "Biology I"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_choose_subjects);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjectList.length; ++i) {
            list.add(subjectList[i]);
        }
        ListView listOfSubjects = (ListView) findViewById(R.id.chooseSubjectList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tutor_choose_subject_item, R.id.subject,list);
        listOfSubjects.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutor_choose_subjects, menu);
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
