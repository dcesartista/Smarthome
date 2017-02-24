package id.ac.ugm.smartcity.smarthome.Presenter;

import android.util.Log;

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.User_Model.User;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkError;
import id.ac.ugm.smartcity.smarthome.Networking.Service;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.AlertView;
import id.ac.ugm.smartcity.smarthome.View.LoginView;
import retrofit2.Response;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 18/02/17.
 */

public class LoginPresenter {
    private final Service service;
    private final LoginView view;
    private CompositeSubscription subscriptions;

    public LoginPresenter(Service service, LoginView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void signIn(Map<String,String> loginParams) {
        view.showLoading();

        Subscription subscription = service.signIn(new Service.SignInCallback() {
            @Override
            public void onSuccess(Response<User> response) {
                view.hideLoading();
                view.loginSuccess(response);
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
