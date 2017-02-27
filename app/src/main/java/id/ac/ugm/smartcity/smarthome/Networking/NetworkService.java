package id.ac.ugm.smartcity.smarthome.Networking;

import java.util.List;
import java.util.Map;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Login.LoginUser;
import id.ac.ugm.smartcity.smarthome.Model.User_Model.Register.RegisterUser;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by dito on 09/02/17.
 */

public interface NetworkService {

    @GET("alerts")
    Observable<List<Alert>> getAlertList();

    @GET("devices")
    Observable<List<Device>> getDeviceList();

    @POST("auth/sign_in")
    Observable<Response<LoginUser>> signIn(
            @QueryMap Map<String,String> params
            );

    @POST("auth")
    Observable<Response<RegisterUser>> register(
            @QueryMap Map<String,String> params
    );

}
