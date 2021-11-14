package com.FindMe.findme;


import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class friendjava extends Fragment {


    private static final String TAG = "TAG";
    //variables
    ListView list;
    Button addFriend, friendRequest;
    Intent intent, intent2, intent3;
    ImageView notification;
    BottomNavigationView navbar;
    //get user
    String currentUserEmail, selectedFriend;

    public static SharedPreferences friendLocationPreferences;
    public static SharedPreferences.Editor friendLocationPrefsEditor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> friendList = new ArrayList<>();


    public friendjava() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);

        notification = view.findViewById(R.id.notification);
        navbar = view.findViewById(R.id.friends_ic);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();
            Toast.makeText(getContext(), currentUserEmail, Toast.LENGTH_SHORT).show();

        }

        try {
            CollectionReference friendList = db.collection("users").document(currentUserEmail).collection("Friends");

            friendList
                    .whereEqualTo("Accepted", true)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> friends = document.getData();
                                friendjava.this.friendList.add(friends.get("friend Email").toString());
                                Log.d(TAG, String.valueOf(friendjava.this.friendList));
                            }
                            try {
                                if (!friendjava.this.friendList.isEmpty()) {
                                    //listview type
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, friendjava.this.friendList);
                                    if (!arrayAdapter.isEmpty())
                                        list.setAdapter(arrayAdapter);
                                    else {
                                        Log.d("Friend list status", "array empty or not received yet");
                                    }
                                } else {
                                    Toast.makeText(getContext(), "You have no Friend at the moment", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("LOG : ", e.toString());
                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    });

        } catch (Exception e) {
            Log.d("Error", e.toString());
        }

        try {
            updateNotification();
        } catch (Exception e) {
            Log.d("Notification Exception :", e.toString());
        }


        //hookks
        list = view.findViewById(R.id.listview1);
        addFriend = view.findViewById(R.id.addNewFriendBTN);
        friendRequest = view.findViewById(R.id.Friend_request_btn);
        intent = new Intent(getContext(), addfriends.class);
        intent2 = new Intent(getContext(), friendrequest.class);
        intent3 = new Intent(getContext(), friendsprofile.class);
        friendLocationPreferences = getContext().getSharedPreferences("friendLocation", MODE_PRIVATE);
        friendLocationPrefsEditor = friendLocationPreferences.edit();


        addFriend.setOnClickListener(v -> startActivity(intent));

        friendRequest.setOnClickListener(view1 -> startActivity(intent2));


        list.setOnItemClickListener((parent, view12, position, id) -> {
            selectedFriend = this.friendList.get(position);
            //startActivity(intent3);
            Toast.makeText(getContext(), "Finding: " + selectedFriend, Toast.LENGTH_SHORT).show();
            //getFriendLocation();

            friendLocationPrefsEditor.putString("selectedfriend", selectedFriend);
            friendLocationPrefsEditor.commit();

            currentLocation cl = new currentLocation();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frag_cont, cl);
            fragmentTransaction.addToBackStack("friend");
            fragmentTransaction.commit();

        });

        return view;

    }

    public void updateNotification() {

        CollectionReference friendRequestFromDB = db.collection("users").document(currentUserEmail)
                .collection("Friend Request");

        friendRequestFromDB
                .whereEqualTo("Accepted", false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        for (QueryDocumentSnapshot ignored : task.getResult()) {
                            notification.setVisibility(View.VISIBLE);
                        }
                    else
                        notification.setVisibility(View.INVISIBLE);
                });

    }








    /* public void getFriendLocation() {
        DocumentReference collectionReference = db.collection("users").document(selectedFriend);

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        longitude = (double) document.get("Longitude");
                        latitude =  (double)document.get("Latitude");

                        if (!String.valueOf(longitude).isEmpty() || !String.valueOf(latitude).isEmpty()) {
                            //friendLocationPrefsEditor.putBoolean("fromFriendList", true);
                            currentLocation.fromFriendActivity = true;
                            friendLocationPrefsEditor.putInt("longitude", (int) longitude);
                            friendLocationPrefsEditor.putInt("latitude", (int) latitude);
                            friendLocationPrefsEditor.commit();


                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        } else {
                            Toast.makeText(getContext(), "User isn't sharing location at the moment", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }*/

}




