package com.tassioauad.pedometro.model.dao;

import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.model.entity.ActivityType;

import java.util.Date;
import java.util.List;

public interface ActivityLocationDao {
    boolean insert(ActivityLocation activityLocation);

    List<ActivityLocation> listAll(Date currentDay);

    List<ActivityLocation> listAll(Date startDate, Date finalDate);
}
