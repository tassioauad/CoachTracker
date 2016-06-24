package com.tassioauad.trackercoach.view;

public interface MeasurementDetailView {

    void showInVehicleDetails(long distance, long timeInMilis);

    void showOnBicycleDetails(long distance, long time);

    void showOnFootDetails(long distance, long timeInMilis);

    void showRunningDetails(long distance, long timeInMilis);
}
