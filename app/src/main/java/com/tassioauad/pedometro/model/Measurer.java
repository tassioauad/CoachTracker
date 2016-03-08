package com.tassioauad.pedometro.model;

import android.location.Location;

import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.model.entity.ActivityType;

import java.util.HashMap;
import java.util.List;

public class Measurer {

    private HashMap<ActivityType, Long> distancePerActivityType = new HashMap<>();

    public Measurer(List<ActivityLocation> activityLocationList) {
        distancePerActivityType.put(ActivityType.IN_VEHICLE, 0l);
        distancePerActivityType.put(ActivityType.ON_BICYCLE, 0l);
        distancePerActivityType.put(ActivityType.ON_FOOT, 0l);
        distancePerActivityType.put(ActivityType.STILL, 0l);
        distancePerActivityType.put(ActivityType.UNKNOWN, 0l);
        distancePerActivityType.put(ActivityType.TILTING, 0l);
        distancePerActivityType.put(ActivityType.DEFAULT, 0l);
        distancePerActivityType.put(ActivityType.WALKING, 0l);
        distancePerActivityType.put(ActivityType.RUNNING, 0l);

        if(activityLocationList.size() > 0) {
            ActivityType currentActivityType = activityLocationList.get(0).getActivityType();
            Location currentLocation = activityLocationList.get(0).getLocation().getLocation();
            long distance = 0;
            for(int i = 1; i < activityLocationList.size(); i++) {
                ActivityLocation activityLocation = activityLocationList.get(i);
                if(currentActivityType == activityLocation.getActivityType()) {
                    distance += currentLocation.distanceTo(activityLocation.getLocation().getLocation());
                } else {
                    distancePerActivityType.put(currentActivityType, distancePerActivityType.get(currentActivityType) + distance);
                    distance = 0;
                    currentLocation = activityLocation.getLocation().getLocation();
                    currentActivityType = activityLocation.getActivityType();
                }
            }
        }
    }

    public long getDistance(ActivityType activityType) {
        return distancePerActivityType.get(activityType);
    }

}
