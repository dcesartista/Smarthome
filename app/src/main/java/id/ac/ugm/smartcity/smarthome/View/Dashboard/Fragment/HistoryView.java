package id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import retrofit2.Response;

/**
 * Created by dito on 09/02/17.
 */

public interface HistoryView {
    void showLoading();

    void hideLoading();

    void onFailure(String appErrorMessage);

    void showHistoryData(Response<List<HistoryData>> histories, int range, int type);

}
