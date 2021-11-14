package com.FindMe.findme;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class friendRequestOption extends Activity {

    private static final String TAG = "TAG";
    private int width, height;
    private String email,currentUserEmail;
    TextView title;
    Button accept, decline;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendrequestoption);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUserEmail = user.getEmail();

        }

        //hooks
        accept = findViewById(R.id.acceptButton);
        decline = findViewById(R.id.declineButton);
        title = findViewById(R.id.requestFrom);

        //getting info in intent
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");

        //reducing the activity layout size
        reducelayout();


        if (!email.isEmpty()) {
            String email_ = "Request from: " + email;
            title.setText(email_);

        }



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //updating accepted user to current user friend list to true
                CollectionReference acceptrequest = db.collection("users").document(email)
                        .collection("Friends");

                Map<String, Object> newFriendRequest = new HashMap<>();
                newFriendRequest.put("Accepted", true);
                acceptrequest.document(currentUserEmail).update(newFriendRequest);


                //updating the accepted user-request friend list
                CollectionReference profiles = db.collection("users").document(currentUserEmail).collection("Friends");
                // add accepted user to friend list
                Map<String, Object> newFriend = new HashMap<>();
                newFriend.put("friend Email", email);
                newFriend.put("Accepted", true);

                profiles.document(email).set(newFriend);

                //updating that request as been accepted
                //that user will be removed from the friend request list
                CollectionReference friendRequest = db.collection("users").document(currentUserEmail).collection("Friend Request");
                Map<String, Object> updateFriendRequestList = new HashMap<>();
                updateFriendRequestList.put("Accepted", true);

                friendRequest.document(email).update(updateFriendRequestList);

                Toast.makeText(friendRequestOption.this, "Friend Request Accepted", Toast.LENGTH_SHORT).show();
            }
        });

        //decline and then delete friend and friend request from database
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CollectionReference deleteDeclineUser = db.collection("users").document(currentUserEmail).collection("Friend Request");
                CollectionReference deleteUserinfoFromSenderlist = db.collection("users").document(email).collection("Friends");

                deleteDeclineUser.document(email)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(friendRequestOption.this, "Request declined", Toast.LENGTH_LONG).show();
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });

                deleteUserinfoFromSenderlist.document(currentUserEmail)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Log.d(TAG, "deleteUserinfoFromSenderlist successful!");
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });




                Toast.makeText(friendRequestOption.this, "Friend Request Declined", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  void reducelayout(){

        //reducing the activity layout size
        //start
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .4));

        //end
    }
}
