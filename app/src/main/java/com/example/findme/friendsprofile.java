package com.example.findme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class friendsprofile extends Fragment implements View.OnClickListener {

    //variables
    TextView lastloc;
    Button dir, del;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendsprofile, container, false);


        lastloc = (TextView) view.findViewById(R.id.lastloc);
        dir = (Button)view.findViewById(R.id.btn1);
        del= (Button)view.findViewById(R.id.btn2);

        dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Google maps coming up", Toast.LENGTH_LONG).show();
            }


    });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Friend Deleted", Toast.LENGTH_LONG).show();
            }


        });

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}

