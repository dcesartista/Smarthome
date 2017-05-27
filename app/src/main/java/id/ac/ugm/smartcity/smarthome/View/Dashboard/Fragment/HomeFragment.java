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
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.DevicePresenter;
import id.ac.ugm.smartcity.smarthome.Presenter.HomePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.Utils.NumberFormatter;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {

    public static final String HOME_ARG = "HOME_ARG";

    @BindView(R.id.spr_device)
    Spinner spinnerDevice;
    @BindView(R.id.tv_temp)
    TextView tvTemp;
    @BindView(R.id.tv_humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_co2)
    TextView tvCO2;
    @BindView(R.id.tv_motion)
    TextView tvMotion;
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
    @BindView(R.id.iv_temp)
    View ivTemperature;
    @BindView(R.id.iv_humidity)
    View ivHumidity;
    @BindView(R.id.pb_energy)
    View pbEnergy;
    @BindView(R.id.pb_temp)
    View pbTemp;
    @BindView(R.id.pb_humidity)
    View pbHumidity;
    @BindView(R.id.pb_co2)
    View pbCO2;
    @BindView(R.id.pb_motion)
    View pbMotion;
    @BindView(R.id.pb_daya)
    View pbDaya;
    @BindView(R.id.pb_arus)
    View pbArus;
    @BindView(R.id.pb_tegangan)
    View pbTegangan;
    @BindView(R.id.ic_gear)
    TextView icGear;
    @BindView(R.id.ic_down)
    TextView icDown;

    //TODO : HOME ID DIBIKIN GAK STATIS, BIKIN HOME SELECTION ACTIVITY
    private String homeId = "1";
    private List<Device> devices;
    private String[] devicesName;
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
        presenter = new HomePresenter(service, this, getContext());
        Log.e("HMMMMppp222","sss1"+getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).getString(App.ACCESS_TOKEN,""));
        if (getUserVisibleHint()){
            presenter.getDeviceList(homeId);
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
                presenter.getDeviceList(homeId);
                presenter.getCurrentEnergy(homeId);
            }
        }
    }

    @OnItemSelected(R.id.spr_device)
    void onItemSelected(int position) {
        if(devices.size() > 0) {
            selectedDevice = devices.get(position);
            Log.e("HMMMM000","LALALALCALLEDLALAL");
            presenter.getCurrentDeviceData(homeId, String.valueOf(selectedDevice.getId()));
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
                pbDaya.setVisibility(View.VISIBLE);
                pbArus.setVisibility(View.VISIBLE);
                pbTegangan.setVisibility(View.VISIBLE);
                break;
            case App.DEVICE_DATA:
                ivTemperature.setVisibility(View.GONE);
                tvTemp.setVisibility(View.GONE);
                ivHumidity.setVisibility(View.GONE);
                tvHumidity.setVisibility(View.GONE);
                tvCO2.setVisibility(View.GONE);
                tvMotion.setVisibility(View.GONE);
                pbTemp.setVisibility(View.VISIBLE);
                pbHumidity.setVisibility(View.VISIBLE);
                pbCO2.setVisibility(View.VISIBLE);
                pbMotion.setVisibility(View.VISIBLE);
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
                pbDaya.setVisibility(View.GONE);
                pbArus.setVisibility(View.GONE);
                pbTegangan.setVisibility(View.GONE);
                break;
            case App.DEVICE_DATA:
                ivTemperature.setVisibility(View.VISIBLE);
                tvTemp.setVisibility(View.VISIBLE);
                ivHumidity.setVisibility(View.VISIBLE);
                tvHumidity.setVisibility(View.VISIBLE);
                tvCO2.setVisibility(View.VISIBLE);
                tvMotion.setVisibility(View.VISIBLE);
                pbTemp.setVisibility(View.GONE);
                pbHumidity.setVisibility(View.GONE);
                pbCO2.setVisibility(View.GONE);
                pbMotion.setVisibility(View.GONE);
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
        tvTemp.setText(NumberFormatter.formatWithDots(data.getTemperature()));
        tvHumidity.setText(NumberFormatter.formatWithDots(data.getHumidity()));
        tvCO2.setText(NumberFormatter.formatWithDots(data.getCo2()));
        tvMotion.setText(NumberFormatter.formatWithDots(data.getMotion()));
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
    public void getDeviceSuccess(Response<List<Device>> response) {
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
        devices = response.body();
        int i = 0;
        devicesName = new String[devices.size()];
        for (Device device : devices){
            devicesName[i] = device.getName();
            i++;
        }

        ArrayAdapter deviceAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                devicesName);
        spinnerDevice.setAdapter(deviceAdapter);
    }

    @Override
    public void updateEnergyLimit(String limit) {
        //TODO: post update limit
    }
}
