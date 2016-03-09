package com.tassioauad.pedometro.view.activity;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tassioauad.pedometro.PedometroApplication;
import com.tassioauad.pedometro.R;
import com.tassioauad.pedometro.dagger.HomeViewModule;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;
import com.tassioauad.pedometro.presenter.HomePresenter;
import com.tassioauad.pedometro.view.HomeView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements HomeView, OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Inject
    HomePresenter presenter;
    @Bind(R.id.fab_startstop)
    FloatingActionButton floatingActionButtonStartStop;
    @Bind(R.id.linearlayout_warn)
    LinearLayout linearLayoutWarn;
    @Bind(R.id.textview_warn)
    TextView textViewWarn;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private GoogleMap googleMap;
    private Location currentLocation;
    private ActivityType currentActivityType = ActivityType.UNKNOWN;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        ((PedometroApplication) getApplication()).getObjectGraph().plus(new HomeViewModule(this)).inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        sharedPreferences = getSharedPreferences(getString(R.string.pedometro_preferences), MODE_PRIVATE);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);

        //Verifying ACCESS_FINE_LOCATION permission. If negative, requesting the permission.
        int accessFineLocationPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (accessFineLocationPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        floatingActionButtonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean(getString(R.string.pedometro_preferences_starttracking), false)) {
                    stopTracking();
                } else {
                    startTracking();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean(getString(R.string.pedometro_preferences_starttracking), false)) {
            startTracking();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.measurement:
                startActivity(MeasurementActivity.newInstance(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startTracking() {
        textViewWarn.setText(getString(R.string.activityhome_warntracking));
        linearLayoutWarn.setVisibility(View.VISIBLE);
        sharedPreferences.edit().putBoolean(getString(R.string.pedometro_preferences_starttracking), true).apply();
        presenter.startToCaptureLocation();
        presenter.startToRecognizeActivity();
        floatingActionButtonStartStop.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
    }

    private void stopTracking() {
        sharedPreferences.edit().putBoolean(getString(R.string.pedometro_preferences_starttracking), false).apply();
        presenter.stopToCaptureLocation();
        presenter.stopToRecognizeActivity();
        floatingActionButtonStartStop.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
        googleMap.clear();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), 0f));
        Toast.makeText(this, R.string.activityhome_warntrackingpaused, Toast.LENGTH_LONG).show();
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
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_LONG);
        toast.getView().setBackgroundColor(getResources().getColor(R.color.indigo500Alpha));
        toast.show();
    }

    @Override
    public void show(Location location, ActivityType activityType) {
        linearLayoutWarn.setVisibility(View.GONE);
        this.currentLocation = location;
        this.currentActivityType = activityType;
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(currentActivityType.getName());
        BitmapDescriptor bitmapDescriptor;
        switch (currentActivityType) {
            case IN_VEHICLE:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_invehicle);
                break;
            case ON_BICYCLE:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_bicycle);
                break;
            case ON_FOOT:
            case WALKING:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_walking);
                break;
            case STILL:
            case TILTING:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_still);
                break;
            case UNKNOWN:
            case DEFAULT:
            default:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_unknown);
                break;
            case RUNNING:
                bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_running);
                break;
        }
        markerOptions.icon(bitmapDescriptor);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void warnWasNotPossibleToRecognizeActivity(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
