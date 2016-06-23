package com.tassioauad.trackercoach.presenter;

import com.tassioauad.trackercoach.model.dao.ActivityLocationDao;
import com.tassioauad.trackercoach.model.entity.ActivityLocation;
import com.tassioauad.trackercoach.view.MeasurementView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeasurementPresenter {

    private MeasurementView view;
    private ActivityLocationDao activityLocationDao;

    public MeasurementPresenter(MeasurementView view, ActivityLocationDao activityLocationDao) {
        this.view = view;
        this.activityLocationDao = activityLocationDao;
    }

    public void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date firstDayOfWeek = calendar.getTime();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        Date firstDayOfNextWeek = calendar.getTime();
        List<ActivityLocation> activityLocationListOfToday = activityLocationDao.listAll(new Date());
        List<ActivityLocation> activityLocationListOfWeek = activityLocationDao.listAll(firstDayOfWeek, firstDayOfNextWeek);

        view.showDetailsOfActivityLocation(activityLocationListOfToday, activityLocationListOfWeek);
    }

}
