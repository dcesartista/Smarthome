package id.ac.ugm.smartcity.smarthome.Networking;

/**
 * Created by dito on 09/02/17.
 */

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.App;
import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Model.User;
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

    public Subscription getDeviceList(final GetDeviceListCallback callback, Map<String, String> headers, String homeId){
        return networkService.getDeviceList(headers, homeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<Device>>>>() {
                    @Override
                    public Observable<? extends Response<List<Device>>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<List<Device>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<List<Device>> devices) {
                        callback.onSuccess(devices);
                    }

                });
    }

    public Subscription getRelayData(final GetRelayDataCallBack callback, Map<String, String> headers,
                                     String homeId, String deviceId){
        return networkService.getRelayData(headers, homeId,deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<Relay>>>() {
                    @Override
                    public Observable<? extends Response<Relay>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<Relay>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<Relay> response) {
                        callback.onSuccess(response);
                    }

                });
    }

    public Subscription changeRelayData(final ChangeRelayDataCallBack callback, Map<String, String> headers,
                                     String homeId, String deviceId, String relayId, Map<String, String> params){
        return networkService.changeRelayStatus(headers, homeId,deviceId, relayId, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<String>>>() {
                    @Override
                    public Observable<? extends Response<String>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<String> response) {
                        callback.onSuccess(response);
                    }

                });
    }

    public Subscription getCurrentDeviceData(final GetCurrentDeviceDataCallback callback, Map<String, String> headers,
                                             String homeId, String deviceId){
        return networkService.getCurrentDeviceData(headers, homeId, deviceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<CurrentDeviceData>>>() {
                    @Override
                    public Observable<? extends Response<CurrentDeviceData>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<CurrentDeviceData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<CurrentDeviceData> response) {
                        callback.onSuccess(response);
                    }

                });
    }

    public Subscription getCurrenEnergy(final GetCurrentEnergyCallback callback, Map<String, String> headers,
                                             String homeId){
        return networkService.getCurrentEnergy(headers, homeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<CurrentEnergy>>>() {
                    @Override
                    public Observable<? extends Response<CurrentEnergy>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<CurrentEnergy>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<CurrentEnergy> response) {
                        callback.onSuccess(response);
                    }

                });
    }

    public Subscription getEnergyHistory(final GetEnergyHistoryCallback callback, String startDate, Map<String, String> headers
            , int range , String homeId) {
        switch (range) {
            case App.DAILY:
                return networkService.getEnergyDaily(headers, homeId, startDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<String>>>>() {
                            @Override
                            public Observable<? extends Response<List<String>>> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        })
                        .subscribe(new Subscriber<Response<List<String>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(new NetworkError(e));

                            }

                            @Override
                            public void onNext(Response<List<String>> histories) {
                                callback.onSuccess(histories);
                            }

                        });
            case App.MONTHLY:
                return networkService.getEnergyMonthly(headers, homeId, startDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<String>>>>() {
                            @Override
                            public Observable<? extends Response<List<String>>> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        })
                        .subscribe(new Subscriber<Response<List<String>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(new NetworkError(e));

                            }

                            @Override
                            public void onNext(Response<List<String>> histories) {
                                callback.onSuccess(histories);
                            }

                        });
            case App.YEARLY:
                return networkService.getEnergyYearly(headers, homeId, startDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<List<String>>>>() {
                            @Override
                            public Observable<? extends Response<List<String>>> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        })
                        .subscribe(new Subscriber<Response<List<String>>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(new NetworkError(e));

                            }

                            @Override
                            public void onNext(Response<List<String>> histories) {
                                callback.onSuccess(histories);
                            }

                        });
            default:
                return null;
        }
    }

    public Subscription getHistory(final GetHistoryCallback callback, String startDate, Map<String, String> headers
            , int type, int range, String homeId, String deviceId) {

        switch (type){
            case App.TEMPERATURE:
                switch (range){
                    case App.DAILY:
                        return networkService.getTemperaturesDaily(headers, homeId, deviceId, startDate)
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
                        return networkService.getTemperaturesMonthly(headers, homeId, deviceId, startDate)
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
                        return networkService.getTemperaturesYearly(headers, homeId, deviceId, startDate)
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
                        return networkService.getHumiditiesDaily(headers, homeId, deviceId, startDate)
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
                        return networkService.getHumiditiesMonthly(headers, homeId, deviceId, startDate)
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
                        return networkService.getHumiditiesYearly(headers, homeId, deviceId, startDate)
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
                        return networkService.getCarbondioxideDaily(headers, homeId, deviceId, startDate)
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
                        return networkService.getCarbondioxideMonthly(headers, homeId, deviceId, startDate)
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
                        return networkService.getCarbondioxideYearly(headers, homeId, deviceId, startDate)
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

    public Subscription updateUser(final UpdateUserCallback callback, Map<String, String> headers, String userId, Map<String, String> params) {

        return networkService.updateUser(headers, userId, params)
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

    public Subscription addNewDevice(final AddNewDeviceCallback callback, Map<String, String> headers
            , String homeId, Map<String,String> params) {

        return networkService.addNewDevice(headers, homeId, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Response<Device>>>() {
                    @Override
                    public Observable<? extends Response<Device>> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<Response<Device>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(Response<Device> response) {
                        callback.onSuccess(response);
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

    public interface GetRelayDataCallBack{
        void onSuccess(Response<Relay> response);

        void onError(NetworkError networkError);
    }

    public interface ChangeRelayDataCallBack{
        void onSuccess(Response<String> response);

        void onError(NetworkError networkError);
    }

    public interface GetCurrentDeviceDataCallback{
        void onSuccess(Response<CurrentDeviceData> response);

        void onError(NetworkError networkError);
    }

    public interface GetCurrentEnergyCallback{
        void onSuccess(Response<CurrentEnergy> response);

        void onError(NetworkError networkError);
    }

    public interface GetHistoryCallback{
        void onSuccess(Response<List<HistoryData>> temperatureHistories);

        void onError(NetworkError networkError);
    }

    public interface GetEnergyHistoryCallback{
        void onSuccess(Response<List<String>> temperatureHistories);

        void onError(NetworkError networkError);
    }

    public interface GetDeviceListCallback{
        void onSuccess(Response<List<Device>> deviceList);

        void onError(NetworkError networkError);
    }

    public interface AddNewDeviceCallback{
        void onSuccess(Response<Device> response);

        void onError(NetworkError networkError);
    }

    public interface UpdateUserCallback{
        void onSuccess(Response<User> response);

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
