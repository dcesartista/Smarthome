package id.ac.ugm.smartcity.smarthome.View;

import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import retrofit2.Response;

/**
 * Created by dito on 24/02/17.
 */

public interface RegisterView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void registerSuccess(Response<RegisterUser> response);
}
