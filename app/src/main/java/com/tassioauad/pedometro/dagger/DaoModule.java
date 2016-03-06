package com.tassioauad.pedometro.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.tassioauad.pedometro.model.dao.ActivityLocationDao;
import com.tassioauad.pedometro.model.dao.SqliteConnection;
import com.tassioauad.pedometro.model.dao.impl.ActivityLocationDaoImpl;

import dagger.Module;
import dagger.Provides;

@Module(library = true, includes = AppModule.class)
public class DaoModule {

    @Provides
    public SQLiteDatabase provideSqLiteDatabase(Context context) {
        return new SqliteConnection(context).getWritableDatabase();
    }

    @Provides
    public ActivityLocationDao provideActivityLocationDao(Context context) {
        return new ActivityLocationDaoImpl(provideSqLiteDatabase(context));
    }

}
