package com.tassioauad.pedometro.dagger;

import com.tassioauad.pedometro.model.dao.ActivityLocationDao;
import com.tassioauad.pedometro.presenter.MeasurementPresenter;
import com.tassioauad.pedometro.view.MeasurementView;
import com.tassioauad.pedometro.view.activity.MeasurementActivity;

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
