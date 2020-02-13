package com.example.findme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addfriends extends Fragment {

    EditText add;
    Button addf;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addfriends,container,false);

        add = (EditText) view.findViewById(R.id.addftext);
        addf = (Button) view.findViewById(R.id.addf);


        //toast
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               add.setText("");
            }
        });

        addf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Friend Added",Toast.LENGTH_LONG);
            }
        });
        return  view;
    }
}
