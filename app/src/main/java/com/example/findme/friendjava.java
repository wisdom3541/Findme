package com.example.findme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class friendjava extends Fragment {


   ListView list;
   SharedPreferences pref;


    public friendjava() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);

        //pref = ("user_details", MODE_PRIVATE);
         list = (ListView) view.findViewById(R.id.listview1);
        final ArrayList<String> mylist = new ArrayList<>();
        mylist.add("wisdom");
        mylist.add("favour");
        mylist.add("faith");
        mylist.add("chisom");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mylist);

        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p = mylist.get(position).toString();
                System.out.println(p);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username",p);
                editor.commit();
                Toast.makeText(getContext(),"Getting location" + p, Toast.LENGTH_LONG).show();


            }
        });

        return view;

    }
}




