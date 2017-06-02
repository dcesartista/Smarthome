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
import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HomeView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private Resources resources;
    private Map<String, String> headers;

    public HomePresenter(Service service, HomeView view, Context context) {
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

    public void getHomes(){
        Subscription subscription = service.getHomes(new Service.GetHomesCallback() {
            @Override
            public void onSuccess(Response<List<Home>> response) {
                view.getHomeSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {

            }
        }, headers);

        subscriptions.add(subscription);
    }

    public void getCurrentEnergy(String homeId) {
        view.showProgressBar(App.ENERGY);
        Subscription subscription = service.getCurrenEnergy(new Service.GetCurrentEnergyCallback() {
            @Override
            public void onSuccess(Response<CurrentEnergy> response) {
                view.hideProgressBar(App.ENERGY);
                view.showCurrentEnergy(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideProgressBar(App.ENERGY);
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId);

        subscriptions.add(subscription);
    }

    public void getDeviceList(String homeId) {
        view.showLoading();
        Subscription subscription = service.getDeviceList(new Service.GetDeviceListCallback() {
            @Override
            public void onSuccess(Response<List<Device>> deviceList) {
                view.hideLoading();
//                view.getDeviceSuccess(deviceList);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId);

        subscriptions.add(subscription);
    }

    public void getAlerts(String homeId) {
        view.showLoading();
        Subscription subscription = service.getAlert(new Service.GetAlertCallback() {
            @Override
            public void onSuccess(Response<List<AlertGroup>> response) {
                view.hideLoading();
                try {
                    view.showAlert(response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId);

        subscriptions.add(subscription);
    }

    public void getCurrentCost(String homeId) {
        view.showCostProgressBar();
        Subscription subscription = service.getCurrentCost(new Service.GetCurrentCostCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                view.hideCostProgressBar();
                view.showCost(response);


            }

            @Override
            public void onError(NetworkError networkError) {
//                view.hideCostProgressBar();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
