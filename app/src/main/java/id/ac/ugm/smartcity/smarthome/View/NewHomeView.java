package id.ac.ugm.smartcity.smarthome.View;

import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import retrofit2.Response;

/**
 * Created by dito on 27/05/17.
 */

public interface NewHomeView {
    void showLoading();

    void hideLoading();

    void saveHomeFailed(String appErrorMessage);

    void saveHomeSuccess(Response<Home> response);
}
