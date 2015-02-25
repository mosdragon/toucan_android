package me.gettoucan.toucan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class TutorChooseSubjectsActivity extends Activity {

    private String[] subjectList = {"Calculus 1", "Calculus II", "Physics I", "Physics II", "Chemistry I", "Biology I"};
    private ArrayList<Long> chosenList = new ArrayList<Long>(subjectList.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_choose_subjects);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < subjectList.length; ++i) {
            list.add(subjectList[i]);
        }
        //updates list of selected subjects
        ListView listOfSubjects = (ListView) findViewById(R.id.chooseSubjectList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.tutor_choose_subject_item, R.id.subject,list);
        listOfSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long idChosen = new Long(id);
                if(chosenList.contains(idChosen)){
                    chosenList.remove(idChosen);
                    view.setBackgroundColor(Color.TRANSPARENT);
                }
                else {
                    chosenList.add(idChosen);
                    view.setBackgroundColor(Color.RED);
                }
            }
        });
        //choose button listener and handles error
        listOfSubjects.setAdapter(adapter);
        Button choose = (Button)findViewById(R.id.chooseButton);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenList.size()==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TutorChooseSubjectsActivity.this);
                    builder.setTitle("ERROR");
                    builder.setMessage("You gotta tutor in something, man!").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.v("", "clicked ok");
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else{
                    Intent i = new Intent(getApplication(),HomeScreenActivity.class);
                    i.putExtra("chosenList", chosenList);
                    startActivity(i);
                }

            }
        });

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
