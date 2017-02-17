package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;

/**
 * Created by dito on 09/02/17.
 */

public interface AlertView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getAlertSuccess(List<Alert> alertList);

}
