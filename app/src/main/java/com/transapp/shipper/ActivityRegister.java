package com.transapp.shipper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.transapp.shipper.commons.Constants;
import com.transapp.shipper.db.FeedReaderDbHelper;
import com.transapp.shipper.db.data.User;
import com.transapp.shipper.managers.ResourceManager;
import com.transapp.shipper.managers.SessionManager;
import com.transapp.shipper.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActivityRegister extends AppCompatActivity {

    private static final String TAG = "ActivityRegister";

    private Toolbar toolbar;

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtCompanyName;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtPassword;

    private Button btnSignUp;

    private ProgressDialog pDialog;
    private SessionManager session;
    private FeedReaderDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolbar();
        initInputControls();
        initSignUpControl();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());
        db = new FeedReaderDbHelper(getApplicationContext());

        if(session.isLogdedIn()){
            Intent intent = new Intent(ActivityRegister.this, ActivityDashboard.class);
            startActivity(intent);
            finish();
        }
    }


    private void initToolbar(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_24dp);
    }

    private void initInputControls(){
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLasName);
        txtCompanyName = (EditText)findViewById(R.id.txtCompanyName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        txtFirstName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(txtFirstName);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        txtLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(txtLastName);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });

        txtCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(txtCompanyName);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });

        txtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Validation.isPhoneNumber(txtPhone, true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });

        txtEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(txtEmail, true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Validation.hasText(txtPassword);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });
    }

    private void initSignUpControl(){
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation())
                    submitForm();
                else
                    Toast.makeText(ActivityRegister.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;

        if(!Validation.hasText(txtFirstName)) ret = false;
        if(!Validation.hasText(txtLastName)) ret = false;
        if(!Validation.hasText(txtCompanyName)) ret = false;
        if(!Validation.hasText(txtPassword)) ret = false;
        if(!Validation.isEmailAddress(txtEmail, true)) ret = false;
        if(!Validation.isPhoneNumber(txtPhone, true)) ret = false;

        return ret;
    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();

        final String firstName = txtFirstName.getText().toString();
        final String lastName = txtLastName.getText().toString();
        final String companyName = txtCompanyName.getText().toString();
        final String phone = txtPhone.getText().toString();
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();

        //registering
        String registerTag = "req_register";

        pDialog.setMessage("Registering...");
        showDialog();

        StringRequest request = new StringRequest(StringRequest.Method.POST, Constants.URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Register Response: " + response.toString());
                        hideDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            if(!error){
                                // User successfully stored in MySQL
                                // Now store the user in sqlite
                                JSONObject jsonUser = jsonObject.getJSONObject("user");
                                User user = new User();
                                user.setId(jsonUser.getInt("id"));
                                user.setFirstName(jsonUser.getString("firstName"));
                                user.setLastName(jsonUser.getString("lastName"));
                                user.setCompanyName(jsonUser.getString("companyName"));
                                user.setPhone(jsonUser.getString("phone"));
                                user.setEmail(jsonUser.getString("email"));
                                user.setUid(jsonUser.getString("uid"));
                                user.setCreatedAt(jsonUser.getString("createdAt"));

                                // Inserting row in users table
                                db.addUser(user);

                                // Launch login activity
                                Intent intent = new Intent(
                                        ActivityRegister.this,
                                        ActivityLogin.class);
                                startActivity(intent);
                                finish();
                            }else {
                                // Error occurred in registration. Get the error
                                // message
                                String errorMsg = jsonObject.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Registration Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("companyName", companyName);
                params.put("phone", phone);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        ResourceManager.getInstance().getRequestQueue().add(request);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
