package id.ac.ugm.smartcity.smarthome.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.DeviceView;
import id.ac.ugm.smartcity.smarthome.View.RelayView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 18/05/17.
 */

public class RelayPresenter {
    private final Service service;
    private final RelayView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private String homeId;
    private SharedPreferences preferences;
    private Resources resources;

    public RelayPresenter(Service service, RelayView view, Context context) {
        this.service = service;
        this.view = view;
        preferences = context.getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        resources = context.getResources();
        this.context = context;
        this.subscriptions = new CompositeSubscription();
    }

    public void getRelayData(String deviceId){
        view.showLoading();
        Map<String, String> headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));

        Subscription subscription = service.getRelayData(new Service.GetRelayDataCallBack() {
            @Override
            public void onSuccess(Response<Relay> response) {
                view.hideLoading();
                view.showRelayStatus(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.e("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId);

        subscriptions.add(subscription);
    }

    public void changeRelayData(String deviceId, String relayID, Map<String,String> params){
        Map<String, String> headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));

        Subscription subscription = service.changeRelayData(new Service.ChangeRelayDataCallBack() {
            @Override
            public void onSuccess(Response<String> response) {
                view.hideLoading();
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.e("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId, relayID, params);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
