package me.toucantutor.toucan.views.courseList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.toucantutor.toucan.R;

/**
 * Created by osama on 3/14/15.
 */
public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    private List<Course> courses;

    public CourseAdapter(Context context, int resource, List<Course> courses) {
        super(context, resource, courses);
        this.context = context;
        this.courses = courses;
    }

//    public CourseAdapter(Context context, int resource, int textViewResourceId,
//                         List<Course> courses) {
//        super(context, resource, textViewResourceId, courses);
//        this.context = context;
//        this.courses = courses;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.course_item, parent, false);
        TextView coursename = (TextView) view.findViewById(R.id.coursename);
        TextView school = (TextView) view.findViewById(R.id.school);

        Course course = courses.get(position);
        coursename.setText(course.getCoursename());
        school.setText("(" + course.getSchool() + ")");
        return view;
    }
}
