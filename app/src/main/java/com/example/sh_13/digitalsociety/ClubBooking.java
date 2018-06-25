package com.example.sh_13.digitalsociety;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

public class ClubBooking extends AppCompatActivity implements View.OnClickListener{

    private Button buttonBookClub;
    private Integer day, month, year;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_booking);

        buttonBookClub = (Button) findViewById(R.id.clubSubmit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");

        buttonBookClub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonBookClub){
            bookClub();
        }
    }

    private void bookClub() {

        final String mobileno = SharedPrefManager.getInstance(this).getMobileNo();
        DatePicker datePicker = (DatePicker) findViewById(R.id.bookClubDate);
        day = datePicker.getDayOfMonth();
        month = datePicker.getMonth() + 1;
        year = datePicker.getYear();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_CLUB_BOOKING,
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
                params.put("dateyear",year.toString());
                params.put("datemonth",month.toString());
                params.put("dateday",day.toString());
                return params;
            }

        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
//