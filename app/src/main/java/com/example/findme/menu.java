package com.example.findme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Intent intent1;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark);

        alert = new AlertDialog.Builder(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.navbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //sidebar open and close
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Toast.makeText(this,"Getting Location", Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new currentlocation()).commit();
            navigationView.setCheckedItem(R.id.location);
        }


        intent1 = new Intent(menu.this, login.class);
    }

    //sidebar listerner and fragment listerner
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.location:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new currentlocation()).commit();
                break;
            case R.id.add_friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new addfriends()).commit();
                break;

            case R.id.friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new friendjava()).commit();
                break;

            case R.id.settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new settings()).commit();
                break;

            case R.id.logout:
                alert.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ")
                        .setMessage("Do you want to Log out")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                //signout pastor
                                FirebaseAuth.getInstance().signOut();
                                startActivity(intent1);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}