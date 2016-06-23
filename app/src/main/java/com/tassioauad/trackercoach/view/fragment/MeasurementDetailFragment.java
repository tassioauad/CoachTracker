package com.tassioauad.trackercoach.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tassioauad.trackercoach.PedometroApplication;
import com.tassioauad.trackercoach.R;
import com.tassioauad.trackercoach.dagger.MeasurementDetailViewModule;
import com.tassioauad.trackercoach.model.entity.ActivityLocation;
import com.tassioauad.trackercoach.presenter.MeasurementDetailPresenter;
import com.tassioauad.trackercoach.view.MeasurementDetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeasurementDetailFragment extends Fragment implements MeasurementDetailView {

    private static final String BUNDLE_KEY_ACTIVITYLOCATION = "bundle_key_activitylocation";

    @Inject
    MeasurementDetailPresenter presenter;
    @Bind(R.id.textview_onfoot_distance)
    TextView textViewOnFootDistance;
    @Bind(R.id.textview_onfoot_time)
    TextView textViewOnFootTime;
    @Bind(R.id.textview_onfoot_averagespeed)
    TextView textViewOnFootAverageSpeed;
    @Bind(R.id.textview_running_distance)
    TextView textViewRunningDistance;
    @Bind(R.id.textview_running_time)
    TextView textViewRunningTime;
    @Bind(R.id.textview_running_averagespeed)
    TextView textViewRunningAverageSpeed;
    @Bind(R.id.textview_bicycle_distance)
    TextView textViewBicycleDistance;
    @Bind(R.id.textview_bicycle_time)
    TextView textViewBicycleTime;
    @Bind(R.id.textview_bicycle_averagespeed)
    TextView textViewBicycleAverageSpeed;
    @Bind(R.id.textview_invehicle_distance)
    TextView textViewVehicleDistance;
    @Bind(R.id.textview_invehicle_time)
    TextView textViewVehicleTime;
    @Bind(R.id.textview_invehicle_averagespeed)
    TextView textViewVehicleAverageSpeed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measurementdetail, container, false);
        ((PedometroApplication) getActivity().getApplication()).getObjectGraph()
                .plus(new MeasurementDetailViewModule(this)).inject(this);
        ButterKnife.bind(this, view);

        List<ActivityLocation> activityLocationList = getArguments().getParcelableArrayList(BUNDLE_KEY_ACTIVITYLOCATION);
        presenter.init(activityLocationList);

        return view;
    }

    public static MeasurementDetailFragment newInstance(List<ActivityLocation> activityLocationList) {
        MeasurementDetailFragment measurementDetailFragment = new MeasurementDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_KEY_ACTIVITYLOCATION, new ArrayList<>(activityLocationList));
        measurementDetailFragment.setArguments(bundle);

        return measurementDetailFragment;
    }

    @Override
    public void showInVehicleDetails(long distance, long timeInMilis) {
        float distanceInKm = distance / 1000f;
        float timeInHour = distanceInKm > 0 ? timeInMilis / 1000f / 60f / 60f : 0;
        float averageSpeed = distanceInKm > 0 && timeInHour > 0 ? distanceInKm / timeInHour : 0;
        textViewVehicleDistance.setText(String.format(getString(R.string.measurementdetailfragment_measuredistance), distanceInKm));
        textViewVehicleTime.setText(String.format(getString(R.string.measurementdetailfragment_measuretime), timeInHour));
        textViewVehicleAverageSpeed.setText(String.format(getString(R.string.measurementdetailfragment_measureaveragespeed), averageSpeed));
    }

    @Override
    public void showOnBicycleDetails(long distance, long timeInMilis) {
        float distanceInKm = distance / 1000f;
        float timeInHour = distanceInKm > 0 ? timeInMilis / 1000f / 60f / 60f : 0;
        float averageSpeed = distanceInKm > 0 && timeInHour > 0 ? distanceInKm / timeInHour : 0;
        textViewBicycleDistance.setText(String.format(getString(R.string.measurementdetailfragment_measuredistance), distanceInKm));
        textViewBicycleTime.setText(String.format(getString(R.string.measurementdetailfragment_measuretime), timeInHour));
        textViewBicycleAverageSpeed.setText(String.format(getString(R.string.measurementdetailfragment_measureaveragespeed), averageSpeed));
    }

    @Override
    public void showOnFootDetails(long distance, long timeInMilis) {
        float distanceInKm = distance / 1000f;
        float timeInHour = distanceInKm > 0 ? timeInMilis / 1000f / 60f / 60f : 0;
        float averageSpeed = distanceInKm > 0 && timeInHour > 0 ? distanceInKm / timeInHour : 0;
        textViewOnFootDistance.setText(String.format(getString(R.string.measurementdetailfragment_measuredistance), distanceInKm));
        textViewOnFootTime.setText(String.format(getString(R.string.measurementdetailfragment_measuretime), timeInHour));
        textViewOnFootAverageSpeed.setText(String.format(getString(R.string.measurementdetailfragment_measureaveragespeed), averageSpeed));
    }

    @Override
    public void showRunningDetails(long distance, long timeInMilis) {
        float distanceInKm = distance / 1000f;
        float timeInHour = distanceInKm > 0 ? timeInMilis / 1000f / 60f / 60f : 0;
        float averageSpeed = distanceInKm > 0 && timeInHour > 0 ? distanceInKm / timeInHour : 0;
        textViewRunningDistance.setText(String.format(getString(R.string.measurementdetailfragment_measuredistance), distanceInKm));
        textViewRunningTime.setText(String.format(getString(R.string.measurementdetailfragment_measuretime), timeInHour));
        textViewRunningAverageSpeed.setText(String.format(getString(R.string.measurementdetailfragment_measureaveragespeed), averageSpeed));
    }
}
