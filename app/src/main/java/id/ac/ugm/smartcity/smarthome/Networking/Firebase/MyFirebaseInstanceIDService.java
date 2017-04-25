package id.ac.ugm.smartcity.smarthome.Networking.Firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.DaggerDeps;
import id.ac.ugm.smartcity.smarthome.Deps;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkModule;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.R;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Inject
    public Service service;

    SharedPreferences preferences;
    private CompositeSubscription subscriptions;
    Deps deps;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        subscriptions = new CompositeSubscription();
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
        deps.inject(this);

        preferences = getSharedPreferences(App.USER_PREFERENCE, MODE_PRIVATE);
        String userId = preferences.getString(App.ID,"FAIL");
        if(!userId.equals("FAIL")){
            Map<String,String> params = new HashMap<>();
            params.put(User.FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());

            updateFcmToken(userId, params);
        }

    }

    void updateFcmToken(final String userId, final Map<String, String> params){
        Resources resources = getResources();
        preferences = getSharedPreferences(App.USER_PREFERENCE, Context.MODE_PRIVATE);
        Map<String, String> headers = new HashMap<>();
        headers.put(resources.getString(R.string.access_token), preferences.getString(App.ACCESS_TOKEN,""));
        headers.put(resources.getString(R.string.token_type), resources.getString(R.string.bearer));
        headers.put(resources.getString(R.string.client), preferences.getString(App.CLIENT,""));
        headers.put(resources.getString(R.string.expiry), preferences.getString(App.EXPIRY,""));
        headers.put(resources.getString(R.string.uid), preferences.getString(App.UID,""));

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriptions.unsubscribe();
    }
}
