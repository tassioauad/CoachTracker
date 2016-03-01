package com.tassioauad.pedometro.model.api;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleApiClient googleApiClient;
    private Context context;
    private LocationManagerListener locationManagerListener;

    public LocationManager(Context context, LocationManagerListener locationManagerListener) {
        this.context = context;
        this.locationManagerListener = locationManagerListener;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void startToCapture() {
        googleApiClient.connect();
    }

    public void stopToCapture() {
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        stopToCapture();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        stopToCapture();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManagerListener.onLocationCaptured(location);
    }

    public interface LocationManagerListener {
        void onLocationCaptured(Location location);
    }
}
