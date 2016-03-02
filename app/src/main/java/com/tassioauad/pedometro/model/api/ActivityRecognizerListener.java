package com.tassioauad.pedometro.model.api;

import com.tassioauad.pedometro.model.entity.ActivityType;

public interface ActivityRecognizerListener {
    void connectionFailed(String errorMessage);

    void onActivityRecognized(ActivityType activityType);
}
