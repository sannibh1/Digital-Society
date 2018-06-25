package com.example.sh_13.digitalsociety;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GymBooking extends AppCompatActivity implements View.OnClickListener{

    private String gender;
    private RadioButton male, female;
    private Button buttonBookGym;
    private EditText editTextGymName;
    private Integer day, month, year;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_booking);

        buttonBookGym = (Button) findViewById(R.id.gymSubmit);

        male = (RadioButton) findViewById(R.id.radioMale);
        female = (RadioButton) findViewById(R.id.radioFemale);
        editTextGymName = (EditText) findViewById(R.id.bookGymName);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");

        buttonBookGym.setOnClickListener(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioMale:
                if (checked)
                    gender = "Male";
                // Pirates are the best
                break;
            case R.id.radioFemale:
                if (checked)
                    gender = "Female";
                // Ninjas rule
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonBookGym){
            bookGym();
        }
    }

    private void bookGym() {

        final String mobileno = SharedPrefManager.getInstance(this).getMobileNo();
        final String name = editTextGymName.getText().toString().trim();

        onRadioButtonClicked(male);
        onRadioButtonClicked(female);

        DatePicker datePicker = (DatePicker) findViewById(R.id.bookGymDate);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GYM_BOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")){

                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(getApplicationContext(), MyProfile.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("mobileno",mobileno);
                params.put("gender",gender);
                params.put("name",name);
                params.put("dateyear",year.toString());
                params.put("datemonth",month.toString());
                params.put("dateday",day.toString());
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


}
