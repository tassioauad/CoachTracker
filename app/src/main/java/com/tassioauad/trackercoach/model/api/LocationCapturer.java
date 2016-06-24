package com.tassioauad.trackercoach.model.api;

public interface LocationCapturer {

    void stopToCaptureLocations();

    void startToCaptureLocations();

    void setLocationCapturerListener(LocationCapturerListener listener);

}
