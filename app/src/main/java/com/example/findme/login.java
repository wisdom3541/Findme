package com.example.findme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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

    //varaibles
    EditText username, pwd;
    Button loginBtn, signupb;
    TextView forgot;
    Intent intent, intent2, intent3;
    private FirebaseAuth mAuth;
    String email, password;
    CheckBox remembermecheck;

    public static SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    private static final String TAG = "My activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //hooks, intents
        username = (EditText) findViewById(R.id.txtName);
        pwd = (EditText) findViewById(R.id.txtPwd);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        signupb = (Button) findViewById(R.id.signupbtn);
        forgot = (TextView) findViewById(R.id.forgot);
        remembermecheck = (CheckBox) findViewById(R.id.check);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        intent = new Intent(login.this, fragmentholder.class);
        intent2 = new Intent(login.this, forgotpassword.class);
        intent3 = new Intent(login.this, signup.class);
        mAuth = FirebaseAuth.getInstance();


        saveLogin = loginPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            username.setText(loginPreferences.getString("username", ""));
            pwd.setText(loginPreferences.getString("password", ""));
            remembermecheck.setChecked(true);
        }


        //listerners
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = username.getText().toString();
                password = pwd.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                        // shared pref for rememberme login
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

                        email = username.getText().toString();
                        password = pwd.getText().toString();

                        if (remembermecheck.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("username", email);
                            loginPrefsEditor.putString("password", password);
                            loginPrefsEditor.commit();
                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }

                    signin();
                } else {
                    Toast.makeText(login.this, "Please enter your email and password", Toast.LENGTH_LONG).show();
                }

            }
        });

        //calls the forgot password page
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
                finish();
            }
        });

        //calls the sign up page
        signupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
                finish();
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

    public void signin() {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(login.this, user.toString(),
                                    Toast.LENGTH_LONG).show();
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Invalid Credentials",
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
