package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface HomeView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void showCurrentDeviceData(Response<CurrentDeviceData> response);

    void getDeviceSuccess(Response<List<Device>> response);

}
