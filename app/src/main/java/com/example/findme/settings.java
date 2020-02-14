package com.example.findme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class settings extends Fragment {

    Button editpro;
    TextView email;
    Intent intent;
    String email1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        editpro= (Button) view.findViewById(R.id.editprobtn);
        email = (TextView) view.findViewById(R.id.useremail);
        intent = new Intent(getContext(),editprofile.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address
            String name = user.getDisplayName();
             email1 = user.getEmail();

             email.setText(email1);
        }




        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);

            }
        });

       return view;
    }
}
