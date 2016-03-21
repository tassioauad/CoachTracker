package com.tassioauad.pedometro.model.api;

import com.tassioauad.pedometro.model.entity.Location;

public interface LocationCapturerListener {
    void connectionFailed(String errorMessage);

    void onLocationCaptured(Location location);
}
