package id.ac.ugm.smartcity.smarthome;

import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;

/**
 * Created by dito on 09/02/17.
 */

public interface DashBoardView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void getAlertSuccess(Alert alert);

}
