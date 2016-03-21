package com.tassioauad.pedometro.dagger;

import com.tassioauad.pedometro.presenter.MeasurementDetailPresenter;
import com.tassioauad.pedometro.view.MeasurementDetailView;
import com.tassioauad.pedometro.view.fragment.MeasurementDetailFragment;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class}, injects = MeasurementDetailFragment.class)
public class MeasurementDetailViewModule {

    private MeasurementDetailView view;

    public MeasurementDetailViewModule(MeasurementDetailView view) {
        this.view = view;
    }

    @Provides
    public MeasurementDetailPresenter provideMeasurementDetailPresenter() {
        return new MeasurementDetailPresenter(view);
    }
}
