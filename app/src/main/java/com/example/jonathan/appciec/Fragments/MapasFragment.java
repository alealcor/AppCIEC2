package com.example.jonathan.appciec.Fragments;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jonathan.appciec.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapasFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private Context mContext;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Get location
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();


            mGoogleApiClient.connect();
        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        /*// Add a marker in Sydney and move the camera
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LatLng here = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(here).title("YOU ARE HERE").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 14));
        // Add a marker in Sydney and move the camera*/

        LatLng casa = new LatLng(-2.2054288, -79.897019);
        mMap.addMarker(new MarkerOptions().position(casa).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.profesor))

        LatLng zona = new LatLng(-2.2047946589108136, -79.89281329619655);
        mMap.addMarker(new MarkerOptions().position(zona).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));

        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(casa,15));
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /*Intent intent = new Intent (mContext, AlertDatosProfesorActivity.class);
        startActivity(intent);*/
        //new AlertDatosProfesorActivity(mContext);
        return false;
    }
}
