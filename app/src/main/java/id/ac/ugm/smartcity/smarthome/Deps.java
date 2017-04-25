package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import javax.inject.Singleton;

import dagger.Component;
import id.ac.ugm.smartcity.smarthome.Networking.Firebase.MyFirebaseInstanceIDService;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkModule;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import id.ac.ugm.smartcity.smarthome.View.LoginActivity;
import id.ac.ugm.smartcity.smarthome.View.RegisterActivity;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(DashBoardActivity dashBoardActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(MyFirebaseInstanceIDService myFirebaseInstanceIDService);
}
