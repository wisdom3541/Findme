package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {
    EditText Name, Email, Password, phonenumber;
    Button signup;
    String email, password, name, phone;
    Intent intent;
    private FirebaseAuth mAuth;

    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //hooks
        Name = (EditText) findViewById(R.id.suname);
        Email = (EditText) findViewById(R.id.suEmail);
        Password = (EditText) findViewById(R.id.suPassword);
        signup = (Button) findViewById(R.id.signupbtn);
        phonenumber = (EditText) findViewById(R.id.phonenumber);

        //intents
        intent = new Intent(signup.this, login.class);
        mAuth = FirebaseAuth.getInstance();

        //listerner and  validations
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Email.getText().toString();
                password = Password.getText().toString();
                name = Name.getText().toString();
                phone = phonenumber.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                    createacct();
                } else {
                    Toast.makeText(signup.this, "Please fill all details correctly", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void addusertodb() {

        //getting the db collection ref
        CollectionReference profiles = db.collection("users");
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Email", email);
        user.put("Phone No", phone);
        profiles.document(email).set(user);

    }

// Add a new document with a generated ID


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    public void createacct() {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Message", "createUserWithEmail:success");
                            addusertodb();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Log.d("Message", mAuth.getCurrentUser().toString());
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Message", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signup.this, "Email used by another user",
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


