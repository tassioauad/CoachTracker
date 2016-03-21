package com.tassioauad.pedometro;

import android.app.Application;

import com.tassioauad.pedometro.dagger.ApiModule;
import com.tassioauad.pedometro.dagger.AppModule;
import com.tassioauad.pedometro.dagger.DaoModule;

import dagger.ObjectGraph;

public class PedometroApplication extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(
                new Object[]{
                        new AppModule(PedometroApplication.this),
                        new ApiModule(),
                        new DaoModule()
                }
        );
    }

    public ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
