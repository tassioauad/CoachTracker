package com.tassioauad.trackercoach.view;

import com.tassioauad.trackercoach.model.entity.ActivityType;
import com.tassioauad.trackercoach.model.entity.Location;

public interface HomeView {
    void warnWasNotPossibleToCaptureLocation(String errorMessage);

    void show(Location location, ActivityType activityType);

    void warnWasNotPossibleToRecognizeActivity(String errorMessage);

    void warnTracking();

    void warnTrackingHasBeenStopped();

    void showStopButton();

    void showPlayButton();
}
