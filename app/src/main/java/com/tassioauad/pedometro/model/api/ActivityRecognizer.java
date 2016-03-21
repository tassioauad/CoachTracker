package com.tassioauad.pedometro.model.api;

public interface ActivityRecognizer {
    void startToRecognizeActivities();

    void stopToRecognizeActivities();

    void setActivityRecognizerListener(ActivityRecognizerListener activityRecognizerListener);
}
