package id.ac.ugm.smartcity.smarthome.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.HistoryView;
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

    public void getTemperatureDaily(String startDate) {
        view.showLoading();

        Subscription subscription = service.getTemperatureDaily(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> temperatures) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(temperatures);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getTemperatureMonthly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getTemperatureMonthly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> temperatures) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(temperatures);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getTemperatureYearly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getTemperatureYearly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> temperatures) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(temperatures);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getCarbondioxideDaily(String startDate) {
        view.showLoading();

        Subscription subscription = service.getCarbondioxideDaily(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> carbondioxides) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(carbondioxides);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getCarbondioxideMonthly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getCarbondioxideMonthly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> carbondioxides) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(carbondioxides);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getCarbondioxideYearly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getCarbondioxideYearly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> carbondioxides) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(carbondioxides);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getHumiditiesDaily(String startDate) {
        view.showLoading();

        Subscription subscription = service.getHumiditiesDaily(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> humidities) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(humidities);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getHumiditiesMonthly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getHumiditiesMonthly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> humidities) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(humidities);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void getHumiditiesYearly(String startDate) {
        view.showLoading();

        Subscription subscription = service.getHumiditiesYearly(new Service.GetHistoryCallback() {
            @Override
            public void onSuccess(List<HistoryData> humidities) {
                Log.e("HAHA", "SUKSES");
                view.hideLoading();
                view.showHistoryData(humidities);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, startDate, headers);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}