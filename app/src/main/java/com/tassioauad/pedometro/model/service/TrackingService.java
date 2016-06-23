package com.tassioauad.pedometro.model.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tassioauad.pedometro.model.api.ActivityRecognizer;
import com.tassioauad.pedometro.model.api.ActivityRecognizerListener;
import com.tassioauad.pedometro.model.api.LocationCapturer;
import com.tassioauad.pedometro.model.api.LocationCapturerListener;
import com.tassioauad.pedometro.model.api.impl.ActivityRecognizerImpl;
import com.tassioauad.pedometro.model.api.impl.LocationCapturerImpl;
import com.tassioauad.pedometro.model.dao.ActivityLocationDao;
import com.tassioauad.pedometro.model.dao.SqliteConnection;
import com.tassioauad.pedometro.model.dao.impl.ActivityLocationDaoImpl;
import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;

import java.util.Date;

public class TrackingService extends Service {

    private static LocationCapturer locationCapturer;
    private static ActivityRecognizer activityRecognizer;
    private static ActivityLocationDao activityLocationDao;
    private static Location currentLocation;
    private static ActivityType currentActivityType;
    private static TrackingServiceBinder binder = new TrackingServiceBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        activityLocationDao = new ActivityLocationDaoImpl(new SqliteConnection(this).getWritableDatabase());
        locationCapturer = new LocationCapturerImpl(this);
        activityRecognizer = new ActivityRecognizerImpl(this);
        locationCapturer.setLocationCapturerListener(new LocationCapturerListener() {

            @Override
            public void connectionFailed(String errorMessage) {}

            @Override
            public void onLocationCaptured(Location location) {
                currentLocation = location;
                saveActivityLocation();
            }
        });
        activityRecognizer.setActivityRecognizerListener(new ActivityRecognizerListener() {
            @Override
            public void connectionFailed(String errorMessage) {}

            @Override
            public void onActivityRecognized(ActivityType activityType) {
                currentActivityType = activityType;
                saveActivityLocation();
            }
        });

        activityRecognizer.startToRecognizeActivities();
        locationCapturer.startToCaptureLocations();


        return Service.START_STICKY;
    }

    private void saveActivityLocation() {
        if (currentLocation != null && currentActivityType != null) {
            ActivityLocation activityLocation = new ActivityLocation();
            activityLocation.setLocation(currentLocation);
            activityLocation.setActivityType(currentActivityType);
            activityLocation.setDate(new Date());
            activityLocationDao.insert(activityLocation);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        locationCapturer.stopToCaptureLocations();
        activityRecognizer.stopToRecognizeActivities();
        super.onDestroy();
    }

    static class TrackingServiceBinder extends Binder {

        Location getLocation() {
            return currentLocation;
        }

        ActivityType getActivityType() {
            return currentActivityType;
        }
    }
}
