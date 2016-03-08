package com.tassioauad.pedometro.presenter;

import com.tassioauad.pedometro.model.Measurer;
import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.view.MeasurementDetailView;

import java.util.List;

public class MeasurementDetailPresenter {

    private MeasurementDetailView view;

    public MeasurementDetailPresenter(MeasurementDetailView view) {
        this.view = view;
    }

    public void init(List<ActivityLocation> activityLocationList) {
        Measurer measurer = new Measurer(activityLocationList);
        view.showTravelledDistanceInVehicle(measurer.getDistance(ActivityType.IN_VEHICLE));
        view.showTravelledDistanceOnBicycle(measurer.getDistance(ActivityType.ON_BICYCLE));
        view.showTravelledDistanceOnFoot(measurer.getDistance(ActivityType.ON_FOOT) + measurer.getDistance(ActivityType.WALKING));
        view.showTravelledDistanceRunning(measurer.getDistance(ActivityType.RUNNING));
    }
}
