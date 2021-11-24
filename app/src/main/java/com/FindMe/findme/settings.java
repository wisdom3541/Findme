package com.FindMe.findme;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View; 
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;


public class settings extends Fragment {

    private static final String TAG = "TAG";
    //variables
    Button editpro, deleteuserbutton;
    TextView email;
    TextView username;
    Intent intent, intent1;
    String email1,user,password;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);

        //hooks
        email = (TextView) view.findViewById(R.id.useremail);
        username = (TextView) view.findViewById(R.id.user1);
        deleteuserbutton = (Button) view.findViewById(R.id.deleteuserbtn);
        intent = new Intent(getContext(), editprofile.class);
        intent1 = new Intent(getContext(), MainPage.class);
        editpro = (Button) view.findViewById(R.id.editprofilebtn);


        //set username to setting
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d("TAG", user.toString());
            email1 = user.getEmail();
            email.setText(email1);

        }


        //listerner
        editpro.setOnClickListener(v -> startActivity(intent));


        deleteuserbutton.setOnClickListener(v ->  {

                db.collection("users").document(email1)
                        .delete()
                        .addOnSuccessListener(aVoid -> {

                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();

                            user1.delete()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "User account deleted.");
                                        }
                                    });
                            testingtReAuth();

                            Log.d(TAG, "DocumentSnapshot successfully deleted!");

                        })
                        .addOnFailureListener(e -> Log.w("TAG: ", "Error deleting document", e));

                FirebaseAuth.getInstance().signOut();

                startActivity(intent1);
            });


        return view;
    }

    public void testingtReAuth() {

        user = login.loginPreferences.getString("username", "");
        password = login.loginPreferences.getString("password", "");

        Log.d(TAG, user + "  " +  password);

        //re-authenticate for delete
        if (user != null && password != null) {


            FirebaseUser userInstance = FirebaseAuth.getInstance().getCurrentUser();

            // Get auth credentials from the user for re-authentication. The example below shows
            // email and password credentials but there are multiple possible providers,
            // such as GoogleAuthProvider or FacebookAuthProvider.
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user, password);

            // Prompt the user to re-provide their sign-in credentials
            userInstance.reauthenticate(credential)
                    .addOnCompleteListener(task -> Log.d(TAG, "User re-authenticated."));
        }
    }


}
