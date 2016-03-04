package com.tassioauad.pedometro.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tassioauad.pedometro.R;
import com.tassioauad.pedometro.model.api.ActivityRecognizerImpl;
import com.tassioauad.pedometro.model.api.ActivityRecognizerListener;
import com.tassioauad.pedometro.model.api.LocationCapturerImpl;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;

public class TestActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationCapturerImpl locationCapturerImpl;
    private ActivityRecognizerImpl activityRecognizer;
    private TextView textviewLocation;
    private TextView textviewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textviewLocation = (TextView) findViewById(R.id.textview_location);
        textviewActivity = (TextView) findViewById(R.id.textview_activity);

        //Verifying ACCESS_FINE_LOCATION permission. If negative, requesting the permission.
        int accessFineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            startToCaptureLocation();
        }

        startToRecognizeActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityRecognizer.stopToRecognizeActivities();
        locationCapturerImpl.stopToCaptureLocations();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startToCaptureLocation();
                }
            }
        }
    }

    private void startToCaptureLocation() {
        locationCapturerImpl = new LocationCapturerImpl(this);
        locationCapturerImpl.setLocationCapturerListener(new LocationCapturerListener() {

            @Override
            public void connectionFailed(String errorMessage) {
                textviewLocation.setText(errorMessage);
            }

            @Override
            public void onLocationCaptured(Location location) {
                textviewLocation.setText(location.getLatitude() + " " + location.getLongitude());
            }

        });
        locationCapturerImpl.startToCaptureLocations();
    }

    private void startToRecognizeActivity() {
        activityRecognizer = new ActivityRecognizerImpl(this);
        activityRecognizer.setActivityRecognizerListener(new ActivityRecognizerListener() {
            @Override
            public void connectionFailed(String errorMessage) {
                textviewActivity.setText(errorMessage);
            }

            @Override
            public void onActivityRecognized(ActivityType activityType) {
                textviewActivity.setText(activityType.getName());
            }
        });
        activityRecognizer.startToRecognizeActivities();
    }
}
