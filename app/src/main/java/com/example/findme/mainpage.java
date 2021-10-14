package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class mainpage extends AppCompatActivity {

    private Button signin,signup;
    Intent intent,intent2,intent3;
    String email;
    String password;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //retrieving id
        signin = (Button) findViewById(R.id.firstsigninbtn);
        signup = (Button) findViewById(R.id.firstsignupbtn);
        intent = new Intent(this, login.class);
        intent2 = new Intent(this, signup.class);
        intent3 = new Intent(this, fragmentholder.class);
        mAuth = FirebaseAuth.getInstance();
        Paper.init(this);


        //listerners
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


        //previously logged in checker

/*         email = Paper.book().read(rememberme.rememberuseremail);
         password = Paper.book().read(rememberme.rememberuserpassword);

        if (email != "" && password != "") {
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                signin();
            }
        }
    */}

        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }

        /*public  void signin(){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                startActivity(intent3);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Message", "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_LONG).show();
                                updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]
        }*/

        private void updateUI(FirebaseUser currentUser) {


        }





}
