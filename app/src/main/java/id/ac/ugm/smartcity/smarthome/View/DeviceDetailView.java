package id.ac.ugm.smartcity.smarthome.View;

import id.ac.ugm.smartcity.smarthome.Model.CurrentSensor;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface DeviceDetailView {
    void showLoading();

    void hideLoading();

    void showProgressBar();

    void hideProgressBar();

    void onFailure(String appErrorMessage);

    void showDeviceData(Response<Device> response);

    void showCurrentSensorData(Response<CurrentSensor> response);

    void showRelayStatus(Response<Relay> response);

    void changeRelayStatus();
}
