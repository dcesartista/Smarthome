package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface GetDeviceView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getHomeSuccess(Response<List<Home>> response);

    void getDeviceSuccess(Response<List<Device>> deviceList);

}
