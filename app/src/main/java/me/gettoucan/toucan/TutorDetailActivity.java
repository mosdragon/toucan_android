package me.gettoucan.toucan;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TutorDetailActivity extends ActionBarActivity {

    Tutor chosenTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        Bundle b = getIntent().getExtras();
        chosenTutor = (Tutor)b.getSerializable("tutorChosen");
        createLayout();

    }

    public void createLayout(){
        TextView name = (TextView)findViewById(R.id.tutorName);
        TextView email = (TextView)findViewById(R.id.tutorEmail);
        TextView certification = (TextView)findViewById(R.id.tutorCertification);
        TextView phone = (TextView)findViewById(R.id.tutorPhoneNumber);
        TextView rating = (TextView)findViewById(R.id.tutorRating);
        TextView distance = (TextView)findViewById(R.id.tutorDistance);
        TextView price = (TextView)findViewById(R.id.tutorPrice);
        Button choose = (Button)findViewById(R.id.choose);

        name.setText(chosenTutor.getName());
        email.setText(chosenTutor.getEmail());
        if(chosenTutor.getCertification()==true){
            certification.setText("Certified");
        }
        else{
            certification.setText("Uncertified");
        }
        phone.setText(chosenTutor.getPhoneNumber());
        rating.setText(chosenTutor.getRating().toString()+" (rating)");
        distance.setText(chosenTutor.getDistance().toString()+" (dist)");
        price.setText(chosenTutor.getPrice().toString()+" ($$)");
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tutor has been chosen. Send result to db
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
