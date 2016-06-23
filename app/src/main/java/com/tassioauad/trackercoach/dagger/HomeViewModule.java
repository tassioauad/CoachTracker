package com.tassioauad.trackercoach.dagger;

import com.tassioauad.trackercoach.model.service.Tracker;
import com.tassioauad.trackercoach.presenter.HomePresenter;
import com.tassioauad.trackercoach.view.HomeView;
import com.tassioauad.trackercoach.view.activity.HomeActivity;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = {AppModule.class, ApiModule.class, DaoModule.class}, injects = HomeActivity.class)
public class HomeViewModule {

    private HomeView view;

    public HomeViewModule(HomeView view) {
        this.view = view;
    }

    @Provides
    public HomePresenter provideHomePresenter(Tracker tracker) {
        return new HomePresenter(view, tracker);
    }
}
