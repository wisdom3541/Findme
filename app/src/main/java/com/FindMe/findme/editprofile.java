package com.FindMe.findme;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editprofile extends AppCompatActivity {

    //variables
    String edituseremail;
    EditText name,email; 
    Button saveprofile;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

            name = (EditText) findViewById(R.id.editname);
            email =(EditText) findViewById(R.id.editemail);
            saveprofile = (Button) findViewById(R.id.savepro);
            intent = new Intent(editprofile.this,settings.class);


            saveprofile.setOnClickListener(v -> {

              edituseremail = name.getText().toString();
              edituseremail = email.getText().toString();

                Toast.makeText(getApplicationContext(),"Profile saved", Toast.LENGTH_LONG).show();

                //getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new settings()).commit();

        });
}}
