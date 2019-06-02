package com.example.user.uniauth;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class Register extends AppCompatActivity  {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private View mProgressView;
    private View mLoginFormView;
    private EditText dob;
    private EditText name;
    private booleans bool;
    private RadioButton rd1;
    private RadioButton rd2;
    private RadioGroup rd;
    private Boolean authType = true;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;
    private RadioButton selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView2 = (EditText) findViewById(R.id.password2);
        dob = (EditText) findViewById(R.id.dob);
        name = (EditText) findViewById(R.id.Name);
        rd = (RadioGroup)findViewById(R.id.rd);
        rd1 = (RadioButton)findViewById(R.id.rb1);
        rd2 = (RadioButton)findViewById(R.id.rb2);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        bool = new booleans();
    }


    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);
        name.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String date = dob.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        String Name = name.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(password2)) {
            mPasswordView2.setError(getString(R.string.error_field_required));
            focusView = mPasswordView2;
            cancel = true;
        }
        if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (!password2.equals(password)) {
            mPasswordView.setError(getString(R.string.re_enterPassword));
            focusView = mPasswordView2;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid name.
        if (TextUtils.isEmpty(Name)) {
            name.setError(getString(R.string.error_field_required));
            focusView = name;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            String user = Name+":"+date+":"+email;
            showProgress(true);
            try {
                bool.saveUser(user, password, true, authType, Register.this);
                SaveToDatabase(Name,date,email,password);
            }catch(Exception e){
                Toast.makeText(Register.this,"Error" + e,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isEmailValid(String email) {

        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".edu"));
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void SaveToDatabase(String Name,String date,String email,String password){
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'RepositoryName' node
        mFirebaseDatabase = mFirebaseInstance.getReference("User Details : ");
        userId = mFirebaseDatabase.push().getKey();
        User userr = new User(Name,date,email, password);
        mFirebaseDatabase.child(userId).setValue(userr);
    }

    public void checkChange(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch(view.getId()){
            case R.id.rb1:
                if(checked){
                    rd2.setChecked(false);
                    authType = true;
                }
                break;
            case R.id.rb2 :
                if(checked){
                    authType = false;
                    rd1.setChecked(false);
                }
                break;
        }
    }
}


