package com.example.findme;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class friendjava extends Fragment {


    private static final String TAG = "TAG";
    //variables
    ListView list;
    Button addfriend,friendrequest;
    Intent intent,intent2;
    //get user
    String currentUserEmail;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> friendlist = new ArrayList<>();

    public friendjava() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();
            Toast.makeText(getContext(), currentUserEmail, Toast.LENGTH_SHORT).show();

        }

        CollectionReference friendList = db.collection("users").document(currentUserEmail).collection("Friends");


        friendList
                .whereEqualTo("Accepted", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> friends = document.getData();
                                friendlist.add(friends.get("friend Email").toString());
                                Log.d(TAG, String.valueOf(friendlist));
                            }

                            if(!friendlist.isEmpty()) {
                                //listview type
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, friendlist);

                                list.setAdapter(arrayAdapter);
                            }else {
                                Toast.makeText(getContext(), "You have no Friend at the moment", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });




        //hookks
        list = (ListView) view.findViewById(R.id.listview1);
        addfriend = (Button) view.findViewById(R.id.addnewfriendBTN);
        friendrequest = (Button) view.findViewById(R.id.friendrequestbtn);
        intent = new Intent(getContext(),addfriends.class);
        intent2 = new Intent(getContext(), friendrequest.class);




        addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);

            }
        });

        friendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });




        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p = friendlist.get(position);
                Toast.makeText(getContext(), p, Toast.LENGTH_LONG).show();

               /* //fragment swap
                friendsprofile fp = new friendsprofile();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frag_cont, fp);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });

        return view;

    }


}




