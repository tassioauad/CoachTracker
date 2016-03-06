package com.tassioauad.pedometro.model.entity;

import java.util.Date;

public class ActivityLocation {

    private Integer id;
    private Date date;
    private ActivityType activityType;
    private Location location;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
