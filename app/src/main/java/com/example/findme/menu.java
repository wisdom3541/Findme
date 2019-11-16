package com.example.findme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

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


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_cont, new addfriends()).commit();
            navigationView.setCheckedItem(R.id.add_friends);
        }


        intent1 = new Intent(menu.this, login.class);
    }

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

            case R.id.logout:
                alert.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ")
                        .setMessage("Do you want to Log out")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(menu.this, "Logging Out", Toast.LENGTH_LONG).show();
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
        } else {
            super.onBackPressed();
        }

    }
}