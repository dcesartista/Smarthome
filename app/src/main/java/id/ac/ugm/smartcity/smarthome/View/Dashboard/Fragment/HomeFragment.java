package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Alert;
import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertOld;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertDay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.Utils;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.AlertAdapter;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    public static final String HOME_ARG = "HOME_ARG";

    @BindView(R.id.tv_energy)
    TextView tvEnergy;
    @BindView(R.id.tv_daya)
    TextView tvDaya;
    @BindView(R.id.tv_arus)
    TextView tvArus;
    @BindView(R.id.tv_tegangan)
    TextView tvTegangan;
    @BindView(R.id.tv_biaya)
    TextView tvBiaya;
    @BindView(R.id.iv_energy)
    View ivEnergy;
    @BindView(R.id.pb_energy)
    View pbEnergy;
    @BindView(R.id.pb_arus)
    View pbArus;
    @BindView(R.id.pb_tegangan)
    View pbTegangan;
    @BindView(R.id.pb_biaya)
    View pbBiaya;
    @BindView(R.id.recycler_alert)
    RecyclerView rvAlert;

    private List<DisplayableItem> displayableItems;
    private LinearLayoutManager layoutManager;
    private AlertAdapter adapter;
    private String homeId;
    private View rootView;
    private Service service;
    private DashboardView dashboardView;
    private HomePresenter presenter;
    private final BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getCurrentEnergy(homeId);
            presenter.getCurrentCost(homeId);
        }
    };

    public static HomeFragment newInstance(int page, Service service, DashboardView dashboardView) {
        Bundle args = new Bundle();
        args.putInt(HOME_ARG, page);
        HomeFragment fragment = new HomeFragment();
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        fragment.setArguments(args);
        return fragment;
    }


    public HomeFragment() {
        // Required empty public constructor
    }

    private void setupRecyclerView() {
        displayableItems = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlert.setLayoutManager(layoutManager);
        adapter = new AlertAdapter(displayableItems, getContext());
        rvAlert.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,rootView);
        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        presenter = new HomePresenter(service, this, getContext());
        Log.e("HMMMMppp222","sss1"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        if (getUserVisibleHint()){
            dashboardView.setToolbarText("SmartHome");
            dashboardView.setSettingVisibility(View.VISIBLE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            presenter.getAlerts(homeId);
            presenter.getCurrentEnergy(homeId);
            presenter.getCurrentCost(homeId);
        }

        setupRecyclerView();

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);

        getContext().registerReceiver(updateReceiver, new IntentFilter(App.UPDATE_ENERGY));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(updateReceiver);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (null != presenter){
                dashboardView.setToolbarText("SmartHome");
                dashboardView.setSettingVisibility(View.VISIBLE);
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
                presenter.getAlerts(homeId);
                presenter.getCurrentEnergy(homeId);
                presenter.getCurrentCost(homeId);
            }
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showProgressBar(int type) {
        switch (type){
            case App.ENERGY:
                ivEnergy.setVisibility(View.GONE);
                tvEnergy.setVisibility(View.GONE);
//                tvDaya.setVisibility(View.GONE);
                tvArus.setVisibility(View.GONE);
                tvTegangan.setVisibility(View.GONE);
                pbEnergy.setVisibility(View.VISIBLE);
                pbArus.setVisibility(View.VISIBLE);
                pbTegangan.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideProgressBar(int type) {
        switch (type){
            case App.ENERGY:
                ivEnergy.setVisibility(View.VISIBLE);
                tvEnergy.setVisibility(View.VISIBLE);
//                tvDaya.setVisibility(View.VISIBLE);
                tvArus.setVisibility(View.VISIBLE);
                tvTegangan.setVisibility(View.VISIBLE);
                pbEnergy.setVisibility(View.GONE);
                pbArus.setVisibility(View.GONE);
                pbTegangan.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void showCurrentEnergy(Response<CurrentEnergy> response) {
        Log.e("LALALA",response.body().toString());
        CurrentEnergy currentEnergy = response.body();
        tvEnergy.setText(NumberFormatter.formatWithDots(currentEnergy.getValue())+" kWh");
//        tvDaya.setText(NumberFormatter.formatWithDots(currentEnergy.getPower()));
        tvArus.setText(NumberFormatter.formatWithDots(currentEnergy.getCurrent())+" A");
        tvTegangan.setText(NumberFormatter.formatWithDots(currentEnergy.getVoltage())+" V");
    }

    @Override
    public void showAlert(Response<List<AlertGroup>> response) throws ParseException {
        Log.e("ASDASDASDASDAS", "HAHAHEHEIHUHU");
        List<AlertGroup> alertGroups = response.body();
        displayableItems.clear();

        for (AlertGroup alertGroup : alertGroups){
            Log.e("ASDASDASDASDAS", alertGroup.getDate());
            Calendar c = Calendar.getInstance();
            c.setTimeZone(new SimpleTimeZone(7, "GMT"));
            c.set(Calendar.HOUR_OF_DAY, 0-7);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0+1);
            c.set(Calendar.MILLISECOND, 0);
            Date date = c.getTime();
            Log.e("DATE1",date.toString());
            Date alertDate = DateFormatter.convertServerDateFormat(alertGroup.getDate());
            Log.e("DATE2",alertDate.toString());
            if(alertDate.equals(date)){
                displayableItems.add(new AlertDay("hari ini"));
            } else {
                displayableItems.add(new AlertDay(DateFormatter.convertDateToStringDate(alertGroup.getDate())));
            }
            List<Alert> alerts = alertGroup.getValue();
            if(null != alerts && alerts.size()>0){
                for (Alert alert : alerts){
                    Log.e("ASDASDASDASDAS", alert.getStatus());
                    displayableItems.add(alert);
                }
            }

        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showCostProgressBar() {
        pbBiaya.setVisibility(View.VISIBLE);
        tvBiaya.setVisibility(View.GONE);
    }

    @Override
    public void hideCostProgressBar() {
        pbBiaya.setVisibility(View.GONE);
        tvBiaya.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCost(Response<String> response) {
        String r = response.body();
        tvBiaya.setText("Rp " +NumberFormatter.formatWithDots(Integer.parseInt(r.split("\\.")[0])));
    }
}
