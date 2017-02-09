package id.ac.ugm.smartcity.smarthome.Presenter;

import android.util.Log;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.NetworkError;
import id.ac.ugm.smartcity.smarthome.Service;
import id.ac.ugm.smartcity.smarthome.DeviceView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by dito on 09/02/17.
 */

public class DevicePresenter {
    private final Service service;
    private final DeviceView view;
    private CompositeSubscription subscriptions;

    public DevicePresenter(Service service, DeviceView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getDeviceList() {
        view.showLoading();

        Subscription subscription = service.getDeviceList(new Service.GetDeviceListCallback() {
            @Override
            public void onSuccess(List<Device> deviceList) {
                view.hideLoading();
                view.getDeviceSuccess(deviceList);
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
