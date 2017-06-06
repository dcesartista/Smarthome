package id.ac.ugm.smartcity.smarthome.View;

import java.text.ParseException;
import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface AlertDetailView {
    void showLoading();

    void hideLoading();

    void onFailure();

    void showAlert(Response<List<AlertGroup>> response) throws ParseException;

}
