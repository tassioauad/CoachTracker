package com.tassioauad.pedometro.view.activity;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tassioauad.pedometro.PedometroApplication;
import com.tassioauad.pedometro.R;
import com.tassioauad.pedometro.dagger.HomeViewModule;
import com.tassioauad.pedometro.model.api.ActivityRecognizerImpl;
import com.tassioauad.pedometro.model.api.ActivityRecognizerListener;
import com.tassioauad.pedometro.model.api.LocationCapturerImpl;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;
import com.tassioauad.pedometro.presenter.HomePresenter;
import com.tassioauad.pedometro.view.HomeView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    @Inject
    HomePresenter presenter;
    @Bind(R.id.textview_location)
    TextView textviewLocation;
    @Bind(R.id.textview_activity)
    TextView textviewActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        ((PedometroApplication) getApplication()).getObjectGraph().plus(new HomeViewModule(this)).inject(this);

        //Verifying ACCESS_FINE_LOCATION permission. If negative, requesting the permission.
        int accessFineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            presenter.startToCaptureLocation();
        }

        presenter.startToRecognizeActivity();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.startToCaptureLocation();
                }
            }
        }
    }


    @Override
    public void warnWasNotPossibleToCaptureLocation(String errorMessage) {
        textviewLocation.setText(errorMessage);

    }

    @Override
    public void showCurrentLocation(Location location) {
        textviewLocation.setText(String.format(getString(R.string.homeactivity_latitudelongitude), location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void warnWasNotPossibleToRecognizeActivity(String errorMessage) {
        textviewActivity.setText(errorMessage);
    }

    @Override
    public void showCurrentActivity(ActivityType activityType) {
        textviewActivity.setText(activityType.getName());
    }
}
