package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.AlertDay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.HomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.DateFormatter;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.AlertAdapter;
import id.ac.ugm.smartcity.smarthome.adapter.CurrentAlertAdapter;
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
    @BindView(R.id.alert_container)
    LinearLayout alertContainer;
    @BindView(R.id.pb_notif)
    View pbNotif;

    private List<Alert> alerts;
    private LinearLayoutManager layoutManager;
    private CurrentAlertAdapter adapter;
    private String homeId;
    private View rootView;
    private Service service;
    private DashboardView dashboardView;
    private HomePresenter presenter;
    private ProgressDialog progressDialog;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,rootView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

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

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);

        getContext().registerReceiver(updateReceiver, new IntentFilter(App.UPDATE_ENERGY));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(updateReceiver);
        presenter.onStop();
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
        if(getUserVisibleHint() && !progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
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
    public void showNotifProgressBar() {
        pbNotif.setVisibility(View.VISIBLE);
        alertContainer.setVisibility(View.GONE);
    }

    @Override
    public void hideNotifProgressBar() {
        pbNotif.setVisibility(View.GONE);
        alertContainer.setVisibility(View.VISIBLE);
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
    public void showAlert(Response<List<Alert>> response) throws ParseException {
        alerts = response.body();
        alertContainer.removeAllViews();
        for(Alert alert : alerts){
            View layout = LayoutInflater.from(getContext()).inflate(R.layout.alert_log, alertContainer, false);
            ImageView ivAlert = (ImageView) layout.findViewById(R.id.icon_alert);
            TextView tvTitle = (TextView)layout.findViewById(R.id.tv_alert_title);
            TextView tvDesc = (TextView)layout.findViewById(R.id.tv_alert_desc);
            TextView tvTime = (TextView)layout.findViewById(R.id.tv_time);

            if(alert.getAlertType() != null ){
                switch (alert.getAlertType()){
                    case "Energy":
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                    case "Temperature":
                        ivAlert.setImageResource(R.drawable.icon_temp);
                        break;
                    case "Humidity":
                        ivAlert.setImageResource(R.drawable.icon_humidity);
                        break;
                    case "Cost":
                        break;
                    case "Light":
                        break;
                    case "Carbondioxide":
                        break;
                    default:
                        ivAlert.setImageResource(R.drawable.ic_energy_yellow);
                        break;
                }
            }
            tvTitle.setText(alert.getAlertType());
            tvDesc.setText(alert.getStatus());
            tvTime.setText(DateFormatter.convertDateToStringTime(alert.getCreatedAt()));

            alertContainer.addView(layout);
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
