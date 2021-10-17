package com.example.findme;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
 
public class forgotpassword extends AppCompatActivity {

    //variable
    private static final String TAG = "TAG";
    Button sendbutton;
    EditText recoverymail;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        //hoooks
        sendbutton = (Button) findViewById(R.id.recoverysendemailbutton);
        recoverymail = (EditText) findViewById(R.id.recoveryemail);


        //listerner
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = recoverymail.getText().toString();

                if (!email.isEmpty()) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Email sent.");
                                        Toast.makeText(forgotpassword.this, "Email sent...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                } else {


                    Toast.makeText(forgotpassword.this, "Please enter your email address...", Toast.LENGTH_LONG).show();


                }


            }
        });


    }

}
