package com.FindMe.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class friendrequest extends Activity {

    private static final String TAG = "TAG";
    ListView listView;
    String email;
    Intent intent;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> myList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendrequest);

        intent = new Intent(friendrequest.this, friendRequestOption.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();

        }

        //hooks
        listView = findViewById(R.id.listview);


        getUpdate_Friend_List(email);

    }


    public void getUpdate_Friend_List(String email) {

        checkDb(email);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String senderEmail = myList.get(position);

            intent.putExtra("email", senderEmail);
            startActivity(intent);

        });

    }

    public void checkDb(String email) {

        // retrieving friend request list from db
        //START
        CollectionReference friendRequestFromDB = db.collection("users").document(email)
                .collection("Friend Request");

        friendRequestFromDB
                .whereEqualTo("Accepted", false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Map<String, Object> test = document.getData();
                            myList.add(Objects.requireNonNull(test.get("friend Email")).toString());
                            Log.d(TAG, String.valueOf(myList));
                        }

                        if (!myList.isEmpty()) {
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(friendrequest.this, android.R.layout.simple_list_item_1, myList);
                            listView.setAdapter(arrayAdapter);

                        } else {
                            Toast.makeText(this, "You have no friend request at the moment....", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });

    }

}
