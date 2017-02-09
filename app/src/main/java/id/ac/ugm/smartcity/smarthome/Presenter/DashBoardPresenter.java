package id.ac.ugm.smartcity.smarthome.Presenter;

/**
 * Created by dito on 09/02/17.
 */

import id.ac.ugm.smartcity.smarthome.DashBoardView;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import id.ac.ugm.smartcity.smarthome.NetworkError;
import id.ac.ugm.smartcity.smarthome.Service;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class DashBoardPresenter {
    private final Service service;
    private final DashBoardView view;
    private CompositeSubscription subscriptions;

    public DashBoardPresenter(Service service, DashBoardView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getAlertList() {
        view.showLoading();

        Subscription subscription = service.getCityList(new Service.GetCityListCallback() {
            @Override
            public void onSuccess(Alert alert) {
                view.hideLoading();
                view.getAlertSuccess(alert);
            }

            @Override
            public void onError(NetworkError networkError) {
                view.hideLoading();
            }

        });

        subscriptions.add(subscription);
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
