package com.example.findme;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
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
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
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

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    private boolean locationPermissionGranted;
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


        friendLocationPreferences = getActivity().getSharedPreferences("friendLocation", MODE_PRIVATE);


        return view;

    }


    //don't be scared....google maps documentation..... some tweaksss
    @Override
    public void onMapReady(GoogleMap googleMap) {

        enableMyLocation();
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(6.5410707, 3.2926795), 15));


        if (map != null) {
            //map.addMarker(new MarkerOptions().position(new LatLng(6.5410707, 3.2926795)).title("Marker"));
            if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext()
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

            Log.d("TAG", String.valueOf(fromFriendActivity));

            try {
                FragmentManager fm = getFragmentManager();
                int count = getFragmentManager().getBackStackEntryCount();
                prevFrag = fm.getBackStackEntryAt(count - 1).getName();
                Log.d("TAG", prevFrag);

                //

                if (prevFrag.equals("friend")) {

                    friendSelected = friendLocationPreferences.getString("selectedfriend", "");
                    Log.d("TAG", "friend" + friendSelected);

                    getFriendLocation();

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
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
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
            permissionDenied = true;
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

    public void getFriendLocation() {
        DocumentReference collectionReference = db.collection("users").document(friendSelected);

        collectionReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    selectedFriendLongitude = (double) document.get("Longitude");
                    selectedFriendLatitude = (double) document.get("Latitude");

                    Log.d("Tag", String.valueOf(selectedFriendLatitude));
                    Log.d("Tag", String.valueOf(selectedFriendLongitude));


                    if (!String.valueOf(selectedFriendLongitude).isEmpty() || !String.valueOf(selectedFriendLatitude).isEmpty()) {


                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        draw();

                    } else {
                        Toast.makeText(getContext(), "User isn't sharing location at the moment", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }


    public void draw() {


        Log.d("Tag", String.valueOf(selectedFriendLatitude));
        Log.d("Tag", String.valueOf(selectedFriendLongitude));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new
                LatLng(selectedFriendLatitude, selectedFriendLongitude), 15));



        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


    }


   /* private void getDeviceLocation() {

         //* Get the best and most recent location of the device, which may be null in rare
        // * cases when a location is not available.

        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener((Executor) this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            Log.d(TAG,(lastKnownLocation.getLatitude() + "   "  + lastKnownLocation.getLongitude()));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }*/


}


















    /* View v = inflater.inflate(R.layout.activity_maps, container, false);

     return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checklocationpermission();
        }

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
        if(ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            buildGoogleApiClient();
            map1.setMyLocationEnabled(true);
        }

    }

    public boolean checklocationpermission(){
        if (ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                    ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestlocation );
                }
                else {
                    ActivityCompat.requestPermissions(this.getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestlocation );

                }
                return false;
        }
        else{
            return true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case requestlocation:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        if (googleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                        map1.setMyLocationEnabled(true);
                    }
                }
                else{
                    Toast.makeText(this.getContext(), "Permission denied", Toast.LENGTH_SHORT).show();

                }
         return;
        }

    }

    protected synchronized void  buildGoogleApiClient(){

        googleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;

        if(userlocmarker != null){
            userlocmarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        double latt = location.getLatitude();
        double longg = location.getLongitude();

        geocoder = new Geocoder(this.getContext(), Locale.getDefault());
        try {
            locadss = geocoder.getFromLocation(latt,longg,1);
             address = locadss.get(0).getAddressLine(0);
        } catch (IOException e){
            e.printStackTrace();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(address);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


        userlocmarker = map1.addMarker(markerOptions);
        // Selct maptype
        map1.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        map1.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map1.animateCamera(CameraUpdateFactory.zoomBy(20));


        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(11000);
        locationRequest.setFastestInterval(11000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }



    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/

