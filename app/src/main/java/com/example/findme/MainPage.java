package com.example.findme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class MainPage extends AppCompatActivity {

    SharedPreferences loginPreferences;
    Intent intent, intent2, intent3;
    FirebaseAuth mAuth;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getting Shared preference
        boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin) {
            email = loginPreferences.getString("username", "");
            password = loginPreferences.getString("password", "");
            //method
            signIn();
        }
        //end

        //hooks
        Button signIn = (Button) findViewById(R.id.firstsigninbtn);
        Button signup = (Button) findViewById(R.id.firstsignupbtn);
        intent = new Intent(this, login.class);
        intent2 = new Intent(this, signup.class);
        intent3 = new Intent(this, fragmentholder.class);
        mAuth = FirebaseAuth.getInstance();


        //listerners
        signIn.setOnClickListener(v -> startActivity(intent));
        signup.setOnClickListener(v -> startActivity(intent2));

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        System.out.println(currentUser);
    }

    //auto sign in if remember password is on
    public void signIn() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(MainPage.this, user.toString(),
                                    Toast.LENGTH_LONG).show();
                            startActivity(intent3);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Tag", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainPage.this, "Invalid Credentials",
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
