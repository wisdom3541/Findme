package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class mainpage extends AppCompatActivity {

    Button signin,signup;
    Intent intent,intent2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        signin = (Button) findViewById(R.id.firstsigninbtn);
        signup = (Button) findViewById(R.id.firstsignupbtn);
         intent = new Intent(this,login.class);
         intent2 = new Intent(this, signup.class);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
}



}
