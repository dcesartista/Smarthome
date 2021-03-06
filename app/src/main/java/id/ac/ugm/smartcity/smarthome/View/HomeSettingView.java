package id.ac.ugm.smartcity.smarthome.View;

import id.ac.ugm.smartcity.smarthome.Model.CurrentSensor;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface HomeSettingView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void updateUI(Response<Home> response);

}
