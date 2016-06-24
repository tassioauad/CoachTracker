package com.tassioauad.trackercoach.model;

import com.tassioauad.trackercoach.model.entity.ActivityLocation;
import com.tassioauad.trackercoach.model.entity.ActivityType;

import java.util.HashMap;
import java.util.List;

public class Measurer {

    private HashMap<ActivityType, Long> distancePerActivityType = new HashMap<>();
    private HashMap<ActivityType, Long> timePerActivityType = new HashMap<>();


    public Measurer(List<ActivityLocation> activityLocationList) {
        initiate();

        if(activityLocationList.size() > 0) {
            ActivityLocation currentActivityLocation = activityLocationList.get(0);
            long distance = 0;
            long time = 0;
            for(int i = 1; i < activityLocationList.size(); i++) {
                ActivityLocation activityLocation = activityLocationList.get(i);
                if(currentActivityLocation.getActivityType() == activityLocation.getActivityType()) {
                    distance += currentActivityLocation.getLocation().getLocation().distanceTo(activityLocation.getLocation().getLocation());
                    time += activityLocation.getDate().getTime() - currentActivityLocation.getDate().getTime();
                    currentActivityLocation = activityLocation;
                } else {
                    distancePerActivityType.put(currentActivityLocation.getActivityType(), distancePerActivityType.get(currentActivityLocation.getActivityType()) + distance);
                    timePerActivityType.put(currentActivityLocation.getActivityType(), timePerActivityType.get(currentActivityLocation.getActivityType()) + time);
                    distance = 0;
                    time = 0;
                    currentActivityLocation = activityLocation;
                }
            }
            if(distance != 0) {
                distancePerActivityType.put(currentActivityLocation.getActivityType(), distancePerActivityType.get(currentActivityLocation.getActivityType()) + distance);
            }
            if(time != 0) {
                timePerActivityType.put(currentActivityLocation.getActivityType(), timePerActivityType.get(currentActivityLocation.getActivityType()) + time);
            }
        }
    }

    private void initiate() {
        distancePerActivityType.put(ActivityType.IN_VEHICLE, 0l);
        distancePerActivityType.put(ActivityType.ON_BICYCLE, 0l);
        distancePerActivityType.put(ActivityType.ON_FOOT, 0l);
        distancePerActivityType.put(ActivityType.STILL, 0l);
        distancePerActivityType.put(ActivityType.UNKNOWN, 0l);
        distancePerActivityType.put(ActivityType.TILTING, 0l);
        distancePerActivityType.put(ActivityType.DEFAULT, 0l);
        distancePerActivityType.put(ActivityType.WALKING, 0l);
        distancePerActivityType.put(ActivityType.RUNNING, 0l);

        timePerActivityType.put(ActivityType.IN_VEHICLE, 0l);
        timePerActivityType.put(ActivityType.ON_BICYCLE, 0l);
        timePerActivityType.put(ActivityType.ON_FOOT, 0l);
        timePerActivityType.put(ActivityType.STILL, 0l);
        timePerActivityType.put(ActivityType.UNKNOWN, 0l);
        timePerActivityType.put(ActivityType.TILTING, 0l);
        timePerActivityType.put(ActivityType.DEFAULT, 0l);
        timePerActivityType.put(ActivityType.WALKING, 0l);
        timePerActivityType.put(ActivityType.RUNNING, 0l);
    }

    public long getDistance(ActivityType activityType) {
        return distancePerActivityType.get(activityType);
    }

    public long getTime(ActivityType activityType) {
        return timePerActivityType.get(activityType);
    }

}
