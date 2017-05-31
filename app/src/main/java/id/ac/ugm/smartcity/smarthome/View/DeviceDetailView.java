package id.ac.ugm.smartcity.smarthome.View;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface DeviceDetailView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getDeviceSuccess(Response<Device> response);

}
