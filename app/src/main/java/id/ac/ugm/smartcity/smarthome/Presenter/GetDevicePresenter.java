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
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.GetDeviceView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class GetDevicePresenter {
    private final Service service;
    private final GetDeviceView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private String homeId;
    private Resources resources;
    private Map<String, String> headers;

    public GetDevicePresenter(Service service, GetDeviceView view, Context context) {
        this.service = service;
        this.view = view;
        this.context = context;
        this.subscriptions = new CompositeSubscription();
        resources = context.getResources();
        preferences = context.getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        homeId = preferences.getString(App.ACTIVE_HOME,"");
        headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));
    }

    public void getDeviceList(String homeId) {
        view.showLoading();
        Log.e("DEVICE","CALLED!!");

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

    public void deleteDevice(String homeId, String deviceId, String password) {

        Log.e("DEVICE","DELETE!!");
        Log.e("HOME ID",homeId);
        Log.e("DEVICE ID",deviceId);
        Log.e("PASSWORD",password);

        Map<String, String> params = new HashMap<>();
        params.put("password",password);

        Subscription subscription = service.deleteDevice(new Service.DeleteDeviceCallback() {
            @Override
            public void onSuccess(Response<Boolean> response) {
                view.hideLoading2();
                Log.e("DEVICE","DELETE SUSES!!");
                view.onDeviceDelete(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading2();
                Log.e("DEVICE","DELETE FAILED!!");
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId, params);

        if (null == subscription){
            Log.e("SUB NULL", "SUBSCRIPTION NULL");
        }

        subscriptions.add(subscription);
    }

    public void getRelayData(String deviceId, final int index){
//        view.showLoading();
        Subscription subscription = service.getRelayData(new Service.GetRelayDataCallBack() {
            @Override
            public void onSuccess(Response<Relay> response) {
//                view.hideLoading();
                view.getRelaySuccess(response, index);
            }

            @Override
            public void onError(NetworkError networkError) {
//                view.hideLoading();
                Log.e("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
