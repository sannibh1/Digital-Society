package com.example.sh_13.digitalsociety;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextmobileNo, editTextfirstName, editTextlastName, editTextemail, editTextpassword;
    private Button buttonregister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextmobileNo = findViewById(R.id.signupMobileNo);
        editTextfirstName = findViewById(R.id.signupFirstName);
        editTextlastName = findViewById(R.id.signupLastName);
        editTextemail = findViewById(R.id.signupEmail);
        editTextpassword = findViewById(R.id.signupPassword);
        buttonregister = findViewById(R.id.buttonRegister);

        progressDialog = new ProgressDialog(this);

        buttonregister.setOnClickListener(this);
    }

    private void registerUser(){
        final String mobileNo = editTextmobileNo.getText().toString().trim();
        final String firstName = editTextfirstName.getText().toString().trim();
        final String lastName = editTextlastName.getText().toString().trim();
        final String email = editTextemail.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mobileno", mobileNo);
                params.put("firstname",firstName);
                params.put("lastname",lastName);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonregister){
            registerUser();
        }
    }
}
