package com.example.findme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast; 

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class fragmentholder extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //variables
    Intent intent1;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentholder);

        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //hooks, intent
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigation);
        intent1 = new Intent(fragmentholder.this, login.class);

        navigationView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Toast.makeText(this, "Getting Location", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new currentlocation()).commit();
        }


    }

    //bottom nav listerner
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new currentlocation()).commit();
                break;
            case R.id.friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new friendjava()).commit();
                break;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new settings()).commit();
                break;

        }
        return true;
    }

    //back pressed
    @Override
    public void onBackPressed() {
        alert = new AlertDialog.Builder(this);
        alert.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Are you sure ")
                .setMessage("Do you want to Log out")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        //signout pastor
                        FirebaseAuth.getInstance().signOut();
                        startActivity(intent1);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }
}