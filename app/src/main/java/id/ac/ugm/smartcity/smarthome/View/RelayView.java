package id.ac.ugm.smartcity.smarthome.View;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface RelayView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void showRelayStatus(Response<Relay> response);

    void changeRelayStatus();

}
