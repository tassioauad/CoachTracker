package com.tassioauad.pedometro.view;

import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;

public interface HomeView {
    void warnWasNotPossibleToCaptureLocation(String errorMessage);

    void showCurrentLocation(Location location);

    void warnWasNotPossibleToRecognizeActivity(String errorMessage);

    void showCurrentActivity(ActivityType activityType);
}
