package com.tassioauad.trackercoach.model.service;

import com.tassioauad.trackercoach.model.entity.ActivityType;
import com.tassioauad.trackercoach.model.entity.Location;

public interface TrackerListener {
    void onTracked(ActivityType activityType, Location location);
}
