package me.toucantutor.toucan.views.courseList;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by osama on 3/14/15.
 */
public class Course implements Comparable<Course>, Serializable {

    private String coursename;
    private String school;

    public Course(JsonObject json) {
        setCoursename(json.get("coursename").getAsString());
        setSchool(json.get("school").getAsString());
    }


    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public int compareTo(Course another) {
        return this.getCoursename().compareTo(another.getCoursename());
    }
}
