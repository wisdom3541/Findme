package com.FindMe.findme;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class friendsprofile extends AppCompatActivity {

    //variables
    Button findme, unfriend;
    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendsprofile);


        findme = findViewById(R.id.findme);
        unfriend = findViewById(R.id.unfriend);
        intent = new Intent(friendsprofile.this, currentLocation.class);


        //reducing the activity layout size
        //start
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .4), (int) (height * .3));

        //end


        findme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(friendsprofile.this, "Loading map...Please wait", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new currentLocation()).commit();
                finish();


            }


        });

        unfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(friendsprofile.this, "Friend Deleted", Toast.LENGTH_LONG).show();
            }


        });
    }


}

