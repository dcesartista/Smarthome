package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
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

    public interface GetAlertListCallback{
        void onSuccess(List<Alert> alertList);

        void onError(NetworkError networkError);
    }
}
