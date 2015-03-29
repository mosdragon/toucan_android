package me.toucantutor.toucan.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.io.Serializable;

import me.toucantutor.toucan.views.courseList.Course;
import me.toucantutor.toucan.views.tutorlist.Tutor;

/**
 * Created by osama on 3/15/15.
 */
public class Globals {

    private static String currentId = "";
    private static String userId = "";
    private static String tutorId = "";
    private static String sessionId = "";
    private static boolean loggedIn;
    private static boolean available;
    private static boolean inSession;
//    If previewing, names are hidden, and tapping on tutor takes you back
//    to login screen
    private static boolean previewing;

//    Time at which tutoring session began (to keep track of timer)
    private static Long beginTime;

    private static Tutor tutor;
    private static Course course;

    public enum State implements Serializable {
        TUTOR, TUTEE, BOTH, NONE
    }
    private static State state;

    private static Activity activity;

    public static void appLoad(Activity activity) {
        Globals.activity = activity;
        SharedPreferences prefs = activity.getSharedPreferences(Constants.GLOBALS,
                Context.MODE_PRIVATE);

        if (prefs != null) {
            loggedIn = prefs.getBoolean(Constants.IS_LOGGED_IN, false);
            if (loggedIn) {
                userId = prefs.getString(Constants.USER_ID, "");
                tutorId = prefs.getString(Constants.TUTOR_ID, "");
                sessionId = prefs.getString(Constants.SESSION_ID, "");

                available = prefs.getBoolean(Constants.IS_AVAILABLE, false);
                inSession = prefs.getBoolean(Constants.IS_IN_SESSION, false);

                if (inSession) {
                    beginTime = prefs.getLong(Constants.BEGIN_TIME, System.currentTimeMillis());

                    Gson gson = new Gson();

//                    Parse objects from JSON
                    String stateString = prefs.getString(Constants.STATE, "");
                    if (!stateString.equals("")) {
                        state = gson.fromJson(stateString, State.class);
                    }

                    String tutorString = prefs.getString(Constants.TUTOR, "");
                    if (!tutorString.equals("")) {
                        tutor = gson.fromJson(tutorString, Tutor.class);
                    }

                    String courseString = prefs.getString(Constants.COURSE, "");
                    if (!stateString.equals("")) {
                        course = gson.fromJson(courseString, Course.class);
                    }
                }
            } else {
                previewing = true;
                state = State.NONE;
            }
        }
    }

    public static void appSave() {
        SharedPreferences prefs = activity.getSharedPreferences(Constants.GLOBALS,
                Context.MODE_PRIVATE);

        if (prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            if (loggedIn) {
                editor.putBoolean(Constants.IS_LOGGED_IN, loggedIn);
                editor.putString(Constants.USER_ID, userId);
                editor.putString(Constants.TUTOR_ID, tutorId);
                editor.putString(Constants.SESSION_ID, sessionId);

                editor.putBoolean(Constants.IS_AVAILABLE, available);
                editor.putBoolean(Constants.IS_IN_SESSION, inSession);

                if (inSession && beginTime != 0) {
                    editor.putLong(Constants.BEGIN_TIME, beginTime);
                }
            } else {
                editor.putBoolean(Constants.IS_PREVIEWING, previewing);
                state = State.NONE;
                Gson gson = new Gson();
                String stateString = gson.toJson(state);
                editor.putString(Constants.STATE, stateString);

                String tutorString = gson.toJson(tutor);
                editor.putString(Constants.TUTOR, tutorString);

                String courseString = gson.toJson(course);
                editor.putString(Constants.COURSE, courseString);
            }
        }
    }

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        Globals.sessionId = sessionId;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Globals.loggedIn = loggedIn;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void setAvailable(boolean available) {
        Globals.available = available;
    }

    public static boolean isInSession() {
        return inSession;
    }

    public static void setInSession(boolean inSession) {
        Globals.inSession = inSession;
    }

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        Globals.state = state;
    }


    public static String getCurrentId() {
        return currentId;
    }

    public static void setCurrentId(String currentId) {
        Globals.currentId = currentId;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Globals.userId = userId;
    }

    public static String getTutorId() {
        return tutorId;
    }

    public static void setTutorId(String tutorId) {
        Globals.tutorId = tutorId;
    }

    public static Long getBeginTime() {
        return beginTime;
    }

    public static void setBeginTime(Long beginTime) {
        Globals.beginTime = beginTime;
    }

    public static boolean isPreviewing() {
        return previewing;
    }

    public static void setPreviewing(boolean previewing) {
        Globals.previewing = previewing;
    }

    public static Tutor getTutor() {
        return tutor;
    }

    public static void setTutor(Tutor tutor) {
        Globals.tutor = tutor;
    }

    public static Course getCourse() {
        return course;
    }

    public static void setCourse(Course course) {
        Globals.course = course;
    }
}
