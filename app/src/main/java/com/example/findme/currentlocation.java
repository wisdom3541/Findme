package com.example.findme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class currentlocation extends Fragment implements OnMapReadyCallback {

    GoogleMap map1;
    public currentlocation(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
     View v = inflater.inflate(R.layout.activity_maps, container, false);

     return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map, mMapFragment);
        fragmentTransaction.commit();
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map1= googleMap;
        LatLng loc = new LatLng(11,104);
        MarkerOptions options = new MarkerOptions();
        options.position(loc).title("You are here");
        map1.addMarker(options);
        map1.moveCamera(CameraUpdateFactory.newLatLng(loc));
    }
}
