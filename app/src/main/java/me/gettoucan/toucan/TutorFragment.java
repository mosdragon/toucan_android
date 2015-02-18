package me.gettoucan.toucan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;

import me.gettoucan.toucan.R;

public class TutorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tutor_fragment, container, false);

        Tutor c = (Tutor)getArguments().getSerializable("tutor");

        TextView textName = (TextView) v.findViewById(R.id.name);
        TextView textEmail = (TextView) v.findViewById(R.id.email);
        TextView textRating = (TextView) v.findViewById(R.id.rating);
        TextView textPrice = (TextView) v.findViewById(R.id.price);
        TextView textDistance = (TextView) v.findViewById(R.id.distance);

        textName.setText(c.getName());
        textEmail.setText(c.getEmail());
        textRating.setText("Rating: "+c.getRating());
        textPrice.setText("Price "+c.getPrice());
        textDistance.setText("Distance "+c.getDistance());

        return v;
    }

    /*
     *creates new instance of Tutor, passing in the Tutor object
     */
    public static TutorFragment newInstance(Tutor current) {
        TutorFragment f = new TutorFragment();
        Bundle b = new Bundle();
        b.putSerializable("tutor", current);
        f.setArguments(b);

        return f;
    }
}