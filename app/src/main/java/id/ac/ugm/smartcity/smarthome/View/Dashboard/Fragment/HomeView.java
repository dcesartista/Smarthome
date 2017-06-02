package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.text.ParseException;
import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface HomeView {
    void showLoading();

    void hideLoading();

    void showProgressBar(int type);

    void hideProgressBar(int type);

    void onFailure(String appErrorMessage);

    void showCurrentDeviceData(Response<CurrentDeviceData> response);

    void showCurrentEnergy(Response<CurrentEnergy> response);

    void showAlert(Response<List<AlertGroup>> response) throws ParseException;

    void getHomeSuccess(Response<List<Home>> response);

}
