package com.tassioauad.pedometro.view;

import com.tassioauad.pedometro.model.entity.ActivityLocation;

import java.util.List;

public interface MeasurementView {
    void showDetailsOfActivityLocation(List<ActivityLocation> activityLocationListOfToday,
                                       List<ActivityLocation> activityLocationListOfWeek);
}
