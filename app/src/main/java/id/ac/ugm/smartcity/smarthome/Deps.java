package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import javax.inject.Singleton;

import dagger.Component;
import id.ac.ugm.smartcity.smarthome.Networking.Firebase.MyFirebaseInstanceIDService;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkModule;
import id.ac.ugm.smartcity.smarthome.View.ACActivity;
import id.ac.ugm.smartcity.smarthome.View.AlertDetailActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.Fragment.Device.AddDeviceActivity;
import id.ac.ugm.smartcity.smarthome.View.DeviceDetailActivity;
import id.ac.ugm.smartcity.smarthome.View.HomeSettingActivity;
import id.ac.ugm.smartcity.smarthome.View.LoginActivity;
import id.ac.ugm.smartcity.smarthome.View.NewHomeActivity;
import id.ac.ugm.smartcity.smarthome.View.RegisterActivity;
import id.ac.ugm.smartcity.smarthome.View.RelayActivity;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(DashBoardActivity dashBoardActivity);
    void inject(LoginActivity loginActivity);
    void inject(RegisterActivity registerActivity);
    void inject(AddDeviceActivity addDeviceActivity);
    void inject(RelayActivity relayActivity);
    void inject(MyFirebaseInstanceIDService myFirebaseInstanceIDService);
    void inject(NewHomeActivity newHomeActivity);
    void inject(DeviceDetailActivity deviceDetailActivity);
    void inject(ACActivity acActivity);
    void inject(HomeSettingActivity homeSettingActivity);
    void inject(AlertDetailActivity alertDetailActivity);
}
