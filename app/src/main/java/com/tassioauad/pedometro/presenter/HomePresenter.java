package com.tassioauad.pedometro.presenter;

import com.tassioauad.pedometro.model.entity.ActivityType;
import com.tassioauad.pedometro.model.entity.Location;
import com.tassioauad.pedometro.model.service.Tracker;
import com.tassioauad.pedometro.model.service.TrackerListener;
import com.tassioauad.pedometro.view.HomeView;

public class HomePresenter {
    private HomeView view;
    private Tracker tracker;

    public HomePresenter(HomeView view, Tracker tracker) {
        this.view = view;
        this.tracker = tracker;
    }

    public void init() {
        tracker.setTrackerListener(new TrackerListener() {
            @Override
            public void onTracked(ActivityType activityType, Location location) {
                if (activityType != null && location != null) {
                    view.show(location, activityType);
                } else {
                    view.warnTracking();
                }
            }
        });
    }


    public void startTrackingService() {
        view.warnTracking();
        tracker.startTrackingService();
    }

    public void stopTrackingService() {
        tracker.stopTrackingService();
        view.warnTrackingHasBeenStopped();
    }
}
