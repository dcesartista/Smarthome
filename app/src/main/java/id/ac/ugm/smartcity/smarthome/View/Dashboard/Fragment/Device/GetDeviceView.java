package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface GetDeviceView {
    void showLoading();

    void hideLoading();

    void showLoading2();

    void hideLoading2();

    void onFailure(String appErrorMessage);

    void getDeviceSuccess(Response<List<Device>> deviceList);

    void onDeviceDelete(Response<Boolean> response);

    void getRelaySuccess(Response<Relay> response, int index);

}
