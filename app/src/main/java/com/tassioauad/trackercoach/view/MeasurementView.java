package com.tassioauad.trackercoach.view;

import com.tassioauad.trackercoach.model.entity.ActivityLocation;

import java.util.List;

public interface MeasurementView {
    void showDetailsOfActivityLocation(List<ActivityLocation> activityLocationListOfToday,
                                       List<ActivityLocation> activityLocationListOfWeek);
}
