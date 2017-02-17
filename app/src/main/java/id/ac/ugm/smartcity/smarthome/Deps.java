package id.ac.ugm.smartcity.smarthome;

/**
 * Created by dito on 09/02/17.
 */

import javax.inject.Singleton;

import dagger.Component;
import id.ac.ugm.smartcity.smarthome.Networking.NetworkModule;
import id.ac.ugm.smartcity.smarthome.View.Dashboard.DashBoardActivity;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(DashBoardActivity dashBoardActivity);
}
