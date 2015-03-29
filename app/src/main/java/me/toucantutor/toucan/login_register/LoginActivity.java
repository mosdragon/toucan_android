package me.toucantutor.toucan.login_register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.toucantutor.toucan.ActualHomeScreen;
import me.toucantutor.toucan.R;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.Constants;
import me.toucantutor.toucan.util.Globals;
import me.toucantutor.toucan.views.courseList.Course;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements TaskCallback {




    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private UserLoginTask mAuthTask = null;

    String email = "";
    String pass = "";
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private CheckBox saveLoginCheckBox;

    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    private static boolean success = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);


        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            mEmailView.setText(loginPreferences.getString("username", ""));
            mPasswordView.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
    }

    public void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        pass = mPasswordView.getText().toString();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);

        boolean cancel = false;
        View focusView = null;


        // Check for empty fields.
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(pass)) {
            mEmailView.setError(getString(R.string.field_required));
            focusView = mEmailView;
            cancel = true;
        //check for a valid email
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        // Check for a valid password.
        else if (!isPasswordValid(pass)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (saveLoginCheckBox.isChecked()) {
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", email);
            loginPrefsEditor.putString("password", pass);
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            JsonObject object = new JsonObject();
            object.addProperty("user",email);
            object.addProperty("password",pass);
            HttpTask task = new HttpTask(this, object, Constants.LOGIN);
            task.execute();


//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
        }
    }


    public static boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && email.length() >= 7;
    }

    public static boolean isUsernameValid(String username) {
        return username.length() >= 6;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    @Override
    public void taskSuccess(JsonObject json) {
        Log.v("status", "MAH NIGA! IT WORKED");
        Log.v("json ",json.toString());
        String userType = (json.get("userType")).getAsString();
        String userId = (json.get("userId")).getAsString();
        Intent intent = new Intent(this, ActualHomeScreen.class);
        intent.putExtra("userId", userId);
        if(userType.equals("BOTH")||userType.equals("TUTOR")){
            intent.putExtra("isTutor", true);
        }
        else{//student
            intent.putExtra("isTutor", false);
        }
        Globals.setLoggedIn(true);
        startActivity(intent);

    }

    @Override
    public void taskFail(JsonObject json) {
        Log.v("status", "DARN_ FAILED");
    }
}


