package com.tassioauad.trackercoach.model.api;

import com.tassioauad.trackercoach.model.entity.ActivityType;

public interface ActivityRecognizerListener {
    void connectionFailed(String errorMessage);

    void onActivityRecognized(ActivityType activityType);
}
