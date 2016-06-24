package com.tassioauad.trackercoach.model.dao;

import com.tassioauad.trackercoach.model.entity.ActivityLocation;

import java.util.Date;
import java.util.List;

public interface ActivityLocationDao {
    boolean insert(ActivityLocation activityLocation);

    List<ActivityLocation> listAll(Date currentDay);

    List<ActivityLocation> listAll(Date startDate, Date finalDate);
}
