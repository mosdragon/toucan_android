package me.toucantutor.toucan.views.tutorlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.toucantutor.toucan.R;

/**
 * Created by aadil on 2/20/15.
 */
public class TutorListAdapter extends ArrayAdapter<Tutor> {

    private List<Tutor> tutorList;
    private int resource;
    private Context context;
    private Tutor currentTutor = null;

    public TutorListAdapter(Context context, int textViewResourceId, List<Tutor> tutors) {
        super(context, textViewResourceId, tutors);
        this.context = context;
        this.tutorList = tutors;
        this.resource = textViewResourceId;
    }

    /*
     *gets each individual view/row of the list using
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //add conditions of list is empty
        TextView nameField;
        TextView ratingField;
        TextView priceField;
        TextView distanceField;
        View row = convertView;

        //inflates the row to create individual rows of the list
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);
        Activity activity = (Activity) context;

        //attaches textView fields to those in the xml file
        nameField = (TextView) row.findViewById(R.id.name);
        ratingField = (TextView) row.findViewById(R.id.rating);
        priceField = (TextView) row.findViewById(R.id.price);
        distanceField = (TextView) row.findViewById(R.id.distance);

        //sets the textview items to values obtained from tutor
        currentTutor = ( Tutor ) tutorList.get(position);
        nameField.setText(currentTutor.getName());
        ratingField.setText(currentTutor.getRating()+"  (rating)");
        priceField.setText(currentTutor.getPrice()+"  (price)");
        distanceField.setText(currentTutor.getDistance()+"  (dist)");
        return row;
    }

}