package com.tassioauad.pedometro.model.api;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.tassioauad.pedometro.model.entity.ActivityType;

public class ActivityRecognizerImpl implements ActivityRecognizer, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final long ACTIVITY_RECOGNITION_INTERVAL = 10000;
    private GoogleApiClient googleApiClient;
    private static ActivityRecognizerListener activityRecognizerListener;
    private Context context;

    public ActivityRecognizerImpl(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(ActivityRecognition.API)
                .build();
    }

    @Override
    public void startToRecognizeActivities() {
        //Trying to connect with Google API. If it works onConnected() will be called, else onConnectionFailed() will be called.
        googleApiClient.connect();
    }
    /**
     * Called when there was an error connecting the client to the service.
     *
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Google API connection has been failed! Stop it and warn!
        activityRecognizerListener.connectionFailed(connectionResult.getErrorMessage());
        stopToRecognizeActivities();
    }

    @Override
    public void stopToRecognizeActivities() {
        ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
                googleApiClient,
                PendingIntent.getService(context, 0,
                        new Intent(context, ActivityRecognitionIntentService.class), PendingIntent.FLAG_UPDATE_CURRENT)
        ); //Stopping to get location updates
        googleApiClient.disconnect(); //Closing connection with Google APIs
    }

    /**
     * After calling connect(), this method will be invoked asynchronously when the connect request has successfully completed.
     * After this callback, the application can make requests on other methods provided by the client and expect that no user intervention
     * is required to call methods that use account and scopes provided to the client constructor.
     *
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        //Google API connection has been done successfully, now we can work with it
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                googleApiClient,
                ACTIVITY_RECOGNITION_INTERVAL,
                PendingIntent.getService(context, 0,
                        new Intent(context, ActivityRecognitionIntentService.class), PendingIntent.FLAG_UPDATE_CURRENT)
        );
    }

    /**
     * Called when the client is temporarily in a disconnected state.
     * This can happen if there is a problem with the remote service (e.g. a crash or resource problem causes it to be killed by the system).
     * When called, all requests have been canceled and no outstanding listeners will be executed.
     * GoogleApiClient will automatically attempt to restore the connection.
     * Applications should disable UI components that require the service, and wait for a call to onConnected(Bundle) to re-enable them.
     */
    @Override
    public void onConnectionSuspended(int i) {
        //Wait for the GoogleApiClient restores the connection.
    }

    public void setActivityRecognizerListener(ActivityRecognizerListener activityRecognizerListener) {
        this.activityRecognizerListener = activityRecognizerListener;
    }

    public static class ActivityRecognitionIntentService extends IntentService {

        public ActivityRecognitionIntentService() {
            super("ACTIVITYRECOGNITION_INTENTSERVICE_NAME");
        }

        /**
         * Called when a new activity detection update is available.
         */
        @Override
        protected void onHandleIntent(final Intent intent) {
            if(ActivityRecognitionResult.hasResult(intent)) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        activityRecognizerListener.onActivityRecognized(
                                ActivityType.values()[ActivityRecognitionResult.extractResult(intent).getMostProbableActivity().getType()]
                        );
                    }
                });
            }
        }
    }
}
