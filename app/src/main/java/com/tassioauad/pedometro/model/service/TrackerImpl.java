package com.tassioauad.pedometro.model.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class TrackerImpl implements Tracker{

    private Context context;
    private boolean isTrackingServiceBound;
    private TrackingService.TrackingServiceBinder trackingServiceBinder;
    private TrackerListener trackerListener;
    private static long UPDATE_TIME_INMILIS = 5000;
    private TimerTask timerTask;

    public TrackerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setUpdateTimeInMilis(long updateTimeInMilis) {
        UPDATE_TIME_INMILIS = updateTimeInMilis;
    }

    @Override
    public void setTrackerListener(TrackerListener trackerListener) {
        this.trackerListener = trackerListener;
    }

    @Override
    public void stopTrackingService() {
        if (isTrackingServiceBound) {
            context.stopService(new Intent(context, TrackingService.class));
            context.unbindService(trackingServiceConnection);
            isTrackingServiceBound = false;
        }
        timerTask.cancel();
    }

    @Override
    public void startTrackingService() {
        Intent intent = new Intent(context, TrackingService.class);
        context.startService(intent);
        context.bindService(intent, trackingServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection trackingServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isTrackingServiceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            trackingServiceBinder = (TrackingService.TrackingServiceBinder) service;
            isTrackingServiceBound = true;
            trackerListener.onTracked(trackingServiceBinder.getActivityType(), trackingServiceBinder.getLocation());

            timerTask = new TimerTask() {
                @Override
                public void run() {
                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            trackerListener.onTracked(trackingServiceBinder.getActivityType(), trackingServiceBinder.getLocation());
                        }
                    });
                }
            };
            new Timer().schedule(timerTask, UPDATE_TIME_INMILIS, UPDATE_TIME_INMILIS);
        }
    };
}
