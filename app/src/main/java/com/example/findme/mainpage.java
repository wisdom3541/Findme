package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class mainpage extends AppCompatActivity {

    Button signin,signup;
    Intent intent,intent2;
    final FirebaseAuth mAuth
    FirebaseUser currentUser = mAuth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        signin = (Button) findViewById(R.id.firstsigninbtn);
        signup = (Button) findViewById(R.id.firstsignupbtn);
         intent = new Intent(this,login.class);
         intent2 = new Intent(this, signup.class);
         mAuth = FirebaseAuth.getInstance();
        Paper.init(this);

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

        String email = Paper.book().read(rememberme.rememberuseremail);
        String password = Paper.book().read(rememberme.rememberuserpassword);

        if( email != "" && password != "")
        {
            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                signin();
            }
        }


        public  void signin(){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success");
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



}
