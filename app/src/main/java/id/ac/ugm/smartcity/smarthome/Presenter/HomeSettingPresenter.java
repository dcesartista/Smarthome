package id.ac.ugm.smartcity.smarthome.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HomeView;
import id.ac.ugm.smartcity.smarthome.View.HomeSettingView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class HomeSettingPresenter {
    private final Service service;
    private final HomeSettingView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private Resources resources;
    private Map<String, String> headers;

    public HomeSettingPresenter(Service service, HomeSettingView view, Context context) {
        this.service = service;
        this.view = view;
        this.context = context;
        this.subscriptions = new CompositeSubscription();
        resources = context.getResources();
        preferences = context.getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));
    }

    public void changeHome(String homeId, Map<String, String> params) {
        view.showLoading();
        Subscription subscription = service.changeHome(new Service.ChangeHomeCallback() {
            @Override
            public void onSuccess(Response<Home> response) {
                view.hideLoading();
                view.updateUI(response);


            }

            @Override
            public void onError(NetworkError networkError) {
//                view.hideCostProgressBar();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, params);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
