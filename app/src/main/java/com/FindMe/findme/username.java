package com.FindMe.findme;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity; 

public class username extends AppCompatActivity {
    TextView username;
    Button submit;
    String username1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);

        username = findViewById(R.id.user1);
        submit =  findViewById(R.id.submituser);

        submit.setOnClickListener(v -> username1 = username.getText().toString());

    }
}
