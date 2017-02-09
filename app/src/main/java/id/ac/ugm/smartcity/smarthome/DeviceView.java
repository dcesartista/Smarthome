package id.ac.ugm.smartcity.smarthome;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;

/**
 * Created by dito on 09/02/17.
 */

public interface DeviceView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getDeviceSuccess(List<Device> deviceList);

}
