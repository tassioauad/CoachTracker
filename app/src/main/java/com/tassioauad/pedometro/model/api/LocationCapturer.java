package com.tassioauad.pedometro.model.api;

public interface LocationCapturer {

    void stopToCaptureLocations();

    void startToCaptureLocations();

    void setLocationCapturerListener(LocationCapturerListener listener);

}
