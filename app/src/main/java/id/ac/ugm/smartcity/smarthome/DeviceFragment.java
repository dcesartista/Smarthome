package id.ac.ugm.smartcity.smarthome;


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
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Presenter.DevicePresenter;
import id.ac.ugm.smartcity.smarthome.adapter.DeviceAdapter;

import static id.ac.ugm.smartcity.smarthome.R.layout.device;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment implements DeviceView {
    public static final String DEVICE_ARG = "DEVICE_ARG";

    @BindView(R.id.recycler_device)
    RecyclerView rvDevice;

    private Service service;
    private DeviceAdapter adapter;
    private LinearLayoutManager layoutManager;
    List<Device> deviceItemList;

    public static DeviceFragment newInstance(int page, Service service) {
        Bundle args = new Bundle();
        args.putInt(DEVICE_ARG, page);
        DeviceFragment fragment = new DeviceFragment();
        fragment.service = service;
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
        DevicePresenter presenter = new DevicePresenter(service, this);
        presenter.getDeviceList();

        return rootView;
    }

    private void setupRecycleView(){

    }

    @Override
    public void showLoading() {
        deviceItemList = new ArrayList<>();

        adapter = new DeviceAdapter(deviceItemList, getContext());
        layoutManager = new LinearLayoutManager(getContext());
        rvDevice.setLayoutManager(layoutManager);
        rvDevice.setAdapter(adapter);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getDeviceSuccess(List<Device> deviceList) {
        for (Device device : deviceList){
            deviceItemList.add(device);
            Log.d("DATA DEVICE",device.getName());
        }
        adapter.notifyDataSetChanged();
    }
}
