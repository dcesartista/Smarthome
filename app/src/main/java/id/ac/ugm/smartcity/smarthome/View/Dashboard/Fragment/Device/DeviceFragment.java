package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;


import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.FontManager;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
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
    String homeId = "1";
    private Service service;
    private DashboardView dashboardView;
    private DeviceAdapter adapter;
    private LinearLayoutManager layoutManager;
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
        if(getUserVisibleHint()){
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
            if (null != presenter){
                dashboardView.setSettingVisibility(View.GONE);
                dashboardView.setHomeSelectorVisibility(View.VISIBLE);
                dashboardView.setToolbarText("Device");
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

    @OnClick(R.id.btn_add_device)
    void addDevice(){
        Intent intent = new Intent(getContext(), AddDeviceActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        /*if(getUserVisibleHint() && !progressDialog.isShowing()){
            Log.e("DEVICE","CALLED");
            progressDialog.show();
        }*/
        pbDevice.setVisibility(View.VISIBLE);
        rvDevice.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        /*if(progressDialog.isShowing()){
            Log.e("DEVICE","DISMISSED");
            progressDialog.dismiss();
        }*/
        pbDevice.setVisibility(View.GONE);
        rvDevice.setVisibility(View.VISIBLE);
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
        }
        adapter.notifyDataSetChanged();
    }
}
