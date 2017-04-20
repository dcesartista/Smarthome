package id.ac.ugm.smartcity.smarthome.Networking;

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.CurrentDeviceData;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("homes/{home_id}/devices/{device_id}/current")
    Observable<Response<CurrentDeviceData>> getCurrentDeviceData(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @GET("homes/{home_id}/current_energy")
    Observable<Response<CurrentEnergy>> getCurrentEnergy(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId
    );

    @POST("homes/{home_id}/devices/{device_id}/temperatures_daily")
    Observable<Response<List<HistoryData>>> getTemperaturesDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/temperatures_monthly")
    Observable<Response<List<HistoryData>>> getTemperaturesMonthly(
            @HeaderMap Map<String, String> headers,
            @Query("start_date") String startDate,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @POST("homes/{home_id}/devices/{device_id}/temperatures_yearly")
    Observable<Response<List<HistoryData>>> getTemperaturesYearly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/humidities_daily")
    Observable<Response<List<HistoryData>>> getHumiditiesDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/humidities_monthly")
    Observable<Response<List<HistoryData>>> getHumiditiesMonthly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/humidities_yearly")
    Observable<Response<List<HistoryData>>> getHumiditiesYearly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxide_daily")
    Observable<Response<List<HistoryData>>> getCarbondioxideDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxide_monthly")
    Observable<Response<List<HistoryData>>> getCarbondioxideMonthly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxide_yearly")
    Observable<Response<List<HistoryData>>> getCarbondioxideYearly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @GET("homes/{home_id}/devices")
    Observable<Response<List<Device>>> getDeviceList(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId
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
