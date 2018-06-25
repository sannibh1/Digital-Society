package com.example.sh_13.digitalsociety;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    private Button buttonClubBooking,buttonGymBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        if (!SharedPrefManager.getInstance(this).isLogedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        buttonClubBooking = (Button) findViewById(R.id.bookClub);
        buttonGymBooking = (Button) findViewById(R.id.bookGym);

        buttonClubBooking.setOnClickListener(this);
        buttonGymBooking.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuMyProfile:
                Toast.makeText(this,"Ypu Clicked My Profile!!!!",Toast.LENGTH_LONG).show();
                break;

            case R.id.menuMyBookings:
                Toast.makeText(this,"Ypu Clicked My bookings!!!!",Toast.LENGTH_LONG).show();
                break;

            case R.id.menuLogOut:
                SharedPrefManager.getInstance(this).logOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == buttonClubBooking) {
            startActivity(new Intent(this, ClubBooking.class));
        }
        else if(view == buttonGymBooking){
            startActivity(new Intent(this, GymBooking.class));
        }
    }
}
