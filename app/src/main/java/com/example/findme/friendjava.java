package com.example.findme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

        pref = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
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
                System.out.println("me " + p);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("username",p);
                editor.commit();

                friendsprofile fp = new friendsprofile();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_cont, fp);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;

    }
}




