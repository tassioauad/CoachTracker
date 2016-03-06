package com.tassioauad.pedometro.model.dao;

import com.tassioauad.pedometro.model.entity.ActivityLocation;

import java.util.Date;
import java.util.List;

public interface ActivityLocationDao {
    boolean insert(ActivityLocation activityLocation);

    List<ActivityLocation> listAllByDay(Date currentDay);
}
