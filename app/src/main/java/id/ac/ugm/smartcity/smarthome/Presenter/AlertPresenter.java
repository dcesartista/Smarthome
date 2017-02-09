package id.ac.ugm.smartcity.smarthome.Presenter;

/**
 * Created by dito on 09/02/17.
 */

import android.util.Log;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.AlertView;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import id.ac.ugm.smartcity.smarthome.NetworkError;
import id.ac.ugm.smartcity.smarthome.Service;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class AlertPresenter {
    private final Service service;
    private final AlertView view;
    private CompositeSubscription subscriptions;

    public AlertPresenter(Service service, AlertView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getAlertList() {
        view.showLoading();

        Subscription subscription = service.getAlertList(new Service.GetAlertListCallback() {
            @Override
            public void onSuccess(List<Alert> alertList) {
                view.hideLoading();
                view.getAlertSuccess(alertList);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
                Log.d("ERROR", networkError.getThrowable().getMessage());
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
