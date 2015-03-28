package me.toucantutor.toucan.util;

/**
 * Created by osama on 2/19/15.
 */
public class Constants {

    public static final String[] SUBJECT_LIST = {"Calculus 1", "Calculus II", "Physics I", "Physics II", "Chemistry I", "Biology I"};

    public static final String BASEURL = "http://beta-toucanapp.rhcloud.com/api/v1/";

    public static final String ACTIVE_TUTOR_URL = BASEURL + "";
    public static final String STUDENT_REGISTER_URL = BASEURL + "users/signupStudent";
    public static final String TUTOR_REGISTER_URL = BASEURL + "users/signupTutor";
    public static final String CHECK_CODE_VALID_URL = BASEURL + "users/checkCodeValid";
    public static final String GET_COURSES_URL = BASEURL + "sessions/getCourses"; //nearby courses
    public static final String FIND_ACTIVE_TUTORS_URL = BASEURL + "sessions/findActiveTutors"; //nearby tutors
    public static final String SELECT_TUTOR_URL = BASEURL + "sessions/selectTutor";
    public static final String TUTOR_BEGIN_URL = BASEURL + "";
    public static final String TUTOR_END_URL = BASEURL + "";
    public static final String REVIEW_URL = BASEURL + "";

    public static final String GLOBALS = "GLOBALS";

    public static final String USER_ID = "USER_ID";
    public static final String TUTOR_ID = "TUTOR_ID";
    public static final String SESSION_ID = "SESSION_ID";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String IS_AVAILABLE = "IS_AVAILABLE";
    public static final String IS_IN_SESSION = "IS_IN_SESSION";
    public static final String IS_PREVIEWING = "IS_PREVIEWING";
    public static final String BEGIN_TIME = "BEGIN_TIME";
    public static final String STATE = "STATE";

    public static final String TUTOR = "TUTOR";
    public static final String COURSE = "COURSE";

    public static final int MILES = 5;

    public static String NAME = "";
    public static String EMAIL = "";
    public static double DUMMY_LAT = 33.749792; //lat/long for athens
    public static double DUMMY_LONG = -84.374185;
    public static Long DUMMY_ID = Long.valueOf(903778);
    public static String PHONE_NUMBER = "6786025306";
    public static boolean isTutor = false;



}
