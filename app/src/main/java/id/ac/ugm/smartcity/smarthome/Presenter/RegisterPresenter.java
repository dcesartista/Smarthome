package id.ac.ugm.smartcity.smarthome.Presenter;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.View.LoginView;
import id.ac.ugm.smartcity.smarthome.View.RegisterView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 18/02/17.
 */

public class RegisterPresenter {
    private final Service service;
    private final RegisterView view;
    private CompositeSubscription subscriptions;

    public RegisterPresenter(Service service, RegisterView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void register(Map<String,String> loginParams) {
        view.showLoading();

        Subscription subscription = service.register(new Service.RegisterCallBack() {
            @Override
            public void onSuccess(Response<RegisterUser> response) {
                Log.e("HMM",String.valueOf(response.code()));
                view.hideLoading();
                if(response.code() == 200)
                    view.registerSuccess(response);
                else{
                    try {
                        JsonElement jelement = new JsonParser().parse(response.errorBody().string());
                        JsonObject jobject = jelement.getAsJsonObject();
                        jobject = jobject.getAsJsonObject("errors");
                        JsonArray messages = jobject.getAsJsonArray("full_messages");
                        view.registerFailed(messages.get(messages.size()-1).getAsString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        }, loginParams);

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
