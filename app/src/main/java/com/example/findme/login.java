package com.example.findme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class login extends AppCompatActivity {
    EditText uname, pwd;
    Button loginBtn,signupb;
    TextView forgot;
    SharedPreferences pref;
    Intent intent,intent2,intent3;
    MediaPlayer song;
    Menu menu;


    String[] username={"wisdom","tobi","ay","ella","mide","temi","faith","ayus","chidinma","solomon"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        signupb =(Button)findViewById(R.id.signupbtn);
        forgot=(TextView) findViewById(R.id.forgot);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(login.this, com.example.findme.menu.class);
        intent2 = new Intent(login.this, forgotpassword.class);
        intent3 = new Intent(login.this,signup.class);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String username = uname.getText().toString();
                String password = pwd.getText().toString();
                if(username.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"Username Invalid",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Username Invalid",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();
                    Toast toast = Toast.makeText(getApplicationContext(), "Login Successful",Toast.LENGTH_SHORT);
                    toast.show();
                    startActivity(intent);
                }
            }
        });
        forgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent2);
            }
        });

        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
    }
}
