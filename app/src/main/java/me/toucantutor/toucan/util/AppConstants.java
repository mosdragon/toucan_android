package me.toucantutor.toucan.util;

/**
 * Created by osama on 2/19/15.
 */
public class AppConstants {

    public static String NAME = "";
    public static String EMAIL = "";
    public static double DUMMY_LAT = 33.749792; //lat/long for athens
    public static double DUMMY_LONG = -84.374185;
    public static Long DUMMY_ID = Long.valueOf(903778);
    public static String PHONE_NUMBER = "6786025306";
    public static boolean isTutor = false;

    public static final String STUDENT_REGISTER_URL = "http://beta-toucanapp.rhcloud.com/api/v1/users/signupStudent";
    public static final String TUTOR_REGISTER_URL = "http://beta-toucanapp.rhcloud.com/api/v1/users/signupTutor";

    public static final String CHECK_CODE_VALID_URL = "http://beta-toucanapp.rhcloud.com/api/v1/users/checkCodeValid";
    public static final String GET_COURSES_URL = "http://beta-toucanapp.rhcloud.com/api/v1/sessions/getCourses"; //nearby courses
    public static final String FIND_ACTIVE_TUTOR_URL = "http://beta-toucanapp.rhcloud.com/api/v1/sessions/findActiveTutors"; //nearby tutors
    public static final String SELECT_TUTOR_URL = "http://beta-toucanapp.rhcloud.com/api/v1/sessions/selectTutor";
    public static final String TUTOR_BEGIN_URL = "";
    public static final String TUTOR_END_URL = "";
    public static final String REVIEW_URL = "";

}
