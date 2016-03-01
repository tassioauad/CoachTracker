package com.tassioauad.pedometro.model.api;

public interface LocationCapturerListener {
    void connectionFailed(String errorMessage);

    void onLocationCaptured(double latitude, double longitude);
}
