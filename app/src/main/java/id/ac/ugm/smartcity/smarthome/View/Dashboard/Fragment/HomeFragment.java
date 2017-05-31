package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.DevicePresenter;
import id.ac.ugm.smartcity.smarthome.Presenter.HomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import id.ac.ugm.smartcity.smarthome.View.CustomSpinner;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
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
    @BindView(R.id.iv_energy)
    View ivEnergy;
    @BindView(R.id.pb_energy)
    View pbEnergy;
    @BindView(R.id.pb_arus)
    View pbArus;
    @BindView(R.id.pb_tegangan)
    View pbTegangan;
    @BindView(R.id.ic_gear)
    TextView icGear;
    @BindView(R.id.ic_down)
    TextView icDown;
    @BindView(R.id.sp_home)
    Spinner spHome;

    private String homeId;
    private List<Home> homes;
    private String[] homeNames;
    private Device selectedDevice;
    private View rootView;
    private Service service;
    private DashboardView dashboardView;
    private HomePresenter presenter;
    private final BroadcastReceiver updateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            presenter.getCurrentEnergy(homeId);
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
        SharedPreferences preferences = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        presenter = new HomePresenter(service, this, getContext());
        Log.e("HMMMMppp222","sss1"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        if (getUserVisibleHint()){
            presenter.getHomes();
            presenter.getCurrentEnergy(homeId);
        }

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(icGear, iconFont);
        FontManager.markAsIconContainer(icDown, iconFont);

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
                presenter.getHomes();
                presenter.getCurrentEnergy(homeId);
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
                tvDaya.setVisibility(View.GONE);
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
                tvDaya.setVisibility(View.VISIBLE);
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
    public void showCurrentDeviceData(Response<CurrentDeviceData> response) {
        Log.e("HMMMM", String.valueOf(response.raw().request().url()));
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        //TODO: cari solusi biar bisa dapet token baru setiap request
        /*if(response.code() == 200) {
            editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();
        }*/

        CurrentDeviceData data = response.body();
        Log.e("HMMMM", String.valueOf(response.code()));
    }

    @Override
    public void showCurrentEnergy(Response<CurrentEnergy> response) {
        Log.e("LALALA",response.body().toString());
        CurrentEnergy currentEnergy = response.body();
        tvEnergy.setText(NumberFormatter.formatWithDots(currentEnergy.getValue()));
        tvDaya.setText(NumberFormatter.formatWithDots(currentEnergy.getPower()));
        tvArus.setText(NumberFormatter.formatWithDots(currentEnergy.getCurrent()));
        tvTegangan.setText(NumberFormatter.formatWithDots(currentEnergy.getVoltage()));
    }

    @Override
    public void getHomeSuccess(Response<List<Home>> response) {
        Log.e("HMMMMzzz", String.valueOf(response.code()));
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        /*if(response.code() == 200) {
            editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();
        }*/
        Log.e("HMMMMppp222","sss"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        homes = response.body();
        int i = 0;
        homeNames = new String[homes.size()];
        for (Home home: homes){
            homeNames[i] = home.getName();
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                homeNames);
        spHome.setAdapter(adapter);

    }

    @Override
    public void updateEnergyLimit(String limit) {
        //TODO: post update limit
    }
}
