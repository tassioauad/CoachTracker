package com.tassioauad.pedometro.model.service;

import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;

public interface TrackerListener {
    void onTracked(ActivityType activityType, Location location);
}
