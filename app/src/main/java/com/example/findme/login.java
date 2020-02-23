package com.example.findme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;


public class login extends AppCompatActivity {
    //varaible
    EditText uname, pwd;
    Button loginBtn,signupb;
    TextView forgot,user1;
    SharedPreferences pref;
    Intent intent,intent2,intent3;
    private FirebaseAuth mAuth;
    String email,password;
    CheckBox remembermecheck;
    private static final String TAG = "My activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        uname = (EditText)findViewById(R.id.txtName);
        pwd = (EditText)findViewById(R.id.txtPwd);
        loginBtn = (Button)findViewById(R.id.btnLogin);
        signupb =(Button)findViewById(R.id.signupbtn);
        forgot=(TextView) findViewById(R.id.forgot);
        remembermecheck = (CheckBox) findViewById(R.id.check);
        Paper.init(this);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(login.this, com.example.findme.menu.class);
        intent2 = new Intent(login.this, forgotpassword.class);
        intent3 = new Intent(login.this,signup.class);
        mAuth = FirebaseAuth.getInstance();


        //button onclick listerner to call signin method from firebaseauth
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = uname.getText().toString();
                password = pwd.getText().toString();
                String username = uname.getText().toString();
                String password = pwd.getText().toString();

                if(remembermecheck.isChecked()){
                    Paper.book().write(rememberme.rememberuseremail,email);
                    Paper.book().write(rememberme.rememberuserpassword,password);
                }

                signin();


                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username",username);
                    editor.putString("password",password);
                    editor.commit();


            }
        });

        //calls the forgot password menu
        forgot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(intent2);
            }
        });

        //calls the sign up menu
        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });
        
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public  void signin(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void updateUI(FirebaseUser currentUser) {


    }
}
