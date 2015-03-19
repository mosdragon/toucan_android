package me.toucantutor.toucan.login_register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import me.toucantutor.toucan.R;
import me.toucantutor.toucan.login_register.LoginActivity;
import me.toucantutor.toucan.tasks.HttpTask;
import me.toucantutor.toucan.tasks.TaskCallback;
import me.toucantutor.toucan.util.AppConstants;
import me.toucantutor.toucan.views.courseList.Course;


/**
 * A login screen that offers register.
 */
public class RegisterActivity extends LoginActivity implements LoaderCallbacks<Cursor>,TaskCallback {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mFNameView;
    private AutoCompleteTextView mLNameView;
    private AutoCompleteTextView mphoneView;
    private EditText mcode;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mFNameView = (AutoCompleteTextView) findViewById(R.id.firstname);
        mLNameView = (AutoCompleteTextView) findViewById(R.id.lastname);
        mphoneView = (AutoCompleteTextView) findViewById(R.id.phone);
        mcode = (EditText) findViewById(R.id.tutorCode);

        mConfirmPasswordView = (EditText) findViewById(R.id.confirmpassword);
        mPasswordView = (EditText) findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        Button mUserRegisterButton = (Button) findViewById(R.id.register_button);
        mUserRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyFields();
                doHttpTask();
            }
        });

    }

    private void doHttpTask(){

        if (!TextUtils.isEmpty(mcode.getText().toString())) {
            JsonObject json = new JsonObject();
            json.addProperty("tutorCode", mcode.getText().toString());
            HttpTask checkCodeValid = new HttpTask(this, json, AppConstants.CHECK_CODE_VALID_URL);
            checkCodeValid.execute();
        }
        else{
            registerStudent();
        }
    }

    public void registerStudent() {

        HttpTask task = new HttpTask(this, new JsonObject(), AppConstants.STUDENT_REGISTER_URL);

    }

    public void registerTutor() {

        HttpTask task = new HttpTask(this, new JsonObject(), AppConstants.TUTOR_REGISTER_URL);

    }

    public void verifyFields(){


        // Reset errors.
        mPasswordView.setError(null);
        mFNameView.setError(null);
        mLNameView.setError(null);
        mConfirmPasswordView.setError(null);
        mEmailView.setError(null);
        mcode.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String firstname = mFNameView.getText().toString();
        String lastname = mLNameView.getText().toString();
        String phone = mLNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmpassword = mConfirmPasswordView.getText().toString();


        boolean errors = false;
        View focusView = null;

        // Check for empty fields.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.field_required));
            focusView = mEmailView;
            errors = true;
            //check for a valid email
        } if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.invalid_email));
            focusView = mEmailView;
            errors = true;
            // Check for a valid password.
        }if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.invalid_password));
            focusView = mPasswordView;
            errors = true;
        }
        // Check for a valid first name
        if (TextUtils.isEmpty(firstname)) {
            mFNameView.setError(getString(R.string.field_required));
            focusView = mFNameView;
            errors = true;
        }
        // Check for a valid last name
        if (TextUtils.isEmpty(lastname)) {
            mFNameView.setError(getString(R.string.field_required));
            focusView = mLNameView;
            errors = true;
        }
        // Check for a valid last name
        if (TextUtils.isEmpty(phone)) {
            mphoneView.setError(getString(R.string.field_required));
            focusView = mphoneView;
            errors = true;
        }
        //check if passwords match
        if (!password.equals(confirmpassword)) {
            mPasswordView.setError("Passwords do not match!");
            focusView = mPasswordView;
            errors = true;
            mPasswordView.clearComposingText();
            mConfirmPasswordView.clearComposingText();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only username addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> usernames = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            usernames.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addUsernamesToAutoComplete(usernames);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addUsernamesToAutoComplete(List<String> usernameAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(RegisterActivity.this,
                        android.R.layout.simple_dropdown_item_1line, usernameAddressCollection);

        //mUserView.setAdapter(adapter);
    }


    @Override
    public void taskSuccess(JsonObject json) {
        if(json.has("isValid")){ //if isvalid returned sucess
            if(json.get("isValid").getAsBoolean()==true){
                registerTutor();
            }
            else{
                mcode.setError("Un-Verified Code");
                View focusView = mcode;
                mcode.clearComposingText();
            }
        }
    }

    @Override
    public void taskFail(JsonObject json) {

    }
}


