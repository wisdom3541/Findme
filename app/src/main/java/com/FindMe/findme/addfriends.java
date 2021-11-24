package com.FindMe.findme;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addfriends extends Activity {

    //variables
    private String email;
    EditText friendEmail;
    Button addfriendBTn;
    String useremail;
    static final boolean accepted = false;


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriends);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            useremail = user.getEmail();

        }


        friendEmail = findViewById(R.id.friendEmail);
        addfriendBTn = findViewById(R.id.addFriendBtn);


        //onClicklisterners
        addfriendBTn.setOnClickListener(v -> {

            email = friendEmail.getText().toString();

            if (!email.isEmpty()) {

                DocumentReference receiverRef = db.collection("users").document(email);

                if (!receiverRef.toString().isEmpty()) {
                    CollectionReference sendRequest = receiverRef.collection("Friend Request");

                    // Create a new user with a first and last name
                    Map<String, Object> newFriendRequest = new HashMap<>();
                    newFriendRequest.put("friend Email", useremail);
                    newFriendRequest.put("Accepted",accepted);

                    sendRequest.document(useremail).set(newFriendRequest);

                    Toast.makeText(addfriends.this, "Friend request sent...!!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(addfriends.this, "User does not exist", Toast.LENGTH_SHORT).show();
                }

                CollectionReference profiles = db.collection("users").document(useremail).collection("Friends");
                // Create a new user with a first and last name
                Map<String, Object> newFriend = new HashMap<>();
                newFriend.put("friend Email", email);
                newFriend.put("Accepted", accepted);

                profiles.document(email).set(newFriend);

            } else {
                Toast.makeText(addfriends.this, "Please enter an Email address", Toast.LENGTH_SHORT).show();
            }


        });

    }

}

