package id.ac.ugm.smartcity.smarthome;

import id.ac.ugm.smartcity.smarthome.Model.recycleritem.Alert;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by dito on 09/02/17.
 */

public interface NetworkService {

    @GET("v1/city")
    Observable<Alert> getAlertList();

}
