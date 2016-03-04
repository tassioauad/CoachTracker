package com.tassioauad.pedometro.presenter;

import com.tassioauad.pedometro.model.api.ActivityRecognizer;
import com.tassioauad.pedometro.model.api.ActivityRecognizerListener;
import com.tassioauad.pedometro.model.api.LocationCapturer;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;
import com.tassioauad.pedometro.view.HomeView;

public class HomePresenter {

    private HomeView view;
    private LocationCapturer locationCapturer;
    private ActivityRecognizer activityRecognizer;

    public HomePresenter(HomeView view, LocationCapturer locationCapturer, ActivityRecognizer activityRecognizer) {
        this.view = view;
        this.locationCapturer = locationCapturer;
        this.activityRecognizer = activityRecognizer;
    }

    public void finish() {
        activityRecognizer.stopToRecognizeActivities();
        locationCapturer.stopToCaptureLocations();
    }

    public void startToCaptureLocation() {
        locationCapturer.setLocationCapturerListener(new LocationCapturerListener() {

            @Override
            public void connectionFailed(String errorMessage) {
                view.warnWasNotPossibleToCaptureLocation(errorMessage);
            }

            @Override
            public void onLocationCaptured(Location location) {
                view.showCurrentLocation(location);
            }

        });
        locationCapturer.startToCaptureLocations();
    }

    public void startToRecognizeActivity() {
        activityRecognizer.setActivityRecognizerListener(new ActivityRecognizerListener() {
            @Override
            public void connectionFailed(String errorMessage) {
                view.warnWasNotPossibleToRecognizeActivity(errorMessage);
            }

            @Override
            public void onActivityRecognized(ActivityType activityType) {
                view.showCurrentActivity(activityType);
            }
        });
        activityRecognizer.startToRecognizeActivities();
    }

}
