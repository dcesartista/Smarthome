package id.ac.ugm.smartcity.smarthome.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.DeviceView;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.GetDeviceView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class DevicePresenter {
    private final Service service;
    private final DeviceView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private String homeId;
    private SharedPreferences preferences;
    private Resources resources;
    private Map<String, String> headers;

    public DevicePresenter(Service service, DeviceView view, Context context) {
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

    public void addDevice(String homeId, String name, String productID, File image){
        view.showLoading();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", image.getName(), requestFile);
        RequestBody fullName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), name);
        RequestBody id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), productID);

        Subscription subscription = service.addNewDevice(new Service.AddNewDeviceCallback() {
            @Override
            public void onSuccess(Response<Device> response) {
                view.hideLoading();
                view.addDeviceSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, fullName, id, body);

        subscriptions.add(subscription);
    }

    public void addDevice(String homeId, Map<String, String> params){
        view.showLoading();

        Subscription subscription = service.addNewDevice(new Service.AddNewDeviceCallback() {
            @Override
            public void onSuccess(Response<Device> response) {
                view.hideLoading();
                view.addDeviceSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, params);

        subscriptions.add(subscription);
    }

    public void editDevice(String homeId, String deviceId, String name, String productID, File image){
        view.showLoading();
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", image.getName(), requestFile);
        RequestBody fullName =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), name);
        RequestBody id =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), productID);

        Subscription subscription = service.editDevice(new Service.EditDeviceCallback() {
            @Override
            public void onSuccess(Response<Device> response) {
                view.hideLoading();
                view.addDeviceSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId, fullName, id, body);

        subscriptions.add(subscription);
    }

    public void editDevice(String homeId, String deviceId, Map<String, String> params){
        view.showLoading();

        Subscription subscription = service.editDevice(new Service.EditDeviceCallback() {
            @Override
            public void onSuccess(Response<Device> response) {
                view.hideLoading();
                view.addDeviceSuccess(response);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                view.onFailure(networkError.getThrowable().getMessage());
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, headers, homeId, deviceId, params);

        subscriptions.add(subscription);
    }



    public void onStop() {
        subscriptions.unsubscribe();
    }
}
