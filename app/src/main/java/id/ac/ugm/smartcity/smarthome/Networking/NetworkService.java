package id.ac.ugm.smartcity.smarthome.Networking;

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.AlertGroup;
import id.ac.ugm.smartcity.smarthome.Model.CurrentEnergy;
import id.ac.ugm.smartcity.smarthome.Model.CurrentSensor;
import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.HistoryData;
import id.ac.ugm.smartcity.smarthome.Model.Home;
import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.Model.User;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by dito on 09/02/17.
 */

public interface NetworkService {

    @GET("homes")
    Observable<Response<List<Home>>> getHomes(
            @HeaderMap Map<String, String> headers
    );

    @POST("homes")
    Observable<Response<Home>> postNewHome(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, String> params
    );

    @PATCH("homes/{home_id}")
    Observable<Response<Home>> changeHome(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @QueryMap Map<String, String> params
    );

    @GET("homes/{home_id}/alert")
    Observable<Response<List<AlertGroup>>> getAlert(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId
    );

    @GET("homes/{home_id}/cost")
    Observable<Response<String>> getCurrentCost(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId
    );

    @POST("homes/{home_id}/cost_daily")
    Observable<Response<List<String>>> getCostDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/cost_monthly")
    Observable<Response<List<String>>> getCostMonthly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/voltage_hourly")
    Observable<Response<List<HistoryData>>> getVoltageHourly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/voltage_daily")
    Observable<Response<List<HistoryData>>> getVoltageDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/current_hourly")
    Observable<Response<List<HistoryData>>> getCurrentHourly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/current_daily")
    Observable<Response<List<HistoryData>>> getCurrentDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @GET("homes/{home_id}/current_energy")
    Observable<Response<CurrentEnergy>> getCurrentEnergy(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId
    );

    @POST("homes/{home_id}/energy_daily")
    Observable<Response<List<String>>> getEnergyDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/energy_monthly")
    Observable<Response<List<String>>> getEnergyMonthly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/energy_yearly")
    Observable<Response<List<String>>> getEnergyYearly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Query("start_date") String startDate
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
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
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
            @Query("start_date") String startDate,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @POST("homes/{home_id}/devices/{device_id}/humidities_yearly")
    Observable<Response<List<HistoryData>>> getHumiditiesYearly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxides_daily")
    Observable<Response<List<HistoryData>>> getCarbondioxideDaily(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxides_monthly")
    Observable<Response<List<HistoryData>>> getCarbondioxideMonthly(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Query("start_date") String startDate
    );

    @POST("homes/{home_id}/devices/{device_id}/carbondioxides_yearly")
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

    @Multipart
    @POST("homes/{home_id}/devices")
    Observable<Response<Device>> addNewDevice(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Part(Device.NAME)RequestBody name,
            @Part(Device.PRODUCT_ID) RequestBody productId,
            @Part MultipartBody.Part image
            );

    /*@POST("homes/{home_id}/devices")
    Observable<Response<Device>> addNewDevice(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @QueryMap Map<String, String> params
    );*/

    @GET("homes/{home_id}/devices/{device_id}")
    Observable<Response<Device>> getDevice(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @GET("homes/{home_id}/devices/{device_id}/current_sensor")
    Observable<Response<CurrentSensor>> getCurrentSensor(
            @HeaderMap Map<String, String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @GET("homes/{home_id}/devices/{device_id}/relays")
    Observable<Response<Relay>> getRelayData(
            @HeaderMap Map<String,String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId
    );

    @PATCH("homes/{home_id}/devices/{device_id}/relays/{relay_id}")
    Observable<Response<Relay>> changeRelayStatus(
            @HeaderMap Map<String,String> headers,
            @Path("home_id") String homeId,
            @Path("device_id") String deviceId,
            @Path("relay_id") String relayId,
            @QueryMap Map<String, String> params
    );

    @PATCH("users/{user_id}")
    Observable<Response<User>> updateUser(
            @HeaderMap Map<String, String> headers,
            @Path("user_id") String userId,
            @QueryMap Map<String,String> params
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
