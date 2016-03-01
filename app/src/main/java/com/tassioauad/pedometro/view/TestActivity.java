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
import com.tassioauad.pedometro.model.api.LocationCapturerImpl;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;

public class TestActivity extends AppCompatActivity {

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
                    LocationCapturerImpl locationCapturerImpl = new LocationCapturerImpl(this);
                    locationCapturerImpl.setLocationCapturerListener(new LocationCapturerListener() {

                        @Override
                        public void connectionFailed(String errorMessage) {
                            textviewLocation.setText(errorMessage);
                        }

                        @Override
                        public void onLocationCaptured(double latitude, double longitude) {
                            textviewLocation.setText(latitude + " " + longitude);

                        }
                    });
                    locationCapturerImpl.startToCaptureLocations();
                }
            }
        }
    }
}
