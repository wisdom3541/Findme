package com.example.findme;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.annotations.Nullable;

public class addfriends extends Fragment {

    //variables
    EditText add;
    Button addf;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addfriends,container,false);

        add = (EditText) view.findViewById(R.id.addftext);
        addf = (Button) view.findViewById(R.id.addf);


        //toast
        //onClicklisterners
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
