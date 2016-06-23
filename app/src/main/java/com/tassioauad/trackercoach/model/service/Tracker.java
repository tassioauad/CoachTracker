package com.tassioauad.trackercoach.model.service;

public interface Tracker {
    void stopTrackingService();

    void startTrackingService();

    void setUpdateTimeInMilis(long updateTimeInMilis);

    void setTrackerListener(TrackerListener trackerListener);

    boolean isTracking();
}
