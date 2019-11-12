package com.example.findme;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    AlertDialog.Builder alert;

   ListView list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);


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

                alert.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("User")
                        .setMessage("Select Options")
                        .setPositiveButton("Get location", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(getContext(),"Logging Out", Toast.LENGTH_LONG).show();
                                  }
                        })
                        .setNegativeButton("Delete Friend", null);
                        Toast.makeText(getContext(),"Deleting User",Toast.LENGTH_LONG).show();


            }
        });

        return view;

    }
}




