package id.ac.ugm.smartcity.smarthome.View;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.User;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface LoginView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void loginSuccess(Response<User> response);

}
