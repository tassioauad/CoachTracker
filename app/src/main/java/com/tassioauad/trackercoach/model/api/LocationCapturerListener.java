package com.tassioauad.trackercoach.model.api;

import com.tassioauad.trackercoach.model.entity.Location;

public interface LocationCapturerListener {
    void connectionFailed(String errorMessage);

    void onLocationCaptured(Location location);
}
