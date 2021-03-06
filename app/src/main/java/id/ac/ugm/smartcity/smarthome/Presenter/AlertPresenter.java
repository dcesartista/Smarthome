package id.ac.ugm.smartcity.smarthome.Presenter;

/**
 * Created by dito on 09/02/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.HashMap;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.AlertView;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import rx.subscriptions.CompositeSubscription;

public class AlertPresenter {
    private final Service service;
    private final AlertView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private Resources resources;

    public AlertPresenter(Service service, AlertView view, Context context) {
        this.service = service;
        this.view = view;
        this.context = context;
        this.subscriptions = new CompositeSubscription();
    }

    public void getAlertList() {
        view.showLoading();
        resources = context.getResources();
        preferences = context.getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        Map<String, String> headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));
        /*Subscription subscription = service.getAlertList(new Service.GetAlertListCallback() {
            @Override
            public void onSuccess(List<AlertOld> alertList) {
                view.hideLoading();
                view.getAlertSuccess(alertList);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        },headers);

        subscriptions.add(subscription);*/
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
