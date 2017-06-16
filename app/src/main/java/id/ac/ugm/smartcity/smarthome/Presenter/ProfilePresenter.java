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
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.ProfileView;
import id.ac.ugm.smartcity.smarthome.View.LoginView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 18/02/17.
 */

public class ProfilePresenter {
    private final Service service;
    private final ProfileView view;
    private CompositeSubscription subscriptions;
    private Context context;
    private SharedPreferences preferences;
    private Resources resources;
    Map<String, String> headers;

    public ProfilePresenter(Service service, ProfileView view, Context context) {
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

    public void updateFcmToken(final String userId, final Map<String, String> params){
        Subscription subscription = service.updateUser(new Service.UpdateUserCallback() {
            @Override
            public void onSuccess(Response<User> response) {

            }

            @Override
            public void onError(NetworkError networkError) {
                updateFcmToken(userId, params);
            }
        }, headers, userId, params);

        subscriptions.add(subscription);
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

    public void getDeviceList(String homeId, final int index) {
        view.showLoading();
        Log.e("DEVICE","CALLED!!");

        Subscription subscription = service.getDeviceList(new Service.GetDeviceListCallback() {
            @Override
            public void onSuccess(Response<List<Device>> deviceList) {
                view.hideLoading();
                view.getDeviceSuccess(deviceList, index);
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
