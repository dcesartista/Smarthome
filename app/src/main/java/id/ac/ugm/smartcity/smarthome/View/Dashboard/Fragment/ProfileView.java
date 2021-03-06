package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface ProfileView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getHomeSuccess(Response<List<Home>> response);

    void getDeviceSuccess(Response<List<Device>> response, int index);

    void checkAdminSuccess(Response<Boolean> response);

}
