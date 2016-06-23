package com.tassioauad.trackercoach.presenter;

import com.tassioauad.trackercoach.model.Measurer;
import com.tassioauad.trackercoach.model.entity.ActivityLocation;
import com.tassioauad.trackercoach.model.entity.ActivityType;
import com.tassioauad.trackercoach.view.MeasurementDetailView;

import java.util.List;

public class MeasurementDetailPresenter {

    private MeasurementDetailView view;

    public MeasurementDetailPresenter(MeasurementDetailView view) {
        this.view = view;
    }

    public void init(List<ActivityLocation> activityLocationList) {
        Measurer measurer = new Measurer(activityLocationList);
        view.showInVehicleDetails(measurer.getDistance(ActivityType.IN_VEHICLE), measurer.getTime(ActivityType.IN_VEHICLE));
        view.showOnBicycleDetails(measurer.getDistance(ActivityType.ON_BICYCLE), measurer.getTime(ActivityType.ON_BICYCLE));
        view.showOnFootDetails(measurer.getDistance(ActivityType.ON_FOOT) + measurer.getDistance(ActivityType.WALKING),
                measurer.getTime(ActivityType.ON_FOOT) + measurer.getTime(ActivityType.WALKING));
        view.showRunningDetails(measurer.getDistance(ActivityType.RUNNING), measurer.getTime(ActivityType.RUNNING));
    }
}
