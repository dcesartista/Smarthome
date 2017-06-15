package id.ac.ugm.smartcity.smarthome.View.Dashboard;

import android.view.View;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Home;
import retrofit2.Response;

/**
 * Created by dito on 26/05/17.
 */

public interface DashboardView {
    void setToolbarText(String text);

    void showHomes(Response<List<Home>> response);

    void setSettingVisibility(int visibility);

    void setHomeSelectorVisibility(int visibility);

    void changeColor(int color);
}
