package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert.AlertOld;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert.AlertDay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.AlertPresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.AlertAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlertFragment extends Fragment implements AlertView {
    public static final String ALERT_ARG = "ALERT_ARG";

    @BindView(R.id.recycler_alert)
    RecyclerView rvAlert;
    private Service service;
    private DashboardView dashboardView;

    private List<DisplayableItem> displayableItems;
    private LinearLayoutManager layoutManager;
    private AlertAdapter adapter;
    ProgressDialog progressDialog;

    public static AlertFragment newInstance(int page, Service service, DashboardView dashboardView) {
        Bundle args = new Bundle();
        args.putInt(ALERT_ARG, page);
        AlertFragment fragment = new AlertFragment();
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        fragment.setArguments(args);
        return fragment;
    }


    public AlertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_alert, container, false);
        ButterKnife.bind(this,rootView);
        setupRecyclerView();
        progressDialog = new ProgressDialog(getContext());
        displayableItems.add(new AlertDay("Hari Ini"));
        AlertPresenter presenter = new AlertPresenter(service, this, getContext());
        return rootView;
    }

    private void setupRecyclerView() {
        displayableItems = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlert.setLayoutManager(layoutManager);
        adapter = new AlertAdapter(displayableItems, getContext());
        rvAlert.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getAlertSuccess(List<AlertOld> alertList) {
        for (AlertOld alert : alertList){
            displayableItems.add(alert);
            Log.d("DATA ALERT",alert.getWarning());
        }
        adapter.notifyDataSetChanged();
    }
}
