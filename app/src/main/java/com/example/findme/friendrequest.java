package com.example.findme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class friendrequest extends Activity {

    private static final String TAG = "TAG";
    ListView listView;
    String email;
    Intent intent;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final ArrayList<String> mylist = new ArrayList<>();


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


        // retrieving friend request list from db
        //START
        CollectionReference friendRequestfromDB = db.collection("users").document(email)
                .collection("Friend Request");

        friendRequestfromDB
                .whereEqualTo("Accepted", false)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String, Object> test = document.getData();
                                mylist.add(test.get("friend Email").toString());
                                Log.d(TAG, String.valueOf(mylist));
                            }

                            if (!mylist.isEmpty()) {
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(friendrequest.this, android.R.layout.simple_list_item_1, mylist);

                                listView.setAdapter(arrayAdapter);

                            } else {
                                Toast.makeText(friendrequest.this, "You have no friend request at the moment....", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //END


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String senderEmail = mylist.get(position);


                intent.putExtra("email", senderEmail);
                startActivity(intent);

            }
        });
    }
}
