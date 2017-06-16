package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.Presenter.GetDevicePresenter;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashboardView;
import id.ac.ugm.smartcity.smarthome.adapter.DeviceAdapter;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment implements GetDeviceView {
    public static final String DEVICE_ARG = "DEVICE_ARG";

    @BindView(R.id.recycler_device)
    RecyclerView rvDevice;
    @BindView(R.id.pb_device)
    View pbDevice;

    //TODO : HOME ID DIBIKIN GAK STATIS, BIKIN HOME SELECTION ACTIVITY
    private String homeId;
    private Service service;
    private DashboardView dashboardView;
    private DeviceAdapter adapter;
    private LinearLayoutManager layoutManager;
    private SharedPreferences preferences;
    private GetDevicePresenter presenter;
    private ProgressDialog progressDialog;
    List<Device> deviceItemList;

    public static DeviceFragment newInstance(int page, Service service, DashboardView dashboardView) {
        Bundle args = new Bundle();
        args.putInt(DEVICE_ARG, page);
        DeviceFragment fragment = new DeviceFragment();
        fragment.service = service;
        fragment.dashboardView = dashboardView;
        fragment.setArguments(args);
        return fragment;
    }


    public DeviceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, rootView);

        setupRecycleView();
        presenter = new GetDevicePresenter(service, this, getContext());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        preferences = getContext().getSharedPreferences(App.USER_PREFERENCE,MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");

        if(getUserVisibleHint()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.blueDark));
            }
            dashboardView.changeColor(getResources().getColor(R.color.blueDark));
            dashboardView.changeHomeSelectorBackground(getResources().getColor(R.color.white));
            dashboardView.setToolbarText("SmartHome");
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            dashboardView.setSettingVisibility(View.GONE);
            dashboardView.setHomeSelectorVisibility(View.VISIBLE);
            dashboardView.setToolbarText("Device");
            presenter.getDeviceList(homeId);
        }

        Typeface iconFont = FontManager.getTypeface(getContext(), FontManager.FONTAWESOME);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.blueDark));
            }

            if (null != presenter){
                dashboardView.changeColor(getResources().getColor(R.color.blueDark));
                Log.e("PRESENTER","NOT NULL!");
                dashboardView.changeHomeSelectorBackground(getResources().getColor(R.color.white));
                dashboardView.setToolbarText("Device");
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
                presenter.getDeviceList(homeId);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onStop();
    }

    private void setupRecycleView(){
        deviceItemList = new ArrayList<>();

        adapter = new DeviceAdapter(deviceItemList, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        rvDevice.setLayoutManager(layoutManager);
        rvDevice.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        pbDevice.setVisibility(View.VISIBLE);
        rvDevice.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        pbDevice.setVisibility(View.GONE);
        rvDevice.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading2() {
        if(getUserVisibleHint() && !progressDialog.isShowing()){
            Log.e("DEVICE","CALLED");
            progressDialog.show();
        }
    }

    @Override
    public void hideLoading2() {
        if(progressDialog.isShowing()){
            Log.e("DEVICE","DISMISSED");
            progressDialog.dismiss();
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getDeviceSuccess(Response<List<Device>> response) {
        SharedPreferences.Editor editor = getContext().getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE).edit();
        /*if(response.code() == 200) {
            editor.putString(App.ACCESS_TOKEN, response.headers().get("Access-Token"));
            editor.putString(App.CLIENT, response.headers().get("Client"));
            editor.putString(App.EXPIRY, response.headers().get("Expiry"));
            editor.putString(App.UID, response.headers().get("Uid"));
            editor.commit();
        }*/
        deviceItemList.clear();
        List<Device> deviceList = response.body();
        for (Device device : deviceList){
            deviceItemList.add(device);
            Log.d("DATA DEVICE",device.getName());
            presenter.getRelayData(String.valueOf(device.getId()),deviceItemList.indexOf(device));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDeviceDelete(Response<Boolean> response) {
        if(response.body()){
            presenter.getDeviceList(homeId);
        } else {
            Toast.makeText(getContext(),getResources().getString(R.string.failed_delete),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getRelaySuccess(Response<Relay> response, int index) {
        Log.e("HUUU1","SUKSES!");
        deviceItemList.get(index).setRelay(response.body());
        adapter.notifyDataSetChanged();
    }
}
