package com.example.findme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class settings extends Fragment {

    Button editpro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        editpro= (Button) view.findViewById(R.id.editprobtn);


        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       return view;
    }
}
