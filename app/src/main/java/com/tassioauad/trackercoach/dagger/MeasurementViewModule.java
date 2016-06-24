package com.tassioauad.trackercoach.dagger;

import com.tassioauad.trackercoach.model.dao.ActivityLocationDao;
import com.tassioauad.trackercoach.presenter.MeasurementPresenter;
import com.tassioauad.trackercoach.view.MeasurementView;
import com.tassioauad.trackercoach.view.activity.MeasurementActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, DaoModule.class}, injects = MeasurementActivity.class)
public class MeasurementViewModule {

    MeasurementView view;

    public MeasurementViewModule(MeasurementView view) {
        this.view = view;
    }

    @Provides
    public MeasurementPresenter provideMeasurementPresenter(ActivityLocationDao activityLocationDao) {
        return new MeasurementPresenter(view, activityLocationDao);
    }

}
