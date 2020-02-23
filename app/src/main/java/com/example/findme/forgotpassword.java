package com.example.findme;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class forgotpassword extends AppCompatActivity {
    Button fgbtn;
    EditText fgtxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpassword);

        fgbtn = (Button)findViewById(R.id.btnLogin);
        fgtxt = (EditText)findViewById(R.id.txtName);

            fgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Sending code to Email",Toast.LENGTH_LONG);

                    fgtxt.setText("");
                    fgtxt.setHint("Enter Code Sent to Mail");
                    fgbtn.setText("Reset");
                }
            });
    }

}
