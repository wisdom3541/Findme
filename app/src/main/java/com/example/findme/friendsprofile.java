package com.example.findme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class friendsprofile extends Fragment implements View.OnClickListener {
    SharedPreferences pref1;
    TextView user,lastloc;
    Button dir, del;
    String username,Lastlocation;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendsprofile, container, false);


        pref1 = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

        pref1 = this.getActivity().getSharedPreferences("LastLocation", Context.MODE_PRIVATE);

        //user = (TextView) view.findViewById(R.id.username);
        lastloc = (TextView) view.findViewById(R.id.lastloc);
        dir = (Button)view.findViewById(R.id.btn1);
        del= (Button)view.findViewById(R.id.btn2);

        username = pref1.getString("username" , null);
        Lastlocation = pref1.getString("LastLocation",null);

        user.setText(username);
        lastloc.setText("Last known location: " + Lastlocation);

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

