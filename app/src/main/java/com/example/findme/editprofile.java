package com.example.findme;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class editprofile extends AppCompatActivity {

    String edituseremail;
    EditText name,email;
    Button saveprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

            name = (EditText) findViewById(R.id.editname);
            email =(EditText) findViewById(R.id.editemail);
            saveprofile = (Button) findViewById(R.id.savepro);


            saveprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  edituseremail = name.getText().toString();
                  edituseremail = email.getText().toString();

                    Toast.makeText(getApplicationContext(),"Profile saved", Toast.LENGTH_LONG).show();

                }
            });
}}
