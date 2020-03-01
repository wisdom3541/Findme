package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    EditText Name,Email,Password,vPassword,edittextphone;
    Button signup;
    String email,password,passwordverify,name,phone;
    Intent intent;
    private FirebaseAuth mAuth;
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //revrieving id
        Name = (EditText) findViewById(R.id.suname);
        Email = (EditText) findViewById(R.id.suEmail);
        Password = (EditText)findViewById(R.id.suPassword);
        vPassword = (EditText)findViewById(R.id.suverifyPassword);
        signup = (Button)findViewById(R.id.signupbtn);
        edittextphone = (EditText)findViewById(R.id.Phonen);

      intent  = new Intent(signup.this,login.class);
        mAuth = FirebaseAuth.getInstance();

        //listerner and  validations
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = Email.getText().toString() ;
                password = Password.getText().toString();
                name = Name.getText().toString();
                passwordverify = vPassword.getText().toString();
                phone = edittextphone.getText().toString();

                    if (name != null && email != null && password != null && passwordverify != null) {
                        if(!phone.equals("")) {

                            if (password.equals(passwordverify)) {

                                createacct();
                            }
                            else {

                                Toast.makeText(com.example.findme.signup.this,"Password doesn't match", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(com.example.findme.signup.this,"Enter Phone Number", Toast.LENGTH_LONG).show();
                        }
                }
                    else {
                        Toast.makeText(com.example.findme.signup.this,"Fill all details", Toast.LENGTH_LONG).show();
                    }

            }
        });

    }

    public void addusertodb()
    {

        CollectionReference profiles = db.collection("users");
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("Name", name);
        user.put("Email", email);
        user.put("Phone No", phone);
        profiles.document(email).set(user);

// Add a new document with a generated ID


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
                            Log.d("Message", "createUserWithEmail:success");
                            addusertodb();
                            startActivity(intent);
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Message", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signup.this, "Please Enter a Valid Email",
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


