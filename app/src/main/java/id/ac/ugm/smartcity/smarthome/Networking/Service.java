package id.ac.ugm.smartcity.smarthome.Networking;

/**
 * Created by dito on 09/02/17.
 */

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
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

    public Subscription getDeviceList(final GetDeviceListCallback callback, Map<String, String> headers){
        return networkService.getDeviceList(headers)
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

    public Subscription getHistory(final GetHistoryCallback callback, String startDate, Map<String, String> headers
            , int type, int range) {

        switch (type){
            case App.TEMPERATURE:
                switch (range){
                    case App.DAILY:
                        return networkService.getTemperaturesDaily(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.MONTHLY:
                        return networkService.getTemperaturesMonthly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.YEARLY:
                        return networkService.getTemperaturesYearly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    default:
                        return null;
                }
            case App.HUMIDITIY:
                switch (range){
                    case App.DAILY:
                        return networkService.getHumiditiesDaily(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.MONTHLY:
                        return networkService.getHumiditiesMonthly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.YEARLY:
                        return networkService.getHumiditiesYearly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    default:
                        return null;
                }
            case App.CARBONDIOXIDE:
                switch (range){
                    case App.DAILY:
                        return networkService.getCarbondioxideDaily(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.MONTHLY:
                        return networkService.getCarbondioxideMonthly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    case App.YEARLY:
                        return networkService.getCarbondioxideYearly(headers, startDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<HistoryData>>>>() {
                                    @Override
                                    public Observable<? extends Response<List<HistoryData>>> call(Throwable throwable) {
                                        return Observable.error(throwable);
                                    }
                                })
                                .subscribe(new Subscriber<Response<List<HistoryData>>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        callback.onError(new NetworkError(e));

                                    }

                                    @Override
                                    public void onNext(Response<List<HistoryData>> histories) {
                                        callback.onSuccess(histories);
                                    }

                                });
                    default:
                        return null;
                }
            default:
                return null;
        }

    }


    public Subscription getAlertList(final GetAlertListCallback callback, Map<String, String> headers) {

        return networkService.getAlertList(headers)
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
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<LoginUser>>>() {
                    @Override
                    public Observable<? extends Response<LoginUser>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<LoginUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(Response<LoginUser> response) {
                        callback.onSuccess(response);
                    }
                });
    }

    public Subscription register(final RegisterCallBack callback, Map<String, String> loginParams){

        return networkService.register(loginParams)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<RegisterUser>>>() {
                    @Override
                    public Observable<? extends Response<RegisterUser>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<RegisterUser>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));
                    }

                    @Override
                    public void onNext(Response<RegisterUser> response) {
                        callback.onSuccess(response);
                    }
                });
    }




    public interface GetAlertListCallback{
        void onSuccess(List<Alert> alertList);

        void onError(NetworkError networkError);
    }

    public interface GetHistoryCallback{
        void onSuccess(Response<List<HistoryData>> temperatureHistories);

        void onError(NetworkError networkError);
    }

    public interface GetDeviceListCallback{
        void onSuccess(List<Device> deviceList);

        void onError(NetworkError networkError);
    }

    public interface SignInCallback{
        void onSuccess(Response<LoginUser> response);

        void onError(NetworkError networkError);
    }

    public interface RegisterCallBack{
        void onSuccess(Response<RegisterUser> response);

        void onError(NetworkError networkError);
    }
}
