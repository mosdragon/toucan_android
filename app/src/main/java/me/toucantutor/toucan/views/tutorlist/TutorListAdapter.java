package me.toucantutor.toucan.views.tutorlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.ImageView;
import java.util.Random;

import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.util.Globals;

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
        RatingBar ratingField;
        TextView ratingValue;
        TextView priceField;
        TextView distanceField;
        ImageView certified;
        View row = convertView;

        //inflates the row to create individual rows of the list
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);
        Activity activity = (Activity) context;

        //attaches textView fields to those in the xml file
        nameField = (TextView) row.findViewById(R.id.name);
        ratingField = (RatingBar) row.findViewById(R.id.rating);
        ratingValue = (TextView) row.findViewById(R.id.ratingValue);
        priceField = (TextView) row.findViewById(R.id.price);
        distanceField = (TextView) row.findViewById(R.id.distance);
        certified = (ImageView) row.findViewById(R.id.certified);

        //sets the textview items to values obtained from tutor
        currentTutor = ( Tutor ) tutorList.get(position);
        nameField.setText(currentTutor.getName().length() < 20
                ? currentTutor.getName().toUpperCase()
                : currentTutor.getName().substring(0, 20).toUpperCase());

        if (Globals.isPreviewing()) {
            nameField.setText("Tutor #" + (position + 1));
            nameField.setText("Name Hidden");
        }

        if (currentTutor.getRating() == -1) {
            ratingField.setRating(0);
            ratingValue.setText("No ratings");
        } else {
            ratingField.setRating(currentTutor.getRating().floatValue());
            ratingValue.setText(currentTutor.getRating() + "");
        }

        String priceText = String.format("$%2.2f", currentTutor.getRate());
        priceField.setText(priceText);

        distanceField.setText(currentTutor.getDistance() + " mi.");
        Random rand = new Random();
        certified.setVisibility(currentTutor.isCertified() ? View.VISIBLE : View.INVISIBLE);
        return row;
    }

}