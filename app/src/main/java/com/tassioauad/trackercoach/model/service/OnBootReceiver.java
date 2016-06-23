package com.tassioauad.trackercoach.model.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Tracker tracker = new TrackerImpl(context);
        if(tracker.isTracking()) {
            tracker.startTrackingService();
        }
    }
}
