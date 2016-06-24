package com.tassioauad.trackercoach.dagger;

import android.content.Context;

import com.tassioauad.trackercoach.model.api.ActivityRecognizer;
import com.tassioauad.trackercoach.model.api.impl.ActivityRecognizerImpl;
import com.tassioauad.trackercoach.model.api.LocationCapturer;
import com.tassioauad.trackercoach.model.api.impl.LocationCapturerImpl;
import com.tassioauad.trackercoach.model.service.Tracker;
import com.tassioauad.trackercoach.model.service.TrackerImpl;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = AppModule.class)
public class ApiModule {

    @Provides
    public ActivityRecognizer provideActivityRecognizer(Context context) {
        return new ActivityRecognizerImpl(context);
    }

    @Provides
    public LocationCapturer provideLocationCapturer(Context context) {
        return new LocationCapturerImpl(context);
    }

    @Provides
    public Tracker provideTracker(Context context) {
        return new TrackerImpl(context);
    }

}
