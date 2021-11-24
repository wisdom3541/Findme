package com.FindMe.findme;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class currentLocation extends Fragment implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int DEFAULT_ZOOM = 15;

    private FusedLocationProviderClient fusedLocationProviderClient;


    // variable declaration
    private final LatLng defaultLocation = new LatLng(6.5410707, 3.2926795);
    private GoogleMap map;
    static String email;
    String prevFrag = null;
    public double latitude, longitude, selectedFriendLongitude, selectedFriendLatitude;
    public boolean lastLocation;
    public static boolean fromFriendActivity;
    SharedPreferences friendLocationPreferences;
    String friendSelected;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public currentLocation() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);


        // Get a handle to the fragment and register the callback.

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email = user.getEmail();

        }


        friendLocationPreferences = requireActivity().getSharedPreferences("friendLocation", MODE_PRIVATE);


        return view;

    }


    //don't be scared....google maps documentation..... some tweaksss
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        enableMyLocation();
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(6.5410707, 3.2926795), DEFAULT_ZOOM));


        if (map != null) {
            //map.addMarker(new MarkerOptions().position(new LatLng(6.5410707, 3.2926795)).title("Marker"));
            if (ActivityCompat.checkSelfPermission(requireActivity().getApplicationContext()
                    , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext()
                    , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Log.d("TAG", String.valueOf(fromFriendActivity));

            //fromFriendActivity = friendLocationPreferences.getBoolean("fromFriendList", false);


            try {
                FragmentManager fm = getFragmentManager();
                assert getFragmentManager() != null;
                int count = getFragmentManager().getBackStackEntryCount();
                prevFrag = fm.getBackStackEntryAt(count - 1).getName();
                Log.d("TAG", prevFrag);

                //

                if (prevFrag.equals("friend")) {

                    friendSelected = friendLocationPreferences.getString("selectedfriend", "");
                    Log.d("TAG", "friend Selected:" + friendSelected);

                    getFriendLocation(friendSelected);

                }

            } catch (NullPointerException e) {
                Log.d("TAG", e.toString());
            }


            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);


        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            boolean locationPermissionGranted = true;
            if (map != null) {
                map.setMyLocationEnabled(true);

            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "Getting Loation....please wait", Toast.LENGTH_LONG).show();


        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Log.d(TAG, String.valueOf(location));
        Toast.makeText(getContext(), "Me", Toast.LENGTH_LONG).show();

        location.getLongitude();
        location.getLatitude();
        Log.d(TAG, location.getLatitude() + "   " + location.getLongitude());

        longitude = location.getLongitude();
        latitude = location.getLatitude();

        Log.d("lat", String.valueOf(latitude));
        Log.d("lat", String.valueOf(longitude));

        map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Me"));

        updateUserLocation();
        // getDeviceLocation();
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            showMissingPermissionError();
            /**
             * Flag indicating whether a requested permission has been denied after returning in
             * {@link #onRequestPermissionsResult(int, String[], int[])}.
             */
            boolean permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getChildFragmentManager(), "Location access needed and turn on your location");
    }

    private void updateUserLocation() {


        if (String.valueOf(longitude).isEmpty() || String.valueOf(latitude).isEmpty()) {

            lastLocation = false;
            Toast.makeText(getContext(), String.valueOf(lastLocation), Toast.LENGTH_SHORT).show();

            //no nothing
        } else {

            DocumentReference profiles = db.collection("users").document(email);
            // updates user location in database
            Map<String, Object> lastlocation = new HashMap<>();
            lastlocation.put("Longitude", longitude);
            lastlocation.put("Latitude", latitude);
            profiles.update(lastlocation);

            // Log.d(TAG, String.valueOf(longitude + latitude));

            lastLocation = true;
            //  Toast.makeText(getContext(), String.valueOf(lastLocation), Toast.LENGTH_SHORT).show();

        }


    }

    public void getFriendLocation(String friendSelected) {
        DocumentReference collectionReference = db.collection("users").document(friendSelected);

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    selectedFriendLongitude = (double) document.get("Longitude");
                    selectedFriendLatitude = (double) document.get("Latitude");

                    Log.d("Tag", String.valueOf(selectedFriendLatitude));
                    Log.d("Tag", String.valueOf(selectedFriendLongitude));


                    //validating that DB didn't return empty string
                    if (!String.valueOf(selectedFriendLongitude).isEmpty() || !String.valueOf(selectedFriendLatitude).isEmpty())
                        getPrecise();
                    else
                        Toast.makeText(getContext(), "User isn't sharing location at the moment", Toast.LENGTH_SHORT).show();


                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }


    // dr
    public void getPrecise() {


        Log.d("Tag", String.valueOf(selectedFriendLatitude));
        Log.d("Tag", String.valueOf(selectedFriendLongitude));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selectedFriendLatitude, selectedFriendLongitude), DEFAULT_ZOOM));
        map.addMarker(new MarkerOptions().position(new LatLng(selectedFriendLatitude, selectedFriendLongitude)).title("Me"));


    }


}








