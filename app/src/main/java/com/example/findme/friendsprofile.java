package com.example.findme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class friendsprofile extends Fragment  {

    TextView user,lastloc;
    Button dir, del;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friendsprofile, container, false);

        user = (TextView) view.findViewById(R.id.username);
        lastloc = (TextView) view.findViewById(R.id.lastloc);
        dir = (Button)view.findViewById(R.id.btn1);
        del= (Button)view.findViewById(R.id.btn2);



        return view;
    }
}