package com.tassioauad.pedometro.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tassioauad.pedometro.R;
import com.tassioauad.pedometro.model.api.LocationManager;

public class TestActivitiy extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private TextView textviewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //Verifying ACCESS_FINE_LOCATION permission. If negative, requesting the permission.
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        textviewLocation = (TextView) findViewById(R.id.textview_location);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = new LocationManager(this, new LocationManager.LocationManagerListener() {
                        @Override
                        public void onLocationCaptured(Location location) {
                            textviewLocation.setText(location.getLatitude() + " " + location.getLongitude());
                        }
                    });
                    locationManager.startToCapture();
                }
            }
        }
    }
}
