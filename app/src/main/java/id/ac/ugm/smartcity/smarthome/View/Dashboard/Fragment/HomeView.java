package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.text.ParseException;
import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Alert;
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

    void showCostProgressBar();

    void hideCostProgressBar();

    void showNotifProgressBar();

    void hideNotifProgressBar();

    void onFailure(String appErrorMessage);

    void showCurrentEnergy(Response<CurrentEnergy> response);

    void showAlert(Response<List<Alert>> response) throws ParseException;

    void showEnergyChart(Response<List<Integer>> response);

    void showCostChart(Response<List<Double>> response);

    void showCost(Response<String> response);

}
