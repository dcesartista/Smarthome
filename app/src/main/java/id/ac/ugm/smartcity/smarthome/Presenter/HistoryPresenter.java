package id.ac.ugm.smartcity.smarthome.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HistoryView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class HistoryPresenter {
    private final Service service;
    private final HistoryView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private Resources resources;
    private Map<String, String> headers;

    public HistoryPresenter(Service service, HistoryView view, Context context) {
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

    public void getCurrentHistory(String startDate, final int range, String homeId,
                                  final int type) {
        view.showLoading();

        Subscription subscription = service.getCurrentHistory(new Service.GetCurrentHistoryCallback() {
            @Override
            public void onSuccess(Response<List<HistoryData>> response) {
                view.hideLoading();
                view.showHistoryData(response,range,type);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers, range, homeId);

        subscriptions.add(subscription);
    }

    public void getVoltageHistory(String startDate, final int range, String homeId,
                                  final int type) {
        view.showLoading();

        Subscription subscription = service.getVoltageHistory(new Service.GetVoltageHistoryCallback() {
            @Override
            public void onSuccess(Response<List<HistoryData>> response) {
                view.hideLoading();
                view.showHistoryData(response,range,type);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers, range, homeId);

        subscriptions.add(subscription);
    }

    public void getEnergyHistory(String startDate, final int range, String homeId) {
        view.showLoading();
        Log.e("HISTORY","CALLED!!");

        Subscription subscription = service.getEnergyHistory(new Service.GetEnergyHistoryCallback() {
            @Override
            public void onSuccess(Response<List<String>> response) {
                view.hideLoading();
                view.showHistoryEnergy(response, range);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers, range, homeId);

        subscriptions.add(subscription);
    }

    public void getCostHistory(String startDate, final int range, String homeId) {
        view.showLoading();
        Log.e("HISTORY","CALLED!!");

        Subscription subscription = service.getCostHistory(new Service.GetCostHistoryCallback() {
            @Override
            public void onSuccess(Response<List<String>> response) {
                view.hideLoading();
                view.showHistoryEnergy(response, range);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers, range, homeId);

        subscriptions.add(subscription);
    }

    public void getDeviceList(String homeId) {
        view.showLoading();
        resources = context.getResources();
        preferences = context.getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        Map<String, String> headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));

        Subscription subscription = service.getDeviceList(new Service.GetDeviceListCallback() {
            @Override
            public void onSuccess(Response<List<Device>> deviceList) {
                view.hideLoading();
                view.getDeviceSuccess(deviceList);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
