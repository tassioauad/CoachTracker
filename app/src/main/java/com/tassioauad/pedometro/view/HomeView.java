package com.tassioauad.pedometro.view;

import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;

public interface HomeView {
    void warnWasNotPossibleToCaptureLocation(String errorMessage);

    void show(Location location, ActivityType activityType);

    void warnWasNotPossibleToRecognizeActivity(String errorMessage);

    void warnTracking();

    void warnTrackingHasBeenStopped();
}
