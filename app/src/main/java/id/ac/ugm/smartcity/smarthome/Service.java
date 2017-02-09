package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

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

    public Subscription getCityList(final GetCityListCallback callback) {

        return networkService.getAlertList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Alert>>() {
                    @Override
                    public Observable<? extends Alert> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Alert>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Alert cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public interface GetCityListCallback{
        void onSuccess(Alert cityListResponse);

        void onError(NetworkError networkError);
    }
}
