package id.ac.ugm.smartcity.smarthome.Networking;

/**
 * Created by dito on 09/02/17.
 */
public class NetworkError {
    private Throwable throwable;
    public NetworkError(Throwable e) {
        throwable = e;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
