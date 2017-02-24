package id.ac.ugm.smartcity.smarthome.Networking;

/**
 * Created by dito on 09/02/17.
 */

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.User;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getDeviceList(final GetDeviceListCallback callback){
        return networkService.getDeviceList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Device>>>() {
                    @Override
                    public Observable<? extends List<Device>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<Device>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<Device> devices) {
                        callback.onSuccess(devices);
                    }

                });
    }

    public Subscription getAlertList(final GetAlertListCallback callback) {

        return networkService.getAlertList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Alert>>>() {
                    @Override
                    public Observable<? extends List<Alert>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<List<Alert>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(List<Alert> alerts) {
                        callback.onSuccess(alerts);
                    }

                });
    }

    public Subscription signIn(final SignInCallback callback, Map<String, String> loginParams){

        return networkService.signIn(loginParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<User>>>() {
                    @Override
                    public Observable<? extends Response<User>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(Response<User> response) {
                        callback.onSuccess(response);
                    }
                });
    }

    public interface GetAlertListCallback{
        void onSuccess(List<Alert> alertList);

        void onError(NetworkError networkError);
    }

    public interface GetDeviceListCallback{
        void onSuccess(List<Device> deviceList);

        void onError(NetworkError networkError);
    }

    public interface SignInCallback{
        void onSuccess(Response<User> response);

        void onError(NetworkError networkError);
    }
}
