package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class username extends AppCompatActivity {
    TextView username;
    Button submit;
    String username1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username);

        username = (TextView) findViewById(R.id.user1);
        submit = (Button) findViewById(R.id.submituser);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username1 = username.getText().toString();

            }
        });

    }
}
