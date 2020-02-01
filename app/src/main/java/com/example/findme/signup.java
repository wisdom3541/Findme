package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signup extends AppCompatActivity {
    TextView username1;
    EditText Name,Email,Password,user;
    Button signup,submit;
    String email,password,username;
    Intent intent;
    private FirebaseAuth mAuth;
    private static final String TAG = "My activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Name = (EditText) findViewById(R.id.suname);
        Email = (EditText) findViewById(R.id.suEmail);


        Password = (EditText)findViewById(R.id.suPassword);
        signup = (Button)findViewById(R.id.signupbtn);

        intent = new Intent(signup.this,login.class);
        mAuth = FirebaseAuth.getInstance();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString() ;
                password = Password.getText().toString();
                createacct();
            }
        });

    }



    public void username(){
        setContentView(R.layout.username);
        user = (EditText) findViewById(R.id.user);
        username1 = (TextView) findViewById(R.id.username);
        submit= (Button) findViewById(R.id.submituser);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               username = user.getText().toString();

                startActivity(intent);
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


    public  void createacct(){
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            username();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]
    }


    private void updateUI(Object o) {
    }
}


