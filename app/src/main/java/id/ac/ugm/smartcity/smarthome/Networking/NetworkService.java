package id.ac.ugm.smartcity.smarthome.Networking;

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by dito on 09/02/17.
 */

public interface NetworkService {

    @GET("alerts")
    Observable<List<Alert>> getAlertList(
            @HeaderMap Map<String, String> headers
    );

    @POST("temperatures_daily")
    Observable<List<HistoryData>> getTemperaturesDaily(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("temperatures_monthly")
    Observable<List<HistoryData>> getTemperaturesMonthly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("temperatures_yearly")
    Observable<List<HistoryData>> getTemperaturesYearly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("humidities_daily")
    Observable<List<HistoryData>> getHumiditiesDaily(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("humidities_monthly")
    Observable<List<HistoryData>> getHumiditiesMonthly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("humidities_yearly")
    Observable<List<HistoryData>> getHumiditiesYearly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("carbondioxide_daily")
    Observable<List<HistoryData>> getCarbondioxideDaily(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("carbondioxide_monthly")
    Observable<List<HistoryData>> getCarbondioxideMonthly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @POST("carbondioxide_yearly")
    Observable<List<HistoryData>> getCarbondioxideYearly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate
    );

    @GET("devices")
    Observable<List<Device>> getDeviceList(
            @HeaderMap Map<String, String> headers
    );

    @POST("auth/sign_in")
    Observable<Response<LoginUser>> signIn(
            @QueryMap Map<String,String> params
            );

    @POST("auth")
    Observable<Response<RegisterUser>> register(
            @QueryMap Map<String,String> params
    );

}
