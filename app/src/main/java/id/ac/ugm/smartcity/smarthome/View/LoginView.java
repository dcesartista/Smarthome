package id.ac.ugm.smartcity.smarthome.View;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface LoginView {
    void showLoading();

    void hideLoading();

    void loginFailed();

    void loginSuccess(Response<LoginUser> response);

    void getHomeSuccess(Response<List<Home>> response);

}
