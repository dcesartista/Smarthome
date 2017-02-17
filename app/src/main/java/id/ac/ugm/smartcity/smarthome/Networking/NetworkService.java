package id.ac.ugm.smartcity.smarthome.Networking;

import java.util.List;

import id.ac.ugm.smartcity.smarthome.Model.Device;
import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by dito on 09/02/17.
 */

public interface NetworkService {

    @GET("alerts")
    Observable<List<Alert>> getAlertList();

    @GET("devices")
    Observable<List<Device>> getDeviceList();

}
