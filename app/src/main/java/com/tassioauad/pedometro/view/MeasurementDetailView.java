package com.tassioauad.pedometro.view;

public interface MeasurementDetailView {

    void showTravelledDistanceInVehicle(long distance);

    void showTravelledDistanceOnBicycle(long distance);

    void showTravelledDistanceOnFoot(long distance);

    void showTravelledDistanceRunning(long distance);

}
