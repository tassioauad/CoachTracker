package com.tassioauad.pedometro.presenter;

import com.tassioauad.pedometro.model.api.ActivityRecognizer;
import com.tassioauad.pedometro.model.api.ActivityRecognizerListener;
import com.tassioauad.pedometro.model.api.LocationCapturer;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;
import com.tassioauad.pedometro.model.dao.ActivityLocationDao;
import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;
import com.tassioauad.pedometro.view.HomeView;

import java.util.Date;

public class HomePresenter {

    private HomeView view;
    private LocationCapturer locationCapturer;
    private ActivityRecognizer activityRecognizer;
    private ActivityLocationDao activityLocationDao;
    private ActivityType currentActivityType = ActivityType.DEFAULT;
    private Location currentLocation;

    public HomePresenter(HomeView view, LocationCapturer locationCapturer, ActivityRecognizer activityRecognizer, ActivityLocationDao activityLocationDao) {
        this.view = view;
        this.locationCapturer = locationCapturer;
        this.activityRecognizer = activityRecognizer;
        this.activityLocationDao = activityLocationDao;
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
                currentLocation = location;
                saveActivityLocation();
            }
        });
        locationCapturer.startToCaptureLocations();
    }

    private void saveActivityLocation() {
        if (currentLocation != null && currentActivityType != null) {
            view.show(currentLocation, currentActivityType);
            ActivityLocation activityLocation = new ActivityLocation();
            activityLocation.setLocation(currentLocation);
            activityLocation.setActivityType(currentActivityType);
            activityLocation.setDate(new Date());
            activityLocationDao.insert(activityLocation);
        }
    }

    public void startToRecognizeActivity() {
        activityRecognizer.setActivityRecognizerListener(new ActivityRecognizerListener() {
            @Override
            public void connectionFailed(String errorMessage) {
                view.warnWasNotPossibleToRecognizeActivity(errorMessage);
            }

            @Override
            public void onActivityRecognized(ActivityType activityType) {
                currentActivityType = activityType;
                saveActivityLocation();
            }
        });
        activityRecognizer.startToRecognizeActivities();
    }

    public void stopToCaptureLocation() {
        locationCapturer.stopToCaptureLocations();
    }

    public void stopToRecognizeActivity() {
        activityRecognizer.stopToRecognizeActivities();
    }
}
