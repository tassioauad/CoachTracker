package com.tassioauad.pedometro.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tassioauad.pedometro.PedometroApplication;
import com.tassioauad.pedometro.R;
import com.tassioauad.pedometro.dagger.MeasurementDetailViewModule;
import com.tassioauad.pedometro.model.entity.ActivityLocation;
import com.tassioauad.pedometro.presenter.MeasurementDetailPresenter;
import com.tassioauad.pedometro.view.MeasurementDetailView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MeasurementDetailFragment extends Fragment implements MeasurementDetailView{

    private static final String BUNDLE_KEY_ACTIVITYLOCATION = "bundle_key_activitylocation";

    @Inject
    MeasurementDetailPresenter presenter;
    @Bind(R.id.textview_onfoot)
    TextView textViewOnFoot;
    @Bind(R.id.textview_running)
    TextView textViewRunning;
    @Bind(R.id.textview_bicycle)
    TextView textViewBicycle;
    @Bind(R.id.textview_vehicle)
    TextView textViewVehicle;

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
    public void showTravelledDistanceInVehicle(long distance) {
        textViewVehicle.setText(String.format(getString(R.string.measurementdetailfragment_measure), distance/1000f));
    }

    @Override
    public void showTravelledDistanceOnBicycle(long distance) {
        textViewBicycle.setText(String.format(getString(R.string.measurementdetailfragment_measure), distance/1000f));
    }

    @Override
    public void showTravelledDistanceOnFoot(long distance) {
        textViewOnFoot.setText(String.format(getString(R.string.measurementdetailfragment_measure), distance/1000f));
    }

    @Override
    public void showTravelledDistanceRunning(long distance) {
        textViewRunning.setText(String.format(getString(R.string.measurementdetailfragment_measure), distance/1000f));
    }
}
